package br.com.opsocial.server.utils.instagram.entities;

import java.io.Serializable;

public class Children implements Serializable {

	private static final long serialVersionUID = -7555739700914946802L;
	
	private String id;
	private String mediaUrl;
	private String permalink;
	
	public Children() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMediaUrl() {
		return mediaUrl;
	}

	public void setMediaUrl(String mediaUrl) {
		this.mediaUrl = mediaUrl;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}
	
	
}
