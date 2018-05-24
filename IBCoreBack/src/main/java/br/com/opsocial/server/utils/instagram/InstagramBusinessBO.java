package br.com.opsocial.server.utils.instagram;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.face4j.facebook.exception.FacebookException;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.instagram.InstagramPost;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.Reflection;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.instagram.entities.Insight;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.RawAPIResponse;
import facebook4j.auth.AccessToken;
import facebook4j.internal.org.json.JSONArray;
import facebook4j.internal.org.json.JSONObject;

public class InstagramBusinessBO {
	
	public InstagramBusinessBO() {
		
	}

	public final String REPORT_AGE_GENDER 		= "audience_gender_age";

	public final String REPORT_LOCALE_COUNTRY 	= "audience_country";

	public final String REPORT_LOCALE_CITIES 	= "audience_city";

	public final String REPORT_LOCALE_LANGUAGE 	= "audience_locale";

	public String INSTAGRAM_ROOT_URL = "https://graph.facebook.com/v2.10/";

	/* SIMPLE MEDIA URL ENDPOINT */
	public String URL_GET_BUSINESS_ACCOUNT = "{instagram_business_account_id}?fields=instagram_business_account";

	public String URL_GET_MEDIA = "{instagram_business_account_id}/media?fields=comments_count,id,caption,ig_id,is_comment_enabled,like_count,media_type,media_url,permalink,shortcode,thumbnail_url,timestamp";

	public String URL_GETBUSINESS_ACCOUNT = "/{instagram_media_id}/comments?message=This%20is%20awesome!";

	public String URL_GET_MEDIA_IMPRESSIONS = "/{instagram_media_id}/insights?metric=impressions";

	public String URL_GET_MEDIA_REACH = "/{instagram_media_id}/insights?metric=reach";

	public String URL_GET_MEDIA_ENGAGEMENT = "{instagram_media_id}/insights?metric=engagement";

	public String URL_GET_MEDIA_SAVES = "{instagram_media_id}/insights?metric=saved";

	public String URL_GET_MEDIA_VIDEO_VIEWS = "{instagram_media_id}/insights?metric=video_views";

	/**
	 * 
	 * 
	 * ERROR COLLECTION 
	 * 
	 * "error": {
	    "message": "(#100) Insights are only available for media posted after the owner account converted to business account",
	    "type": "OAuthException",
	    "code": 100,
	    "fbtrace_id": "AKoZ+LmSg7B"
	  }
	 * 
	 * 
	 * 
	 */

	/* CAROUSEL MEDIA URL ENDPOINT */
	public String URL_GET_CAROUSEL_ENGAGEMENT 	= "/{instagram_media_id}/insights?metric=carousel_album_engagement";

	public String URL_GET_CAROUSEL_IMPRESSIONS 	= "/{instagram_media_id}/insights?metric=carousel_album_impressions";

	public String URL_GET_CAROUSEL_REACH 		= "/{instagram_media_id}/insights?metric=carousel_album_reach";

	public String URL_GET_CAROUSEL_SAVES 		= "{instagram_media_id}/insights?metric=carousel_album_saved";

	public String URL_GET_CAROUSEL_VIDEO_VIEWS 	= "{instagram_media_id}/insights?metric=carousel_album_video_views";

	/* STORIES MEDIA URL ENDPOINT */
	
	public String URL_GET_STORIES_EXITS 		= "/{instagram_media_id}/insights?metric=exits";

	public String URL_GET_STORIES_REPLIES 		= "/{instagram_media_id}/insights?metric=replies";

	public String URL_GET_STORIES_TAP_FORWARD 	= "/{instagram_media_id}/insights?metric=taps_forward";

	public String URL_GET_STORIES_TAP_BACK 		= "{instagram_media_id}/insights?metric=taps_back";

	/* PROFILE BASIC INFO URL ENDPOINT */
	
	public String URL_GET_PROFILE_BASIC_INFO	= "/{instagram_business_account_id}/?fields=biography";

	public String URL_GET_FOLLOWERS_COUNT		= "/{instagram_business_account_id}/?fields=followers_count";

	public String URL_GET_FOLLOWS_COUNT			= "/{instagram_business_account_id}/?fields=follows_count";

	public String URL_GET_MEDIA_COUNT			= "/{instagram_business_account_id}/?fields=media_count";

	/* INSIGHTS URL END POINT */
	/**
	 * 
	 * 
	 * Métricas:

		impressions (aceita dia, semana e dias_28)
		reach (aceita dia, semana e dias_28)
		follower_count (aceita apenas dia como período de tempo)
		email_contacts (aceita apenas dia como período de tempo)
		phone_call_clicks (aceita apenas dia como período de tempo)
		text_message_clicks (aceita apenas dia como período de tempo)
		get_directions_clicks (aceita apenas dia como período de tempo)
		website_clicks (aceita apenas dia como período de tempo)
		profile_views (aceita apenas dia como período de tempo)
		audience_gender_age (aceita apenas vitalício como período de tempo)
		audience_locale (aceita apenas vitalício como período de tempo)
		audience_country (aceita apenas vitalício como período de tempo)
		audience_city (aceita apenas vitalício como período de tempo)
		online_followers (aceita apenas vitalício como período de tempo)
		
		Períodos de tempo:
		
		dia
		semana
		dias_28
		vitalício (as métricas relacionadas ao público usarão apenas os dados do dia anterior)
	 *  
	 */

	public String URL_GET_IMPRESSIONS_PERIOD	= "/{instagram_business_account_id}/insights?metric=impressions&period=days_28&since=1501545600&until=1502493720";

	public String URL_GET_AGE_GENDER 			= "/{instagram_business_account_id}/insights/?metric=audience_gender_age&period=lifetime";

	public String URL_GET_AUDIENCE_LOCALE 		= "/{instagram_business_account_id}/insights/?metric=audience_locale&period=lifetime";

	public String URL_GET_AUDIENCE_COUNTRY 		= "/{instagram_business_account_id}/insights/?metric=audience_country&period=lifetime";

	public String URL_GET_AUDIENCE_CITY 		= "/{instagram_business_account_id}/insights/?metric=audience_city&period=lifetime";

	public String URL_GET_AUDIENCE_ONLINE_FOLLOWERS	= "/{instagram_business_account_id}/insights?period=lifetime&metric=online_followers";

	public String URL_GET_ALL_STORIES_ID		= "/{instagram_business_account_id}/stories";
	
	public String URL_GET_PROFILE_INSIGHTS = "/{instagram_business_account_id}/insights";
	
	private static final String PERIOD_DAY = "day";
	private static final String PERIOD_LIFETIME = "lifetime";

	public List<Object> listaColetada = new ArrayList<>();

	public List<Object> listaPersistir = new ArrayList<>();
	
	private Profile profile;
	private ZonedDateTime reportDay;

	public InstagramBusinessBO(Profile profile, ZonedDateTime reportDay) {
		this.profile = profile;
		this.reportDay = reportDay;
	}
	
	public void createConnection() {

        // Facebook configuration
		/*
        String facebookClientId = FacebookIntegration.ACCESS_TOKEN;
        String facebookClientSecret = FacebookIntegration.ACCESS_TOKEN_SECRET;
        if (facebookClientId != null && facebookClientSecret != null) {
        	FacebookConnectionFactory faceFactory = new FacebookConnectionFactory(facebookClientId,facebookClientSecret);
        	faceFactory.createConnection(faceFactory.getOAuthOperations().authenticateClient());
        	//----- System.out.println("FacebookConnectionFactory");
        }*/
		
		
		Facebook facebook = new FacebookFactory().getInstance();
		facebook.setOAuthAppId(FacebookIntegration.ACCESS_TOKEN, FacebookIntegration.ACCESS_TOKEN_SECRET);
		
		String commaSeparetedPermissions = "read_page_mailboxes,publish_actions,read_insights," +
		"manage_pages,user_posts,user_photos,publish_pages,instagram_basic,instagram_manage_comments,instagram_manage_insights,business_management,pages_show_list,read_audience_network_insights";

		
		
		facebook.setOAuthPermissions(commaSeparetedPermissions);
		facebook.setOAuthAccessToken(new AccessToken(FacebookIntegration.ACCESS_TOKEN, null));
		
		//facebook.getOAuthAppAccessToken();
			
			
			/*
			

			
			// Single FQL
			String query = "SELECT uid2 FROM friend WHERE uid1=me()";
			JSONArray jsonArray = facebook.executeFQL(query);
			for (int i = 0; i < jsonArray.length(); i++) {
			    JSONObject jsonObject = jsonArray.getJSONObject(i);
			    //----- System.out.println(jsonObject.get("uid2"));
			}

			// Multiple FQL
			Map<String, String> queries = new HashMap<String, String>();
			queries.put("all friends", "SELECT uid2 FROM friend WHERE uid1=me()");
			queries.put("my name", "SELECT name FROM user WHERE uid=me()");
			Map<String, JSONArray> result = facebook.executeMultiFQL(queries);
			JSONArray allFriendsJSONArray = result.get("all friends");
			for (int i = 0; i < allFriendsJSONArray.length(); i++) {
			    JSONObject jsonObject = allFriendsJSONArray.getJSONObject(i);
			    //----- System.out.println(jsonObject.get("uid2"));
			}
			JSONArray myNameJSONArray = result.get("my name");
			//----- System.out.println(myNameJSONArray.getJSONObject(0).get("name"));

			
			*/
			
		
		
		
		
		
		
		
		
		

	}

	public void getUserBusinessAccountInfo() {

	}

	public void makeRequestsTest(String instagram_business_id, String media_id, Profile profile) {

		try {
			this.executeGetTestData(instagram_business_id, profile);
			this.executeGetTestDataMedia(media_id, profile);

			for (Object o : listaColetada) {
				//----- System.out.println(o.toString());
			}

			for (Object o : listaPersistir) {
				//----- System.out.println(o.toString());
			}
		} catch (FacebookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void recoverBusinessID(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_BUSINESS_ACCOUNT, id));

			JsonParser parser = new JsonParser();			
			JsonObject jsonResponseData = parser.parse(res.asString()).getAsJsonObject();

			MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");

			JsonObject obj = jsonResponseData.getAsJsonObject("instagram_business_account");
			
			listaColetada.add(obj);
			
			profile.setInstagramBusinessId(obj.getAsJsonPrimitive("id").getAsString());
			remote.save(profile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<InstagramReport> retrieveProfileOnlineFollowersInsights(Profile profile, ZonedDateTime day) {
		
		List<InstagramReport> instagramReports = new ArrayList<InstagramReport>();
		
		Long dateSince = day.toEpochSecond();
		Long dateUntil = dateSince;
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			Map<String, String> queryParameters = new HashMap<String, String>();
			queryParameters.put("metric", "online_followers");
			queryParameters.put("period", PERIOD_LIFETIME);
			queryParameters.put("since", dateSince.toString());
			queryParameters.put("until", dateUntil.toString());
			
			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_PROFILE_INSIGHTS, profile.getInstagramBusinessId()), queryParameters);
			
			JSONArray jsonArray = res.asJSONObject().getJSONArray("data");
			
			for(int i = 0; i < jsonArray.length(); i++) {	
				
				JsonParser parser = new JsonParser();
				JsonObject jsonResponseData = parser.parse(jsonArray.getJSONObject(i).toString()).getAsJsonObject();
				JsonArray dataValues = jsonResponseData.getAsJsonArray("values");
				
				List<Object> resultFormated = (List<Object>) this.getDataArrayNode(dataValues);
				
				if(!resultFormated.isEmpty()) {
					
					List<InstagramReport> instagramReportsExtacted = new ArrayList<InstagramReport>();
					this.processDataToInstagramReport(resultFormated, instagramReportsExtacted, jsonArray.getJSONObject(i).getString("name"));
					
					instagramReports.addAll(instagramReportsExtacted);
				}
			}
			
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return instagramReports;
	}
	
	public List<InstagramReport> retrieveProfileInsightsForLifetime(Profile profile) {
		
		List<InstagramReport> instagramReports = new ArrayList<InstagramReport>();
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			Map<String, String> queryParameters = new HashMap<String, String>();
			queryParameters.put("metric", "audience_gender_age,audience_locale,audience_country,audience_city");
			queryParameters.put("period", PERIOD_LIFETIME);
			
			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_PROFILE_INSIGHTS, profile.getInstagramBusinessId()), queryParameters);
			
			JSONArray jsonArray = res.asJSONObject().getJSONArray("data");
			
			for(int i = 0; i < jsonArray.length(); i++) {
				
				JsonParser parser = new JsonParser();
				JsonObject jsonResponseData = parser.parse(jsonArray.getJSONObject(i).toString()).getAsJsonObject();
				JsonArray dataValues = jsonResponseData.getAsJsonArray("values");
				
				Object resultFormated = this.getDataArrayNode(dataValues);
				List<InstagramReport> instagramReportsExtacted = new ArrayList<InstagramReport>();
				this.processDataToInstagramReport((List<Object>) resultFormated, instagramReportsExtacted, jsonArray.getJSONObject(i).getString("name"));
				
				instagramReports.addAll(instagramReportsExtacted);
			}
			
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return instagramReports;
	}
	
	public List<InstagramReport> retrieveProfileInsightsForDay(Profile profile, ZonedDateTime day) {
		
		List<InstagramReport> instagramReports = new ArrayList<InstagramReport>();
		
		Long dateSince = day.toEpochSecond();
		Long dateUntil = dateSince;
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
			
			Map<String, String> queryParameters = new HashMap<String, String>();
			queryParameters.put("metric", "follower_count,reach,impressions,email_contacts,phone_call_clicks,"
								+ "text_message_clicks,get_directions_clicks,website_clicks,profile_views");
			queryParameters.put("period", PERIOD_DAY);
			queryParameters.put("since", dateSince.toString());
			queryParameters.put("until", dateUntil.toString());
			
			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_PROFILE_INSIGHTS, profile.getInstagramBusinessId()), queryParameters);
			
			JsonParser parser = new JsonParser();			
			JsonObject jsonResponseData = parser.parse(res.asString()).getAsJsonObject();
			
			JsonArray dataValues = jsonResponseData.getAsJsonArray("data");
			
			for (JsonElement jsonElement : dataValues) {
				
				Insight insight = gson.fromJson(jsonElement, Insight.class);
				
				InstagramReport instagramReport = new InstagramReport();
				instagramReport.setDate(day.toEpochSecond());
				instagramReport.setIdInstagramUser(profile.getNetworkId());
				instagramReport.setProperty(insight.getName());
				instagramReport.setValue(insight.getValues().get(0).getValue());
				
				instagramReports.add(instagramReport);
			}
			
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return instagramReports;
	}

	public void getMediaData(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_MEDIA, id));

			JsonParser parser = new JsonParser();			
			JsonObject jsonResponseData = parser.parse(res.asString()).getAsJsonObject();

			this.getProcessDataResponse(jsonResponseData);
			
			JsonArray dataValues = jsonResponseData.getAsJsonArray("data");
			
			listaColetada.add(dataValues);
			
			for (Object item : dataValues) {
				
				InstagramPost meuItem = new InstagramPost();
				Reflection.populateObjectByAnnotation(meuItem, (JsonObject) item);
				
				//----- System.out.println(meuItem);
			}
			
			//profile.setInstagramBusinessId(dataValues.getAsJsonPrimitive("id").getAsString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getMediaImpressions(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_MEDIA_IMPRESSIONS, id));

			listaColetada.add(this.getDataResponse(res));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getMediaReach(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_MEDIA_REACH, id));
			//arryresponse [data[value]]
			//this.getDataResponse(res);
			Object resultFormated = this.getDataArrayNode(this.getDataJsonArray(res, "data"));
			
			listaColetada.add(resultFormated);
			//----- System.out.println("reach");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getMediaEngagement(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_MEDIA_ENGAGEMENT, id));
			//arryresponse [data[values]]
			//this.getDataResponse(res);

			Object resultFormated = ((JsonObject) this.getDataJsonArray(res, "data").get(0)).getAsJsonArray("values").get(0).getAsJsonObject().get("value");

			listaColetada.add(resultFormated);
			//----- System.out.println("engagement");//{"value":21}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getMediaSaves(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_MEDIA_SAVES, id));
			//arryresponse [data[values]]
			//this.getDataResponse(res);
			//Object resultFormated = this.getDataJsonArray(res, "data").getAsJsonObject().getAsJsonArray("values").getAsJsonObject().get("value");
			Object resultFormated = ((JsonObject) this.getDataJsonArray(res, "data").get(0)).getAsJsonArray("values").get(0).getAsJsonObject().get("value");
			listaColetada.add(resultFormated);
			//----- System.out.println("saved");//{"value":0}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getMediaVideoViews(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_MEDIA_VIDEO_VIEWS, id));
			listaColetada.add(this.getDataResponse(res));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getCarouselEngagement(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_CAROUSEL_ENGAGEMENT, id));

			this.getDataResponse(res);
			listaColetada.add(this.getDataResponse(res));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getCarouselImpressions(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_CAROUSEL_IMPRESSIONS, id));

			this.getDataResponse(res);
			listaColetada.add(this.getDataResponse(res));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getCarouselReach(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_CAROUSEL_REACH, id));

			this.getDataResponse(res);
			listaColetada.add(this.getDataResponse(res));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getCarouselSaves(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_CAROUSEL_SAVES, id));
			//arryresponse [data[values]]
			//this.getDataResponse(res);
			Object resultFormated = this.getDataArrayNode(this.getDataJsonArray(res, "data"));
			listaColetada.add(resultFormated);
			//----- System.out.println("saved");//{"value":0}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getCarouselVideoViews(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_CAROUSEL_VIDEO_VIEWS, id));

			this.getDataResponse(res);
			listaColetada.add(this.getDataResponse(res));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getStoriesExits(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_STORIES_EXITS, id));

			this.getDataResponse(res);
			listaColetada.add(this.getDataResponse(res));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getStoriesReplies(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_STORIES_REPLIES, id));

			this.getDataResponse(res);
			listaColetada.add(this.getDataResponse(res));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getStoriesTapFoward(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_STORIES_TAP_FORWARD, id));

			this.getDataResponse(res);
			listaColetada.add(this.getDataResponse(res));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getStoriesTapBack(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_STORIES_TAP_BACK, id));

			this.getDataResponse(res);
			listaColetada.add(this.getDataResponse(res));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getProfileBasicInfo(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_PROFILE_BASIC_INFO, id));
			//objectresponse 
			String descBiography = this.getDataResponse(res).get("biography").getAsString();	//----- System.out.println(descBiography);

			listaColetada.add(descBiography);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
	}

	public void getFollowersCount(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_FOLLOWERS_COUNT, id));
			//objectresponse
			Integer followers_count = this.getDataResponse(res).get("followers_count").getAsInt();	//----- System.out.println(followers_count);
			listaColetada.add(followers_count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getFollowsCount(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_FOLLOWS_COUNT, id));
			//objectresponse
			Integer follows_count = this.getDataResponse(res).get("follows_count").getAsInt();	//----- System.out.println(follows_count);
			listaColetada.add(follows_count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getMediaCount(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_MEDIA_COUNT, id));
			//objectresponse
			Integer media_count = this.getDataResponse(res).get("media_count").getAsInt();	//----- System.out.println(media_count);
			listaColetada.add(media_count);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getImpressionsPeriod(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_IMPRESSIONS_PERIOD, id));
			//arryresponse [data[values]]
			//this.getDataResponse(res);
			Object resultFormated = this.getDataArrayNode(this.getDataJsonArray(res, "data"));//LOOP, paging
			listaColetada.add(resultFormated);
			//----- System.out.println("impressions");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getAgeByGender(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_AGE_GENDER, id));
			//arryresponse [data[values]]
			//this.getDataResponse(res);
			Object resultFormated = this.getDataArrayNode(this.getDataJsonArray(res, "data"));

			List<InstagramReport> listAgeGender = new ArrayList<>();
			this.processDataToInstagramReport( (List<Object>) resultFormated, listAgeGender, "audience_gender_age" );

			listaColetada.add(resultFormated);
			//----- System.out.println("audience_gender_age");//{"value":{"F.13-17":3,"F.18-24":129,"F.25-34":360,"F.35-44":111,"F.45-54":16,"F.55-64":6,"F.65+":1,"M.13-17":6,"M.18-24":108,"M.25-34":469,"M.35-44":142,"M.45-54":19,"M.55-64":3,"M.65+":2,"U.25-34":5,"U.35-44":2,"U.65+":1},"end_time":"2017-10-23T07:00:00+0000"}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getLabelValue(JsonObject jsonObject) {
		
		return jsonObject.get("label").getAsString();
	}

	public void getAudienceByLocale(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_AUDIENCE_LOCALE, id));
			//arryresponse [data[values]]
			//this.getDataResponse(res);
			Object resultFormated = this.getDataArrayNode(this.getDataJsonArray(res, "data"));

			List<InstagramReport> listAudience_locale = new ArrayList<>();
			this.processDataToInstagramReport( (List<Object>) resultFormated, listAudience_locale, "audience_locale" );

			listaColetada.add(resultFormated);
			//----- System.out.println("audience_locale");//{"value":{"ar_AR":1,"en_GB":8,"en_US":38,"es_ES":1,"es_LA":6,"fr_FR":2,"pt_BR":1317,"pt_PT":7,"ro_RO":1,"ru_RU":1,"th_TH":1},"end_time":"2017-10-23T07:00:00+0000"}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getAudienceByCountry(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_AUDIENCE_COUNTRY, id));
			//arryresponse [data[values]]
			//this.getDataResponse(res);
			Object resultFormated = this.getDataArrayNode(this.getDataJsonArray(res, "data"));
			//TODO siglas para nomes
			List<InstagramReport> listAudience_country = new ArrayList<>();
			this.processDataToInstagramReport( (List<Object>) resultFormated, listAudience_country, "audience_country" );
			listaColetada.add(resultFormated);
			//----- System.out.println("audience_country");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getAudienceByCity(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_AUDIENCE_CITY, id));
			//arryresponse [data[values]]
			//this.getDataResponse(res);
			Object resultFormated = this.getDataArrayNode(this.getDataJsonArray(res, "data"));

			List<InstagramReport> listAudience_city = new ArrayList<>();
			this.processDataToInstagramReport( (List<Object>) resultFormated, listAudience_city, "audience_city" );
			listaColetada.add(resultFormated);
			//----- System.out.println("audience_city");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getAudienceOnlineFollowers(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_AUDIENCE_ONLINE_FOLLOWERS, id));
			//arryresponse [data[values]]
			//this.getDataResponse(res);
			Object resultFormated = this.getDataArrayNode(this.getDataJsonArray(res, "data"));

			List<InstagramReport> listAgeGender = new ArrayList<>();
			this.processDataToInstagramReport( (List<Object>) resultFormated, listAgeGender, "follower_activity_Horas" );

			listaColetada.add(resultFormated);
			//----- System.out.println("online_followers");//{"value":0,"end_time":"2017-10-22T07:00:00+0000"}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public void getAllStories_ID(String id, Profile profile) throws FacebookException {

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			RawAPIResponse res = facebook.callGetAPI(this.replacePatternParameter(this.URL_GET_ALL_STORIES_ID, id));
			//arryresponse [data]
			//this.getDataResponse(res);
			Object resultFormated = this.getDataArrayNode(this.getDataJsonArray(res, "data"));
			listaColetada.add(resultFormated);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String replacePatternParameter(String strReplace, String value) {

		if (strReplace.indexOf("{instagram_business_account_id}") > -1) {

			strReplace = strReplace.replaceAll("\\{instagram_business_account_id\\}", value);
		} else if (strReplace.indexOf("{instagram_media_id}") > -1) {

			strReplace = strReplace.replaceAll("\\{instagram_media_id\\}", value);	
		}

		return strReplace;
	}

	public void executeGetTestData(String id, Profile profile) throws FacebookException {

		this.getMediaData(id, profile);

		this.getProfileBasicInfo(id, profile);

		this.getFollowersCount(id, profile);

		this.getFollowsCount(id, profile);

		this.getMediaCount(id, profile);

		this.getImpressionsPeriod(id, profile);

		this.getAgeByGender(id, profile);

		this.getAudienceByLocale(id, profile);

		this.getAudienceByCountry(id, profile);

		this.getAudienceByCity(id, profile);

		this.getAudienceOnlineFollowers(id, profile);

		this.getAllStories_ID(id, profile);
	}

	private void executeGetTestDataMedia(String id, Profile profile) throws FacebookException {

		//this.getMediaImpressions(id, profile);

		this.getMediaReach(id, profile);

		this.getMediaEngagement(id, profile);

		this.getMediaSaves(id, profile);

		this.getMediaVideoViews(id, profile);

		this.getCarouselEngagement(id, profile);

		this.getCarouselImpressions(id, profile);

		this.getCarouselReach(id, profile);

		this.getCarouselSaves(id, profile);

		this.getCarouselVideoViews(id, profile);

		this.getStoriesExits(id, profile);

		this.getStoriesReplies(id, profile);

		this.getStoriesTapFoward(id, profile);

		this.getStoriesTapBack(id, profile);
	}

	/**
	 *	!!!!!! PROCESSAMENTO DOS DADOS RECEBIDOS PELA API !!!!!!
	 * 
	 */	
	private void getProcessDataResponse(JsonObject o) {
		//----- System.out.println(o.toString());
	}

	private JsonObject getDataResponse(RawAPIResponse res) throws Exception {

		JsonParser parser = new JsonParser();
		JsonObject jsonResponseData = parser.parse(res.asString()).getAsJsonObject();
		return jsonResponseData;
	}

	private JsonObject getDataJsonObject(RawAPIResponse res) throws Exception {

		JsonParser parser = new JsonParser();
		JsonObject jsonResponseData = parser.parse(res.asString()).getAsJsonObject();
		return jsonResponseData;
	}

	private JsonArray getDataJsonArray(RawAPIResponse res, String dataField) throws Exception {

			JsonParser parser = new JsonParser();
			JsonObject jsonResponseData = parser.parse(res.asString()).getAsJsonObject();
			JsonArray dataValues = jsonResponseData.getAsJsonArray(dataField);//data,paging,'values'
			return dataValues;
	}

	private Object getDataArrayNode(JsonArray dataSource) throws Exception {
		return this.getDataArrayNode(dataSource, null);
	}

	private Object getDataArrayNode(JsonArray dataSource, Object itemBase) throws Exception {

		List<Object> returnData = new ArrayList<>();

		for (Object item : dataSource) {

			if (item instanceof JsonObject) {

				if ( ((JsonObject) item).isJsonArray() ) {
					returnData.addAll((List<Object>) this.getDataArrayNode(dataSource));
				} else {

					if ( ((JsonObject) item).has("values") ) {
						returnData.addAll((List<Object>) this.getDataArrayNode(((JsonObject) item).getAsJsonArray("values")));
					} else if ( ((JsonObject) item).has("value") ) {

						if ( (item instanceof JsonPrimitive) || ( ((JsonObject) item).get("value") instanceof JsonPrimitive ) ) {

							//itObject.add( propertyName, ((JsonObject) item).get("value"));

							if (itemBase != null) {
								Object newItem = Class.forName((itemBase.getClass()).getCanonicalName()).newInstance();
								Reflection.populateObjectByAnnotation(newItem, (JsonObject) item);
								returnData.add(newItem);
							}

						} else if (item instanceof JsonObject  && ( ((JsonObject) item).get("value") instanceof JsonObject ) ) {
							for(Map.Entry<String, JsonElement> att : ((JsonObject) item).getAsJsonObject("value").entrySet()) {

								JsonObject itObject = new JsonObject();
								itObject.addProperty("label", att.getKey());
								itObject.addProperty("value", att.getValue().getAsNumber());
								returnData.add(itObject);
							}
						}
					}

					//returnData.add(item);
				}
			}
		}

		return returnData;
	}
	
	public void processDataToInstagramReport(List<Object> resultFormated, List<InstagramReport> listAddItem, String typeReport) {

		for(Object itJson : resultFormated) {

			InstagramReport itPersist = new InstagramReport();

			itPersist.setIdInstagramUser(profile.getNetworkId());
			itPersist.setDate(this.reportDay.toEpochSecond());

			String lbl = this.getLabelValue(((JsonObject) itJson));
			String propertyName = "";

			if (typeReport.equals(this.REPORT_AGE_GENDER)) {
				propertyName = "audience_";
				propertyName = this.processPropertyNameValue(lbl, propertyName);
				lbl = this.processAgeGenderLabelValue(lbl);
			} else {
				propertyName = typeReport;
			}

			itPersist.setLabel(lbl);
			itPersist.setProperty(propertyName);
			
			itPersist.setValue(((JsonObject) itJson).get("value").getAsLong());
			
			listAddItem.add(itPersist);
		}
	}
	
	private String processAgeGenderLabelValue (String label) {

		if (label.indexOf("F.") > -1) {

			label = label.replaceAll("F.", "");
		} else if (label.indexOf("M.") > -1) {

			label = label.replaceAll("M.", "");
		} else if (label.indexOf("U.") > -1) {

			label = label.replaceAll("U.", "");
		}
		
		return label;
	}
	
	private String processPropertyNameValue(String label, String propertyName) {

		if (label.indexOf("F.") > -1) {

			propertyName += "age_women";
		} else if (label.indexOf("M.") > -1) {

			propertyName += "age_men";
		} else if (label.indexOf("U.") > -1) {

			propertyName += "age_undefined";
		}
		
		return propertyName;
	}

	/**
	 *	!!!!!! PROCESSAMENTO DOS DADOS RECEBIDOS PELA API !!!!!!
	 * 
	 */
}