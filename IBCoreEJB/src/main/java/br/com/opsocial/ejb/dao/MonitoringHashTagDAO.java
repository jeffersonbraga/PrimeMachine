package br.com.opsocial.ejb.dao;

import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringHashTag;

@Local
public interface MonitoringHashTagDAO extends AbstractDAO<MonitoringHashTag> {

	public boolean hasEntity(String hashTag, Long idMonitoring, Character networkType, Long date);
	public MonitoringHashTag getEntity(String hashTag, Long idMonitoring, Character networkType, Long date);
	
	public Map<String, Long> getHashTagsCount(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
}
