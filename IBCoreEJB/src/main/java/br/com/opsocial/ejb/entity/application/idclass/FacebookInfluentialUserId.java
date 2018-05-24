package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FacebookInfluentialUserId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "userid")
	private String userId;
	
	@Column(name = "pageid")
	private String pageId;
	
	public FacebookInfluentialUserId() {
		
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

}
