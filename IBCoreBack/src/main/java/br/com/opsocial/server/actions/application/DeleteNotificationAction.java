package br.com.opsocial.server.actions.application;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceNotificationRemote;
import br.com.opsocial.ejb.entity.application.Notification;

@RestController
@RequestMapping("woopsocial")
public class DeleteNotificationAction extends ServerAction {

	@Override
	@RequestMapping(value = "/delete_notification",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception {
		
		Long notificationId = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("notificationId")).getValue();

		MaintenanceNotificationRemote remote = (MaintenanceNotificationRemote) RecoverMaintenance.recoverMaintenance("Notification");
		
		Notification notification = remote.getById(notificationId); 
		
		remote.delete(notification);
	}

}
