package br.com.opsocial.client.entity.monitoring;

import java.util.Date;
import java.util.List;
import java.util.Map;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class MonitoringReportDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final Integer HISTORICAL = 0;
	public static final Integer PERIOD_SEVEN = 7;
	public static final Integer PERIOD_FIFTEEN = 15;
	public static final Integer PERIOD_THIRTY = 30;
	
	public static final Integer GEO_POSTS_LIMIT = 100;
	
	public static final String DAY = "day";
	public static final String WEEK_DAY = "week_day";
	public static final String HOUR = "hour";
	
	private Long dateFrom;
	private Long dateUntil;
	
	private Integer period;
	
	private Map<Character, Long> postsPerDaysNetworks;
	private Map<Integer, Map<Character, Long>> postsPerHoursNetworks;
	private Map<Integer, Map<Character, Long>> postsPerWeekDaysNetworks;
	private Map<Date, Long> postsPerDay;
	private Map<Date, Map<Character, Long>> postsPerDayNetworks;
	
	private Date dayWithMorePosts;
	
	private Character highestPostsVolumeNetwork;
	private Integer highestPostsVolumePercentage;
	
	private Integer timeWithMorePosts;
	private Integer timeWithMorePostsPercentage;
	
	private Integer weekDayWithMorePosts;
	private Integer weekDayWithMorePostsPercentage;
	
	private Long postsTotal;
	private Long dailyAverage;
	
	private Integer garbagePostsTotalPercentage;
	private Long garbagePostsTotal;
	
	private Map<Character, Long> sentimentAnalysis;
	private Integer approvalRatePerc;
	private Integer disapprovalRatePerc;
	private Long qualifiedPostsTotal;
	
	private List<MonitoringTermReportDTO> monitoringTermReports;
	private List<MonitoringInfluentialUserDTO> monitoringInfluentialUsers;
	
	private List<MonitoringInfluentialUserDTO> monitoringPositiveUsers;
	private List<MonitoringInfluentialUserDTO> monitoringNegativeUsers;
	
	private Map<Date, Long> postsUsersPerDay;
	private Long postsUsersTotal;
	private Integer postsUsersAverage;
	private Double postsUsersRecurrentPerc;
	
	private Integer womenPostsUsersPerc = 0;
	private Integer menPostsUsersPerc = 0;
	
	private Map<String, Long> postsCities;
	
	private List<MonitoringTagReportDTO> monitoringTagReports;
	
	private String cloud;
	private String cloudTagsPositivePosts;
	private String cloudTagsNegativePosts;
	
	private Map<String, Long> hashTags;
	
	private Map<String, Long> links;
	
	private List<String> mostFrequentWords;
	private List<String> mostFrequentWordsPositive;
	private List<String> mostFrequentWordsNegative;
	
	private String monitoringGeoPostsJSON;
	
	private List<MonitoringGroupReportDTO> monitoringGroupsReport;
	
	public MonitoringReportDTO() {
		
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
	
	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public Map<Character, Long> getPostsPerDaysNetworks() {
		return postsPerDaysNetworks;
	}

	public void setPostsPerDaysNetworks(Map<Character, Long> postsPerDaysNetworks) {
		this.postsPerDaysNetworks = postsPerDaysNetworks;
	}

	public Map<Integer, Map<Character, Long>> getPostsPerHoursNetworks() {
		return postsPerHoursNetworks;
	}

	public void setPostsPerHoursNetworks(Map<Integer, Map<Character, Long>> postsPerHoursNetworks) {
		this.postsPerHoursNetworks = postsPerHoursNetworks;
	}

	public Map<Integer, Map<Character, Long>> getPostsPerWeekDaysNetworks() {
		return postsPerWeekDaysNetworks;
	}

	public void setPostsPerWeekDaysNetworks(
			Map<Integer, Map<Character, Long>> postsPerWeekDaysNetworks) {
		this.postsPerWeekDaysNetworks = postsPerWeekDaysNetworks;
	}

	public Map<Date, Long> getPostsPerDay() {
		return postsPerDay;
	}

	public void setPostsPerDay(Map<Date, Long> postsPerDay) {
		this.postsPerDay = postsPerDay;
	}

	public Map<Date, Map<Character, Long>> getPostsPerDayNetworks() {
		return postsPerDayNetworks;
	}

	public void setPostsPerDayNetworks(
			Map<Date, Map<Character, Long>> postsPerDayNetworks) {
		this.postsPerDayNetworks = postsPerDayNetworks;
	}

	public Date getDayWithMorePosts() {
		return dayWithMorePosts;
	}

	public void setDayWithMorePosts(Date dayWithMorePosts) {
		this.dayWithMorePosts = dayWithMorePosts;
	}

	public Character getHighestPostsVolumeNetwork() {
		return highestPostsVolumeNetwork;
	}

	public void setHighestPostsVolumeNetwork(Character highestPostsVolumeNetwork) {
		this.highestPostsVolumeNetwork = highestPostsVolumeNetwork;
	}

	public Integer getHighestPostsVolumePercentage() {
		return highestPostsVolumePercentage;
	}

	public void setHighestPostsVolumePercentage(Integer highestPostsVolumePercentage) {
		this.highestPostsVolumePercentage = highestPostsVolumePercentage;
	}

	public Integer getTimeWithMorePosts() {
		return timeWithMorePosts;
	}

	public void setTimeWithMorePosts(Integer timeWithMorePosts) {
		this.timeWithMorePosts = timeWithMorePosts;
	}

	public Integer getTimeWithMorePostsPercentage() {
		return timeWithMorePostsPercentage;
	}

	public void setTimeWithMorePostsPercentage(Integer timeWithMorePostsPercentage) {
		this.timeWithMorePostsPercentage = timeWithMorePostsPercentage;
	}

	public Integer getWeekDayWithMorePosts() {
		return weekDayWithMorePosts;
	}

	public void setWeekDayWithMorePosts(Integer weekDayWithMorePosts) {
		this.weekDayWithMorePosts = weekDayWithMorePosts;
	}

	public Integer getWeekDayWithMorePostsPercentage() {
		return weekDayWithMorePostsPercentage;
	}

	public void setWeekDayWithMorePostsPercentage(
			Integer weekDayWithMorePostsPercentage) {
		this.weekDayWithMorePostsPercentage = weekDayWithMorePostsPercentage;
	}

	public Long getPostsTotal() {
		return postsTotal;
	}

	public void setPostsTotal(Long postsTotal) {
		this.postsTotal = postsTotal;
	}

	public Long getDailyAverage() {
		return dailyAverage;
	}

	public void setDailyAverage(Long dailyAverage) {
		this.dailyAverage = dailyAverage;
	}

	public Integer getGarbagePostsTotalPercentage() {
		return garbagePostsTotalPercentage;
	}

	public void setGarbagePostsTotalPercentage(Integer garbagePostsTotalPercentage) {
		this.garbagePostsTotalPercentage = garbagePostsTotalPercentage;
	}

	public Long getGarbagePostsTotal() {
		return garbagePostsTotal;
	}

	public void setGarbagePostsTotal(Long garbagePostsTotal) {
		this.garbagePostsTotal = garbagePostsTotal;
	}

	public Map<Character, Long> getSentimentAnalysis() {
		return sentimentAnalysis;
	}

	public void setSentimentAnalysis(Map<Character, Long> sentimentAnalysis) {
		this.sentimentAnalysis = sentimentAnalysis;
	}

	public Integer getApprovalRatePerc() {
		return approvalRatePerc;
	}

	public void setApprovalRatePerc(Integer approvalRatePerc) {
		this.approvalRatePerc = approvalRatePerc;
	}

	public Integer getDisapprovalRatePerc() {
		return disapprovalRatePerc;
	}

	public void setDisapprovalRatePerc(Integer disapprovalRatePerc) {
		this.disapprovalRatePerc = disapprovalRatePerc;
	}

	public Long getQualifiedPostsTotal() {
		return qualifiedPostsTotal;
	}

	public void setQualifiedPostsTotal(Long qualifiedPostsTotal) {
		this.qualifiedPostsTotal = qualifiedPostsTotal;
	}

	public List<MonitoringTermReportDTO> getMonitoringTermReports() {
		return monitoringTermReports;
	}

	public void setMonitoringTermReports(
			List<MonitoringTermReportDTO> monitoringTermReports) {
		this.monitoringTermReports = monitoringTermReports;
	}

	public List<MonitoringInfluentialUserDTO> getMonitoringInfluentialUsers() {
		return monitoringInfluentialUsers;
	}

	public void setMonitoringInfluentialUsers(List<MonitoringInfluentialUserDTO> monitoringInfluentialUsers) {
		this.monitoringInfluentialUsers = monitoringInfluentialUsers;
	}

	public List<MonitoringInfluentialUserDTO> getMonitoringPositiveUsers() {
		return monitoringPositiveUsers;
	}

	public void setMonitoringPositiveUsers(
			List<MonitoringInfluentialUserDTO> monitoringPositiveUsers) {
		this.monitoringPositiveUsers = monitoringPositiveUsers;
	}

	public List<MonitoringInfluentialUserDTO> getMonitoringNegativeUsers() {
		return monitoringNegativeUsers;
	}

	public void setMonitoringNegativeUsers(
			List<MonitoringInfluentialUserDTO> monitoringNegativeUsers) {
		this.monitoringNegativeUsers = monitoringNegativeUsers;
	}

	public Map<Date, Long> getPostsUsersPerDay() {
		return postsUsersPerDay;
	}

	public void setPostsUsersPerDay(Map<Date, Long> postsUsersPerDay) {
		this.postsUsersPerDay = postsUsersPerDay;
	}

	public Long getPostsUsersTotal() {
		return postsUsersTotal;
	}

	public void setPostsUsersTotal(Long postsUsersTotal) {
		this.postsUsersTotal = postsUsersTotal;
	}

	public Integer getPostsUsersAverage() {
		return postsUsersAverage;
	}

	public void setPostsUsersAverage(Integer postsUsersAverage) {
		this.postsUsersAverage = postsUsersAverage;
	}

	public Double getPostsUsersRecurrentPerc() {
		return postsUsersRecurrentPerc;
	}

	public void setPostsUsersRecurrentPerc(Double postsUsersRecurrentPerc) {
		this.postsUsersRecurrentPerc = postsUsersRecurrentPerc;
	}

	public Integer getWomenPostsUsersPerc() {
		return womenPostsUsersPerc;
	}

	public void setWomenPostsUsersPerc(Integer womenPostsUsersPerc) {
		this.womenPostsUsersPerc = womenPostsUsersPerc;
	}

	public Integer getMenPostsUsersPerc() {
		return menPostsUsersPerc;
	}

	public void setMenPostsUsersPerc(Integer menPostsUsersPerc) {
		this.menPostsUsersPerc = menPostsUsersPerc;
	}

	public Map<String, Long> getPostsCities() {
		return postsCities;
	}

	public void setPostsCities(Map<String, Long> postsCities) {
		this.postsCities = postsCities;
	}

	public String getCloudTagsPositivePosts() {
		return cloudTagsPositivePosts;
	}

	public void setCloudTagsPositivePosts(String cloudTagsPositivePosts) {
		this.cloudTagsPositivePosts = cloudTagsPositivePosts;
	}

	public String getCloudTagsNegativePosts() {
		return cloudTagsNegativePosts;
	}

	public void setCloudTagsNegativePosts(String cloudTagsNegativePosts) {
		this.cloudTagsNegativePosts = cloudTagsNegativePosts;
	}

	public Map<String, Long> getHashTags() {
		return hashTags;
	}

	public void setHashTags(Map<String, Long> hashTags) {
		this.hashTags = hashTags;
	}

	public Map<String, Long> getLinks() {
		return links;
	}

	public void setLinks(Map<String, Long> links) {
		this.links = links;
	}

	public String getMonitoringGeoPostsJSON() {
		return monitoringGeoPostsJSON;
	}

	public void setMonitoringGeoPostsJSON(String monitoringGeoPostsJSON) {
		this.monitoringGeoPostsJSON = monitoringGeoPostsJSON;
	}

	public List<MonitoringTagReportDTO> getMonitoringTagReports() {
		return monitoringTagReports;
	}

	public void setMonitoringTagReports(
			List<MonitoringTagReportDTO> monitoringTagReports) {
		this.monitoringTagReports = monitoringTagReports;
	}

	public String getCloud() {
		return cloud;
	}

	public void setCloud(String cloud) {
		this.cloud = cloud;
	}

	public List<String> getMostFrequentWords() {
		return mostFrequentWords;
	}

	public void setMostFrequentWords(List<String> mostFrequentWords) {
		this.mostFrequentWords = mostFrequentWords;
	}

	public List<String> getMostFrequentWordsPositive() {
		return mostFrequentWordsPositive;
	}

	public void setMostFrequentWordsPositive(List<String> mostFrequentWordsPositive) {
		this.mostFrequentWordsPositive = mostFrequentWordsPositive;
	}

	public List<String> getMostFrequentWordsNegative() {
		return mostFrequentWordsNegative;
	}

	public void setMostFrequentWordsNegative(List<String> mostFrequentWordsNegative) {
		this.mostFrequentWordsNegative = mostFrequentWordsNegative;
	}

	public List<MonitoringGroupReportDTO> getMonitoringGroupsReport() {
		return monitoringGroupsReport;
	}

	public void setMonitoringGroupsReport(
			List<MonitoringGroupReportDTO> monitoringGroupsReport) {
		this.monitoringGroupsReport = monitoringGroupsReport;
	}
}
