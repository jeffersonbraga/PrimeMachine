package br.com.opsocial.server.actions.home;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.DocumentException;

import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class LoginAction extends ServerAction {

	public ResponseEntity<User> doAction(String login, String password) throws Exception, DocumentException {

		User user = null; 

		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");

		user = remote.getByLoginPassword(login, password);
		
		if(user != null) {
			//TODO criptografar login
			//TODO user.setSessionId(asklas);
			//session.setAttribute("user", user);

			return new ResponseEntity<>(user, HttpStatus.OK);

		} else {
			return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
		}
	}

	public void doAction() throws Exception, DocumentException {

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/authenticate",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Boolean> checkIsAuthenticated() {

		return new ResponseEntity<>(SecurityUtils.isAuthenticated(), HttpStatus.OK);
	}
}