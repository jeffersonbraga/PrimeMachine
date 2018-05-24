package br.com.opsocial.server.utils.streams;

import java.io.Serializable;

import br.com.opsocial.client.entity.socialnetworks.facebook.AttachmentDTO;
import br.com.opsocial.client.entity.socialnetworks.facebook.FromDTO;

public class StreamPostComment implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Long createdTime;
	private FromDTO from;
	private String commentId;
	private Integer likeCount;
	private Integer commentCount;
	private String message;
	private String type;
	private Boolean userLikes;
	private AttachmentDTO attachment; 
	private StreamPostComments replies;
	
	public StreamPostComment() {
	
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
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

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
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

	public Boolean getUserLikes() {
		return userLikes;
	}

	public void setUserLikes(Boolean userLikes) {
		this.userLikes = userLikes;
	}

	public AttachmentDTO getAttachment() {
		return attachment;
	}

	public void setAttachment(AttachmentDTO attachment) {
		this.attachment = attachment;
	}

	public StreamPostComments getReplies() {
		return replies;
	}

	public void setReplies(StreamPostComments replies) {
		this.replies = replies;
	}

}
