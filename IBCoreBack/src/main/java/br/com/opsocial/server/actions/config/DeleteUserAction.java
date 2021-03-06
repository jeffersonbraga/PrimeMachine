package br.com.opsocial.server.actions.config;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class DeleteUserAction extends ServerAction {
	
	@CrossOrigin
	@RequestMapping(value = "/delete_user",
    method = RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction(Long idUser, HttpSession session, HttpServletRequest request) throws Exception, OptimisticLockException {

		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");

		User user = remote.getById(idUser);
		User userSession = SecurityUtils.getCurrentUser();	

		if(userSession.getAccount().getIdAccount() == user.getAccount().getIdAccount() && userSession.getType() == 'A'){
			remote.setIdUserSession(userSession.getId());
			remote.delete(user);
			session.setAttribute("user", null);
		}
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub
		
	}
}
