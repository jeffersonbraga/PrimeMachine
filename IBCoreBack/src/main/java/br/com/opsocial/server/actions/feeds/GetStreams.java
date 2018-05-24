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

import br.com.opsocial.ejb.das.MaintenanceStreamRemote;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.feed.Stream;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class GetStreams {

	@CrossOrigin
	@RequestMapping(value = "/get_streams",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Stream>> doAction(HttpSession session, HttpServletRequest request) throws Exception {

		MaintenanceStreamRemote streamRemote = (MaintenanceStreamRemote) RecoverMaintenance.recoverMaintenance("Stream");

		User user = SecurityUtils.getCurrentUser();		
		List<Stream> streams = null;
		if ( (user != null) && (user.getIdUser() != null) ) {
			streams = streamRemote.getStreamsByUser(user.getIdUser());
		}

		return new ResponseEntity<>(streams, HttpStatus.OK);
	}
}
