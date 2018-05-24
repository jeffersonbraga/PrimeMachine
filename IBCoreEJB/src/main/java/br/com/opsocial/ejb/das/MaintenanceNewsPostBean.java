package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.NewsPostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.news.NewsPost;

@Stateless
public class MaintenanceNewsPostBean extends AbstractDASImpl<NewsPost> implements MaintenanceNewsPostRemote {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	NewsPostDAO dao;
	
	@Override
	public NewsPost save(NewsPost object) throws IllegalArgumentException, Exception {
		return dao.save(object);
	}
	
	@Override
	public void setDao(AbstractDAO<NewsPost> dao) {
		this.dao = (NewsPostDAO) dao;
	}

	@Override
	public AbstractDAO<NewsPost> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO getDAO() {
		return dao;
	}

	@Override
	public NewsPost getByUrl(String url) {
		return dao.getByUrl(url);
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
