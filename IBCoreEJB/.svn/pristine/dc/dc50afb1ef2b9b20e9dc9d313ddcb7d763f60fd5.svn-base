package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="reportsdetailed", schema="opsocial")
public class ReportDetailed extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idreportdetailed")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "reportsdetailed_idreportdetailed_seq")
	private Long idReportDetailed;
	
	@Column(name = "pageid")
	private String pageId;
	
	@Column(name="property")
	private String property;
	
	@Column(name="value")
	private Long value;
	
	@Column(name="date")
	private Long date;
	
	@Column(name="fromgroup")
	private String fromGroup;
	
	public ReportDetailed() {
		
	}
	
	@Override
	public Long getId() {		
		return idReportDetailed;
	}

	@Override
	public void setId(Long id) {
		this.idReportDetailed = id;		
	}

	/**
	 * @return the idReportDetailed
	 */
	public Long getIdReportDetailed() {
		return idReportDetailed;
	}

	/**
	 * @param idReportDetailed the idReportDetailed to set
	 */
	public void setIdReportDetailed(Long idReportDetailed) {
		this.idReportDetailed = idReportDetailed;
	}

	/**
	 * @return the pageId
	 */
	public String getPageId() {
		return pageId;
	}

	/**
	 * @param pageId the pageId to set
	 */
	public void setPageId(String pageId) {
		this.pageId = pageId;
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

	public String getFromGroup() {
		return fromGroup;
	}

	public void setFromGroup(String fromGroup) {
		this.fromGroup = fromGroup;
	}
}
