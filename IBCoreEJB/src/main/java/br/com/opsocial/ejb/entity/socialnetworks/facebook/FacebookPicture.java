package br.com.opsocial.ejb.entity.socialnetworks.facebook;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class FacebookPicture extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private String source;
	private int width;
	private int height;
	
	public final static int MAX_WIDTH_FEEDS = 600;
	
	public FacebookPicture() {
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
}
