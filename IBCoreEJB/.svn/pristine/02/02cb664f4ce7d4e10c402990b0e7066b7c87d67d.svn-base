package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTermReport;

@Local
public interface MonitoringTermReportDAO extends AbstractDAO<MonitoringTermReport> {

	public boolean hasEntity(Long idMonitoring, String term, Character networkType, Long date);
	
	public MonitoringTermReport getEntity(Long idMonitoring, String term, Character networkType, Long date);
	
	public Long getPostsTotalByTerm(Long idMonitoring, String term, Long dateFrom, Long dateUntil);
}
