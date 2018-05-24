package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.twitter.TwitterPostMonitoring;

@Local
public interface TwitterPostMonitoringDAO extends AbstractDAO<TwitterPostMonitoring> {

	public Long getCountOfPosts(Long idMonitoring, Long dateFrom, Long dateUntil);
	
	public List<TwitterPostMonitoring> listMostRecents(Long idMonitoring, Integer maxResults);
	
	public List<TwitterPostMonitoring> listMostRecents(Long createdAt, Long idMonitoring);
	
	public List<TwitterPostMonitoring> listElder(Long createdAt, Long idMonitoring, Integer maxResults);
	
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountOfPostsPerHourDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date);
	
	public List<TwitterPostMonitoring> getByInterval(Long idMonitoring , Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit);
	
	public Long getByIntervalCount(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords);
	
	public List<TwitterPostMonitoring> listByMonitoring(Long idMonitoring);
	
	public List<TwitterPostMonitoring> listByTerm(String term, Long idMonitoring);
	
	public TwitterPostMonitoring getByComposedId(Long idMonitoring, Long statusId, String term);
	
	public TwitterPostMonitoring getByComposedId(Long idMonitoring, Long statusId);
	
	public List<TwitterPostMonitoring> listSample(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, Integer sample, List<String> notIn);
	
	public List<TwitterPostMonitoring> listByRetweedStatus(Long idMonitoring, Long statusId, Long retweedtedStatus);
	
	public List<String> listTwitterPostsText(Long idMonitoring, Long startDate, Long endDate, Long limit);
	
	public List<String> listTwitterPostsText(Long idMonitoring, Long startDate, Long endDate, Long limit, Character qualification);
	
	public List<TwitterPostMonitoring> getTwitterPostsByUser(Long idMonitoring, Long userId, Long dateFrom, Long dateUntil);
	
}
