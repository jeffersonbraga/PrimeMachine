package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.report.FacebookInfluentialUser;

@Local
public interface FacebookInfluentialUserDAO extends AbstractDAO<FacebookInfluentialUser> {

	public List<FacebookInfluentialUser> getInfUsersWithAppSocopedUserId(String pageId, Integer limit);
	public List<FacebookInfluentialUser> getInfluentialFansByPage(String pageId, Character isVerified, Integer limit);
	public List<FacebookInfluentialUser> getMostInfluentialFans(String pageId, Integer offset, Integer limit);
	
	public boolean deleteFacebookInfluentialUsers(String pageId);
}
