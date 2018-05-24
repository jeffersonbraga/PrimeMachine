package br.com.opsocial.ejb.dao;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.text.WordUtils;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.idclass.TwitterFollowerId;
import br.com.opsocial.ejb.entity.report.TwitterFollower;
import br.com.opsocial.ejb.utils.reports.UtilReports;

@Stateless
public class TwitterFollowerDAOImpl extends AbstractDAOImpl<TwitterFollower> implements TwitterFollowerDAO {

	@Override
	public TwitterFollower save(TwitterFollower twitterFollower) throws Exception {

		try {

			TwitterFollowerId twitterFollowerId = new TwitterFollowerId();
			twitterFollowerId.setFollowerId(twitterFollower.getFollowerId());
			twitterFollowerId.setIdProfile(twitterFollower.getIdProfile());
			
			TwitterFollower objTmp = getById(twitterFollowerId);

			if(objTmp != null) {

				try {
					validateVersion(objTmp, twitterFollower);
				} catch (IllegalStateException ex) {
					throw new OptimisticLockException();
				}

				em.merge(twitterFollower);

				this.flush();

			} else {

				em.persist(twitterFollower);

				this.flush();
			}

		} catch (Exception e) {
			throw new Exception(e);
		}

		return twitterFollower;
	}
	
	@Override
	public void delete(TwitterFollower twitterFollower) throws IllegalArgumentException, IllegalStateException, Exception {
		
		TwitterFollowerId twitterFollowerId = new TwitterFollowerId();
		twitterFollowerId.setFollowerId(twitterFollower.getFollowerId());
		twitterFollowerId.setIdProfile(twitterFollower.getIdProfile());
		
		try {
			
			TwitterFollower objTmp = getById(twitterFollowerId);
			
			try {
				validateVersion(objTmp, twitterFollower);
			} catch (IllegalStateException ex) {
				throw new Exception(ex.getMessage(), ex);
			}
			
			try {
				
				twitterFollower = em.merge(twitterFollower);
				em.remove(twitterFollower);
				
				this.flush();
					
			} catch (IllegalStateException ex) {				
				throw new Exception(ex.getMessage(), ex);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	@Override
	public long[] getFollowersNotUpdated(Long idProfile, Long updateDate) {
		
		sql = "SELECT tf.followerId FROM TwitterFollower tf WHERE tf.idProfile = :idProfile AND tf.lastUpdateDate < :updateDate";
		
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("updateDate", updateDate);
		query.setFirstResult(0);
		query.setMaxResults(100);

		List<Long> followersId = query.getResultList();
		
		return ArrayUtils.toPrimitive(followersId.toArray(new Long[followersId.size()]));
	}

	@Override
	public boolean deactivateFollowersTemporarily(Long idProfile) {
		
		sql = "UPDATE TwitterFollower tf SET tf.isStillFollower = :isStillFollower WHERE tf.idProfile = :idProfile";
		
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("isStillFollower", UtilReports.NOT_A_FOLLOWER);
		
		try {
			
			query.executeUpdate();
			return true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteUnfollowers(Long idProfile) {
		
		sql = "DELETE FROM TwitterFollower tf WHERE tf.idProfile = :idProfile AND tf.isStillFollower = :isStillFollower";
		
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("isStillFollower", UtilReports.NOT_A_FOLLOWER);
		
		try {
			
			query.executeUpdate();
			return true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Map<String, Long> getFollowersOfFollowers(Long idProfile) {
		
		sql = "SELECT ranges.\"range\", COUNT(tf) AS \"número de seguidores\" " +
				"FROM (" +
					"SELECT 0 minRange, 300 maxRange, '0-300' \"range\" " +
					"UNION ALL " +
					"SELECT 301, 600, '300-600' " +
					"UNION ALL " +
					"SELECT 601, 1000, '600-1000' " +
					"UNION ALL " +
					"SELECT 1001, 2000, '1000-2000' " +
					"UNION ALL " +
					"SELECT 2001, 5000, '2000-5000' " +
					"UNION ALL " +
					"SELECT 5001, 1000000000, '5000+' " +
				") AS ranges " +
				"LEFT JOIN opsocial.twitterfollowers AS tf " +
				"ON tf.followerscount BETWEEN ranges.minRange AND ranges.maxRange " +  
				"WHERE tf.idprofile = " + idProfile + " " +
				"GROUP BY ranges.range";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> engagementPercentage = new HashMap<String, Long>();
		for (Object[] object : result) {
			engagementPercentage.put(((String) object[0]), (Long) object[1]);
		}
		
		return engagementPercentage;
	}

	@Override
	public Integer[] getWomenMenFollowersPerc(Long idProfile) {
		
		sql = "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.countfemale) / (SELECT NULLIF(COUNT(tf), 0) FROM opsocial.twitterfollowers as tf " +
				"WHERE tf.idprofile = " + idProfile + " AND (tf.gender = 'M' OR tf.gender = 'F'))), 0), 0) AS integer) AS perc FROM " +
				"(SELECT COUNT(tf) AS countfemale FROM opsocial.twitterfollowers as tf " +
				"WHERE tf.idprofile = " + idProfile + " AND tf.gender = 'F') AS r " +
				"UNION ALL " +
				"SELECT CAST(COALESCE(ROUND(100 * (SUM(r.countmale) / (SELECT NULLIF(COUNT(tf), 0) FROM opsocial.twitterfollowers as tf " +
				"WHERE tf.idprofile = " + idProfile + " AND (tf.gender = 'M' OR tf.gender = 'F'))), 0), 0) AS integer) AS perc FROM " +
				"(SELECT COUNT(tf) AS countmale FROM opsocial.twitterfollowers as tf " +
				"WHERE tf.idprofile = " + idProfile + " AND tf.gender = 'M') AS r";
		
		query = em.createNativeQuery(sql);
		
		Integer[] result = new Integer[2];
		
		try {
			
			List<Integer> results = (List<Integer>) query.getResultList();
			
			result[0] = results.get(0);
			result[1] = results.get(1);
			
		} catch (Exception e) {
			
			result[0] = 0;
			result[1] = 0;
		}
		
		return result;
	}

	@Override
	public Long getAverageFollowersOfFollowers(Long idProfile) {
		
		sql = "SELECT AVG(tf.followersCount) FROM TwitterFollower tf " +
				"WHERE tf.idProfile = :idProfile";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);

		try {
			return ((Double) query.getSingleResult()).longValue();
		} catch (NoResultException e) {
			return 0L;
		} catch (NullPointerException e) {
			return 0L;
		}
	}

	@Override
	public Long getMedianFollowersOfFollowers(Long idProfile) {
		
		sql = "WITH y AS (" +
				"SELECT tf.followerscount , row_number() OVER (ORDER BY tf.followerscount) AS rn " +
				"FROM opsocial.twitterfollowers tf " +
				"WHERE  tf.followerscount IS NOT NULL AND tf.idprofile = " + idProfile +
				"), c AS (" +
				"SELECT count(*) As ct " +
				"FROM opsocial.twitterfollowers tf " +
				"WHERE tf.idprofile = " + idProfile +
				")"+ 
				"SELECT CASE WHEN c.ct % 2 = 0 THEN " +
				"round((SELECT avg(followerscount) FROM y WHERE y.rn IN (c.ct / 2, c.ct/2+1)), 3) " +
				"ELSE " +
                "(SELECT followerscount  FROM y WHERE y.rn = (c.ct+1) / 2) " +
                "END AS median " +
                "FROM c;";

		query = em.createNativeQuery(sql);

		try {
			return ((BigDecimal) query.getSingleResult()).longValue();
		} catch (NoResultException e) {
			return 0L;
		} catch (NullPointerException e) {
			return 0L;
		}
	}

	@Override
	public Map<String, Long> getFollowersActivity(Long idProfile) {
		
		Calendar untilDate = new GregorianCalendar();
		untilDate.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		untilDate.set(Calendar.AM_PM, Calendar.AM);
		untilDate.set(Calendar.HOUR_OF_DAY, 0);
		untilDate.set(Calendar.MINUTE, 0);
		untilDate.set(Calendar.SECOND, 0);
		
		Long date = untilDate.getTimeInMillis() / 1000L;
		
		sql = "SELECT ranges.\"range\", COUNT(tf) AS \"Seguidores\" " +
				"FROM (" +
				"SELECT " + (date - 86400L) + " minRange, " + (date - 1L) + " maxRange, '1 dia' \"range\" " +
				"UNION ALL " +
				"SELECT " + (date - 172800L) + ", " + (date - 86401L) + ", '2 dias' " +
				"UNION ALL " +
				"SELECT " + (date - 345600L) + ", " + (date - 172801L) + ", '4 dias' " +
				"UNION ALL " +
				"SELECT " + (date - 604800L) + ", " + (date - 345601L) + ", '1 sem.' " +
				"UNION ALL " +
				"SELECT " + (date - 1209600L) + ", " + (date - 604801L) + ", '2 sem.' " +
				"UNION ALL " +
				"SELECT " + (date - 2592000L) + ", " + (date - 1209601L) + ", '1 mês' " +
				"UNION ALL " +
				"SELECT " + (date - 7776000L) + ", " + (date - 2592001L) + ", '3 meses' " +
				") AS ranges " +
				"LEFT JOIN opsocial.twitterfollowers AS tf " +
				"ON tf.lasttweetdate BETWEEN ranges.minRange AND ranges.maxRange " +  
				"WHERE tf.idprofile = " + idProfile + " " +
				"GROUP BY ranges.range " +
				"ORDER BY \"Seguidores\" DESC";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> engagementPercentage = new LinkedHashMap<String, Long>();
		for (Object[] object : result) {
			engagementPercentage.put(((String) object[0]), (Long) object[1]);
		}
		
		return engagementPercentage;
	}

	@Override
	public Map<String, Long> getCitiesWithMoreFollowers(Long idProfile, Integer offset, Integer limit) {
		
		sql = "SELECT LOWER(tf.location) AS loc, COUNT(tf) AS countfollowers FROM opsocial.twitterfollowers tf " +
				"WHERE tf.idprofile = " + idProfile + " AND tf.location <> '' " +
				"GROUP BY loc " +
				"ORDER BY countfollowers DESC";

		query = em.createNativeQuery(sql);
		query.setFirstResult(offset);
		if(limit != null) {
			query.setMaxResults(limit);
		}
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> engagementPercentage = new LinkedHashMap<String, Long>();
		for (Object[] object : result) {
			engagementPercentage.put(WordUtils.capitalizeFully(((String) object[0])), (Long) object[1]);
		}
			
		return engagementPercentage;
	}

	@Override
	public List<TwitterFollower> getMostInfluentialFollowers(Long idProfile, Integer offset, Integer limit) {
		
		sql = "SELECT tf FROM TwitterFollower tf " +
				"WHERE tf.idProfile = :idProfile " +
				"ORDER BY tf.followersCount DESC";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		return query.getResultList();
	}

	@Override
	public List<TwitterFollower> getMostRecentFollowers(Long idProfile, Integer offset, Integer limit) {
		
		sql = "SELECT tf FROM TwitterFollower tf " +
				"WHERE tf.idProfile = :idProfile AND tf.followingDate IS NOT NULL " +
				"ORDER BY tf.followingDate DESC";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		return query.getResultList();
	}

}
