package br.com.opsocial.ejb.dao;

import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLocationReport;

@Local
public interface MonitoringLocationReportDAO extends AbstractDAO<MonitoringLocationReport> {

	public boolean hasEntity(Long idMonitoring, String location, Long date);
	public MonitoringLocationReport getEntity(Long idMonitoring, String location, Long date);
	
	public Map<String, Long> getPostsCitiesCount(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	
	
}
