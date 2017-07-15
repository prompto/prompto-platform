package prompto.store.mongo;

import prompto.store.IStoreFactory;

public class MongoStoreFactory implements IStoreFactory {

	@Override
	public MongoStore newStore(String[] args, Type type) throws Exception {
		String user = null;
		String password = null;
		String server = null;
		int port = 27017;
		
		final String argKey = "-mongo-" + type.name().toLowerCase() + "-";
		for(int i=0;i<args.length;i++) {
			String arg = args[i]; 
			if(!arg.toLowerCase().startsWith(argKey))
				continue;
			arg = arg.substring(argKey.length());
			if(arg.equalsIgnoreCase("host"))
				server = args[++i];
			else if(arg.equalsIgnoreCase("port"))
				port = Integer.decode(args[++i]);
			else if(arg.equalsIgnoreCase("user"))
				user = args[++i];
			else if(arg.equalsIgnoreCase("password"))
				password = args[++i];
		}
		MongoStore store = new MongoStore(server, port, type.name(), user, password);
		return store;
	}
}
