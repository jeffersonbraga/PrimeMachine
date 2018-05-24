package br.com.opsocial.ejb.entity.report;

import java.util.List;
import java.util.Map;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class ReportTwitterOverview extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private Long dateFrom;
	private Long dateUntil;
	
	private List<TwitterReport> followersByDay;
	private Long totalOfFollowers;
	private Long followersBalance;
	private TwitterReport dayWithMoreFollowers;
	private Double followersGrowthRate;
	
	private List<TwitterReport> tweetsSentWithText;
	private List<TwitterReport> tweetsSentWithPhotoVideo;
	private List<TwitterReport> tweetsSentWithLink;
	private Long averageTweetsSentPerDay;
	
	private Map<String, Long> tweetsSentCountByType; 
	private String tweetTypeWithMoreTweets;
	private Integer tweetTypeWithMoreTweetsPercentage;
	
	private Map<Integer, Long> tweetsPerHour;
	private Map<Integer, Long> tweetsPerWeekDay;
	private Integer timeWithMoreTweets;
	private Integer timeWithMoreTweetsPercentage;
	private Integer weekDayWithMoreTweets;
	private Integer weekDayWithMoreTweetsPercentage;
	
	private List<TwitterReport> repliesByDay;
	private List<TwitterReport> retweetsByDay;
	private List<TwitterReport> mentionsByDay;
	
	private Map<String, Integer> engagementPercentages;
	private Long engagementPeakDay;
	
	public Map<Integer, Long> getTweetsPerWeekDay() {
		return tweetsPerWeekDay;
	}

	public void setTweetsPerWeekDay(Map<Integer, Long> tweetsPerWeekDay) {
		this.tweetsPerWeekDay = tweetsPerWeekDay;
	}

	public ReportTwitterOverview() {
	}

	public Long getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Long dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Long getDateUntil() {
		return dateUntil;
	}

	public void setDateUntil(Long dateUntil) {
		this.dateUntil = dateUntil;
	}

	public List<TwitterReport> getFollowersByDay() {
		return followersByDay;
	}

	public void setFollowersByDay(List<TwitterReport> followersByDay) {
		this.followersByDay = followersByDay;
	}

	public Long getTotalOfFollowers() {
		return totalOfFollowers;
	}

	public void setTotalOfFollowers(Long totalOfFollowers) {
		this.totalOfFollowers = totalOfFollowers;
	}

	public Long getFollowersBalance() {
		return followersBalance;
	}

	public void setFollowersBalance(Long followersBalance) {
		this.followersBalance = followersBalance;
	}

	public TwitterReport getDayWithMoreFollowers() {
		return dayWithMoreFollowers;
	}

	public void setDayWithMoreFollowers(TwitterReport dayWithMoreFollowers) {
		this.dayWithMoreFollowers = dayWithMoreFollowers;
	}

	public Double getFollowersGrowthRate() {
		return followersGrowthRate;
	}

	public void setFollowersGrowthRate(Double followersGrowthRate) {
		this.followersGrowthRate = followersGrowthRate;
	}

	public List<TwitterReport> getTweetsSentWithText() {
		return tweetsSentWithText;
	}

	public void setTweetsSentWithText(List<TwitterReport> tweetsSentWithText) {
		this.tweetsSentWithText = tweetsSentWithText;
	}

	public List<TwitterReport> getTweetsSentWithPhotoVideo() {
		return tweetsSentWithPhotoVideo;
	}

	public void setTweetsSentWithPhotoVideo(
			List<TwitterReport> tweetsSentWithPhotoVideo) {
		this.tweetsSentWithPhotoVideo = tweetsSentWithPhotoVideo;
	}

	public List<TwitterReport> getTweetsSentWithLink() {
		return tweetsSentWithLink;
	}

	public void setTweetsSentWithLink(List<TwitterReport> tweetsSentWithLink) {
		this.tweetsSentWithLink = tweetsSentWithLink;
	}

	public Long getAverageTweetsSentPerDay() {
		return averageTweetsSentPerDay;
	}

	public void setAverageTweetsSentPerDay(Long averageTweetsSentPerDay) {
		this.averageTweetsSentPerDay = averageTweetsSentPerDay;
	}

	public Map<String, Long> getTweetsSentCountByType() {
		return tweetsSentCountByType;
	}

	public void setTweetsSentCountByType(Map<String, Long> tweetsSentCountByType) {
		this.tweetsSentCountByType = tweetsSentCountByType;
	}

	public String getTweetTypeWithMoreTweets() {
		return tweetTypeWithMoreTweets;
	}

	public void setTweetTypeWithMoreTweets(String tweetTypeWithMoreTweets) {
		this.tweetTypeWithMoreTweets = tweetTypeWithMoreTweets;
	}

	public Integer getTweetTypeWithMoreTweetsPercentage() {
		return tweetTypeWithMoreTweetsPercentage;
	}

	public void setTweetTypeWithMoreTweetsPercentage(
			Integer tweetTypeWithMoreTweetsPercentage) {
		this.tweetTypeWithMoreTweetsPercentage = tweetTypeWithMoreTweetsPercentage;
	}

	public Map<Integer, Long> getTweetsPerHour() {
		return tweetsPerHour;
	}

	public void setTweetsPerHour(Map<Integer, Long> tweetsPerHour) {
		this.tweetsPerHour = tweetsPerHour;
	}

	public Integer getTimeWithMoreTweets() {
		return timeWithMoreTweets;
	}

	public void setTimeWithMoreTweets(Integer timeWithMoreTweets) {
		this.timeWithMoreTweets = timeWithMoreTweets;
	}

	public Integer getTimeWithMoreTweetsPercentage() {
		return timeWithMoreTweetsPercentage;
	}

	public void setTimeWithMoreTweetsPercentage(Integer timeWithMoreTweetsPercentage) {
		this.timeWithMoreTweetsPercentage = timeWithMoreTweetsPercentage;
	}

	public Integer getWeekDayWithMoreTweets() {
		return weekDayWithMoreTweets;
	}

	public void setWeekDayWithMoreTweets(Integer weekDayWithMoreTweets) {
		this.weekDayWithMoreTweets = weekDayWithMoreTweets;
	}

	public Integer getWeekDayWithMoreTweetsPercentage() {
		return weekDayWithMoreTweetsPercentage;
	}

	public void setWeekDayWithMoreTweetsPercentage(
			Integer weekDayWithMoreTweetsPercentage) {
		this.weekDayWithMoreTweetsPercentage = weekDayWithMoreTweetsPercentage;
	}

	public List<TwitterReport> getRepliesByDay() {
		return repliesByDay;
	}

	public void setRepliesByDay(List<TwitterReport> repliesByDay) {
		this.repliesByDay = repliesByDay;
	}

	public List<TwitterReport> getRetweetsByDay() {
		return retweetsByDay;
	}

	public void setRetweetsByDay(List<TwitterReport> retweetsByDay) {
		this.retweetsByDay = retweetsByDay;
	}

	public List<TwitterReport> getMentionsByDay() {
		return mentionsByDay;
	}

	public void setMentionsByDay(List<TwitterReport> mentionsByDay) {
		this.mentionsByDay = mentionsByDay;
	}

	public Map<String, Integer> getEngagementPercentages() {
		return engagementPercentages;
	}

	public void setEngagementPercentages(Map<String, Integer> engagementPercentages) {
		this.engagementPercentages = engagementPercentages;
	}

	public Long getEngagementPeakDay() {
		return engagementPeakDay;
	}

	public void setEngagementPeakDay(Long engagementPeakDay) {
		this.engagementPeakDay = engagementPeakDay;
	}
}
