package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="reportscontrol", schema="opsocial")
public class ReportControl extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idreportcontrol")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "reportscontrol_idreportcontrol_seq")
	private Long idReportsControl;
	
	@Column(name="networkid")
	private String networkId;
	
	@Column(name="networktype")
	private Character networkType;
	
	@Column(name="date")
	private Long date;
	
	@Column(name="type")
	private String type;
	
	@Column(name="startdate")
	private Long startDate;
	
	@Column(name="finishDate")
	private Long finishDate;
	
	public ReportControl() {
	}
	
	/**
	 * @return the idReportsControl
	 */
	public Long getIdReportsControl() {
		return idReportsControl;
	}

	/**
	 * @param idReportsControl the idReportsControl to set
	 */
	public void setIdReportsControl(Long idReportsControl) {
		this.idReportsControl = idReportsControl;
	}
	
	@Override
	public Long getId() {		
		return idReportsControl;
	}

	@Override
	public void setId(Long id) {
		this.idReportsControl = id;		
	}

	/**
	 * @return the networkId
	 */
	public String getNetworkId() {
		return networkId;
	}

	/**
	 * @param networkId the networkId to set
	 */
	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	/**
	 * @return the networkType
	 */
	public Character getNetworkType() {
		return networkType;
	}

	/**
	 * @param networkType the networkType to set
	 */
	public void setNetworkType(Character networkType) {
		this.networkType = networkType;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
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
