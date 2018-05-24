package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.mailbox.Conversation;

@Stateless
public class ConversationDAOImpl extends AbstractDAOImpl<Conversation> implements ConversationDAO {
		
	@Override
	public Conversation getByNetworkConversationId(String id, Long idProfile) {
	
		sql = "Select c From Conversation c where c.networkConversationId = :id and c.profile.idProfile = :idProfile";
		
		query = em.createQuery(sql);
		query.setParameter("id", id);
		query.setParameter("idProfile", idProfile);
		
		try {
			return (Conversation) query.getSingleResult();	
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public Integer listUnreadConversations(Long idProfile) {
		
		sql = "Select count(c) From Conversation c where c.profile.idProfile = :idProfile and c.unread=true";
		
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		
		return Integer.valueOf(((Long) query.getSingleResult()).toString());
	}
	
	@Override
	public List<Conversation> listByProfile(Long idProfile) {
		
		sql = "Select c From Conversation c where c.profile.idProfile = :idProfile order by c.updatedTime desc";
		
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		
		return query.getResultList();
	}

	@Override
	public Conversation getByProfileAndConversation(Long idProfile, Long idConversation) {
		
		sql = "Select c From Conversation c where c.profile.idProfile= :idProfile and c.idConversation = :idConversation";
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("idConversation", idConversation);
		
		if(!query.getResultList().isEmpty()) {
			return (Conversation) query.getSingleResult();
		} else {
			return null;
		}	
	}

	@Override
	public Conversation listMostRecent(Long idProfile) {
		sql = "Select c From Conversation c where c.profile.idProfile = :idProfile order by c.updatedTime desc";
		
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setMaxResults(1);
		
		return (Conversation) query.getSingleResult();
	}

	@Override
	public List<Conversation> listNotReadConversations(Long idProfile) {
		sql = "Select c From Conversation c where c.profile.idProfile = :idProfile and c.unread=true";
		
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		
		return query.getResultList();
	}


}
