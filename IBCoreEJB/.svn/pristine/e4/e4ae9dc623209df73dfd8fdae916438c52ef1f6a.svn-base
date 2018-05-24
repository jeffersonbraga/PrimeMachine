package br.com.opsocial.ejb.das;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTagReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTagReportDAO;

@Stateless
public class MaintenanceMonitoringTagReportBean extends AbstractDASImpl<MonitoringTagReport> implements MaintenanceMonitoringTagReportRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	MonitoringTagReportDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringTagReport> dao) {
		this.dao = (MonitoringTagReportDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringTagReport> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<MonitoringTagReport> getDAO() {
		return dao;
	}

	@Override
	public Map<Character, Long> getTaggedPostsPerNetwork(Long idMonitoring, Long idTag, Long dateFrom, Long dateUntil) {
		return dao.getTaggedPostsPerNetwork(idMonitoring, idTag, dateFrom, dateUntil);
	}

	@Override
	public boolean hasEntity(Long idMonitoring, Long idTag, Character networkType, Long date) {
		return dao.hasEntity(idMonitoring, idTag, networkType, date);
	}

	@Override
	public MonitoringTagReport getEntity(Long idMonitoring, Long idTag, Character networkType, Long date) {
		return dao.getEntity(idMonitoring, idTag, networkType, date);
	}

	@Override
	public Map<Character, Long> getTaggedPostsPerNetwork(Long idMonitoring,
			String tags, Long dateFrom, Long dateUntil) {
		return dao.getTaggedPostsPerNetwork(idMonitoring, tags, dateFrom, dateUntil);
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
