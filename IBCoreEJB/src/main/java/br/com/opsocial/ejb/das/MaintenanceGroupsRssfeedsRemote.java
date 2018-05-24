package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.groups.GroupsRssfeeds;

@Remote
public interface MaintenanceGroupsRssfeedsRemote extends AbstractDAS<GroupsRssfeeds> {

	List<Object> getByGroup(Long idGroup);
	GroupsRssfeeds save(GroupsRssfeeds groupsRssfeeds) throws Exception;
	void deleteByRssFeedAndGroup(Long idGroup, Long idRssfeed);
}
