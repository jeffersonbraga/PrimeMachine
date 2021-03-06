package br.com.opsocial.ejb.entity.linkedin;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="linkedinposts", schema="opsocial")
public class LinkedinPost extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "postid")
	private String postId;
	
	@Column(name = "networkid")
	private String networkId;

	@Column(name = "iscommentable")
	private Boolean isCommentable;
	
	@Column(name = "isliked")
	private Boolean isLiked;
	
	@Column(name = "totalcomments")
	private Long totalComments;
	
	@Column(name = "numlikes")
	private Long numLikes;
	
	@Column(name = "postcomment")
	private String postComment;
	
	@Column(name = "posteyebrowurl")
	private String postEyebrowUrl;
	
	@Column(name = "postsubmittedurl")
	private String postSubmittedUrl;
	
	@Column(name = "postsubmittedimageurl")
	private String postSubmittedImageUrl;
	
	@Column(name = "postdescription")
	private String postDescription;
	
	@Column(name = "posttitle")
	private String postTitle;
	
	@Column(name = "postthumbnailurl")
	private String postThumbnailUrl;
	
	@Column(name = "posttime")
	private Long postTime;
	
	@Column(name = "islikable")
	private Boolean isLikable;
	
	@Column(name = "updatetype")
	private String updateType;

	public String getPostId() {
		return postId;
	}

	public void setPostId(String postId) {
		this.postId = postId;
	}

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

	public Boolean getIsCommentable() {
		return isCommentable;
	}

	public void setIsCommentable(Boolean isCommentable) {
		this.isCommentable = isCommentable;
	}

	public Boolean getIsLiked() {
		return isLiked;
	}

	public void setIsLiked(Boolean isLiked) {
		this.isLiked = isLiked;
	}

	public Long getTotalComments() {
		return totalComments;
	}

	public void setTotalComments(Long totalComments) {
		this.totalComments = totalComments;
	}

	public Long getNumLikes() {
		return numLikes;
	}

	public void setNumLikes(Long numLikes) {
		this.numLikes = numLikes;
	}

	public String getPostComment() {
		return postComment;
	}

	public void setPostComment(String postComment) {
		this.postComment = postComment;
	}

	public String getPostEyebrowUrl() {
		return postEyebrowUrl;
	}

	public void setPostEyebrowUrl(String postEyebrowUrl) {
		this.postEyebrowUrl = postEyebrowUrl;
	}

	public String getPostSubmittedUrl() {
		return postSubmittedUrl;
	}

	public void setPostSubmittedUrl(String postSubmittedUrl) {
		this.postSubmittedUrl = postSubmittedUrl;
	}

	public String getPostSubmittedImageUrl() {
		return postSubmittedImageUrl;
	}

	public void setPostSubmittedImageUrl(String postSubmittedImageUrl) {
		this.postSubmittedImageUrl = postSubmittedImageUrl;
	}

	public String getPostDescription() {
		return postDescription;
	}

	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}

	public String getPostTitle() {
		return postTitle;
	}

	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}

	public Long getPostTime() {
		return postTime;
	}

	public void setPostTime(Long postTime) {
		this.postTime = postTime;
	}

	public String getPostThumbnailUrl() {
		return postThumbnailUrl;
	}

	public void setPostThumbnailUrl(String postThumbnailUrl) {
		this.postThumbnailUrl = postThumbnailUrl;
	}

	public Boolean getIsLikable() {
		return isLikable;
	}

	public void setIsLikable(Boolean isLikable) {
		this.isLikable = isLikable;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public LinkedinPost() {}
}
