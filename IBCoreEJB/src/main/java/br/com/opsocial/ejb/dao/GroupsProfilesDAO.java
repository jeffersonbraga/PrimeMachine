package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.groups.GroupsProfiles;

@Local
public interface GroupsProfilesDAO extends AbstractDAO<GroupsProfiles> {

	List<Object> getByGroup(Long idGroup);
	Integer deleteNotNecessaryProfiles(Long idGroup, List<Profile> listProfilesToAdd);
	GroupsProfiles save(GroupsProfiles groupsProfiles) throws Exception;
}
