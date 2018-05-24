package br.com.opsocial.client.entity.reclameaqui;

import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;

public class ReclameAquiPostMonitoringDTO extends PostMonitoringDTO {
	
	private static final long serialVersionUID = 1L;
	
	private ReclameAquiPostDTO reclameAquiPostDTO;
	private Character garbage;
	
	public ReclameAquiPostMonitoringDTO() {
	}

	public ReclameAquiPostDTO getReclameAquiPostDTO() {
		return reclameAquiPostDTO;
	}

	public void setReclameAquiPostDTO(ReclameAquiPostDTO reclameAquiPostDTO) {
		this.reclameAquiPostDTO = reclameAquiPostDTO;
	}

	public Character getGarbage() {
		return garbage;
	}

	public void setGarbage(Character garbage) {
		this.garbage = garbage;
	}
}
