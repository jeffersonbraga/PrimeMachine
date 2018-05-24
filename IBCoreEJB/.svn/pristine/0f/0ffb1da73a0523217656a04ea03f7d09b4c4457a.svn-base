package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.youtube.YoutubePostMonitoring;

@Remote
public interface MaintenanceYoutubePostMonitoringRemote extends AbstractDAS<YoutubePostMonitoring> {
	
	public Long getCountOfPosts(Long idMonitoring, Long dateFrom, Long dateUntil);
	
	public List<YoutubePostMonitoring> listMostRecents(Long idMonitoring, Integer maxResults);
	
	public List<YoutubePostMonitoring> listMostRecents(Long publishedAt, Long idMonitoring);
	
	public List<YoutubePostMonitoring> listElder(Long publishedAt, Long idMonitoring, Integer maxResults);
	
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfGarbagePostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfPostsPerDay(Long idMonitoring, Long date);

	public List<Object[]> getCountOfPostsPerHourDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date);
	
	public List<YoutubePostMonitoring> getByInterval(Long idMonitoring, Long startDate, Long endDate, 
			String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit);
	
	public Long getByIntervalCount(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords);
	
	public List<YoutubePostMonitoring> listPostsByActiveAccount(Date now, Long from, Long until);
	
	public List<YoutubePostMonitoring> listByMonitoring(Long idMonitoring);
	
	public List<YoutubePostMonitoring> listByTerm(String term, Long idMonitoring);
	
	public YoutubePostMonitoring getByComposedId(Long idMonitoring, String videoId, String term);
	
	public YoutubePostMonitoring getByComposedId(Long idMonitoring, String videoId);

	public List<YoutubePostMonitoring> listSample(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, Integer sample, List<String> notIn);
	
	public List<YoutubePostMonitoring> listSamePosts(Long idMonitoring, String description);
}
