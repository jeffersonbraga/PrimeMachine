package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringSearchDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringSearch;

@Stateless
public class MaintenanceMonitoringSearchBean extends AbstractDASImpl<MonitoringSearch> implements MaintenanceMonitoringSearchRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	MonitoringSearchDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringSearch> dao) {
		this.dao = (MonitoringSearchDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringSearch> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<MonitoringSearch> getDAO() {
		return dao;
	}

	@Override
	public MonitoringSearch save(MonitoringSearch monitoringSearch) throws IllegalArgumentException, Exception {
		return super.save(monitoringSearch);
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
