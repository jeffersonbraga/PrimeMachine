package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.TwitterPostURLDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.twitter.TwitterPostURL;

@Stateless
public class MaintenanceTwitterPostURLBean extends AbstractDASImpl<TwitterPostURL> implements MaintenanceTwitterPostURLRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	TwitterPostURLDAO dao;
	
	@Override
	public TwitterPostURLDAO getDAO() {
		return dao;
	}
	
	@Override
	public void setDao(AbstractDAO<TwitterPostURL> dao) {
		this.dao = (TwitterPostURLDAO) dao;
	}

	@Override
	public AbstractDAO<TwitterPostURL> getDao() {
		return (TwitterPostURLDAO) dao;
	}

	@Override
	public List<TwitterPostURL> listByStatusId(Long statusId) {
		return dao.listByStatusId(statusId);
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
