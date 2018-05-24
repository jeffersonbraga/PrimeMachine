package br.com.opsocial.server.actions.config;

import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class UpdateTypeUserAction {
	
	@CrossOrigin
	@RequestMapping(value = "/update_type_user",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> doAction(@Valid @RequestBody User user, HttpSession session, HttpServletRequest request) throws Exception, OptimisticLockException {

		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");

		User userSession = SecurityUtils.getCurrentUser();	 
		User userDb = remote.getById(user.getId());

		if(userSession.getAccount().getIdAccount() == user.getAccount().getIdAccount() && userSession.getType() == 'A'){
			//remote.setIdUserSession(userSession.getId());
			userDb.setType(user.getType());
			remote.save(userDb);
		}
		
		return new ResponseEntity<>(userDb, HttpStatus.OK);
	}
}
