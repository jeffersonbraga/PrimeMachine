package br.com.opsocial.server.utils.streams;

import java.io.Serializable;

public class ActionLikeAux implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private StreamPost streamPost;
	private StreamPostComment streamPostComment;
	private Long idProfile;
	
	public ActionLikeAux() {
		
	}

	public StreamPost getStreamPost() {
		return streamPost;
	}

	public void setStreamPost(StreamPost streamPost) {
		this.streamPost = streamPost;
	}

	

	public StreamPostComment getStreamPostComment() {
		return streamPostComment;
	}

	public void setStreamPostComment(StreamPostComment streamPostComment) {
		this.streamPostComment = streamPostComment;
	}

	public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}
	
}
