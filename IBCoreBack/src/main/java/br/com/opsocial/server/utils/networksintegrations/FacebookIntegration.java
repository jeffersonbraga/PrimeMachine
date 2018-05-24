package br.com.opsocial.server.utils.networksintegrations;

import com.face4j.facebook.Client;
import com.face4j.facebook.Facebook;
import com.face4j.facebook.OAuthAccessToken;
import com.face4j.facebook.factory.FacebookFactory;

/*import com.face4j.facebook.Client;
import com.face4j.facebook.Facebook;
import com.face4j.facebook.OAuthAccessToken;
import com.face4j.facebook.factory.FacebookFactory;*/

import facebook4j.auth.AccessToken;
import facebook4j.conf.ConfigurationBuilder;

public class FacebookIntegration {

//  OpSocial Production
//	public static String ACCESS_TOKEN = "130835310411674";
//	public static String ACCESS_TOKEN_SECRET = "7769faf17364a94bfe9e18855864099c";
//	public static final String APP_TOKEN = "130835310411674|rVmhrpU9YEmYSsL4etwKI_UiIPM";
	
//  OpSocial - Test (Application test is a new resource from Facebook, we don't need more an external app for test)
//	public static String ACCESS_TOKEN = "398557193639483";
//	public static String ACCESS_TOKEN_SECRET = "343ca6833501e1051c8a38a7703785b9";
//	public static final String APP_TOKEN = "398557193639483|bKasnBq0gmY8mOxZ-qJXpRGNxDc";
	
//  OpSocial - Test AWS (Application test is a new resource from Facebook, we don't need more an external app for test)
	public static String ACCESS_TOKEN = "561713787323822";
	public static String ACCESS_TOKEN_SECRET = "57d5e43ad35422e181b538f6abb9c23a";
	public static final String APP_TOKEN = "561713787323822|Coe6eHAMFbpLuJ1l5D9NHDeLhkI";
	
//  Woop Production
//	public static String ACCESS_TOKEN = "152164415378056";
//	public static String ACCESS_TOKEN_SECRET = "1a3bc9c006681fdaead36165adf821fa";
//	public static final String APP_TOKEN = "152164415378056|svENOEHq4fiyjxe-ROmVRgrGoGM";	
	
// LifeSoftware
//	public static String ACCESS_TOKEN = "371239530046484";
//	public static String ACCESS_TOKEN_SECRET = "1a3bc9c006681fdaead36165adf821fa";
//	public static final String APP_TOKEN = "152164415378056|svENOEHq4fiyjxe-ROmVRgrGoGM";	
	
	
	public static final String APP_PERMISSIONS = "read_page_mailboxes,publish_actions,read_insights," +
			"manage_pages,user_posts,user_photos,publish_pages,instagram_basic,instagram_manage_comments,instagram_manage_insights";

	public static final String GRAPH_API_V2_10 = "https://graph.facebook.com/v2.10/";
	
	public FacebookIntegration() {
		
	}
	
	public Facebook getFacebook(OAuthAccessToken accessToken) {
		
		Client client = new Client(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		FacebookFactory faceFactory = new FacebookFactory(client);
		
		Facebook facebook = faceFactory.getInstance(accessToken);
		
		return facebook;
	}
	
	public facebook4j.Facebook getFacebook(AccessToken accessToken) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setRestBaseURL(GRAPH_API_V2_10);
		
		facebook4j.FacebookFactory ff = new facebook4j.FacebookFactory(cb.build());
		
		facebook4j.Facebook facebook = ff.getInstance();
		facebook.setOAuthAppId(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		
		facebook.setOAuthAccessToken(new AccessToken(accessToken.getToken(), null));
		
		return facebook;
	}
	
	public FacebookFactory getSystemFacebook() {
		
		Client client = new Client(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		FacebookFactory faceFactory = new FacebookFactory(client);
		
		return faceFactory;
	}
	
	public facebook4j.Facebook getFacebookSystem() {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setRestBaseURL(GRAPH_API_V2_10);
		
		facebook4j.FacebookFactory ff = new facebook4j.FacebookFactory(cb.build());
		
		facebook4j.Facebook facebook = ff.getInstance();
		facebook.setOAuthAppId(ACCESS_TOKEN, ACCESS_TOKEN_SECRET);
		
		return facebook;
	}
	
}
