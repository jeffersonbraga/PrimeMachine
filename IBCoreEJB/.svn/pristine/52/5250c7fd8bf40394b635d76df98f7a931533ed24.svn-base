package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProfileUserDashboardId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "iduser")
	private Long user;
	
	@Column(name = "idprofile")
	private Long profile;
	
	public ProfileUserDashboardId() {
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getProfile() {
		return profile;
	}

	public void setProfile(Long profile) {
		this.profile = profile;
	}

}
