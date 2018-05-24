package br.com.opsocial.client.entity.socialnetworks.facebook;

import java.util.List;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class CommentsDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	List<CommentDTO> comments;
	FacebookPaging paging;
	
	public CommentsDTO() {
		
	}

	public List<CommentDTO> getComments() {
		return comments;
	}

	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

	public FacebookPaging getPaging() {
		return paging;
	}

	public void setPaging(FacebookPaging paging) {
		this.paging = paging;
	}
	
}
