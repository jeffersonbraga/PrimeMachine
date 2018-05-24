package br.com.opsocial.server.utils.streams;

import java.io.Serializable;

public class ActionDeleteAux implements Serializable {

	private static final long serialVersionUID = 1L;

	private String postId;
	private String commentId;
	private Long idProfile;
	
	public ActionDeleteAux() {
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}
	
}
