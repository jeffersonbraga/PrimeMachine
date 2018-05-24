package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.GooglePlusPostMonitoringId;
import br.com.opsocial.ejb.entity.google.GooglePlusPost;
import br.com.opsocial.ejb.entity.google.GooglePlusPostMonitoring;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Stateless
public class GooglePlusPostMonitoringDAOImpl extends AbstractDAOImpl<GooglePlusPostMonitoring> implements GooglePlusPostMonitoringDAO {

	private String nativeSql = "";
	List<Long> activityIds = null;
	
	@Override
	public void delete(GooglePlusPostMonitoring object) throws Exception {
		try {
			object = em.merge(object);
			em.remove(object);
			this.flush();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public Long getCountOfPosts(Long idMonitoring, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT COUNT(g) FROM GooglePlusPostMonitoring g WHERE g.monitoring.idMonitoring = :idMonitoring " + 
				" and g.garbage='F' AND (g.googlePlusPost.published >= :dateFrom AND g.googlePlusPost.published <= :dateUntil)";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);

		return (Long) query.getSingleResult();
	}
	
	@Override
	public List<GooglePlusPostMonitoring> listMostRecents(Long idMonitoring, Integer maxResults) {
	
		sql = "select activityid from opsocial.googlepluspostsmonitorings where idmonitoring=" + idMonitoring + 
				" and garbage='F' " + 
				" order by createdtime desc limit " + maxResults;
		
		query = em.createNativeQuery(sql);
		
		String ids = "";
		
		for(Object id : query.getResultList()) {
			ids += "'" + id.toString() + "',";
		}
		
		if(!ids.isEmpty()) {
			sql = "Select g From GooglePlusPostMonitoring g where g.monitoring.idMonitoring = :idMonitoring and " +
					" g.googlePlusPost.activityId in (" + ids.substring(0,ids.length()-1) + ") order by g.createdTime desc";
			query = em.createQuery(sql);
			query.setParameter("idMonitoring", idMonitoring);
			return query.getResultList();
		} else {
			return new ArrayList<GooglePlusPostMonitoring>();
		}
	}
	
	@Override
	public List<GooglePlusPostMonitoring> listMostRecents(Long published, Long idMonitoring) {
	
		sql = "select activityid from opsocial.googlepluspostsmonitorings where createdtime > " + published + 
				" and idmonitoring=" + idMonitoring + " and garbage='F' order by createdtime ";
		
		query = em.createNativeQuery(sql);
		
		String ids = "";
		
		for(Object id : query.getResultList()) {
			ids += "'" + id.toString() + "',";
		}
		
		if(!ids.isEmpty()) {
			sql = "Select g From GooglePlusPostMonitoring g where g.monitoring.idMonitoring = :idMonitoring and " +
					"g.googlePlusPost.activityId in (" + ids.substring(0,ids.length()-1) + ") order by g.createdTime";
			query = em.createQuery(sql);
			query.setParameter("idMonitoring", idMonitoring);
			return query.getResultList();
		} else {
			return new ArrayList<GooglePlusPostMonitoring>();
		}
	}
	
	@Override
	public List<GooglePlusPostMonitoring> listElder(Long published, Long idMonitoring, Integer maxResults) {
	
		sql = "select activityid from opsocial.googlepluspostsmonitorings where createdtime < " + published + 
				" and idmonitoring=" + idMonitoring + " and garbage='F'  order by createdtime desc limit " + maxResults;
		
		query = em.createNativeQuery(sql);
		
		String ids = "";
		
		for(Object id : query.getResultList()) {
			ids += "'" + id.toString() + "',";
		}
		
		if(!ids.isEmpty()) {
			sql = "Select g From GooglePlusPostMonitoring g where g.monitoring.idMonitoring = :idMonitoring and " +
					"g.googlePlusPost.activityId in (" + ids.substring(0,ids.length()-1) + ") order by g.createdTime desc";
			query = em.createQuery(sql);
			query.setParameter("idMonitoring", idMonitoring);
			return query.getResultList();	
		} else {
			return new ArrayList<GooglePlusPostMonitoring>();
		}
	}
	
	@Override
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date) {
		
		sql = "SELECT gpm.term, COUNT(gpm) FROM GooglePlusPostMonitoring gpm " +
				"WHERE gpm.monitoring.idMonitoring = :idMonitoring AND (gpm.retrievedDate >= :date AND gpm.retrievedDate < (:date + 86400)) " +
				"AND gpm.garbage = 'F' " +
				"GROUP BY gpm.term";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("date", date);
		
		List<Object[]> result = query.getResultList();
		
		return result;
	}
	
	@Override
	public Long getCountOfPostsPerDay(Long idMonitoring, Long date) {
		
		sql = "SELECT COUNT(gpm) FROM GooglePlusPostMonitoring gpm WHERE gpm.monitoring.idMonitoring = :idMonitoring " + 
				"AND (gpm.retrievedDate >= :date AND gpm.retrievedDate < (:date + 86400)) and gpm.garbage = 'F'";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("date", date);

		return (Long) query.getSingleResult();
	}

	@Override
	public List<Object[]> getCountOfPostsPerHourDay(Long idMonitoring, Long date) {
		
		sql = "SELECT series.hour, count(gpm) FROM (SELECT generate_series(0,23)*1 AS hour) AS series " +
				"LEFT JOIN (SELECT gpm.* FROM opsocial.googlepluspostsmonitorings as gpm INNER JOIN opsocial.monitorings AS m ON gpm.idmonitoring = m.idmonitoring " +
					"WHERE gpm.idmonitoring = " + idMonitoring + " AND (gpm.retrieveddate >= " + date + " AND gpm.retrieveddate < (" + date + " + 86400)) and gpm.garbage = 'F') AS gpm ON " +
				"series.hour = date_part('hour', TO_TIMESTAMP(gpm.retrieveddate)::timestamp with time zone) " +
			  "GROUP BY series.hour " +  
			  "ORDER BY series.hour";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		return result;
	}
	
	@Override
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date) {

		sql = "SELECT gpm.qualification, gpm.term, COUNT(*) FROM opsocial.googlepluspostsmonitorings gpm WHERE gpm.idmonitoring = " + idMonitoring + " " + 
				"AND (gpm.retrieveddate >= " + date + " AND gpm.garbage='F' and  gpm.retrieveddate < (" + date + " + 86400)) AND gpm.qualification <> '' " + 
				"GROUP BY gpm.qualification, gpm.term";

		query = em.createNativeQuery(sql);

		try {
			return (List<Object[]>) query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}
	
	@Override
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date) {
		
		sql = "SELECT mpt.idtag, COUNT(*) FROM opsocial.googlepluspostsmonitorings AS gpm " +
				"INNER JOIN opsocial.monitoringspoststags AS mpt ON " +
				"mpt.postid = gpm.activityid AND mpt.term = gpm.term AND mpt.idmonitoring = gpm.idmonitoring " +
				"WHERE mpt.idmonitoring = " + idMonitoring + " AND gpm.garbage='F' AND (gpm.retrieveddate >= " + date + " AND gpm.retrieveddate < (" + date + " + 86400)) " + 
				"GROUP BY mpt.idtag";

		query = em.createNativeQuery(sql);

		try {
			return (List<Object[]>) query.getResultList();
		} catch (NoResultException e) {
			e.printStackTrace();
			return new ArrayList<Object[]>();
		}
	}

	@Override
	public List<GooglePlusPostMonitoring> getByInterval(Long idMonitoring, Long startDate, Long endDate, String qualification, 
			List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit) {

		String joinMonitoringTags = "";
		
		if(monitoringTags != null && !monitoringTags.isEmpty()) {
			
			joinMonitoringTags = "INNER JOIN opsocial.monitoringspoststags AS mpt ON (" + 
					"gpm.idmonitoring = mpt.idmonitoring AND gpm.term = mpt.term AND gpm.activityid = mpt.postid " +
					"AND mpt.network = '" + Profile.GOOGLE + "') ";	
		}
		
		sql = "SELECT gpm.idmonitoring, gpm.activityid, gpm.term, gpm.visible, gpm.qualification, gpm.version, gpm.retrieveddate, " +
				"gpm.garbage, gpm.createdtime, gpm.hashashtags, gpm.haslinks FROM opsocial.googlepluspostsmonitorings AS gpm " + 
				joinMonitoringTags + 
			"WHERE gpm.idmonitoring = " + idMonitoring + " AND gpm.createdtime >= " + startDate + " AND " +
			"gpm.createdtime <= " + endDate + " AND gpm.garbage = 'F'";
		
		if(monitoringWords != null) {
			
			sql += " AND gpm.activityid IN ( "
					+ "SELECT gp2.activityid FROM opsocial.googleplusposts gp2 LEFT JOIN opsocial.objectattachments oa on (gp2.activityid = oa.activityid) WHERE ";
		
			for(String w : monitoringWords) {
				if(!w.equals("")) {//TODO implementar regex melhor
					sql += "LOWER(gp2.objectcontent) LIKE LOWER('%" + w + "%') OR " +
								 "LOWER(oa.displayname) LIKE LOWER('%" + w + "%') OR " +
								 "LOWER(oa.content) LIKE LOWER('%" + w + "%') OR ";
				}
			}

			sql = sql.substring(0, sql.lastIndexOf("OR"));

			sql += ")";
			
		}
		
		if(monitoringTerms != null) {
			
			sql += " AND (";
			
			for(String t : monitoringTerms) {
				sql += " gpm.term = '" + t + "' OR";	
			}
			
			sql = sql.substring(0, sql.lastIndexOf("OR"));
			
			sql += ")";
		}
		
		if(qualification != null) {
			
			sql += " AND (";
			
			String[] arrayQualification = qualification.split(";");
			
			for(String q : arrayQualification) {
				if(q.equals("Q")) {
					sql += " gpm.qualification is null OR";
				} else {
					sql += " gpm.qualification = '" + q + "' OR";	
				} 
			}
			
			sql = sql.substring(0, sql.lastIndexOf("OR"));
			
			sql += ")";
		}			
		
		if(monitoringTags != null && !monitoringTags.isEmpty()) {
			
			String monitoringTagsIn = " AND mpt.idtag IN(";
			
			monitoringTagsIn = monitoringTagsIn.concat(""+monitoringTags.get(0)+"");
			for(int i = 1; i < monitoringTags.size(); i++) {
				monitoringTagsIn = monitoringTagsIn.concat(","+monitoringTags.get(i)+"");
			}
			
			monitoringTagsIn = monitoringTagsIn.concat(")");
			
			sql += monitoringTagsIn;
		}
		
		sql += " GROUP BY gpm.idmonitoring, gpm.activityid, gpm.term, gpm.visible, gpm.qualification, gpm.version," +
				" gpm.retrieveddate, gpm.garbage, gpm.createdtime, gpm.hashashtags, gpm.haslinks";
		
		sql += " ORDER BY gpm.createdtime desc";
		
		query = em.createNativeQuery(sql);
		query.setFirstResult(offset * 50);
		query.setMaxResults(limit);
		
		List<GooglePlusPostMonitoring> googlePlusPostsMonitorings = new ArrayList<GooglePlusPostMonitoring>();
		
		List<Object[]> result = query.getResultList(); 

		Monitoring monitoring = new Monitoring();
		sql = "SELECT mn FROM Monitoring mn WHERE mn.idMonitoring=" + idMonitoring;
		query = em.createQuery(sql);
		monitoring = (Monitoring) query.getSingleResult();
		
		for (Object[] object : result) {
			
			GooglePlusPostMonitoring googlePlusPostMonitoring = new GooglePlusPostMonitoring();
			
			GooglePlusPost googlePlusPost = new GooglePlusPost();
			String activityId = (String) object[1];
			sql = "SELECT gp FROM GooglePlusPost gp WHERE gp.activityId = :activityId"; 
			query = em.createQuery(sql);
			query.setParameter("activityId", activityId);
			googlePlusPost = (GooglePlusPost) query.getSingleResult();
			
			googlePlusPostMonitoring.setMonitoring(monitoring);
			googlePlusPostMonitoring.setGooglePlusPost(googlePlusPost); //TODO ver os que podem ser null
			googlePlusPostMonitoring.setTerm((String) object[2]);
			googlePlusPostMonitoring.setVisible(((String) object[3]).charAt(0));
			googlePlusPostMonitoring.setQualification(object[4] != null ? ((String) object[4]).charAt(0) : null);
			googlePlusPostMonitoring.setVersion((Long) object[5]);
			googlePlusPostMonitoring.setRetrievedDate(object[6] != null ? ((Long) object[6]) : null);
			googlePlusPostMonitoring.setGarbage(((String) object[7]).charAt(0));
			googlePlusPostMonitoring.setCreatedTime(object[8] != null ? ((Long) object[8]) : null);
			googlePlusPostMonitoring.setHasHashTags(object[9] != null ? ((Boolean) object[9]) : null);
			googlePlusPostMonitoring.setHasLinks(object[10] != null ? ((Boolean) object[10]) : null);
			
			googlePlusPostsMonitorings.add(googlePlusPostMonitoring);
		}
		
		return googlePlusPostsMonitorings;
	}

	@Override
	public Long getByIntervalCount(Long idMonitoring, Long startDate,
			Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords) {
		
		Long postsCount = 0L;
		
		String joinMonitoringTags = "";
		
		if(monitoringTags != null && !monitoringTags.isEmpty()) {
			
			joinMonitoringTags = "INNER JOIN opsocial.monitoringspoststags AS mpt ON (" +
					"gpm.idmonitoring = mpt.idmonitoring AND gpm.term = mpt.term AND CAST(gpm.statusid AS varchar) = mpt.postid " +
					"AND mpt.network = '" + Profile.GOOGLE + " ') ";
			
			sql = "SELECT count(distinct(gpm))";	
		} else {
			sql = "SELECT count(gpm)";
		}
		
		sql += " FROM opsocial.googlepluspostsmonitorings AS gpm " + joinMonitoringTags +
				"WHERE gpm.idmonitoring = " + idMonitoring + " AND gpm.createdtime >= " + startDate + " AND " +
				"gpm.createdtime <= " + endDate + " AND gpm.garbage = 'F'";

		if(monitoringWords != null) {
			
			sql += " AND gpm.activityid IN ( "
					+ "SELECT gp2.activityid FROM opsocial.googleplusposts gp2 LEFT JOIN opsocial.objectattachments oa on (gp2.activityid = oa.activityid) WHERE ";
		
			for(String w : monitoringWords) {
				if(!w.equals("")) { //TODO implementar regex melhor
					sql += "LOWER(gp2.objectcontent) LIKE LOWER('%" + w + "%') OR " +
								 "LOWER(oa.displayname) LIKE LOWER('%" + w + "%') OR " +
								 "LOWER(oa.content) LIKE LOWER('%" + w + "%') OR ";
				}
			}

			sql = sql.substring(0, sql.lastIndexOf("OR"));

			sql += ")";
		}
		
		if(monitoringTerms != null) {
			
			sql += " AND (";
			
			for(String t : monitoringTerms) {
				sql += " gpm.term = '" + t + "' OR";	
			}
			
			sql = sql.substring(0, sql.lastIndexOf("OR"));
			
			sql += ")";
		}
		
		if(qualification != null) {
			
			sql += " AND (";
			
			String[] arrayQualification = qualification.split(";");
			
			for(String q : arrayQualification) {
				if(q.equals("Q")) {
					sql += " gpm.qualification is null OR";
				} else {
					sql += " gpm.qualification = '" + q + "' OR";	
				} 
			}
			
			sql = sql.substring(0, sql.lastIndexOf("OR"));
			
			sql += ")";
		}		
		
		if(monitoringTags != null && !monitoringTags.isEmpty()) {
			
			String monitoringTagsIn = "AND mpt.idtag IN(";
			
			monitoringTagsIn = monitoringTagsIn.concat(""+monitoringTags.get(0)+"");
			for(int i = 1; i < monitoringTags.size(); i++) {
				monitoringTagsIn = monitoringTagsIn.concat(","+monitoringTags.get(i)+"");
			}
			
			monitoringTagsIn = monitoringTagsIn.concat(")");
			
			sql += monitoringTagsIn;
		}
		
		query = em.createNativeQuery(sql);
		
		postsCount = (Long) query.getSingleResult(); 
		
		return postsCount;
	}
	
	@Override
	public List<GooglePlusPostMonitoring> listPostsByActiveAccount(Date now, Long from, Long until) {
		
		sql = "Select g From GooglePlusPostMonitoring g where " +
				"((g.monitoring.account.testExpire > :now and g.monitoring.account.planType = 0)" +
				" or " +
				"(g.monitoring.account.planExpire > :now and g.monitoring.account.planType <> 0)) " +
				"and g.googlePlusPost.published between :from and :until";
		
		query = em.createQuery(sql);
		query.setParameter("from", from);
		query.setParameter("until", until);
		query.setParameter("now", now);
		
		return query.getResultList();
	}
	
	@Override
	public GooglePlusPostMonitoring merge(GooglePlusPostMonitoring googlePlusPostMonitoring) throws Exception {

		GooglePlusPostMonitoringId googlePlusPostMonitoringId = new GooglePlusPostMonitoringId();
		googlePlusPostMonitoringId.setGooglePlusPost(googlePlusPostMonitoring.getGooglePlusPost().getActivityId());
		googlePlusPostMonitoringId.setMonitoring(googlePlusPostMonitoring.getMonitoring().getIdMonitoring());
		googlePlusPostMonitoringId.setTerm(googlePlusPostMonitoring.getTerm());

		GooglePlusPostMonitoring objTmp = getById(googlePlusPostMonitoringId);

		try {
			validateVersion(objTmp, googlePlusPostMonitoring);
		} catch (IllegalStateException ex) {
			throw new OptimisticLockException();
		}

		em.merge(googlePlusPostMonitoring);
		googlePlusPostMonitoring = getById(googlePlusPostMonitoringId);
		
		this.flush();

		return googlePlusPostMonitoring;
	}
	
	@Override
	public List<GooglePlusPostMonitoring> listByMonitoring(Long idMonitoring) {
		sql = "Select g From GooglePlusPostMonitoring g where g.monitoring.idMonitoring = :idMonitoring";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);

		return query.getResultList();
	}

	@Override
	public List<GooglePlusPostMonitoring> listByTerm(String term,
			Long idMonitoring) {

		sql = "Select g From GooglePlusPostMonitoring g where g.monitoring.idMonitoring = :idMonitoring and g.term = :term";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);

		return query.getResultList();
	}

	@Override
	public GooglePlusPostMonitoring getByComposedId(Long idMonitoring,
			String activityId, String term) {
		
		sql = "Select g From GooglePlusPostMonitoring g where g.monitoring.idMonitoring = :idMonitoring and g.term = :term and g.googlePlusPost.activityId = :activityId";
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
		query.setParameter("activityId", activityId);
		
		return (GooglePlusPostMonitoring) query.getSingleResult();
	}

	@Override
	public Long getCountOfGarbagePostsPerDay(Long idMonitoring, Long date) {
		
		sql = "SELECT COUNT(g) FROM GooglePlusPostMonitoring g WHERE g.monitoring.idMonitoring = :idMonitoring " + 
				"AND (g.retrievedDate >= :date AND g.retrievedDate < (:date + 86400)) AND g.garbage = 'T'";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("date", date);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return 0L;
		}
	}
	
	@Override
	public GooglePlusPostMonitoring getByComposedId(Long idMonitoring, String activityId) {
		
		sql = "Select g From GooglePlusPostMonitoring g where g.monitoring.idMonitoring = :idMonitoring and g.googlePlusPost.activityId = :activityId";
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("activityId", activityId);
		
		if(!query.getResultList().isEmpty()) {
			return (GooglePlusPostMonitoring) query.getSingleResult();
		} else {
			return null;
		}
	}
	
	@Override
	public List<GooglePlusPostMonitoring> listSample(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, Integer sample, List<String> notIn) {
		
		sql = "Select * From opsocial.googlepluspostsmonitorings gpm where " +
				"gpm.idMonitoring = " + idMonitoring + " and " +
				"gpm.createdTime >= " + startDate +" and " +
				"gpm.createdTime <= " + endDate +" and " +
				"gpm.garbage='F' ";
				
		if(qualification != null) {
			
			sql += "and (";
			
			String[] arrayQualification = qualification.split(";");
			
			for(String q : arrayQualification) {
				if(q.equals("null") || q.equals("Q")) {
					sql += " gpm.qualification is null or";
				} else {
					sql += " gpm.qualification = '" + q + "' or";	
				} 
			}
			
			sql = sql.substring(0, sql.lastIndexOf("or"));
			
			sql += ")";
		}
		
		if(!notIn.isEmpty()) {
			
			sql += "and activityid not in (";
			
			for(String postId : notIn) {
				sql += "'" + postId + "',";
			}
			
			sql = sql.substring(0,sql.lastIndexOf(","));
			
			sql += ")";
		}
		
		sql += " order by random(), createdtime desc limit " + sample;
		
		query = em.createNativeQuery(sql, GooglePlusPostMonitoring.class);
		
		List<GooglePlusPostMonitoring> googlePlusPostMonitorings = (List<GooglePlusPostMonitoring>) query.getResultList(); 
		
		if(monitoringTags != null && !monitoringTags.isEmpty()) {
			
			List<GooglePlusPostMonitoring> googlePostsMonitoringToExclude = new ArrayList<GooglePlusPostMonitoring>();
			
			for(GooglePlusPostMonitoring googlePlusPostMonitoring : googlePlusPostMonitorings) {
				
				try {
					
					MaintenanceMonitoringPostTagRemote monitoringPostTagRemote = (MaintenanceMonitoringPostTagRemote) 
							new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringPostTagBean!br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote");
					
					if(!monitoringPostTagRemote.thereIsMonitoringTag(googlePlusPostMonitoring.getGooglePlusPost().getActivityId(), 
							googlePlusPostMonitoring.getMonitoring().getIdMonitoring(), googlePlusPostMonitoring.getTerm(), 
							Profile.GOOGLE, monitoringTags)) {
						googlePostsMonitoringToExclude.add(googlePlusPostMonitoring);
					}
					
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
			
			googlePlusPostMonitorings.removeAll(googlePostsMonitoringToExclude);
		}
		
		return googlePlusPostMonitorings;
	}

	@Override
	public List<GooglePlusPostMonitoring> listSamePosts(Long idMonitoring, String activityId, String objectId) {
		
		String activityIdCond = "";
		String objectIdCond = "";
		if(objectId != null) {
			activityIdCond = " OR gp.activityid = '" + objectId + "'";
			objectIdCond = " OR gp.objectid = '" + objectId + "'";
		}
		
		sql = "SELECT gpm.* from opsocial.googlepluspostsmonitorings as gpm " +
				"INNER JOIN opsocial.googleplusposts AS gp ON " +
				"gp.activityid = gpm.activityid " +
				"WHERE gpm.idmonitoring = " + idMonitoring + " " +
				"AND ((((gp.objectid = '" + activityId + "'" + objectIdCond + ") AND gp.verb = '" + GooglePlusPost.VERB_SHARE + "') " +
				"AND gp.activityid != '" + activityId + "')" + activityIdCond + ")";
		
		query = em.createNativeQuery(sql, GooglePlusPostMonitoring.class);
		
		return query.getResultList();
	}

}
