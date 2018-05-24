package br.com.opsocial.ejb.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.application.Notification;

@Local
public interface NotificationDAO extends AbstractDAO<Notification> {

	public Notification getByProfile(Long idProfile);
	
	public List<Notification> listByAccount(Long idAccount);
	
	public List<Notification> listUnreadNotifications(Long idAccount);
	
	public List<Notification> listLastNotifications(Long idAccount, Date dateFrom, Integer limit);
	
	public List<Notification> listNextNotifications(Long idAccount, Date dateFrom, Integer limit);
	
	public Long countUnreadNotifications(Long idAccount);
	
}
