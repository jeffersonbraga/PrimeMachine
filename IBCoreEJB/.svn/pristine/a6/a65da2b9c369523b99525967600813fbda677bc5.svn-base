package br.com.opsocial.ejb.entity.application.idclass;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReportLogoId implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name = "objectid")
	private Long objectId;
	
	@Column(name = "type")
	private Character type;
	
	public ReportLogoId() {
		
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public Character getType() {
		return type;
	}

	public void setType(Character type) {
		this.type = type;
	}
}
