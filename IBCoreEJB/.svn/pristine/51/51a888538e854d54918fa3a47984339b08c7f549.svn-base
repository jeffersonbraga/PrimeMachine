package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.InstagramFollowerDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramEngagedFollower;
import br.com.opsocial.ejb.entity.instagram.InstagramFollower;
import br.com.opsocial.ejb.entity.instagram.InstagramFollowerTrend;

@Stateless
public class MaintenanceInstagramFollowerBean extends AbstractDASImpl<InstagramFollower> implements MaintenanceInstagramFollowerRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB 
	InstagramFollowerDAO dao;
	
	@Override
	public void setDao(AbstractDAO<InstagramFollower> dao) {
		this.dao = (InstagramFollowerDAO) dao;
	}

	@Override
	public AbstractDAO<InstagramFollower> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<InstagramFollower> getDAO() {
		return dao;
	}
	
	@Override
	public InstagramFollower save(InstagramFollower object) throws IllegalArgumentException, Exception {
		return dao.save(object);
	}

	@Override
	public List<InstagramEngagedFollower> getMostEngagedFollowers(Long idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getMostEngagedFollowers(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramFollowerTrend> getInstagramFollowersTrends(Long idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getInstagramFollowersTrends(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public InstagramFollowerTrend getInstagramFollowerTrend(Long idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getInstagramFollowerTrend(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramFollowerTrend> getInstagramFollowersCount(Long idInstagramUser) {
		return dao.getInstagramFollowersCount(idInstagramUser);
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
