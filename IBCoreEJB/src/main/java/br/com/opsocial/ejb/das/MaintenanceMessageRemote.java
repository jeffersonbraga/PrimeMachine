package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.mailbox.Message;

@Remote
public interface MaintenanceMessageRemote extends AbstractDAS<Message> {
	
	public List<Message> listByConversation(Long idConversation);
	
	public Message getByIdAndConversation(String id, Long idConversation);
}
