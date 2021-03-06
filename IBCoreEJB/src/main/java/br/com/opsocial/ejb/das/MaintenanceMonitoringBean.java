package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Stateless
public class MaintenanceMonitoringBean extends AbstractDASImpl<Monitoring> implements MaintenanceMonitoringRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	MonitoringDAO dao;
	
	@Override
	public AbstractDAO<Monitoring> getDAO() {
		return dao;
	}

	@Override
	public Boolean hasMonitoring(Long idAccount, String name, Character type) {
		return dao.hasMonitoring(idAccount, name, type);
	}

	@Override
	public void setDao(AbstractDAO<Monitoring> dao) {
		this.dao = (MonitoringDAO) dao;
	}

	@Override
	public AbstractDAO<Monitoring> getDao() {
		return dao;
	}

	@Override
	public List<Monitoring> getMonitoringsByAccount(Long idAccount) {
		return dao.getMonitoringsByAccount(idAccount);
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
