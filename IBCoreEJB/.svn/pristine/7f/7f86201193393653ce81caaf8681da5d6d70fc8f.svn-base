package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.TwitterFollowerDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.report.TwitterFollower;

@Stateless
public class MaintenanceTwitterFollowerBean extends AbstractDASImpl<TwitterFollower> implements MaintenanceTwitterFollowerRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	TwitterFollowerDAO dao;

	@Override
	public TwitterFollower save(TwitterFollower object) throws IllegalArgumentException, Exception {
		return dao.save(object);
	}
	
	@Override
	public void delete(TwitterFollower object) throws IllegalArgumentException, IllegalStateException, Exception {
		dao.delete(object);
	}

	@Override
	public long[] getFollowersNotUpdated(Long idProfile, Long updateDate) {
		return dao.getFollowersNotUpdated(idProfile, updateDate);
	}
	
	@Override
	public boolean deactivateFollowersTemporarily(Long idProfile) {
		return dao.deactivateFollowersTemporarily(idProfile);
	}
	
	@Override
	public boolean deleteUnfollowers(Long idProfile) {
		return dao.deleteUnfollowers(idProfile);
	}
	
	@Override
	public Map<String, Long> getFollowersOfFollowers(Long idProfile) {
		return dao.getFollowersOfFollowers(idProfile);
	}
	
	@Override
	public void setDao(AbstractDAO<TwitterFollower> dao) {
		this.dao = (TwitterFollowerDAO) dao;
	}
	
	@Override
	public Integer[] getWomenMenFollowersPerc(Long idProfile) {
		return dao.getWomenMenFollowersPerc(idProfile);
	}
	
	@Override
	public Long getAverageFollowersOfFollowers(Long idProfile) {
		return dao.getAverageFollowersOfFollowers(idProfile);
	}
	
	@Override
	public Long getMedianFollowersOfFollowers(Long idProfile) {
		return dao.getMedianFollowersOfFollowers(idProfile);
	}
	
	@Override
	public Map<String, Long> getFollowersActivity(Long idProfile) {
		return dao.getFollowersActivity(idProfile);
	}
	
	@Override
	public Map<String, Long> getCitiesWithMoreFollowers(Long idProfile, Integer offset, Integer limit) {
		return dao.getCitiesWithMoreFollowers(idProfile, offset, limit);
	}
	
	@Override
	public List<TwitterFollower> getMostInfluentialFollowers(Long idProfile, Integer offset, Integer limit) {
		return dao.getMostInfluentialFollowers(idProfile, offset, limit);
	}
	
	@Override
	public List<TwitterFollower> getMostRecentFollowers(Long idProfile, Integer offset, Integer limit) {
		return dao.getMostRecentFollowers(idProfile, offset, limit);
	}

	@Override
	public AbstractDAO<TwitterFollower> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO getDAO() {
		return dao;
	}

	@Override
	public void setIdUserSession(Long idUserSession) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getIdUserSession() {
		// TODO Auto-generated method stub
		return null;
	}
}
