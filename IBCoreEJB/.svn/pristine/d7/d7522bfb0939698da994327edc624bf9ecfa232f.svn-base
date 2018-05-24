package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLogReport;

@Local
public interface MonitoringLogReportDAO extends AbstractDAO<MonitoringLogReport> {

	public List<MonitoringLogReport> getMonitoringLogReports(Long idMonitoring, String reportType, Character updateApplied);
	public boolean hasMonitoringLogReports(Long idMonitoring, Character updateApplied);
}
