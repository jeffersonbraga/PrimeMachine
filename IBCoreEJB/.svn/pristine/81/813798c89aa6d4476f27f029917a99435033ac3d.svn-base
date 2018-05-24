package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TwitterFollowerId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "followerid")
	private Long followerId;
	
	@Column(name = "idprofile")
	private Long idProfile;
	
	public TwitterFollowerId() {
	}
	
	public Long getFollowerId() {
		return followerId;
	}

	public void setFollowerId(Long followerId) {
		this.followerId = followerId;
	}

	public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}

}
