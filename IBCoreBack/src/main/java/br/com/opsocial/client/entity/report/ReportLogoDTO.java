package br.com.opsocial.client.entity.report;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class ReportLogoDTO extends Persistent {

	private static final long serialVersionUID = 1L;

	private Long objectId;
	private Character type;
	private String logo;
	private Integer width;
	private Integer height;
	private String footerText;
	
	public static final Character MONITORING = 'M';
	public static final Character FACEBOOK = 'F';
	public static final Character TWITTER = 'T';
	public static final Character INSTAGRAM = 'I';
	
	public ReportLogoDTO() {
		
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
