package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.StreamDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.feed.Stream;

@Stateless
public class MaintenanceStreamBean extends AbstractDASImpl<Stream> implements MaintenanceStreamRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	StreamDAO dao;

	@Override
	public void setDao(AbstractDAO<Stream> dao) {
		this.dao = (StreamDAO) dao;
	}

	@Override
	public AbstractDAO<Stream> getDao() {
		return dao;
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
	public AbstractDAO getDAO() {
		return dao;
	}

	@Override
	public List<Stream> getStreamsByUser(Long idUser) {
		return dao.getStreamsByUser(idUser);
	}

	@Override
	public Long getMaxOrderByTab(Long idTab) {
		return dao.getMaxOrderByTab(idTab);
	}

}
