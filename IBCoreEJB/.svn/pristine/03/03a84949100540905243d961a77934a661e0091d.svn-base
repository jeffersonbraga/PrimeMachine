package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringGeoPost;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostLocation;

@Local
public interface MonitoringPostLocationDAO extends AbstractDAO<MonitoringPostLocation> {

	public List<MonitoringGeoPost> getGetPosts(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	
}
