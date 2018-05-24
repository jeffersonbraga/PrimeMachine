package br.com.opsocial.server.utils.reports;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import twitter4j.MediaEntity;
import twitter4j.Paging;
import twitter4j.RateLimitStatus;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.URLEntity;
import twitter4j.User;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceReportControlRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterFollowerReportRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterReportRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterStatusRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterStatusReportRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.ReportControl;
import br.com.opsocial.ejb.entity.report.TwitterFollowerReport;
import br.com.opsocial.ejb.entity.report.TwitterReport;
import br.com.opsocial.ejb.entity.report.TwitterStatus;
import br.com.opsocial.ejb.entity.report.TwitterStatusReport;

public class ReportTwitter implements Runnable {

	private TwitterIntegration twitterIntegration;
	private Twitter twitter;
	private Profile profile;
	
	private TwitterReport twitterReport;
	private TwitterStatus twitterStatus;
	private List<TwitterStatus> twitterStatusList;
	private List<TwitterStatusReport> twitterStatusReportList;
	
	private MaintenanceProfileRemote profileRemote;
	private MaintenanceTwitterStatusRemote twitterStatusRemote;
	private MaintenanceTwitterReportRemote twitterReportRemote;
	private MaintenanceTwitterStatusReportRemote twitterStatusReportRemote;
	private MaintenanceReportControlRemote reportControlRemote;
	private MaintenanceTwitterFollowerReportRemote twitterFollowerReportRemote;

	private Long reportDateStamp;

	private Long mentionsCountAll;
	private Long mentionsCount;

	private Long retweetsOfMeCountAll;
	private Long retweetsOfMeCount;

	private Long repliesByMeCountAll;
	private Long repliesByMeCount;

	private Long repliesOfMeCountAll;
	private Long repliesOfMeCount;

	private Long favoriteCountAll;
	private Long favoriteCount;

	private Long messagesSentByMeCountAll;
	private Long messagesSentByMeCount;

	private Long msgSentWithTextCountAll;
	private Long msgSentWithTextCount;

	private Long msgSentWithLinkCountAll;
	private Long msgSentWithLinkCount;

	private Long msgSentWithPhotoCountAll;
	private Long msgSentWithPhotoCount;

	private Long sinceId;

	public static final String FOLLOWERS_COUNT = "followersCount";
	public static final String FOLLOWERS_COUNT_ALL = "followersCountAll";
	public static final String FRIENDS_COUNT = "friendsCount";

	public static final String RETWEETS_OF_ME = "retweetsOfMeCount";
	public static final String MENTIONS = "mentionsCount";
	public static final String MESSAGES_SENT_BY_ME_COUNT = "messagesSentByMeCount";
	public static final String REPLIES_BY_ME = "repliesByMeCount";
	public static final String REPLIES_OF_ME = "repliesOfMeCount";

	public static final String FAVORITE_COUNT = "favoriteCount";
	public static final String FAVORITE_COUNT_ALL = "favoriteCountAll";
	
	public static final String RETWEETS_OF_ME_COUNT_ALL = "retweetsOfMeCountAll";
	public static final String REPLIES_OF_ME_COUNT_ALL = "repliesOfMeCountAll";

	public static final String MSG_SENT_WITH_TEXT_COUNT = "msgSentWithTextCount";
	public static final String MSG_SENT_WITH_PHOTO_COUNT = "msgSentWithPhotoCount";
	public static final String MSG_SENT_WITH_LINK_COUNT = "msgSentWithLinkCount";

	public static final String FOLLOWERS_FEMALE_COUNT_ALL = "followersFemaleCountAll";
	public static final String FOLLOWERS_MALE_COUNT_ALL = "followersMaleCountAll";
	
	public static final String MEDIA_ENTITY_PHOTO = "photo";

	private List<String> photoVideoDomains;

	public ReportTwitter(Profile profile, Long reportDateStamp) {
		
		this.profile = profile;
		this.reportDateStamp = reportDateStamp;

		twitterIntegration = new TwitterIntegration();
		
		profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");

		twitterStatusRemote = (MaintenanceTwitterStatusRemote) RecoverMaintenance.recoverMaintenance("TwitterStatus");
		twitterReportRemote = (MaintenanceTwitterReportRemote) RecoverMaintenance.recoverMaintenance("TwitterReport");
		twitterStatusReportRemote = (MaintenanceTwitterStatusReportRemote) RecoverMaintenance.recoverMaintenance("TwitterStatusReport");
		reportControlRemote = (MaintenanceReportControlRemote) RecoverMaintenance.recoverMaintenance("ReportControl");
		twitterFollowerReportRemote = (MaintenanceTwitterFollowerReportRemote) RecoverMaintenance.recoverMaintenance("TwitterFollowerReport");

		photoVideoDomains = new ArrayList<String>();
		photoVideoDomains.add("pic.twitter.com");
		photoVideoDomains.add("youtube.com");
		photoVideoDomains.add("youtu.be");
		photoVideoDomains.add("vimeo.com");
		photoVideoDomains.add("instagram.com");
		photoVideoDomains.add("yfrog.com");
		photoVideoDomains.add("vine.co");
	}
	
	@Override
	public void run() {
		retrieveInsights();
	}

	public void retrieveInsights() {

		if(reportControlRemote.getEntityByNetwork(profile.getNetworkId(), Profile.TWITTER, reportDateStamp) == null) {

			ReportControl reportControl = new ReportControl();
			reportControl.setNetworkId(profile.getNetworkId());
			reportControl.setNetworkType(profile.getType());
			reportControl.setDate(reportDateStamp);
			reportControl.setStartDate(new Date().getTime() / 1000L);

			try {
				
				reportControl = reportControlRemote.save(reportControl);
				
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
			try {
				
				twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());

				User user = twitter.showUser(Long.valueOf(profile.getNetworkId()));
				
				profile.setScreenName(user.getScreenName());
				if(user.getProfileImageURLHttps().contains("normal")) {
    				profile.setAvatar(user.getProfileImageURLHttps().replace("_normal", ""));
				} else {
    				profile.setAvatar(user.getProfileImageURLHttps());	
				}
				profile.setActive(Profile.ACTIVE);

				try {

					profileRemote.save(profile);

				} catch (Exception e) {
					e.printStackTrace();
				}

				countOfFollowers(profile, user);
				countOfFriends(profile, user);
				countOfStatuses(profile, user);

				countOfActionsByMe(profile);
				countOfMentions(profile);
				countOfRetweetsOfMe(profile);

				saveStatusInsights(profile.getNetworkId());

			} catch (TwitterException twitterException) {
				twitterException.printStackTrace();
			}

			try {
				
				reportControl = reportControlRemote.getById(reportControl.getIdReportsControl());

				reportControl.setFinishDate(new Date().getTime() / 1000L);
				reportControlRemote.save(reportControl);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
	}

	private void countOfActionsByMe(Profile profile) {

		try {

			twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), "messagesSentByMeCountAll");

			if(twitterReport == null) {

				sinceId = 0L;

				messagesSentByMeCountAll = 0L;
				repliesByMeCountAll = 0L;
				favoriteCountAll = 0L;
				msgSentWithTextCountAll = 0L;
				msgSentWithPhotoCountAll = 0L;
				msgSentWithLinkCountAll = 0L;

				retrieveCountOfAllActionsByMe();

				// MessagesSentByMe
				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setValue(messagesSentByMeCountAll);
				twitterReport.setProperty("messagesSentByMeCountAll");
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("messagesSentByMeCount");
				twitterReport.setValue(0L);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				// RepliesByMe
				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setValue(repliesByMeCountAll);
				twitterReport.setProperty("repliesByMeCountAll");
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("repliesByMeCount");
				twitterReport.setValue(0L);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				// FavoriteCount
				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setValue(favoriteCountAll);
				twitterReport.setProperty(FAVORITE_COUNT_ALL);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty(FAVORITE_COUNT);
				twitterReport.setValue(0L);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				// MessagesSentByType - Text
				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setValue(msgSentWithTextCountAll);
				twitterReport.setProperty("msgSentWithTextCountAll");
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("msgSentWithTextCount");
				twitterReport.setValue(0L);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				// MessagesSentByType - Photo
				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setValue(msgSentWithPhotoCountAll);
				twitterReport.setProperty("msgSentWithPhotoCountAll");
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("msgSentWithPhotoCount");
				twitterReport.setValue(0L);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				// MessagesSentByType - Link
				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setValue(msgSentWithLinkCountAll);
				twitterReport.setProperty("msgSentWithLinkCountAll");
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("msgSentWithLinkCount");
				twitterReport.setValue(0L);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);
			} else {
				sinceId = twitterReport.getSinceId();

				messagesSentByMeCount = 0L;
				repliesByMeCount = 0L;
				favoriteCount = 0L;
				msgSentWithTextCount = 0L;
				msgSentWithPhotoCount = 0L;
				msgSentWithLinkCount = 0L;

				if(!sinceId.equals(0L)) {
					retrieveCountOfActionsByMeBySinceId();
				} else {
					messagesSentByMeCountAll = 0L;
					repliesByMeCountAll = 0L;
					favoriteCountAll = 0L;
					msgSentWithTextCountAll = 0L;
					msgSentWithPhotoCountAll = 0L;
					msgSentWithLinkCountAll = 0L;
					retrieveCountOfAllActionsByMe();
					messagesSentByMeCount = messagesSentByMeCountAll;
					repliesByMeCount = repliesByMeCountAll;
					msgSentWithTextCount = msgSentWithTextCountAll;
					msgSentWithPhotoCount = msgSentWithPhotoCountAll;
					msgSentWithLinkCount = msgSentWithLinkCountAll;
				}

				// MessagesSentByMe				
				twitterReport.setValue(twitterReport.getValue() + messagesSentByMeCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("messagesSentByMeCount");
				twitterReport.setValue(messagesSentByMeCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				// RepliesByMe
				twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), "repliesByMeCountAll");
				twitterReport.setValue(twitterReport.getValue() + repliesByMeCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("repliesByMeCount");
				twitterReport.setValue(repliesByMeCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				// FavoriteCount
				twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), FAVORITE_COUNT_ALL);
				twitterReport.setValue(twitterReport.getValue() + favoriteCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty(FAVORITE_COUNT);
				twitterReport.setValue(favoriteCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				// MessagesSentByMeByType
				twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), "msgSentWithTextCountAll");
				twitterReport.setValue(twitterReport.getValue() + msgSentWithTextCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), "msgSentWithPhotoCountAll");
				twitterReport.setValue(twitterReport.getValue() + msgSentWithPhotoCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), "msgSentWithLinkCountAll");
				twitterReport.setValue(twitterReport.getValue() + msgSentWithLinkCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("msgSentWithTextCount");
				twitterReport.setValue(msgSentWithTextCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("msgSentWithPhotoCount");
				twitterReport.setValue(msgSentWithPhotoCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("msgSentWithLinkCount");
				twitterReport.setValue(msgSentWithLinkCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void countOfRetweetsOfMe(Profile profile) {

		try {
			twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), "retweetsOfMeCountAll");
			if(twitterReport == null) {
				sinceId = 0L;
				retweetsOfMeCountAll = 0L;
				retrieveCountOfAllRetweetsOfMe(profile);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setValue(retweetsOfMeCountAll);
				twitterReport.setProperty("retweetsOfMeCountAll");
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("retweetsOfMeCount");
				twitterReport.setValue(0L);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);
			} else {
				sinceId = twitterReport.getSinceId();
				retweetsOfMeCount = 0L;

				if(!sinceId.equals(0L)) {
					retrieveCountOfRetweetsOfMeBySinceId(profile);
				} else {
					retweetsOfMeCountAll = 0L;
					retrieveCountOfAllRetweetsOfMe(profile);
					retweetsOfMeCount = retweetsOfMeCountAll;
				}

				twitterReport.setValue(twitterReport.getValue() + retweetsOfMeCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("retweetsOfMeCount");
				twitterReport.setValue(retweetsOfMeCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void countOfMentions(Profile profile) { 

		try {
			twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), "mentionsCountAll");
			if(twitterReport == null) {

				sinceId = 0L;
				mentionsCountAll = 0L;
				repliesOfMeCountAll = 0L;
				twitterStatusList = twitterStatusRemote.getTwitterStatusByProfile(profile.getNetworkId(), 0, 100);
				createTwitterStatusReport("repliesOfMeCountAll");
				retrieveCountOfAllMentions(profile);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setValue(mentionsCountAll);
				twitterReport.setProperty("mentionsCountAll");
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("mentionsCount");
				twitterReport.setValue(0L);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("repliesOfMeCountAll");
				twitterReport.setValue(repliesOfMeCountAll);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("repliesOfMeCount");
				twitterReport.setValue(0L);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				for(TwitterStatusReport twitterStatusReport : twitterStatusReportList) {
					twitterStatusReport.setSinceId(sinceId);
					twitterStatusReportRemote.save(twitterStatusReport);
				}

			} else {

				sinceId = twitterReport.getSinceId();
				mentionsCount = 0L;
				repliesOfMeCount = 0L;
				twitterStatusList = twitterStatusRemote.getTwitterStatusByProfile(profile.getNetworkId(), 0, 100);
				createTwitterStatusReportVerify("repliesOfMeCountAll");

				if(!sinceId.equals(0L)) {
					retrieveCountOfMentionsBySinceId(profile);
				} else {
					mentionsCountAll = 0L;
					repliesOfMeCountAll = 0L;
					retrieveCountOfAllMentions(profile);
					mentionsCount = mentionsCountAll;
					repliesOfMeCount = repliesOfMeCountAll;
				}

				twitterReport.setValue(twitterReport.getValue() + mentionsCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("mentionsCount");
				twitterReport.setValue(mentionsCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), "repliesOfMeCountAll");
				twitterReport.setValue(twitterReport.getValue() + repliesOfMeCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("repliesOfMeCount");
				twitterReport.setValue(repliesOfMeCount);
				twitterReport.setDate(reportDateStamp);
				twitterReport.setSinceId(sinceId);

				twitterReportRemote.save(twitterReport);

				for(TwitterStatusReport twitterStatusReport : twitterStatusReportList) {
					twitterStatusReport.setSinceId(sinceId);
					twitterStatusReportRemote.save(twitterStatusReport);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void countOfFollowers(Profile profile, User user) {

		try {

			twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), "followersCountAll");
			Long followersCountAllPrevious;

			if(twitterReport == null) {

				twitterReport = new TwitterReport();
				followersCountAllPrevious = 0L;
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setValue(Long.valueOf(user.getFollowersCount()));
				twitterReport.setProperty("followersCountAll");
				twitterReport.setDate(reportDateStamp);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("followersCount");
				twitterReport.setValue(followersCountAllPrevious);
				twitterReport.setDate(reportDateStamp);

				twitterReportRemote.save(twitterReport);

			} else {

				followersCountAllPrevious = twitterReport.getValue();
				twitterReport.setValue(Long.valueOf(user.getFollowersCount()));
				twitterReport.setDate(reportDateStamp);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("followersCount");
				twitterReport.setValue(user.getFollowersCount() - followersCountAllPrevious);
				twitterReport.setDate(reportDateStamp);

				twitterReportRemote.save(twitterReport);

			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void countOfFriends(Profile profile, User user) {

		try {
			twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), "friendsCountAll");
			Long friendsCountAllPrevious;
			if(twitterReport == null) {
				twitterReport = new TwitterReport();
				friendsCountAllPrevious = 0L;
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setValue(Long.valueOf(user.getFriendsCount()));
				twitterReport.setProperty("friendsCountAll");
				twitterReport.setDate(reportDateStamp);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("friendsCount");
				twitterReport.setValue(friendsCountAllPrevious);
				twitterReport.setDate(reportDateStamp);

				twitterReportRemote.save(twitterReport);
			} else {
				friendsCountAllPrevious = twitterReport.getValue();
				twitterReport.setValue(Long.valueOf(user.getFriendsCount()));
				twitterReport.setDate(reportDateStamp);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("friendsCount");
				twitterReport.setValue(user.getFriendsCount() - friendsCountAllPrevious);
				twitterReport.setDate(reportDateStamp);

				twitterReportRemote.save(twitterReport);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void countOfStatuses(Profile profile, User user) {

		try {
			twitterReport = twitterReportRemote.getEntityByProfileProperty(profile.getNetworkId(), "statusesCountAll");
			Long statusesCountAllPrevious;
			if(twitterReport == null) {
				twitterReport = new TwitterReport();
				statusesCountAllPrevious = 0L;
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setValue(Long.valueOf(user.getStatusesCount()));
				twitterReport.setProperty("statusesCountAll");
				twitterReport.setDate(reportDateStamp);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("statusesCount");
				twitterReport.setValue(statusesCountAllPrevious);
				twitterReport.setDate(reportDateStamp);

				twitterReportRemote.save(twitterReport);
			} else {
				statusesCountAllPrevious = twitterReport.getValue();
				twitterReport.setValue(Long.valueOf(user.getStatusesCount()));
				twitterReport.setDate(reportDateStamp);

				twitterReportRemote.save(twitterReport);

				twitterReport = new TwitterReport();
				twitterReport.setIdProfile(profile.getNetworkId());
				twitterReport.setProperty("statusesCount");
				twitterReport.setDate(reportDateStamp);
				twitterReport.setValue(user.getStatusesCount() - statusesCountAllPrevious);

				twitterReportRemote.save(twitterReport);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retrieveCountOfAllActionsByMe() {

		try {

			Paging paging = new Paging();
			paging.setCount(200);

			ResponseList<Status> userStatus = twitter.getUserTimeline(paging);

			if(!userStatus.isEmpty()) {

				sinceId = userStatus.get(0).getId();
				boolean alreadyCountPhoto;
				boolean alreadyCountLink;

				for(Status status : userStatus) {

					// Favorite count.
					favoriteCountAll = favoriteCountAll + Long.valueOf(status.getFavoriteCount());

					// Messages sent by me.
					if(!status.isRetweetedByMe()) {
						messagesSentByMeCountAll = messagesSentByMeCountAll + 1;
					}

					// Replies sent by me
					if(status.getInReplyToUserId() != -1) {
						repliesByMeCountAll = repliesByMeCountAll + 1;
					}

					// Type of messages sent by me.
					if(!status.isRetweetedByMe() && status.getInReplyToUserId() == -1) {
						if(status.getMediaEntities().length > 0) {
							
							alreadyCountPhoto = false;
							
							for (MediaEntity mediaEntity : status.getMediaEntities()) {
								if(mediaEntity.getType().equals(MEDIA_ENTITY_PHOTO)) {
									msgSentWithPhotoCountAll = msgSentWithPhotoCountAll + 1;
									alreadyCountPhoto = true;
									break;
								} else {
									//----- System.out.println("Status ID ..: " + status.getId());
									//----- System.out.println("Other Media Entity ..: " + mediaEntity.getType());
								}
							}
							
						} else if(status.getURLEntities().length > 0) {

							alreadyCountPhoto = false;
							alreadyCountLink = false;

							for(URLEntity url : status.getURLEntities()) {
								if(tweetHasPhotoVideo(url.getDisplayURL()) && !alreadyCountPhoto) {
									msgSentWithPhotoCountAll = msgSentWithPhotoCountAll + 1;
									alreadyCountPhoto = true;
								} else if(!alreadyCountLink) {
									msgSentWithLinkCountAll = msgSentWithLinkCountAll + 1;
									alreadyCountLink = true;
								}
							}
						} else {
							msgSentWithTextCountAll = msgSentWithTextCountAll + 1;
						}
					}

					// Status insights of my status.
					if(!status.isRetweetedByMe() && status.getInReplyToUserId() == -1) {

						twitterStatus = new TwitterStatus();
						twitterStatus.setIdStatus(String.valueOf(status.getId()));
						twitterStatus.setIdProfile(String.valueOf(status.getUser().getId()));
						twitterStatus.setCreatedAt(status.getCreatedAt().getTime() / 1000L);
						twitterStatus.setText(status.getText());

						twitterStatusRemote.save(twitterStatus);
					}
				}

				retrieveCountOfAllActionsByMeMaxId(userStatus.get(userStatus.size()-1).getId());

			} else {
				return;
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retrieveCountOfAllActionsByMeMaxId(Long maxId) { 

		try {

			Paging paging = new Paging();
			paging.setCount(200);
			paging.setMaxId(maxId);

			ResponseList<Status> userStatus = twitter.getUserTimeline(paging);

			if(userStatus.size() > 1) {

				userStatus.remove(0);
				boolean alreadyCountPhoto;
				boolean alreadyCountLink;

				for(Status status : userStatus) {

					// Favorite count.
					favoriteCountAll = favoriteCountAll + Long.valueOf(status.getFavoriteCount());

					if(!status.isRetweetedByMe()) {
						messagesSentByMeCountAll = messagesSentByMeCountAll + 1;
					}

					if(status.getInReplyToUserId() != -1) {
						repliesByMeCountAll = repliesByMeCountAll + 1;
					}

					// Type of messages sent by me.
					if(!status.isRetweetedByMe() && status.getInReplyToUserId() == -1) {
						if(status.getMediaEntities().length > 0) {
							
							alreadyCountPhoto = false;
							
							for (MediaEntity mediaEntity : status.getMediaEntities()) {
								if(mediaEntity.getType().equals(MEDIA_ENTITY_PHOTO)) {
									msgSentWithPhotoCountAll = msgSentWithPhotoCountAll + 1;
									alreadyCountPhoto = true;
									break;
								} else {
									//----- System.out.println("Status ID ..: " + status.getId());
									//----- System.out.println("Other Media Entity ..: " + mediaEntity.getType());
								}
							}
							
						} else if(status.getURLEntities().length > 0) {

							alreadyCountPhoto = false;
							alreadyCountLink = false;

							for(URLEntity url : status.getURLEntities()) {
								if(tweetHasPhotoVideo(url.getDisplayURL()) && !alreadyCountPhoto) {
									msgSentWithPhotoCountAll = msgSentWithPhotoCountAll + 1;
									alreadyCountPhoto = true;
								} else if(!alreadyCountLink) {
									msgSentWithLinkCountAll = msgSentWithLinkCountAll + 1;
									alreadyCountLink = true;
								}
							}
						} else {
							msgSentWithTextCountAll = msgSentWithTextCountAll + 1;
						}
					}

					if(!status.isRetweetedByMe() && status.getInReplyToUserId() == -1) {

						twitterStatus = new TwitterStatus();
						twitterStatus.setIdStatus(String.valueOf(status.getId()));
						twitterStatus.setIdProfile(String.valueOf(status.getUser().getId()));
						twitterStatus.setCreatedAt(status.getCreatedAt().getTime() / 1000L);
						twitterStatus.setText(status.getText());

						twitterStatusRemote.save(twitterStatus);
					}
				}

				retrieveCountOfAllActionsByMeMaxId(userStatus.get(userStatus.size()-1).getId());
			} else {
				return;
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retrieveCountOfActionsByMeBySinceId() { 

		try {

			Paging paging = new Paging();
			paging.setCount(200);
			paging.setSinceId(sinceId);

			ResponseList<Status> userStatus = twitter.getUserTimeline(paging);

			if(!userStatus.isEmpty()) {
				
				sinceId = userStatus.get(0).getId();
				boolean alreadyCountPhoto;
				boolean alreadyCountLink;
				
				for(Status status : userStatus) {

					favoriteCount = favoriteCount + status.getFavoriteCount();

					if(!status.isRetweetedByMe()) {
						messagesSentByMeCount = messagesSentByMeCount + 1;
					}
					if(status.getInReplyToUserId() != -1) {
						repliesByMeCount = repliesByMeCount + 1;
					}

					// Type of messages sent by me.
					if(!status.isRetweetedByMe() && status.getInReplyToUserId() == -1) {
						if(status.getMediaEntities().length > 0) {
							
							alreadyCountPhoto = false;
							
							for (MediaEntity mediaEntity : status.getMediaEntities()) {
								if(mediaEntity.getType().equals(MEDIA_ENTITY_PHOTO)) {
									msgSentWithPhotoCount = msgSentWithPhotoCount + 1;
									alreadyCountPhoto = true;
									break;
								} else {
									//----- System.out.println("Status ID ..: " + status.getId());
									//----- System.out.println("Other Media Entity ..: " + mediaEntity.getType());
								}
							}
							
						} else if(status.getURLEntities().length > 0) {

							alreadyCountPhoto = false;
							alreadyCountLink = false;

							for(URLEntity url : status.getURLEntities()) {
								if(tweetHasPhotoVideo(url.getDisplayURL()) && !alreadyCountPhoto) {
									msgSentWithPhotoCount = msgSentWithPhotoCount + 1;
									alreadyCountPhoto = true;
								} else if(!alreadyCountLink) {
									msgSentWithLinkCount = msgSentWithLinkCount + 1;
									alreadyCountLink = true;
								}
							}
						} else {
							msgSentWithTextCount= msgSentWithTextCount + 1;
						}
					}

					if(!status.isRetweetedByMe() && status.getInReplyToUserId() == -1) {

						twitterStatus = new TwitterStatus();
						twitterStatus.setIdStatus(String.valueOf(status.getId()));
						twitterStatus.setIdProfile(String.valueOf(status.getUser().getId()));
						twitterStatus.setCreatedAt(status.getCreatedAt().getTime() / 1000L);
						twitterStatus.setText(status.getText());

						twitterStatusRemote.save(twitterStatus);
					}
				}

				retrieveCountOfActionsByMeBySinceId();

			} else {
				return;
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void retrieveCountOfMentionsBySinceId(Profile profile) {

		try {

			Paging paging = new Paging();
			paging.setCount(200);
			paging.setSinceId(sinceId);

			ResponseList<Status> mentions = twitter.getMentionsTimeline(paging);

			if(!mentions.isEmpty()) {

				sinceId = mentions.get(0).getId();
				mentionsCount = mentionsCount + mentions.size();

				if(!twitterStatusList.isEmpty()) {

					for(Status status : mentions) {

						if(status.getInReplyToStatusId() != -1) {
							for(TwitterStatus twitterStatus : twitterStatusList) {

								if (String.valueOf(status.getInReplyToStatusId()).equals(twitterStatus.getIdStatus())) {
									repliesOfMeCount = repliesOfMeCount + 1;
									for(TwitterStatusReport twitterStatusreport : twitterStatusReportList) {
										if(twitterStatusreport.getIdStatus().equals(twitterStatus.getIdStatus())) {
											twitterStatusreport.setValue(twitterStatusreport.getValue() + 1);
											break;
										}
									}

									saveTwitterFollowerReport(Long.valueOf(profile.getNetworkId()), status.getUser().getId(), ReportTwitter.REPLIES_OF_ME);

									break;
								}

							}
						}

						saveTwitterFollowerReport(Long.valueOf(profile.getNetworkId()), status.getUser().getId(), ReportTwitter.MENTIONS);
					}
				}

				retrieveCountOfMentionsBySinceId(profile);

			} else {
				return;
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}		
	}

	private void retrieveCountOfAllMentions(Profile profile) {

		try {

			Paging paging = new Paging();
			paging.setCount(200);

			ResponseList<Status> mentions = twitter.getMentionsTimeline(paging);

			if(!mentions.isEmpty()) {

				sinceId = mentions.get(0).getId();
				mentionsCountAll = mentionsCountAll + mentions.size();

				if(!twitterStatusList.isEmpty()) {

					for(Status status : mentions) {

						if(status.getInReplyToStatusId() != -1) {
							for(TwitterStatus twitterStatus : twitterStatusList) {
								if (String.valueOf(status.getInReplyToStatusId()).equals(twitterStatus.getIdStatus())) {

									repliesOfMeCountAll = repliesOfMeCountAll + 1;
									for(TwitterStatusReport twitterStatusreport : twitterStatusReportList) {
										if(twitterStatusreport.getIdStatus().equals(twitterStatus.getIdStatus())) {
											twitterStatusreport.setValue(twitterStatusreport.getValue() + 1);
											break;
										}
									}

									saveTwitterFollowerReport(Long.valueOf(profile.getNetworkId()), status.getUser().getId(), ReportTwitter.REPLIES_OF_ME);

									break;
								}
							}
						}

						saveTwitterFollowerReport(Long.valueOf(profile.getNetworkId()), status.getUser().getId(), ReportTwitter.MENTIONS);
					}

				}

				retrieveCountOfAllMentionsByMaxId(mentions.get(mentions.size()-1).getId(), profile);

			} else {
				return;
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private void retrieveCountOfAllMentionsByMaxId(Long maxId, Profile profile) {

		try {

			Paging paging = new Paging();
			paging.setCount(200);
			paging.setMaxId(maxId);

			ResponseList<Status> mentions = twitter.getMentionsTimeline(paging);

			if(mentions.size() > 1) {

				mentions.remove(0);
				mentionsCountAll = mentionsCountAll + mentions.size();

				if(!twitterStatusList.isEmpty()) {

					for(Status status : mentions) {

						if(status.getInReplyToStatusId() != -1) {
							for(TwitterStatus twitterStatus : twitterStatusList) {

								if (String.valueOf(status.getInReplyToStatusId()).equals(twitterStatus.getIdStatus())) {
									repliesOfMeCountAll = repliesOfMeCountAll + 1;
									for(TwitterStatusReport twitterStatusreport : twitterStatusReportList) {
										if(twitterStatusreport.getIdStatus().equals(twitterStatus.getIdStatus())) {
											twitterStatusreport.setValue(twitterStatusreport.getValue() + 1);
											break;
										}
									}

									saveTwitterFollowerReport(Long.valueOf(profile.getNetworkId()), status.getUser().getId(), ReportTwitter.REPLIES_OF_ME);

									break;
								}

							}
						}

						saveTwitterFollowerReport(Long.valueOf(profile.getNetworkId()), status.getUser().getId(), ReportTwitter.MENTIONS);
					}
				}
				retrieveCountOfAllMentionsByMaxId(mentions.get(mentions.size()-1).getId(), profile);
			} else {
				return;
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private void retrieveCountOfRetweetsOfMeBySinceId(Profile profile) {

		try {

			Paging paging = new Paging();
			paging.setCount(100);
			paging.setSinceId(sinceId);

			ResponseList<Status> retweetsOfMe = twitter.getRetweetsOfMe(paging);

			if(!retweetsOfMe.isEmpty()) {

				this.sinceId = retweetsOfMe.get(0).getId();

				for(Status retweetOfMe : retweetsOfMe) {
					retweetsOfMeCount = retweetsOfMeCount + retweetOfMe.getRetweetCount();
				}

				retrieveCountOfRetweetsOfMeBySinceId(profile);

			} else {
				return;
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private void retrieveCountOfAllRetweetsOfMe(Profile profile) {

		try {

			Paging paging = new Paging();
			paging.setCount(100);

			ResponseList<Status> retweetsOfMe = twitter.getRetweetsOfMe(paging);

			if(!retweetsOfMe.isEmpty()) {

				sinceId = retweetsOfMe.get(0).getId();

				for(Status retweetOfMe : retweetsOfMe) {
					retweetsOfMeCountAll = retweetsOfMeCountAll + retweetOfMe.getRetweetCount();
				}

				retrieveCountOfAllRetweetsOfMeByMaxId(retweetsOfMe.get(retweetsOfMe.size()-1).getId(), profile);

			} else {
				return;
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private void retrieveCountOfAllRetweetsOfMeByMaxId(Long maxId, Profile profile) {

		try {

			Paging paging = new Paging();
			paging.setCount(100);
			paging.setMaxId(maxId);

			ResponseList<Status> retweetsOfMe = twitter.getRetweetsOfMe(paging);

			if(retweetsOfMe.size() > 1) {

				retweetsOfMe.remove(0);

				for(Status retweetOfMe : retweetsOfMe) {
					retweetsOfMeCountAll = retweetsOfMeCountAll + retweetOfMe.getRetweetCount();
				}

				retrieveCountOfAllRetweetsOfMeByMaxId(retweetsOfMe.get(retweetsOfMe.size()-1).getId(), profile);

			} else {
				return;
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private void createTwitterStatusReport(String property) {

		twitterStatusReportList = new ArrayList<TwitterStatusReport>();

		for(TwitterStatus twitterStatus : twitterStatusList) {

			TwitterStatusReport twitStatusRep = new TwitterStatusReport();
			twitStatusRep.setIdProfile(twitterStatus.getIdProfile());
			twitStatusRep.setIdStatus(twitterStatus.getIdStatus());
			twitStatusRep.setProperty(property);
			twitStatusRep.setValue(0L);
			twitStatusRep.setDate(reportDateStamp);

			twitterStatusReportList.add(twitStatusRep);
		}
	}

	private void createTwitterStatusReportVerify(String property) {

		twitterStatusReportList = new ArrayList<TwitterStatusReport>();
		TwitterStatusReport twitStatusRep;

		for(TwitterStatus twitterStatus : twitterStatusList) {

			twitStatusRep = twitterStatusReportRemote.getEntityByProperty(twitterStatus.getIdProfile(), 
					twitterStatus.getIdStatus(), property);

			if(twitStatusRep == null) {

				twitStatusRep = new TwitterStatusReport();
				twitStatusRep.setIdProfile(twitterStatus.getIdProfile());
				twitStatusRep.setIdStatus(twitterStatus.getIdStatus());
				twitStatusRep.setProperty(property);
				twitStatusRep.setValue(0L);
			}
			twitStatusRep.setDate(reportDateStamp);

			twitterStatusReportList.add(twitStatusRep);
		}
	}

	private boolean tweetHasPhotoVideo(String displayUrl) {

		boolean hasPhotoVideo = false;

		for (String photoVideoDomain : photoVideoDomains) {

			if(displayUrl.startsWith(photoVideoDomain)) {
				hasPhotoVideo = true;
				break;
			}

		}

		return hasPhotoVideo;
	}

	private void saveTwitterFollowerReport(Long idProfile, Long followerId, String property) {

		TwitterFollowerReport twitterFollowerReport = twitterFollowerReportRemote.getEntity(idProfile, followerId, property, reportDateStamp);

		if(twitterFollowerReport == null) {

			twitterFollowerReport = new TwitterFollowerReport();
			twitterFollowerReport.setIdProfile(idProfile);
			twitterFollowerReport.setFollowerId(followerId);
			twitterFollowerReport.setDate(reportDateStamp);
			twitterFollowerReport.setValue(1L);
			twitterFollowerReport.setProperty(property);

		} else {

			twitterFollowerReport.setValue(twitterFollowerReport.getValue() + 1L);

		}

		try {

			twitterFollowerReportRemote.save(twitterFollowerReport);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void saveStatusInsights(String networkid) {
		
		Integer offset = 0;
		Integer limit = 100;
		Integer requestsNumber = 0;
		
		try {
		
			RateLimitStatus rateLimitStatusResponse = 
					twitter.getRateLimitStatus(ReportTwitterFollowers.FAMILY_RESOURCE_STATUSES).get(
							ReportTwitterFollowers.RESOURCE_STATUSES_LOOKUP);
			
			requestsNumber = rateLimitStatusResponse.getRemaining();
			
			while(requestsNumber > 0) {
				
				long[] twitterStatusIds = twitterStatusRemote.getTwitterStatusIds(networkid, offset, limit);
				
				if(twitterStatusIds.length > 0) {
					
					try {
						
						ResponseList<Status> statuses = twitter.lookup(twitterStatusIds);
						
						for(Status status : statuses) {
							
							// Save/Update number of retweets.
							TwitterStatusReport numberOfRetweets;
							
							try {
								
								numberOfRetweets = twitterStatusReportRemote.getEntityByProperty(networkid, String.valueOf(status.getId()), ReportTwitter.RETWEETS_OF_ME_COUNT_ALL);
								
								if(numberOfRetweets != null) {
									
									numberOfRetweets.setValue(Long.valueOf(status.getRetweetCount()));
									numberOfRetweets.setDate(reportDateStamp);
									
								} else {
									
									numberOfRetweets = new TwitterStatusReport();
									numberOfRetweets.setIdProfile(String.valueOf(status.getUser().getId()));
									numberOfRetweets.setIdStatus(String.valueOf(status.getId()));
									numberOfRetweets.setProperty(ReportTwitter.RETWEETS_OF_ME_COUNT_ALL);
									numberOfRetweets.setValue(Long.valueOf(status.getRetweetCount()));
									numberOfRetweets.setDate(reportDateStamp);
								}
								
								twitterStatusReportRemote.save(numberOfRetweets);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
							
							// Save/Update number of favorites.
							TwitterStatusReport numberOfFavorites;
							
							try {
								
								numberOfFavorites = twitterStatusReportRemote.getEntityByProperty(networkid, String.valueOf(status.getId()), ReportTwitter.FAVORITE_COUNT_ALL);
								
								if(numberOfFavorites != null) {
									
									numberOfFavorites.setValue(Long.valueOf(status.getFavoriteCount()));
									numberOfFavorites.setDate(reportDateStamp);
									
								} else {
									
									numberOfFavorites = new TwitterStatusReport();
									numberOfFavorites.setIdProfile(String.valueOf(status.getUser().getId()));
									numberOfFavorites.setIdStatus(String.valueOf(status.getId()));
									numberOfFavorites.setProperty(ReportTwitter.FAVORITE_COUNT_ALL);
									numberOfFavorites.setValue(Long.valueOf(status.getFavoriteCount()));
									numberOfFavorites.setDate(reportDateStamp);
								}
								
								twitterStatusReportRemote.save(numberOfFavorites);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						
						requestsNumber = statuses.getRateLimitStatus().getRemaining();
						
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					offset += limit;
					
				} else {
					break;
				}
			}
			
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}