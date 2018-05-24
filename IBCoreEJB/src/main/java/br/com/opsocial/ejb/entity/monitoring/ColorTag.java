package br.com.opsocial.ejb.entity.monitoring;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="colortags", schema="opsocial")
public class ColorTag extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "idcolortag")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "colortags_idcolortag_seq")
	private Long idColorTag;
	
	@Column(name="innercolor")
	private String innerColor;
	
	@Column(name="innerbordercolor")
	private String innerBorderColor;
	
	@Column(name="bordercolor")
	private String borderColor;
	
	@Column(name="hovercolor")
	private String hoverColor;
	
	public ColorTag() {
	}

	public Long getIdColorTag() {
		return idColorTag;
	}

	public void setIdColorTag(Long idColorTag) {
		this.idColorTag = idColorTag;
	}
	
	@Override
	public Long getId() {
		return idColorTag;
	}
	
	@Override
	public void setId(Long id) {
		idColorTag = id;
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
