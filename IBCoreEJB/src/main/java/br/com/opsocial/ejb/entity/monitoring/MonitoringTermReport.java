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
@Table(name="monitoringtermsreports", schema="opsocial")
public class MonitoringTermReport extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idmonitoringtermreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringtermsreports_idmonitoringtermreport_seq")
	private Long idMonitoringTermReport;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@Column(name = "term")
	private String term;
	
	@Column(name = "networktype")
	private Character networkType;
	
	@Column(name = "date")
	private Long date;
	
	@Column(name = "value")
	private Long value;
	
	public MonitoringTermReport() {
		
	}

	public Long getIdMonitoringTermReport() {
		return idMonitoringTermReport;
	}

	public void setIdMonitoringTermReport(Long idMonitoringTermReport) {
		this.idMonitoringTermReport = idMonitoringTermReport;
	}
	
	@Override
	public Long getId() {
		return idMonitoringTermReport;
	}
	
	@Override
	public void setId(Long id) {
		this.idMonitoringTermReport = id;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Character getNetworkType() {
		return networkType;
	}

	public void setNetworkType(Character networkType) {
		this.networkType = networkType;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
	
}
