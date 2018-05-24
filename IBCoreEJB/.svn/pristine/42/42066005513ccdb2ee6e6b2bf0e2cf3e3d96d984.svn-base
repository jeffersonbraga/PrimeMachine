package br.com.opsocial.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReport;
import br.com.opsocial.ejb.utils.monitorings.UtilMonitorings;

@Stateless
public class MonitoringReportDAOImpl extends AbstractDAOImpl<MonitoringReport> implements MonitoringReportDAO {

	@Override
	public boolean hasEntity(Character networkType, Long date, String periodType, String period, Long idMonitoring) {
		
		sql = "SELECT mr FROM MonitoringReport mr WHERE mr.networkType = :networkType " +
				"AND mr.date = :date " + 
				"AND mr.periodType = :periodType " +
				"AND mr.period = :period " +
				"AND mr.monitoring.idMonitoring = :idMonitoring";
		
		query = em.createQuery(sql);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		query.setParameter("periodType", periodType);
		query.setParameter("period", period);
		query.setParameter("idMonitoring", idMonitoring);
		
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	@Override
	public MonitoringReport getEntity(Character networkType, Long date, String periodType, String period, Long idMonitoring) {
		
		sql = "SELECT mr FROM MonitoringReport mr WHERE mr.networkType = :networkType " +
				"AND mr.date = :date " + 
				"AND mr.periodType = :periodType " +
				"AND mr.period = :period " +
				"AND mr.monitoring.idMonitoring = :idMonitoring";
		
		query = em.createQuery(sql);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		query.setParameter("periodType", periodType);
		query.setParameter("period", period);
		query.setParameter("idMonitoring", idMonitoring);
		
		try {
			return (MonitoringReport) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public boolean hasEntity(Character networkType, Long date, Character garbage, String periodType, Long idMonitoring) {
		
		sql = "SELECT mr FROM MonitoringReport mr WHERE mr.networkType = :networkType " +
				"AND mr.date = :date " + 
				"AND mr.garbage = :garbage " +
				"AND mr.periodType = :periodType " +
				"AND mr.monitoring.idMonitoring = :idMonitoring";
		
		query = em.createQuery(sql);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		query.setParameter("garbage", garbage);
		query.setParameter("periodType", periodType);
		query.setParameter("idMonitoring", idMonitoring);
		
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
		
	}

	@Override
	public MonitoringReport getEntity(Character networkType, Long date, Character garbage, String periodType, Long idMonitoring) {
		
		sql = "SELECT mr FROM MonitoringReport mr WHERE mr.networkType = :networkType " +
				"AND mr.date = :date " + 
				"AND mr.garbage = :garbage " +
				"AND mr.periodType = :periodType " +
				"AND mr.monitoring.idMonitoring = :idMonitoring";
		
		query = em.createQuery(sql);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);
		query.setParameter("garbage", garbage);
		query.setParameter("periodType", periodType);
		query.setParameter("idMonitoring", idMonitoring);
		
		try {
			return (MonitoringReport) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}	
	
	@Override
	public Map<Character, Long> getPostsPerDaysNetworks(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT mr.networkType, SUM(mr.value) FROM MonitoringReport mr " +
				"WHERE mr.monitoring.idMonitoring = :idMonitoring AND mr.periodType = :periodType AND (mr.date >= :dateFrom AND mr.date <= :dateUntil) AND mr.garbage = :garbage " +
				"GROUP BY mr.networkType";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setParameter("periodType", UtilMonitorings.DAY);
		query.setParameter("garbage", 'F');
		
		List<Object[]> result = query.getResultList();
		
		Map<Character, Long> postsPerDaysNetworks = new HashMap<Character, Long>();
		for (Object[] object : result) {
			postsPerDaysNetworks.put((Character)object[0], (Long)object[1]);
		}
		
		return postsPerDaysNetworks;
	}
	
	@Override
	public Map<Character, Long> getFacebookPostsInteractions(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT COALESCE(SUM(comments), 0), COALESCE(SUM(shares), 0), COALESCE(SUM(likes), 0) FROM " +
				"(SELECT DISTINCT(fp.postid), fp.comments, fp.shares, fp.likes FROM opsocial.facepostsmonitorings AS fpm " + 
					"INNER JOIN opsocial.facebookposts AS fp ON " +
					"fpm.postid = fp.postid " + 
				 "WHERE fpm.idmonitoring = " + idMonitoring + " AND (fpm.retrieveddate >= " + dateFrom + " AND fpm.retrieveddate < (" + dateUntil +" + 86400)) AND fpm.garbage = '" + 'F' + "'" +
				") " +
			  "AS sums";

		query = em.createNativeQuery(sql);
		
		Object[] result = (Object[]) query.getSingleResult();
		
		Map<Character, Long> facebookPostsInteractions = new HashMap<Character, Long>();	
		facebookPostsInteractions.put('C', (Long) result[0]);
		facebookPostsInteractions.put('S', (Long) result[1]);
		facebookPostsInteractions.put('L', (Long) result[2]);
		
		return facebookPostsInteractions;
	}
	
	@Override
	public Map<Date, Map<Character, Long>> getPostsPerDayNetworks(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT mr.date, mr.networktype, SUM(mr.value) FROM opsocial.monitoringreports as mr "  +
				"WHERE mr.idmonitoring = " + idMonitoring + " AND mr.periodtype = '" + UtilMonitorings.DAY + "' AND (mr.date >= " + dateFrom + " AND mr.date <= " + dateUntil + ") AND mr.garbage = '" + 'F' + "' " +
				"GROUP BY mr.networktype, mr.date " +
				"ORDER BY mr.date ASC";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<Date, Map<Character, Long>> postsPerDayNetworks= new TreeMap<Date, Map<Character,Long>>();
		Map<Character, Long> postsPerNetwork = null;
		for (int i = 0; i < result.size(); i++) {
			
			if(i % UtilMonitorings.MONITORING_NETWORKS == 0) {
				postsPerNetwork = new HashMap<Character, Long>();
				postsPerDayNetworks.put(new Date((Long) result.get(i)[0] * 1000L), postsPerNetwork);
			}
			
			postsPerNetwork.put(((String)result.get(i)[1]).charAt(0), ((BigDecimal)result.get(i)[2]).longValue());
		}

		return postsPerDayNetworks;
	}

	@Override
	public Map<Integer, Map<Character, Long>> getPostsPerHoursNetworks(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT cast(mr.period AS integer), mr.networktype, SUM(mr.value) FROM opsocial.monitoringreports as mr "  +
				"WHERE mr.idmonitoring = " + idMonitoring + " AND mr.periodtype = '" + UtilMonitorings.HOUR + "' AND (mr.date >= " + dateFrom + " AND mr.date <= " + dateUntil + ") " +
				"GROUP BY mr.networktype, mr.period " +
				"ORDER BY cast(mr.period AS integer) ASC";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<Integer, Map<Character, Long>> postsPerHoursNetworks= new TreeMap<Integer, Map<Character,Long>>();
		Map<Character, Long> postsPerNetwork = null;
		for (int i = 0; i < result.size(); i++) {
			
			if(i % 5 == 0) {
				postsPerNetwork = new HashMap<Character, Long>();
				postsPerHoursNetworks.put((Integer)result.get(i)[0], postsPerNetwork);
			}
			
			postsPerNetwork.put(((String)result.get(i)[1]).charAt(0), ((BigDecimal)result.get(i)[2]).longValue());
		}

		return postsPerHoursNetworks;
	}
	
	@Override
	public Map<Character, Long> getPostsPerHoursNetworks(Long idMonitoring, Long dateFrom, Long dateUntil, Integer hourFrom, Integer hourUntil) {
		
		sql = "SELECT mr.networktype, SUM(mr.value) FROM opsocial.monitoringreports as mr "  +
				"WHERE mr.idmonitoring = " + idMonitoring + " AND mr.periodtype = '" + UtilMonitorings.HOUR + "' AND (mr.date >= " + dateFrom + " AND mr.date <= " + dateUntil + ") " +
				" and (mr.period >= '" + hourFrom + "' and mr.period <= '" + hourUntil + "')" +
				"GROUP BY mr.networktype ";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<Character, Long> postsPerNetwork = new HashMap<Character, Long>();
		
		for (int i = 0; i < result.size(); i++) {
			postsPerNetwork.put(((String)result.get(i)[0]).charAt(0), ((BigDecimal)result.get(i)[1]).longValue());
		}

		return postsPerNetwork;
	}


	@Override
	public Map<Integer, Map<Character, Long>> getPostsPerWeekDaysNetworks(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT series.weekday, mr.networktype, COALESCE(sum(mr.value), 0) FROM (SELECT generate_series(0,6) AS weekday) AS series " +
				"LEFT JOIN (SELECT mr.* FROM opsocial.monitoringreports AS mr WHERE mr.idmonitoring = " + idMonitoring + " AND periodtype = '" + UtilMonitorings.DAY + "' AND (mr.date >= " + dateFrom + " AND mr.date <= " + dateUntil + ") AND mr.garbage = '" + 'F' + "') AS mr ON " +
				"series.weekday = EXTRACT(DOW FROM TO_TIMESTAMP(mr.date + 3600)::timestamp with time zone) " +
				"GROUP BY mr.networktype, series.weekday " +
				"ORDER BY series.weekday";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<Integer, Map<Character, Long>> postsPerWeekDaysNetworks = new TreeMap<Integer, Map<Character,Long>>();
		Map<Character, Long> postsPerNetwork = null;
		Integer previousWeekDay = -1;
		for (int i = 0; i < result.size(); i++) {
			
			if(previousWeekDay.compareTo((Integer)result.get(i)[0]) < 0) { 
			
				postsPerNetwork = new HashMap<Character, Long>();
				postsPerWeekDaysNetworks.put((Integer)result.get(i)[0], postsPerNetwork);
				
				if(result.get(i)[1] == null) {
					postsPerNetwork.put(Profile.FACEBOOK, 0L);
					postsPerNetwork.put(Profile.GOOGLE, 0L);
					postsPerNetwork.put(Profile.TWITTER, 0L);
					postsPerNetwork.put(Profile.YOUTUBE, 0L);
					postsPerNetwork.put(Profile.INSTAGRAM, 0L);
				}
			}
			
			if(result.get(i)[1] != null) {
				postsPerNetwork.put(((String)result.get(i)[1]).charAt(0), ((BigDecimal)result.get(i)[2]).longValue());
			}
			
			previousWeekDay = (Integer)result.get(i)[0];
		}

		return postsPerWeekDaysNetworks;
	}

	@Override
	public Long getPostsTotal(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(COALESCE(sum(mr.value), 0) AS bigint) FROM opsocial.monitoringreports AS mr " +
				"WHERE mr.idmonitoring = " + idMonitoring + " AND periodtype = '" + UtilMonitorings.DAY + "' AND (mr.date >= " + dateFrom + " AND mr.date <= " + dateUntil + ") AND mr.garbage = '" + 'F' + "'";

		query = em.createNativeQuery(sql);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public Long getGarbagePostsTotal(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(COALESCE(sum(mr.value), 0) AS bigint) FROM opsocial.monitoringreports AS mr " +
				"WHERE mr.idmonitoring = " + idMonitoring + " AND periodtype = '" + UtilMonitorings.DAY + "' AND (mr.date >= " + dateFrom + " AND mr.date <= " + dateUntil + ") AND mr.garbage = '" + 'T' + "'";

		query = em.createNativeQuery(sql);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public Long getReportInitialDate(Long idMonitoring) {
		
		Long min;

		sql = "SELECT min(mr.date) FROM MonitoringReport mr WHERE mr.monitoring.idMonitoring = :idMonitoring";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);

		try {

			min = (Long) query.getSingleResult();

			if(min == null) {
				min = 0L;
			}

			return min;
		} catch (Exception e) {
			return 0L;
		}
	}

	@Override
	public List<Long> getDatesGeneratedReports(Long idMonitoring) {
		
		sql = "SELECT DISTINCT(mr.date) FROM opsocial.monitoringreports mr " +
				"INNER JOIN opsocial.monitorings m ON " +
				"mr.idmonitoring = m.idmonitoring " +
				"WHERE m.idmonitoring = " + idMonitoring + " " +
				"ORDER BY mr.date ASC";

		query = em.createNativeQuery(sql);

		try {

			return query.getResultList();

		} catch (NoResultException e) {
			return new ArrayList<Long>();
		}
	}

	@Override
	public Long getPostsTotalByTerm(Long idMonitoring, String term, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(SUM(countposts) AS bigint) FROM " +
					"(SELECT COUNT(fpm) AS countposts FROM opsocial.facepostsmonitorings AS fpm " +
						"WHERE fpm.idmonitoring = " + idMonitoring + " AND (fpm.retrieveddate >= " + dateFrom + " AND fpm.retrieveddate < (" + dateUntil + " + 86400)) AND fpm.term = '" + term + "' AND fpm.garbage = 'F' " +
					"UNION ALL  " +
						"SELECT COUNT(ypm) AS countposts FROM opsocial.youtubepostsmonitorings AS ypm  " +
						"WHERE ypm.idmonitoring = " + idMonitoring + " AND (ypm.retrieveddate >= " + dateFrom + " AND ypm.retrieveddate < (" + dateUntil + " + 86400)) AND ypm.term = '" + term + "' AND ypm.garbage = 'F' " +
					"UNION ALL " +
						"SELECT COUNT(tpm) AS countposts FROM opsocial.twitterpostsmonitorings AS tpm  " +
						"WHERE tpm.idmonitoring = " + idMonitoring + " AND (tpm.retrieveddate >= " + dateFrom + " AND tpm.retrieveddate < (" + dateUntil + " + 86400)) AND tpm.term = '" + term + "' " +
					"UNION ALL " +
						"SELECT COUNT(gpm) AS countposts FROM opsocial.googlepluspostsmonitorings gpm  " +
						"WHERE gpm.idmonitoring = " + idMonitoring + " AND (gpm.retrieveddate >= " + dateFrom + " AND gpm.retrieveddate < (" + dateUntil + " + 86400)) AND gpm.term = '" + term + "' AND gpm.garbage = 'F' " +
					"UNION ALL " +
						"SELECT COUNT(ipm) AS countposts FROM opsocial.instagrampostsmonitorings ipm  " +
						"WHERE ipm.idmonitoring = " + idMonitoring + " AND (ipm.retrieveddate >= " + dateFrom + " AND ipm.retrieveddate < (" + dateUntil + " + 86400)) AND ipm.term = '" + term + "' " +
					"UNION ALL " +
						"SELECT COUNT(rapm) AS countposts FROM opsocial.reclameaquipostsmonitorings rapm  " +
						"WHERE rapm.idmonitoring = " + idMonitoring + " AND (rapm.retrieveddate >= " + dateFrom + " AND rapm.retrieveddate < (" + dateUntil + " + 86400)) AND rapm.term = '" + term + "' " +
					"UNION ALL " +
						"SELECT COUNT(npm) AS countposts FROM opsocial.newspostsmonitorings npm " +
						"WHERE npm.idmonitoring = " + idMonitoring + " AND (npm.retrieveddate >= " + dateFrom + " AND npm.retrieveddate < (" + dateUntil + " + 86400)) AND npm.term = '" + term + "' AND npm.garbage = 'F' " +
					"UNION ALL " +
						"SELECT COUNT(bpm) AS countposts FROM opsocial.blogpostsmonitorings bpm " +
						"WHERE bpm.idmonitoring = " + idMonitoring + " AND (bpm.retrieveddate >= " + dateFrom + " AND bpm.retrieveddate < (" + dateUntil + " + 86400)) AND bpm.term = '" + term + "' AND bpm.garbage = 'F' " +
					") AS countallposts";
		
		query = em.createNativeQuery(sql);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public Map<Date, Long> getPostsPerDay(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT date, CAST(COALESCE(SUM(mr.value), 0) AS BIGINT) FROM opsocial.monitoringreports AS mr " +
				"WHERE mr.idmonitoring = " + idMonitoring + " AND periodtype = '" + UtilMonitorings.DAY + "' " +
						"AND (mr.date >= " + dateFrom + " AND mr.date <= " + dateUntil + ") AND mr.garbage = '" + 'F' + "' " +
				"GROUP BY date " +	
				"ORDER BY date";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();

		Map<Date, Long> postsPerDay = new LinkedHashMap<Date, Long>();
		for(Object[] object : result) {
			postsPerDay.put(new Date((Long) object[0] * 1000L), (Long) object[1]);
		}

		return postsPerDay;
	}
	
	@Override
	public Map<Character, Long> getSentimentAnalysisForSamples(Long idMonitoring, HashMap<Character, String> mappedPosts) {
		
		sql = "SELECT qualificationposts, CAST(SUM(countposts) AS bigint) FROM (";
		
		if(mappedPosts.get(Profile.FACEBOOK) != null) {
			sql += "SELECT fpm.qualification AS qualificationposts, COUNT(fpm) AS countposts FROM opsocial.facepostsmonitorings AS fpm " +
				 "WHERE fpm.idmonitoring = " + idMonitoring + " AND fpm.postId in " + mappedPosts.get(Profile.FACEBOOK) + " AND fpm.qualification <> '' " +
				 "GROUP BY qualificationposts ";
		}
		
		if(mappedPosts.get(Profile.YOUTUBE) != null) {
			sql += "UNION ALL " +
				 "SELECT ypm.qualification AS qualificationposts, COUNT(ypm) AS countposts FROM opsocial.youtubepostsmonitorings AS ypm " +
				 "WHERE ypm.idmonitoring = " + idMonitoring + " AND ypm.videoId in " + mappedPosts.get(Profile.YOUTUBE) + " AND ypm.qualification <> '' " +
				 "GROUP BY qualificationposts ";
		}
		
		if(mappedPosts.get(Profile.TWITTER) != null) {
			sql += "UNION ALL " +
				 "SELECT tpm.qualification AS qualificationposts, COUNT(tpm) AS countposts FROM opsocial.twitterpostsmonitorings AS tpm " +
				 "WHERE tpm.idmonitoring = " + idMonitoring + " AND tpm.statusId in " + mappedPosts.get(Profile.TWITTER) + "  AND tpm.qualification <> '' " +
				 "GROUP BY qualificationposts ";
		}

		if(mappedPosts.get(Profile.GOOGLE) != null) {
			sql += "UNION ALL " +
				 "SELECT gpm.qualification AS qualificationposts, COUNT(gpm) AS countposts FROM opsocial.googlepluspostsmonitorings gpm " +
				 "WHERE gpm.idmonitoring = " + idMonitoring + " AND gpm.activityId  in " + mappedPosts.get(Profile.GOOGLE) + " AND gpm.qualification <> '' " +
				 "GROUP BY qualificationposts ";
		}

		if(mappedPosts.get(Profile.INSTAGRAM) != null) {
			sql += "UNION ALL " +
				 "SELECT ipm.qualification AS qualificationposts, COUNT(ipm) AS countposts FROM opsocial.instagrampostsmonitorings ipm " +
				 "WHERE ipm.idmonitoring = " + idMonitoring + " AND ipm.idMedia in " + mappedPosts.get(Profile.INSTAGRAM) + " AND ipm.qualification <> '' " +
				 "GROUP BY qualificationposts ";
		}
		
		if(mappedPosts.get(Profile.RECLAMEAQUI) != null) {
			sql += "UNION ALL " +
				 "SELECT rapm.qualification AS qualificationposts, COUNT(rapm) AS countposts FROM opsocial.reclameaquipostsmonitorings rapm " +
				 "WHERE rapm.idmonitoring = " + idMonitoring + " AND rapm.cacheid in " + mappedPosts.get(Profile.RECLAMEAQUI) + " AND rapm.qualification <> '' " +
				 "GROUP BY qualificationposts ";
		}

		if(mappedPosts.get(Profile.NEWS) != null) {
			sql += "UNION ALL " +
				 "SELECT npm.qualification AS qualificationposts, COUNT(npm) AS countposts FROM opsocial.newspostsmonitorings npm " +
				 "WHERE npm.idmonitoring = " + idMonitoring + " AND npm.idnewspost in " + mappedPosts.get(Profile.NEWS) + " AND npm.qualification <> '' " +
				 "GROUP BY qualificationposts ";
		}

		if(mappedPosts.get(Profile.BLOGS) != null) {
			sql += "UNION ALL " +
				 "SELECT bpm.qualification AS qualificationposts, COUNT(bpm) AS countposts FROM opsocial.blogpostsmonitorings bpm " +
				 "WHERE bpm.idmonitoring = " + idMonitoring + " AND bpm.idblogpost in " + mappedPosts.get(Profile.BLOGS) + " AND bpm.qualification <> '' " +
				 "GROUP BY qualificationposts ";
		}
		
		sql += ") AS countallposts " +
			  "GROUP BY qualificationposts";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<Character, Long> sentimentAnalysis = new HashMap<Character, Long>();
		for (Object[] object : result) {
			sentimentAnalysis.put(((String)object[0]).charAt(0), (Long)object[1]);
		}
		
		return sentimentAnalysis;
	}
	
	@Override
	public Map<Character, Long> getSentimentAnalysisForSamples(Long idMonitoring, String term, HashMap<Character, String> mappedPosts) {
		
		sql = "SELECT qualificationposts, CAST(SUM(countposts) AS bigint) FROM (";
	
		if(mappedPosts.get(Profile.FACEBOOK) != null) {
		
			sql += "SELECT fpm.qualification AS qualificationposts, COUNT(fpm) AS countposts FROM opsocial.facepostsmonitorings AS fpm " +
				 "WHERE fpm.idmonitoring = " + idMonitoring + " AND fpm.postid in " + mappedPosts.get(Profile.FACEBOOK) + 
					" AND fpm.qualification <> '' AND fpm.term = '" + term + "' " +
				 "GROUP BY qualificationposts ";
		}
		
		if(mappedPosts.get(Profile.YOUTUBE) != null) {
			
			sql += "UNION ALL " +
				 "SELECT ypm.qualification AS qualificationposts, COUNT(ypm) AS countposts FROM opsocial.youtubepostsmonitorings AS ypm " +
				 "WHERE ypm.idmonitoring = " + idMonitoring + " AND ypm.videoid in " + mappedPosts.get(Profile.YOUTUBE) + 
				 	" AND ypm.qualification <> '' AND ypm.term = '" + term + "' " +
				 "GROUP BY qualificationposts ";
		}
		
		if(mappedPosts.get(Profile.TWITTER) != null) {
			
			sql += "UNION ALL " +
				 "SELECT tpm.qualification AS qualificationposts, COUNT(tpm) AS countposts FROM opsocial.twitterpostsmonitorings AS tpm " +
				 "WHERE tpm.idmonitoring = " + idMonitoring + " AND tpm.statusid in " + mappedPosts.get(Profile.TWITTER) + 
				 	" AND tpm.qualification <> '' AND tpm.term = '" + term + "' " +
				 "GROUP BY qualificationposts ";
		}
		
		if(mappedPosts.get(Profile.GOOGLE) != null) {
			
			sql += "UNION ALL " +
				 "SELECT gpm.qualification AS qualificationposts, COUNT(gpm) AS countposts FROM opsocial.googlepluspostsmonitorings gpm " +
				 "WHERE gpm.idmonitoring = " + idMonitoring + " AND gpm.activityId in " + mappedPosts.get(Profile.GOOGLE) + 
				 	" AND gpm.qualification <> '' AND gpm.term = '" + term + "' " +
				 "GROUP BY qualificationposts ";
		}
		
		if(mappedPosts.get(Profile.INSTAGRAM) != null) {
			
			sql += "UNION ALL " +
				 "SELECT ipm.qualification AS qualificationposts, COUNT(ipm) AS countposts FROM opsocial.instagrampostsmonitorings ipm " +
				 "WHERE ipm.idmonitoring = " + idMonitoring + " AND ipm.idmedia in " + mappedPosts.get(Profile.INSTAGRAM) + 
				 	" AND ipm.qualification <> '' AND ipm.term = '" + term + "' " +
				 "GROUP BY qualificationposts ";
		}
		
		if(mappedPosts.get(Profile.RECLAMEAQUI) != null) {
			
			sql += "UNION ALL " +
				 "SELECT rapm.qualification AS qualificationposts, COUNT(rapm) AS countposts FROM opsocial.reclameaquipostsmonitorings rapm " +
				 "WHERE rapm.idmonitoring = " + idMonitoring + " AND rapm.cacheid in " + mappedPosts.get(Profile.RECLAMEAQUI) + 
				 	" AND rapm.qualification <> '' AND rapm.term = '" + term + "' " +
				 "GROUP BY qualificationposts ";
		}
		
		if(mappedPosts.get(Profile.NEWS) != null) {
			
			sql += "UNION ALL " +
				 "SELECT npm.qualification AS qualificationposts, COUNT(npm) AS countposts FROM opsocial.newspostsmonitorings npm " +
				 "WHERE npm.idmonitoring = " + idMonitoring + " AND npm.idnewspost in " + mappedPosts.get(Profile.NEWS) + 
				 	" AND npm.qualification <> '' AND npm.term = '" + term + "' " +
				 "GROUP BY qualificationposts ";
		}
		
		if(mappedPosts.get(Profile.BLOGS) != null) {
			
			sql += "UNION ALL " +
				 "SELECT bpm.qualification AS qualificationposts, COUNT(bpm) AS countposts FROM opsocial.blogpostsmonitorings bpm " +
				 "WHERE bpm.idmonitoring = " + idMonitoring + " AND bpm.idblogpost in " + mappedPosts.get(Profile.BLOGS) + 
				 	" AND bpm.qualification <> '' AND bpm.term = '" + term + "' " +
				 "GROUP BY qualificationposts ";
		}
		
		sql += ") AS countallposts " +
			  "GROUP BY qualificationposts";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<Character, Long> sentimentAnalysis = new HashMap<Character, Long>();
		for (Object[] object : result) {
			sentimentAnalysis.put(((String)object[0]).charAt(0), (Long)object[1]);
		}
		
		return sentimentAnalysis;
	}
	
	@Override
	public Long getPostsTotalByTermForSamples(Long idMonitoring, String term, HashMap<Character, String> mappedPosts) {
		
		sql = "SELECT CAST(SUM(countposts) AS bigint) FROM (";
		
		if(mappedPosts.get(Profile.FACEBOOK) != null) {
			sql += "SELECT COUNT(fpm) AS countposts FROM opsocial.facepostsmonitorings AS fpm " +
				"WHERE fpm.idmonitoring = " + idMonitoring + " AND fpm.postid in " + mappedPosts.get(Profile.FACEBOOK) + " AND fpm.term = '" + term + "' ";
		}
		
		if(mappedPosts.get(Profile.YOUTUBE) != null) {
			sql += " UNION ALL  " +
				"SELECT COUNT(ypm) AS countposts FROM opsocial.youtubepostsmonitorings AS ypm  " +
				"WHERE ypm.idmonitoring = " + idMonitoring + " AND ypm.videoid in " + mappedPosts.get(Profile.YOUTUBE) + " AND ypm.term = '" + term + "' ";
		}
		
		if(mappedPosts.get(Profile.TWITTER) != null) {
			sql += " UNION ALL " +
				"SELECT COUNT(tpm) AS countposts FROM opsocial.twitterpostsmonitorings AS tpm  " +
				"WHERE tpm.idmonitoring = " + idMonitoring + " AND tpm.statusid in " + mappedPosts.get(Profile.TWITTER) + " AND tpm.term = '" + term + "' ";
		}

		if(mappedPosts.get(Profile.GOOGLE) != null) {
			sql += " UNION ALL " +
				"SELECT COUNT(gpm) AS countposts FROM opsocial.googlepluspostsmonitorings gpm  " +
				"WHERE gpm.idmonitoring = " + idMonitoring + " AND gpm.activityid in " + mappedPosts.get(Profile.GOOGLE) + " AND gpm.term = '" + term + "' ";
		}

		if(mappedPosts.get(Profile.INSTAGRAM) != null) {
			sql += " UNION ALL " +
				"SELECT COUNT(ipm) AS countposts FROM opsocial.instagrampostsmonitorings ipm  " +
				"WHERE ipm.idmonitoring = " + idMonitoring + " AND ipm.idmedia in " + mappedPosts.get(Profile.INSTAGRAM) + " AND ipm.term = '" + term + "' ";
		}

		if(mappedPosts.get(Profile.RECLAMEAQUI) != null) {
			sql += " UNION ALL " +
				"SELECT COUNT(rapm) AS countposts FROM opsocial.reclameaquipostsmonitorings rapm  " +
				"WHERE rapm.idmonitoring = " + idMonitoring + " AND rapm.cacheid in " + mappedPosts.get(Profile.RECLAMEAQUI) + " AND rapm.term = '" + term + "' ";
		}
		
		if(mappedPosts.get(Profile.NEWS) != null) {
			sql += " UNION ALL " +
				"SELECT COUNT(npm) AS countposts FROM opsocial.newspostsmonitorings npm " +
				"WHERE npm.idmonitoring = " + idMonitoring + " AND npm.idnewspost in " + mappedPosts.get(Profile.NEWS) + " AND npm.term = '" + term + "' ";
		}

		if(mappedPosts.get(Profile.BLOGS) != null) {
			sql += " UNION ALL " +
				"SELECT COUNT(bpm) AS countposts FROM opsocial.blogpostsmonitorings bpm " +
				"WHERE bpm.idmonitoring = " + idMonitoring + " AND bpm.idblogpost in " + mappedPosts.get(Profile.BLOGS) + " AND bpm.term = '" + term + "' ";
		}
		
		sql += ") AS countallposts";

		query = em.createNativeQuery(sql);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return 0L;
		}
	}

}
