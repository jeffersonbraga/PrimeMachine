package br.com.opsocial.client.entity.facebook;

import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.ejb.entity.facebook.FacebookPost;

public class FacePostMonitoringDTO extends PostMonitoringDTO {

	private static final long serialVersionUID = 1L;

	private FacebookPost facebookPostDTO;
	private Character garbage;
	private Long idProfile;
	
	public FacePostMonitoringDTO() {
	}

	public FacebookPost getFacebookPostDTO() {
		return facebookPostDTO;
	}

	public void setFacebookPostDTO(FacebookPost facebookPostDTO) {
		this.facebookPostDTO = facebookPostDTO;
	}

	public Character getGarbage() {
		return garbage;
	}

	public void setGarbage(Character garbage) {
		this.garbage = garbage;
	}

	public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}
	
	
}
