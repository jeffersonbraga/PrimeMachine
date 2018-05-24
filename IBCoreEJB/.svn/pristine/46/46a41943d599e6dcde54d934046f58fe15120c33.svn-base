package br.com.opsocial.ejb.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.report.FaceReportPost;
import br.com.opsocial.ejb.entity.report.PostEngagementPerc;
import br.com.opsocial.ejb.entity.report.PostEngagementStats;
import br.com.opsocial.ejb.entity.report.SumByDate;
import br.com.opsocial.ejb.utils.reports.UtilReports;

@Stateless
public class FaceReportPostDAOImpl extends AbstractDAOImpl<FaceReportPost> implements FaceReportPostDAO {

	@Override
	public List<FaceReportPost> getEntitiesByPageId(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT frp FROM FaceReportPost frp WHERE frp.pageId = :pageId " +
				"AND (frp.createdTime >= :dateFrom AND frp.createdTime < :dateUntil) ORDER BY frp.createdTime DESC";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);

		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<FaceReportPost>();
		}
	}

	@Override
	public List<FaceReportPost> getPostsMostEngaged(String pageId, Integer offset, Integer limit) {
		
		sql = "SELECT frpimpressions.postid, frpimpressions.pageid, frpimpressions.message, frpimpressions.type, " +
				"frpimpressions.permalink, frpimpressions.createdtime, frpimpressions.likes, frpimpressions.comments, " + 
				"frpimpressions.shares, frpimpressions.version, ROUND(100 * (frpstories.storiesconsumptions / frpimpressions.impressions), 2) AS engagement " +
				"FROM " +
					"(SELECT frp.postid, NULLIF(SUM(fpi.value), 0) AS storiesconsumptions FROM opsocial.facereportsposts AS frp " +
					"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
					"ON frp.postid = fpi.postid AND (fpi.property = '" + UtilReports.POST_STORIES + "' OR fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "') " +
					"WHERE frp.pageId = '" + pageId + "' " +
					"GROUP BY frp.postid) frpstories " +
				"INNER JOIN " +
					"(SELECT frp.postid, frp.pageid, frp.message, frp.type, frp.permalink, frp.createdtime, frp.likes, frp.comments, frp.shares, frp.version, NULLIF(fpi.value, 0) AS impressions FROM opsocial.facereportsposts AS frp " +
					"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
					"ON frp.postid = fpi.postid AND fpi.property = '" + UtilReports.POST_IMPRESSIONS_UNIQUE + "' " +
					"WHERE frp.pageId = '" + pageId + "') frpimpressions " +
				"ON " +
					"frpstories.postid = frpimpressions.postid " +
				"ORDER BY engagement DESC NULLS LAST " +
				"OFFSET " + offset + " LIMIT " + limit;

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		List<FaceReportPost> reportPosts = new ArrayList<FaceReportPost>();
		
		for(Object[] object : result) {
			
			FaceReportPost reportPost = new FaceReportPost();
			reportPost.setPostId((String) object[0]);
			reportPost.setPageId((String) object[1]);
			reportPost.setMessage((String) object[2]);
			reportPost.setType((String) object[3]);
			reportPost.setPermalink((String) object[4]);
			reportPost.setCreatedTime((Long) object[5]);
			reportPost.setLikes((Integer) object[6]);
			reportPost.setComments((Integer) object[7]);
			reportPost.setShares((Integer) object[8]);
			reportPost.setVersion((Long) object[9]);
			reportPost.setEngagement(object[10] != null ? ((BigDecimal) object[10]).doubleValue() : 0);
			
			reportPosts.add(reportPost);
		}
		
		return reportPosts;
	}

	@Override
	public List<PostEngagementStats> getPostsEngagementStats(String pageId) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(createdtime)::timestamp with time zone) as integer), CAST(date_part('month', TO_TIMESTAMP(createdtime)::timestamp with time zone) as integer), " +
				"SUM(likes) as likes, SUM(shares) as shares, SUM(comments) as comments, CAST(SUM(consumptions) as bigint) as clicks FROM opsocial.facereportsposts " + 
				"WHERE pageid = '" + pageId + "' AND createdtime >= " + UtilReports.getDateForFanPageGrowth() + " " +
					"GROUP BY date_part('year', TO_TIMESTAMP(createdtime)::timestamp with time zone), date_part('month', TO_TIMESTAMP(createdtime)::timestamp with time zone) " +
					"ORDER BY date_part('year', TO_TIMESTAMP(createdtime)::timestamp with time zone), date_part('month', TO_TIMESTAMP(createdtime)::timestamp with time zone)";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();		
		
		List<PostEngagementStats> postEngagementStatsList = new ArrayList<PostEngagementStats>();
		
		for (Object[] object : result) {
			
			PostEngagementStats postEngagementStats = new PostEngagementStats();
			postEngagementStats.setYear((Integer) object[0]);
			postEngagementStats.setMonth((Integer) object[1]);
			postEngagementStats.setLikes((Long) object[2]);
			postEngagementStats.setShares((Long) object[3]);
			postEngagementStats.setComments((Long) object[4]);
			postEngagementStats.setClicks((Long) object[5]);
						
			postEngagementStatsList.add(postEngagementStats);
		}
		
		// Completes the list of twelve months required by the graphic, if there is no twelve months with data in database.
		if(postEngagementStatsList.size() < 12) {
			
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
				for (int j = 0; j < postEngagementStatsList.size(); j++) {
					if(monthSequence.get(i).equals(postEngagementStatsList.get(j).getMonth())) {
						noInclude = true;
						break;
					}
				}
				
				if(!noInclude) {
					
					PostEngagementStats postEngagementStats = new PostEngagementStats();
					postEngagementStats.setYear(yearSequence.get(i));
					postEngagementStats.setMonth(monthSequence.get(i));
					postEngagementStats.setLikes(0L);
					postEngagementStats.setShares(0L);
					postEngagementStats.setComments(0L);
					postEngagementStats.setClicks(0L);
					
					postEngagementStatsList.add(i, postEngagementStats);
					
					if(postEngagementStatsList.size() == 12) {
						break;
					}
				}
			}
		}
		
		return postEngagementStatsList;
	}

	@Override
	public PostEngagementPerc getPostsEngagementPercs(String pageId) {
		
		sql = "SELECT CAST(ROUND(100 * (CAST(SUM(likes) AS numeric) / (SUM(frp.likes) + SUM(frp.shares) + SUM(frp.comments) + CAST(SUM(frp.consumptions) as bigint))), 0) as integer) as likes, " +
				"CAST(ROUND(100 * (CAST(SUM(shares) AS numeric) / (SUM(frp.likes) + SUM(frp.shares) + SUM(frp.comments) + CAST(SUM(frp.consumptions) as bigint))), 0) as integer) as shares, " +
				"CAST(ROUND(100 * (CAST(SUM(comments) AS numeric) / (SUM(frp.likes) + SUM(frp.shares) + SUM(frp.comments) + CAST(SUM(frp.consumptions) as bigint))), 0) as integer) as comments, " + 
				"CAST(ROUND(100 * (CAST(SUM(consumptions) AS numeric) / (SUM(frp.likes) + SUM(frp.shares) + SUM(frp.comments) + CAST(SUM(frp.consumptions) as bigint))), 0) as integer) as clicks " +
				"FROM opsocial.facereportsposts AS frp " +
				"WHERE pageid = '" + pageId + "' AND createdtime >= " + UtilReports.getDateForFanPageGrowth();

		query = em.createNativeQuery(sql);
		
		Object[] result = (Object[]) query.getSingleResult();
		
		PostEngagementPerc postEngagementPerc = new PostEngagementPerc();
		postEngagementPerc.setLikes((Integer) result[0]);
		postEngagementPerc.setShares((Integer) result[1]);
		postEngagementPerc.setComments((Integer) result[2]);
		postEngagementPerc.setClicks((Integer) result[3]);
		
		return postEngagementPerc;
	}

	@Override
	public SumByDate getMostEngagementMonth(String pageId) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(createdtime)::timestamp with time zone) as integer), CAST(date_part('month', TO_TIMESTAMP(createdtime)::timestamp with time zone) as integer), " + 
				"SUM(likes) + SUM(shares) + SUM(comments) + CAST(SUM(consumptions) as bigint) as engagement FROM opsocial.facereportsposts " +
				"WHERE pageid = '" + pageId + "' AND createdtime >= " + UtilReports.getDateForFanPageGrowth() + " " + 
				"GROUP BY date_part('year', TO_TIMESTAMP(createdtime)::timestamp with time zone), date_part('month', TO_TIMESTAMP(createdtime)::timestamp with time zone) " + 
				"ORDER BY engagement DESC, date_part('year', TO_TIMESTAMP(createdtime)::timestamp with time zone) DESC, date_part('month', TO_TIMESTAMP(createdtime)::timestamp with time zone) DESC " +
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);
		
		Object[] result = (Object[]) query.getSingleResult();
		
		SumByDate sumMonthYear = new SumByDate();
		sumMonthYear.setYear((Integer) result[0]);
		sumMonthYear.setMonth((Integer) result[1]);
		sumMonthYear.setSum((Long) result[2]);
		
		return sumMonthYear;
	}

	@Override
	public List<PostEngagementStats> getPostsEngagementStats(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS year, " +
				"CAST(date_part('month', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(frp.createdtime)::timestamp with time zone) as integer) AS day, fpi.property, CAST(COALESCE(SUM(fpi.value), 0) AS bigint) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid " + 
				"AND fpi.property " +
				"IN('" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "','" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "'," +
				"'" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "') " + 
				"AND frp.pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime < " + dateUntil + ") " +
				"GROUP BY year, month, day, fpi.property " +
				"ORDER BY year, month, day, fpi.property";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();		
		
		List<PostEngagementStats> postEngagementStatsList = new ArrayList<PostEngagementStats>();	
		
		int i = 0;
		PostEngagementStats postEngagementStats = new PostEngagementStats();
		for (Object[] object : result) {
			
			if(i == 0) {
				postEngagementStats = new PostEngagementStats();
			}
			
			postEngagementStats.setYear((Integer) object[0]);
			postEngagementStats.setMonth((Integer) object[1]);
			postEngagementStats.setDay((Integer) object[2]);
			
			String property = (String) object[3];
			
			if(property.equals(UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE)) {
				postEngagementStats.setLikes((Long) object[4]);
			} else if(property.equals(UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT)) {
				postEngagementStats.setComments((Long) object[4]);
			} else {
				postEngagementStats.setShares((Long) object[4]);
			}
			
			i++;
			if(i == 3) {
				postEngagementStatsList.add(postEngagementStats);
				i = 0;
			}
		}
		
		return postEngagementStatsList;
	}

	@Override
	public Map<String, Long> getPostsEngagementTotalStats(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT fpi.property, CAST(COALESCE(SUM(fpi.value), 0) AS bigint) FROM opsocial.facereportsposts AS frp " +
				"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
				"ON frp.postid = fpi.postid " + 
				"AND fpi.property " +
				"IN('" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "','" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "'," +
						"'" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "') " + 
				"AND frp.pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime < " + dateUntil + ") " +
				"GROUP BY fpi.property " +	
				"ORDER BY fpi.property ASC";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Long)object[1]);
		}

		return sumsByProperty;
	}
	
	@Override
	public List<FaceReportPost> getTopPostsMostEngaged(String pageId, Long dateFrom, Long dateUntil, Integer limit) {
	
		sql = "SELECT frpimpressions.postid, frpimpressions.pageid, frpimpressions.message, frpimpressions.type, " +
				"frpimpressions.photosrc, frpimpressions.photowidth, frpimpressions.photoheight, " + 
				"frpimpressions.permalink, frpimpressions.createdtime, frpimpressions.likes, frpimpressions.comments, " + 
				"frpimpressions.shares, frpimpressions.version, ROUND(100 * (frpstories.storiesconsumptions / frpimpressions.impressions), 2) AS engagement " +
				"FROM " +
					"(SELECT frp.postid, NULLIF(SUM(fpi.value), 0) AS storiesconsumptions FROM opsocial.facereportsposts AS frp " +
					"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
					"ON frp.postid = fpi.postid AND (fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "' OR fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "' OR " +
							"fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "' OR fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "') " +
					"WHERE frp.pageId = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime < " + dateUntil + ") " +
					"GROUP BY frp.postid) frpstories " +
				"INNER JOIN " +
					"(SELECT frp.postid, frp.pageid, frp.message, frp.type, frp.photosrc, frp.photowidth, frp.photoheight, frp.permalink, frp.createdtime, frp.likes, frp.comments, frp.shares, frp.version, NULLIF(SUM(fpi.value), 0) AS impressions FROM opsocial.facereportsposts AS frp " +
					"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
					"ON frp.postid = fpi.postid AND (fpi.property = '" + UtilReports.POST_IMPRESSIONS_ORGANIC_UNIQUE + "' OR fpi.property = '" + UtilReports.POST_IMPRESSIONS_VIRAL_UNIQUE + "' OR " +
							"fpi.property = '" + UtilReports.POST_IMPRESSIONS_PAID_UNIQUE + "') " +
					"WHERE frp.pageId = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime < " + dateUntil + ") " +
					"GROUP BY frp.postid) frpimpressions " +
				"ON " +
					"frpstories.postid = frpimpressions.postid " +
					"WHERE ROUND(100 * (frpstories.storiesconsumptions / frpimpressions.impressions), 2) IS NOT NULL " +
				"ORDER BY engagement DESC " +
				"OFFSET 0 LIMIT " + limit;
		
		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		List<FaceReportPost> reportPosts = new ArrayList<FaceReportPost>();
		
		for(Object[] object : result) {
			
			FaceReportPost reportPost = new FaceReportPost();
			reportPost.setPostId((String) object[0]);
			reportPost.setPageId((String) object[1]);
			reportPost.setMessage((String) object[2]);
			reportPost.setType((String) object[3]);
			reportPost.setPhotoSrc((String) object[4]);
			reportPost.setPhotoWidth((Integer) object[5]);
			reportPost.setPhotoHeight((Integer) object[6]);
			reportPost.setPermalink((String) object[7]);
			reportPost.setCreatedTime((Long) object[8]);
			reportPost.setLikes((Integer) object[9]);
			reportPost.setComments((Integer) object[10]);
			reportPost.setShares((Integer) object[11]);
			reportPost.setVersion((Long) object[12]);
			reportPost.setEngagement(object[13] != null ? ((BigDecimal) object[13]).doubleValue() : 0);
			
			reportPosts.add(reportPost);
		}
		
		return reportPosts;
	}

	@Override
	public List<FaceReportPost> getPostsMostEngaged(String pageId, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		
		sql = "SELECT frpimpressions.postid, frpimpressions.pageid, frpimpressions.message, frpimpressions.type, " +
				"frpimpressions.permalink, frpimpressions.createdtime, frpimpressions.likes, frpimpressions.comments, " + 
				"frpimpressions.shares, frpimpressions.version, ROUND(100 * (frpstories.storiesconsumptions / frpimpressions.impressions), 2) AS engagement " +
				"FROM " +
					"(SELECT frp.postid, NULLIF(SUM(fpi.value), 0) AS storiesconsumptions FROM opsocial.facereportsposts AS frp " +
					"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
					"ON frp.postid = fpi.postid AND (fpi.property = '" + UtilReports.POST_CONSUMPTIONS + "' OR fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_LIKE + "' OR " +
							"fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_COMMENT + "' OR fpi.property = '" + UtilReports.POST_STORIES_BY_ACTION_TYPE_SHARE + "') " +
					"WHERE frp.pageId = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime < " + dateUntil + ") " +
					"GROUP BY frp.postid) frpstories " +
				"INNER JOIN " +
					"(SELECT frp.postid, frp.pageid, frp.message, frp.type, frp.permalink, frp.createdtime, frp.likes, frp.comments, frp.shares, frp.version, NULLIF(SUM(fpi.value), 0) AS impressions FROM opsocial.facereportsposts AS frp " +
					"INNER JOIN opsocial.facebookpostsinsights AS fpi " +
					"ON frp.postid = fpi.postid AND (fpi.property = '" + UtilReports.POST_IMPRESSIONS_ORGANIC_UNIQUE + "' OR fpi.property = '" + UtilReports.POST_IMPRESSIONS_VIRAL_UNIQUE + "' OR " +
							"fpi.property = '" + UtilReports.POST_IMPRESSIONS_PAID_UNIQUE + "') " +
					"WHERE frp.pageId = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime < " + dateUntil + ") " +
					"GROUP BY frp.postid) frpimpressions " +
				"ON " +
					"frpstories.postid = frpimpressions.postid " +
				"ORDER BY frpimpressions.createdtime DESC " +
				"OFFSET " + offset;

		if(limit != null) {
			sql += " LIMIT " + limit;
		}
		
		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		List<FaceReportPost> reportPosts = new ArrayList<FaceReportPost>();
		
		for(Object[] object : result) {
			
			FaceReportPost reportPost = new FaceReportPost();
			reportPost.setPostId((String) object[0]);
			reportPost.setPageId((String) object[1]);
			reportPost.setMessage((String) object[2]);
			reportPost.setType((String) object[3]);
			reportPost.setPermalink((String) object[4]);
			reportPost.setCreatedTime((Long) object[5]);
			reportPost.setLikes((Integer) object[6]);
			reportPost.setComments((Integer) object[7]);
			reportPost.setShares((Integer) object[8]);
			reportPost.setVersion((Long) object[9]);
			reportPost.setEngagement(object[10] != null ? ((BigDecimal) object[10]).doubleValue() : 0);
			
			reportPosts.add(reportPost);
		}
		
		return reportPosts;
	}

	@Override
	public List<SumByDate> getNumberOfPosts(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(createdtime)::timestamp with time zone) as integer) AS year, " + 
				"CAST(date_part('month', TO_TIMESTAMP(createdtime)::timestamp with time zone) as integer) AS month, " +
				"CAST(date_part('day', TO_TIMESTAMP(createdtime)::timestamp with time zone) as integer) AS day, " +
				"COUNT(frp) AS countposts FROM opsocial.facereportsposts AS frp " + 
				"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime < " + dateUntil + ") " +
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
	public Long getAveragePostsPerDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(COALESCE(ROUND(AVG(countposts), 0), 0) AS bigint) FROM (" +
				"SELECT CAST(date_part('year', TO_TIMESTAMP(createdtime)::timestamp with time zone) as integer) AS year, " + 
					"CAST(date_part('month', TO_TIMESTAMP(createdtime)::timestamp with time zone) as integer) AS month, " +
					"CAST(date_part('day', TO_TIMESTAMP(createdtime)::timestamp with time zone) as integer) AS day, " +
					"COUNT(frp) AS countposts FROM opsocial.facereportsposts AS frp " + 
					"WHERE pageid = '" + pageId + "' AND (createdtime >= " + dateFrom + " AND createdtime < " + dateUntil + ") " +
					"GROUP BY year, month, day " +
					"ORDER BY year, month, day) " +
				"AS avgcountposts";

		query = em.createNativeQuery(sql);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Map<String, Long> getNumberOfPostsPerType(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT frp.type, COUNT(frp) FROM FaceReportPost frp " +
				"WHERE frp.pageId = :pageId AND (frp.createdTime >= :dateFrom AND frp.createdTime < :dateUntil) " +
				"GROUP BY frp.type";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);

		List<Object[]> result = query.getResultList();
		
		Map<String, Long> countOfPosts = new HashMap<String, Long>();
		for(Object[] object : result) {
			countOfPosts.put((String) object[0], (Long) object[1]);	
		}

		return countOfPosts;
	}

	@Override
	public List<FaceReportPost> getPostsByDate(String pageId, Long dateFrom, Long dateUntil, String order, Integer offset, Integer limit) {
		
		sql = "SELECT frp FROM FaceReportPost frp " +
				"WHERE frp.pageId = :pageId AND (frp.createdTime >= :dateFrom AND frp.createdTime < :dateUntil) " +
				"ORDER BY frp.createdTime " + order;

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setFirstResult(offset);
		query.setMaxResults(limit);

		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<FaceReportPost>();
		}
	}

}
