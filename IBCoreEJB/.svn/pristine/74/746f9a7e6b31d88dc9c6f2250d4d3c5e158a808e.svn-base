package br.com.opsocial.ejb.das;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostTag;

@Remote
public interface MaintenanceMonitoringPostTagRemote extends AbstractDAS<MonitoringPostTag> {

	public List<MonitoringPostTag> listByMonitoringPost(String postId, Long idMonitoring, String term, Character network);
	
	public boolean hasMonitoringTag(Long idTag, String postId, Long idMonitoring, String term, Character network);
	
	public boolean thereIsMonitoringTag(String postId,
			Long idMonitoring, String term, Character network, List<Long> monitoringTags);
	
	public Map<Character, Long> getTaggedPostsPerNetworkForSamples(Long idMonitoring, Long idTag, HashMap<Character, String> mappedPosts);
}
