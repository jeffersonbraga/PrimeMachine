package br.com.opsocial.ejb.das;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.InstagramReportPostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramReportPost;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;
import br.com.opsocial.ejb.entity.report.FilterLikesComments;

@Stateless
public class MaintenanceInstagramReportPostBean extends AbstractDASImpl<InstagramReportPost> implements MaintenanceInstagramReportPostRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	InstagramReportPostDAO dao; 
	
	@Override
	public void setDao(AbstractDAO<InstagramReportPost> dao) {
		this.dao = (InstagramReportPostDAO) dao;
	}

	@Override
	public AbstractDAO<InstagramReportPost> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<InstagramReportPost> getDAO() {
		return dao;
	}
	
	@Override
	public InstagramReportPost save(InstagramReportPost object) throws IllegalArgumentException, Exception {
		return super.save(object);
	}

	@Override
	public Long getLastIdMedia(String idInstagramUser) {
		return dao.getLastIdMedia(idInstagramUser);
	}

	@Override
	public Long getCountOfPosts(String idInstagramUser) {
		return dao.getCountOfPosts(idInstagramUser);
	}

	@Override
	public Long getCountOfLikes(String idInstagramUser) {
		return dao.getCountOfLikes(idInstagramUser);
	}

	@Override
	public Long getCountOfComments(String idInstagramUser) {
		return dao.getCountOfComments(idInstagramUser);
	}

	@Override
	public Object[] getFilterMostUsed(String idInstagramUser) {
		return dao.getFilterMostUsed(idInstagramUser);
	}

	@Override
	public Object[] getFilterLessUsed(String idInstagramUser) {
		return dao.getFilterLessUsed(idInstagramUser);
	}

	@Override
	public Map<Integer, Long> getPostsPerMonthYear(String idInstagramUser, Integer year) {
		return dao.getPostsPerMonthYear(idInstagramUser, year);
	}

	@Override
	public Long getCountOfPosts(String idInstagramUser, Integer year) {
		return dao.getCountOfPosts(idInstagramUser, year);
	}

	@Override
	public Object[] getMonthWithMorePosts(String idInstagramUser, Integer year) {
		return dao.getMonthWithMorePosts(idInstagramUser, year);
	}

	@Override
	public Map<Integer, Long> getPostsPerWeek(String idInstagramUser) {
		return dao.getPostsPerWeek(idInstagramUser);
	}

	@Override
	public Object[] getWeekDayWithMorePosts(String idInstagramUser) {
		return dao.getWeekDayWithMorePosts(idInstagramUser);
	}

	@Override
	public Map<Integer, Long> getPostsPerHour(String idInstagramUser) {
		return dao.getPostsPerHour(idInstagramUser);
	}

	@Override
	public Object[] getHourWithMorePosts(String idInstagramUser) {
		return dao.getHourWithMorePosts(idInstagramUser);
	}

	@Override
	public List<InstagramReportPost> getPostsWithMoreLikes(String idInstagramUser) {
		return dao.getPostsWithMoreLikes(idInstagramUser);
	}

	@Override
	public List<InstagramReportPost> getPostsWithMoreComments(String idInstagramUser) {
		return dao.getPostsWithMoreComments(idInstagramUser);
	}

	@Override
	public Object[] getFilterWithMoreLikes(String idInstagramUser) {
		return dao.getFilterWithMoreLikes(idInstagramUser);
	}

	@Override
	public Object[] getFilterWithMoreComments(String idInstagramUser) {
		return dao.getFilterWithMoreComments(idInstagramUser);
	}

	@Override
	public List<FilterLikesComments> getFilterLikesComments(String idInstagramUser) {
		return dao.getFilterLikesComments(idInstagramUser);
	}

	@Override
	public List<InstagramReportPost> getPostsWithMoreLikes(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getPostsWithMoreLikes(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramReportPost> getPostsWithMoreComments(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getPostsWithMoreComments(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramReportPost> getPostsWithMoreInteractions(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getPostsWithMoreInteractions(idInstagramUser, dateFrom, dateUntil);
	}
	
	@Override
	public Map<String, Long> getFiltersCounts(String idInstagramUser) {
		return dao.getFiltersCounts(idInstagramUser);
	}

	@Override
	public Map<String, Long> getInteractionsPerDay(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getInteractionsPerDay(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramSimpleDataDTO> getPostsPerMonthYearSimpleData(String valueOf, int i) {
		return dao.getPostsPerMonthYearSimpleData(valueOf, i);
	}

	@Override
	public List<InstagramSimpleDataDTO> getPostsPerHourSimpleData(String valueOf) {
		return dao.getPostsPerHourSimpleData(valueOf);
	}

	@Override
	public List<InstagramSimpleDataDTO> getPostsPerWeekSimpleData(String valueOf) {
		return dao.getPostsPerWeekSimpleData(valueOf);
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
	public Long getCountOfPosts(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getCountOfPosts(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public Long getCountOfPostsByMedia(String idInstagramUser, Long dateFrom, Long dateUntil, String media) {
		return dao.getCountOfPostsByMedia(idInstagramUser, dateFrom, dateUntil, media);
	}

	@Override
	public BigDecimal getAveragePostsInteractions(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getAveragePostsInteractions(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public BigDecimal getAverageInteractionsFollower(String valueOf, Long getAverageInteractionsFollower, Long dateUntil) {
		return dao.getAverageInteractionsFollower(valueOf, getAverageInteractionsFollower, dateUntil);
	}

	@Override
	public InstagramReportPost getLastPost(String idInstagramUser) {
		return dao.getLastPost(idInstagramUser);
	}

}
