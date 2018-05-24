package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.OptimisticLockException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.idclass.FacebookInfluentialUserId;
import br.com.opsocial.ejb.entity.report.FacebookInfluentialUser;

@Stateless
public class FacebookInfluentialUserDAOImpl extends AbstractDAOImpl<FacebookInfluentialUser> implements FacebookInfluentialUserDAO {
	
	@Override
	public FacebookInfluentialUser save(FacebookInfluentialUser facebookInfluentialUser) throws Exception {
		
		try {
			
			FacebookInfluentialUserId facebookInfluentialUserId = new FacebookInfluentialUserId();
			facebookInfluentialUserId.setPageId(facebookInfluentialUser.getPageId());
			facebookInfluentialUserId.setUserId(facebookInfluentialUser.getUserId());
			
			FacebookInfluentialUser objTmp = getById(facebookInfluentialUserId);
			
			if(objTmp != null) {
				
				try {
					validateVersion(objTmp, facebookInfluentialUser);
				} catch (IllegalStateException ex) {
					throw new OptimisticLockException();
				}
			
				em.merge(facebookInfluentialUser);
				
				this.flush();
				
			} else {
				
				em.persist(facebookInfluentialUser);
				
				this.flush();
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return facebookInfluentialUser;
	}

	@Override
	public List<FacebookInfluentialUser> getMostInfluentialFans(String pageId, Integer offset, Integer limit) {
		
		sql = "SELECT fiu.userid, fiu.pageid, fiu.name, fiu.comments, fiu.likes, fiu.version " +  
				"FROM opsocial.facebookinfluentialusers as fiu " +
				"WHERE fiu.pageId = '" + pageId + "' " +
				"ORDER BY fiu.comments + fiu.likes DESC, fiu.name ASC " +
				"OFFSET " + offset + " LIMIT " + limit;

		query = em.createNativeQuery(sql);

		List<Object[]> result = query.getResultList();
		
		List<FacebookInfluentialUser> facebookInfluentialUsers = new ArrayList<FacebookInfluentialUser>();
		
		for(Object[] object : result) {
			
			FacebookInfluentialUser facebookInfluentialUser = new FacebookInfluentialUser();
			facebookInfluentialUser.setUserId((String) object[0]);
			facebookInfluentialUser.setPageId((String) object[1]);
			facebookInfluentialUser.setName((String) object[2]);
			facebookInfluentialUser.setComments((Long) object[3]);
			facebookInfluentialUser.setLikes((Long) object[4]);
			facebookInfluentialUser.setVersion((Long) object[5]);

			facebookInfluentialUsers.add(facebookInfluentialUser);
		}
		
		return facebookInfluentialUsers;
	}

	@Override
	public boolean deleteFacebookInfluentialUsers(String pageId) {
		
		sql = "DELETE FROM FacebookInfluentialUser fiu WHERE fiu.pageId = :pageId";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);

		try {

			query.executeUpdate();
			return true;

		} catch (Exception e) {

			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<FacebookInfluentialUser> getInfluentialFansByPage(String pageId, Character isVerified, Integer limit) {
		
		sql = "SELECT fiu FROM FacebookInfluentialUser fiu WHERE fiu.pageId = :pageId and fiu.isVerified = :isVerified";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("isVerified", isVerified);
		query.setFirstResult(0);
		query.setMaxResults(limit);

		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<FacebookInfluentialUser>();
		}
	}

	@Override
	public List<FacebookInfluentialUser> getInfUsersWithAppSocopedUserId(String pageId, Integer limit) {
		
		sql = "SELECT fiu FROM FacebookInfluentialUser fiu WHERE fiu.pageId = :pageId " +
				"AND fiu.isVerified = :isVerified AND fiu.appScopedUserId IS NOT NULL";

		query = em.createQuery(sql);
		query.setParameter("pageId", pageId);
		query.setParameter("isVerified", 'T');
		query.setFirstResult(0);
		query.setMaxResults(limit);

		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<FacebookInfluentialUser>();
		}
	}

}
