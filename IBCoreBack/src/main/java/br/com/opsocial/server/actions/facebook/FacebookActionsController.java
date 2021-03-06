package br.com.opsocial.server.actions.facebook;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.facebook.FacebookAPI;
import br.com.opsocial.server.utils.streams.ActionLikeAux;

@RestController
@RequestMapping("woopsocial")
public class FacebookActionsController {

	@CrossOrigin
	@RequestMapping(value = "/facebook/like",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getMyPosts(@RequestBody ActionLikeAux actionLikeAux) throws Exception {
		
		ResponseEntity<Object> responseEntity;
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		Profile profile = profileRemote.getById(actionLikeAux.getIdProfile());
		
		FacebookAPI facebookGraphAPI = new FacebookAPI();
		
		try {
			
			facebookGraphAPI.likePost(actionLikeAux.getStreamPost().getPostId(), profile.getToken());

			responseEntity = new ResponseEntity<Object>(HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<Object>(HttpStatus.FORBIDDEN);
		}
		
		return responseEntity;
	}
	
}
