package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.report.TwitterStatus;
import br.com.opsocial.ejb.utils.reports.UtilReports;

@Stateless
public class TwitterStatusDAOImpl extends AbstractDAOImpl<TwitterStatus> implements TwitterStatusDAO {

	@Override
	public List<TwitterStatus> getTwitterStatus(String where, String order, Integer limit,
			Integer offset) {
	
		try {
			sql = "SELECT ts FROM TwitterStatus ts WHERE "+ where.trim() + " ORDER BY " +order;
			query = em.createQuery(sql);
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<TwitterStatus>();
		}
	}

	@Override
	public List<TwitterStatus> getTWitterStatus(String where, String order) {
		
		try {
			sql = "SELECT ts FROM TwitterStatus ts WHERE "+ where.trim() + " ORDER BY " +order.trim();
			query = em.createQuery(sql);
			
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<TwitterStatus>();
		}
		
	}
	

	@Override
	public List<TwitterStatus> getTwitterStatusByProfile(String idProfile, Integer pagingSince, Integer limit) {
		
		String where = "ts.idProfile = '"+idProfile+"'";
		String order = "ts.createdAt DESC";
		Integer offset = pagingSince;
		
		try {
			sql = "SELECT ts FROM TwitterStatus ts WHERE "+ where.trim() + " ORDER BY " +order;
			query = em.createQuery(sql);
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<TwitterStatus>();
		}
	}

	@Override
	public Map<Integer, Long> getTweetsPerHour(String idProfile, Long dateFrom, Long dateUntil) {

		sql = "SELECT series.hour, count(ts) FROM (SELECT generate_series(0,23) * 1 AS hour) AS series " +
				"LEFT JOIN (SELECT ts.* FROM opsocial.twitterstatus ts " +
				"WHERE ts.idprofile = '" + idProfile + "' AND (ts.createdat >= " + dateFrom + " AND ts.createdat <= " + dateUntil + ")) AS ts ON " +
				"series.hour = date_part('hour', TO_TIMESTAMP(ts.createdat)::timestamp with time zone) " +
				"GROUP BY series.hour " +
				"ORDER BY series.hour";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<Integer, Long> tweetsPerHour = new TreeMap<Integer, Long>();
		for (Object[] object : result) {
			tweetsPerHour.put(((Integer) object[0]), (Long) object[1]);
		}
		
		return tweetsPerHour;
	}

	@Override
	public Map<Integer, Long> getTweetsPerWeekDay(String idProfile, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT series.weekday, count(ts) FROM (SELECT generate_series(0,6) * 1 AS weekday) AS series " +
				"LEFT JOIN (" +
				"SELECT ts.* FROM opsocial.twitterstatus ts " +
				"WHERE ts.idprofile = '" + idProfile + "' AND (ts.createdat >= " + dateFrom + " AND ts.createdat <= " + dateUntil + ")) AS ts ON " +
				"series.weekday = extract(DOW FROM TO_TIMESTAMP(ts.createdat)::timestamp with time zone) " +
				"GROUP BY series.weekday " +
				"ORDER BY series.weekday";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<Integer, Long> tweetsPerWeekDay = new TreeMap<Integer, Long>();
		for (Object[] object : result) {
			tweetsPerWeekDay.put(((Integer) object[0]), (Long) object[1]);
		}
		
		return tweetsPerWeekDay;
	}

	@Override
	public Object[] getTimeWithMoreTweets(String idProfile, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT series.hour, CAST(COALESCE((ROUND(100 * (COUNT(ts)::decimal / " + 
				"(SELECT NULLIF(COALESCE(COUNT(ts), 0), 0) AS counttweets FROM opsocial.twitterstatus ts " +
				"WHERE ts.idprofile = '" + idProfile + "' AND (ts.createdat >= " + dateFrom + " AND ts.createdat <= " + dateUntil + "))), 0)), 0) AS integer) AS tweetspercentage " +
				"FROM (SELECT generate_series(0,23) * 1 AS hour) AS series " +
				"LEFT JOIN (" +
				"SELECT ts.* FROM opsocial.twitterstatus ts " +
				"WHERE ts.idprofile = '" + idProfile + "' AND (ts.createdat >= " + dateFrom + " AND ts.createdat <= " + dateUntil + ")) AS ts ON " +
				"series.hour = date_part('hour', TO_TIMESTAMP(ts.createdat)::timestamp with time zone) " +
				"GROUP BY series.hour " +
				"ORDER BY tweetspercentage DESC, series.hour DESC " +
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);
		
		Object[] result = null;
		
		try {
			
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException e) {
			
			result = new Object[2];
			result[0] = 0;
			result[1] = 0;
		}
		
		return result;
	}

	@Override
	public Object[] getWeekDayWithMoreTweets(String idProfile, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT series.weekday, CAST(COALESCE((ROUND(100 * (COUNT(ts)::decimal / " +
				"(SELECT NULLIF(COALESCE(COUNT(ts), 0), 0) AS counttweets FROM opsocial.twitterstatus ts " +
				"WHERE ts.idprofile = '" + idProfile + "' AND (ts.createdat >= " + dateFrom + " AND ts.createdat <= " + dateUntil + "))), 0)), 0) AS integer) AS tweetspercentage " +
				"FROM (SELECT generate_series(0,6) * 1 AS weekday) AS series " +
				"LEFT JOIN (" +
				"SELECT ts.* FROM opsocial.twitterstatus ts " +
				"WHERE ts.idprofile = '" + idProfile + "' AND (ts.createdat >= " + dateFrom + " AND ts.createdat <= " + dateUntil + ")) AS ts ON " +
				"series.weekday = extract(DOW FROM TO_TIMESTAMP(ts.createdat)::timestamp with time zone) " +
				"GROUP BY series.weekday " +
				"ORDER BY tweetspercentage DESC, series.weekday DESC " +
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);
		
		Object[] result = null;
		
		try {
			
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException e) {
			
			result = new Object[2];
			result[0] = 0;
			result[1] = 0;
		}
		
		return result;
	}

	@Override
	public List<TwitterStatus> getTwitterStatusMostEngaged(String idProfile, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		
		sql = "SELECT ts.idtwitterstatus, ts.idstatus, ts.idprofile, ts.createdat, ts.text, ts.version, SUM(tsr.value) AS engagement FROM opsocial.twitterstatus AS ts " +
				"INNER JOIN opsocial.twitterstatusreports AS tsr ON " +
				"ts.idprofile = tsr.idprofile AND ts.idstatus = tsr.idstatus " +
				"WHERE ts.idprofile = '" + idProfile + "' AND tsr.property " +
				"IN('" + UtilReports.REPLIES_OF_ME_COUNT_ALL + "','" + UtilReports.RETWEETS_OF_ME_COUNT_ALL + "','" + UtilReports.FAVORITE_COUNT_ALL + "') " +
				"AND (ts.createdAt >= " + dateFrom + " AND ts.createdAt <= " + dateUntil + ") " +
				"GROUP BY ts.idtwitterstatus, ts.idstatus, ts.idprofile, ts.createdat, ts.text, ts.version " +
				"ORDER BY engagement DESC " +
				"OFFSET " + offset + " LIMIT " + limit;
		
		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		List<TwitterStatus> twitterStatuses = new ArrayList<TwitterStatus>();
		
		for(Object[] object : result) {
			
			TwitterStatus twitterStatus = new TwitterStatus();
			twitterStatus.setIdTwitterStatus(((Integer) object[0]).longValue());
			twitterStatus.setIdStatus((String) object[1]);
			twitterStatus.setIdProfile((String) object[2]);
			twitterStatus.setCreatedAt((Long) object[3]);
			twitterStatus.setText((String) object[4]);
			twitterStatus.setVersion((Long) object[5]);
			
			twitterStatuses.add(twitterStatus);
		}
		
		return twitterStatuses;
	}
	
	@Override
	public List<TwitterStatus> getTwitterStatus(String idProfile, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		
		sql = "SELECT ts FROM TwitterStatus ts " +
				"WHERE ts.idProfile = :idProfile " +
				"AND (ts.createdAt >= :dateFrom AND ts.createdAt <= :dateUntil ) " +
				"ORDER BY ts.createdAt DESC";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setFirstResult(offset);
		if(limit != null) {
			query.setMaxResults(limit);
		}

		return query.getResultList();
	}

	@Override
	public long[] getTwitterStatusIds(String idProfile, Integer offset, Integer limit) {
		
		try {
			
			sql = "SELECT ts.idStatus FROM TwitterStatus ts WHERE ts.idProfile = :idProfile " + 
							"ORDER BY ts.createdAt DESC";
			
			query = em.createQuery(sql);
			query.setParameter("idProfile", idProfile);
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			
			List<Object> result = query.getResultList();

			long[] statusIds = new long[result.size()];
			for (int i = 0; i < result.size(); i++) {
				statusIds[i] = Long.valueOf(result.get(i).toString());
			}
			
			return statusIds;
			
		} catch (Exception e) {
			e.printStackTrace();
			return new long[0];
		}
		
	}

}
