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
import br.com.opsocial.ejb.entity.report.TwitterReport;
import br.com.opsocial.ejb.utils.reports.UtilReports;

@Stateless
public class TwitterReportDAOImpl extends AbstractDAOImpl<TwitterReport> implements TwitterReportDAO {

	@Override
	public Integer[] getValuesByProperty(String where, String order) {
		
		Integer[] valuesForProperty;

		sql = "SELECT tr.value FROM TwitterReport tr WHERE "+ where.trim() + " ORDER BY " + order.trim();
		query = em.createQuery(sql);
		
		List<Long> result = query.getResultList();
		
		valuesForProperty = new Integer[result.size()];
		
		for(int i = 0; i < result.size(); i++) {
			valuesForProperty[i] = result.get(i).intValue();
		}

		return valuesForProperty;
	} 

	@Override
	public Long getValueByProperty(String where) {
		
		sql = "SELECT tr.value FROM TwitterReport tr WHERE " + where.trim();

		try {
			return (Long) em.createQuery(sql).getSingleResult();
		} catch (Exception e) {
			return 0L;
		}
	}

	@Override
	public Integer[] getSumValuesByProperty(String where, String groupBy, String order) {
		
		Integer[] sumValuesForProperty;
		
		sql = "SELECT sum(tr.value) FROM TwitterReport tr WHERE " + where.trim() 
				+ " GROUP BY " + groupBy.trim()
				+ " ORDER BY " + order.trim();

		query = em.createQuery(sql);
		
		List<Long> result = query.getResultList();
		
		sumValuesForProperty = new Integer[result.size()];
		
		for(int i = 0; i < result.size(); i++) {
			sumValuesForProperty[i] = result.get(i).intValue();
		}

		return sumValuesForProperty;
	}
	

	@Override
	public List<TwitterReport> getEntitiesByProfiles(List<String> idProfiles, 
			String property, Long dateFrom, Long dateUntil) {
		
		String profilesId = "(";
		boolean first = true;
		for(String idProfile : idProfiles) {
			if(first) {
				profilesId = profilesId.concat("'"+idProfile+"'");
				first = false;
			} else {
				profilesId = profilesId.concat(",'"+idProfile+"'");
			}
		}
		profilesId = profilesId.concat(")");
		
		sql = "SELECT sum(tr.value), tr.date FROM TwitterReport tr " +
				"WHERE tr.idProfile IN "+profilesId+" AND tr.property = :property " +
				"AND (tr.date >= :dateFrom AND tr.date <= :dateUntil) " +
				"GROUP BY tr.date " +
				"ORDER BY tr.date";

		query = em.createQuery(sql);
		query.setParameter("property", property);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		List<Object[]> result = query.getResultList();
		
		List<TwitterReport> twitterReports = new ArrayList<TwitterReport>();
		for(int i = 0; i < result.size(); i++) {
			
			TwitterReport twitterReport = new TwitterReport();
			twitterReport.setValue((Long)result.get(i)[0]);
			twitterReport.setProperty(property);
			twitterReport.setDate((Long)result.get(i)[1]);
			
			twitterReports.add(twitterReport);
		}
		
		return twitterReports;
	}

	@Override
	public List<TwitterReport> getEntitiesByProfile(String idProfile, String property, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT tr FROM TwitterReport tr WHERE tr.idProfile = :idProfile AND tr.property = :property " +
				"AND (tr.date >= :dateFrom AND tr.date <= :dateUntil) ORDER BY tr.date";
		
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("property", property);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);

		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<TwitterReport>();
		}
	}

	@Override
	public Map<String, Long> getSumsForProperties(String idProfile, List<String> properties, Long dateFrom, Long dateUntil) {
		
		String propertiesIn = "(";
		
		if(!properties.isEmpty()) {
			propertiesIn = propertiesIn.concat("'"+properties.get(0)+"'");
			for(int i = 1; i < properties.size(); i++) {
				propertiesIn = propertiesIn.concat(",'"+properties.get(i)+"'");
			}
		}
		
		propertiesIn = propertiesIn.concat(")");
		
		sql = "SELECT SUM(tr.value), tr.property FROM TwitterReport tr " +
				"WHERE tr.property IN " + propertiesIn + " AND tr.idProfile = :idProfile " +
				"AND (tr.date >= :dateFrom AND tr.date <= :dateUntil) " +
				"GROUP BY tr.property";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
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
	public Long getSumOfValues(String idProfile, String property) {
		
		Long sum;
		
		sql = "SELECT sum(tr.value) FROM TwitterReport tr " +
				"WHERE tr.idProfile = :idProfile AND tr.property = :property " +
				"GROUP BY tr.property";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("property", property);
		
		try {
			
			sum = (Long) query.getSingleResult();
			
			if(sum == null) {
				sum = 0L;
			}
			
		} catch (NoResultException e) {
			sum = 0L;
		}

		return sum;
	}

	@Override
	public TwitterReport getEntityByProfileProperty(String idProfile,
			String property) throws IllegalArgumentException {
		
		String where = "tr.idProfile = '"+idProfile+"' AND tr.property = '"+property+"'";
		
		sql = "SELECT tr FROM TwitterReport tr WHERE " + where.trim();

		try {
			return (TwitterReport) em.createQuery(sql).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Long getSumValuesForProfileProperty(String idProfile, String property, Long dateFrom, Long dateUntil) {
		
		String where = "x.idProfile = '"+idProfile+"' AND x.property = '"+property+"' AND (x.date >= "+dateFrom+" AND x.date <= "+dateUntil+")";
		
		return getSum("value",where);
	}

	@Override
	public Long getValueForProperty(String idProfile, String property) throws IllegalArgumentException {
		
		String where = "tr.idProfile = '"+idProfile+"' AND tr.property = '"+property+"'";
		
		return getValueByProperty(where);
	}

	@Override
	public Integer[] getValuesForProperty(String idProfile, String property, Long dateFrom, Long dateUntil) {
		
		String where = "tr.idProfile = '"+idProfile+"' AND tr.property = '"+property+"' " +
				"AND (tr.date >= "+dateFrom+" AND tr.date <= "+dateUntil+")";
		String order = "tr.date";
		
		return getValuesByProperty(where,order);
	}

	@Override
	public Integer[] getValuesForProperty(List<String> idProfiles, String property, Long dateFrom, Long dateUntil) {
		
		String profilesId = "(";
		boolean first = true;
		for(String idProfile : idProfiles) {
			if(first) {
				profilesId = profilesId.concat("'"+idProfile+"'");
				first = false;
			} else {
				profilesId = profilesId.concat(",'"+idProfile+"'");
			}
		}
		profilesId = profilesId.concat(")");
		
		String where = "tr.idProfile IN "+profilesId+" AND tr.property = '"+property+"' " +
				"AND (tr.date >= "+dateFrom+" AND tr.date <= "+dateUntil+")";
		String groupBy = "tr.date";
		String order = "tr.date";
		
		return getSumValuesByProperty(where, groupBy, order);
	}

	@Override
	public Long getValue(String idProfile, String property) {
		
		sql = "SELECT tr.value FROM TwitterReport tr " +
				"WHERE tr.idProfile = :idProfile AND tr.property = :property";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("property", property);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getSumOfValues(String idProfile, String property, Long dateFrom, Long dateUntil) {
		
		Long sum;
		
		sql = "SELECT sum(tr.value) FROM TwitterReport tr " +
				"WHERE tr.idProfile = :idProfile AND tr.property = :property " +
				"AND (tr.date >= :dateFrom AND tr.date <= :dateUntil) " +
				"GROUP BY tr.property";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("property", property);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		try {

			sum = (Long) query.getSingleResult();
			
			if(sum == null) {
				sum = 0L;
			}
			
		} catch (NoResultException e) {
			sum = 0L;
		}

		return sum;
	}

	@Override
	public TwitterReport getDayWithMoreFollowers(String idProfile, String property, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT tr FROM TwitterReport tr " +
				"WHERE tr.idProfile = :idProfile AND tr.property = :property " +
				"AND (tr.date >= :dateFrom AND tr.date <= :dateUntil) " +
				"ORDER BY tr.value DESC, tr.date DESC";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("property", property);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setFirstResult(0);
		query.setMaxResults(1);
		
		try {
			
			return (TwitterReport) query.getSingleResult();
			
		} catch (NoResultException e) {

			TwitterReport twitterReport = new TwitterReport();
			twitterReport.setIdProfile(idProfile);
			twitterReport.setProperty(property);
			twitterReport.setValue(0L);
			twitterReport.setDate(dateFrom);
			
			return twitterReport;
		}
	}

	@Override
	public Long getAverageOfValues(String idProfile, String property, Long dateFrom, Long dateUntil) {
		
		Long average;
		
		sql = "SELECT AVG(tr.value) FROM TwitterReport tr " +
				"WHERE tr.idProfile = :idProfile AND tr.property = :property " +
				"AND (tr.date >= :dateFrom AND tr.date <= :dateUntil) " +
				"GROUP BY tr.property";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("property", property);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		try {
			
			average = ((Double) query.getSingleResult()).longValue();
		
			if(average == null) {
				average = 0L;
			}
			
		} catch (NoResultException e) {
			average = 0L;
		}

		return average;
	}

	@Override
	public Object[] getTweetTypeWithMoreTweets(String idProfile, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT tr.property, CAST(COALESCE(ROUND(100 * (SUM(tr.value)::decimal / (SELECT NULLIF(CAST(COALESCE(SUM(tr.value), 0) AS bigint), 0) AS sumvalue FROM opsocial.twitterreports tr " + 
				"WHERE tr.idprofile = '" + idProfile + "' AND tr.property IN ('" + UtilReports.MSG_SENT_WITH_TEXT_COUNT + "','" + UtilReports.MSG_SENT_WITH_PHOTO_COUNT + "','" + UtilReports.MSG_SENT_WITH_LINK_COUNT + "') " +
				"AND (tr.date >= " + dateFrom + " AND tr.date <= " + dateUntil + "))), 0), 0) AS integer) " +
				"FROM opsocial.twitterreports tr " +
				"WHERE tr.idprofile = '" + idProfile + "' AND tr.property IN ('" + UtilReports.MSG_SENT_WITH_TEXT_COUNT + "','" + UtilReports.MSG_SENT_WITH_PHOTO_COUNT + "','" + UtilReports.MSG_SENT_WITH_LINK_COUNT + "') " +
				"AND (tr.date >= " + dateFrom + " AND tr.date <= " + dateUntil + ") " +
				"GROUP BY tr.property " +
				"ORDER BY SUM(tr.value) DESC, tr.property " +
				"OFFSET 0 LIMIT 1";

		query = em.createNativeQuery(sql);
		
		Object[] result = null;
		try {
			
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException e) {
			
			result = new Object[2];
			result[0] = UtilReports.MSG_SENT_WITH_TEXT_COUNT;
			result[1] = 0;
		}
		
		return result;
	}

	@Override
	public Map<String, Integer> getEngagementPercentages(String idProfile, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT r.property, CAST(COALESCE(ROUND(100 * (SUM(r.eachengagement) / (" + 
				"SELECT NULLIF(CAST(COALESCE(SUM(tr.value), 0) AS bigint), 0) AS totalengagement FROM opsocial.twitterreports AS tr " +
				"WHERE tr.idprofile = '" + idProfile + "' AND tr.property " +
						"IN('" + UtilReports.REPLIES_OF_ME + "','" + UtilReports.RETWEETS_OF_ME + "','" + UtilReports.MENTIONS + "','" + UtilReports.FAVORITE_COUNT + "') " +
				"AND (tr.date >= " + dateFrom + " AND tr.date <= " + dateUntil + "))), 0), 0) AS integer) FROM (" +
				"SELECT tr.property, CAST(COALESCE(SUM(tr.value), 0) AS bigint) AS eachengagement FROM opsocial.twitterreports AS tr " +
				"WHERE tr.idprofile = '" + idProfile + "' AND tr.property " +
						"IN('" + UtilReports.REPLIES_OF_ME + "','" + UtilReports.RETWEETS_OF_ME + "','" + UtilReports.MENTIONS + "','" + UtilReports.FAVORITE_COUNT + "') " +
				"AND (tr.date >= " + dateFrom + " AND tr.date <= " + dateUntil + ") " +
				"GROUP BY tr.property) AS r " +
				"GROUP BY r.property";

		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		
		Map<String, Integer> engagementPercentage = new HashMap<String, Integer>();
		for (Object[] object : result) {
			engagementPercentage.put(((String) object[0]), (Integer) object[1]);
		}
		
		return engagementPercentage;
	}

	@Override
	public Long getEngagementPeakDay(String idProfile, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT tr.date, SUM(tr.value) AS sumvalue FROM TwitterReport tr " +
				"WHERE tr.idProfile = :idProfile AND tr.property " +
				"IN('" + UtilReports.REPLIES_OF_ME + "','" + UtilReports.RETWEETS_OF_ME + "','" + UtilReports.MENTIONS + "','" + UtilReports.FAVORITE_COUNT + "') " +
				"AND (tr.date >= :dateFrom AND tr.date <= :dateUntil) " +
				"GROUP BY tr.date " +
				"ORDER BY sumvalue DESC, tr.date DESC";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		query.setFirstResult(0);
		query.setMaxResults(1);
		
		Object[] result = null;
		try {
			
			result = (Object[]) query.getSingleResult();
			
		} catch (NoResultException e) {
			
			result = new Object[2];
			result[0] = 0L;
			result[1] = 0L;
		}
		
		return (Long) result[0];
	}
	
	@Override
	public Map<Date, Long> getTweetsInteractionsByDay(String idProfile, Long dateFrom, Long dateUntil) {
		
		sql = "SELECT tr.date, SUM(tr.value) AS sumvalue FROM TwitterReport tr " +
				"WHERE tr.idProfile = :idProfile AND tr.property " +
				"IN('" + UtilReports.REPLIES_OF_ME + "','" + UtilReports.RETWEETS_OF_ME + "','" + UtilReports.MENTIONS + "','" + UtilReports.FAVORITE_COUNT + "') " +
				"AND (tr.date >= :dateFrom AND tr.date <= :dateUntil) " +
				"GROUP BY tr.date " +
				"ORDER BY tr.date ASC";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		List<Object[]> result = query.getResultList();
		
		Map<Date, Long> tweetsInteractionsByDay = new LinkedHashMap<Date, Long>();
		for (Object[] object : result) {
			tweetsInteractionsByDay.put(new Date((Long) object[0] * 1000L), (Long) object[1]);
		}
		
		return tweetsInteractionsByDay;
	}

	@Override
	public Long getTweetsInteractionsCount(String idProfile, Long dateFrom, Long dateUntil) {
		
		Long sum;
		
		sql = "SELECT sum(tr.value) FROM TwitterReport tr " +
				"WHERE tr.idProfile = :idProfile AND tr.property " +
				"IN('" + UtilReports.REPLIES_OF_ME + "','" + UtilReports.RETWEETS_OF_ME + "'," +
				   "'" + UtilReports.MENTIONS + "','" + UtilReports.FAVORITE_COUNT + "') " + 
				"AND (tr.date >= :dateFrom AND tr.date <= :dateUntil)";

		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("dateFrom", dateFrom);
		query.setParameter("dateUntil", dateUntil);
		
		try {
			
			sum = (Long) query.getSingleResult();
			
			if(sum == null) {
				sum = 0L;
			}
			
		} catch (NoResultException e) {
			sum = 0L;
		}

		return sum;
	}
	
}
