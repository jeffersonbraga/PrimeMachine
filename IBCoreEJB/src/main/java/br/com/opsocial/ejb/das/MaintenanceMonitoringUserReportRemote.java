package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringUserReport;

@Remote
public interface MaintenanceMonitoringUserReportRemote extends AbstractDAS<MonitoringUserReport> {

	public boolean hasEntity(Long idMonitoring, String property, Long date);
	public MonitoringUserReport getEntity(Long idMonitoring, String property, Long date);
	
	public Long getPostsUsersTotal(Long idMonitoring, Long dateFrom, Long dateUntil);
	public Map<Date, Long> getPostsUsersPerDayCount(Long idMonitoring, Long dateFrom, Long dateUntil);
	public Map<String, Long> getPostsUsersGenderCount(Long idMonitoring, Long dateFrom, Long dateUntil);
	
}
