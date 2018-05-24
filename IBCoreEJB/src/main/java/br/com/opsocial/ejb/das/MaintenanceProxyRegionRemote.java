package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.ProxyRegion;

@Remote
public interface MaintenanceProxyRegionRemote extends AbstractDAS<ProxyRegion> {

	public ProxyRegion getProxyRegion(String country, Integer stickIpDuration);
	
}
