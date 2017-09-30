package prompto.security;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
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

public class StoredPasswordDigestLoginModule extends LoginModuleBase {

	IStore store;

	static final AttributeInfo LOGIN = new AttributeInfo("login", Family.TEXT,
			false, Arrays.asList("key"));
	static final AttributeInfo SALT = new AttributeInfo("salt", Family.TEXT,
			false, null);
	static final AttributeInfo METHOD = new AttributeInfo("method",
			Family.TEXT, false, null);
	static final AttributeInfo DIGEST = new AttributeInfo("digest",
			Family.TEXT, false, null);
	static final AttributeInfo QUESTIONS = new AttributeInfo("questions",
			Family.CATEGORY, true, null);
	static final AttributeInfo QUESTION = new AttributeInfo("question",
			Family.TEXT, false, null);
	static final AttributeInfo ANSWER = new AttributeInfo("answer",
			Family.TEXT, false, null);

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		super.initialize(subject, callbackHandler, sharedState, options);
		ILoginConfiguration config = (ILoginConfiguration) options
				.get("config");
		IStoreConfiguration storeConfig = config.getStoreConfiguration();
		try {
			store = IStoreFactory.newStoreFromConfig(storeConfig);
			store.createOrUpdateColumns(Arrays.asList(LOGIN, SALT, METHOD,
					DIGEST, QUESTIONS, QUESTION, ANSWER));
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public UserInfo getUserInfo(String username) throws Exception {
		return new UserInfo(username, new StoredPasswordDigestCredential(
				username), Collections.singletonList("*"));
	}

	static final Map<String, BiFunction<String, String, String>> methods = Collections
			.singletonMap("PBKDF2",
					StoredPasswordDigestLoginModule::digest_PBKDF2);

	public static String digest_PBKDF2(String credentials, String saltString) {
		try {
			final int iterations = 1000;
			final int keyLength = 512;
			final char[] password = credentials.toCharArray();
			final byte[] salt = Base64.getDecoder().decode(saltString);
			PBEKeySpec spec = new PBEKeySpec(password, salt, iterations,
					keyLength);
			SecretKeyFactory skf = SecretKeyFactory
					.getInstance("PBKDF2WithHmacSHA1");
			byte[] hash = skf.generateSecret(spec).getEncoded();
			return Base64.getEncoder().encodeToString(hash);
		} catch (Throwable t) {
			t.printStackTrace();
			return null;
		}
	}

	public static String newSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}

	@SuppressWarnings("serial")
	class StoredPasswordDigestCredential extends Credential {

		String username;

		public StoredPasswordDigestCredential(String username) {
			this.username = username;
		}

		@Override
		public boolean check(Object credentials) {
			if (isNullCredentials(credentials))
				return false;
			IQueryBuilder query = store.newQueryBuilder();
			query.verify(LOGIN, MatchOp.EQUALS, this.username);
			IStored authRecord = store.fetchOne(query.build());
			if (authRecord == null)
				return false; // unregistered user
			BiFunction<String, String, String> method = methods.get(authRecord.getData(METHOD.getName()));
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
			return Objects.equals(storedDigest, computedDigest);
		}

		private boolean isNullCredentials(Object credentials) {
			return (credentials == null || (credentials instanceof String && credentials
					.toString().isEmpty()));
		}

	}

}
