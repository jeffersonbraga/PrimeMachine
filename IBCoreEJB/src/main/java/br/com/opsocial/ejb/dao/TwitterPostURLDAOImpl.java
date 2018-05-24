package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.twitter.TwitterPostURL;

@Stateless
public class TwitterPostURLDAOImpl extends AbstractDAOImpl<TwitterPostURL> implements TwitterPostURLDAO {

	@Override
	public List<TwitterPostURL> listByStatusId(Long statusId) {

		sql = "SELECT tpu FROM TwitterPostURL tpu " +
				"WHERE tpu.twitterPost.statusId = :statusId";

		query = em.createQuery(sql);
		query.setParameter("statusId", statusId);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<TwitterPostURL>();
		}
	}

}
