package br.com.opsocial.server.utils.facebook.entities;

import java.io.Serializable;

public class Attachment implements Serializable {

	private static final long serialVersionUID = 8899670465777741734L;
	
	private String id;
	private String mimeType;
	private Integer size;
	private String name;
	
	public Attachment() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
