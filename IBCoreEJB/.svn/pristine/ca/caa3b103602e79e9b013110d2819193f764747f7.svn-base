package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.GooglePagingDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.google.GooglePaging;

@Stateless
public class MaintenanceGooglePagingBean extends AbstractDASImpl<GooglePaging> implements MaintenanceGooglePagingRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	GooglePagingDAO dao;
	
	@Override
	public void setDao(AbstractDAO<GooglePaging> dao) {
		this.dao = (GooglePagingDAO) dao;
	}

	@Override
	public AbstractDAO<GooglePaging> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<GooglePaging> getDAO() {
		return dao;
	}

	@Override
	public GooglePaging getEntity(Long idMonitoring, String term, Character network) {
		return dao.getEntity(idMonitoring, term, network);
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

}
