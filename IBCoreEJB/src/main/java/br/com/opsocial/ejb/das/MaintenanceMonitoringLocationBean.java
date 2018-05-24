package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringLocationDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLocation;

@Stateless
public class MaintenanceMonitoringLocationBean extends AbstractDASImpl<MonitoringLocation> implements MaintenanceMonitoringLocationRemote {
	
	private static final long serialVersionUID = 1L;

	@EJB
	MonitoringLocationDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringLocation> dao) {
		this.dao = (MonitoringLocationDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringLocation> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO getDAO() {
		return dao;
	}
	
	@Override
	public MonitoringLocation save(MonitoringLocation monitoringLocation) throws IllegalArgumentException, Exception {
		return dao.save(monitoringLocation);
	}

	@Override
	public Map<String, Long> getPostsCitiesCount(Long idMonitoring, Character networkType, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		return dao.getPostsCitiesCount(idMonitoring, networkType, dateFrom, dateUntil, offset, limit);
	}

	@Override
	public List<Object[]> getPostsCitiesCountByDay(Long idMonitoring, Long date) {
		return dao.getPostsCitiesCountByDay(idMonitoring, date);
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
