package br.com.opsocial.server.actions.application;

import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceNotificationRemote;
import br.com.opsocial.ejb.entity.application.Notification;

@RestController
@RequestMapping("woopsocial")
public class UpdateNextNotificationsAction extends ServerAction {

	@Override
	@RequestMapping(value = "/update_next_notifications",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {

		MaintenanceNotificationRemote notificationRemote = (MaintenanceNotificationRemote) 
				RecoverMaintenance.recoverMaintenance("Notification");

		Date dateNextNotification = ((br.com.opsocial.client.entity.primitive.Date) getParameters().get("dateNextNotification")).getValue();

		// Get New Interactions
		List<Notification> nextNotifications = 
				notificationRemote.listNextNotifications(SecurityUtils.getCurrentUser().getAccount().getIdAccount(), dateNextNotification, 5);

		getParameters().put("nextNotifications", nextNotifications);
	}
}