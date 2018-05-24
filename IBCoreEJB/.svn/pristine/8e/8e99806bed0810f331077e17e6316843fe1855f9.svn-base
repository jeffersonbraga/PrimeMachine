package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FacePostMonitoringId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "postid")
	private String facebookPost;
	
	@Column(name = "idmonitoring")
	private Long monitoring;
	
	@Column(name = "term")
	private String term;
	
	public FacePostMonitoringId() {
	}

	public String getFacebookPost() {
		return facebookPost;
	}

	public void setFacebookPost(String facebookPost) {
		this.facebookPost = facebookPost;
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
