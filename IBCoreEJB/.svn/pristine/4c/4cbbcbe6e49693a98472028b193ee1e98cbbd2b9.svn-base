package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.rssfeed.RssFeed;

@Local
public interface RssFeedDAO extends AbstractDAO<RssFeed> {

	RssFeed getAllOnGroupByDate(Long idGroup);

	void deleteByTitle(String title);
}
