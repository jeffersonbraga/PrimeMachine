package br.com.opsocial.server.utils.facebook.entities;

import java.io.Serializable;

public class Share implements Serializable {

	private static final long serialVersionUID = 289063072089300971L;
	
	private String id;
	private String name;
	private String link;
	private String description;
	
	public Share() {
	
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
