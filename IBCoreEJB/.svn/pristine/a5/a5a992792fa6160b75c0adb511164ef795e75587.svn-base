package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.OpLog;
import br.com.opsocial.ejb.entity.application.User;

@Remote
public interface MaintenanceOpLogRemote extends AbstractDAS<OpLog> {

	public List<OpLog> getLogByUserEntityAndOperation(Long idUser, String entity, String operation);
	
	public User getLogByEntityByInsert(Long entityKey, String entity);
}
