package br.com.opsocial.server.actions.planscontrol;

import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class MonitoringInsertionControlAction extends ServerAction {
	
	@RequestMapping(value = "/monitoring_insertion_control",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction(HttpSession session) throws Exception, OptimisticLockException {
		
		MaintenanceMonitoringRemote remote = (MaintenanceMonitoringRemote) RecoverMaintenance.recoverMaintenance("Monitoring");
		
		User user = SecurityUtils.getCurrentUser();

		Monitoring monitoringDTO = (Monitoring) getParameters().get("monitoring");
		
		List<Monitoring> monitorings = remote.getMonitoringsByAccount(user.getAccount().getIdAccount());
		
		Integer tagsCount = 0;
		
		for(Monitoring monitoring : monitorings) {
			if(monitoringDTO != null) {
				if(!monitoring.getIdMonitoring().equals(monitoringDTO.getIdMonitoring())) {
					tagsCount += monitoring.getTerms().split(";").length;	
				}	
			} else {
				tagsCount += monitoring.getTerms().split(";").length;
			}
		}
		
		if(getParameters().get("terms") != null) {
			String terms = ((StringUtil) getParameters().get("terms")).getValue();
			tagsCount += terms.split(";").length;
		}

		
		if(getUser().getAccount().getPlanType().equals(Account.FREEMIUM) && tagsCount > 5) {
			getParameters().put("monitoringscontrol", new StringUtil(OpSocialBackApplication.messageByLocaleService.getMessage("monitoring.insert.freemiumlimit")));
		} else if(tagsCount > getUser().getAccount().getMonitorings()) {
			String message = OpSocialBackApplication.messageByLocaleService.getMessage("monitoring.insert.planlimit", new Object[]{getUser().getAccount().getMonitorings()});
			getParameters().put("monitoringscontrol", new StringUtil(message));
		}
		
		if(getParameters().get("monitoringscontrol") == null) {	
			getParameters().put("monitoringscontrol", new StringUtil("canInsert"));
		}
		
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub
		
	}

}
