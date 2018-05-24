package br.com.opsocial.server.actions.profiles;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceRateLimitStatusRemote;
import br.com.opsocial.ejb.das.MaintenanceReportGenerateRemote;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.InternalAnalytic;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.report.ReportGenerate;
import br.com.opsocial.ejb.entity.twitter.RateLimitStatus;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.emails.EmailSender;
import br.com.opsocial.server.utils.monitorings.MonitoringTwitter;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;
import br.com.opsocial.server.utils.reports.ReportFacebook;
import br.com.opsocial.server.utils.reports.ReportGenerateRunnable;
import br.com.opsocial.server.utils.reports.ReportGenerateThread;
import br.com.opsocial.server.utils.reports.ReportGenerateVerification;
import br.com.opsocial.server.validation.InsertProfilesValidation;
import twitter4j.Twitter;

@RestController
@RequestMapping("woopsocial")
public class InsertProfilesAction {
	
	@Autowired
	InsertProfilesValidation insertProfilesValidation;
	
	@CrossOrigin
	@RequestMapping(value = "/insert_profiles",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> doAction(@RequestBody List<Profile> profiles, HttpSession session) throws Exception {
		
		Profile parentProfile = null;
		ResponseEntity<Object> responseEntity;
		List<Profile> profilesInserted = new ArrayList<Profile>();
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) 
				RecoverMaintenance.recoverMaintenance("Profile");
		
		MaintenanceReportGenerateRemote reportGenerateRemote = (MaintenanceReportGenerateRemote) 
				RecoverMaintenance.recoverMaintenance("ReportGenerate");
		
		MaintenanceUserRemote userRemote = (MaintenanceUserRemote)
				RecoverMaintenance.recoverMaintenance("User");
		
		ReportGenerateThread reportGenerateThread = new ReportGenerateThread();
		
		User user = SecurityUtils.getCurrentUser();
		// Resgata usuário atualizado, a fim de resgatar número de perfis de seu plano o mais atualizado possível.
		user = userRemote.getById(user.getIdUser());
		
		// Verify if the account allows more profiles to be inserted.
		Boolean canInsertMoreProfiles = insertProfilesValidation.canInsertMoreProfiles(profiles, user);
		
		JsonObject response = new JsonObject();
		response.addProperty("error", insertProfilesValidation.getProfileControlMessage());
		
		if(canInsertMoreProfiles) {
			
			for(Profile profile : profiles) {
				
				profile.setInsertionDate(Calendar.getInstance().getTimeInMillis() / 1000L);
				
				if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
					
					Profile baseProfile = profileRemote.getEntityByNetworkId(profile.getNetworkId(), user.getAccount().getIdAccount());
					
					if(baseProfile == null) {

						if(profile.getType().equals(Profile.FACEBOOK)) {
							
							parentProfile = profileRemote.save(profile);
							
							Object[] messageArgs = new Object[]{ user.getName(), parentProfile.getScreenName() };
							
							String message = OpSocialBackApplication.messageByLocaleService.getMessage("add.channel.fb", messageArgs);
							UtilFunctions.addNotification(message, parentProfile.getAccount().getIdAccount(), Notification.COLOR_SUCCESS, "");
							
							UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.ADD_FACEBOOK_PROFILE_CHANNEL, profile.getAccount().getIdAccount());
							
							profilesInserted.add(parentProfile);
							
							List<Profile> childPages = profileRemote.getEntitiesByProfileInformation(
									profile.getNetworkId(), user.getAccount().getIdAccount());
							
							// Atualiza o Parent Profile de páginas que já se encontram inseridas no banco.
							for(Profile childPage : childPages) {
								
								childPage.setParentProfile(parentProfile);
								childPage.setProfileInformation(null);
								profileRemote.save(childPage);
							}
							
						} else if(profile.getType().equals(Profile.FACEBOOK_PAGE)) {
							
							if(parentProfile != null) {
								profile.setParentProfile(parentProfile);
								profile.setProfileInformation(null);
							}
							
							profile = profileRemote.save(profile);
							
							Object[] messageArgs = new Object[]{ user.getName(), profile.getScreenName() };
							
							String message = OpSocialBackApplication.messageByLocaleService.getMessage("add.channel.fb", messageArgs);
							UtilFunctions.addNotification(message, profile.getAccount().getIdAccount(), Notification.COLOR_SUCCESS, "");
							
							UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.ADD_FACEBOOK_PAGE_CHANNEL, profile.getAccount().getIdAccount());
							
							profilesInserted.add(profile);
						
							if(!reportGenerateRemote.hasEntity(profile.getNetworkId(), profile.getType())) {
								
								ReportGenerate reportGenerate = new ReportGenerate();
								reportGenerate.setNetworkId(profile.getNetworkId());
								reportGenerate.setNetworkType(profile.getType());
								reportGenerate.setTokenSecret(profile.getToken());
								reportGenerate.setState(ReportFacebook.UNAVAILABLE);
								
								// Verifica se a página pode resgatar seus insights.
								if(ReportGenerateVerification.isReportActive(reportGenerate)) {
									
									reportGenerate.setState(ReportFacebook.GENERATING);
									reportGenerate = reportGenerateRemote.save(reportGenerate);
									
									reportGenerateThread.addReportGenerateRunnable(new ReportGenerateRunnable(profile, ReportGenerateRunnable.REPORT_DAYS));
								} else {
									reportGenerate = reportGenerateRemote.save(reportGenerate);
								}
							} else {
								
								ReportGenerate reportGenerate = reportGenerateRemote.getEntity(profile.getNetworkId(), profile.getType());
								reportGenerate.setTokenSecret(profile.getToken());
								reportGenerate.setState(ReportFacebook.FINALIZED);
								
								reportGenerateRemote.save(reportGenerate);
							}
						}	
					}
					
				} else if(profile.getType().equals(Profile.TWITTER)) {
					
					profile = profileRemote.save(profile);
					profilesInserted.add(profile);
					
					Object[] messageArgs = new Object[]{ user.getName(), profile.getScreenName() };
					
					String message = OpSocialBackApplication.messageByLocaleService.getMessage("add.channel.tw", messageArgs);

					UtilFunctions.addNotification(message, profile.getAccount().getIdAccount(), Notification.COLOR_SUCCESS, "");
					
					UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.ADD_TWITTER_CHANNEL, profile.getAccount().getIdAccount());
					
					MaintenanceRateLimitStatusRemote rateLimitStatusRemote = (MaintenanceRateLimitStatusRemote) RecoverMaintenance.recoverMaintenance("RateLimitStatus");

	            	Twitter twitter = new TwitterIntegration().getTwitter(profile.getToken(), profile.getTokenSecret());

	            	twitter4j.RateLimitStatus rateLimitStatusResponse = twitter.getRateLimitStatus("search").get(MonitoringTwitter.RESOURCE_SEARCH_TWEETS);

	            	RateLimitStatus rateLimitStatus = new RateLimitStatus();
	            	rateLimitStatus.setProfile(profile);
	            	rateLimitStatus.setResource(MonitoringTwitter.RESOURCE_SEARCH_TWEETS);
	            	rateLimitStatus.setRequestsLimit(rateLimitStatusResponse.getLimit());
	            	rateLimitStatus.setRequestsRemaining(rateLimitStatusResponse.getRemaining());
	            	rateLimitStatus.setResetDate(Long.valueOf(rateLimitStatusResponse.getResetTimeInSeconds()));

	            	rateLimitStatusRemote.save(rateLimitStatus);
	            	
				} else if(profile.getType().equals(Profile.INSTAGRAM)) {
					
					profile = profileRemote.save(profile);
					profilesInserted.add(profile);
					
					Object[] messageArgs = new Object[]{ user.getName(), profile.getScreenName() };
					
					String message = OpSocialBackApplication.messageByLocaleService.getMessage("add.channel.ig", messageArgs);
					UtilFunctions.addNotification(message, profile.getAccount().getIdAccount(), Notification.COLOR_SUCCESS, "");
					
					UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.ADD_INSTAGRAM_CHANNEL, profile.getAccount().getIdAccount());
	            	
	            	if(!reportGenerateRemote.hasEntity(profile.getNetworkId(), profile.getType())) {
	            		
	            		ReportGenerate reportGenerate = new ReportGenerate();
						reportGenerate.setNetworkId(profile.getNetworkId());
						reportGenerate.setNetworkType(profile.getType());
						reportGenerate.setTokenSecret(profile.getToken());
						reportGenerate.setState(ReportFacebook.GENERATING);
						reportGenerate.setIsInNewInstaFollowersReports('T');	
						
						reportGenerate = reportGenerateRemote.save(reportGenerate);

	    				reportGenerateThread.addReportGenerateRunnable(new ReportGenerateRunnable(profile, ReportGenerateRunnable.REPORT_DAYS));
	            	}
	            	
	            } else if(profile.getType().equals(Profile.LINKEDIN)) {
					
					parentProfile = profileRemote.save(profile);
					
					profilesInserted.add(parentProfile);
					
					Object[] messageArgs = new Object[]{ user.getName(), parentProfile.getScreenName() };
					
					String message = OpSocialBackApplication.messageByLocaleService.getMessage("add.channel.lnkd", messageArgs);
					UtilFunctions.addNotification(message, profile.getAccount().getIdAccount(), Notification.COLOR_SUCCESS, "");
					
					UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.ADD_LINKEDIN_PROFILE_CHANNEL, profile.getAccount().getIdAccount());
					
					List<Profile> childPages = profileRemote.getEntitiesByProfileInformation(
							profile.getNetworkId(), user.getAccount().getIdAccount());
					
					// Atualiza o Parent Profile de páginas que já se encontram inseridas no banco.
					for(Profile childPage : childPages) {
						
						childPage.setParentProfile(parentProfile);
						childPage.setProfileInformation(null);
						profileRemote.save(childPage);
					}
					
				} else if(profile.getType().equals(Profile.LINKEDIN_PAGE)) {
					
					if(parentProfile != null) {
						profile.setParentProfile(parentProfile);
						profile.setProfileInformation(null);
					}
					
					profile = profileRemote.save(profile);
					profilesInserted.add(profile);
					
					Object[] messageArgs = new Object[]{ user.getName(), profile.getScreenName() };
					
					String message = OpSocialBackApplication.messageByLocaleService.getMessage("add.channel.lnkd", messageArgs);
					UtilFunctions.addNotification(message, profile.getAccount().getIdAccount(), Notification.COLOR_SUCCESS, "");
					
					UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.ADD_LINKEDIN_PAGE_CHANNEL, profile.getAccount().getIdAccount());
				}
			}
			
			reportGenerateThread.start();
			
			new EmailSender().sendWelcomeToWoopEmail(user.getAccount());
						
			responseEntity = new ResponseEntity<Object>(profilesInserted, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<Object>(response.toString(), HttpStatus.FORBIDDEN);
		}
		
		return responseEntity;
	}
	

	@CrossOrigin
	@RequestMapping(value = "/validate_insert_profiles",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> validateCanInsertProfile(@RequestBody List<Profile> profiles, HttpSession session) throws Exception {

		User user 											= SecurityUtils.getCurrentUser();
		Boolean canInsertMoreProfiles 						= insertProfilesValidation.canInsertMoreProfiles(profiles, user);
		ResponseEntity<Object> responseEntity;

		if(canInsertMoreProfiles) {
			responseEntity = new ResponseEntity<Object>(true, HttpStatus.OK);
		} else {

			JsonObject response = new JsonObject();
			response.addProperty("error", insertProfilesValidation.getProfileControlMessage());
			responseEntity = new ResponseEntity<Object>(response.toString(), HttpStatus.FORBIDDEN);
		}

		return responseEntity;
	}
}