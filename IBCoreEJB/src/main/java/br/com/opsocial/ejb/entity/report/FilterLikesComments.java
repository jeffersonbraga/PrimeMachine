package br.com.opsocial.ejb.entity.report;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class FilterLikesComments extends Persistent {

	private static final long serialVersionUID = 1L;

	private String filter;
	private Long comments;
	private Long likes;
	
	public FilterLikesComments() {
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}
}
