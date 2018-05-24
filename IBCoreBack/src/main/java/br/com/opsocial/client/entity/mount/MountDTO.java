package br.com.opsocial.client.entity.mount;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.opsocial.client.entity.application.MonitoringDTO;
import br.com.opsocial.client.entity.application.UsersDTO;
import br.com.opsocial.client.entity.blogs.BlogPostMonitoringDTO;
import br.com.opsocial.client.entity.facebook.FacePostMonitoringDTO;
import br.com.opsocial.client.entity.facebook.FacebookPostDTO;
import br.com.opsocial.client.entity.googleplus.GooglePlusPostMonitoringDTO;
import br.com.opsocial.client.entity.instagram.InstagramPostDTO;
import br.com.opsocial.client.entity.instagram.InstagramPostMonitoringDTO;
import br.com.opsocial.client.entity.mailbox.ConversationDTO;
import br.com.opsocial.client.entity.mailbox.MessageDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringInfluentialUserDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringPostTagDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringTagDTO;
import br.com.opsocial.client.entity.monitoring.SampleDTO;
import br.com.opsocial.client.entity.monitoring.TwitterPostMonitoringDTO;
import br.com.opsocial.client.entity.news.NewsPostMonitoringDTO;
import br.com.opsocial.client.entity.reclameaqui.ReclameAquiPostMonitoringDTO;
import br.com.opsocial.client.entity.report.ReportLogoDTO;
import br.com.opsocial.client.entity.report.facebook.FaceReportPostCommentDTO;
import br.com.opsocial.client.entity.report.facebook.FaceReportPostDTO;
import br.com.opsocial.client.entity.report.facebook.FacebookInfluentialUserDTO;
import br.com.opsocial.client.entity.report.facebook.FanPageGrowth;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookCityDTO;
import br.com.opsocial.client.entity.report.instagram.FilterLikesCommentsDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramEngagedFollowerDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramReportPostDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramTopPostTagDTO;
import br.com.opsocial.client.entity.report.twitter.TwitterFollowerDTO;
import br.com.opsocial.client.entity.report.twitter.TwitterReportDTO;
import br.com.opsocial.client.entity.report.twitter.TwitterReportStatusDTO;
import br.com.opsocial.client.entity.socialnetworks.facebook.AttachmentDTO;
import br.com.opsocial.client.entity.socialnetworks.facebook.FromDTO;
import br.com.opsocial.client.entity.socialnetworks.facebook.ImageDTO;
import br.com.opsocial.client.entity.socialnetworks.facebook.MediaDTO;
import br.com.opsocial.client.entity.twitter.TrendingTopicDTO;
import br.com.opsocial.client.entity.youtube.YoutubePostMonitoringDTO;
import br.com.opsocial.ejb.das.MaintenanceTwitterStatusRemote;
import br.com.opsocial.ejb.entity.application.Anniversarie;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.blogs.BlogPostMonitoring;
import br.com.opsocial.ejb.entity.facebook.FacePostMonitoring;
import br.com.opsocial.ejb.entity.google.GooglePlusPostMonitoring;
import br.com.opsocial.ejb.entity.instagram.InstagramEngagedFollower;
import br.com.opsocial.ejb.entity.instagram.InstagramPost;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;
import br.com.opsocial.ejb.entity.instagram.InstagramReportPost;
import br.com.opsocial.ejb.entity.instagram.InstagramTopPostTag;
import br.com.opsocial.ejb.entity.mailbox.Conversation;
import br.com.opsocial.ejb.entity.monitoring.ColorTag;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringInfluentialUser;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostLocation;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostTag;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTag;
import br.com.opsocial.ejb.entity.monitoring.Sample;
import br.com.opsocial.ejb.entity.news.NewsPostMonitoring;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPostMonitoring;
import br.com.opsocial.ejb.entity.report.AgeGroup;
import br.com.opsocial.ejb.entity.report.FaceReportPost;
import br.com.opsocial.ejb.entity.report.FaceReportPostComment;
import br.com.opsocial.ejb.entity.report.FacebookInfluentialUser;
import br.com.opsocial.ejb.entity.report.FilterLikesComments;
import br.com.opsocial.ejb.entity.report.PostEngagementStats;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;
import br.com.opsocial.ejb.entity.report.ReportLogo;
import br.com.opsocial.ejb.entity.report.SumByDate;
import br.com.opsocial.ejb.entity.report.TwitterFollower;
import br.com.opsocial.ejb.entity.report.TwitterReport;
import br.com.opsocial.ejb.entity.report.TwitterStatus;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.ejb.entity.sets.Sets;
import br.com.opsocial.ejb.entity.twitter.TrendingTopic;
import br.com.opsocial.ejb.entity.twitter.TwitterPostMonitoring;
import br.com.opsocial.ejb.entity.youtube.YoutubePostMonitoring;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.facebook.entities.IdNameEntity;
import br.com.opsocial.server.utils.facebook.entities.Message;
import br.com.opsocial.server.utils.facebook.entities.MessageData;
import br.com.opsocial.server.utils.instagram.InstagramAPI;
import br.com.opsocial.server.utils.instagram.entities.CommentFeed;
import br.com.opsocial.server.utils.instagram.entities.Follower;
import br.com.opsocial.server.utils.instagram.entities.Media;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;
import br.com.opsocial.server.utils.streams.LinkSummary;
import br.com.opsocial.server.utils.streams.StreamConversation;
import br.com.opsocial.server.utils.streams.StreamFollower;
import br.com.opsocial.server.utils.streams.StreamMessage;
import br.com.opsocial.server.utils.streams.StreamMessageTag;
import br.com.opsocial.server.utils.streams.StreamMessages;
import br.com.opsocial.server.utils.streams.StreamPhoto;
import br.com.opsocial.server.utils.streams.StreamPost;
import br.com.opsocial.server.utils.streams.StreamPostComment;
import br.com.opsocial.server.utils.streams.StreamPostComments;
import br.com.opsocial.server.utils.streams.StreamPostPaging;
import facebook4j.Comment;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Like;
import facebook4j.PagableList;
import facebook4j.Post.Attachment;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.Tag;
import twitter4j.DirectMessage;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class MountDTO {

	public static Object mountSet(Sets setDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Post mountPost(Sets setDTO, Post post) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Post mountPost(Post postDTO, Object mountSet) {
		// TODO Auto-generated method stub
		return null;
	}

	public static MonitoringInfluentialUserDTO mountMonitoringInfluentialUser(
			MonitoringInfluentialUser monitoringPositiveUserNew) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TwitterReportDTO mountTwitterReport(TwitterReport twitterReport) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<TwitterFollowerDTO> mountTwitterFollower(List<TwitterFollower> mostInfluentialFollowers) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<TwitterReportStatusDTO> mountTwitterReportStatus(List<TwitterStatus> twitterStatus, Profile profile) {
		
		MaintenanceTwitterStatusRemote twitterStatusRemote = (MaintenanceTwitterStatusRemote)
				RecoverMaintenance.recoverMaintenance("TwitterStatus");
		
		TwitterIntegration twitterIntegration = new TwitterIntegration();
		Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());
		
		List<TwitterReportStatusDTO> twitterReportStatusDTOs = new ArrayList<TwitterReportStatusDTO>();
		
		for(TwitterStatus status : twitterStatus) {
			
			TwitterReportStatusDTO twitterReportStatusDTO = new TwitterReportStatusDTO();
			twitterReportStatusDTO.setIdProfile(status.getIdProfile());
			twitterReportStatusDTO.setIdStatus(status.getIdStatus());
			twitterReportStatusDTO.setCreatedAt(new Date(status.getCreatedAt() * 1000L));
			
			if(status.getText().equals("")) {
				
				try {
					
					status.setText(twitter.showStatus(Long.valueOf(status.getIdStatus())).getText());
					
					twitterStatusRemote.save(status);
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (TwitterException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			if(status.getMediaUrl() == null) {
				
				try {
					
					MediaEntity[] mediaEntities = twitter.showStatus(Long.valueOf(status.getIdStatus())).getMediaEntities(); 
					
					for (MediaEntity mediaEntity : mediaEntities) {
						if(mediaEntity.getType().equals("photo")) {
							
							status.setMediaUrl(mediaEntity.getMediaURLHttps());
							
							twitterStatusRemote.save(status);
							
							break;
						}	
					}
					
				} catch (NumberFormatException e) {
					e.printStackTrace();
				} catch (TwitterException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
			twitterReportStatusDTO.setText(status.getText());
			twitterReportStatusDTO.setStatusUrl("https://twitter.com/" + profile.getScreenName() + "/status/" + status.getIdStatus());
			twitterReportStatusDTO.setVersion(status.getVersion());
			twitterReportStatusDTO.setMediaUrl(status.getMediaUrl());
			
			twitterReportStatusDTOs.add(twitterReportStatusDTO);
		}
		
		return twitterReportStatusDTOs;
	}

	public static List<FaceReportPostCommentDTO> mountFaceReportPostComment(List<FaceReportPostComment> comments) {
		// TODO Auto-generated method stub
		return null;
	}

	public static FaceReportPostDTO mountFaceReportPost(FaceReportPost faceReportPost) {
		
		FaceReportPostDTO faceReportPostDTO = new FaceReportPostDTO();
		
		faceReportPostDTO.setPostId(faceReportPost.getPostId());
		faceReportPostDTO.setPageId(faceReportPost.getPageId());
		faceReportPostDTO.setMessage(faceReportPost.getMessage());
		faceReportPostDTO.setType(faceReportPost.getType());
		faceReportPostDTO.setPermalink(faceReportPost.getPermalink());
		faceReportPostDTO.setCreatedTime(new Date(faceReportPost.getCreatedTime() * 1000L));
		faceReportPostDTO.setLikes(faceReportPost.getLikes());
		faceReportPostDTO.setComments(faceReportPost.getComments());
		faceReportPostDTO.setShares(faceReportPost.getShares());
		faceReportPostDTO.setLastDateInsights(faceReportPost.getLastDateInsights());
		faceReportPostDTO.setPhotoSrc(faceReportPost.getPhotoSrc());
		faceReportPostDTO.setPhotoHeight(faceReportPost.getPhotoHeight());
		faceReportPostDTO.setPhotoWidth(faceReportPost.getPhotoWidth());
		faceReportPostDTO.setEngagement(faceReportPost.getEngagement());
		faceReportPostDTO.setVersion(faceReportPost.getVersion());
		
		return faceReportPostDTO;
	}

	public static SumByDate mountSumByDate(SumByDate sumByDate) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object mountPostEngagementStats(PostEngagementStats engagementStats) {
		// TODO Auto-generated method stub
		return null;
	}

	public static FanPageGrowth mountFanPageGrowth(FanPageGrowth pageGrowth) {
		// TODO Auto-generated method stub
		return null;
	}

	public static AgeGroup mountAgeGroup(AgeGroup majorAgeGroupReachedUsers) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ReportFacebookCityDTO mountReportFacebookCity(ReportFacebookLocation reportFacebookCity) {
		// TODO Auto-generated method stub
		return null;
	}

	public static FacebookInfluentialUserDTO mountFacebookInfluentialUser(FacebookInfluentialUser facebookInfluentialUser) {
		
		FacebookInfluentialUserDTO facebookInfluentialUserDTO = new FacebookInfluentialUserDTO();
		
		facebookInfluentialUserDTO.setComments(facebookInfluentialUser.getComments());
		facebookInfluentialUserDTO.setImageProfile(FacebookIntegration.GRAPH_API_V2_10 + facebookInfluentialUser.getUserId() + "/picture");
		facebookInfluentialUserDTO.setLikes(facebookInfluentialUser.getLikes());
		facebookInfluentialUserDTO.setName(facebookInfluentialUser.getName());
		facebookInfluentialUserDTO.setPageId(facebookInfluentialUser.getPageId());
		facebookInfluentialUserDTO.setUserId(facebookInfluentialUser.getUserId());
		facebookInfluentialUserDTO.setVersion(facebookInfluentialUser.getVersion());
		facebookInfluentialUserDTO.setProfileUrl(UtilFunctions.FACEBOOK_URL + facebookInfluentialUser.getUserId());
		
		return facebookInfluentialUserDTO;
	}
	
	// WoopSocial 
	public static List<StreamPostComment> mountStreamPostCommentsForInstagram(CommentFeed commentFeed) {
		
		List<StreamPostComment> streamPostComments = new ArrayList<StreamPostComment>();
		
		for(br.com.opsocial.server.utils.instagram.entities.Comment comment : commentFeed.getData()) {
			streamPostComments.add(mountStreamPostCommentForInstagram(comment));			
		}
		
		return streamPostComments;
	}
	
	// OpSocial
//	public static List<StreamPostComment> mountStreamPostCommentsForInstagram(List<CommentData> commentData) {
//		
//		List<StreamPostComment> streamPostComments = new ArrayList<StreamPostComment>();
//		
//		for(CommentData comment : commentData) {
//			streamPostComments.add(mountStreamPostCommentForInstagram(comment));			
//		}
//		
//		return streamPostComments;
//	}
	
	public static List<StreamFollower> mountFollowersForInstagram(List<Follower> followers, Long idProfile) {
		
		List<StreamFollower> streamFollowers = new ArrayList<StreamFollower>();
		
		for(Follower follower : followers) {
			
			StreamFollower streamFollower = new StreamFollower();
			streamFollower.setIdProfile(idProfile);
			streamFollower.setFollowerId(follower.getPk());
			streamFollower.setFullName(follower.getFullName()); 
			streamFollower.setUserName(follower.getUsername());
			streamFollower.setProfilePicture(follower.getProfilePicUrl());
			
			streamFollowers.add(streamFollower);
		}
		
		return streamFollowers;
	}
	
	// OpSocial
//	public static List<StreamFollower> mountFollowersForInstagram(List<UserFeedData> followers, Long idProfile) {
//		
//		List<StreamFollower> streamFollowers = new ArrayList<StreamFollower>();
//		
//		for(UserFeedData follower : followers) {
//			
//			StreamFollower streamFollower = new StreamFollower();
//			streamFollower.setIdProfile(idProfile);
//			streamFollower.setFollowerId(Long.valueOf(follower.getId()));
//			streamFollower.setFullName(follower.getFullName()); 
//			streamFollower.setUserName(follower.getUserName());
//			streamFollower.setProfilePicture(follower.getProfilePictureUrl());
//			
//			streamFollowers.add(streamFollower);
//		}
//		
//		return streamFollowers;
//	}
	
	public static List<StreamConversation> mountConversationsForFacebook(List<br.com.opsocial.server.utils.facebook.entities.Conversation> conversations, Profile profile) {
		
		List<StreamConversation> streamConversations = new ArrayList<StreamConversation>();
			
		for(br.com.opsocial.server.utils.facebook.entities.Conversation conversation : conversations) {
		
			StreamConversation streamConversation = new StreamConversation();
			streamConversation.setIdConversation(conversation.getId());
			streamConversation.setMessageCount(conversation.getMessageCount());
			if(conversation.getUnreadCount() == null) {
				streamConversation.setUnread(false);
			} else {				
				streamConversation.setUnread(conversation.getUnreadCount() > 0 ? true : false);
			}
			streamConversation.setUpdatedTime(ZonedDateTime.parse(conversation.getUpdatedTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")).toEpochSecond());
			
			for(IdNameEntity sender : conversation.getSenders().getData()) {
				
				if(!sender.getId().equals(profile.getNetworkId())) {
					streamConversation.setUserId(sender.getId());
					streamConversation.setUserName(sender.getName());
				}
			}
			
			streamConversation.setUserAvatar("https://graph.facebook.com/v2.10/" + streamConversation.getUserId() + "/picture/");
			
			streamConversation.setStreamMessages(mountMessagesForFacebook(conversation.getMessages(), conversation.getId()));
			
			streamConversations.add(streamConversation);
		}
		
		return streamConversations;
	}
	
	public static StreamMessages mountMessagesForFacebook(MessageData messagesData, String idConversation) {
		
		StreamMessages streamMessages = new StreamMessages();
		List<StreamMessage> messages = new ArrayList<StreamMessage>();
		
		for(br.com.opsocial.server.utils.facebook.entities.Message message : messagesData.getData()) {
			messages.add(mountMessageForFacebook(message, idConversation));
		}
		
		streamMessages.setMessages(messages);
		
		StreamPostPaging streamPostPaging = new StreamPostPaging(20);
		if(messagesData.getPaging() != null && messagesData.getPaging().getNext() != null) {
			streamPostPaging.setAfter(messagesData.getPaging().getCursors().getAfter());
			streamPostPaging.setBefore(messagesData.getPaging().getCursors().getBefore());
		} else {
			streamPostPaging.setAfter(null);
		}
		
		streamMessages.setStreamPostPaging(streamPostPaging);
		
		return streamMessages;
	}
	
	public static StreamMessage mountMessageForFacebook(Message message, String idConversation) {
		
		StreamMessage streamMessage = new StreamMessage();
		streamMessage.setIdMessage(message.getId());
		streamMessage.setMessage(message.getMessage());
		streamMessage.setCreatedTime(ZonedDateTime.parse(message.getCreatedTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")).toEpochSecond());
		streamMessage.setUserId(message.getFrom().getId()); 
		streamMessage.setUserName(message.getFrom().getName());
		streamMessage.setIdConversation(idConversation);
		
		if(message.getAttachments() != null) {
			streamMessage.setAttachment(message.getAttachments().getData().get(0));
		}
		
		if(message.getShares() != null) {
			streamMessage.setShare(message.getShares().getData().get(0));
		}		
		
		return streamMessage;
	}
	
	public static StreamMessage mountMessageForTwitter(DirectMessage directMessage, String idConversation) {
		
		StreamMessage streamMessage = new StreamMessage();
		streamMessage.setIdMessage(String.valueOf(directMessage.getId()));
		streamMessage.setCreatedTime(directMessage.getCreatedAt().getTime());
		streamMessage.setMessage(directMessage.getText());
		streamMessage.setUserId(String.valueOf(directMessage.getSenderId()));
		streamMessage.setUserName(directMessage.getSenderScreenName());
		streamMessage.setIdConversation(idConversation);
		
		return streamMessage;
	}
	
	// OpSocial
	public static StreamPostComment mountStreamPostCommentForInstagram(br.com.opsocial.server.utils.instagram.entities.Comment comment) {
		
		StreamPostComment streamPostComment = new StreamPostComment();
		streamPostComment.setCommentId(comment.getId());
		streamPostComment.setMessage(comment.getText());
		streamPostComment.setCreatedTime(ZonedDateTime.parse(comment.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")).toEpochSecond());
		
		FromDTO fromDTO = new FromDTO();
		fromDTO.setName(comment.getUser().getUsername());
		fromDTO.setProfilePicture(comment.getUser().getProfilePictureUrl());
		
		streamPostComment.setFrom(fromDTO);
		
		return streamPostComment;
	}
	
	public static List<StreamPost> mountStreamPostsForInstagram(List<Media> mediaFeedData, Profile profile) {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		for(Media media : mediaFeedData) {
			
			StreamPost streamPost = new StreamPost();
			
			streamPost.setPostId(media.getId());
			streamPost.setProfileName(profile.getScreenName());
			streamPost.setScreenName(profile.getScreenName());
			streamPost.setNetwork(Profile.INSTAGRAM);
			streamPost.setText(media.getCaption());
			streamPost.setLink(media.getPermalink());
			streamPost.setCreatedTime(ZonedDateTime.parse(media.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")).toEpochSecond());
			streamPost.setProfileId(profile.getNetworkId());
			
			streamPost.setProfilePicture(profile.getAvatar());
			streamPost.setPictureUrl(media.getMediaUrl());
			streamPost.setPictureWidth(0);
			streamPost.setPictureHeight(0);
			streamPost.setLikesCount(media.getLikeCount().intValue());
			streamPost.setCommentsCount(media.getCommentsCount().intValue());
		
			if(media.getMediaType().equals("CAROUSEL_ALBUM")) {
				streamPost.setType(FacebookPostDTO.ALBUM);
				streamPost.setStreamPhotos(new InstagramAPI().getChildren(streamPost, profile));
			}
			
			streamPosts.add(streamPost);
		}
		
		return streamPosts;
	}
	
//	// OpSocial
//	public static List<StreamPost> mountStreamPostsForInstagram(List<MediaFeedData> mediaFeedData) {
//		
//		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
//		
//		for(MediaFeedData feed : mediaFeedData) {
//			
//			StreamPost streamPost = new StreamPost();
//			
//			streamPost.setPostId(feed.getId().substring(0,feed.getId().indexOf("_")));
//			streamPost.setProfileName(feed.getUser().getFullName());
//			streamPost.setScreenName(feed.getUser().getUserName());
//			streamPost.setNetwork(Profile.INSTAGRAM);
//			streamPost.setText(feed.getCaption() != null ? feed.getCaption().getText() : null);
//			streamPost.setLink(feed.getLink());
//			streamPost.setCreatedTime(Long.valueOf(feed.getCreatedTime()));
//			
//			streamPost.setProfilePicture(feed.getUser().getProfilePictureUrl());
//			streamPost.setPictureUrl(feed.getImages().getLowResolution().getImageUrl());
//			streamPost.setPictureWidth(feed.getImages().getLowResolution().getImageWidth());
//			streamPost.setPictureHeight(feed.getImages().getLowResolution().getImageHeight());
//			streamPost.setLikesCount(feed.getLikes().getCount());
//			streamPost.setCommentsCount(feed.getComments().getCount());
//			
//			streamPosts.add(streamPost);
//		}
//		
//		return streamPosts;
//	}
	
	public static List<StreamPost> mountStreamPostsForTwitter(twitter4j.ResponseList<Status> statuses, Profile profile) {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		for(Status status : statuses) {
			
			StreamPost streamPost = new StreamPost();
			
			streamPost.setPostId(String.valueOf(status.getId()));
			streamPost.setText(status.getText());
			streamPost.setLink("https://twitter.com/" + status.getUser().getScreenName() + "/status/" + status.getId());
			streamPost.setScreenName(status.getUser().getScreenName());
			streamPost.setProfileName(status.getUser().getName());
			streamPost.setProfileLink("https://twitter.com/" + status.getUser().getScreenName());
			streamPost.setProfilePicture(status.getUser().getProfileImageURLHttps().toString());
			streamPost.setNetwork(Profile.TWITTER);
			streamPost.setCreatedTime(status.getCreatedAt().getTime() / 1000L);
			streamPost.setSharesCount(status.getRetweetCount());
			streamPost.setLiked(status.isFavorited());
			streamPost.setProfileId(profile.getNetworkId());
			
			if (status.isRetweet()) {
				streamPost.setLikesCount(status.getRetweetedStatus().getFavoriteCount());
			} else {
				streamPost.setLikesCount(status.getFavoriteCount());
			}
			
			if(status.getMediaEntities().length > 0) {
				
				if(status.getMediaEntities().length > 1) {
					
					streamPost.setType(FacebookPostDTO.ALBUM);
					
					List<StreamPhoto> streamPhotos = new ArrayList<StreamPhoto>();
					
					for(MediaEntity mediaEntity : status.getMediaEntities()) {
						
						StreamPhoto streamPhoto = new StreamPhoto();
						streamPhoto.setUrl(mediaEntity.getMediaURLHttps());
						streamPhoto.setType(mediaEntity.getType());
						
						streamPhotos.add(streamPhoto);
					}
					
					streamPost.setStreamPhotos(streamPhotos);
					
				} else {
					
					streamPost.setType(FacebookPostDTO.PHOTO);
					
					streamPost.setPictureUrl(status.getMediaEntities()[0].getMediaURLHttps());
					streamPost.setPictureWidth(status.getMediaEntities()[0].getSizes() != null && status.getMediaEntities()[0].getSizes().get(1) != null ?
							status.getMediaEntities()[0].getSizes().get(1).getWidth() : null);
					streamPost.setPictureHeight(status.getMediaEntities()[0].getSizes() != null && status.getMediaEntities()[0].getSizes().get(1) != null ?
							status.getMediaEntities()[0].getSizes().get(1).getHeight() : null);
				}
			}

			streamPosts.add(streamPost);
		}
		
		return streamPosts;
	}
	
	public static List<StreamPostComment> mountStreamPostCommentForFacebook(ResponseList<Comment> comments, Facebook facebook) {
		
		List<StreamPostComment> streamPostComments = new ArrayList<StreamPostComment>();

		for(Comment comment : comments) {
			streamPostComments.add(mountPostCommentForFacebook(comment, false, facebook));
		}
		
		return streamPostComments;
	}
	
	public static StreamPostComment mountPostCommentForFacebook(Comment comment, Boolean isReply, Facebook facebook) {
		
		StreamPostComment streamPostComment = new StreamPostComment();
		streamPostComment.setCommentId(comment.getId());
		streamPostComment.setMessage(comment.getMessage());
		streamPostComment.setCreatedTime(comment.getCreatedTime().getTime() / 1000L);
		streamPostComment.setLikeCount(comment.getLikeCount());
		streamPostComment.setCommentCount(comment.getCommentCount());
		streamPostComment.setUserLikes(comment.isUserLikes());
		
		FromDTO fromDTO = new FromDTO();
		fromDTO.setName(comment.getFrom().getName());
		fromDTO.setIdFrom(comment.getFrom().getId());
		
		try {
			
			facebook4j.User user = facebook.getUser(comment.getFrom().getId(), new Reading().fields("picture{url}"));
			fromDTO.setProfilePicture(user.getPicture().getURL().toString());
			
		} catch (FacebookException e) {
			e.printStackTrace();
		}
		
		streamPostComment.setFrom(fromDTO);
		
		if(comment.getAttachment() != null) {
			
			AttachmentDTO attachmentDTO = new AttachmentDTO();
			attachmentDTO.setDescription(comment.getAttachment().getDescription());
			attachmentDTO.setTitle(comment.getAttachment().getTitle());
			attachmentDTO.setType(comment.getAttachment().getType());
			attachmentDTO.setUrl(comment.getAttachment().getUrl());
			
			ImageDTO imageDTO = new ImageDTO();
			imageDTO.setSrc(comment.getAttachment().getMedia().getImage().getSource().toString());
			imageDTO.setWidth(comment.getAttachment().getMedia().getImage().getWidth());
			imageDTO.setHeight(comment.getAttachment().getMedia().getImage().getHeight());
			
			MediaDTO mediaDTO = new MediaDTO(imageDTO);
			mediaDTO.setVideoSrc(comment.getAttachment().getTarget().getUrl());
			
			attachmentDTO.setMedia(mediaDTO);
			
			streamPostComment.setAttachment(attachmentDTO);
		}
		
		if(!isReply) {
			streamPostComment.setReplies(MountDTO.mountStreamPostCommentsForFacebook(comment.getComments(), facebook, true));
		}
		
		return streamPostComment; 
	}
	
	public static StreamPostComments mountStreamPostCommentsForFacebook(PagableList<Comment> comments, Facebook facebook, Boolean isReply) {
		
		StreamPostComments streamPostComments = new StreamPostComments();
		List<StreamPostComment> commentsReplies = new ArrayList<StreamPostComment>();
		
		for(Comment comment : comments) {
			commentsReplies.add(mountPostCommentForFacebook(comment, isReply, facebook));
		}
		
		streamPostComments.setComments(commentsReplies);
		
		StreamPostPaging streamPostPaging = new StreamPostPaging(20);
		if(comments.getPaging() != null && !comments.isEmpty()) {
			streamPostPaging.setAfter(comments.getPaging().getCursors().getAfter()); 
		}
		
		streamPostComments.setPaging(streamPostPaging);
		
		return streamPostComments;
	}
	
	public static List<StreamPost> mountStreamPostsForFacebook(ResponseList<facebook4j.Post> posts, Facebook facebook, Profile profile) {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		for(facebook4j.Post post : posts) {
			
			try {
				
				StreamPost streamPost = new StreamPost();
				streamPost.setPostId(post.getId());
				streamPost.setObjectId(post.getObjectId());
 				streamPost.setText(post.getMessage());
				streamPost.setType(post.getType());
				streamPost.setNetwork(Profile.FACEBOOK_PAGE);
				streamPost.setCreatedTime(post.getCreatedTime().getTime() / 1000L);
				streamPost.setProfileName(post.getFrom().getName());
				streamPost.setProfileId(post.getFrom().getId());
				
				facebook4j.User postUser = facebook.getUser(post.getFrom().getId(), new Reading().fields("link,picture"));
				
				streamPost.setProfilePicture(postUser.getPicture().getURL().toString());
				streamPost.setProfileLink(postUser.getLink().toString());
				streamPost.setStreamPostComments(mountStreamPostCommentsForFacebook(post.getComments(), facebook, false));
				
				if(post.getType().equals(FacebookPostDTO.PHOTO)) {
					streamPost.setLink(UtilFunctions.FACEBOOK_URL + post.getObjectId());
				} else {
					streamPost.setLink(UtilFunctions.FACEBOOK_URL + post.getId());
				}
				
				LinkSummary linkSummary = new LinkSummary();
				linkSummary.setTitle(post.getName());
				linkSummary.setDescription(post.getDescription());
				
				if(post.getMessageTags() != null) {
					List<StreamMessageTag> streamMessageTags = new ArrayList<StreamMessageTag>();
					for (Tag tag : post.getMessageTags()) {
						StreamMessageTag streamMessageTag = new StreamMessageTag();
						
						streamMessageTag.setId(tag.getId());
						streamMessageTag.setName(tag.getName());
						streamMessageTag.setType(tag.getType());
						streamMessageTag.setOffset(tag.getOffset());
						streamMessageTag.setLength(tag.getLength());
						
						streamMessageTags.add(streamMessageTag);
					}
					
					streamPost.setStreamMessageTags(streamMessageTags);
				}
				
				if(post.getPicture() != null) {
					
					if(post.getType().equals(FacebookPostDTO.PHOTO)) {
						
						if(!post.getAttachments().isEmpty()) {
							
							if(post.getAttachments().get(0).getType().equals(FacebookPostDTO.ALBUM)) {
								
								streamPost.setType(FacebookPostDTO.ALBUM);
								
								List<StreamPhoto> streamPhotos = new ArrayList<StreamPhoto>();
								
								for(Attachment attachment : post.getAttachments().get(0).getSubattachments()) {
									
									StreamPhoto streamPhoto = new StreamPhoto();
									streamPhoto.setTitle(attachment.getTitle());
									streamPhoto.setType(attachment.getType());
									streamPhoto.setUrl(attachment.getUrl());
									
									streamPhotos.add(streamPhoto);
								}
								
								streamPost.setStreamPhotos(streamPhotos);
								
							} else {
								streamPost.setPictureUrl(post.getAttachments().get(0).getUrl());
							}	
						}
						
					} else if(post.getType().equals(FacebookPostDTO.LINK)) {
						
						if(!post.getAttachments().isEmpty()) {
							linkSummary.setImage(post.getAttachments().get(0).getUrl());
						} else {
							linkSummary.setImage(post.getPicture().toString());
						}
						
						if(post.getLink() != null) {
							linkSummary.setLink(post.getLink().toString());
						} else {
							if(post.getActions() != null && !post.getActions().isEmpty()) {
								linkSummary.setLink(post.getActions().get(0).getLink());
							}
						}
					}
				}
				
				if(post.getType().equals(FacebookPostDTO.VIDEO)) {
					
					streamPost.setVideoUrl(post.getSource().toString());
					
					if(!post.getAttachments().isEmpty() && post.getAttachments().get(0).getType().equals(AttachmentDTO.GIF)) {
						streamPost.setType(FacebookPostDTO.GIF);
					}
					
					if(!post.getAttachments().isEmpty()) {
						streamPost.setPictureUrl(post.getAttachments().get(0).getUrl());
					}

				}
				
				streamPost.setLinkSummary(linkSummary);
				streamPost.setCommentsCount(post.getComments().getSummary().getTotalCount());

				if(post.getSharesCount() == null) {
					streamPost.setSharesCount(0);
				} else {
					streamPost.setSharesCount(post.getSharesCount());
				}
				
				if(post.getLikes() != null) {
					
					streamPost.setLikesCount(post.getLikes().getSummary().getTotalCount());
					
					for(Like like : post.getLikes()) {
						if(like.getName().equals(profile.getScreenName())) {
							streamPost.setLiked(true);
							break;
						}
					}
					
				} else {
					streamPost.setLikesCount(0);
				}
				
				streamPosts.add(streamPost);
				
			} catch (FacebookException e) {
				e.printStackTrace();
			}
		}
		
		return streamPosts;
	}

	public static List<InstagramTopPostTagDTO> mountInstagramTopPostTag(List<InstagramTopPostTag> instagramTopPostTags,
			long l) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<FilterLikesCommentsDTO> mountFilterLikesComments(List<FilterLikesComments> filterLikesComments) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<InstagramReportPostDTO> mountInstagramReportPost(List<InstagramReportPost> postsWithMoreLikes) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<InstagramEngagedFollowerDTO> mountInstagramEngagedFollower(
			List<InstagramEngagedFollower> updateInstagramEngagedFollowers) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<TwitterReportStatusDTO> mountTwitterReportStatus(List<TwitterStatus> twitterStatusMostEngaged) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object mountReportLogo(ReportLogo reportLogo) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ReportLogo mountReportLogo(ReportLogoDTO reportLogoDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public static MonitoringDTO mountMonitoring(Monitoring monitoring) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<MonitoringTagDTO> mountMonitoringTag(List<MonitoringTag> listByMonitoring) {
		// TODO Auto-generated method stub
		return null;
	}

	public static FacePostMonitoringDTO mountFacePostMonitoring(FacePostMonitoring byComposedId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<MonitoringPostTag> mountMonitoringPostTag(List<MonitoringPostTag> listByMonitoringPost) {
		// TODO Auto-generated method stub
		return null;
	}

	public static TwitterPostMonitoringDTO mountTwitterPostMonitoring(TwitterPostMonitoring byComposedId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static GooglePlusPostMonitoringDTO mountGooglePlusPostMonitoring(GooglePlusPostMonitoring byComposedId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static BlogPostMonitoringDTO mountBlogPostMonitoring(BlogPostMonitoring byComposedId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static NewsPostMonitoringDTO mountNewsPostMonitoring(NewsPostMonitoring byComposedId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ReclameAquiPostMonitoringDTO mountReclameAquiPostMonitoring(ReclameAquiPostMonitoring byComposedId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static InstagramPostMonitoringDTO mountInstagramPostMonitoring(InstagramPostMonitoring byComposedId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static YoutubePostMonitoringDTO mountYoutubePostMonitoring(YoutubePostMonitoring byComposedId) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Sample mountSample(SampleDTO sampleDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Monitoring mountMonitoring(MonitoringDTO monitoringDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Profile mountProfile(Profile group) {
		// TODO Auto-generated method stub
		return null;
	}

	public static MonitoringPostLocation mountMonitoringPostLocation(MonitoringPostLocation monitoringPostLocation) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object mountColorTag(List<ColorTag> colorTags) {
		// TODO Auto-generated method stub
		return null;
	}

	public static MonitoringTag mountMonitoringTag(MonitoringTagDTO monitoringTagDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object mountMonitoringPostTag(MonitoringPostTag monitoringPostTag) {
		// TODO Auto-generated method stub
		return null;
	}

	public static MonitoringPostTag mountMonitoringPostTag(MonitoringPostTagDTO monitoringPostTagDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Object mountMonitoringTag(MonitoringTag monitoringTag) {
		// TODO Auto-generated method stub
		return null;
	}

	public static ConversationDTO mountConversation(Conversation notread) {
		// TODO Auto-generated method stub
		return null;
	}

	public static MessageDTO mountMessage(br.com.opsocial.ejb.entity.mailbox.Message message) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Conversation mountConversation(ConversationDTO conversationDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	public static InstagramPostDTO mountInstagramPost(InstagramPost updateFeed) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<Profile> mountProfile(List<Profile> profiles) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Anniversarie mountAnniversarie(Anniversarie anniversarie) {
		// TODO Auto-generated method stub
		return null;
	}

	public static List<TrendingTopicDTO> mountTrendingTopic(List<TrendingTopic> mostRecentTrendingTopics) {
		// TODO Auto-generated method stub
		return null;
	}

	public static UsersDTO mountUser(User byId) {
		// TODO Auto-generated method stub
		return null;
	}
}

