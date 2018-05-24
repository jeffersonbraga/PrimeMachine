package br.com.opsocial.server.actions.profiles;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.server.services.ServerAction;

@RestController
@RequestMapping("woopsocial")
public class DeleteExpiredProfilesAction extends ServerAction {

	@Override
	@RequestMapping(value = "/delete_expired_profiles",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		getContext().removeAttribute("expiredProfiles" + getUser().getAccount().getIdAccount());
	}

}
