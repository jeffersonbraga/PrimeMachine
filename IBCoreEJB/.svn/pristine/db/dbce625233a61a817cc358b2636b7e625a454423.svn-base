package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;
import javax.persistence.OptimisticLockException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramUser;

@Stateless
public class InstagramUserDAOImpl extends AbstractDAOImpl<InstagramUser> implements InstagramUserDAO {

	@Override
	public InstagramUser save(InstagramUser instagramUser) throws Exception {

		try {
			
			InstagramUser objTmp = getById(instagramUser.getIdInstagramUser());

			if(objTmp != null) {

				try {
					validateVersion(objTmp, instagramUser);
				} catch (IllegalStateException ex) {
					throw new OptimisticLockException();
				}

				em.merge(instagramUser);

				this.flush(instagramUser);

			} else {

				em.persist(instagramUser);

				this.flush(instagramUser);
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}

		return instagramUser;
	}

}
