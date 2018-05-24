package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TwitterPostMonitoringId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "statusid")
	private Long twitterPost;
	
	@Column(name = "idmonitoring")
	private Long monitoring;
	
	@Column(name = "term")
	private String term;
	
	public TwitterPostMonitoringId() {
	}

	public Long getTwitterPost() {
		return twitterPost;
	}

	public void setTwitterPost(Long twitterPost) {
		this.twitterPost = twitterPost;
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
