package prompto.security;

import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class TrustAllCertificatesManager implements X509TrustManager {
	
	public static void install(HttpsURLConnection cnx) throws GeneralSecurityException {
		// Install the all-trusting trust manager
		SSLContext sc = SSLContext.getInstance("SSL");
		sc.init(null, new TrustManager[] { new TrustAllCertificatesManager() }, new java.security.SecureRandom());
		cnx.setSSLSocketFactory(sc.getSocketFactory());	
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


