package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.Notification;

@Remote
public interface MaintenanceNotificationRemote extends AbstractDAS<Notification> {

	public Notification getByProfile(Long idProfile);
	
	public List<Notification> listByAccount(Long idAccount);
	
	public List<Notification> listUnreadNotifications(Long idAccount);
	
    public List<Notification> listLastNotifications(Long idAccount, Date dateFrom, Integer limit);
	
	public List<Notification> listNextNotifications(Long idAccount, Date dateFrom, Integer limit);
	
	public Long countUnreadNotifications(Long idAccount);
}
