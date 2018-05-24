package br.com.opsocial.server.utils.streams;

import java.io.Serializable;

public class ActionShareAux implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private StreamPost streamPost;
	private String message;
	private Long idProfile;
	
	public ActionShareAux() {
		
	}

	public StreamPost getStreamPost() {
		return streamPost;
	}

	public void setStreamPost(StreamPost streamPost) {
		this.streamPost = streamPost;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}
	
}
