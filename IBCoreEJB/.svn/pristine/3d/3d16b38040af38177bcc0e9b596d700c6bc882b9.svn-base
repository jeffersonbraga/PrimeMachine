package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLogReport;

@Remote
public interface MaintenanceMonitoringLogReportRemote extends AbstractDAS<MonitoringLogReport> {
	
	public List<MonitoringLogReport> getMonitoringLogReports(Long idMonitoring, String reportType, Character updateApplied);
	public boolean hasMonitoringLogReports(Long idMonitoring, Character updateApplied);
	
}
