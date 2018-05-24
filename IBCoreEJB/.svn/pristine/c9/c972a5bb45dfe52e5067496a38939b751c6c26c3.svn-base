package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InstagramProfileDeviceId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "instagramuserid")
	private String instagramUserId;
	
	@Column(name = "idaccount")
	private Long account;
	
	public InstagramProfileDeviceId() {
	}

	public String getInstagramUserId() {
		return instagramUserId;
	}

	public void setInstagramUserId(String instagramUserId) {
		this.instagramUserId = instagramUserId;
	}

	public Long getAccount() {
		return account;
	}

	public void setAccount(Long account) {
		this.account = account;
	}

}
