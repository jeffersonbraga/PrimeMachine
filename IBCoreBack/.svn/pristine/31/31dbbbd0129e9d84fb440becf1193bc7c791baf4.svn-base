package br.com.opsocial.server.actions.application;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class UnsubscribeEmailsAction {

	@CrossOrigin
	@RequestMapping(value = "/unsubscribe_emails",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> doAction(@Valid @RequestBody String email) throws Exception, OptimisticLockException {
		
		MaintenanceUserRemote userRemote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
		MaintenanceAccountRemote accountRemote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
		
		User user = userRemote.getByEmail(email);
		String message = "";
		
		if(user != null) {
			
			Account account = accountRemote.getById(user.getAccount().getIdAccount());
			account.setSubscribeEmails(false);
			
			accountRemote.save(account);
			
			message = "OK";
		}
		
		return new ResponseEntity<>(message, HttpStatus.OK);
	}
	
}
