package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTermReport;

@Remote
public interface MaintenanceMonitoringTermReportRemote extends AbstractDAS<MonitoringTermReport> {

	public boolean hasEntity(Long idMonitoring, String term, Character networkType, Long date);
	
	public MonitoringTermReport getEntity(Long idMonitoring, String term, Character networkType, Long date);
	
	public Long getPostsTotalByTerm(Long idMonitoring, String term, Long dateFrom, Long dateUntil);
	
}
