package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.Profile;

@Remote
public interface MaintenanceProfileRemote extends AbstractDAS<Profile> {
	
	public List<Profile> getEntityByNetworkType(Character type);

	public Profile getEntityByNetworkId(String networkId, Long idAccount) throws IllegalArgumentException;
	
	public Profile getFirstProfileInserted(Long idAccount); 
	
	public List<Profile> getEntityByNetworkType(List<Character> types, Long idAccount); 
	
	public List<Profile> getEntityByNetworkType(Character type, Long idAccount); 
	
	public List<Profile> getEntitiesByProfileInformation(String profileInformation, Long idAccount, Long idProfile);
	
	public List<Profile> getEntitiesByProfileInformation(String profileInformation, Long idAccount);
	
	public List<Profile> getChildrenByProfile(Long idProfile);
	
	public Boolean hasFacebookProfileInTurn();

	public Profile getFacebookProfileTurn();
	
	public List<Profile> listByAccount(Long idAccount);
	
	public List<Profile> listProfilesByActiveAccount();
	
	public List<Profile> listProfilesByAccount(Long idAccount);
	
	public List<Profile> listActivesByType(Character type);
	
	public List<Profile> listInactivesByType(Character type);
	
	public List<Profile> listInactivesByType(Character type, Date date); 
	
	public List<Profile> listByIdMonitoringForGroups(Character type, Long idMonitoring);
	
	public List<Profile> listGroupsWithNoMonitoring(Long beforeInsertionDate);
	
	public List<Profile> listInstagramProfilesToReconnect(Long idAccount);
	
	public List<Profile> listInstagramProfilesToAuthenticate(Long idAccount);

	public List<Profile> listInvalidTokenProfiles(Long idAccount, char invalidToken);
	
	public List<Profile> listInvalidTokenProfiles();
}
