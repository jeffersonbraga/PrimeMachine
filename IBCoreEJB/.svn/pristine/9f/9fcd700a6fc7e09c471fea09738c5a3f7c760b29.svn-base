package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReclameAquiPostMonitoringId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "chacheid")
	private String reclameAquiPost;
	
	@Column(name = "idmonitoring")
	private Long monitoring;
	
	@Column(name = "term")
	private String term;
	
	public ReclameAquiPostMonitoringId() {
	}

	public String getReclameAquiPost() {
		return reclameAquiPost;
	}

	public void setReclameAquiPost(String reclameAquiPost) {
		this.reclameAquiPost = reclameAquiPost;
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
