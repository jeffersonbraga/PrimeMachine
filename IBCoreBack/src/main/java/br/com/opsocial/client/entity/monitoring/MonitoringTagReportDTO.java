package br.com.opsocial.client.entity.monitoring;

import java.util.Map;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class MonitoringTagReportDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private MonitoringTagDTO monitoringTag;
	private Long totalTaggedPosts;
	private Map<Character, Long> taggedPostsPerNetwork;
	
	public MonitoringTagReportDTO() {
		
	}

	public MonitoringTagDTO getMonitoringTag() {
		return monitoringTag;
	}

	public void setMonitoringTag(MonitoringTagDTO monitoringTag) {
		this.monitoringTag = monitoringTag;
	}

	public Long getTotalTaggedPosts() {
		return totalTaggedPosts;
	}

	public void setTotalTaggedPosts(Long totalTaggedPosts) {
		this.totalTaggedPosts = totalTaggedPosts;
	}

	public Map<Character, Long> getTaggedPostsPerNetwork() {
		return taggedPostsPerNetwork;
	}

	public void setTaggedPostsPerNetwork(Map<Character, Long> taggedPostsPerNetwork) {
		this.taggedPostsPerNetwork = taggedPostsPerNetwork;
	}
	
}
