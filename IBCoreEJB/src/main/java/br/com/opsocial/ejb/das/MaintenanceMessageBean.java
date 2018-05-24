package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MessageDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.mailbox.Message;

@Stateless
public class MaintenanceMessageBean extends AbstractDASImpl<Message> implements MaintenanceMessageRemote {
	
	private static final long serialVersionUID = 1L;

	@EJB
	MessageDAO dao;  

	@Override
	public AbstractDAO<Message> getDAO() {
		return dao;
	}
	
	@Override
	public void setDao(AbstractDAO<Message> dao) {
		this.dao = (MessageDAO) dao;
	}

	@Override
	public AbstractDAO<Message> getDao() {
		return dao;
	}

	@Override
	public List<Message> listByConversation(Long idConversation) {
		return dao.listByConversation(idConversation);
	}

	@Override
	public Message getByIdAndConversation(String id, Long idConversation) {
		return dao.getByIdAndConversation(id, idConversation);
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
