package br.com.opsocial.server.actions.profiles;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.primitive.Boolean;
import br.com.opsocial.client.entity.primitive.PersistentList;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.AuthFacebookCallbackServlet;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.networksintegrations.FacebookIntegration;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.generic.Persistent;
import facebook4j.Facebook;

@RestController
@RequestMapping("woopsocial")
public class ChangeAdminFacebookAction extends ServerAction {

	@Override
	@RequestMapping(value = "/change_admin_facebook",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		getSession().setAttribute("facebook_auth_action", AuthFacebookCallbackServlet.CHANGE_ADMIN_TOKEN);
		getSession().setAttribute("alr_rec_facebook", new Boolean(false));
		getSession().setAttribute("profiles", (PersistentList<Profile>) getParameters().get("profiles"));
		
		Facebook facebook = new FacebookIntegration().getFacebookSystem();
		facebook.setOAuthPermissions(FacebookIntegration.APP_PERMISSIONS);
		
		getRequest().getSession().setAttribute("facebook", facebook);
		
		String callbackURL = OpSocialBackApplication.SERVER_PATH + "/woopsocial/authFacebookCallback";
		
		StringUtil oauthURL =  new StringUtil(facebook.getOAuthAuthorizationURL(callbackURL.toString()));
		
		getParameters().put("authenticationurl", oauthURL);
	}

}
