package br.com.opsocial.ejb.das;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.rssfeed.RssFeed;

@Remote
public interface MaintenanceRssFeedRemote extends AbstractDAS<RssFeed> {

	RssFeed getAllOnGroupByDate(Long idGroup);

	void deleteByTitle(String title);
}
