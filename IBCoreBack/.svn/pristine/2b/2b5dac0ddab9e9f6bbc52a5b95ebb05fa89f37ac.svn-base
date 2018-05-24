package br.com.opsocial.server.actions.profiles;

import java.util.UUID;

import javax.persistence.OptimisticLockException;

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
import br.com.opsocial.ejb.das.MaintenanceInstagramDeviceRemote;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.InternalAnalytic;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.ProxyRegion;
import br.com.opsocial.ejb.entity.instagram.InstagramDevice;
import br.com.opsocial.ejb.entity.instagram.InstagramProfileDevice;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.instagram.InstagramPostSender;

@RestController
@RequestMapping("woopsocial")
public class InsertInstagramSchedulingAction {
	
	@CrossOrigin
	@RequestMapping(value = "/authenticate-instagram-scheduling",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> authenticateInstagramScheduling(@RequestBody Profile profileToEdit) throws Exception, OptimisticLockException {
		
		Boolean isAuthenticated;
		ResponseEntity<Object> responseEntity;
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote)
				RecoverMaintenance.recoverMaintenance("Profile");
		
		Profile profile = profileRemote.getById(profileToEdit.getIdProfile());
		
		InstagramProfileDevice instagramProfileDevice = UtilFunctions.getInstagramProfileDevice(profile, profile.getAccount());
		
		profile = profileRemote.getById(profileToEdit.getIdProfile());
		profile.setInstagramGUID(instagramProfileDevice.getInstagramGUID());
		profile.setInstagramDeviceId(instagramProfileDevice.getInstagramDeviceId());
		profile.setInstagramPhoneId(instagramProfileDevice.getInstagramPhoneId());
		profile.setInstagramDevice(instagramProfileDevice.getInstagramDevice());
		profile.setScreenName(profileToEdit.getScreenName());
		profile.setInstagramPassword(profileToEdit.getInstagramPassword());
		
		InstagramPostSender instagramPostHelper = new InstagramPostSender(profile.getInstagramCookies(), profile.getInstagramLastLoginTime(), 
				instagramProfileDevice.getProxyRegion(), instagramProfileDevice.getInstagramDevice(), instagramProfileDevice.getPort());
		isAuthenticated = instagramPostHelper.authenticateUser(profile, true);
		
		if(isAuthenticated) {
			
			profile.setIsSchedulingAuthenticated(Profile.ACTIVE);
			profile.setInstagramCookies(instagramPostHelper.getCookies());
			profile.setInstagramLastLoginTime(instagramPostHelper.getLastLoginTime());
			
			profileRemote.save(profile);
			profile = profileRemote.getById(profile.getIdProfile());
			
			UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.ADD_INSTAGRAM_SCHEDULING_OK, profile.getAccount().getIdAccount());
			
			responseEntity = new ResponseEntity<Object>(profile, HttpStatus.OK);
		} else {
			
			String errorLoginMessage = instagramPostHelper.extractLoginErrorMessage();
			
			if(errorLoginMessage.equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.checkpointrequired"))) {
				
				profile = profileRemote.getById(profileToEdit.getIdProfile());
				
				profile.setAuthenticationProfileCount(profile.getAuthenticationProfileCount() + 1L); 
				profileRemote.save(profile);
			}
			
			JsonObject response = new JsonObject();
			response.addProperty("error", errorLoginMessage);
			
			UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.ADD_INSTAGRAM_SCHEDULING_NOT_OK, profile.getAccount().getIdAccount());
			
			responseEntity = new ResponseEntity<Object>(response.toString(), HttpStatus.FORBIDDEN);
		}
		
		return responseEntity;
	}

	@CrossOrigin
	@RequestMapping(value = "/edit-instagram-scheduling",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> editInstagramScheduling(@RequestBody Profile profileToEdit) throws Exception, OptimisticLockException {
		
		Boolean isAuthenticated;
		ResponseEntity<Object> responseEntity;
		
		MaintenanceInstagramDeviceRemote instagramDeviceRemote = (MaintenanceInstagramDeviceRemote) 
				RecoverMaintenance.recoverMaintenance("InstagramDevice");
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote)
				RecoverMaintenance.recoverMaintenance("Profile");
		
		Profile profile = profileRemote.getById(profileToEdit.getIdProfile());
		
		String instagramGUID;
		String instagramDeviceId;
		String instagramPhoneId;
		InstagramDevice instagramDevice;
		if(profile.getInstagramDevice() == null) {
			
			instagramGUID = UUID.randomUUID().toString();
			instagramPhoneId = UUID.randomUUID().toString();
			instagramDeviceId = "android-" + UtilFunctions.generateMD5Hash(profile.getScreenName() + profile.getInstagramPassword());
			
			instagramDevice = instagramDeviceRemote.getById(instagramDeviceRemote.getInstagramDeviceLessUsedId());
		} else {
			instagramGUID = profile.getInstagramGUID();
			instagramDeviceId = profile.getInstagramDeviceId();
			instagramPhoneId = profile.getInstagramPhoneId();
			instagramDevice = profile.getInstagramDevice();
		}
		
		profile = profileRemote.getById(profileToEdit.getIdProfile());
		profile.setInstagramGUID(instagramGUID);
		profile.setInstagramDeviceId(instagramDeviceId);
		profile.setInstagramPhoneId(instagramPhoneId);
		profile.setInstagramDevice(instagramDevice);
		profile.setScreenName(profileToEdit.getScreenName());
		profile.setInstagramPassword(profileToEdit.getInstagramPassword());
		
		ProxyRegion proxyRegion = UtilFunctions.getProxyRegion(profile.getAccount().getCountry(), ProxyRegion.TEN_MINUTES);
		
		InstagramPostSender instagramPostHelper = new InstagramPostSender(profile.getInstagramCookies(), profile.getInstagramLastLoginTime(),
				proxyRegion, instagramDevice, UtilFunctions.getResidentialPort(proxyRegion));
		isAuthenticated = instagramPostHelper.authenticateUser(profile, true);
		
		if(isAuthenticated) {
			
			profile.setIsSchedulingAuthenticated(Profile.ACTIVE);
			profile.setInstagramCookies(instagramPostHelper.getCookies());
			profile.setInstagramLastLoginTime(instagramPostHelper.getLastLoginTime());
			
			profileRemote.save(profile);
			profile = profileRemote.getById(profile.getIdProfile());
			
			UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.EDIT_INSTAGRAM_SCHEDULING_OK, profile.getAccount().getIdAccount());
			
			responseEntity = new ResponseEntity<Object>(profile, HttpStatus.OK);
		} else {
			
			String errorLoginMessage = instagramPostHelper.extractLoginErrorMessage();
			
			if(errorLoginMessage.equals(OpSocialBackApplication.messageByLocaleService.getMessage("profile.instagram.checkpointrequired"))) {
				
				profile = profileRemote.getById(profileToEdit.getIdProfile());
				
				profile.setAuthenticationProfileCount(profile.getAuthenticationProfileCount() + 1L); 
				profileRemote.save(profile);
			}
			
			JsonObject response = new JsonObject();
			response.addProperty("error", errorLoginMessage);
			
			UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.EDIT_INSTAGRAM_SCHEDULING_NOT_OK, profile.getAccount().getIdAccount());
			
			responseEntity = new ResponseEntity<Object>(response.toString(), HttpStatus.FORBIDDEN);
		}
		
		return responseEntity;
	}

	@CrossOrigin
	@RequestMapping(value = "/insert_instagram_scheduling",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> insertInstagramScheduling(@RequestBody Profile profileToInsert) throws Exception, OptimisticLockException {
		
		Boolean isAuthenticated;
		ResponseEntity<Object> responseEntity;
		
		Profile profile = profileToInsert;
		
		InstagramProfileDevice instagramProfileDevice = UtilFunctions.getInstagramProfileDevice(profile, SecurityUtils.getCurrentUser().getAccount());
		
		profile.setInstagramGUID(instagramProfileDevice.getInstagramGUID());
		profile.setInstagramDeviceId(instagramProfileDevice.getInstagramDeviceId());
		profile.setInstagramPhoneId(instagramProfileDevice.getInstagramPhoneId());
		profile.setInstagramDevice(instagramProfileDevice.getInstagramDevice());
		profile.setScreenName(profileToInsert.getScreenName());
		profile.setInstagramPassword(profileToInsert.getInstagramPassword());
		
		InstagramPostSender instagramPostHelper = new InstagramPostSender(profile.getInstagramCookies(), profile.getInstagramLastLoginTime(),  
				instagramProfileDevice.getProxyRegion(), instagramProfileDevice.getInstagramDevice(), instagramProfileDevice.getPort());
		isAuthenticated = instagramPostHelper.authenticateUser(profile, true);
		
		if(isAuthenticated) {
			
			profile.setIsSchedulingAuthenticated(Profile.ACTIVE);
			profile.setInstagramCookies(instagramPostHelper.getCookies());
			profile.setInstagramLastLoginTime(instagramPostHelper.getLastLoginTime());
			
			UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.ADD_INSTAGRAM_SCHEDULING_OK, SecurityUtils.getCurrentUser().getAccount().getIdAccount());
		}
		
		if(isAuthenticated) {
			responseEntity = new ResponseEntity<Object>(profile, HttpStatus.OK);
		} else {
			
			JsonObject response = new JsonObject();
			response.addProperty("error", instagramPostHelper.extractLoginErrorMessage());
			
			UtilFunctions.addInternalAnalytic(InternalAnalytic.CHANNELS_MODULE, InternalAnalytic.ADD_INSTAGRAM_SCHEDULING_NOT_OK, SecurityUtils.getCurrentUser().getAccount().getIdAccount());
			
			responseEntity = new ResponseEntity<Object>(response.toString(), HttpStatus.FORBIDDEN);
		}
		
		return responseEntity;
	}

}
