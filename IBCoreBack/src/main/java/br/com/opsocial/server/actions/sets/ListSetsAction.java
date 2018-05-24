package br.com.opsocial.server.actions.sets;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceSetRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.ejb.entity.sets.Sets;

import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;

@RestController
@RequestMapping("woopsocial")
public class ListSetsAction extends ServerAction {
	
	@RequestMapping(value = "/list_sets",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction(HttpSession session) throws Exception, OptimisticLockException {

//		User user = SecurityUtils.getCurrentUser();
//		
//		List<Character> types = new ArrayList<Character>();
//		types.add(Profile.TWITTER);
//		
//		MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
//
//		List<Profile> profiles = remote.getEntityByNetworkType(types, user.getAccount().getIdAccount());
//
//		List<Profile> profileDTOs = new ArrayList<Profile>();
//		
//		for(Profile profile : profiles) {
//			if(profile.isActive()) {
//				profile.setAvatar(UtilFunctions.getNetworkAvatar(profile));
//			}
//			
//			profileDTOs.add(profile);
//		}
//
//		
//		MaintenanceSetRemote setRemote = (MaintenanceSetRemote) RecoverMaintenance.recoverMaintenance("Set");
//
//		List<Sets> sets = setRemote.listByAccount(user.getAccount().getIdAccount());
//
//		List<Sets> setDTOs = new ArrayList<Sets>();
//	
//		for(Sets set : sets) {
//			for(Post post : set.getPosts()) {
//				post.setFormattedPost(UtilFunctions.applyFormattingInSetPosts(post.getText()));
//			}
//			
//			setDTOs.add(set);
//		}
//		
//		getParameters().put("profiles", profileDTOs);
//		getParameters().put("sets", setDTOs);
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {}
}
