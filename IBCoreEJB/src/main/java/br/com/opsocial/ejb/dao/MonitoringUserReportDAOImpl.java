package br.com.opsocial.ejb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringUserReport;

@Stateless
public class MonitoringUserReportDAOImpl extends AbstractDAOImpl<MonitoringUserReport> implements MonitoringUserReportDAO {

	@Override
	public boolean hasEntity(Long idMonitoring, String property, Long date) {
	
		sql = "SELECT mur FROM MonitoringUserReport mur WHERE mur.monitoring.idMonitoring = :idMonitoring " +
				"AND mur.property = :property " +
				"AND mur.date = :date ";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("property", property);
		query.setParameter("date", date);
		
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public MonitoringUserReport getEntity(Long idMonitoring, String property, Long date) {
		
		sql = "SELECT mur FROM MonitoringUserReport mur WHERE mur.monitoring.idMonitoring = :idMonitoring " +
				"AND mur.property = :property " +
				"AND mur.date = :date ";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("property", property);
		query.setParameter("date", date);
		
		try {
			return (MonitoringUserReport) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Map<Date, Long> getPostsUsersPerDayCount(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT date, CAST(SUM(value) AS bigint) FROM opsocial.monitoringusersreports " +
				"WHERE idmonitoring = " + idMonitoring + " AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +
					"AND property = '" + MonitoringUserReport.USERS_COUNT + "' " +
				"GROUP BY date " +
				"ORDER BY date";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<Date, Long> postsUsersPerDay = new LinkedHashMap<Date, Long>();
		for(Object[] object : result) {
			postsUsersPerDay.put(new Date((Long) object[0] * 1000L), (Long) object[1]);
		}

		return postsUsersPerDay;
	}

	@Override
	public Map<String, Long> getPostsUsersGenderCount(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT property, CAST(SUM(value) AS bigint) FROM opsocial.monitoringusersreports " +
				"WHERE idmonitoring = " + idMonitoring + " AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +
				"AND property IN('" + MonitoringUserReport.USERS_FEMALE_COUNT + "','" + MonitoringUserReport.USERS_MALE_COUNT + "') " + 
				"GROUP BY property";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> postsUsersGender = new HashMap<String, Long>();
		for(Object[] object : result) {
			postsUsersGender.put((String) object[0], (Long) object[1]);
		}

		return postsUsersGender;
	}

	@Override
	public Long getPostsUsersTotal(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(SUM(value) AS bigint) FROM opsocial.monitoringusersreports " +
				"WHERE idmonitoring = " + idMonitoring + " AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +
					"AND property = '" + MonitoringUserReport.USERS_COUNT + "'";
	
		query = em.createNativeQuery(sql);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

}
