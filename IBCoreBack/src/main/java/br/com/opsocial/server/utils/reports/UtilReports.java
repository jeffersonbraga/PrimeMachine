package br.com.opsocial.server.utils.reports;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.naming.NamingException;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.client.entity.report.facebook.FaceReportPostDTO;
import br.com.opsocial.client.entity.report.facebook.FacebookInfluentialUserDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookContentDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookDashboardDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookHistoricalDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookOverviewDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookPostCommentsDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookPostToPostDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramInteractionRankingDTO;
import br.com.opsocial.client.entity.report.instagram.InstagramPostsStatsYearDTO;
import br.com.opsocial.client.entity.report.instagram.ReportInstagramHistoricalDTO;
import br.com.opsocial.client.entity.report.instagram.ReportInstagramOverviewDTO;
import br.com.opsocial.client.entity.report.linkedin.ReportLinkedinOverviewDTO;
import br.com.opsocial.client.entity.report.twitter.ReportTwitterDashboardDTO;
import br.com.opsocial.client.entity.report.twitter.ReportTwitterOverviewDTO;
import br.com.opsocial.client.entity.report.twitter.TwitterReportDTO;
import br.com.opsocial.client.entity.report.twitter.TwitterReportStatusDTO;
import br.com.opsocial.ejb.das.MaintenanceFaceReportLocationRemote;
import br.com.opsocial.ejb.das.MaintenanceFaceReportPostCommentRemote;
import br.com.opsocial.ejb.das.MaintenanceFaceReportPostRemote;
import br.com.opsocial.ejb.das.MaintenanceFacebookInfluentialUserRemote;
import br.com.opsocial.ejb.das.MaintenanceFacebookPostInsightRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostCommentRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostLikeRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostTagRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramReportPostRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramReportRemote;
import br.com.opsocial.ejb.das.MaintenanceReportControlRemote;
import br.com.opsocial.ejb.das.MaintenanceReportDetailedRemote;
import br.com.opsocial.ejb.das.MaintenanceReportGenerateRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterFollowerRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterFollowerReportRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterReportRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterStatusRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterStatusReportRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.instagram.InstagramReportPost;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;
import br.com.opsocial.ejb.entity.report.AgeGroup;
import br.com.opsocial.ejb.entity.report.FaceReportPost;
import br.com.opsocial.ejb.entity.report.FacebookInfluentialUser;
import br.com.opsocial.ejb.entity.report.FanPageGrowth;
import br.com.opsocial.ejb.entity.report.PropertyValue;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;
import br.com.opsocial.ejb.entity.report.SumByDate;
import br.com.opsocial.ejb.entity.report.TwitterReport;
import br.com.opsocial.server.utils.Calc;
import br.com.opsocial.server.utils.GenericDateInterval;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;

public abstract class UtilReports {

	public static final Long INSIGHTS_POSTS_INTERVAL = 1800L;
	public static final Integer DASHBOARD_INSIGHTS_PERIOD = 14;

	public static final String PAGE_FANS_FROM_PAGES = "pageFansFromPages";
	public static final String PAGE_FANS_FROM_RECOMMENDATIONS = "pageFansFromRecommendations";
	public static final String PAGE_FANS_FROM_LIKE_SUGGESTIONS = "pageFansFromLikeSuggestions";

	public UtilReports() {

	}

	public static ReportInstagramOverviewDTO mountReportInstagramOverviewDTO(Profile profile, GenericDateInterval genericDateInterval) {

		ReportInstagramOverviewDTO reportInstagramOverview = new ReportInstagramOverviewDTO();

		MaintenanceInstagramReportPostRemote instagramReportPostRemote = (MaintenanceInstagramReportPostRemote)
				RecoverMaintenance.recoverMaintenance("InstagramReportPost");

		MaintenanceInstagramPostCommentRemote instagramPostCommentRemote = (MaintenanceInstagramPostCommentRemote)
				RecoverMaintenance.recoverMaintenance("InstagramPostComment");

		MaintenanceInstagramPostLikeRemote instagramPostLikeRemote = (MaintenanceInstagramPostLikeRemote)
				RecoverMaintenance.recoverMaintenance("InstagramPostLike");

		MaintenanceInstagramReportRemote instagramReportRemote = (MaintenanceInstagramReportRemote)
				RecoverMaintenance.recoverMaintenance("InstagramReport");

		MaintenanceInstagramPostTagRemote instagramPostTagRemote = (MaintenanceInstagramPostTagRemote)
				RecoverMaintenance.recoverMaintenance("InstagramPostTag");

		MaintenanceReportControlRemote reportControlRemote = (MaintenanceReportControlRemote)
				RecoverMaintenance.recoverMaintenance("ReportControl");

		reportInstagramOverview.setDateFrom(genericDateInterval.getDateFrom());
		reportInstagramOverview.setDateUntil(genericDateInterval.getDateUntil());

		Long firstDate = reportControlRemote.getFirstDate(profile.getNetworkId(), Profile.INSTAGRAM);
		Long dateFromVerified = genericDateInterval.getDateFrom();
		Long dateForFollowersTotal = genericDateInterval.getDateUntil() + 86400L;

		if(genericDateInterval.getDateFrom() <= firstDate) {
			dateFromVerified = firstDate + 86400L;
		}

		if(genericDateInterval.getDateUntil() < firstDate) {
			dateForFollowersTotal = firstDate + 86400L;
		}

		reportInstagramOverview.setFollowers(instagramReportRemote.getEntityBeforeDate(profile.getNetworkId(), 
				ReportInstagram.PROFILE_FOLLOWERS, dateForFollowersTotal).getValue());

		reportInstagramOverview.setInstagramFollowerTrends(instagramReportRemote.getInstagramFollowersTrends(profile.getNetworkId(), 
				dateFromVerified, genericDateInterval.getDateUntil()));

		reportInstagramOverview.setInstagramFollowerTrend(instagramReportRemote.getInstagramFollowersTrend(profile.getNetworkId(), 
				dateFromVerified, genericDateInterval.getDateUntil()));

		reportInstagramOverview.setLikesPerDay(instagramPostLikeRemote.getLikesPerDay(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportInstagramOverview.setLikes(instagramPostLikeRemote.getCountOfLikes(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportInstagramOverview.setCommentsPerDay(instagramPostCommentRemote.getCommentsPerDay(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportInstagramOverview.setComments(instagramPostCommentRemote.getCountOfComments(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportInstagramOverview.setImpressions(instagramReportRemote.getPropertySum(profile.getNetworkId(), ReportInstagram.IMPRESSIONS, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportInstagramOverview.setReach(instagramReportRemote.getPropertySum(profile.getNetworkId(), ReportInstagram.REACH, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportInstagramOverview.setProfileViews(instagramReportRemote.getPropertySum(profile.getNetworkId(), ReportInstagram.PROFILE_VIEWS, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportInstagramOverview.setWebsiteClicks(instagramReportRemote.getPropertySum(profile.getNetworkId(), ReportInstagram.WEBSITE_CLICKS, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		if(!reportInstagramOverview.getLikesPerDay().isEmpty()) {
			reportInstagramOverview.setLikesAverage(
					new BigDecimal(reportInstagramOverview.getLikes().doubleValue() / Double.valueOf(reportInstagramOverview.getLikesPerDay().size())).setScale(0, BigDecimal.ROUND_HALF_DOWN).doubleValue());
		} else {
			reportInstagramOverview.setLikesAverage(0D);
		}

		if(!reportInstagramOverview.getCommentsPerDay().isEmpty()) {
			reportInstagramOverview.setCommentsAverage(
					new BigDecimal(reportInstagramOverview.getComments().doubleValue() / Double.valueOf(reportInstagramOverview.getCommentsPerDay().size())).setScale(0, BigDecimal.ROUND_HALF_DOWN).doubleValue());
		} else {
			reportInstagramOverview.setCommentsAverage(0D);
		}

		reportInstagramOverview.setPostsWithMoreLikes(MountDTO.mountInstagramReportPost(instagramReportPostRemote.getPostsWithMoreLikes(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil())));

		reportInstagramOverview.setPostsWithMoreComments(MountDTO.mountInstagramReportPost(instagramReportPostRemote.getPostsWithMoreComments(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil())));

		reportInstagramOverview.setInstagramTopPostTags(instagramPostTagRemote.getInstagramTopPostTags(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportInstagramOverview.setLikesPerHour(instagramPostLikeRemote.getLikesPerHour(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		Object[] timeWithMoreLikes = instagramPostLikeRemote.getTimeWithMoreLikes(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportInstagramOverview.setTimeWithMoreLikes((Integer) timeWithMoreLikes[0]);
		reportInstagramOverview.setTimeWithMoreLikesPerc((Integer) timeWithMoreLikes[1]);

		Object[] timeWithLessLikes = instagramPostLikeRemote.getTimeWithLessLikes(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportInstagramOverview.setTimeWithLessLikes((Integer) timeWithLessLikes[0]);
		reportInstagramOverview.setTimeWithLessLikesPerc((Integer) timeWithLessLikes[1]);

		reportInstagramOverview.setCommentsPerHour(instagramPostCommentRemote.getCommentsPerHour(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		Object[] timeWithMoreComments = instagramPostCommentRemote.getTimeWithMoreComments(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportInstagramOverview.setTimeWithMoreComments((Integer) timeWithMoreComments[0]);
		reportInstagramOverview.setTimeWithMoreCommentsPerc((Integer) timeWithMoreComments[1]);

		Object[] timeWithLessComments = instagramPostCommentRemote.getTimeWithLessComments(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportInstagramOverview.setTimeWithLessComments((Integer) timeWithLessComments[0]);
		reportInstagramOverview.setTimeWithLessCommentsPerc((Integer) timeWithLessComments[1]);

		reportInstagramOverview.setInteractionsPerHour(UtilReports.getInteractionsPerHour(reportInstagramOverview.getLikesPerHour(), 
				reportInstagramOverview.getCommentsPerHour()));

		reportInstagramOverview.setInstagramInteractionsRanking(UtilReports.getInstagramInteractionsRanking(
				reportInstagramOverview.getInteractionsPerHour()));

		/**
		 * 
		 * 
		 * ROTINA INSERIDA PARA DADOS INSTAGRAM AUDIENCES
		 */
		//this.getInstagramAudiences(reportInstagramOverview, profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil());
		reportInstagramOverview.setAudiencesFollowersTrend(instagramReportRemote.getAudiencesFollowersTrend(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));
		reportInstagramOverview.setAudiencesDemograph(instagramReportRemote.getAudiencesDemograph(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));

		reportInstagramOverview.setAudiencesAveragePublicAge(instagramReportRemote.getAudiencesAveragePublicAge(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));
		reportInstagramOverview.setAudiencesAveragePublicGender(instagramReportRemote.getAudiencesAveragePublicGender(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));

		reportInstagramOverview.setAudiencesTopCountry(instagramReportRemote.getAudiencesTopCountry(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));
		reportInstagramOverview.setAudiencesTopCities(instagramReportRemote.getAudiencesTopCity(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));
		reportInstagramOverview.setAudiencesOnlineFollowersByHour(instagramReportRemote.getAudiencesOnlineFollowersByHour(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));
		reportInstagramOverview.setAudiencesOnlineFollowersByDay(instagramReportRemote.getAudiencesOnlineFollowersByDay(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));
		reportInstagramOverview.setAudiencesPostsInteraction(instagramReportRemote.getAudiencesPostsInteraction(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));

		reportInstagramOverview.setLikesPerDayFollowers(instagramReportRemote.getLikesPerDayFollowers(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));
		reportInstagramOverview.setLikesPerDayNoFollowers(instagramReportRemote.getLikesPerDayNoFollowers(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));

		reportInstagramOverview.setCommentsPerDayCompare(instagramReportRemote.getCommentsPerDay(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));

		reportInstagramOverview.setLikesPerHourSimpleData(instagramPostLikeRemote.getLikesPerHourSimpleData(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportInstagramOverview.setCommentsPerHourSimpleData(instagramPostCommentRemote.getCommentsPerHourSimpleData(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportInstagramOverview.setTotalPosts(instagramReportPostRemote.getCountOfPosts(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));

		reportInstagramOverview.setTotalPostsCarousel(instagramReportPostRemote.getCountOfPostsByMedia(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil(), ReportInstagram.POST_TYPE_CAROUSEL));

		reportInstagramOverview.setTotalPostsVideo(instagramReportPostRemote.getCountOfPostsByMedia(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil(), ReportInstagram.POST_TYPE_VIDEO));

		reportInstagramOverview.setTotalPostsImage(instagramReportPostRemote.getCountOfPostsByMedia(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil(), ReportInstagram.POST_TYPE_IMAGE));

		reportInstagramOverview.setAverageInteractionsPosts(instagramReportPostRemote.getAveragePostsInteractions(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()).setScale(0, BigDecimal.ROUND_HALF_DOWN).doubleValue());

		reportInstagramOverview.setAverageInteractionsFollower(instagramReportPostRemote.getAverageInteractionsFollower(profile.getNetworkId(), dateFromVerified, genericDateInterval.getDateUntil()));
		
		reportInstagramOverview.setPostsWithMoreInteractions(
								instagramReportPostRemote.getPostsWithMoreInteractions(profile.getNetworkId(), 
								genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		
		Comparator<InstagramReportPost> postsComparator = new Comparator<InstagramReportPost>() {
		  public int compare(InstagramReportPost s1, InstagramReportPost s2) {
			  
			if((s1.getLikes() + s1.getComments()) > (s2.getLikes() + s2.getComments())) {  
	            return -1;  
	        }  
			
	        if((s1.getLikes() + s1.getComments()) < (s2.getLikes() + s2.getComments())) {  
	            return 1;  
	        }
	        
	        return 0;
		  }
		};
		
		reportInstagramOverview.getPostsWithMoreInteractions().sort(postsComparator);
		
		return reportInstagramOverview;
	}

	public static ReportInstagramHistoricalDTO mountReportInstagramHistoricalDTO(Profile profile) {

		ReportInstagramHistoricalDTO reportInstagramHistorical = new ReportInstagramHistoricalDTO();

		MaintenanceInstagramReportPostRemote instagramReportPostRemote = (MaintenanceInstagramReportPostRemote)
				RecoverMaintenance.recoverMaintenance("InstagramReportPost");

		MaintenanceInstagramPostCommentRemote instagramPostCommentRemote = (MaintenanceInstagramPostCommentRemote)
				RecoverMaintenance.recoverMaintenance("InstagramPostComment");

		MaintenanceInstagramPostLikeRemote instagramPostLikeRemote = (MaintenanceInstagramPostLikeRemote)
				RecoverMaintenance.recoverMaintenance("InstagramPostLike");

		MaintenanceInstagramPostTagRemote instagramPostTagRemote = (MaintenanceInstagramPostTagRemote)
				RecoverMaintenance.recoverMaintenance("InstagramPostTag");

		MaintenanceInstagramReportRemote instagramReportRemote = (MaintenanceInstagramReportRemote)
				RecoverMaintenance.recoverMaintenance("InstagramReport");

		Calendar dateUntilFollowers = Calendar.getInstance();
		dateUntilFollowers.add(Calendar.DATE, +1);

		Long timestampUntilFollowers = UtilFunctions.getMidnightDateNoDaylightTime(dateUntilFollowers.getTime());

		reportInstagramHistorical.setFollowers(instagramReportRemote.getEntityBeforeDate(profile.getNetworkId(), 
				ReportInstagram.PROFILE_FOLLOWERS, timestampUntilFollowers).getValue());

		reportInstagramHistorical.setPosts(instagramReportPostRemote.getCountOfPosts(profile.getNetworkId()));
		reportInstagramHistorical.setLikes(instagramReportPostRemote.getCountOfLikes(profile.getNetworkId()));
		reportInstagramHistorical.setComments(instagramReportPostRemote.getCountOfComments(profile.getNetworkId()));

		Calendar calendarDate = Calendar.getInstance();
		calendarDate.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

		Integer currentYear = calendarDate.get(Calendar.YEAR);

		// ---------------------------- First Year Data -------------------------
		reportInstagramHistorical.setInstagramPostsStatsYearFirst(getInstagramPostsStatsYear(currentYear - 2, profile));
		
		if(reportInstagramHistorical.getInstagramPostsStatsYearFirst().getPostsTotal() != null) {
			reportInstagramHistorical.setYearWithMorePosts(reportInstagramHistorical.getInstagramPostsStatsYearFirst().getYear());
			reportInstagramHistorical.setYearWithMorePostsValue(reportInstagramHistorical.getInstagramPostsStatsYearFirst().getPostsTotal());
		}
		reportInstagramHistorical.setYearBetterPostFrequency(reportInstagramHistorical.getInstagramPostsStatsYearFirst().getYear());
		reportInstagramHistorical.setYearBetterPostFrequencyValue(reportInstagramHistorical.getInstagramPostsStatsYearFirst().getPostFrequencyByMonth());

		// ---------------------------- Second Year Data -------------------------
		reportInstagramHistorical.setInstagramPostsStatsYearSecond(getInstagramPostsStatsYear(currentYear - 1, profile));
		
		if(reportInstagramHistorical.getInstagramPostsStatsYearSecond().getPostsTotal() != null && reportInstagramHistorical.getInstagramPostsStatsYearSecond().getPostsTotal() >= reportInstagramHistorical.getYearWithMorePostsValue()) {
			reportInstagramHistorical.setYearWithMorePosts(reportInstagramHistorical.getInstagramPostsStatsYearSecond().getYear());
			reportInstagramHistorical.setYearWithMorePostsValue(reportInstagramHistorical.getInstagramPostsStatsYearSecond().getPostsTotal());
		}
		
		if(reportInstagramHistorical.getInstagramPostsStatsYearSecond().getPostFrequencyByMonth() >= reportInstagramHistorical.getYearBetterPostFrequencyValue()) {
			reportInstagramHistorical.setYearBetterPostFrequency(reportInstagramHistorical.getInstagramPostsStatsYearSecond().getYear());
			reportInstagramHistorical.setYearBetterPostFrequencyValue(reportInstagramHistorical.getInstagramPostsStatsYearSecond().getPostFrequencyByMonth());
		}
		
		// ---------------------------- Third Year Data -------------------------
		reportInstagramHistorical.setInstagramPostsStatsYearThird(getInstagramPostsStatsYear(currentYear, profile));
		
		if(reportInstagramHistorical.getInstagramPostsStatsYearThird().getPostsTotal() != null && reportInstagramHistorical.getInstagramPostsStatsYearThird().getPostsTotal() >= reportInstagramHistorical.getYearWithMorePostsValue()) {
			reportInstagramHistorical.setYearWithMorePosts(reportInstagramHistorical.getInstagramPostsStatsYearThird().getYear());
			reportInstagramHistorical.setYearWithMorePostsValue(reportInstagramHistorical.getInstagramPostsStatsYearThird().getPostsTotal());
		}
		
		if(reportInstagramHistorical.getInstagramPostsStatsYearThird().getPostFrequencyByMonth() >= reportInstagramHistorical.getYearBetterPostFrequencyValue()) {
			reportInstagramHistorical.setYearBetterPostFrequency(reportInstagramHistorical.getInstagramPostsStatsYearThird().getYear());
			reportInstagramHistorical.setYearBetterPostFrequencyValue(reportInstagramHistorical.getInstagramPostsStatsYearThird().getPostFrequencyByMonth());
		}

		reportInstagramHistorical.setPostsPerWeekDaySimpleData(instagramReportPostRemote.getPostsPerWeekSimpleData(profile.getNetworkId()));

		Object[] weekDayWithMorePosts = instagramReportPostRemote.getWeekDayWithMorePosts(profile.getNetworkId());

		reportInstagramHistorical.setWeekDayWithMorePosts((Integer) weekDayWithMorePosts[0]);
		reportInstagramHistorical.setWeekDayWithMorePostsValue((Long) weekDayWithMorePosts[1]);

		reportInstagramHistorical.setPostsPerHourSimpleData(instagramReportPostRemote.getPostsPerHourSimpleData(profile.getNetworkId()));

		Object[] hourWithMorePosts = instagramReportPostRemote.getHourWithMorePosts(profile.getNetworkId());

		reportInstagramHistorical.setHourWithMorePosts((Integer) hourWithMorePosts[0]);
		reportInstagramHistorical.setHourWithMorePostsValue((Long) hourWithMorePosts[1]);

		if(!reportInstagramHistorical.getPosts().equals(0L)) {
			reportInstagramHistorical.setLikesAverage(
					new BigDecimal(reportInstagramHistorical.getLikes().doubleValue() / reportInstagramHistorical.getPosts().doubleValue()).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
			reportInstagramHistorical.setCommentsAverage(
					new BigDecimal(reportInstagramHistorical.getComments().doubleValue() / reportInstagramHistorical.getPosts().doubleValue()).setScale(2, BigDecimal.ROUND_HALF_DOWN).doubleValue());
		} else {
			reportInstagramHistorical.setLikesAverage(0D);
			reportInstagramHistorical.setCommentsAverage(0D);
		}

		reportInstagramHistorical.setPostsWithMoreLikes(
				MountDTO.mountInstagramReportPost(instagramReportPostRemote.getPostsWithMoreLikes(profile.getNetworkId())));

		reportInstagramHistorical.setPostsWithMoreComments(
				MountDTO.mountInstagramReportPost(instagramReportPostRemote.getPostsWithMoreComments(profile.getNetworkId())));

		reportInstagramHistorical.setInstagramTopPostTags(instagramPostTagRemote.getInstagramTopPostTags(profile.getNetworkId(), 
				new Date(0).getTime(), new Date().getTime()));

		reportInstagramHistorical.setLikesPerHour(instagramPostLikeRemote.getLikesPerHour(profile.getNetworkId()));

		Object[] timeWithMoreLikes = instagramPostLikeRemote.getTimeWithMoreLikes(profile.getNetworkId());

		reportInstagramHistorical.setTimeWithMoreLikes((Integer) timeWithMoreLikes[0]);
		reportInstagramHistorical.setTimeWithMoreLikesPerc((Integer) timeWithMoreLikes[1]);

		Object[] timeWithLessLikes = instagramPostLikeRemote.getTimeWithLessLikes(profile.getNetworkId());

		reportInstagramHistorical.setTimeWithLessLikes((Integer) timeWithLessLikes[0]);
		reportInstagramHistorical.setTimeWithLessLikesPerc((Integer) timeWithLessLikes[1]);

		reportInstagramHistorical.setCommentsPerHour(instagramPostCommentRemote.getCommentsPerHour(profile.getNetworkId()));

		Object[] timeWithMoreComments = instagramPostCommentRemote.getTimeWithMoreComments(profile.getNetworkId());

		reportInstagramHistorical.setTimeWithMoreComments((Integer) timeWithMoreComments[0]);
		reportInstagramHistorical.setTimeWithMoreCommentsPerc((Integer) timeWithMoreComments[1]);

		Object[] timeWithLessComments = instagramPostCommentRemote.getTimeWithLessComments(profile.getNetworkId());

		reportInstagramHistorical.setTimeWithLessComments((Integer) timeWithLessComments[0]);
		reportInstagramHistorical.setTimeWithLessCommentsPerc((Integer) timeWithLessComments[1]);

		reportInstagramHistorical.setInteractionsPerHour(UtilReports.getInteractionsPerHour(reportInstagramHistorical.getLikesPerHour(), 
				reportInstagramHistorical.getCommentsPerHour()));

		reportInstagramHistorical.setInstagramInteractionsRanking(UtilReports.getInstagramInteractionsRanking(
				reportInstagramHistorical.getInteractionsPerHour()));

		reportInstagramHistorical.setLikesPerHourSimpleData(instagramPostLikeRemote.getLikesPerHourSimpleData(profile.getNetworkId(), new Date(0).getTime(), new Date().getTime()));

		reportInstagramHistorical.setCommentsPerHourSimpleData(instagramPostCommentRemote.getCommentsPerHourSimpleData(profile.getNetworkId(), new Date(0).getTime(), new Date().getTime()));

		reportInstagramHistorical.setAudiencesAveragePublicAge(instagramReportRemote.getAudiencesAveragePublicAge(profile.getNetworkId(), new Date(0).getTime(), new Date().getTime()));
		reportInstagramHistorical.setAudiencesAveragePublicGender(instagramReportRemote.getAudiencesAveragePublicGender(profile.getNetworkId(), new Date(0).getTime(), new Date().getTime()));
		
		reportInstagramHistorical.setAudiencesDemograph(instagramReportRemote.getAudiencesDemograph(profile.getNetworkId(), new Date(0).getTime(), new Date().getTime()));

		reportInstagramHistorical.setPostsWithMoreInteractions(
								instagramReportPostRemote.getPostsWithMoreInteractions(profile.getNetworkId(), 
										new Date(0).getTime(), new Date().getTime()));
		
		Comparator<InstagramReportPost> postsComparator = new Comparator<InstagramReportPost>() {
			  public int compare(InstagramReportPost s1, InstagramReportPost s2) {
				  
				if((s1.getLikes() + s1.getComments()) > (s2.getLikes() + s2.getComments())) {  
		            return -1;  
		        }  
				
		        if((s1.getLikes() + s1.getComments()) < (s2.getLikes() + s2.getComments())) {  
		            return 1;  
		        }
		        
		        return 0;
			  }
			};
			
		reportInstagramHistorical.getPostsWithMoreInteractions().sort(postsComparator);
		
		return reportInstagramHistorical;
	}

	public static ReportFacebookHistoricalDTO mountReportFacebookHistoricalDTO(Profile profile, Integer tableDataLimit) throws NamingException {

		ReportFacebookHistoricalDTO reportFacebookHistorical = new ReportFacebookHistoricalDTO();

		MaintenanceReportDetailedRemote reportDetailedRemote = (MaintenanceReportDetailedRemote)
				RecoverMaintenance.recoverMaintenance("ReportDetailed");

		MaintenanceFaceReportLocationRemote faceReportLocationRemote = (MaintenanceFaceReportLocationRemote) 
				RecoverMaintenance.recoverMaintenance("FaceReportLocation");

		MaintenanceFacebookInfluentialUserRemote facebookInfluentialUserRemote = (MaintenanceFacebookInfluentialUserRemote)
				RecoverMaintenance.recoverMaintenance("FacebookInfluentialUser");

		MaintenanceReportControlRemote reportControlRemote = (MaintenanceReportControlRemote)
				RecoverMaintenance.recoverMaintenance("ReportControl");

		List<FanPageGrowth> fanPageGrowth = reportDetailedRemote.getFanPageGrowth(profile.getNetworkId());

		reportFacebookHistorical.setFanPageGrowth(fanPageGrowth);

		reportFacebookHistorical.setFansUntilOneYearAgo(reportDetailedRemote.getCountFansBeforeDate(profile.getNetworkId(), UtilReports.getDateForFanPageGrowth()));

		Long reportFirstDate = reportControlRemote.getMinDate(profile.getNetworkId(), profile.getType(), ReportFacebook.GROUP_MAIN);
		Long reportLastDate = reportControlRemote.getMaxDate(profile.getNetworkId(), profile.getType(), ReportFacebook.GROUP_MAIN);

		reportFacebookHistorical.setMajorFanPageGrowth(getMajorFanPageGrowth(reportFacebookHistorical.getFanPageGrowth(), reportFacebookHistorical.getFansUntilOneYearAgo()));
		reportFacebookHistorical.setMinorFanPageGrowth(getMinorFanPageGrowth(reportFacebookHistorical.getFanPageGrowth(), reportFacebookHistorical.getFansUntilOneYearAgo(), reportFirstDate));

		// ----------- About your fans ---------------- //
		reportFacebookHistorical.setAboutYourFans(reportDetailedRemote.getAboutYourFansGender(profile.getNetworkId()));
		reportFacebookHistorical.setAboutYourFemaleFans(getAboutYourFans(reportFacebookHistorical.getAboutYourFans(), 'F'));
		reportFacebookHistorical.setAboutYourMaleFans(getAboutYourFans(reportFacebookHistorical.getAboutYourFans(), 'M'));

		reportFacebookHistorical.setMajorAgeGroupFans(reportDetailedRemote.getMajorAgeGroupFans(profile.getNetworkId()));

		Integer[] womenMenFansPerc = reportDetailedRemote.getWomenMenFansPerc(profile.getNetworkId());

		reportFacebookHistorical.setMenFansPerc(womenMenFansPerc[0]);
		reportFacebookHistorical.setWomenFansPerc(womenMenFansPerc[1]);

		// ----------- About your reached users ---------------- //
		reportFacebookHistorical.setAboutReachedUsers(reportDetailedRemote.getAboutReachedUsers(profile.getNetworkId()));
		reportFacebookHistorical.setAboutReachedFemaleUsers(getAboutReachedUsers(reportFacebookHistorical.getAboutReachedUsers(), 'F'));
		reportFacebookHistorical.setAboutReachedMaleUsers(getAboutReachedUsers(reportFacebookHistorical.getAboutReachedUsers(), 'M'));

		reportFacebookHistorical.setMajorAgeGroupReachedUsers(reportDetailedRemote.getMajorAgeGroupReachedUsers(profile.getNetworkId()));

		Integer[] womenMenReachedUsersPerc = reportDetailedRemote.getWomenMenReachedUsersPerc(profile.getNetworkId());

		reportFacebookHistorical.setMenReachedUsersPerc(womenMenReachedUsersPerc[0]);
		reportFacebookHistorical.setWomenReachedUsersPerc(womenMenReachedUsersPerc[1]);

		// ----------- Reactions ---------------- //
		reportFacebookHistorical.setLikesPerMonth(reportDetailedRemote.getPropertyByMonth(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_LIKE_TOTAL));
		reportFacebookHistorical.setLovePerMonth(reportDetailedRemote.getPropertyByMonth(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_LOVE_TOTAL));
		reportFacebookHistorical.setWowPerMonth(reportDetailedRemote.getPropertyByMonth(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_WOW_TOTAL));
		reportFacebookHistorical.setAngerPerMonth(reportDetailedRemote.getPropertyByMonth(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_ANGER_TOTAL));
		reportFacebookHistorical.setHahaPerMonth(reportDetailedRemote.getPropertyByMonth(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_HAHA_TOTAL));
		reportFacebookHistorical.setSorryPerMonth(reportDetailedRemote.getPropertyByMonth(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_SORRY_TOTAL));

		reportFacebookHistorical.setTotalLikes(getTotalForMonths(reportFacebookHistorical.getLikesPerMonth()));
		reportFacebookHistorical.setTotalLove(getTotalForMonths(reportFacebookHistorical.getLovePerMonth()));
		reportFacebookHistorical.setTotalWow(getTotalForMonths(reportFacebookHistorical.getWowPerMonth()));
		reportFacebookHistorical.setTotalAnger(getTotalForMonths(reportFacebookHistorical.getAngerPerMonth()));
		reportFacebookHistorical.setTotalHaha(getTotalForMonths(reportFacebookHistorical.getHahaPerMonth()));
		reportFacebookHistorical.setTotalSorry(getTotalForMonths(reportFacebookHistorical.getSorryPerMonth()));

		Long totalReactions = 0L;
		totalReactions = reportFacebookHistorical.getTotalLikes() + reportFacebookHistorical.getTotalAnger() + reportFacebookHistorical.getTotalHaha() +
				reportFacebookHistorical.getTotalLove() + reportFacebookHistorical.getTotalSorry() + reportFacebookHistorical.getTotalWow();

		reportFacebookHistorical.setAngerPercentage(Calc.calcPercentageNormal(reportFacebookHistorical.getTotalAnger().doubleValue(), 
				totalReactions.doubleValue(), 1));
		reportFacebookHistorical.setHahaPercentage(Calc.calcPercentageNormal(reportFacebookHistorical.getTotalHaha().doubleValue(),  
				totalReactions.doubleValue(), 1));	 
		reportFacebookHistorical.setLikesPercentage(Calc.calcPercentageNormal(reportFacebookHistorical.getTotalLikes().doubleValue(),
				totalReactions.doubleValue(), 1));
		reportFacebookHistorical.setLovePercentage(Calc.calcPercentageNormal(reportFacebookHistorical.getTotalLove().doubleValue(),  
				totalReactions.doubleValue(), 1));
		reportFacebookHistorical.setSorryPercentage(Calc.calcPercentageNormal(reportFacebookHistorical.getTotalSorry().doubleValue(), 
				totalReactions.doubleValue(), 1));
		reportFacebookHistorical.setWowPercentage(Calc.calcPercentageNormal(reportFacebookHistorical.getTotalWow().doubleValue(), 
				totalReactions.doubleValue(), 1));

		// ----------- Comments ---------------- //
		reportFacebookHistorical.setCommentsPerMonth(reportDetailedRemote.getPropertyByMonth(profile.getNetworkId(), ReportFacebook.PAGE_POSITIVE_FEEDBACK_COMMENT));
		reportFacebookHistorical.setTotalComments(getTotalForMonths(reportFacebookHistorical.getCommentsPerMonth()));
		reportFacebookHistorical.setMajorCommentsMonth(getMajorSumByDateMonth(reportFacebookHistorical.getCommentsPerMonth()));

		// ----------- Shares ---------------- //
		reportFacebookHistorical.setSharesPerMonth(reportDetailedRemote.getPropertyByMonth(profile.getNetworkId(), ReportFacebook.PAGE_POSITIVE_FEEDBACK_LINK));
		reportFacebookHistorical.setTotalShares(getTotalForMonths(reportFacebookHistorical.getSharesPerMonth()));
		reportFacebookHistorical.setMajorSharesMonth(getMajorSumByDateMonth(reportFacebookHistorical.getSharesPerMonth()));

		// ----------- Clicks ---------------- //
		reportFacebookHistorical.setClicksPerMonth(reportDetailedRemote.getPropertyByMonth(profile.getNetworkId(), ReportFacebook.PAGE_CONSUMPTIONS));
		reportFacebookHistorical.setTotalClicks(getTotalForMonths(reportFacebookHistorical.getClicksPerMonth()));
		reportFacebookHistorical.setMajorClicksMonth(getMajorSumByDateMonth(reportFacebookHistorical.getClicksPerMonth()));

		// ----------- Cities more fans/reached users ---------------- //
		reportFacebookHistorical.setCitiesMoreFans(faceReportLocationRemote.getReportFacebookCityFan(profile.getNetworkId(), 0, tableDataLimit));
		reportFacebookHistorical.setCitiesMoreReached(faceReportLocationRemote.getReportFacebookCityImp(profile.getNetworkId(), 0, tableDataLimit));

		// ----------- Most Influential Fans ---------------- //
		List<FacebookInfluentialUserDTO> facebookInfluentialUsersDTO = new ArrayList<FacebookInfluentialUserDTO>();
		List<FacebookInfluentialUser> facebookInfluentialUsers = facebookInfluentialUserRemote.getMostInfluentialFans(profile.getNetworkId(), 0, 20);

		for(FacebookInfluentialUser facebookInfluentialUser : facebookInfluentialUsers) {
			facebookInfluentialUsersDTO.add(MountDTO.mountFacebookInfluentialUser(facebookInfluentialUser));
		}

		reportFacebookHistorical.setMostInfluentialFans(facebookInfluentialUsersDTO);

		reportFacebookHistorical.setFansLikeSource(normalizeFansLikeSource(reportDetailedRemote.getFansLikeSource(profile.getNetworkId(), reportFirstDate, reportLastDate)));
		reportFacebookHistorical.setExternalReferences(faceReportLocationRemote.getExternalReferences(profile.getNetworkId(), reportFirstDate, reportLastDate, 0, tableDataLimit));

		return reportFacebookHistorical;
	}

	public static ReportFacebookOverviewDTO mountReportFacebookOverviewDTO(Profile profile, GenericDateInterval genericDateInterval, Integer tableDataLimit) throws NamingException {

		ReportFacebookOverviewDTO reportFacebookOverview = new ReportFacebookOverviewDTO();

		MaintenanceReportDetailedRemote reportDetailedRemote = (MaintenanceReportDetailedRemote)
				RecoverMaintenance.recoverMaintenance("ReportDetailed");

		MaintenanceFaceReportLocationRemote faceReportLocationRemote = (MaintenanceFaceReportLocationRemote) 
				RecoverMaintenance.recoverMaintenance("FaceReportLocation");

		MaintenanceFacebookInfluentialUserRemote facebookInfluentialUserRemote = (MaintenanceFacebookInfluentialUserRemote)
				RecoverMaintenance.recoverMaintenance("FacebookInfluentialUser");

		reportFacebookOverview.setDateFrom(genericDateInterval.getDateFrom());
		reportFacebookOverview.setDateUntil(genericDateInterval.getDateUntil());

		Calendar untilDate = Calendar.getInstance();
		untilDate.setTimeInMillis((genericDateInterval.getDateFrom() - 86400L) * 1000L);

		Integer period = Long.valueOf((genericDateInterval.getDateUntil() - genericDateInterval.getDateFrom()) / 86400L).intValue();

		GenericDateInterval genericDateIntervalOld = getFacebookReportsDates(untilDate, period);

		reportFacebookOverview.setReachByDay(reportDetailedRemote.getReachByDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		Long reachCount = 0L;
		for(FanPageGrowth fanPageGrowth : reportFacebookOverview.getReachByDay()) {
			reachCount += fanPageGrowth.getSum(); 
		}

		reportFacebookOverview.setTotalReach(reachCount);

		Long reachCountOld = reportDetailedRemote.getTotalReach(profile.getNetworkId(), genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil());

		reportFacebookOverview.setTotalReachPerformance(Calc.calcPercentageGrowth(reachCountOld.doubleValue(), 
				reportFacebookOverview.getTotalReach().doubleValue(), 0).intValue());

		reportFacebookOverview.setImpressionsByDay(reportDetailedRemote.getImpressionsByDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil())); 

		Long impressionsCount = 0L;
		for(FanPageGrowth fanPageGrowth : reportFacebookOverview.getImpressionsByDay()) {
			impressionsCount += fanPageGrowth.getSum();
		}

		reportFacebookOverview.setTotalImpressions(impressionsCount);

		Long impressionsCountOld = reportDetailedRemote.getTotalImpressions(profile.getNetworkId(), genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil());

		reportFacebookOverview.setTotalImpressionsPerformance(Calc.calcPercentageGrowth(impressionsCountOld.doubleValue(), 
				reportFacebookOverview.getTotalImpressions().doubleValue(), 0).intValue());

		reportFacebookOverview.setEngagementByDay(reportDetailedRemote.getEngagementByDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		Long engagementCount = 0L;
		for(FanPageGrowth fanPageGrowth : reportFacebookOverview.getEngagementByDay()) {
			engagementCount += fanPageGrowth.getSum();
		}

		reportFacebookOverview.setTotalEngagement(engagementCount);

		Long engagementCountOld = reportDetailedRemote.getTotalEngagement(profile.getNetworkId(), genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil());

		reportFacebookOverview.setTotalEngagementPerformance(Calc.calcPercentageGrowth(engagementCountOld.doubleValue(), 
				reportFacebookOverview.getTotalEngagement().doubleValue(), 0).intValue());

		List<FanPageGrowth> fanPageGrowth = reportDetailedRemote.getFanPageGrowth(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil(), 
				reportDetailedRemote.getCountFansBeforeDate(profile.getNetworkId(), genericDateInterval.getDateFrom()));

		reportFacebookOverview.setFanPageGrowth(fanPageGrowth);

		reportFacebookOverview.setFansUntilDateFrom(reportDetailedRemote.getCountFansBeforeDate(profile.getNetworkId(), genericDateInterval.getDateFrom()));

		List<FanPageGrowth> likesByDay = reportDetailedRemote.getLikesByDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportFacebookOverview.setLikesByDay(likesByDay);

		List<FanPageGrowth> unlikesByDay = reportDetailedRemote.getUnlikesByDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportFacebookOverview.setUnlikesByDay(unlikesByDay);

		reportFacebookOverview.setLikesBalanceByDay(getLikesBalanceByDay(reportFacebookOverview.getLikesByDay(), reportFacebookOverview.getUnlikesByDay()));

		reportFacebookOverview.setTotalOfFans(reportDetailedRemote.getCountFansBeforeDate(profile.getNetworkId(), genericDateInterval.getDateUntil() + 86400));
		reportFacebookOverview.setMajorFanPageGrowth(getMajorFanPageGrowthDaySum(reportFacebookOverview.getLikesBalanceByDay()));
		reportFacebookOverview.setConqueredFans(reportDetailedRemote.getConqueredFans(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookOverview.setLostFans(reportDetailedRemote.getLostFans(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookOverview.setBalanceFans(reportFacebookOverview.getConqueredFans() - reportFacebookOverview.getLostFans());

		Long conqueredFansOld = reportDetailedRemote.getConqueredFans(profile.getNetworkId(), genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil());
		Long lostFansOld = reportDetailedRemote.getLostFans(profile.getNetworkId(), genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil());

		reportFacebookOverview.setBalanceFansPerformance(Calc.calcPercentageGrowth(Double.valueOf(conqueredFansOld - lostFansOld), 
				reportFacebookOverview.getBalanceFans().doubleValue(), 0).intValue());

		reportFacebookOverview.setAboutReachedUsers(reportDetailedRemote.getAboutReachedUsers(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookOverview.setAboutReachedFemaleUsers(getAboutReachedUsers(reportFacebookOverview.getAboutReachedUsers(), 'F'));
		reportFacebookOverview.setAboutReachedMaleUsers(getAboutReachedUsers(reportFacebookOverview.getAboutReachedUsers(), 'M'));

		reportFacebookOverview.setMajorAgeGroupReachedUsers(reportDetailedRemote.getMajorAgeGroupReachedUsers(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		Integer[] womenMenReachedUsersPerc = reportDetailedRemote.getWomenMenReachedUsersPerc(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportFacebookOverview.setMenReachedUsersPerc(womenMenReachedUsersPerc[0]);
		reportFacebookOverview.setWomenReachedUsersPerc(womenMenReachedUsersPerc[1]);

		reportFacebookOverview.setAboutYourFans(reportDetailedRemote.getAboutYourFansGender(profile.getNetworkId()));
		reportFacebookOverview.setAboutYourFemaleFans(getAboutYourFans(reportFacebookOverview.getAboutYourFans(), 'F'));
		reportFacebookOverview.setAboutYourMaleFans(getAboutYourFans(reportFacebookOverview.getAboutYourFans(), 'M'));

		reportFacebookOverview.setMajorAgeGroupFans(reportDetailedRemote.getMajorAgeGroupFans(profile.getNetworkId()));

		Integer[] womenMenFansPerc = reportDetailedRemote.getWomenMenFansPerc(profile.getNetworkId());

		reportFacebookOverview.setMenFansPerc(womenMenFansPerc[0]);
		reportFacebookOverview.setWomenFansPerc(womenMenFansPerc[1]);

		List<FacebookInfluentialUserDTO> facebookInfluentialUsersDTO = new ArrayList<FacebookInfluentialUserDTO>();
		List<FacebookInfluentialUser> facebookInfluentialUsers = facebookInfluentialUserRemote.getMostInfluentialFans(profile.getNetworkId(), 0, 20);

		for(FacebookInfluentialUser facebookInfluentialUser : facebookInfluentialUsers) {
			facebookInfluentialUsersDTO.add(MountDTO.mountFacebookInfluentialUser(facebookInfluentialUser));
		}

		reportFacebookOverview.setMostInfluentialFans(facebookInfluentialUsersDTO);

		reportFacebookOverview.setFansOnlinePerHour(reportDetailedRemote.getFansOnlinePerHour(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookOverview.setAverageFansOnlinePerHour(reportDetailedRemote.getAverageFansOnlinePerHour(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookOverview.setFansOnlinePerWeekDay(reportDetailedRemote.getFansOnlinePerWeekDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		Object[] timeWithMoreFansOnline = reportDetailedRemote.getTimeWithMoreFansOnline(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportFacebookOverview.setTimeWithMoreFansOnline((Integer) timeWithMoreFansOnline[0]);
		reportFacebookOverview.setTimeWithMoreFansOnlinePercentage((Integer) timeWithMoreFansOnline[1]);

		Object[] weekDayWithMoreFansOnline = reportDetailedRemote.getWeekDayWithMoreFansOnline(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportFacebookOverview.setWeekDayWithMoreFansOnline((Integer) weekDayWithMoreFansOnline[0]);
		reportFacebookOverview.setWeekDayWithMoreFansOnlinePercentage((Integer) weekDayWithMoreFansOnline[1]);

		reportFacebookOverview.setCountriesMoreFans(faceReportLocationRemote.getReportFacebookCountryFan(profile.getNetworkId(), 0, tableDataLimit));
		reportFacebookOverview.setCountriesMoreReached(faceReportLocationRemote.getReportFacebookCountryImp(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil(), 0, tableDataLimit));

		reportFacebookOverview.setCitiesMoreFans(faceReportLocationRemote.getReportFacebookCityFan(profile.getNetworkId(), 0, tableDataLimit));
		reportFacebookOverview.setCitiesMoreReached(faceReportLocationRemote.getReportFacebookCityImp(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil(), 0, tableDataLimit));

		reportFacebookOverview.setOtherActivities(reportDetailedRemote.getOtherActivities(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookOverview.setFansLikeSource(normalizeFansLikeSource(reportDetailedRemote.getFansLikeSource(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil())));
		reportFacebookOverview.setExternalReferences(faceReportLocationRemote.getExternalReferences(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil(), 0, tableDataLimit));

		return reportFacebookOverview;
	}

	public static ReportFacebookContentDTO mountReportFacebookContentDTO(Profile profile, GenericDateInterval genericDateInterval) throws NamingException {

		ReportFacebookContentDTO reportFacebookContent = new ReportFacebookContentDTO();

		MaintenanceReportDetailedRemote reportDetailedRemote = (MaintenanceReportDetailedRemote)
				RecoverMaintenance.recoverMaintenance("ReportDetailed");

		MaintenanceFaceReportPostRemote faceReportPostRemote = (MaintenanceFaceReportPostRemote) 
				RecoverMaintenance.recoverMaintenance("FaceReportPost");

		MaintenanceFacebookPostInsightRemote facebookPostInsightRemote = (MaintenanceFacebookPostInsightRemote)
				RecoverMaintenance.recoverMaintenance("FacebookPostInsight");

		reportFacebookContent.setDateFrom(genericDateInterval.getDateFrom());
		reportFacebookContent.setDateUntil(genericDateInterval.getDateUntil());

		Calendar untilDate = Calendar.getInstance();
		untilDate.setTimeInMillis((genericDateInterval.getDateFrom() - 86400L) * 1000L);

		Integer period = Long.valueOf((genericDateInterval.getDateUntil() - genericDateInterval.getDateFrom()) / 86400L).intValue();

		GenericDateInterval genericDateIntervalOld = getFacebookReportsDates(untilDate, period);

		reportFacebookContent.setNumberOfPosts(faceReportPostRemote.getNumberOfPosts(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400));

		reportFacebookContent.setAveragePostsPerDay(faceReportPostRemote.getAveragePostsPerDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400));
		reportFacebookContent.setAveragePostsReach(facebookPostInsightRemote.getAveragePostsReach(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400));
		reportFacebookContent.setMajorPostsReachDay(facebookPostInsightRemote.getMajorPostsReachDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400));
		reportFacebookContent.setAveragePostsInteractions(facebookPostInsightRemote.getAveragePostsInteractions(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400));
		reportFacebookContent.setMajorPostsInteractionsDay(facebookPostInsightRemote.getMajorPostsInteractionsDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400));

		// ----------- Reactions ---------------- //
		reportFacebookContent.setLikesPerDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_LIKE_TOTAL, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportFacebookContent.setLovePerDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_LOVE_TOTAL, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportFacebookContent.setHahaPerDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_HAHA_TOTAL, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportFacebookContent.setWowPerDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_WOW_TOTAL, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportFacebookContent.setSorryPerDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_SORRY_TOTAL, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportFacebookContent.setAngerPerDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_ACTIONS_POST_REACTIONS_ANGER_TOTAL, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportFacebookContent.setTotalLikes(getTotalCount(reportFacebookContent.getLikesPerDay()));
		reportFacebookContent.setTotalAnger(getTotalCount(reportFacebookContent.getAngerPerDay()));
		reportFacebookContent.setTotalHaha(getTotalCount(reportFacebookContent.getHahaPerDay()));
		reportFacebookContent.setTotalLove(getTotalCount(reportFacebookContent.getLovePerDay()));
		reportFacebookContent.setTotalSorry(getTotalCount(reportFacebookContent.getSorryPerDay()));
		reportFacebookContent.setTotalWow(getTotalCount(reportFacebookContent.getWowPerDay()));

		Long totalReactions = 0L;
		totalReactions = reportFacebookContent.getTotalLikes() + reportFacebookContent.getTotalAnger() + reportFacebookContent.getTotalHaha() +
				reportFacebookContent.getTotalLove() + reportFacebookContent.getTotalSorry() + reportFacebookContent.getTotalWow();

		reportFacebookContent.setAngerPercentage(Calc.calcPercentageNormal(reportFacebookContent.getTotalAnger().doubleValue(), 
				totalReactions.doubleValue(), 1));
		reportFacebookContent.setHahaPercentage(Calc.calcPercentageNormal(reportFacebookContent.getTotalHaha().doubleValue(),  
				totalReactions.doubleValue(), 1));	 
		reportFacebookContent.setLikesPercentage(Calc.calcPercentageNormal(reportFacebookContent.getTotalLikes().doubleValue(),
				totalReactions.doubleValue(), 1));
		reportFacebookContent.setLovePercentage(Calc.calcPercentageNormal(reportFacebookContent.getTotalLove().doubleValue(),  
				totalReactions.doubleValue(), 1));
		reportFacebookContent.setSorryPercentage(Calc.calcPercentageNormal(reportFacebookContent.getTotalSorry().doubleValue(), 
				totalReactions.doubleValue(), 1));
		reportFacebookContent.setWowPercentage(Calc.calcPercentageNormal(reportFacebookContent.getTotalWow().doubleValue(), 
				totalReactions.doubleValue(), 1));

		// ----------- Comments ---------------- //
		reportFacebookContent.setCommentsPerDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_POSITIVE_FEEDBACK_COMMENT,
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setTotalComments(getTotalCount(reportFacebookContent.getCommentsPerDay()));
		reportFacebookContent.setMajorCommentsDay(getMajorFanPageGrowthDaySum(reportFacebookContent.getCommentsPerDay()));

		// ----------- Shares ---------------- //
		reportFacebookContent.setSharesPerDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_POSITIVE_FEEDBACK_LINK, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setTotalShares(getTotalCount(reportFacebookContent.getSharesPerDay()));
		reportFacebookContent.setMajorSharesDay(getMajorFanPageGrowthDaySum(reportFacebookContent.getSharesPerDay()));

		// ---------- Posts Reach ------------- //
		reportFacebookContent.setPostsReachPaidByDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_POSTS_IMPRESSIONS_PAID_UNIQUE,
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setPostsReachPaidCount(getTotalCount(reportFacebookContent.getPostsReachPaidByDay()));

		reportFacebookContent.setPostsReachOrganicByDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_POSTS_IMPRESSIONS_ORGANIC_UNIQUE,
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setPostsReachOrganicCount(getTotalCount(reportFacebookContent.getPostsReachOrganicByDay()));

		Long totalReach = 0L;
		totalReach = reportFacebookContent.getPostsReachOrganicCount() + reportFacebookContent.getPostsReachPaidCount();

		reportFacebookContent.setPostsReachOrganicPercentage(Calc.calcPercentageNormal(reportFacebookContent.getPostsReachOrganicCount().doubleValue(),  
				totalReach.doubleValue(), 0));
		reportFacebookContent.setPostsReachPaidPercentage(Calc.calcPercentageNormal(reportFacebookContent.getPostsReachPaidCount().doubleValue(), 
				totalReach.doubleValue(), 0)); 

		reportFacebookContent.setAverageReachOfPostsPerDay(Calc.calcAverage(totalReach.doubleValue(), 
				reportFacebookContent.getPostsReachOrganicByDay().size(), 0).longValue());

		// ---------- Posts Impressions ------------- //
		reportFacebookContent.setPostsImpressionsPaidByDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_POSTS_IMPRESSIONS_PAID, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setPostsImpressionsPaidCount(getTotalCount(reportFacebookContent.getPostsImpressionsPaidByDay()));

		reportFacebookContent.setPostsImpressionsOrganicByDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), ReportFacebook.PAGE_POSTS_IMPRESSIONS_ORGANIC, 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setPostsImpressionsOrganicCount(getTotalCount(reportFacebookContent.getPostsImpressionsOrganicByDay()));

		Long totalImpressions = 0l;
		totalImpressions = reportFacebookContent.getPostsImpressionsOrganicCount() + reportFacebookContent.getPostsImpressionsPaidCount();

		reportFacebookContent.setPostsImpressionsPaidPercentage(Calc.calcPercentageNormal(reportFacebookContent.getPostsImpressionsPaidCount().doubleValue(),
				totalImpressions.doubleValue(), 0));
		reportFacebookContent.setPostsImpressionsOrganicPercentage(Calc.calcPercentageNormal(reportFacebookContent.getPostsImpressionsOrganicCount().doubleValue(), 
				totalImpressions.doubleValue(), 0));

		reportFacebookContent.setAverageImpressionsOfPostsPerDay(Calc.calcAverage(totalImpressions.doubleValue(), 
				reportFacebookContent.getPostsImpressionsOrganicByDay().size(), 0).longValue());

		// ---------- Posts Consumptions ------------- //
		reportFacebookContent.setPostsConsumptionsType(reportDetailedRemote.getPropertiesFromGroup(profile.getNetworkId(), 
				ReportFacebook.GROUP_CON_TYPE, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportFacebookContent.setPostsConsumptionsCount(getTotalForProperties(reportFacebookContent.getPostsConsumptionsType()));

		reportFacebookContent.setPostsPhotoViewPercentage(Calc.calcPercentageNormal(getValueForProperty(reportFacebookContent.getPostsConsumptionsType(),
				ReportFacebook.PAGE_CONSUMPTIONS_PHOTO_VIEW).doubleValue(), reportFacebookContent.getPostsConsumptionsCount().doubleValue(), 0).intValue());
		reportFacebookContent.setPostsLinkClicksPercentage(Calc.calcPercentageNormal(getValueForProperty(reportFacebookContent.getPostsConsumptionsType(), 
				ReportFacebook.PAGE_CONSUMPTIONS_LINK_CLICKS).doubleValue(), reportFacebookContent.getPostsConsumptionsCount().doubleValue(), 0).intValue());
		reportFacebookContent.setPostsOtherClicksPercentage(Calc.calcPercentageNormal(getValueForProperty(reportFacebookContent.getPostsConsumptionsType(), 
				ReportFacebook.PAGE_CONSUMPTIONS_OTHER_CLICKS).doubleValue(), reportFacebookContent.getPostsConsumptionsCount().doubleValue(), 0).intValue());
		reportFacebookContent.setPostsVideoPlaysPercentage(Calc.calcPercentageNormal(getValueForProperty(reportFacebookContent.getPostsConsumptionsType(), 
				ReportFacebook.PAGE_CONSUMPTIONS_VIDEO_PLAY).doubleValue(), reportFacebookContent.getPostsConsumptionsCount().doubleValue(), 0).intValue());

		// ---------- Page Negative Feedback ------------- //
		reportFacebookContent.setPageNegativeFeedbacksType(reportDetailedRemote.getPropertiesFromGroup(profile.getNetworkId(), 
				ReportFacebook.GROUP_NEGATIVE_FEEDBACK, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		// ---------- Page Total Visualization Videos ------------- //
		reportFacebookContent.setVideoTotalOrganicViewsPerDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(),
				ReportFacebook.PAGE_VIDEO_VIEWS_ORGANIC, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setVideoTotalPaidViewsPerDay(reportDetailedRemote.getPropertyByDay(profile.getNetworkId(), 
				ReportFacebook.PAGE_VIDEO_VIEWS_PAID, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportFacebookContent.setMajorTotalViewsDay(reportDetailedRemote.getMajorTotalViewsDay(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportFacebookContent.setVideoTotalOrganicViews(getTotalCount(reportFacebookContent.getVideoTotalOrganicViewsPerDay()));
		reportFacebookContent.setVideoTotalPaidViews(getTotalCount(reportFacebookContent.getVideoTotalPaidViewsPerDay()));
		reportFacebookContent.setVideoTotalViews(reportFacebookContent.getVideoTotalOrganicViews() + reportFacebookContent.getVideoTotalPaidViews());

		reportFacebookContent.setVideoTotalOrganicViewsPercentage(Calc.calcPercentageNormal(reportFacebookContent.getVideoTotalOrganicViews().doubleValue(), 
				reportFacebookContent.getVideoTotalViews().doubleValue(), 0).intValue());
		reportFacebookContent.setVideoTotalPaidViewsPercentage(Calc.calcPercentageNormal(reportFacebookContent.getVideoTotalPaidViews().doubleValue(), 
				reportFacebookContent.getVideoTotalViews().doubleValue(), 0).intValue());

		Long videoTotalViewsOld = reportDetailedRemote.getPropertyCount(profile.getNetworkId(), ReportFacebook.PAGE_VIDEO_VIEWS_ORGANIC, 
				genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil()) + 
				reportDetailedRemote.getPropertyCount(profile.getNetworkId(), ReportFacebook.PAGE_VIDEO_VIEWS_PAID, 
						genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil());

		reportFacebookContent.setVideoTotalViewsPerformance(Calc.calcPercentageGrowth(videoTotalViewsOld.doubleValue(), 
				reportFacebookContent.getVideoTotalViews().doubleValue(), 0).intValue());

		// ---------- Page Complete Visualization Videos ------------- //
		reportFacebookContent.setVideoCompleteOrganicViews(reportDetailedRemote.getPropertyCount(profile.getNetworkId(), 
				ReportFacebook.PAGE_VIDEO_COMPLETE_VIEWS_30S_ORGANIC, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setVideoCompletePaidViews(reportDetailedRemote.getPropertyCount(profile.getNetworkId(), 
				ReportFacebook.PAGE_VIDEO_COMPLETE_VIEWS_30S_PAID, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setVideoCompleteViews(reportFacebookContent.getVideoCompleteOrganicViews() + reportFacebookContent.getVideoCompletePaidViews());

		reportFacebookContent.setVideoCompleteOrganicViewsPercentage(Calc.calcPercentageNormal(reportFacebookContent.getVideoCompleteOrganicViews().doubleValue(), 
				reportFacebookContent.getVideoCompleteViews().doubleValue(), 0).intValue());
		reportFacebookContent.setVideoCompletePaidViewsPercentage(Calc.calcPercentageNormal(reportFacebookContent.getVideoCompletePaidViews().doubleValue(), 
				reportFacebookContent.getVideoCompleteViews().doubleValue(), 0).intValue());

		Long videoCompleteViewsOld = reportDetailedRemote.getPropertyCount(profile.getNetworkId(), ReportFacebook.PAGE_VIDEO_COMPLETE_VIEWS_30S_ORGANIC, 
				genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil()) +
				reportDetailedRemote.getPropertyCount(profile.getNetworkId(), ReportFacebook.PAGE_VIDEO_COMPLETE_VIEWS_30S_PAID,
						genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil());

		reportFacebookContent.setVideoCompleteViewsPerformance(Calc.calcPercentageGrowth(videoCompleteViewsOld.doubleValue(),
				reportFacebookContent.getVideoCompleteViews().doubleValue(), 0).intValue());

		// ---------- Page Partial Visualization Videos ------------- //
		reportFacebookContent.setVideoPartialOrganicViews(reportDetailedRemote.getPropertyCount(profile.getNetworkId(), 
				ReportFacebook.PAGE_VIDEO_VIEWS_10S_ORGANIC, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setVideoPartialPaidViews(reportDetailedRemote.getPropertyCount(profile.getNetworkId(), 
				ReportFacebook.PAGE_VIDEO_VIEWS_10S_PAID, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setVideoPartialViews(reportFacebookContent.getVideoPartialOrganicViews() + reportFacebookContent.getVideoPartialPaidViews());

		reportFacebookContent.setVideoPartialOrganicViewsPercentage(Calc.calcPercentageNormal(reportFacebookContent.getVideoPartialOrganicViews().doubleValue(),
				reportFacebookContent.getVideoPartialViews().doubleValue(), 0).intValue());
		reportFacebookContent.setVideoPartialPaidViewsPercentage(Calc.calcPercentageNormal(reportFacebookContent.getVideoPartialPaidViews().doubleValue(), 
				reportFacebookContent.getVideoPartialViews().doubleValue(), 0).intValue());

		Long videoPartialViewsOld = reportDetailedRemote.getPropertyCount(profile.getNetworkId(), ReportFacebook.PAGE_VIDEO_VIEWS_10S_ORGANIC, 
				genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil()) +
				reportDetailedRemote.getPropertyCount(profile.getNetworkId(), ReportFacebook.PAGE_VIDEO_VIEWS_10S_PAID, 
						genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil());

		reportFacebookContent.setVideoPartialViewsPerformance(Calc.calcPercentageGrowth(videoPartialViewsOld.doubleValue(), 
				reportFacebookContent.getVideoPartialViews().doubleValue(), 0).intValue());

		// ---------- Page Time/Visualization Types Videos ------------- //
		reportFacebookContent.setVideoViewTime(reportDetailedRemote.getPropertyCount(profile.getNetworkId(), 
				ReportFacebook.PAGE_VIDEO_VIEW_TIME, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setVideoRepeatViews(reportDetailedRemote.getPropertyCount(profile.getNetworkId(), 
				ReportFacebook.PAGE_VIDEO_REPEAT_VIEWS, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));  

		Long videoTotalViewTimeOld = reportDetailedRemote.getPropertyCount(profile.getNetworkId(), ReportFacebook.PAGE_VIDEO_VIEW_TIME, 
				genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil()); 

		reportFacebookContent.setVideoViewTimePerformance(Calc.calcPercentageGrowth(videoTotalViewTimeOld.doubleValue(), 
				reportFacebookContent.getVideoViewTime().doubleValue(), 0).intValue());

		Long videoRepeatViewsOld = reportDetailedRemote.getPropertyCount(profile.getNetworkId(), ReportFacebook.PAGE_VIDEO_REPEAT_VIEWS, 
				genericDateIntervalOld.getDateFrom(), genericDateIntervalOld.getDateUntil());

		reportFacebookContent.setVideoRepeatViewsPerformance(Calc.calcPercentageGrowth(videoRepeatViewsOld.doubleValue(), 
				reportFacebookContent.getVideoRepeatViews().doubleValue(), 0).intValue());

		reportFacebookContent.setVideoAutoPlayedViews(reportDetailedRemote.getPropertyCount(profile.getNetworkId(), 
				ReportFacebook.PAGE_VIDEO_VIEWS_AUTOPLAYED, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportFacebookContent.setVideoClickToPlayViews(reportDetailedRemote.getPropertyCount(profile.getNetworkId(), 
				ReportFacebook.PAGE_VIDEO_VIEWS_CLICK_TO_PLAY, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		Long videoViewsTypeTotal = reportFacebookContent.getVideoAutoPlayedViews() + reportFacebookContent.getVideoClickToPlayViews();

		reportFacebookContent.setVideoAutoPlayedViewsPercentage(Calc.calcPercentageNormal(reportFacebookContent.getVideoAutoPlayedViews().doubleValue(), 
				videoViewsTypeTotal.doubleValue(), 0).intValue());
		reportFacebookContent.setVideoClickToPlayViewsPercentage(Calc.calcPercentageNormal(reportFacebookContent.getVideoClickToPlayViews().doubleValue(), 
				videoViewsTypeTotal.doubleValue(), 0).intValue());

		// ---------- Page Time Video Age/Gender ------------- //
		reportFacebookContent.setVideoViewTimeByFemaleUsers(facebookPostInsightRemote.getVideoTotalTimeByAgeGender(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400, br.com.opsocial.ejb.utils.reports.UtilReports.FEMALE));
		reportFacebookContent.setVideoViewTimeByMaleUsers(facebookPostInsightRemote.getVideoTotalTimeByAgeGender(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400, br.com.opsocial.ejb.utils.reports.UtilReports.MALE));
		reportFacebookContent.setVideoViewTimeByUnidentifiedUsers(facebookPostInsightRemote.getVideoTotalTimeByAgeGender(profile.getNetworkId(), 
				genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400, br.com.opsocial.ejb.utils.reports.UtilReports.UNIDENTIFIED));

		List<AgeGroup> ageGroups = UtilFunctions.initiateAgeGroupList();
		ageGroups = groupAgeGroupVideoViewTime(reportFacebookContent.getVideoViewTimeByFemaleUsers(), ageGroups);
		ageGroups = groupAgeGroupVideoViewTime(reportFacebookContent.getVideoViewTimeByMaleUsers(), ageGroups);
		ageGroups = groupAgeGroupVideoViewTime(reportFacebookContent.getVideoViewTimeByUnidentifiedUsers(), ageGroups);

		reportFacebookContent.setMajorAgeGroupVideoViewTimeUsers(getMajorAgeGroupVideoViewTimeUsers(ageGroups));

		Long videoViewTimeByFemaleUsersTotal = getTotalForAgeGroups(reportFacebookContent.getVideoViewTimeByFemaleUsers());
		Long videoViewTimeByMaleUsersTotal = getTotalForAgeGroups(reportFacebookContent.getVideoViewTimeByMaleUsers());

		Long videoViewTimeByGenderTotal = videoViewTimeByFemaleUsersTotal + videoViewTimeByMaleUsersTotal;

		reportFacebookContent.setFemaleVideoViewTimeUsersPerc(Calc.calcPercentageNormal(videoViewTimeByFemaleUsersTotal.doubleValue(), 
				videoViewTimeByGenderTotal.doubleValue(), 0).intValue());
		reportFacebookContent.setMaleVideoViewTimeUsersPerc(Calc.calcPercentageNormal(videoViewTimeByMaleUsersTotal.doubleValue(), 
				videoViewTimeByGenderTotal.doubleValue(), 0).intValue());

		return reportFacebookContent;
	}

	public static ReportFacebookPostCommentsDTO mountReportFacebookPostComments(FaceReportPostDTO faceReportPostDTO, GenericDateInterval genericDateInterval) {

		MaintenanceFaceReportPostCommentRemote faceReportPostCommentRemote = (MaintenanceFaceReportPostCommentRemote)
				RecoverMaintenance.recoverMaintenance("FaceReportPostComment");

		ReportFacebookPostCommentsDTO reportFacebookPostComments = new ReportFacebookPostCommentsDTO();

		reportFacebookPostComments.setDateFrom(genericDateInterval.getDateFrom());
		reportFacebookPostComments.setDateUntil(genericDateInterval.getDateUntil());

		reportFacebookPostComments.setFaceReportPostDTO(faceReportPostDTO);

		reportFacebookPostComments.setComments(
				MountDTO.mountFaceReportPostComment(faceReportPostCommentRemote.getComments(faceReportPostDTO.getPostId())));

		return reportFacebookPostComments;
	}

	public static ReportFacebookPostToPostDTO mountReportFacebookPostToPost(Profile profile, GenericDateInterval genericDateInterval) throws NamingException {

		ReportFacebookPostToPostDTO reportFacebookPostToPost = new ReportFacebookPostToPostDTO();

		MaintenanceFaceReportPostRemote faceReportPostRemote = (MaintenanceFaceReportPostRemote) 
				RecoverMaintenance.recoverMaintenance("FaceReportPost");

		MaintenanceFacebookPostInsightRemote facebookPostInsightRemote = (MaintenanceFacebookPostInsightRemote)
				RecoverMaintenance.recoverMaintenance("FacebookPostInsight");

		reportFacebookPostToPost.setDateFrom(genericDateInterval.getDateFrom());
		reportFacebookPostToPost.setDateUntil(genericDateInterval.getDateUntil());

		List<FaceReportPostDTO> faceReportPostsDTO = new ArrayList<FaceReportPostDTO>();
		List<FaceReportPost> faceReportPosts;
		faceReportPosts = faceReportPostRemote.getPostsByDate(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400, "DESC", 0, ReportFacebookPostToPostDTO.POSTS_PER_CALL);

		for(FaceReportPost faceReportPost : faceReportPosts) {

			FaceReportPostDTO faceReportPostDTO = MountDTO.mountFaceReportPost(faceReportPost);

			Map<String, Long> postInsights = checkPostInsights(facebookPostInsightRemote.getPostInsights(faceReportPostDTO.getPostId()));

			// ----------- Reach ---------------- //
			faceReportPostDTO.setOrganicReach(postInsights.get(FaceReportPostDTO.POST_IMPRESSIONS_ORGANIC_UNIQUE) != null ? postInsights.get(FaceReportPostDTO.POST_IMPRESSIONS_ORGANIC_UNIQUE) : 0L);
			faceReportPostDTO.setPaidReach(postInsights.get(FaceReportPostDTO.POST_IMPRESSIONS_PAID_UNIQUE) != null ? postInsights.get(FaceReportPostDTO.POST_IMPRESSIONS_PAID_UNIQUE) : 0L);
			faceReportPostDTO.setReach(faceReportPostDTO.getOrganicReach() + faceReportPostDTO.getPaidReach());

			faceReportPostDTO.setOrganicReachPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getOrganicReach().doubleValue(),
					faceReportPostDTO.getReach().doubleValue(), 0).intValue());
			faceReportPostDTO.setPaidReachPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getPaidReach().doubleValue(), 
					faceReportPostDTO.getReach().doubleValue(), 0).intValue());

			// ----------- Reactions ---------------- //
			faceReportPostDTO.setLove(postInsights.get(FaceReportPostDTO.POST_REACTIONS_LOVE_TOTAL) != null ? postInsights.get(FaceReportPostDTO.POST_REACTIONS_LOVE_TOTAL) : 0L);
			faceReportPostDTO.setAnger(postInsights.get(FaceReportPostDTO.POST_REACTIONS_ANGER_TOTAL) != null ? postInsights.get(FaceReportPostDTO.POST_REACTIONS_ANGER_TOTAL) : 0L);
			faceReportPostDTO.setHaha(postInsights.get(FaceReportPostDTO.POST_REACTIONS_HAHA_TOTAL) != null ? postInsights.get(FaceReportPostDTO.POST_REACTIONS_HAHA_TOTAL) : 0L);
			faceReportPostDTO.setWow(postInsights.get(FaceReportPostDTO.POST_REACTIONS_WOW_TOTAL) != null ? postInsights.get(FaceReportPostDTO.POST_REACTIONS_WOW_TOTAL) : 0L);
			faceReportPostDTO.setSorry(postInsights.get(FaceReportPostDTO.POST_REACTIONS_SORRY_TOTAL) != null ? postInsights.get(FaceReportPostDTO.POST_REACTIONS_SORRY_TOTAL) : 0L);

			faceReportPostDTO.setReactions(faceReportPostDTO.getLove() + faceReportPostDTO.getAnger() + faceReportPostDTO.getHaha() +
					faceReportPostDTO.getWow() + faceReportPostDTO.getSorry());

			if(postInsights.containsKey(FaceReportPostDTO.POST_REACTIONS_LIKE_TOTAL) && 
					postInsights.get(FaceReportPostDTO.POST_REACTIONS_LIKE_TOTAL) > 0) {
				faceReportPostDTO.setReactions(faceReportPostDTO.getReactions() + 
						postInsights.get(FaceReportPostDTO.POST_REACTIONS_LIKE_TOTAL));
				faceReportPostDTO.setLikes(postInsights.get(FaceReportPostDTO.POST_REACTIONS_LIKE_TOTAL).intValue());
			} else {
				faceReportPostDTO.setLikes(postInsights.get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_LIKE) != null ? postInsights.get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_LIKE).intValue() : 0);
				faceReportPostDTO.setReactions(faceReportPostDTO.getReactions() + faceReportPostDTO.getLikes());
			}

			faceReportPostDTO.setLovePercentage(Calc.calcPercentageNormal(faceReportPostDTO.getLove().doubleValue(), 
					faceReportPostDTO.getReactions().doubleValue(), 0));
			faceReportPostDTO.setAngerPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getAnger().doubleValue(), 
					faceReportPostDTO.getReactions().doubleValue(), 0));
			faceReportPostDTO.setHahaPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getHaha().doubleValue(), 
					faceReportPostDTO.getReactions().doubleValue(), 0));
			faceReportPostDTO.setWowPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getWow().doubleValue(), 
					faceReportPostDTO.getReactions().doubleValue(), 0));
			faceReportPostDTO.setSorryPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getSorry().doubleValue(), 
					faceReportPostDTO.getReactions().doubleValue(), 0));
			faceReportPostDTO.setLikesPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getLikes().doubleValue(), 
					faceReportPostDTO.getReactions().doubleValue(), 0));

			// ----------- Interactions ---------------- //
			faceReportPostDTO.setComments(postInsights.get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_COMMENT) != null ? postInsights.get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_COMMENT).intValue() : 0);
			faceReportPostDTO.setShares(postInsights.get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_SHARE) != null ? postInsights.get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_SHARE).intValue() : 0);
			faceReportPostDTO.setClicks(postInsights.get(FaceReportPostDTO.POST_CONSUMPTIONS) != null ? postInsights.get(FaceReportPostDTO.POST_CONSUMPTIONS) : 0L);

			faceReportPostDTO.setInteractions(faceReportPostDTO.getComments() + faceReportPostDTO.getShares() + faceReportPostDTO.getClicks());

			faceReportPostDTO.setCommentsPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getComments().doubleValue(), 
					faceReportPostDTO.getInteractions().doubleValue(), 0));
			faceReportPostDTO.setSharesPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getShares().doubleValue(), 
					faceReportPostDTO.getInteractions().doubleValue(), 0));
			faceReportPostDTO.setClicksPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getClicks().doubleValue(), 
					faceReportPostDTO.getInteractions().doubleValue(), 0));

			// Importante!!! Soma das reactions deve ser feita após a contabilização da porcentagem dos comentários, shares e clicks.
			faceReportPostDTO.setInteractions(faceReportPostDTO.getInteractions() + faceReportPostDTO.getLove() + faceReportPostDTO.getAnger() + 
					faceReportPostDTO.getHaha() + faceReportPostDTO.getWow() + faceReportPostDTO.getSorry() + faceReportPostDTO.getLikes());

			if(faceReportPostDTO.getType().equals(ReportFacebookPosts.VIDEO_POSTED)) {

				faceReportPostDTO.setVideoRetention(facebookPostInsightRemote.getVideoRetention(faceReportPostDTO.getPostId(), ReportFacebookPosts.POST_VIDEO_RETENTION_GRAPH));

				Map<String, Long> postVideoInsights = facebookPostInsightRemote.getPostVideoInsights(faceReportPostDTO.getPostId());

				// ----------- Video Reach ---------------- // 
				faceReportPostDTO.setVideoOrganicViews(postVideoInsights.get(ReportFacebookPosts.POST_VIDEO_VIEWS_ORGANIC) != null ? postVideoInsights.get(ReportFacebookPosts.POST_VIDEO_VIEWS_ORGANIC) : 0L);
				faceReportPostDTO.setVideoPaidViews(postVideoInsights.get(ReportFacebookPosts.POST_VIDEO_VIEWS_PAID) != null ? postVideoInsights.get(ReportFacebookPosts.POST_VIDEO_VIEWS_PAID) : 0L);

				faceReportPostDTO.setVideoViews(faceReportPostDTO.getVideoOrganicViews() + faceReportPostDTO.getVideoPaidViews());

				faceReportPostDTO.setVideoOrganicViewsPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getVideoOrganicViews().doubleValue(), 
						faceReportPostDTO.getVideoViews().doubleValue(), 0).intValue());
				faceReportPostDTO.setVideoPaidViewsPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getVideoPaidViews().doubleValue(), 
						faceReportPostDTO.getVideoViews().doubleValue(), 0).intValue());

				// ----------- Video Interactions ---------------- //
				faceReportPostDTO.setVideoAutoPlayedViews(postVideoInsights.get(ReportFacebookPosts.POST_VIDEO_VIEWS_AUTOPLAYED) != null ? postVideoInsights.get(ReportFacebookPosts.POST_VIDEO_VIEWS_AUTOPLAYED) : 0L);
				faceReportPostDTO.setVideoClickToPlayViews(postVideoInsights.get(ReportFacebookPosts.POST_VIDEO_VIEWS_CLICKED_TO_PLAY) != null ? postVideoInsights.get(ReportFacebookPosts.POST_VIDEO_VIEWS_CLICKED_TO_PLAY) : 0L);

				Long videoViewsByType = faceReportPostDTO.getVideoAutoPlayedViews() + faceReportPostDTO.getVideoClickToPlayViews();

				faceReportPostDTO.setVideoAutoPlayedViewsPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getVideoAutoPlayedViews().doubleValue(), 
						videoViewsByType.doubleValue(), 0).intValue());
				faceReportPostDTO.setVideoClickToPlayViewsPercentage(Calc.calcPercentageNormal(faceReportPostDTO.getVideoClickToPlayViews().doubleValue(), 
						videoViewsByType.doubleValue(), 0).intValue());

				// ----------- Video View Time Age/Gender ---------------- //
				faceReportPostDTO.setVideoViewTimeByFemaleUsers(facebookPostInsightRemote.getVideoTotalTimeByAgeGender(faceReportPostDTO.getPostId(), 
						br.com.opsocial.ejb.utils.reports.UtilReports.FEMALE));
				faceReportPostDTO.setVideoViewTimeByMaleUsers(facebookPostInsightRemote.getVideoTotalTimeByAgeGender(faceReportPostDTO.getPostId(), 
						br.com.opsocial.ejb.utils.reports.UtilReports.MALE));
				faceReportPostDTO.setVideoViewTimeByUnidentifiedUsers(facebookPostInsightRemote.getVideoTotalTimeByAgeGender(faceReportPostDTO.getPostId(), 
						br.com.opsocial.ejb.utils.reports.UtilReports.UNIDENTIFIED));
			}

			faceReportPostsDTO.add(faceReportPostDTO);
		}

		reportFacebookPostToPost.setPosts(faceReportPostsDTO);

		return reportFacebookPostToPost;
	}

	public static Map<String, Long> checkPostInsights(Map<String, Long> postInsights) {

		if(!postInsights.containsKey(FaceReportPostDTO.POST_REACTIONS_LIKE_TOTAL)) {
			postInsights.put(FaceReportPostDTO.POST_REACTIONS_LIKE_TOTAL, 0L);
		}
		if(!postInsights.containsKey(FaceReportPostDTO.POST_REACTIONS_ANGER_TOTAL)) {
			postInsights.put(FaceReportPostDTO.POST_REACTIONS_ANGER_TOTAL, 0L);
		}
		if(!postInsights.containsKey(FaceReportPostDTO.POST_REACTIONS_HAHA_TOTAL)) {
			postInsights.put(FaceReportPostDTO.POST_REACTIONS_HAHA_TOTAL, 0L);
		}
		if(!postInsights.containsKey(FaceReportPostDTO.POST_REACTIONS_LOVE_TOTAL)) {
			postInsights.put(FaceReportPostDTO.POST_REACTIONS_LOVE_TOTAL, 0L);
		}
		if(!postInsights.containsKey(FaceReportPostDTO.POST_REACTIONS_SORRY_TOTAL)) {
			postInsights.put(FaceReportPostDTO.POST_REACTIONS_SORRY_TOTAL, 0L);
		}
		if(!postInsights.containsKey(FaceReportPostDTO.POST_REACTIONS_WOW_TOTAL)) {
			postInsights.put(FaceReportPostDTO.POST_REACTIONS_WOW_TOTAL, 0L);
		}

		return postInsights;
	}

	//	public static ReportLinkedinOverviewDTO mountReportLinkedinOverviewDTO(Profile profile, GenericDateInterval genericDateInterval, Integer tableDataLimit) {
	//
	//		ReportLinkedinOverviewDTO reportLinkedinOverviewDTO = new ReportLinkedinOverviewDTO();
	//
	//		MaintenanceTwitterReportRemote twitterReportRemote = (MaintenanceTwitterReportRemote) 
	//				RecoverMaintenance.recoverMaintenance("TwitterReport");
	//
	//		MaintenanceTwitterStatusRemote twitterStatusRemote = (MaintenanceTwitterStatusRemote)
	//				RecoverMaintenance.recoverMaintenance("TwitterStatus");
	//
	//		MaintenanceTwitterStatusReportRemote twitterStatusReportRemote = (MaintenanceTwitterStatusReportRemote)
	//				RecoverMaintenance.recoverMaintenance("TwitterStatusReport");
	//
	//		MaintenanceTwitterFollowerRemote twitterFollowerRemote = (MaintenanceTwitterFollowerRemote)
	//				RecoverMaintenance.recoverMaintenance("TwitterFollower");
	//
	//		MaintenanceTwitterFollowerReportRemote twitterFollowerReportRemote = (MaintenanceTwitterFollowerReportRemote)
	//				RecoverMaintenance.recoverMaintenance("TwitterFollowerReport");
	//
	//		reportTwitterOverview.setDateFrom(genericDateInterval.getDateFrom());
	//		reportTwitterOverview.setDateUntil(genericDateInterval.getDateUntil());
	//
	//		reportTwitterOverview.setFollowersByDay(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.FOLLOWERS_COUNT , genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setTotalOfFollowers(twitterReportRemote.getValue(profile.getNetworkId(), ReportTwitter.FOLLOWERS_COUNT_ALL));
	//		reportTwitterOverview.setFollowersBalance(twitterReportRemote.getSumOfValues(profile.getNetworkId(), ReportTwitter.FOLLOWERS_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setDayWithMoreFollowers(twitterReportRemote.getDayWithMoreFollowers(profile.getNetworkId(), ReportTwitter.FOLLOWERS_COUNT , genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setFollowersGrowthRate(Calc.calcPercentageNormal(reportTwitterOverview.getFollowersBalance().doubleValue(), reportTwitterOverview.getTotalOfFollowers().doubleValue() - reportTwitterOverview.getFollowersBalance().doubleValue(), 2));
	//
	//		reportTwitterOverview.setTweetsSentWithText(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.MSG_SENT_WITH_TEXT_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setTweetsSentWithPhotoVideo(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.MSG_SENT_WITH_PHOTO_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setTweetsSentWithLink(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.MSG_SENT_WITH_LINK_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setAverageTweetsSentPerDay(twitterReportRemote.getAverageOfValues(profile.getNetworkId(), ReportTwitter.MESSAGES_SENT_BY_ME_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//
	//		reportTwitterOverview.setTweetsSentCountByType(twitterReportRemote.getSumsForProperties(profile.getNetworkId(), 
	//				Arrays.asList(ReportTwitter.MSG_SENT_WITH_TEXT_COUNT, ReportTwitter.MSG_SENT_WITH_PHOTO_COUNT, ReportTwitter.MSG_SENT_WITH_LINK_COUNT), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//
	//		Object[] tweetTypeWithMoreTweets = twitterReportRemote.getTweetTypeWithMoreTweets(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());
	//
	//		reportTwitterOverview.setTweetTypeWithMoreTweets((String) tweetTypeWithMoreTweets[0]);
	//		reportTwitterOverview.setTweetTypeWithMoreTweetsPercentage((Integer) tweetTypeWithMoreTweets[1]);
	//
	//		reportTwitterOverview.setTweetsPerHour(twitterStatusRemote.getTweetsPerHour(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setTweetsPerWeekDay(twitterStatusRemote.getTweetsPerWeekDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//
	//		Object[] timeWithMoreTweets = twitterStatusRemote.getTimeWithMoreTweets(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());
	//
	//		reportTwitterOverview.setTimeWithMoreTweets((Integer) timeWithMoreTweets[0]);
	//		reportTwitterOverview.setTimeWithMoreTweetsPercentage((Integer) timeWithMoreTweets[1]);
	//
	//		Object[] weekDayWithMorePosts = twitterStatusRemote.getWeekDayWithMoreTweets(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());
	//
	//		reportTwitterOverview.setWeekDayWithMoreTweets((Integer) weekDayWithMorePosts[0]);
	//		reportTwitterOverview.setWeekDayWithMoreTweetsPercentage((Integer) weekDayWithMorePosts[1]);
	//
	//		reportTwitterOverview.setRepliesByDay(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.REPLIES_OF_ME, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setRetweetsByDay(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.RETWEETS_OF_ME, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setMentionsByDay(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.MENTIONS, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setFavoritesByDay(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.FAVORITE_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//
	//		reportTwitterOverview.setEngagementPercentages(twitterReportRemote.getEngagementPercentages(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setEngagementPeakDay(twitterReportRemote.getEngagementPeakDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//
	//		reportTwitterOverview.setFollowersOfFollowers(twitterFollowerRemote.getFollowersOfFollowers(Long.valueOf(profile.getNetworkId())));
	//
	//		Integer[] womenMenFollowersPerc = twitterFollowerRemote.getWomenMenFollowersPerc(Long.valueOf(profile.getNetworkId()));
	//
	//		reportTwitterOverview.setWomenFollowersPerc(womenMenFollowersPerc[0]);
	//		reportTwitterOverview.setMenFollowersPerc(womenMenFollowersPerc[1]);
	//
	//		reportTwitterOverview.setAverageFollowersOfFollowers(twitterFollowerRemote.getAverageFollowersOfFollowers(Long.valueOf(profile.getNetworkId())));
	//		reportTwitterOverview.setRetweetCount(twitterReportRemote.getSumOfValues(profile.getNetworkId(), ReportTwitter.RETWEETS_OF_ME, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setPotentialReach(twitterFollowerRemote.getMedianFollowersOfFollowers(Long.valueOf(profile.getNetworkId())) * reportTwitterOverview.getRetweetCount());
	//
	//		reportTwitterOverview.setFollowersActivity(twitterFollowerRemote.getFollowersActivity(Long.valueOf(profile.getNetworkId())));
	//		reportTwitterOverview.setFollowersEngagedCount(twitterFollowerReportRemote.getFollowersEngagedCount(Long.valueOf(profile.getNetworkId()), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setFollowersEngagement(twitterFollowerReportRemote.getFollowersEngagement(Long.valueOf(profile.getNetworkId()), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
	//		reportTwitterOverview.setFollowersEngagementPercentage(Calc.calcPercentageNormal(reportTwitterOverview.getFollowersEngagedCount().doubleValue(), 
	//				reportTwitterOverview.getFollowersEngagement().doubleValue(), 0).intValue());
	//
	//		if(!reportTwitterOverview.getFollowersActivity().isEmpty()) {
	//
	//			Entry<String, Long> entry = ((Entry<String, Long>)reportTwitterOverview.getFollowersActivity().entrySet().toArray()[0]);
	//
	//			reportTwitterOverview.setFollowersActivityBiggerPercentage(
	//					Calc.calcPercentageNormal(entry.getValue().doubleValue(), 
	//							reportTwitterOverview.getTotalOfFollowers().doubleValue(), 0).intValue());
	//			reportTwitterOverview.setFollowersActivityBiggerPeriod(entry.getKey());
	//
	//		} else {
	//
	//			reportTwitterOverview.setFollowersActivityBiggerPercentage(0);
	//			reportTwitterOverview.setFollowersActivityBiggerPeriod("1 dia");
	//		}
	//
	//		if(tableDataLimit != null) {
	//			reportTwitterOverview.setCitiesWithMoreFollowers(twitterFollowerRemote.getCitiesWithMoreFollowers(Long.valueOf(profile.getNetworkId()), 0, 5));
	//		} else {
	//			reportTwitterOverview.setCitiesWithMoreFollowers(twitterFollowerRemote.getCitiesWithMoreFollowers(Long.valueOf(profile.getNetworkId()), 0, 10));
	//		}
	//
	//		reportTwitterOverview.setMostInfluentialFollowers(twitterFollowerRemote.getMostInfluentialFollowers(Long.valueOf(profile.getNetworkId()), 0, 100));
	//		reportTwitterOverview.setMostRecentFollowers(twitterFollowerRemote.getMostRecentFollowers(Long.valueOf(profile.getNetworkId()), 0, 100));
	//
	//		List<TwitterReportStatusDTO> twitterReportStatus = MountDTO.mountTwitterReportStatus(
	//				twitterStatusRemote.getTwitterStatus(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400, 0, tableDataLimit), 
	//				profile);
	//
	//		for(TwitterReportStatusDTO twitterReportStatusDTO : twitterReportStatus) {
	//
	//			twitterReportStatusDTO.setRepliesCount(twitterStatusReportRemote.getEntityValueByProperty(
	//					twitterReportStatusDTO.getIdProfile(), twitterReportStatusDTO.getIdStatus(), "repliesOfMeCountAll"));
	//
	//			twitterReportStatusDTO.setRetweetsCount(twitterStatusReportRemote.getEntityValueByProperty(
	//					twitterReportStatusDTO.getIdProfile(), twitterReportStatusDTO.getIdStatus(), "retweetsOfMeCountAll"));
	//
	//			twitterReportStatusDTO.setFavoriteCount(twitterStatusReportRemote.getEntityValueByProperty(
	//					twitterReportStatusDTO.getIdProfile(), twitterReportStatusDTO.getIdStatus(), ReportTwitter.FAVORITE_COUNT_ALL));
	//		}
	//
	//		reportTwitterOverview.setTwitterReportStatus(twitterReportStatus);
	//
	//		return reportTwitterOverview;
	//	}

	public static ReportLinkedinOverviewDTO mountReportLinkedinOverviewDTO(Profile profile,
			GenericDateInterval genericDateInterval, Object object) {


		return null;
	}

	public static ReportTwitterOverviewDTO mountReportTwitterOverviewDTO(Profile profile, GenericDateInterval genericDateInterval, Integer tableDataLimit) {

		ReportTwitterOverviewDTO reportTwitterOverview = new ReportTwitterOverviewDTO();

		MaintenanceTwitterReportRemote twitterReportRemote = (MaintenanceTwitterReportRemote) 
				RecoverMaintenance.recoverMaintenance("TwitterReport");

		MaintenanceTwitterStatusRemote twitterStatusRemote = (MaintenanceTwitterStatusRemote)
				RecoverMaintenance.recoverMaintenance("TwitterStatus");

		MaintenanceTwitterStatusReportRemote twitterStatusReportRemote = (MaintenanceTwitterStatusReportRemote)
				RecoverMaintenance.recoverMaintenance("TwitterStatusReport");

		MaintenanceTwitterFollowerRemote twitterFollowerRemote = (MaintenanceTwitterFollowerRemote)
				RecoverMaintenance.recoverMaintenance("TwitterFollower");

		MaintenanceTwitterFollowerReportRemote twitterFollowerReportRemote = (MaintenanceTwitterFollowerReportRemote)
				RecoverMaintenance.recoverMaintenance("TwitterFollowerReport");

		reportTwitterOverview.setDateFrom(genericDateInterval.getDateFrom());
		reportTwitterOverview.setDateUntil(genericDateInterval.getDateUntil());

		reportTwitterOverview.setFollowersByDay(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.FOLLOWERS_COUNT , genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setTotalOfFollowers(twitterReportRemote.getValue(profile.getNetworkId(), ReportTwitter.FOLLOWERS_COUNT_ALL));
		reportTwitterOverview.setFollowersBalance(twitterReportRemote.getSumOfValues(profile.getNetworkId(), ReportTwitter.FOLLOWERS_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setDayWithMoreFollowers(twitterReportRemote.getDayWithMoreFollowers(profile.getNetworkId(), ReportTwitter.FOLLOWERS_COUNT , genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setFollowersGrowthRate(Calc.calcPercentageNormal(reportTwitterOverview.getFollowersBalance().doubleValue(), reportTwitterOverview.getTotalOfFollowers().doubleValue() - reportTwitterOverview.getFollowersBalance().doubleValue(), 2));

		reportTwitterOverview.setTweetsSentWithText(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.MSG_SENT_WITH_TEXT_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setTweetsSentWithPhotoVideo(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.MSG_SENT_WITH_PHOTO_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setTweetsSentWithLink(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.MSG_SENT_WITH_LINK_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setAverageTweetsSentPerDay(twitterReportRemote.getAverageOfValues(profile.getNetworkId(), ReportTwitter.MESSAGES_SENT_BY_ME_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportTwitterOverview.setTweetsSentCountByType(twitterReportRemote.getSumsForProperties(profile.getNetworkId(), 
				Arrays.asList(ReportTwitter.MSG_SENT_WITH_TEXT_COUNT, ReportTwitter.MSG_SENT_WITH_PHOTO_COUNT, ReportTwitter.MSG_SENT_WITH_LINK_COUNT), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		Object[] tweetTypeWithMoreTweets = twitterReportRemote.getTweetTypeWithMoreTweets(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportTwitterOverview.setTweetTypeWithMoreTweets((String) tweetTypeWithMoreTweets[0]);
		reportTwitterOverview.setTweetTypeWithMoreTweetsPercentage((Integer) tweetTypeWithMoreTweets[1]);

		reportTwitterOverview.setTweetsPerHour(twitterStatusRemote.getTweetsPerHour(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setTweetsPerWeekDay(twitterStatusRemote.getTweetsPerWeekDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		Object[] timeWithMoreTweets = twitterStatusRemote.getTimeWithMoreTweets(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportTwitterOverview.setTimeWithMoreTweets((Integer) timeWithMoreTweets[0]);
		reportTwitterOverview.setTimeWithMoreTweetsPercentage((Integer) timeWithMoreTweets[1]);

		Object[] weekDayWithMorePosts = twitterStatusRemote.getWeekDayWithMoreTweets(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil());

		reportTwitterOverview.setWeekDayWithMoreTweets((Integer) weekDayWithMorePosts[0]);
		reportTwitterOverview.setWeekDayWithMoreTweetsPercentage((Integer) weekDayWithMorePosts[1]);

		reportTwitterOverview.setRepliesByDay(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.REPLIES_OF_ME, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setRetweetsByDay(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.RETWEETS_OF_ME, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setMentionsByDay(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.MENTIONS, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setFavoritesByDay(twitterReportRemote.getEntitiesByProfile(profile.getNetworkId(), ReportTwitter.FAVORITE_COUNT, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportTwitterOverview.setEngagementPercentages(twitterReportRemote.getEngagementPercentages(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setEngagementPeakDay(twitterReportRemote.getEngagementPeakDay(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));

		reportTwitterOverview.setFollowersOfFollowers(twitterFollowerRemote.getFollowersOfFollowers(Long.valueOf(profile.getNetworkId())));

		Integer[] womenMenFollowersPerc = twitterFollowerRemote.getWomenMenFollowersPerc(Long.valueOf(profile.getNetworkId()));

		reportTwitterOverview.setWomenFollowersPerc(womenMenFollowersPerc[0]);
		reportTwitterOverview.setMenFollowersPerc(womenMenFollowersPerc[1]);

		reportTwitterOverview.setAverageFollowersOfFollowers(twitterFollowerRemote.getAverageFollowersOfFollowers(Long.valueOf(profile.getNetworkId())));
		reportTwitterOverview.setRetweetCount(twitterReportRemote.getSumOfValues(profile.getNetworkId(), ReportTwitter.RETWEETS_OF_ME, genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setPotentialReach(twitterFollowerRemote.getMedianFollowersOfFollowers(Long.valueOf(profile.getNetworkId())) * reportTwitterOverview.getRetweetCount());

		reportTwitterOverview.setFollowersActivity(twitterFollowerRemote.getFollowersActivity(Long.valueOf(profile.getNetworkId())));
		reportTwitterOverview.setFollowersEngagedCount(twitterFollowerReportRemote.getFollowersEngagedCount(Long.valueOf(profile.getNetworkId()), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setFollowersEngagement(twitterFollowerReportRemote.getFollowersEngagement(Long.valueOf(profile.getNetworkId()), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil()));
		reportTwitterOverview.setFollowersEngagementPercentage(Calc.calcPercentageNormal(reportTwitterOverview.getFollowersEngagedCount().doubleValue(), 
				reportTwitterOverview.getFollowersEngagement().doubleValue(), 0).intValue());

		if(!reportTwitterOverview.getFollowersActivity().isEmpty()) {

			Entry<String, Long> entry = ((Entry<String, Long>)reportTwitterOverview.getFollowersActivity().entrySet().toArray()[0]);

			reportTwitterOverview.setFollowersActivityBiggerPercentage(
					Calc.calcPercentageNormal(entry.getValue().doubleValue(), 
							reportTwitterOverview.getTotalOfFollowers().doubleValue(), 0).intValue());
			reportTwitterOverview.setFollowersActivityBiggerPeriod(entry.getKey());

		} else {

			reportTwitterOverview.setFollowersActivityBiggerPercentage(0);
			reportTwitterOverview.setFollowersActivityBiggerPeriod("1 dia");
		}

		if(tableDataLimit != null) {
			reportTwitterOverview.setCitiesWithMoreFollowers(twitterFollowerRemote.getCitiesWithMoreFollowers(Long.valueOf(profile.getNetworkId()), 0, 5));
		} else {
			reportTwitterOverview.setCitiesWithMoreFollowers(twitterFollowerRemote.getCitiesWithMoreFollowers(Long.valueOf(profile.getNetworkId()), 0, 10));
		}

		reportTwitterOverview.setMostInfluentialFollowers(twitterFollowerRemote.getMostInfluentialFollowers(Long.valueOf(profile.getNetworkId()), 0, 100));
		reportTwitterOverview.setMostRecentFollowers(twitterFollowerRemote.getMostRecentFollowers(Long.valueOf(profile.getNetworkId()), 0, 100));

		List<TwitterReportStatusDTO> twitterReportStatus = MountDTO.mountTwitterReportStatus(
				twitterStatusRemote.getTwitterStatus(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400, 0, tableDataLimit), 
				profile);

		for(TwitterReportStatusDTO twitterReportStatusDTO : twitterReportStatus) {

			twitterReportStatusDTO.setRepliesCount(twitterStatusReportRemote.getEntityValueByProperty(
					twitterReportStatusDTO.getIdProfile(), twitterReportStatusDTO.getIdStatus(), "repliesOfMeCountAll"));

			twitterReportStatusDTO.setRetweetsCount(twitterStatusReportRemote.getEntityValueByProperty(
					twitterReportStatusDTO.getIdProfile(), twitterReportStatusDTO.getIdStatus(), "retweetsOfMeCountAll"));

			twitterReportStatusDTO.setFavoriteCount(twitterStatusReportRemote.getEntityValueByProperty(
					twitterReportStatusDTO.getIdProfile(), twitterReportStatusDTO.getIdStatus(), ReportTwitter.FAVORITE_COUNT_ALL));
		}

		reportTwitterOverview.setTwitterReportStatus(twitterReportStatus);

		return reportTwitterOverview;
	}

	public static List<TwitterReportDTO> mountTwitterReportsDTOList(List<TwitterReport> twitterReports) {

		List<TwitterReportDTO> twitterReporstDTO = new ArrayList<TwitterReportDTO>();

		for(TwitterReport twitterReport : twitterReports) {
			twitterReporstDTO.add(MountDTO.mountTwitterReport(twitterReport));
		}

		return twitterReporstDTO;
	}

	public static GenericDateInterval getTwitterReportDates(Calendar untilDate, int period) {

		Calendar fromDate = new GregorianCalendar(untilDate.get(Calendar.YEAR),
				untilDate.get(Calendar.MONTH),
				untilDate.get(Calendar.DAY_OF_MONTH));
		fromDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - (period - 1));

		return new GenericDateInterval(UtilFunctions.getDateForTwitterReport(fromDate), UtilFunctions.getDateForTwitterReport(untilDate));
	}

	public static GenericDateInterval getTwitterReportDates(int period) {

		Calendar untilDate = new GregorianCalendar();
		untilDate.set(Calendar.DAY_OF_MONTH,untilDate.get(Calendar.DAY_OF_MONTH) - 1);
		Calendar fromDate = new GregorianCalendar(untilDate.get(Calendar.YEAR),
				untilDate.get(Calendar.MONTH),
				untilDate.get(Calendar.DAY_OF_MONTH));
		fromDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - (period - 1));

		return new GenericDateInterval(UtilFunctions.getDateForTwitterReport(fromDate), UtilFunctions.getDateForTwitterReport(untilDate));
	}

	public static GenericDateInterval getInstagramReportDates(Calendar untilDate, int period) {

		Calendar fromDate = new GregorianCalendar(untilDate.get(Calendar.YEAR),
				untilDate.get(Calendar.MONTH),
				untilDate.get(Calendar.DAY_OF_MONTH));
		fromDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - (period - 1));

		return new GenericDateInterval(UtilFunctions.getMidnightDateNoDaylightTime(fromDate.getTime()), 
				UtilFunctions.getMidnightDateNoDaylightTime(untilDate.getTime()));
	}

	public static GenericDateInterval getInstagramReportDates(int period) {

		Calendar untilDate = new GregorianCalendar();
		untilDate.set(Calendar.DAY_OF_MONTH,untilDate.get(Calendar.DAY_OF_MONTH) - 1);
		Calendar fromDate = new GregorianCalendar(untilDate.get(Calendar.YEAR),
				untilDate.get(Calendar.MONTH),
				untilDate.get(Calendar.DAY_OF_MONTH));
		fromDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - (period - 1));

		return new GenericDateInterval(UtilFunctions.getMidnightDateNoDaylightTime(fromDate.getTime()), 
				UtilFunctions.getMidnightDateNoDaylightTime(untilDate.getTime()));
	}

	public static GenericDateInterval getFacebookReportsDates(Calendar untilDate, int period) {

		Calendar fromDate = new GregorianCalendar(untilDate.get(Calendar.YEAR),
				untilDate.get(Calendar.MONTH),
				untilDate.get(Calendar.DAY_OF_MONTH));
		fromDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - (period - 1));

		return new GenericDateInterval(UtilFunctions.getDateForFacebookReport(fromDate), UtilFunctions.getDateForFacebookReport(untilDate));
	}

	public static GenericDateInterval getFacebookReportDates(int period) {

		Calendar untilDate = new GregorianCalendar();
		untilDate.set(Calendar.DAY_OF_MONTH,untilDate.get(Calendar.DAY_OF_MONTH) - 3);
		Calendar fromDate = new GregorianCalendar(untilDate.get(Calendar.YEAR),
				untilDate.get(Calendar.MONTH),
				untilDate.get(Calendar.DAY_OF_MONTH));
		fromDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - (period - 1));

		GenericDateInterval genericDateInterval = new GenericDateInterval(UtilFunctions.getDateForFacebookReport(fromDate), UtilFunctions.getDateForFacebookReport(untilDate));

		return genericDateInterval;
	}

	public static Long getDateForFanPageGrowth() {

		Calendar date = new GregorianCalendar();
		date.add(Calendar.YEAR, - 1);
		date.add(Calendar.MONTH, 1);
		date.set(Calendar.DAY_OF_MONTH, 1);
		date.setTimeZone(TimeZone.getTimeZone("America/Phoenix"));
		date.set(Calendar.AM_PM, Calendar.AM);
		date.set(Calendar.HOUR_OF_DAY, 0);
		date.set(Calendar.MINUTE, 0);
		date.set(Calendar.SECOND, 0);

		return date.getTimeInMillis() / 1000L;
	}

	public static AgeGroup getMajorAgeGroupVideoViewTimeUsers(List<AgeGroup> ageGroups) {

		AgeGroup majorAgeGroup = new AgeGroup();
		majorAgeGroup.setInitialAgeGroup("13");
		majorAgeGroup.setEndAgeGroup("17");
		Long viewTimeTotal = 0L;
		for(AgeGroup ageGroup : ageGroups) {
			viewTimeTotal += ageGroup.getValue();
			if(majorAgeGroup.getValue() < ageGroup.getValue()) {
				majorAgeGroup = ageGroup;
			}
		}

		majorAgeGroup.setPercentage(Calc.calcPercentageNormal(majorAgeGroup.getValue().doubleValue(), viewTimeTotal.doubleValue(), 0).intValue());

		return majorAgeGroup;
	}

	public static List<AgeGroup> groupAgeGroupVideoViewTime(List<AgeGroup> videoViewTimeByGender, List<AgeGroup> ageGroups) {

		for(AgeGroup ageGroup : ageGroups) {
			for(AgeGroup ageGroupByGender : videoViewTimeByGender) {
				if(ageGroup.getInitialAgeGroup().equals(ageGroupByGender.getInitialAgeGroup())) {
					ageGroup.setValue(ageGroup.getValue() + ageGroupByGender.getValue());
					break;
				}
			}
		}

		return ageGroups;
	}

	public static Long getValueForProperty(List<PropertyValue> list, String property) {

		Long value = 0L;
		for(PropertyValue propertyValue : list) {
			if(propertyValue.getProperty().equals(property)) {
				value = propertyValue.getValue();
				break;
			}
		}

		return value;
	}
	
	public static Long getTotalForInstagram(List<InstagramSimpleDataDTO> instagramSimpleDataList) {
		
		Long total = 0L;
		for(InstagramSimpleDataDTO instagramSimpleData : instagramSimpleDataList) {
			total += (Long) instagramSimpleData.getValue();
		}
		
		return total;
	}

	public static Long getTotalForAgeGroups(List<AgeGroup> ageGroups) {

		Long total = 0L;
		for(AgeGroup ageGroup : ageGroups) {
			total += ageGroup.getValue();
		}

		return total;
	}

	public static Long getTotalForProperties(List<PropertyValue> list) {

		Long total = 0L;
		for(PropertyValue propertyValue : list) {
			total += propertyValue.getValue();
		}

		return total;
	}

	public static Long getTotalForMonths(List<SumByDate> list) {

		Long total = 0L;
		for (SumByDate sumByDate : list) {
			total += sumByDate.getSum();
		}

		return total;
	}

	public static Long getTotalCount(List<FanPageGrowth> list) {

		Long totalCount = 0L;
		for (FanPageGrowth fanPageGrowth : list) {
			totalCount += fanPageGrowth.getSum();
		}

		return totalCount;
	}

	public static List<AgeGroup> getAboutReachedUsers(Map<String, Long> AboutReachedUsers, Character gender) {

		List<AgeGroup> ageGroups = new ArrayList<AgeGroup>();

		for(Map.Entry<String, Long> entry : AboutReachedUsers.entrySet()) {

			AgeGroup ageGroup = new AgeGroup();

			if(gender.equals('M')) {
				if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_MALE_13_AMONG_17)) {
					ageGroup.setInitialAgeGroup("13");
					ageGroup.setEndAgeGroup("17");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_MALE_18_AMONG_24)) {
					ageGroup.setInitialAgeGroup("18");
					ageGroup.setEndAgeGroup("24");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_MALE_25_AMONG_34)) {
					ageGroup.setInitialAgeGroup("25");
					ageGroup.setEndAgeGroup("34");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_MALE_35_AMONG_44)) {
					ageGroup.setInitialAgeGroup("35");
					ageGroup.setEndAgeGroup("44");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_MALE_45_AMONG_54)) {
					ageGroup.setInitialAgeGroup("45");
					ageGroup.setEndAgeGroup("54");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_MALE_55_AMONG_64)) {
					ageGroup.setInitialAgeGroup("55");
					ageGroup.setEndAgeGroup("64");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_MALE_65_PLUS)) {
					ageGroup.setInitialAgeGroup("65");
					ageGroup.setEndAgeGroup("");
				}
			} else {
				if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_FEMALE_13_AMONG_17)) {
					ageGroup.setInitialAgeGroup("13");
					ageGroup.setEndAgeGroup("17");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_FEMALE_18_AMONG_24)) {
					ageGroup.setInitialAgeGroup("18");
					ageGroup.setEndAgeGroup("24");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_FEMALE_25_AMONG_34)) {
					ageGroup.setInitialAgeGroup("25");
					ageGroup.setEndAgeGroup("34");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_FEMALE_35_AMONG_44)) {
					ageGroup.setInitialAgeGroup("35");
					ageGroup.setEndAgeGroup("44");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_FEMALE_45_AMONG_54)) {
					ageGroup.setInitialAgeGroup("45");
					ageGroup.setEndAgeGroup("54");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_FEMALE_55_AMONG_64)) {
					ageGroup.setInitialAgeGroup("55");
					ageGroup.setEndAgeGroup("64");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_IMPRESSIONS_FEMALE_65_PLUS)) {
					ageGroup.setInitialAgeGroup("65");
					ageGroup.setEndAgeGroup("");
				}
			}

			if(ageGroup.getInitialAgeGroup() != null) {
				ageGroup.setValue(entry.getValue());

				ageGroups.add(ageGroup);
			}

		}

		return ageGroups;
	}

	public static List<AgeGroup> getAboutYourFans(Map<String, Long> AboutYourFans, Character gender) {

		List<AgeGroup> ageGroups = new ArrayList<AgeGroup>();

		for(Map.Entry<String, Long> entry : AboutYourFans.entrySet()) {

			AgeGroup ageGroup = new AgeGroup();

			if(gender.equals('M')) {
				if(entry.getKey().equals(ReportFacebook.PAGE_FANS_MALE_13_AMONG_17)) {
					ageGroup.setInitialAgeGroup("13");
					ageGroup.setEndAgeGroup("17");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_MALE_18_AMONG_24)) {
					ageGroup.setInitialAgeGroup("18");
					ageGroup.setEndAgeGroup("24");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_MALE_25_AMONG_34)) {
					ageGroup.setInitialAgeGroup("25");
					ageGroup.setEndAgeGroup("34");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_MALE_35_AMONG_44)) {
					ageGroup.setInitialAgeGroup("35");
					ageGroup.setEndAgeGroup("44");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_MALE_45_AMONG_54)) {
					ageGroup.setInitialAgeGroup("45");
					ageGroup.setEndAgeGroup("54");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_MALE_55_AMONG_64)) {
					ageGroup.setInitialAgeGroup("55");
					ageGroup.setEndAgeGroup("64");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_MALE_65_PLUS)) {
					ageGroup.setInitialAgeGroup("65");
					ageGroup.setEndAgeGroup("");
				}
			} else {
				if(entry.getKey().equals(ReportFacebook.PAGE_FANS_FEMALE_13_AMONG_17)) {
					ageGroup.setInitialAgeGroup("13");
					ageGroup.setEndAgeGroup("17");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_FEMALE_18_AMONG_24)) {
					ageGroup.setInitialAgeGroup("18");
					ageGroup.setEndAgeGroup("24");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_FEMALE_25_AMONG_34)) {
					ageGroup.setInitialAgeGroup("25");
					ageGroup.setEndAgeGroup("34");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_FEMALE_35_AMONG_44)) {
					ageGroup.setInitialAgeGroup("35");
					ageGroup.setEndAgeGroup("44");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_FEMALE_45_AMONG_54)) {
					ageGroup.setInitialAgeGroup("45");
					ageGroup.setEndAgeGroup("54");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_FEMALE_55_AMONG_64)) {
					ageGroup.setInitialAgeGroup("55");
					ageGroup.setEndAgeGroup("64");
				} else if(entry.getKey().equals(ReportFacebook.PAGE_FANS_FEMALE_65_PLUS)) {
					ageGroup.setInitialAgeGroup("65");
					ageGroup.setEndAgeGroup("");
				}
			}

			if(ageGroup.getInitialAgeGroup() != null) {
				ageGroup.setValue(entry.getValue());

				ageGroups.add(ageGroup);
			}
		}

		return ageGroups;
	}

	public static List<FanPageGrowth> getLikesBalanceByDay(List<FanPageGrowth> likesByDay, List<FanPageGrowth> unlikesByDay) {

		List<FanPageGrowth> likesBalanceByDay = new ArrayList<FanPageGrowth>();

		for (int i = 0; i < likesByDay.size(); i++) {

			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setDate(likesByDay.get(i).getDate());
			fanPageGrowth.setSum(likesByDay.get(i).getSum() - unlikesByDay.get(i).getSum());

			likesBalanceByDay.add(fanPageGrowth);
		}

		return likesBalanceByDay;
	}

	public static FanPageGrowth getMajorFanPageGrowth(List<FanPageGrowth> fanPageGrowthList, Long fansUntilOneYearAgo) {

		FanPageGrowth maxFanPageGrowth = null;

		if(!fanPageGrowthList.isEmpty()) {

			Long oldTotal = fansUntilOneYearAgo;
			Long newTotal = oldTotal + fanPageGrowthList.get(0).getSum();

			fanPageGrowthList.get(0).setGrowthRate(Calc.calcPercentageGrowth(oldTotal.doubleValue(), newTotal.doubleValue(), 0).intValue());

			oldTotal = newTotal;

			maxFanPageGrowth = fanPageGrowthList.get(0); 

			for (int i = 1; i < fanPageGrowthList.size(); i++) {

				newTotal = oldTotal + fanPageGrowthList.get(i).getSum();

				fanPageGrowthList.get(i).setGrowthRate(Calc.calcPercentageGrowth(oldTotal.doubleValue(), newTotal.doubleValue(), 0).intValue());

				if(fanPageGrowthList.get(i).getGrowthRate() >= maxFanPageGrowth.getGrowthRate()) {
					maxFanPageGrowth = fanPageGrowthList.get(i);
				}

				oldTotal = newTotal;
			}
		} else {
			maxFanPageGrowth = new FanPageGrowth();
			maxFanPageGrowth.setDay(1);
			maxFanPageGrowth.setMonth(1);
			maxFanPageGrowth.setYear(1);
			maxFanPageGrowth.setSum(0L);
		}

		return maxFanPageGrowth;
	}

	public static FanPageGrowth getMinorFanPageGrowth(List<FanPageGrowth> fanPageGrowthList, Long fansUntilOneYearAgo, Long firstDate) {

		FanPageGrowth minFanPageGrowth = new FanPageGrowth();
		minFanPageGrowth.setGrowthRate(1000000000);

		Calendar firstDateCalendar = Calendar.getInstance();
		firstDateCalendar.setTimeInMillis(firstDate * 1000L);
		firstDateCalendar = UtilFunctions.getCalendarDateForFacebookReport(firstDateCalendar);

		Long oldTotal = fansUntilOneYearAgo;
		Long newTotal = oldTotal;

		/* Importante manter fanPageGrowthList.size() - 1, pois exclui o mês corrente da análise, mês este que está em andamento 
		 * e por isso não pode ser comparado com os meses que são "cheios", tornando assim a análise mais concisa */
		for(int i = 0; i < fanPageGrowthList.size() - 1; i++) {

			newTotal = oldTotal + fanPageGrowthList.get(i).getSum();

			fanPageGrowthList.get(i).setGrowthRate(Calc.calcPercentageGrowth(oldTotal.doubleValue(), newTotal.doubleValue(), 0).intValue());

			/* Verificação que mantêm os meses em que não conseguimos recuperar dados fora da análise de menor crescimento */
			if(fanPageGrowthList.get(i).getGrowthRate() <= minFanPageGrowth.getGrowthRate() && 
					((firstDateCalendar.get(Calendar.YEAR) == fanPageGrowthList.get(i).getYear() && 
					fanPageGrowthList.get(i).getMonth() >= firstDateCalendar.get(Calendar.MONTH) + 1) || 
							(fanPageGrowthList.get(i).getYear() > firstDateCalendar.get(Calendar.YEAR)))) {
				minFanPageGrowth = fanPageGrowthList.get(i);
			}

			oldTotal = newTotal;
		}

		return minFanPageGrowth;
	}

	public static SumByDate getMajorSumByDateMonth(List<SumByDate> sumByDates) {

		SumByDate majorSumByDateMonth = null;

		if(!sumByDates.isEmpty()) {

			majorSumByDateMonth = sumByDates.get(0);

			for(SumByDate sumByDate : sumByDates) {

				if(sumByDate.getSum() >= majorSumByDateMonth.getSum()) {
					majorSumByDateMonth = sumByDate;
				}
			}	
		} else {

			Calendar now = Calendar.getInstance();
			now.setTimeZone(TimeZone.getTimeZone("America/Phoenix"));
			now.set(Calendar.AM_PM, Calendar.AM);
			now.set(Calendar.HOUR_OF_DAY, 0);
			now.set(Calendar.MINUTE, 0);
			now.set(Calendar.SECOND, 0);

			majorSumByDateMonth = new SumByDate();
			majorSumByDateMonth.setMonth(now.get(Calendar.MONTH) + 1);
			majorSumByDateMonth.setYear(now.get(Calendar.YEAR));
			majorSumByDateMonth.setSum(0L);
		}

		return majorSumByDateMonth;
	}

	public static FanPageGrowth getMajorFanPageGrowthDaySum(List<FanPageGrowth> fanPageGrowthList) {

		FanPageGrowth maxFanPageGrowth = null;

		if(!fanPageGrowthList.isEmpty()) {

			maxFanPageGrowth = fanPageGrowthList.get(0); 

			for (int i = 1; i < fanPageGrowthList.size(); i++) {

				if(fanPageGrowthList.get(i).getSum() >= maxFanPageGrowth.getSum()) {
					maxFanPageGrowth = fanPageGrowthList.get(i);
				}

			}
		} else {

			maxFanPageGrowth = new FanPageGrowth();
			maxFanPageGrowth.setDate(new Date().getTime() / 1000L);
			maxFanPageGrowth.setSum(0L);
		}

		return maxFanPageGrowth;
	}

	public static Map<Integer, Long> getInteractionsPerHour(Map<Integer, Long> likesPerHour, Map<Integer, Long> commentsPerHour) {

		Map<Integer, Long> interactionsPerHour = new TreeMap<Integer, Long>();

		for(int i = 0; i < 24; i++) {
			interactionsPerHour.put(i, likesPerHour.get(i) + commentsPerHour.get(i));
		}

		return interactionsPerHour;
	}

	public static Long getInteractionsCount(Map<Integer, Long> interactionsPerHour) {

		Long interactionsCount = 0L;

		for(Map.Entry<Integer, Long> interactionPerHour : interactionsPerHour.entrySet()) {
			interactionsCount += interactionPerHour.getValue();
		}

		return interactionsCount;
	}
	
	public static InstagramPostsStatsYearDTO getInstagramPostsStatsYear(Integer year, Profile profile) {
		
		MaintenanceInstagramReportPostRemote instagramReportPostRemote = (MaintenanceInstagramReportPostRemote)
				RecoverMaintenance.recoverMaintenance("InstagramReportPost");

		MaintenanceInstagramPostCommentRemote instagramPostCommentRemote = (MaintenanceInstagramPostCommentRemote)
				RecoverMaintenance.recoverMaintenance("InstagramPostComment");

		MaintenanceInstagramPostLikeRemote instagramPostLikeRemote = (MaintenanceInstagramPostLikeRemote)
				RecoverMaintenance.recoverMaintenance("InstagramPostLike");
		
		InstagramPostsStatsYearDTO instagramPostsStatsYear = new InstagramPostsStatsYearDTO();
		instagramPostsStatsYear.setYear(year);
		instagramPostsStatsYear.setPostsTotal(instagramReportPostRemote.getCountOfPosts(profile.getNetworkId(), year));
		instagramPostsStatsYear.setPostsPerMonthSimpleData(instagramReportPostRemote.getPostsPerMonthYearSimpleData(profile.getNetworkId(), year));
		
		instagramPostsStatsYear.setLikesPerMonthSimpleData(instagramPostLikeRemote.getLikesPerMonthYearSimpleData(profile.getNetworkId(), year));
		instagramPostsStatsYear.setLikesTotal(getTotalForInstagram(instagramPostsStatsYear.getLikesPerMonthSimpleData()));
		instagramPostsStatsYear.setAverageLikesPerMonth(Calc.calcAverage(instagramPostsStatsYear.getLikesTotal().doubleValue(), 
				instagramPostsStatsYear.getLikesPerMonthSimpleData().size(), 0).intValue());
		
		instagramPostsStatsYear.setCommentsPerMonthSimpleData(instagramPostCommentRemote.getCommentsPerMonthYearSimpleData(profile.getNetworkId(), year));
		instagramPostsStatsYear.setCommentsTotal(getTotalForInstagram(instagramPostsStatsYear.getCommentsPerMonthSimpleData()));
		instagramPostsStatsYear.setAverageCommentsPerMonth(Calc.calcAverage(instagramPostsStatsYear.getCommentsTotal().doubleValue(), 
				instagramPostsStatsYear.getCommentsPerMonthSimpleData().size(), 0).intValue());
		
		instagramPostsStatsYear.setPostFrequencyByMonth(Calc.calcAverage(instagramPostsStatsYear.getPostsTotal().doubleValue(), 
				instagramPostsStatsYear.getPostsPerMonthSimpleData().size(), 0).intValue());

		return instagramPostsStatsYear;
	}

	public static List<InstagramInteractionRankingDTO> getInstagramInteractionsRanking(Map<Integer, Long> interactionsPerHour) {

		List<InstagramInteractionRankingDTO> instagramInteractionsRanking = new ArrayList<InstagramInteractionRankingDTO>();
		Long interactionsCount = getInteractionsCount(interactionsPerHour);

		for(Map.Entry<Integer, Long> interactionPerHour : interactionsPerHour.entrySet()) {

			InstagramInteractionRankingDTO instagramInteractionRanking = new InstagramInteractionRankingDTO();
			instagramInteractionRanking.setHour(interactionPerHour.getKey());
			instagramInteractionRanking.setCount(interactionPerHour.getValue());
			instagramInteractionRanking.setPercentage(Calc.calcPercentageNormal(interactionPerHour.getValue().doubleValue(), 
					interactionsCount.doubleValue(), 2));

			instagramInteractionsRanking.add(instagramInteractionRanking);
		}

		Collections.sort(instagramInteractionsRanking);
		Collections.reverse(instagramInteractionsRanking);

		return instagramInteractionsRanking;
	}

	public static List<ReportFacebookLocation> normalizeFansLikeSource(Map<String, Long> fansLikeSource) {

		Map<String, Long> fansLikeSourceNormalized = new HashMap<String, Long>();

		// Não contabilizar algumas origens.
		fansLikeSource.remove(ReportFacebook.PAGE_FANS_PROFILE_CONNECT);
		fansLikeSource.remove(ReportFacebook.PAGE_FANS_PAGE_BROWSER);
		fansLikeSource.remove(ReportFacebook.PAGE_FANS_MOBILE_PAGE_BROWSER);
		fansLikeSource.remove(ReportFacebook.PAGE_FANS_TICKER);

		// Normalizar valores "Páginas que curtiram outras páginas".
		HashSet<String> pageFansFromPages = new HashSet<String>();
		pageFansFromPages.add(ReportFacebook.PAGE_FANS_PAGE_INVITE);
		pageFansFromPages.add(ReportFacebook.PAGE_FANS_FAVORITES);

		// Normalizar valores "Recomendações do Facebook".
		HashSet<String> pageFansFromRecommendations = new HashSet<String>();
		pageFansFromRecommendations.add(ReportFacebook.PAGE_FANS_RECOMMENDED_PAGES);
		pageFansFromRecommendations.add(ReportFacebook.PAGE_FANS_FEED_CHAINING);

		// Normalizar valores "Sugestões ao curtir novas páginas".
		HashSet<String> pageFansFromLikeSuggestions = new HashSet<String>();
		pageFansFromLikeSuggestions.add(ReportFacebook.PAGE_FANS_ALL_CATEGORY_PYML);
		pageFansFromLikeSuggestions.add(ReportFacebook.PAGE_FANS_PAGE_SUGGESTIONS_ON_LIKING);
		pageFansFromLikeSuggestions.add(ReportFacebook.PAGE_FANS_MOBILE_PAGE_SUGGESTIONS_ON_LIKING);

		fansLikeSourceNormalized.put(PAGE_FANS_FROM_PAGES, 0L);
		fansLikeSourceNormalized.put(PAGE_FANS_FROM_RECOMMENDATIONS, 0L);
		fansLikeSourceNormalized.put(PAGE_FANS_FROM_LIKE_SUGGESTIONS, 0L);

		for(Map.Entry<String, Long> fanLikeSource : fansLikeSource.entrySet()) { 

			if(pageFansFromPages.contains(fanLikeSource.getKey())) {
				fansLikeSourceNormalized.put(PAGE_FANS_FROM_PAGES, 
						fansLikeSourceNormalized.get(PAGE_FANS_FROM_PAGES) + fanLikeSource.getValue());
			} else if(pageFansFromRecommendations.contains(fanLikeSource.getKey())) {
				fansLikeSourceNormalized.put(PAGE_FANS_FROM_RECOMMENDATIONS, 
						fansLikeSourceNormalized.get(PAGE_FANS_FROM_RECOMMENDATIONS) + fanLikeSource.getValue());
			} else if(pageFansFromLikeSuggestions.contains(fanLikeSource.getKey())) {
				fansLikeSourceNormalized.put(PAGE_FANS_FROM_LIKE_SUGGESTIONS, 
						fansLikeSourceNormalized.get(PAGE_FANS_FROM_LIKE_SUGGESTIONS) + fanLikeSource.getValue());
			} else {
				fansLikeSourceNormalized.put(fanLikeSource.getKey(), fanLikeSource.getValue());
			}
		}

		List<Map.Entry<String, Long>> entries =
				new ArrayList<Map.Entry<String, Long>>(fansLikeSourceNormalized.entrySet());

		Collections.sort(entries, new Comparator<Map.Entry<String, Long>>() {

			public int compare(Map.Entry<String, Long> a, Map.Entry<String, Long> b) {
				return b.getValue().compareTo(a.getValue());
			}
		});

		List<ReportFacebookLocation> reportFacebookCountries = new ArrayList<ReportFacebookLocation>();
		for (Map.Entry<String, Long> entry : entries) {

			ReportFacebookLocation reportFacebookCountry = new ReportFacebookLocation();
			reportFacebookCountry.setLocation(OpSocialBackApplication.utilFunctionsService.getLikeSourceName(entry.getKey()));
			reportFacebookCountry.setValue(entry.getValue());

			reportFacebookCountries.add(reportFacebookCountry);
		}

		return reportFacebookCountries;
	}

	public static Character getReportState(Profile profile) {

		MaintenanceReportGenerateRemote remoteReportGenerate = (MaintenanceReportGenerateRemote)
				RecoverMaintenance.recoverMaintenance("ReportGenerate");

		return remoteReportGenerate.getEntity(profile.getNetworkId(), profile.getType()).getState();
	}

}
