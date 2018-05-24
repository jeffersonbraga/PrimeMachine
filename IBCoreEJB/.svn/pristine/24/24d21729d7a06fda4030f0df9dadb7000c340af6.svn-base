package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InstagramPostMonitoringId implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "idmedia")
	private Long instagramPost;
	
	@Column(name = "idmonitoring")
	private Long monitoring;
	
	@Column(name = "term") 
	private String term;
	
	public InstagramPostMonitoringId() {
	}

	public Long getInstagramPost() {
		return instagramPost;
	}

	public void setInstagramPost(Long instagramPost) {
		this.instagramPost = instagramPost;
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
