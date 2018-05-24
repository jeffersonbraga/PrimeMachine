package br.com.opsocial.server.services;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
import br.com.opsocial.client.entity.application.AccountUserDTO;
import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceColorTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagRemote;
import br.com.opsocial.ejb.das.MaintenanceTabRemote;
import br.com.opsocial.ejb.das.MaintenanceTimezoneRemote;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.Timezone;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.feed.Tab;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTag;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.emails.EmailSender;

@RestController
@RequestMapping("woopsocial")
public class AccountController {
	
	@CrossOrigin
	@RequestMapping(value = "/create_account",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createAccount(@Valid @RequestBody AccountUserDTO accountUserDTO, HttpSession session, HttpServletRequest request) {
		
		MaintenanceAccountRemote accountRemote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
		MaintenanceUserRemote userRemote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
		MaintenanceTabRemote remoteTab = (MaintenanceTabRemote) RecoverMaintenance.recoverMaintenance("Tab");
		
		Account account = new Account();
		User user = new User();
		
		String message = verifyUsers(accountUserDTO.getEmail());
		
		if(message == null) {
		
			try {
				
	//			account.setCity(accountUserDTO.getCity());
				//TODO é necessário somente no OpSocial BR
	//			account.setState(accountUserDTO.getCountry());
				account.setCountry(accountUserDTO.getCountry());
				account.setPlanType(Account.FREEMIUM);
				account.setUsers(1);
				account.setProfiles(0);
				account.setEnterprise(accountUserDTO.getFirstName());
				account.setCadastreDate(new Date());
				account.setPlanExpire(null);
				
				//TODO é necessário somente no OpSocial BR
	//				if(!req.getParameter("promotionalcode").trim().isEmpty()) {
	//					account.setPromotionalCode(req.getParameter("promotionalcode").trim());
	//				}
	
				user.setAccount(accountRemote.save(account));
	
	//			user.setName(accountUserDTO.getFirstName() + " " + accountUserDTO.getLastName());
				user.setName(accountUserDTO.getFirstName());
				user.setEmail(accountUserDTO.getEmail());
	//			user.setPhone(accountUserDTO.getPhoneNumber());
	
				String password = accountUserDTO.getPassword(); //user.getPassword();
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = passwordEncoder.encode(password);
				user.setPassword(hashedPassword);
				
	//			user.setPassword(accountUserDTO.getPassword());
	
				user.getAccount().setCadastreDate(new Date());	
	
				if(user.getCadastreDate() == null) {
					user.setCadastreDate(new Date());
				}
	
				user.setType('A');
				user.setShowInstagramAuthWindow('F');
				user.setShowLettersToClientsWindow('F');
				
				user = userRemote.save(user);
				
				Tab tab = new Tab();
				
				tab.setName("My Stream");
				tab.setUser(user);
				remoteTab.save(tab);
				
				//TODO é necessário somente no OpSocial BR
	//			insertGlobalMonitoringsTags(user.getAccount());
				
				//TODO é necessário somente no OpSocial BR
	//			new Thread(new PaymentEmail(user)).start();
				/*
				UserDetailsService us = new UserDetailsService();
				us.loadUserByUsername(user.getEmail());
				*/
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);	
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);		
			}
		} else {			
			return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
		}
		
		return new ResponseEntity<>(user, HttpStatus.OK);	
	}
	
	@CrossOrigin
	@RequestMapping(value = "/cancel_subscription",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> cancelSubscription(@Valid @RequestBody Account account, HttpSession session, HttpServletRequest request) {
		
		try {
			
			// TODO: Verificar com Jeff o que mais precisamos fazer para cancelar a conta do usuário.
			MaintenanceAccountRemote accountRemote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
			
			account = accountRemote.getById(account.getIdAccount());
			
			account.setActive(User.INACTIVE); 
			
			accountRemote.save(account);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(HttpStatus.OK);	
	}

	class PaymentEmail implements Runnable {
		
		private User user;
		
		public PaymentEmail(User user) {
			this.user = user;
		}

		@Override
		public void run() {
			
			try {
				
				String subject = "Olá, novo cliente OpSocial. ";
				
				File email = new File(OpSocialBackApplication.EMAILS_DIR + "email-novo-cliente.html");  
				Document doc = Jsoup.parse(email, "UTF-8");
				doc.getElementById("username").html("Oi " + user.getName().split(" ")[0]);
				
				new SendMail(EmailSender.HELLO_WOOP_EMAIL, subject, user.getEmail(), doc.html(), EmailSender.HELLO_WOOP_PERSONAL);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	private void insertGlobalMonitoringsTags(Account account) {

		MaintenanceMonitoringTagRemote monitoringTagRemote = (MaintenanceMonitoringTagRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringTag");

		MaintenanceColorTagRemote colorTagRemote = (MaintenanceColorTagRemote) 
				RecoverMaintenance.recoverMaintenance("ColorTag");

		try {

			MonitoringTag monitoringTagSuggestion = new MonitoringTag();
			monitoringTagSuggestion.setAccount(account);
			monitoringTagSuggestion.setColorTag(colorTagRemote.getById(1L));
			monitoringTagSuggestion.setName("sugestão");

			monitoringTagRemote.save(monitoringTagSuggestion);

			MonitoringTag monitoringTagCritic = new MonitoringTag();
			monitoringTagCritic.setAccount(account);
			monitoringTagCritic.setColorTag(colorTagRemote.getById(2L));
			monitoringTagCritic.setName("crítica");

			monitoringTagRemote.save(monitoringTagCritic);

			MonitoringTag monitoringTagComplaint = new MonitoringTag();
			monitoringTagComplaint.setAccount(account);
			monitoringTagComplaint.setColorTag(colorTagRemote.getById(3L));
			monitoringTagComplaint.setName("reclamação");

			monitoringTagRemote.save(monitoringTagComplaint);

			MonitoringTag monitoringTagInfluencer = new MonitoringTag();
			monitoringTagInfluencer.setAccount(account);
			monitoringTagInfluencer.setColorTag(colorTagRemote.getById(4L));
			monitoringTagInfluencer.setName("influenciador");

			monitoringTagRemote.save(monitoringTagInfluencer);

			MonitoringTag monitoringTagCrisis = new MonitoringTag();
			monitoringTagCrisis.setAccount(account);
			monitoringTagCrisis.setColorTag(colorTagRemote.getById(5L));
			monitoringTagCrisis.setName("crise");

			monitoringTagRemote.save(monitoringTagCrisis);

			MonitoringTag monitoringTagCompliment = new MonitoringTag();
			monitoringTagCompliment.setAccount(account);
			monitoringTagCompliment.setColorTag(colorTagRemote.getById(6L));
			monitoringTagCompliment.setName("elogio");

			monitoringTagRemote.save(monitoringTagCompliment);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@CrossOrigin
	@RequestMapping(value = "/updatetimezone",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> updatetimezone(@Valid @RequestBody String timezone_str) {

		try {

			MaintenanceTimezoneRemote mtr = (MaintenanceTimezoneRemote) RecoverMaintenance.recoverMaintenance("Timezone");
			//----- System.out.println(new Long(timezone_str));
			List<Timezone> tmz = mtr.getAll();
			Long cd_timezone = new Long(timezone_str);
			Account acc = SecurityUtils.getCurrentUser().getAccount();
			for (Timezone it : tmz) {
				if (it.getIdTimezone().equals(cd_timezone)) {
					acc.setTimezone(it);
				}
			}

			MaintenanceAccountRemote mar = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
			mar.save(acc);

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(HttpStatus.OK);	
	}

	@CrossOrigin
	@RequestMapping(value = "/get_timezone",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Timezone> getDefaultTimezone() {

		Timezone vlrTimezone = null;
		try {

			if (SecurityUtils.getCurrentUser() != null && SecurityUtils.getCurrentUser().getAccount() != null && SecurityUtils.getCurrentUser().getAccount().getTimezone() != null) {
				vlrTimezone = SecurityUtils.getCurrentUser().getAccount().getTimezone();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Timezone>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	

		return new ResponseEntity<>(vlrTimezone, HttpStatus.OK);
	}

	private String verifyUsers(String email) {

		MaintenanceUserRemote userRemote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
		
		if(userRemote.getByEmail(email) != null) {
			return OpSocialBackApplication.messageByLocaleService.getMessage("user.insert.existingemail");
		}
		
		return null;
	}
}