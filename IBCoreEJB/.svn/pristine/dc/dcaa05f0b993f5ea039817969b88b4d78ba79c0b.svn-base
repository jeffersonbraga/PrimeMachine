package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="twitterreports", schema="opsocial")
public class TwitterReport extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idtwitterreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "twitterreports_idtwitterreport_seq")
	private Long idTwitterReport;

	@Column(name = "idprofile")
	private String idProfile;

	@Column(name="property")
	private String property;

	@Column(name="value")
	private Long value;

	@Column(name="date")
	private Long date;

	@Column(name="sinceid")
	private Long sinceId;

	public TwitterReport() {
		
	}

	/**
	 * @return the idTwitterReport
	 */
	public Long getIdTwitterReport() {
		return idTwitterReport;
	}

	/**
	 * @param idTwitterReport the idTwitterReport to set
	 */
	public void setIdTwitterReport(Long idTwitterReport) {
		this.idTwitterReport = idTwitterReport;
	}

	@Override
	public Long getId() {
		return idTwitterReport;
	}

	@Override
	public void setId(Long id) {
		this.idTwitterReport = id;
	}

	/**
	 * @return the idprofile
	 */
	public String getIdProfile() {
		return idProfile;
	}


	/**
	 * @param idprofile the idprofile to set
	 */
	public void setIdProfile(String idprofile) {
		this.idProfile = idprofile;
	}


	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}


	/**
	 * @param property the property to set
	 */
	public void setProperty(String property) {
		this.property = property;
	}


	/**
	 * @return the value
	 */
	public Long getValue() {
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(Long value) {
		this.value = value;
	}


	/**
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
	}


	/**
	 * @return the sinceId
	 */
	public Long getSinceId() {
		return sinceId;
	}


	/**
	 * @param sinceId the sinceId to set
	 */
	public void setSinceId(Long sinceId) {
		this.sinceId = sinceId;
	}

}
