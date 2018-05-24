package br.com.opsocial.server.actions.application;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.AuthFacebookCallbackServlet;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;
import facebook4j.Facebook;

@RestController
@RequestMapping("woopsocial")
public class ReAuthFacebookAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/re_auth_facebook",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		Profile profileDTO = (Profile) getParameters().get("profile");
		getRequest().getSession().setAttribute("reauth", profileDTO);
		getRequest().getSession().setAttribute("facebook_auth_action", AuthFacebookCallbackServlet.REAUTH_TOKEN);
		
		Facebook facebook = new FacebookIntegration().getFacebookSystem();
		facebook.setOAuthPermissions(FacebookIntegration.APP_PERMISSIONS);

		getRequest().getSession().setAttribute("facebook", facebook);

		String callbackURL = OpSocialBackApplication.SERVER_PATH + "/woopsocial/authFacebookCallback";
		
		getParameters().put("authenticationurl", new StringUtil(facebook.getOAuthAuthorizationURL(callbackURL.toString())));
	}

}
