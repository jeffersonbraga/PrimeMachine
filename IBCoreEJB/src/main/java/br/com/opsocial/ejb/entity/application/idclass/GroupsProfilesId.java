package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GroupsProfilesId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "idgroup")
	private Long groups;
	
	@Column(name = "idprofile")
	private Long profiles;

	public Long getGroups() {
		return groups;
	}

	public void setGroups(Long groups) {
		this.groups = groups;
	}

	public Long getProfiles() {
		return profiles;
	}

	public void setProfiles(Long profiles) {
		this.profiles = profiles;
	}
}
