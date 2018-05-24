package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.twitter.RateLimitStatus;

@Remote
public interface MaintenanceRateLimitStatusRemote extends AbstractDAS<RateLimitStatus> {

	public RateLimitStatus getEntityByNetworkType(Long idProfile, String resource);
	
	public List<RateLimitStatus> listByProfile(Long idProfile);
	
}
