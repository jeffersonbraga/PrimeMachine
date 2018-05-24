package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Local
public interface MonitoringDAO extends AbstractDAO<Monitoring> {

	public Boolean hasMonitoring(Long idAccount, String name, Character type);
	public List<Monitoring> getMonitoringsByAccount(Long idAccount);
}



