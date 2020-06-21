package prompto.security.auth;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.eclipse.jetty.jaas.spi.UserInfo;
import org.eclipse.jetty.util.security.Credential;

import prompto.config.IStoreConfiguration;
import prompto.config.auth.source.IStoredAuthenticationSourceConfiguration;
import prompto.security.auth.method.DigestMethod;
import prompto.store.AttributeInfo;
import prompto.store.Family;
import prompto.store.IQueryBuilder;
import prompto.store.IStorable;
import prompto.store.IStore;
import prompto.store.IStoreFactory;
import prompto.store.IStored;
import prompto.store.IQueryBuilder.MatchOp;
import prompto.store.IStorable.IDbIdFactory;
import prompto.utils.Logger;

/* need a shared cache to avoid costly calls to db */
public class StoredUserInfoCache {

	static Logger logger = new Logger();
	
	static StoredUserInfoCache instance = null;
	static Timer timer = new Timer();
	static long KEEP_ALIVE_DELAY = 30_000;
	
	public static StoredUserInfoCache initialize(IStoredAuthenticationSourceConfiguration config) {
		if(StoredUserInfoCache.instance==null) {
			synchronized(StoredUserInfoCache.class) {
				if(StoredUserInfoCache.instance==null) {
					StoredUserInfoCache.instance = new StoredUserInfoCache(config);// ensure the cache doesn't grow out of control
					TimerTask task = new TimerTask() { @Override public void run() { instance.evictOldEntriesFromCache(); }};
					timer.scheduleAtFixedRate(task, 30_000L, 30_000L);
				}
			}
		}
		return StoredUserInfoCache.instance;
	}
	
	static final AttributeInfo LOGIN = new AttributeInfo("login", Family.TEXT, false, Arrays.asList("key"));
	static final AttributeInfo SALT = new AttributeInfo("salt", Family.TEXT, false, null);
	static final AttributeInfo METHOD = new AttributeInfo("method", Family.TEXT, false, null);
	static final AttributeInfo DIGEST = new AttributeInfo("digest", Family.TEXT, false, null);
	static final AttributeInfo QUESTIONS = new AttributeInfo("questions", Family.CATEGORY, true, null);
	static final AttributeInfo QUESTION = new AttributeInfo("question", Family.TEXT, false, null);
	static final AttributeInfo ANSWER = new AttributeInfo("answer", Family.TEXT, false, null);


	Map<String, StoredPasswordDigestCredential> cache = new ConcurrentHashMap<>(new HashMap<>());
	IStore store;
	
	public StoredUserInfoCache(IStoredAuthenticationSourceConfiguration config) {
		IStoreConfiguration storeConfig = config.getStoreConfiguration();
		try {
			store = IStoreFactory.newStoreFromConfig(storeConfig);
			store.createOrUpdateAttributes(Arrays.asList(LOGIN, SALT, METHOD, DIGEST, QUESTIONS, QUESTION, ANSWER));
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}
	
	public StoredUserInfoCache(IStore store) {
		this.store = store;
		store.createOrUpdateAttributes(Arrays.asList(LOGIN, SALT, METHOD, DIGEST, QUESTIONS, QUESTION, ANSWER));
	}
	
	void evictOldEntriesFromCache() {
		final long now = System.currentTimeMillis();
		cache.values().stream()
			.filter(c->((now - c.lastChecked) > KEEP_ALIVE_DELAY))
			.collect(Collectors.toList()) // need an intermediate list to avoid modifying while traversing
			.forEach(c->cache.remove(c.userName));
		
	}


	public UserInfo getUserInfo(String username) {
		StoredPasswordDigestCredential credential = cache.computeIfAbsent(username, u->new StoredPasswordDigestCredential(u));
		return new UserInfo(username, credential, Collections.singletonList("*"));
	}

	@SuppressWarnings("serial")
	class StoredPasswordDigestCredential extends Credential {

		String userName;
		long lastChecked;
		int hashed;
		
		public StoredPasswordDigestCredential(String userName) {
			this.userName = userName;
			lastChecked = 0;
			hashed = 0;
		}

		@Override
		public boolean check(Object credentials) {
			if(wasCheckedRecently(credentials))
				return true;
			if(!checkNow(credentials))
				return false;
			lastChecked = System.currentTimeMillis();
			hashed = String.valueOf(credentials).hashCode();
			return true;
		}
		
		private boolean checkNow(Object credentials) {
			logger.info(()->"Authenticating user: " + userName);
			if (isNullCredentials(credentials))
				return false;
			IQueryBuilder query = store.newQueryBuilder();
			query.verify(LOGIN, MatchOp.EQUALS, this.userName);
			IStored authRecord = store.fetchOne(query.build());
			if (authRecord == null) {
				logger.info(()->"Unkown user: " + this.userName);
				return false; // unregistered user
			}
			DigestMethod method = DigestMethod.forName((String)authRecord.getData(METHOD.getName()));
			if (method == null)
				return false; // unknown digest method
			Object storedDigest = authRecord.getData(DIGEST.getName());
			if (storedDigest == null)
				return false; // no digest stored
			Object storedSalt = authRecord.getData(SALT.getName());
			if (storedSalt == null)
				return false; // no digest stored
			// compute value from credentials
			String computedDigest = method.apply(credentials.toString(), storedSalt.toString());
			boolean equal = Objects.equals(storedDigest, computedDigest);
			if(!equal)
				logger.info(()->"Invalid password for user: " + this.userName);
			return equal;
		}

		private boolean wasCheckedRecently(Object credentials) {
			long delay = System.currentTimeMillis() - lastChecked; 
			if(delay > KEEP_ALIVE_DELAY)
				return false;
			else
				return hashed == String.valueOf(credentials).hashCode();
		}

		private boolean isNullCredentials(Object credentials) {
			return (credentials == null || (credentials instanceof String && credentials.toString().isEmpty()));
		}

	}
	
	
	public boolean hasLogin(String login) {
		IQueryBuilder query = store.newQueryBuilder();
		query.verify(LOGIN, MatchOp.EQUALS, login);
		IStored authRecord = store.fetchOne(query.build());
		return authRecord != null;
	}

	public boolean checkLogin(String login, String password) throws NoSuchAlgorithmException {
		StoredPasswordDigestCredential credential = new StoredPasswordDigestCredential(login);
		return credential.check(password);
	}

	public void createLogin(String login, String password) throws NoSuchAlgorithmException {
		createLogin(store, login, password);
	}
	
	public void updateLogin(String login, String password) throws NoSuchAlgorithmException {
		IQueryBuilder query = store.newQueryBuilder();
		query.verify(LOGIN, MatchOp.EQUALS, login);
		IStored stored = store.fetchOne(query.build());
		if(stored==null)
			return;
		String salt = DigestMethod.newSalt();
		IStorable storable = store.newStorable(Arrays.asList("User"), new IDbIdFactory() {
			@Override public void accept(Object t) { }
			@Override public Object get() { return stored.getDbId(); }
			@Override public boolean isUpdate() { return true; }
		});
		storable.setData("login", login);
		storable.setData("salt", salt);
		storable.setData("method", "PBKDF2");
		String digest = DigestMethod.forName("PBKDF2").digest(password, salt);
		storable.setData("digest", digest);
		store.store(storable);
		cache.remove(login);
	}
	
	public void close() throws IOException {
		store.close();
	}
	
	public static void createLogin(IStore store, String login, String password) throws NoSuchAlgorithmException {
		String salt = DigestMethod.newSalt();
		IStorable storable = store.newStorable(Arrays.asList("User"), null);
		storable.setData("login", login);
		storable.setData("salt", salt);
		storable.setData("method", "PBKDF2");
		String digest = DigestMethod.forName("PBKDF2").digest(password, salt);
		storable.setData("digest", digest);
		storable.setDbId(store.newDbId());
		store.store(storable);
	}


}
