package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MonitoringPostLocationId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "postid")
	private String postId;
	
	@Column(name = "idmonitoring")
	private Long monitoring;
	
	private MonitoringLocationId monitoringLocation;
	
	public MonitoringPostLocationId() {
		
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Long getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Long monitoring) {
		this.monitoring = monitoring;
	}

	public MonitoringLocationId getMonitoringLocation() {
		return monitoringLocation;
	}

	public void setMonitoringLocation(MonitoringLocationId monitoringLocation) {
		this.monitoringLocation = monitoringLocation;
	}
}
