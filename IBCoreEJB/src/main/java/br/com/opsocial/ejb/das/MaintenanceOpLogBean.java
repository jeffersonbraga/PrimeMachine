package br.com.opsocial.ejb.das;

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.OpLogDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.OpLog;
import br.com.opsocial.ejb.entity.application.User;

@Stateless
public class MaintenanceOpLogBean extends AbstractDASImpl<OpLog> implements MaintenanceOpLogRemote {
	private static final long serialVersionUID = 1L;
	
	@Resource
	private SessionContext sctx;
	
	@EJB
	OpLogDAO dao;

	@Override
	public List<OpLog> getLogByUserEntityAndOperation(Long idUser, String entity, String operation) {
		return dao.getLogByUserEntityAndOperation(idUser, entity, operation);
	}
	
	public SessionContext getSessionContext (){
		return this.sctx;
	}

	@Override
	public void setDao(AbstractDAO<OpLog> dao) {
		this.dao = (OpLogDAO) dao;	
	}

	@Override
	public AbstractDAO<OpLog> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<OpLog> getDAO() {
		return dao;
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

	@Override
	public User getLogByEntityByInsert(Long entityKey, String entity) {
		return dao.getLogByEntityByInsert(entityKey, entity);
	}
}
