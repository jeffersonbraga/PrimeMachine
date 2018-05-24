package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;
import javax.persistence.OptimisticLockException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramPost;

@Stateless
public class InstagramPostDAOImpl extends AbstractDAOImpl<InstagramPost> implements InstagramPostDAO {
	
	@Override
	public InstagramPost getByIdMedia(Long idMedia) {
		
		sql = "Select i From InstagramPost i where i.idMedia = :idMedia ";
		query = em.createQuery(sql);
		query.setParameter("idMedia", idMedia);
		
		try {
			return (InstagramPost) query.getSingleResult();	
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public InstagramPost save(InstagramPost instagramPost) throws Exception {
		
		try {
			
			InstagramPost objTmp = getById(instagramPost.getIdMedia());
			
			if(objTmp != null) {
				
				try {
					validateVersion(objTmp, instagramPost);
				} catch (IllegalStateException ex) {
					throw new OptimisticLockException();
				}
			
				em.merge(instagramPost);
				
				this.flush(instagramPost);

			} else {
				
				em.persist(instagramPost);
				
				this.flush(instagramPost);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return instagramPost;
	}
	
}
