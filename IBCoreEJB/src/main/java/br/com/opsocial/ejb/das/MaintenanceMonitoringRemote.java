package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Remote
public interface MaintenanceMonitoringRemote extends AbstractDAS<Monitoring> {
	
	public Boolean hasMonitoring(Long idAccount, String name, Character type);
	
	public List<Monitoring> getMonitoringsByAccount(Long idAccount);
}
