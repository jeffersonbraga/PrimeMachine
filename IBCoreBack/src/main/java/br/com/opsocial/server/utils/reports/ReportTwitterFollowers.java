package br.com.opsocial.server.utils.reports;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.NamingException;

import twitter4j.IDs;
import twitter4j.ResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;
import br.com.opsocial.ejb.das.MaintenanceRateLimitStatusRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterFollowerControlRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterFollowerRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.TwitterFollowerId;
import br.com.opsocial.ejb.entity.report.TwitterFollower;
import br.com.opsocial.ejb.entity.report.TwitterFollowerControl;
import br.com.opsocial.ejb.entity.twitter.RateLimitStatus;

public class ReportTwitterFollowers extends TimerTask {

	private TwitterIntegration twitterIntegration;
	private TwitterFollowerControl twitterFollowerControl;
	private Twitter twitter;
	private Profile profile;
	private MaintenanceTwitterFollowerControlRemote twitterFollowerControlRemote;
	private MaintenanceTwitterFollowerRemote twitterFollowerRemote;
	private MaintenanceRateLimitStatusRemote rateLimitStatusRemote;	

	private Long reportDateStamp;

	private List<String> femaleNamesSample;
	private List<String> maleNamesSample;

	public static final String FOLLOWERS_FEMALE_COUNT_ALL = "followersFemaleCountAll";
	public static final String FOLLOWERS_MALE_COUNT_ALL = "followersMaleCountAll";

	public static final String GROUP_FOLLOWERS = "followers";

	public final static String FAMILY_RESOURCE_FOLLOWERS = "followers";
	public final static String FAMILY_RESOURCE_USERS = "users";
	public final static String FAMILY_RESOURCE_STATUSES = "statuses";
	
	public final static String RESOURCE_FOLLOWERS_IDS = "/followers/ids";
	public final static String RESOURCE_USERS_LOOKUP = "/users/lookup";
	public final static String RESOURCE_STATUSES_LOOKUP = "/statuses/lookup";
	
	public static final Character GENERATING = 'G';
	public static final Character FINALIZED = 'F';
	
	public static final Character IS_A_FOLLOWER = 'T';
	public static final Character NOT_A_FOLLOWER = 'F';
	
	public static final Long REQUESTS_INTERVAL = 900 * 1000L;

	public ReportTwitterFollowers(Profile profile) {

		this.profile = profile;
		this.twitterIntegration = new TwitterIntegration();

		Calendar reportDate = Calendar.getInstance();
		reportDate.set(Calendar.HOUR_OF_DAY, 0);
		reportDate.set(Calendar.MINUTE, 0);
		reportDate.set(Calendar.SECOND, 0);

		reportDateStamp = reportDate.getTimeInMillis() / 1000L;
		
		twitterFollowerControlRemote = (MaintenanceTwitterFollowerControlRemote) RecoverMaintenance.recoverMaintenance("TwitterFollowerControl");
		twitterFollowerRemote = (MaintenanceTwitterFollowerRemote) RecoverMaintenance.recoverMaintenance("TwitterFollower");
		rateLimitStatusRemote = (MaintenanceRateLimitStatusRemote) RecoverMaintenance.recoverMaintenance("RateLimitStatus");
		
		twitterFollowerControl = twitterFollowerControlRemote.getById(Long.valueOf(profile.getNetworkId()));

		femaleNamesSample = new ArrayList<String>();
		femaleNamesSample.add("MARIA");
		femaleNamesSample.add("ANA");
		femaleNamesSample.add("FRANCISCA");
		femaleNamesSample.add("ANTONIA");
		femaleNamesSample.add("MÁRCIA");
		femaleNamesSample.add("ADRIANA");
		femaleNamesSample.add("SANDRA");
		femaleNamesSample.add("JOSEFA");
		femaleNamesSample.add("PATRÍCIA");
		femaleNamesSample.add("VERA");
		femaleNamesSample.add("TEREZINHA");
		femaleNamesSample.add("SONIA");
		femaleNamesSample.add("RITA");
		femaleNamesSample.add("LUCIANA");
		femaleNamesSample.add("ROSA");
		femaleNamesSample.add("RAIMUNDA");

		maleNamesSample = new ArrayList<String>();
		maleNamesSample.add("JOSÉ");
		maleNamesSample.add("ANTÔNIO");
		maleNamesSample.add("JOÃO");
		maleNamesSample.add("FRANCISCO");
		maleNamesSample.add("LUIZ");
		maleNamesSample.add("PAULO");
		maleNamesSample.add("CARLOS");
		maleNamesSample.add("MANOEL");
		maleNamesSample.add("PEDRO");
		maleNamesSample.add("MARCOS");
		maleNamesSample.add("RAIMUNDO");
		maleNamesSample.add("SEBASTIÃO");
		maleNamesSample.add("MARCELO");
		maleNamesSample.add("JORGE");
		maleNamesSample.add("GERALDO");
		maleNamesSample.add("LUÍZ");
		maleNamesSample.add("FERNANDO");
		maleNamesSample.add("FÁBIO");
		maleNamesSample.add("ROBERTO");
		maleNamesSample.add("MÁRCIO");
		maleNamesSample.add("EDSON");
		maleNamesSample.add("ANDRÉ");
		maleNamesSample.add("SÉRGIO");
		maleNamesSample.add("DANIEL");
		maleNamesSample.add("RODRIGO");
		maleNamesSample.add("RAFAEL");
		maleNamesSample.add("JOAQUIM");
		maleNamesSample.add("RICARDO");
		maleNamesSample.add("EDUARDO");
		maleNamesSample.add("ALEXANDRE");
		maleNamesSample.add("CLAUDIO");
		maleNamesSample.add("BENEDITO");
		maleNamesSample.add("LEANDRO");
		maleNamesSample.add("MARIO");
	}
	
	@Override
	public void run() {
		
		twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());

		retrieveFollowersId(profile);
		calculateFollowersReports(profile);

		// Verifica se há necessidade de uma nova atualização de seguidores do perfil.
		if(twitterFollowerControl.getState().equals(GENERATING)) {

			Timer timer = new Timer();
			timer.schedule(new ReportTwitterFollowers(profile), REQUESTS_INTERVAL);

		}
		
	}
	
	private void retrieveFollowersId(Profile profile) {

		IDs followersId;
		long cursor;

		try {

			if(twitterFollowerControl.getNextCursor() != 0L) {
				
				if(!isRateLimited(profile, FAMILY_RESOURCE_FOLLOWERS, RESOURCE_FOLLOWERS_IDS)) {
					
					cursor = twitterFollowerControl.getNextCursor();
					
					do {		

						followersId = twitter.getFollowersIDs(cursor);

						updateRateLimitStatus(followersId.getRateLimitStatus(), profile.getIdProfile(), RESOURCE_FOLLOWERS_IDS);
						
						for(long followerId : followersId.getIDs()) {
							saveTwitterFollower(followerId);
						}

						cursor = followersId.getNextCursor();

					} while (cursor != 0L && !isRateLimited(profile, FAMILY_RESOURCE_FOLLOWERS, RESOURCE_FOLLOWERS_IDS));
					
					twitterFollowerControl.setNextCursor(cursor);
					
					try {

						twitterFollowerControlRemote.save(twitterFollowerControl);
						
						twitterFollowerControl = twitterFollowerControlRemote.getById(twitterFollowerControl.getIdProfile()); 

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private void calculateFollowersReports(Profile profile) {

		ResponseList<User> followersUsers;
		long[] followersId;
		Character gender;

		if(!isRateLimited(profile, FAMILY_RESOURCE_USERS, RESOURCE_USERS_LOOKUP)) {

			do {

				followersId = twitterFollowerRemote.getFollowersNotUpdated(twitterFollowerControl.getIdProfile(), reportDateStamp);	

				if(followersId.length == 0) {
					
					twitterFollowerControl.setState(FINALIZED);
					twitterFollowerControl.setNextCursor(-1L);
					
					try {

						twitterFollowerControlRemote.save(twitterFollowerControl);
						
						twitterFollowerControl = twitterFollowerControlRemote.getById(twitterFollowerControl.getIdProfile()); 

					} catch (Exception e) {
						e.printStackTrace();
					}
					
					// Rotina que exclui os perfis que deixaram de ser seguidores do perfil corrente.
					twitterFollowerRemote.deleteUnfollowers(twitterFollowerControl.getIdProfile());
					
					// Rotina que atualiza os últimos 20 seguidores.
					updateMostRecentFollwers(twitterFollowerControl.getIdProfile());
					
					break;
				}

				try {

					followersUsers = twitter.lookupUsers(followersId);
					
					updateRateLimitStatus(followersUsers.getRateLimitStatus(), profile.getIdProfile(), RESOURCE_USERS_LOOKUP);

					for (User followerUser : followersUsers) {

						gender = 'U';

						gender = discoverGender(followerUser.getName().split(" ")[0].toUpperCase());

						updateTwitterFollower(followerUser, gender);
					}

				} catch (TwitterException e) {
					
					updateRateLimitStatus(e.getRateLimitStatus(), profile.getIdProfile(), RESOURCE_USERS_LOOKUP);
					
					// Rotina que exclui perfis que constam como followers, 
					// mas na verdade foram bloqueados pelo twitter ou deixaram de existir.
					if(e.resourceNotFound() && (e.getErrorCode() == 34 || e.getErrorCode() == 17)) {
						
						for(long followerId : followersId) {
							deleteTwitterFollower(profile, followerId);
						}
						
					} else {
						e.printStackTrace();
					}
				}

			} while (!isRateLimited(profile, FAMILY_RESOURCE_USERS, RESOURCE_USERS_LOOKUP));
		}
		
	}

	private void saveTwitterFollower(long followerId) {

		TwitterFollowerId twitterFollowerId = new TwitterFollowerId();
		twitterFollowerId.setFollowerId(followerId);
		twitterFollowerId.setIdProfile(twitterFollowerControl.getIdProfile());
		
		TwitterFollower twitterFollower = twitterFollowerRemote.getById(twitterFollowerId);
		
		if(twitterFollower == null) {
			
			twitterFollower = new TwitterFollower();
			twitterFollower.setScreenName("");
			twitterFollower.setAvatar("");
			twitterFollower.setFollowerId(followerId);
			twitterFollower.setIdProfile(Long.valueOf(profile.getNetworkId()));
			twitterFollower.setFollowersCount(0L);
			twitterFollower.setLastTweetDate(0L);
			twitterFollower.setLocation("");
			twitterFollower.setGender('U');
			twitterFollower.setLastUpdateDate(0L);
			twitterFollower.setIsStillFollower(IS_A_FOLLOWER);

			try {

				twitterFollowerRemote.save(twitterFollower);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} else {
			
			twitterFollower.setIsStillFollower(IS_A_FOLLOWER);
			
			try {

				twitterFollowerRemote.save(twitterFollower);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	private void updateTwitterFollower(User follower, Character gender) {

		TwitterFollowerId twitterFollowerId = new TwitterFollowerId();
		twitterFollowerId.setFollowerId(follower.getId());
		twitterFollowerId.setIdProfile(Long.valueOf(profile.getNetworkId()));

		TwitterFollower twitterFollower = twitterFollowerRemote.getById(twitterFollowerId);
		twitterFollower.setScreenName(follower.getScreenName());
		twitterFollower.setAvatar(follower.getProfileImageURLHttps());
		twitterFollower.setFollowersCount(Long.valueOf(follower.getFollowersCount()));
		twitterFollower.setLocation(follower.getLocation());
		twitterFollower.setLastTweetDate(follower.getStatus() != null ? follower.getStatus().getCreatedAt().getTime() / 1000L : 0L);
		twitterFollower.setGender(gender);
		twitterFollower.setLastUpdateDate(reportDateStamp);

		try {

			twitterFollowerRemote.save(twitterFollower);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void deleteTwitterFollower(Profile profile, long followerId) {
		
		TwitterFollowerId twitterFollowerId = new TwitterFollowerId();
		twitterFollowerId.setFollowerId(followerId);
		twitterFollowerId.setIdProfile(Long.valueOf(profile.getNetworkId()));
		
		TwitterFollower twitterFollower = twitterFollowerRemote.getById(twitterFollowerId);
		
		try {
			
			twitterFollowerRemote.delete(twitterFollower);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Character discoverGender(String name) {

		Character gender = 'U';

		if(femaleNamesSample.contains(name)) {
			gender = 'F';
		} else if (maleNamesSample.contains(name)) {
			gender = 'M';
		} else if(name.endsWith("A")) {
			gender = 'F';
		}

		return gender;
	}	

	public boolean isRateLimited(Profile profile, String resourceFamily, String resource) {

		RateLimitStatus rateLimitStatus;
		Boolean rateLimited = true;

		try {

			rateLimitStatus = rateLimitStatusRemote.getEntityByNetworkType(profile.getIdProfile(), resource);

			if(rateLimitStatus == null) {

				twitter4j.RateLimitStatus rateLimitStatusResponse = twitter.getRateLimitStatus(resourceFamily).get(resource);

				rateLimitStatus = new RateLimitStatus();
				rateLimitStatus.setProfile(profile);
				rateLimitStatus.setResource(resource);
				rateLimitStatus.setRequestsLimit(rateLimitStatusResponse.getLimit());
				rateLimitStatus.setRequestsRemaining(rateLimitStatusResponse.getRemaining());
				rateLimitStatus.setResetDate(Long.valueOf(rateLimitStatusResponse.getResetTimeInSeconds()));

				rateLimitStatus = rateLimitStatusRemote.save(rateLimitStatus);

				rateLimited = false;
			} else {

				if(rateLimitStatus.getResetDate() < (new Date().getTime() / 1000L)) {

					twitter4j.RateLimitStatus rateLimitStatusResponse = twitter.getRateLimitStatus(resourceFamily).get(resource);

					rateLimitStatus.setRequestsLimit(rateLimitStatusResponse.getLimit());
					rateLimitStatus.setRequestsRemaining(rateLimitStatusResponse.getRemaining());
					rateLimitStatus.setResetDate(Long.valueOf(rateLimitStatusResponse.getResetTimeInSeconds()));

					rateLimitStatus = rateLimitStatusRemote.save(rateLimitStatus);

					rateLimited = false;
				} else if(rateLimitStatus.getRequestsRemaining() > 0) {
					rateLimited = false;
				}
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return rateLimited;
	}
	
	private void updateRateLimitStatus(twitter4j.RateLimitStatus rateLimitStatusResponse, Long idProfile, String resource) {

		RateLimitStatus rateLimitStatus = rateLimitStatusRemote.getEntityByNetworkType(idProfile, resource);
		
		if(rateLimitStatusResponse != null) {
			
			rateLimitStatus.setRequestsLimit(rateLimitStatusResponse.getLimit());
			rateLimitStatus.setRequestsRemaining(rateLimitStatusResponse.getRemaining());
			rateLimitStatus.setResetDate(Long.valueOf(rateLimitStatusResponse.getResetTimeInSeconds()));

			try {

				rateLimitStatusRemote.save(rateLimitStatus);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}
	
	private void updateMostRecentFollwers(Long idProfile) {
		
		ResponseList<User> followersUsers;

		try {

			followersUsers = twitter.getFollowersList(idProfile, -1L);

			int i = 1;
			for (User follower : followersUsers) {

				TwitterFollowerId twitterFollowerId = new TwitterFollowerId();
				twitterFollowerId.setFollowerId(follower.getId());
				twitterFollowerId.setIdProfile(Long.valueOf(profile.getNetworkId()));

				TwitterFollower twitterFollower = twitterFollowerRemote.getById(twitterFollowerId);
				if(twitterFollower != null) {
					
					twitterFollower.setFollowingDate(reportDateStamp - i);

					try {

						twitterFollowerRemote.save(twitterFollower);

					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (Exception e) {
						e.printStackTrace();
					}
					
				}
				
				i++;
			}

		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
	}
}
