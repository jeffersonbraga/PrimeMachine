package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.twitter.RateLimitStatus;

@Local
public interface RateLimitStatusDAO extends AbstractDAO<RateLimitStatus> {
	
	public RateLimitStatus getEntityByNetworkType(Long idProfile, String resource);
	
	public List<RateLimitStatus> listByProfile(Long idProfile);

}
