package br.com.opsocial.server.actions.feeds;

import javax.persistence.OptimisticLockException;
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
public class DeleteStream {
	
	@CrossOrigin
	@RequestMapping(value = "/delete_stream",
    method = RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Stream> doAction(Long idStream, HttpSession session, HttpServletRequest request) throws Exception, OptimisticLockException {
		
		MaintenanceStreamRemote remote = (MaintenanceStreamRemote) RecoverMaintenance.recoverMaintenance("Stream");
		
		User user = SecurityUtils.getCurrentUser();
		
		Stream stream = remote.getById(idStream);
		
		if(user.getIdUser().equals(stream.getTab().getUser().getIdUser())) {

			remote.delete(stream);
			
			return new ResponseEntity<>(stream, HttpStatus.OK);
		}else{
			return null;
		}
	}
}
