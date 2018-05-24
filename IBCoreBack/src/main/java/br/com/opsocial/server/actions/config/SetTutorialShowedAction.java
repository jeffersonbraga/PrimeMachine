package br.com.opsocial.server.actions.config;

import javax.persistence.OptimisticLockException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class SetTutorialShowedAction {

	@CrossOrigin
	@RequestMapping(value = "/set_tutorial_showed",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> doAction() throws Exception, OptimisticLockException {

		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");

		User user = SecurityUtils.getCurrentUser();
			
		user.setTutorialShowed('T');
		remote.save(user);
		
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
