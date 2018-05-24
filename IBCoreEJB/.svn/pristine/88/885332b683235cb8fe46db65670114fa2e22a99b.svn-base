package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.mailbox.Conversation;

@Remote
public interface MaintenanceConversationRemote extends AbstractDAS<Conversation> {
	
public Conversation getByNetworkConversationId(String id, Long idProfile);
	
	public Integer listUnreadConversations(Long idProfile);
	
	public List<Conversation> listByProfile(Long idProfile);
	
	public Conversation getByProfileAndConversation(Long idProfile, Long idConversation);
	
	public Conversation listMostRecent(Long idProfile);
	
	public List<Conversation> listNotReadConversations(Long idProfile);
	

}
