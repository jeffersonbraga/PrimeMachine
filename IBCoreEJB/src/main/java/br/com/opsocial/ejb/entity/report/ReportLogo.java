package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.ReportLogoId;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name = "reportslogos", schema = "opsocial")
@IdClass(ReportLogoId.class)
public class ReportLogo extends Persistent {

	private static final long serialVersionUID = 1L;
	
	public static final Character MONITORING = 'M';
	public static final Character FACEBOOK = 'F';
	public static final Character TWITTER = 'T';
	
	@Id
	@Column(name = "objectid")
	private Long objectId;
	
	@Id
	@Column(name = "type")
	private Character type;
	
	@Column(name="logo")
	private String logo;
	
	@Column(name="width")
	private Integer width;
	
	@Column(name="height")
	private Integer height;
	
	@Column(name="footertext")
	private String footerText;
	
	public ReportLogo() {
		
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

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getFooterText() {
		return footerText;
	}

	public void setFooterText(String footerText) {
		this.footerText = footerText;
	}

}
