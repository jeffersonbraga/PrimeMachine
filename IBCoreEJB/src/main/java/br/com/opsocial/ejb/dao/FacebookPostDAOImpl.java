package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.facebook.FacebookPost;

@Stateless
public class FacebookPostDAOImpl extends AbstractDAOImpl<FacebookPost> implements FacebookPostDAO {
	
	@Override
	public FacebookPost save(FacebookPost facebookPost) throws Exception {
		
		try {
			
			FacebookPost objTmp = getById(facebookPost.getPostId());
			
			if(objTmp != null) {
				
				em.merge(facebookPost);
				
				this.flush(facebookPost);

			} else {
				
				em.persist(facebookPost);
				
				this.flush(facebookPost);		
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return facebookPost;
	}
}
