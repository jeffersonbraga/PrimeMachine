package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GooglePlusPostMonitoringId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "activityid")
	private String googlePlusPost;
	
	@Column(name = "idmonitoring")
	private Long monitoring;
	
	@Column(name = "term")
	private String term;
	
	public GooglePlusPostMonitoringId() {
	}

	public String getGooglePlusPost() {
		return googlePlusPost;
	}

	public void setGooglePlusPost(String googlePlusPost) {
		this.googlePlusPost = googlePlusPost;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
