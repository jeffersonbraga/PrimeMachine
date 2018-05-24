package br.com.opsocial.client.entity.report.facebook;

import java.util.List;

import br.com.opsocial.client.entity.application.TableOrder;
import br.com.opsocial.ejb.entity.generic.Persistent;

public class ReportFacebookPostToPostDTO extends Persistent {

	private static final long serialVersionUID = 1L;

	private Long dateFrom;
	private Long dateUntil;
	
	private List<FaceReportPostDTO> posts;
	private TableOrder postsOrder;
	
	public final static Integer POSTS_PER_CALL = 50;
	
	public ReportFacebookPostToPostDTO() {
	}

	public Long getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Long dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Long getDateUntil() {
		return dateUntil;
	}

	public void setDateUntil(Long dateUntil) {
		this.dateUntil = dateUntil;
	}

	public List<FaceReportPostDTO> getPosts() {
		return posts;
	}

	public void setPosts(List<FaceReportPostDTO> posts) {
		this.posts = posts;
	}

	public TableOrder getPostsOrder() {
		return postsOrder;
	}

	public void setPostsOrder(TableOrder postsOrder) {
		this.postsOrder = postsOrder;
	}
	
}
