package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.SuggestionDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.Suggestion;

@Stateless
public class MaintenanceSuggestionBean extends AbstractDASImpl<Suggestion> implements MaintenanceSuggestionRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	SuggestionDAO dao;  

	@Override
	public AbstractDAO<Suggestion> getDAO() {
		return dao;
	}
	
	@Override
	public void setDao(AbstractDAO<Suggestion> dao) {
		this.dao = (SuggestionDAO) dao;
	}

	@Override
	public AbstractDAO<Suggestion> getDao() {
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
