package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.google.GooglePlusPost;

@Stateless
public class GooglePlusPostDAOImpl extends AbstractDAOImpl<GooglePlusPost> implements GooglePlusPostDAO {

	@Override
	public GooglePlusPost save(GooglePlusPost googlePlusPost) throws Exception {
		
		try {
				
			GooglePlusPost objTmp = getById(googlePlusPost.getActivityId());
			
			if(objTmp != null) {
			
				em.merge(googlePlusPost);
				
				this.flush(googlePlusPost);
			
			} else {
				
				em.persist(googlePlusPost);
				
				this.flush(googlePlusPost);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return googlePlusPost;
	}
}
