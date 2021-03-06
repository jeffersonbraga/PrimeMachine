package br.com.opsocial.ejb.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringInfluentialUser;

@Local
public interface MonitoringInfluentialUserDAO extends AbstractDAO<MonitoringInfluentialUser> {

	public boolean hasEntity(String idUser, Long idMonitoring, Character networkType, Long date);
	public MonitoringInfluentialUser getEntity(String idUser, Long idMonitoring, Character networkType, Long date);
	public List<MonitoringInfluentialUser> getUsersWithMoreFollowers(Long idMonitoring, Character networkType, Long reportDate, Integer limit);
	
	public Map<String, Long> getMostInfluentialFans(Long idMonitoring, Character networkType, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	public MonitoringInfluentialUser getMostInfluentialFan(String idUser, Long idMonitoring, Character networkType, Long dateFrom, Long dateUntil);
	public Long getMostInfluentialFanPostsCount(String idUser, Long idMonitoring, Character networkType, Long dateFrom, Long dateUntil);
	
	public Map<String, Long> getMostPositiveFans(Long idMonitoring, Character networkType, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	public Map<String, Long> getMostNegativeFans(Long idMonitoring, Character networkType, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	
	public Map<Date, Long> getPostsUsersPerDayCount(Long idMonitoring, Long dateFrom, Long dateUntil);
	public Long getPostsUsersRecurrent(Long idMonitoring, Long dateFrom, Long dateUntil);
	public Map<String, Long> getPostsUsersGenderCount(Long idMonitoring, Long dateFrom, Long dateUntil);
	
	public Long getPostsUsersPerDayCount(Long idMonitoring, Long date);
	public Long getPostsUsersPerGenderCount(Long idMonitoring, Long date, String gender);
}
