package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.groups.GroupsRssfeeds;

@Local
public interface GroupsRssfeedsDAO extends AbstractDAO<GroupsRssfeeds> {

	List<Object> getByGroup(Long idGroup);
	GroupsRssfeeds save(GroupsRssfeeds groupsRssfeeds) throws Exception;
	void deleteByRssFeedAndGroup(Long idGroup, Long idRssfeed);
}
