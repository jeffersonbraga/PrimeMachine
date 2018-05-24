package br.com.opsocial.server.actions.feeds.facebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceConversationRemote;
import br.com.opsocial.ejb.das.MaintenanceMessageRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.mailbox.Conversation;
import br.com.opsocial.ejb.entity.mailbox.Message;

@RestController
@RequestMapping("woopsocial")
public class RecoverMailboxAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/recover_mailbox",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
	
		Profile profile = (Profile) getParameters().get("profile");
		
		MaintenanceConversationRemote conversationRemote = 
				(MaintenanceConversationRemote) RecoverMaintenance.recoverMaintenance("Conversation");
		
		final MaintenanceMessageRemote messageRemote = 
				(MaintenanceMessageRemote) RecoverMaintenance.recoverMaintenance("Message");
		
		List<Conversation> conversations = conversationRemote.listByProfile(profile.getIdProfile());
		
		List<Conversation> conversationDTOs = new ArrayList<Conversation>();
		
		for(Conversation conversation : conversations) {
			
			if(conversations.indexOf(conversation) == 0) {
				
				if(conversation.getUnread()) {
					conversation.setUnread(false);
					conversation = conversationRemote.save(conversation);
				}
			
				Conversation conversationDTO = conversation;
			
				conversation.setMessages(messageRemote.listByConversation(conversation.getIdConversation()));
				
				List<Message> messageDTOs = new ArrayList<Message>();
				
				for(Message message : conversation.getMessages()) {
					messageDTOs.add(message);
				}
				
				conversationDTO.setMessages(messageDTOs);
				
				conversationDTOs.add(conversationDTO);
			} else {
				Conversation conversationDTO = conversation;
				conversationDTOs.add(conversationDTO);
			}
		}
		
		setParameters(new HashMap<String,Object>());
		
		getParameters().put("conversations", conversationDTOs);
	}
}
