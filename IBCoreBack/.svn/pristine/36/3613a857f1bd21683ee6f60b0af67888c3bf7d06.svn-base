package br.com.opsocial.server.actions.feeds.instagram;

import javax.persistence.OptimisticLockException;

import org.jinstagram.Instagram;
import org.jinstagram.exceptions.InstagramBadRequestException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.client.entity.primitive.Long;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.services.ServicesImpl;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.networksintegrations.InstagramIntegration;
import br.com.opsocial.server.utils.tasks.ValidateTokensController;
import br.com.opsocial.ejb.entity.application.Profile;

@RestController
@RequestMapping("woopsocial")
public class InstagramCommentAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/instagram_comment",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
	
		Long idMedia = (Long) getParameters().get("id_media");
		Profile profileDTO = (Profile) getParameters().get("profile");
		StringUtil comment = (StringUtil) getParameters().get("comment");
		
		InstagramIntegration instagramIntegration = new InstagramIntegration();
		
		Profile profile = MountDTO.mountProfile(profileDTO);
		
		Instagram instagram = instagramIntegration.getInstagram(profile);	

		try {
			
			instagram.setMediaComments(idMedia.getValue().toString(), comment.getValue());
			
		} catch (InstagramBadRequestException e) {
			ValidateTokensController.validatingTokens(profile.getAccount());
			e.printStackTrace();
		}
	}
}
