package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPostMonitoring;

@Remote
public interface MaintenanceReclameAquiPostMonitoringRemote extends AbstractDAS<ReclameAquiPostMonitoring> {
	
	public List<ReclameAquiPostMonitoring> listMostRecents(Long idMonitoring, Integer maxResults);
	
	public List<ReclameAquiPostMonitoring> listMostRecents(Long createdTime, Long idMonitoring);
	
	public List<ReclameAquiPostMonitoring> listElder(Long createdTime, Long idMonitoring, Integer maxResults);
	
	public List<ReclameAquiPostMonitoring> getByInterval(Long idMonitoring , Long startDate, Long endDate, 
			String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit);
	
	public Long getByIntervalCount(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords);
	
	public ReclameAquiPostMonitoring getByComposedId(Long idMonitoring, String cacheId, String term);
	
	public List<ReclameAquiPostMonitoring> listByTerm(String term, Long idMonitoring);
	
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date);
	
	public Long getCountOfPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountOfPostsPerHourDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date);
	
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date);
	
	public ReclameAquiPostMonitoring getByComposedId(Long idMonitoring, String cacheId);

	public List<ReclameAquiPostMonitoring> listSample(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, Integer sample, List<String> notIn);
}
