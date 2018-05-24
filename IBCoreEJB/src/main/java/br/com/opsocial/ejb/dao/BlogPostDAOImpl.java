package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.blogs.BlogPost;

@Stateless
public class BlogPostDAOImpl extends AbstractDAOImpl<BlogPost> implements BlogPostDAO {
	
	@Override
	public BlogPost getByUrl(String url) {
		
		sql = "SELECT b FROM BlogPost b WHERE b.url = :url";
		
		query = em.createQuery(sql);
		query.setParameter("url", url);
		
		try {
			return (BlogPost) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
}
