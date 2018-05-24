package br.com.opsocial.server.actions.application;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceNotificationRemote;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class ListNotificationsAction  extends ServerAction{

	@CrossOrigin
	@RequestMapping(value = "/list_notifications",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doNewAction() throws Exception, OptimisticLockException {

		User user 		=  SecurityUtils.getCurrentUser();
		List<Object> l = new ArrayList<Object>();
		if (user != null) {

			Long idAccount 	= user.getAccount().getIdAccount();
			MaintenanceNotificationRemote remote = (MaintenanceNotificationRemote) RecoverMaintenance.recoverMaintenance("Notification");
			List<Notification> notifications = remote.listByAccount(idAccount);

			l.add(notifications);
		}

		return new ResponseEntity<>(l, HttpStatus.OK);	
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub
	}
}