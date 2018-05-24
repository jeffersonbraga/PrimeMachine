package br.com.opsocial.server.utils.monitorings;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import br.com.opsocial.client.entity.application.MonitoringDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringInfluentialUserDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringReportDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringTagDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringTagReportDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringTermReportDTO;
import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.Calc;
import br.com.opsocial.server.utils.GenericDateInterval;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import br.com.opsocial.ejb.das.MaintenanceMonitoringHashTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringInfluentialUserRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLinkRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLocationReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostLocationRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringQualificationReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTermReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringUserReportRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostMonitoringRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringGeoPost;
import br.com.opsocial.ejb.entity.monitoring.MonitoringInfluentialUser;
import br.com.opsocial.ejb.entity.monitoring.MonitoringUserReport;

public abstract class UtilMonitorings {
	
	public static final Character WEB = 'W';
	public static final Character PDF = 'P';
	public static final Character XLS = 'X';
	
	public UtilMonitorings() {
	}
	
	public static MonitoringReportDTO mountMonitoringReportDTO(MonitoringDTO monitoring, GenericDateInterval dateInterval, Character format) {
		
		MonitoringReportDTO monitoringReportDTO = new MonitoringReportDTO();
		
		MaintenanceMonitoringReportRemote monitoringReportRemote = (MaintenanceMonitoringReportRemote) 
				RecoverMaintenance.recoverMaintenance("MonitoringReport");
		
		MaintenanceMonitoringTermReportRemote monitoringTermReportRemote = (MaintenanceMonitoringTermReportRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringTermReport");
		
		MaintenanceMonitoringInfluentialUserRemote monitoringInfluentialUserRemote = (MaintenanceMonitoringInfluentialUserRemote) 
				RecoverMaintenance.recoverMaintenance("MonitoringInfluentialUser");
		
		MaintenanceMonitoringHashTagRemote monitoringHashTagRemote = (MaintenanceMonitoringHashTagRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringHashTag");
		
		MaintenanceMonitoringLinkRemote monitoringLinkRemote = (MaintenanceMonitoringLinkRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringLink");
		
		MaintenanceMonitoringQualificationReportRemote monitoringQualificationReportRemote = (MaintenanceMonitoringQualificationReportRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringQualificationReport");
		
		MaintenanceMonitoringTagReportRemote monitoringTagReportRemote = (MaintenanceMonitoringTagReportRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringTagReport");
		
		MaintenanceMonitoringUserReportRemote monitoringUserReportRemote = (MaintenanceMonitoringUserReportRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringUserReport");
		
		MaintenanceMonitoringLocationReportRemote monitoringLocationReportRemote = (MaintenanceMonitoringLocationReportRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringLocationReport");
		
		MaintenanceMonitoringPostLocationRemote monitoringPostLocationRemote = (MaintenanceMonitoringPostLocationRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringPostLocation");
		
		monitoringReportDTO.setDateFrom(dateInterval.getDateFrom());
		monitoringReportDTO.setDateUntil(dateInterval.getDateUntil());
		
		monitoringReportDTO.setPostsTotal(monitoringReportRemote.getPostsTotal(monitoring.getIdMonitoring(), dateInterval.getDateFrom(), dateInterval.getDateUntil()));
		
		monitoringReportDTO.setGarbagePostsTotal(monitoringReportRemote.getGarbagePostsTotal(monitoring.getIdMonitoring(), dateInterval.getDateFrom(), dateInterval.getDateUntil()));
		
		monitoringReportDTO.setGarbagePostsTotalPercentage(Calc.calcPercentageNormal(monitoringReportDTO.getGarbagePostsTotal().doubleValue(), 
					monitoringReportDTO.getGarbagePostsTotal().doubleValue() + monitoringReportDTO.getPostsTotal().doubleValue(), 0).intValue());
		
		monitoringReportDTO.setPostsPerDaysNetworks(monitoringReportRemote.getPostsPerDaysNetworks(monitoring.getIdMonitoring(), dateInterval.getDateFrom(), dateInterval.getDateUntil()));
		
		Object[] highestPostsVolume = getHighestPostsVolume(monitoringReportDTO.getPostsPerDaysNetworks(), monitoringReportDTO.getPostsTotal());
		
		monitoringReportDTO.setHighestPostsVolumeNetwork((Character)highestPostsVolume[0]);
		monitoringReportDTO.setHighestPostsVolumePercentage((Integer)highestPostsVolume[1]);
		
		monitoringReportDTO.setPostsPerHoursNetworks(monitoringReportRemote.getPostsPerHoursNetworks(monitoring.getIdMonitoring(), dateInterval.getDateFrom(), dateInterval.getDateUntil()));
		
		Object[] timeWithMorePosts = getTimeWithMorePosts(monitoringReportDTO.getPostsPerHoursNetworks(), monitoringReportDTO.getPostsTotal());
		
		monitoringReportDTO.setTimeWithMorePosts((Integer) timeWithMorePosts[0]);
		monitoringReportDTO.setTimeWithMorePostsPercentage((Integer) timeWithMorePosts[1]);
		
		monitoringReportDTO.setPostsPerWeekDaysNetworks(monitoringReportRemote.getPostsPerWeekDaysNetworks(monitoring.getIdMonitoring(), dateInterval.getDateFrom(), dateInterval.getDateUntil()));
		
		Object[] weekDayWithMorePosts = getWeekDayWithMorePosts(monitoringReportDTO.getPostsPerWeekDaysNetworks(), monitoringReportDTO.getPostsTotal());
		
		monitoringReportDTO.setWeekDayWithMorePosts((Integer) weekDayWithMorePosts[0]);
		monitoringReportDTO.setWeekDayWithMorePostsPercentage((Integer) weekDayWithMorePosts[1]);
		
		monitoringReportDTO.setPostsPerDay(monitoringReportRemote.getPostsPerDay(monitoring.getIdMonitoring(), dateInterval.getDateFrom(), dateInterval.getDateUntil()));
		monitoringReportDTO.setDailyAverage(Calc.calcAverage(monitoringReportDTO.getPostsTotal().doubleValue(), monitoringReportDTO.getPostsPerDay().size(), 0).longValue());
		monitoringReportDTO.setDayWithMorePosts(getDayWithMorePosts(monitoringReportDTO.getPostsPerDay(), dateInterval.getDateUntil()));
		
		Map<String, Long> mostPositiveUsers = monitoringInfluentialUserRemote.getMostPositiveFans(monitoring.getIdMonitoring(), 
				Profile.TWITTER, dateInterval.getDateFrom(), dateInterval.getDateUntil(), 0, 100);
		
		List<MonitoringInfluentialUserDTO> monitoringPositiveUsers = new ArrayList<MonitoringInfluentialUserDTO>();
		for(Map.Entry<String, Long> mostPositiveUser : mostPositiveUsers.entrySet()) {
			
			MonitoringInfluentialUser monitoringPositiveUserNew = monitoringInfluentialUserRemote.getMostInfluentialFan(mostPositiveUser.getKey(), monitoring.getIdMonitoring(), 
					Profile.TWITTER, dateInterval.getDateFrom(), dateInterval.getDateUntil()); 
			
			monitoringPositiveUserNew.setPostsCount(mostPositiveUser.getValue());
			
			monitoringPositiveUsers.add(MountDTO.mountMonitoringInfluentialUser(monitoringPositiveUserNew));
		}
		
		monitoringPositiveUsers = updateProfileImageURLs(monitoringPositiveUsers);
		
		monitoringReportDTO.setMonitoringPositiveUsers(monitoringPositiveUsers);
		
		Map<String, Long> mostNegativeUsers = monitoringInfluentialUserRemote.getMostNegativeFans(monitoring.getIdMonitoring(), 
				Profile.TWITTER, dateInterval.getDateFrom(), dateInterval.getDateUntil(), 0, 100);
		
		List<MonitoringInfluentialUserDTO> monitoringNegativeUsers = new ArrayList<MonitoringInfluentialUserDTO>();
		for(Map.Entry<String, Long> mostNegativeUser : mostNegativeUsers.entrySet()) {
			
			MonitoringInfluentialUser monitoringInfluentialUser = monitoringInfluentialUserRemote.getMostInfluentialFan(
					mostNegativeUser.getKey(), monitoring.getIdMonitoring(), Profile.TWITTER, dateInterval.getDateFrom(), dateInterval.getDateUntil());
			
			monitoringInfluentialUser.setPostsCount(mostNegativeUser.getValue());
			
			monitoringNegativeUsers.add(MountDTO.mountMonitoringInfluentialUser(monitoringInfluentialUser));
		}
		
		monitoringNegativeUsers = updateProfileImageURLs(monitoringNegativeUsers);
		
		monitoringReportDTO.setMonitoringNegativeUsers(monitoringNegativeUsers);
		
		monitoringReportDTO.setPostsUsersPerDay(monitoringUserReportRemote.getPostsUsersPerDayCount(monitoring.getIdMonitoring(), 
				dateInterval.getDateFrom(), dateInterval.getDateUntil()));
		
		monitoringReportDTO.setPostsUsersTotal(getPostsUsersTotal(monitoringReportDTO.getPostsUsersPerDay().values()));
		
		monitoringReportDTO.setPostsUsersAverage(Calc.calcAverage(monitoringReportDTO.getPostsUsersTotal().doubleValue(), monitoringReportDTO.getPostsUsersPerDay().size(), 0).intValue());
		monitoringReportDTO.setPostsUsersRecurrentPerc(Calc.calcPercentageNormal(
				monitoringInfluentialUserRemote.getPostsUsersRecurrent(monitoring.getIdMonitoring(), 
				dateInterval.getDateFrom(), dateInterval.getDateUntil()).doubleValue(), monitoringReportDTO.getPostsUsersTotal().doubleValue(), 2));

		Map<String, Long> postsUsersGenderCount = monitoringUserReportRemote.getPostsUsersGenderCount(monitoring.getIdMonitoring(), 
				dateInterval.getDateFrom(), dateInterval.getDateUntil());
		
		Long postsUsersGenderTotal = getPostsUsersGenderTotal(postsUsersGenderCount.values());
		
		if(postsUsersGenderCount.containsKey(MonitoringUserReport.USERS_FEMALE_COUNT)) {
			monitoringReportDTO.setWomenPostsUsersPerc(Calc.calcPercentageNormal(
					postsUsersGenderCount.get(MonitoringUserReport.USERS_FEMALE_COUNT).doubleValue(), postsUsersGenderTotal.doubleValue(), 0).intValue());
		}
		
		if(postsUsersGenderCount.containsKey(MonitoringUserReport.USERS_MALE_COUNT)) {
			monitoringReportDTO.setMenPostsUsersPerc(Calc.calcPercentageNormal(
					postsUsersGenderCount.get(MonitoringUserReport.USERS_MALE_COUNT).doubleValue(), postsUsersGenderTotal.doubleValue(), 0).intValue());
		}
		
		monitoringReportDTO.setPostsCities(monitoringLocationReportRemote.getPostsCitiesCount(monitoring.getIdMonitoring(), 
				dateInterval.getDateFrom(), dateInterval.getDateUntil(), 0, 10));
		
		monitoringReportDTO.setHashTags(monitoringHashTagRemote.getHashTagsCount(monitoring.getIdMonitoring(), 
				dateInterval.getDateFrom(), dateInterval.getDateUntil(), 0, 10));
		
		monitoringReportDTO.setLinks(monitoringLinkRemote.getLinksCount(monitoring.getIdMonitoring(), 
				dateInterval.getDateFrom(), dateInterval.getDateUntil(), 0, 10));
	
		monitoringReportDTO.setSentimentAnalysis(monitoringQualificationReportRemote.getSentimentAnalysis(monitoring.getIdMonitoring(), dateInterval.getDateFrom(), dateInterval.getDateUntil()));

		Long qualifiedPostsTotal = 0L;
		Long positivePosts = 0L;
		Long negativePosts = 0L;
		for(Map.Entry<Character, Long> sentimentAnalysis : monitoringReportDTO.getSentimentAnalysis().entrySet()) {
			qualifiedPostsTotal = qualifiedPostsTotal + sentimentAnalysis.getValue();
			if(!sentimentAnalysis.getKey().equals('N')) {
				positivePosts = positivePosts + sentimentAnalysis.getValue();
			} else {
				negativePosts = negativePosts + sentimentAnalysis.getValue();
			}
		}
		monitoringReportDTO.setQualifiedPostsTotal(qualifiedPostsTotal);
		monitoringReportDTO.setApprovalRatePerc(Calc.calcPercentageNormal(positivePosts.doubleValue(), qualifiedPostsTotal.doubleValue(), 0).intValue());
		monitoringReportDTO.setDisapprovalRatePerc(Calc.calcPercentageNormal(negativePosts.doubleValue(), qualifiedPostsTotal.doubleValue(), 0).intValue());
		
		List<MonitoringTermReportDTO> monitoringTermReports = new ArrayList<MonitoringTermReportDTO>();
		String[] terms = monitoring.getTerms().split(";");
		for(String term : terms) {
			
			MonitoringTermReportDTO monitoringTermReport = new MonitoringTermReportDTO();
			monitoringTermReport.setTerm(term);
			
			monitoringTermReport.setSentimentAnalysis(monitoringQualificationReportRemote.getSentimentAnalysis(monitoring.getIdMonitoring(), term, dateInterval.getDateFrom(), dateInterval.getDateUntil()));
			
			monitoringTermReport.setTotalPosts(monitoringTermReportRemote.getPostsTotalByTerm(
					monitoring.getIdMonitoring(), term, dateInterval.getDateFrom(), dateInterval.getDateUntil()));
			
			Long qualifiedPostsByTerm = 0L;
			for(Map.Entry<Character, Long> sentimentAnalysis : monitoringTermReport.getSentimentAnalysis().entrySet()) {
				qualifiedPostsByTerm = qualifiedPostsByTerm + sentimentAnalysis.getValue();
			}
			
			Map<Character, Integer> sentimentAnalysisPerc = new HashMap<Character, Integer>();
			for(Map.Entry<Character, Long> sentimentAnalysis : monitoringTermReport.getSentimentAnalysis().entrySet()) {
				sentimentAnalysisPerc.put(sentimentAnalysis.getKey(), Calc.calcPercentageNormal(sentimentAnalysis.getValue().doubleValue(), qualifiedPostsByTerm.doubleValue(), 0).intValue());
			}
			
			monitoringTermReport.setSentimentAnalysisPerc(sentimentAnalysisPerc);
			
			monitoringTermReports.add(monitoringTermReport);
		}
		
		monitoringReportDTO.setMonitoringTermReports(monitoringTermReports);
		
		Map<String, Long> mostInfluentialUsers = monitoringInfluentialUserRemote.getMostInfluentialFans(monitoring.getIdMonitoring(), Profile.TWITTER, 
				dateInterval.getDateFrom(), dateInterval.getDateUntil(), 0, 100);
		
		List<MonitoringInfluentialUserDTO> monitoringInfluentialUsers = new ArrayList<MonitoringInfluentialUserDTO>();
		for(Map.Entry<String, Long> monitoringInfluentialUser : mostInfluentialUsers.entrySet()) {
			
			MonitoringInfluentialUser monitoringInfluentialUserNew = monitoringInfluentialUserRemote.getMostInfluentialFan(monitoringInfluentialUser.getKey(), monitoring.getIdMonitoring(), 
					Profile.TWITTER, dateInterval.getDateFrom(), dateInterval.getDateUntil()); 
			
			monitoringInfluentialUserNew.setPostsCount(monitoringInfluentialUserRemote.getMostInfluentialFanPostsCount(
						monitoringInfluentialUserNew.getMonitoringUser().getIdUser(), 
					monitoringInfluentialUserNew.getMonitoring().getIdMonitoring(), monitoringInfluentialUserNew.getMonitoringUser().getNetworkType(), 
					dateInterval.getDateFrom(), dateInterval.getDateUntil()));
			
			monitoringInfluentialUsers.add(MountDTO.mountMonitoringInfluentialUser(monitoringInfluentialUserNew));
		}
		
		monitoringInfluentialUsers = updateProfileImageURLs(monitoringInfluentialUsers);
		
		monitoringReportDTO.setMonitoringInfluentialUsers(monitoringInfluentialUsers);
		
		List<MonitoringTagDTO> monitoringTags = monitoring.getMonitoringTags();
		List<MonitoringTagReportDTO> monitoringTagReports = new ArrayList<MonitoringTagReportDTO>();
		for(MonitoringTagDTO monitoringTag : monitoringTags) {
			
			MonitoringTagReportDTO monitoringTagReport = new MonitoringTagReportDTO();
			monitoringTagReport.setMonitoringTag(monitoringTag);
			
			monitoringTagReport.setTaggedPostsPerNetwork(monitoringTagReportRemote.getTaggedPostsPerNetwork(
					monitoring.getIdMonitoring(), monitoringTag.getIdTag(), dateInterval.getDateFrom(), dateInterval.getDateUntil()));

			Long taggedPosts = 0L;
			for(Map.Entry<Character, Long> taggerPostsPerNetwork : monitoringTagReport.getTaggedPostsPerNetwork().entrySet()) {
				taggedPosts = taggedPosts + taggerPostsPerNetwork.getValue();
			}
			
			monitoringTagReport.setTotalTaggedPosts(taggedPosts);
			
			monitoringTagReports.add(monitoringTagReport);
		}
		
		monitoringReportDTO.setMonitoringTagReports(monitoringTagReports);
		
		if(format.equals(XLS)) {
			monitoringReportDTO.setPostsPerDayNetworks(monitoringReportRemote.getPostsPerDayNetworks(monitoring.getIdMonitoring(), dateInterval.getDateFrom(), dateInterval.getDateUntil()));
			monitoringReportDTO.setMostFrequentWords(UtilMonitorings.getMostFrequentWords(monitoring, dateInterval));
			monitoringReportDTO.setMostFrequentWordsPositive(UtilMonitorings.getMostFrequentWords(monitoring, dateInterval, PostMonitoringDTO.POSITIVE));
			monitoringReportDTO.setMostFrequentWordsNegative(UtilMonitorings.getMostFrequentWords(monitoring, dateInterval, PostMonitoringDTO.NEGATIVE));
		}
		
		if(!format.equals(UtilMonitorings.XLS)) {
			
			monitoringReportDTO.setMonitoringGeoPostsJSON(getMonitoringGeoPostsJSON(monitoringPostLocationRemote.getGetPosts(monitoring.getIdMonitoring(), dateInterval.getDateFrom(), dateInterval.getDateUntil(), 0, MonitoringReportDTO.GEO_POSTS_LIMIT)));
			
			// Cloud Tags Positive Posts
			Cloud cloudTagsPositivePosts = new Cloud();
			cloudTagsPositivePosts.setMaxWeight(50);
			cloudTagsPositivePosts.setMinWeight(10);
			cloudTagsPositivePosts.setMaxTagsToDisplay(25);

			MaintenanceTwitterPostMonitoringRemote twitterPostMonitoringRemote = (MaintenanceTwitterPostMonitoringRemote)
					RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");
			
			populateCloud(null, twitterPostMonitoringRemote.listTwitterPostsText(monitoring.getIdMonitoring(), 
					dateInterval.getDateFrom(), dateInterval.getDateUntil(), 100L, PostMonitoringDTO.POSITIVE), cloudTagsPositivePosts);
			
			String htmlTagsPositivePosts = "";
			
			for(Tag tag : cloudTagsPositivePosts.tags()) {	
				htmlTagsPositivePosts += "<font style='opacity: " + new BigDecimal((tag.getWeight() + 50) / 100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() + 
					";filter: alpha(opacity = " + (tag.getWeightInt() + 50)  + ");zoom: 1;font-family: Source Sans Pro; font-weight:600; color:#53970b;font-size:" + tag.getWeight() + "px;margin-right:5px;word-break:break-all;'>" + tag.getName() + "   </font>";
			}
			
			monitoringReportDTO.setCloudTagsPositivePosts(htmlTagsPositivePosts);
			
			// Cloud Tags Negative Posts
			Cloud cloudTagsNegativePosts = new Cloud();
			cloudTagsNegativePosts.setMaxWeight(50);
			cloudTagsNegativePosts.setMinWeight(10);
			cloudTagsNegativePosts.setMaxTagsToDisplay(25);
			
			populateCloud(null, twitterPostMonitoringRemote.listTwitterPostsText(monitoring.getIdMonitoring(), 
					dateInterval.getDateFrom(), dateInterval.getDateUntil(), 100L, PostMonitoringDTO.NEGATIVE), cloudTagsNegativePosts);

			String htmlTagsNegativePosts = "";

			for(Tag tag : cloudTagsNegativePosts.tags()) {	
				htmlTagsNegativePosts += "<font style='opacity: " + new BigDecimal((tag.getWeight() + 50) / 100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() + 
						";filter: alpha(opacity = " + (tag.getWeightInt() + 50)  + ");zoom: 1;font-family: Source Sans Pro; font-weight:600; color:#d53525;font-size:" + tag.getWeight() + "px;margin-right:5px;word-break:break-all;'>" + tag.getName() + "   </font>";
			}

			monitoringReportDTO.setCloudTagsNegativePosts(htmlTagsNegativePosts);
			
			monitoringReportDTO.setCloud(mountCloud(monitoring, dateInterval));
		}
		
		return monitoringReportDTO;
	}
	
	public static List<MonitoringInfluentialUserDTO> updateProfileImageURLs(List<MonitoringInfluentialUserDTO> monitoringInfluentialUsers) {
		
		try {
			
			TwitterIntegration twitterIntegration = new TwitterIntegration();
			Twitter twitter = twitterIntegration.getTwitterApp(); 
			
			long[] followersId = new long[monitoringInfluentialUsers.size()];
			for(int i = 0; i < monitoringInfluentialUsers.size(); i++) {
				followersId[i] = Long.valueOf(monitoringInfluentialUsers.get(i).getMonitoringUser().getIdUser());
			}
			
			ResponseList<User> users = twitter.lookupUsers(followersId);
			
			for(User user : users) {
				for(MonitoringInfluentialUserDTO monitoringInfluentialUserDTO : monitoringInfluentialUsers) {
					if(user.getId() == Long.valueOf(monitoringInfluentialUserDTO.getMonitoringUser().getIdUser())) {
						monitoringInfluentialUserDTO.getMonitoringUser().setImageProfile(user.getProfileImageURLHttps());
						break;
					}
				}
			}
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		return monitoringInfluentialUsers;
	}
	
	public static String getMonitoringGeoPostsJSON(List<MonitoringGeoPost> monitoringGeoPosts) {
		
		Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		
		JsonArray monitoringGeoPostsJSON = new JsonArray();
		
		for(MonitoringGeoPost monitoringGeoPost : monitoringGeoPosts) {
			monitoringGeoPostsJSON.add(gson.toJsonTree(monitoringGeoPost));
		}
		
		return monitoringGeoPostsJSON.toString();
	}
	
	public static GenericDateInterval getReportDatesByPeriod(Integer period, MonitoringDTO monitoring) {
		
		Calendar untilDate = new GregorianCalendar();
		untilDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - 1);
		
		Calendar fromDate;
		
		if(period > 0) {
			
			fromDate = new GregorianCalendar(untilDate.get(Calendar.YEAR),
												untilDate.get(Calendar.MONTH),
												untilDate.get(Calendar.DAY_OF_MONTH));

			fromDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - (period - 1));
			
		} else {
			
			fromDate = Calendar.getInstance();
			fromDate.setTimeInMillis(monitoring.getCreatedAt() * 1000L);
		}
		
		GenericDateInterval genericDateInterval = new GenericDateInterval();
		genericDateInterval.setDateUntil(UtilFunctions.getMidnightDateNoDaylightTime(untilDate.getTime()));
		genericDateInterval.setDateFrom(UtilFunctions.getMidnightDateNoDaylightTime(fromDate.getTime()));
		
		return genericDateInterval;
	}
	
	private static Long getPostsUsersGenderTotal(Collection<Long> postsUsersGenders) {
		
		Long postsUsersGendersTotal = 0L;
		
		for (Long postsUsersGender : postsUsersGenders) {
			postsUsersGendersTotal += postsUsersGender;
		}
		
		return postsUsersGendersTotal;
	}
	
	private static Long getPostsUsersTotal(Collection<Long> postsUsersCount) {
		
		Long postsUsersTotal = 0L;
		
		for (Long postUserCount : postsUsersCount) {
			postsUsersTotal += postUserCount;
		}
		
		return postsUsersTotal;
	}
	
	private static Object[] getWeekDayWithMorePosts(Map<Integer, Map<Character, Long>> postsPerWeekDaysNetworks, Long postsTotal) {
		
		Integer majorWeekDayWithMorePosts = 0;
		Long weekDayWithMorePostsValue = 0L;
		
		for(Map.Entry<Integer, Map<Character, Long>> postsPerWeekDayNetworks : postsPerWeekDaysNetworks.entrySet()) {
			
			Long postsPerWeekDay = 0L;
			
			postsPerWeekDay += postsPerWeekDayNetworks.getValue().containsKey('F') ? postsPerWeekDayNetworks.getValue().get('F') : 0L;
			postsPerWeekDay += postsPerWeekDayNetworks.getValue().containsKey('T') ? postsPerWeekDayNetworks.getValue().get('T') : 0L;
			postsPerWeekDay += postsPerWeekDayNetworks.getValue().containsKey('G') ? postsPerWeekDayNetworks.getValue().get('G') : 0L;
			postsPerWeekDay += postsPerWeekDayNetworks.getValue().containsKey('I') ? postsPerWeekDayNetworks.getValue().get('I') : 0L;
			postsPerWeekDay += postsPerWeekDayNetworks.getValue().containsKey('Y') ? postsPerWeekDayNetworks.getValue().get('Y') : 0L;
			postsPerWeekDay += postsPerWeekDayNetworks.getValue().containsKey('R') ? postsPerWeekDayNetworks.getValue().get('R') : 0L;
			
			if(postsPerWeekDay > weekDayWithMorePostsValue) {
				weekDayWithMorePostsValue = postsPerWeekDay;
				majorWeekDayWithMorePosts = postsPerWeekDayNetworks.getKey();
			}
		}
		
		Object[] weekDayWithMorePosts = new Object[2];
		weekDayWithMorePosts[0] = majorWeekDayWithMorePosts;
		weekDayWithMorePosts[1] = Calc.calcPercentageNormal(weekDayWithMorePostsValue.doubleValue(), postsTotal.doubleValue(), 0).intValue();
		
		return weekDayWithMorePosts;
	}
	
	private static Object[] getHighestPostsVolume(Map<Character, Long> postsPerDaysNetworks, Long postsTotal) {
		
		// Twitter é a padrão já que é a mais importante, caso não haja resultados para nenhuma rede.
		Character highestPostsNetwork = 'T';
		Long highestPostsVolumeValue = 0L;
		
		for (Map.Entry<Character, Long> postsPerDaysNetwork : postsPerDaysNetworks.entrySet()) {
			
			if(postsPerDaysNetwork.getValue() > highestPostsVolumeValue) {
				highestPostsVolumeValue = postsPerDaysNetwork.getValue();
				highestPostsNetwork = postsPerDaysNetwork.getKey();
			}
		}
		
		Object[] highestPostsVolumeNetwork = new Object[2];
		highestPostsVolumeNetwork[0] = highestPostsNetwork;
		highestPostsVolumeNetwork[1] = Calc.calcPercentageNormal(highestPostsVolumeValue.doubleValue(), postsTotal.doubleValue(), 0).intValue();
		
		return highestPostsVolumeNetwork;
	}
	
	private static Object[] getTimeWithMorePosts(Map<Integer, Map<Character, Long>> postsPerHoursNetworks, Long postsTotal) {
		
		Long majorPostsTimeValue = 0L;
		Integer majorPostsTime = 0;
		
		for(Map.Entry<Integer, Map<Character, Long>> postsPerHourNetworks : postsPerHoursNetworks.entrySet()) {
			long postsPerHour = postsPerHourNetworks.getValue().get('F') + postsPerHourNetworks.getValue().get('T') +
					postsPerHourNetworks.getValue().get('G') + postsPerHourNetworks.getValue().get('Y') + postsPerHourNetworks.getValue().get('I');
			
			if(postsPerHour > majorPostsTimeValue) {
				majorPostsTimeValue = postsPerHour;
				majorPostsTime = postsPerHourNetworks.getKey();
			}
		}
		
		Object[] timeWithMorePosts = new Object[2];
		timeWithMorePosts[0] = majorPostsTime;
		timeWithMorePosts[1] = Calc.calcPercentageNormal(majorPostsTimeValue.doubleValue(), postsTotal.doubleValue(), 0).intValue();
		
		return timeWithMorePosts;
	}
	
	public static String mountCloud(MonitoringDTO monitoring, GenericDateInterval genericDateInterval) {
		
		//Nuvem de tags ------------------------------------------------------------
	
		Cloud cloud = new Cloud();
		cloud.setMaxWeight(50);
		cloud.setMinWeight(10);
		cloud.setMaxTagsToDisplay(50);
		
		MaintenanceTwitterPostMonitoringRemote twitterPostMonitoringRemote = (MaintenanceTwitterPostMonitoringRemote)
				RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");
		
		List<String> textContents = twitterPostMonitoringRemote.listTwitterPostsText(monitoring.getIdMonitoring(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil(), 100L);
		
		populateCloud(null, textContents, cloud);
		
		String html = "";
		
		for(Tag tag : cloud.tags()) {	
			html += "<font style='opacity: " + new BigDecimal((tag.getWeight() + 50) / 100).setScale(2,BigDecimal.ROUND_HALF_UP).doubleValue() + 
				";filter: alpha(opacity = " + (tag.getWeightInt() + 50)  + ");zoom: 1;font-family: Source Sans Pro; font-weight:600; color:#0079ae;font-size:" + tag.getWeight() + "px;margin-right:5px;'>" + tag.getName() + "   </font>";
		}
		
		return html;
	}
	
	public static List<String> getMostFrequentWords(MonitoringDTO monitoring, GenericDateInterval dateInterval, Character qualification) {
		
		Cloud cloudTags = new Cloud();
		cloudTags.setMaxWeight(50);
		cloudTags.setMinWeight(10);
		cloudTags.setMaxTagsToDisplay(25);
		
		MaintenanceTwitterPostMonitoringRemote twitterPostMonitoringRemote = (MaintenanceTwitterPostMonitoringRemote)
				RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");
		
		List<String> textContents = twitterPostMonitoringRemote.listTwitterPostsText(monitoring.getIdMonitoring(), 
				dateInterval.getDateFrom(), dateInterval.getDateUntil(), 100L, qualification);
		
		populateCloud(null, textContents, cloudTags);

		List<String> mostFrequentWords = new ArrayList<String>();
		
		for(Tag tag : cloudTags.tags(new Tag.ScoreComparatorDesc())) {
			mostFrequentWords.add(tag.getName());
		}
		
		return mostFrequentWords;
		
	}
	
	public static List<String> getMostFrequentWords(MonitoringDTO monitoring, GenericDateInterval genericDateInterval) {
		
		Cloud cloud = new Cloud();
		cloud.setMaxWeight(50);
		cloud.setMinWeight(10);
		cloud.setMaxTagsToDisplay(50);

		MaintenanceTwitterPostMonitoringRemote twitterPostMonitoringRemote = (MaintenanceTwitterPostMonitoringRemote)
				RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");
		
		List<String> textContents = twitterPostMonitoringRemote.listTwitterPostsText(monitoring.getIdMonitoring(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil(), 100L);
		
		populateCloud(null, textContents, cloud);
		
		List<String> mostFrequentWords = new ArrayList<String>();
		
		for(Tag tag : cloud.tags(new Tag.ScoreComparatorDesc())) {
			mostFrequentWords.add(tag.getName());
		}
		
		return mostFrequentWords;
	}
	
	private static Date getDayWithMorePosts(Map<Date, Long> postsPerDay, Long dateUntil) {
		
		Long majorPostsDay = 0L;
		Date dayWithMorePosts = null;
		
		for (Map.Entry<Date, Long> postsDay : postsPerDay.entrySet()) {
			
			if(postsDay.getValue() > majorPostsDay) { 
				majorPostsDay = postsDay.getValue();
				dayWithMorePosts = postsDay.getKey();
			}
			
		}

		if(dayWithMorePosts == null) {
			dayWithMorePosts = new Date(dateUntil * 1000L); 
		}
		
		return dayWithMorePosts;
	}
	
	private static void populateCloud(List<String[]> arrayMessages, List<String> messages, Cloud cloud) {

		if(arrayMessages != null) {
			
			for(Object[] message : arrayMessages) {
				
				for(Object text : message) {
					
					if(text != null) {
				
						for(String word : text.toString().split(" ")) {
							
							if(word.length() > 5 && !word.contains("http://")) {
			
								word = word.replace(".", "");
								word = word.replace("\"", "");
								word = word.replace("'", "");
								word = word.replace(",", "");
								word = word.replace(";", "");
								word = word.replace("!", "");
								word = word.replace("?", "");
								
								Tag tag = new Tag(word);
								cloud.addTag(tag);	
							}
						}
					}
				}
			}
		} else if(messages != null) {
			
			for(Object message : messages) {
				
				if(message != null) {
			
					for(String word : message.toString().split(" ")) {
						
						if(word.length() > 5 && !word.contains("http://")) {
		
							word = word.replace(".", "");
							word = word.replace("\"", "");
							word = word.replace("'", "");
							word = word.replace(",", "");
							word = word.replace(";", "");
							word = word.replace("!", "");
							word = word.replace("?", "");
							
							Tag tag = new Tag(word);
							cloud.addTag(tag);	
						}
					}
				}
			}
		}	
	}

	public static String mountCloud(Monitoring monitoringDTO, GenericDateInterval genericDateInterval) {
		// TODO Auto-generated method stub
		return null;
	}
}
