package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.twitter.TrendingTopic;

@Local
public interface TrendingTopicDAO extends AbstractDAO<TrendingTopic> {
	
	public List<TrendingTopic> getMostRecentTrendingTopics(Integer offset, Integer limit);

}
