package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.report.TwitterFollowerReport;

@Local
public interface TwitterFollowerReportDAO extends AbstractDAO<TwitterFollowerReport> {

	public TwitterFollowerReport getEntity(Long idProfile, Long followerId, String property, Long date);
	public Long getFollowersEngagedCount(Long idProfile, Long dateFrom, Long dateUntil);
	public Long getFollowersEngagement(Long idProfile, Long dateFrom, Long dateUntil);
	
}
