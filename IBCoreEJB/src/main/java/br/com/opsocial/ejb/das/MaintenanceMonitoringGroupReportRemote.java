package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringGroupReport;

@Remote
public interface MaintenanceMonitoringGroupReportRemote extends AbstractDAS<MonitoringGroupReport> {
	
	public boolean hasEntity(Long idMonitoring, Long idProfile, Long date);
	
	public MonitoringGroupReport getEntity(Long idMonitoring, Long idProfile, Long date);
	
	public Long getPostsTotalByGroup(Long idMonitoring, Long idProfile, Long dateFrom, Long dateUntil);
	
}
