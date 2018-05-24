package br.com.opsocial.ejb.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringQualificationReport;

@Stateless
public class MonitoringQualificationReportDAOImpl extends AbstractDAOImpl<MonitoringQualificationReport> implements MonitoringQualificationReportDAO {

	@Override
	public boolean hasEntity(Long idMonitoring, String term, Character qualification, Character networkType, Long date) {
		
		sql = "SELECT mqr FROM MonitoringQualificationReport mqr WHERE mqr.monitoring.idMonitoring = :idMonitoring " +
				"AND mqr.term = :term " +
				"AND mqr.qualification = :qualification " +
				"AND mqr.networkType = :networkType " +
				"AND mqr.date = :date ";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
		query.setParameter("qualification", qualification);
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
	public MonitoringQualificationReport getEntity(Long idMonitoring, String term, Character qualification, Character networkType, Long date) {
		
		sql = "SELECT mqr FROM MonitoringQualificationReport mqr WHERE mqr.monitoring.idMonitoring = :idMonitoring " +
				"AND mqr.term = :term " +
				"AND mqr.qualification = :qualification " +
				"AND mqr.networkType = :networkType " +
				"AND mqr.date = :date ";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
		query.setParameter("qualification", qualification);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		
		try {
			return (MonitoringQualificationReport) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public Map<Character, Long> getSentimentAnalysis(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT mqr.qualification, SUM(mqr.value) FROM MonitoringQualificationReport mqr " + 
				"WHERE mqr.monitoring.idMonitoring = :idMonitoring AND (mqr.date >= :dateFrom AND mqr.date <= :dateUntil) " +
				"GROUP BY mqr.qualification";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		List<Object[]> result = query.getResultList();
		
		Map<Character, Long> sentimentAnalysis = new HashMap<Character, Long>();
		for (Object[] object : result) {
			sentimentAnalysis.put((Character) object[0], (Long) object[1]);
		}
		
		return sentimentAnalysis;
	}
	
	@Override
	public Map<Character, Long> getSentimentAnalysisByNetwork(Long idMonitoring, Long dateFrom, Long dateUntil, String qualifications) {
		
		sql = "SELECT mqr.network, SUM(mqr.value) FROM MonitoringQualificationReport mqr " + 
				"WHERE mqr.monitoring.idMonitoring = :idMonitoring AND (mqr.date >= :dateFrom AND mqr.date <= :dateUntil) " +
				" and mqr.qualification in (" + qualifications + ") GROUP BY mqr.network";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		List<Object[]> result = query.getResultList();
		
		Map<Character, Long> sentimentAnalysis = new HashMap<Character, Long>();
		for (Object[] object : result) {
			sentimentAnalysis.put((Character) object[0], (Long) object[1]);
		}
		
		return sentimentAnalysis;
	}

	@Override
	public Map<Character, Long> getSentimentAnalysis(Long idMonitoring, String term, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT mqr.qualification, SUM(mqr.value) FROM MonitoringQualificationReport mqr " + 
				"WHERE mqr.monitoring.idMonitoring = :idMonitoring AND (mqr.date >= :dateFrom AND mqr.date <= :dateUntil) " +
				"AND mqr.term = :term " +
				"GROUP BY mqr.qualification";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setParameter("term", term);
		
		List<Object[]> result = query.getResultList();
		
		Map<Character, Long> sentimentAnalysis = new HashMap<Character, Long>();
		for (Object[] object : result) {
			sentimentAnalysis.put((Character) object[0], (Long) object[1]);
		}
		
		return sentimentAnalysis;
	}

}
