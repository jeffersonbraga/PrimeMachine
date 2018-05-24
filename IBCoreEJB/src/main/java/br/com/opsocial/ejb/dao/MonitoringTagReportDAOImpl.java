package br.com.opsocial.ejb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTagReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTagReportDAO;

@Stateless
public class MonitoringTagReportDAOImpl extends AbstractDAOImpl<MonitoringTagReport> implements MonitoringTagReportDAO {

	@Override
	public boolean hasEntity(Long idMonitoring, Long idTag, Character networkType, Long date) {
		
		sql = "SELECT mtr FROM MonitoringTagReport mtr WHERE mtr.monitoring.idMonitoring = :idMonitoring " +
				"AND mtr.monitoringTag.idTag = :idTag " +
				"AND mtr.networkType = :networkType " +
				"AND mtr.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("idTag", idTag);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public MonitoringTagReport getEntity(Long idMonitoring, Long idTag, Character networkType, Long date) {
		
		sql = "SELECT mtr FROM MonitoringTagReport mtr WHERE mtr.monitoring.idMonitoring = :idMonitoring " +
				"AND mtr.monitoringTag.idTag = :idTag " +
				"AND mtr.networkType = :networkType " +
				"AND mtr.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("idTag", idTag);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		
		try {
			return (MonitoringTagReport) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public Map<Character, Long> getTaggedPostsPerNetwork(Long idMonitoring, Long idTag, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT mtr.networkType, SUM(mtr.value) FROM MonitoringTagReport mtr " +
				"WHERE mtr.monitoring.idMonitoring = :idMonitoring AND (mtr.date >= :dateFrom AND mtr.date <= :dateUntil) " +
				"AND mtr.monitoringTag.idTag = :idTag " +
				"GROUP BY mtr.networkType";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setParameter("idTag", idTag);
		
		List<Object[]> result = query.getResultList();
		
		Map<Character, Long> taggedPostsPerNetwork = new HashMap<Character, Long>();
		for (Object[] object : result) {
			taggedPostsPerNetwork.put((Character) object[0], (Long) object[1]);
		}
		
		return taggedPostsPerNetwork;
	}
	
	@Override
	public Map<Character, Long> getTaggedPostsPerNetwork(Long idMonitoring, String tags, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT mtr.networkType, SUM(mtr.value) FROM MonitoringTagReport mtr " +
				"WHERE mtr.monitoring.idMonitoring = :idMonitoring AND (mtr.date >= :dateFrom AND mtr.date <= :dateUntil) " +
				"AND mtr.monitoringTag.idTag in (" + tags + 
				") GROUP BY mtr.networkType";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		List<Object[]> result = query.getResultList();
		
		Map<Character, Long> taggedPostsPerNetwork = new HashMap<Character, Long>();
		for (Object[] object : result) {
			taggedPostsPerNetwork.put((Character) object[0], (Long) object[1]);
		}
		
		return taggedPostsPerNetwork;
	}

}
