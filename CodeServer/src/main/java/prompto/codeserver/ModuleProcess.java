package prompto.codeserver;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;
import java.lang.ProcessBuilder.Redirect;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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

import prompto.config.CodeStoreAuthenticationConfiguration;
import prompto.config.StoredRecordConfigurationReader;
import prompto.runtime.Mode;
import prompto.server.AppServer;
import prompto.server.PromptoServlet;
import prompto.store.IDataStore;
import prompto.store.IStored;
import prompto.utils.Logger;
import prompto.utils.SocketUtils;
import prompto.value.IValue;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlConfig.WriteClassName;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlWriter;
import com.esotericsoftware.yamlbeans.document.YamlDocument;
import com.esotericsoftware.yamlbeans.document.YamlDocumentReader;
import com.esotericsoftware.yamlbeans.document.YamlEntry;
import com.esotericsoftware.yamlbeans.document.YamlMapping;

/* represents the process used to run a Module on the dev server */
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
	
	public static Long launchIfNeeded(Object dbId, Boolean optional) {
		if(optional==null)
			optional = false;
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
					if(optional)
						return -1L; // don't create it
					module = createModuleProcess(dbId);
					if(module!=null)
						modules.put(dbId, module);
					else {
						logger.warn(()->"Remote server failed to start!");
						return -1L; // TODO send error to client
					}
				}
				return new Long(module.port);
			} catch(Throwable t) {
				t.printStackTrace();
				return -1L; // TODO send error to client
			}
		}
	}

	private static ModuleProcess createModuleProcess(Object dbId) throws Exception {
		IStored stored = IDataStore.getInstance().fetchUnique(dbId);
		if(stored==null)
			return null;
		ModuleProcess module = new ModuleProcess(stored);
		module.start();
		return module.process.isAlive() ? module : null;
	}
	
	static class OutStream {
		
		static Process waitForServerReadiness(ProcessBuilder builder) throws IOException, InterruptedException {
			OutStream out = new OutStream(builder);
			return out.waitForServerReadiness();
		}
		
		ProcessBuilder builder;
		
		OutStream(ProcessBuilder builder) {
			this.builder = builder;
		}
		
		Process waitForServerReadiness() throws InterruptedException, IOException {
			logger.info(()->"Starting: " + builder.command().toString());
			Object ready = new Object();
			Process process = builder.start();
			Thread reader = new Thread(()->{
				try { 
					waitForServerReadiness(process);
				} catch(Throwable t) {
					t.printStackTrace(System.err);
				} finally {
					synchronized (ready) {
						ready.notify();
					}
				}
			});
			reader.start();
			synchronized(ready) {
				ready.wait();
			}
			return process;
		}
		
		private void waitForServerReadiness(Process process) throws IOException {
			InputStream input = process.getInputStream();
			byte[] data = new byte[0x10000];
			while(process.isAlive()) {
				int read = input.read(data);
				if(read<0)
					break;
				if(read>0) {
					System.out.write(data, 0 , read);
					if(new String(data, 0, read).contains(AppServer.WEB_SERVER_SUCCESSFULLY_STARTED)) 
						break;
				}
			}
		}

	}

	
	IStored stored;
	int port;
	Process process;

	public ModuleProcess(IStored stored) {
		this.stored = stored;
	}

	public void start() throws Exception {
		this.port = SocketUtils.findAvailablePortInRange(8080, 9090); // TODO extract from security group
		String[] args = buildCommandLineArgs();
		ProcessBuilder builder = new ProcessBuilder(args)
			.redirectError(Redirect.INHERIT)
			.directory(Files.createTempDirectory("prompto-" + getModuleName() + "-").toFile());
		this.process = OutStream.waitForServerReadiness(builder);
	}

	private String getModuleName() {
		return stored.getData("name").toString();
	}


	private String getModuleVersion() {
		return stored.getData("version").toString();
	}

	private String getStartMethod() {
		Object value = stored.getData("startMethod");
		return value==null ? null : value.toString();
	}

	private String getServerAboutToStartMethod() {
		Object value = stored.getData("serverAboutToStartMethod");
		return value==null ? null : value.toString();
	}

	public void shutDown() {
		try {
			process.destroyForcibly();
			process.waitFor();
		} catch(InterruptedException e) {
			// OK
		}
	}
	

	private String[] buildCommandLineArgs() throws Exception {
		List<String> cmds = new ArrayList<String>();
		cmds.add("java");
		String debugPort = System.getenv("PROMPTO_DEBUG_TARGET_PORT");
		if(debugPort!=null && !debugPort.isEmpty()) {
			cmds.add("-Xdebug");
			cmds.add("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=" + debugPort);
		}
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
		cmds.add(getModuleName());
		cmds.add("-applicationVersion");
		cmds.add(getModuleVersion());
		String origin = PromptoServlet.REGISTERED_ORIGIN.get();
		if(origin!=null) {
			cmds.add("-http-allowedOrigins");
			cmds.add(origin);
			cmds.add("-http-allowsXAuthorization");
			cmds.add("true");
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
		document.setEntry("applicationName", getModuleName());
		document.setEntry("applicationVersion", getModuleVersion());
		document.setEntry("runtimeMode", Mode.DEVELOPMENT.name());
		document.deleteEntry("webSiteRoot");
		document.deleteEntry("startMethod");
		String method = getStartMethod();
		if(method!=null)
			document.setEntry("startMethod", method);
		document.deleteEntry("serverAboutToStart");
		method = getServerAboutToStartMethod();
		if(method!=null)
			document.setEntry("serverAboutToStart", method);
		writeCodeStoreYamlEntries(document);
		writeDataStoreYamlEntries(document);
		writeHttpYamlEntries(document);
			
	}

	private void writeDataStoreYamlEntries(YamlDocument document) throws YamlException {
		YamlEntry entry = document.getEntry("dataStore");
		YamlMapping current = (YamlMapping)entry.getValue();
		// create a copy
		YamlMapping target = new YamlMapping();
		for(int i=0; i<current.size(); i++) {
			entry = current.getEntry(i);
			if("dbName".equals(entry.getKey()))
				continue;
			else
				target.setEntry(entry.getKey().getValue(), entry.getValue());
		}
		target.setEntry("dbName", "APPS"); // TODO make this configurable
		document.setEntry("dataStore", target);
	}

	private void writeCodeStoreYamlEntries(YamlDocument document) throws YamlException {
		YamlEntry entry = document.getEntry("dataStore");
		document.setEntry("codeStore", entry.getValue());
	}

	
	private void writeHttpYamlEntries(YamlDocument document) throws YamlException {
		YamlEntry entry = document.getEntry("http");
		YamlMapping http = (YamlMapping)entry.getValue();
		http.setEntry("port", port);
		http.deleteEntry("redirectFrom");
		http.deleteEntry("sendsXAuthorization");
		String origin = PromptoServlet.REGISTERED_ORIGIN.get();
		if(origin!=null) {
			http.setEntry("allowedOrigins", origin);
			http.setEntry("allowsXAuthorization", true);
		}
		YamlMapping auth = authenticationSettingsToYaml();
		if(auth!=null)
			http.setEntry("authentication", auth);
	}

	private boolean hasAuthenticationSettings() {
		return stored.hasData("authenticationSettings");
	}

	private YamlMapping authenticationSettingsToYaml() throws YamlException {
		if(hasAuthenticationSettings()) {
			StoredRecordConfigurationReader reader = new StoredRecordConfigurationReader(IDataStore.getInstance(), stored);
			CodeStoreAuthenticationConfiguration config = new CodeStoreAuthenticationConfiguration(reader);
			return config.toYaml(Mode.DEVELOPMENT);
		} else
			return null;
	}

	private File createTempYamlFile() throws IOException {
		return File.createTempFile("config-", ".yml");
	}

	File locateYamlConfigFile() throws Exception {
		String location = locateYamlConfigFilePath();
		return new File(location);
	}

	private void addClassPathArgs(List<String> cmds) throws URISyntaxException {
		if(isRunningFromJar())
			addServerJarArgs(cmds);
		else
			addImplicitClassPathArgs(cmds);
	}

	private boolean isRunningFromJar() {
		String[] args = System.getProperty("sun.java.command").split(" ");
		return args[0].toLowerCase().endsWith(".jar");
	}

	private void addImplicitClassPathArgs(List<String> cmds) {
		cmds.add("-cp");
		String classPaths = Stream.of(System.getProperty("java.class.path").toString().split(":"))
				.filter((s)->!s.startsWith(this.getClass().getPackage().getName()))
				.collect(Collectors.joining(":"));
		cmds.add(classPaths);
	}

	private void addServerJarArgs(List<String> cmds) throws URISyntaxException {
		URL thisJar = this.getClass().getProtectionDomain().getCodeSource().getLocation();
		File parent = Paths.get(thisJar.toURI()).getParent().toFile();
		for(File file : parent.listFiles()) {
			if(file.getName().startsWith("Server-") && file.getName().endsWith(".jar")) {
				cmds.add("-jar");
				cmds.add(file.getAbsolutePath());
				return;
			}
		}
		throw new IllegalStateException("Could not locate Server jar in " + System.getProperty("user.dir") + "!");
	}

	// see: https://stackoverflow.com/questions/13495449/how-to-split-a-command-line-like-string
	private static final Pattern splitter = Pattern.compile("[^\\s]*\"(\\\\+\"|[^\"])*?\"|[^\\s]*'(\\\\+'|[^'])*?'|(\\\\\\s|[^\\s])+", Pattern.MULTILINE);
	
	private static String locateYamlConfigFilePath() {
		return extractCmdLineArgument("-yamlConfigFile");
	}

	public static String extractCmdLineArgument(String argument) {
		return extractCmdLineArgument(System.getProperty("sun.java.command"), argument);
	}

	public static String extractCmdLineArgument(String cmdLine, String argument) {
		Matcher matcher = splitter.matcher(cmdLine);
		while(matcher.find()) {
			String key = matcher.group();
			if(argument.equals(key) && matcher.find())
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
	

