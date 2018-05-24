package br.com.opsocial.server.actions.sets;

import java.util.HashMap;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.services.ServerAction;
//import br.com.opsocial.server.utils.RecoverMaintenance;
//import br.com.opsocial.server.utils.UtilFunctions;
//import br.com.opsocial.ejb.das.MaintenancePostRemote;
//import br.com.opsocial.ejb.entity.facebook.FacebookPost;
//import br.com.opsocial.ejb.entity.sets.Post;
//import br.com.opsocial.ejb.entity.sets.Set;

@RestController
@RequestMapping("woopsocial")
public class AddPostAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/add_post",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {

		/*Post postDTO = (Post) getParameters().get("post");		
		Character imageType = (Character) getParameters().get("imageType");
		Integer postIndex = (Integer) getParameters().get("postIndex"); 
		
		setParameters(new HashMap<String, Object>());
		
		MaintenancePostRemote remote = (MaintenancePostRemote) RecoverMaintenance.recoverMaintenance("Post");
				
		if(imageType != null) {
			if(imageType.equals(FacebookPost.FILTERED_PICTURE)) {
				postDTO.setPicture(UtilFunctions.createFilteredPicture(postDTO.getPicture()));
			}	
		} 

		Set setDTO = postDTO.getSet();
		
		Post post  = remote.save(MountDTO.mountPost(postDTO, MountDTO.mountSet(setDTO)));
		post.setFormattedPost(UtilFunctions.applyFormattingInSetPosts(post.getText()));
		
		if(postIndex != null) {
			setDTO.getPosts().add(postIndex, MountDTO.mountPost(setDTO, post));
		} else {
			setDTO.getPosts().add(MountDTO.mountPost(setDTO, post));
		}
		
		String avatar = setDTO.getProfile().getAvatar();
		setDTO.getProfile().setAvatar(avatar);
		
		getParameters().put("updatedset", setDTO);*/
	}
}
