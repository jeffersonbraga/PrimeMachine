package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.blogs.BlogPostMonitoring;

@Local
public interface BlogPostMonitoringDAO extends AbstractDAO<BlogPostMonitoring>{
	
	public List<BlogPostMonitoring> listMostRecents(Long idMonitoring, Integer maxResults);
	
	public List<BlogPostMonitoring> listMostRecents(Long createdTime, Long idMonitoring);
	
	public List<BlogPostMonitoring> listElder(Long createdTime, Long idMonitoring, Integer maxResults);
	
	public List<BlogPostMonitoring> getByInterval(Long idMonitoring , Long startDate, Long endDate, 
			String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit);
	
	public Long getByIntervalCount(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords);
	
	public BlogPostMonitoring getByComposedId(Long idMonitoring, Long idBlogPost, String term);
	
	public List<BlogPostMonitoring> listByTerm(String term, Long idMonitoring);
	
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfPostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfGarbagePostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountOfPostsPerHourDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date);
	
	public BlogPostMonitoring getByComposedId(Long idMonitoring, Long idBlogPost);
	
	public List<BlogPostMonitoring> listSample(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, Integer sample, List<String> notIn);

}
