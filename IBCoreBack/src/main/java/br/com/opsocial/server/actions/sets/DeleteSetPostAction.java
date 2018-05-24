package br.com.opsocial.server.actions.sets;

import java.util.HashMap;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.services.ServerAction;
//import br.com.opsocial.server.utils.RecoverMaintenance;
//import br.com.opsocial.ejb.das.MaintenancePostRemote;
//import br.com.opsocial.ejb.entity.sets.Post;

@RestController
@RequestMapping("woopsocial")
public class DeleteSetPostAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/delete_set_post",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception {
	
		//Post postDTO = (Post) getParameters().get("post");
		
		//setParameters(new HashMap<String, Object>());

		//MaintenancePostRemote remote = (MaintenancePostRemote) RecoverMaintenance.recoverMaintenance("Post");

		//Post post = MountDTO.mountPost(postDTO, postDTO.getSet());
		//remote.delete(post);	
	}
}
