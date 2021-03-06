package br.com.opsocial.server.actions.dashboard;

import java.util.HashMap;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.report.facebook.ReportFacebookDashboardDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.reports.UtilReports;
import br.com.opsocial.ejb.das.MaintenanceReportGenerateRemote;
import br.com.opsocial.ejb.entity.application.Profile;

@RestController
@RequestMapping("woopsocial")
public class DashboardReportFacebookAction extends ServerAction {

	private Profile profile;
	
	@Override
	@RequestMapping(value = "/dashboard_report_facebook",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		MaintenanceReportGenerateRemote remoteReportGenerate = (MaintenanceReportGenerateRemote)
				RecoverMaintenance.recoverMaintenance("ReportGenerate");
		
		profile = (Profile) getParameters().get("profile");
		
		setParameters(new HashMap<String, Object>());
		
		profile.setReportState(remoteReportGenerate.getEntity(profile.getNetworkId(), Profile.FACEBOOK_PAGE).getState());
		
		ReportFacebookDashboardDTO reportFacebookDashboard;
		
		if(profile.getReportState().equals(Profile.REPORT_FINALIZED) || profile.getReportState().equals(Profile.REPORT_REFRESH_TOKEN)) {
//			reportFacebookDashboard = UtilReports.mountReportFacebookDashboardDTO(profile);
		} else {
			reportFacebookDashboard = new ReportFacebookDashboardDTO();
		}
		
//		getParameters().put("reportFacebookDashboard", reportFacebookDashboard);
		getParameters().put("profile", profile);
		
	}

}
