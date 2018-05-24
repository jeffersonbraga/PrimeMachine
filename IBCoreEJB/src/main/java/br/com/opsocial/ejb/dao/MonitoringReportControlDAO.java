package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReportControl;

@Local
public interface MonitoringReportControlDAO extends AbstractDAO<MonitoringReportControl> {
	
	public boolean hasEntity(Long idMonitoring, String type, Long date);
	public MonitoringReportControl getEntity(Long idMonitoring, String type, Long date);
	
	public List<Long> getDates(Long idMonitoring, String type);

}
