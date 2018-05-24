package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.google.GooglePlusPostMonitoring;

@Remote
public interface MaintenanceGooglePlusPostMonitoringRemote extends AbstractDAS<GooglePlusPostMonitoring> {
	
	public Long getCountOfPosts(Long idMonitoring, Long dateFrom, Long dateUntil);
	
	public List<GooglePlusPostMonitoring> listMostRecents(Long idMonitoring, Integer maxResults);
	
	public List<GooglePlusPostMonitoring> listMostRecents(Long published, Long idMonitoring);
	
	public List<GooglePlusPostMonitoring> listElder(Long published, Long idMonitoring, Integer maxResults);
	
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfGarbagePostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountOfPostsPerHourDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date);
	
	public List<GooglePlusPostMonitoring> getByInterval(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit);
	
	public Long getByIntervalCount(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords);
	
	public List<GooglePlusPostMonitoring> listPostsByActiveAccount(Date now, Long from, Long until);
	
	public List<GooglePlusPostMonitoring> listByMonitoring(Long idMonitoring);
	
	public List<GooglePlusPostMonitoring> listByTerm(String term, Long idMonitoring);
	
	public GooglePlusPostMonitoring getByComposedId(Long idMonitoring, String activityId, String term);
	
	public GooglePlusPostMonitoring getByComposedId(Long idMonitoring, String activityId);

	public List<GooglePlusPostMonitoring> listSample(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, Integer sample, List<String> notIn);
	
	public List<GooglePlusPostMonitoring> listSamePosts(Long idMonitoring, String activityId, String objectId);
}
