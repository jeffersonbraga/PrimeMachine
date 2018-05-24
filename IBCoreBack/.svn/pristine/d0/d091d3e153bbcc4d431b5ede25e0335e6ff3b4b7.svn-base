package br.com.opsocial.server.site;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceColorTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagRemote;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTag;
import br.com.opsocial.server.services.SendMail;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.emails.EmailSender;

@RestController
@RequestMapping("woopsocial")
public class PaymentServlet {

	@CrossOrigin
	@RequestMapping(value = "/payment",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	protected void doPost(Long idUser, Byte plan, String enterprise, String promotionalcode, 
			String city, String country, String email, String name, String phone, String password, 
			HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		MaintenanceUserRemote userRemote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");

		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");

		PrintWriter out = resp.getWriter();

		JsonObject object = new JsonObject();

		if(idUser != null) {

			User user = userRemote.getById(idUser);

			user.getAccount().setActive('T');

			if(user.getAccount().getPlanExpire() == null) {
				Date planExpire = new Date();
				planExpire.setDate(planExpire.getDate() + 2);
				user.getAccount().setPlanExpire(planExpire);
			}

			user.getAccount().setPlanType(plan);
			user.getAccount().setEnterprise(enterprise.trim());
			user.getAccount().setProfiles(5);
			user.getAccount().setMonitorings(5);
			
			if(!promotionalcode.trim().isEmpty()) {
				user.getAccount().setPromotionalCode(promotionalcode.trim());
			}

			MaintenanceAccountRemote accountRemote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");

			try {

				user = userRemote.save(user);

				accountRemote.save(user.getAccount());

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			new Thread(new PaymentEmail(user, req.getSession().getServletContext())).start();

			object.addProperty("success", "true");

		} else {

			if(userRemote.getByEmail(email) == null) {

				try {
					User user = new User();

					Account account = new Account();
					account.setCity(city.trim());
					//TODO Só será usado no OpSocial
//					account.setState(state.trim());
					account.setCountry(country.trim());
					account.setPlanType(plan);
					account.setEnterprise(enterprise.trim());
					account.setCadastreDate(new Date());
					
					if(account.getPlanType().equals(Account.OPLAY)) {
						account.setProfiles(5);
						account.setMonitorings(5);
					} else if(account.getPlanType().equals(Account.OPOP)) {
						account.setProfiles(10);
						account.setMonitorings(10);
					} else if(account.getPlanType().equals(Account.OPOWER)) {
						account.setProfiles(15);
						account.setMonitorings(15);
					} else if(account.getPlanType().equals(Account.OPLUS)) {
						account.setProfiles(25);
						account.setMonitorings(25);
					} else if(account.getPlanType().equals(Account.OPRIME)) {
						account.setProfiles(35);
						account.setMonitorings(35);
					} else if(account.getPlanType().equals(Account.OPREMIUM)) {
						account.setProfiles(50);
						account.setMonitorings(50);
					}

					Date planExpire = new Date();
					planExpire.setDate(planExpire.getDate() + 2);

					account.setPlanExpire(planExpire);
					if(!promotionalcode.trim().isEmpty()) {
						account.setPromotionalCode(promotionalcode.trim());
					}

					MaintenanceAccountRemote accountRemote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");

					user.setAccount(accountRemote.save(account));

					user.setName(name.trim());
					user.setEmail(email.trim().toLowerCase());
					user.setPhone(phone);
					user.setPassword(password.trim());

					user.getAccount().setCadastreDate(new Date());	

					if(user.getCadastreDate() == null) {
						user.setCadastreDate(new Date());
					}

					user.setType('A');
					user.setShowInstagramAuthWindow('F');
					user.setShowLettersToClientsWindow('F');

					user = userRemote.save(user);
					
					insertGloblaMonitoringsTags(user.getAccount());

					new Thread(new PaymentEmail(user, req.getSession().getServletContext())).start();

					object.addProperty("success", "true");
					object.addProperty("idUser", String.valueOf(user.getIdUser()));
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {

				object.addProperty("success", "false");
				object.addProperty("message", "Já existe uma conta com esse email.");
			}
		}

		out.print(object);
		out.flush();
	}

	class PaymentEmail implements Runnable {
		
		private User user;
		private ServletContext servletContext;
		
		public PaymentEmail(User user, ServletContext servletContext) {
			this.user = user;
			this.servletContext = servletContext;
		}

		@Override
		public void run() {
			
			try {
				
				String subject = "Olá, novo cliente OpSocial. ";
				
				File email = new File(servletContext.getRealPath("email/" + "email-novo-cliente.html"));  
				Document doc = Jsoup.parse(email, "UTF-8");
				doc.getElementById("username").html("Oi " + user.getName().split(" ")[0]);
				
				new SendMail(EmailSender.HELLO_WOOP_EMAIL, subject, user.getEmail(), doc.html(), EmailSender.HELLO_WOOP_PERSONAL);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	private void insertGloblaMonitoringsTags(Account account) {

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
}
