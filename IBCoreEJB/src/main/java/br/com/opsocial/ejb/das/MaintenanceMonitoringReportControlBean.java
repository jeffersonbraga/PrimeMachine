package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringReportControlDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReportControl;

@Stateless
public class MaintenanceMonitoringReportControlBean extends AbstractDASImpl<MonitoringReportControl> implements MaintenanceMonitoringReportControlRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	MonitoringReportControlDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringReportControl> dao) {
		this.dao = (MonitoringReportControlDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringReportControl> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<MonitoringReportControl> getDAO() {
		return dao;
	}

	@Override
	public boolean hasEntity(Long idMonitoring, String type, Long date) {
		return dao.hasEntity(idMonitoring, type, date);
	}

	@Override
	public MonitoringReportControl getEntity(Long idMonitoring, String type, Long date) {
		return dao.getEntity(idMonitoring, type, date);
	}

	@Override
	public List<Long> getDates(Long idMonitoring, String type) {
		return dao.getDates(idMonitoring, type);
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
