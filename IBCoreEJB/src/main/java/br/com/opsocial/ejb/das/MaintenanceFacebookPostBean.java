package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.FacebookPostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.facebook.FacebookPost;

@Stateless
public class MaintenanceFacebookPostBean extends AbstractDASImpl<FacebookPost> implements MaintenanceFacebookPostRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	FacebookPostDAO dao;

	@Override
	public FacebookPostDAO getDAO() {
		return dao;
	}
	
	@Override
	public FacebookPost save(FacebookPost facebookPost) throws IllegalArgumentException, Exception {
		return dao.save(facebookPost);
	}

	@Override
	public void setDao(AbstractDAO<FacebookPost> dao) {
		this.dao = (FacebookPostDAO) dao;
	}

	@Override
	public AbstractDAO<FacebookPost> getDao() {
		return dao;
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
