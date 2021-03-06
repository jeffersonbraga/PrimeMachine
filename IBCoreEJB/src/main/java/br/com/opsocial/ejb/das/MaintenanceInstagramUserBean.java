package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.InstagramUserDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramUser;

@Stateless
public class MaintenanceInstagramUserBean extends AbstractDASImpl<InstagramUser> implements MaintenanceInstagramUserRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	InstagramUserDAO dao;
	
	@Override
	public InstagramUserDAO getDAO() {
		return dao;
	}
	
	@Override
	public void setDao(AbstractDAO<InstagramUser> dao) {
		this.dao = (InstagramUserDAO) dao;
	}

	@Override
	public AbstractDAO<InstagramUser> getDao() {
		return dao;
	}

	@Override
	public InstagramUser save(InstagramUser object) throws IllegalArgumentException, Exception {
		return super.save(object);
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
