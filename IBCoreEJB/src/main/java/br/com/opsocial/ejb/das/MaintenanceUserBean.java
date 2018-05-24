package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.UserDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.User;

@Stateless
public class MaintenanceUserBean extends AbstractDASImpl<User> implements MaintenanceUserRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	UserDAO dao; 
	
	@Override
	public AbstractDAO<User> getDAO() {
		return dao;
	}
	
	public User getByLoginPassword(String login, String password) throws IllegalArgumentException {
		return dao.getByLoginPassword(login, password);
    }	
	
	@Override
	public User save(User user) throws IllegalArgumentException, Exception {
		return super.save(user);
	}

	@Override
	public User getByEmail(String email) throws IllegalArgumentException {
		return dao.getByEmail(email);
	}

	@Override
	public void setDao(AbstractDAO<User> dao) {
		this.dao = (UserDAO) dao;
		
	}

	@Override
	public AbstractDAO<User> getDao() {
		return dao;
	}

	@Override
	public List<User> listUsersByAccount(Long idAccount, Character type, Long currentUser) {
		return dao.listUsersByAccount(idAccount, type, currentUser);
	}

	@Override
	public List<User> listUsersByAccount(Long idAccount) {
		return dao.listUsersByAccount(idAccount);
	}

	@Override
	public List<User> listUsersByAccount(Long idAccount, Character type) {
		return dao.listUsersByAccount(idAccount, type);
	}
    
    @Override
    public void setIdUserSession(Long idUserSession){
    	this.idUserSession = idUserSession;
    }
	
    @Override
	public Long getIdUserSession(){
		return this.idUserSession;
	}

	@Override
	public User getByUrlActivation(String url) throws IllegalArgumentException {
		return dao.getByUrlActivation(url);
	}

	@Override
	public List<User> listActiveUsersByAccount(Long idAccount) {
		return dao.listActiveUsersByAccount(idAccount);
	}
}