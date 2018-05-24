package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringGeoPost;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostLocation;

@Remote
public interface MaintenanceMonitoringPostLocationRemote extends AbstractDAS<MonitoringPostLocation> {
	
	public List<MonitoringGeoPost> getGetPosts(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit);

}
