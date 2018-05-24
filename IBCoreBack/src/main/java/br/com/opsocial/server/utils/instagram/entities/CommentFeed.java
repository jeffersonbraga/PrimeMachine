package br.com.opsocial.server.utils.instagram.entities;

import java.io.Serializable;
import java.util.List;

public class CommentFeed implements Serializable {

	private static final long serialVersionUID = -1430931366016003939L;
	
	private List<Comment> data;
	
	public CommentFeed() {
	}

	public List<Comment> getData() {
		return data;
	}

	public void setData(List<Comment> data) {
		this.data = data;
	}
	

}
