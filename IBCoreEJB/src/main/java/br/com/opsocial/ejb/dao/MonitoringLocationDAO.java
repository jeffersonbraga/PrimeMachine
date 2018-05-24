package br.com.opsocial.ejb.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLocation;

@Local
public interface MonitoringLocationDAO extends AbstractDAO<MonitoringLocation> {
	
	public Map<String, Long> getPostsCitiesCount(Long idMonitoring, Character networkType, Long dateFrom, Long dateUntil, Integer offset, Integer limit);

	public List<Object[]> getPostsCitiesCountByDay(Long idMonitoring, Long date);
}
