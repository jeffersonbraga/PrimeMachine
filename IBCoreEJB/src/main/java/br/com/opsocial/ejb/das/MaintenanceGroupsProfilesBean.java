package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.GroupsProfilesDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.groups.GroupsProfiles;

@Stateless
public class MaintenanceGroupsProfilesBean extends AbstractDASImpl<GroupsProfiles> implements MaintenanceGroupsProfilesRemote {
	
	private static final long serialVersionUID = 1L;

	@EJB
	GroupsProfilesDAO dao;

	@Override
	public void setDao(AbstractDAO<GroupsProfiles> dao) {
		this.dao = (GroupsProfilesDAO) dao;
	}

	@Override
	public AbstractDAO<GroupsProfiles> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<GroupsProfiles> getDAO() {
		return dao;
	}

	@Override
	public List<Object> getByGroup(Long idGroup) {
		return dao.getByGroup(idGroup);
	}

	@Override
	public Integer deleteNotNecessaryProfiles(Long idGroup, List<Profile> listProfilesToAdd) {
		return dao.deleteNotNecessaryProfiles(idGroup, listProfilesToAdd);
	}

	@Override
	public GroupsProfiles save(GroupsProfiles groupsProfiles) throws Exception {
		return dao.save(groupsProfiles);
	}

	@Override
	public void setIdUserSession(Long idUserSession) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getIdUserSession() {
		// TODO Auto-generated method stub
		return null;
	}
}
