package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.TwitterReport;

@Remote
public interface MaintenanceTwitterReportRemote extends AbstractDAS<TwitterReport>{

	public Long getSumValuesForProfileProperty(String idProfile, String property) throws IllegalArgumentException;
	public Long getSumValuesForProfileProperty(String idProfile, String property, Long dateFrom, Long dateUntil);
	public TwitterReport getEntityByProfileProperty(String idProfile, String property) throws IllegalArgumentException;
	public Long getValueForProperty(String idProfile, String property) throws IllegalArgumentException;
	public Integer[] getValuesForProperty(String idProfile, String property, Long dateFrom, Long dateUntil) throws IllegalArgumentException;
	public Integer[] getValuesForProperty(List<String> idProfiles, String property, Long dateFrom, Long dateUntil) throws IllegalArgumentException;
	public List<TwitterReport> getEntitiesByProfiles(List<String> idProfiles, String property, Long dateFrom, Long dateUntil) throws IllegalArgumentException;
	public List<TwitterReport> getEntitiesByProfile(String idProfile, String property, Long dateFrom, Long dateUntil) throws IllegalArgumentException;
	
	public Map<String, Long> getSumsForProperties(String idProfile, List<String> properties, Long dateFrom, Long dateUntil) throws IllegalArgumentException;
	
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
