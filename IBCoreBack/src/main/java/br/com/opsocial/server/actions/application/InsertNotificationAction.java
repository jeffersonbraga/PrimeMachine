package br.com.opsocial.server.actions.application;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;

@RestController
@RequestMapping("woopsocial")
public class InsertNotificationAction extends ServerAction {
	
	private User user;

	@CrossOrigin
	@RequestMapping(value = "/add_notification",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction(@Valid @RequestBody Notification notification, HttpSession session, HttpServletRequest request) throws Exception, OptimisticLockException {
		
		user =  SecurityUtils.getCurrentUser();
				
		UtilFunctions.addNotification(notification.getMessage(), user.getAccount().getIdAccount(), notification.getColor(), notification.getAction());
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub
		
	}

}
