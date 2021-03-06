package br.com.opsocial.server.actions.config;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;

@RestController
@RequestMapping("woopsocial")
public class ChangeUserTypeAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/change_user_type",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
	
		User userDTO = (User) getParameters().get("changeuser");
		
		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
		
		User user = remote.save(userDTO);
		
		user = remote.getById(user.getIdUser());
		
		getParameters().put("changeuser", user);
		
	}
}
