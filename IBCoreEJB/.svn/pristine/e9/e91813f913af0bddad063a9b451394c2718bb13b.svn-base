package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLocation;

@Remote
public interface MaintenanceMonitoringLocationRemote extends AbstractDAS<MonitoringLocation> {
	
	public Map<String, Long> getPostsCitiesCount(Long idMonitoring, Character networkType, Long dateFrom, Long dateUntil, Integer offset, Integer limit);

	public List<Object[]> getPostsCitiesCountByDay(Long idMonitoring, Long date);
}
