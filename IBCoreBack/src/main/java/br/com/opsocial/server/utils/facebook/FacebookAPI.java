package br.com.opsocial.server.utils.facebook;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.face4j.facebook.Facebook;
import com.face4j.facebook.OAuthAccessToken;
import com.face4j.facebook.entity.User;
import com.face4j.facebook.enums.HttpClientType;
import com.face4j.facebook.exception.FacebookException;
import com.face4j.facebook.http.APICallerFactory;
import com.face4j.facebook.http.APICallerInterface;
import com.face4j.facebook.util.JSONToObjectTransformer;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.facebook.FacebookPostDTO;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.client.entity.socialnetworks.facebook.CommentDTO;
import br.com.opsocial.client.entity.socialnetworks.facebook.CommentsDTO;
import br.com.opsocial.client.entity.socialnetworks.facebook.FacebookPaging;
import br.com.opsocial.ejb.das.MaintenanceConversationRemote;
import br.com.opsocial.ejb.das.MaintenanceMessageRemote;
import br.com.opsocial.ejb.das.MaintenanceNotificationRemote;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.mailbox.Conversation;
import br.com.opsocial.ejb.entity.mailbox.Message;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.ejb.entity.sets.PostAttachment;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.aws.S3Integration;
import br.com.opsocial.server.utils.facebook.entities.ConversationData;
import br.com.opsocial.server.utils.facebook.entities.MessageData;
import br.com.opsocial.server.utils.instagram.entities.Comment;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import br.com.opsocial.server.utils.streams.StreamConversations;
import br.com.opsocial.server.utils.streams.StreamMessage;
import br.com.opsocial.server.utils.streams.StreamMessages;
import br.com.opsocial.server.utils.streams.StreamPost;
import br.com.opsocial.server.utils.streams.StreamPostComment;
import br.com.opsocial.server.utils.streams.StreamPostComments;
import br.com.opsocial.server.utils.streams.StreamPostPaging;
import facebook4j.Account;
import facebook4j.CommentUpdate;
import facebook4j.Media;
import facebook4j.PostUpdate;
import facebook4j.RawAPIResponse;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.VideoUpdate;
import facebook4j.auth.AccessToken;
import facebook4j.auth.OAuthAuthorization;
import facebook4j.internal.http.HttpClientImpl;
import facebook4j.internal.http.HttpParameter;
import facebook4j.internal.http.HttpRequest;
import facebook4j.internal.http.RequestMethod;

public class FacebookAPI {  

	public static final String PUBLISHING_PHOTOS = "https://graph.facebook.com/v2.10/ALBUM_ID/photos";
	public static final String PUBLISHING_PHOTO = "https://graph.facebook.com/v2.10/PAGE_ID/photos";
	public static final String CREATE_ALBUM = "https://graph.facebook.com/v2.10/PROFILE_ID/albums";
	
	public static final String INSIGHT_PAGE_FANS = "page_fans";
	
	public static final String MESSAGE_FIELDS = "id,created_time,message,from,shares{id,description,link,name},attachments{id,mime_type,size,name}";
	public static final String CONVERSATIONS_FIELDS = "senders,participants,can_reply,is_subscribed,message_count,snippet,updated_time,messages{id,created_time,message,from,shares{id,description,link,name},attachments{id,mime_type,size,name}},unread_count";
	public static final String COMMENT_FIELDS = "user_likes,likes.summary(true),id,comment_count,type,created_time,from{id,name},like_count,message,attachment{description,media{image},target,title,type,url},comments.limit(2){user_likes,id,comment_count,type,created_time,from{id,name},like_count,message,attachment{description,media{image},target,title,type,url}}";
	public static final String COMMENT_RESPONSE_COMMENT = "user_likes,likes.summary(true),id,comment_count,created_time,from{id,name},like_count,message,attachment{description,media{image},target,title,type,url},comments.limit(2){user_likes,id,comment_count,created_time,from{id,name},like_count,message,attachment{description,media{image},target,title,type,url}}";
	public static final String POST_FIELDS = "id,caption,created_time,description,message,message_tags,link,actions,picture,source,type,name,from,object_id,shares,likes.summary(true),comments.summary(true).limit(2){user_likes,id,comment_count,type,created_time,from{id,name},like_count,message,attachment{description,media{image},target,title,type,url},comments.limit(2){user_likes,id,comment_count,type,created_time,from{id,name},like_count,message,attachment{description,media{image},target,title,type,url}}},attachments{description,media{image},target,title,type,url,subattachments{description,media{image},target,title,type,url}}";
	public static final String COMMENT_REPLY_FIELDS = "user_likes,id,comment_count,type,created_time,from{id,name},like_count,message,attachment{description,media{image},target,title,type,url}";
	
	public static final String POST_USER = "link,picture{picture}";
	
	public static final String ALBUM_FIELDS = "id,name";
	
	public CommentsDTO recoverComments(String id, String accessToken, FacebookPaging paging, Integer limit) throws FacebookException {
		
		NameValuePair[] nameValuePairs;
		if(paging == null) {
			nameValuePairs = new NameValuePair[] {new NameValuePair("limit", limit.toString()),
					  new NameValuePair("access_token", accessToken), 
					  new NameValuePair("format", "JSON")};
		} else {
			nameValuePairs = new NameValuePair[] {new NameValuePair("limit", limit.toString()), 
					  new NameValuePair("after", paging.getCursors().getAfter()),
					  new NameValuePair("access_token", accessToken), 
					  new NameValuePair("format", "JSON")};
		}

		APICallerInterface caller = APICallerFactory.getAPICallerInstance(HttpClientType.APACHE_HTTP_CLIENT);
		
		String jsonResponse = caller.getData("https://graph.facebook.com/v2.10/" + id + 
				"/comments?&access_token=" + accessToken.replace("|", "%7C"), nameValuePairs).replaceAll("\\{\\}", "[]");
		
		CommentsDTO commentsDTO = new CommentsDTO();
		List<CommentDTO> comments = new ArrayList<CommentDTO>();
		
		try{
			jsonResponse = jsonResponse.replaceAll("\\{\\}", "[]");
	
			JsonParser parser = new JsonParser();		
	
			if(!jsonResponse.equals(new String("[]"))) {
				
				CommentDTO[] commentsArray = JSONToObjectTransformer.getObject(parser
						.parse(jsonResponse).getAsJsonObject().get("data").getAsJsonArray().toString(), CommentDTO[].class);
				
				for(int i=0; i<commentsArray.length; i++) {
					
					commentsArray[i].setCommentId(parser.parse(jsonResponse).getAsJsonObject().get("data").getAsJsonArray()
							.get(i).getAsJsonObject().get("id").toString().replaceAll("\"", ""));
					
					commentsArray[i].getFrom().setIdFrom(parser.parse(jsonResponse).getAsJsonObject().get("data").getAsJsonArray()
							.get(i).getAsJsonObject().get("from").getAsJsonObject().get("id").toString().replaceAll("\"", ""));
					
					OAuthAccessToken authAccessToken = new OAuthAccessToken(accessToken);
					Facebook facebook = new FacebookIntegration().getFacebook(authAccessToken);
					
					User user = facebook.getUser(commentsArray[i].getFrom().getIdFrom());
					commentsArray[i].getFrom().setProfilePicture(user.getPictureURL());
					commentsArray[i].getFrom().setName(user.getName());
					
					try {
						Date createTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ROOT)
							.parse(commentsArray[i].getCreatedTime().replaceAll("\\+0([0-9]){1}\\:00", "+0$100"));
						
						commentsArray[i].setCreatedTime(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(createTime));
					} catch (ParseException e) {
						commentsArray[i].setCreatedTime("");
						e.printStackTrace();
					}
					
					comments.add(commentsArray[i]);
				}

				commentsDTO.setComments(comments);
				
				if(parser.parse(jsonResponse).getAsJsonObject().has("paging")) {
					
					paging = JSONToObjectTransformer.getObject(parser
							.parse(jsonResponse).getAsJsonObject().get("paging").getAsJsonObject().toString(), FacebookPaging.class);
					
					commentsDTO.setPaging(paging);
				}
				
			}
		} catch (FacebookException faceException) {
			if(faceException.getError().getErrorCode() == 400 || faceException.getError().getErrorCode() == 190) {
				faceException.printStackTrace();
			}
		}
	
		return commentsDTO;
	}
	
	public boolean unlikePost(String postId, String accessToken) {
	
		try {
			
			HttpClient client = new HttpClient();
			
			DeleteMethod method = new DeleteMethod("https://graph.facebook.com/v2.10/" + postId + "/likes/?access_token=" + accessToken.replace("|", "%7C"));
			int statusCode = client.executeMethod(method);
		
			if (statusCode != HttpStatus.SC_OK) {
			  System.err.println("Method failed: " + method.getStatusLine());
			}
			
			byte[] responseBody = method.getResponseBody();
			  
			String response = new String(responseBody, "UTF-8");
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean likePost(String postId, String accessToken) {
		
		try {
			
			HttpClient client = new HttpClient();
			
			PostMethod method = new PostMethod("https://graph.facebook.com/v2.10/" + postId + "/likes/?access_token=" + accessToken.replace("|", "%7C"));
			
			int statusCode = client.executeMethod(method);
		
			if (statusCode != HttpStatus.SC_OK) {
			  System.err.println("Method failed: " + method.getStatusLine());
			}
			
			byte[] responseBody = method.getResponseBody();
			  
			String response = new String(responseBody, "UTF-8");
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public List<Account> getAccounts(facebook4j.Facebook facebook) {
		
		List<Account> accounts = new ArrayList<Account>();
		ResponseList<Account> accountsFromAPI = null;
		
		try {
			
			do {
				
				if(accountsFromAPI == null) {
					accountsFromAPI = facebook.getAccounts(new Reading().limit(100));
				} else {
					accountsFromAPI = facebook.fetchNext(accountsFromAPI.getPaging());
				}
				
				accounts.addAll(accountsFromAPI);
				
			} while(accountsFromAPI.getPaging() != null && accountsFromAPI.getPaging().getNext() != null);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return accounts;
	}

	public boolean post(Post post, Profile profile) {

		Boolean wasSent = true;

		try {

			AccessToken accessToken = new AccessToken(profile.getToken(), null);
			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			if(post.getAppendType() != null) {

				if(post.getAppendType().equals(Post.APPEND_ALBUM) && post.getTitle() == null) {
					post.setAppendType(Post.APPEND_PICTURE);
				}

				if(post.getAppendType().equals(Post.APPEND_PICTURE)) {

					try {

						ResponseList<facebook4j.Album> albums = facebook.getAlbums(profile.getNetworkId(), new Reading().limit(50).fields(ALBUM_FIELDS));
						Boolean albumFound = false;

						for(facebook4j.Album album : albums) {
							if(album.getName().equals("Timeline Photos")) {
								FacebookAPI facebookAPI = new FacebookAPI();
								albumFound = true;
								String fileName = "";// = tempPath + post.getPicture().substring(post.getPicture().lastIndexOf("/") + 1);

								List<String> pictures = new ArrayList<String>();

								for(PostAttachment itemAtt : post.getPostsAttachments()) {
									fileName = itemAtt.getFileName().substring(itemAtt.getFileName().lastIndexOf("/") + 1);
									pictures.add(fileName);
								}

								wasSent = facebookAPI.uploadPhotos(profile.getNetworkId(), pictures, facebook, post.getText());
								break;
							}
						}

						if(!albumFound) {

							FacebookAPI facebookAPI = new FacebookAPI();

							String fileName = "";// = tempPath + post.getPicture().substring(post.getPicture().lastIndexOf("/") + 1);

							for(PostAttachment itemAtt : post.getPostsAttachments()) {
								fileName = itemAtt.getFileName().substring(itemAtt.getFileName().lastIndexOf("/") + 1);
							}

							albumFound = wasSent = facebookAPI.uploadPhoto(profile.getNetworkId(), fileName, accessToken.getToken(), post.getText());
						}

					} catch (facebook4j.FacebookException e) {
						wasSent = false;
						e.printStackTrace();
					}
					
				} else if(post.getAppendType().equals(Post.APPEND_RESUME)) {

					try {

						URL postLink = new URL(post.getLink());

						PostUpdate postUpdate = new PostUpdate(postLink);
						postUpdate.setMessage(post.getText());
						postUpdate.setCaption(postLink.getHost());
						if(post.getPicture() != null) {
							postUpdate.setPicture(new URL(post.getPicture()));
						}
						if(post.getTitle() != null) {
							postUpdate.setName(post.getTitle());
						}
						if(post.getDescription() != null) {
							postUpdate.setDescription(post.getDescription());
						}

						facebook.postFeed(postUpdate);
						wasSent = true;

					} catch (MalformedURLException e) {
						wasSent = false;
						e.printStackTrace();
					} catch (facebook4j.FacebookException e) {
						wasSent = false;
						e.printStackTrace();
					} 

				} else if(post.getAppendType().equals(Post.APPEND_ALBUM)) {

					try {

						FacebookAPI facebookAPI = new FacebookAPI();

						ResponseList<facebook4j.Album> albums = facebook.albums().getAlbums();

						facebook4j.Album insertedAlbum = null;

						for(facebook4j.Album album : albums) {
							if(album.getName().equals(post.getTitle())) {
								insertedAlbum = album;
								break;
							}	
						}

						if (insertedAlbum == null) {
							facebookAPI.createAlbum(profile.getNetworkId(), accessToken.getToken(), post.getTitle(), post.getDescription());

							albums = facebook.albums().getAlbums();

							for(facebook4j.Album album : albums) {
								if(album.getName().equals(post.getTitle())) {
									insertedAlbum = album;
									break;
								}	
							}
						}

						if (insertedAlbum != null) {
							
							List<String> pictureNames = new ArrayList<String>();

							for(PostAttachment picture : post.getPostsAttachments()) {

								String fileName = picture.getFileName().substring(picture.getFileName().lastIndexOf("/") + 1);

								if (this.isImageFile(picture.getFileType())) {
									pictureNames.add(fileName);
								} else {
									this.processVideoFile(fileName, post.getText(), facebook);
								}
							}

							facebookAPI.uploadPhoto(insertedAlbum.getId(), pictureNames, accessToken.getToken(), post.getText());

							wasSent = true;
						} else {
							wasSent = false;
						}

					} catch (facebook4j.FacebookException e) {
						wasSent = false;
						e.printStackTrace();
					}

				} else if(post.getAppendType().equals(Post.APPEND_VIDEO)) {
					
					for(PostAttachment picture : post.getPostsAttachments()) {
						this.processVideoFile(picture.getFileName(), post.getText(), facebook);
					}
				}
				
			} else {

				try {

					facebook.postStatusMessage(post.getText());
					wasSent = true;
					
				} catch (facebook4j.FacebookException e) {
					wasSent = false;
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			wasSent = false;
			e.printStackTrace();
		}

		return wasSent;
	}

	private boolean isImageFile(Character fileType) {

		if(fileType.equals(PostAttachment.PHOTO)) {

			return true;			
		}

		return false;
	}

	private boolean processVideoFile(String fileName, String text, facebook4j.Facebook facebook) {

		String videoPostsPath = OpSocialBackApplication.UPLOAD_DIR_VIDEO_POSTS;
		
		new S3Integration().downloadS3Object(S3Integration.WOOPSOCIAL_BUCKET_NAME, 
				S3Integration.WOOPSOCIAL_VIDEOS_POSTS_FOLDER, fileName, videoPostsPath);
		
		try {

			Media media = new Media(new File(videoPostsPath, fileName));

			VideoUpdate videoUpdate = new VideoUpdate(media);
			videoUpdate.setDescription(text);
			videoUpdate.setPublished(true);

			facebook.postVideo(videoUpdate);

			return true;
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
			return false;
		}
	}

	public Boolean editPost(StreamPost streamPost, Profile profile) {
		
		Boolean edited = false;
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);
			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
			
			Map<String, String> queryParameters = new HashMap<String, String>();
			queryParameters.put("message", streamPost.getText());

			RawAPIResponse res = facebook.callPostAPI(streamPost.getPostId(), queryParameters);
			
			JsonParser parser = new JsonParser();			
			edited = parser.parse(res.asString()).getAsJsonObject().get("success").getAsBoolean();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return edited;
	}

	public List<StreamPost> getTaggedPosts(Profile profile, StreamPostPaging streamPostPaging) {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);
			
			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
			
			ResponseList<facebook4j.Post> posts;
			
			if(streamPostPaging.getUntil() != null) {
				
				posts = facebook.getTagged(new Reading().
						fields(POST_FIELDS).
						limit(streamPostPaging.getLimit()).
						offset(streamPostPaging.getOffset()).
						until(new Date(java.lang.Long.valueOf(streamPostPaging.getUntil()) * 1000L)));
				
				posts.remove(0);
			} else {
				Reading reading = new Reading().fields(POST_FIELDS).limit(streamPostPaging.getLimit());
				posts = facebook.getTagged(reading);
			}
			
			if(posts.getPaging() != null && !posts.isEmpty()) {
				
				String until = String.valueOf(posts.get(posts.size() - 1).getCreatedTime().getTime() / 1000L);
				
				streamPostPaging.setUntil(until);
			}
			
			streamPosts.addAll(MountDTO.mountStreamPostsForFacebook(posts, facebook, profile));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return streamPosts;
	}
	
	public StreamPostComments getCommentReplies(StreamPostComment streamPostComment, StreamPostPaging streamPostPaging, Profile profile) {
		
		StreamPostComments streamPostComments = new StreamPostComments();
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);
			
			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
			
			ResponseList<facebook4j.Comment> comments;
			
			if(streamPostPaging.getAfter() != null) {
				
				comments = facebook.getCommentReplies(streamPostComment.getCommentId(), new Reading().
						fields(COMMENT_FIELDS).
						limit(streamPostPaging.getLimit()).
						//offset(streamPostPaging.getOffset()).
						after(streamPostPaging.getAfter()));
				
			} else {
				comments = facebook.getCommentReplies(streamPostComment.getCommentId(), new Reading().fields(COMMENT_FIELDS).limit(streamPostPaging.getLimit()));
			}
			
			if(comments.getPaging() != null && !comments.isEmpty()) {
				streamPostPaging.setAfter(comments.getPaging().getCursors().getAfter()); 
			}
			
			streamPostComments.setComments(MountDTO.mountStreamPostCommentForFacebook(comments, facebook));
			streamPostComments.setPaging(streamPostPaging);
			
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		}
		
		return streamPostComments;
	}
	
	public StreamMessage sendMessage(StreamMessage streamMessage, Profile profile) {
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
			
			String idMessage = facebook.answerConversation(streamMessage.getIdConversation(), streamMessage.getMessage());
			
			streamMessage.setIdMessage(idMessage);
			
			//facebook.getMessage(idMessage, new Reading().fields(MESSAGE_FIELDS));
			
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		}
		
		return streamMessage;
	}
	
	public StreamMessages getMessages(String idConversation, StreamPostPaging streamPostPaging, Profile profile) {
		
		StreamMessages streamMessages = new StreamMessages();
		
		AccessToken accessToken = new AccessToken(profile.getToken(), null);
		
		facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
		
		try {
			
			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
			
			if(streamPostPaging.getAfter() != null) {
				
				Map<String, String> queryParameters = new HashMap<String, String>();
				queryParameters.put("fields", MESSAGE_FIELDS);
				queryParameters.put("limit", streamPostPaging.getLimit().toString());
				queryParameters.put("after", streamPostPaging.getAfter());

				RawAPIResponse res = facebook.callGetAPI(idConversation + "/messages", queryParameters);
				
				MessageData messageData = gson.fromJson(res.asString(), MessageData.class);
				
				streamMessages = MountDTO.mountMessagesForFacebook(messageData, idConversation);
				
			} else {
				
				Map<String, String> queryParameters = new HashMap<String, String>();
				queryParameters.put("fields", MESSAGE_FIELDS);
				queryParameters.put("limit", streamPostPaging.getLimit().toString());

				RawAPIResponse res = facebook.callGetAPI(idConversation + "/messages", queryParameters);

				MessageData messageData = gson.fromJson(res.asString(), MessageData.class);
				
				streamMessages = MountDTO.mountMessagesForFacebook(messageData, idConversation);
			}
			
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		}
		
		return streamMessages;
	}
	
	public List<StreamMessage> mountMessages(JsonArray jsonMessages, String idConversation) {
		
		List<StreamMessage> messages = new ArrayList<StreamMessage>(); 
		
		Iterator<JsonElement> messagesList = jsonMessages.iterator();
		
		while (messagesList.hasNext() ) {

			try {

				JsonObject jsonMessage = ((JsonObject) messagesList.next());
				
				StreamMessage streamMessage = new StreamMessage();
				streamMessage.setIdMessage(jsonMessage.get("id").getAsString());
				streamMessage.setIdConversation(idConversation);
				streamMessage.setMessage(jsonMessage.get("message").getAsString());
				streamMessage.setUserId(jsonMessage.get("from").getAsJsonObject().get("id").getAsString());
				streamMessage.setUserName(jsonMessage.get("from").getAsJsonObject().get("name").getAsString());
				
				Date createdTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ROOT)
						.parse(jsonMessage.get("created_time").getAsString().replaceAll("\\+0([0-9]){1}\\:00", "+0$100"));
				
				streamMessage.setCreatedTime(createdTime.getTime() / 1000L);
				
				messages.add(streamMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return messages;
	}
	
	public StreamConversations getConversations(Profile profile, StreamPostPaging streamPostPaging) {
		
		StreamConversations streamConversations = new StreamConversations();
		
		AccessToken accessToken = new AccessToken(profile.getToken(), null);
		
		facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
		
		try {
			
			Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
			
			if(streamPostPaging.getAfter() != null) {
				
				Map<String, String> queryParameters = new HashMap<String, String>();
				queryParameters.put("fields", CONVERSATIONS_FIELDS);
				queryParameters.put("limit", streamPostPaging.getLimit().toString());
				queryParameters.put("after", streamPostPaging.getAfter());

				RawAPIResponse res = facebook.callGetAPI(profile.getNetworkId() + "/conversations", queryParameters);
				
				ConversationData conversationData = gson.fromJson(res.asString(), ConversationData.class);
				
				if(conversationData.getPaging().getNext() != null) {
					streamPostPaging.setAfter(conversationData.getPaging().getCursors().getAfter());		
				} else {
					streamPostPaging.setAfter(null);
				}
			
				streamPostPaging.setBefore(conversationData.getPaging().getCursors().getBefore());
				streamConversations.setConversations(MountDTO.mountConversationsForFacebook(conversationData.getData(), profile));
				
			} else if(streamPostPaging.getBefore() != null) {
				
				Map<String, String> queryParameters = new HashMap<String, String>();
				queryParameters.put("fields", CONVERSATIONS_FIELDS);
				queryParameters.put("limit", streamPostPaging.getLimit().toString());
				queryParameters.put("before", streamPostPaging.getBefore());

				RawAPIResponse res = facebook.callGetAPI(profile.getNetworkId() + "/conversations", queryParameters);
				
				ConversationData conversationData = gson.fromJson(res.asString(), ConversationData.class);
				
				streamPostPaging.setBefore(conversationData.getPaging().getCursors().getBefore());
				streamConversations.setConversations(MountDTO.mountConversationsForFacebook(conversationData.getData(), profile));
				
			} else {
				
				Map<String, String> queryParameters = new HashMap<String, String>();
				queryParameters.put("fields", CONVERSATIONS_FIELDS);
				queryParameters.put("limit", streamPostPaging.getLimit().toString());

				RawAPIResponse res = facebook.callGetAPI(profile.getNetworkId() + "/conversations", queryParameters);
				
				ConversationData conversationData = gson.fromJson(res.asString(), ConversationData.class);
				
				if (conversationData.getPaging() != null) {
					if(conversationData.getPaging().getNext() != null) {
						streamPostPaging.setAfter(conversationData.getPaging().getCursors().getAfter());		
					} else {
						streamPostPaging.setAfter(null);
					}
					
					streamPostPaging.setBefore(conversationData.getPaging().getCursors().getBefore());
				} else {
					streamPostPaging.setAfter(null);
					streamPostPaging.setBefore(null);
				}
				streamConversations.setConversations(MountDTO.mountConversationsForFacebook(conversationData.getData(), profile));
			}
			 
			streamConversations.setStreamPostPaging(streamPostPaging);
			
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		}
		
		return streamConversations;
	}
	
	public StreamPostComments getComments(StreamPost streamPost, StreamPostPaging streamPostPaging, Profile profile) {
		
		StreamPostComments streamPostComments = new StreamPostComments();
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);
			
			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
			
			ResponseList<facebook4j.Comment> comments;
			
			if(streamPostPaging.getAfter() != null) {
				
				comments = facebook.getPostComments(streamPost.getPostId(), new Reading().
						fields(COMMENT_FIELDS).
						limit(streamPostPaging.getLimit()).
						after(streamPostPaging.getAfter()));
				
			} else {				
				comments = facebook.getPostComments(streamPost.getPostId(), new Reading().fields(COMMENT_FIELDS).limit(streamPostPaging.getLimit()));
			}
			
			if(comments.getPaging() != null && !comments.isEmpty()) {
				streamPostPaging.setAfter(comments.getPaging().getCursors().getAfter()); 
			}
			
			streamPostComments.setComments(MountDTO.mountStreamPostCommentForFacebook(comments, facebook));
			streamPostComments.setPaging(streamPostPaging);
			
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		}
		
		return streamPostComments;
	}
	
	public List<StreamPost> getTimelinePosts(Profile profile, StreamPostPaging streamPostPaging) {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);
			
			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
			
			ResponseList<facebook4j.Post> posts;
			
			if(streamPostPaging.getUntil() != null) {
				
				posts = facebook.getFeed(new Reading().
						fields(POST_FIELDS).
						limit(streamPostPaging.getLimit()).
						until(new Date(java.lang.Long.valueOf(streamPostPaging.getUntil()) * 1000L)));
				
				posts.remove(0);
			} else {
				Reading reading = new Reading().fields(POST_FIELDS).limit(streamPostPaging.getLimit());
				posts = facebook.getFeed(reading);
			}
			
			if(posts.getPaging() != null && !posts.isEmpty()) {
				
				String until = String.valueOf(posts.get(posts.size() - 1).getCreatedTime().getTime() / 1000L);
				
				streamPostPaging.setUntil(until);
			}
			
			streamPosts.addAll(MountDTO.mountStreamPostsForFacebook(posts, facebook, profile));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return streamPosts;
	}
	
	public List<StreamPost> getMyPosts(Profile profile, StreamPostPaging streamPostPaging) {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);
			
			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
			
			ResponseList<facebook4j.Post> posts;
			
			if(streamPostPaging.getUntil() != null) {
				
				posts = facebook.getPosts(new Reading().
						fields(POST_FIELDS).
						limit(streamPostPaging.getLimit() + 1).
						until(new Date(java.lang.Long.valueOf(streamPostPaging.getUntil()) * 1000L)));
				
				posts.remove(0);
			} else {
				Reading reading = new Reading().fields(POST_FIELDS).limit(streamPostPaging.getLimit());
				posts = facebook.getPosts(reading);
			}
			
			if(posts.getPaging() != null && !posts.isEmpty()) {
				
				String until = String.valueOf(posts.get(posts.size() - 1).getCreatedTime().getTime() / 1000L);
				
				streamPostPaging.setUntil(until);
			}
			
			streamPosts.addAll(MountDTO.mountStreamPostsForFacebook(posts, facebook, profile));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return streamPosts;
	}
	
	public StreamPostComment comment(String objectId, StreamPostComment streamPostComment, Profile profile) {
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);

			facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

			if (streamPostComment.getType() != null && 
					(streamPostComment.getType().equals(FacebookPostDTO.PHOTO) || streamPostComment.getType().equals(FacebookPostDTO.VIDEO))) {

				CommentUpdate commentUpdate = new CommentUpdate();
				if (streamPostComment.getMessage() != null && !streamPostComment.getMessage().equals("")) {
					commentUpdate.setMessage(streamPostComment.getMessage());
				}
				
				Media source = new Media(new File(OpSocialBackApplication.UPLOAD_DIR_COMMENT_POSTS + "/" + streamPostComment.getAttachment().getMedia().getImage().getSrc()));

				commentUpdate.setSource(source);
				
				streamPostComment = MountDTO.mountPostCommentForFacebook(facebook.getComment(facebook.commentPost(objectId, commentUpdate), 
						new Reading().fields(COMMENT_RESPONSE_COMMENT)), false, facebook);
				
			} else {
				streamPostComment = MountDTO.mountPostCommentForFacebook(facebook.getComment(facebook.commentPost(objectId, streamPostComment.getMessage()), 
						new Reading().fields(COMMENT_RESPONSE_COMMENT)), false, facebook);
			}
			
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		}
		
		return streamPostComment;
	}
	
	public boolean uploadPhoto(String profileId, String fileName, String accessToken, String message) {
		
		String imagesPostsPath = OpSocialBackApplication.UPLOAD_DIR_IMAGES_POSTS;
		
		try {
			
			org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httpPost = new HttpPost(PUBLISHING_PHOTO.replaceFirst("PAGE_ID", profileId) + "?" 
							+ "access_token=" + accessToken);
			
			MultipartEntity reqEntity = new MultipartEntity();
			
			new S3Integration().downloadS3Object(S3Integration.WOOPSOCIAL_BUCKET_NAME, 
					S3Integration.WOOPSOCIAL_IMAGES_POSTS_FOLDER, fileName, imagesPostsPath);
			
			File fileUpload = new File(imagesPostsPath, fileName);
			FileBody uploadFilePart = new FileBody(fileUpload);
			reqEntity.addPart("source", uploadFilePart);
			if(message != null) {
				reqEntity.addPart("message", new StringBody(message, "text/plain", Charset.forName("UTF-8")));
			}
			
			httpPost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httpPost);

			if(response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean uploadPhoto(String albumId, List<String> pictureNames, String accessToken, String message) {

		String imagesPostsPath = OpSocialBackApplication.UPLOAD_DIR_IMAGES_POSTS;
		
		try {
			
			org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
			
			HttpPost httpPost = new HttpPost(PUBLISHING_PHOTOS.replaceFirst("ALBUM_ID", albumId) + "?" 
							+ "access_token=" + accessToken);
			
			MultipartEntity reqEntity = new MultipartEntity();
			
			for(String pictureName : pictureNames) {
				
				new S3Integration().downloadS3Object(S3Integration.WOOPSOCIAL_BUCKET_NAME, 
						S3Integration.WOOPSOCIAL_IMAGES_POSTS_FOLDER, pictureName, imagesPostsPath);
				
				File fileUpload = new File(imagesPostsPath, pictureName);
				FileBody uploadFilePart = new FileBody(fileUpload);
				reqEntity.addPart("source", uploadFilePart);
				
			}

			if(message != null) {
				reqEntity.addPart("message", new StringBody(message, "text/plain", Charset.forName("UTF-8")));
			}

			httpPost.setEntity(reqEntity);

			HttpResponse response = httpclient.execute(httpPost);
			
			if(response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
				return true;
			} else {
				return false;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean uploadPhotos(String profileId, List<String> imageNames, facebook4j.Facebook facebook, String message) {
		
		try {
			
			String imagesPostsPath = OpSocialBackApplication.UPLOAD_DIR_IMAGES_POSTS;
			
			List<String> attachedMedia = new ArrayList<String>();
			
			// Upload all photos first
			for(String imageName : imageNames) {
				
				new S3Integration().downloadS3Object(S3Integration.WOOPSOCIAL_BUCKET_NAME, 
						S3Integration.WOOPSOCIAL_IMAGES_POSTS_FOLDER, imageName, imagesPostsPath);
				
				HttpParameter[] httpParameters = new HttpParameter[2];
				
				httpParameters[0] = new HttpParameter("file", new File(imagesPostsPath, imageName));
				httpParameters[1] = new HttpParameter("published", false);
				
				OAuthAuthorization  authorization = new OAuthAuthorization(facebook.getConfiguration());
				authorization.setOAuthAccessToken(facebook.getOAuthAccessToken());
				
				HttpClientImpl http = new HttpClientImpl();
				
				facebook4j.internal.http.HttpResponse res = http.request(new HttpRequest(RequestMethod.POST, PUBLISHING_PHOTO.replaceFirst("PAGE_ID", profileId), httpParameters, authorization, null));
				
				JsonParser parser = new JsonParser();			
				String photoId = parser.parse(res.asString()).getAsJsonObject().get("id").getAsString();
				
				attachedMedia.add(photoId);
			}
			
			// Create the post and attach all images uploaded in the previous call. 
			Map<String, String> queryParameters = new HashMap<String, String>(); 
			if (message != null && message != "") {
				queryParameters.put("message", message);
			}
			
			int i = 0;
			for(String mediaId : attachedMedia) {
				queryParameters.put("attached_media[" + i + "]", "{\"media_fbid\":\"" + mediaId + "\"}");
				i++;
			}
			
			RawAPIResponse response = facebook.callPostAPI(profileId + "/feed", queryParameters);
			
			JsonParser parser = new JsonParser();			
			String responseJSON = parser.parse(response.asString()).getAsJsonObject().get("id").getAsString();
			
			//----- System.out.println("Response Multiple Photos Upload..: " + responseJSON);
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean createAlbum(String profileId, String accessToken, String name, String message) {

		try {

			if (name != null) {
				name = name.replace(" ", "%20");
			}

			if (message != null) {
				message = message.replace(" ", "%20");
			}

			Date backdate = new Date();
			backdate.setMinutes(backdate.getMinutes() + 1);

			String backdateTime = backdate.toString().replace(" ", "%20");
			
			org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(CREATE_ALBUM.replaceFirst("PROFILE_ID", profileId) + "?" 
							+ "access_token=" + accessToken + "&name=" + name + "&message=" + message);
			
			HttpResponse response = httpclient.execute(httpPost);
			
			return true;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public CommentDTO comment(String objectId, String accessToken, String message) {
		
		try {
			
			HttpClient client = new HttpClient();
			
			PostMethod method = new PostMethod(
				"https://graph.facebook.com/v2.10/" + objectId + "/comments?message=" +
					URLEncoder.encode(message,"UTF-8") + "&access_token=" + accessToken);
			
			int statusCode = client.executeMethod(method);
		
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + method.getStatusLine());
				return null;
			}
			
			byte[] responseBody = method.getResponseBody();
			
			CommentDTO commentDTO = new CommentDTO();
			commentDTO.setCommentId(new JsonParser().parse(new String(responseBody, "UTF-8")).getAsJsonObject().get("id").toString());
			commentDTO.setMessage(message);
			
			return commentDTO;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Long recoverValueByProperty(String pageId, String accessToken, String property) throws FacebookException {
			
		NameValuePair[] nameValuePairs = {new NameValuePair("access_token", accessToken), new NameValuePair("format", "JSON")};

		APICallerInterface caller = APICallerFactory.getAPICallerInstance(HttpClientType.APACHE_HTTP_CLIENT);

		Long insightValue = 0L;

		String jsonResponse = caller.getData("https://graph.facebook.com/v2.10/" + pageId + 
				"/insights/" + property + "?period=week&until=now", nameValuePairs);

		jsonResponse = jsonResponse.replaceAll("\\{\\}", "[]");

		JsonParser parser = new JsonParser();
		jsonResponse = parser.parse(jsonResponse).getAsJsonObject().get("data").getAsJsonArray().toString();

		if(!jsonResponse.equals(new String("[]"))) {
			
			JsonArray array = parser.parse(jsonResponse).getAsJsonArray().get(0).getAsJsonObject().get("values").getAsJsonArray();
			
			insightValue = Long.parseLong(array.get(array.size() - 1).getAsJsonObject().get("value").toString());
		}

		return insightValue;	
	}
	
	public Boolean verifyAccessToken(String accessToken) {
		
		NameValuePair[] nameValuePairs = {
				new NameValuePair("access_token", FacebookIntegration.APP_TOKEN), 
				new NameValuePair("input_token", accessToken), 
				new NameValuePair("format", "JSON")};

		APICallerInterface caller = APICallerFactory.getAPICallerInstance(HttpClientType.APACHE_HTTP_CLIENT);

		try {
			String jsonResponse = caller.getData("https://graph.facebook.com/v2.10/debug_token", nameValuePairs);
		
			jsonResponse = jsonResponse.replaceAll("\\{\\}", "[]");
	
			JsonParser parser = new JsonParser();
			String isValid = parser.parse(jsonResponse).getAsJsonObject().get("data").getAsJsonObject().get("is_valid").toString();

			if(isValid.equals("true")) {
				return true;
			} else {
				return false;
			}
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public Boolean verifyMailboxPermissions(String token, String networkId) {
		
		try {
			
			HttpClient client = new HttpClient();
			
			GetMethod method = new GetMethod("https://graph.facebook.com/v2.10/" + networkId + "/permissions/?" + 
					"access_token=" + token);
			
			int statusCode = client.executeMethod(method);
			
			if (statusCode != HttpStatus.SC_OK) {
			  System.err.println("Method failed: " + method.getStatusLine());
			}
			
			byte[] responseBody = method.getResponseBody();
			  
			String response = new String(responseBody, "UTF-8");
			
			JsonParser parser = new JsonParser();
			
			if(parser.parse(response).getAsJsonObject().get("data") != null) {
				JsonArray permissions = parser.parse(response).getAsJsonObject().get("data").getAsJsonArray();
				
				for(JsonElement permission : permissions) {
					if(permission.getAsJsonObject().get("read_mailbox") != null && permission.getAsJsonObject().get("read_page_mailboxes") != null) {
						return true;	
					} else {
						return false;
					}
				}
			} else {
				//----- System.out.println(networkId + ";" + response);
			}
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public void saveFacebookConversations(Profile profile) {
		
		MaintenanceConversationRemote conversationRemote = (MaintenanceConversationRemote) RecoverMaintenance.recoverMaintenance("Conversation");

		MaintenanceMessageRemote messageRemote = (MaintenanceMessageRemote) RecoverMaintenance.recoverMaintenance("Message");

		MaintenanceNotificationRemote notificationRemote = (MaintenanceNotificationRemote) RecoverMaintenance.recoverMaintenance("Notification");

		try {
		
			NameValuePair[] nameValuePairs = {new NameValuePair("access_token", profile.getToken()), 
					new NameValuePair("format", "JSON"),
					new NameValuePair("fields", CONVERSATIONS_FIELDS)};

			APICallerInterface caller = APICallerFactory.getAPICallerInstance(HttpClientType.APACHE_HTTP_CLIENT); 

			String jsonResponse = caller.getData("https://graph.facebook.com/v2.10/" + profile.getNetworkId() + 
					"/conversations", nameValuePairs).replaceAll("\\{\\}", "[]");

			try {

				JsonParser parser = new JsonParser();

				if(!jsonResponse.equals(new String("[]"))) {

					JsonArray conversations = parser
							.parse(jsonResponse).getAsJsonObject().get("data").getAsJsonArray();

					for(JsonElement element : conversations) {

						Conversation conversation = conversationRemote.getByNetworkConversationId(element.getAsJsonObject().get("id").getAsString(), profile.getIdProfile());

						boolean findUser = false;

						if(conversation == null) {

							conversation = new Conversation();

							conversation.setNetworkConversationId(element.getAsJsonObject().get("id").getAsString());
							conversation.setUnread(true);

							JsonArray users = element.getAsJsonObject().get("senders")
									.getAsJsonObject().get("data").getAsJsonArray();

							for(JsonElement sender : users) {
								if(!sender.getAsJsonObject().get("id").getAsString().equals(profile.getNetworkId())) {
									conversation.setUserId(sender.getAsJsonObject().get("id").getAsString());
									conversation.setUserName(sender.getAsJsonObject().get("name").getAsString());
									findUser = true;
									break;
								}
							}

							if(!findUser) {
								users = element.getAsJsonObject().get("participants")
										.getAsJsonObject().get("data").getAsJsonArray();

								for(JsonElement participant : users) {
									if(!participant.getAsJsonObject().get("id").getAsString().equals(profile.getNetworkId())) {
										conversation.setUserId(participant.getAsJsonObject().get("id").getAsString());
										conversation.setUserName(participant.getAsJsonObject().get("name").getAsString());
										findUser = true;
										break;
									}
								}
							}
						} else {
							findUser = true;
						}

						if(findUser) {

							conversation.setCanReply(element.getAsJsonObject().get("can_reply") != null ? element.getAsJsonObject().get("can_reply").getAsBoolean() : null);
							conversation.setIsSubscribed(element.getAsJsonObject().get("is_subscribed").getAsBoolean());
							conversation.setMessageCount(element.getAsJsonObject().get("message_count").getAsInt());
							conversation.setSnippet(element.getAsJsonObject().get("snippet") != null ? element.getAsJsonObject().get("snippet").getAsString() : null);
							conversation.setProfile(profile);

							try {
								Date createTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ROOT)
								.parse(element.getAsJsonObject().get("updated_time").getAsString().replaceAll("\\+0([0-9]){1}\\:00", "+0$100"));
								conversation.setUpdatedTime(createTime.getTime());
							} catch (ParseException e) {
								e.printStackTrace();
							}				

							try {
								conversation = conversationRemote.save(conversation);
							} catch (IllegalArgumentException e1) {
								e1.printStackTrace();
							} catch (Exception e1) {
								e1.printStackTrace();
							}

							JsonArray messages = element.getAsJsonObject().get("messages").getAsJsonObject().get("data").getAsJsonArray();

							for(JsonElement m : messages) {

								Message message = messageRemote.getByIdAndConversation(m.getAsJsonObject().get("id").getAsString(), conversation.getIdConversation());

								if(message == null) {

									try {
										conversation = conversationRemote.getById(conversation.getIdConversation());
										conversation.setUnread(true);
										conversation = conversationRemote.save(conversation);
									} catch (IllegalArgumentException e1) {
										e1.printStackTrace();
									} catch (Exception e1) {
										e1.printStackTrace();
									}

									message = new Message();

									message.setConversation(conversation);

									try {
										Date createTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ROOT)
										.parse(m.getAsJsonObject().get("created_time").getAsString().replaceAll("\\+0([0-9]){1}\\:00", "+0$100"));
										message.setCreatedTime(createTime.getTime());
									} catch (ParseException e) {
										e.printStackTrace();
									}

									message.setNetworkMessageId(m.getAsJsonObject().get("id").getAsString());
									message.setMessage(m.getAsJsonObject().get("message").getAsString());
									message.setUserId(m.getAsJsonObject().get("from").getAsJsonObject().get("id").getAsString());
									message.setUserName(m.getAsJsonObject().get("from").getAsJsonObject().get("name").getAsString());

									try {
										messageRemote.save(message);
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (Exception e) {
										e.printStackTrace();
									}

									Integer unreadConversations = conversationRemote.listUnreadConversations(profile.getIdProfile());

									Notification notification = notificationRemote.getByProfile(profile.getIdProfile());

									if(notification == null) {
										notification = new Notification();
										notification.setAction("ShowMailboxAction");
										notification.setProfile(profile);
										notification.setAccount(profile.getAccount());
									} else {
										notification = notificationRemote.getById(notification.getIdNotification());
									}

									notification.setDate(new Date());
									notification.setMessage(profile.getScreenName() + " possui " + unreadConversations + " novas mensagens");
									notification.setUnread(true);

									try {
										notificationRemote.save(notification);
									} catch (IllegalArgumentException e) {
										e.printStackTrace();
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							}
						}
					}
				} 
			} catch (Exception e) {
				e.printStackTrace();
			}

		}  catch (Exception e) {
			e.printStackTrace();
		}
	}
}
