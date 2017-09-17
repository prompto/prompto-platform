package prompto.security;

import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;

import org.eclipse.jetty.jaas.spi.UserInfo;
import org.eclipse.jetty.util.security.Credential;

import prompto.config.ILoginConfiguration;
import prompto.config.IStoreConfiguration;
import prompto.store.AttributeInfo;
import prompto.store.Family;
import prompto.store.IQueryBuilder;
import prompto.store.IQueryBuilder.MatchOp;
import prompto.store.IStore;
import prompto.store.IStoreFactory;
import prompto.store.IStored;

public class StoredHashedPasswordLoginModule extends LoginModuleBase {

	IStore store;
	
	static final AttributeInfo LOGIN = new AttributeInfo("login", Family.TEXT, false, Arrays.asList("key"));
	static final AttributeInfo METHOD = new AttributeInfo("method", Family.TEXT, false, null);
	static final AttributeInfo DIGEST = new AttributeInfo("digest", Family.TEXT, false, null);
	static final AttributeInfo QUESTIONS = new AttributeInfo("questions", Family.CATEGORY, true, null);
	static final AttributeInfo QUESTION = new AttributeInfo("question", Family.TEXT, false, null);
	static final AttributeInfo ANSWER = new AttributeInfo("answer", Family.TEXT, false, null);
	
	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);
		ILoginConfiguration config = (ILoginConfiguration)options.get("config");
		IStoreConfiguration storeConfig = config.getStoreConfiguration();
		try {
			store = IStoreFactory.newStoreFromConfig(storeConfig);
			store.createOrUpdateColumns(Arrays.asList(LOGIN, DIGEST, QUESTIONS, QUESTION, ANSWER));
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}


	@Override
	public UserInfo getUserInfo(String username) throws Exception {
		return new UserInfo(username, new PasswordDigestIsStoredCredential(username), Collections.singletonList("*"));
	}
	
	
	static final Map<String, Function<Object, String>> methods = Collections.singletonMap("MD5", StoredHashedPasswordLoginModule::digest_MD5);
	
	public static String digest_MD5(Object credentials) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(credentials.toString().getBytes());
			return Base64.getEncoder().encodeToString(md.digest());
		} catch(Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("serial")
	class PasswordDigestIsStoredCredential extends Credential {

		String username;
		
		public PasswordDigestIsStoredCredential(String username) {
			this.username = username;
		}

		@Override
		public boolean check(Object credentials) {
			IQueryBuilder query = store.newQueryBuilder();
			query.verify(LOGIN, MatchOp.EQUALS, this.username);
			IStored stored = store.fetchOne(query.build());
			if(stored==null)
				return isNullCredentials(credentials);
			Function<Object, String> method = methods.get(stored.getData(METHOD.getName()));
			if(method==null)
				return false;
			else {
				Object value = stored.getData(DIGEST.getName());
				return checkEqual(value, credentials, method);
			}
		}
		
		boolean checkEqual(Object stored, Object proposed, Function<Object, String> method) {
			if(stored==null) 
				return isNullCredentials(proposed);
			else if(isNullCredentials(proposed))
				return false;
			String digest = method.apply(proposed);
			return stored.equals(digest);
		}

		private boolean isNullCredentials(Object credentials) {
			return (credentials==null || (credentials instanceof String && credentials.toString().isEmpty()));
		}

		
	}
	
	


	
}
