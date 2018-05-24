package br.com.opsocial.ejb.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.report.TwitterReport;

@Local
public interface TwitterReportDAO extends AbstractDAO<TwitterReport>{

	Integer[] getValuesByProperty(String where, String order);
	Integer[] getSumValuesByProperty(String where, String groupBy, String order);
	List<TwitterReport> getEntitiesByProfiles(List<String> idProfiles, String property, Long dateFrom, Long dateUntil);
	Long getValueByProperty(String where);
	List<TwitterReport> getEntitiesByProfile(String idProfile, String property, Long dateFrom, Long dateUntil);
	
	Map<String, Long> getSumsForProperties(String idProfile, List<String> properties, Long dateFrom, Long dateUntil);
	
	
	public Long getValueForProperty(String idProfile, String property);
	Integer[] getValuesForProperty(List<String> idProfiles, String property, Long dateFrom, Long dateUntil);
	Integer[] getValuesForProperty(String idProfile, String property, Long dateFrom, Long dateUntil);
	Long getSumValuesForProfileProperty(String idProfile, String property, Long dateFrom, Long dateUntil);
	TwitterReport getEntityByProfileProperty(String idProfile, String property);
	
	public Long getSumOfValues(String idProfile, String property);
	public Long getSumOfValues(String idProfile, String property, Long dateFrom, Long dateUntil);
	
	public Long getValue(String idProfile, String property);
	public TwitterReport getDayWithMoreFollowers(String idProfile, String property, Long dateFrom, Long dateUntil);
	
	public Long getAverageOfValues(String idProfile, String property, Long dateFrom, Long dateUntil);
	
	public Object[] getTweetTypeWithMoreTweets(String idProfile, Long dateFrom, Long dateUntil);
	
	public Map<String, Integer> getEngagementPercentages(String idProfile, Long dateFrom, Long dateUntil);
	public Long getEngagementPeakDay(String idProfile, Long dateFrom, Long dateUntil);
	
	public Map<Date, Long> getTweetsInteractionsByDay(String idProfile, Long dateFrom, Long dateUntil);
	public Long getTweetsInteractionsCount(String idProfile, Long dateFrom, Long dateUntil);
}
