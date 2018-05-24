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
@Table(name="monitoringlogsreports", schema="opsocial")
public class MonitoringLogReport extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final String MON_REPORT_DAY = "MRD";
	public static final String MON_REPORT_HOUR = "MRH";
	public static final String MON_TERM_REPORT = "MTERMR";
	public static final String MON_QUALIFICATION_REPORT = "MQUAR";
	public static final String MON_QUALIFICATION_REPORT_NEW = "MQUARN";
	public static final String MON_TAG_REPORT = "MTAGR";
	public static final String MON_TAG_REPORT_NEW = "MTAGRN";
	public static final String MON_LOCATION_REPORT = "MLOCR";
	public static final String MON_INFLUENTIAL_USER = "MINFUR";
	public static final String MON_INFLUENTIAL_USER_POS = "MINFUPR";
	public static final String MON_INFLUENTIAL_USER_NEG = "MINFUNR";
	public static final String MON_LINK_REPORT = "MLINKR";
	public static final String MON_HASHTAG_REPORT = "MHASHR";
	public static final String MON_GROUP_REPORT = "MGR";
	
	public static final Character UPDATE_APPLIED = 'T';
	public static final Character UPDATE_NOT_APPLIED = 'F';
	
	@Id
	@Column(name = "idmonitoringlogreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringlogsreports_idmonitoringlogreport_seq")
	private Long idMonitoringLogReport;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@Column(name = "reportid")
	private String reportId;
	
	@Column(name="reporttype")
	private String reportType;
	
	@Column(name = "valuetoapply")
	private Integer valueToApply;
	
	@Column(name = "updateapplied")
	private Character updateApplied;
	
	@Column(name = "logdate")
	private Long logDate;
	
	@Column(name = "updateapplieddate")
	private Long updateAppliedDate;
	
	public MonitoringLogReport() {
		
	}

	public Long getIdMonitoringLogReport() {
		return idMonitoringLogReport;
	}

	public void setIdMonitoringLogReport(Long idMonitoringLogReport) {
		this.idMonitoringLogReport = idMonitoringLogReport;
	}
	
	@Override
	public Long getId() {
		return idMonitoringLogReport;
	}
	
	@Override
	public void setId(Long id) {
		this.idMonitoringLogReport = id;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public Integer getValueToApply() {
		return valueToApply;
	}

	public void setValueToApply(Integer valueToApply) {
		this.valueToApply = valueToApply;
	}

	public Character getUpdateApplied() {
		return updateApplied;
	}

	public void setUpdateApplied(Character updateApplied) {
		this.updateApplied = updateApplied;
	}

	public Long getLogDate() {
		return logDate;
	}

	public void setLogDate(Long logDate) {
		this.logDate = logDate;
	}

	public Long getUpdateAppliedDate() {
		return updateAppliedDate;
	}

	public void setUpdateAppliedDate(Long updateAppliedDate) {
		this.updateAppliedDate = updateAppliedDate;
	}
	
}
