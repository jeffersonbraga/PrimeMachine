package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.InternalAnalyticDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.InternalAnalytic;

@Stateless
public class MaintenanceInternalAnalyticBean extends AbstractDASImpl<InternalAnalytic> implements MaintenanceInternalAnalyticRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	InternalAnalyticDAO dao;
	
	@Override
	public void setDao(AbstractDAO<InternalAnalytic> dao) {
		this.dao = (InternalAnalyticDAO) dao;
	}

	@Override
	public AbstractDAO<InternalAnalytic> getDao() {
		return dao;
	}

	@Override
	public void setIdUserSession(Long idUserSession) {
	}

	@Override
	public Long getIdUserSession() {
		return null;
	}

	@Override
	public AbstractDAO<InternalAnalytic> getDAO() {
		return dao;
	}

}
