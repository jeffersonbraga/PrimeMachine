package br.com.opsocial.ejb.entity.twitter;

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
@Table(name="twitterposts", schema="opsocial")
public class TwitterPost extends Persistent {

	private static final long serialVersionUID = 1L;

	public static final Character FORBIDDEN_STATUS = 'F';
	public static final Character DELETED_STATUS = 'D';
	public static final Character ACTIVE_STATUS = 'A';

	public final static String PHOTO = "photo";
	public final static String TEXT = "text";

	@Id
	@Column(name = "statusid")
	private Long statusId;
	
	@Column(name = "text")
	private String text;
	
	@Column(name = "createdat")
	private Long createdAt;
	
	@Column(name = "userid")
	private Long userId;
	
	@Column(name = "username") 
	private String userName;
	
	@Column(name = "screenname")
	private String screenName;
	
	@Column(name = "profileimageurl")
	private String profileImageUrl;
	
	@Column(name = "retweetcount")
	private Long retweetCount;
	
	@Column(name = "favoritecount")
	private Long favoriteCount;
	
	@Column(name = "state")
	private Character state;
	
	@OneToMany(mappedBy = "twitterPost")
	private List<TwitterPostMonitoring> twitterPostsMonitoring;
	
	@Column(name="lastupdate")
    @Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdate;
	
	@Column(name="link")
	private String link;
	
	@Column(name="type")
	private String type;
	
	@Column(name="pictureurl")
	private String pictureUrl;
	
	@Column(name="picturewidth")
	private Integer pictureWidth;
	
	@Column(name="pictureheight")
	private Integer pictureHeight;

	@Column(name="retweetedstatus")
	private Long retweetedStatus;

	@Transient
	private String profileUrl;

	public TwitterPost() {
	}
	
	public Long getStatusId() {
		return statusId;
	}

	public void setStatusId(Long statusId) {
		this.statusId = statusId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Long createdAt) {
		this.createdAt = createdAt;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Long getRetweetCount() {
		return retweetCount;
	}

	public void setRetweetCount(Long retweetCount) {
		this.retweetCount = retweetCount;
	}

	public Long getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(Long favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Character getState() {
		return state;
	}

	public void setState(Character state) {
		this.state = state;
	}

	public List<TwitterPostMonitoring> getTwitterPostsMonitoring() {
		return twitterPostsMonitoring;
	}

	public void setTwitterPostsMonitoring(
			List<TwitterPostMonitoring> twitterPostsMonitoring) {
		this.twitterPostsMonitoring = twitterPostsMonitoring;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Long getRetweetedStatus() {
		return retweetedStatus;
	}

	public void setRetweetedStatus(Long retweetedStatus) {
		this.retweetedStatus = retweetedStatus;
	}

	public String getProfileUrl() {
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = profileUrl;
	}
}
