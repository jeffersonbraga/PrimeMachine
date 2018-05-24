package br.com.opsocial.client.entity.report.twitter;

import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.report.TwitterReport;

public class ReportTwitterDashboardDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private Long dateFrom;
	private Long dateUntil;
	
	private List<TwitterReport> followersByDay;
	private Long followersBalance;
	private Integer followersBalancePerformance;
	private Long averageFollowersPerDay;
	
	private Long followersTotal;
	private Long followersTotalOld;
	private Integer followersTotalPerformance;
	
	private Map<Date, Long> tweetsInteractionsByDay;
	private Long tweetsInteractions;
	private Integer tweetsInteractionsPerformance;
	
	private List<TwitterReport> repliesByDay;
	private Long repliesTotal;
	private Integer repliesTotalPerformance;
	
	private List<TwitterReport> retweetsByDay;
	private Long retweetsTotal;
	private Integer retweetsTotalPerformance;
	
	private List<TwitterReport> mentionsByDay;
	private Long mentionsTotal;
	private Integer mentionsTotalPerformance;
	
	private List<TwitterReport> favoritesByDay;
	private Long favoritesTotal;
	private Integer favoritesTotalPerformance;
	
	private List<TwitterReportStatusDTO> twitterReportStatus;
	
	public ReportTwitterDashboardDTO() {
		
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

	public Long getFollowersBalance() {
		return followersBalance;
	}

	public void setFollowersBalance(Long followersBalance) {
		this.followersBalance = followersBalance;
	}

	public Integer getFollowersBalancePerformance() {
		return followersBalancePerformance;
	}

	public void setFollowersBalancePerformance(Integer followersBalancePerformance) {
		this.followersBalancePerformance = followersBalancePerformance;
	}

	public Long getAverageFollowersPerDay() {
		return averageFollowersPerDay;
	}

	public void setAverageFollowersPerDay(Long averageFollowersPerDay) {
		this.averageFollowersPerDay = averageFollowersPerDay;
	}

	public Long getFollowersTotal() {
		return followersTotal;
	}

	public void setFollowersTotal(Long followersTotal) {
		this.followersTotal = followersTotal;
	}

	public Long getFollowersTotalOld() {
		return followersTotalOld;
	}

	public void setFollowersTotalOld(Long followersTotalOld) {
		this.followersTotalOld = followersTotalOld;
	}

	public Integer getFollowersTotalPerformance() {
		return followersTotalPerformance;
	}

	public void setFollowersTotalPerformance(Integer followersTotalPerformance) {
		this.followersTotalPerformance = followersTotalPerformance;
	}

	public Map<Date, Long> getTweetsInteractionsByDay() {
		return tweetsInteractionsByDay;
	}

	public void setTweetsInteractionsByDay(
			Map<Date, Long> tweetsInteractionsByDay) {
		this.tweetsInteractionsByDay = tweetsInteractionsByDay;
	}

	public Long getTweetsInteractions() {
		return tweetsInteractions;
	}

	public void setTweetsInteractions(Long tweetsInteractions) {
		this.tweetsInteractions = tweetsInteractions;
	}

	public Integer getTweetsInteractionsPerformance() {
		return tweetsInteractionsPerformance;
	}

	public void setTweetsInteractionsPerformance(Integer tweetsInteractionsPerformance) {
		this.tweetsInteractionsPerformance = tweetsInteractionsPerformance;
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

	public List<TwitterReport> getFavoritesByDay() {
		return favoritesByDay;
	}

	public void setFavoritesByDay(List<TwitterReport> favoritesByDay) {
		this.favoritesByDay = favoritesByDay;
	}

	public Long getRepliesTotal() {
		return repliesTotal;
	}

	public void setRepliesTotal(Long repliesTotal) {
		this.repliesTotal = repliesTotal;
	}

	public Integer getRepliesTotalPerformance() {
		return repliesTotalPerformance;
	}

	public void setRepliesTotalPerformance(Integer repliesTotalPerformance) {
		this.repliesTotalPerformance = repliesTotalPerformance;
	}

	public Long getRetweetsTotal() {
		return retweetsTotal;
	}

	public void setRetweetsTotal(Long retweetsTotal) {
		this.retweetsTotal = retweetsTotal;
	}

	public Integer getRetweetsTotalPerformance() {
		return retweetsTotalPerformance;
	}

	public void setRetweetsTotalPerformance(Integer retweetsTotalPerformance) {
		this.retweetsTotalPerformance = retweetsTotalPerformance;
	}

	public Long getMentionsTotal() {
		return mentionsTotal;
	}

	public void setMentionsTotal(Long mentionsTotal) {
		this.mentionsTotal = mentionsTotal;
	}

	public Integer getMentionsTotalPerformance() {
		return mentionsTotalPerformance;
	}

	public void setMentionsTotalPerformance(Integer mentionsTotalPerformance) {
		this.mentionsTotalPerformance = mentionsTotalPerformance;
	}

	public Long getFavoritesTotal() {
		return favoritesTotal;
	}

	public void setFavoritesTotal(Long favoritesTotal) {
		this.favoritesTotal = favoritesTotal;
	}

	public Integer getFavoritesTotalPerformance() {
		return favoritesTotalPerformance;
	}

	public void setFavoritesTotalPerformance(Integer favoritesTotalPerformance) {
		this.favoritesTotalPerformance = favoritesTotalPerformance;
	}

	public List<TwitterReportStatusDTO> getTwitterReportStatus() {
		return twitterReportStatus;
	}

	public void setTwitterReportStatus(
			List<TwitterReportStatusDTO> twitterReportStatus) {
		this.twitterReportStatus = twitterReportStatus;
	}
	
}
