package br.com.opsocial.ejb.entity.facebook;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="facebookposts", schema="opsocial")
public class FacebookPost extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "postid")
	private String postId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="objectid")
	private String objectId;
	
	@Column(name="caption")
	private String caption;
	
	@Column(name="message")
	private String message;
	
	@Column(name="description")
	private String description;
	
	@Column(name="link")
	private String link;
	
	@Column(name="createdtime")
	private Long createdTime;
	
	@Column(name="type")
	private String type;
	
	@Column(name="source")
	private String source;
	
	@Column(name="profileid")
	private String profileId;
	
	@Column(name="profilenamefrom")
	private String profileNameFrom;
	
	@Column(name="profilelinkfrom")
	private String profileLinkFrom;
	
	@Column(name="profilepicturefrom")
	private String profilePictureFrom;
	
	@Column(name="pictureurl")
	private String pictureUrl;
	
	@Column(name="picturewidth")
	private Integer pictureWidth;
	
	@Column(name="pictureheight")
	private Integer pictureHeight;
	
	@Column(name = "likes")
	private Integer likes = 0;

	@Column(name = "comments")
	private Integer comments = 0;

	@Column(name = "shares")
	private Integer shares = 0;
	
	@OneToMany(mappedBy = "facebookPost")
	private List<FacePostMonitoring> facePostsMonitoring;
	
	@Column(name="lastupdate")
    @Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@Transient
	private Boolean userLike = false;
	
	public FacebookPost() {
		
	}
	
	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
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

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getProfileNameFrom() {
		return profileNameFrom;
	}

	public void setProfileNameFrom(String profileNameFrom) {
		this.profileNameFrom = profileNameFrom;
	}

	public String getProfileLinkFrom() {
		return profileLinkFrom;
	}

	public void setProfileLinkFrom(String profileLinkFrom) {
		this.profileLinkFrom = profileLinkFrom;
	}

	public String getProfilePictureFrom() {
		return profilePictureFrom;
	}

	public void setProfilePictureFrom(String profilePictureFrom) {
		this.profilePictureFrom = profilePictureFrom;
	}
	

	public String getPictureUrl() {
		return pictureUrl;
	}


	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
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

	public List<FacePostMonitoring> getFacePostsMonitoring() {
		return facePostsMonitoring;
	}

	public void setFacePostsMonitoring(List<FacePostMonitoring> facePostsMonitoring) {
		this.facePostsMonitoring = facePostsMonitoring;
	}
	
	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Boolean getUserLike() {
		return userLike;
	}

	public void setUserLike(Boolean userLike) {
		this.userLike = userLike;
	}
}
