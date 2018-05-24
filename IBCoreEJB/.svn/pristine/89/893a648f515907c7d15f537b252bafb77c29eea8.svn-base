package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.LinkedinPostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.linkedin.LinkedinPost;
import br.com.opsocial.ejb.entity.report.SumByDate;

@Stateless
public class MaintenanceLinkedinPostBean extends AbstractDASImpl<LinkedinPost> implements MaintenanceLinkedinPostRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	LinkedinPostDAO dao;

	@Override
	public void setDao(AbstractDAO<LinkedinPost> dao) {
		this.dao = (LinkedinPostDAO) dao;
	}

	@Override
	public AbstractDAO<LinkedinPost> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<LinkedinPost> getDAO() {
		return dao;
	}

	public MaintenanceLinkedinPostBean() {}

	@Override
	public Integer countLinkedinPosts(String networkId) {
		return dao.countLinkedinPosts(networkId);
	}

	@Override
	public List<LinkedinPost> getLinkedinPosts(String networkId) {
		return dao.getLinkedinPosts(networkId);
	}

	@Override
	public List<LinkedinPost> getLinkedinPostsOnPeriod(String networkId, Long dateFrom, Long dateUntil) {
		return dao.getLinkedinPostsOnPeriod(networkId, dateFrom, dateUntil);
	}

	@Override
	public List<SumByDate> getLinkedinPostsCountByDay(String networkId, Long dateFrom, Long dateUntil) {
		return dao.getLinkedinPostsCountByDay(networkId, dateFrom, dateUntil);
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
