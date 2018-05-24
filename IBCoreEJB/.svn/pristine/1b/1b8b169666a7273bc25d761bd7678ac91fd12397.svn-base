package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.GroupsRssfeedsDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.groups.GroupsRssfeeds;

@Stateless
public class MaintenanceGroupsRssfeedsBean extends AbstractDASImpl<GroupsRssfeeds> implements MaintenanceGroupsRssfeedsRemote {
	
	private static final long serialVersionUID = 1L;

	@EJB
	GroupsRssfeedsDAO dao;

	@Override
	public void setDao(AbstractDAO<GroupsRssfeeds> dao) {
		this.dao = (GroupsRssfeedsDAO) dao;
	}

	@Override
	public AbstractDAO<GroupsRssfeeds> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<GroupsRssfeeds> getDAO() {
		return dao;
	}

	@Override
	public GroupsRssfeeds save(GroupsRssfeeds groupsRssfeeds) throws Exception {
		return dao.save(groupsRssfeeds);
	}

	@Override
	public List<Object> getByGroup(Long idGroup) {
		return dao.getByGroup(idGroup);
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
	public void deleteByRssFeedAndGroup(Long idGroup, Long idRssfeed) {
		dao.deleteByRssFeedAndGroup(idGroup, idRssfeed);
	}
}
