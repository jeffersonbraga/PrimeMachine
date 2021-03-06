package br.com.opsocial.server.actions.feeds;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceTabRemote;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.feed.Tab;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class ShowFeedsAction {
	
	@CrossOrigin
	@RequestMapping(value = "/show_feeds_action",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Tab>> doAction(HttpSession session, HttpServletRequest request) throws Exception {

		MaintenanceTabRemote remote = (MaintenanceTabRemote) RecoverMaintenance.recoverMaintenance("Tab");
		
		User user = SecurityUtils.getCurrentUser();
		
		List<Tab> tabs = remote.getTabsByUser(user.getIdUser());
				
		return new ResponseEntity<>(tabs, HttpStatus.OK);	
	}
}
