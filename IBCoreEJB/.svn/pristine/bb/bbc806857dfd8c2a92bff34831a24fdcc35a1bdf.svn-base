package br.com.opsocial.ejb.das;

import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLink;

@Remote
public interface MaintenanceMonitoringLinkRemote extends AbstractDAS<MonitoringLink> {

	public boolean hasEntity(String link, Long idMonitoring, Character networkType, Long date);
	public MonitoringLink getEntity(String link, Long idMonitoring, Character networkType, Long date);
	
	public Map<String, Long> getLinksCount(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	
}
