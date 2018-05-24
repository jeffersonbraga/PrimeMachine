package br.com.opsocial.ejb.entity.report;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="facereportsposts", schema="opsocial")
public class FaceReportPost extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "postid")
	private String postId;
	
	@Column(name = "pageid")
	private String pageId;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "permalink")
	private String permalink;
	
	@Column(name = "createdtime")
	private Long createdTime;
	
	@Column(name = "likes")
	private Integer likes;
	
	@Column(name = "comments")
	private Integer comments;
	
	@Column(name = "shares")
	private Integer shares;
	
	@Column(name = "lastdateinsights")
	private Long lastDateInsights;
	
	@Column(name = "photosrc")
	private String photoSrc;
	
	@Column(name = "photowidth")
	private Integer photoWidth;
	
	@Column(name = "photoheight")
	private Integer photoHeight;
	
	@Transient
	private Double engagement;
	
	public FaceReportPost() {
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPermalink() {
		return permalink;
	}

	public void setPermalink(String permalink) {
		this.permalink = permalink;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public Integer getComments() {
		return comments;
	}

	public void setComments(Integer comments) {
		this.comments = comments;
	}

	public Integer getShares() {
		return shares;
	}

	public void setShares(Integer shares) {
		this.shares = shares;
	}

	public Long getLastDateInsights() {
		return lastDateInsights;
	}

	public void setLastDateInsights(Long lastDateInsights) {
		this.lastDateInsights = lastDateInsights;
	}

	public String getPhotoSrc() {
		return photoSrc;
	}

	public void setPhotoSrc(String photoSrc) {
		this.photoSrc = photoSrc;
	}

	public Integer getPhotoWidth() {
		return photoWidth;
	}

	public void setPhotoWidth(Integer photoWidth) {
		this.photoWidth = photoWidth;
	}

	public Integer getPhotoHeight() {
		return photoHeight;
	}

	public void setPhotoHeight(Integer photoHeight) {
		this.photoHeight = photoHeight;
	}

	public Double getEngagement() {
		return engagement;
	}

	public void setEngagement(Double engagement) {
		this.engagement = engagement;
	}

	public void setPostInsights(Map<String, Long> postInsightsDashboard) {
		// TODO Auto-generated method stub
		
	}
}
