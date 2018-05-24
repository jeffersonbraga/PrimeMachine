package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.twitter.TwitterPaging;

@Stateless
public class TwitterPagingDAOImpl extends AbstractDAOImpl<TwitterPaging> implements TwitterPagingDAO {

	@Override
	public TwitterPaging getEntity(Long idMonitoring, String term) {
		
		sql = "SELECT tp FROM TwitterPaging tp " +
			  "WHERE tp.monitoring.idMonitoring = :idMonitoring AND tp.term = :term";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("term", term);
		
		try {
			return (TwitterPaging) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

}
