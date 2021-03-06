package br.com.opsocial.server.services;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.emails.EmailSender;
import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.generic.Persistent;

public class ExtendTestServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		try {

			MaintenanceAccountRemote remote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
			MaintenanceUserRemote userRemote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
	
			Account account = remote.getById(new Long(req.getParameter("idaccount")));
			
			if(account.getExtendTest().equals('F')) {
				
				Date newExpire = new Date();
				newExpire.setDate(newExpire.getDate() + 7);
				account.setTestExpire(newExpire);
				account.setExtendTest('T');
				account.setActive('T');
				
				remote.save(account);
				
				List<User> users = userRemote.listUsersByAccount(new Long(req.getParameter("idaccount")));
				
				try {
					String subject = "Temos mais uma semana juntos!";
					
					File email = new File(OpSocialBackApplication.EMAILS_DIR + "dia-8.html");  
					Document doc = Jsoup.parse(email, "UTF-8");
					doc.getElementById("username").html("Oi " + users.get(0).getName().split(" ")[0]);
					
					new SendMail(EmailSender.HELLO_WOOP_EMAIL, subject, users.get(0).getEmail(), doc.html(), EmailSender.HELLO_WOOP_PERSONAL);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} catch (NamingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.setContentType("text/html");
		
		resp.getWriter().print(	
				"<html>" + 
				"<head>" + 
					"<title>OpSocial - Plataforma de controle de mídias sociais</title>" + 
					"<link href='https://fonts.googleapis.com/css?family=Ubuntu' rel='stylesheet'>" +
				"</head>" +
				"<body>" +
					"<div style='width:100%; height:100%; position: relative;'>" + 
						"<div style='top:50%; left:50%; margin-top:-100px; margin-left: -111px; height:100px; width: 350px; position: absolute;'>" + 
							"<center><a href='http://www.opsocial.com.br' target='_blank'>" +
								"<img src='http://www.app.opsocial.com.br/images/email/logo.png' width='117'" +  
								"height='36' alt='www.opsocial.com.br'/>" + 
							"</a>" + 
							"<br><br>" + 
							"<font style='font-family: Ubuntu; font-size: 12pt; color: black; margin-left:15px'>" +
							"Obrigado por continuar utilizando o OpSocial,<br> seu prazo foi estendido para mais 7 dias.</font></center>" + 
						"</div>" + 
					"</div>" + 
				"</body>" + 
				"</html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
	
	}

}
