package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringUserDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringUser;

@Stateless
public class MaintenanceMonitoringUserBean extends AbstractDASImpl<MonitoringUser> implements MaintenanceMonitoringUserRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	MonitoringUserDAO dao;
	
	@Override
	public MonitoringUser save(MonitoringUser object) throws IllegalArgumentException, Exception {
		return dao.save(object);
	}
	
	@Override
	public void setDao(AbstractDAO<MonitoringUser> dao) {
		this.dao = (MonitoringUserDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringUser> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO getDAO() {
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
