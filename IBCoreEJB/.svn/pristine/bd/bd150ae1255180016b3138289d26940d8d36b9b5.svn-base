package br.com.opsocial.ejb.entity.instagram;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.InstagramPostMonitoringId;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Entity
@Table(name = "instagrampostsmonitorings", schema = "opsocial")
@IdClass(InstagramPostMonitoringId.class)
public class InstagramPostMonitoring extends Persistent {
	
	private static final long serialVersionUID = 1L;

	@Id
	@JoinColumn(name="idmedia")
	private InstagramPost instagramPost;
	
	@Id
	@ManyToOne()
	@JoinColumn(name="idmonitoring")
	private Monitoring monitoring;

	@Id
	@Column(name = "term")
	private String term;
	
	@Column(name = "qualification")
	private Character qualification;
	
	@Column(name = "retrieveddate")
	private Long retrievedDate;
	
	@Column(name="garbage")
	private Character garbage;
	
	@Column(name = "createdtime")
	private Long createdTime;
	
	@Column(name = "hashashtags")
	private Boolean hasHashTags;
	
	@Column(name = "haslinks")
	private Boolean hasLinks;
	
	public InstagramPostMonitoring() {
	}

	public InstagramPost getInstagramPost() {
		return instagramPost;
	}

	public void setInstagramPost(InstagramPost instagramPost) {
		this.instagramPost = instagramPost;
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

	public Character getGarbage() {
		return garbage;
	}

	public void setGarbage(Character garbage) {
		this.garbage = garbage;
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
