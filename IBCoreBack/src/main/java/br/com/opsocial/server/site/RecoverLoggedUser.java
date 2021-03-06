package br.com.opsocial.server.site;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;

@RestController
@RequestMapping("woopsocial")
public class RecoverLoggedUser {
	
	@CrossOrigin
	@RequestMapping(value = "/get_logged_user",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> doAction(HttpSession session, HttpServletRequest request) {
		
		User user 		=  SecurityUtils.getCurrentUser();

		return new ResponseEntity<>(user, HttpStatus.OK);
	}
}
