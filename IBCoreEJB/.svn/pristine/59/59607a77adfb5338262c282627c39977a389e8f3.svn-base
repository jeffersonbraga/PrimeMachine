package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.opsocial.ejb.entity.application.idclass.TwitterFollowerId;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.utils.SocialConstraints;

@Entity
@Table(name="twitterfollowers", schema="opsocial")
@IdClass(TwitterFollowerId.class)
public class TwitterFollower extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "followerid")
	private Long followerId;
	
	@Id
	@Column(name = "idprofile")
	private Long idProfile;
	
	@Column(name = "screenname")
	private String screenName;
	
	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "followerscount")
	private Long followersCount;
	
	@Column(name = "location")
	private String location;
	
	@Column(name = "lasttweetdate")
	private Long lastTweetDate;
	
	@Column(name = "gender")
	private Character gender;
	
	@Column(name = "lastupdatedate")
	private Long lastUpdateDate;
	
	@Column(name = "isstillfollower")
	private Character isStillFollower;
	
	@Column(name = "followingdate")
	private Long followingDate;
	
	@Transient
	private String profileUrl;
	
	public TwitterFollower() {
	}
	
	public Long getFollowerId() {
		return followerId;
	}

	public void setFollowerId(Long followerId) {
		this.followerId = followerId;
	}

	public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
		this.setProfileUrl(screenName);
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Long getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Long followersCount) {
		this.followersCount = followersCount;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Long getLastTweetDate() {
		return lastTweetDate;
	}

	public void setLastTweetDate(Long lastTweetDate) {
		this.lastTweetDate = lastTweetDate;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}
	
	public Long getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Long lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Character getIsStillFollower() {
		return isStillFollower;
	}

	public void setIsStillFollower(Character isStillFollower) {
		this.isStillFollower = isStillFollower;
	}

	public Long getFollowingDate() {
		return followingDate;
	}

	public void setFollowingDate(Long followingDate) {
		this.followingDate = followingDate;
	}

	public String getProfileUrl() {
		if (profileUrl == ""  || profileUrl == null){
			profileUrl = SocialConstraints.TWITTER_URL + this.screenName;
		}
		return profileUrl;
	}

	public void setProfileUrl(String profileUrl) {
		this.profileUrl = SocialConstraints.TWITTER_URL + profileUrl;
	}
}
