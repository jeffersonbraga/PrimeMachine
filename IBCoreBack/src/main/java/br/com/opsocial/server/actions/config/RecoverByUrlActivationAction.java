package br.com.opsocial.server.actions.config;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class RecoverByUrlActivationAction extends ServerAction{

	private User user;

	@CrossOrigin
	@RequestMapping(value = "/get_activation",
	method = RequestMethod.GET,
	produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doAction(String url) throws Exception, OptimisticLockException {

		List<Object> l = new ArrayList<Object>();

		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");

		user = remote.getByUrlActivation(url);

		if(user == null) {
			l.add(OpSocialBackApplication.messageByLocaleService.getMessage("user.activation.urlnotfound"));
		}else{
			l.add(user);			
		}

		return new ResponseEntity<>(l, HttpStatus.OK);
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub

	}
}
