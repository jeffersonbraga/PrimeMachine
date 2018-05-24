package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.AgeGroup;
import br.com.opsocial.ejb.entity.report.FanPageGrowth;
import br.com.opsocial.ejb.entity.report.PropertyValue;
import br.com.opsocial.ejb.entity.report.ReportDetailed;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;
import br.com.opsocial.ejb.entity.report.SumByDate;

@Remote
public interface MaintenanceReportDetailedRemote extends AbstractDAS<ReportDetailed> {
	
	public Long getCountDateByPageId(String pageId, Long date, String property) throws IllegalArgumentException;
	public Long getSumForProperty(String pageId, String property, Long dateFrom, Long dateUntil) throws IllegalArgumentException;
	public Integer[] getValuesForProperty(String pageId, String property, Long dateFrom, Long dateUntil) throws IllegalArgumentException;
	public Long getValueForProperty(String pageId, String property) throws IllegalArgumentException;
	public Long getDateForProperty(String pageId, String property) throws IllegalArgumentException;
	public ReportDetailed getEntityByPageIdProperty(String pageId, String property) throws IllegalArgumentException;
	public ReportDetailed getEntity(String pageId, String property, Long date) throws IllegalArgumentException;
	public List<ReportDetailed> getEntitiesByPage(String pageId, String property, Long dateFrom, Long dateUntil) throws IllegalArgumentException;
	public Map<String, List<ReportDetailed>> getEntitiesWithoutValue(String pageId, Long Date) throws IllegalArgumentException, Exception;
	public ReportDetailed saveByDay(ReportDetailed object) throws IllegalArgumentException, Exception;
	public List<Long> getDatesWithoutValue(String pageId) throws IllegalArgumentException, Exception;
	
	public Map<String, Long> getSumsForProperties(String pageId, List<String> properties, Long dateFrom, Long dateUntil) throws IllegalArgumentException;
	public Map<String, Long> getSumsForPropertiesPages(List<String> pageIds, List<String> properties, Long dateFrom, Long dateUntil) throws IllegalArgumentException;
	
	public Map<String, Long> getValuesForProperties(String pageId, List<String> properties) throws IllegalArgumentException;
	public Map<String, Long> getSumsForPropertiesPages(List<String> pageIds, List<String> properties) throws IllegalArgumentException;
	
	// New reports - Historical.
	public List<FanPageGrowth> getFanPageGrowth(String pageId);
	public Long getCountFansBeforeDate(String pageId, Long date);
	
	public Map<String, Long> getAboutYourFansGender(String pageId);
	public AgeGroup getMajorAgeGroupFans(String pageId);
	public Integer[] getWomenMenFansPerc(String pageId);
	
	public Map<String, Long> getAboutReachedUsers(String pageId);
	public AgeGroup getMajorAgeGroupReachedUsers(String pageId);
	public Integer[] getWomenMenReachedUsersPerc(String pageId);
	
	public List<SumByDate> getSingleReach(String pageId);
	public SumByDate getMostReachedMonth(String pageId);
	
	// New reports - Overview.
	public List<FanPageGrowth> getFanPageGrowth(String pageId, Long dateFrom, Long dateUntil, Long accumulatedFanPageGrowth);
	
	public List<FanPageGrowth> getLikesByDay(String pageId, Long dateFrom, Long dateUntil);
	public List<FanPageGrowth> getUnlikesByDay(String pageId, Long dateFrom, Long dateUntil);
	
	public ReportDetailed getTotalOfFans(String pageId);
	public Long getConqueredFans(String pageId, Long dateFrom, Long dateUntil);
	public Long getLostFans(String pageId, Long dateFrom, Long dateUntil);
	
	public Map<String, Long> getFansLikeSource(String pageId, Long dateFrom, Long dateUntil);
	
	public Map<String, Long> getAboutReachedUsers(String pageId, Long dateFrom, Long dateUntil);
	public AgeGroup getMajorAgeGroupReachedUsers(String pageId, Long dateFrom, Long dateUntil);
	public Integer[] getWomenMenReachedUsersPerc(String pageId, Long dateFrom, Long dateUntil);
	
	public List<ReportFacebookLocation> getOtherActivities(String pageId, Long dateFrom, Long dateUntil);
	
	// New reports - Content.
	public List<FanPageGrowth> getFansOnlinePerHour(String pageId, Long dateFrom, Long dateUntil);
	public List<FanPageGrowth> getAverageFansOnlinePerHour(String pageId, Long dateFrom, Long dateUntil);
	public List<FanPageGrowth> getFansOnlinePerWeekDay(String pageId, Long dateFrom, Long dateUntil);
	public Object[] getTimeWithMoreFansOnline(String pageId, Long dateFrom, Long dateUntil);
	public Object[] getWeekDayWithMoreFansOnline(String pageId, Long dateFrom, Long dateUntil);
	
	public List<FanPageGrowth> getImpressionsByDay(String pageId, Long dateFrom, Long dateUntil);
	public List<FanPageGrowth> getReachByDay(String pageId, Long dateFrom, Long dateUntil);
	public Long getTotalReach(String pageId, Long dateFrom, Long dateUntil);
	public Long getTotalImpressions(String pageId, Long dateFrom, Long dateUntil);
	
	public List<FanPageGrowth> getEngagementByDay(String pageId, Long dateFrom, Long dateUntil);
	public Long getTotalEngagement(String pageId, Long dateFrom, Long dateUntil);
	
	public List<FanPageGrowth> getPropertyByDay(String pageId, String property, Long dateFrom, Long dateUntil);
	public List<PropertyValue> getPropertiesFromGroup(String pageId, String fromGroup, Long dateFrom, Long dateUntil);
	public Long getPropertyCount(String pageId, String property, Long dateFrom, Long dateUntil);
	
	public List<SumByDate> getPropertyByMonth(String pageId, String property);
	
	public FanPageGrowth getMajorTotalViewsDay(String pageId, Long dateFrom, Long dateUntil);
	
	public boolean deleteReportsDetailed(String pageId);
}
