package br.com.opsocial.ejb.dao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramReportPost;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;
import br.com.opsocial.ejb.entity.report.FilterLikesComments;

@Stateless
public class InstagramReportPostDAOImpl extends AbstractDAOImpl<InstagramReportPost> implements InstagramReportPostDAO {

	@Override
	public InstagramReportPost save(InstagramReportPost instagramReportPost) throws Exception {

		try {

			InstagramReportPost objTmp = getById(instagramReportPost.getIdMedia());

			if(objTmp != null) {

				try {
					validateVersion(objTmp, instagramReportPost);
				} catch (IllegalStateException ex) {
					throw new OptimisticLockException();
				}

				em.merge(instagramReportPost);

				this.flush(instagramReportPost);

			} else {

				em.persist(instagramReportPost);

				this.flush(instagramReportPost);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}

		return instagramReportPost;
	}


	@Override
	public InstagramReportPost getLastPost(String idInstagramUser) {
		
		sql = "SELECT irp FROM InstagramReportPost irp WHERE irp.idInstagramUser = :idInstagramUser " +
				"ORDER BY irp.createTime DESC";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);
		query.setFirstResult(0);
		query.setMaxResults(1);

		try {
			return (InstagramReportPost) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public Long getLastIdMedia(String idInstagramUser) {

		sql = "SELECT irp.idMedia FROM InstagramReportPost irp WHERE irp.idInstagramUser = :idInstagramUser " +
				"ORDER BY irp.idMedia DESC";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);
		query.setFirstResult(0);
		query.setMaxResults(1);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public Long getCountOfPosts(String idInstagramUser) {

		sql = "SELECT COUNT(irp.idMedia) FROM InstagramReportPost irp WHERE irp.idInstagramUser = :idInstagramUser";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public Long getCountOfLikes(String idInstagramUser) {

		sql = "SELECT SUM(irp.likes) FROM InstagramReportPost irp WHERE irp.idInstagramUser = :idInstagramUser";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);

		Long countOfLikes = null;
		try {
			
			countOfLikes = (Long) query.getSingleResult();
			
			if(countOfLikes == null) {
				countOfLikes = 0L;
			}
			
		} catch (NoResultException e) {
			e.printStackTrace();
			countOfLikes = 0L;
		}
		
		return countOfLikes;
	}

	@Override
	public Long getCountOfComments(String idInstagramUser) {

		sql = "SELECT SUM(irp.comments) FROM InstagramReportPost irp WHERE irp.idInstagramUser = :idInstagramUser";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);

		Long countOfComments = null;
		try {
			
			countOfComments = (Long) query.getSingleResult();
			
			if(countOfComments == null) {
				countOfComments = 0L;
			}
			
		} catch (NoResultException e) {
			e.printStackTrace();
			countOfComments = 0L;
		}
		
		return countOfComments;
	}

	@Override
	public Object[] getFilterMostUsed(String idInstagramUser) {

		sql = "SELECT irp.filter, COUNT(irp.filter) FROM opsocial.instagramreportsposts irp " + 
				"WHERE irp.idinstagramUser = '" + idInstagramUser + "' " +
				"GROUP BY irp.filter " +
				"ORDER BY COUNT(irp.filter) DESC " + 
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);

		Object[] result = null;
		try {

			result = (Object[]) query.getSingleResult();

		} catch (NoResultException e) {

			result = new Object[2];
			result[0] = "nenhum";
			result[1] = 0L;
		}

		return result;
	}

	@Override
	public Object[] getFilterLessUsed(String idInstagramUser) {

		sql = "SELECT irp.filter, COUNT(irp.filter) FROM opsocial.instagramreportsposts irp " + 
				"WHERE irp.idInstagramUser = '" + idInstagramUser + "' " +
				"GROUP BY irp.filter " +
				"ORDER BY COUNT(irp.filter) ASC " + 
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);

		Object[] result = null;
		try {

			result = (Object[]) query.getSingleResult();

		} catch (NoResultException e) {

			result = new Object[2];
			result[0] = "nenhum";
			result[1] = 0L;
		}

		return result;
	}

	@Override
	public Map<Integer, Long> getPostsPerMonthYear(String idInstagramUser, Integer year) {

		sql = "SELECT CAST(date_part('month', TO_TIMESTAMP(irp.createtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(COALESCE(COUNT(*), 0) AS bigint) AS sumposts FROM opsocial.instagramreportsposts AS irp " +
				"WHERE idinstagramuser = '" + idInstagramUser + "' " + 
				"AND CAST(date_part('year', TO_TIMESTAMP(irp.createtime)::timestamp with time zone) as integer) = " + year + " " +
				"GROUP BY month " +
				"ORDER BY month";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<Integer, Long> sumsByProperty = new HashMap<Integer, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((Integer)object[0], (Long)object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public Long getCountOfPosts(String idInstagramUser, Integer year) {

		sql = "SELECT CAST(COALESCE(COUNT(*), 0) AS bigint) AS sumposts FROM opsocial.instagramreportsposts AS irp " +
				"WHERE idinstagramuser = '" + idInstagramUser + "' " + 
				"AND CAST(date_part('year', TO_TIMESTAMP(irp.createtime)::timestamp with time zone) as integer) = " + year;

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return 0L;
		}
	}

	@Override
	public Object[] getMonthWithMorePosts(String idInstagramUser, Integer year) {

		sql = "SELECT CAST(date_part('month', TO_TIMESTAMP(irp.createtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(COALESCE(COUNT(*), 0) AS bigint) AS sumposts FROM opsocial.instagramreportsposts AS irp " +
				"WHERE idinstagramuser = '" + idInstagramUser + "' " + 
				"AND CAST(date_part('year', TO_TIMESTAMP(irp.createtime)::timestamp with time zone) as integer) = " + year + " " +
				"GROUP BY month " +
				"ORDER BY sumposts DESC, month DESC " + 
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);

		Object[] result = null;
		try {

			result = (Object[]) query.getSingleResult();

		} catch (NoResultException e) {

			result = new Object[2];
			result[0] = 1;
			result[1] = 0L;
		}

		return result;
	}

	@Override
	public Map<Integer, Long> getPostsPerWeek(String idInstagramUser) {

		sql = "SELECT series.weekday, CAST(COALESCE(COUNT(*), 0) AS bigint) FROM (SELECT generate_series(0,6) AS weekday) AS series " +
				"INNER JOIN ( " +
				"SELECT irp.* FROM opsocial.instagramreportsposts as irp " +
				"WHERE idinstagramuser = '" + idInstagramUser + "') " +
				"AS irp ON " +
				"series.weekday = extract(DOW FROM TO_TIMESTAMP(irp.createtime)::timestamp with time zone) " +
				"GROUP BY series.weekday " +
				"ORDER BY series.weekday";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<Integer, Long> fansOnlinePerWeekDay = new TreeMap<Integer, Long>();
		for(Object[] object : result) {
			fansOnlinePerWeekDay.put((Integer) object[0], (Long) object[1]);
		}

		return fansOnlinePerWeekDay;
	}

	@Override
	public Object[] getWeekDayWithMorePosts(String idInstagramUser) {

		sql = "SELECT series.weekday, COALESCE(COUNT(*), 0) AS countposts FROM (SELECT generate_series(0,6) AS weekday) AS series " +
				"INNER JOIN ( " +
				"SELECT irp.* FROM opsocial.instagramreportsposts as irp " +
				"WHERE idinstagramuser = '" + idInstagramUser + "') " +
				"AS irp ON " +
				"series.weekday = extract(DOW FROM TO_TIMESTAMP(irp.createtime)::timestamp with time zone) " +
				"GROUP BY series.weekday " +
				"ORDER BY countposts DESC " + 
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);

		Object[] result = null;
		try {

			result = (Object[]) query.getSingleResult();

		} catch (NoResultException e) {

			result = new Object[2];
			result[0] = 0;
			result[1] = 0L;
		}

		return result;
	}

	@Override
	public Map<Integer, Long> getPostsPerHour(String idInstagramUser) {

		sql = "SELECT series.hour, count(irp.idmedia) FROM (SELECT generate_series(0,23) * 1 AS hour) AS series " +
				"LEFT JOIN (SELECT irp.* FROM opsocial.instagramreportsposts irp " +
				"WHERE idinstagramuser = '" + idInstagramUser + "') AS irp ON " +
				"series.hour = date_part('hour', TO_TIMESTAMP(irp.createtime)::timestamp with time zone) " +
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
	public Object[] getHourWithMorePosts(String idInstagramUser) {

		sql = "SELECT series.hour, count(*) AS countposts FROM (SELECT generate_series(0,23) * 1 AS hour) AS series " +
				"LEFT JOIN (SELECT irp.* FROM opsocial.instagramreportsposts irp " +
				"WHERE idinstagramuser = '" + idInstagramUser + "') AS irp ON " +
				"series.hour = date_part('hour', TO_TIMESTAMP(irp.createtime)::timestamp with time zone) " +
				"GROUP BY series.hour " +
				"ORDER BY countposts DESC " + 
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
	public List<InstagramReportPost> getPostsWithMoreLikes(String idInstagramUser) {

		sql = "SELECT irp FROM InstagramReportPost irp WHERE irp.idInstagramUser = :idInstagramUser " + 
				"ORDER BY irp.likes DESC";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);
		query.setFirstResult(0);
		query.setMaxResults(5);

		return query.getResultList();
	}

	@Override
	public List<InstagramReportPost> getPostsWithMoreComments(String idInstagramUser) {

		sql = "SELECT irp FROM InstagramReportPost irp WHERE irp.idInstagramUser = :idInstagramUser " + 
				"ORDER BY irp.comments DESC";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);
		query.setFirstResult(0);
		query.setMaxResults(5);

		return query.getResultList();
	}

	@Override
	public List<FilterLikesComments> getFilterLikesComments(String idInstagramUser) {

		sql = "SELECT irp.filter, CAST(SUM(irp.likes) AS bigint), CAST(SUM(irp.comments) AS bigint) FROM InstagramReportPost AS irp " +
					"WHERE irp.idInstagramUser = :idInstagramUser " +
					"GROUP BY irp.filter ORDER BY irp.filter ASC";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);

		List<Object[]> result = query.getResultList();

		List<FilterLikesComments> filtersLikesComments = new ArrayList<>();
		for (Object[] object : result) {
			
			FilterLikesComments filterLikesComments = new FilterLikesComments();
			filterLikesComments.setFilter((String) object[0]);
			filterLikesComments.setLikes((Long) object[1]);
			filterLikesComments.setComments((Long) object[2]);
			
			filtersLikesComments.add(filterLikesComments);
		}

		return filtersLikesComments;
	}

	@Override
	public Object[] getFilterWithMoreLikes(String idInstagramUser) {
		
	sql = "SELECT irp.filter, SUM(irp.likes) AS likescount FROM opsocial.instagramreportsposts AS irp " +
				"WHERE irp.idinstagramuser = '" + idInstagramUser + "' " +
				"GROUP BY irp.filter " + 
				"ORDER BY likescount DESC " +
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);

		Object[] result = null;
		try {

			result = (Object[]) query.getSingleResult();

		} catch (NoResultException e) {

			result = new Object[2];
			result[0] = "nenhum";
			result[1] = 0L;
		}

		return result;
	}

	@Override
	public Object[] getFilterWithMoreComments(String idInstagramUser) {
		
		sql = "SELECT irp.filter, SUM(irp.comments) AS commentscount FROM opsocial.instagramreportsposts AS irp " +
				"WHERE irp.idinstagramuser = '" + idInstagramUser + "' " +
				"GROUP BY irp.filter " + 
				"ORDER BY commentscount DESC " +
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);

		Object[] result = null;
		try {

			result = (Object[]) query.getSingleResult();

		} catch (NoResultException e) {

			result = new Object[2];
			result[0] = "nenhum";
			result[1] = 0L;
		}

		return result;
	}

	@Override
	public List<InstagramReportPost> getPostsWithMoreLikes(String idInstagramUser, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT irp FROM InstagramReportPost irp WHERE irp.idInstagramUser = :idInstagramUser " + 
				" AND (irp.createTime >= :dateFrom AND irp.createTime < :dateUntil) " + 
				"ORDER BY irp.likes DESC";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil + 86400L);
		query.setFirstResult(0);
		query.setMaxResults(5);

		return query.getResultList();
	}

	@Override
	public List<InstagramReportPost> getPostsWithMoreComments(String idInstagramUser, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT irp FROM InstagramReportPost irp WHERE irp.idInstagramUser = :idInstagramUser " + 
				" AND (irp.createTime >= :dateFrom AND irp.createTime < :dateUntil) " + 
				"ORDER BY irp.comments DESC";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil + 86400L);
		query.setFirstResult(0);
		query.setMaxResults(5);

		return query.getResultList();
	}
	
	@Override
	public List<InstagramReportPost> getPostsWithMoreInteractions(String idInstagramUser, Long dateFrom, Long dateUntil) {

		sql = "SELECT idMedia FROM opsocial.instagramreportsposts irp WHERE irp.idinstagramUser = '" + idInstagramUser + "' " + 
				" AND (irp.createtime >= '" + dateFrom + "' AND irp.createtime < '" + (dateUntil + 86400) + "') " + 
				"ORDER BY irp.comments + irp.likes DESC " +
				"OFFSET 0 LIMIT 10";
		
		query = em.createNativeQuery(sql);
		
		List<Long> ids = new ArrayList<Long>();
		
		for(Object id : query.getResultList()) {
			ids.add(Long.valueOf(id.toString()));
		}
		
		if(!ids.isEmpty()) {
			
			sql = "Select irp From InstagramReportPost irp WHERE irp.idMedia IN :ids";
			
			query = em.createQuery(sql);
			query.setParameter("ids", ids);
			
			return query.getResultList();
		} else {
			return new ArrayList<InstagramReportPost>();
		}
	}

	@Override
	public Map<String, Long> getFiltersCounts(String idInstagramUser) {
		
		sql = "SELECT irp.filter, COUNT(irp) FROM InstagramReportPost irp WHERE CAST(irp.idInstagramUser AS VARCHAR) = :idInstagramUser " + 
				"GROUP BY irp.filter";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);

		List<Object[]> result = query.getResultList();

		Map<String, Long> filtersCounts = new TreeMap<String, Long>();
		for(Object[] object : result) {
			filtersCounts.put((String) object[0], (Long) object[1]);
		}

		return filtersCounts;
	}

	@Override
	public Map<String, Long> getInteractionsPerDay(String idInstagramUser, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT extract('epoch' from date_trunc('day', TO_TIMESTAMP(irp.createtime)::timestamp with time zone))::bigint AS day, " +
				"CAST(COALESCE(SUM(irp.likes), 0) AS bigint) + CAST(COALESCE(SUM(irp.comments), 0) AS bigint) AS suminteractions FROM opsocial.instagramreportsposts AS irp " +
				"WHERE irp.idinstagramuser = '" + idInstagramUser + "' AND (irp.createtime >= " + dateFrom + " AND irp.createtime < " + dateUntil + " + 86400) " +
				"GROUP BY day " +
				"ORDER BY day";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> likeCounts = new LinkedHashMap<String, Long>();
		for(Object[] object : result) {
			likeCounts.put(new SimpleDateFormat("dd/MM").format(new Date((Long) object[0] * 1000L)), (Long)object[1]);
		}
		
		return likeCounts;
	}

	@Override
	public List<InstagramSimpleDataDTO> getPostsPerMonthYearSimpleData(String idInstagramUser, Integer year) {

		sql = "SELECT CAST(date_part('month', TO_TIMESTAMP(irp.createtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(COALESCE(COUNT(*), 0) AS bigint) AS sumposts FROM opsocial.instagramreportsposts AS irp " +
				"WHERE idinstagramuser = '" + idInstagramUser + "' " + 
				"AND CAST(date_part('year', TO_TIMESTAMP(irp.createtime)::timestamp with time zone) as integer) = " + year + " " +
				"GROUP BY month " +
				"ORDER BY month";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();
		List<InstagramSimpleDataDTO> dataReport = new ArrayList<InstagramSimpleDataDTO>();

		for (Object[] o : result) {

			InstagramSimpleDataDTO item = new InstagramSimpleDataDTO();
			item.setKey(o[0]);
			item.setValue(o[1]);			
			dataReport.add(item);
		}

		return dataReport;
	}

	@Override
	public List<InstagramSimpleDataDTO> getPostsPerWeekSimpleData(String idInstagramUser) {

		sql = "SELECT series.weekday, COALESCE(COUNT(*), 0) FROM (SELECT generate_series(0,6) AS weekday) AS series " +
				"INNER JOIN ( " +
				"SELECT irp.* FROM opsocial.instagramreportsposts as irp " +
				"WHERE idinstagramuser = '" + idInstagramUser + "') " +
				"AS irp ON " +
				"series.weekday = extract(DOW FROM TO_TIMESTAMP(irp.createtime)::timestamp with time zone) " +
				"GROUP BY series.weekday " +
				"ORDER BY series.weekday";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();
		List<InstagramSimpleDataDTO> dataReport = new ArrayList<InstagramSimpleDataDTO>();

		for (Object[] o : result) {

			InstagramSimpleDataDTO item = new InstagramSimpleDataDTO();
			item.setKey(o[0]);
			item.setValue(o[1]);			
			dataReport.add(item);
		}

		return dataReport;
	}

	@Override
	public List<InstagramSimpleDataDTO> getPostsPerHourSimpleData(String idInstagramUser) {

		sql = "SELECT series.hour, count(irp.idmedia) FROM (SELECT generate_series(0,23) * 1 AS hour) AS series " +
				"LEFT JOIN (SELECT irp.* FROM opsocial.instagramreportsposts irp " +
				"WHERE idinstagramuser = '" + idInstagramUser + "') AS irp ON " +
				"series.hour = date_part('hour', TO_TIMESTAMP(irp.createtime)::timestamp with time zone) " +
				"GROUP BY series.hour " +
				"ORDER BY series.hour";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();
		List<InstagramSimpleDataDTO> dataReport = new ArrayList<InstagramSimpleDataDTO>();

		for (Object[] o : result) {

			InstagramSimpleDataDTO item = new InstagramSimpleDataDTO();
			item.setKey(o[0]);
			item.setValue(o[1]);			
			dataReport.add(item);
		}

		return dataReport;
	}

	public Long getCountOfPosts(String idInstagramUser, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COUNT(irp.idMedia) AS bigint) FROM InstagramReportPost irp "
				+ "WHERE irp.idInstagramUser = :idInstagramUser and (irp.createTime >= :dateFrom AND irp.createTime < :dateUntil)";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil + 86400L);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return 0L;
		}
	}

	public Long getCountOfPostsByMedia(String idInstagramUser, Long dateFrom, Long dateUntil, String media) {

		sql = "SELECT CAST(COUNT(irp.idMedia) AS bigint) FROM InstagramReportPost irp "
				+ "WHERE irp.idInstagramUser = :idInstagramUser and (irp.createTime >= :dateFrom AND irp.createTime < :dateUntil) and irp.type = :media";

		query = em.createQuery(sql);
		query.setParameter("idInstagramUser", idInstagramUser);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil + 86400L);
		query.setParameter("media", media);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return 0L;
		}
	}
	
	@Override
	public BigDecimal getAveragePostsInteractions(String idInstagramUser, Long dateFrom, Long dateUntil) {

		sql = "SELECT COALESCE(AVG(irp.comments + irp.likes), 0) FROM opsocial.instagramreportsposts irp "
				+ "WHERE irp.idinstagramUser = '" + idInstagramUser + "' AND (irp.createtime >= " + dateFrom + " AND irp.createtime < " + dateUntil + ")";
		
		query = em.createNativeQuery(sql);
		
		try {
			return (BigDecimal) query.getSingleResult();
		} catch (NoResultException e) {
			e.printStackTrace();
			return new BigDecimal(0);
		}
	}
	
	@Override
	public BigDecimal getAverageInteractionsFollower(String idInstagramUser, Long dateFrom, Long dateUntil) {

		sql = "SELECT COALESCE(ROUND((z.soma / COALESCE(z.total,1)), 2), 0) FROM "
				+ "(SELECT count(*) AS total, SUM(y.soma) AS soma FROM ( "
					+ "SELECT x.username, count(*) AS soma FROM (SELECT irp.idmedia, ilp.username FROM opsocial.instagramreportsposts irp "
					+ "INNER JOIN opsocial.instagrampostslikes ilp on ilp.idmedia = irp.idmedia WHERE irp.idinstagramUser = '" + idInstagramUser + "' AND (irp.createtime >= '" + dateFrom + "' AND irp.createtime < '" + dateUntil + "') "
							+ "ORDER BY ilp.username) x GROUP BY x.username) y ) z";

		query = em.createNativeQuery(sql);

		return (BigDecimal) query.getSingleResult();
	}

	@Override
	public List<InstagramReport> getAudiencesPostsInteraction(String idInstagramUser, Long dateFromVerified, Long dateUntil) {

		sql = "SELECT extract('epoch' from date_trunc('day', TO_TIMESTAMP(irp.createtime)::timestamp with time zone))::bigint AS day, " +
				"CAST(COALESCE(SUM(irp.likes), 0) AS bigint) + CAST(COALESCE(SUM(irp.comments), 0) AS bigint) AS suminteractions FROM opsocial.instagramreportsposts AS irp " +
				"WHERE irp.idinstagramuser = '" + idInstagramUser + "' AND (irp.createtime >= " + dateFromVerified + " AND irp.createtime < " + dateUntil + " + 86400) " +
				"GROUP BY day " +
				"ORDER BY day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> likeCounts = new LinkedHashMap<String, Long>();
		for(Object[] object : result) {
			likeCounts.put(new SimpleDateFormat("dd/MM").format(new Date((Long) object[0] * 1000L)), (Long)object[1]);
		}

		return null;
	}

}