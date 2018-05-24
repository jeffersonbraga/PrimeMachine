package br.com.opsocial.server.utils.streams;

import java.io.Serializable;

public class StreamPostCommentsAux implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private StreamPostPaging streamPostPaging;
	private StreamPost streamPost;
	private Long idProfile;
	
	public StreamPostCommentsAux() {

	}

	public StreamPostPaging getStreamPostPaging() {
		return streamPostPaging;
	}

	public void setStreamPostPaging(StreamPostPaging streamPostPaging) {
		this.streamPostPaging = streamPostPaging;
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
	
	
}
