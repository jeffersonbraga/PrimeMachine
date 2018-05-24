package br.com.opsocial.ejb.das;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringLinkDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLink;

@Stateless
public class MaintenanceMonitoringLinkRemoteBean extends AbstractDASImpl<MonitoringLink> implements MaintenanceMonitoringLinkRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	MonitoringLinkDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringLink> dao) {
		this.dao = (MonitoringLinkDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringLink> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<MonitoringLink> getDAO() {
		return dao;
	}

	@Override
	public boolean hasEntity(String link, Long idMonitoring, Character networkType, Long date) {
		return dao.hasEntity(link, idMonitoring, networkType, date);
	}

	@Override
	public MonitoringLink getEntity(String link, Long idMonitoring, Character networkType, Long date) {
		return dao.getEntity(link, idMonitoring, networkType, date);
	}

	@Override
	public Map<String, Long> getLinksCount(Long idMonitoring, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		return dao.getLinksCount(idMonitoring, dateFrom, dateUntil, offset, limit);
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
