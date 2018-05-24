package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.blogs.BlogPost;

@Local
public interface BlogPostDAO extends AbstractDAO<BlogPost> {
	
	public BlogPost getByUrl(String url);

}
