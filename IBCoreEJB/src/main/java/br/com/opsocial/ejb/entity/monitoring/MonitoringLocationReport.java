package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="monitoringlocationsreports", schema="opsocial")
public class MonitoringLocationReport extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idmonitoringlocationreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringlocationsreports_idmonitoringlocationreport_seq")
	private Long idMonitoringLocationReport;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@Column(name="location")
	private String location;
	
	@Column(name="value")
	private Long value;
	
	@Column(name="date")
	private Long date;
	
	public MonitoringLocationReport() {
		
	}

	public Long getIdMonitoringLocationReport() {
		return idMonitoringLocationReport;
	}

	public void setIdMonitoringLocationReport(Long idMonitoringLocationReport) {
		this.idMonitoringLocationReport = idMonitoringLocationReport;
	}
	
	@Override
	public Long getId() {
		return idMonitoringLocationReport;
	}
	
	@Override
	public void setId(Long id) {
		idMonitoringLocationReport = id;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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
