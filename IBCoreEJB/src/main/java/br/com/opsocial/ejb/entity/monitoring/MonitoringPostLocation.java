package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.MonitoringPostLocationId;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name = "monitoringpostslocations", schema = "opsocial")
@IdClass(MonitoringPostLocationId.class)
public class MonitoringPostLocation extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "postid")
	private String postId;
	
	@Id
	@ManyToOne()
	@JoinColumn(name="idmonitoring")
	private Monitoring monitoring;
	
	@Id
	@ManyToOne()
	@JoinColumns ({
        @JoinColumn(name="idlocation", referencedColumnName = "idlocation"),
        @JoinColumn(name="networktype", referencedColumnName = "networktype")
    })
	private MonitoringLocation monitoringLocation;
	
	@Column(name = "date")
	private Long date;
	
	@Column(name = "latitude")
	private Double latitude = 0.0;
	
	@Column(name = "longitude")
	private Double longitude = 0.0;
	
	public MonitoringPostLocation() {
		
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

	public MonitoringLocation getMonitoringLocation() {
		return monitoringLocation;
	}

	public void setMonitoringLocation(MonitoringLocation monitoringLocation) {
		this.monitoringLocation = monitoringLocation;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
	
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	
}
