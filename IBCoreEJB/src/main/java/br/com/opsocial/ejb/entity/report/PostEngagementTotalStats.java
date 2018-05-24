package br.com.opsocial.ejb.entity.report;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class PostEngagementTotalStats extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private Long totalEngagement;
	private Long likes;
	private Long shares;
	private Long comments;
	
	public PostEngagementTotalStats() {
	}

	public Long getTotalEngagement() {
		return totalEngagement;
	}

	public void setTotalEngagement(Long totalEngagement) {
		this.totalEngagement = totalEngagement;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getShares() {
		return shares;
	}

	public void setShares(Long shares) {
		this.shares = shares;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

}
