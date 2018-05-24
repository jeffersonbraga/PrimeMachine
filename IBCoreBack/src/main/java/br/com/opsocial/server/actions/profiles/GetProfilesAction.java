package br.com.opsocial.server.actions.profiles;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class GetProfilesAction {

    //@Secured(OpSocialBackApplication.CREDENTIAL_ADMINSTRATOR)
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/get_profiles",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Profile>> getProfiles() throws Exception {

		MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		List<Profile> profileDTOs = new ArrayList<Profile>();

		User user = SecurityUtils.getCurrentUser();
		if (user != null) {
			List<Character> types = new ArrayList<Character>();
			types.add(Profile.FACEBOOK);
			types.add(Profile.FACEBOOK_PAGE);
			types.add(Profile.TWITTER);
			types.add(Profile.INSTAGRAM);
			types.add(Profile.LINKEDIN);
			types.add(Profile.LINKEDIN_PAGE);

			List<Profile> profiles = remote.getEntityByNetworkType(types, new Long(user.getAccount().getIdAccount()));

			// Carregar primeiro Páginas/Perfis Facebook.
			for(Profile profile : profiles) {
				if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.FACEBOOK)) {
					profileDTOs.add(profile);
				}
			}

			// Carregar restante das redes.
			for(Profile profile : profiles) {
				if(!profile.getType().equals(Profile.FACEBOOK_PAGE) && !profile.getType().equals(Profile.FACEBOOK)) {
	//				if(profile.getType().equals(Profile.INSTAGRAM)) {
	//					if(profile.getIsSchedulingAuthenticated() == Profile.ACTIVE) {
	//						profileDTOs.add(profile);
	//					}
	//				} else {
						profileDTOs.add(profile);
	//				}
				}			
			}
		}
		return new ResponseEntity<>(profileDTOs, HttpStatus.OK);
	}
}