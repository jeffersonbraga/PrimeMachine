package br.com.opsocial.server.actions.profiles.linkedin;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.http.client.utils.URIBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.server.services.AuthFacebookCallbackServlet;

@RestController
@RequestMapping("woopsocial")
public class RefreshTokenByLinkedInAction {

	@CrossOrigin
	@RequestMapping(value = "/refresh_tokenby_linkedin",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> doAction(@Valid @RequestBody Profile profile, HttpSession session, HttpServletRequest request) throws Exception, OptimisticLockException {

		URIBuilder builder = new URIBuilder("https://www.linkedin.com/oauth/v2/authorization");

		builder.addParameter("response_type", "code");
		builder.addParameter("client_id", InsertByLinkedInAction.CLIENT_ID);
		builder.addParameter("redirect_uri", "http://localhost:8080/OpSocialBack/woopsocial/authLinkedInCallback");
		builder.addParameter("state", "a1z2x77777773s4c5");
		builder.addParameter("scope", InsertByLinkedInAction.APP_PERMISSIONS);
		
		String location = builder.toString();

		JsonObject response = new JsonObject();
	    response.addProperty("authenticationUrl", location);
	    
		session.setAttribute("linkedin_auth_action", AuthFacebookCallbackServlet.REFRESH_TOKEN);
		session.setAttribute("alr_rec_linkedin", new Boolean(false));
		session.setAttribute("profile_linkedin", profile);
		
		String authenticationURL = response.toString();
		
		return new ResponseEntity<String>(location, HttpStatus.OK);
	}
}
