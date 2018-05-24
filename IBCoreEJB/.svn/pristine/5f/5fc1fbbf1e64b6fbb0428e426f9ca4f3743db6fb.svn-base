package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="twitterfollowersreports", schema="opsocial")
public class TwitterFollowerReport extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idtwitterfollowerreport")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "twitterfollowersreports_idtwitterfollowerreport_seq")
	private Long idTwitterFollowerReport;
	
	@Column(name = "idprofile")
	private Long idProfile;
	
	@Column(name = "followerid")
	private Long followerId;
	
	@Column(name = "property")
	private String property;
	
	@Column(name = "date")
	private Long date;
	
	@Column(name="value")
	private Long value;
	
	public TwitterFollowerReport() {
	}
	
	public Long getIdTwitterFollowerReport() {
		return idTwitterFollowerReport;
	}

	public void setIdTwitterFollowerReport(Long idTwitterFollowerReport) {
		this.idTwitterFollowerReport = idTwitterFollowerReport;
	}
	
	@Override
	public Long getId() {
		return idTwitterFollowerReport;
	}
	
	@Override
	public void setId(Long id) {
		this.idTwitterFollowerReport = id;
	}

	public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}

	public Long getFollowerId() {
		return followerId;
	}

	public void setFollowerId(Long followerId) {
		this.followerId = followerId;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Long getDate() {
		return date;
	}

	public void setDate(Long date) {
		this.date = date;
	}

	public Long getValue() {
		return value;
	}

	public void setValue(Long value) {
		this.value = value;
	}
}
