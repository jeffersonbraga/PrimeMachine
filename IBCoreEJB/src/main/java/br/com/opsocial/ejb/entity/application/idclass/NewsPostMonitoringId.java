package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NewsPostMonitoringId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "idnewspost")
	private Long newsPost;
	
	@Column(name = "idmonitoring")
	private Long monitoring;
	
	@Column(name = "term")
	private String term;

	public NewsPostMonitoringId() {
	}

	public Long getNewsPost() {
		return newsPost;
	}

	public void setNewsPost(Long newsPost) {
		this.newsPost = newsPost;
	}

	public Long getMonitoring() {
		return monitoring;
	}

	public void setMonitoring(Long monitoring) {
		this.monitoring = monitoring;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
}
