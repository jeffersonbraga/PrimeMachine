package br.com.opsocial.client.entity.instagram;

import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;

public class InstagramPostMonitoringDTO extends PostMonitoringDTO {
	
	private static final long serialVersionUID = 1L;

	public static final Character POSITIVE = 'P';
	public static final Character NEUTER = 'S';
	public static final Character NEGATIVE = 'N';

	private InstagramPostDTO instagramPostDTO;
	private Character garbage;
	
	public InstagramPostMonitoringDTO() {
	}

	public InstagramPostDTO getInstagramPostDTO() {
		return instagramPostDTO;
	}

	public void setInstagramPostDTO(InstagramPostDTO instagramPostDTO) {
		this.instagramPostDTO = instagramPostDTO;
	}

	public Character getGarbage() {
		return garbage;
	}

	public void setGarbage(Character garbage) {
		this.garbage = garbage;
	}
}
