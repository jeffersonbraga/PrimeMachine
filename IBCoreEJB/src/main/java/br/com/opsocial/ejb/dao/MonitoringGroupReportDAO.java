package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringGroupReport;

@Local
public interface MonitoringGroupReportDAO extends AbstractDAO<MonitoringGroupReport> {
	
	public boolean hasEntity(Long idMonitoring, Long idProfile, Long date);
	
	public MonitoringGroupReport getEntity(Long idMonitoring, Long idProfile, Long date);
	
	public Long getPostsTotalByGroup(Long idMonitoring, Long idProfile, Long dateFrom, Long dateUntil);
	
}
