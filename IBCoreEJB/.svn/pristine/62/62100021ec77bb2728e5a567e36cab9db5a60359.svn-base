package br.com.opsocial.ejb.dao;

import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLink;

@Local
public interface MonitoringLinkDAO extends AbstractDAO<MonitoringLink> {

	public boolean hasEntity(String link, Long idMonitoring, Character networkType, Long date);
	public MonitoringLink getEntity(String link, Long idMonitoring, Character networkType, Long date);
	
	public Map<String, Long> getLinksCount(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	
}
