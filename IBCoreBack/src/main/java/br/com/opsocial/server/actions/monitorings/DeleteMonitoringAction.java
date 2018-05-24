package br.com.opsocial.server.actions.monitorings;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.application.MonitoringDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@RestController
@RequestMapping("woopsocial")
public class DeleteMonitoringAction extends ServerAction {

	private Boolean wasDeleted;
	
	@Override
	@RequestMapping(value = "/delete_monitoring",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception {
		
		MonitoringDTO monitoringDTO = (MonitoringDTO) getParameters().get("monitoring");
		
		MaintenanceMonitoringRemote remote = (MaintenanceMonitoringRemote) RecoverMaintenance.recoverMaintenance("Monitoring");
		
		final Monitoring monitoring = remote.getById(monitoringDTO.getIdMonitoring());
		
		wasDeleted = true;
		
		try {
			
			monitoring.setState(Monitoring.DELETED);
			remote.save(monitoring);
			
		} catch (Exception e) {
			wasDeleted = false;
			e.printStackTrace();
		}
		
		getParameters().put("wasDeleted", new br.com.opsocial.client.entity.primitive.Boolean(wasDeleted));
	}
}
