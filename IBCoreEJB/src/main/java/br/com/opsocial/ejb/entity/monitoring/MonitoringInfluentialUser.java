package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="monitoringinfluentialusers", schema="opsocial")
public class MonitoringInfluentialUser extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idmonitoringinfluentialuser")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringinfluentialusers_idmonitoringinfluentialuser_seq")
	private Long idMonitoringInfluentialUser;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@ManyToOne()
	@JoinColumns ({
        @JoinColumn(name="iduser", referencedColumnName = "iduser"),
        @JoinColumn(name="networktype", referencedColumnName = "networktype")
    })
	private MonitoringUser monitoringUser;
	
	@Column(name = "friends")
	private Long friends;
	
	@Column(name = "followers")
	private Long followers;
	
	@Column(name = "postscount")
	private Long postsCount;

	@Column(name = "positivepostscount")
	private Long positivePostsCount;
	
	@Column(name = "negativepostscount")
	private Long negativePostsCount;
	
	@Column(name = "date")
	private Long date;
	
	public MonitoringInfluentialUser() {
		
	}

	public Long getIdMonitoringInfluentialUser() {
		return idMonitoringInfluentialUser;
	}

	public void setIdMonitoringInfluentialUser(Long idMonitoringInfluentialUser) {
		this.idMonitoringInfluentialUser = idMonitoringInfluentialUser;
	}
	
	@Override
	public Long getId() {
		return idMonitoringInfluentialUser;
	}
	
	@Override
	public void setId(Long id) {
		idMonitoringInfluentialUser = id;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public MonitoringUser getMonitoringUser() {
		return monitoringUser;
	}

	public void setMonitoringUser(MonitoringUser monitoringUser) {
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

	public Long getPositivePostsCount() {
		return positivePostsCount;
	}

	public void setPositivePostsCount(Long positivePostsCount) {
		this.positivePostsCount = positivePostsCount;
	}

	public Long getNegativePostsCount() {
		return negativePostsCount;
	}

	public void setNegativePostsCount(Long negativePostsCount) {
		this.negativePostsCount = negativePostsCount;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
	
}
