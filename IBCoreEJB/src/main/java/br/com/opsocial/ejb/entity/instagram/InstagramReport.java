package br.com.opsocial.ejb.entity.instagram;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostRemove;
import javax.persistence.PostUpdate;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="instagramreports", schema="opsocial")
public class InstagramReport extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idinstagramreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "instagramreports_idinstagramreport_seq")
	private Long idInstagramReport;

	@Column(name = "idinstagramuser")
	private String idInstagramUser;

	@Column(name="property")
	private String property;

	@Column(name="value")
	private Long value;

	@Column(name="date")
	private Long date;

	@Column(name="label")
	private String label;

	@Column(name="dayweek")
	private String dayWeek;
	
	public InstagramReport() {
		
	}

	public Long getIdInstagramReport() {
		return idInstagramReport;
	}

	public void setIdInstagramReport(Long idInstagramReport) {
		this.idInstagramReport = idInstagramReport;
	}
	
	@Override
	public Long getId() {
		return idInstagramReport;
	}

	@Override
	public void setId(Long id) {
		this.idInstagramReport = id;
	}

	public String getIdInstagramUser() {
		return idInstagramUser;
	}

	public void setIdInstagramUser(String idInstagramUser) {
		this.idInstagramUser = idInstagramUser;
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

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getDayWeek() {
		return dayWeek;
	}

	public void setDayWeek(String dayWeek) {
		this.dayWeek = dayWeek;
	}

}