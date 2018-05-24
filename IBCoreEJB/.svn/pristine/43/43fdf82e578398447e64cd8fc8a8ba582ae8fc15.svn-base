package br.com.opsocial.ejb.dao;

import java.util.Date;
import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringUserReport;

@Local
public interface MonitoringUserReportDAO extends AbstractDAO<MonitoringUserReport> {

	public boolean hasEntity(Long idMonitoring, String property, Long date);
	public MonitoringUserReport getEntity(Long idMonitoring, String property, Long date);
	
	public Long getPostsUsersTotal(Long idMonitoring, Long dateFrom, Long dateUntil);
	public Map<Date, Long> getPostsUsersPerDayCount(Long idMonitoring, Long dateFrom, Long dateUntil);
	public Map<String, Long> getPostsUsersGenderCount(Long idMonitoring, Long dateFrom, Long dateUntil);
	
}
