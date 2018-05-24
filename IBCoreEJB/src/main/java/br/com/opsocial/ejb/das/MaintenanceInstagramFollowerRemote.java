package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.instagram.InstagramEngagedFollower;
import br.com.opsocial.ejb.entity.instagram.InstagramFollower;
import br.com.opsocial.ejb.entity.instagram.InstagramFollowerTrend;

@Remote
public interface MaintenanceInstagramFollowerRemote extends AbstractDAS<InstagramFollower> {

	public List<InstagramEngagedFollower> getMostEngagedFollowers(Long idInstagramUser, Long dateFrom, Long dateUntil);
	
	public List<InstagramFollowerTrend> getInstagramFollowersTrends(Long idInstagramUser, Long dateFrom, Long dateUntil);
	public InstagramFollowerTrend getInstagramFollowerTrend(Long idInstagramUser, Long dateFrom, Long dateUntil);
	
	public List<InstagramFollowerTrend> getInstagramFollowersCount(Long idInstagramUser);
}
