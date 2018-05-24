package br.com.opsocial.ejb.entity.google;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="googleplusposts", schema="opsocial")
public class GooglePlusPost extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final String VERB_POST = "post";
	public static final String VERB_SHARE = "share";
	
	@Id
	@Column(name = "activityid")
	private String activityId;
	
	@Column(name="title")
	private String title;
	
	@Column(name = "published")
	private Long published;
	
	@Column(name = "url")
	private String url;
	
	@Column(name = "verb")
	private String verb;
	
	@Column(name = "actorid")
	private String actorId;
	
	@Column(name = "actordisplayname")
	private String actorDisplayName;
	
	@Column(name = "actorurl")
	private String actorUrl;
	
	@Column(name = "actorimageurl")
	private String actorImageUrl;
	
	@Column(name = "objectid")
	private String objectId;
	
	@Column(name = "objectcontent")
	private String objectContent;
	
	@Column(name = "objecttype")
	private String objectType;
	
	@Column(name = "likes")
	private Integer likes=0;

	@Column(name = "comments")
	private Integer comments=0;

	@Column(name = "shares")
	private Integer shares=0;
	
	@OneToMany(mappedBy = "googlePlusPost", fetch=FetchType.EAGER)
	private List<ObjectAttachment> objectsAttachment;
	
	@Column(name="lastupdate")
    @Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	public GooglePlusPost() {
	}

	public String getActivityId() {
		return activityId;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getPublished() {
		return published;
	}

	public void setPublished(Long published) {
		this.published = published;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getActorDisplayName() {
		return actorDisplayName;
	}

	public void setActorDisplayName(String actorDisplayName) {
		this.actorDisplayName = actorDisplayName;
	}

	public String getActorUrl() {
		return actorUrl;
	}

	public void setActorUrl(String actorUrl) {
		this.actorUrl = actorUrl;
	}

	public String getActorImageUrl() {
		return actorImageUrl;
	}

	public void setActorImageUrl(String actorImageUrl) {
		this.actorImageUrl = actorImageUrl;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectContent() {
		return objectContent;
	}

	public void setObjectContent(String objectContent) {
		this.objectContent = objectContent;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
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

	public List<ObjectAttachment> getObjectsAttachment() {
		return objectsAttachment;
	}

	public void setObjectsAttachment(List<ObjectAttachment> objectsAttachment) {
		this.objectsAttachment = objectsAttachment;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
