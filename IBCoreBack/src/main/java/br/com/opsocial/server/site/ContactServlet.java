package br.com.opsocial.server.site;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import br.com.opsocial.server.services.SendMail;
import br.com.opsocial.server.utils.emails.EmailSender;

@RestController
@RequestMapping("woopsocial")
public class ContactServlet {

	@CrossOrigin
	@RequestMapping(value = "/contact",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	protected void doPost(String name, String email, String phone, String message, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		String subject = "Woop Contact";
		
		String html = 
			"<b>Name:</b> " + name + "<br/>" +
			"<b>Email:</b> " + email + "<br/><br/>" +
			"<b>Phone:</b> " + phone + "<br/><br/>" +
			"<b>Message:</b> <br/>" + message;
		
//		new SendMail(EmailSender.HELLO_WOOP_EMAIL, subject, "contato@opsocial.com.br", html, EmailSender.HELLO_WOOP_PERSONAL);
		new SendMail(EmailSender.HELLO_WOOP_EMAIL, subject, "hello@woopsocial.com", html, EmailSender.HELLO_WOOP_PERSONAL);
		
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		JsonObject object = new JsonObject();
		object.addProperty("success", "true");
		
		out.print(object);
		out.flush();
		
		//return new ResponseEntity<>(l, HttpStatus.OK);
	}
}
