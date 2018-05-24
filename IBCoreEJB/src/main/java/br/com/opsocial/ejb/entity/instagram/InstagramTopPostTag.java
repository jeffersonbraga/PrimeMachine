package br.com.opsocial.ejb.entity.instagram;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class InstagramTopPostTag extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private String tag;
	private Long likes;
	private Long comments;
	private Long interactions;
	private Double interactionsPercentage;
	
	public InstagramTopPostTag() {
		
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	public Long getInteractions() {
		return interactions;
	}

	public void setInteractions(Long interactions) {
		this.interactions = interactions;
	}

	public Double getInteractionsPercentage() {
		return interactionsPercentage;
	}

	public void setInteractionsPercentage(Double interactionsPercentage) {
		this.interactionsPercentage = interactionsPercentage;
	}
	
}
