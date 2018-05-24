package br.com.opsocial.server.actions.home;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.User;

@RestController
@RequestMapping("woopsocial")
public class SendNewPasswordAction extends ServerAction {

	@Override
	@RequestMapping(value = "/send_new_password",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception {
		
		String email = ((StringUtil) getParameters().get("email")).getValue();
		
		MaintenanceUserRemote remote = (MaintenanceUserRemote) RecoverMaintenance.recoverMaintenance("User");
		
		User user = remote.getByEmail(email); 
		
		if(user != null) {
			
			String newPassWord = RandomStringUtils.random(10, true, true);
			
//			String subject = "Nova senha OpSocial";
//			
//			String message = 
//				"<div style='height:300px; width:100%'>" +
//					"<p style=\"margin-left: 65px; font-family: Ubuntu; font-size: 12pt; color: black; line-height: normal; text-align: justify;\">" +
//						"Olá, recebemos a sua solicitação.<br><br>" + 
//						"Sua nova senha é: " + newPassWord + 
//					"</p>" +
//				"</div>";
//			
//			SendMail mail = new SendMail(subject, message, user.getEmail(), false);
			
//			if(mail.wasSend()) {
//				user.setPassword(newPassWord);
//				remote.save(user);
				getParameters().put("isSentEmail", new Boolean(true));
//			} else {
//				getParameters().put("isSentEmail", new Boolean(false));
//			}
			getParameters().put("exists", new Boolean(true));
		} else {
			getParameters().put("exists", new Boolean(false));
		}	
		
	}
}
