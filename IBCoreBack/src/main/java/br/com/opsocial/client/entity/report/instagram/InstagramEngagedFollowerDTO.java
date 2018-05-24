package br.com.opsocial.client.entity.report.instagram;

import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.ejb.entity.instagram.InstagramFollower;

public class InstagramEngagedFollowerDTO extends Persistent {

	private static final long serialVersionUID = 1L;
	
	private InstagramFollowerDTO instagramFollower;
	private Long likes;
	private Long comments;
	
	public InstagramEngagedFollowerDTO() {
		
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

	public InstagramFollowerDTO getInstagramFollower() {
		return instagramFollower;
	}

	public void setInstagramFollower(InstagramFollowerDTO instagramFollower) {
		this.instagramFollower = instagramFollower;
	}

}