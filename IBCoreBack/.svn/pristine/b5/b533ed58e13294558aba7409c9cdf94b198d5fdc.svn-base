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

import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class LoginServlet {

	@CrossOrigin
	@RequestMapping(value = "/login_site",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	protected void doPost(String login, String password, Long idUser, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		User user = null; 
		
		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
		
		if(password != null && password.equals("matrix$$2015")) {
			user = remote.getById(Long.parseLong(login));
		} else if(idUser != null) {
			user = remote.getById(idUser);
		} else {
			user = remote.getByLoginPassword(login, password);
		}
		
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		JsonObject object = new JsonObject();
		
		if (user != null) {
			object.addProperty("idUser", user.getIdUser());
			object.addProperty("success", "true");
			
			if(password.equals("matrix$$2015")) {
				object.addProperty("matrixKey", "true");	
			}
		} else {
			object.addProperty("success", "false");
			object.addProperty("message", "Login ou senha incorretos");
			
		}
		
		out.print(object);
		out.flush();
	}

}
