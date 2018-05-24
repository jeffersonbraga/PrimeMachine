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
@Table(name="monitoringqualificationreports", schema="opsocial")
public class MonitoringQualificationReport extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idmonitoringqualificationreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringqualificationreports_idmonitoringqualification_seq")
	private Long idMonitoringQualificationReport;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@Column(name = "term")
	private String term;
	
	@Column(name = "qualification")
	private Character qualification;
	
	@Column(name = "networktype")
	private Character networkType;
	
	@Column(name = "date")
	private Long date;
	
	@Column(name = "value")
	private Long value;
	
	public MonitoringQualificationReport() {
		
	}

	public Long getIdMonitoringQualificationReport() {
		return idMonitoringQualificationReport;
	}

	public void setIdMonitoringQualificationReport(
			Long idMonitoringQualificationReport) {
		this.idMonitoringQualificationReport = idMonitoringQualificationReport;
	}
	
	@Override
	public Long getId() {
		return idMonitoringQualificationReport;
	}
	
	@Override
	public void setId(Long id) {
		this.idMonitoringQualificationReport = id;
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

	public Character getQualification() {
		return qualification;
	}

	public void setQualification(Character qualification) {
		this.qualification = qualification;
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
