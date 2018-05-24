package br.com.opsocial.client.entity.report.instagram;

import java.util.List;
import java.util.Map;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.instagram.InstagramEngagedFollower;
import br.com.opsocial.ejb.entity.instagram.InstagramFollowerTrend;
import br.com.opsocial.ejb.entity.instagram.InstagramReportPost;

public class ReportInstagramDashboardDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private Long dateFrom;
	private Long dateUntil;
	
	private List<InstagramFollowerTrend> instagramFollowerTrends;
	private Long followersBalance;
	private Integer followersBalancePerformance;
	
	private Map<String, Long> interactionsPerDay;
	private Long interactions;
	private Integer interactionsPerformance;
	private Integer interactionsFromFollowers;
	private Integer interactionsFromNoFollowers;
	
	private Map<String, Long> likesPerDay;
	private Long likes;
	private Integer likesPerformance;
	
	private Map<String, Long> commentsPerDay;
	private Long comments;
	private Integer commentsPerformance;
	
	private String hashTagsCloud;
	
	private List<InstagramEngagedFollower> mostEngagedFollowers;
	
	private List<InstagramReportPost> postsWithMoreInteractions;
	
	public ReportInstagramDashboardDTO() {
		
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

	public List<InstagramFollowerTrend> getInstagramFollowerTrends() {
		return instagramFollowerTrends;
	}

	public void setInstagramFollowerTrends(
			List<InstagramFollowerTrend> instagramFollowerTrends) {
		this.instagramFollowerTrends = instagramFollowerTrends;
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

	public Map<String, Long> getInteractionsPerDay() {
		return interactionsPerDay;
	}

	public void setInteractionsPerDay(Map<String, Long> interactionsPerDay) {
		this.interactionsPerDay = interactionsPerDay;
	}

	public Long getInteractions() {
		return interactions;
	}

	public void setInteractions(Long interactions) {
		this.interactions = interactions;
	}

	public Integer getInteractionsPerformance() {
		return interactionsPerformance;
	}

	public void setInteractionsPerformance(Integer interactionsPerformance) {
		this.interactionsPerformance = interactionsPerformance;
	}

	public Map<String, Long> getLikesPerDay() {
		return likesPerDay;
	}

	public void setLikesPerDay(Map<String, Long> likesPerDay) {
		this.likesPerDay = likesPerDay;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Integer getLikesPerformance() {
		return likesPerformance;
	}

	public void setLikesPerformance(Integer likesPerformance) {
		this.likesPerformance = likesPerformance;
	}

	public Map<String, Long> getCommentsPerDay() {
		return commentsPerDay;
	}

	public void setCommentsPerDay(Map<String, Long> commentsPerDay) {
		this.commentsPerDay = commentsPerDay;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	public Integer getCommentsPerformance() {
		return commentsPerformance;
	}

	public void setCommentsPerformance(Integer commentsPerformance) {
		this.commentsPerformance = commentsPerformance;
	}

	public Integer getInteractionsFromFollowers() {
		return interactionsFromFollowers;
	}

	public void setInteractionsFromFollowers(Integer interactionsFromFollowers) {
		this.interactionsFromFollowers = interactionsFromFollowers;
	}

	public Integer getInteractionsFromNoFollowers() {
		return interactionsFromNoFollowers;
	}

	public void setInteractionsFromNoFollowers(Integer interactionsFromNoFollowers) {
		this.interactionsFromNoFollowers = interactionsFromNoFollowers;
	}

	public String getHashTagsCloud() {
		return hashTagsCloud;
	}

	public void setHashTagsCloud(String hashTagsCloud) {
		this.hashTagsCloud = hashTagsCloud;
	}

	public List<InstagramEngagedFollower> getMostEngagedFollowers() {
		return mostEngagedFollowers;
	}

	public void setMostEngagedFollowers(
			List<InstagramEngagedFollower> mostEngagedFollowers) {
		this.mostEngagedFollowers = mostEngagedFollowers;
	}

	public List<InstagramReportPost> getPostsWithMoreInteractions() {
		return postsWithMoreInteractions;
	}

	public void setPostsWithMoreInteractions(
			List<InstagramReportPost> postsWithMoreInteractions) {
		this.postsWithMoreInteractions = postsWithMoreInteractions;
	}
}
