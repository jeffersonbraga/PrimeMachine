package br.com.opsocial.ejb.entity.report;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.FacebookInfluentialUserId;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="facebookinfluentialusers", schema="opsocial")
@IdClass(FacebookInfluentialUserId.class)
public class FacebookInfluentialUser extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "userid")
	private String userId;
	
	@Id
	@Column(name = "pageid")
	private String pageId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "comments")
	private Long comments;
	
	@Column(name = "likes")
	private Long likes;
	
	@Column(name = "appscopeduserid")
	private String appScopedUserId;
	
	@Column(name = "isverified")
	private Character isVerified = 'F';
	
	@Column(name = "hasappscopeduserid")
	private Character hasAppScopedUserId = 'F';
	
	public FacebookInfluentialUser() {
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getComments() {
		return comments;
	}

	public void setComments(Long comments) {
		this.comments = comments;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public String getAppScopedUserId() {
		return appScopedUserId;
	}

	public void setAppScopedUserId(String appScopedUserId) {
		this.appScopedUserId = appScopedUserId;
	}

	public Character getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(Character isVerified) {
		this.isVerified = isVerified;
	}

	public Character getHasAppScopedUserId() {
		return hasAppScopedUserId;
	}

	public void setHasAppScopedUserId(Character hasAppScopedUserId) {
		this.hasAppScopedUserId = hasAppScopedUserId;
	}
}
