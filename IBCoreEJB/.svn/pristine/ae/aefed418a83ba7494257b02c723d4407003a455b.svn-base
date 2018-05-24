package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.User;

@Stateless
public class UserDAOImpl extends AbstractDAOImpl<User> implements UserDAO {

	@Override
	public User getUser(String where) {
		
		sql = "SELECT us FROM User us WHERE "+ where.trim();
		
		query = em.createQuery(sql);
		
		try {
			return (User) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	public User getByLoginPassword(String login, String password) throws IllegalArgumentException {
		
		List<User> users = getByFullSQL("select c from User c where c.email  = '" + login + "' and c.password = '" + password + "'");
		
        if (users == null || users.isEmpty()) {
            return null;
        } else {
            User user = users.get(0);
            return user;
        }
    }
	
	@Override
	public User getByEmail(String email) throws IllegalArgumentException {
		
		String where = "us.email = '"+email+"'";
		
		sql = "SELECT us FROM User us WHERE "+ where.trim();
		
		query = em.createQuery(sql);
		
		try {
			return (User) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public User getByUrlActivation(String url) throws IllegalArgumentException {
		
		String where = "us.urlActivation = '"+url+"' AND us.active ='F'";
		
		sql = "SELECT us FROM User us WHERE "+ where.trim();
		
		query = em.createQuery(sql);
		
		try {
			return (User) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<User> listUsersByAccount(Long idAccount, Character type, Long currentUser) {

		sql = "SELECT u FROM User u WHERE u.account.idAccount = :idAccount and u.type = :type and u.idUser <> :currentUser";
		
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		query.setParameter("type", type);
		query.setParameter("currentUser", currentUser);
		
		return query.getResultList();
	}

	@Override
	public List<User> listUsersByAccount(Long idAccount) {

		sql = "SELECT u FROM User u WHERE u.account.idAccount = :idAccount";
		
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		
		return query.getResultList();
	}
	
	@Override
	public List<User> listActiveUsersByAccount(Long idAccount) {

		sql = "SELECT u FROM User u WHERE u.account.idAccount = :idAccount AND u.active = :active";
		
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		query.setParameter("active", User.ACTIVE);
		
		return query.getResultList();
	}

	@Override
	public List<User> listUsersByAccount(Long idAccount, Character type) {
		
		sql = "SELECT u FROM User u WHERE u.account.idAccount = :idAccount and u.type = :type";
		
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		query.setParameter("type", type);
		
		return query.getResultList();
	}
}
