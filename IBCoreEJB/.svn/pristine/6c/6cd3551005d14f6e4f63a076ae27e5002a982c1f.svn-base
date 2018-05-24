package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.mailbox.Message;

@Local
public interface MessageDAO extends AbstractDAO<Message> {
	
	public List<Message> listByConversation(Long idConversation);
	
	public Message getByIdAndConversation(String id, Long idConversation);

}
