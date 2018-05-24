package br.com.opsocial.server.actions.monitorings;

import java.util.HashMap;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.application.MonitoringDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.monitorings.MonitoringBlogsNewsPosts;
import br.com.opsocial.server.utils.tasks.MonitoringController;
import br.com.opsocial.server.utils.tasks.MonitoringController.TaskMonitoring;
import br.com.opsocial.server.utils.tasks.ReclameAquiPostController;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@RestController
@RequestMapping("woopsocial")
public class ChangeMonitoringStateAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/change_monitoring_state",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
	
		final MonitoringDTO monitoringDTO = (MonitoringDTO) getParameters().get("monitoring");
				
		MaintenanceMonitoringRemote remote = (MaintenanceMonitoringRemote) RecoverMaintenance.recoverMaintenance("Monitoring");
		
		final MonitoringController controller = (MonitoringController) getSession().getServletContext().getAttribute("monitoring_controller");
				
		if(monitoringDTO.getState().equals(MonitoringDTO.PAUSED)) {
			
			TaskMonitoring taskToCancel = controller.getMappedTasks().get(monitoringDTO.getIdMonitoring());
			
			controller.getMappedTasks().remove(monitoringDTO.getIdMonitoring());
			controller.getAlreadyMonitoring().remove(monitoringDTO.getIdMonitoring());
			taskToCancel.cancel();

		} else {

			Monitoring monitoring = MountDTO.mountMonitoring(monitoringDTO);
			
			controller.startMonitoring(monitoring, 0);
			
			ReclameAquiPostController.savePosts(monitoring);
			new MonitoringBlogsNewsPosts(monitoring).savePostsWebHoseAPI();
		}
		
		final Monitoring monitoring = remote.getById(monitoringDTO.getIdMonitoring());
		monitoring.setState(monitoringDTO.getState());
		
		remote.save(monitoring);
		
		setParameters(new HashMap<String,Object>());
	}
}
