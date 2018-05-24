package br.com.opsocial.ejb.entity.socialnetworks.twitter;

import java.util.List;

public class Url {

	private String displayUrl;
	private String url;
	private List<Integer> indices;
	
	public Url() {
	}

	public String getDisplayUrl() {
		return displayUrl;
	}

	public void setDisplayUrl(String displayUrl) {
		this.displayUrl = displayUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public List<Integer> getIndices() {
		return indices;
	}

	public void setIndices(List<Integer> indices) {
		this.indices = indices;
	}

}
