package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.AudienceContactDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.AudienceContact;

@Stateless
public class MaintenanceAudienceContactBean extends AbstractDASImpl<AudienceContact> implements MaintenanceAudienceContactRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	AudienceContactDAO dao;
	
	@Override
	public void setDao(AbstractDAO<AudienceContact> dao) {
		this.dao = (AudienceContactDAO) dao;
	}

	@Override
	public AbstractDAO<AudienceContact> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<AudienceContact> getDAO() {
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
