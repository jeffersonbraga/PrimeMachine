package br.com.opsocial.server.utils.streams;

import java.io.Serializable;

public class ActionCommentAux implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private StreamPostComment streamPostComment;
	private StreamPost streamPost;
	private Long idProfile;
	private String commentId;
	
	public ActionCommentAux() {
	
	}

	public StreamPostComment getStreamPostComment() {
		return streamPostComment;
	}

	public void setStreamPostComment(StreamPostComment streamPostComment) {
		this.streamPostComment = streamPostComment;
	}

	public StreamPost getStreamPost() {
		return streamPost;
	}

	public void setStreamPost(StreamPost streamPost) {
		this.streamPost = streamPost;
	}

	public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}
}
