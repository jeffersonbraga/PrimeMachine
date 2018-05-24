package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class InstagramFollowerId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="idprofile")
	private String idProfile;
	
	@Column(name="followerid")
	private String followerId;
	
	public InstagramFollowerId() {
		
	}

	public String getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(String idProfile) {
		this.idProfile = idProfile;
	}

	public String getFollowerId() {
		return followerId;
	}

	public void setFollowerId(String followerId) {
		this.followerId = followerId;
	}
	
}
