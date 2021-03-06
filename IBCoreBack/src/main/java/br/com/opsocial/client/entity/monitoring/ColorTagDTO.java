package br.com.opsocial.client.entity.monitoring;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class ColorTagDTO extends Persistent {

	private static final long serialVersionUID = 1L;

	private Long idColorTag;
	private String innerColor;
	private String innerBorderColor;
	private String borderColor;
	private String hoverColor;
	
	public ColorTagDTO() {
		
	}

	public Long getIdColorTag() {
		return idColorTag;
	}

	public void setIdColorTag(Long idColorTag) {
		this.idColorTag = idColorTag;
	}

	public String getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(String innerColor) {
		this.innerColor = innerColor;
	}

	public String getInnerBorderColor() {
		return innerBorderColor;
	}

	public void setInnerBorderColor(String innerBorderColor) {
		this.innerBorderColor = innerBorderColor;
	}

	public String getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(String borderColor) {
		this.borderColor = borderColor;
	}

	public String getHoverColor() {
		return hoverColor;
	}

	public void setHoverColor(String hoverColor) {
		this.hoverColor = hoverColor;
	}
	
}
