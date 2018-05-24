package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReportControl;

@Remote
public interface MaintenanceMonitoringReportControlRemote extends AbstractDAS<MonitoringReportControl> {

	public boolean hasEntity(Long idMonitoring, String type, Long date);
	public MonitoringReportControl getEntity(Long idMonitoring, String type, Long date);
	
	public List<Long> getDates(Long idMonitoring, String type);
	
}
