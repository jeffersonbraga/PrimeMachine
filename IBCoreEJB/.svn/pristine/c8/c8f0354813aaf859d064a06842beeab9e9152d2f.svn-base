package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.FacebookInfluentialUser;

@Remote
public interface MaintenanceFacebookInfluentialUserRemote extends AbstractDAS<FacebookInfluentialUser> {

	public List<FacebookInfluentialUser> getInfUsersWithAppSocopedUserId(String pageId, Integer limit);
	public List<FacebookInfluentialUser> getInfluentialFansByPage(String pageId, Character isVerified, Integer limit);
	public List<FacebookInfluentialUser> getMostInfluentialFans(String pageId, Integer offset, Integer limit);

	public boolean deleteFacebookInfluentialUsers(String pageId);
}
