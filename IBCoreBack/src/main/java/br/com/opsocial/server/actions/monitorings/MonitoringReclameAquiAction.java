package br.com.opsocial.server.actions.monitorings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.application.MonitoringDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.client.entity.primitive.Boolean;
import br.com.opsocial.client.entity.primitive.Long;
import br.com.opsocial.client.entity.reclameaqui.ReclameAquiPostMonitoringDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPostMonitoring;

@RestController
@RequestMapping("woopsocial")
public class MonitoringReclameAquiAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/monitoring_reclame_aqui",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		MonitoringDTO monitoring = (MonitoringDTO) getParameters().get("monitoring");
		Long mostRecent = (Long) getParameters().get("mostRecent");
		Long elder = (Long) getParameters().get("elder");
		
		MaintenanceReclameAquiPostMonitoringRemote remote = (MaintenanceReclameAquiPostMonitoringRemote) 
				RecoverMaintenance.recoverMaintenance("ReclameAquiPostMonitoring");
		
		MaintenanceMonitoringPostTagRemote monitoringPostTagRemote = (MaintenanceMonitoringPostTagRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringPostTag");
		
		List<ReclameAquiPostMonitoring> reclameAquiPostMonitorings = new ArrayList<ReclameAquiPostMonitoring>();
		
		// Carregar tags ------------------------------------

		MaintenanceMonitoringTagRemote monitoringTagRemote = (MaintenanceMonitoringTagRemote) RecoverMaintenance.recoverMaintenance("MonitoringTag");
		
		monitoring.setMonitoringTags(MountDTO.mountMonitoringTag(
				monitoringTagRemote.listByMonitoring(monitoring.getIdMonitoring(), monitoring.getAccount().getIdAccount())));
		
		//------------------------------------------------------
		
		if(mostRecent != null) {
			
			if(mostRecent.getValue() != null) {
				reclameAquiPostMonitorings = remote.listMostRecents(mostRecent.getValue(), monitoring.getIdMonitoring());
				getParameters().put("requestType", new Character('N'));	
			} else {
				reclameAquiPostMonitorings = remote.listMostRecents(monitoring.getIdMonitoring(), 50);			
			}	
			
		} else if(elder != null) {
			reclameAquiPostMonitorings = remote.listElder(elder.getValue(), monitoring.getIdMonitoring(), 50);
			getParameters().put("requestType", new Character('O'));
			
		} else {
			reclameAquiPostMonitorings = remote.listMostRecents(monitoring.getIdMonitoring(), 50);			
		}
		
		List<PostMonitoringDTO> posts = new ArrayList<PostMonitoringDTO>();
	
		for(ReclameAquiPostMonitoring reclameAquiPostMonitoring : reclameAquiPostMonitorings) {
			
			ReclameAquiPostMonitoringDTO reclameAquiPostMonitoringDTO = MountDTO.mountReclameAquiPostMonitoring(reclameAquiPostMonitoring); 
			reclameAquiPostMonitoringDTO.setMonitoringPostTags(
					MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(reclameAquiPostMonitoring.getReclameAquiPost().getCacheId(), 
						monitoring.getIdMonitoring(), reclameAquiPostMonitoring.getTerm(), Profile.RECLAMEAQUI)));
			
			posts.add(reclameAquiPostMonitoringDTO);
		}

		Date now = new Date();
		
		Date createTime = new Date(monitoring.getCreatedAt() * 1000);
		createTime.setMinutes(createTime.getMinutes() + 5);
		
		if(now.after(createTime) && posts.isEmpty() && (mostRecent == null || mostRecent.getValue() == null)) {
			getParameters().put("no_results", new Boolean(true));
		} else {
			getParameters().put("posts", posts);
		}		
		getParameters().put("monitoring", monitoring);
	}

}
