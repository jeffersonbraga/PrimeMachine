package br.com.opsocial.server.actions.config;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenanceTabRemote;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.feed.Tab;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.SendMail;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.emails.EmailSender;

@RestController
@RequestMapping("woopsocial")
public class InsertUserAction {
	
	@CrossOrigin
	@RequestMapping(value = "/insert_user",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doAction(Character type, String email, HttpServletRequest request, HttpSession session) throws Exception, OptimisticLockException {

		List<Object> l = new ArrayList<Object>();
		
		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
		MaintenanceTabRemote remoteTab = (MaintenanceTabRemote) RecoverMaintenance.recoverMaintenance("Tab");
		User user = SecurityUtils.getCurrentUser();
		User newUser = new User();
		
		String message = verifyUsers(email);
		
		if(message == null) {
	
			newUser.setActive('F');
			newUser.setCadastreDate(new Date());
			
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			String hashedPassword = passwordEncoder.encode(RandomStringUtils.random(10, true, true));
			newUser.setPassword(hashedPassword);
			
			newUser.setName(email);
			newUser.setShowInstagramAuthWindow('T');
			newUser.setShowLettersToClientsWindow('F');
			newUser.setType(type);
			newUser.setEmail(email);
			newUser.setUrlActivation(RandomStringUtils.random(50, true, true));
			newUser.setIdUserInvitation(user.getIdUser());
			newUser.setNameUserInvitation(user.getName());
			remote.setIdUserSession(user.getId());
			
			newUser.setAccount(user.getAccount());
			
			User userDB = remote.save(newUser);
			
			Tab tab = new Tab();
			
			tab.setName("My Stream");
			tab.setUser(userDB);
			remoteTab.save(tab);

//			session.setAttribute("user", newUser);
			
			sendMail(userDB);
			
			l.add(userDB);
			
			return new ResponseEntity<>(l, HttpStatus.OK);
			
		} else {
			
			l.add(message);
			
			return new ResponseEntity<>(l, HttpStatus.OK);
		}
	}

	private void sendMail(User user) throws IOException {

		String subject = "You are now part of a Woop Social work team";
		
		File fileEmail = new File(OpSocialBackApplication.EMAILS_DIR + "new-member.html");  
		Document doc = Jsoup.parse(fileEmail, "UTF-8");
		doc.getElementById("urlActivation").attr("href", OpSocialBackApplication.ACTIVATON_LINK + user.getUrlActivation());
		doc.getElementById("unsubscribeEmail").attr("href", OpSocialBackApplication.UNSUBSCRIBE_EMAIL_LINK + user.getEmail());
		
		new SendMail(EmailSender.HELLO_WOOP_EMAIL, subject, user.getEmail(), doc.html(), EmailSender.HELLO_WOOP_PERSONAL);
	}

	private String verifyUsers(String email) {

		MaintenanceUserRemote userRemote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
		
		if(userRemote.getByEmail(email) == null) {
		
 			List<User> users = userRemote.listUsersByAccount(SecurityUtils.getCurrentUser().getAccount().getIdAccount());
			Integer activeUsersCount = users.size();
			
			if(activeUsersCount >= SecurityUtils.getCurrentUser().getAccount().getUsers()) {
				return OpSocialBackApplication.messageByLocaleService.getMessage("user.insert.planlimit", new Object[]{SecurityUtils.getCurrentUser().getAccount().getUsers()});
			}
			
		} else {
			return OpSocialBackApplication.messageByLocaleService.getMessage("user.insert.existingemail");
		}
		
		return null;
	}
	
}
