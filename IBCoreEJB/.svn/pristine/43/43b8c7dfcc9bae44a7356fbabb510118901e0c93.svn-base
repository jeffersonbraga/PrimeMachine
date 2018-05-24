package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="facereportspostscomments", schema="opsocial")
public class FaceReportPostComment extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "commentid")
	private String commentId;
	
	@JoinColumn(name = "postid", referencedColumnName = "postid")
	@ManyToOne()
	private FaceReportPost faceReportPost;
	
	@Column(name = "userid")
	private String userId;
	
	@Column(name = "username")
	private String userName;
	
	@Column(name = "message")
	private String message;
	
	@Column(name = "createdtime")
	private Long createdTime;
	
	@Column(name = "likes")
	private Integer likes;
	
	@Column(name = "comments")
	private Integer comments;
	
	@Column(name = "attachmenttype")
	private String attachmentType;
	
	@Column(name = "attachmenturl")
	private String attachmentUrl;
	
	public FaceReportPostComment() {
		
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public FaceReportPost getFaceReportPost() {
		return faceReportPost;
	}

	public void setFaceReportPost(FaceReportPost faceReportPost) {
		this.faceReportPost = faceReportPost;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getAttachmentUrl() {
		return attachmentUrl;
	}

	public void setAttachmentUrl(String attachmentUrl) {
		this.attachmentUrl = attachmentUrl;
	}
}
