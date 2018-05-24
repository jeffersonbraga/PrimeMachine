package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.twitter.TrendingTopic;

@Remote
public interface MaintenanceTrendingTopicRemote extends AbstractDAS<TrendingTopic> {
	
	public List<TrendingTopic> getMostRecentTrendingTopics(Integer offset, Integer limit);

}
