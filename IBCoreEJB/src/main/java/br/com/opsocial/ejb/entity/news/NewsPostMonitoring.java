package br.com.opsocial.ejb.entity.news;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.NewsPostMonitoringId;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Entity
@Table(name = "newspostsmonitorings", schema = "opsocial")
@IdClass(NewsPostMonitoringId.class)
public class NewsPostMonitoring extends Persistent {
	

private static final long serialVersionUID = 1L;
	
	@Id
	@JoinColumn(name="idnewspost")
	private NewsPost newsPost;
	
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

	public NewsPostMonitoring() {
	}

	public NewsPost getNewsPost() {
		return newsPost;
	}

	public void setNewsPost(NewsPost newsPost) {
		this.newsPost = newsPost;
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
}
