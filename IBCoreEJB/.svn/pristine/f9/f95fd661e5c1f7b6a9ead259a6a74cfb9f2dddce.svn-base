package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.groups.GroupsProfiles;

@Remote
public interface MaintenanceGroupsProfilesRemote extends AbstractDAS<GroupsProfiles> {

	List<Object> getByGroup(Long idGroup);
	Integer deleteNotNecessaryProfiles(Long idGroup, List<Profile> listProfilesToAdd);
	GroupsProfiles save(GroupsProfiles groupsProfiles) throws Exception;
}
