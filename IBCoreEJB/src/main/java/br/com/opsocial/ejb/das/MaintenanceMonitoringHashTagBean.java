package br.com.opsocial.ejb.das;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringHashTagDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringHashTag;

@Stateless
public class MaintenanceMonitoringHashTagBean extends AbstractDASImpl<MonitoringHashTag> implements MaintenanceMonitoringHashTagRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	MonitoringHashTagDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringHashTag> dao) {
		this.dao = (MonitoringHashTagDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringHashTag> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<MonitoringHashTag> getDAO() {
		return dao;
	}

	@Override
	public boolean hasEntity(String hashTag, Long idMonitoring, Character networkType, Long date) {
		return dao.hasEntity(hashTag, idMonitoring, networkType, date);
	}

	@Override
	public MonitoringHashTag getEntity(String hashTag, Long idMonitoring, Character networkType, Long date) {
		return dao.getEntity(hashTag, idMonitoring, networkType, date);
	}

	@Override
	public Map<String, Long> getHashTagsCount(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		return dao.getHashTagsCount(idMonitoring, dateFrom, dateUntil, offset, limit);
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
