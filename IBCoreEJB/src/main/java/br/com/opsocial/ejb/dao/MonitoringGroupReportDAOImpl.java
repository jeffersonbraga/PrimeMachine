package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringGroupReport;

@Stateless
public class MonitoringGroupReportDAOImpl extends AbstractDAOImpl<MonitoringGroupReport> implements MonitoringGroupReportDAO {

	@Override
	public boolean hasEntity(Long idMonitoring, Long idProfile, Long date) {
		
		sql = "SELECT mgr FROM MonitoringGroupReport mgr WHERE mgr.monitoring.idMonitoring = :idMonitoring " +
				"AND mgr.profile.idProfile = :idProfile " +
				"AND mgr.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("idProfile", idProfile);
		query.setParameter("date", date);
		
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public MonitoringGroupReport getEntity(Long idMonitoring, Long idProfile, Long date) {
		
		sql = "SELECT mgr FROM MonitoringGroupReport mgr WHERE mgr.monitoring.idMonitoring = :idMonitoring " +
				"AND mgr.profile.idProfile = :idProfile " +
				"AND mgr.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("idProfile", idProfile);
		query.setParameter("date", date);
		
		try {
			return (MonitoringGroupReport) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public Long getPostsTotalByGroup(Long idMonitoring, Long idProfile, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT mgr.profile.idProfile, SUM(mgr.value) FROM MonitoringGroupReport mgr WHERE " +
				"mgr.monitoring.idMonitoring = :idMonitoring AND mgr.profile.idProfile = :idProfile AND " +
				"(mgr.date >= :dateFrom AND mgr.date <= :dateUntil) " +
				"GROUP BY mgr.profile.idProfile";	
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("idProfile", idProfile);
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
