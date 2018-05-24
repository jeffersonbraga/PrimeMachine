package br.com.opsocial.client.entity.socialnetworks.youtube;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class StatisticsDTO extends Persistent {
	
	private static final long serialVersionUID = 1L;

	private Long views;	
	private Long likes;
	private Long dislikes;

	public StatisticsDTO() {
	}

	public Long getViews() {
		return views;
	}

	public void setViews(Long views) {
		this.views = views;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public Long getDislikes() {
		return dislikes;
	}

	public void setDislikes(Long dislikes) {
		this.dislikes = dislikes;
	}
}
