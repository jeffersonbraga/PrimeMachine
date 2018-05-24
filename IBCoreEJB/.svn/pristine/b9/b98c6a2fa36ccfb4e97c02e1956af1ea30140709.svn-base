package br.com.opsocial.ejb.das;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ReportDetailedDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.report.AgeGroup;
import br.com.opsocial.ejb.entity.report.FanPageGrowth;
import br.com.opsocial.ejb.entity.report.PropertyValue;
import br.com.opsocial.ejb.entity.report.ReportDetailed;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;
import br.com.opsocial.ejb.entity.report.SumByDate;

@Stateless
public class MaintenanceReportDetailedBean extends AbstractDASImpl<ReportDetailed> implements MaintenanceReportDetailedRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	ReportDetailedDAO dao; 
	
	@Override
	public AbstractDAO<ReportDetailed> getDAO() {
		return dao;
	}
	
	@Override
	public ReportDetailed saveByDay(ReportDetailed object) 
			throws IllegalArgumentException, Exception {
		return dao.saveByDay(object);
	}
	
	@Override
	public Collection<ReportDetailed> save(ArrayList<ReportDetailed> t)
			throws Exception, IllegalArgumentException {
		return super.save(t);
	}

	@Override
	public Long getCountDateByPageId(String pageId, Long date, String property)
			throws IllegalArgumentException {
		return dao.getCountDateByPageId(pageId, date, property);
	}

	@Override
	public Long getSumForProperty(String pageId, String property, Long dateFrom, Long dateUntil) throws IllegalArgumentException {
		return dao.getSumForProperty(pageId, property, dateFrom, dateUntil);
	}
	
	@Override
	public Integer[] getValuesForProperty(String pageId, String property, Long dateFrom, Long dateUntil) throws IllegalArgumentException {
		return dao.getValuesForProperty(pageId, property, dateFrom, dateUntil);
	}
	
	@Override
	public Long getValueForProperty(String pageId, String property) throws IllegalArgumentException {
		return dao.getValueForProperty(pageId, property);
	}

	@Override
	public Long getDateForProperty(String pageId, String property) throws IllegalArgumentException {
		return dao.getDateForProperty(pageId, property);
	}
	
	@Override
	public ReportDetailed getEntityByPageIdProperty(String pageId, String property) throws IllegalArgumentException {
		return dao.getEntityByPageIdProperty(pageId, property);
	}

	@Override
	public List<ReportDetailed> getEntitiesByPage(String pageId, String property, Long dateFrom, Long dateUntil) 
			throws IllegalArgumentException {
		return dao.getEntitiesByPage(pageId, property, dateFrom, dateUntil);
	}

	@Override
	public ReportDetailed getEntity(String pageId, String property, Long date)
			throws IllegalArgumentException {
		return dao.getEntity(pageId, property, date);
	}

	@Override
	public Map<String, List<ReportDetailed>> getEntitiesWithoutValue(
			String pageId, Long date) throws IllegalArgumentException, Exception {
		return dao.getEntitiesWithoutValue(pageId, date);
	}

	@Override
	public List<Long> getDatesWithoutValue(String pageId) throws IllegalArgumentException, Exception {
		return dao.getDatesWithoutValue(pageId);
	}

	@Override
	public Map<String, Long> getSumsForProperties(String pageId, List<String> properties, Long dateFrom, Long dateUntil) throws IllegalArgumentException {
		return dao.getSumsForProperties(pageId, properties, dateFrom, dateUntil);
	}

	@Override
	public Map<String, Long> getValuesForProperties(String pageId,List<String> properties) throws IllegalArgumentException {
		return dao.getValuesForProperties(pageId, properties);
	}

	@Override
	public Map<String, Long> getSumsForPropertiesPages(List<String> pageIds, List<String> properties, Long dateFrom, Long dateUntil) throws IllegalArgumentException {
		return dao.getSumsForPropertiesPages(pageIds, properties, dateFrom, dateUntil);
	}

	@Override
	public Map<String, Long> getSumsForPropertiesPages(List<String> pageIds, List<String> properties) throws IllegalArgumentException {
		return dao.getSumsForPropertiesPages(pageIds, properties);
	}
	
	@Override
	public List<FanPageGrowth> getFanPageGrowth(String pageId) {
		return dao.getFanPageGrowth(pageId);
	}
	
	@Override
	public Long getCountFansBeforeDate(String pageId, Long date) {
		return dao.getCountFansBeforeDate(pageId, date);
	}
	
	@Override
	public Map<String, Long> getAboutYourFansGender(String pageId) {
		return dao.getAboutYourFansGender(pageId);
	}
	
	@Override
	public AgeGroup getMajorAgeGroupFans(String pageId) {
		return dao.getMajorAgeGroupFans(pageId);
	}
	
	@Override
	public Integer[] getWomenMenFansPerc(String pageId) {
		return dao.getWomenMenFansPerc(pageId);
	}
	
	@Override
	public Map<String, Long> getAboutReachedUsers(String pageId) {
		return dao.getAboutReachedUsers(pageId);
	}
	
	@Override
	public AgeGroup getMajorAgeGroupReachedUsers(String pageId) {
		return dao.getMajorAgeGroupReachedUsers(pageId);
	}
	
	@Override
	public Integer[] getWomenMenReachedUsersPerc(String pageId) {
		return dao.getWomenMenReachedUsersPerc(pageId);
	}
	
	@Override
	public List<SumByDate> getSingleReach(String pageId) {
		return dao.getSingleReach(pageId);
	}

	@Override
	public SumByDate getMostReachedMonth(String pageId) {
		return dao.getMostReachedMonth(pageId);
	}
	
	@Override
	public List<FanPageGrowth> getFanPageGrowth(String pageId, Long dateFrom, Long dateUntil, Long accumulatedFanPageGrowth) {
		return dao.getFanPageGrowth(pageId, dateFrom, dateUntil, accumulatedFanPageGrowth);
	}
	
	@Override
	public List<FanPageGrowth> getLikesByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getLikesByDay(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<FanPageGrowth> getUnlikesByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getUnlikesByDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public ReportDetailed getTotalOfFans(String pageId) {
		return dao.getTotalOfFans(pageId);
	}
	
	@Override
	public Long getConqueredFans(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getConqueredFans(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getLostFans(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getLostFans(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Map<String, Long> getFansLikeSource(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getFansLikeSource(pageId, dateFrom, dateUntil);
	}

	@Override
	public Map<String, Long> getAboutReachedUsers(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAboutReachedUsers(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public AgeGroup getMajorAgeGroupReachedUsers(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getMajorAgeGroupReachedUsers(pageId, dateFrom, dateUntil);
	}

	@Override
	public Integer[] getWomenMenReachedUsersPerc(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getWomenMenReachedUsersPerc(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<ReportFacebookLocation> getOtherActivities(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getOtherActivities(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<FanPageGrowth> getFansOnlinePerHour(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getFansOnlinePerHour(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<FanPageGrowth> getAverageFansOnlinePerHour(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAverageFansOnlinePerHour(pageId, dateFrom, dateUntil);
	}	
	
	@Override
	public List<FanPageGrowth> getFansOnlinePerWeekDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getFansOnlinePerWeekDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Object[] getTimeWithMoreFansOnline(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getTimeWithMoreFansOnline(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Object[] getWeekDayWithMoreFansOnline(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getWeekDayWithMoreFansOnline(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public void setDao(AbstractDAO<ReportDetailed> dao) {
		this.dao = (ReportDetailedDAO) dao;
	}

	@Override
	public AbstractDAO<ReportDetailed> getDao() {
		return dao;
	}

	@Override
	public boolean deleteReportsDetailed(String pageId) {
		return dao.deleteReportsDetailed(pageId);
	}

	@Override
	public List<FanPageGrowth> getImpressionsByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getImpressionsByDay(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<FanPageGrowth> getReachByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getReachByDay(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getTotalReach(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getTotalReach(pageId, dateFrom, dateUntil);
	}

	@Override
	public Long getTotalImpressions(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getTotalImpressions(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<FanPageGrowth> getEngagementByDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getEngagementByDay(pageId, dateFrom, dateUntil); 
	}

	@Override
	public Long getTotalEngagement(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getTotalEngagement(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<FanPageGrowth> getPropertyByDay(String pageId, String property, Long dateFrom, Long dateUntil) {
		return dao.getPropertyByDay(pageId, property, dateFrom, dateUntil);
	}

	@Override
	public List<PropertyValue> getPropertiesFromGroup(String pageId, String fromGroup, Long dateFrom, Long dateUntil) {
		return dao.getPropertiesFromGroup(pageId, fromGroup, dateFrom, dateUntil);
	}

	@Override
	public Long getPropertyCount(String pageId, String property, Long dateFrom, Long dateUntil) {
		return dao.getPropertyCount(pageId, property, dateFrom, dateUntil);
	}

	@Override
	public FanPageGrowth getMajorTotalViewsDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getMajorTotalViewsDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<SumByDate> getPropertyByMonth(String pageId, String property) {
		return dao.getPropertyByMonth(pageId, property);
	}
	
	@Override
	public void setIdUserSession(Long idUserSession) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getIdUserSession() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
