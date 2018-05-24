package br.com.opsocial.server.utils.sets;

import java.text.SimpleDateFormat;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenancePostRemote;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.facebook.FacebookAPI;
import br.com.opsocial.server.utils.instagram.InstagramAPI;
import br.com.opsocial.server.utils.instagram.InstagramPostSender;
import br.com.opsocial.server.utils.linkedin.LinkedInApi;
import br.com.opsocial.server.utils.twitter.TwitterAPI;

public class PostMessage implements Runnable {

	private Post post;
	private Profile profile;
	
	public PostMessage(Post post, Profile profile) {
		this.post = post;
		this.profile = profile;
	}
	
	@Override
	public void run() {
		
		MaintenancePostRemote postRemote = (MaintenancePostRemote) RecoverMaintenance.recoverMaintenance("Post");
		
		Boolean wasSent = true;
		String notificationMessage = "";
		Boolean tryToPostAgain = false;
		
		try {
			
			post.setPosted(Post.POSTING);
			post.setCountPostingAttempts(post.getCountPostingAttempts() + 1);
			postRemote.save(post);
			
			if(profile.getType().equals(Profile.TWITTER)) {

				wasSent = new TwitterAPI().post(post, profile);

				if(!wasSent) {
					
					notificationMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.schedule.notsent", 
							new Object[]{profile.getScreenName(), UtilFunctions.getNetworkName(profile.getType()), 
									new SimpleDateFormat("MM/dd/yyyy HH:mm").format(post.getPostDate()), "Twitter"});
				}
				
			} else if(profile.getType().equals(Profile.FACEBOOK) ||
					profile.getType().equals(Profile.FACEBOOK_PAGE)) {

				wasSent = new FacebookAPI().post(post, profile);

				if(!wasSent) {
					notificationMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.schedule.notsent", 
							new Object[]{profile.getScreenName(), UtilFunctions.getNetworkName(profile.getType()), 
									new SimpleDateFormat("MM/dd/yyyy HH:mm").format(post.getPostDate()), "Facebook"});
				}
				
			} else if(profile.getType().equals(Profile.LINKEDIN) ||
					profile.getType().equals(Profile.LINKEDIN_PAGE)) {

				wasSent = new LinkedInApi().post(post, profile);

				if(!wasSent) {
					notificationMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.schedule.notsent", 
							new Object[]{profile.getScreenName(), UtilFunctions.getNetworkName(profile.getType()), 
									new SimpleDateFormat("MM/dd/yyyy HH:mm").format(post.getPostDate()), "LinkedIn"});
				}
				
			} else if(profile.getType().equals(Profile.INSTAGRAM)) {
				
				InstagramAPI instagramAPI = new InstagramAPI();	
				
				try {
	
					wasSent = instagramAPI.post(post, profile);

					if(!wasSent) {
						notificationMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.schedule.instagram.notok", 
								new Object[]{profile.getScreenName(), UtilFunctions.getNetworkName(profile.getType()), 
										new SimpleDateFormat("MM/dd/yyyy HH:mm").format(post.getPostDate()), instagramAPI.extractSendImageErrorMessage()});
					}

				} catch (Exception e) {
					wasSent = false;
					notificationMessage = OpSocialBackApplication.messageByLocaleService.getMessage("post.schedule.notsent", 
							new Object[]{profile.getScreenName(), UtilFunctions.getNetworkName(profile.getType()), 
									new SimpleDateFormat("MM/dd/yyyy HH:mm").format(post.getPostDate()), "Instagram"});
					e.printStackTrace();
				}
				
				tryToPostAgain = instagramAPI.tryToPostAgain();
			}
			
			post = postRemote.getById(post.getIdPost());
			
			if(wasSent) {

				post.setPosted(Post.POSTED);
				post.setStatus(Post.FINALIZED);
				postRemote.save(post);
			} else {

				// Verifica se houve algum problema temporário no envio da postagem, 
				// que pode ser reenviada no próximo minuto.
				if(!tryToPostAgain || post.getCountPostingAttempts() > InstagramPostSender.MAX_POSTING_ATTEMPTS) {

					post.setPosted(Post.ERROR_POSTING);
					postRemote.save(post);

					UtilFunctions.addNotification(notificationMessage, profile.getAccount().getIdAccount(), Notification.COLOR_ERROR, "/home/compose/" + post.getIdPost());

					new Thread(new PostNotDeliveredEmail(post, notificationMessage)).start();
					
				} else {
					
					post.setPosted(Post.NOT_POSTED);
					postRemote.save(post);
				}

			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}
