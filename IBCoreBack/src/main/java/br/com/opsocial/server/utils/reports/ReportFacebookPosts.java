package br.com.opsocial.server.utils.reports;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;

import com.face4j.facebook.enums.HttpClientType;
import com.face4j.facebook.exception.FacebookException;
import com.face4j.facebook.http.APICallerFactory;
import com.face4j.facebook.http.APICallerInterface;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import br.com.opsocial.ejb.das.MaintenanceFaceReportPostCommentRemote;
import br.com.opsocial.ejb.das.MaintenanceFaceReportPostRemote;
import br.com.opsocial.ejb.das.MaintenanceFacebookInfluentialUserRemote;
import br.com.opsocial.ejb.das.MaintenanceFacebookPostInsightRemote;
import br.com.opsocial.ejb.entity.application.idclass.FacebookInfluentialUserId;
import br.com.opsocial.ejb.entity.report.FaceReportPost;
import br.com.opsocial.ejb.entity.report.FaceReportPostComment;
import br.com.opsocial.ejb.entity.report.FacebookInfluentialUser;
import br.com.opsocial.ejb.entity.report.FacebookPostInsight;
import br.com.opsocial.ejb.entity.report.PagePost;
import br.com.opsocial.ejb.entity.socialnetworks.facebook.Insights;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import facebook4j.Facebook;
import facebook4j.Ordering;
import facebook4j.Paging;
import facebook4j.Photo;
import facebook4j.Post;
import facebook4j.RawAPIResponse;
import facebook4j.Reading;
import facebook4j.ResponseList;
import facebook4j.Video;
import facebook4j.auth.AccessToken;

public class ReportFacebookPosts {

	private String pageId;
	private String accessToken;
	private String postInsightsMetrics;

	private Long dateUntil;
	private Long dateFrom;

	private static final Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	private APICallerInterface caller = null;

	List<PagePost> pagePosts;

	private MaintenanceFaceReportPostRemote faceReportPostRemote;
	private MaintenanceFaceReportPostCommentRemote faceReportPostCommentRemote;
	private MaintenanceFacebookInfluentialUserRemote facebookInfluentialUserRemote;
	private MaintenanceFacebookPostInsightRemote facebookPostInsightRemote;
	
	private static final String PERIOD_LIFETIME = "lifetime";

	public static final String POST_INSIGHTS_URL = "https://graph.facebook.com/v2.10/idPost/insights/";

	public static final String POST_COMMENTS_USERS = "https://graph.facebook.com/v2.10/idPost/comments";
	public static final String POST_LIKES_USERS = "https://graph.facebook.com/v2.10/idPost/likes";
	
	public static final String POST_STORIES_BY_ACTION_TYPE_LIKE = "post_stories_by_action_type_like";
	public static final String POST_STORIES_BY_ACTION_TYPE_COMMENT = "post_stories_by_action_type_comment";
	public static final String POST_STORIES_BY_ACTION_TYPE_SHARE = "post_stories_by_action_type_share";
	
	public static final String POST_CONSUMPTIONS_BY_TYPE_OTHER_CLICKS = "post_consumptions_by_type_other_clicks";
	public static final String POST_CONSUMPTIONS_BY_TYPE_PHOTO_VIEW = "post_consumptions_by_type_photo_view";
	public static final String POST_CONSUMPTIONS_BY_TYPE_LINK_CLICKS = "post_consumptions_by_type_link_clicks";
	public static final String POST_CONSUMPTIONS_BY_TYPE_VIDEO_PLAY = "post_consumptions_by_type_video_play";
	
	public static final String POST_AUDIO_LISTEN_COUNT = "post_audio_listen_count";
	public static final String POST_AUDIO_LISTEN_TIME = "post_audio_listen_time";
	public static final String POST_CONSUMPTIONS = "post_consumptions";
	public static final String POST_CONSUMPTIONS_BY_TYPE = "post_consumptions_by_type";
	public static final String POST_CONSUMPTIONS_BY_TYPE_UNIQUE = "post_consumptions_by_type_unique";
	public static final String POST_CONSUMPTIONS_UNIQUE = "post_consumptions_unique";
	public static final String POST_ENGAGED_FAN = "post_engaged_fan";
	public static final String POST_ENGAGED_USERS = "post_engaged_users";
	public static final String POST_FAN_REACH = "post_fan_reach";
	public static final String POST_IMPRESSIONS = "post_impressions";
	public static final String POST_IMPRESSIONS_BY_PAID_NON_PAID = "post_impressions_by_paid_non_paid";
	public static final String POST_IMPRESSIONS_BY_PAID_NON_PAID_UNIQUE = "post_impressions_by_paid_non_paid_unique";
	public static final String POST_IMPRESSIONS_BY_STORY_TYPE = "post_impressions_by_story_type";
	public static final String POST_IMPRESSIONS_BY_STORY_TYPE_UNIQUE = "post_impressions_by_story_type_unique";
	public static final String POST_IMPRESSIONS_FAN = "post_impressions_fan";
	public static final String POST_IMPRESSIONS_FAN_PAID = "post_impressions_fan_paid";
	public static final String POST_IMPRESSIONS_FAN_PAID_UNIQUE = "post_impressions_fan_paid_unique";
	public static final String POST_IMPRESSIONS_ORGANIC = "post_impressions_organic";
	public static final String POST_IMPRESSIONS_ORGANIC_UNIQUE = "post_impressions_organic_unique";
	public static final String POST_IMPRESSIONS_PAID = "post_impressions_paid";
	public static final String POST_IMPRESSIONS_PAID_UNIQUE = "post_impressions_paid_unique";
	public static final String POST_IMPRESSIONS_UNIQUE = "post_impressions_unique";
	public static final String POST_IMPRESSIONS_VIRAL = "post_impressions_viral";
	public static final String POST_IMPRESSIONS_VIRAL_UNIQUE = "post_impressions_viral_unique";
	public static final String POST_INTERESTS_ACTION_BY_TYPE = "post_interests_action_by_type";
	public static final String POST_INTERESTS_ACTION_BY_TYPE_UNIQUE = "post_interests_action_by_type_unique";
	public static final String POST_INTERESTS_CONSUMPTIONS_BY_TYPE = "post_interests_consumptions_by_type";
	public static final String POST_INTERESTS_CONSUMPTIONS_BY_TYPE_UNIQUE = "post_interests_consumptions_by_type_unique";
	public static final String POST_NEGATIVE_FEEDBACK = "post_negative_feedback";
	public static final String POST_NEGATIVE_FEEDBACK_BY_TYPE = "post_negative_feedback_by_type";
	public static final String POST_NEGATIVE_FEEDBACK_BY_TYPE_UNIQUE = "post_negative_feedback_by_type_unique";
	public static final String POST_NEGATIVE_FEEDBACK_UNIQUE = "post_negative_feedback_unique";
	public static final String POST_REACTIONS_ANGER_TOTAL = "post_reactions_anger_total";
	public static final String POST_REACTIONS_HAHA_TOTAL = "post_reactions_haha_total";
	public static final String POST_REACTIONS_LIKE_TOTAL = "post_reactions_like_total";
	public static final String POST_REACTIONS_LOVE_TOTAL = "post_reactions_love_total";
	public static final String POST_REACTIONS_SORRY_TOTAL = "post_reactions_sorry_total";
	public static final String POST_REACTIONS_WOW_TOTAL = "post_reactions_wow_total";
	public static final String POST_STORIES = "post_stories";
	public static final String POST_STORIES_BY_ACTION_TYPE = "post_stories_by_action_type";
	public static final String POST_STORY_ADDS = "post_story_adds";
	public static final String POST_STORY_ADDS_BY_ACTION_TYPE = "post_story_adds_by_action_type";
	public static final String POST_STORY_ADDS_BY_ACTION_TYPE_UNIQUE = "post_story_adds_by_action_type_unique";
	public static final String POST_STORY_ADDS_UNIQUE = "post_story_adds_unique";
	public static final String POST_STORYTELLERS = "post_storytellers";
	public static final String POST_STORYTELLERS_BY_ACTION_TYPE = "post_storytellers_by_action_type";
	public static final String POST_VIDEO_AVG_TIME_WATCHED = "post_video_avg_time_watched";
	public static final String POST_VIDEO_COMPLETE_VIEWS_30S = "post_video_complete_views_30s";
	public static final String POST_VIDEO_COMPLETE_VIEWS_30S_AUTOPLAYED = "post_video_complete_views_30s_autoplayed";
	public static final String POST_VIDEO_COMPLETE_VIEWS_30S_CLICKED_TO_PLAY = "post_video_complete_views_30s_clicked_to_play";
	public static final String POST_VIDEO_COMPLETE_VIEWS_30S_ORGANIC = "post_video_complete_views_30s_organic";
	public static final String POST_VIDEO_COMPLETE_VIEWS_30S_PAID = "post_video_complete_views_30s_paid";
	public static final String POST_VIDEO_COMPLETE_VIEWS_30S_UNIQUE = "post_video_complete_views_30s_unique";
	public static final String POST_VIDEO_COMPLETE_VIEWS_ORGANIC = "post_video_complete_views_organic";
	public static final String POST_VIDEO_COMPLETE_VIEWS_ORGANIC_UNIQUE = "post_video_complete_views_organic_unique";
	public static final String POST_VIDEO_COMPLETE_VIEWS_PAID = "post_video_complete_views_paid";
	public static final String POST_VIDEO_COMPLETE_VIEWS_PAID_UNIQUE = "post_video_complete_views_paid_unique";
	public static final String POST_VIDEO_LENGTH = "post_video_length";
	public static final String POST_VIDEO_RETENTION_GRAPH = "post_video_retention_graph";
	public static final String POST_VIDEO_RETENTION_GRAPH_AUTOPLAYED = "post_video_retention_graph_autoplayed";
	public static final String POST_VIDEO_RETENTION_GRAPH_CLICKED_TO_PLAY = "post_video_retention_graph_clicked_to_play";
	public static final String POST_VIDEO_VIEW_TIME = "post_video_view_time";
	public static final String POST_VIDEO_VIEW_TIME_BY_AGE_BUCKET_AND_GENDER = "post_video_view_time_by_age_bucket_and_gender";
	public static final String POST_VIDEO_VIEW_TIME_BY_COUNTRY_ID = "post_video_view_time_by_country_id";
	public static final String POST_VIDEO_VIEW_TIME_BY_DISTRIBUTION_TYPE = "post_video_view_time_by_distribution_type";
	public static final String POST_VIDEO_VIEW_TIME_BY_REGION_ID = "post_video_view_time_by_region_id";
	public static final String POST_VIDEO_VIEW_TIME_ORGANIC = "post_video_view_time_organic";
	public static final String POST_VIDEO_VIEW_TIME_PAID = "post_video_view_time_paid";
	public static final String POST_VIDEO_VIEWS = "post_video_views";
	public static final String POST_VIDEO_VIEWS_10S = "post_video_views_10s";
	public static final String POST_VIDEO_VIEWS_10S_AUTOPLAYED = "post_video_views_10s_autoplayed";
	public static final String POST_VIDEO_VIEWS_10S_CLICKED_TO_PLAY = "post_video_views_10s_clicked_to_play";
	public static final String POST_VIDEO_VIEWS_10S_ORGANIC = "post_video_views_10s_organic";
	public static final String POST_VIDEO_VIEWS_10S_PAID = "post_video_views_10s_paid";
	public static final String POST_VIDEO_VIEWS_10S_SOUND_ON = "post_video_views_10s_sound_on";
	public static final String POST_VIDEO_VIEWS_10S_UNIQUE = "post_video_views_10s_unique";
	public static final String POST_VIDEO_VIEWS_AUTOPLAYED = "post_video_views_autoplayed";
	public static final String POST_VIDEO_VIEWS_BY_DISTRIBUTION_TYPE = "post_video_views_by_distribution_type";
	public static final String POST_VIDEO_VIEWS_CLICKED_TO_PLAY = "post_video_views_clicked_to_play";
	public static final String POST_VIDEO_VIEWS_ORGANIC = "post_video_views_organic";
	public static final String POST_VIDEO_VIEWS_ORGANIC_UNIQUE = "post_video_views_organic_unique";
	public static final String POST_VIDEO_VIEWS_PAID = "post_video_views_paid";
	public static final String POST_VIDEO_VIEWS_PAID_UNIQUE = "post_video_views_paid_unique";
	public static final String POST_VIDEO_VIEWS_SOUND_ON = "post_video_views_sound_on";
	public static final String POST_VIDEO_VIEWS_UNIQUE = "post_video_views_unique";
	
	public static final Integer POSTS_INSIGHTS_PERIOD = 30;
	public static final Integer POSTS_COMMENTS_PERIOD = 10;
	public static final Integer POST_COMMENTS_LIMIT = 100;

	public static final String POST_FIELDS = "id,message,created_time,type,object_id,shares,likes.summary(true),comments.summary(true)";
	public static final String POST_COMMENTS_FIELDS = "id,comment_count,created_time,from,like_count,message,attachment";
	public static final String POST_PHOTO_FIELDS = "link,height,width,picture";
	public static final String POST_VIDEO_FIELDS = "embed_html,embeddable,icon,permalink_url,picture,source";
	
	public static final String POST_TYPE_PHOTO = "photo";
	public static final String POST_TYPE_STATUS = "status";
	public static final String POST_TYPE_LINK = "link";
	public static final String POST_TYPE_VIDEO = "video";

	public final static String PHOTOS_POSTED = "247";
	public final static String STATUS_UPDATE = "46";
	public final static String LINK_POSTED = "80";
	public final static String VIDEO_POSTED = "128";
	public final static String OFFER_POSTED = "22";
	
	public ReportFacebookPosts(String accessToken, String pageId) {

		this.pageId = pageId;		
		this.accessToken = accessToken;

		this.faceReportPostRemote = (MaintenanceFaceReportPostRemote) RecoverMaintenance.recoverMaintenance("FaceReportPost");
		this.faceReportPostCommentRemote = (MaintenanceFaceReportPostCommentRemote) RecoverMaintenance.recoverMaintenance("FaceReportPostComment");
		this.facebookInfluentialUserRemote = (MaintenanceFacebookInfluentialUserRemote) RecoverMaintenance.recoverMaintenance("FacebookInfluentialUser");
		this.facebookPostInsightRemote = (MaintenanceFacebookPostInsightRemote) RecoverMaintenance.recoverMaintenance("FacebookPostInsight");
		
		this.prepareInsightsMetricsForPosts();
	}
	
	private void prepareInsightsMetricsForPosts() {
		
		StringBuilder builder = new StringBuilder();
		builder.append(POST_AUDIO_LISTEN_COUNT + ",");
		builder.append(POST_AUDIO_LISTEN_TIME + ",");
		builder.append(POST_IMPRESSIONS + ",");
		builder.append(POST_CONSUMPTIONS + ",");
		builder.append(POST_CONSUMPTIONS_BY_TYPE + ",");
		builder.append(POST_CONSUMPTIONS_BY_TYPE_UNIQUE + ",");
		builder.append(POST_CONSUMPTIONS_UNIQUE + ",");
		builder.append(POST_ENGAGED_FAN + ",");
		builder.append(POST_ENGAGED_USERS + ",");
		builder.append(POST_FAN_REACH + ",");
		builder.append(POST_IMPRESSIONS_BY_PAID_NON_PAID + ",");
		builder.append(POST_IMPRESSIONS_BY_PAID_NON_PAID_UNIQUE + ",");
		builder.append(POST_IMPRESSIONS_BY_STORY_TYPE + ",");
		builder.append(POST_IMPRESSIONS_BY_STORY_TYPE_UNIQUE + ",");
		builder.append(POST_IMPRESSIONS_FAN + ",");
		builder.append(POST_IMPRESSIONS_FAN_PAID + ",");
		builder.append(POST_IMPRESSIONS_FAN_PAID_UNIQUE + ",");
		builder.append(POST_IMPRESSIONS_ORGANIC + ",");
		builder.append(POST_IMPRESSIONS_ORGANIC_UNIQUE + ",");
		builder.append(POST_IMPRESSIONS_PAID + ",");
		builder.append(POST_IMPRESSIONS_PAID_UNIQUE + ",");
		builder.append(POST_IMPRESSIONS_UNIQUE + ",");
		builder.append(POST_IMPRESSIONS_VIRAL + ",");
		builder.append(POST_IMPRESSIONS_VIRAL_UNIQUE + ",");
		builder.append(POST_INTERESTS_ACTION_BY_TYPE + ",");
		builder.append(POST_INTERESTS_ACTION_BY_TYPE_UNIQUE + ",");
		builder.append(POST_INTERESTS_CONSUMPTIONS_BY_TYPE + ",");
		builder.append(POST_INTERESTS_CONSUMPTIONS_BY_TYPE_UNIQUE + ",");
		builder.append(POST_NEGATIVE_FEEDBACK + ",");
		builder.append(POST_NEGATIVE_FEEDBACK_BY_TYPE + ",");
		builder.append(POST_NEGATIVE_FEEDBACK_BY_TYPE_UNIQUE + ",");
		builder.append(POST_NEGATIVE_FEEDBACK_UNIQUE + ",");
		builder.append(POST_REACTIONS_ANGER_TOTAL + ",");
		builder.append(POST_REACTIONS_HAHA_TOTAL + ",");
		builder.append(POST_REACTIONS_LIKE_TOTAL + ",");
		builder.append(POST_REACTIONS_LOVE_TOTAL + ",");
		builder.append(POST_REACTIONS_SORRY_TOTAL + ",");
		builder.append(POST_REACTIONS_WOW_TOTAL + ",");
		builder.append(POST_STORIES + ",");
		builder.append(POST_STORIES_BY_ACTION_TYPE + ",");
		builder.append(POST_STORY_ADDS + ",");
		builder.append(POST_STORY_ADDS_BY_ACTION_TYPE + ",");
		builder.append(POST_STORY_ADDS_BY_ACTION_TYPE_UNIQUE + ",");
		builder.append(POST_STORY_ADDS_UNIQUE + ",");
		builder.append(POST_STORYTELLERS + ",");
		builder.append(POST_STORYTELLERS_BY_ACTION_TYPE + ",");
		builder.append(POST_VIDEO_AVG_TIME_WATCHED + ",");
		builder.append(POST_VIDEO_COMPLETE_VIEWS_30S + ",");
		builder.append(POST_VIDEO_COMPLETE_VIEWS_30S_AUTOPLAYED + ",");
		builder.append(POST_VIDEO_COMPLETE_VIEWS_30S_CLICKED_TO_PLAY + ",");
		builder.append(POST_VIDEO_COMPLETE_VIEWS_30S_ORGANIC + ",");
		builder.append(POST_VIDEO_COMPLETE_VIEWS_30S_PAID + ",");
		builder.append(POST_VIDEO_COMPLETE_VIEWS_30S_UNIQUE + ",");
		builder.append(POST_VIDEO_COMPLETE_VIEWS_ORGANIC + ",");
		builder.append(POST_VIDEO_COMPLETE_VIEWS_ORGANIC_UNIQUE + ",");
		builder.append(POST_VIDEO_COMPLETE_VIEWS_PAID + ",");
		builder.append(POST_VIDEO_COMPLETE_VIEWS_PAID_UNIQUE + ",");
		builder.append(POST_VIDEO_LENGTH + ",");
		builder.append(POST_VIDEO_RETENTION_GRAPH + ",");
		builder.append(POST_VIDEO_RETENTION_GRAPH_AUTOPLAYED + ",");
		builder.append(POST_VIDEO_RETENTION_GRAPH_CLICKED_TO_PLAY + ",");
		builder.append(POST_VIDEO_VIEW_TIME + ",");
		builder.append(POST_VIDEO_VIEW_TIME_BY_AGE_BUCKET_AND_GENDER + ",");
		builder.append(POST_VIDEO_VIEW_TIME_BY_COUNTRY_ID + ",");
		builder.append(POST_VIDEO_VIEW_TIME_BY_DISTRIBUTION_TYPE + ",");
		builder.append(POST_VIDEO_VIEW_TIME_BY_REGION_ID + ",");
		builder.append(POST_VIDEO_VIEW_TIME_ORGANIC + ",");
		builder.append(POST_VIDEO_VIEW_TIME_PAID + ",");
		builder.append(POST_VIDEO_VIEWS + ",");
		builder.append(POST_VIDEO_VIEWS_10S + ",");
		builder.append(POST_VIDEO_VIEWS_10S_AUTOPLAYED + ",");
		builder.append(POST_VIDEO_VIEWS_10S_CLICKED_TO_PLAY + ",");
		builder.append(POST_VIDEO_VIEWS_10S_ORGANIC + ",");
		builder.append(POST_VIDEO_VIEWS_10S_PAID + ",");
		builder.append(POST_VIDEO_VIEWS_10S_SOUND_ON + ",");
		builder.append(POST_VIDEO_VIEWS_10S_UNIQUE + ",");
		builder.append(POST_VIDEO_VIEWS_AUTOPLAYED + ",");
		builder.append(POST_VIDEO_VIEWS_BY_DISTRIBUTION_TYPE + ",");
		builder.append(POST_VIDEO_VIEWS_CLICKED_TO_PLAY + ",");
		builder.append(POST_VIDEO_VIEWS_ORGANIC + ",");
		builder.append(POST_VIDEO_VIEWS_ORGANIC_UNIQUE + ",");
		builder.append(POST_VIDEO_VIEWS_PAID + ",");
		builder.append(POST_VIDEO_VIEWS_PAID_UNIQUE + ",");
		builder.append(POST_VIDEO_VIEWS_SOUND_ON + ",");
		builder.append(POST_VIDEO_VIEWS_UNIQUE);
		
		postInsightsMetrics = builder.toString();
	}
	
	public void updatePostsInsights() {
		
		Calendar untilDate = new GregorianCalendar();
		untilDate.set(Calendar.DAY_OF_MONTH,untilDate.get(Calendar.DAY_OF_MONTH) - 4);
		Calendar fromDate = new GregorianCalendar(untilDate.get(Calendar.YEAR),
				untilDate.get(Calendar.MONTH),
				untilDate.get(Calendar.DAY_OF_MONTH));
		fromDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - (POSTS_INSIGHTS_PERIOD - 1));

		this.dateUntil  = UtilFunctions.getDateForFacebookReport(untilDate);
		this.dateFrom = UtilFunctions.getDateForFacebookReport(fromDate);
		
		List<FaceReportPost> faceReportPosts = faceReportPostRemote.getEntitiesByPageId(pageId, dateFrom, dateUntil);
		
		for(FaceReportPost faceReportPost : faceReportPosts) {
			getPostsInsights(faceReportPost.getPostId());
		}
		
	}
	
	public void updatePostsComments() {

		Calendar untilDate = new GregorianCalendar();
		untilDate.set(Calendar.DAY_OF_MONTH,untilDate.get(Calendar.DAY_OF_MONTH) - 3);
		Calendar fromDate = new GregorianCalendar(untilDate.get(Calendar.YEAR),
				untilDate.get(Calendar.MONTH),
				untilDate.get(Calendar.DAY_OF_MONTH));
		fromDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - (POSTS_COMMENTS_PERIOD - 1));

		this.dateUntil  = UtilFunctions.getDateForFacebookReport(untilDate);
		this.dateFrom = UtilFunctions.getDateForFacebookReport(fromDate);

		List<FaceReportPost> faceReportPosts = faceReportPostRemote.getEntitiesByPageId(pageId, dateFrom, dateUntil);

		for(FaceReportPost faceReportPost : faceReportPosts) {
			updatePostComments(faceReportPost.getPostId());
		}

	}

	public void getPosts() {
		
		FacebookIntegration facebookIntegration = new FacebookIntegration();
		Facebook facebook = facebookIntegration.getFacebook(new AccessToken(accessToken));
		
		try {
			
			ResponseList<Post> posts = null;
			
			do {	
				
				if(posts == null) {
					
					posts = facebook.getPosts(pageId, new Reading().fields(POST_FIELDS).
							since(dateFrom.toString()).until(dateUntil.toString()).
							limit(25).order(Ordering.REVERSE_CHRONOLOGICAL));
					
				} else {
					
					Paging<Post> paging = posts.getPaging();
					posts = facebook.fetchNext(paging);
				}

				for(Post post : posts) {
					
					try {
						
						FaceReportPost faceReportPost = new FaceReportPost();

						faceReportPost.setPostId(post.getId());
						faceReportPost.setPageId(pageId);
						faceReportPost.setCreatedTime(post.getCreatedTime().getTime() / 1000L);
						faceReportPost.setMessage(post.getMessage());

						faceReportPost.setType(getPostType(post.getType()));
						
						if(post.getType().equals(POST_TYPE_PHOTO)) {
							faceReportPost.setPermalink(UtilFunctions.FACEBOOK_URL + post.getObjectId());
						} else {
							faceReportPost.setPermalink(UtilFunctions.FACEBOOK_URL + post.getId());
						}
						
						faceReportPost.setComments(post.getComments().getSummary().getTotalCount());
						faceReportPost.setLikes(post.getLikes().getSummary().getTotalCount());

						if(post.getSharesCount() == null) {
							faceReportPost.setShares(0);
						} else {
							faceReportPost.setShares(post.getSharesCount());
						}

						faceReportPost.setLastDateInsights(new GregorianCalendar().getTimeInMillis() / 1000L);
						
						if(post.getObjectId() != null) {
							
							if(post.getType().equals(POST_TYPE_PHOTO)) {
								
								Photo photo = facebook.getPhoto(post.getObjectId(), new Reading().fields(POST_PHOTO_FIELDS));
								
								faceReportPost.setPhotoSrc(photo.getPicture().toString());
								faceReportPost.setPhotoHeight(photo.getHeight());
								faceReportPost.setPhotoWidth(photo.getWidth());
								
							} else if(post.getType().equals(POST_TYPE_VIDEO)) {
								
								Video video = facebook.getVideo(post.getObjectId(), new Reading().fields(POST_VIDEO_FIELDS));
								
								faceReportPost.setPhotoSrc(video.getPicture().toString());
							}
						}
						
						faceReportPostRemote.save(faceReportPost);

						getPostCommentsUsers(faceReportPost.getPostId());
						getPostLikesUsers(faceReportPost.getPostId());

						getPostsInsights(faceReportPost.getPostId());
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			} while(posts.getPaging() != null);
			
		} catch (facebook4j.FacebookException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String getPostType(String type) {
		
		if(type.equals(POST_TYPE_PHOTO)) {
			return PHOTOS_POSTED;
		} else if(type.equals(POST_TYPE_LINK)) {
			return LINK_POSTED;
		} else if(type.equals(POST_TYPE_STATUS)) {
			return STATUS_UPDATE;
		} else if(type.equals(POST_TYPE_VIDEO)) {
			return VIDEO_POSTED;
		} else {
			return OFFER_POSTED;
		}
	}

	private void getPostLikesUsers(String postId) {

		NameValuePair[] nameValuePairs = {getNameValuePairAccessToken(), new NameValuePair("format", "JSON")};

		caller = APICallerFactory.getAPICallerInstance(HttpClientType.APACHE_HTTP_CLIENT);

		try {
			
			String jsonResponse = caller.getData(POST_LIKES_USERS.replace("idPost", postId), nameValuePairs);

			jsonResponse = jsonResponse.replaceAll("\\{\\}", "[]");

			JsonParser parser = new JsonParser();

			JsonArray jsonResponseData = parser.parse(jsonResponse).getAsJsonObject().get("data").getAsJsonArray();

			Iterator<JsonElement> likes = jsonResponseData.iterator();
			while (likes.hasNext() ) {

				try {

					JsonObject	element = (JsonObject) likes.next();

					FacebookInfluentialUserId facebookInfluentialUserId = new FacebookInfluentialUserId();
					facebookInfluentialUserId.setPageId(pageId);
					facebookInfluentialUserId.setUserId(element.get("id").getAsJsonPrimitive().getAsString());

					FacebookInfluentialUser facebookInfluentialUser = facebookInfluentialUserRemote.getById(facebookInfluentialUserId); 

					if(facebookInfluentialUser == null) {

						facebookInfluentialUser = new FacebookInfluentialUser();
						facebookInfluentialUser.setUserId(facebookInfluentialUserId.getUserId());
						facebookInfluentialUser.setPageId(facebookInfluentialUserId.getPageId());
						facebookInfluentialUser.setName(element.get("name").getAsJsonPrimitive().getAsString());
						facebookInfluentialUser.setLikes(1L);
						facebookInfluentialUser.setComments(0L);
						facebookInfluentialUser.setHasAppScopedUserId('T');
						facebookInfluentialUser.setIsVerified('T');

					} else {

						facebookInfluentialUser.setLikes(facebookInfluentialUser.getLikes() + 1L);
					}

					facebookInfluentialUserRemote.save(facebookInfluentialUser);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	private void updatePostComments(String postId) {

		FacebookIntegration facebookIntegration = new FacebookIntegration();
		Facebook facebook = facebookIntegration.getFacebook(new AccessToken(accessToken));
		
		ResponseList<facebook4j.Comment> comments = null;
		
		try {
			
			do {
				
				if(comments == null) {
					
					comments = facebook.getCommentReplies(postId, new Reading().fields(POST_COMMENTS_FIELDS).
							limit(POST_COMMENTS_LIMIT).order(Ordering.REVERSE_CHRONOLOGICAL)); 
					
				} else {
					
					Paging<facebook4j.Comment> paging = comments.getPaging();
					comments = facebook.fetchNext(paging);
				}
				
				for(facebook4j.Comment comment : comments) {

					try {
						
						FaceReportPostComment faceReportPostComment = new FaceReportPostComment();
						faceReportPostComment.setCommentId(comment.getId());
						faceReportPostComment.setFaceReportPost(faceReportPostRemote.getById(postId));
						faceReportPostComment.setUserId(comment.getFrom().getId());
						faceReportPostComment.setUserName(comment.getFrom().getName());
						faceReportPostComment.setMessage(comment.getMessage());
						faceReportPostComment.setCreatedTime(comment.getCreatedTime().getTime() / 1000L);
						faceReportPostComment.setLikes(comment.getLikeCount());
						faceReportPostComment.setComments(comment.getCommentCount());
						if(comment.getAttachment() != null) {
							faceReportPostComment.setAttachmentType(comment.getAttachment().getType());
							faceReportPostComment.setAttachmentUrl(comment.getAttachment().getUrl());
						}

						faceReportPostCommentRemote.save(faceReportPostComment);
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				
			} while(comments.getPaging() != null && comments.getPaging().getNext() != null);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getPostCommentsUsers(String postId) {

		FacebookIntegration facebookIntegration = new FacebookIntegration();
		Facebook facebook = facebookIntegration.getFacebook(new AccessToken(accessToken));
		
		ResponseList<facebook4j.Comment> comments = null;
		
		try {
			
			do {
				
				if(comments == null) {
					
					comments = facebook.getCommentReplies(postId, new Reading().fields(POST_COMMENTS_FIELDS).
							limit(POST_COMMENTS_LIMIT).order(Ordering.REVERSE_CHRONOLOGICAL)); 
					
				} else {
					
					Paging<facebook4j.Comment> paging = comments.getPaging();
					comments = facebook.fetchNext(paging);
				}
				
				for(facebook4j.Comment comment : comments) {
					
					try {
						
						FaceReportPostComment faceReportPostComment = new FaceReportPostComment();
						faceReportPostComment.setCommentId(comment.getId());
						faceReportPostComment.setFaceReportPost(faceReportPostRemote.getById(postId));
						faceReportPostComment.setUserId(comment.getFrom().getId());
						faceReportPostComment.setUserName(comment.getFrom().getName());
						faceReportPostComment.setMessage(comment.getMessage());
						faceReportPostComment.setCreatedTime(comment.getCreatedTime().getTime() / 1000L);
						faceReportPostComment.setLikes(comment.getLikeCount());
						faceReportPostComment.setComments(comment.getCommentCount());
						if(comment.getAttachment() != null) {
							faceReportPostComment.setAttachmentType(comment.getAttachment().getType());
							faceReportPostComment.setAttachmentUrl(comment.getAttachment().getUrl());
						}

						faceReportPostCommentRemote.save(faceReportPostComment);
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					try {

						FacebookInfluentialUserId facebookInfluentialUserId = new FacebookInfluentialUserId();
						facebookInfluentialUserId.setPageId(pageId);
						facebookInfluentialUserId.setUserId(comment.getFrom().getId());

						FacebookInfluentialUser facebookInfluentialUser = facebookInfluentialUserRemote.getById(facebookInfluentialUserId); 

						if(facebookInfluentialUser == null) {

							facebookInfluentialUser = new FacebookInfluentialUser();
							facebookInfluentialUser.setUserId(facebookInfluentialUserId.getUserId());
							facebookInfluentialUser.setPageId(facebookInfluentialUserId.getPageId());
							facebookInfluentialUser.setName(comment.getFrom().getName());
							facebookInfluentialUser.setComments(1L);
							facebookInfluentialUser.setLikes(0L);
							facebookInfluentialUser.setHasAppScopedUserId('T');
							facebookInfluentialUser.setIsVerified('T');

						} else {
							facebookInfluentialUser.setComments(facebookInfluentialUser.getComments() + 1L);
						}

						facebookInfluentialUserRemote.save(facebookInfluentialUser);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			} while(comments.getPaging() != null && comments.getPaging().getNext() != null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void getPostsInsights(String postId) {

		try {
			
			FacebookIntegration facebookIntegration = new FacebookIntegration();
			Facebook facebook = facebookIntegration.getFacebook(new AccessToken(accessToken));
			
			Map<String, String> queryParameters = new HashMap<String, String>();
			queryParameters.put("metric", postInsightsMetrics);
			queryParameters.put("period", PERIOD_LIFETIME);

			RawAPIResponse res = facebook.callGetAPI(postId + "/insights", queryParameters);
			
			JsonParser parser = new JsonParser();			
			JsonArray jsonResponseData = parser.parse(res.asString()).getAsJsonObject().get("data").getAsJsonArray();

			Iterator<JsonElement> insightsList = jsonResponseData.iterator();
			
			while (insightsList.hasNext() ) {
				
				try {

					JsonObject jsonObject = ((JsonObject) insightsList.next());

					Insights insights = gson.fromJson(jsonObject, Insights.class);

					JsonObject valueObject = jsonObject.get("values").getAsJsonArray().get(0).getAsJsonObject();

					if(valueObject.get("value").isJsonPrimitive()) {

						try {

							insights.setValue(valueObject.get("value").getAsLong());

							FacebookPostInsight facebookPostInsight = new FacebookPostInsight();
							facebookPostInsight.setPostId(postId);
							facebookPostInsight.setProperty(insights.getName());
							facebookPostInsight.setValue(insights.getValue());

							facebookPostInsightRemote.save(facebookPostInsight);

						} catch (NumberFormatException e) {
							
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}

					} else if(valueObject.get("value").isJsonObject()) {

						JsonObject object = valueObject.get("value").getAsJsonObject();

						for (Map.Entry<String,JsonElement> entry : object.entrySet()) {

							try {

								FacebookPostInsight facebookPostInsight = new FacebookPostInsight();
								facebookPostInsight.setPostId(postId);
								facebookPostInsight.setProperty(insights.getName() + "_" + entry.getKey().replace(" ", "_"));
								
								if(entry.getValue().isJsonPrimitive()) {
									
									// Necessário para poder fazer salvar o valor de métricas de retenção de vídeo, que vem com os dados como decimais
									// sendo que no banco o campo value é do tipo BigInt.
									if(insights.getName().equals(POST_VIDEO_RETENTION_GRAPH) 
											|| insights.getName().equals(POST_VIDEO_RETENTION_GRAPH_AUTOPLAYED) || insights.getName().equals(POST_VIDEO_RETENTION_GRAPH_CLICKED_TO_PLAY)) {
										
										facebookPostInsight.setValue((long) (entry.getValue().getAsDouble() * 10000L));
										facebookPostInsight.setType(insights.getName());
										
									} else {
										facebookPostInsight.setValue(entry.getValue().getAsLong());
									}
									
								} else {
									facebookPostInsight.setValue(0L);
								}

								facebookPostInsightRemote.save(facebookPostInsight);

							} catch (NumberFormatException e) {
								
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}

						normalizeInsightsValues(insights, object, postId);

					} else if(valueObject.get("value").isJsonArray()) {
						normalizeInsightsValues(insights, postId);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	private void normalizeInsightsValues(Insights insights, JsonObject objectValue, String postId) {
		
		if(insights.getName().equals(POST_STORIES_BY_ACTION_TYPE)) {
			normalizePostStoriesByActionType(objectValue, postId);
		} else if(insights.getName().equals(POST_CONSUMPTIONS_BY_TYPE)) {
			normalizePostConsumptionsByType(objectValue, postId);
		}
		
	}
	
	private void normalizeInsightsValues(Insights insights, String postId) {
		
		if(insights.getName().equals(POST_STORIES_BY_ACTION_TYPE)) {
			normalizePostStoriesByActionType(postId);
		} else if(insights.getName().equals(POST_CONSUMPTIONS_BY_TYPE)) {
			normalizePostConsumptionsByType(postId);
		}
		
	}
	
	private void normalizePostStoriesByActionType(JsonObject objectValue, String postId) {
		
		if(!objectValue.has("like")) {
			
			FacebookPostInsight facebookPostInsight = new FacebookPostInsight();
			facebookPostInsight.setPostId(postId);
			facebookPostInsight.setProperty(POST_STORIES_BY_ACTION_TYPE_LIKE);
			facebookPostInsight.setValue(0L);

			try {
				
				facebookPostInsightRemote.save(facebookPostInsight);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(!objectValue.has("comment")) {
			
			FacebookPostInsight facebookPostInsight = new FacebookPostInsight();
			facebookPostInsight.setPostId(postId);
			facebookPostInsight.setProperty(POST_STORIES_BY_ACTION_TYPE_COMMENT);
			facebookPostInsight.setValue(0L);

			try {
				
				facebookPostInsightRemote.save(facebookPostInsight);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(!objectValue.has("share")) {
			
			FacebookPostInsight facebookPostInsight = new FacebookPostInsight();
			facebookPostInsight.setPostId(postId);
			facebookPostInsight.setProperty(POST_STORIES_BY_ACTION_TYPE_SHARE);
			facebookPostInsight.setValue(0L);

			try {
				
				facebookPostInsightRemote.save(facebookPostInsight);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void normalizePostStoriesByActionType(String postId) {

		FacebookPostInsight facebookPostInsightLike = new FacebookPostInsight();
		facebookPostInsightLike.setPostId(postId);
		facebookPostInsightLike.setProperty(POST_STORIES_BY_ACTION_TYPE_LIKE);
		facebookPostInsightLike.setValue(0L);

		try {

			facebookPostInsightRemote.save(facebookPostInsightLike);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		FacebookPostInsight facebookPostInsightComment = new FacebookPostInsight();
		facebookPostInsightComment.setPostId(postId);
		facebookPostInsightComment.setProperty(POST_STORIES_BY_ACTION_TYPE_COMMENT);
		facebookPostInsightComment.setValue(0L);

		try {

			facebookPostInsightRemote.save(facebookPostInsightComment);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		FacebookPostInsight facebookPostInsightShare = new FacebookPostInsight();
		facebookPostInsightShare.setPostId(postId);
		facebookPostInsightShare.setProperty(POST_STORIES_BY_ACTION_TYPE_SHARE);
		facebookPostInsightShare.setValue(0L);

		try {

			facebookPostInsightRemote.save(facebookPostInsightShare);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void normalizePostConsumptionsByType(JsonObject objectValue, String postId) {
		
		if(!objectValue.has("other clicks")) {
			
			FacebookPostInsight facebookPostInsight = new FacebookPostInsight();
			facebookPostInsight.setPostId(postId);
			facebookPostInsight.setProperty(POST_CONSUMPTIONS_BY_TYPE_OTHER_CLICKS);
			facebookPostInsight.setValue(0L);

			try {
				
				facebookPostInsightRemote.save(facebookPostInsight);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(!objectValue.has("photo view")) {
			
			FacebookPostInsight facebookPostInsight = new FacebookPostInsight();
			facebookPostInsight.setPostId(postId);
			facebookPostInsight.setProperty(POST_CONSUMPTIONS_BY_TYPE_PHOTO_VIEW);
			facebookPostInsight.setValue(0L);

			try {
				
				facebookPostInsightRemote.save(facebookPostInsight);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(!objectValue.has("link clicks")) {
			
			FacebookPostInsight facebookPostInsight = new FacebookPostInsight();
			facebookPostInsight.setPostId(postId);
			facebookPostInsight.setProperty(POST_CONSUMPTIONS_BY_TYPE_LINK_CLICKS);
			facebookPostInsight.setValue(0L);

			try {
				
				facebookPostInsightRemote.save(facebookPostInsight);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if(!objectValue.has("video play")) {
			
			FacebookPostInsight facebookPostInsight = new FacebookPostInsight();
			facebookPostInsight.setPostId(postId);
			facebookPostInsight.setProperty(POST_CONSUMPTIONS_BY_TYPE_VIDEO_PLAY);
			facebookPostInsight.setValue(0L);

			try {
				
				facebookPostInsightRemote.save(facebookPostInsight);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void normalizePostConsumptionsByType(String postId) {

		FacebookPostInsight facebookPostInsightOtherClicks = new FacebookPostInsight();
		facebookPostInsightOtherClicks.setPostId(postId);
		facebookPostInsightOtherClicks.setProperty(POST_CONSUMPTIONS_BY_TYPE_OTHER_CLICKS);
		facebookPostInsightOtherClicks.setValue(0L);

		try {

			facebookPostInsightRemote.save(facebookPostInsightOtherClicks);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		FacebookPostInsight facebookPostInsightPhotoView = new FacebookPostInsight();
		facebookPostInsightPhotoView.setPostId(postId);
		facebookPostInsightPhotoView.setProperty(POST_CONSUMPTIONS_BY_TYPE_PHOTO_VIEW);
		facebookPostInsightPhotoView.setValue(0L);

		try {

			facebookPostInsightRemote.save(facebookPostInsightPhotoView);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		FacebookPostInsight facebookPostInsightLinkClicks = new FacebookPostInsight();
		facebookPostInsightLinkClicks.setPostId(postId);
		facebookPostInsightLinkClicks.setProperty(POST_CONSUMPTIONS_BY_TYPE_LINK_CLICKS);
		facebookPostInsightLinkClicks.setValue(0L);

		try {

			facebookPostInsightRemote.save(facebookPostInsightLinkClicks);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		FacebookPostInsight facebookPostInsightVideoPlays = new FacebookPostInsight();
		facebookPostInsightVideoPlays.setPostId(postId);
		facebookPostInsightVideoPlays.setProperty(POST_CONSUMPTIONS_BY_TYPE_VIDEO_PLAY);
		facebookPostInsightVideoPlays.setValue(0L);

		try {

			facebookPostInsightRemote.save(facebookPostInsightVideoPlays);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private NameValuePair getNameValuePairAccessToken() {
		return new NameValuePair("access_token", this.accessToken);
	}

	public Long getDateUntil() {
		return dateUntil;
	}

	public void setDateUntil(Long dateUntil) {
		this.dateUntil = dateUntil;
	}

	public Long getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Long dateFrom) {
		this.dateFrom = dateFrom;
	}
}