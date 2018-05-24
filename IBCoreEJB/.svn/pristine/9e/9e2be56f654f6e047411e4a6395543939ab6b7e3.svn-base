package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.news.NewsPostMonitoring;

@Local
public interface NewsPostMonitoringDAO extends AbstractDAO<NewsPostMonitoring>{
	
	public List<NewsPostMonitoring> listMostRecents(Long idMonitoring, Integer maxResults);
	
	public List<NewsPostMonitoring> listMostRecents(Long createdTime, Long idMonitoring);
	
	public List<NewsPostMonitoring> listElder(Long createdTime, Long idMonitoring, Integer maxResults);
	
	public List<NewsPostMonitoring> getByInterval(Long idMonitoring , Long startDate, Long endDate, 
			String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit);
	
	public Long getByIntervalCount(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords);
	
	public NewsPostMonitoring getByComposedId(Long idMonitoring, Long idNewsPost, String term);
	
	public List<NewsPostMonitoring> listByTerm(String term, Long idMonitoring);
	
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfPostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfGarbagePostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date);
	
	public NewsPostMonitoring getByComposedId(Long idMonitoring, Long idNewsPost);
	
	public List<NewsPostMonitoring> listSample(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, Integer sample, List<String> notIn);
}
