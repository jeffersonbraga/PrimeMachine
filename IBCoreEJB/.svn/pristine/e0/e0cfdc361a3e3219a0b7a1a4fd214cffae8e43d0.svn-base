package br.com.opsocial.ejb.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLink;

@Stateless
public class MonitoringLinkDAOImpl extends AbstractDAOImpl<MonitoringLink> implements MonitoringLinkDAO {

	@Override
	public boolean hasEntity(String link, Long idMonitoring, Character networkType, Long date) {
		
		sql = "SELECT ml FROM MonitoringLink ml WHERE ml.link = :link " +
				"AND ml.monitoring.idMonitoring = :idMonitoring " +
				"AND ml.networkType = :networkType " +
				"AND ml.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("link", link);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public MonitoringLink getEntity(String link, Long idMonitoring, Character networkType, Long date) {
		
		sql = "SELECT ml FROM MonitoringLink ml WHERE ml.link = :link " +
				"AND ml.monitoring.idMonitoring = :idMonitoring " +
				"AND ml.networkType = :networkType " +
				"AND ml.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("link", link);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		
		try {
			return (MonitoringLink) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Map<String, Long> getLinksCount(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		
		sql = "SELECT link, CAST(SUM(linkscount) AS BIGINT) AS linkssum FROM opsocial.monitoringlinks " +
				"WHERE idmonitoring = " + idMonitoring + " AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +
				"GROUP BY link " +
				"ORDER BY linkssum DESC  " +
				"OFFSET " + offset + " LIMIT " + limit;
		
		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> linksCount = new LinkedHashMap<String, Long>();
		for(Object[] object : result) {
			linksCount.put((String) object[0], (Long) object[1]);
		}

		return linksCount;
	}

	
	
}
