package br.com.opsocial.ejb.entity.socialnetworks.facebook;

public class Comment {
	
	private String id;
	private String message;
	private int createdTime;
	private int likeCount = 0;
	private int commentCount = 0;
	private User from;
	private StoryAttachment attachment;
	
	public Comment() {
		
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(int createdTime) {
		this.createdTime = createdTime;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public StoryAttachment getAttachment() {
		return attachment;
	}

	public void setAttachment(StoryAttachment attachment) {
		this.attachment = attachment;
	}

}
