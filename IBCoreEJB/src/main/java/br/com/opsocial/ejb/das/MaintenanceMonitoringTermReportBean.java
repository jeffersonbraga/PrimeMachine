package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringTermReportDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTermReport;

@Stateless
public class MaintenanceMonitoringTermReportBean extends AbstractDASImpl<MonitoringTermReport> implements MaintenanceMonitoringTermReportRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	MonitoringTermReportDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringTermReport> dao) {
		this.dao = (MonitoringTermReportDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringTermReport> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<MonitoringTermReport> getDAO() {
		return dao;
	}

	@Override
	public boolean hasEntity(Long idMonitoring, String term, Character networkType, Long date) {
		return dao.hasEntity(idMonitoring, term, networkType, date);
	}

	@Override
	public MonitoringTermReport getEntity(Long idMonitoring, String term, Character networkType, Long date) {
		return dao.getEntity(idMonitoring, term, networkType, date);
	}

	@Override
	public Long getPostsTotalByTerm(Long idMonitoring, String term,Long dateFrom, Long dateUntil) {
		return dao.getPostsTotalByTerm(idMonitoring, term, dateFrom, dateUntil);
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
