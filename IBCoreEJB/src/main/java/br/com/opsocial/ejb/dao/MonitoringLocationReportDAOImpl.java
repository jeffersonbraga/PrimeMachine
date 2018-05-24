package br.com.opsocial.ejb.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLocationReport;

@Stateless
public class MonitoringLocationReportDAOImpl extends AbstractDAOImpl<MonitoringLocationReport> implements MonitoringLocationReportDAO {

	@Override
	public boolean hasEntity(Long idMonitoring, String location, Long date) {
		
		sql = "SELECT mur FROM MonitoringLocationReport mur WHERE mur.monitoring.idMonitoring = :idMonitoring " +
				"AND mur.location = :location " +
				"AND mur.date = :date ";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("location", location);
		query.setParameter("date", date);
		
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public MonitoringLocationReport getEntity(Long idMonitoring, String location, Long date) {
		
		sql = "SELECT mur FROM MonitoringLocationReport mur WHERE mur.monitoring.idMonitoring = :idMonitoring " +
				"AND mur.location = :location " +
				"AND mur.date = :date ";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("location", location);
		query.setParameter("date", date);
		
		try {
			return (MonitoringLocationReport) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Map<String, Long> getPostsCitiesCount(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		
		sql = "SELECT location, CAST(SUM(value) AS bigint) AS sumlocation FROM opsocial.monitoringlocationsreports " +
				"WHERE idmonitoring = " + idMonitoring + " AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +
				"GROUP BY location " +
				"ORDER BY sumlocation DESC, location ASC " +
				"OFFSET " + offset + " LIMIT " + limit;

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> postsCitiesCount = new LinkedHashMap<String, Long>();
		for(Object[] object : result) {
			postsCitiesCount.put((String) object[0], (Long) object[1]);
		}

		return postsCitiesCount;
	}

}
