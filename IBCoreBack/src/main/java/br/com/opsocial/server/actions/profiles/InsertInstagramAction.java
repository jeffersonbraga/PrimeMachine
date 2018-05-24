package br.com.opsocial.server.actions.profiles;

import javax.persistence.OptimisticLockException;
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
public class InsertInstagramAction {
	
	@CrossOrigin
	@RequestMapping(value = "/insert_instagram",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> doAction(HttpSession session) throws Exception, OptimisticLockException {	
		
		session.setAttribute("instagram_auth_action", AuthFacebookCallbackServlet.GET_ACCESS_TOKEN);
		session.setAttribute("alr_rec_instagram", new Boolean(false));
		
		// WoopSocial Instagram Authentication
		Facebook facebook = new FacebookIntegration().getFacebookSystem();
		facebook.setOAuthPermissions(FacebookIntegration.APP_PERMISSIONS);
		
		session.setAttribute("facebookSystem", facebook);

		String callbackURL = OpSocialBackApplication.SERVER_PATH + "/woopsocial/instagram";

		JsonObject response = new JsonObject();
	    response.addProperty("authenticationUrl", facebook.getOAuthAuthorizationURL(callbackURL.toString()));
		
//		// OpSocial Instagram Authentication
//		JsonObject response = new JsonObject();
//	    response.addProperty("authenticationUrl", "https://api.instagram.com/oauth/authorize/?client_id=" + 
//				InstagramIntegration.CLIENT_ID + "&redirect_uri=" + Persistent.SERVER_PATH + "/opsocial/instagram&response_type=code&scope=likes+basic+public_content+follower_list+relationships+comments");
		
		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}
}
