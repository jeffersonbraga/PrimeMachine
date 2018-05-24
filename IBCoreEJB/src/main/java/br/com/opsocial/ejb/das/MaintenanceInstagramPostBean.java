package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.InstagramPostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramPost;

@Stateless
public class MaintenanceInstagramPostBean extends AbstractDASImpl<InstagramPost> implements MaintenanceInstagramPostRemote  {

	private static final long serialVersionUID = 1L;

	@EJB
	private InstagramPostDAO dao;  

	@Override
	public AbstractDAO<InstagramPost> getDAO() {
		return dao;
	}
	
	@Override
	public InstagramPost getByIdMedia(Long idMedia) {
		return dao.getByIdMedia(idMedia);
	}

	@Override
	public void setDao(AbstractDAO<InstagramPost> dao) {
		this.dao = (InstagramPostDAO) dao;
	}

	@Override
	public AbstractDAO<InstagramPost> getDao() {
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
