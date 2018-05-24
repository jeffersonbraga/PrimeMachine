package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ReclameAquiPostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPost;

@Stateless
public class MaintenanceReclameAquiPostBean extends AbstractDASImpl<ReclameAquiPost> implements MaintenanceReclameAquiPostRemote{
	
private static final long serialVersionUID = 1L;
	
	@EJB
	ReclameAquiPostDAO dao;  

	@Override
	public AbstractDAO<ReclameAquiPost> getDAO() {
		return dao;
	}

	@Override
	public void setDao(AbstractDAO<ReclameAquiPost> dao) {
		this.dao = (ReclameAquiPostDAO) dao;
	}

	@Override
	public AbstractDAO<ReclameAquiPost> getDao() {
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
