package br.com.opsocial.ejb.das;

import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLocationReport;

@Remote
public interface MaintenanceMonitoringLocationReportRemote extends AbstractDAS<MonitoringLocationReport> {

	public boolean hasEntity(Long idMonitoring, String location, Long date);
	public MonitoringLocationReport getEntity(Long idMonitoring, String location, Long date);
	
	public Map<String, Long> getPostsCitiesCount(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
}
