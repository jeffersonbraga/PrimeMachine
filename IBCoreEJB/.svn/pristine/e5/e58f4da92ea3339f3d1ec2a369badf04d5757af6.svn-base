package br.com.opsocial.ejb.entity.instagram;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="instagrampostscomments", schema="opsocial")
public class InstagramPostComment extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idcomment")
	private String idComment;
	
	@JoinColumn(name = "idmedia", referencedColumnName = "idmedia")
	@ManyToOne()
	private InstagramReportPost instagramReportPost;
	
	@Column(name="idinstagramuser")
	private String idInstagramUser;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="fullname")
	private String fullName;
	
	@Column(name="profilepicture")
	private String profilePicture;
	
	@Column(name="createtime")
	private Long createTime;
	
	@Column(name="text")
	private String text;
	
	public InstagramPostComment() {
		
	}

	public String getIdComment() {
		return idComment;
	}

	public void setIdComment(String idComment) {
		this.idComment = idComment;
	}

	public InstagramReportPost getInstagramReportPost() {
		return instagramReportPost;
	}

	public void setInstagramReportPost(InstagramReportPost instagramReportPost) {
		this.instagramReportPost = instagramReportPost;
	}

	public String getIdInstagramUser() {
		return idInstagramUser;
	}

	public void setIdInstagramUser(String idInstagramUser) {
		this.idInstagramUser = idInstagramUser;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
