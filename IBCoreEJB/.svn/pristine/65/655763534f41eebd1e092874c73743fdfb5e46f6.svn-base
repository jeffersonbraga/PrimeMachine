package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.ProxyRegion;

@Stateless
public class ProxyRegionDAOImpl extends AbstractDAOImpl<ProxyRegion> implements ProxyRegionDAO {

	@Override
	public ProxyRegion getProxyRegion(String country, Integer stickIpDuration) {
	
		sql = "SELECT pr FROM ProxyRegion pr WHERE pr.country = :country AND pr.stickIpDuration = :stickIpDuration ORDER BY pr.serverUseCount ASC";	
		
		query = em.createQuery(sql);
		query.setParameter("country", country);
		query.setParameter("stickIpDuration", stickIpDuration);
		query.setFirstResult(0);
		query.setMaxResults(1);
		
		try {
			return (ProxyRegion) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
