package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.TimezoneDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.Timezone;

@Stateless
public class MaintenanceTimezoneBean extends AbstractDASImpl<Timezone> implements MaintenanceTimezoneRemote{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	TimezoneDAO dao;

	@Override
	public void setDao(AbstractDAO<Timezone> dao) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AbstractDAO<Timezone> getDao() {
		// TODO Auto-generated method stub
		return this.dao;
	}

	@Override
	public AbstractDAO getDAO() {
		// TODO Auto-generated method stub
		return this.dao;
	}

	@Override
	public List<Timezone> listTimezones() {
		return dao.listTimezones();
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