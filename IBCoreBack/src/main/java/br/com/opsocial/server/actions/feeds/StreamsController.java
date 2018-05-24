package br.com.opsocial.server.actions.feeds;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.exceptions.InstagramBadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.facebook.FacebookAPI;
import br.com.opsocial.server.utils.instagram.InstagramAPI;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import br.com.opsocial.server.utils.networksintegrations.InstagramIntegration;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;
import br.com.opsocial.server.utils.streams.ActionCommentAux;
import br.com.opsocial.server.utils.streams.ActionDeleteAux;
import br.com.opsocial.server.utils.streams.ActionLikeAux;
import br.com.opsocial.server.utils.streams.ActionShareAux;
import br.com.opsocial.server.utils.streams.StreamConversations;
import br.com.opsocial.server.utils.streams.StreamConversationsAux;
import br.com.opsocial.server.utils.streams.StreamFollower;
import br.com.opsocial.server.utils.streams.StreamFollowers;
import br.com.opsocial.server.utils.streams.StreamFollowersAux;
import br.com.opsocial.server.utils.streams.StreamMessage;
import br.com.opsocial.server.utils.streams.StreamMessageAux;
import br.com.opsocial.server.utils.streams.StreamMessages;
import br.com.opsocial.server.utils.streams.StreamPost;
import br.com.opsocial.server.utils.streams.StreamPostComment;
import br.com.opsocial.server.utils.streams.StreamPostCommentRepliesAux;
import br.com.opsocial.server.utils.streams.StreamPostComments;
import br.com.opsocial.server.utils.streams.StreamPostCommentsAux;
import br.com.opsocial.server.utils.streams.StreamPostsAux;
import br.com.opsocial.server.utils.tasks.ValidateTokensController;
import br.com.opsocial.server.utils.twitter.TwitterAPI;
import facebook4j.Facebook;
import facebook4j.RawAPIResponse;
import facebook4j.auth.AccessToken;
import twitter4j.Twitter;

@RestController
@RequestMapping("woopsocial")
public class StreamsController {

	@CrossOrigin
	@RequestMapping(value = "/streams/myposts",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> getMyPosts(@RequestBody StreamPostsAux streamPostsAux) throws Exception {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(streamPostsAux.getProfile().getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			streamPosts.addAll(new FacebookAPI().getMyPosts(profile, streamPostsAux.getStreamPostPaging()));
		} else if(profile.getType().equals(Profile.TWITTER)) {
			streamPosts.addAll(new TwitterAPI().getMyPosts(profile, streamPostsAux.getStreamPostPaging()));
		} else if(profile.getType().equals(Profile.INSTAGRAM)) {
			streamPosts.addAll(new InstagramAPI().getMyPosts(profile, streamPostsAux.getStreamPostPaging()));
		}
		
		List<Object> response = new ArrayList<Object>();
		response.add(streamPosts);
		response.add(streamPostsAux.getStreamPostPaging());

		return new ResponseEntity<List<Object>>(response, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/timeline",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> getTimelinePosts(@RequestBody StreamPostsAux streamPostsAux) throws Exception {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(streamPostsAux.getProfile().getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			streamPosts.addAll(new FacebookAPI().getTimelinePosts(profile, streamPostsAux.getStreamPostPaging()));
		} else if(profile.getType().equals(Profile.TWITTER)) {
			streamPosts.addAll(new TwitterAPI().getHomeTimelinePosts(profile, streamPostsAux.getStreamPostPaging()));
		}
		
		List<Object> response = new ArrayList<Object>();
		response.add(streamPosts);
		response.add(streamPostsAux.getStreamPostPaging());

		return new ResponseEntity<List<Object>>(response, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/mentions",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> getMentionsPosts(@RequestBody StreamPostsAux streamPostsAux) throws Exception {
		
		List<StreamPost> streamPosts = new ArrayList<StreamPost>();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(streamPostsAux.getProfile().getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			streamPosts.addAll(new FacebookAPI().getTaggedPosts(profile, streamPostsAux.getStreamPostPaging()));
		} else if(profile.getType().equals(Profile.TWITTER)) {
			streamPosts.addAll(new TwitterAPI().getMentionsPosts(profile, streamPostsAux.getStreamPostPaging()));
		}
		
		List<Object> response = new ArrayList<Object>();
		response.add(streamPosts);
		response.add(streamPostsAux.getStreamPostPaging());

		return new ResponseEntity<List<Object>>(response, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/conversations",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamConversations> getConversations(@RequestBody StreamConversationsAux streamConversationsAux) throws Exception {
		
		StreamConversations streamConversations = new StreamConversations();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(streamConversationsAux.getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			streamConversations = new FacebookAPI().getConversations(profile, streamConversationsAux.getStreamPostPaging());
		} else if(profile.getType().equals(Profile.TWITTER)) {
			streamConversations = new TwitterAPI().getConversations(profile, streamConversationsAux.getStreamPostPaging(), streamConversationsAux.getStreamPostPagingAux());
		}
		
		return new ResponseEntity<StreamConversations>(streamConversations, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/messages",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamMessages> getMessages(@RequestBody StreamMessageAux streamMessageAux) throws Exception {
		
		StreamMessages streamMessages = new StreamMessages();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(streamMessageAux.getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			streamMessages = new FacebookAPI().getMessages(streamMessageAux.getStreamConversation().getIdConversation(), streamMessageAux.getStreamPostPaging(), profile);
		}
		
		return new ResponseEntity<StreamMessages>(streamMessages, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/messages/send",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamMessage> sendMessage(@RequestBody StreamMessageAux streamMessageAux) throws Exception {
		
		StreamMessage streamMessage = new StreamMessage();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(streamMessageAux.getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			streamMessage = new FacebookAPI().sendMessage(streamMessageAux.getStreamMessage(), profile);
		} else if(profile.getType().equals(Profile.TWITTER)) {
			streamMessage = new TwitterAPI().sendMessage(streamMessageAux.getStreamMessage(), profile);
		}
		
		return new ResponseEntity<StreamMessage>(streamMessage, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/followers",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamFollowers> getFollowers(@RequestBody StreamFollowersAux streamFollowersAux) throws Exception {
		
		StreamFollowers streamFollowers = new StreamFollowers();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(streamFollowersAux.getIdProfile());
		
		if(profile.getType().equals(Profile.INSTAGRAM)) { 
			streamFollowers = new InstagramAPI().getFollowers(profile, streamFollowersAux.getStreamPostPaging());
		}
		
		return new ResponseEntity<StreamFollowers>(streamFollowers, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/followers/follow",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamFollower> follow(@RequestBody StreamFollower streamFollower) throws Exception {
		
		ResponseEntity<StreamFollower> responseEntity;
		Boolean followed = false;
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(streamFollower.getIdProfile());
		
		if(profile.getType().equals(Profile.INSTAGRAM)) { 
			followed = new InstagramAPI().follow(streamFollower.getFollowerId().toString(), profile);
		}
		
		if(followed) {
			responseEntity = new ResponseEntity<StreamFollower>(streamFollower, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<StreamFollower>(streamFollower, HttpStatus.FORBIDDEN);
		}
		
		return responseEntity;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/followers/unfollow",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamFollower> unfollow(@RequestBody StreamFollower streamFollower) throws Exception {
		
		ResponseEntity<StreamFollower> responseEntity;
		Boolean unfollowed = false;
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(streamFollower.getIdProfile());
		
		if(profile.getType().equals(Profile.INSTAGRAM)) { 
			unfollowed = new InstagramAPI().unfollow(streamFollower.getFollowerId().toString(), profile);
		}
		
		if(unfollowed) {
			responseEntity = new ResponseEntity<StreamFollower>(streamFollower, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<StreamFollower>(streamFollower, HttpStatus.FORBIDDEN);
		}
		
		return responseEntity;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/comments",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getComments(@RequestBody StreamPostCommentsAux streamPostCommentsAux) throws Exception {
		
		StreamPostComments streamPostComments = new StreamPostComments();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(streamPostCommentsAux.getIdProfile());
		
		if(streamPostCommentsAux.getStreamPost().getNetwork().equals(Profile.FACEBOOK_PAGE) || streamPostCommentsAux.getStreamPost().getNetwork().equals(Profile.FACEBOOK)) {
			streamPostComments = new FacebookAPI().getComments(streamPostCommentsAux.getStreamPost(), streamPostCommentsAux.getStreamPostPaging(), profile);
		} else if(profile.getType().equals(Profile.INSTAGRAM)) {
			streamPostComments = new InstagramAPI().getComments(streamPostCommentsAux.getStreamPost(), profile);
		}

		return new ResponseEntity<Object>(streamPostComments, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/comments/replies",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getCommentsReplies(@RequestBody StreamPostCommentRepliesAux streamPostCommentRepliesAux) throws Exception {
		
		StreamPostComments streamPostComments = new StreamPostComments();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(streamPostCommentRepliesAux.getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			streamPostComments = new FacebookAPI().getCommentReplies(streamPostCommentRepliesAux.getStreamPostComment(), 
					streamPostCommentRepliesAux.getStreamPostPaging(), profile);
		}

		return new ResponseEntity<Object>(streamPostComments, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/posts/comment",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> commentPost(@RequestBody ActionCommentAux actionCommentAux) throws Exception {
		
		StreamPostComment streamPostComment = new StreamPostComment();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(actionCommentAux.getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			
			String objectId;
			
			objectId = actionCommentAux.getStreamPost().getPostId();
						
			streamPostComment = new FacebookAPI().comment(objectId, actionCommentAux.getStreamPostComment(), profile);
			
		} else if(profile.getType().equals(Profile.TWITTER)) {
			streamPostComment = new TwitterAPI().reply(actionCommentAux.getStreamPost().getPostId(), actionCommentAux.getStreamPostComment(), profile);
		} else if(profile.getType().equals(Profile.INSTAGRAM)) {
			streamPostComment = new InstagramAPI().comment(actionCommentAux.getStreamPost().getPostId(), actionCommentAux.getStreamPostComment(), profile);
		}

		return new ResponseEntity<Object>(streamPostComment, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/comments/reply",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> replyComment(@RequestBody ActionCommentAux actionCommentAux) throws Exception {
		
		StreamPostComment streamPostComment = new StreamPostComment();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(actionCommentAux.getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			streamPostComment = new FacebookAPI().comment(actionCommentAux.getCommentId(), actionCommentAux.getStreamPostComment(), profile);
		}
		
		return new ResponseEntity<Object>(streamPostComment, HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/comments/delete",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deleteComment(@RequestBody ActionDeleteAux actionDeleteAux) throws Exception {
		
		ResponseEntity<Object> responseEntity;
		Boolean deleted = false;

		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(actionDeleteAux.getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			
			try {
				
				AccessToken accessToken = new AccessToken(profile.getToken(), null);
				Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

				deleted = facebook.deleteComment(actionDeleteAux.getCommentId());
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if(profile.getType().equals(Profile.INSTAGRAM)) {
			
			try {
				
				AccessToken accessToken = new AccessToken(profile.getToken(), null);
				facebook4j.Facebook facebook = new FacebookIntegration().getFacebook(accessToken);
				
				RawAPIResponse res = facebook.callDeleteAPI(actionDeleteAux.getCommentId());
				
				JsonParser parser = new JsonParser();			
				JsonObject jsonResponse = parser.parse(res.asString()).getAsJsonObject();
				
				if(jsonResponse.has("success")) {
					deleted = jsonResponse.get("success").getAsBoolean();
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		if(deleted) {
			responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
		
		return responseEntity;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/posts/delete",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> deletePost(@RequestBody ActionDeleteAux actionDeleteAux) throws Exception {
		
		ResponseEntity<Object> responseEntity;
		Boolean deleted = false;

		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(actionDeleteAux.getIdProfile());

		if (profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {

			try {
				
				AccessToken accessToken = new AccessToken(profile.getToken(), null);
				Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

				deleted = facebook.deletePost(actionDeleteAux.getPostId());
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (profile.getType().equals(Profile.TWITTER)) {
			
			try {
				
				TwitterIntegration twitterIntegration = new TwitterIntegration();
				Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());
				
				twitter.destroyStatus(Long.valueOf(actionDeleteAux.getPostId()));
				deleted = true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(deleted) {
			responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
		
		return responseEntity;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/posts/share",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> sharePost(@RequestBody ActionShareAux actionShareAux) throws Exception {
		
		ResponseEntity<Object> responseEntity;
		Boolean shared = false;

		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(actionShareAux.getIdProfile());

		if (profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {

			try {
				
				AccessToken accessToken = new AccessToken(profile.getToken(), null);
				Facebook facebook = new FacebookIntegration().getFacebook(accessToken);

				facebook.postLink(profile.getNetworkId(), new URL(actionShareAux.getStreamPost().getLink()),
						actionShareAux.getMessage());
				shared = true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (profile.getType().equals(Profile.TWITTER)) {
			
			try {
				
				TwitterIntegration twitterIntegration = new TwitterIntegration();
				Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());
				
				twitter.retweetStatus(Long.valueOf(actionShareAux.getStreamPost().getPostId()));
				shared = true;
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(shared) {
			responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
		
		return responseEntity;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/posts/edit",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamPost> editPost(@RequestBody ActionShareAux actionShareAux) throws Exception {
		
		ResponseEntity<StreamPost> responseEntity;
		Boolean edited = false;
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(actionShareAux.getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) { 
			edited = new FacebookAPI().editPost(actionShareAux.getStreamPost(), profile);
		}
		
		if(edited) {
			responseEntity = new ResponseEntity<StreamPost>(HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<StreamPost>(HttpStatus.FORBIDDEN);
		}
		
		return responseEntity;
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/comments/like",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamPostComment> likeComment(@RequestBody ActionLikeAux actionLikeAux) throws Exception {
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(actionLikeAux.getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			
			FacebookAPI facebookGraphAPI = new FacebookAPI();
			
			if(actionLikeAux.getStreamPostComment().getUserLikes()) {
				actionLikeAux.getStreamPostComment().setUserLikes(!facebookGraphAPI.unlikePost(actionLikeAux.getStreamPostComment().getCommentId(), profile.getToken()));
			} else {
				actionLikeAux.getStreamPostComment().setUserLikes(facebookGraphAPI.likePost(actionLikeAux.getStreamPostComment().getCommentId(), profile.getToken()));
			}				
		}
		
		return new ResponseEntity<StreamPostComment>(actionLikeAux.getStreamPostComment(), HttpStatus.OK);
	}
	
	@CrossOrigin
	@RequestMapping(value = "/streams/posts/like",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<StreamPost> likePost(@RequestBody ActionLikeAux actionLikeAux) throws Exception {
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(actionLikeAux.getIdProfile());
		
		if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
			
			String token; 
			
			if (profile.getType().equals(Profile.FACEBOOK)) {
				List<Profile> pages = profileRemote.getChildrenByProfile(profile.getIdProfile());
				
				token = pages.get(0).getToken();
			} else {
				token = profile.getToken();
			}
			
			FacebookAPI facebookGraphAPI = new FacebookAPI();
			
			String objectId;

			if (actionLikeAux.getStreamPost().getObjectId() != null) {
				objectId = actionLikeAux.getStreamPost().getObjectId();
			} else {
				objectId = actionLikeAux.getStreamPost().getPostId();
			}
			
			if(actionLikeAux.getStreamPost().getLiked()) {
				actionLikeAux.getStreamPost().setLiked(!facebookGraphAPI.unlikePost(objectId, token));
			} else {
				actionLikeAux.getStreamPost().setLiked(facebookGraphAPI.likePost(objectId, token));
			}			
			
		} else if(profile.getType().equals(Profile.TWITTER)) {
			
			TwitterIntegration twitterIntegration = new TwitterIntegration();
			Twitter twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());
			
			try {
				
				if(actionLikeAux.getStreamPost().getLiked()) {
					twitter.destroyFavorite(Long.valueOf(actionLikeAux.getStreamPost().getPostId()));
					actionLikeAux.getStreamPost().setLiked(false);
				} else {
					twitter.createFavorite(Long.valueOf(actionLikeAux.getStreamPost().getPostId()));
					actionLikeAux.getStreamPost().setLiked(true);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else if(profile.getType().equals(Profile.INSTAGRAM)) {
			
			InstagramIntegration instagramIntegration = new InstagramIntegration();
			
			Instagram instagram = instagramIntegration.getInstagram(profile);
			
			try {
				
				if(actionLikeAux.getStreamPost().getLiked()) {
					instagram.deleteUserLike(actionLikeAux.getStreamPost().getPostId());
					actionLikeAux.getStreamPost().setLiked(false);
				} else {
					instagram.setUserLike(actionLikeAux.getStreamPost().getPostId());
					actionLikeAux.getStreamPost().setLiked(true);
				}
				
			} catch (InstagramBadRequestException e) {
				ValidateTokensController.validatingTokens(profile.getAccount());
				e.printStackTrace();
			}
			
		}
		
		return new ResponseEntity<StreamPost>(actionLikeAux.getStreamPost(), HttpStatus.OK);
	}
	
	
}
