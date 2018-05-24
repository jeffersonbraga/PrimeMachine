package br.com.opsocial.ejb.entity.groups;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.GroupsProfilesId;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name = "groupsprofiles", schema = "opsocial")
@IdClass(GroupsProfilesId.class)
public class GroupsProfiles extends Persistent {

	private static final long serialVersionUID = 1L;

	@Id
	@JoinColumn(name="idgroup")
	private Groups groups;
	
	@Id
	@ManyToOne()
	@JoinColumn(name="idprofile")
	private Profile profiles;

	public Groups getGroups() {
		return groups;
	}

	public void setGroups(Groups groups) {
		this.groups = groups;
	}

	public Profile getProfile() {
		return profiles;
	}

	public void setProfile(Profile profile) {
		this.profiles = profile;
	}
}
