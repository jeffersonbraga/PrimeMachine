package br.com.opsocial.client.entity.report.facebook;

import java.util.Date;
import java.util.List;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.report.AgeGroup;
import br.com.opsocial.ejb.entity.report.PropertyValue;

public class FaceReportPostDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private String postId;
	private String pageId;
	private String message;
	private String type;
	private String permalink;
	private Date createdTime;
	private Integer likes;
	private Integer comments;
	private Integer shares;
	private Long lastDateInsights;
	private String photoSrc;
	private Integer photoWidth;
	private Integer photoHeight;
	
	private Double engagement;
	
	private Long reach;
	private Long reactions;
	private Long interactions;
	
	private Long organicReach;
	private Integer organicReachPercentage;
	private Long paidReach;
	private Integer paidReachPercentage;
	
	private Double likesPercentage;
	private Long love;
	private Double lovePercentage;
	private Long wow;
	private Double wowPercentage;
	private Long haha;
	private Double hahaPercentage;
	private Long sorry;
	private Double sorryPercentage;
	private Long anger;
	private Double angerPercentage;
	
	private Double commentsPercentage;
	
	private Double sharesPercentage;
	
	private Long clicks;
	private Double clicksPercentage;
	
	// Video Only Metrics
	private List<PropertyValue> videoRetention;
	
	private Long videoViews;
	private Long videoOrganicViews;
	private Long videoPaidViews;
	private Integer videoOrganicViewsPercentage;
	private Integer videoPaidViewsPercentage;
	
	private Long videoAutoPlayedViews;
	private Integer videoAutoPlayedViewsPercentage;
	private Long videoClickToPlayViews;
	private Integer videoClickToPlayViewsPercentage;
	
	private List<AgeGroup> videoViewTimeByFemaleUsers;
	private List<AgeGroup> videoViewTimeByMaleUsers;
	private List<AgeGroup> videoViewTimeByUnidentifiedUsers;
	
	public final static String PHOTOS_POSTED = "247";
	public final static String STATUS_UPDATE = "46";
	public final static String LINK_POSTED = "80";
	public final static String VIDEO_POSTED = "128";
	
	public static final String POST_IMPRESSIONS_UNIQUE = "post_impressions_unique";
	public static final String POST_IMPRESSIONS = "post_impressions";
	public static final String POST_STORIES = "post_stories";
	public static final String POST_CONSUMPTIONS = "post_consumptions";
	
	public static final String POST_IMPRESSIONS_PAID_UNIQUE = "post_impressions_paid_unique";
	public static final String POST_IMPRESSIONS_ORGANIC_UNIQUE = "post_impressions_organic_unique";
	public static final String POST_IMPRESSIONS_VIRAL_UNIQUE = "post_impressions_viral_unique";
	
	public static final String POST_IMPRESSIONS_PAID = "post_impressions_paid";
	public static final String POST_IMPRESSIONS_ORGANIC = "post_impressions_organic";
	public static final String POST_IMPRESSIONS_VIRAL = "post_impressions_viral";
	
	public static final String POST_STORIES_BY_ACTION_TYPE = "post_stories_by_action_type";
	public static final String POST_STORIES_BY_ACTION_TYPE_LIKE = "post_stories_by_action_type_like";
	public static final String POST_STORIES_BY_ACTION_TYPE_COMMENT = "post_stories_by_action_type_comment";
	public static final String POST_STORIES_BY_ACTION_TYPE_SHARE = "post_stories_by_action_type_share";
	
	public static final String POST_REACTIONS_LOVE_TOTAL = "post_reactions_love_total";
	public static final String POST_REACTIONS_HAHA_TOTAL = "post_reactions_haha_total";
	public static final String POST_REACTIONS_WOW_TOTAL = "post_reactions_wow_total";
	public static final String POST_REACTIONS_SORRY_TOTAL = "post_reactions_sorry_total";
	public static final String POST_REACTIONS_ANGER_TOTAL = "post_reactions_anger_total";
	public static final String POST_REACTIONS_LIKE_TOTAL = "post_reactions_like_total";
	
	public static final String POST_CONSUMPTIONS_BY_TYPE = "post_consumptions_by_type";
	public static final String POST_CONSUMPTIONS_BY_TYPE_OTHER_CLICKS = "post_consumptions_by_type_other_clicks";
	public static final String POST_CONSUMPTIONS_BY_TYPE_PHOTO_VIEW = "post_consumptions_by_type_photo_view";
	public static final String POST_CONSUMPTIONS_BY_TYPE_LINK_CLICKS = "post_consumptions_by_type_link_clicks";
	public static final String POST_CONSUMPTIONS_BY_TYPE_VIDEO_PLAY = "post_consumptions_by_type_video_play";
	
	public FaceReportPostDTO() {
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Integer getShares() {
		return shares;
	}

	public void setShares(Integer shares) {
		this.shares = shares;
	}

	public Long getLastDateInsights() {
		return lastDateInsights;
	}

	public void setLastDateInsights(Long lastDateInsights) {
		this.lastDateInsights = lastDateInsights;
	}

	public String getPhotoSrc() {
		return photoSrc;
	}

	public void setPhotoSrc(String photoSrc) {
		this.photoSrc = photoSrc;
	}

	public Integer getPhotoWidth() {
		return photoWidth;
	}

	public void setPhotoWidth(Integer photoWidth) {
		this.photoWidth = photoWidth;
	}

	public Integer getPhotoHeight() {
		return photoHeight;
	}

	public void setPhotoHeight(Integer photoHeight) {
		this.photoHeight = photoHeight;
	}

	public Double getEngagement() {
		return engagement;
	}

	public void setEngagement(Double engagement) {
		this.engagement = engagement;
	}

	public Long getReach() {
		return reach;
	}

	public void setReach(Long reach) {
		this.reach = reach;
	}

	public Long getReactions() {
		return reactions;
	}

	public void setReactions(Long reactions) {
		this.reactions = reactions;
	}

	public Long getInteractions() {
		return interactions;
	}

	public void setInteractions(Long interactions) {
		this.interactions = interactions;
	}

	public Long getOrganicReach() {
		return organicReach;
	}

	public void setOrganicReach(Long organicReach) {
		this.organicReach = organicReach;
	}

	public Integer getOrganicReachPercentage() {
		return organicReachPercentage;
	}

	public void setOrganicReachPercentage(Integer organicReachPercentage) {
		this.organicReachPercentage = organicReachPercentage;
	}

	public Long getPaidReach() {
		return paidReach;
	}

	public void setPaidReach(Long paidReach) {
		this.paidReach = paidReach;
	}

	public Integer getPaidReachPercentage() {
		return paidReachPercentage;
	}

	public void setPaidReachPercentage(Integer paidReachPercentage) {
		this.paidReachPercentage = paidReachPercentage;
	}

	public Double getLikesPercentage() {
		return likesPercentage;
	}

	public void setLikesPercentage(Double likesPercentage) {
		this.likesPercentage = likesPercentage;
	}

	public Long getLove() {
		return love;
	}

	public void setLove(Long Love) {
		this.love = Love;
	}

	public Double getLovePercentage() {
		return lovePercentage;
	}

	public void setLovePercentage(Double lovePercentage) {
		this.lovePercentage = lovePercentage;
	}

	public Long getWow() {
		return wow;
	}

	public void setWow(Long Wow) {
		this.wow = Wow;
	}

	public Double getWowPercentage() {
		return wowPercentage;
	}

	public void setWowPercentage(Double wowPercentage) {
		this.wowPercentage = wowPercentage;
	}

	public Long getHaha() {
		return haha;
	}

	public void setHaha(Long Haha) {
		this.haha = Haha;
	}

	public Double getHahaPercentage() {
		return hahaPercentage;
	}

	public void setHahaPercentage(Double hahaPercentage) {
		this.hahaPercentage = hahaPercentage;
	}

	public Long getSorry() {
		return sorry;
	}

	public void setSorry(Long Sorry) {
		this.sorry = Sorry;
	}

	public Double getSorryPercentage() {
		return sorryPercentage;
	}

	public void setSorryPercentage(Double sorryPercentage) {
		this.sorryPercentage = sorryPercentage;
	}

	public Long getAnger() {
		return anger;
	}

	public void setAnger(Long Anger) {
		this.anger = Anger;
	}

	public Double getAngerPercentage() {
		return angerPercentage;
	}

	public void setAngerPercentage(Double angerPercentage) {
		this.angerPercentage = angerPercentage;
	}

	public Double getCommentsPercentage() {
		return commentsPercentage;
	}

	public void setCommentsPercentage(Double commentsPercentage) {
		this.commentsPercentage = commentsPercentage;
	}

	public Double getSharesPercentage() {
		return sharesPercentage;
	}

	public void setSharesPercentage(Double sharesPercentage) {
		this.sharesPercentage = sharesPercentage;
	}

	public Long getClicks() {
		return clicks;
	}

	public void setClicks(Long clicks) {
		this.clicks = clicks;
	}

	public Double getClicksPercentage() {
		return clicksPercentage;
	}

	public void setClicksPercentage(Double clicksPercentage) {
		this.clicksPercentage = clicksPercentage;
	}
	
	public List<PropertyValue> getVideoRetention() {
		return videoRetention;
	}

	public void setVideoRetention(List<PropertyValue> videoRetention) {
		this.videoRetention = videoRetention;
	}

	public Long getVideoViews() {
		return videoViews;
	}

	public void setVideoViews(Long videoViews) {
		this.videoViews = videoViews;
	}

	public Long getVideoOrganicViews() {
		return videoOrganicViews;
	}

	public void setVideoOrganicViews(Long videoOrganicViews) {
		this.videoOrganicViews = videoOrganicViews;
	}

	public Long getVideoPaidViews() {
		return videoPaidViews;
	}

	public void setVideoPaidViews(Long videoPaidViews) {
		this.videoPaidViews = videoPaidViews;
	}

	public Integer getVideoOrganicViewsPercentage() {
		return videoOrganicViewsPercentage;
	}

	public void setVideoOrganicViewsPercentage(Integer videoOrganicViewsPercentage) {
		this.videoOrganicViewsPercentage = videoOrganicViewsPercentage;
	}

	public Integer getVideoPaidViewsPercentage() {
		return videoPaidViewsPercentage;
	}

	public void setVideoPaidViewsPercentage(Integer videoPaidViewsPercentage) {
		this.videoPaidViewsPercentage = videoPaidViewsPercentage;
	}

	public Long getVideoAutoPlayedViews() {
		return videoAutoPlayedViews;
	}

	public void setVideoAutoPlayedViews(Long videoAutoPlayedViews) {
		this.videoAutoPlayedViews = videoAutoPlayedViews;
	}

	public Integer getVideoAutoPlayedViewsPercentage() {
		return videoAutoPlayedViewsPercentage;
	}

	public void setVideoAutoPlayedViewsPercentage(Integer videoAutoPlayedViewsPercentage) {
		this.videoAutoPlayedViewsPercentage = videoAutoPlayedViewsPercentage;
	}

	public Long getVideoClickToPlayViews() {
		return videoClickToPlayViews;
	}

	public void setVideoClickToPlayViews(Long videoClickToPlayViews) {
		this.videoClickToPlayViews = videoClickToPlayViews;
	}

	public Integer getVideoClickToPlayViewsPercentage() {
		return videoClickToPlayViewsPercentage;
	}

	public void setVideoClickToPlayViewsPercentage(Integer videoClickToPlayViewsPercentage) {
		this.videoClickToPlayViewsPercentage = videoClickToPlayViewsPercentage;
	}

	public List<AgeGroup> getVideoViewTimeByFemaleUsers() {
		return videoViewTimeByFemaleUsers;
	}

	public void setVideoViewTimeByFemaleUsers(List<AgeGroup> videoViewTimeByFemaleUsers) {
		this.videoViewTimeByFemaleUsers = videoViewTimeByFemaleUsers;
	}

	public List<AgeGroup> getVideoViewTimeByMaleUsers() {
		return videoViewTimeByMaleUsers;
	}

	public void setVideoViewTimeByMaleUsers(List<AgeGroup> videoViewTimeByMaleUsers) {
		this.videoViewTimeByMaleUsers = videoViewTimeByMaleUsers;
	}

	public List<AgeGroup> getVideoViewTimeByUnidentifiedUsers() {
		return videoViewTimeByUnidentifiedUsers;
	}

	public void setVideoViewTimeByUnidentifiedUsers(List<AgeGroup> videoViewTimeByUnidentifiedUsers) {
		this.videoViewTimeByUnidentifiedUsers = videoViewTimeByUnidentifiedUsers;
	}
}
