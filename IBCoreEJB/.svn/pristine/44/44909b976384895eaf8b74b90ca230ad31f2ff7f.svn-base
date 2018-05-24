package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringInfluentialUser;

@Stateless
public class MonitoringInfluentialUserDAOImpl extends AbstractDAOImpl<MonitoringInfluentialUser> implements MonitoringInfluentialUserDAO {

	@Override
	public boolean hasEntity(String idUser, Long idMonitoring, Character networkType, Long date) {
		
		sql = "SELECT miu FROM MonitoringInfluentialUser miu WHERE miu.monitoringUser.idUser = :idUser " +
				"AND miu.monitoring.idMonitoring = :idMonitoring " +
				"AND miu.monitoringUser.networkType = :networkType " +
				"AND miu.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("idUser", idUser);
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
	public MonitoringInfluentialUser getEntity(String idUser, Long idMonitoring, Character networkType, Long date) {
		
		sql = "SELECT miu FROM MonitoringInfluentialUser miu WHERE miu.monitoringUser.idUser = :idUser " +
				"AND miu.monitoring.idMonitoring = :idMonitoring " +
				"AND miu.monitoringUser.networkType = :networkType " +
				"AND miu.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("idUser", idUser);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		
		try {
			return (MonitoringInfluentialUser) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public List<MonitoringInfluentialUser> getUsersWithMoreFollowers(Long idMonitoring, Character networkType, Long reportDate, Integer limit) {
		
		sql = "SELECT miu FROM MonitoringInfluentialUser miu WHERE miu.monitoring.idMonitoring = :idMonitoring " +
				"AND miu.monitoringUser.networkType = :networkType " +
				"AND miu.date = :reportDate " +
				"AND miu.postsCount > 0 " + 
				"ORDER BY miu.followers DESC";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("networkType", networkType);
		query.setParameter("reportDate", reportDate);
		query.setFirstResult(0);
		query.setMaxResults(limit);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<MonitoringInfluentialUser>();
		}
	}

	@Override
	public Map<String, Long> getMostInfluentialFans(Long idMonitoring, Character networkType, Long dateFrom,
			Long dateUntil, Integer offset, Integer limit) {
		
		sql = "SELECT iduser, CAST(AVG(followers) * SUM(postscount) AS bigint) AS influenceindex FROM opsocial.monitoringinfluentialusers " +
					"WHERE idmonitoring = " + idMonitoring + " AND networktype = '" + networkType + "' " +
							"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") AND postscount > 0 " +
				"GROUP BY iduser " +
				"ORDER BY influenceindex DESC " + 
				"LIMIT " + limit + " OFFSET " + offset;

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> mostInfluentialFans = new LinkedHashMap<String, Long>();
		for(Object[] object : result) {
			mostInfluentialFans.put((String) object[0], (Long) object[1]);
		}

		return mostInfluentialFans;
	}

	@Override
	public MonitoringInfluentialUser getMostInfluentialFan(String idUser, Long idMonitoring, Character networkType,
			Long dateFrom, Long dateUntil) {
		
		sql = "SELECT miu FROM MonitoringInfluentialUser miu WHERE miu.monitoringUser.idUser = :idUser " +
				"AND miu.monitoring.idMonitoring = :idMonitoring " +
				"AND miu.monitoringUser.networkType = :networkType " +
				"AND (miu.date >= :dateFrom AND miu.date <= :dateUntil) " + 
				"ORDER BY miu.date DESC";
		
		query = em.createQuery(sql);
		query.setParameter("idUser", idUser);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("networkType", networkType);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setFirstResult(0);
		query.setMaxResults(1);
		
		try {
			return (MonitoringInfluentialUser) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long getMostInfluentialFanPostsCount(String idUser, Long idMonitoring, Character networkType, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT SUM(miu.postsCount) FROM MonitoringInfluentialUser miu WHERE miu.monitoringUser.idUser = :idUser " +
				"AND miu.monitoring.idMonitoring = :idMonitoring " +
				"AND miu.monitoringUser.networkType = :networkType " +
				"AND (miu.date >= :dateFrom AND miu.date <= :dateUntil)";
		
		query = em.createQuery(sql);
		query.setParameter("idUser", idUser);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("networkType", networkType);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
		
	}
	
	@Override
	public Map<String, Long> getMostPositiveFans(Long idMonitoring, Character networkType, Long dateFrom, 
			Long dateUntil, Integer offset, Integer limit) {
		
		sql = "SELECT iduser, CAST(SUM(positivepostscount) AS BIGINT) AS positiveposts, CAST(SUM(postscount) AS BIGINT) AS allposts " +
				"FROM opsocial.monitoringinfluentialusers " +
				"WHERE idmonitoring = " + idMonitoring + " AND networktype = '" + networkType + "' " +
						"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") AND positivepostscount > 0 " +
				"GROUP BY iduser " +
				"ORDER BY positiveposts DESC, allposts DESC " +
				"OFFSET " + offset + " LIMIT " + limit;

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> mostPositiveFans = new LinkedHashMap<String, Long>();
		for(Object[] object : result) {
			mostPositiveFans.put((String) object[0], (Long) object[1]);
		}

		return mostPositiveFans;
	}

	@Override
	public Map<String, Long> getMostNegativeFans(Long idMonitoring, Character networkType, Long dateFrom, Long dateUntil,
			Integer offset, Integer limit) {
		
		sql = "SELECT iduser, CAST(SUM(negativepostscount) AS BIGINT) AS negativeposts, CAST(SUM(postscount) AS BIGINT) AS allposts " +
				"FROM opsocial.monitoringinfluentialusers " +
				"WHERE idmonitoring = " + idMonitoring + " AND networktype = '" + networkType + "' " +
						"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") AND negativepostscount > 0 " +
				"GROUP BY iduser " +
				"ORDER BY negativeposts DESC, allposts DESC " +
				"OFFSET " + offset + " LIMIT " + limit;

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> mostNegativeFans = new LinkedHashMap<String, Long>();
		for(Object[] object : result) {
			mostNegativeFans.put((String) object[0], (Long) object[1]);
		}

		return mostNegativeFans;
	}

	@Override
	public Long getPostsUsersPerDayCount(Long idMonitoring, Long date) {
		
		sql = "SELECT date, COUNT(*) FROM opsocial.monitoringinfluentialusers " +
				"WHERE idmonitoring = " + idMonitoring + " AND date = " + date + " AND postscount > 0 " +
				"GROUP BY date";

		query = em.createNativeQuery(sql);

		Long value = 0L;
		try {
			
			Object[] result = (Object[]) query.getSingleResult();
			
			value = (Long) result[1];
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	@Override
	public Map<Date, Long> getPostsUsersPerDayCount(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT date, count(*) FROM opsocial.monitoringinfluentialusers " +
				"WHERE idmonitoring = " + idMonitoring + " AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +
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
	public Long getPostsUsersRecurrent(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT COUNT(*) FROM (SELECT iduser, networktype FROM opsocial.monitoringinfluentialusers " +
				"WHERE idmonitoring = " + idMonitoring + " AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") AND postscount > 0 " +
				"GROUP BY iduser, networktype " +
				"HAVING count(*) > 1) AS temptable";
	
		query = em.createNativeQuery(sql);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getPostsUsersPerGenderCount(Long idMonitoring, Long date, String gender) {
		
		sql = "SELECT mu.sex, COUNT(*) FROM opsocial.monitoringusers AS mu " +
				"INNER JOIN opsocial.monitoringinfluentialusers AS miu ON " +
				"mu.iduser = miu.iduser AND mu.networktype = miu.networktype " + 
				"WHERE mu.sex = '" + gender + "' AND miu.idmonitoring = " + idMonitoring + " " +
					"AND miu.date = " + date + " AND miu.postscount > 0 " +  
				"GROUP BY mu.sex";
		
		query = em.createNativeQuery(sql);
		
		Long value = 0L;
		try {
			
			Object[] result = (Object[]) query.getSingleResult();
			
			value = (Long) result[1];
			
		} catch (NoResultException e) {
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return value;
	}
	
	@Override
	public Map<String, Long> getPostsUsersGenderCount(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT mu.sex, COUNT(DISTINCT(miu.iduser, miu.networktype)) FROM opsocial.monitoringusers AS mu " +
				"INNER JOIN opsocial.monitoringinfluentialusers AS miu ON " +
				"mu.iduser = miu.iduser AND mu.networktype = miu.networktype " +
				"WHERE mu.sex <> '' AND miu.idmonitoring = " + idMonitoring + " " +
					"AND (miu.date >= " + dateFrom + " AND miu.date <= " + dateUntil + ") " +
				"GROUP BY mu.sex";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> postsUsersGender = new HashMap<String, Long>();
		for(Object[] object : result) {
			postsUsersGender.put((String) object[0], (Long) object[1]);
		}

		return postsUsersGender;
	}

}
