package br.com.opsocial.client.entity.socialnetworks.facebook;

import java.io.Serializable;

public class MediaDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private ImageDTO image;
	private String videoSrc;
	
	public MediaDTO() {
	}
	
	public MediaDTO(ImageDTO image) {
		this.image = image;
	}

	public ImageDTO getImage() {
		return image;
	}

	public void setImage(ImageDTO image) {
		this.image = image;
	}

	public String getVideoSrc() {
		return videoSrc;
	}

	public void setVideoSrc(String videoSrc) {
		this.videoSrc = videoSrc;
	}
}
