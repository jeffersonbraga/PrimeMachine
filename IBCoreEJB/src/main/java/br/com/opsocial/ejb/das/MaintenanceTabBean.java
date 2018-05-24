package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.TabDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.feed.Tab;

@Stateless
public class MaintenanceTabBean extends AbstractDASImpl<Tab> implements MaintenanceTabRemote {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@EJB
	TabDAO dao;

	@Override
	public void setDao(AbstractDAO<Tab> dao) {
		this.dao = (TabDAO) dao;
		
	}

	@Override
	public AbstractDAO<Tab> getDao() {
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
	public List<Tab> getTabsByUser(Long idUser){
		return dao.getTabsByUser(idUser);
	}

	@Override
	public Tab getInitialTab(Long idUser) {
		return dao.getInitialTab(idUser);
	}

}
