package br.com.opsocial.server.utils.streams;

import java.io.Serializable;

public class StreamConversationsAux implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private StreamPostPaging streamPostPaging;
	private StreamPostPaging streamPostPagingAux;
	private Long idProfile;
	
	public StreamConversationsAux() {
		
	}

	public StreamPostPaging getStreamPostPaging() {
		return streamPostPaging;
	}

	public void setStreamPostPaging(StreamPostPaging streamPostPaging) {
		this.streamPostPaging = streamPostPaging;
	}

	public Long getIdProfile() {
		return idProfile;
	}

	public void setIdProfile(Long idProfile) {
		this.idProfile = idProfile;
	}

	public StreamPostPaging getStreamPostPagingAux() {
		return streamPostPagingAux;
	}

	public void setStreamPostPagingAux(StreamPostPaging streamPostPagingAux) {
		this.streamPostPagingAux = streamPostPagingAux;
	}
}
