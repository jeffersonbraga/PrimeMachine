package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.application.ProxyRegion;

@Local
public interface ProxyRegionDAO extends AbstractDAO<ProxyRegion> {
	
	public ProxyRegion getProxyRegion(String country, Integer stickIpDuration);

}
