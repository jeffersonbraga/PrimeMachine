package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="facereportslocation", schema="opsocial")
public class FaceReportLocation extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idfacereportlocation")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "facereportslocation_idfacereportlocation_seq")
	private Long idFaceReportLocation;
	
	@Column(name = "pageid")
	private String pageId;
	
	@Column(name="location")
	private String location;
	
	@Column(name="value")
	private Long value;
	
	@Column(name="date")
	private Long date;
	
	@Column(name="fromgroup")
	private String fromGroup;
	
	public FaceReportLocation() {
	}
	
	public Long getIdFaceReportLocation() {
		return idFaceReportLocation;
	}

	public void setIdFaceReportLocation(Long idFaceReportLocation) {
		this.idFaceReportLocation = idFaceReportLocation;
	}
	
	@Override
	public Long getId() {
		return idFaceReportLocation;
	}
	
	@Override
	public void setId(Long id) {
		this.idFaceReportLocation = id;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public String getFromGroup() {
		return fromGroup;
	}

	public void setFromGroup(String fromGroup) {
		this.fromGroup = fromGroup;
	}
}
