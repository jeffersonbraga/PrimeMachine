package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InstagramPostTagId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "idmedia")
	private String instagramReportPost;
	
	@Column(name = "tag")
	private String tag;
	
	public InstagramPostTagId() {
		
	}

	public String getInstagramReportPost() {
		return instagramReportPost;
	}

	public void setInstagramReportPost(String instagramReportPost) {
		this.instagramReportPost = instagramReportPost;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}
	
}
