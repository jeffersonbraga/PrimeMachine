package br.com.opsocial.server.actions.application;

import java.net.URL;
import java.util.HashMap;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.ServerAction;

@RestController
@RequestMapping("woopsocial")
public class UrlBuilderAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/url_builder",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
	
		StringUtil link = (StringUtil) getParameters().get("link");

		try {
			
			URL url = new URL(link.getValue());
			
			setParameters(new HashMap<String, Object>());
			
			getParameters().put("host", new StringUtil(url.getHost()));
			getParameters().put("port", new StringUtil(String.valueOf(url.getPort())));
			getParameters().put("path", new StringUtil(url.getPath()));
		} catch (Exception e) {
			getParameters().put("host", new StringUtil(link.getValue()));
		}
	}
}
