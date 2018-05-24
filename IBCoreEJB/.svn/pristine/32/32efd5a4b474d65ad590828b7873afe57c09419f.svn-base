package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.mailbox.Message;

@Stateless
public class MessageDAOImpl extends AbstractDAOImpl<Message> implements MessageDAO {
	
	@Override
	public List<Message> listByConversation(Long idConversation) {
		
		sql = "Select m From Message m where m.conversation.idConversation = :idConversation order by m.createdTime";
		
		query = em.createQuery(sql);
		query.setParameter("idConversation", idConversation);
		
		return query.getResultList();
	}
	
	@Override
	public Message getByIdAndConversation(String id, Long idConversation) {

		sql = "Select m From Message m where m.networkMessageId = :id and m.conversation.idConversation = :idConversation";
		
		query = em.createQuery(sql);
		query.setParameter("id", id);
		query.setParameter("idConversation", idConversation);
		
		try {
			return (Message) query.getSingleResult();	
		} catch (Exception e) {
			return null;
		}
	
	}
}
