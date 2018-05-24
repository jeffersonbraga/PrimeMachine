package br.com.opsocial.server.actions.profiles;

import javax.servlet.ServletException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;

import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.networksintegrations.GoogleIntegration;

@RestController
@RequestMapping("woopsocial")
public class InsertGooglePlusAction extends ServerAction {

	@Override
	@RequestMapping(value = "/insert_googleplus",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception {
		
		try {
			
			StringBuffer callbackURL = getRequest().getRequestURL();
			int index = callbackURL.lastIndexOf("/");
			callbackURL.replace(index, callbackURL.length(), "").append("/authGooglePlusCallback"); 
			
			String authorizationUrl = new GoogleAuthorizationCodeRequestUrl(GoogleIntegration.CLIENT_ID, callbackURL.toString(), 
					GoogleIntegration.SCOPE).setAccessType("offline").build();
			
			getParameters().put("authenticationurl", new StringUtil(authorizationUrl));
			
		} catch (Exception e) {
			throw new ServletException(e);
		}		
	}
}
