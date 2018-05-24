package br.com.opsocial.server.actions.application;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.primitive.BitlyShortenUrlDTO;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.BitlyClient;

@RestController
@RequestMapping("woopsocial")
public class ShortenLinksAction extends ServerAction {

	@RequestMapping(value = "/shorten_links",
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<BitlyShortenUrlDTO>> makeUrlShortner(@Valid @RequestBody List<BitlyShortenUrlDTO> urlsToShorten) throws Exception {

		List<BitlyShortenUrlDTO> shortenedUrls = new ArrayList<BitlyShortenUrlDTO>();
		try {

			BitlyClient bitlyClient = new BitlyClient();
			for(BitlyShortenUrlDTO urlToShorten : urlsToShorten) {
				if(!urlToShorten.isShortened()) {
					shortenedUrls.add(bitlyClient.shortenUrl(urlToShorten));
				} else {
					shortenedUrls.add(urlToShorten);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(shortenedUrls, HttpStatus.OK);
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub
		
	}

}
