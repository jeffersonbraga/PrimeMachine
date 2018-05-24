package br.com.opsocial.server.utils.reports.linkedin;

public class LinkedinUpdates {
	
	private boolean isCommentable;
	private String updateKey;
	private boolean isLiked;
	private LinkedinObjects updateComments;
	private Long numLikes;
	private LinkedinUpdateContent updateContent;
	private boolean isLikable;
	private LinkedinObjects likes;
	private Long timestamp;
	private String updateType;
	
	public LinkedinUpdateContent getUpdateContent() {
		return updateContent;
	}

	public void setUpdateContent(LinkedinUpdateContent updateContent) {
		this.updateContent = updateContent;
	}
	
	public LinkedinObjects getUpdateComments() {
		return updateComments;
	}

	public void setUpdateComments(LinkedinObjects updateComments) {
		this.updateComments = updateComments;
	}

	public LinkedinObjects getLikes() {
		return likes;
	}

	public void setLikes(LinkedinObjects likes) {
		this.likes = likes;
	}

	public boolean isCommentable() {
		return isCommentable;
	}

	public void setCommentable(boolean isCommentable) {
		this.isCommentable = isCommentable;
	}

	public String getUpdateKey() {
		return updateKey;
	}

	public void setUpdateKey(String updateKey) {
		this.updateKey = updateKey;
	}

	public boolean isLiked() {
		return isLiked;
	}

	public void setLiked(boolean isLiked) {
		this.isLiked = isLiked;
	}

	public Long getNumLikes() {
		return numLikes;
	}

	public void setNumLikes(Long numLikes) {
		this.numLikes = numLikes;
	}

//	public LinkedinUpdateContent getUpdateContent() {
//		return updateContent;
//	}
//
//	public void setUpdateContent(LinkedinUpdateContent updateContent) {
//		this.updateContent = updateContent;
//	}

	public boolean isLikable() {
		return isLikable;
	}

	public void setLikable(boolean isLikable) {
		this.isLikable = isLikable;
	}

//	public LinkedinLikes getLikes() {
//		return likes;
//	}
//
//	public void setLikes(LinkedinLikes likes) {
//		this.likes = likes;
//	}

	public Long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

//	public LinkedinUpdateComments[] getUpdateComments() {
//		return updateComments;
//	}
//
//	public void setUpdateComments(LinkedinUpdateComments[] updateComments) {
//		this.updateComments = updateComments;
//	}

	public LinkedinUpdates() {}
}
