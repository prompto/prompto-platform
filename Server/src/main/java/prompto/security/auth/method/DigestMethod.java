package prompto.security.auth.method;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;
import java.util.function.BiFunction;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@FunctionalInterface
public interface DigestMethod extends BiFunction<String, String, String> {
	
	default String digest(String credentials, String saltString) {
		return apply(credentials, saltString);
	}
	
	static final Map<String, DigestMethod> digestMethods = Collections.singletonMap("PBKDF2", new PBKDF2());

	public static DigestMethod forName(String name) {
		return digestMethods.get(name);
	}
	
	class PBKDF2 implements DigestMethod {

		@Override
		public String apply(String credentials, String saltString) {
			try {
				final int iterations = 1000;
				final int keyLength = 512;
				final char[] password = credentials.toCharArray();
				final byte[] salt = Base64.getDecoder().decode(saltString);
				PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, keyLength);
				SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
				byte[] hash = skf.generateSecret(spec).getEncoded();
				return Base64.getEncoder().encodeToString(hash);
			} catch (Throwable t) {
				t.printStackTrace();
				return null;
			}
		}
	}
	

	public static String newSalt() throws NoSuchAlgorithmException {
		SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
		byte[] salt = new byte[16];
		sr.nextBytes(salt);
		return Base64.getEncoder().encodeToString(salt);
	}
}