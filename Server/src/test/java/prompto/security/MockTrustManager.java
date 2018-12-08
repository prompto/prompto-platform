package prompto.security;

import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MockTrustManager implements X509TrustManager {
	
	static SSLSocketFactory saved;

	public static void install() throws Exception {
		saved = HttpsURLConnection.getDefaultSSLSocketFactory();
		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new MockTrustManager() }, new java.security.SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());	}
	
	public static void restore() {
		if(saved!=null)
			HttpsURLConnection.setDefaultSSLSocketFactory(saved);
	}
	
	@Override
	public X509Certificate[] getAcceptedIssuers() {
		return new X509Certificate[0];
	}

	@Override
	public void checkClientTrusted(X509Certificate[] certs, String authType) {
	}

	@Override
	public void checkServerTrusted(X509Certificate[] certs, String authType) {
	}
} 


