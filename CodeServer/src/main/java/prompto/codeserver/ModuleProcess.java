package prompto.codeserver;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.ProcessBuilder.Redirect;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import prompto.server.AppServer;
import prompto.server.PromptoServlet;
import prompto.store.IDataStore;
import prompto.store.IStored;
import prompto.utils.Logger;
import prompto.value.IValue;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.esotericsoftware.yamlbeans.YamlConfig.WriteClassName;
import com.esotericsoftware.yamlbeans.document.YamlDocument;
import com.esotericsoftware.yamlbeans.document.YamlDocumentReader;
import com.esotericsoftware.yamlbeans.document.YamlEntry;
import com.esotericsoftware.yamlbeans.document.YamlMapping;
import com.esotericsoftware.yamlbeans.document.YamlScalar;

/* represents the process used to run a Module on the server */
public class ModuleProcess {
	
	static Logger logger = new Logger();
	static Map<Object, ModuleProcess> modules = new HashMap<>();
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread(ModuleProcess::shutDownAll));
	}
	
	static void shutDownAll() {
		logger.info(()->"Shutting down module servers...");
		synchronized(modules) {
			List<ModuleProcess> values = new ArrayList<>(modules.values());
			modules.clear();
			values.forEach(ModuleProcess::shutDown);
		}
	}
	
	public static Long launchIfNeeded(Object dbId) {
		synchronized(modules) {
			try {
				if(dbId instanceof IValue)
					dbId = ((IValue)dbId).getStorableData();
				// already launched ?
				ModuleProcess module = modules.get(dbId);
				// if no longer alive recreate 
				if(module!=null && !module.process.isAlive())
					module = null;
				// create needed ?
				if(module==null) {
					module = createModuleProcess(dbId);
					modules.put(dbId, module);
				}
				return new Long(module.port);
			} catch(Throwable t) {
				t.printStackTrace();
				return null; // TODO
			}
		}
	}

	private static ModuleProcess createModuleProcess(Object dbId) throws Exception {
		IStored stored = IDataStore.instance.get().fetchUnique(dbId);
		if(stored==null)
			return null;
		ModuleProcess module = new ModuleProcess();
		module.dbId = dbId;
		module.module = stored.getData("name").toString();
		module.version = stored.getData("version").toString();
		module.start();
		return module;
	}
	
	static class OutStream {
		
		static Process waitForServerReadiness(ProcessBuilder builder) throws IOException, InterruptedException {
			OutStream out = new OutStream(builder);
			return out.waitForServerReadiness();
		}
		
		ProcessBuilder builder;
		Object ready = new Object();
		
		OutStream(ProcessBuilder builder) {
			this.builder = builder;
		}
		
		Process waitForServerReadiness() throws InterruptedException, IOException {
			logger.info(()->"Starting: " + builder.command().toString());
			Process process = builder.start();
			InputStream input = process.getInputStream();
			Thread reader = new Thread(()->{
				byte[] data = new byte[0x10000];
				while(process.isAlive()) try {
					int read = input.read(data);
					if(read<0)
						break;
					if(read>0) {
						System.out.write(data, 0 , read);
						if(new String(data, 0, read).contains(AppServer.WEB_SERVER_SUCCESSFULLY_STARTED)) synchronized (ready) {
							ready.notify();
						}
					}
				} catch(IOException e) {
					e.printStackTrace(System.err);
				}
			});
			reader.start();
			synchronized(ready) {
				ready.wait();
			}
			return process;
		}
		
	}

	Object dbId;
	String module;
	String version;
	int port;
	Process process;

	public void start() throws Exception {
		this.port = findAvailablePort();
		String[] args = buildCommandLineArgs();
		ProcessBuilder builder = new ProcessBuilder(args)
			.redirectError(Redirect.INHERIT)
			.directory(Files.createTempDirectory("prompto-" + module + "-").toFile());
		this.process = OutStream.waitForServerReadiness(builder);
	}

	public void shutDown() {
		try {
			process.destroyForcibly();
			process.waitFor();
		} catch(InterruptedException e) {
			// OK
		}
	}
	

	private int findAvailablePort() throws IOException {
		try(ServerSocket s = new ServerSocket(0)) {
			return s.getLocalPort();
		}
	}

	private String[] buildCommandLineArgs() throws Exception {
		List<String> cmds = new ArrayList<String>();
		cmds.add("java");
		// cmds.add("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8888");
		addClassPathArgs(cmds);
		cmds.add(AppServer.class.getName());
		addPromptoArgs(cmds);
		return cmds.toArray(new String[cmds.size()]);
	}

	private void addPromptoArgs(List<String> args) throws Exception {
		if(isYamlConfig())
			addPromptoYamlConfigArgs(args);
		else
			addPromptoCommandLineArgs(args);
	}
	
	
	private void addPromptoCommandLineArgs(List<String> cmds) {
		addRelevantCmdLineArgs(cmds);
		addSpecificCmdLineArgs(cmds);
	}

	private void addSpecificCmdLineArgs(List<String> cmds) {
		cmds.add("-http-port");
		cmds.add(String.valueOf(port));
		cmds.add("-applicationName");
		cmds.add(module.toString());
		cmds.add("-applicationVersion");
		cmds.add(version.toString());
		String origin = PromptoServlet.REGISTERED_ORIGIN.get();
		if(origin!=null) {
			cmds.add("-http-allowedOrigin");
			cmds.add(origin);
		}
	}

	private boolean isYamlConfig() {
		String cmdLine = System.getProperty("sun.java.command").toString();
		return cmdLine.contains("-yamlConfigFile");
	}

	private void addPromptoYamlConfigArgs(List<String> cmds) throws Exception {
		File currentFile = locateYamlConfigFile();
		try(Reader reader = new FileReader(currentFile)) {
			YamlDocument currentYaml = new YamlDocumentReader(reader).read();
			writeSpecificYamlEntries(currentYaml);
			File targetFile = createTempYamlFile();
			cmds.add("-yamlConfigFile");
			cmds.add(targetFile.getAbsolutePath());
			logger.info(()->"Writing yaml config to " + targetFile.getAbsolutePath());
			try(Writer writer = new FileWriter(targetFile)) {
				YamlConfig config = new YamlConfig();
				config.writeConfig.setWriteClassname(WriteClassName.NEVER);
				config.writeConfig.setAutoAnchor(false);
				YamlWriter targetYaml = new YamlWriter(writer, config);
				targetYaml.write(currentYaml);
			}
		}
	}

	
	private void writeSpecificYamlEntries(YamlDocument document) throws YamlException {
		document.setEntry("applicationName", module.toString());
		document.setEntry("applicationVersion", version.toString());
		document.deleteEntry("webSiteRoot");
		writeCodeStoreYamlEntries(document);
		writeDataStoreYamlEntries(document);
		writeHttpYamlEntries(document);
			
	}

	private void writeDataStoreYamlEntries(YamlDocument document) throws YamlException {
		YamlEntry entry = (YamlEntry)document.getEntry("dataStore");
		YamlMapping store = (YamlMapping)entry.getValue();
		entry = store.getEntry("dbName");
		entry.setValue("DATA");
	}

	private void writeCodeStoreYamlEntries(YamlDocument document) throws YamlException {
		YamlEntry entry = (YamlEntry)document.getEntry("codeStore");
		YamlMapping store = (YamlMapping)entry.getValue();
		entry = store.getEntry("dbName");
		entry.setValue("CODE");
	}

	private void writeHttpYamlEntries(YamlDocument document) throws YamlException {
		YamlEntry entry = (YamlEntry)document.getEntry("http");
		YamlMapping http = (YamlMapping)entry.getValue();
		http.setEntry("port", port);
		String origin = PromptoServlet.REGISTERED_ORIGIN.get();
		if(origin!=null)
			http.setEntry("allowedOrigin", origin);
	}

	private File createTempYamlFile() throws IOException {
		return File.createTempFile("config-", ".yml");
	}

	File locateYamlConfigFile() throws Exception {
		String cmdLine = System.getProperty("sun.java.command").toString();
		String location = locateYamlConfigFile(cmdLine);
		return new File(location);
	}

	private void addClassPathArgs(List<String> cmds) {
		String classPaths = Stream.of(System.getProperty("java.class.path").toString().split(":"))
				.filter((s)->!s.contains("CodeServer"))
				.collect(Collectors.joining(":"));
		cmds.add("-cp");
		cmds.add(classPaths);
	}

	// see: https://stackoverflow.com/questions/13495449/how-to-split-a-command-line-like-string
	private static final Pattern splitter = Pattern.compile("[^\\s]*\"(\\\\+\"|[^\"])*?\"|[^\\s]*'(\\\\+'|[^'])*?'|(\\\\\\s|[^\\s])+", Pattern.MULTILINE);
	
	public static String locateYamlConfigFile(String cmdLine) {
		Matcher matcher = splitter.matcher(cmdLine);
		while(matcher.find()) {
			String key = matcher.group();
			if("-yamlConfigFile".equals(key) && matcher.find())
				return matcher.group();
		}
		return null;
	}

	private void addRelevantCmdLineArgs(List<String> cmds) {
		String cmdLine = System.getProperty("sun.java.command").toString();
		Matcher matcher = splitter.matcher(cmdLine);
		while(matcher.find()) {
			String key = matcher.group();
			if(isRelevantCmdLineArg(key)) {
				if(matcher.find()) {
					cmds.add(key);
					cmds.add(matcher.group());
				}
			}
		}
	}

	private static Set<String> relevantArgFullNames = new HashSet<>(Arrays.asList("-addOnURLs"));
	private static List<String> relevantArgStartNames = Arrays.asList("-codeStore-", "-dataStore-");
	
	private boolean isRelevantCmdLineArg(String key) {
		if(relevantArgFullNames.contains(key))
			return true;
		else
			return relevantArgStartNames.stream()
					.anyMatch(key::startsWith);
	}

}
	

