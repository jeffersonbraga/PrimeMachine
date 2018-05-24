package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.report.AgeGroup;
import br.com.opsocial.ejb.entity.report.FanPageGrowth;
import br.com.opsocial.ejb.entity.report.PropertyValue;
import br.com.opsocial.ejb.entity.report.ReportDetailed;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;
import br.com.opsocial.ejb.entity.report.SumByDate;
import br.com.opsocial.ejb.utils.reports.UtilReports;

@Stateless  
public class ReportDetailedDAOImpl extends AbstractDAOImpl<ReportDetailed> implements ReportDetailedDAO {

	@Override
	public Long getValueByProperty(String where) {
		
		sql = "SELECT rp.value FROM ReportDetailed rp WHERE " + where.trim();

		List<Long> result = em.createQuery(sql).getResultList(); 
		if(result.isEmpty()) {
			return 0L;
		} else {
			if(result.get(0) != null) {
				return result.get(0);
			} else {
				return 0L;
			}
		} 
	}

	public Integer[] getValuesByProperty(String where, String order) {

		Integer[] valuesForProperty;

		sql = "SELECT rp.value FROM ReportDetailed rp WHERE "+ where.trim() + " ORDER BY " + order.trim();
		query = em.createQuery(sql);
		List<Long> result = query.getResultList();
		valuesForProperty = new Integer[result.size()];
		for(int i = 0; i < result.size(); i++) {
			valuesForProperty[i] = result.get(i).intValue();
		}

		return valuesForProperty;
	}

	@Override
	public Long getDate(String where) {
		sql = "SELECT max(rp.date) FROM ReportDetailed rp WHERE " + where.trim();

		return (Long) em.createQuery(sql).getSingleResult();
	}

	@Override
	public ReportDetailed getEntityByPageProperty(String where) {
		sql = "SELECT rp FROM ReportDetailed rp WHERE " + where.trim();

		return (ReportDetailed) em.createQuery(sql).getSingleResult();
	}

	@Override
	public List<ReportDetailed> getEntitiesByPage(String pageId,
			String property, Long dateFrom, Long dateUntil) {

		sql = "SELECT rp FROM ReportDetailed rp WHERE rp.pageId = :pageId AND rp.property = :property " +
				"AND (rp.date >= :dateFrom AND rp.date <= :dateUntil) ORDER BY rp.date";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("property", property);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);

		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<ReportDetailed>();
		}
	}

	@Override
	public ReportDetailed getEntity(String pageId, String property, Long date) {

		sql = "SELECT rp FROM ReportDetailed rp WHERE rp.pageId = :pageId " +
				"AND rp.property = :property " +
				"AND rp.date = :date";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("property", property);
		query.setParameter("date", date);

		try {
			return (ReportDetailed) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Map<String, List<ReportDetailed>> getEntitiesWithoutValue(String pageId, Long date) {

		Map<String, List<ReportDetailed>> entitiesWithouValue = new HashMap<String, List<ReportDetailed>>();

		// Agrupa apenas os insights recuperados do facebook pertencentes ao grupo GROUP_UNIQUE.
		sql = "SELECT rp FROM ReportDetailed rp WHERE rp.pageId = :pageId AND rp.date = :date AND rp.value IS NULL AND rp.fromGroup = :fromGroup";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("date", date);
		query.setParameter("fromGroup", UtilReports.GROUP_UNIQUE);

		List<ReportDetailed> reportDetailedsUnique = query.getResultList();
		if(!reportDetailedsUnique.isEmpty()) {
			entitiesWithouValue.put(UtilReports.GROUP_UNIQUE, reportDetailedsUnique);
		}

		// Agrupar apenas os insights recuperados do facebook pertencentes ao grupo GROUP_IMP_GENDER_AGE.
		sql = "SELECT rp FROM ReportDetailed rp WHERE rp.pageId = :pageId AND rp.date = :date AND rp.value IS NULL AND rp.fromGroup = :fromGroup";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("date", date);
		query.setParameter("fromGroup", UtilReports.GROUP_IMP_GENDER_AGE);

		List<ReportDetailed> reportDetailedsImpGenderAge = query.getResultList();
		if(!reportDetailedsImpGenderAge.isEmpty()) {
			entitiesWithouValue.put(UtilReports.GROUP_IMP_GENDER_AGE, reportDetailedsImpGenderAge);
		}

		// Agrupar apenas os insights recuperados do facebook pertencentes ao grupo GROUP_IMP_STORY_TYPE.
		sql = "SELECT rp FROM ReportDetailed rp WHERE rp.pageId = :pageId AND rp.date = :date AND rp.value IS NULL AND rp.fromGroup = :fromGroup";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("date", date);
		query.setParameter("fromGroup", UtilReports.GROUP_IMP_STORY_TYPE);

		List<ReportDetailed> reportDetailedsImpStoryType = query.getResultList();
		if(!reportDetailedsImpStoryType.isEmpty()) {
			entitiesWithouValue.put(UtilReports.GROUP_IMP_STORY_TYPE, reportDetailedsImpStoryType);
		}

		// Agrupar apenas os insights recuperados do facebook pertencentes ao grupo GROUP_CON_TYPE.
		sql = "SELECT rp FROM ReportDetailed rp WHERE rp.pageId = :pageId AND rp.date = :date AND rp.value IS NULL AND rp.fromGroup = :fromGroup";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("date", date);
		query.setParameter("fromGroup", UtilReports.GROUP_CON_TYPE);

		List<ReportDetailed> reportDetailedsConType = query.getResultList();
		if(!reportDetailedsConType.isEmpty()) {
			entitiesWithouValue.put(UtilReports.GROUP_CON_TYPE, reportDetailedsConType);
		}

		// Agrupar apenas os insights recuperados do facebook pertencentes ao grupo GROUP_STORIES_TYPE.
		sql = "SELECT rp FROM ReportDetailed rp WHERE rp.pageId = :pageId AND rp.date = :date AND rp.value IS NULL AND rp.fromGroup = :fromGroup";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("date", date);
		query.setParameter("fromGroup", UtilReports.GROUP_STORIES_TYPE);

		List<ReportDetailed> reportDetailedsStoriesType = query.getResultList();
		if(!reportDetailedsStoriesType.isEmpty()) {
			entitiesWithouValue.put(UtilReports.GROUP_STORIES_TYPE, reportDetailedsStoriesType);
		}
		
		// Agrupa apenas os insights recuperados do facebook pertencentes ao grupo GROUP_FANS_LIKE_SOURCE.
		sql = "SELECT rp FROM ReportDetailed rp WHERE rp.pageId = :pageId AND rp.date = :date AND rp.value IS NULL AND rp.fromGroup = :fromGroup";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("date", date);
		query.setParameter("fromGroup", UtilReports.GROUP_FANS_LIKE_SOURCE);

		List<ReportDetailed> reportDetailedsFansLikeSource = query.getResultList();
		if(!reportDetailedsFansLikeSource.isEmpty()) {
			entitiesWithouValue.put(UtilReports.GROUP_FANS_LIKE_SOURCE, reportDetailedsFansLikeSource);
		}
		
		// Agrupa apenas os insights recuperados do facebook pertencentes ao grupo GROUP_FANS_ONLINE.
		sql = "SELECT rp FROM ReportDetailed rp WHERE rp.pageId = :pageId AND rp.date = :date AND rp.value IS NULL AND rp.fromGroup = :fromGroup";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("date", date);
		query.setParameter("fromGroup", UtilReports.GROUP_FANS_ONLINE);

		List<ReportDetailed> reportDetailedsFansOnline = query.getResultList();
		if(!reportDetailedsFansOnline.isEmpty()) {
			entitiesWithouValue.put(UtilReports.GROUP_FANS_ONLINE, reportDetailedsFansOnline);
		}

		return entitiesWithouValue;
	}

	@Override
	public List<Long> getDatesWithoutValue(String pageId) {

		sql = "SELECT rp.date FROM ReportDetailed rp WHERE rp.pageId = :pageId AND rp.value IS NULL GROUP BY rp.date";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);

		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Long>();
		}
	}

	@Override
	public Map<String, Long> getSumsForProperties(String pageId, List<String> properties, Long dateFrom, Long dateUntil) {
		
		String propertiesIn = "(";
		
		if(!properties.isEmpty()) {
			propertiesIn = propertiesIn.concat("'"+properties.get(0)+"'");
			for(int i = 1; i < properties.size(); i++) {
				propertiesIn = propertiesIn.concat(",'"+properties.get(i)+"'");
			}
		}
		
		propertiesIn = propertiesIn.concat(")");
		
		sql = "SELECT SUM(rd.value), rd.property FROM ReportDetailed rd " +
				"WHERE rd.pageId = :pageId AND rd.property IN " + propertiesIn + " " +
				"AND (rd.date >= :dateFrom AND rd.date <= :dateUntil) " +
				"GROUP BY rd.property";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String)object[1], (Long)object[0]);
		}
		
		Map<String, Long> sumsByPropertyAux = new HashMap<String, Long>();
		for(String property : properties) {
			if(!sumsByProperty.containsKey((String) property)) {
				sumsByPropertyAux.put(property, 0L);
			}
		}
		
		sumsByProperty.putAll(sumsByPropertyAux);

		return sumsByProperty;
	}

	@Override
	public Map<String, Long> getValuesForProperties(String pageId, List<String> properties) {
		
		String propertiesIn = "(";
		
		if(!properties.isEmpty()) {
			propertiesIn = propertiesIn.concat("'"+properties.get(0)+"'");
			for(int i = 1; i < properties.size(); i++) {
				propertiesIn = propertiesIn.concat(",'"+properties.get(i)+"'");
			}
		}
		
		propertiesIn = propertiesIn.concat(")");
		
		sql = "SELECT rd.value, rd.property FROM ReportDetailed rd " +
				"WHERE rd.pageId = :pageId AND rd.property IN " + propertiesIn;

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> valuesByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			
			valuesByProperty.put((String)object[1], (Long)object[0]);
		}
		
		Map<String, Long> valuesByPropertyAux = new HashMap<String, Long>();
		for(String property : properties) {
			if(!valuesByProperty.containsKey((String) property)) {
				valuesByPropertyAux.put(property, 0L);
			}
		}
		
		valuesByProperty.putAll(valuesByPropertyAux);

		return valuesByProperty;
	}

	@Override
	public Map<String, Long> getSumsForPropertiesPages(List<String> pageIds, List<String> properties, Long dateFrom, Long dateUntil) {
		
		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		
		if(!pageIds.isEmpty()) {
			
			String propertiesIn = "(";
			
			if(!properties.isEmpty()) {
				propertiesIn = propertiesIn.concat("'"+properties.get(0)+"'");
				for(int i = 1; i < properties.size(); i++) {
					propertiesIn = propertiesIn.concat(",'"+properties.get(i)+"'");
				}
			}
			
			propertiesIn = propertiesIn.concat(")");
			
			String pageIdsIn = "(";
			
			if(!pageIds.isEmpty()) {
				pageIdsIn = pageIdsIn.concat("'"+pageIds.get(0)+"'");
				pageIds.remove(0);
				for(String pageId : pageIds) {
					pageIdsIn = pageIdsIn.concat(",'"+pageId+"'");
				}
			}
			
			pageIdsIn = pageIdsIn.concat(")");
			
			sql = "SELECT SUM(rd.value), rd.property FROM ReportDetailed rd " +
					"WHERE rd.pageId IN " + pageIdsIn + " AND rd.property IN " + propertiesIn + " " +
					"AND (rd.date >= :dateFrom AND rd.date <= :dateUntil) " +
					"GROUP BY rd.property";

			query = em.createQuery(sql);
			query.setParameter("dateFrom", dateFrom);
			query.setParameter("dateUntil", dateUntil);
			
			List<Object[]> result = query.getResultList();
			
			for(Object[] object : result) {
				sumsByProperty.put((String)object[1], (Long)object[0]);
			}
			
			Map<String, Long> sumsByPropertyAux = new HashMap<String, Long>();
			for(String property : properties) {
				if(!sumsByProperty.containsKey((String) property)) {
					sumsByPropertyAux.put(property, 0L);
				}
			}
			
			sumsByProperty.putAll(sumsByPropertyAux);
		} else {
			
			for(String property : properties) {
				sumsByProperty.put(property, 0L);
			}
		}

		return sumsByProperty;
	}

	@Override
	public Map<String, Long> getSumsForPropertiesPages(List<String> pageIds, List<String> properties) {
		
		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		
		if(!pageIds.isEmpty()) {
			
			String propertiesIn = "(";
			
			if(!properties.isEmpty()) {
				propertiesIn = propertiesIn.concat("'"+properties.get(0)+"'");
				for(int i = 1; i < properties.size(); i++) {
					propertiesIn = propertiesIn.concat(",'"+properties.get(i)+"'");
				}
			}
			
			propertiesIn = propertiesIn.concat(")");
			
			String pageIdsIn = "(";
			
			if(!pageIds.isEmpty()) {
				pageIdsIn = pageIdsIn.concat("'"+pageIds.get(0)+"'");
				pageIds.remove(0);
				for(String pageId : pageIds) {
					pageIdsIn = pageIdsIn.concat(",'"+pageId+"'");
				}
			}
			
			pageIdsIn = pageIdsIn.concat(")");
			
			sql = "SELECT SUM(rd.value), rd.property FROM ReportDetailed rd " +
					"WHERE rd.pageId IN " + pageIdsIn + " AND rd.property IN " + propertiesIn + " " +
					"GROUP BY rd.property";

			query = em.createQuery(sql);
			
			List<Object[]> result = query.getResultList();
			
			
			for(Object[] object : result) {
				sumsByProperty.put((String)object[1], (Long)object[0]);
			}
			
			Map<String, Long> sumsByPropertyAux = new HashMap<String, Long>();
			for(String property : properties) {
				if(!sumsByProperty.containsKey((String) property)) {
					sumsByPropertyAux.put(property, 0L);
				}
			}
			
			sumsByProperty.putAll(sumsByPropertyAux);
		} else {
			
			for(String property : properties) {
				sumsByProperty.put(property, 0L);
			}
		}

		return sumsByProperty;
	}
	
	@Override
	public ReportDetailed saveByDay(ReportDetailed object) throws Exception {
		
		if(this.getEntity(object.getPageId(), object.getProperty(), object.getDate()) == null) {
			return super.save(object);
		} else {
			return null;
		}
	}
	
	@Override
	public Long getCountDateByPageId(String pageId, Long date, String property)
			throws IllegalArgumentException {
		String where = "pageId = '"+pageId+"' AND x.date = "+date+" AND x.property = '"+property+"'";
		return getCount(where);
	}

	@Override
	public Long getSumForProperty(String pageId, String property, Long dateFrom, Long dateUntil) throws IllegalArgumentException {
		String where = "x.pageId = '"+pageId+"' AND x.property = '"+property+"' AND (x.date >= "+dateFrom+" AND x.date <= "+dateUntil+")";
		return getSum("value", where);
	}
	
	@Override
	public Integer[] getValuesForProperty(String pageId, String property, Long dateFrom, Long dateUntil) throws IllegalArgumentException {
		String where = "rp.pageId = '"+pageId+"' AND rp.property = '"+property+"' " +
				"AND (rp.date >= "+dateFrom+" AND rp.date <= "+dateUntil+")";
		String order = "rp.date";
		
		return getValuesByProperty(where,order);
	}
	
	@Override
	public Long getValueForProperty(String pageId, String property) throws IllegalArgumentException {
		String where = "rp.pageId = '"+pageId+"' AND rp.property = '"+property+"'";
		return getValueByProperty(where);
	}

	@Override
	public Long getDateForProperty(String pageId, String property) throws IllegalArgumentException {
		String where = "rp.pageId = '"+pageId+"' AND rp.property = '"+property+"'";
		return getDate(where);
	}
	
	@Override
	public ReportDetailed getEntityByPageIdProperty(String pageId, String property) throws IllegalArgumentException {
		String where = "rp.pageId = '"+pageId+"' AND rp.property = '"+property+"'";
		return getEntityByPageProperty(where);
	}

	@Override
	public List<FanPageGrowth> getFanPageGrowth(String pageId) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(date)::timestamp with time zone) as integer), CAST(date_part('month', TO_TIMESTAMP(date)::timestamp with time zone) as integer), CAST(COALESCE(SUM(value), 0) AS bigint) FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_NEW_FANS + "' AND date >= " + UtilReports.getDateForFanPageGrowth() + " " +
					"GROUP BY date_part('year', TO_TIMESTAMP(date)::timestamp with time zone), date_part('month', TO_TIMESTAMP(date)::timestamp with time zone) " +
					"ORDER BY date_part('year', TO_TIMESTAMP(date)::timestamp with time zone), date_part('month', TO_TIMESTAMP(date)::timestamp with time zone)";

		query = em.createNativeQuery(sql);
		
		List<Object[]> resultNewFans = query.getResultList();
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(date)::timestamp with time zone) as integer), CAST(date_part('month', TO_TIMESTAMP(date)::timestamp with time zone) as integer), CAST(COALESCE(SUM(value), 0) AS bigint) FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_FAN_REMOVES + "' AND date >= " + UtilReports.getDateForFanPageGrowth() + " " +
					"GROUP BY date_part('year', TO_TIMESTAMP(date)::timestamp with time zone), date_part('month', TO_TIMESTAMP(date)::timestamp with time zone) " +
					"ORDER BY date_part('year', TO_TIMESTAMP(date)::timestamp with time zone), date_part('month', TO_TIMESTAMP(date)::timestamp with time zone)";

		query = em.createNativeQuery(sql);
		
		List<Object[]> resultFanRemoves = query.getResultList();
		
		List<FanPageGrowth> fansPageGrowth = new ArrayList<FanPageGrowth>();
		
		int x = 0;
		for (Object[] object : resultNewFans) {
			
			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setYear((Integer)object[0]);
			fanPageGrowth.setMonth((Integer)object[1]);
			fanPageGrowth.setSum((Long)object[2] - (Long)resultFanRemoves.get(x)[2]);
			
			fansPageGrowth.add(fanPageGrowth);
			
			x++;
		}
		
		// Completes the list of twelve months required by the graphic, if there is no twelve months with data in database.
		if(fansPageGrowth.size() < 12) {
			
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
				for (int j = 0; j < fansPageGrowth.size(); j++) {
					if(monthSequence.get(i).equals(fansPageGrowth.get(j).getMonth())) {
						noInclude = true;
						break;
					}
				}
				
				if(!noInclude) {
					
					FanPageGrowth fanPageGrowth = new FanPageGrowth();
					fanPageGrowth.setMonth(monthSequence.get(i));
					fanPageGrowth.setYear(yearSequence.get(i));
					fanPageGrowth.setSum(0L); 
					
					fansPageGrowth.add(i, fanPageGrowth);
					
					if(fansPageGrowth.size() == 12) {
						break;
					}
				}
			}
		}
		
		return fansPageGrowth;
	}

	@Override
	public Long getCountFansBeforeDate(String pageId, Long date) {
		
		Long countFans = 0L;
		
		sql = "SELECT CAST(value - (SELECT COALESCE(SUM(value), 0) - (SELECT COALESCE(SUM(value), 0) FROM opsocial.reportsdetailed " +
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_FAN_REMOVES + "' AND date >= " + date + ") AS sumvalue " +
				"FROM opsocial.reportsdetailed " +
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_NEW_FANS + "' AND date >= " + date + ") AS bigint) AS fanscount FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_FANS + "'";

		query = em.createNativeQuery(sql);
		
		try {
			
			countFans = (Long) query.getSingleResult();
			
			if(countFans < 0L) {
				countFans = 0L;
			}
			
			return countFans;
		} catch (NoResultException e) {
			return countFans;
		}
	}

	@Override
	public Map<String, Long> getAboutYourFansGender(String pageId) {
		
		sql = "SELECT rd.value, rd.property FROM ReportDetailed rd " +
				"WHERE rd.pageId = :pageId AND rd.fromGroup = :fromGroup";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_FANS_GENDER_AGE);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> valuesByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			valuesByProperty.put((String)object[1], (Long)object[0] == null ? 0L : (Long)object[0]);
		}

		return valuesByProperty;
	}

	@Override
	public AgeGroup getMajorAgeGroupFans(String pageId) {
		
		sql = "SELECT initialAgeGroup, endAgeGroup, CAST(perc AS int) FROM " + 
				"(SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT SUM(rd.value) FROM opsocial.reportsdetailed AS rd " + 
				 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
				"(SELECT CAST('13' AS varchar) AS initialAgeGroup, CAST('17' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
				 "WHERE (rd.property = '" + UtilReports.PAGE_FANS_MALE_13_AMONG_17 + "' OR rd.property = '" + UtilReports.PAGE_FANS_FEMALE_13_AMONG_17 + "') AND rd.pageId = '" + pageId + "' " +
				") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		       "SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT SUM(rd.value) FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " + 
		       "(SELECT CAST('18' AS varchar) AS initialAgeGroup, CAST('24' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE (rd.property = '" + UtilReports.PAGE_FANS_MALE_18_AMONG_24 + "' OR rd.property = '" + UtilReports.PAGE_FANS_FEMALE_18_AMONG_24 + "') AND rd.pageId = '" + pageId + "' " +
		        ") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		       "SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT SUM(rd.value) FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
		       "(SELECT CAST('25' AS varchar) AS initialAgeGroup, CAST('34' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE (rd.property = '" + UtilReports.PAGE_FANS_MALE_25_AMONG_34 + "' OR rd.property = '" + UtilReports.PAGE_FANS_FEMALE_25_AMONG_34 + "') AND rd.pageId = '" + pageId + "' " +
		        ") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		      	"SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT SUM(rd.value) FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
		      	"(SELECT CAST('35' AS varchar) AS initialAgeGroup, CAST('44' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE (rd.property = '" + UtilReports.PAGE_FANS_MALE_35_AMONG_44 + "' OR rd.property = '" + UtilReports.PAGE_FANS_FEMALE_35_AMONG_44 + "') AND rd.pageId = '" + pageId + "' " +
		      	") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		      	"SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT SUM(rd.value) FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
		      	"(SELECT CAST('45' AS varchar) AS initialAgeGroup, CAST('54' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE (rd.property = '" + UtilReports.PAGE_FANS_MALE_45_AMONG_54 + "' OR rd.property = '" + UtilReports.PAGE_FANS_FEMALE_45_AMONG_54 + "') AND rd.pageId = '" + pageId + "' " +
		      	") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		      	"SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT SUM(rd.value) FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
		      	"(SELECT CAST('55' AS varchar) AS initialAgeGroup, CAST('64' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE (rd.property = '" + UtilReports.PAGE_FANS_MALE_55_AMONG_64 + "' OR rd.property = '" + UtilReports.PAGE_FANS_FEMALE_55_AMONG_64 + "') AND rd.pageId = '" + pageId + "' " +
		      	") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		       "SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT SUM(rd.value) FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
		       "(SELECT CAST('65' AS varchar) AS initialAgeGroup, CAST('+' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE (rd.property = '" + UtilReports.PAGE_FANS_MALE_65_PLUS + "' OR rd.property = '" + UtilReports.PAGE_FANS_FEMALE_65_PLUS + "') AND rd.pageId = '" + pageId + "' " +
		       ") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		       ") AS sums " +
		       "ORDER BY perc DESC " +
		       "OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);
		
		Object[] result = (Object[]) query.getSingleResult();
		
		AgeGroup ageGroupFan = new AgeGroup();
		ageGroupFan.setInitialAgeGroup((String) result[0]);
		ageGroupFan.setEndAgeGroup((String) result[1]);
		ageGroupFan.setPercentage((Integer) result[2]);
		
		return ageGroupFan;
	}

	@Override
	public Integer[] getWomenMenFansPerc(String pageId) {
		
		sql = "SELECT CAST(perc AS int) FROM " +
				"(SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT SUM(rd.value) FROM opsocial.reportsdetailed AS rd " + 
					"WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
				"(SELECT SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " +
					"WHERE rd.property IN ('" + UtilReports.PAGE_FANS_MALE_13_AMONG_17 + "','" + UtilReports.PAGE_FANS_MALE_18_AMONG_24 + "'," +
										  "'" + UtilReports.PAGE_FANS_MALE_25_AMONG_34 + "','" + UtilReports.PAGE_FANS_MALE_35_AMONG_44 + "', " +
									      "'" + UtilReports.PAGE_FANS_MALE_45_AMONG_54 + "','" + UtilReports.PAGE_FANS_MALE_55_AMONG_64 + "'," + 
										  "'" + UtilReports.PAGE_FANS_MALE_65_PLUS + "') AND rd.pageId = '" + pageId + "') AS r " +
			    "UNION ALL " +
			    "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT SUM(rd.value) FROM opsocial.reportsdetailed AS rd " + 
			    	"WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
			   "(SELECT SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " +
			   		"WHERE rd.property IN ('" + UtilReports.PAGE_FANS_FEMALE_13_AMONG_17 + "','" + UtilReports.PAGE_FANS_FEMALE_18_AMONG_24 + "'," +
										  "'" + UtilReports.PAGE_FANS_FEMALE_25_AMONG_34 + "','" + UtilReports.PAGE_FANS_FEMALE_35_AMONG_44 + "', " +
									      "'" + UtilReports.PAGE_FANS_FEMALE_45_AMONG_54 + "','" + UtilReports.PAGE_FANS_FEMALE_55_AMONG_64 + "'," + 
										  "'" + UtilReports.PAGE_FANS_FEMALE_65_PLUS + "') AND rd.pageId = '" + pageId + "') AS r " +
			   ") AS genderPerc";

		query = em.createNativeQuery(sql);
		
		List<Integer> results = (List<Integer>) query.getResultList();
		
		Integer[] result = new Integer[2];
		
		result[0] = results.get(0);
		result[1] = results.get(1);
		
		return result;
	}

	@Override
	public Map<String, Long> getAboutReachedUsers(String pageId) {
		
		sql = "SELECT rd.property, SUM(rd.value) FROM ReportDetailed rd " + 
				"WHERE rd.pageId = :pageId AND rd.fromGroup = :fromGroup " +
				"GROUP BY rd.property";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_IMP_GENDER_AGE);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String)object[0], (Long)object[1] == null ? 0L : (Long)object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public AgeGroup getMajorAgeGroupReachedUsers(String pageId) {
		
		sql = "SELECT initialAgeGroup, endAgeGroup, CAST(perc AS int) FROM " + 
				"(SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
				 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
				"(SELECT CAST('13' AS varchar) AS initialAgeGroup, CAST('17' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
				 "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_13_AMONG_17 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_13_AMONG_17 + "') AND rd.pageId = '" + pageId + "' " +
				") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		       "SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " + 
		       "(SELECT CAST('18' AS varchar) AS initialAgeGroup, CAST('24' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_18_AMONG_24 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_18_AMONG_24 + "') AND rd.pageId = '" + pageId + "' " +
		        ") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		       "SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
		       "(SELECT CAST('25' AS varchar) AS initialAgeGroup, CAST('34' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_25_AMONG_34 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_25_AMONG_34 + "') AND rd.pageId = '" + pageId + "' " +
		        ") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		      	"SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
		      	"(SELECT CAST('35' AS varchar) AS initialAgeGroup, CAST('44' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_35_AMONG_44 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_35_AMONG_44 + "') AND rd.pageId = '" + pageId + "' " +
		      	") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		      	"SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
		      	"(SELECT CAST('45' AS varchar) AS initialAgeGroup, CAST('54' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_45_AMONG_54 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_45_AMONG_54 + "') AND rd.pageId = '" + pageId + "' " +
		      	") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		      	"SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
		      	"(SELECT CAST('55' AS varchar) AS initialAgeGroup, CAST('64' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_55_AMONG_64 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_55_AMONG_64 + "') AND rd.pageId = '" + pageId + "' " +
		      	") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		       "SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
		       "(SELECT CAST('65' AS varchar) AS initialAgeGroup, CAST('+' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_65_PLUS + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_65_PLUS + "') AND rd.pageId = '" + pageId + "' " +
		       ") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		       ") AS sums " +
		       "ORDER BY perc DESC " +
		       "OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);
		
		AgeGroup ageGroupFan;
		
		try {
			
			Object[] result = (Object[]) query.getSingleResult();
			
			ageGroupFan = new AgeGroup();
			ageGroupFan.setInitialAgeGroup((String) result[0]);
			ageGroupFan.setEndAgeGroup((String) result[1]);
			ageGroupFan.setPercentage((Integer) result[2]);
			
		} catch (Exception e) {
			
			//----- System.out.println("Bug getMajorAgeGroupReachedUsers");
			//----- System.out.println("Profile Networkid ..:" + pageId);
			
			ageGroupFan = new AgeGroup();
			ageGroupFan.setInitialAgeGroup("");
			ageGroupFan.setEndAgeGroup("");
			ageGroupFan.setPercentage(0);
		}
		
		return ageGroupFan;
	}

	@Override
	public Integer[] getWomenMenReachedUsersPerc(String pageId) {
		
		sql = "SELECT CAST(perc AS int) FROM " +
				"(SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
					"WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
				"(SELECT SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " +
					"WHERE rd.property IN ('" + UtilReports.PAGE_IMPRESSIONS_MALE_13_AMONG_17 + "','" + UtilReports.PAGE_IMPRESSIONS_MALE_18_AMONG_24 + "'," +
										  "'" + UtilReports.PAGE_IMPRESSIONS_MALE_25_AMONG_34 + "','" + UtilReports.PAGE_IMPRESSIONS_MALE_35_AMONG_44 + "', " +
									      "'" + UtilReports.PAGE_IMPRESSIONS_MALE_45_AMONG_54 + "','" + UtilReports.PAGE_IMPRESSIONS_MALE_55_AMONG_64 + "'," + 
										  "'" + UtilReports.PAGE_IMPRESSIONS_MALE_65_PLUS + "') AND rd.pageId = '" + pageId + "') AS r " +
			    "UNION ALL " +
			    "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
			    	"WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "')), 0), 0) AS integer) AS perc FROM " +
			   "(SELECT SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " +
			   		"WHERE rd.property IN ('" + UtilReports.PAGE_IMPRESSIONS_FEMALE_13_AMONG_17 + "','" + UtilReports.PAGE_IMPRESSIONS_FEMALE_18_AMONG_24 + "'," +
										  "'" + UtilReports.PAGE_IMPRESSIONS_FEMALE_25_AMONG_34 + "','" + UtilReports.PAGE_IMPRESSIONS_FEMALE_35_AMONG_44 + "', " +
									      "'" + UtilReports.PAGE_IMPRESSIONS_FEMALE_45_AMONG_54 + "','" + UtilReports.PAGE_IMPRESSIONS_FEMALE_55_AMONG_64 + "'," + 
										  "'" + UtilReports.PAGE_IMPRESSIONS_FEMALE_65_PLUS + "') AND rd.pageId = '" + pageId + "') AS r " +
			   ") AS genderPerc";

		query = em.createNativeQuery(sql);
		
		List<Integer> results = (List<Integer>) query.getResultList();
		
		Integer[] result = new Integer[2];
		
		result[0] = results.get(0);
		result[1] = results.get(1);
		
		return result;
	}

	@Override
	public List<SumByDate> getSingleReach(String pageId) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(date)::timestamp with time zone) as integer), CAST(date_part('month', TO_TIMESTAMP(date)::timestamp with time zone) as integer), COALESCE(SUM(value), 0) FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_POSTS_IMPRESSIONS_UNIQUE + "' AND date >= " + UtilReports.getDateForFanPageGrowth() + " " +
					"GROUP BY date_part('year', TO_TIMESTAMP(date)::timestamp with time zone), date_part('month', TO_TIMESTAMP(date)::timestamp with time zone) " +
					"ORDER BY date_part('year', TO_TIMESTAMP(date)::timestamp with time zone), date_part('month', TO_TIMESTAMP(date)::timestamp with time zone)";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		List<SumByDate> sumsMonthYear = new ArrayList<SumByDate>();
		
		for (Object[] object : result) {
			
			SumByDate sumMonthYear = new SumByDate();
			sumMonthYear.setYear((Integer)object[0]);
			sumMonthYear.setMonth((Integer)object[1]);
			sumMonthYear.setSum((Long)object[2]);
			
			sumsMonthYear.add(sumMonthYear);
		}
		
		// Completes the list of twelve months required by the graphic, if there is no twelve months with data in database.
		if(sumsMonthYear.size() < 12) {
			
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
				for (int j = 0; j < sumsMonthYear.size(); j++) {
					if(monthSequence.get(i).equals(sumsMonthYear.get(j).getMonth())) {
						noInclude = true;
						break;
					}
				}
				
				if(!noInclude) {
					
					SumByDate sumMonthYear = new SumByDate();
					sumMonthYear.setMonth(monthSequence.get(i));
					sumMonthYear.setYear(yearSequence.get(i));
					sumMonthYear.setSum(0L); 
					
					sumsMonthYear.add(i, sumMonthYear);
					
					if(sumsMonthYear.size() == 12) {
						break;
					}
				}
			}
		}
		
		return sumsMonthYear;
	}

	@Override
	public SumByDate getMostReachedMonth(String pageId) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(date)::timestamp with time zone) as integer), CAST(date_part('month', TO_TIMESTAMP(date)::timestamp with time zone) as integer), CAST(COALESCE(SUM(value), 0) AS bigint) as sumMonthYear FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_POSTS_IMPRESSIONS_UNIQUE + "' AND date >= " + UtilReports.getDateForFanPageGrowth() + " " +
					"GROUP BY date_part('year', TO_TIMESTAMP(date)::timestamp with time zone), date_part('month', TO_TIMESTAMP(date)::timestamp with time zone) " +
					"ORDER BY sumMonthYear DESC " + 
					"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);
		
		Object[] result;
		
		SumByDate sumMonthYear;
		try {
			
			result = (Object[]) query.getSingleResult();
			
			sumMonthYear = new SumByDate();
			sumMonthYear.setYear((Integer) result[0]);
			sumMonthYear.setMonth((Integer) result[1]);
			sumMonthYear.setSum((Long) result[2]);
			
		} catch (NoResultException e) {
			
		    Calendar date = new GregorianCalendar();

			sumMonthYear = new SumByDate();
			sumMonthYear.setYear(date.get(Calendar.YEAR));
			sumMonthYear.setMonth((date.get(Calendar.MONTH) + 1));
			sumMonthYear.setSum(0L);
		}
		
		return sumMonthYear;
	}

	@Override
	public List<FanPageGrowth> getFanPageGrowth(String pageId, Long dateFrom, Long dateUntil, Long accumulatedFanPageGrowth) {
		
		sql = "SELECT date, CAST(COALESCE(value, 0) AS BIGINT) " +
				"FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_NEW_FANS + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +  
				"ORDER BY date";

		query = em.createNativeQuery(sql);
		
		List<Object[]> resultNewFans = query.getResultList();
		
		sql = "SELECT date, CAST(COALESCE(value, 0) AS BIGINT) " +
				"FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_FAN_REMOVES + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +  
				"ORDER BY date";

		query = em.createNativeQuery(sql);
		
		List<Object[]> resultFanRemoves = query.getResultList();
		
		List<FanPageGrowth> fansPageGrowth = new ArrayList<FanPageGrowth>();
		
		int x = 0;
		for (Object[] object : resultNewFans) {
			
			accumulatedFanPageGrowth = accumulatedFanPageGrowth + ((Long) object[1] - (Long) resultFanRemoves.get(x)[1]);
			
			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setDate((Long) object[0]);
			fanPageGrowth.setSum(accumulatedFanPageGrowth);
			
			fansPageGrowth.add(fanPageGrowth);
			
			x++;
		}

		return fansPageGrowth;
	}
	
	@Override
	public List<FanPageGrowth> getImpressionsByDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT date AS day, CAST(COALESCE(value, 0) AS BIGINT) " +
				"FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_POSTS_IMPRESSIONS + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +  
				"ORDER BY day";

		query = em.createNativeQuery(sql);
		
		List<Object[]> resultNewFans = query.getResultList();
		
		List<FanPageGrowth> fansPageGrowth = new ArrayList<FanPageGrowth>();
		
		for (Object[] object : resultNewFans) {
			
			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setDate((Long) object[0]);
			fanPageGrowth.setSum((Long) object[1]);
			
			fansPageGrowth.add(fanPageGrowth);
		}

		return fansPageGrowth;
	}
	
	@Override
	public List<FanPageGrowth> getReachByDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT date AS day, CAST(COALESCE(value, 0) AS BIGINT) " +
				"FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_POSTS_IMPRESSIONS_UNIQUE + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +  
				"ORDER BY day";

		query = em.createNativeQuery(sql);
		
		List<Object[]> resultNewFans = query.getResultList();
		
		List<FanPageGrowth> fansPageGrowth = new ArrayList<FanPageGrowth>();
		
		for (Object[] object : resultNewFans) {
			
			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setDate((Long) object[0]);
			fanPageGrowth.setSum((Long) object[1]);
			
			fansPageGrowth.add(fanPageGrowth);
		}

		return fansPageGrowth;
	}
	

	@Override
	public List<FanPageGrowth> getEngagementByDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT date AS day, COALESCE(CAST(SUM(value) AS BIGINT), 0) " +
				"FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_POSITIVE_FEEDBACK + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +  
				"GROUP BY day " +
				"ORDER BY day";

		query = em.createNativeQuery(sql);
		
		List<Object[]> resultNewFans = query.getResultList();
		
		List<FanPageGrowth> fansPageGrowth = new ArrayList<FanPageGrowth>();
		
		for (Object[] object : resultNewFans) {
			
			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setDate((Long) object[0]);
			fanPageGrowth.setSum((Long) object[1]);
			
			fansPageGrowth.add(fanPageGrowth);
		}

		return fansPageGrowth;
	}

	@Override
	public List<FanPageGrowth> getLikesByDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT date AS day, CAST(COALESCE(value, 0) AS BIGINT) " +
				"FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_NEW_FANS + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +  
				"ORDER BY day";

		query = em.createNativeQuery(sql);
		
		List<Object[]> resultNewFans = query.getResultList();
		
		List<FanPageGrowth> fansPageGrowth = new ArrayList<FanPageGrowth>();
		
		for (Object[] object : resultNewFans) {
			
			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setDate((Long) object[0]);
			fanPageGrowth.setSum((Long) object[1]);
			
			fansPageGrowth.add(fanPageGrowth);
		}

		return fansPageGrowth;
	}

	@Override
	public List<FanPageGrowth> getUnlikesByDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT date AS day, CAST(COALESCE(value, 0) AS BIGINT) " +
				"FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_FAN_REMOVES + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +  
				"ORDER BY day";

		query = em.createNativeQuery(sql);
		
		List<Object[]> resultFanRemoves = query.getResultList();
		
		List<FanPageGrowth> fansPageGrowth = new ArrayList<FanPageGrowth>();
		
		for (Object[] object : resultFanRemoves) {
			
			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setDate((Long) object[0]);
			fanPageGrowth.setSum((Long) object[1]);
			
			fansPageGrowth.add(fanPageGrowth);
		}

		return fansPageGrowth;
	}

	@Override
	public ReportDetailed getTotalOfFans(String pageId) {
		
		sql = "SELECT rp FROM ReportDetailed rp WHERE rp.pageId = :pageId AND rp.property = :property";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("property", UtilReports.PAGE_FANS);

		try {
			return (ReportDetailed) query.getSingleResult();
		} catch (NoResultException e) {
			
			ReportDetailed reportDetailed = new ReportDetailed();
			reportDetailed.setValue(0L);
			
			return reportDetailed;
		}
	}

	@Override
	public Long getConqueredFans(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT COALESCE(CAST(SUM(value) AS BIGINT), 0) " +
				"FROM opsocial.reportsdetailed " +
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_NEW_FANS + "' " +
				"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}
	
	@Override
	public Long getTotalEngagement(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT COALESCE(CAST(SUM(value) AS BIGINT), 0) " +
				"FROM opsocial.reportsdetailed " +
				"WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_POSITIVE_FEEDBACK + "' " +
				"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}
	
	@Override
	public Long getTotalReach(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT COALESCE(CAST(SUM(value) AS BIGINT), 0) " +
				"FROM opsocial.reportsdetailed " +
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_POSTS_IMPRESSIONS_UNIQUE + "' " +
				"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}
	
	@Override
	public Long getTotalImpressions(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT COALESCE(CAST(SUM(value) AS BIGINT), 0) " +
				"FROM opsocial.reportsdetailed " +
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_POSTS_IMPRESSIONS + "' " +
				"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getLostFans(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT COALESCE(CAST(SUM(value) AS BIGINT), 0) " +
				"FROM opsocial.reportsdetailed " +
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_FAN_REMOVES + "' " +
				"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Map<String, Long> getFansLikeSource(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT property, CAST(COALESCE(SUM(value), 0) AS bigint) as sumvalue FROM opsocial.reportsdetailed as rd " +
				"WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_LIKE_SOURCE + "' " +
				"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +
				"GROUP BY property " +
				"ORDER BY sumvalue DESC, property ASC";
		
		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> sumsByProperty = new LinkedHashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String) object[0], (Long) object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public Map<String, Long> getAboutReachedUsers(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT rd.property, SUM(rd.value) FROM ReportDetailed rd " + 
				"WHERE rd.pageId = :pageId AND rd.fromGroup = :fromGroup " +
				"AND (rd.date >= :dateFrom AND rd.date <= :dateUntil) " +
				"GROUP BY rd.property";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_IMP_GENDER_AGE);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Long> sumsByProperty = new HashMap<String, Long>();
		for(Object[] object : result) {
			sumsByProperty.put((String)object[0], (Long)object[1] == null ? 0L : (Long)object[1]);
		}

		return sumsByProperty;
	}

	@Override
	public AgeGroup getMajorAgeGroupReachedUsers(String pageId, Long dateFrom, Long dateUntil) {

		sql = "SELECT initialAgeGroup, endAgeGroup, CAST(perc AS int) FROM " + 
				"(SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
				 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + "))), 0), 0) AS integer) AS perc FROM " +
				"(SELECT CAST('13' AS varchar) AS initialAgeGroup, CAST('17' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
				 "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_13_AMONG_17 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_13_AMONG_17 + "') AND rd.pageId = '" + pageId + "' " +
				 		"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")" +
				") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		       "SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + "))), 0), 0) AS integer) AS perc FROM " + 
		       "(SELECT CAST('18' AS varchar) AS initialAgeGroup, CAST('24' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_18_AMONG_24 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_18_AMONG_24 + "') AND rd.pageId = '" + pageId + "' " +
		        		"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")" +
		        ") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		       "SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + "))), 0), 0) AS integer) AS perc FROM " +
		       "(SELECT CAST('25' AS varchar) AS initialAgeGroup, CAST('34' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_25_AMONG_34 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_25_AMONG_34 + "') AND rd.pageId = '" + pageId + "' " +
        				"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")" +
		        ") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		      	"SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + "))), 0), 0) AS integer) AS perc FROM " +
		      	"(SELECT CAST('35' AS varchar) AS initialAgeGroup, CAST('44' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_35_AMONG_44 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_35_AMONG_44 + "') AND rd.pageId = '" + pageId + "' " +
	        			"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")" +
		      	") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		      	"SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + "))), 0), 0) AS integer) AS perc FROM " +
		      	"(SELECT CAST('45' AS varchar) AS initialAgeGroup, CAST('54' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_45_AMONG_54 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_45_AMONG_54 + "') AND rd.pageId = '" + pageId + "' " +
	        			"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")" +
		      	") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		      	"SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + "))), 0), 0) AS integer) AS perc FROM " +
		      	"(SELECT CAST('55' AS varchar) AS initialAgeGroup, CAST('64' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		      	 "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_55_AMONG_64 + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_55_AMONG_64 + "') AND rd.pageId = '" + pageId + "' " +
	        			"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")" +
		      	") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		      "UNION ALL " +
		       "SELECT r.initialAgeGroup, r.endAgeGroup, CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + "))), 0), 0) AS integer) AS perc FROM " +
		       "(SELECT CAST('65' AS varchar) AS initialAgeGroup, CAST('+' AS varchar) AS endAgeGroup, SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " + 
		        "WHERE (rd.property = '" + UtilReports.PAGE_IMPRESSIONS_MALE_65_PLUS + "' OR rd.property = '" + UtilReports.PAGE_IMPRESSIONS_FEMALE_65_PLUS + "') AND rd.pageId = '" + pageId + "' " +
        				"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")" +
		       ") AS r " +
		       "GROUP BY r.initialAgeGroup, r.endAgeGroup " +
		       ") AS sums " +
		       "ORDER BY perc DESC " +
		       "OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);
		
		AgeGroup ageGroupFan;
		
		try {
			
			Object[] result = (Object[]) query.getSingleResult();
			
			ageGroupFan = new AgeGroup();
			ageGroupFan.setInitialAgeGroup((String) result[0]);
			ageGroupFan.setEndAgeGroup((String) result[1]);
			ageGroupFan.setPercentage((Integer) result[2]);
			
		} catch (Exception e) {
			
			//----- System.out.println("Bug getMajorAgeGroupReachedUsers");
			//----- System.out.println("Profile Networkid ..:" + pageId);
			
			ageGroupFan = new AgeGroup();
			ageGroupFan.setInitialAgeGroup("");
			ageGroupFan.setEndAgeGroup("");
			ageGroupFan.setPercentage(0);
		}
		
		return ageGroupFan;
	}

	@Override
	public Integer[] getWomenMenReachedUsersPerc(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT CAST(perc AS int) FROM " +
				"(SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
					"WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + "))), 0), 0) AS integer) AS perc FROM " +
				"(SELECT SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " +
					"WHERE rd.property IN ('" + UtilReports.PAGE_IMPRESSIONS_MALE_13_AMONG_17 + "','" + UtilReports.PAGE_IMPRESSIONS_MALE_18_AMONG_24 + "'," +
										  "'" + UtilReports.PAGE_IMPRESSIONS_MALE_25_AMONG_34 + "','" + UtilReports.PAGE_IMPRESSIONS_MALE_35_AMONG_44 + "', " +
									      "'" + UtilReports.PAGE_IMPRESSIONS_MALE_45_AMONG_54 + "','" + UtilReports.PAGE_IMPRESSIONS_MALE_55_AMONG_64 + "'," + 
										  "'" + UtilReports.PAGE_IMPRESSIONS_MALE_65_PLUS + "') AND rd.pageId = '" + pageId + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")) AS r " +
			    "UNION ALL " +
			    "SELECT CAST(COALESCE(ROUND(100 * (SUM(r.sumvalue) / (SELECT NULLIF(CAST(COALESCE(SUM(rd.value), 0) AS bigint), 0) FROM opsocial.reportsdetailed AS rd " + 
			    	"WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_IMP_GENDER_AGE + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + "))), 0), 0) AS integer) AS perc FROM " +
			   "(SELECT SUM(rd.value) AS sumvalue FROM opsocial.reportsdetailed AS rd " +
			   		"WHERE rd.property IN ('" + UtilReports.PAGE_IMPRESSIONS_FEMALE_13_AMONG_17 + "','" + UtilReports.PAGE_IMPRESSIONS_FEMALE_18_AMONG_24 + "'," +
										  "'" + UtilReports.PAGE_IMPRESSIONS_FEMALE_25_AMONG_34 + "','" + UtilReports.PAGE_IMPRESSIONS_FEMALE_35_AMONG_44 + "', " +
									      "'" + UtilReports.PAGE_IMPRESSIONS_FEMALE_45_AMONG_54 + "','" + UtilReports.PAGE_IMPRESSIONS_FEMALE_55_AMONG_64 + "'," + 
										  "'" + UtilReports.PAGE_IMPRESSIONS_FEMALE_65_PLUS + "') AND rd.pageId = '" + pageId + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")) AS r " +
			   ") AS genderPerc";

		query = em.createNativeQuery(sql);
		
		Integer[] result;
		try {
			
			List<Integer> results = (List<Integer>) query.getResultList();
			
			result = new Integer[2];
			
			result[0] = results.get(0);
			result[1] = results.get(1);
			
		} catch (Exception e) {
			
			result = new Integer[2];
			result[0] = 0;
			result[1] = 0;
		}
		
		return result;
	}

	@Override
	public List<ReportFacebookLocation> getOtherActivities(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT rd.property, SUM(rd.value) AS sumvalue FROM ReportDetailed rd " + 
				"WHERE rd.property IN (:pageStoriesByUserPost,:pageStoriesByCheckin,:pageStoriesByMention) " +
				"AND rd.pageId = :pageId AND (rd.date >= :dateFrom AND rd.date <= :dateUntil) " +
				"GROUP BY rd.property " + 
				"ORDER BY sumvalue DESC, rd.property ASC";

		query = em.createQuery(sql);
		query.setParameter("pageStoriesByUserPost", UtilReports.PAGE_STORIES_BY_USER_POST);
		query.setParameter("pageStoriesByCheckin", UtilReports.PAGE_STORIES_BY_CHECKIN);
		query.setParameter("pageStoriesByMention", UtilReports.PAGE_STORIES_BY_MENTION);
		query.setParameter("pageId", pageId);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		List<Object[]> result = query.getResultList();
		
		List<ReportFacebookLocation> otherActivities = new ArrayList<ReportFacebookLocation>();
		for(Object[] object : result) {
			
			ReportFacebookLocation otherActivity = new ReportFacebookLocation();
			otherActivity.setLocation((String)object[0]);
			otherActivity.setValue((Long)object[1] == null ? 0L : (Long)object[1]);
		    
			otherActivities.add(otherActivity);
		}

		return otherActivities;
	}

	@Override
	public List<FanPageGrowth> getFansOnlinePerHour(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT rd.property, SUM(rd.value) FROM ReportDetailed rd " + 
				"WHERE rd.pageId = :pageId AND rd.fromGroup = :fromGroup " +
				"AND ((rd.date >= :dateFrom OR (rd.date = :dateFrom - 86400 AND rd.property " +
				"IN ('" + UtilReports.PAGE_FANS_ONLINE_20 + "','" + UtilReports.PAGE_FANS_ONLINE_21 + "'," +
						"'" + UtilReports.PAGE_FANS_ONLINE_22 + "','" + UtilReports.PAGE_FANS_ONLINE_23 + "'))) " +
				"AND ((rd.date <= :dateUntil - 86400) OR (rd.date = :dateUntil AND rd.property " +
				"NOT IN ('" + UtilReports.PAGE_FANS_ONLINE_20 + "','" + UtilReports.PAGE_FANS_ONLINE_21 + "'," +
						"'" + UtilReports.PAGE_FANS_ONLINE_22 + "','" + UtilReports.PAGE_FANS_ONLINE_23 + "')))) " +
				"GROUP BY rd.property";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_FANS_ONLINE);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		List<Object[]> result = query.getResultList();
		
		List<FanPageGrowth> fansPageGrowth = new ArrayList<FanPageGrowth>();
		
		String pageFansOnlineHour = "";
		Integer pageFansOnlineHourInt = 0;
		for(Object[] object : result) {
			
			// Adjust from PST/GMT -7 to UTC/GMT -3 
			pageFansOnlineHour = (String) object[0];
			pageFansOnlineHourInt = Integer.valueOf(pageFansOnlineHour.substring(pageFansOnlineHour.lastIndexOf('_') + 1)) + 4;  
			
			if(pageFansOnlineHourInt >= 24) {
				pageFansOnlineHourInt = pageFansOnlineHourInt - 24; 
			}
			
			pageFansOnlineHour = UtilReports.PAGE_FANS_ONLINE + "_" + 
					String.valueOf(pageFansOnlineHourInt);
			
			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setDate(pageFansOnlineHourInt.longValue());
			fanPageGrowth.setSum((Long) object[1] == null ? 0L : (Long) object[1]);
			
			fansPageGrowth.add(fanPageGrowth);
		}

		return fansPageGrowth;
	}

	@Override
	public List<FanPageGrowth> getAverageFansOnlinePerHour(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT rd.property, AVG(rd.value) FROM ReportDetailed rd " + 
				"WHERE rd.pageId = :pageId AND rd.fromGroup = :fromGroup " +
				"AND ((rd.date >= :dateFrom OR (rd.date = :dateFrom - 86400 AND rd.property " +
				"IN ('" + UtilReports.PAGE_FANS_ONLINE_20 + "','" + UtilReports.PAGE_FANS_ONLINE_21 + "'," +
						"'" + UtilReports.PAGE_FANS_ONLINE_22 + "','" + UtilReports.PAGE_FANS_ONLINE_23 + "'))) " +
				"AND ((rd.date <= :dateUntil - 86400) OR (rd.date = :dateUntil AND rd.property " +
				"NOT IN ('" + UtilReports.PAGE_FANS_ONLINE_20 + "','" + UtilReports.PAGE_FANS_ONLINE_21 + "'," +
						"'" + UtilReports.PAGE_FANS_ONLINE_22 + "','" + UtilReports.PAGE_FANS_ONLINE_23 + "')))) " +
				"GROUP BY rd.property";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("fromGroup", UtilReports.GROUP_FANS_ONLINE);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		List<Object[]> result = query.getResultList();
		
		List<FanPageGrowth> fansPageGrowth = new ArrayList<FanPageGrowth>();
		
		String pageFansOnlineHour = "";
		Integer pageFansOnlineHourInt = 0;
		for(Object[] object : result) {
			
			// Adjust from PST/GMT -7 to UTC/GMT -3 
			pageFansOnlineHour = (String) object[0];
			pageFansOnlineHourInt = Integer.valueOf(pageFansOnlineHour.substring(pageFansOnlineHour.lastIndexOf('_') + 1)) + 4;  

			if(pageFansOnlineHourInt >= 24) {
				pageFansOnlineHourInt = pageFansOnlineHourInt - 24; 
			}

			pageFansOnlineHour = UtilReports.PAGE_FANS_ONLINE + "_" + 
					String.valueOf(pageFansOnlineHourInt);
			
			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setDate(pageFansOnlineHourInt.longValue());
			fanPageGrowth.setSum((Double) object[1] == null ? 0L : ((Double) object[1]).longValue());
			
			fansPageGrowth.add(fanPageGrowth);
		}

		return fansPageGrowth;
	}

	@Override
	public List<FanPageGrowth> getFansOnlinePerWeekDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT series.weekday, CAST(COALESCE(SUM(rd.value), 0) AS bigint) FROM (SELECT generate_series(0,6) AS weekday) AS series " +
				"INNER JOIN ( " +
					"SELECT rd.* FROM opsocial.reportsdetailed as rd " + 
					"WHERE pageid = '" + pageId + "' AND property = '"+ UtilReports.PAGE_FANS_ONLINE_PER_DAY + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")) " + 
					"AS rd ON " +
					"series.weekday = extract(DOW FROM TO_TIMESTAMP(rd.date)::timestamp with time zone) " +
				"GROUP BY series.weekday " +
				"ORDER BY series.weekday";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		List<FanPageGrowth> fansPageGrowth = new ArrayList<FanPageGrowth>();
		
		for(Object[] object : result) {
			
			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setDate(((Integer) object[0]).longValue());
			fanPageGrowth.setSum((Long) object[1]);
			
			fansPageGrowth.add(fanPageGrowth);
		}

		return fansPageGrowth;
	}

	@Override
	public Object[] getTimeWithMoreFansOnline(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT property, CAST(COALESCE(ROUND(100 * (sumvalue / (SELECT NULLIF(CAST(SUM(rd.value) AS numeric), 0) FROM opsocial.reportsdetailed as rd " + 
				"WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_ONLINE + "' " +
				"AND ((rd.date >= " + dateFrom + " OR (rd.date = " + dateFrom + " - 86400 AND rd.property " +
				"IN ('" + UtilReports.PAGE_FANS_ONLINE_20 + "','" + UtilReports.PAGE_FANS_ONLINE_21 + "'," +
						"'" + UtilReports.PAGE_FANS_ONLINE_22 + "','" + UtilReports.PAGE_FANS_ONLINE_23 + "'))) " +
				"AND ((rd.date <= " + dateUntil + " - 86400) OR (rd.date = " + dateUntil + " AND rd.property " +
				"NOT IN ('" + UtilReports.PAGE_FANS_ONLINE_20 + "','" + UtilReports.PAGE_FANS_ONLINE_21 + "'," +
						"'" + UtilReports.PAGE_FANS_ONLINE_22 + "','" + UtilReports.PAGE_FANS_ONLINE_23 + "')))))), 0), 0) AS integer) " +
				"FROM ( " +
				"SELECT property, CAST(SUM(value) AS numeric) as sumvalue FROM opsocial.reportsdetailed as rd " + 
				"WHERE pageid = '" + pageId + "' AND fromgroup = '" + UtilReports.GROUP_FANS_ONLINE + "' " +
				"AND ((rd.date >= " + dateFrom + " OR (rd.date = " + dateFrom + " - 86400 AND rd.property " +
				"IN ('" + UtilReports.PAGE_FANS_ONLINE_20 + "','" + UtilReports.PAGE_FANS_ONLINE_21 + "'," +
						"'" + UtilReports.PAGE_FANS_ONLINE_22 + "','" + UtilReports.PAGE_FANS_ONLINE_23 + "'))) " +
				"AND ((rd.date <= " + dateUntil + " - 86400) OR (rd.date = " + dateUntil + " AND rd.property " +
				"NOT IN ('" + UtilReports.PAGE_FANS_ONLINE_20 + "','" + UtilReports.PAGE_FANS_ONLINE_21 + "'," +
						"'" + UtilReports.PAGE_FANS_ONLINE_22 + "','" + UtilReports.PAGE_FANS_ONLINE_23 + "')))) " +
				"GROUP BY property " +
				"ORDER BY sumvalue DESC " +
				"LIMIT 1 OFFSET 0 " +
				") AS rda";
		
		query = em.createNativeQuery(sql);
		
		Object[] result = null;
		try {
			
			result = (Object[]) query.getSingleResult();
			
			// Adjust from PST/GMT -7 to UTC/GMT -3
			String pageFansOnlineHour = (String) result[0];
			Integer pageFansOnlineHourInt = Integer.valueOf(pageFansOnlineHour.substring(pageFansOnlineHour.lastIndexOf('_') + 1)) + 4;
			Integer pageFansOnlineHourPercetage = (Integer) result[1];

			if(pageFansOnlineHourInt >= 24) {
				pageFansOnlineHourInt = pageFansOnlineHourInt - 24; 
			}

			pageFansOnlineHour = UtilReports.PAGE_FANS_ONLINE + "_" + 
					String.valueOf(pageFansOnlineHourInt);
			
			
			result = new Object[2];
			result[0] = pageFansOnlineHourInt;
			result[1] = pageFansOnlineHourPercetage;
			
		} catch (NoResultException e) {
			
			result = new Object[2];
			result[0] = 0;
			result[1] = 0;
		}
		
		return result;
	}

	@Override
	public Object[] getWeekDayWithMoreFansOnline(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT weekdaysum, CAST(COALESCE(ROUND(100 * (sumweekday / (SELECT NULLIF(CAST(SUM(rd.value) AS numeric), 0) FROM opsocial.reportsdetailed as rd " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_FANS_ONLINE_PER_DAY + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + "))), 0), 0) AS integer) " +
				"FROM ( " +
				"SELECT series.weekday AS weekdaysum, COALESCE(SUM(rd.value), 0) AS sumweekday FROM (SELECT generate_series(0,6) AS weekday) AS series " +
				"INNER JOIN ( " +
				"SELECT rd.* FROM opsocial.reportsdetailed as rd " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + UtilReports.PAGE_FANS_ONLINE_PER_DAY + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")) " + 
				"AS rd ON " +
				"series.weekday = extract(DOW FROM TO_TIMESTAMP(rd.date)::timestamp with time zone) " +
				"GROUP BY series.weekday " +
				"ORDER BY sumweekday DESC " +
				"LIMIT 1 OFFSET 0 " +
				") AS rda";
		
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
	public boolean deleteReportsDetailed(String pageId) {
		
		sql = "DELETE FROM ReportDetailed rd WHERE rd.pageId = :pageId";
		
		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
	
		try {
			
			query.executeUpdate();
			return true;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Override
	public List<SumByDate> getPropertyByMonth(String pageId, String property) {
		
		sql = "SELECT CAST(date_part('year', TO_TIMESTAMP(date)::timestamp with time zone) as integer), CAST(date_part('month', TO_TIMESTAMP(date)::timestamp with time zone) as integer), CAST(COALESCE(SUM(value), 0) AS bigint) FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + property + "' AND date >= " + UtilReports.getDateForFanPageGrowth() + " " +
					"GROUP BY date_part('year', TO_TIMESTAMP(date)::timestamp with time zone), date_part('month', TO_TIMESTAMP(date)::timestamp with time zone) " +
					"ORDER BY date_part('year', TO_TIMESTAMP(date)::timestamp with time zone), date_part('month', TO_TIMESTAMP(date)::timestamp with time zone)";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		List<SumByDate> sumsMonthYear = new ArrayList<SumByDate>();
		
		for (Object[] object : result) {
			
			SumByDate sumMonthYear = new SumByDate();
			sumMonthYear.setYear((Integer)object[0]);
			sumMonthYear.setMonth((Integer)object[1]);
			sumMonthYear.setSum((Long)object[2]);
			
			sumsMonthYear.add(sumMonthYear);
		}
		
		// Completes the list of twelve months required by the graphic, if there is no twelve months with data in database.
		if(sumsMonthYear.size() < 12) {
			
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
				for (int j = 0; j < sumsMonthYear.size(); j++) {
					if(monthSequence.get(i).equals(sumsMonthYear.get(j).getMonth())) {
						noInclude = true;
						break;
					}
				}
				
				if(!noInclude) {
					
					SumByDate sumMonthYear = new SumByDate();
					sumMonthYear.setMonth(monthSequence.get(i));
					sumMonthYear.setYear(yearSequence.get(i));
					sumMonthYear.setSum(0L); 
					
					sumsMonthYear.add(i, sumMonthYear);
					
					if(sumsMonthYear.size() == 12) {
						break;
					}
				}
			}
		}
		
		return sumsMonthYear;
	}

	@Override
	public List<FanPageGrowth> getPropertyByDay(String pageId, String property, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT date AS day, CAST(COALESCE(value, 0) AS BIGINT) " +
				"FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property = '" + property + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +  
				"ORDER BY day";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		List<FanPageGrowth> fansPageGrowth = new ArrayList<FanPageGrowth>();
		
		for (Object[] object : result) {
			
			FanPageGrowth fanPageGrowth = new FanPageGrowth();
			fanPageGrowth.setDate((Long) object[0]);
			fanPageGrowth.setSum((Long) object[1]);
			
			fansPageGrowth.add(fanPageGrowth);
		}

		return fansPageGrowth;
	}

	@Override
	public List<PropertyValue> getPropertiesFromGroup(String pageId, String fromGroup, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT property, CAST(COALESCE(SUM(value), 0) AS BIGINT) " +
				"FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND fromgroup = '" + fromGroup + "' AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +
				"GROUP BY property " +
				"ORDER BY property";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
		
		for (Object[] object : result) {
			
			PropertyValue propertyValue = new PropertyValue();
			propertyValue.setProperty((String) object[0]);
			propertyValue.setValue((Long) object[1]);
			
			propertyValues.add(propertyValue);
		}

		return propertyValues;
	}

	@Override
	public Long getPropertyCount(String pageId, String property, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT COALESCE(CAST(SUM(value) AS BIGINT), 0) " +
				"FROM opsocial.reportsdetailed " +
				"WHERE pageid = '" + pageId + "' AND property = '" + property + "' " +
				"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ")";

		query = em.createNativeQuery(sql);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public FanPageGrowth getMajorTotalViewsDay(String pageId, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT date AS day, CAST(COALESCE(SUM(value), 0) AS BIGINT) as sum " +
				"FROM opsocial.reportsdetailed " + 
				"WHERE pageid = '" + pageId + "' AND property IN ('" + UtilReports.PAGE_VIDEO_VIEWS_ORGANIC + "','" + UtilReports.PAGE_VIDEO_VIEWS_PAID + "') " + 
				"AND (date >= " + dateFrom + " AND date <= " + dateUntil + ") " +
				"GROUP BY day "+
				"ORDER BY sum DESC " +
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);
		
		Object[] result;
		FanPageGrowth fanPageGrowth = new FanPageGrowth();
		
		try {
			
			result = (Object[]) query.getSingleResult();
			
			fanPageGrowth.setDate((Long) result[0]);
			fanPageGrowth.setSum((Long) result[1]);
			
		} catch (NoResultException e) {
			
			e.printStackTrace();
			
			fanPageGrowth.setDate(dateUntil);
			fanPageGrowth.setSum(0L);
		}
		
		return fanPageGrowth;
	}
}
