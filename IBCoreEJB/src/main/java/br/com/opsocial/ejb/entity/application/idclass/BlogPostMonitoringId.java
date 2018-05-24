package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BlogPostMonitoringId implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "idblogpost")
	private Long blogPost;
	
	@Column(name = "idmonitoring")
	private Long monitoring;
	
	@Column(name = "term")
	private String term;

	public BlogPostMonitoringId() {
	}

	public Long getBlogPost() {
		return blogPost;
	}

	public void setBlogPost(Long blogPost) {
		this.blogPost = blogPost;
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
