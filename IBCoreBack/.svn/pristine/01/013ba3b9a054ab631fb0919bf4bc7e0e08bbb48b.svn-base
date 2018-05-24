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
public class AuthFacebookCallbackServlet {

	private String facebookAuthAction;
	
	private HttpSession session;

	private facebook4j.User fbUser;
	private AccessToken accessToken;
	private Facebook facebook;
	private Calendar tokenDate;

	private MaintenanceProfileRemote remoteProfile;
	private User user;

	private Profile baseProfile;

	public static final String GET_ACCESS_TOKEN = "getAccessToken";
	public static final String REFRESH_TOKEN = "refreshToken";
	public static final String CHANGE_ADMIN_TOKEN = "changeAdminToken";
	public static final String REAUTH_TOKEN = "reauthToken";
	public static final String GET_GROUPS = "getGroups";
	
	public static final String ME_FIELDS = "id,name,picture";

	@CrossOrigin
	@RequestMapping(value = "/authFacebookCallback",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	protected void doAction(String code, HttpSession session) {
		
		try {

			Facebook systemFacebook = (Facebook) session.getAttribute("facebookSystem");
			Profile reauthProfile = (Profile) session.getAttribute("reauth");

			if(code != null) {
				
				accessToken = systemFacebook.getOAuthAccessToken(code);
				tokenDate = new GregorianCalendar();
				tokenDate.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

				facebook = new FacebookIntegration().getFacebook(accessToken);

				fbUser = facebook.getMe(new Reading().fields(ME_FIELDS));

				remoteProfile = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");

				user = SecurityUtils.getCurrentUser();

				baseProfile = remoteProfile.getEntityByNetworkId(fbUser.getId(), user.getAccount().getIdAccount());

				facebookAuthAction = (String) session.getAttribute("facebook_auth_action");

				if(facebookAuthAction.equals(REAUTH_TOKEN)) {
					reauthToken(reauthProfile);
				} else if(facebookAuthAction.equals(GET_ACCESS_TOKEN)) {
					getAccessToken(session);
				} else if(facebookAuthAction.equals(REFRESH_TOKEN)) {
					Profile profileToReconnect = (Profile) session.getAttribute("profile_facebook_reconnect");
					refreshToken(profileToReconnect, session);
				} else if(facebookAuthAction.equals(CHANGE_ADMIN_TOKEN)) {
					changeAdminPages();
				}
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FacebookException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void reauthToken(Profile reauthProfile) {

		if(reauthProfile != null) {

			try {
				session.removeAttribute("reauth");

				if(reauthProfile.getNetworkId().equals(fbUser.getId())) {

					Profile profile = remoteProfile.getEntityByNetworkId(reauthProfile.getNetworkId(), user.getAccount().getIdAccount());

					if(profile != null) {
						
						profile.setToken(accessToken.getToken());
						remoteProfile.save(profile);
						
					} else {
						
						List<Profile> pages = remoteProfile.getEntitiesByProfileInformation(
								reauthProfile.getNetworkId(), user.getAccount().getIdAccount());

						List<Account> accounts = new FacebookAPI().getAccounts(facebook);

						for(Profile page : pages) {

							for(Account account : accounts) {

								if(!account.getCategory().equals("Application")) {
									if(page.getNetworkId().equals(account.getId())) {
										
										page.setToken(account.getAccessToken());
										page.setTokenSecret(accessToken.getToken());
										remoteProfile.save(page);

										break;
									}			
								}
							}
						}	
					}						
				}

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (FacebookException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}  
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

				profilesToAdd.add(profile);
			}
			
			List<Account> accounts = new FacebookAPI().getAccounts(facebook);
				
			for(Account account : accounts) {

				try {

					if(!account.getCategory().equals("Application")) {

						Profile basePageProfile = remoteProfile.getEntityByNetworkId(account.getId(), user.getAccount().getIdAccount());

						if(basePageProfile == null) {

							Profile page = new Profile();
							page.setNetworkId(account.getId());
							page.setScreenName(account.getName());
							page.setType(Profile.FACEBOOK_PAGE);
							page.setToken(account.getAccessToken());
							page.setTokenSecret(accessToken.getToken());
							page.setAvatar("http://graph.facebook.com/v2.10/" + account.getId() +"/picture");
							page.setTurn('F');

							if(baseProfile == null) {
								page.setTokenExpiresIn(accessToken.getExpires());
								page.setTokenDate(tokenDate.getTimeInMillis() / 1000L);
								page.setProfileInformation(fbUser.getId()+"♪"+fbUser.getName());
							} else {
								page.setTokenExpiresIn(baseProfile.getTokenExpiresIn());
								page.setTokenDate(baseProfile.getTokenDate());
								page.setParentProfile(baseProfile);
							}

							page.setAccount(user.getAccount());

							profilesToAdd.add(page);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			session.setAttribute("alr_rec_facebook", new Boolean(true));
			session.setAttribute("profile_facebook", profilesToAdd);

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
								
								if(pageProfile.getType().equals(Profile.INSTAGRAM)) {
									
									// Verify if an Instagram profile is connected to this page.
									String instagramBussinessAccountId = new InstagramAPI().getInstagramBussinessAccountId(account, facebook);
									
									if(instagramBussinessAccountId != null) {
										
										br.com.opsocial.server.utils.instagram.entities.User instagramUser = new InstagramAPI().getUser(instagramBussinessAccountId, facebook);
										
										if(instagramUser.getIgId().toString().equals(pageProfile.getNetworkId())) {
											
											pageProfile.setActive('T');
											pageProfile.setIsTokenValid(Profile.VALID_TOKEN);
											pageProfile.setToken(accessToken.getToken());
											pageProfile.setTokenSecret("");
											pageProfile.setTokenExpiresIn(accessToken.getExpires());
											pageProfile.setTokenDate(tokenDate.getTimeInMillis() / 1000L);

											remoteProfile.save(pageProfile);
											
											pagesToRefreshToken.add(remoteProfile.getById(pageProfile.getIdProfile()));
										}
									}
									
								} else {
									
									if(account.getId().equals(pageProfile.getNetworkId())) {

										pageProfile.setActive('T');
										pageProfile.setIsTokenValid(Profile.VALID_TOKEN);
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
									
									if(pageProfile.getType().equals(Profile.INSTAGRAM)) {
										
										// Verify if an Instagram profile is connected to this page.
										String instagramBussinessAccountId = new InstagramAPI().getInstagramBussinessAccountId(account, facebook);
										
										if(instagramBussinessAccountId != null) {
											
											br.com.opsocial.server.utils.instagram.entities.User instagramUser = new InstagramAPI().getUser(instagramBussinessAccountId, facebook);
											
											if(instagramUser.getIgId().toString().equals(pageProfile.getNetworkId())) {
												
												pageProfile.setActive('T');
												pageProfile.setIsTokenValid(Profile.VALID_TOKEN);
												pageProfile.setToken(accessToken.getToken());
												pageProfile.setTokenSecret("");
												pageProfile.setTokenExpiresIn(accessToken.getExpires());
												pageProfile.setTokenDate(tokenDate.getTimeInMillis() / 1000L);

												remoteProfile.save(pageProfile);
												
												pagesToRefreshToken.add(remoteProfile.getById(pageProfile.getIdProfile()));
											}
										}
										
									} else {
										
										if(account.getId().equals(pageProfile.getNetworkId())) {

											pageProfile.setActive('T');
											pageProfile.setIsTokenValid(Profile.VALID_TOKEN);
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

	private void changeAdminPages() {

		try {

			List<Profile> profiles = ((List<Profile>) session.getAttribute("profiles"));
			List<Profile> profilesNotFound = new ArrayList<Profile>();
			List<Profile> pagesToRefreshToken = new ArrayList<Profile>();

			if(baseProfile != null) {

				baseProfile.setActive('T');
				baseProfile.setToken(accessToken.getToken());
				baseProfile.setTokenExpiresIn(accessToken.getExpires());
				baseProfile.setTokenDate(tokenDate.getTimeInMillis() / 1000L);

				baseProfile = remoteProfile.save(baseProfile);

				List<Account> accounts = new FacebookAPI().getAccounts(facebook);

				for(Profile pageProfile : profiles) {

					Profile profile = pageProfile;
					boolean wasFound = false;

					for(Account account : accounts) {
						if(!account.getCategory().equals("Application")) {
							if(account.getId().equals(pageProfile.getNetworkId())) {

								profile = remoteProfile.getById(profile.getIdProfile());

								profile.setActive('T');
								profile.setToken(account.getAccessToken());
								profile.setTokenSecret(accessToken.getToken());
								profile.setTokenExpiresIn(baseProfile.getTokenExpiresIn());
								profile.setTokenDate(baseProfile.getTokenDate());
								profile.setParentProfile(baseProfile);
								profile.setProfileInformation(null);

								remoteProfile.save(profile);

								pagesToRefreshToken.add(remoteProfile.getById(profile.getIdProfile()));

								wasFound = true;

								break;
							}
						}
					}

					if(!wasFound) {
						profilesNotFound.add(pageProfile);
					}
				}
			} else {

				List<Account> accounts = new FacebookAPI().getAccounts(facebook);

				for(Profile pageProfile : profiles) {

					boolean wasFound = false;

					for(Account account : accounts) {
						if(!account.getCategory().equals("Application")) {
							if(account.getId().equals(pageProfile.getNetworkId())) {

								pageProfile = remoteProfile.getById(pageProfile.getIdProfile());

								pageProfile.setActive('T');
								pageProfile.setToken(account.getAccessToken());
								pageProfile.setTokenSecret(accessToken.getToken());
								pageProfile.setTokenExpiresIn(accessToken.getExpires());
								pageProfile.setTokenDate(tokenDate.getTimeInMillis() / 1000L);
								pageProfile.setProfileInformation(fbUser.getId()+"♪"+fbUser.getName());
								pageProfile.setParentProfile(null);

								remoteProfile.save(pageProfile);

								pagesToRefreshToken.add(remoteProfile.getById(pageProfile.getIdProfile()));

								wasFound = true;

								break;
							}
						}
					}

					if(!wasFound) {
						profilesNotFound.add(pageProfile);
					}
				}	
			}

			ReportGenerateVerification.removeRefreshTokenState(pagesToRefreshToken);

			profiles.removeAll(profilesNotFound);

			session.setAttribute("profilesNotFound", profilesNotFound);
			session.setAttribute("profiles", profiles);
			session.setAttribute("alr_rec_facebook", new Boolean(true));

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (FacebookException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
