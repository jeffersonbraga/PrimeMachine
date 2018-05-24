package br.com.opsocial.ejb.das;

import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTagReport;

@Remote
public interface MaintenanceMonitoringTagReportRemote extends AbstractDAS<MonitoringTagReport> {

	public boolean hasEntity(Long idMonitoring, Long idTag, Character networkType, Long date);
	public MonitoringTagReport getEntity(Long idMonitoring, Long idTag, Character networkType, Long date);
	
	public Map<Character, Long> getTaggedPostsPerNetwork(Long idMonitoring, Long idTag, Long dateFrom, Long dateUntil);
	
	public Map<Character, Long> getTaggedPostsPerNetwork(Long idMonitoring, String tags, Long dateFrom, Long dateUntil);
	
}
