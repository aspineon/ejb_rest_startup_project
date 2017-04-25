package eu.limontacolori.privatearea.rest.client;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.HashMap;

import javax.net.ssl.SSLContext;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

import org.apache.http.conn.ssl.SSLContexts;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;

import eu.limontacolori.privatearea.exceptions.jaxrs.BadImportException;

public abstract class RestEasyImportClient {
	
	Logger logger = LogManager.getLogger(RestEasyImportClient.class);

	protected ResteasyClient client;
	protected String BASE_URL = "https://app.limontacolori.com:3001";
	protected static final String PIN_PARAM_KEY = "pin";
	protected static final String DEFAULT_PIN = "123456";
	protected Form form;
	
	public RestEasyImportClient() throws BadImportException {
		try {
			this.client = this.getBuilder().build();
		} catch (Exception exc) {
			logger.fatal("Unable to get Resteasy client", exc);
			throw new BadImportException("Unable to get Resteasy client");
		}
		this.form = new Form();
		this.form.param(PIN_PARAM_KEY,DEFAULT_PIN);
	}
	
	private ResteasyClientBuilder getBuilder() throws NoSuchAlgorithmException, CertificateException, IOException, KeyStoreException, KeyManagementException, UnrecoverableKeyException {
		KeyStore keyStore = KeyStore.getInstance("JKS");
		keyStore.load(null, null);
		
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		InputStream certstream = this.getClass().getClassLoader().getResourceAsStream("apilimontacoloricom.crt");
		Certificate certs =  cf.generateCertificate(certstream);

		keyStore.setCertificateEntry("limca_api", certs);
		SSLContext sslContext = SSLContexts.custom()
		        .loadTrustMaterial(keyStore)
		        .build();
		ResteasyClientBuilder clientBuilder = (ResteasyClientBuilder) ClientBuilder.newBuilder();
		clientBuilder.sslContext(sslContext).hostnameVerifier((s1, s2) -> true);
		return clientBuilder;
	}
	
	public abstract Response post();
	
	public RestEasyImportClient setFormParams(HashMap<String, String> params) {
		params.forEach((key,value)-> {
			this.form.param(key, value);
		});
		return this;
	}
	
}
