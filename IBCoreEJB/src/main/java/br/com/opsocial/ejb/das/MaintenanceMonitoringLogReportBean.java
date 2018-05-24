package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringLogReportDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLogReport;

@Stateless
public class MaintenanceMonitoringLogReportBean extends AbstractDASImpl<MonitoringLogReport> implements MaintenanceMonitoringLogReportRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	MonitoringLogReportDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringLogReport> dao) {
		this.dao = (MonitoringLogReportDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringLogReport> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<MonitoringLogReport> getDAO() {
		return dao;
	}

	@Override
	public List<MonitoringLogReport> getMonitoringLogReports(Long idMonitoring, String reportType, Character updateApplied) {
		return dao.getMonitoringLogReports(idMonitoring, reportType, updateApplied);
	}

	@Override
	public boolean hasMonitoringLogReports(Long idMonitoring, Character updateApplied) {
		return dao.hasMonitoringLogReports(idMonitoring, updateApplied);
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
