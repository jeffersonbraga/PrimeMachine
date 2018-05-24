package br.com.opsocial.ejb.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.report.TwitterFollower;

@Local
public interface TwitterFollowerDAO extends AbstractDAO<TwitterFollower> {
	
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
