package br.com.opsocial.ejb.entity.facebook;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.FacePostMonitoringId;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Entity
@Table(name = "facepostsmonitorings", schema = "opsocial")
@IdClass(FacePostMonitoringId.class)
public class FacePostMonitoring extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@JoinColumn(name="postid")
	private FacebookPost facebookPost;
	
	@Id
	@ManyToOne()
	@JoinColumn(name="idmonitoring")
	private Monitoring monitoring;

	@Id
	@Column(name = "term")
	private String term;
	
	@JoinColumn(name = "idprofile", referencedColumnName = "idprofile")
	@ManyToOne()
	private Profile profile;
	
	@Column(name = "visible")
	private Character visible;
	
	@Column(name = "qualification")
	private Character qualification;
	
	@Column(name="garbage")
	private Character garbage;
	
	@Column(name = "retrieveddate")
	private Long retrievedDate;
	
	@Column(name = "createdtime")
	private Long createdTime;
	
	@Column(name = "hashashtags")
	private Boolean hasHashTags;
	
	@Column(name = "haslinks")
	private Boolean hasLinks;
	
	public FacePostMonitoring() {
	}

	public FacebookPost getFacebookPost() {
		return facebookPost;
	}

	public void setFacebookPost(FacebookPost facebookPost) {
		this.facebookPost = facebookPost;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public Character getVisible() {
		return visible;
	}

	public void setVisible(Character visible) {
		this.visible = visible;
	}

	public Character getQualification() {
		return qualification;
	}

	public void setQualification(Character qualification) {
		this.qualification = qualification;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public Character getGarbage() {
		return garbage;
	}

	public void setGarbage(Character garbage) {
		this.garbage = garbage;
	}

	public Long getRetrievedDate() {
		return retrievedDate;
	}

	public void setRetrievedDate(Long retrievedDate) {
		this.retrievedDate = retrievedDate;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Boolean getHasHashTags() {
		return hasHashTags;
	}

	public void setHasHashTags(Boolean hasHashTags) {
		this.hasHashTags = hasHashTags;
	}

	public Boolean getHasLinks() {
		return hasLinks;
	}

	public void setHasLinks(Boolean hasLinks) {
		this.hasLinks = hasLinks;
	}
}
