package br.com.opsocial.server.actions.home;

import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class LogoutAction extends ServerAction {

	@CrossOrigin
	@RequestMapping(value = "/logout",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction(HttpSession session) throws Exception {
 		
		User user = SecurityUtils.getCurrentUser();
		
		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
		
		if(user != null) {
			user = remote.getById(user.getIdUser());
			user.setSessionId(null);
			
			remote.save(user);
		}
		
		SecurityUtils.getCurrentUserLogOut();
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {}
}
