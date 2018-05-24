package br.com.opsocial.server.actions.reports;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.client.entity.report.ReportLogoDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceReportLogoRemote;
import br.com.opsocial.ejb.entity.application.idclass.ReportLogoId;
import br.com.opsocial.ejb.entity.report.ReportLogo;

@RestController
@RequestMapping("woopsocial")
public class InsertReportLogoAction extends ServerAction {

	@Override
	@RequestMapping(value = "/insert_report_logo",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		MaintenanceReportLogoRemote reportLogoRemote = (MaintenanceReportLogoRemote) 
				RecoverMaintenance.recoverMaintenance("ReportLogo");
		
		ReportLogoDTO reportLogoDTO = (ReportLogoDTO) getParameters().get("reportLogo");
		
		ReportLogo reportLogo = MountDTO.mountReportLogo(reportLogoDTO);
		
		Boolean wasLogoUpdated = true;
		
		try {
			
			reportLogoRemote.save(reportLogo);
			
			ReportLogoId reportLogoId = new ReportLogoId();
			reportLogoId.setObjectId(reportLogo.getObjectId());
			reportLogoId.setType(reportLogo.getType());
			
			reportLogo = reportLogoRemote.getById(reportLogoId);
			
		} catch (Exception e) {
			wasLogoUpdated = false;
			e.printStackTrace();
		}
		
		getParameters().put("wasUpdated", new br.com.opsocial.client.entity.primitive.Boolean(wasLogoUpdated));
		getParameters().put("reportLogo", MountDTO.mountReportLogo(reportLogo));
	}

}
