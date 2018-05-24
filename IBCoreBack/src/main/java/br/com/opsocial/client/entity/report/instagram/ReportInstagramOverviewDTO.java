package br.com.opsocial.client.entity.report.instagram;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.instagram.InstagramCommentCompareDTO;
import br.com.opsocial.ejb.entity.instagram.InstagramEngagedFollower;
import br.com.opsocial.ejb.entity.instagram.InstagramFollowerTrend;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramReportPost;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;
import br.com.opsocial.ejb.entity.instagram.InstagramTopPostTag;

public class ReportInstagramOverviewDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private Long dateFrom;
	private Long dateUntil;
	
	private Long followers;
	
	private List<InstagramFollowerTrend> instagramFollowerTrends;
	private InstagramFollowerTrend instagramFollowerTrend;
	
	private Long likes;
	private Long comments;
	private Double likesAverage;
	private Double commentsAverage;
	
	private List<InstagramReport> likesPerDay;
	private List<InstagramReport> commentsPerDay;
	
	private List<InstagramReportPostDTO> postsWithMoreLikes;
	private List<InstagramReportPostDTO> postsWithMoreComments;
	private List<InstagramReportPost> postsWithMoreInteractions;
	
	private List<InstagramEngagedFollower> mostEngagedFollowers;
	
	private Long impressions;
	private Long reach;
	private Long profileViews;
	private Long websiteClicks;
	
	private List<InstagramTopPostTag> instagramTopPostTags;
	
	private Map<Integer, Long> likesPerHour;
	private Integer timeWithMoreLikes;
	private Integer timeWithMoreLikesPerc;
	private Integer timeWithLessLikes;
	private Integer timeWithLessLikesPerc;
	
	private Map<Integer, Long> commentsPerHour;
	private Integer timeWithMoreComments;
	private Integer timeWithMoreCommentsPerc;
	private Integer timeWithLessComments;
	private Integer timeWithLessCommentsPerc;
	
	private Map<Integer, Long> interactionsPerHour;
	private List<InstagramInteractionRankingDTO> instagramInteractionsRanking;
	private Long interactionsCount;

	private List<InstagramReport> audiencesFollowersTrend;

	private List<InstagramReport> audiencesDemograph;

	private List<InstagramReport> audiencesTopCountry;

	private List<InstagramReport> audiencesTopCities;

	private List<InstagramReport> audiencesOnlineFollowersByHour;

	private List<InstagramReport> audiencesOnlineFollowersByDay;

	private List<InstagramReport> audiencesPostsInteraction;

	private List<InstagramReport> likesPerDayFollowers;

	private List<InstagramReport> likesPerDayNoFollowers;

	private List<InstagramCommentCompareDTO> commentsPerDayCompare;

	private List<InstagramSimpleDataDTO> likesPerHourSimpleData;

	private List<InstagramSimpleDataDTO> commentsPerHourSimpleData;

	private InstagramSimpleDataDTO audiencesAveragePublicAge;

	private List<InstagramSimpleDataDTO> audiencesAveragePublicGender;
	
	private Long totalPosts;
	
	private Long totalPostsImage;
	
	private Long totalPostsVideo;
	
	private Long totalPostsCarousel;
	
	private Double averageInteractionsPosts;
	
	private BigDecimal averageInteractionsFollower;
	
	private Long prints;

	public ReportInstagramOverviewDTO() {
		
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

	public Long getFollowers() {
		return followers;
	}

	public void setFollowers(Long followers) {
		this.followers = followers;
	}

	public List<InstagramFollowerTrend> getInstagramFollowerTrends() {
		return instagramFollowerTrends;
	}

	public void setInstagramFollowerTrends(
			List<InstagramFollowerTrend> instagramFollowerTrends) {
		this.instagramFollowerTrends = instagramFollowerTrends;
	}

	public InstagramFollowerTrend getInstagramFollowerTrend() {
		return instagramFollowerTrend;
	}

	public void setInstagramFollowerTrend(
			InstagramFollowerTrend instagramFollowerTrend) {
		this.instagramFollowerTrend = instagramFollowerTrend;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	public List<InstagramReport> getLikesPerDay() {
		return likesPerDay;
	}

	public void setLikesPerDay(List<InstagramReport> likesPerDay) {
		this.likesPerDay = likesPerDay;
	}

	public List<InstagramReport> getCommentsPerDay() {
		return commentsPerDay;
	}

	public void setCommentsPerDay(List<InstagramReport> commentsPerDay) {
		this.commentsPerDay = commentsPerDay;
	}

	public Double getLikesAverage() {
		return likesAverage;
	}

	public void setLikesAverage(Double likesAverage) {
		this.likesAverage = likesAverage;
	}

	public Double getCommentsAverage() {
		return commentsAverage;
	}

	public void setCommentsAverage(Double commentsAverage) {
		this.commentsAverage = commentsAverage;
	}

	public List<InstagramReportPostDTO> getPostsWithMoreLikes() {
		return postsWithMoreLikes;
	}

	public void setPostsWithMoreLikes(
			List<InstagramReportPostDTO> postsWithMoreLikes) {
		this.postsWithMoreLikes = postsWithMoreLikes;
	}

	public List<InstagramReportPostDTO> getPostsWithMoreComments() {
		return postsWithMoreComments;
	}

	public List<InstagramEngagedFollower> getMostEngagedFollowers() {
		return mostEngagedFollowers;
	}

	public void setMostEngagedFollowers(
			List<InstagramEngagedFollower> mostEngagedFollowers) {
		this.mostEngagedFollowers = mostEngagedFollowers;
	}
	
	public Long getImpressions() {
		return impressions;
	}

	public void setImpressions(Long impressions) {
		this.impressions = impressions;
	}

	public Long getReach() {
		return reach;
	}

	public void setReach(Long reach) {
		this.reach = reach;
	}

	public Long getProfileViews() {
		return profileViews;
	}

	public void setProfileViews(Long profileViews) {
		this.profileViews = profileViews;
	}

	public Long getWebsiteClicks() {
		return websiteClicks;
	}

	public void setWebsiteClicks(Long websiteClicks) {
		this.websiteClicks = websiteClicks;
	}

	public List<InstagramTopPostTag> getInstagramTopPostTags() {
		return instagramTopPostTags;
	}

	public void setInstagramTopPostTags(
			List<InstagramTopPostTag> instagramTopPostTags) {
		this.instagramTopPostTags = instagramTopPostTags;
	}

	public Map<Integer, Long> getLikesPerHour() {
		return likesPerHour;
	}

	public void setLikesPerHour(Map<Integer, Long> likesPerHour) {
		this.likesPerHour = likesPerHour;
	}

	public Integer getTimeWithMoreLikes() {
		return timeWithMoreLikes;
	}

	public void setTimeWithMoreLikes(Integer timeWithMoreLikes) {
		this.timeWithMoreLikes = timeWithMoreLikes;
	}

	public Integer getTimeWithMoreLikesPerc() {
		return timeWithMoreLikesPerc;
	}

	public void setTimeWithMoreLikesPerc(Integer timeWithMoreLikesPerc) {
		this.timeWithMoreLikesPerc = timeWithMoreLikesPerc;
	}

	public Integer getTimeWithLessLikes() {
		return timeWithLessLikes;
	}

	public void setTimeWithLessLikes(Integer timeWithLessLikes) {
		this.timeWithLessLikes = timeWithLessLikes;
	}

	public Integer getTimeWithLessLikesPerc() {
		return timeWithLessLikesPerc;
	}

	public void setTimeWithLessLikesPerc(Integer timeWithLessLikesPerc) {
		this.timeWithLessLikesPerc = timeWithLessLikesPerc;
	}

	public Map<Integer, Long> getCommentsPerHour() {
		return commentsPerHour;
	}

	public void setCommentsPerHour(Map<Integer, Long> commentsPerHour) {
		this.commentsPerHour = commentsPerHour;
	}

	public Integer getTimeWithMoreComments() {
		return timeWithMoreComments;
	}

	public void setTimeWithMoreComments(Integer timeWithMoreComments) {
		this.timeWithMoreComments = timeWithMoreComments;
	}

	public Integer getTimeWithMoreCommentsPerc() {
		return timeWithMoreCommentsPerc;
	}

	public void setTimeWithMoreCommentsPerc(Integer timeWithMoreCommentsPerc) {
		this.timeWithMoreCommentsPerc = timeWithMoreCommentsPerc;
	}

	public Integer getTimeWithLessComments() {
		return timeWithLessComments;
	}

	public void setTimeWithLessComments(Integer timeWithLessComments) {
		this.timeWithLessComments = timeWithLessComments;
	}

	public Integer getTimeWithLessCommentsPerc() {
		return timeWithLessCommentsPerc;
	}

	public void setTimeWithLessCommentsPerc(Integer timeWithLessCommentsPerc) {
		this.timeWithLessCommentsPerc = timeWithLessCommentsPerc;
	}

	public Map<Integer, Long> getInteractionsPerHour() {
		return interactionsPerHour;
	}

	public void setInteractionsPerHour(Map<Integer, Long> interactionsPerHour) {
		this.interactionsPerHour = interactionsPerHour;
	}

	public List<InstagramInteractionRankingDTO> getInstagramInteractionsRanking() {
		return instagramInteractionsRanking;
	}

	public void setInstagramInteractionsRanking(
			List<InstagramInteractionRankingDTO> instagramInteractionsRanking) {
		this.instagramInteractionsRanking = instagramInteractionsRanking;
	}

	public Long getInteractionsCount() {
		return interactionsCount;
	}

	public void setInteractionsCount(Long interactionsCount) {
		this.interactionsCount = interactionsCount;
	}

	public void setPostsWithMoreComments(
			List<InstagramReportPostDTO> postsWithMoreComments) {
		this.postsWithMoreComments = postsWithMoreComments;
	}

	public List<InstagramReport> getAudiencesFollowersTrend() {
		return audiencesFollowersTrend;
	}

	public List<InstagramReport> getAudiencesDemograph() {
		return audiencesDemograph;
	}

	public List<InstagramReport> getAudiencesTopCountry() {
		return audiencesTopCountry;
	}

	public List<InstagramReport> getAudiencesOnlineFollowersByHour() {
		return audiencesOnlineFollowersByHour;
	}

	public List<InstagramReport> getAudiencesOnlineFollowersByDay() {
		return audiencesOnlineFollowersByDay;
	}

	public List<InstagramReport> getAudiencesPostsInteraction() {
		return audiencesPostsInteraction;
	}

	public void setAudiencesFollowersTrend(List<InstagramReport> audiencesFollowersTrend) {
		this.audiencesFollowersTrend = audiencesFollowersTrend;
	}

	public void setAudiencesDemograph(List<InstagramReport> audiencesDemograph) {
		this.audiencesDemograph = audiencesDemograph;
	}

	public void setAudiencesTopCountry(List<InstagramReport> audiencesTopCountry) {
		this.audiencesTopCountry = audiencesTopCountry;
	}

	public void setAudiencesOnlineFollowersByHour(List<InstagramReport> audiencesOnlineFollowersByHour) {
		this.audiencesOnlineFollowersByHour = audiencesOnlineFollowersByHour;
	}

	public void setAudiencesOnlineFollowersByDay(List<InstagramReport> audiencesOnlineFollowersByDay) {
		this.audiencesOnlineFollowersByDay = audiencesOnlineFollowersByDay;
	}

	public void setAudiencesPostsInteraction(List<InstagramReport> audiencesPostsInteraction) {
		this.audiencesPostsInteraction = audiencesPostsInteraction;
	}

	public List<InstagramReport> getAudiencesTopCities() {
		return audiencesTopCities;
	}

	public void setAudiencesTopCities(List<InstagramReport> audiencesTopCities) {
		this.audiencesTopCities = audiencesTopCities;
	}

	public List<InstagramReport> getLikesPerDayFollowers() {
		return likesPerDayFollowers;
	}

	public List<InstagramReport> getLikesPerDayNoFollowers() {
		return likesPerDayNoFollowers;
	}

	public void setLikesPerDayFollowers(List<InstagramReport> likesPerDayFollowers) {
		this.likesPerDayFollowers = likesPerDayFollowers;
	}

	public void setLikesPerDayNoFollowers(List<InstagramReport> likesPerDayNoFollowers) {
		this.likesPerDayNoFollowers = likesPerDayNoFollowers;
	}

	public List<InstagramCommentCompareDTO> getCommentsPerDayCompare() {
		return commentsPerDayCompare;
	}

	public void setCommentsPerDayCompare(List<InstagramCommentCompareDTO> commentsPerDayCompare) {
		this.commentsPerDayCompare = commentsPerDayCompare;
	}

	public List<InstagramSimpleDataDTO> getLikesPerHourSimpleData() {
		return likesPerHourSimpleData;
	}

	public List<InstagramSimpleDataDTO> getCommentsPerHourSimpleData() {
		return commentsPerHourSimpleData;
	}

	public void setLikesPerHourSimpleData(List<InstagramSimpleDataDTO> likesPerHourSimpleData) {
		this.likesPerHourSimpleData = likesPerHourSimpleData;
	}

	public void setCommentsPerHourSimpleData(List<InstagramSimpleDataDTO> commentsPerHourSimpleData) {
		this.commentsPerHourSimpleData = commentsPerHourSimpleData;
	}

	public InstagramSimpleDataDTO getAudiencesAveragePublicAge() {
		return audiencesAveragePublicAge;
	}

	public List<InstagramSimpleDataDTO> getAudiencesAveragePublicGender() {
		return audiencesAveragePublicGender;
	}

	public void setAudiencesAveragePublicAge(InstagramSimpleDataDTO audiencesAveragePublicAge) {
		this.audiencesAveragePublicAge = audiencesAveragePublicAge;
	}

	public void setAudiencesAveragePublicGender(List<InstagramSimpleDataDTO> audiencesAveragePublicGender) {
		this.audiencesAveragePublicGender = audiencesAveragePublicGender;
	}

	public Long getTotalPosts() {
		return totalPosts;
	}

	public Long getTotalPostsImage() {
		return totalPostsImage;
	}

	public Long getTotalPostsVideo() {
		return totalPostsVideo;
	}

	public Long getTotalPostsCarousel() {
		return totalPostsCarousel;
	}

	public Double getAverageInteractionsPosts() {
		return averageInteractionsPosts;
	}

	public BigDecimal getAverageInteractionsFollower() {
		return averageInteractionsFollower;
	}

	public Long getPrints() {
		return prints;
	}

	public void setTotalPosts(Long totalPosts) {
		this.totalPosts = totalPosts;
	}

	public void setTotalPostsImage(Long totalPostsImage) {
		this.totalPostsImage = totalPostsImage;
	}

	public void setTotalPostsVideo(Long totalPostsVideo) {
		this.totalPostsVideo = totalPostsVideo;
	}

	public void setTotalPostsCarousel(Long totalPostsCarousel) {
		this.totalPostsCarousel = totalPostsCarousel;
	}

	public void setAverageInteractionsPosts(Double averageInteractionsPosts) {
		this.averageInteractionsPosts = averageInteractionsPosts;
	}

	public void setAverageInteractionsFollower(BigDecimal averageInteractionsFollower) {
		this.averageInteractionsFollower = averageInteractionsFollower;
	}

	public void setPrints(Long prints) {
		this.prints = prints;
	}

	public List<InstagramReportPost> getPostsWithMoreInteractions() {
		return postsWithMoreInteractions;
	}

	public void setPostsWithMoreInteractions(List<InstagramReportPost> postsWithMoreInteractions) {
		this.postsWithMoreInteractions = postsWithMoreInteractions;
	}
}