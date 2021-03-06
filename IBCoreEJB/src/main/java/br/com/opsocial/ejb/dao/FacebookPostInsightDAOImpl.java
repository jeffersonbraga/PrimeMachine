package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.report.AgeGroup;
import br.com.opsocial.ejb.entity.report.FacebookPostInsight;
import br.com.opsocial.ejb.entity.report.PropertyValue;
import br.com.opsocial.ejb.entity.report.SumByDate;
import br.com.opsocial.ejb.utils.reports.UtilReports;

@Stateless
public class FacebookPostInsightDAOImpl extends AbstractDAOImpl<FacebookPostInsight> implements FacebookPostInsightDAO {

	@Override
	public FacebookPostInsight save(FacebookPostInsight facebookPostInsight) throws Exception {
		
		try {
			
			FacebookPostInsight objTmp = getPostInsight(facebookPostInsight.getPostId(), facebookPostInsight.getProperty());
			
			if(objTmp != null) {
				
				facebookPostInsight.setIdFacebookPostInsight(objTmp.getIdFacebookPostInsight());
				facebookPostInsight.setVersion(objTmp.getVersion());
				
				em.merge(facebookPostInsight);
				
				this.flush();

			} else {
				
				em.persist(facebookPostInsight);
				
				this.flush();
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return facebookPostInsight;
	}
	
	@Override
	public FacebookPostInsight getPostInsight(String postId, String property) {
		
		sql = "SELECT fpi FROM FacebookPostInsight AS fpi " +
				"WHERE fpi.postId = :postId AND fpi.property = :property";

		query = em.createQuery(sql);
		query.setParameter("postId", postId);
		query.setParameter("property", property);

		try {
			return (FacebookPostInsight) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public Long getAveragePostsReach(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(AVG(fpi.value), 0), 0) AS bigint) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_UNIQUE +"' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public SumByDate getMajorPostsReachDay(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_UNIQUE +"' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY sumreach DESC, year DESC, month DESC, day DESC " +
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);

		Object[] result;
		SumByDate sumByDate = new SumByDate();
		try {

			result = (Object[]) query.getSingleResult();

			sumByDate.setYear((Integer) result[0]);
			sumByDate.setMonth((Integer) result[1]);
			sumByDate.setDay((Integer) result[2]);
			sumByDate.setSum((Long) result[3]);

		} catch (NoResultException e) {

			Calendar date = new GregorianCalendar();
			date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH) - 1);
			date.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

			sumByDate.setYear(date.get(Calendar.YEAR));
			sumByDate.setMonth(date.get(Calendar.MONTH) + 1);
			sumByDate.setDay(date.get(Calendar.DAY_OF_MONTH));
			sumByDate.setSum(0L);
		}

		return sumByDate;
	}

	@Override
	public Long getAveragePostsInteractions(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(AVG(fpi.value), 0), 0) AS bigint) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES +"' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public SumByDate getMajorPostsInteractionsDay(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES +"' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY sumreach DESC, year DESC, month DESC, day DESC " +
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);

		Object[] result;
		SumByDate sumByDate = new SumByDate();
		try {

			result = (Object[]) query.getSingleResult();

			sumByDate.setYear((Integer) result[0]);
			sumByDate.setMonth((Integer) result[1]);
			sumByDate.setDay((Integer) result[2]);
			sumByDate.setSum((Long) result[3]);

		} catch (NoResultException e) {

			Calendar date = new GregorianCalendar();
			date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH) - 1);
			date.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

			sumByDate.setYear(date.get(Calendar.YEAR));
			sumByDate.setMonth(date.get(Calendar.MONTH) + 1);
			sumByDate.setDay(date.get(Calendar.DAY_OF_MONTH));
			sumByDate.setSum(0L);
		}

		return sumByDate;
	}
	
	@Override
	public Long getPostsStoriesLikeCount(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstories FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}
	
	@Override
	public Long getPostsStoriesCommentCount(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstories FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}
	
	@Override
	public Long getPostsStoriesShareCount(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstories FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}
	
	@Override
	public Long getPostsInteractionsCount(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS suminteractions FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND " +
				"(fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "' " +
				"OR fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "' " +
				"OR fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "' " +
				"OR fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "') " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}
	
	@Override
	public List<SumByDate> getPostsInteractionsByDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " + 
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND " +
				"(fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "' " +
				"OR fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "' " +
				"OR fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "' " +
				"OR fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "') " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}
	
	@Override
	public List<SumByDate> getPostsReachByDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " + 
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_UNIQUE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public List<SumByDate> getPostsReachPaid(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " + 
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_PAID_UNIQUE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public List<SumByDate> getPostsReachOrganic(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " + 
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_ORGANIC_UNIQUE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public List<SumByDate> getPostsReachViral(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_VIRAL_UNIQUE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public Long getPostsReachOrganicCount(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_ORGANIC_UNIQUE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getPostsReachViralCount(String pageId, Long dateFrom,Long dateUntil) {

		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_VIRAL_UNIQUE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getPostsReachPaidCount(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_PAID_UNIQUE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getPostsReachCount(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_UNIQUE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getAverageReachOfPostsPerDay(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(AVG(sumreach), 0), 0) AS bigint) FROM ("+
				"SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " + 
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(SUM(fpi.value) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_UNIQUE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day" +
				") AS avgsumreach";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}
	
	@Override
	public List<SumByDate> getPostsImpressionsByDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " + 
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public List<SumByDate> getPostsImpressionsPaid(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " + 
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_PAID + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public List<SumByDate> getPostsImpressionsOrganic(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " + 
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_ORGANIC + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public List<SumByDate> getPostsImpressionsViral(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_VIRAL + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public Long getPostsImpressionsOrganicCount(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_ORGANIC + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getPostsImpressionsViralCount(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_VIRAL + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getPostsImpressionsPaidCount(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_PAID + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getPostsImpressionsCount(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getAverageImpressionsOfPostsPerDay(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(AVG(sumreach), 0), 0) AS bigint) FROM ("+
				"SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " + 
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(SUM(fpi.value) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day" +
				") AS avgsumreach";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public List<SumByDate> getPostsStoriesLikeByDay(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public List<SumByDate> getPostsStoriesCommentByDay(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public List<SumByDate> getPostsStoriesShareByDay(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public Long getPostsStoriesCount(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstories FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Integer getPostsStoriesLikePercentage(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumstorieslike) / ( " + 
				"SELECT NULLIF(CAST(COALESCE(SUM(fpi.value), 0) AS bigint), 0) AS sumstories FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + "))), 0), 0) AS integer) FROM ( " +
				"SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstorieslike FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")) AS r";

		query = em.createNativeQuery(sql);

		try {
			return (Integer) query.getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}
	}

	@Override
	public Integer getPostsStoriesCommentPercentage(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumstorieslike) / ( " + 
				"SELECT NULLIF(CAST(COALESCE(SUM(fpi.value), 0) AS bigint), 0) AS sumstories FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + "))), 0), 0) AS integer) FROM ( " +
				"SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstorieslike FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")) AS r";

		query = em.createNativeQuery(sql);

		try {
			return (Integer) query.getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}
	}

	@Override
	public Integer getPostsStoriesSharePercentage(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumstorieslike) / ( " + 
				"SELECT NULLIF(CAST(COALESCE(SUM(fpi.value), 0) AS bigint), 0) AS sumstories FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + "))), 0), 0) AS integer) FROM ( " +
				"SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstorieslike FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")) AS r";

		query = em.createNativeQuery(sql);

		try {
			return (Integer) query.getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}
	}

	@Override
	public Map<String, Long> getPostsStoriesLikeByStatusType(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT frp.type, CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstorieslike FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY frp.type";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Long) object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public Map<String, Long> getPostsStoriesCommentByStatusType(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT frp.type, CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstorieslike FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY frp.type";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Long) object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public Map<String, Long> getPostsStoriesShareByStatusType(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT frp.type, CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstorieslike FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY frp.type";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Long) object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public List<SumByDate> getPostsConsumptionsByDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}
	
	@Override
	public List<SumByDate> getPostsConsumptionsPhotoViewByDay(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_PHOTO_VIEW + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public List<SumByDate> getPostsConsumptionsLinkClicksByDay(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_LINK_CLICKS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public List<SumByDate> getPostsConsumptionsVideoPlaysByDay(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_VIDEO_PLAY + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public List<SumByDate> getPostsConsumptionsOtherClicksByDay(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_OTHER_CLICKS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY year, month, day " +
				"ORDER BY year, month, day";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setDay((Integer) object[2]);
			sumByDate.setSum((Long) object[3]);

			sumByDates.add(sumByDate);
		}

		return sumByDates;
	}

	@Override
	public Long getPostsConsumptionsCount(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstories FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Integer getPostsConsumptionsPhotoViewPercentage(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumconsumptionsphotoview) / ( " + 
				"SELECT NULLIF(CAST(COALESCE(SUM(fpi.value), 0) AS bigint), 0) AS sumconsumptions FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + "))), 0), 0) AS integer) FROM ( " +
				"SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumconsumptionsphotoview FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_PHOTO_VIEW + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")) AS r";

		query = em.createNativeQuery(sql);

		try {
			return (Integer) query.getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}
	}

	@Override
	public Integer getPostsConsumptionsLinkClicksPercentage(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumconsumptionslinkclicks) / ( " + 
				"SELECT NULLIF(CAST(COALESCE(SUM(fpi.value), 0) AS bigint), 0) AS sumconsumptions FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + "))), 0), 0) AS integer) FROM ( " +
				"SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumconsumptionslinkclicks FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_LINK_CLICKS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")) AS r";

		query = em.createNativeQuery(sql);

		try {
			return (Integer) query.getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}
	}

	@Override
	public Integer getPostsConsumptionsVideoPlaysPercentage(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumconsumptionsvideoplays) / ( " + 
				"SELECT NULLIF(CAST(COALESCE(SUM(fpi.value), 0) AS bigint), 0) AS sumconsumptions FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + "))), 0), 0) AS integer) FROM ( " +
				"SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumconsumptionsvideoplays FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_VIDEO_PLAY + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")) AS r";

		query = em.createNativeQuery(sql);

		try {
			return (Integer) query.getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}
	}

	@Override
	public Integer getPostsConsumptionsOtherClicksPercentage(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumconsumptionsotherclicks) / ( " + 
				"SELECT NULLIF(CAST(COALESCE(SUM(fpi.value), 0) AS bigint), 0) AS sumconsumptions FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + "))), 0), 0) AS integer) FROM ( " +
				"SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumconsumptionsotherclicks FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_OTHER_CLICKS + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")) AS r";

		query = em.createNativeQuery(sql);

		try {
			return (Integer) query.getSingleResult();
		} catch (NoResultException e) {
			return 0;
		}
	}

	@Override
	public Long getAverageConsumptionsPhotoViewPerPost(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(AVG(fpi.value), 0), 0) AS bigint) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_PHOTO_VIEW +"' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getAverageConsumptionsLinkClicksPerPost(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(AVG(fpi.value), 0), 0) AS bigint) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_LINK_CLICKS +"' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getAverageConsumptionsVideoPlaysPerPost(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(AVG(fpi.value), 0), 0) AS bigint) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_VIDEO_PLAY +"' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getAverageConsumptionsOtherClicksPerPost(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT CAST(COALESCE(ROUND(AVG(fpi.value), 0), 0) AS bigint) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_CONSUMPTIONS_BY_TYPE_OTHER_CLICKS +"' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Map<String, Long> getAveragePostReachByPostType(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT frp.type, CAST(COALESCE(AVG(fpi.value), 0) AS bigint) AS sumstorieslike FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_UNIQUE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " + 
				"GROUP BY frp.type";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Long)object[1] == null ? 0L : (Long)object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public Map<String, Long> getAveragePostInteractionsByPostType(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT frp1.type, frp1.sumstoriesconsumptions / frp2.countpostspertype AS avgpostinteractions " +
				"FROM " +
				"(SELECT frp.type, CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumstoriesconsumptions FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND (fpi.property = '" + UtilReports.POST_STORIES + "' OR fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "') " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY frp.type) frp1 " +
				"INNER JOIN " +
				"(SELECT frp.type, COUNT(frp) AS countpostspertype FROM opsocial.facereportsposts AS frp " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY frp.type) frp2 " +
				"ON " +
				"frp1.type = frp2.type";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Long)object[1] == null ? 0L : (Long)object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public Map<String, Integer> getEngagementPercentageByPostType(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT frpstories.type, CAST(AVG(CAST(ROUND(100 * (frpstories.storiesconsumptions / frpimpressions.impressions), 0) AS integer)) AS integer) " +
				"FROM " +
				"(SELECT frp.postid, frp.type, NULLIF(SUM(fpi.value), 0) AS storiesconsumptions FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND (fpi.property = '" + UtilReports.POST_STORIES + "' OR fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "') " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " +
				"GROUP BY frp.postid, frp.type) frpstories " +
				"INNER JOIN " +
				"(SELECT frp.postid, frp.type, NULLIF(fpi.value, 0) AS impressions FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_UNIQUE + "' " +
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ")) frpimpressions " +
				"ON " +
				"frpstories.postid = frpimpressions.postid " +
				"GROUP BY frpstories.type";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Integer> sumsByProperty = new HashMap<String, Integer>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Integer)object[1] == null ? 0 : (Integer)object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public Map<String, Long> getPostInsights(String postId) {

		sql = "SELECT fpi.property, COALESCE(fpi.value, 0) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid " + 
				"AND fpi.property " +
				"IN('" + UtilReports.POST_IMPRESSIONS_ORGANIC_UNIQUE + "','" + UtilReports.POST_IMPRESSIONS_PAID_UNIQUE + "'," +
				"'" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "','" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "','" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "'," +
				"'" + UtilReports.POST_REACTIONS_ANGER_TOTAL + "','" + UtilReports.POST_REACTIONS_HAHA_TOTAL + "','" + UtilReports.POST_REACTIONS_LOVE_TOTAL + "'," +
				"'" + UtilReports.POST_REACTIONS_SORRY_TOTAL + "','" + UtilReports.POST_REACTIONS_WOW_TOTAL + "','" + UtilReports.POST_REACTIONS_LIKE_TOTAL + "'," +
				"'" + UtilReports.POST_CONSUMPTIONS + "') AND frp.postid = '" + postId + "'";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Long)object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public List<SumByDate> getInsightsByMonth(String pageId, String property) {

		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + property + "' " +
				"WHERE pageid = '" + pageId + "' AND createdtime >= " + UtilReports.getDateForFanPageGrowth() + " " +
				"GROUP BY year, month " +
				"ORDER BY year, month";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		List<SumByDate> sumByDates = new ArrayList<SumByDate>();	

		for (Object[] object : result) {

			SumByDate sumByDate = new SumByDate();
			sumByDate.setYear((Integer) object[0]);
			sumByDate.setMonth((Integer) object[1]);
			sumByDate.setSum((Long) object[2]);

			sumByDates.add(sumByDate);
		}

		// Completes the list of twelve months required by the graphic, if there is no twelve months with data in database.
		if(sumByDates.size() < 12) {

			Calendar start = Calendar.getInstance();
			start.setTimeInMillis(UtilReports.getDateForFanPageGrowth() * 1000L);
			start.setTimeZone(TimeZone.getTimeZone("America/Phoenix"));
			start.set(Calendar.AM_PM, Calendar.AM);
			start.set(Calendar.HOUR_OF_DAY, 0);
			start.set(Calendar.MINUTE, 0);
			start.set(Calendar.SECOND, 0);

			Calendar end = Calendar.getInstance();
			end.setTimeZone(TimeZone.getTimeZone("America/Phoenix"));
			end.set(Calendar.AM_PM, Calendar.AM);
			end.set(Calendar.HOUR_OF_DAY, 0);
			end.set(Calendar.MINUTE, 0);
			end.set(Calendar.SECOND, 0);

			List<Integer> monthSequence = new ArrayList<Integer>();
			List<Integer> yearSequence = new ArrayList<Integer>();
			while(!start.after(end)) {

				monthSequence.add(start.get(Calendar.MONTH) + 1);
				yearSequence.add(start.get(Calendar.YEAR));

				start.add(Calendar.MONTH, 1);
			}


			boolean noInclude;
			for (int i = 0; i < 12; i++) {

				noInclude = false;
				for (int j = 0; j < sumByDates.size(); j++) {
					if(monthSequence.get(i).equals(sumByDates.get(j).getMonth())) {
						noInclude = true;
						break;
					}
				}

				if(!noInclude) {

					SumByDate sumMonthYear = new SumByDate();
					sumMonthYear.setMonth(monthSequence.get(i));
					sumMonthYear.setYear(yearSequence.get(i));
					sumMonthYear.setSum(0L); 

					sumByDates.add(i, sumMonthYear);

					if(sumByDates.size() == 12) {
						break;
					}
				}
			}
		}

		return sumByDates;
	}

	@Override
	public Integer getPostsInteractionPercentage(String pageId, String property) {
		
		sql = "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.suminteraction) / (" +
				"SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS suminteractions FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property IN('" + UtilReports.POST_CONSUMPTIONS + "','" + UtilReports.POST_STORIES + "') " +
				"WHERE pageid = '" + pageId + "' AND createdtime >= " + UtilReports.getDateForFanPageGrowth() + ")), 0), 0) AS integer) FROM (" +
				"SELECT CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS suminteraction FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property = '" + property + "' " +
				"WHERE pageid = '" + pageId + "' AND createdtime >= " + UtilReports.getDateForFanPageGrowth() + ") AS r";

		query = em.createNativeQuery(sql);

		try {
			return (Integer) query.getSingleResult();
		} catch (NoResultException e) {
			return 0;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public SumByDate getMostEngagedMonth(String pageId) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " + 
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(SUM(fpi.value) AS bigint) AS sumengagement FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid AND fpi.property IN('" + UtilReports.POST_CONSUMPTIONS + "','" + UtilReports.POST_STORIES + "') " +
				"WHERE frp.pageid = '" + pageId + "' AND createdtime >= " + UtilReports.getDateForFanPageGrowth() + " " +
				"GROUP BY year, month " +
				"ORDER BY sumengagement DESC, year, month " +
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);

		Object[] result;
		SumByDate sumByDate = new SumByDate();
		try {

			result = (Object[]) query.getSingleResult();

			sumByDate.setYear((Integer) result[0]);
			sumByDate.setMonth((Integer) result[1]);
			sumByDate.setSum((Long) result[2]);

		} catch (NoResultException e) {

			Calendar date = new GregorianCalendar();
			date.set(Calendar.DAY_OF_MONTH, date.get(Calendar.DAY_OF_MONTH) - 1);
			date.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));

			sumByDate.setYear(date.get(Calendar.YEAR));
			sumByDate.setMonth(date.get(Calendar.MONTH) + 1);
			sumByDate.setSum(0L);
		}

		return sumByDate;
	}

	@Override
	public Map<String, Long> getPostInsightsDashboard(String postId) {
		
		sql = "SELECT fpi.property, COALESCE(fpi.value, 0) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid " + 
				"AND fpi.property " +
				"IN('" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "'," +
				   "'" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "'," +
				   "'" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "'," +
				   "'" + UtilReports.POST_CONSUMPTIONS + "') AND frp.postid = '" + postId + "'";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Long) object[1]);
		}

		return sumsByProperty;
	}
	
	@Override
	public Map<String, Long> getPostInsightsHistorical(String postId) {
		
		sql = "SELECT fpi.property, COALESCE(fpi.value, 0) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid " + 
				"AND fpi.property " +
				"IN('" + UtilReports.POST_IMPRESSIONS_UNIQUE + "','" + UtilReports.POST_CONSUMPTIONS + "','" + UtilReports.POST_STORIES + "') AND frp.postid = '" + postId + "'";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Long) object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public List<AgeGroup> getVideoTotalTimeByAgeGender(String pageId, Long dateFrom, Long dateUntil, Character gender) {
		
		List<AgeGroup> ageGroups = UtilReports.initiateAgeGroupList();
		
		sql = "SELECT property, CAST(COALESCE(SUM(fpi.value), 0) AS bigint) AS sumreach FROM opsocial.facereportsposts AS frp " +  
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " + 
				"ON frp.postid = fpi.postid " + 
					"AND fpi.property IN (" + UtilReports.getVideoTotalTimeByAgeGenderProperties(gender) + ") " + 
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime <= " + dateUntil + ") " + 
				"GROUP BY fpi.property " + 
				"ORDER BY fpi.property";
		
		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		for(AgeGroup ageGroup : ageGroups) {
			for(Object[] object : result) {
				if(((String) object[0]).contains(ageGroup.getInitialAgeGroup())) {
					ageGroup.setValue((Long) object[1]);
					break;
				}
			}
		}
		
		return ageGroups;
	}

	@Override
	public List<PropertyValue> getVideoRetention(String postId, String type) {
		
		sql = "SELECT fpi.property, COALESCE(fpi.value, 0) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid " + 
				"WHERE frp.postid = '" + postId + "' AND fpi.type = '" + type + "' ";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();
		
		List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		
		for(Object[] object : result) {
			
			String property = (String) object[0]; 
		
			PropertyValue propertyValue = new PropertyValue();
			propertyValue.setProperty(property.substring(property.lastIndexOf('_') + 1));
			propertyValue.setValue((Long) object[1] == null ? 0L : (Long) object[1]);
			
			propertyValues.add(propertyValue);
		}

		return propertyValues;
	}
	
	@Override
	public Map<String, Long> getPostVideoInsights(String postId) {

		sql = "SELECT fpi.property, COALESCE(fpi.value, 0) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid " + 
				"WHERE frp.postid = '" + postId + "' AND fpi.property IN('" + UtilReports.POST_VIDEO_VIEWS_ORGANIC + "','" + UtilReports.POST_VIDEO_VIEWS_PAID + "'," +
				"'" + UtilReports.POST_VIDEO_VIEWS_AUTOPLAYED + "','" + UtilReports.POST_VIDEO_VIEWS_CLICKED_TO_PLAY + "')";

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();

		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Long)object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public List<AgeGroup> getVideoTotalTimeByAgeGender(String postId, Character gender) {
		
		List<AgeGroup> ageGroups = UtilReports.initiateAgeGroupList();

		sql = "SELECT fpi.property, COALESCE(fpi.value, 0) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid " + 
				"WHERE frp.postid = '" + postId + "' AND fpi.property IN(" + UtilReports.getVideoTotalTimeByAgeGenderProperties(gender) + ")";
		
		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		for(AgeGroup ageGroup : ageGroups) {
			for(Object[] object : result) {
				if(((String) object[0]).contains(ageGroup.getInitialAgeGroup())) {
					ageGroup.setValue((Long) object[1]);
					break;
				}
			}
		}
		
		return ageGroups;
	}

}
