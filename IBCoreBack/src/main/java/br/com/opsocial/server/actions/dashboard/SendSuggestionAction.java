package br.com.opsocial.server.actions.dashboard;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.SendMail;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceSuggestionRemote;
import br.com.opsocial.ejb.entity.application.Suggestion;

@RestController
@RequestMapping("woopsocial")
public class SendSuggestionAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/send_suggestion",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
	
		String text = ((StringUtil) getParameters().get("text")).getValue();
		final Long idSuggestion;
		
		final MaintenanceSuggestionRemote remote = (MaintenanceSuggestionRemote) RecoverMaintenance.recoverMaintenance("Suggestion");
		
		Suggestion suggestion = new Suggestion();
		suggestion.setText(text);
		suggestion.setUser(getUser());
		suggestion.setSendDate(new Date());
		
		idSuggestion = remote.save(suggestion).getIdSuggestion();
		
		new Thread() {
			
			public void run() {
				
				String emailToSend = "contato@opsocial.com.br";
				Suggestion suggestionToUpdate = remote.getById(idSuggestion);
				
				String subject = "Sugestão OPA(Programa de Aperfeiçoamento OpSocial)";
				
				String message = 
						"<div style=\"margin-left:26px;" +
						"margin-top:20px;" +
						"margin-bottom:60px\">" +
						"<p style=\"font-family:arial,sans-serif;" +
									"font-style:normal;" +
									"font-weight:300;" +
									"font-size:14px;" +
									"color:#2f2f2f;" +
									"margin:0px;" +
									"padding-bottom:5px\">" +
							"A seguinte sugestão foi enviada pelo OPA:" +
						"</p>" + 
						"<p style=\"font-family:arial,sans-serif;" +
								"font-style:normal;" +
								"font-weight:300;" +
								"font-size:14px;" +
								"color:#2f2f2f;" +
								"margin:0px;" +
								"padding-bottom:5px\">" + 
							"Usuário: " + suggestionToUpdate.getUser().getName() + " (" + suggestionToUpdate.getUser().getEmail() + ")" +
						"</p>" +
						"<p style=\"font-family:arial,sans-serif;" +
						"font-style:normal;" +
						"font-weight:300;" +
						"font-size:14px;" +
						"color:#2f2f2f;" +
						"margin:0px;" +
						"padding-bottom:5px\">" + 
							"Sugestão: " + suggestionToUpdate.getText() + 
							"</p>" +
							"<p style=\"font-family:arial,sans-serif;" +
							"font-style:normal;" +
							"font-weight:300;" +
							"font-size:14px;" +
							"color:#2f2f2f;" +
							"margin:0px;" +
							"padding-bottom:5px\">" + 
								"Data: " + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(suggestionToUpdate.getSendDate()) + 
								"</p>" +
						"</div>";
				
				String footer = 
						"<br><br>" +
								"<div style=\"margin-left:26px\">" +
								"<font style=\"font-family: arial,sans-serif; weight:300; font-size:14px; color: #5a5a5a\">" +
								"Att,<br>" + 
								"Equipe OpSocial" +
								"</div>" +
								"<br><br>";
				
				SendMail mail = new SendMail(subject, message, footer, emailToSend, false);
				
				if(mail.wasSend()) {
					suggestionToUpdate.setEmailWasSent(true);
				} else {
					suggestionToUpdate.setEmailWasSent(false);
				}
				
				try {
					
					remote.save(suggestionToUpdate);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}.run();
	}
}
