package br.com.opsocial.server.actions.profiles;

import java.util.ArrayList;
import java.util.List;

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
public class RefreshTokenByFaceContents {

	@CrossOrigin
	@RequestMapping(value = "/refresh_tokenby_face_contents",	
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doAction(HttpSession session) throws Exception {
		List<Object> l = new ArrayList<Object>();

		if(session.getAttribute("alr_rec_facebook") != null) {			
			if(((Boolean) session.getAttribute("alr_rec_facebook"))) {
				l.add(session.getAttribute("message_facebook"));
				l.add(session.getAttribute("profile_facebook"));
				
				session.removeAttribute("message_facebook");	
				session.removeAttribute("alr_rec_facebook");
				session.removeAttribute("profile_facebook");	
			}
		}
		
		return new ResponseEntity<>(l, HttpStatus.OK);
	}

}
