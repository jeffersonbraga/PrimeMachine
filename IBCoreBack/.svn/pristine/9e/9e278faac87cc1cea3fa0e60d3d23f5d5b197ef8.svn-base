package br.com.opsocial.server.actions.monitorings;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.networksintegrations.TwitterIntegration;
import br.com.opsocial.ejb.entity.application.Profile;

@RestController
@RequestMapping("woopsocial")
public class TwitterInteractionsAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/twitter_interactions",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
	
		Profile profileDTO = (Profile) getParameters().get("profile");
		String interactionType = ((StringUtil) getParameters().get("interactionType")).getValue();
		
		if(interactionType.equals("quote")) {

			String text = ((StringUtil) getParameters().get("text")).getValue();
			
			TwitterIntegration twitterIntegration = new TwitterIntegration();
			Twitter twitter = twitterIntegration.getTwitter(profileDTO.getToken(), profileDTO.getTokenSecret());
			twitter.updateStatus(text);
			
		} else if(interactionType.equals("reply")) {
			
			String text = ((StringUtil) getParameters().get("text")).getValue();
			
			TwitterIntegration twitterIntegration = new TwitterIntegration();
			Twitter twitter = twitterIntegration.getTwitter(profileDTO.getToken(), profileDTO.getTokenSecret());
			
			Long statusId = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("statusId")).getValue();
			
			StatusUpdate statusUpdate = new StatusUpdate(text);
			statusUpdate.inReplyToStatusId(statusId);
			
			twitter.updateStatus(statusUpdate);
			
		} else if(interactionType.equals("retweet")) {
			TwitterIntegration twitterIntegration = new TwitterIntegration();
			Twitter twitter = twitterIntegration.getTwitter(profileDTO.getToken(), profileDTO.getTokenSecret());
			
			Long statusId = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("statusId")).getValue();
			
			twitter.retweetStatus(statusId);
		} 
	}
}
