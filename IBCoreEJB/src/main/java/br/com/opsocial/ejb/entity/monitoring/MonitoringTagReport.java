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
@Table(name="monitoringtagsreports", schema="opsocial")
public class MonitoringTagReport extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "idmonitoringtagreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringtagsreports_idmonitoringtagreport_seq")
	private Long idMonitoringTagReport;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@JoinColumn(name = "idtag", referencedColumnName = "idtag")
	@ManyToOne()
	private MonitoringTag monitoringTag;
	
	@Column(name = "networktype")
	private Character networkType;
	
	@Column(name = "date")
	private Long date;
	
	@Column(name = "value")
	private Long value;
	
	public MonitoringTagReport() {
		
	}

	public Long getIdMonitoringTagReport() {
		return idMonitoringTagReport;
	}

	public void setIdMonitoringTagReport(Long idMonitoringTagReport) {
		this.idMonitoringTagReport = idMonitoringTagReport;
	}
	
	@Override
	public Long getId() {
		return idMonitoringTagReport;
	}
	
	@Override
	public void setId(Long id) {
		this.idMonitoringTagReport = id;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public MonitoringTag getMonitoringTag() {
		return monitoringTag;
	}

	public void setMonitoringTag(MonitoringTag monitoringTag) {
		this.monitoringTag = monitoringTag;
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
