package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.instagram.InstagramEngagedFollower;
import br.com.opsocial.ejb.entity.instagram.InstagramFollower;
import br.com.opsocial.ejb.entity.instagram.InstagramFollowerTrend;

@Local
public interface InstagramFollowerDAO extends AbstractDAO<InstagramFollower> { 
	
	public List<InstagramEngagedFollower> getMostEngagedFollowers(Long idInstagramUser, Long dateFrom, Long dateUntil);
	
	public List<InstagramFollowerTrend> getInstagramFollowersTrends(Long idInstagramUser, Long dateFrom, Long dateUntil);
	public InstagramFollowerTrend getInstagramFollowerTrend(Long idInstagramUser, Long dateFrom, Long dateUntil);
	
	public List<InstagramFollowerTrend> getInstagramFollowersCount(Long idInstagramUser);
	
}
