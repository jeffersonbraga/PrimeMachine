package br.com.opsocial.client.entity.monitoring;

import java.util.List;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class MonitoringFollowerReportDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private MonitoringInfluentialUserDTO monitoringInfluentialUser;
	private List<PostMonitoringDTO> postsMonitoring;
	
	public MonitoringFollowerReportDTO() {
		
	}

	public MonitoringInfluentialUserDTO getMonitoringInfluentialUser() {
		return monitoringInfluentialUser;
	}

	public void setMonitoringInfluentialUser(
			MonitoringInfluentialUserDTO monitoringInfluentialUser) {
		this.monitoringInfluentialUser = monitoringInfluentialUser;
	}

	public List<PostMonitoringDTO> getPostsMonitoring() {
		return postsMonitoring;
	}

	public void setPostsMonitoring(List<PostMonitoringDTO> postsMonitoring) {
		this.postsMonitoring = postsMonitoring;
	}
	
}
