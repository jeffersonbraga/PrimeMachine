package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ConversationDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.mailbox.Conversation;

@Stateless
public class MaintenanceConversationBean extends AbstractDASImpl<Conversation> implements MaintenanceConversationRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	ConversationDAO dao;  

	@Override
	public AbstractDAO<Conversation> getDAO() {
		return dao;
	}
	
	@Override
	public void setDao(AbstractDAO<Conversation> dao) {
		this.dao = (ConversationDAO) dao;
	}

	@Override
	public AbstractDAO<Conversation> getDao() {
		return dao;
	}

	@Override
	public Conversation getByNetworkConversationId(String id, Long idProfile) {
		return dao.getByNetworkConversationId(id, idProfile);
	}

	@Override
	public Integer listUnreadConversations(Long idProfile) {
		return dao.listUnreadConversations(idProfile);
	}

	@Override
	public List<Conversation> listByProfile(Long idProfile) {
		return dao.listByProfile(idProfile);
	}

	@Override
	public Conversation getByProfileAndConversation(Long idProfile,
			Long idConversation) {
		return dao.getByProfileAndConversation(idProfile, idConversation);
	}

	@Override
	public Conversation listMostRecent(Long idProfile) {
		return dao.listMostRecent(idProfile);
	}

	@Override
	public List<Conversation> listNotReadConversations(Long idProfile) {
		return dao.listNotReadConversations(idProfile);
	}

	@Override
	public void setIdUserSession(Long idUserSession) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getIdUserSession() {
		// TODO Auto-generated method stub
		return null;
	}
}
