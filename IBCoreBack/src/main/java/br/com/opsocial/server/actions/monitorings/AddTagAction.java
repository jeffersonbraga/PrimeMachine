package br.com.opsocial.server.actions.monitorings;

import java.util.HashMap;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.monitoring.MonitoringTagDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagRemote;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTag;

@RestController
@RequestMapping("woopsocial")
public class AddTagAction extends ServerAction {

	@Override
	@RequestMapping(value = "/add_tag",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		MonitoringTagDTO monitoringTagDTO = (MonitoringTagDTO) getParameters().get("monitoringTag");

		setParameters(new HashMap<String, Object>());
		
		MaintenanceMonitoringTagRemote monitoringTagRemote = (MaintenanceMonitoringTagRemote) 
				RecoverMaintenance.recoverMaintenance("MonitoringTag");
		
		MonitoringTag monitoringTag = monitoringTagRemote.save(MountDTO.mountMonitoringTag(monitoringTagDTO));
		
		getParameters().put("monitoringTag", MountDTO.mountMonitoringTag(monitoringTag));
	}

}
