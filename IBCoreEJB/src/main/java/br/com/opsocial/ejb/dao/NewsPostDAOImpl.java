package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.news.NewsPost;

@Stateless
public class NewsPostDAOImpl extends AbstractDAOImpl<NewsPost> implements NewsPostDAO {
	
	@Override
	public NewsPost getByUrl(String url) {
		
		sql = "SELECT n FROM NewsPost n WHERE n.url = :url";
		
		query = em.createQuery(sql);
		query.setParameter("url", url);
		
		try {
			return (NewsPost) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
}
