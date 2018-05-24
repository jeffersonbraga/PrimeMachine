package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.FaceReportPostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.report.FaceReportPost;
import br.com.opsocial.ejb.entity.report.PostEngagementPerc;
import br.com.opsocial.ejb.entity.report.PostEngagementStats;
import br.com.opsocial.ejb.entity.report.SumByDate;

@Stateless
public class MaintenanceFaceReportPostBean extends AbstractDASImpl<FaceReportPost> implements MaintenanceFaceReportPostRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	FaceReportPostDAO dao;

	@Override
	public AbstractDAO<FaceReportPost> getDAO() {
		return dao;
	}

	@Override
	public List<FaceReportPost> getEntitiesByPageId(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getEntitiesByPageId(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<FaceReportPost> getPostsMostEngaged(String pageId, Integer offset, Integer limit) {
		return dao.getPostsMostEngaged(pageId, offset, limit);
	}
	
	@Override
	public List<PostEngagementStats> getPostsEngagementStats(String pageId) {
		return dao.getPostsEngagementStats(pageId);
	}
	
	@Override
	public PostEngagementPerc getPostsEngagementPercs(String pageId) {
		return dao.getPostsEngagementPercs(pageId);
	}
	
	@Override
	public SumByDate getMostEngagementMonth(String pageId) {
		return dao.getMostEngagementMonth(pageId);
	}
	
	@Override
	public List<PostEngagementStats> getPostsEngagementStats(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsEngagementStats(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Map<String, Long> getPostsEngagementTotalStats(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getPostsEngagementTotalStats(pageId, dateFrom, dateUntil);
	}

	@Override
	public List<FaceReportPost> getPostsMostEngaged(String pageId, Long dateFrom, Long dateUntil, Integer offset, Integer limit) {
		return dao.getPostsMostEngaged(pageId, dateFrom, dateUntil, offset, limit);
	}
	
	@Override
	public List<SumByDate> getNumberOfPosts(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getNumberOfPosts(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Long getAveragePostsPerDay(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getAveragePostsPerDay(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public Map<String, Long> getNumberOfPostsPerType(String pageId, Long dateFrom, Long dateUntil) {
		return dao.getNumberOfPostsPerType(pageId, dateFrom, dateUntil);
	}
	
	@Override
	public List<FaceReportPost> getTopPostsMostEngaged(String pageId, Long dateFrom, Long dateUntil, Integer limit) {
		return dao.getTopPostsMostEngaged(pageId, dateFrom, dateUntil, limit);
	}
	
	@Override
	public void setDao(AbstractDAO<FaceReportPost> dao) {
		this.dao = (FaceReportPostDAO) dao;	
	}

	@Override
	public AbstractDAO<FaceReportPost> getDao() {
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

	@Override
	public List<FaceReportPost> getPostsByDate(String pageId, Long dateFrom, Long dateUntil, String order, Integer offset, Integer limit) {
		return dao.getPostsByDate(pageId, dateFrom, dateUntil, order, offset, limit);
	}
}
