package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.RateLimitStatusDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.twitter.RateLimitStatus;

@Stateless
public class MaintenanceRateLimitStatusBean extends AbstractDASImpl<RateLimitStatus> implements MaintenanceRateLimitStatusRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	RateLimitStatusDAO dao;

	@Override
	public RateLimitStatusDAO getDAO() {
		return dao;
	}

	@Override
	public RateLimitStatus getEntityByNetworkType(Long idProfile, String resource) {
		return dao.getEntityByNetworkType(idProfile, resource);
	}

	@Override
	public void setDao(AbstractDAO<RateLimitStatus> dao) {
		this.dao = (RateLimitStatusDAO) dao;
	}

	@Override
	public AbstractDAO<RateLimitStatus> getDao() {
		return dao;
	}

	@Override
	public List<RateLimitStatus> listByProfile(Long idProfile) {
		return dao.listByProfile(idProfile);
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
