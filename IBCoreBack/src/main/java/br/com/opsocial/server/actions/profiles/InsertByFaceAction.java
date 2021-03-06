package br.com.opsocial.server.actions.profiles;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonObject;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.ejb.entity.generic.Persistent;
import br.com.opsocial.server.services.AuthFacebookCallbackServlet;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import facebook4j.Facebook;

@RestController
@RequestMapping("woopsocial")
public class InsertByFaceAction {

	@CrossOrigin
	@RequestMapping(value = "/insertby_face",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> doAction(HttpSession session) throws Exception {

		session.setAttribute("facebook_auth_action", AuthFacebookCallbackServlet.GET_ACCESS_TOKEN);
		session.setAttribute("alr_rec_facebook", new Boolean(false));

		Facebook facebook = new FacebookIntegration().getFacebookSystem();
		facebook.setOAuthPermissions(FacebookIntegration.APP_PERMISSIONS);
		
		session.setAttribute("facebookSystem", facebook);

		String callbackURL = OpSocialBackApplication.SERVER_PATH + "/woopsocial/authFacebookCallback";

		JsonObject response = new JsonObject();
	    response.addProperty("authenticationUrl", facebook.getOAuthAuthorizationURL(callbackURL.toString()));
		
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}

}
