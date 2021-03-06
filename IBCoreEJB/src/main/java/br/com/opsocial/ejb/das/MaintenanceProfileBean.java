package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ProfileDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.Profile;

@Stateless
public class MaintenanceProfileBean extends AbstractDASImpl<Profile> implements MaintenanceProfileRemote {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	ProfileDAO dao; 

	@Override
	public AbstractDAO<Profile> getDAO() { 
		return dao;
	} 

	@Override
	public List<Profile> getEntityByNetworkType(Character type) {
		return dao.getEntityByNetworkType(type);
	}
	
	@Override
	public Profile getEntityByNetworkId(String networkId, Long idAccount) throws IllegalArgumentException {
		return dao.getEntityByNetworkId(networkId, idAccount);
	}

	@Override
	public Profile getFirstProfileInserted(Long idAccount) {
		return dao.getFirstProfileInserted(idAccount);
	}
	
	@Override
	public List<Profile> getEntityByNetworkType(Character type, Long idAccount) {
		return dao.getEntityByNetworkType(type, idAccount);
	}

	@Override
	public List<Profile> getEntityByNetworkType(List<Character> types, Long idAccount) {
		return dao.getEntityByNetworkType(types, idAccount);
	}

	@Override
	public List<Profile> getEntitiesByProfileInformation(
			String profileInformation, Long idAccount, Long idProfile) {
		return dao.getEntitiesByProfileInformation(profileInformation, idAccount, idProfile);
	}

	@Override
	public List<Profile> getEntitiesByProfileInformation(
			String profileInformation, Long idAccount) {
		return dao.getEntitiesByProfileInformation(profileInformation, idAccount);
	}

	@Override
	public List<Profile> getChildrenByProfile(Long idProfile) {
		return dao.getChildrenByProfile(idProfile);
	}

	@Override
	public void setDao(AbstractDAO<Profile> dao) {
		this.dao = (ProfileDAO) dao;	
	}

	@Override
	public AbstractDAO<Profile> getDao() {
		return dao;
	}

	@Override
	public Boolean hasFacebookProfileInTurn() {
		return dao.hasFacebookProfileInTurn();
	}

	@Override
	public Profile getFacebookProfileTurn() {
		return dao.getFacebookProfileTurn();
	}

	@Override
	public List<Profile> listByAccount(Long idAccount) {
		return dao.listByAccount(idAccount);
	}

	@Override
	public List<Profile> listProfilesByActiveAccount() {
		return dao.listProfilesByActiveAccount();
	}

	@Override
	public List<Profile> listProfilesByAccount(Long idAccount) {
		return dao.listProfilesByAccount(idAccount);
	}

	@Override
	public List<Profile> listActivesByType(Character type) {
		return dao.listActivesByType(type);
	}

	@Override
	public List<Profile> listInactivesByType(Character type) {
		return dao.listInactivesByType(type);
	}

	@Override
	public List<Profile> listInactivesByType(Character type, Date date) {
		return dao.listInactivesByType(type, date);
	}

	@Override
	public List<Profile> listByIdMonitoringForGroups(Character type,
			Long idMonitoring) {
		return dao.listByIdMonitoringForGroups(type, idMonitoring);
	}

	@Override
	public List<Profile> listGroupsWithNoMonitoring(Long beforeInsertionDate) {
		return dao.listGroupsWithNoMonitoring(beforeInsertionDate);
	}

	@Override
	public List<Profile> listInstagramProfilesToReconnect(Long idAccount) {
		return dao.listInstagramProfilesToReconnect(idAccount);
	}

	@Override
	public List<Profile> listInstagramProfilesToAuthenticate(Long idAccount) {
		return dao.listInstagramProfilesToAuthenticate(idAccount);
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

	@Override
	public List<Profile> listInvalidTokenProfiles(Long idAccount, char invalidToken) {
		return dao.listInvalidTokenProfiles(idAccount, invalidToken);
	}
	
	@Override
	public List<Profile> listInvalidTokenProfiles() {
		return dao.listInvalidTokenProfiles();
	}

}
