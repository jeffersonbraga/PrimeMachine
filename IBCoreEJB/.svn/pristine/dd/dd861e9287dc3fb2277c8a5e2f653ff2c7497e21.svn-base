package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.GroupDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.groups.Groups;

@Stateless
public class MaintenanceGroupBean extends AbstractDASImpl<Groups> implements MaintenanceGroupRemote {
	
	private static final long serialVersionUID = 1L;

	@EJB
	GroupDAO dao;

	@Override
	public void setDao(AbstractDAO<Groups> dao) {
		this.dao = (GroupDAO) dao;
	}

	@Override
	public AbstractDAO<Groups> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<Groups> getDAO() {
		return dao;
	}

	@Override
	public List<Groups> listGroupsByAccount(Long idAccount, Character groupType) {
		return dao.listGroupsByAccount(idAccount, groupType);
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
