package br.com.opsocial.server.utils.reports.linkedin;

public class LinkedinStatusUpdate {

	private Long clickCount;
	private Long likeCount;
	private Long time;
	private Long impressionCount;
	private Long commentCount;
	private Long shareCount;
	
	public Long getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Long commentCount) {
		this.commentCount = commentCount;
	}

	public Long getShareCount() {
		return shareCount;
	}

	public void setShareCount(Long shareCount) {
		this.shareCount = shareCount;
	}

	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}

	public Long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(Long likeCount) {
		this.likeCount = likeCount;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public Long getImpressionCount() {
		return impressionCount;
	}

	public void setImpressionCount(Long impressionCount) {
		this.impressionCount = impressionCount;
	}

	public LinkedinStatusUpdate() {}
}
