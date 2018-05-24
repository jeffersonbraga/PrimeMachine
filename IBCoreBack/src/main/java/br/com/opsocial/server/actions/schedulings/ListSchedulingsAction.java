package br.com.opsocial.server.actions.schedulings;

import java.util.ArrayList;
import java.util.HashMap;
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

import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class ListSchedulingsAction extends ServerAction {
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/list_schedulings",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doAction(Long idaccount, HttpSession session) throws Exception {
		//User user = SecurityUtils.getCurrentUser();
		//Long idaccount = user.getAccount().getIdAccount();
		
		List<Object> l = new ArrayList<Object>();
		
		setParameters(new HashMap<String, Object>());
		
		MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		
		// Facebook profiles
		
		List<Character> profileTypes = new ArrayList<Character>();
		profileTypes.add(Profile.FACEBOOK_PAGE);
		profileTypes.add(Profile.FACEBOOK);

		List<Profile> facebookProfiles = remote.getEntityByNetworkType(profileTypes, idaccount);

		List<Profile> facebookProfilesDTOs = new ArrayList<Profile>();

		for(Profile profile : facebookProfiles) {
			facebookProfilesDTOs.add(profile);
		}

		//getParameters().put("facebookprofiles", facebookProfilesDTOs);
		l.add(facebookProfilesDTOs);

		// Twitter profiles

		profileTypes = new ArrayList<Character>();
		profileTypes.add(Profile.TWITTER);

		List<Profile> twitterProfiles = remote.getEntityByNetworkType(profileTypes, idaccount);

		List<Profile> twitterProfilesDTOs = new ArrayList<Profile>();

		for(Profile profile : twitterProfiles) {
			twitterProfilesDTOs.add(profile);
		}

		//getParameters().put("twitterprofiles", twitterProfilesDTOs);
		l.add(twitterProfilesDTOs);

		// Instagram profiles

		profileTypes = new ArrayList<Character>();
		profileTypes.add(Profile.INSTAGRAM);

		List<Profile> instagramProfiles = remote.getEntityByNetworkType(profileTypes, idaccount);

		List<Profile> instagramProfilesDTOs = new ArrayList<Profile>();

		for(Profile profile : instagramProfiles) {
			instagramProfilesDTOs.add(profile);
		}

		//getParameters().put("instagramprofiles", instagramProfilesDTOs);
		l.add(instagramProfilesDTOs);
		
		return new ResponseEntity<>(l, HttpStatus.OK);
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub
		
	}
}
