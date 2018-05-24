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

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.server.services.ServerAction;

@RestController
@RequestMapping("woopsocial")
public class RecoverNetworksContents extends ServerAction {
	
	@CrossOrigin
	@RequestMapping(value = "/get_profiles_to_connect",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doAction(HttpSession session) throws Exception {
		List<Object> l = new ArrayList<Object>();
		
		List<Profile> profilesToAdd = (List<Profile>) session.getAttribute("profilesToAdd");
		
		if(session.getAttribute("profilesToAdd") != null){
			l.add(profilesToAdd);
		}
		
		session.setAttribute("profile", null);
		session.setAttribute("message", null);
		session.setAttribute("profilesToAdd", null);
		
		return new ResponseEntity<>(l, HttpStatus.OK);
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		
	}
}
