package br.com.opsocial.ejb.dao;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringHashTag;

@Stateless
public class MonitoringHashTagDAOImpl extends AbstractDAOImpl<MonitoringHashTag> implements MonitoringHashTagDAO {

	@Override
	public boolean hasEntity(String hashTag, Long idMonitoring, Character networkType, Long date) {
		
		sql = "SELECT mht FROM MonitoringHashTag mht WHERE mht.hashTag = :hashTag " +
				"AND mht.monitoring.idMonitoring = :idMonitoring " +
				"AND mht.networkType = :networkType " +
				"AND mht.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("hashTag", hashTag);
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
	public MonitoringHashTag getEntity(String hashTag, Long idMonitoring, Character networkType, Long date) {
		
		sql = "SELECT mht FROM MonitoringHashTag mht WHERE mht.hashTag = :hashTag " +
				"AND mht.monitoring.idMonitoring = :idMonitoring " +
				"AND mht.networkType = :networkType " +
				"AND mht.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("hashTag", hashTag);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		
		try {
			return (MonitoringHashTag) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Map<String, Long> getHashTagsCount(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		
		sql = "SELECT hashtag, CAST(SUM(hashtagscount) AS BIGINT) AS hashtagssum FROM opsocial.monitoringhashtags " +
				"WHERE idmonitoring = " + idMonitoring + " AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +
				"GROUP BY hashtag " +
				"ORDER BY hashtagssum DESC " +
				"OFFSET " + offset + " LIMIT " + limit;

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> hashTagsCount = new LinkedHashMap<String, Long>();
		for(Object[] object : result) {
			hashTagsCount.put((String) object[0], (Long) object[1]);
		}

		return hashTagsCount;
	}

}
