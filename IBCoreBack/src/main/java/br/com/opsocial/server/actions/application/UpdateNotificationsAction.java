package br.com.opsocial.server.actions.application;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceNotificationRemote;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class UpdateNotificationsAction {

	@RequestMapping(value = "/update_notifications",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction(@Valid @RequestBody Notification notification) {
	
		MaintenanceNotificationRemote notificationRemote = (MaintenanceNotificationRemote) 
				RecoverMaintenance.recoverMaintenance("Notification");

		notification.setUnread(false);
		
		try {
			notificationRemote.save(notification);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
