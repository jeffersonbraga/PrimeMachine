package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.NotificationDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.Notification;

@Stateless
public class MaintenanceNotificationBean extends AbstractDASImpl<Notification> implements MaintenanceNotificationRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	NotificationDAO dao; 

	@Override
	public AbstractDAO<Notification> getDAO() {
		return dao;
	} 

	@Override
	public void setDao(AbstractDAO<Notification> dao) {
		this.dao = (NotificationDAO) dao;
	}

	@Override
	public AbstractDAO<Notification> getDao() {
		return dao;
	}

	@Override
	public Notification getByProfile(Long idProfile) {
		return dao.getByProfile(idProfile);
	}
	
	@Override
	public List<Notification> listByAccount(Long idAccount) {
		return dao.listByAccount(idAccount);
	}

	@Override
	public Long countUnreadNotifications(Long idAccount) {
		return dao.countUnreadNotifications(idAccount);
	}

	@Override
	public List<Notification> listLastNotifications(Long idAccount, Date dateFrom, Integer limit) {
		return dao.listLastNotifications(idAccount, dateFrom, limit);
	}

	@Override
	public List<Notification> listNextNotifications(Long idAccount, Date dateFrom, Integer limit) {
		return dao.listNextNotifications(idAccount, dateFrom, limit);
	}

	@Override
	public List<Notification> listUnreadNotifications(Long idAccount) {
		return dao.listUnreadNotifications(idAccount);
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
