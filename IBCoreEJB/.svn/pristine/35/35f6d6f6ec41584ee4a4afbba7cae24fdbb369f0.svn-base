package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.feed.Tab;

@Local
public interface TabDAO extends AbstractDAO<Tab> {

	List<Tab> getTabsByUser(Long idUser);

	Tab getInitialTab(Long idUser);

}
