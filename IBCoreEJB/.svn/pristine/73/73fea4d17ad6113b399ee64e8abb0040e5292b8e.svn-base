package br.com.opsocial.ejb.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;

@Local
public interface InstagramPostMonitoringDAO extends AbstractDAO<InstagramPostMonitoring> {
	
	public List<InstagramPostMonitoring> listMostRecents(Long idMonitoring, Integer maxResults);
	
	public List<InstagramPostMonitoring> listMostRecents(Long createTime, Long idMonitoring);
	
	public List<InstagramPostMonitoring> listElder(Long createTime, Long idMonitoring, Integer maxResults);
	
	public List<InstagramPostMonitoring> getByInterval(Long idMonitoring, Long startDate, Long endDate, 
			String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit);
	
	public Long getByIntervalCount(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords);
	
	public List<InstagramPostMonitoring> listPostsByActiveAccount(Date now, Long from, Long until);
	
	public List<InstagramPostMonitoring> listByMonitoring(Long idMonitoring);
	
	public List<InstagramPostMonitoring> listByTerm(String term, Long idMonitoring);
	
	public InstagramPostMonitoring getByComposedId(Long idMonitoring, Long idMedia, String term);
	
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfGarbagePostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountOfPostsPerHourDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date);
	
	public InstagramPostMonitoring getByComposedId(Long idMonitoring, Long idMedia);
	
	public List<InstagramPostMonitoring> listSample(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, Integer sample, List<String> notIn);
	
	public List<InstagramPostMonitoring> getInstagramPostsByUser(Long idMonitoring, Long userId, Long dateFrom, Long dateUntil);
}
