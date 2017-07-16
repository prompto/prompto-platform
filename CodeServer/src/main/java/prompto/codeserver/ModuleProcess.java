package prompto.codeserver;

import java.io.IOException;
import java.io.InputStream;
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

import prompto.server.AppServer;
import prompto.server.PromptoServlet;
import prompto.store.IDataStore;
import prompto.store.IStored;
import prompto.value.IValue;

public class ModuleProcess {
	
	static Map<Object, ModuleProcess> modules = new HashMap<>();
	
	static {
		Runtime.getRuntime().addShutdownHook(new Thread(ModuleProcess::shutDownAll));
	}
	
	static void shutDownAll() {
		System.out.println("Shutting down module servers...");
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

	private static ModuleProcess createModuleProcess(Object dbId) throws IOException, InterruptedException {
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

	public void start() throws IOException, InterruptedException {
		this.port = findAvailablePort();
		String[] args = buildCommandLineArgs();
		ProcessBuilder builder = new ProcessBuilder(args)
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

	private String[] buildCommandLineArgs() {
		List<String> cmds = new ArrayList<String>();
		cmds.add("java");
		// cmds.add("-agentlib:jdwp=transport=dt_socket,server=y,suspend=y,address=8888");
		addClassPathArgs(cmds);
		cmds.add(AppServer.class.getName());
		addRelevantCmdLineArgs(cmds);
		addPromptoArgs(cmds);
		return cmds.toArray(new String[cmds.size()]);
	}

	private void addPromptoArgs(List<String> cmds) {
		cmds.add("-http_port");
		cmds.add(String.valueOf(port));
		cmds.add("-application");
		cmds.add(module.toString());
		cmds.add("-version");
		cmds.add(version.toString());
		String origin = PromptoServlet.REGISTERED_ORIGIN.get();
		if(origin!=null) {
			cmds.add("-origin");
			cmds.add(origin);
		}
	}

	private void addClassPathArgs(List<String> cmds) {
		cmds.add("-cp");
		cmds.add(System.getProperty("java.class.path").toString());
	}

	// see: https://stackoverflow.com/questions/13495449/how-to-split-a-command-line-like-string
	private static final Pattern splitter = Pattern.compile("[^\\s]*\"(\\\\+\"|[^\"])*?\"|[^\\s]*'(\\\\+'|[^'])*?'|(\\\\\\s|[^\\s])+", Pattern.MULTILINE);
	
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

	private static Set<String> relevantArgFullNames = new HashSet<>(Arrays.asList("-codeStoreFactory", "-dataStoreFactory", "-addOns"));
	private static List<String> relevantArgStartNames = Arrays.asList("-mongo-", "-solr-");
	
	private boolean isRelevantCmdLineArg(String key) {
		if(relevantArgFullNames.contains(key))
			return true;
		else
			return relevantArgStartNames.stream()
					.anyMatch(key::startsWith);
	}

}
	

