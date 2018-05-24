package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTag;

@Remote
public interface MaintenanceMonitoringTagRemote extends AbstractDAS<MonitoringTag> {
	
	public List<MonitoringTag> listByMonitoring(Long idMonitoring, Long idAccount);

	public List<MonitoringTag> listGlobalTags(Long idAccount);
	
	public List<MonitoringTag> listForMonitoring(Long idMonitoring, Long idAccount);
	
}
