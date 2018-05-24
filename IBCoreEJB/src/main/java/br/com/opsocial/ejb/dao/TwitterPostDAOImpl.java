package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.twitter.TwitterPost;

@Stateless
public class TwitterPostDAOImpl extends AbstractDAOImpl<TwitterPost> implements TwitterPostDAO {

	@Override
	public TwitterPost save(TwitterPost twitterPost) throws Exception {
		
		try {
			
			TwitterPost objTmp = getById(twitterPost.getStatusId());
			
			if(objTmp != null) {
				
				try {
					validateVersion(objTmp, twitterPost);
				} catch (IllegalStateException ex) {
					throw new OptimisticLockException();
				}
			
				em.merge(twitterPost);

				this.flush(twitterPost);
			
			} else {
				
				em.persist(twitterPost);
				
				this.flush(twitterPost);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return twitterPost;
	}

	@Override
	public List<TwitterPost> getTwitterPostsForUpdate(Long idAccount) {
		
		sql = "SELECT DISTINCT tp FROM TwitterPost tp " +
					"INNER JOIN tp.twitterPostsMonitoring tpm " +
					"INNER JOIN tpm.monitoring AS m " +  
					"INNER JOIN m.account AS a " +
					"WHERE a.idAccount = :idAccount AND tp.state = 'A' " + 
					"ORDER BY tp.createdAt DESC";
		
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		query.setFirstResult(0);
		query.setMaxResults(180);
		
		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<TwitterPost>();
		}
	}
}
