package br.com.opsocial.server.utils.twitter;

import java.io.File;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.springframework.beans.factory.annotation.Autowired;
import br.com.opsocial.client.entity.facebook.FacebookPostDTO;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.client.entity.socialnetworks.facebook.FromDTO;
import br.com.opsocial.ejb.das.MaintenanceConversationRemote;
import br.com.opsocial.ejb.das.MaintenanceMessageRemote;
import br.com.opsocial.ejb.das.MaintenanceNotificationRemote;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.mailbox.Conversation;
import br.com.opsocial.ejb.entity.mailbox.Message;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.server.services.ServicesImpl;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.aws.S3Integration;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;
import br.com.opsocial.server.utils.streams.StreamConversation;
import br.com.opsocial.server.utils.streams.StreamConversations;
import br.com.opsocial.server.utils.streams.StreamMessage;
import br.com.opsocial.server.utils.streams.StreamMessages;
import br.com.opsocial.server.utils.streams.StreamPost;
import br.com.opsocial.server.utils.streams.StreamPostComment;
import br.com.opsocial.server.utils.streams.StreamPostPaging;
import facebook4j.IdNameEntity;
import twitter4j.DirectMessage;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.UploadedMedia;
import twitter4j.media.ImageUpload;
import twitter4j.media.ImageUploadFactory;
import twitter4j.media.MediaProvider;
import br.com.opsocial.server.services.ServicesImpl;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;
import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenanceConversationRemote;
import br.com.opsocial.ejb.das.MaintenanceMessageRemote;
import br.com.opsocial.ejb.das.MaintenanceNotificationRemote;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.mailbox.Conversation;
import br.com.opsocial.ejb.entity.mailbox.Message;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.ejb.entity.sets.PostAttachment;

public class TwitterAPI {

	public TwitterAPI() {
	}
	
	public StreamPostComment reply(String statusId, StreamPostComment streamPostComment, Profile profile) {
		
		TwitterIntegration twitterIntegration = new TwitterIntegration();
		Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());
		
		try {
			
			Status status;
			
			if(streamPostComment.getType() != null && streamPostComment.getType().equals(FacebookPostDTO.PHOTO)) {
				
//				ImageUpload upload = new ImageUploadFactory(
//						twitterIntegration.getConfigurationBuilderForMedia(profile.getToken(), profile.getTokenSecret())).
//						getInstance(MediaProvider.TWITTER);
//					
//				upload.upload(new File(streamPostComment.getAttachment().getMedia().getImage().getSrc()),
//						streamPostComment.getMessage());

				UploadedMedia uploadedMedia = twitter.uploadMedia(new File(streamPostComment.getAttachment().getMedia().getImage().getSrc()));
				StatusUpdate statusUpdate = new StatusUpdate(streamPostComment.getMessage());
				statusUpdate.setMediaIds(new long[]{uploadedMedia.getMediaId()});
				statusUpdate.setInReplyToStatusId(Long.valueOf(statusId));
				
				status = twitter.updateStatus(statusUpdate);
				
			} else if(streamPostComment.getType() != null && streamPostComment.getType().equals(FacebookPostDTO.VIDEO)) {

				UploadedMedia uploadedMedia = twitter.uploadMedia(new File(streamPostComment.getAttachment().getMedia().getVideoSrc()));
				StatusUpdate statusUpdate = new StatusUpdate(streamPostComment.getMessage());
				statusUpdate.setMediaIds(new long[]{uploadedMedia.getMediaId()});
				statusUpdate.setInReplyToStatusId(Long.valueOf(statusId));
				
				status = twitter.updateStatus(statusUpdate);

			} else {
				
				StatusUpdate statusUpdate = new StatusUpdate(streamPostComment.getMessage());
				statusUpdate.setInReplyToStatusId(Long.valueOf(statusId));
				
				status = twitter.updateStatus(statusUpdate);				
			}
			
			streamPostComment.setCommentId(String.valueOf(status.getId()));
			streamPostComment.setLikeCount(status.getFavoriteCount());
			streamPostComment.setCreatedTime(status.getCreatedAt().getTime() / 1000L);
			streamPostComment.setMessage(status.getText()); 
			
			FromDTO fromDTO = new FromDTO();
			fromDTO.setName(status.getUser().getScreenName());
			fromDTO.setProfilePicture(status.getUser().getProfileImageURLHttps().toString());
			
			streamPostComment.setFrom(fromDTO);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return streamPostComment;
	}
	
	public List<StreamPost> getMentionsPosts(Profile profile, StreamPostPaging streamPostPaging) {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		TwitterIntegration twitterIntegration = new TwitterIntegration();
		Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());
		
		Paging paging;
		
		ResponseList<Status> statuses;
		
		try {
			
			if(streamPostPaging.getMaxId() != null) {
				
				paging = new Paging();
				paging.setCount(streamPostPaging.getLimit());
				paging.setPage(streamPostPaging.getPage());
				paging.setMaxId(streamPostPaging.getMaxId());
				
				statuses = twitter.getMentionsTimeline(paging);
				
				if(statuses.size() > 0) {
					streamPostPaging.setMaxId(statuses.get(statuses.size() - 1).getId());
					streamPostPaging.setPage(paging.getPage() + 1);
				}
				
			} else {
				paging = new Paging(1, streamPostPaging.getLimit());
				
				statuses = twitter.getMentionsTimeline(paging);
				
				if(statuses.size() > 0) {
					streamPostPaging.setMaxId(statuses.get(statuses.size() - 1).getId());
					streamPostPaging.setPage(paging.getPage() + 1);
				}
			}
			
			streamPosts.addAll(MountDTO.mountStreamPostsForTwitter(statuses, profile));
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return streamPosts;
	}
	
	public List<StreamPost> getHomeTimelinePosts(Profile profile, StreamPostPaging streamPostPaging) {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		TwitterIntegration twitterIntegration = new TwitterIntegration();
		Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());
		
		Paging paging;
		
		ResponseList<Status> statuses;
		
		try {
			
			if(streamPostPaging.getMaxId() != null) {
				
				paging = new Paging();
				paging.setCount(streamPostPaging.getLimit());
				paging.setPage(streamPostPaging.getPage());
				paging.setMaxId(streamPostPaging.getMaxId());
				
				statuses = twitter.getHomeTimeline(paging);
				
				if(statuses.size() > 0) {
					streamPostPaging.setMaxId(statuses.get(statuses.size() - 1).getId());
					streamPostPaging.setPage(paging.getPage() + 1);
				}
				
			} else {
				paging = new Paging(1, streamPostPaging.getLimit());
				
				statuses = twitter.getHomeTimeline(paging);
				
				if(statuses.size() > 0) {
					streamPostPaging.setMaxId(statuses.get(statuses.size() - 1).getId());
					streamPostPaging.setPage(paging.getPage() + 1);
				}
			}
			
			streamPosts.addAll(MountDTO.mountStreamPostsForTwitter(statuses, profile));
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return streamPosts;
	}
	
	public List<StreamPost> getMyPosts(Profile profile, StreamPostPaging streamPostPaging) {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		TwitterIntegration twitterIntegration = new TwitterIntegration();
		Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());
		
		Paging paging;
		
		ResponseList<Status> statuses;
		
		try {
			
			if(streamPostPaging.getMaxId() != null) {
				
				paging = new Paging();
				paging.setCount(streamPostPaging.getLimit());
				paging.setPage(streamPostPaging.getPage());
				paging.setMaxId(streamPostPaging.getMaxId());
				
				statuses = twitter.getUserTimeline(paging);
				
				if(statuses.size() > 0) {
					streamPostPaging.setMaxId(statuses.get(statuses.size() - 1).getId());
					streamPostPaging.setPage(paging.getPage() + 1);
				}
				
			} else {
				paging = new Paging(1, streamPostPaging.getLimit());
				
				statuses = twitter.getUserTimeline(paging);
				
				if(statuses.size() > 0) {
					streamPostPaging.setMaxId(statuses.get(statuses.size() - 1).getId());
					streamPostPaging.setPage(paging.getPage() + 1);
				}
			}
			
			streamPosts.addAll(MountDTO.mountStreamPostsForTwitter(statuses, profile));
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return streamPosts;
	}
	
	public boolean post(Post post, Profile profile) {
	
		Boolean wasSent = true;
		String imagePostsPath = OpSocialBackApplication.UPLOAD_DIR_IMAGES_POSTS;
		String videoPostsPath = OpSocialBackApplication.UPLOAD_DIR_VIDEO_POSTS; 

		try {

			TwitterIntegration twitterIntegration = new TwitterIntegration();
			Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());

			if(post.getAppendType() != null && post.getAppendType().equals(Post.APPEND_PICTURE)) {
				
				ImageUpload upload = new ImageUploadFactory(
						twitterIntegration.getConfigurationBuilderForMedia(profile.getToken(), profile.getTokenSecret())).
						getInstance(MediaProvider.TWITTER);

				PostAttachment pAttachment = post.getPostsAttachments().get(0);

				try {
					
					new S3Integration().downloadS3Object(S3Integration.WOOPSOCIAL_BUCKET_NAME, 
							S3Integration.WOOPSOCIAL_IMAGES_POSTS_FOLDER, pAttachment.getFileName(), imagePostsPath);

					upload.upload(new File(imagePostsPath, pAttachment.getFileName()), post.getText());
					wasSent = true;

				} catch (Exception e) {
					wasSent = false;
					e.printStackTrace();
				}
			} else if(post.getAppendType() != null && post.getAppendType().equals(Post.APPEND_VIDEO)) {

				PostAttachment pAttachment = post.getPostsAttachments().get(0);
				
				new S3Integration().downloadS3Object(S3Integration.WOOPSOCIAL_BUCKET_NAME, 
						S3Integration.WOOPSOCIAL_VIDEOS_POSTS_FOLDER, pAttachment.getFileName(), videoPostsPath);

				UploadedMedia uploadedMedia = twitter.uploadMedia(new File(videoPostsPath, pAttachment.getFileName()));
				StatusUpdate statusUpdate = new StatusUpdate(post.getText());
				statusUpdate.setMediaIds(new long[]{uploadedMedia.getMediaId()});
				twitter.updateStatus(statusUpdate);

				wasSent = true;

			} else if(post.getAppendType() != null && post.getAppendType().equals(Post.APPEND_ALBUM)) {

				long[] ids_media = new long[post.getPostsAttachments().size()];
				int idx = 0;
				for(PostAttachment itAttachment : post.getPostsAttachments()) {
					
					new S3Integration().downloadS3Object(S3Integration.WOOPSOCIAL_BUCKET_NAME, 
							S3Integration.WOOPSOCIAL_VIDEOS_POSTS_FOLDER, itAttachment.getFileName(), imagePostsPath);
					
					UploadedMedia uploadedMedia = twitter.uploadMedia(new File(imagePostsPath, itAttachment.getFileName()));
					ids_media[idx++] = uploadedMedia.getMediaId();
				}

				StatusUpdate statusUpdate = new StatusUpdate(post.getText());				
				statusUpdate.setMediaIds(ids_media);

				twitter.updateStatus(statusUpdate);
				
			} else if(post.getAppendType() != null && post.getAppendType().equals(Post.APPEND_RESUME)) {
				/* 
						"entities": {
					      	"hashtags": [
					        
					      	],
					      	"urls": [
					        	{
						          "url": "https:\/\/t.co\/XweGngmxlP",
						          "unwound": {
						           		"url": "https:\/\/cards.twitter.com\/cards\/18ce53wgo4h\/3xo1c",
						            	"title": "Building the Future of the Twitter API Platform"
					          		}
					        	}
					      	],
					      	"user_mentions": [
					        
					      	]
					    }
				 */

				try {
					this.getResumeConditional(post);
					Status s = twitter.updateStatus(post.getText());
					//----- System.out.println(s.getURLEntities());
					wasSent = true;

				} catch (Exception e) {
					wasSent = false;
					e.printStackTrace();
				}
			} else {
				try {

					twitter.updateStatus(post.getText());
					wasSent = true;

				} catch (Exception e) {
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

	public StreamMessage sendMessage(StreamMessage streamMessage, Profile profile) {

		try {

			TwitterIntegration twitterIntegration = new TwitterIntegration();

			Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());

			DirectMessage directMessage = twitter.sendDirectMessage(Long.valueOf(streamMessage.getIdConversation()), streamMessage.getMessage());

			streamMessage = MountDTO.mountMessageForTwitter(directMessage, streamMessage.getIdConversation());

		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return streamMessage;
	}
	
	public StreamConversations getConversations(Profile profile, StreamPostPaging streamPostPagingDirect, StreamPostPaging streamPostPagingSent) { 
		
		StreamConversations streamConversations = new StreamConversations();
		List<StreamConversation> streamConversationsList = new ArrayList<StreamConversation>();
		
		try {
			
			TwitterIntegration twitterIntegration = new TwitterIntegration();

			Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());
			
			/* Get/Paginate Direct Received Messages */
			Paging pagingDirect;
			ResponseList<DirectMessage> directMessages;
			
			if(streamPostPagingDirect.getMaxId() != null) { 
				
				pagingDirect = new Paging();
				pagingDirect.setCount(streamPostPagingDirect.getLimit());
				pagingDirect.setPage(streamPostPagingDirect.getPage());
				pagingDirect.setMaxId(streamPostPagingDirect.getMaxId());
				
				directMessages = twitter.getDirectMessages(pagingDirect);
				
				if(directMessages.size() > 0) {
					streamPostPagingDirect.setMaxId(directMessages.get(directMessages.size() - 1).getId());
					streamPostPagingDirect.setPage(pagingDirect.getPage() + 1);
				}
				
			} else {
				
				pagingDirect = new Paging(1, streamPostPagingDirect.getLimit());
				
				directMessages = twitter.getDirectMessages(pagingDirect);
				
				if(directMessages.size() > 0) {
					streamPostPagingDirect.setMaxId(directMessages.get(directMessages.size() - 1).getId());
					streamPostPagingDirect.setSinceId(directMessages.get(0).getId());
					streamPostPagingDirect.setPage(pagingDirect.getPage() + 1);
				}
			}
			
			streamConversations.setStreamPostPaging(streamPostPagingDirect);
			
			List<StreamMessage> streamMessagesAux = new ArrayList<StreamMessage>();
			Map<Long, StreamConversation> mapStreamConversations = new LinkedHashMap<Long, StreamConversation>(); 
			
			for(DirectMessage directMessage : directMessages) {
				
				StreamConversation streamConversation = new StreamConversation();
				streamConversation.setIdConversation(String.valueOf(directMessage.getSenderId()));
				streamConversation.setUnread(true);
				streamConversation.setUpdatedTime(directMessage.getCreatedAt().getTime());
				streamConversation.setUserId(String.valueOf(directMessage.getSenderId()));
				streamConversation.setUserName(directMessage.getSenderScreenName());
				streamConversation.setUserAvatar(directMessage.getSender().getProfileImageURLHttps());

				streamMessagesAux.add(MountDTO.mountMessageForTwitter(directMessage, streamConversation.getIdConversation()));
				
				mapStreamConversations.put(directMessage.getSenderId(), streamConversation);
			}
			
			/* Get/Paginate Sent Messages */
			Paging pagingSent;
			ResponseList<DirectMessage> sentMessages;
			
			if(streamPostPagingSent.getMaxId() != null) { 
				
				pagingSent = new Paging();
				pagingSent.setCount(streamPostPagingSent.getLimit());
				pagingSent.setPage(streamPostPagingSent.getPage());
				pagingSent.setMaxId(streamPostPagingSent.getMaxId());
				
				sentMessages = twitter.getSentDirectMessages(pagingSent);
				
				if(sentMessages.size() > 0) {
					streamPostPagingSent.setMaxId(sentMessages.get(sentMessages.size() - 1).getId());
					streamPostPagingSent.setPage(pagingSent.getPage() + 1);
				}
				
			} else {
				
				pagingSent = new Paging(1, streamPostPagingSent.getLimit());
				
				sentMessages = twitter.getSentDirectMessages(pagingSent);
				
				if(sentMessages.size() > 0) {
					streamPostPagingSent.setMaxId(sentMessages.get(sentMessages.size() - 1).getId());
					streamPostPagingSent.setSinceId(sentMessages.get(0).getId());
					streamPostPagingSent.setPage(pagingSent.getPage() + 1);
				}
			}
			
			streamConversations.setStreamPostPagingAux(streamPostPagingSent);
			
			for(DirectMessage sentMessage : sentMessages) {
				
				StreamConversation streamConversation = new StreamConversation();
				streamConversation.setIdConversation(String.valueOf(sentMessage.getRecipientId()));
				streamConversation.setUnread(true);
				streamConversation.setUpdatedTime(sentMessage.getCreatedAt().getTime());
				streamConversation.setUserId(String.valueOf(sentMessage.getRecipientId()));
				streamConversation.setUserName(sentMessage.getRecipientScreenName());
				streamConversation.setUserAvatar(sentMessage.getRecipient().getProfileImageURLHttps());

				streamMessagesAux.add(MountDTO.mountMessageForTwitter(sentMessage, streamConversation.getIdConversation()));
				
				mapStreamConversations.put(sentMessage.getRecipientId(), streamConversation);
			}
			
			// Organize all received/sent messages that belongs to a particular conversation.
			for(Map.Entry<Long, StreamConversation> streamConversationEntry : mapStreamConversations.entrySet()) {
				
				StreamMessages streamMessages = new StreamMessages();
				List<StreamMessage> messages = new ArrayList<StreamMessage>();
				
				for(StreamMessage streamMessage : streamMessagesAux) {
					if(streamConversationEntry.getKey().equals(Long.valueOf(streamMessage.getIdConversation()))) {
						messages.add(streamMessage);
					}
				}
				
				// Sort messages of a conversation by createdTime.
				Comparator<StreamMessage> comparatorMessages = (m2, m1) -> m2.getCreatedTime().compareTo(m1.getCreatedTime()); 
				messages.sort(comparatorMessages.reversed());
				
				streamMessages.setMessages(messages);
				
				streamConversationEntry.getValue().setStreamMessages(streamMessages);
				
				streamConversationsList.add(streamConversationEntry.getValue());
			}
			
			// Sort conversations by updatedTime.
			Comparator<StreamConversation> comparator = (c2, c1)->c2.getUpdatedTime().compareTo(c1.getUpdatedTime());
			streamConversationsList.sort(comparator.reversed());
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		streamConversations.setConversations(streamConversationsList);
		
		return streamConversations;
	}
	
	public void saveTwitterMessages(Profile profile) {
		
		MaintenanceConversationRemote conversationRemote = (MaintenanceConversationRemote) RecoverMaintenance.recoverMaintenance("Conversation");

		MaintenanceMessageRemote messageRemote = (MaintenanceMessageRemote) RecoverMaintenance.recoverMaintenance("Message");

		MaintenanceNotificationRemote notificationRemote = (MaintenanceNotificationRemote) RecoverMaintenance.recoverMaintenance("Notification");

		try {
		
			TwitterIntegration twitterIntegration = new TwitterIntegration();

			Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());

			ResponseList<DirectMessage> directMessages = twitter.getDirectMessages();

			for(DirectMessage directMessage : directMessages) {

				Conversation conversation = conversationRemote.getByNetworkConversationId(String.valueOf(directMessage.getSenderId()), profile.getIdProfile());

				if(conversation == null) {
					conversation = new Conversation();
					conversation.setNetworkConversationId(String.valueOf(directMessage.getSenderId()));
					conversation.setUserName(directMessage.getSenderScreenName());
					conversation.setUserId(String.valueOf(directMessage.getSenderId()));
					conversation.setProfile(profile);
					conversation.setUnread(true);
				}

				conversation.setUpdatedTime(directMessage.getCreatedAt().getTime());
				conversation.setUserAvatar(directMessage.getSender().getProfileImageURLHttps());

				try {
					conversation = conversationRemote.save(conversation);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				Message message = messageRemote.getByIdAndConversation(String.valueOf(directMessage.getId()), conversation.getIdConversation());

				if(message == null) {

					try {
						conversation = conversationRemote.getById(conversation.getIdConversation());
						conversation.setUpdatedTime(directMessage.getCreatedAt().getTime());
						conversation.setUnread(true);
						conversation = conversationRemote.save(conversation);
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					message = new Message();
					message.setNetworkMessageId(String.valueOf(directMessage.getId()));
					message.setConversation(conversation);
					message.setCreatedTime(directMessage.getCreatedAt().getTime());
					message.setMessage(directMessage.getText());
					message.setUserId(String.valueOf(directMessage.getSenderId()));
					message.setUserName(directMessage.getSenderScreenName());

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

			ResponseList<DirectMessage> sentMessages = twitter.getSentDirectMessages();

			for(DirectMessage sentMessage : sentMessages) {

				Conversation conversation = conversationRemote.getByNetworkConversationId(String.valueOf(sentMessage.getRecipientId()), profile.getIdProfile());

				if(conversation == null) {
					conversation = new Conversation();
					conversation.setNetworkConversationId(String.valueOf(sentMessage.getRecipientId()));
					conversation.setUserName(sentMessage.getRecipientScreenName());
					conversation.setUserId(String.valueOf(sentMessage.getRecipientId()));
					conversation.setProfile(profile);
					conversation.setUnread(true);
				}

				conversation.setUpdatedTime(sentMessage.getCreatedAt().getTime());

				try {
					conversation = conversationRemote.save(conversation);
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();
				} catch (Exception e1) {
					e1.printStackTrace();
				}

				Message message = messageRemote.getByIdAndConversation(String.valueOf(sentMessage.getId()), conversation.getIdConversation());

				if(message == null) {

					try {	
						conversation = conversationRemote.getById(conversation.getIdConversation());
						conversation.setUnread(true);
						conversation.setUpdatedTime(sentMessage.getCreatedAt().getTime());
						conversation = conversationRemote.save(conversation);
					} catch (IllegalArgumentException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					message = new Message();
					message.setConversation(conversation);
					message.setNetworkMessageId(String.valueOf(sentMessage.getId()));
					message.setCreatedTime(sentMessage.getCreatedAt().getTime());
					message.setMessage(sentMessage.getText());
					message.setUserId(String.valueOf(sentMessage.getSenderId()));
					message.setUserName(sentMessage.getSenderScreenName());

					try {
						message = messageRemote.save(message);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getResumeConditional(Post post) {

		if ( (post.getText().lastIndexOf(post.getLink()) == -1) || (post.getText().lastIndexOf(this.getLinkWithoutProtocol(post)) == -1) ) {

			post.setText(post.getText() + " " + post.getLink());
		}
	}

	public String getLinkWithoutProtocol(Post post) {

		String strTemp = post.getLink();
		if (strTemp.indexOf("http:") > -1) {
			strTemp = strTemp.replace("http://", "");
		} else if (strTemp.indexOf("https:") > -1) {
			strTemp = strTemp.replace("https://", "");
		}

		return strTemp;
	}
}