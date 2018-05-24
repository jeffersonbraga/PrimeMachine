package br.com.opsocial.server.utils.streams;

import java.io.Serializable;
import java.util.List;

public class StreamPost implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String postId;
	private String objectId;
	private String profileId;
	private String text;
	private String link;
	private LinkSummary linkSummary;
	private Long createdTime;
	private String type;
	private Character network;
	private String screenName;
	private String profileName;
	private String profileLink;
	private String profilePicture;
	private String pictureUrl;
	private String videoUrl;
	private Integer likesCount;
	private Integer commentsCount;
	private Integer sharesCount;
	private Integer pictureWidth;
	private Integer pictureHeight;
	private Boolean liked = false;
	private StreamPostComments streamPostComments;
	private List<StreamPhoto> streamPhotos;
	private List<StreamMessageTag> streamMessageTags;
	
	public StreamPost() {
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public LinkSummary getLinkSummary() {
		return linkSummary;
	}

	public void setLinkSummary(LinkSummary linkSummary) {
		this.linkSummary = linkSummary;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Character getNetwork() {
		return network;
	}

	public void setNetwork(Character network) {
		this.network = network;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getProfileName() {
		return profileName;
	}

	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}

	public String getProfileLink() {
		return profileLink;
	}

	public void setProfileLink(String profileLink) {
		this.profileLink = profileLink;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getLikesCount() {
		return likesCount;
	}

	public void setLikesCount(Integer likesCount) {
		this.likesCount = likesCount;
	}

	public Integer getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Integer commentsCount) {
		this.commentsCount = commentsCount;
	}

	public Integer getSharesCount() {
		return sharesCount;
	}

	public void setSharesCount(Integer sharesCount) {
		this.sharesCount = sharesCount;
	}

	public Integer getPictureWidth() {
		return pictureWidth;
	}

	public void setPictureWidth(Integer pictureWidth) {
		this.pictureWidth = pictureWidth;
	}

	public Integer getPictureHeight() {
		return pictureHeight;
	}

	public void setPictureHeight(Integer pictureHeight) {
		this.pictureHeight = pictureHeight;
	}

	public Boolean getLiked() {
		return liked;
	}

	public void setLiked(Boolean userLiked) {
		this.liked = userLiked;
	}

	public StreamPostComments getStreamPostComments() {
		return streamPostComments;
	}

	public void setStreamPostComments(StreamPostComments streamPostComments) {
		this.streamPostComments = streamPostComments;
	}

	public List<StreamPhoto> getStreamPhotos() {
		return streamPhotos;
	}

	public void setStreamPhotos(List<StreamPhoto> streamPhotos) {
		this.streamPhotos = streamPhotos;
	}

	public List<StreamMessageTag> getStreamMessageTags() {
		return streamMessageTags;
	}

	public void setStreamMessageTags(List<StreamMessageTag> streamMessageTags) {
		this.streamMessageTags = streamMessageTags;
	}

	
}
