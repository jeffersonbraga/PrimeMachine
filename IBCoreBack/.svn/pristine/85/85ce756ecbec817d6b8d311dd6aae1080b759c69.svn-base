package br.com.opsocial.server.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.facebook.FacebookAPI;
import br.com.opsocial.server.utils.instagram.InstagramAPI;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import br.com.opsocial.server.utils.reports.ReportGenerateVerification;
import facebook4j.Account;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.Reading;
import facebook4j.auth.AccessToken;

@RestController
@RequestMapping("woopsocial")
public class AuthInstagramCallbackServlet {
	
	private String instagramAuthAction;

	private facebook4j.User fbUser;
	private AccessToken accessToken;
	private Facebook facebook;
	private Calendar tokenDate;

	private MaintenanceProfileRemote remoteProfile;
	private User user;

	private Profile baseProfile;

	public static final String GET_ACCESS_TOKEN = "getAccessToken";
	public static final String REFRESH_TOKEN = "refreshToken";
	
	public static final String ME_FIELDS = "id,name,picture";
	
	@CrossOrigin
	@RequestMapping(value = "/instagram",
	method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	protected void doAction(String code, HttpSession session) {
		
		try {
			
			Facebook systemFacebook = (Facebook) session.getAttribute("facebookSystem");

			if(code != null) {
				
				accessToken = systemFacebook.getOAuthAccessToken(code);
				tokenDate = new GregorianCalendar();
				tokenDate.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

				facebook = new FacebookIntegration().getFacebook(accessToken);

				fbUser = facebook.getMe(new Reading().fields(ME_FIELDS));

				remoteProfile = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");

				user = SecurityUtils.getCurrentUser();

				baseProfile = remoteProfile.getEntityByNetworkId(fbUser.getId(), user.getAccount().getIdAccount());

				instagramAuthAction = (String) session.getAttribute("instagram_auth_action");

				if(instagramAuthAction.equals(GET_ACCESS_TOKEN)) {
					getAccessToken(session);
				} else if(instagramAuthAction.equals(REFRESH_TOKEN)) {
					Profile profileToReconnect = (Profile) session.getAttribute("profile_instagram_reconnect");
					refreshToken(profileToReconnect, session);
				}
			}
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FacebookException e) {
			e.printStackTrace();
		}
	}
	
	private void getAccessToken(HttpSession session) {
		
		List<Profile> profilesToAdd = new ArrayList<Profile>();
		
		try {

			if(baseProfile == null) {

				Profile profile = new Profile();
				profile.setAccount(user.getAccount());
				profile.setNetworkId(fbUser.getId());
				profile.setScreenName(fbUser.getName());
				profile.setType(Profile.FACEBOOK);
				profile.setToken(accessToken.getToken());
				profile.setTokenSecret("");
				profile.setAvatar(fbUser.getPicture().getURL().toString());
				profile.setTokenExpiresIn(accessToken.getExpires());
				profile.setTokenDate(tokenDate.getTimeInMillis() / 1000L);
				profile.setTurn('F');
			}
			
			List<Account> accounts = new FacebookAPI().getAccounts(facebook);
				
			for(Account account : accounts) {

				try {

					if(!account.getCategory().equals("Application")) {

						// Verify if an Instagram profile is connected to this page.
						String instagramBussinessAccountId = new InstagramAPI().getInstagramBussinessAccountId(account, facebook);
						
						if(instagramBussinessAccountId != null) {
							
							br.com.opsocial.server.utils.instagram.entities.User instagramUser = new InstagramAPI().getUser(instagramBussinessAccountId, facebook);
							
							// Verify if this Instagram Profile is already inserted on this account.
							Profile baseInstagramProfile = remoteProfile.getEntityByNetworkId(instagramUser.getIgId().toString(), user.getAccount().getIdAccount());
							
							if(baseInstagramProfile == null) {

								Profile profile = new Profile();
								profile.setNetworkId(instagramUser.getIgId().toString());
								profile.setScreenName(instagramUser.getUsername());
								profile.setType(Profile.INSTAGRAM); 
								profile.setActive(Profile.ACTIVE);
								profile.setAccount(user.getAccount());
								profile.setToken(accessToken.getToken()); 
								profile.setTokenSecret("");
								profile.setAvatar(instagramUser.getProfilePictureUrl());
								profile.setTurn('F');
								profile.setIsSchedulingAuthenticated(Profile.INACTIVE);
								profile.setIsTokenValid(Profile.VALID_TOKEN);
								profile.setInstagramBusinessId(instagramUser.getId());
								
								if(baseProfile == null) {
									profile.setTokenExpiresIn(accessToken.getExpires());
									profile.setTokenDate(tokenDate.getTimeInMillis() / 1000L);
									profile.setProfileInformation(fbUser.getId() + "♪" + fbUser.getName());
								} else {
									profile.setTokenExpiresIn(baseProfile.getTokenExpiresIn());
									profile.setTokenDate(baseProfile.getTokenDate());
									profile.setParentProfile(baseProfile);
								}
								
								profilesToAdd.add(profile);
							}
						}
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			session.setAttribute("alr_rec_instagram", new Boolean(true));
			session.setAttribute("profile_instagram", profilesToAdd);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void refreshToken(Profile profile, HttpSession session) {

		try {

			List<Profile> pagesToRefreshToken = new ArrayList<Profile>();

			if(baseProfile != null) {

				if(profile.getIdProfile().equals(baseProfile.getIdProfile())) {

					baseProfile.setActive('T');
					baseProfile.setToken(accessToken.getToken());
					baseProfile.setTokenExpiresIn(accessToken.getExpires());
					baseProfile.setTokenDate(tokenDate.getTimeInMillis() / 1000L);
					baseProfile.setIsTokenValid('T');

					baseProfile = remoteProfile.save(baseProfile);

					profile = baseProfile;

					List<Account> accounts = new FacebookAPI().getAccounts(facebook);

					for(Profile pageProfile : remoteProfile.getChildrenByProfile(baseProfile.getIdProfile())) {
						for(Account account : accounts) {
							if(!account.getCategory().equals("Application")) {
								if(account.getId().equals(pageProfile.getNetworkId())) {

									pageProfile.setActive('T');
									pageProfile.setToken(account.getAccessToken());
									pageProfile.setTokenSecret(accessToken.getToken());
									pageProfile.setTokenExpiresIn(baseProfile.getTokenExpiresIn());
									pageProfile.setTokenDate(baseProfile.getTokenDate());
									pageProfile.setIsTokenValid('T');

									remoteProfile.save(pageProfile);

									pagesToRefreshToken.add(remoteProfile.getById(pageProfile.getIdProfile()));

									break;
								}
							}
						}
					}

				} else {
					session.setAttribute("message_facebook", OpSocialBackApplication.messageByLocaleService.getMessage("profile.reconnect.profileinvalid", new Object[]{"Facebook"}));
				}
			} else {

				if(fbUser.getId().equals(profile.getNetworkId())) {

					List<Profile> profilesPage = remoteProfile.getEntitiesByProfileInformation(profile.getNetworkId(), user.getAccount().getIdAccount());

					if(!profilesPage.isEmpty()) {

						List<Account> accounts = new FacebookAPI().getAccounts(facebook);

						for(Profile pageProfile : profilesPage) {

							for(Account account : accounts) {
								if(!account.getCategory().equals("Application")) {
									if(account.getId().equals(pageProfile.getNetworkId())) {

										pageProfile.setActive('T');
										pageProfile.setToken(account.getAccessToken());
										pageProfile.setTokenSecret(accessToken.getToken());
										pageProfile.setTokenExpiresIn(accessToken.getExpires());
										pageProfile.setTokenDate(tokenDate.getTimeInMillis() / 1000L);

										remoteProfile.save(pageProfile);

										pagesToRefreshToken.add(remoteProfile.getById(pageProfile.getIdProfile()));

										break;
									}
								}
							}
						}

						profile.setToken(accessToken.getToken());

					} else {
						session.setAttribute("message_facebook", OpSocialBackApplication.messageByLocaleService.getMessage("profile.reconnect.profileinvalid", new Object[]{"Facebook"}));
					}
				} else {
					session.setAttribute("message_facebook", OpSocialBackApplication.messageByLocaleService.getMessage("profile.reconnect.profileinvalid", new Object[]{"Facebook"}));
				}
			}

			ReportGenerateVerification.removeRefreshTokenState(pagesToRefreshToken);

			session.setAttribute("alr_rec_facebook", new Boolean(true));
			session.setAttribute("profile_facebook", profile);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FacebookException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
