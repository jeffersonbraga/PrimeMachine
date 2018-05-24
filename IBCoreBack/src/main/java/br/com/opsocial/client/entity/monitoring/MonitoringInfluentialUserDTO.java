package br.com.opsocial.client.entity.monitoring;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

public class MonitoringInfluentialUserDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private Long idMonitoringInfluentialUser;
	private Monitoring monitoring;
	private MonitoringUserDTO monitoringUser;
	private Long friends;
	private Long followers;
	private Long postsCount;
	private Long date;
	
	public MonitoringInfluentialUserDTO() {
	}

	public Long getIdMonitoringInfluentialUser() {
		return idMonitoringInfluentialUser;
	}

	public void setIdMonitoringInfluentialUser(Long idMonitoringInfluentialUser) {
		this.idMonitoringInfluentialUser = idMonitoringInfluentialUser;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public MonitoringUserDTO getMonitoringUser() {
		return monitoringUser;
	}

	public void setMonitoringUser(MonitoringUserDTO monitoringUser) {
		this.monitoringUser = monitoringUser;
	}

	public Long getFriends() {
		return friends;
	}

	public void setFriends(Long friends) {
		this.friends = friends;
	}

	public Long getFollowers() {
		return followers;
	}

	public void setFollowers(Long followers) {
		this.followers = followers;
	}

	public Long getPostsCount() {
		return postsCount;
	}

	public void setPostsCount(Long postsCount) {
		this.postsCount = postsCount;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}
