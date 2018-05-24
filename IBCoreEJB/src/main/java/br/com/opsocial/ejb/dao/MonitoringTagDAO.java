package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTag;

@Local
public interface MonitoringTagDAO extends AbstractDAO<MonitoringTag> {

	public List<MonitoringTag> listByMonitoring(Long idMonitoring, Long idAccount);
	
	public List<MonitoringTag> listGlobalTags(Long idAccount);
	
	public List<MonitoringTag> listForMonitoring(Long idMonitoring, Long idAccount);
	
}
