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
public class RecoverAccountDataServlet {

	@CrossOrigin
	@RequestMapping(value = "/recover_account",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	protected void doPost(Long idUser, Byte plan, HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
		
		User user = remote.getById(idUser);
		
		resp.setContentType("text/html");
		resp.setCharacterEncoding("UTF-8");
		
		PrintWriter out = resp.getWriter();
		
		JsonObject object = new JsonObject();
		
		
		if (user != null) {
			object.addProperty("plan", plan);
			object.addProperty("idUser", String.valueOf(user.getIdUser()));
			object.addProperty("email", user.getEmail());
			object.addProperty("name", user.getName());
//			object.addProperty("city", user.getAccount().getCity());
			object.addProperty("enterprise", user.getAccount().getEnterprise());
			object.addProperty("phone", user.getPhone());
			//TODO só será usado no OpSocial
//			object.addProperty("state", user.getAccount().getState());
			object.addProperty("country", user.getAccount().getCountry());
			object.addProperty("success", "true");
		} else {
			object.addProperty("success", "false");
		}
		
		out.print(object);
		out.flush();
	}

}
