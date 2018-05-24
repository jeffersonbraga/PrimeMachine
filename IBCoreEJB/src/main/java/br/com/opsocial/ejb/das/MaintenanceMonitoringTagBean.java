package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringTagDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTag;

@Stateless
public class MaintenanceMonitoringTagBean extends AbstractDASImpl<MonitoringTag> implements MaintenanceMonitoringTagRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	MonitoringTagDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringTag> dao) {
		this.dao = (MonitoringTagDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringTag> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<MonitoringTag> getDAO() {
		return dao;
	}

	@Override
	public List<MonitoringTag> listByMonitoring(Long idMonitoring, Long idAccount) {
		return dao.listByMonitoring(idMonitoring, idAccount);
	}

	@Override
	public List<MonitoringTag> listGlobalTags(Long idAccount) {
		return dao.listGlobalTags(idAccount);
	}

	@Override
	public List<MonitoringTag> listForMonitoring(Long idMonitoring, Long idAccount) {
		return dao.listForMonitoring(idMonitoring, idAccount);
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
