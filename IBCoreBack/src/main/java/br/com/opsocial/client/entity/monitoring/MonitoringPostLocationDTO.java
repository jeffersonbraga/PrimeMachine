package br.com.opsocial.client.entity.monitoring;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

public class MonitoringPostLocationDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private String postId;
	private Monitoring monitoring;
	private MonitoringLocationDTO monitoringLocation;
	private Long date;
	
	public MonitoringPostLocationDTO() {
		
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public MonitoringLocationDTO getMonitoringLocation() {
		return monitoringLocation;
	}

	public void setMonitoringLocation(MonitoringLocationDTO monitoringLocation) {
		this.monitoringLocation = monitoringLocation;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}
