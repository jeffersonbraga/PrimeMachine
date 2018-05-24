package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.InstagramProfileDeviceDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramProfileDevice;

@Stateless
public class MaintenanceInstagramProfileDeviceBean extends AbstractDASImpl<InstagramProfileDevice> implements MaintenanceInstagramProfileDeviceRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	InstagramProfileDeviceDAO dao;
	
	@Override
	public void setDao(AbstractDAO<InstagramProfileDevice> dao) {
		this.dao = (InstagramProfileDeviceDAO) dao;
	}

	@Override
	public AbstractDAO<InstagramProfileDevice> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<InstagramProfileDevice> getDAO() {
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
