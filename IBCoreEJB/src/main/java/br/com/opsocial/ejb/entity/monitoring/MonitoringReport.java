package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="monitoringreports", schema="opsocial")
public class MonitoringReport extends Persistent {

	private static final long serialVersionUID = 1L;

	public static final Character GARBAGE = 'T';
	public static final Character NOT_GARBAGE = 'F';
	
	public static final Integer HISTORICAL = 0;
	public static final Integer PERIOD_SEVEN = 7;
	public static final Integer PERIOD_FIFTEEN = 15;
	public static final Integer PERIOD_THIRTY = 30;
	
	public static final Integer GEO_POSTS_LIMIT = 100;
	
	public static final String DAY = "day";
	public static final String WEEK_DAY = "week_day";
	public static final String HOUR = "hour";
	
	@Id
	@Column(name = "idmonitoringreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringreports_idmonitoringreport_seq")
	private Long idMonitoringReport;
	
	//@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	//@ManyToOne()
	@Transient
	private Monitoring monitoring;
	
	@Column(name = "networktype")
	private Character networkType;
	
	@Column(name = "date")
	private Long date;
	
	@Column(name = "periodtype")
	private String periodType;
	
	@Column(name = "period")
	private String period;
	
	@Column(name = "value")
	private Long value;
	
	@Column(name = "garbage")
	private Character garbage;
	
	public MonitoringReport() {
	}
	
	public Long getIdMonitoringReport() {
		return idMonitoringReport;
	}

	public void setIdMonitoringReport(Long idMonitoringReport) {
		this.idMonitoringReport = idMonitoringReport;
	}
	
	@Override
	public Long getId() {
		return idMonitoringReport;
	}
	
	@Override
	public void setId(Long id) {
		this.idMonitoringReport = id;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
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

	public String getPeriodType() {
		return periodType;
	}

	public void setPeriodType(String periodType) {
		this.periodType = periodType;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}

	public Character getGarbage() {
		return garbage;
	}

	public void setGarbage(Character garbage) {
		this.garbage = garbage;
	}
}
