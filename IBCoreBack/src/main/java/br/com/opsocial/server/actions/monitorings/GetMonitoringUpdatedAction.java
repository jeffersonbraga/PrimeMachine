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
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagRemote;
import br.com.opsocial.ejb.das.MaintenanceReportLogoRemote;
import br.com.opsocial.ejb.entity.application.idclass.ReportLogoId;
import br.com.opsocial.ejb.entity.report.ReportLogo;

@RestController
@RequestMapping("woopsocial")
public class GetMonitoringUpdatedAction extends ServerAction {

	@Override
	@RequestMapping(value = "/get_monitoring_updated",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		Long idMonitoring = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("idMonitoring")).getValue();
		
		setParameters(new HashMap<String, Object>());
		
		MaintenanceMonitoringRemote monitoringRemote = (MaintenanceMonitoringRemote) RecoverMaintenance.recoverMaintenance("Monitoring");
		MaintenanceReportLogoRemote reportLogoRemote = (MaintenanceReportLogoRemote) RecoverMaintenance.recoverMaintenance("ReportLogo");

		MonitoringDTO monitoring = MountDTO.mountMonitoring(monitoringRemote.getById(idMonitoring));
		
		// Carregar tags ------------------------------------
		MaintenanceMonitoringTagRemote monitoringTagRemote = (MaintenanceMonitoringTagRemote) RecoverMaintenance.recoverMaintenance("MonitoringTag");
		
		monitoring.setMonitoringTags(MountDTO.mountMonitoringTag(
				monitoringTagRemote.listByMonitoring(monitoring.getIdMonitoring(), monitoring.getAccount().getIdAccount())));
		//------------------------------------------------------
		
		ReportLogoId reportLogoId = new ReportLogoId();
		reportLogoId.setObjectId(monitoring.getIdMonitoring());
		reportLogoId.setType(ReportLogo.MONITORING);
		
		ReportLogo reportLogo = reportLogoRemote.getById(reportLogoId);
		
		if(reportLogo != null) {
			monitoring.setReportLogo(MountDTO.mountReportLogo(reportLogo));
		}
		
		getParameters().put("monitoring", monitoring);
	}

}
