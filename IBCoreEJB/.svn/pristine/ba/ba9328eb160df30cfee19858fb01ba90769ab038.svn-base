package br.com.opsocial.ejb.entity.instagram;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="instagramusers", schema="opsocial")
public class InstagramUser extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="idinstagramuser")
	private Long idInstagramUser;
	
	@Column(name="username")
	private String userName;
	
	@Column(name="fullname")
	private String fullName;
	
	@Column(name="profilepicture")
	private String profilePicture;
	
	@Column(name="lastupdatedate")
	private Long lastUpdateDate;
	
	public InstagramUser() {
	}

	public Long getIdInstagramUser() {
		return idInstagramUser;
	}

	public void setIdInstagramUser(Long idInstagramUser) {
		this.idInstagramUser = idInstagramUser;
	}
	
	@Override
	public Long getId() {
		return idInstagramUser;
	}
	
	@Override
	public void setId(Long id) {
		idInstagramUser = id;
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

	public Long getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Long lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
}
