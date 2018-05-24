package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.mailbox.Conversation;

@Local
public interface ConversationDAO extends AbstractDAO<Conversation> {
	
	public Conversation getByNetworkConversationId(String id, Long idProfile);
	
	public Integer listUnreadConversations(Long idProfile);
	
	public List<Conversation> listByProfile(Long idProfile);
	
	public Conversation getByProfileAndConversation(Long idProfile, Long idConversation);
	
	public Conversation listMostRecent(Long idProfile);
	
	public List<Conversation> listNotReadConversations(Long idProfile);
	
}
