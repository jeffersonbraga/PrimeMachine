package br.com.opsocial.server.utils.networksintegrations;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.users.basicinfo.Counts;
import org.jinstagram.entity.users.basicinfo.UserInfoData;
import org.jinstagram.exceptions.InstagramException;

import br.com.opsocial.server.utils.RecoverMaintenance;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.instagram.InstagramPost;

public class InstagramIntegration {

	//OpSocial
//	public static String CLIENT_ID = "013f81c63aa54ddc999d04e6ffda64b8";
//	public static String CLIENT_SECRET = "672a43304f844c0fb6a1f1c11877019e";

	// OpSocial Reports App (Apenas para teste)
	public static String CLIENT_ID = "351c96dc853b422ba98fee6607b72530";
	public static String CLIENT_SECRET = "f3b0b9504c4a4dd7aa3e05c80f2770ce";
	
	private static final Token EMPTY_TOKEN = null;
	
	private Profile profile;
	
	public InstagramIntegration() {
	}
	
	public Instagram getInstagram() {
		return new Instagram(CLIENT_ID);
	}
	
	public Instagram getInstagram(String accessToken, String tokenSecret) {
		
		Token token = new Token(accessToken, tokenSecret);
		
		return new Instagram(token);
	}
	
	public Instagram getInstagram(Profile profile) {
		Token token = new Token(profile.getToken(), profile.getTokenSecret());
		
		return new Instagram(token);
	}
	
	public String saveProfile(String code, User user) {
		
		try {
		
			InstagramService service =  new InstagramAuthService()
	        .apiKey(CLIENT_ID)
	        .apiSecret(CLIENT_SECRET)
	        .callback(OpSocialBackApplication.SERVER_PATH + "/instagram")     
	        .build();
			
			service.getAuthorizationUrl();
			
			Verifier verifier = new Verifier(code);
			Token accessToken = service.getAccessToken(verifier);
			
			Instagram instagram = new Instagram(accessToken);
			
			UserInfoData userInfoData = instagram.getCurrentUserInfo().getData();
			
			if(userInfoData == null) {
				return "Falha na autenticação, tente novamente mais tarde.";
			} else {

				MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				Profile baseProfile = remote.getEntityByNetworkId(String.valueOf(userInfoData.getId()), user.getAccount().getIdAccount());
				
				if(baseProfile == null) {
					profile = new Profile();
					profile.setActive('T');
					profile.setAvatar(userInfoData.getProfilePicture());
					profile.setNetworkId(String.valueOf(userInfoData.getId()));
					profile.setToken(instagram.getAccessToken().getToken());
					profile.setTokenSecret(instagram.getAccessToken().getSecret());
					profile.setScreenName(userInfoData.getUsername());
					profile.setType(Profile.INSTAGRAM);
					profile.setAccount(user.getAccount());
				        
			        return null;
				} else {
					return "Perfil já cadastrado";
				}
			}
            
		} catch (InstagramException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	public List<InstagramPost> search(String tag, Profile profileTurn) {
		
		List<InstagramPost> posts = null;
		
		HttpClient client = new HttpClient();
		
		GetMethod method = null;
		
		try {
			
			if(profileTurn == null) {
				method = new GetMethod("https://api.instagram.com/v1/tags/" + URLEncoder.encode(tag,"UTF-8") + "/media/recent?client_id=" + CLIENT_ID);	
			} else {
				method = new GetMethod("https://api.instagram.com/v1/tags/" + URLEncoder.encode(tag,"UTF-8") + "/media/recent?access_token=" + profileTurn.getToken());
			}
			
			int statusCode = client.executeMethod(method);
			
			if (statusCode == HttpStatus.SC_OK) {
			
				posts = new ArrayList<InstagramPost>();
				  
				String jsonResponse = new String(method.getResponseBody(), "UTF-8");
				
				jsonResponse.replaceAll("\\{\\}", "[]");

				JsonParser parser = new JsonParser();
				
				Instagram instagram = null;
				
				if(profileTurn != null) {
					Token token = new Token(profileTurn.getToken(), profileTurn.getTokenSecret());
					instagram = new Instagram(token);
				} else {
					instagram = new Instagram(InstagramIntegration.CLIENT_ID);	
				}
				
				if(!jsonResponse.equals(new String("[]"))) {
					
					JsonArray jsonArray = parser.parse(jsonResponse).getAsJsonObject().get("data").getAsJsonArray();
					
					for(int i=0;i<jsonArray.size();i++) {
						
						try {
							
							InstagramPost post = new InstagramPost();
							post.setAvatar(jsonArray.get(i).getAsJsonObject().get("user").getAsJsonObject().get("profile_picture").toString().replaceAll("\"", ""));
							post.setCountLikes(Integer.parseInt(jsonArray.get(i).getAsJsonObject().get("likes").getAsJsonObject().get("count").toString()));
							post.setCreateTime(jsonArray.get(i).getAsJsonObject().get("created_time").toString().replaceAll("\"", ""));
							post.setMediaLink(jsonArray.get(i).getAsJsonObject().get("link").getAsString());
							
							UserInfoData userInfoData = instagram.getUserInfo(jsonArray.get(i).getAsJsonObject()
									.get("user").getAsJsonObject().get("id").toString().replaceAll("\"", "")).getData();
							
							if(userInfoData != null) {
								Counts counts = userInfoData.getCounts();
								post.setFollowers(counts.getFollowedBy());
								post.setFollowing(counts.getFollows());
								post.setMedias(counts.getMedia());
							}
							
							post.setIdInstagramUser(Long.valueOf(jsonArray.get(i).getAsJsonObject().get("user").getAsJsonObject().get("id").toString().replaceAll("\"", "")));
							
							String idMedia = jsonArray.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", "");
							idMedia = idMedia.substring(0,idMedia.indexOf("_"));
							post.setIdMedia(Long.valueOf(idMedia));
							
							post.setPictureCaption(jsonArray.get(i).getAsJsonObject().get("caption") instanceof JsonObject ? 
									jsonArray.get(i).getAsJsonObject().get("caption").getAsJsonObject().get("text").toString().replaceAll("\"", "") : null);
							
							post.setPictureHeight(Integer.valueOf(jsonArray.get(i).getAsJsonObject().get("images")
									.getAsJsonObject().get("low_resolution").getAsJsonObject().get("height").toString()));
							
							post.setPictureWidth(Integer.valueOf(jsonArray.get(i).getAsJsonObject().get("images")
									.getAsJsonObject().get("low_resolution").getAsJsonObject().get("width").toString()));
							
							post.setPictureUrl(jsonArray.get(i).getAsJsonObject().get("images")
									.getAsJsonObject().get("low_resolution").getAsJsonObject().get("url").toString().replaceAll("\"", ""));
							
							post.setUserName(jsonArray.get(i).getAsJsonObject().get("user").getAsJsonObject().get("username").toString().replaceAll("\"", ""));
							post.setFullName(jsonArray.get(i).getAsJsonObject().get("user").getAsJsonObject().get("full_name").toString().replaceAll("\"", ""));
							
							List<String> tags = new ArrayList<String>();
							if(jsonArray.get(i).getAsJsonObject().has("tags") && jsonArray.get(i).getAsJsonObject().get("tags").isJsonArray()) {
								for (JsonElement jsonElement : jsonArray.get(i).getAsJsonObject().get("tags").getAsJsonArray()) {
									tags.add(jsonElement.getAsString());
								}
							}
							
							post.setTags(tags);
							
							posts.add(post);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (InstagramException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			
		return posts; 
	}
}
