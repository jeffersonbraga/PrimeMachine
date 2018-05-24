package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.FacebookInfluentialUserDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.report.FacebookInfluentialUser;

@Stateless
public class MaintenanceFacebookInfluentialUserBean extends AbstractDASImpl<FacebookInfluentialUser> implements MaintenanceFacebookInfluentialUserRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	FacebookInfluentialUserDAO dao;
	
	@Override
	public AbstractDAO getDAO() {
		return dao;
	}
	
	@Override
	public FacebookInfluentialUser save(FacebookInfluentialUser object) throws IllegalArgumentException, Exception {
		return dao.save(object);
	}
	
	@Override
	public List<FacebookInfluentialUser> getMostInfluentialFans(String pageId, Integer offset, Integer limit) {
		return dao.getMostInfluentialFans(pageId, offset, limit);
	}
	
	@Override
	public void setDao(AbstractDAO<FacebookInfluentialUser> dao) {
		this.dao = (FacebookInfluentialUserDAO) dao;
	}

	@Override
	public AbstractDAO<FacebookInfluentialUser> getDao() {
		return dao;
	}

	@Override
	public boolean deleteFacebookInfluentialUsers(String pageId) {
		return dao.deleteFacebookInfluentialUsers(pageId);
	}

	@Override
	public List<FacebookInfluentialUser> getInfluentialFansByPage(String pageId, Character isVerified, Integer limit) {
		return dao.getInfluentialFansByPage(pageId, isVerified, limit);
	}

	@Override
	public List<FacebookInfluentialUser> getInfUsersWithAppSocopedUserId(String pageId, Integer limit) {
		return dao.getInfUsersWithAppSocopedUserId(pageId, limit);
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
