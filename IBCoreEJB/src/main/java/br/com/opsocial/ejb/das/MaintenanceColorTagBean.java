package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ColorTagDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.ColorTag;

@Stateless
public class MaintenanceColorTagBean extends AbstractDASImpl<ColorTag> implements MaintenanceColorTagRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	ColorTagDAO dao;
	
	@Override
	public void setDao(AbstractDAO<ColorTag> dao) {
		this.dao = (ColorTagDAO) dao;
	}

	@Override
	public AbstractDAO<ColorTag> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<ColorTag> getDAO() {
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

}
