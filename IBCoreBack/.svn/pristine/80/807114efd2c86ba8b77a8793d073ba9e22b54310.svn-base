package br.com.opsocial.server.utils.reports;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.opsocial.ejb.das.MaintenanceInstagramPostTagRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramReportPostRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramReportRemote;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceReportControlRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.InstagramPostTagId;
import br.com.opsocial.ejb.entity.instagram.InstagramPostTag;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramReportPost;
import br.com.opsocial.ejb.entity.report.ReportControl;
import br.com.opsocial.ejb.entity.report.ReportGenerate;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.instagram.InstagramAPI;
import br.com.opsocial.server.utils.instagram.InstagramBusinessBO;
import br.com.opsocial.server.utils.instagram.entities.Media;
import br.com.opsocial.server.utils.instagram.entities.Paging;
import br.com.opsocial.server.utils.instagram.entities.User;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import br.com.opsocial.server.utils.tasks.ValidateTokensController;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.auth.AccessToken;

public class ReportInstagram {
	
	private FacebookIntegration facebookIntegration;
	private Facebook facebook;

	private Profile profile;
	
	private MaintenanceProfileRemote profileRemote;
	private MaintenanceReportControlRemote reportControlRemote;

	private MaintenanceInstagramReportPostRemote instagramReportPostRemote;
	private MaintenanceInstagramPostTagRemote instagramPostTagRemote;
	private MaintenanceInstagramReportRemote instagramReportRemote;
	
	public static final String GROUP_MAIN = "main";

	public static final Character IS_A_FOLLOWER = 'T';
	public static final Character NOT_A_FOLLOWER = 'F';

	public static final String PROFILE_FOLLOWERS = "profile_followers";
	public static final String PROFILE_FOLLOWERS_ADDS = "profile_followers_adds";
	public static final String PROFILE_FOLLOWERS_REMOVES = "profile_followers_removes";
	
	public static final String FOLLOWER_COUNT = "follower_count";

	public static final Integer UPDATE_POSTS_PERIOD = 30;
	
	public static final Long MAX_ALREADY_FOLLOWERS = 50L;
	public static final Long MAX_FOLLOWERS = 5000L;
	
	public static final String POST_TYPE_IMAGE = "IMAGE";
	public static final String POST_TYPE_VIDEO = "VIDEO";
	public static final String POST_TYPE_CAROUSEL = "CAROUSEL";
	
	public static final String IMPRESSIONS = "impressions";
	public static final String WEBSITE_CLICKS = "website_clicks";
	public static final String REACH = "reach";
	public static final String PROFILE_VIEWS = "profile_views";
	
	public static final String AUDIENCE_AGE_MEN = "audience_age_men";
	public static final String AUDIENCE_AGE_WOMEN = "audience_age_women";

	public static final String AUDIENCE_COUNTRY = "audience_country";
	public static final String AUDIENCE_CITY = "audience_city";
	
	private boolean isFirstReportGeneration;
	
	private ReportGenerate reportGenerate;

	// 1 hora e 1 minuto (acrescentado 1 minuto de "delay") 
	public static final Long REQUEST_WINDOW_TIME = 3660000L;

	public ReportInstagram(Profile profile, Boolean isFirstReportGeneration) {

		facebookIntegration = new FacebookIntegration();

		this.profile = profile;
		this.isFirstReportGeneration = isFirstReportGeneration;

		profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		reportControlRemote = (MaintenanceReportControlRemote) RecoverMaintenance.recoverMaintenance("ReportControl");

		instagramReportPostRemote = (MaintenanceInstagramReportPostRemote) RecoverMaintenance.recoverMaintenance("InstagramReportPost");
		instagramPostTagRemote = (MaintenanceInstagramPostTagRemote) RecoverMaintenance.recoverMaintenance("InstagramPostTag");
		instagramReportRemote = (MaintenanceInstagramReportRemote) RecoverMaintenance.recoverMaintenance("InstagramReport");
	}
	
	public void retrieveAllInsightsForDay(ZonedDateTime day) {
		
		// Updates user info.
		User user = updateProfileInfo();
		
		// Retrieve insights for current day.
		retrieveInsightsForDay(day, user);
		
		// Retrieve last posts.
		InstagramReportPost lastInstagramReportPost = instagramReportPostRemote.getLastPost(profile.getNetworkId());
		saveInstagramReportsPosts(user, lastInstagramReportPost);
	}
	
	public void retrieveInsightsForPeriod(Integer period) {
		
		ZonedDateTime dateUntil = UtilFunctions.getDateForInstagramReport(LocalDateTime.now(ZoneId.of("UTC")).minusDays(2));
		ZonedDateTime dateSince = UtilFunctions.getDateForInstagramReport(LocalDateTime.now(ZoneId.of("UTC")).minusDays(period - 1));
		
		User user = updateProfileInfo();
		
		List<ZonedDateTime> reportDates = Stream.iterate(dateSince, date -> date.plusDays(1))
			    .limit(ChronoUnit.DAYS.between(dateSince, dateUntil) + 1L)
			    .collect(Collectors.toList());
		
		for(ZonedDateTime currentDay : reportDates) {
			retrieveInsightsForDay(currentDay, user);
		}
		
		retrieveInsightsForLifetime(dateUntil);
		
		InstagramReportPost lastInstagramReportPost = instagramReportPostRemote.getLastPost(profile.getNetworkId());
		saveInstagramReportsPosts(user, lastInstagramReportPost);
	}
	
	public void retrieveInsightsForLifetime(ZonedDateTime day) {
		
		try {
			
			InstagramBusinessBO instagramBusinessBO = new InstagramBusinessBO(profile, day);
			
			List<InstagramReport> instagramReports = new ArrayList<InstagramReport>();
			
			instagramReports.addAll(instagramBusinessBO.retrieveProfileInsightsForLifetime(profile));
			
			// Save all instagramreports.
			for (InstagramReport instagramReport : instagramReports) {
				
				try {
					
					InstagramReport instagramReportDB = instagramReportRemote.getEntity(profile.getNetworkId(), 
							instagramReport.getProperty(), instagramReport.getLabel());
					
					// Verify if this insight already exists on database, since it is a lifetime metric there should be only one for the current profile.
					if(instagramReportDB != null) {
						instagramReport.setIdInstagramReport(instagramReportDB.getIdInstagramReport());
						instagramReport.setVersion(instagramReportDB.getVersion());
					}
					
					instagramReportRemote.save(instagramReport);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}
	
	// Update the profile info.
	public User updateProfileInfo() {
		
		User user = null;
		
		try {
			
			AccessToken accessToken = new AccessToken(profile.getToken(), null);
			facebook = facebookIntegration.getFacebook(accessToken);

			// Update the profile info.
			user = new InstagramAPI().getUser(profile.getInstagramBusinessId(), facebook);
			
			profile = profileRemote.getById(profile.getIdProfile());
			
			profile.setScreenName(user.getUsername());
			profile.setAvatar(user.getProfilePictureUrl());
			profile.setFollowers(user.getFollowersCount());
			profile.setFollows(user.getFollowsCount());

			profileRemote.save(profile);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FacebookException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	}
	
	public void retrieveInsightsForDay(ZonedDateTime day, User user) {
		
		if(reportControlRemote.getEntityByNetwork(profile.getNetworkId(), Profile.INSTAGRAM, day.toEpochSecond()) == null) {

			try {

				AccessToken accessToken = new AccessToken(profile.getToken(), null);
				facebook = facebookIntegration.getFacebook(accessToken);

				profile = profileRemote.getById(profile.getIdProfile());

				// Start a report generation
				ReportControl reportControl = new ReportControl();
				reportControl.setNetworkId(profile.getNetworkId());
				reportControl.setNetworkType(profile.getType());
				reportControl.setDate(day.toEpochSecond());
				reportControl.setType(GROUP_MAIN);
				reportControl.setStartDate(new Date().getTime());

				reportControl = reportControlRemote.save(reportControl);

				InstagramBusinessBO instagramBusinessBO = new InstagramBusinessBO(profile, day);

				List<InstagramReport> instagramReports = new ArrayList<InstagramReport>();

				instagramReports.addAll(instagramBusinessBO.retrieveProfileInsightsForDay(profile, day));
				instagramReports.addAll(instagramBusinessBO.retrieveProfileOnlineFollowersInsights(profile, day));

				normalizeProfileInsights(profile, instagramReports, day.toEpochSecond());

				// Save all instagramreports.
				for (InstagramReport instagramReport : instagramReports) {
					
					try {
						instagramReportRemote.save(instagramReport);
					} catch (Exception e) {
						System.out.println("Networkd id: " + instagramReport.getIdInstagramUser());
						System.out.println("Date: " + instagramReport.getDate());
						System.out.println("Networkd id: " + instagramReport.getProperty());
						System.out.println("Value: " + instagramReport.getValue());
						e.printStackTrace();
					}
				}

				// If the list instagramReports is empty, it means that facebook did not generate the insights for this day, so we'll try another day. 
				if(instagramReports.isEmpty()) {
					reportControlRemote.delete(reportControl);
				} else {

					reportControl = reportControlRemote.getById(reportControl.getIdReportsControl());

					reportControl.setFinishDate(new Date().getTime());
					reportControlRemote.save(reportControl);
				}

			} catch (FacebookException e) {
				ValidateTokensController.validatingTokens(profile.getAccount());
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void normalizeProfileInsights(Profile profile, List<InstagramReport> instagramReports, Long day) {
		
		List<InstagramReport> instagramReportsGenerated = new ArrayList<InstagramReport>();
		
		for(InstagramReport instagramReport : instagramReports) {
			
			if(instagramReport.getProperty().equals(FOLLOWER_COUNT)) {
				
				instagramReport.setProperty(PROFILE_FOLLOWERS_ADDS);
				
				InstagramReport instaRepProfileFollowers = new InstagramReport();
				instaRepProfileFollowers.setIdInstagramUser(profile.getNetworkId());
				instaRepProfileFollowers.setProperty(PROFILE_FOLLOWERS);
				instaRepProfileFollowers.setDate(day);
				instaRepProfileFollowers.setValue(profile.getFollowers());
				
				instagramReportsGenerated.add(instaRepProfileFollowers);
				
				InstagramReport instaRepProfileFollowersRemoves = new InstagramReport();
				instaRepProfileFollowersRemoves.setIdInstagramUser(profile.getNetworkId());
				instaRepProfileFollowersRemoves.setProperty(PROFILE_FOLLOWERS_REMOVES);
				instaRepProfileFollowersRemoves.setDate(day);
				
				if(isFirstReportGeneration == false) {
					
					InstagramReport instRepFollowersYesterday = 
							instagramReportRemote.getEntityBeforeDate(profile.getNetworkId(), PROFILE_FOLLOWERS, day);
					
					if(instRepFollowersYesterday != null) {
						
						// Verify if date is yesterday.
						ZonedDateTime probablyYesterday = ZonedDateTime.of(LocalDateTime.ofEpochSecond(instRepFollowersYesterday.getDate(), 0, ZoneOffset.UTC), 
								ZoneId.of("America/Los_Angeles")); 
						ZonedDateTime yesterday = ZonedDateTime.of(LocalDateTime.ofEpochSecond(day, 0, ZoneOffset.UTC).minusDays(1), 
								ZoneId.of("America/Los_Angeles")); 
						
						if(probablyYesterday.isEqual(yesterday)) {
							
							if(instaRepProfileFollowers.getValue() > instRepFollowersYesterday.getValue()) {
								instaRepProfileFollowersRemoves.setValue(Math.abs((instaRepProfileFollowers.getValue() - instRepFollowersYesterday.getValue()) - instagramReport.getValue()));
							} else {
								instaRepProfileFollowersRemoves.setValue((instRepFollowersYesterday.getValue() - instaRepProfileFollowers.getValue()) + instagramReport.getValue());
							}
							
						} else {
							instaRepProfileFollowersRemoves.setValue(0L);	
						}
					
					} else {
						instaRepProfileFollowersRemoves.setValue(0L);
					}
					
				} else {
					instaRepProfileFollowersRemoves.setValue(0L);
				}
				
				instagramReportsGenerated.add(instaRepProfileFollowersRemoves);
				
				break;
			}
			
		}
		
		instagramReports.addAll(instagramReportsGenerated);
	}
	
	public void refreshPostsInsights() {
		
		ZonedDateTime dateSince = UtilFunctions.getDateForInstagramReport(LocalDateTime.now(ZoneId.of("UTC")).minusDays(UPDATE_POSTS_PERIOD));
		
		Long dateSinceInSeconds = dateSince.toEpochSecond();
		Paging paging = null;
		Boolean updateMorePosts = true;

		do {

			try {
				
				br.com.opsocial.server.utils.instagram.entities.MediaFeed mediaFeed = new InstagramAPI().getMedia(profile, paging, 25);
				
				for(Media media : mediaFeed.getData()) {
					
					InstagramReportPost instagramReportPost = instagramReportPostRemote.getById(media.getId());
					
					instagramReportPost.setAvatar(profile.getAvatar());
					instagramReportPost.setLikes(media.getLikeCount().intValue());
					instagramReportPost.setComments(media.getCommentsCount().intValue());
					instagramReportPost.setPictureCaption(media.getCaption());
					
					if(dateSinceInSeconds > instagramReportPost.getCreateTime()) {
						updateMorePosts = false;
						break;
					}

					try {

						instagramReportPostRemote.save(instagramReportPost);

						instagramReportPost = instagramReportPostRemote.getById(instagramReportPost.getIdMedia());

						saveInstagramTags(UtilFunctions.getHashTags(instagramReportPost.getPictureCaption()), instagramReportPost);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						System.out.println("User id: " + profile.getNetworkId().toString());
						e.printStackTrace();
					}
				}
				
				if(updateMorePosts) {
					paging = mediaFeed.getPaging();
				} else {
					paging = null;
				}
				

			} catch (FacebookException e) {
				ValidateTokensController.validatingTokens(profile.getAccount());
				e.printStackTrace();
				paging = null;
			} catch (Exception e) {
				e.printStackTrace();
				paging = null;
			}

		} while (paging != null && paging.getNext() != null);
	}
	
	private void saveInstagramReportsPosts(User user, InstagramReportPost lastInstagramReportPost) {

		Paging paging = null;
		Boolean hasMorePosts = true;
		
		do {

			try {
				
				br.com.opsocial.server.utils.instagram.entities.MediaFeed mediaFeed = new InstagramAPI().getMedia(profile, paging, 25);
				
				for(Media media : mediaFeed.getData()) {
					
					InstagramReportPost instagramReportPost = new InstagramReportPost();
					instagramReportPost.setIdMedia(media.getId());
					instagramReportPost.setIdInstagramUser(user.getIgId().toString());
					instagramReportPost.setUserName(user.getUsername());
					instagramReportPost.setFullName(user.getName());
					instagramReportPost.setAvatar(user.getProfilePictureUrl());
					instagramReportPost.setCreateTime(ZonedDateTime.parse(media.getTimestamp(), DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")).toEpochSecond());
					instagramReportPost.setLink(media.getPermalink());
					instagramReportPost.setType(media.getMediaType());
					instagramReportPost.setLikes(media.getLikeCount().intValue());
					instagramReportPost.setComments(media.getCommentsCount().intValue());

					if(media.getMediaType().equals(POST_TYPE_VIDEO)) {
						instagramReportPost.setPictureUrl(media.getThumbnailUrl());
					} else {
						instagramReportPost.setPictureUrl(media.getMediaUrl());
					}
					instagramReportPost.setPictureCaption(media.getCaption());
					
					if(lastInstagramReportPost != null && (lastInstagramReportPost.getCreateTime() >= instagramReportPost.getCreateTime())) {
						hasMorePosts = false;
						break;
					}

					try {

						instagramReportPostRemote.save(instagramReportPost);

						instagramReportPost = instagramReportPostRemote.getById(instagramReportPost.getIdMedia());

						saveInstagramTags(UtilFunctions.getHashTags(instagramReportPost.getPictureCaption()), instagramReportPost);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						System.out.println("User id: " + user.getIgId().toString());
						e.printStackTrace();
					}
				}
				
				if(hasMorePosts) {
					paging = mediaFeed.getPaging();
				} else {
					paging = null;
				}
				

			} catch (FacebookException e) {
				ValidateTokensController.validatingTokens(profile.getAccount());
				e.printStackTrace();
				paging = null;
			} catch (Exception e) {
				e.printStackTrace();
				paging = null;
			}

		} while (paging != null && paging.getNext() != null);
	}
	
	private void saveInstagramTags(List<String> tags, InstagramReportPost instagramReportPost) {

		for(String tag : tags) {

			try {

				InstagramPostTag instagramPostTag;

				InstagramPostTagId instagramPostTagId = new InstagramPostTagId();
				instagramPostTagId.setInstagramReportPost(instagramReportPost.getIdMedia());
				instagramPostTagId.setTag(tag);

				instagramPostTag = instagramPostTagRemote.getById(instagramPostTagId);

				if(instagramPostTag == null) {

					instagramPostTag = new InstagramPostTag();
					instagramPostTag.setInstagramReportPost(instagramReportPost);
					instagramPostTag.setTag(tag);

					try {

						instagramPostTagRemote.save(instagramPostTag);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public ReportGenerate getReportGenerate() {
		return reportGenerate;
	}

	public void setReportGenerate(ReportGenerate reportGenerate) {
		this.reportGenerate = reportGenerate;
	}
}
