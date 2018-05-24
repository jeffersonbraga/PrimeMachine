package br.com.opsocial.server.actions.profiles;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;

@RestController
@RequestMapping("woopsocial")
public class RedefinePasswordAction extends ServerAction {
	
	@RequestMapping(value = "/redefine_password",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction(HttpSession session) throws Exception, OptimisticLockException {
		
		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");

		StringUtil newPassword = (StringUtil) getParameters().get("newpassword");
		getUser().setPassword(newPassword.getValue());
		
		setUser(remote.save(getUser()));
		
		session.setAttribute("user", getUser());
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {}
}
