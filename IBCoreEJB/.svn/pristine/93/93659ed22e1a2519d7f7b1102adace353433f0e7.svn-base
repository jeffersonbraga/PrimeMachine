package br.com.opsocial.ejb.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramReportPost;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;
import br.com.opsocial.ejb.entity.report.FilterLikesComments;

@Local
public interface InstagramReportPostDAO extends AbstractDAO<InstagramReportPost> {

	public Long getLastIdMedia(String idInstagramUser);
	public InstagramReportPost getLastPost(String idInstagramUser);
	
	public Long getCountOfPosts(String idInstagramUser);
	public Long getCountOfLikes(String idInstagramUser);
	public Long getCountOfComments(String idInstagramUser);
	public Object[] getFilterMostUsed(String idInstagramUser);
	public Object[] getFilterLessUsed(String idInstagramUser);
	
	public Map<Integer, Long> getPostsPerMonthYear(String idInstagramUser, Integer year);
	public Long getCountOfPosts(String idInstagramUser, Integer year);
	public Object[] getMonthWithMorePosts(String idInstagramUser, Integer year);
	
	public Map<Integer, Long> getPostsPerWeek(String idInstagramUser);
	public Object[] getWeekDayWithMorePosts(String idInstagramUser);
	public Map<Integer, Long> getPostsPerHour(String idInstagramUser);
	public Object[] getHourWithMorePosts(String idInstagramUser);
	
	public List<InstagramReportPost> getPostsWithMoreLikes(String idInstagramUser);
	public List<InstagramReportPost> getPostsWithMoreComments(String idInstagramUser);
	public List<InstagramReportPost> getPostsWithMoreLikes(String idInstagramUser, Long dateFrom, Long dateUntil);
	public List<InstagramReportPost> getPostsWithMoreComments(String idInstagramUser, Long dateFrom, Long dateUntil);
	
	public List<InstagramReportPost> getPostsWithMoreInteractions(String idInstagramUser, Long dateFrom, Long dateUntil);
	
	public List<FilterLikesComments> getFilterLikesComments(String idInstagramUser);
	public Object[] getFilterWithMoreLikes(String idInstagramUser);
	public Object[] getFilterWithMoreComments(String idInstagramUser);
	
	public Map<String, Long> getFiltersCounts(String idInstagramUser);
	
	public Map<String, Long> getInteractionsPerDay(String idInstagramUser, Long dateFrom, Long dateUntil);

	public List<InstagramSimpleDataDTO> getPostsPerMonthYearSimpleData(String idInstagramUser, Integer year);

	List<InstagramSimpleDataDTO> getPostsPerHourSimpleData(String idInstagramUser);

	List<InstagramSimpleDataDTO> getPostsPerWeekSimpleData(String idInstagramUser);

	BigDecimal getAveragePostsInteractions(String idInstagramUser, Long dateFrom, Long dateUntil);

	BigDecimal getAverageInteractionsFollower(String idInstagramUser, Long dateFrom, Long dateUntil);
	
	Long getCountOfPosts(String idInstagramUser, Long dateFrom, Long dateUntil);
	
	Long getCountOfPostsByMedia(String idInstagramUser, Long dateFrom, Long dateUntil, String media);
	
	List<InstagramReport> getAudiencesPostsInteraction(String idInstagramUser, Long dateFromVerified, Long dateUntil);
}