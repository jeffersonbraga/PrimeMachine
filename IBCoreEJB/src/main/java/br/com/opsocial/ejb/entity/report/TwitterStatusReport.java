package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="twitterstatusreports", schema="opsocial")
public class TwitterStatusReport extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idtwitterstatusreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "twitterstatusreports_idtwitterstatusreport_seq")
	private Long idTwitterStatusReport;

	@Column(name = "idprofile")
	private String idProfile;

	@Column(name = "idstatus")
	private String idStatus;

	@Column(name="property")
	private String property;

	@Column(name="value")
	private Long value;

	@Column(name="date")
	private Long date;

	@Column(name="sinceid")
	private Long sinceId;

	public TwitterStatusReport() {
	}

	public Long getIdTwitterStatusReport() {
		return idTwitterStatusReport;
	}

	public void setIdTwitterStatusReport(Long idTwitterStatusReport) {
		this.idTwitterStatusReport = idTwitterStatusReport;
	}

	@Override
	public Long getId() {
		return idTwitterStatusReport;
	}

	@Override
	public void setId(Long id) {
		this.idTwitterStatusReport = id;
	}

	public String getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}

	public String getIdStatus() {
		return idStatus;
	}

	public void setIdStatus(String idStatus) {
		this.idStatus = idStatus;
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

	public Long getSinceId() {
		return sinceId;
	}

	public void setSinceId(Long sinceId) {
		this.sinceId = sinceId;
	}
}
