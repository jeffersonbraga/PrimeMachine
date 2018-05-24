package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.twitter.TrendingTopic;

@Stateless
public class TrendingTopicDAOImpl extends AbstractDAOImpl<TrendingTopic> implements TrendingTopicDAO {

	@Override
	public List<TrendingTopic> getMostRecentTrendingTopics(Integer offset, Integer limit) {
		
		sql = "SELECT tt FROM TrendingTopic tt " +
				"ORDER BY tt.trendingDate DESC, tt.position ASC";

		query = em.createQuery(sql);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		return query.getResultList();
	}

}
