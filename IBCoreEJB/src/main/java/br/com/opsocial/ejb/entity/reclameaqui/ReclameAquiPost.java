package br.com.opsocial.ejb.entity.reclameaqui;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="reclameaquiposts", schema="opsocial")
public class ReclameAquiPost extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "cacheid")
	private String cacheId;
	
	@Column(name = "displaylink")
	private String displayLink;

	@Column(name = "imagelink")
	private String imageLink;

	@Column(name = "imageheight")
	private Integer imageHeight;

	@Column(name = "imagewidth")
	private Integer imageWidth;
	
	@Column(name = "link")
	private String link;

	@Column(name = "snippet")
	private String snippet;

	@Column(name = "title")
	private String title;
	
	@Column(name="createdtime")
	private Long createdTime;
	
	public ReclameAquiPost() {
	}

	public String getCacheId() {
		return cacheId;
	}

	public void setCacheId(String cacheId) {
		this.cacheId = cacheId;
	}

	public String getDisplayLink() {
		return displayLink;
	}

	public void setDisplayLink(String displayLink) {
		this.displayLink = displayLink;
	}

	public String getImageLink() {
		return imageLink;
	}

	public void setImageLink(String imageLink) {
		this.imageLink = imageLink;
	}

	public Integer getImageHeight() {
		return imageHeight;
	}

	public void setImageHeight(Integer imageHeight) {
		this.imageHeight = imageHeight;
	}

	public Integer getImageWidth() {
		return imageWidth;
	}

	public void setImageWidth(Integer imageWidth) {
		this.imageWidth = imageWidth;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getSnippet() {
		return snippet;
	}

	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}
}
