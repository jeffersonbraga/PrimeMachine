package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ProxyRegionDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.ProxyRegion;

@Stateless
public class MaintenanceProxyRegionBean extends AbstractDASImpl<ProxyRegion> implements MaintenanceProxyRegionRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	ProxyRegionDAO dao;
	
	@Override
	public void setDao(AbstractDAO<ProxyRegion> dao) {
		this.dao = (ProxyRegionDAO) dao;
	}

	@Override
	public AbstractDAO<ProxyRegion> getDao() {
		return dao;
	}

	@Override
	public void setIdUserSession(Long idUserSession) {
		
	}

	@Override
	public Long getIdUserSession() {
		return null;
	}

	@Override
	public AbstractDAO<ProxyRegion> getDAO() {
		return dao;
	}

	@Override
	public ProxyRegion getProxyRegion(String country, Integer stickIpDuration) {
		return dao.getProxyRegion(country, stickIpDuration);
	}

}
