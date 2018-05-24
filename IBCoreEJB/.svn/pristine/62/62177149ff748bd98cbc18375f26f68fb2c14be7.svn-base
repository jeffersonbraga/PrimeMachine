package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.TwitterFollowerReport;

@Remote
public interface MaintenanceTwitterFollowerReportRemote extends AbstractDAS<TwitterFollowerReport> {

	public TwitterFollowerReport getEntity(Long idProfile, Long followerId, String property, Long date);
	public Long getFollowersEngagedCount(Long idProfile, Long dateFrom, Long dateUntil);
	public Long getFollowersEngagement(Long idProfile, Long dateFrom, Long dateUntil);
	
}
