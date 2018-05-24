package br.com.opsocial.ejb.das;

import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringHashTag;

@Remote
public interface MaintenanceMonitoringHashTagRemote extends AbstractDAS<MonitoringHashTag> {

	public boolean hasEntity(String hashTag, Long idMonitoring, Character networkType, Long date);
	public MonitoringHashTag getEntity(String hashTag, Long idMonitoring, Character networkType, Long date);

	public Map<String, Long> getHashTagsCount(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
}
