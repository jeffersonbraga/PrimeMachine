package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class YoutubePostMonitoringId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "videoid")
	private String youtubePost;
	
	@Column(name = "idmonitoring")
	private Long monitoring;
	
	@Column(name = "term")
	private String term;
	
	public YoutubePostMonitoringId() {
	}

	public String getYoutubePost() {
		return youtubePost;
	}

	public void setYoutubePost(String youtubePost) {
		this.youtubePost = youtubePost;
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
