package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;
import javax.persistence.OptimisticLockException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.report.TwitterFollowerControl;

@Stateless
public class TwitterFollowerControlDAOImpl extends AbstractDAOImpl<TwitterFollowerControl> implements TwitterFollowerControlDAO {

	@Override
	public TwitterFollowerControl save(TwitterFollowerControl twitterFollowerControl) throws Exception {

		try {

			TwitterFollowerControl objTmp = getById(twitterFollowerControl.getIdProfile());

			if(objTmp != null) {

				try {
					validateVersion(objTmp, twitterFollowerControl);
				} catch (IllegalStateException ex) {
					throw new OptimisticLockException();
				}

				twitterFollowerControl = em.merge(twitterFollowerControl);

				this.flush();

			} else {

				em.persist(twitterFollowerControl);

				this.flush();
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}

		return twitterFollowerControl;
	}

}
