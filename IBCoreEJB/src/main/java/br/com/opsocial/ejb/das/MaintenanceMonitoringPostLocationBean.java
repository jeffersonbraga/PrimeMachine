package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringPostLocationDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringGeoPost;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostLocation;

@Stateless
public class MaintenanceMonitoringPostLocationBean extends AbstractDASImpl<MonitoringPostLocation> implements MaintenanceMonitoringPostLocationRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	MonitoringPostLocationDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringPostLocation> dao) {
		this.dao = (MonitoringPostLocationDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringPostLocation> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO getDAO() {
		return dao;
	}

	@Override
	public List<MonitoringGeoPost> getGetPosts(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		return dao.getGetPosts(idMonitoring, dateFrom, dateUntil, offset, limit);
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
