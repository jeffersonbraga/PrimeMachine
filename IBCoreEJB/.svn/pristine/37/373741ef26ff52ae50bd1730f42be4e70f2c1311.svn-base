package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InstagramPostLikeId implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "idmedia")
	private String instagramReportPost;
	
	@Column(name = "idinstagramuser")
	private String idInstagramUser;
	
	public InstagramPostLikeId() {
		
	}

	public String getInstagramReportPost() {
		return instagramReportPost;
	}

	public void setInstagramReportPost(String instagramReportPost) {
		this.instagramReportPost = instagramReportPost;
	}

	public String getIdInstagramUser() {
		return idInstagramUser;
	}

	public void setIdInstagramUser(String idInstagramUser) {
		this.idInstagramUser = idInstagramUser;
	}
	
}
