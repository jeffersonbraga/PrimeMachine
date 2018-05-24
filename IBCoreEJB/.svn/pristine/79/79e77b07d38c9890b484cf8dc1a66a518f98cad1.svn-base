package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.BlogPostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.blogs.BlogPost;

@Stateless
public class MaintenanceBlogPostBean extends AbstractDASImpl<BlogPost> implements MaintenanceBlogPostRemote {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	BlogPostDAO dao;
	
	@Override
	public BlogPost save(BlogPost object) throws IllegalArgumentException, Exception {
		return dao.save(object);
	}
	
	@Override
	public void setDao(AbstractDAO<BlogPost> dao) {
		this.dao = (BlogPostDAO) dao;
	}

	@Override
	public AbstractDAO<BlogPost> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO getDAO() {
		return dao;
	}

	@Override
	public BlogPost getByUrl(String url) {
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
