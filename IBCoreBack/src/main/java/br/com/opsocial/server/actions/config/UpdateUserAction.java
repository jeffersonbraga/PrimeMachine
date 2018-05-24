package br.com.opsocial.server.actions.config;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class UpdateUserAction extends ServerAction {
	
	@CrossOrigin
	@RequestMapping(value = "/update_user",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doAction(@Valid @RequestBody User user, HttpServletRequest request, HttpSession session) {
	
		List<Object> l = new ArrayList<Object>();
		
		String message = null;
		
		try {
			
			MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
			MaintenanceAccountRemote accountRemote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
			
			User userSession =  SecurityUtils.getCurrentUser(); 
			User userDb = remote.getById(userSession.getId());

			if(!userSession.getPassword().equals(user.getPassword())) {
				String password = user.getPassword();
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(password);
				user.setPassword(hashedPassword);
			}
			
			Account account = user.getAccount();
			
			accountRemote.save(account);
			
			//remote.setIdUserSession(userSession.getId());
			
			if (!userSession.getEmail().equals(user.getEmail())) {
				message = verifyUsers(user.getEmail());
			}
			
			if(message == null) {
				if(userSession.getId().equals(user.getId())){
					//Previne injeção de dados
					userDb.setPassword(user.getPassword());					
					userDb.setName(user.getName());
					userDb.setEmail(user.getEmail());
					userDb.setAvatar(user.getAvatar());
					userDb.setType(user.getType());
					
					remote.save(userDb);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (message == null) {
			l.add(user);
			
			return new ResponseEntity<>(l, HttpStatus.OK);
		} else {
			
			l.add(message);
			
			return new ResponseEntity<>(l, HttpStatus.OK);
		}
	}
	
	private String verifyUsers(String email) {

		MaintenanceUserRemote userRemote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
		
		if(userRemote.getByEmail(email) == null) {
		
 			List<User> users = userRemote.listActiveUsersByAccount(SecurityUtils.getCurrentUser().getAccount().getIdAccount());
			Integer activeUsersCount = users.size();
			
			if(activeUsersCount >= SecurityUtils.getCurrentUser().getAccount().getUsers()) {
				return OpSocialBackApplication.messageByLocaleService.getMessage("user.insert.planlimit", new Object[]{SecurityUtils.getCurrentUser().getAccount().getUsers()});
			}
			
		} else {
			return OpSocialBackApplication.messageByLocaleService.getMessage("user.insert.existingemail");
		}
		
		return null;
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub
		
	}
}
