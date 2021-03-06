package br.com.opsocial.server.actions.feeds.instagram;

import java.util.List;

import javax.persistence.OptimisticLockException;

import org.jinstagram.Instagram;
import org.jinstagram.exceptions.InstagramBadRequestException;
import org.jinstagram.model.Relationship;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.client.entity.primitive.Long;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.services.ServicesImpl;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.networksintegrations.InstagramIntegration;
import br.com.opsocial.server.utils.tasks.ValidateTokensController;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;

@RestController
@RequestMapping("woopsocial")
public class ChangeRelationshipAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/change_relationship",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		StringUtil relationship = (StringUtil) getParameters().get("relationship");
		Long idUser = (Long) getParameters().get("id_user");
		Profile profileDTO = (Profile) getParameters().get("profile");
		
		Profile profile = null;
		
		if(profileDTO != null) {
			profile = MountDTO.mountProfile(profileDTO);
		} else {

			MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
	
			List<Profile> profiles = remote.getEntityByNetworkType(Profile.INSTAGRAM, getUser().getAccount().getIdAccount());
			
			if(!profiles.isEmpty()) {
				profile = profiles.get(0);
			}
		}
		
		if(profile != null) {	
		
			try {
				InstagramIntegration instagramIntegration = new InstagramIntegration();
				
				Instagram instagram = instagramIntegration.getInstagram(profile);
				
				if(relationship.getValue().equals("follow")) {
					instagram.setUserRelationship(idUser.getValue().toString(), Relationship.FOLLOW);
				} else if(relationship.getValue().equals("unfollow")) {
					instagram.setUserRelationship(idUser.getValue().toString(), Relationship.UNFOLLOW);
				}
			} catch (InstagramBadRequestException e) {
				ValidateTokensController.validatingTokens(profile.getAccount());
				e.printStackTrace();
			}
		}
	}
}
