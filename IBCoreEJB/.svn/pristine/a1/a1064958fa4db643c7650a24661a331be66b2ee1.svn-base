package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.google.GooglePaging;

@Stateless
public class GooglePagingDAOImpl extends AbstractDAOImpl<GooglePaging> implements GooglePagingDAO {

	@Override
	public GooglePaging getEntity(Long idMonitoring, String term, Character network) {

		sql = "SELECT gp FROM GooglePaging gp " +
				"WHERE gp.monitoring.idMonitoring = :idMonitoring " +
				"AND gp.term = :term AND gp.network = :network";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
		query.setParameter("network", network);

		try {
			return (GooglePaging) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
