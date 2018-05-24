package br.com.opsocial.ejb.entity.monitoring;

import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;

@Local
public interface MonitoringTagReportDAO extends AbstractDAO<MonitoringTagReport> {
	
	public boolean hasEntity(Long idMonitoring, Long idTag, Character networkType, Long date);
	public MonitoringTagReport getEntity(Long idMonitoring, Long idTag, Character networkType, Long date);
	
	public Map<Character, Long> getTaggedPostsPerNetwork(Long idMonitoring, Long idTag, Long dateFrom, Long dateUntil);
	
	public Map<Character, Long> getTaggedPostsPerNetwork(Long idMonitoring, String tags, Long dateFrom, Long dateUntil);

}
