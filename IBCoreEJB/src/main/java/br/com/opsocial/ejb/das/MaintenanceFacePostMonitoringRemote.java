package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.facebook.FacePostMonitoring;

@Remote
public interface MaintenanceFacePostMonitoringRemote extends AbstractDAS<FacePostMonitoring> {

	public List<FacePostMonitoring> listMostRecents(Long idMonitoring, Integer maxResults);
	
	public List<FacePostMonitoring> listMostRecents(Long createdTime, Long idMonitoring);
	
	public List<FacePostMonitoring> listElder(Long createdTime, Long idMonitoring, Integer maxResults);
		
	public Long getCountOfPosts(Long idMonitoring, Long dateFrom, Long dateUntil);
	
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfPostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfGarbagePostsPerDay(Long idMonitoring, Long date);

	public List<Object[]> getCountOfPostsPerHourDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountGroupsPostsPerDay(Long idMonitoring, Long date);

	public List<FacePostMonitoring> getByInterval(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer limit);

	public Map<String, Long> getCountOfPostsPerUser(Long idMonitoring, Long date);
	
	public Long getByIntervalCount(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags);
	
	public List<FacePostMonitoring> listPostsByActiveAccount(Date now, Long from, Long until);

	public List<FacePostMonitoring> listByMonitoring(Long idMonitoring);
	
	public List<FacePostMonitoring> listByTerm(String term, Long idMonitoring);
	
	public FacePostMonitoring getByComposedId(Long idMonitoring, String postId, String term);
	
	public FacePostMonitoring getByComposedId(Long idMonitoring, String postId);

	public List<String[]> getWordsFromPostsContent(Long idMonitoring, Long startDate, Long endDate, Character qualification);

	public List<FacePostMonitoring> listSample(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, Integer sample, List<String> notIn);

	public List<FacePostMonitoring> listByObjectId(Long idMonitoring, String objectId);
}

