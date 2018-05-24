package br.com.opsocial.ejb.das;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.instagram.InstagramReportPost;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;
import br.com.opsocial.ejb.entity.report.FilterLikesComments;

@Remote
public interface MaintenanceInstagramReportPostRemote extends AbstractDAS<InstagramReportPost> {

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

	public List<InstagramSimpleDataDTO> getPostsPerMonthYearSimpleData(String valueOf, int i);

	public List<InstagramSimpleDataDTO> getPostsPerHourSimpleData(String valueOf);

	public List<InstagramSimpleDataDTO> getPostsPerWeekSimpleData(String valueOf);
	
	public Long getCountOfPosts(String idInstagramUser, Long dateFrom, Long dateUntil);
	
	public Long getCountOfPostsByMedia(String idInstagramUser, Long dateFrom, Long dateUntil, String media);
	
	public BigDecimal getAveragePostsInteractions(String idInstagramUser, Long dateFrom, Long dateUntil);

	public BigDecimal getAverageInteractionsFollower(String valueOf, Long dateFromVerified, Long dateUntil);
}