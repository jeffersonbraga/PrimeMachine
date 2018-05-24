package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="monitoringgroupsreports", schema="opsocial")
public class MonitoringGroupReport extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idmonitoringgroupreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringgroupsreports_idmonitoringgroupreport_seq")
	private Long idMonitoringGroupReport;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@JoinColumn(name = "idprofile", referencedColumnName = "idprofile")
	@ManyToOne()
	private Profile profile;
	
	@Column(name = "value")
	private Long value;
	
	@Column(name = "date")
	private Long date;
	
	public MonitoringGroupReport() {
		
	}

	public Long getIdMonitoringGroupReport() {
		return idMonitoringGroupReport;
	}

	public void setIdMonitoringGroupReport(Long idMonitoringGroupReport) {
		this.idMonitoringGroupReport = idMonitoringGroupReport;
	}
	
	@Override
	public Long getId() {
		return idMonitoringGroupReport;
	}
	
	@Override
	public void setId(Long id) {
		idMonitoringGroupReport = id;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}
}
