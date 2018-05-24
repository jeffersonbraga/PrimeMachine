package br.com.opsocial.client.entity.socialnetworks.facebook;

import java.io.Serializable;

public class AttachmentDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public final static String VIDEO = "video_inline";
	public final static String GIF = "animated_image_video";
	
	private String description;
	private MediaDTO media;
	private String title;
	private String type;
	private String url;
	
	public AttachmentDTO() {
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MediaDTO getMedia() {
		return media;
	}

	public void setMedia(MediaDTO media) {
		this.media = media;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

//	public AttachmentDTO[] getSubattachments() {
//		return subattachments;
//	}
//
//	public void setSubattachments(AttachmentDTO[] subattachments) {
//		this.subattachments = subattachments;
//	}
}
