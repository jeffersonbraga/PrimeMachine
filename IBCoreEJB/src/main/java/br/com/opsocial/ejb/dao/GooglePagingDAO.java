package br.com.opsocial.ejb.dao;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.google.GooglePaging;

@Local
public interface GooglePagingDAO extends AbstractDAO<GooglePaging> {

	public GooglePaging getEntity(Long idMonitoring, String term, Character network);
	
}
