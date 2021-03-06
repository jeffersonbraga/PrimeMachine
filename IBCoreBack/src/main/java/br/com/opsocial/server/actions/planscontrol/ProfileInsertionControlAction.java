package br.com.opsocial.server.actions.planscontrol;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class ProfileInsertionControlAction {
	
	@CrossOrigin
	@RequestMapping(value = "/profile_insertion_control",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> verifyProfilesPlanLimit(Integer profilesCount, HttpSession session) throws Exception, OptimisticLockException {
		
		User user = SecurityUtils.getCurrentUser();
		Integer profilesToAdd = profilesCount;
		
		Boolean canInsertMoreProfiles = true;
		String profileControlMessage = "";
	
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		
		List<Character> profileTypes = new ArrayList<Character>();
		profileTypes.add(Profile.FACEBOOK_PAGE);
		profileTypes.add(Profile.TWITTER);
		profileTypes.add(Profile.FACEBOOK);
		profileTypes.add(Profile.INSTAGRAM);
		
		List<Profile> profiles = profileRemote.getEntityByNetworkType(profileTypes, user.getAccount().getIdAccount());
		
		if(user.getAccount().getPlanType().equals(Account.FREEMIUM) && profiles.size() + profilesToAdd > 5) {
			profileControlMessage = OpSocialBackApplication.messageByLocaleService.getMessage("profile.insert.freeplanlimit");
			canInsertMoreProfiles = false;
			
		} else if(profiles.size() + profilesToAdd > user.getAccount().getProfiles()) {
			profileControlMessage = OpSocialBackApplication.messageByLocaleService.getMessage("profile.insert.planlimit", new Object[]{user.getAccount().getProfiles()});
			canInsertMoreProfiles = false;	
		}
		
		JsonObject response = new JsonObject();
		response.addProperty("profileControlMessage", profileControlMessage);
		response.addProperty("canInsertMoreProfiles", canInsertMoreProfiles);
		
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}

}
