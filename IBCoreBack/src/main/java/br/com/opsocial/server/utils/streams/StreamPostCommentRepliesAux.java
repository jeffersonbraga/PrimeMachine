package br.com.opsocial.server.utils.streams;

import java.io.Serializable;

public class StreamPostCommentRepliesAux implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private StreamPostPaging streamPostPaging;
	private StreamPostComment streamPostComment;
	private Long idProfile;
	
	public StreamPostCommentRepliesAux() {
	}

	public StreamPostPaging getStreamPostPaging() {
		return streamPostPaging;
	}

	public void setStreamPostPaging(StreamPostPaging streamPostPaging) {
		this.streamPostPaging = streamPostPaging;
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
