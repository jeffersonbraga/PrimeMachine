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
import br.com.opsocial.ejb.entity.application.idclass.InstagramPostMonitoringId;
import br.com.opsocial.ejb.entity.instagram.InstagramPost;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Stateless
public class InstagramPostMonitoringDAOImpl extends AbstractDAOImpl<InstagramPostMonitoring> implements InstagramPostMonitoringDAO {
	
	@Override
	public void delete(InstagramPostMonitoring object) throws Exception {
		try {
			object = em.merge(object);
			em.remove(object);
			this.flush();
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	@Override
	public List<InstagramPostMonitoring> listMostRecents(Long idMonitoring, Integer maxResults) {

		sql = "SELECT idmedia FROM opsocial.instagrampostsmonitorings WHERE idmonitoring=" + idMonitoring + 
				" AND garbage = 'F' " + 
				" ORDER BY createdtime DESC LIMIT " + maxResults;
		
		query = em.createNativeQuery(sql);
		
		List<Long> ids = new ArrayList<Long>();
		
		for(Object id : query.getResultList()) {
			ids.add(Long.valueOf(id.toString()));
		}
		
		if(!ids.isEmpty()) {
			sql = "SELECT i FROM InstagramPostMonitoring i WHERE i.monitoring.idMonitoring = :idMonitoring AND " +
					"i.instagramPost.idMedia IN :ids ORDER BY i.createdTime DESC";
			query = em.createQuery(sql);
			query.setParameter("ids", ids);
			query.setParameter("idMonitoring", idMonitoring);
			return query.getResultList();
		} else {
			return new ArrayList<InstagramPostMonitoring>();
		}
	}

	@Override
	public List<InstagramPostMonitoring> listMostRecents(Long createTime, Long idMonitoring) {

		sql = "SELECT idmedia FROM opsocial.instagrampostsmonitorings WHERE createdtime > " + createTime + 
				" AND idmonitoring=" + idMonitoring + " AND garbage = 'F' ORDER BY createdtime";
		
		query = em.createNativeQuery(sql);
		
		List<Long> ids = new ArrayList<Long>();
		
		for(Object id : query.getResultList()) {
			ids.add(Long.valueOf(id.toString()));
		}
		
		if(!ids.isEmpty()) {
			sql = "Select i From InstagramPostMonitoring i where i.monitoring.idMonitoring = :idMonitoring and " +
					"i.instagramPost.idMedia in :ids order by i.createdTime";
			query = em.createQuery(sql);
			query.setParameter("ids", ids);
			query.setParameter("idMonitoring", idMonitoring);
			return query.getResultList();		
		} else {
			return new ArrayList<InstagramPostMonitoring>();
		}
	}

	@Override
	public List<InstagramPostMonitoring> listElder(Long createTime, Long idMonitoring, Integer maxResults) {

		sql = "SELECT idmedia FROM opsocial.instagrampostsmonitorings WHERE createdtime < " + createTime + 
				" AND idmonitoring=" + idMonitoring + " AND garbage = 'F' ORDER BY createdtime DESC LIMIT " + maxResults;
		
		query = em.createNativeQuery(sql);
		
		List<Long> ids = new ArrayList<Long>();
		
		for(Object id : query.getResultList()) {
			ids.add(Long.valueOf(id.toString()));
		}
		
		if(!ids.isEmpty()) {
			sql = "SELECT i FROM InstagramPostMonitoring i WHERE i.monitoring.idMonitoring = :idMonitoring AND " +
					"i.instagramPost.idMedia IN :ids ORDER BY i.createdTime DESC";
			query = em.createQuery(sql);
			query.setParameter("ids", ids);
			query.setParameter("idMonitoring", idMonitoring);
			return query.getResultList();
		} else {
			return new ArrayList<InstagramPostMonitoring>();
		}
	}
	
	@Override
	public List<InstagramPostMonitoring> getByInterval(Long idMonitoring, Long startDate, Long endDate, 
			String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit) {
		
		String joinMonitoringTags = "";
		
		if(monitoringTags != null && !monitoringTags.isEmpty()) {
			
			joinMonitoringTags = "INNER JOIN opsocial.monitoringspoststags AS mpt ON (" +
					"ipm.idmonitoring = mpt.idmonitoring AND  ipm.term = mpt.term AND CAST(ipm.idmedia AS varchar) = mpt.postid " +
					"AND mpt.network = '" + Profile.INSTAGRAM + "') ";
		}
		
		sql = "SELECT ipm.idmonitoring, ipm.idmedia, ipm.term, ipm.qualification, ipm.retrieveddate, ipm.garbage, " +
				"ipm.createdtime, ipm.hashashtags, ipm.haslinks FROM opsocial.instagrampostsmonitorings ipm " +
				joinMonitoringTags + 
				"WHERE ipm.idmonitoring = " + idMonitoring + " AND " +
				"ipm.createdtime >= " + startDate + " AND " +
				"ipm.createdtime <= " + endDate + " AND " +
				"ipm.garbage = 'F'";
		
		if(monitoringWords != null) {
			
			sql += " AND ipm.idmedia IN ( "
				+ "SELECT ip2.idmedia FROM opsocial.instagramposts ip2 WHERE ";
					
			for(String w : monitoringWords) {
				if(!w.equals("")) //TODO implementar regex melhor
					sql += "ip2.picturecaption LIKE '%" + w + "%' OR ";
			}

			sql = sql.substring(0, sql.lastIndexOf("OR "));
			sql += ")";

		}
		
		if(monitoringTerms != null) {
			
			sql += " and (";
			
			for(String t : monitoringTerms) {
				sql += " ipm.term = '" + t + "' or";	
			}
			
			sql = sql.substring(0, sql.lastIndexOf("or"));
			
			sql += ")";
		}
		
		if(qualification != null) {
			
			sql += " and (";
			
			String[] arrayQualification = qualification.split(";");
			
			for(String q : arrayQualification) {
				if(q.equals("Q")) {
					sql += " ipm.qualification is null or";
				} else {
					sql += " ipm.qualification = '" + q + "' or";	
				} 
			}
			
			sql = sql.substring(0, sql.lastIndexOf("or"));
			
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
		
		sql += " GROUP BY ipm.idmonitoring, ipm.idmedia, ipm.term, ipm.qualification," +
				" ipm.retrieveddate, ipm.garbage, ipm.createdtime, ipm.hashashtags, ipm.haslinks";
		
		sql += " ORDER BY ipm.createdtime desc";
		
		query = em.createNativeQuery(sql);
		query.setFirstResult(offset * 50);
		query.setMaxResults(limit);
		
		List<InstagramPostMonitoring> instagramPostsMonitorings = new ArrayList<InstagramPostMonitoring>();
		
		List<Object[]> result = query.getResultList();

		Monitoring monitoring = new Monitoring();
		sql = "SELECT mn FROM Monitoring mn WHERE mn.idMonitoring=" + idMonitoring;
		query = em.createQuery(sql);
		monitoring = (Monitoring) query.getSingleResult();
		
		for (Object[] object : result) {
			
			InstagramPostMonitoring instagramPostsMonitoring = new InstagramPostMonitoring();
			
			InstagramPost instagramPost = new InstagramPost();
			Long idMedia = (Long) object[1];
			sql = "SELECT ip FROM InstagramPost ip WHERE ip.idMedia = :idMedia"; 
			query = em.createQuery(sql);
			query.setParameter("idMedia", idMedia);
			instagramPost = (InstagramPost) query.getSingleResult();
			
			instagramPostsMonitoring.setMonitoring(monitoring);
			instagramPostsMonitoring.setInstagramPost(instagramPost); //TODO ver os que podem ser null
			instagramPostsMonitoring.setTerm((String) object[2]);
			instagramPostsMonitoring.setQualification(object[3] != null ? ((String) object[3]).charAt(0) : null);
			instagramPostsMonitoring.setRetrievedDate(object[4] != null ? ((Long) object[4]) : null);
			instagramPostsMonitoring.setGarbage(object[5] != null ? ((String) object[5]).charAt(0) : null);
			instagramPostsMonitoring.setCreatedTime(object[6] != null ? ((Long) object[6]) : null);
			instagramPostsMonitoring.setHasHashTags(object[7] != null ? ((Boolean) object[7]) : null);
			instagramPostsMonitoring.setHasLinks(object[8] != null ? ((Boolean) object[8]) : null);
			
			instagramPostsMonitorings.add(instagramPostsMonitoring);
		}
		
		return instagramPostsMonitorings;
	}

	@Override
	public Long getByIntervalCount(Long idMonitoring, Long startDate,
			Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords) {
		
		Long postsCount = 0L;
		
		String joinMonitoringTags = "";
		
		if(monitoringTags != null && !monitoringTags.isEmpty()) {
			
			joinMonitoringTags = "INNER JOIN opsocial.monitoringspoststags AS mpt ON (" +
					"ipm.idmonitoring = mpt.idmonitoring AND ipm.term = mpt.term AND CAST(ipm.statusid AS varchar) = mpt.postid " +
					"AND mpt.network = '" + Profile.INSTAGRAM + " ') ";
			
			sql = "SELECT count(distinct(ipm))";	
		} else {
			sql = "SELECT count(ipm)";
		}
		
		sql += " FROM opsocial.instagrampostsmonitorings AS ipm " + joinMonitoringTags +
				"WHERE ipm.idmonitoring = " + idMonitoring + " AND (ipm.createdtime  >= " + startDate + " AND " +
				"ipm.createdtime  <= " + endDate + ") AND ipm.garbage = 'F'";
		
		if(monitoringWords != null) {
			
			sql += " AND ipm.idmedia in ( "
					+ "Select ip2.idmedia From opsocial.instagramposts ip2 where ";
						
			for(String w : monitoringWords) {
				if(!w.equals("")) //TODO implementar regex melhor
					sql += "ip2.picturecaption LIKE '%" + w + "%' OR ";
			}
	
			sql = sql.substring(0, sql.lastIndexOf("OR "));
			sql += ")";
		}
		
		if(monitoringTerms != null) {
			
			sql += " and (";
			
			for(String t : monitoringTerms) {
				sql += " ipm.term = '" + t + "' or";	
			}
			
			sql = sql.substring(0, sql.lastIndexOf("or"));
			
			sql += ")";
		}
		
		if(qualification != null) {
			
			sql += " and (";
			
			String[] arrayQualification = qualification.split(";");
			
			for(String q : arrayQualification) {
				if(q.equals("Q")) {
					sql += " ipm.qualification is null or";
				} else {
					sql += " ipm.qualification = '" + q + "' or";	
				} 
			}
			
			sql = sql.substring(0, sql.lastIndexOf("or"));
			
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
	public InstagramPostMonitoring merge(InstagramPostMonitoring instagramPostMonitoring) throws Exception {

		InstagramPostMonitoringId instagramPostMonitoringId = new InstagramPostMonitoringId();
		instagramPostMonitoringId.setInstagramPost(instagramPostMonitoring.getInstagramPost().getIdMedia());
		instagramPostMonitoringId.setMonitoring(instagramPostMonitoring.getMonitoring().getIdMonitoring());
		instagramPostMonitoringId.setTerm(instagramPostMonitoring.getTerm());

		InstagramPostMonitoring objTmp = getById(instagramPostMonitoringId);

		try {
			validateVersion(objTmp, instagramPostMonitoring);
		} catch (IllegalStateException ex) {
			throw new OptimisticLockException();
		}

		em.merge(instagramPostMonitoring);
		instagramPostMonitoring = getById(instagramPostMonitoringId);
		
		this.flush();

		return instagramPostMonitoring;
	}
	
	@Override
	public List<InstagramPostMonitoring> listPostsByActiveAccount(Date now, Long from, Long until) {
		
		sql = "Select i From InstagramPostMonitoring i where " +
				"((i.monitoring.account.testExpire > :now and i.monitoring.account.planType = 0)" +
				" or " +
				"(i.monitoring.account.planExpire > :now and i.monitoring.account.planType <> 0)) " +
				"and i.instagramPost.createTime between :from and :until";
		
		query = em.createQuery(sql);
		query.setParameter("from", String.valueOf(from));
		query.setParameter("until", String.valueOf(until));
		query.setParameter("now", now);
		
		return query.getResultList();
	}
	
	@Override
	public List<InstagramPostMonitoring> listByMonitoring(Long idMonitoring) {
		
		sql = "Select i From InstagramPostMonitoring i where i.monitoring.idMonitoring = :idMonitoring";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);

		return query.getResultList();
	}

	@Override
	public List<InstagramPostMonitoring> listByTerm(String term,
			Long idMonitoring) {

		sql = "Select i From InstagramPostMonitoring i where i.monitoring.idMonitoring = :idMonitoring and i.term = :term";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);

		return query.getResultList();
	}

	@Override
	public InstagramPostMonitoring getByComposedId(Long idMonitoring,
			Long idMedia, String term) {
		
		sql = "Select i From InstagramPostMonitoring i where i.monitoring.idMonitoring = :idMonitoring and i.term = :term and i.instagramPost.idMedia = :idMedia";
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
		query.setParameter("idMedia", idMedia);
		
		return (InstagramPostMonitoring) query.getSingleResult();
	}
	
	@Override
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date) {
		
		sql = "SELECT i.term, COUNT(i) FROM InstagramPostMonitoring i " +
				"WHERE i.monitoring.idMonitoring = :idMonitoring AND (i.retrievedDate >= :date AND i.retrievedDate < (:date + 86400)) " +
				"AND i.garbage = 'F' " +
				"GROUP BY i.term";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("date", date);
		
		List<Object[]> result = query.getResultList();
		
		return result;
	}

	@Override
	public Long getCountOfGarbagePostsPerDay(Long idMonitoring, Long date) {
		
		sql = "SELECT COUNT(i) FROM InstagramPostMonitoring i WHERE i.monitoring.idMonitoring = :idMonitoring " + 
				"AND (i.retrievedDate >= :date AND i.retrievedDate < (:date + 86400)) AND i.garbage = 'T'";

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
	public Long getCountOfPostsPerDay(Long idMonitoring, Long date) {
		
		sql = "SELECT COUNT(i) FROM InstagramPostMonitoring i WHERE i.monitoring.idMonitoring = :idMonitoring " + 
				"AND (i.retrievedDate >= :date AND i.retrievedDate < (:date + 86400)) AND i.garbage = 'F'";

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
	public List<Object[]> getCountOfPostsPerHourDay(Long idMonitoring, Long date) {
		
		sql = "SELECT series.hour, count(i) FROM (SELECT generate_series(0,23)*1 AS hour) AS series " +
				"LEFT JOIN (SELECT i.* FROM opsocial.instagrampostsmonitorings as i INNER JOIN opsocial.monitorings AS m ON i.idmonitoring = m.idmonitoring " +
					"WHERE i.idmonitoring = " + idMonitoring + " AND (i.retrieveddate >= " + date + " AND i.retrieveddate < (" + date + " + 86400)) AND i.garbage = 'F') AS i ON " +
				"series.hour = date_part('hour', TO_TIMESTAMP(i.retrieveddate)::timestamp with time zone) " +
			  "GROUP BY series.hour " +  
			  "ORDER BY series.hour";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		return result;
	}
	
	@Override
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date) {
		
		sql = "SELECT ipm.qualification, ipm.term, COUNT(*) FROM opsocial.instagrampostsmonitorings ipm WHERE ipm.idmonitoring = " + idMonitoring + " " + 
				"AND (ipm.retrieveddate >= " + date + " AND ipm.retrieveddate < (" + date + " + 86400)) AND ipm.garbage = 'F' AND ipm.qualification <> '' " + 
				"GROUP BY ipm.qualification, ipm.term";

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
		
		sql = "SELECT mpt.idtag, COUNT(*) FROM opsocial.instagrampostsmonitorings AS ipm " +
				"INNER JOIN opsocial.monitoringspoststags AS mpt ON " +
				"mpt.postid = CAST(ipm.idmedia AS varchar) AND mpt.term = ipm.term AND mpt.idmonitoring = ipm.idmonitoring " + 
				"WHERE mpt.idmonitoring = " + idMonitoring + " AND (ipm.retrieveddate >= " + date + " AND ipm.retrieveddate < (" + date + " + 86400)) " + 
				"AND ipm.garbage = 'F' " +
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
	public InstagramPostMonitoring getByComposedId(Long idMonitoring, Long idMedia) {
		
		sql = "Select i From InstagramPostMonitoring i where i.monitoring.idMonitoring = :idMonitoring and i.instagramPost.idMedia = :idMedia";
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("idMedia", idMedia);
		
		if(!query.getResultList().isEmpty()) {
			return (InstagramPostMonitoring) query.getSingleResult();
		} else {
			return null;
		}
	}
	
	@Override
	public List<InstagramPostMonitoring> listSample(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, Integer sample, List<String> notIn) {
		
		sql = "Select * From opsocial.instagrampostsmonitorings ipm where " +
				"ipm.idMonitoring = " + idMonitoring + " and garbage = 'F' and " +
				"ipm.createdTime >= " + startDate +" and " +
				"ipm.createdTime <= " + endDate;
				
		if(qualification != null) {
			
			sql += "and (";
			
			String[] arrayQualification = qualification.split(";");
			
			for(String q : arrayQualification) {
				if(q.equals("null") || q.equals("Q")) {
					sql += " ipm.qualification is null or";
				} else {
					sql += " ipm.qualification = '" + q + "' or";	
				} 
			}
			
			sql = sql.substring(0, sql.lastIndexOf("or"));
			
			sql += ")";
		}
		
		if(!notIn.isEmpty()) {
			
			sql += "and idmedia not in (";
			
			for(String postId : notIn) {
				sql += postId + ",";
			}
			
			sql = sql.substring(0,sql.lastIndexOf(","));
			
			sql += ")";
		}
		
		sql += " order by random(), createdtime desc limit " + sample;
		
		query = em.createNativeQuery(sql, InstagramPostMonitoring.class);
		
		@SuppressWarnings("unchecked")
		List<InstagramPostMonitoring> instagramPostsMonitorings = (List<InstagramPostMonitoring>) query.getResultList(); 
		
		if(monitoringTags != null && !monitoringTags.isEmpty()) {
			
			List<InstagramPostMonitoring> instagramPostsMonitoringToExclude = new ArrayList<InstagramPostMonitoring>();
			
			for(InstagramPostMonitoring instagramPostMonitoring : instagramPostsMonitorings) {
				
				try {
					
					MaintenanceMonitoringPostTagRemote monitoringPostTagRemote = (MaintenanceMonitoringPostTagRemote) 
							new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringPostTagBean!br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote");
					
					if(!monitoringPostTagRemote.thereIsMonitoringTag(String.valueOf(instagramPostMonitoring.getInstagramPost().getIdMedia()), 
							instagramPostMonitoring.getMonitoring().getIdMonitoring(), instagramPostMonitoring.getTerm(), 
							Profile.INSTAGRAM, monitoringTags)) {
						instagramPostsMonitoringToExclude.add(instagramPostMonitoring);
					}
					
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
			
			instagramPostsMonitorings.removeAll(instagramPostsMonitoringToExclude);
		}
		
		return instagramPostsMonitorings;
	}

	@Override
	public List<InstagramPostMonitoring> getInstagramPostsByUser(Long idMonitoring, Long userId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT ipm FROM InstagramPostMonitoring ipm WHERE ipm.monitoring.idMonitoring = :idMonitoring " + 
				"AND ipm.instagramPost.idInstagramUser = :userId " +
				"AND (ipm.retrievedDate >= :dateFrom AND ipm.retrievedDate < :dateUntil) " +
				"AND ipm.garbage = 'F' " +
				"ORDER BY ipm.retrievedDate DESC";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("userId", userId);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);

		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<InstagramPostMonitoring>();
		}
	}

}
