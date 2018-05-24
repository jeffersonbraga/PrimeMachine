package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.application.OpLog;
import br.com.opsocial.ejb.entity.application.User;

@Local
public interface OpLogDAO extends AbstractDAO<OpLog> {

	public List<OpLog> getLogByUserEntityAndOperation(Long idUser, String entity, String operation);

	public User getLogByEntityByInsert(Long entityKey, String entity);
}
