package br.com.opsocial.ejb.entity.twitter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.TwitterPostMonitoringId;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Entity
@Table(name = "twitterpostsmonitorings", schema = "opsocial")
@IdClass(TwitterPostMonitoringId.class)
public class TwitterPostMonitoring extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@ManyToOne
	@JoinColumn(name="statusid")
	private TwitterPost twitterPost;
	
	@Id
	@ManyToOne
	@JoinColumn(name="idmonitoring")
	private Monitoring monitoring;

	@Id
	@Column(name = "term")
	private String term;
	
	@Column(name = "visible")
	private Character visible;
	
	@Column(name = "qualification")
	private Character qualification;
	
	@Column(name = "retrieveddate")
	private Long retrievedDate;
	
	@Column(name = "createdtime")
	private Long createdTime;
	
	@Column(name = "idlocation")
	private String idLocation;
	
	@Column(name = "hashashtags")
	private Boolean hasHashTags;
	
	@Column(name = "haslinks")
	private Boolean hasLinks;
	
	public TwitterPostMonitoring() {
	}
	
	public TwitterPost getTwitterPost() {
		return twitterPost;
	}

	public void setTwitterPost(TwitterPost twitterPost) {
		this.twitterPost = twitterPost;
	}

	public Monitoring getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Monitoring monitoring) {
		this.monitoring = monitoring;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
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

	public String getIdLocation() {
		return idLocation;
	}

	public void setIdLocation(String idLocation) {
		this.idLocation = idLocation;
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
