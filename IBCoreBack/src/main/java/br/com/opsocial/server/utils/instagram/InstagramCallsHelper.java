package br.com.opsocial.server.utils.instagram;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.ProxyRegion;
import br.com.opsocial.ejb.entity.instagram.InstagramDevice;
import br.com.opsocial.server.utils.UtilFunctions;

public class InstagramCallsHelper {
	
	private final CloseableHttpClient client;
    private final HttpClientContext context;
    
    private ProxyRegion proxyRegion;
    private InstagramDevice instagramDevice;
    private Profile profile;
    private String deviceUserAgent;
    
    private static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    
    public static final String SYNC_FEATURES_URL = "https://i.instagram.com/api/v1/qe/sync/"; 
	public static final String AUTO_COMPLETE_USER_LIST_URL = "https://i.instagram.com/api/v1/friendships/autocomplete_user_list/";
	public static final String TIMELINE_FEED_URL = "https://i.instagram.com/api/v1/feed/timeline/";
	public static final String RANKED_RECIPIENTS_URL = "https://i.instagram.com/api/v1/direct_v2/ranked_recipients/";
	public static final String RECENT_RECIPIENTS_URL = "https://i.instagram.com/api/v1/direct_share/recent_recipients/";
	public static final String MEGAPHONE_LOG_URL = "https://i.instagram.com/api/v1/megaphone/log/";
	public static final String DIRECT_V2_INBOX_URL = "https://i.instagram.com/api/v1/direct_v2/inbox/";
	public static final String NEWS_INBOX_URL = "https://i.instagram.com/api/v1/news/inbox/";
	public static final String FEED_REELS_TRAY_URL = "https://i.instagram.com/api/v1/feed/reels_tray/";
	public static final String DISCOVER_EXPLORE_URL = "https://i.instagram.com/api/v1/discover/explore/";
	
	public InstagramCallsHelper(String cookies, ProxyRegion proxyRegion, Integer port, InstagramDevice instagramDevice, Profile profile) {
		
		// Proxy GeoSurf
        HttpHost superProxy = new HttpHost(proxyRegion.getServerAddress(), port);
        context = HttpClientContext.create();
    	addCookies(cookies);
        client = HttpClients.custom()
            .setProxy(superProxy)
            .setRetryHandler(new HttpRequestRetryHandler() {
				
				@Override
				public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
					
					if (executionCount > 3) {
						return false;
					}
					
					if (exception instanceof NoHttpResponseException) {
						return true;
					}
					return false;
				}
			})
            .build();
		
		this.proxyRegion = proxyRegion;
		this.instagramDevice = instagramDevice;
		this.profile = profile;	
		this.deviceUserAgent = instagramDevice.getDeviceUserAgent() + " " + proxyRegion.getDeviceLanguage() + ")";
	}
	
	public void addCookies(String cookies) {
    	
    	if(cookies != null) {
        	
    		BasicClientCookie [] cookiesArray = GSON.fromJson(cookies, BasicClientCookie [].class);
        	
        	List<BasicClientCookie > cookiesList = Arrays.asList(cookiesArray);
        	
        	BasicCookieStore cookieStore = new BasicCookieStore();
        	
        	for(BasicClientCookie cookie : cookiesList) {
				cookieStore.addCookie(cookie);
			}
        	
        	context.setCookieStore(cookieStore);
    	}
    }
	
	// Simulates the calls that the app does when the user log in.
	public void requestsAfterAuthenticated() {
		
		syncFeatures(profile.getInstagramGUID(), profile.getNetworkId(), false);
		autoCompleteUserList();
		timelineFeed();
		getRankedRecipients();
		getRecentRecipients();
		megaphoneLog(profile.getInstagramGUID(), profile.getInstagramDeviceId());
		getV2Inbox();
		getRecentActivity();
		getReelsTrayFeed();
		explore();
		
		try {
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Request After Authenticated Finished...");
	}
	
	// SUPER IMPORTANT:
    //
    // STOP trying to ask us to remove this code section!
    //
    // EVERY time the user presses their device's home button to leave the
    // app and then comes back to the app, Instagram does ALL of these things
    // to refresh its internal app state. We MUST emulate that perfectly,
    // otherwise Instagram will silently detect you as a "fake" client
    // after a while!
    //
    // You can configure the login's $appRefreshInterval in the function
    // parameter above, but you should keep it VERY frequent (definitely
    // NEVER longer than 6 hours), so that Instagram sees you as a real
    // client that keeps quitting and opening their app like a REAL user!
    //
    // Otherwise they WILL detect you as a bot and silently BLOCK features
    // or even ban you.
    //
    // You have been warned.
	public void requestWhenAlreadyAuthenticated() {
		
		autoCompleteUserList();
		getReelsTrayFeed();
		getRankedRecipients();
		getRecentRecipients();
		megaphoneLog(profile.getInstagramGUID(), profile.getInstagramDeviceId());
		getV2Inbox();
		getRecentActivity();
		explore();
		
		try {
			close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Request When Already Authenticated Finished...");
	}
	
	private void timelineFeed() {
		
		try {
		
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", InstagramPostSender.X_IG_CAPABILITIES);
			
			String rankToken = profile.getNetworkId() + "_" + profile.getInstagramGUID();
			
			URIBuilder uriBuilder = new URIBuilder(new URI(TIMELINE_FEED_URL)).
					addParameter("rank_token", rankToken).
					addParameter("ranked_content", "true");
		    URI uri = uriBuilder.build();
			
			requestGET(uri, headers);
			
			System.out.println("Timeline Feed...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getRankedRecipients() {
		
		try {

			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", InstagramPostSender.X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(RANKED_RECIPIENTS_URL)).
					addParameter("show_threads", "true");
		    URI uri = uriBuilder.build();
			
			requestGET(uri, headers);
			
			System.out.println("Ranked Recipients...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getRecentRecipients() {
		
		try {
			
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", InstagramPostSender.X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(RECENT_RECIPIENTS_URL));
		    URI uri = uriBuilder.build();
			
			requestGET(uri, headers);
			
			System.out.println("Recent Recepients...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getV2Inbox() {
		
		try {
	
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", InstagramPostSender.X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(DIRECT_V2_INBOX_URL));
		    URI uri = uriBuilder.build();
			
		    requestGET(uri, headers);
			
		    System.out.println("V2 Inbox...");
		    
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getRecentActivity() {
		
		try {
	
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", InstagramPostSender.X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(NEWS_INBOX_URL)).
					addParameter("activity_module", "all");
		    URI uri = uriBuilder.build();
			
			requestGET(uri, headers);
			
			System.out.println("Recent Activity...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void getReelsTrayFeed() {
		
		try {

			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", InstagramPostSender.X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(FEED_REELS_TRAY_URL));
		    URI uri = uriBuilder.build();
			
			requestGET(uri, headers);
			
			System.out.println("Reels Tray Feed...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void explore() {
		
		try {
	
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", InstagramPostSender.X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(DISCOVER_EXPLORE_URL));
		    URI uri = uriBuilder.build();
			
			requestGET(uri, headers);
			
			System.out.println("Explore...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void megaphoneLog(String instagramGUID, String deviceId) {
	
		try {
			
			String CSRFToken = getCSRFToken();

			String data = "type=feed_aysf&action=seen&reason=&_uuid="+ instagramGUID+ "&device_id="+ deviceId + "&_csrftoken=" + CSRFToken + 
					"&uuid=" + MessageDigest.getInstance("MD5").digest(StandardCharsets.UTF_8.encode(String.valueOf(new Date().getTime() / 1000L)).array()).toString();
			
			requestPostDefault(MEGAPHONE_LOG_URL, data, deviceUserAgent);
			
			System.out.println("Megaphone Log...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String requestPostDefault(String jsonAddress, String postData, String agent) {
		
		String sonuc = "";
		
		try {
			
			Header[] headers = new Header[11];
			headers[0] = new BasicHeader("Host", "i.instagram.com");
			headers[1] = new BasicHeader("User-Agent", agent);
			headers[2] = new BasicHeader("Connection", "keep-alive");
			headers[3] = new BasicHeader("Accept", "*/*");
			headers[4] = new BasicHeader("Accept-Encoding", "gzip, deflate, sdch");
			headers[5] = new BasicHeader("X-IG-Capabilities", InstagramPostSender.X_IG_CAPABILITIES);
			headers[6] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[7] = new BasicHeader("X-IG-Connection-Speed", String.valueOf(ThreadLocalRandom.current().nextLong(1000, 3701)) + "kbps");
			headers[8] = new BasicHeader("X-FB-HTTP-Engine", InstagramPostSender.X_FB_HTTP_ENGINE);
			headers[9] = new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
			headers[10] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			
			requestPOST(jsonAddress, postData, headers);
			
		} catch (Exception e) {
			e.printStackTrace();
			return sonuc;
		}
		
		return sonuc;
	}
	
	private void syncFeatures(String instagramGUID, String networkId, Boolean preLogin) {

		try {

			Header[] headers = new Header[5];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Content-type", "application/x-www-form-urlencoded; charset=UTF-8");
			headers[2] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[3] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[4] = new BasicHeader("X-IG-Capabilities", InstagramPostSender.X_IG_CAPABILITIES);
			
			String data = "";
			if(preLogin) {
				data = "{\"id\":\"" + instagramGUID + "\",\"experiments\":\"" + InstagramPostSender.LOGIN_EXPERIMENTS + "\"}";
			} else {
				
				String CSRFToken = getCSRFToken();
				
				data = "{\"_uuid\":\"" + instagramGUID + "\",\"_uid\":\"" + networkId + "\",\"_csrftoken\":\"" + CSRFToken + "\",\"id\":\"" + networkId + "\",\"experiments\":\"" + InstagramPostSender.EXPERIMENTS + "\"}";
			}
			
			String sig = UtilFunctions.generateSignature(data, instagramDevice.getIgSigKey());
			data = "signed_body=" + sig + "." + URLEncoder.encode(data, "UTF-8") + "&ig_sig_key_version=4";
			
			requestPOST(SYNC_FEATURES_URL, data, headers);
			
			System.out.println("Sync Features...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void autoCompleteUserList() {
		
		try {
			
			Header[] headers = new Header[4];
			headers[0] = new BasicHeader("User-Agent", deviceUserAgent);
			headers[1] = new BasicHeader("Accept-Language", proxyRegion.getAcceptLanguageHeader());
			headers[2] = new BasicHeader("X-IG-Connection-Type", "WIFI");
			headers[3] = new BasicHeader("X-IG-Capabilities", InstagramPostSender.X_IG_CAPABILITIES);
			
			URIBuilder uriBuilder = new URIBuilder(new URI(AUTO_COMPLETE_USER_LIST_URL)).addParameter("version", "2");
		    URI uri = uriBuilder.build();
			
			requestGET(uri, headers);
			
			System.out.println("Auto Complete User List...");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void requestPOST(String url, String postData, Header[] headers) throws IOException {
    	
    	HttpPost request = new HttpPost(url);
		request.setEntity(new StringEntity(postData, Charset.forName("UTF-8")));
		request.setHeaders(headers);
    	
		CloseableHttpResponse response = client.execute(request, context);
		
		EntityUtils.consume(response.getEntity());
		response.close();
    }
	
	public void requestGET(URI uri, Header[] headers) throws IOException {
    	
    	HttpGet request = new HttpGet(uri);
        request.setHeaders(headers);
        
        CloseableHttpResponse response = client.execute(request, context);
        
        EntityUtils.consume(response.getEntity());
		response.close();
    }
	
	public String getCSRFToken() {
    	
    	BasicClientCookie basicClientCookie = new BasicClientCookie("csrftoken", "");
    	
    	for(Cookie cookie : context.getCookieStore().getCookies()) {
    		if(cookie.getName().equals("csrftoken")) {
    			basicClientCookie.setValue(cookie.getValue());
    			break;
    		}
		}
    	
    	return basicClientCookie.getValue();
    }
	
	public void close() throws IOException { 
		client.close();
	}

}
