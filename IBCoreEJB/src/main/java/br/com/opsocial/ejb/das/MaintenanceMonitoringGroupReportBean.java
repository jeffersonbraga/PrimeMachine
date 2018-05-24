package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringGroupReportDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringGroupReport;

@Stateless
public class MaintenanceMonitoringGroupReportBean extends AbstractDASImpl<MonitoringGroupReport> implements MaintenanceMonitoringGroupReportRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	MonitoringGroupReportDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringGroupReport> dao) {
		this.dao = (MonitoringGroupReportDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringGroupReport> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<MonitoringGroupReport> getDAO() {
		return dao;
	}

	@Override
	public Long getPostsTotalByGroup(Long idMonitoring, Long idProfile, Long dateFrom, Long dateUntil) {
		return dao.getPostsTotalByGroup(idMonitoring, idProfile, dateFrom, dateUntil);
	}

	@Override
	public boolean hasEntity(Long idMonitoring, Long idProfile, Long date) {
		return dao.hasEntity(idMonitoring, idProfile, date);
	}

	@Override
	public MonitoringGroupReport getEntity(Long idMonitoring, Long idProfile, Long date) {
		return dao.getEntity(idMonitoring, idProfile, date);
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
