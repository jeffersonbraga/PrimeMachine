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
@Table(name="monitoringreportscontrol", schema="opsocial")
public class MonitoringReportControl extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final String QUALIFICATION = "qualification";
	public static final String TAG = "tag";
	public static final String TERM = "term";
	public static final String USERS = "users";
	public static final String LOCATION = "location";
	public static final String GROUP = "group";

	@Id
	@Column(name = "idmonitoringreportcontrol")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "monitoringreportscontrol_idmonitoringreportcontrol_seq")
	private Long idMonitoringReportControl;
	
	@JoinColumn(name = "idmonitoring", referencedColumnName = "idmonitoring")
	@ManyToOne()
	private Monitoring monitoring;
	
	@Column(name="type")
	private String type;
	
	@Column(name="date")
	private Long date;
	
	@Column(name="startdate")
	private Long startDate;
	
	@Column(name="finishDate")
	private Long finishDate;
	
	public MonitoringReportControl() {
		
	}

	public Long getIdMonitoringReportControl() {
		return idMonitoringReportControl;
	}

	public void setIdMonitoringReportControl(Long idMonitoringReportControl) {
		this.idMonitoringReportControl = idMonitoringReportControl;
	}
	
	@Override
	public Long getId() {
		return idMonitoringReportControl;
	}
	
	@Override
	public void setId(Long id) {
		this.idMonitoringReportControl = id;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getStartDate() {
		return startDate;
	}

	public void setStartDate(Long startDate) {
		this.startDate = startDate;
	}

	public Long getFinishDate() {
		return finishDate;
	}

	public void setFinishDate(Long finishDate) {
		this.finishDate = finishDate;
	}
	
}
