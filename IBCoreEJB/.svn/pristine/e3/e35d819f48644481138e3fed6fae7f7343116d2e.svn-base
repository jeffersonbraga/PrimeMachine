package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.TwitterPostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.twitter.TwitterPost;

@Stateless
public class MaintenanceTwitterPostBean extends AbstractDASImpl<TwitterPost> implements MaintenanceTwitterPostRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	TwitterPostDAO dao;

	@Override
	public TwitterPostDAO getDAO() {
		return dao;
	}
	
	@Override
	public TwitterPost save(TwitterPost object) throws IllegalArgumentException, Exception {
		return dao.save(object);
	}

	@Override
	public void setDao(AbstractDAO<TwitterPost> dao) {
		this.dao = (TwitterPostDAO) dao;
	}

	@Override
	public AbstractDAO<TwitterPost> getDao() {
		return dao;
	}

	@Override
	public List<TwitterPost> getTwitterPostsForUpdate(Long idAccount) {
		return dao.getTwitterPostsForUpdate(idAccount);
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
