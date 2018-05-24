package br.com.opsocial.server.utils.networksintegrations;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.customsearch.Customsearch;
import com.google.api.services.customsearch.model.Result;
import com.google.api.services.plus.Plus;
import com.google.api.services.youtube.YouTube;

public class GoogleIntegration {
	
	// OpSocial Keys
	public static String APP_NAME = "OpSocial"; 
	public static String CLIENT_ID = "447065294546.apps.googleusercontent.com";
	public static String CLIENT_SECRET = "w6dJJCqqmm66ZxBet2UL3V6L";
	public static String API_KEY = "AIzaSyDZGuZRmV6ucYiC2Z7q9XnIxfQ5l0dZpbc";
	public static String ACCESS_TOKEN="ya29.1.AADtN_VPQjjs_K12g6u-rbMrKp5uXJ2J4QrvMQrQx25-NZy3Dm_Bz338G860ECJt-4qjYg";
	public static String REFRESH_TOKEN="1/weAohPPqJdn4UHF_wixe47ho5vAOU7O_7NNHEDjChDg";
	
	public static List<String> SCOPE = Arrays.asList(
		    "https://www.googleapis.com/auth/plus.me",
		    "https://www.googleapis.com/auth/plus.stream.write",
		    "https://www.googleapis.com/auth/plus.login",
		    "https://www.google.com/reader/api");
	
	private String newAccessToken;
	private boolean hasNewToken;
	
	public GoogleIntegration() {
		this.hasNewToken = false;
	}
	
	public Plus getGooglePlus() {
		
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		
		GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(jsonFactory)
        .setTransport(httpTransport)
        .setClientSecrets(GoogleIntegration.CLIENT_ID, GoogleIntegration.CLIENT_SECRET).build();
		
        Plus plus = new Plus.Builder(httpTransport, jsonFactory, credential).setApplicationName(APP_NAME).build();
		
        return plus;
	}

	public Plus getGooglePlus(String accessToken, String refreshToken) {
		
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		
		GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(jsonFactory)
        .setTransport(httpTransport)
        .setClientSecrets(GoogleIntegration.CLIENT_ID, GoogleIntegration.CLIENT_SECRET).build()
        .setAccessToken(accessToken).setRefreshToken(refreshToken);
		
		if(!accessToken.equals(credential.getAccessToken())) {
			this.newAccessToken = credential.getAccessToken();
			this.hasNewToken = true;
		}
		
        Plus plus = new Plus.Builder(httpTransport, jsonFactory, credential).setApplicationName(APP_NAME).build();
		
        return plus;
	}
	
	public Customsearch getCustomSearch() {
		
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		
		GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(jsonFactory)
        .setTransport(httpTransport)
        .setClientSecrets(GoogleIntegration.CLIENT_ID, GoogleIntegration.CLIENT_SECRET).build();
		
		Customsearch customsearch = new Customsearch.Builder(httpTransport, jsonFactory, credential).setApplicationName(APP_NAME).build();
		
		return customsearch;
	}
	
	public YouTube getYoutube() {
		
		HttpTransport httpTransport = new NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		
		GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(jsonFactory)
        .setTransport(httpTransport)
        .setClientSecrets(GoogleIntegration.CLIENT_ID, GoogleIntegration.CLIENT_SECRET).build();
		
		YouTube youtube = new YouTube.Builder(httpTransport, jsonFactory, credential).setApplicationName(APP_NAME).build();
		
		return youtube;
	}
	
	public YouTube getYoutube(String accessToken, String refreshToken) {
		
		HttpTransport httpTransport = new com.google.api.client.http.javanet.NetHttpTransport();
		JsonFactory jsonFactory = new JacksonFactory();
		
		GoogleCredential credential = new GoogleCredential.Builder()
        .setJsonFactory(jsonFactory)
        .setTransport(httpTransport)
        .setClientSecrets(GoogleIntegration.CLIENT_ID, GoogleIntegration.CLIENT_SECRET).build()
        .setAccessToken(accessToken).setRefreshToken(refreshToken);
		
		if(!accessToken.equals(credential.getAccessToken())) {
			this.newAccessToken = credential.getAccessToken();
			this.hasNewToken = true;
		}
		
		YouTube youtube = new YouTube.Builder(httpTransport, jsonFactory, credential).setApplicationName(APP_NAME).build();
		
		return youtube;
	}
	
	public List<Result> customSearch(String term) throws IOException, GeneralSecurityException {

		Customsearch custom = new GoogleIntegration().getCustomSearch();
		Customsearch.Cse.List list = custom.cse().list(term);
		list.setCx("002712205870816217284:nkfzmrenjni");
		list.setKey(GoogleIntegration.API_KEY);
		list.setStart(1L);
		list.setNum(10L);
		list.setSort("date");
		
		List<Result> listResult = new ArrayList<Result>();

		try {
			listResult.addAll(list.execute().getItems());
		} catch (Exception e) {
			e.printStackTrace();
		}     	

		return listResult;
	}

	/**
	 * @return the newAccessToken
	 */
	public String getNewAccessToken() {
		return newAccessToken;
	}

	/**
	 * @param newAccessToken the newAccessToken to set
	 */
	public void setNewAccessToken(String newAccessToken) {
		this.newAccessToken = newAccessToken;
	}

	/**
	 * @return the hasNewToken
	 */
	public boolean hasNewToken() {
		return hasNewToken;
	}

	/**
	 * @param hasNewToken the hasNewToken to set
	 */
	public void setHasNewToken(boolean hasNewToken) {
		this.hasNewToken = hasNewToken;
	}
}
