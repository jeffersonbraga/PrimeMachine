package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.TwitterFollower;

@Remote
public interface MaintenanceTwitterFollowerRemote extends AbstractDAS<TwitterFollower> {
	
	public long[] getFollowersNotUpdated(Long idProfile, Long updateDate);
	public boolean deactivateFollowersTemporarily(Long idProfile);
	public boolean deleteUnfollowers(Long idProfile);
	
	public Map<String, Long> getFollowersOfFollowers(Long idProfile);
	public Integer[] getWomenMenFollowersPerc(Long idProfile);
	public Long getAverageFollowersOfFollowers(Long idProfile);
	public Long getMedianFollowersOfFollowers(Long idProfile);
	
	public Map<String, Long> getFollowersActivity(Long idProfile);
	
	public Map<String, Long> getCitiesWithMoreFollowers(Long idProfile, Integer offset, Integer limit);
	public List<TwitterFollower> getMostInfluentialFollowers(Long idProfile, Integer offset, Integer limit);
	public List<TwitterFollower> getMostRecentFollowers(Long idProfile, Integer offset, Integer limit);
}
