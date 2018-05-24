package br.com.opsocial.client.entity.socialnetworks.facebook;

import java.util.List;

import br.com.opsocial.ejb.entity.generic.Persistent;

public class CommentDTO extends Persistent {

	private static final long serialVersionUID = 1L;

	private String createdTime;
	private FromDTO from;
	private String commentId;
	private Integer likeCount;
	private String message;
	private String type;
	private String userLikes;
	private AttachmentDTO attachment; 
	private List<CommentDTO> replies;
	private FacebookPaging paging;
	
	public CommentDTO() {
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public FromDTO getFrom() {
		return from;
	}

	public void setFrom(FromDTO from) {
		this.from = from;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Integer likeCount) {
		this.likeCount = likeCount;
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

	public String getUserLikes() {
		return userLikes;
	}

	public void setUserLikes(String userLikes) {
		this.userLikes = userLikes;
	}

	public List<CommentDTO> getReplies() {
		return replies;
	}

	public void setReplies(List<CommentDTO> replies) {
		this.replies = replies;
	}

	public AttachmentDTO getAttachment() {
		return attachment;
	}

	public void setAttachment(AttachmentDTO attachment) {
		this.attachment = attachment;
	}

	public FacebookPaging getPaging() {
		return paging;
	}

	public void setPaging(FacebookPaging paging) {
		this.paging = paging;
	}
}
