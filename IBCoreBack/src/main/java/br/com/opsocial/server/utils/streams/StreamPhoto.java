package br.com.opsocial.server.utils.streams;

import java.io.Serializable;

public class StreamPhoto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String url;
	private String title;
	private String type;
	
	public StreamPhoto() {
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
