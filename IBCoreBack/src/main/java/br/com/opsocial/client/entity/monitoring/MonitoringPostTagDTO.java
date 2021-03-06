package br.com.opsocial.client.entity.monitoring;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class MonitoringPostTagDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private Long idPostTag;
	private MonitoringTagDTO monitoringTag;
	private String postId;
	private String term;
	private Long idMonitoring;
	private Character network;
	
	public MonitoringPostTagDTO() {
		
	}

	public Long getIdPostTag() {
		return idPostTag;
	}

	public void setIdPostTag(Long idPostTag) {
		this.idPostTag = idPostTag;
	}

	public MonitoringTagDTO getMonitoringTag() {
		return monitoringTag;
	}

	public void setMonitoringTag(MonitoringTagDTO monitoringTag) {
		this.monitoringTag = monitoringTag;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Long getIdMonitoring() {
		return idMonitoring;
	}

	public void setIdMonitoring(Long idMonitoring) {
		this.idMonitoring = idMonitoring;
	}

	public Character getNetwork() {
		return network;
	}

	public void setNetwork(Character network) {
		this.network = network;
	}
}
