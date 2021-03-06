package br.com.opsocial.server.utils.sets;

import java.io.File;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.generic.Persistent;
/*import java.io.File;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.generic.Persistent;*/
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.server.services.SendMail;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.emails.EmailSender;

public class PostNotDeliveredEmail implements Runnable {
	
	private Post post;
	private String notificationMessage;
	
	public PostNotDeliveredEmail(Post post, String notificationMessage) {
		this.post = post;
		this.notificationMessage = notificationMessage;
	}

	@Override
	public void run() {
		
		try {
			
			MaintenanceUserRemote userRemote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
			List<User> users = userRemote.listUsersByAccount(post.getProfile().getAccount().getIdAccount());
			
			// Woop Email
			String subject = "Your posts were not sent.";
			
			for (User user : users) {
				
				File email = new File(OpSocialBackApplication.EMAILS_DIR + "post-not-delivered.html");  
				Document doc = Jsoup.parse(email, "UTF-8");
				doc.getElementById("username").html("Ops!");
				doc.getElementById("notification-message").html(notificationMessage);
				doc.getElementById("unsubscribeEmail").attr("href", OpSocialBackApplication.UNSUBSCRIBE_EMAIL_LINK + user.getEmail());
				
				new SendMail(EmailSender.HELLO_WOOP_EMAIL, subject, user.getEmail(), doc.html(), EmailSender.HELLO_WOOP_PERSONAL);
			}
			
			// Op Email
//			String subject = "Agendamento não pode ser enviado.";
//			
//			for (User user : users) {
//				
//				File email = new File(Persistent.EMAILS_PATH + "post-not-delivered.html");  
//				Document doc = Jsoup.parse(email, "UTF-8");
//				doc.getElementById("username").html("Oi " + user.getName().split(" ")[0] + "!");
//				doc.getElementById("notification-message").html(notificationMessage);
//				
//				new SendMail(EmailSender.HELLO_WOOP_EMAIL, subject, user.getEmail(), doc.html(), EmailSender.HELLO_WOOP_PERSONAL);
//			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}