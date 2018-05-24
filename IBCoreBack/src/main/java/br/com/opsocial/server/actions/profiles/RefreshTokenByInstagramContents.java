package br.com.opsocial.server.actions.profiles;

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

@RestController
@RequestMapping("woopsocial")
public class RefreshTokenByInstagramContents {

	@CrossOrigin
	@RequestMapping(value = "/refresh_tokenby_instagram_contents",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doAction(HttpSession session) throws Exception, OptimisticLockException {
		List<Object> l = new ArrayList<Object>();
		
		if(session.getAttribute("alr_rec_instagram") != null) {
			if(((Boolean) session.getAttribute("alr_rec_instagram"))) {
				l.add(session.getAttribute("message_instagram"));
				l.add(session.getAttribute("profile_instagram"));
				
				session.removeAttribute("profile_instagram");
				session.removeAttribute("message_instagram");
				session.removeAttribute("alr_rec_instagram");
			}
		}
		
		return new ResponseEntity<>(l, HttpStatus.OK);
	}

}
