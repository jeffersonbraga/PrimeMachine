package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ExceptionDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;

@Stateless
public class MaintenanceExceptionBean extends AbstractDASImpl<br.com.opsocial.ejb.entity.application.Exception> implements MaintenanceExceptionRemote {
	
	private static final long serialVersionUID = 1L;

	@EJB
	ExceptionDAO dao;  

	@Override
	public AbstractDAO<br.com.opsocial.ejb.entity.application.Exception> getDAO() {
		return dao;
	}
	
	@Override
	public void setDao(AbstractDAO<br.com.opsocial.ejb.entity.application.Exception> dao) {
		this.dao = (ExceptionDAO) dao;
	}

	@Override
	public AbstractDAO<br.com.opsocial.ejb.entity.application.Exception> getDao() {
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
}
