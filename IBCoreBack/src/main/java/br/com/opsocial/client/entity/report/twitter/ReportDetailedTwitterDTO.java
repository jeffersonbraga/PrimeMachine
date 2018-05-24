package br.com.opsocial.client.entity.report.twitter;

import java.util.List;
import java.util.Map;

import br.com.opsocial.client.entity.primitive.Date;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;

public class ReportDetailedTwitterDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Long> sumsByProperty;
	
	private Long followersMalePerc;
	private Long followersFemalePerc;
	
	private Long retweetsOfMeCountAll;
	private List<TwitterReportDTO> retweetsOfMeByDay;
	private List<TwitterReportDTO> repliesOfMe;
	private List<TwitterReportDTO> messagesSentByMe;
	
	private Date dateFrom;
	private Date dateUntil;
	
	private Profile twitterProfile;
	private List<TwitterReportStatusDTO> twitterStatusReport;
	
	public static final String FOLLOWERS_COUNT = "followersCount";
	public static final String FRIENDS_COUNT = "friendsCount";
	
	public static final String RETWEETS_OF_ME_COUNT = "retweetsOfMeCount";
	public static final String MENTIONS_COUNT = "mentionsCount";
	public static final String MESSAGES_SENT_BY_ME_COUNT = "messagesSentByMeCount";
	public static final String REPLIES_BY_ME = "repliesByMeCount";
	public static final String REPLIES_OF_ME = "repliesOfMeCount";
	public static final String FAVORITE_COUNT = "favoriteCount";
	
	public static final String MSG_SENT_WITH_TEXT_COUNT = "msgSentWithTextCount";
	public static final String MSG_SENT_WITH_PHOTO_COUNT = "msgSentWithPhotoCount";
	public static final String MSG_SENT_WITH_LINK_COUNT = "msgSentWithLinkCount";
	
	public static final String FOLLOWERS_FEMALE_COUNT_ALL = "followersFemaleCountAll";
	public static final String FOLLOWERS_MALE_COUNT_ALL = "followersMaleCountAll";
	
	public ReportDetailedTwitterDTO() {
	}
	
	public Map<String, Long> getSumsByProperty() {
		return sumsByProperty;
	}
	
	public void setSumsByProperty(Map<String, Long> sumsByProperty) {
		this.sumsByProperty = sumsByProperty;
	}
	
	/**
	 * @return the followersMalePerc
	 */
	public Long getFollowersMalePerc() {
		return followersMalePerc;
	}
	/**
	 * @param followersMalePerc the followersMalePerc to set
	 */
	public void setFollowersMalePerc(Long followersMalePerc) {
		this.followersMalePerc = followersMalePerc;
	}
	/**
	 * @return the followersFemalePerc
	 */
	public Long getFollowersFemalePerc() {
		return followersFemalePerc;
	}
	/**
	 * @param followersFemalePerc the followersFemalePerc to set
	 */
	public void setFollowersFemalePerc(Long followersFemalePerc) {
		this.followersFemalePerc = followersFemalePerc;
	}
	/**
	 * @return the retweetsOfMeCountAll
	 */
	public Long getRetweetsOfMeCountAll() {
		return retweetsOfMeCountAll;
	}
	/**
	 * @param retweetsOfMeCountAll the retweetsOfMeCountAll to set
	 */
	public void setRetweetsOfMeCountAll(Long retweetsOfMeCountAll) {
		this.retweetsOfMeCountAll = retweetsOfMeCountAll;
	}
	
	public List<TwitterReportDTO> getRetweetsOfMeByDay() {
		return retweetsOfMeByDay;
	}

	public void setRetweetsOfMeByDay(List<TwitterReportDTO> retweetsOfMeByDay) {
		this.retweetsOfMeByDay = retweetsOfMeByDay;
	}

	public List<TwitterReportDTO> getRepliesOfMe() {
		return repliesOfMe;
	}

	public void setRepliesOfMe(List<TwitterReportDTO> repliesOfMe) {
		this.repliesOfMe = repliesOfMe;
	}

	public List<TwitterReportDTO> getMessagesSentByMe() {
		return messagesSentByMe;
	}

	public void setMessagesSentByMe(List<TwitterReportDTO> messagesSentByMe) {
		this.messagesSentByMe = messagesSentByMe;
	}

	public Date getDateFrom() {
		return dateFrom;
	}
	
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}
	
	public Date getDateUntil() {
		return dateUntil;
	}
	
	public void setDateUntil(Date dateUntil) {
		this.dateUntil = dateUntil;
	}
	
	public Profile getTwitterProfile() {
		return twitterProfile;
	}
	
	public void setTwitterProfile(Profile twitterProfile) {
		this.twitterProfile = twitterProfile;
	}
	
	public List<TwitterReportStatusDTO> getTwitterStatusReport() {
		return twitterStatusReport;
	}
	
	public void setTwitterStatusReport(List<TwitterReportStatusDTO> twitterStatusReport) {
		this.twitterStatusReport = twitterStatusReport;
	}
	
}
