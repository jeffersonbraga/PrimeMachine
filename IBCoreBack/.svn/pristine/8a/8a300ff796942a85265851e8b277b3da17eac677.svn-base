package br.com.opsocial.server.utils.tasks;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.naming.NamingException;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;
import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceRateLimitStatusRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.twitter.RateLimitStatus;
import br.com.opsocial.ejb.entity.twitter.TwitterPost;

public class TwitterPostsUpdateController {
	
	private Timer timer;
	
	public TwitterPostsUpdateController() {
		
		timer = new Timer();
		timer.schedule(new TaskUpdateTwitterPosts(), 0, 900L * 1000L);
	}
	
	class TaskUpdateTwitterPosts extends TimerTask {

		public void run() {
    		
    		try {
    			
    			MaintenanceAccountRemote remote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
	    		
	    		List<Account> accounts = remote.getAllActive();
	    		
	    		for(Account account : accounts) {
    				new Thread(new UpdateTwitterPostsRunnable(account)).start();	
	    		}
	    		
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
		
		public class UpdateTwitterPostsRunnable implements Runnable {
			
			private Account account;
			
			private TwitterIntegration twitterIntegration;
			private Twitter twitter;
			private RateLimitStatus rateLimitStatus;
			
			private MaintenanceProfileRemote profileRemote;
			private MaintenanceTwitterPostRemote twitterPostRemote;
			private MaintenanceRateLimitStatusRemote rateLimitStatusRemote;
			
			public final static String RESOURCE_STATUSES_SHOW = "/statuses/show/:id";
			
			public UpdateTwitterPostsRunnable(Account account) {
				
				this.account = account;
				this.twitterIntegration = new TwitterIntegration();
				
				this.profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				this.twitterPostRemote = (MaintenanceTwitterPostRemote) RecoverMaintenance.recoverMaintenance("TwitterPost");
				this.rateLimitStatusRemote = (MaintenanceRateLimitStatusRemote) RecoverMaintenance.recoverMaintenance("RateLimitStatus");
			}
			
			@Override
			public void run() {

				List<TwitterPost> twitterPosts = twitterPostRemote.getTwitterPostsForUpdate(account.getIdAccount());

				for(TwitterPost twitterPost : twitterPosts) {
					if(!isRateLimited()) {

						try {

							Status status = twitter.showStatus(twitterPost.getStatusId());

							updateRateLimitStatus(status.getRateLimitStatus());

							twitterPost.setFavoriteCount(Long.valueOf(status.getFavoriteCount()));
							twitterPost.setRetweetCount(Long.valueOf(status.getRetweetCount()));
							twitterPost.setLastUpdate(new Date());

						} catch (TwitterException twitterException) {

							if(twitterException.resourceNotFound() && twitterException.getErrorCode() == 34) {

								//----- System.out.println("Status excluído: " + twitterPost.getStatusId());
								//----- System.out.println("Perfil: " + twitterPost.getScreenName());

								twitterPost.setState(TwitterPost.DELETED_STATUS);

							} else if(twitterException.getStatusCode() == TwitterException.FORBIDDEN && twitterException.getErrorCode() == 179) {

								//----- System.out.println("Status proibido: " + twitterPost.getStatusId());
								//----- System.out.println("Perfil: " + twitterPost.getScreenName());

								twitterPost.setState(TwitterPost.FORBIDDEN_STATUS);

							}

							twitterException.printStackTrace();

						} finally {

							try {

								twitterPostRemote.save(twitterPost);

							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}
				}
			}
			
			private void updateRateLimitStatus(twitter4j.RateLimitStatus rateLimitStatusResponse) {

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
			
			public boolean isRateLimited() {
				
				twitter = null;
				
				try {

					List<Profile> profiles = profileRemote.getEntityByNetworkType(Profile.TWITTER, this.account.getIdAccount());

					for(Profile profile : profiles) {

						rateLimitStatus = rateLimitStatusRemote.getEntityByNetworkType(profile.getIdProfile(), RESOURCE_STATUSES_SHOW);

						if(rateLimitStatus == null) {

							twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());

							twitter4j.RateLimitStatus rateLimitStatusResponse = twitter.getRateLimitStatus("statuses").get(RESOURCE_STATUSES_SHOW);

							rateLimitStatus = new RateLimitStatus();
							rateLimitStatus.setProfile(profile);
							rateLimitStatus.setResource(RESOURCE_STATUSES_SHOW);
							rateLimitStatus.setRequestsLimit(rateLimitStatusResponse.getLimit());
							rateLimitStatus.setRequestsRemaining(rateLimitStatusResponse.getRemaining());
							rateLimitStatus.setResetDate(Long.valueOf(rateLimitStatusResponse.getResetTimeInSeconds()));

							rateLimitStatus = rateLimitStatusRemote.save(rateLimitStatus);

							break;
						} else {
							if(rateLimitStatus.getResetDate() < (new Date().getTime() / 1000L)) {
								
								twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());

								twitter4j.RateLimitStatus rateLimitStatusResponse = twitter.getRateLimitStatus("statuses").get(RESOURCE_STATUSES_SHOW);

								rateLimitStatus.setRequestsLimit(rateLimitStatusResponse.getLimit());
								rateLimitStatus.setRequestsRemaining(rateLimitStatusResponse.getRemaining());
								rateLimitStatus.setResetDate(Long.valueOf(rateLimitStatusResponse.getResetTimeInSeconds()));

								rateLimitStatus = rateLimitStatusRemote.save(rateLimitStatus);

								break;
							} else {
								if(rateLimitStatus.getRequestsRemaining() > 0) {
									twitter = twitterIntegration.getTwitter(profile.getToken(), profile.getTokenSecret());
									break;
								}
							}
						}
					}

					if(twitter == null) {

						rateLimitStatus = rateLimitStatusRemote.getEntityByNetworkType(null, RESOURCE_STATUSES_SHOW);

						if(rateLimitStatus == null) {

							twitter = twitterIntegration.getTwitterApp();

							twitter4j.RateLimitStatus rateLimitStatusResponse = twitter.getRateLimitStatus("statuses").get(RESOURCE_STATUSES_SHOW);

							rateLimitStatus = new RateLimitStatus();
							rateLimitStatus.setProfile(null);
							rateLimitStatus.setResource(RESOURCE_STATUSES_SHOW);
							rateLimitStatus.setRequestsLimit(rateLimitStatusResponse.getLimit());
							rateLimitStatus.setRequestsRemaining(rateLimitStatusResponse.getRemaining());
							rateLimitStatus.setResetDate(Long.valueOf(rateLimitStatusResponse.getResetTimeInSeconds()));

							rateLimitStatus = rateLimitStatusRemote.save(rateLimitStatus);
							
						} else {
							if(rateLimitStatus.getResetDate() < (new Date().getTime() / 1000L)) {

								twitter = twitterIntegration.getTwitterApp();

								twitter4j.RateLimitStatus rateLimitStatusResponse = twitter.getRateLimitStatus("statuses").get(RESOURCE_STATUSES_SHOW);

								rateLimitStatus.setRequestsLimit(rateLimitStatusResponse.getLimit());
								rateLimitStatus.setRequestsRemaining(rateLimitStatusResponse.getRemaining());
								rateLimitStatus.setResetDate(Long.valueOf(rateLimitStatusResponse.getResetTimeInSeconds()));

								rateLimitStatus = rateLimitStatusRemote.save(rateLimitStatus);
							} else {
								if(rateLimitStatus.getRequestsRemaining() > 0) {
									twitter = twitterIntegration.getTwitterApp();
								}
							}						
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
				
				return twitter == null ? true : false;
			}
		}
	}
}
