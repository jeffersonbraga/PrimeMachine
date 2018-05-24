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
@Table(name="Monitoringusersreports", schema="opsocial")
public class MonitoringUserReport extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final String USERS_COUNT = "users_count";
	public static final String USERS_FEMALE_COUNT = "users_female_count";
	public static final String USERS_MALE_COUNT = "users_male_count";

	@Id
	@Column(name = "idmonitoringuserreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringusersreports_idmonitoringuserreport_seq")
	private Long idMonitoringUserReport;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@Column(name="property")
	private String property;
	
	@Column(name="value")
	private Long value;
	
	@Column(name="date")
	private Long date;
	
	public MonitoringUserReport() {
		
	}

	public Long getIdMonitoringUserReport() {
		return idMonitoringUserReport;
	}

	public void setIdMonitoringUserReport(Long idMonitoringUserReport) {
		this.idMonitoringUserReport = idMonitoringUserReport;
	}
	
	@Override
	public Long getId() {
		return idMonitoringUserReport;
	}
	
	@Override
	public void setId(Long id) {
		idMonitoringUserReport = id;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
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
