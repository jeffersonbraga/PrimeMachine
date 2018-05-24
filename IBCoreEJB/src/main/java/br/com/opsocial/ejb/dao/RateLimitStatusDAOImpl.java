package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.twitter.RateLimitStatus;

@Stateless
public class RateLimitStatusDAOImpl extends AbstractDAOImpl<RateLimitStatus> implements RateLimitStatusDAO {

	@Override
	public RateLimitStatus getEntityByNetworkType(Long idProfile, String resource) {
		
		if(idProfile == null) {
			
			sql = "SELECT rls From RateLimitStatus rls WHERE rls.profile IS NULL AND rls.resource = :resource";
			
			query = em.createQuery(sql);
			query.setParameter("resource", resource);
			
		} else {
			sql = "SELECT rls From RateLimitStatus rls WHERE rls.profile.idProfile = :idProfile AND rls.resource = :resource";
			
			query = em.createQuery(sql);
			query.setParameter("idProfile", idProfile);
			query.setParameter("resource", resource);
		}
		
		try {
			return (RateLimitStatus) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<RateLimitStatus> listByProfile(Long idProfile) {
		
		sql = "SELECT rls From RateLimitStatus rls WHERE rls.profile.idProfile = :idProfile";
		
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		
		return query.getResultList();
	}

}
