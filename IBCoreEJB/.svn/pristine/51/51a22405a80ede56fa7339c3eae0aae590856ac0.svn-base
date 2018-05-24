package br.com.opsocial.ejb.entity.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.idclass.ProfileUserDashboardId;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name = "profilesusersdashboard", schema = "opsocial")
@IdClass(ProfileUserDashboardId.class)
public class ProfileUserDashboard extends Persistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@ManyToOne()
	@JoinColumn(name="idprofile")
	private Profile profile;
	
	@Id
	@ManyToOne()
	@JoinColumn(name="iduser")
	private User user;
	
	@Column(name = "insertiondate")
	private Long insertionDate;
	
	public ProfileUserDashboard() {
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getInsertionDate() {
		return insertionDate;
	}

	public void setInsertionDate(Long insertionDate) {
		this.insertionDate = insertionDate;
	}
	
}
