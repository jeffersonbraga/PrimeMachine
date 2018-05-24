package br.com.opsocial.ejb.entity.groups;

import java.io.Serializable;
import java.util.List;

import br.com.opsocial.ejb.entity.application.Profile;

public class GroupProfilesHelper implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private List<Profile> profiles;
    private Groups group;
    
	public List<Profile> getProfiles() {
		return profiles;
	}
	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
	public Groups getGroup() {
		return group;
	}
	public void setGroup(Groups group) {
		this.group = group;
	}
}
