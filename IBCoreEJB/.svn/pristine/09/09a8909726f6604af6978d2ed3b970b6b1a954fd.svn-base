package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTermReport;

@Stateless
public class MonitoringTermReportDAOImpl extends AbstractDAOImpl<MonitoringTermReport> implements MonitoringTermReportDAO {
	
	@Override
	public boolean hasEntity(Long idMonitoring, String term, Character networkType, Long date) {
		
		sql = "SELECT mtr FROM MonitoringTermReport mtr WHERE mtr.monitoring.idMonitoring = :idMonitoring " +
				"AND mtr.term = :term " + 
				"AND mtr.networkType = :networkType " + 
				"AND mtr.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
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
	public MonitoringTermReport getEntity(Long idMonitoring, String term, Character networkType, Long date) {
		
		sql = "SELECT mtr FROM MonitoringTermReport mtr WHERE mtr.monitoring.idMonitoring = :idMonitoring " +
				"AND mtr.term = :term " + 
				"AND mtr.networkType = :networkType " + 
				"AND mtr.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		
		try {
			return (MonitoringTermReport) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long getPostsTotalByTerm(Long idMonitoring, String term, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT mtr.term, SUM(mtr.value) FROM MonitoringTermReport mtr WHERE " +
				"mtr.monitoring.idMonitoring = :idMonitoring AND mtr.term = :term AND " +
				"(mtr.date >= :dateFrom AND mtr.date <= :dateUntil) " +
				"GROUP BY mtr.term";	
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		Long postsTotal;
		
		try {
			
			Object[] result = (Object[]) query.getSingleResult();
			
			postsTotal = (Long) result[1];
			
		} catch (NoResultException e) {
			postsTotal = 0L;
		}
		
		return postsTotal;
	}

}
