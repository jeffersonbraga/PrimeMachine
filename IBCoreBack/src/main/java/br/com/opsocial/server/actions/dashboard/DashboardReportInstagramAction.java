package br.com.opsocial.server.actions.dashboard;

import java.util.HashMap;

import javax.persistence.OptimisticLockException;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.client.entity.report.instagram.ReportInstagramDashboardDTO;
import br.com.opsocial.client.entity.report.instagram.ReportInstagramOverviewDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.reports.UtilReports;
import br.com.opsocial.ejb.das.MaintenanceReportGenerateRemote;

@RestController
@RequestMapping("woopsocial")
public class DashboardReportInstagramAction extends ServerAction {

	private Profile profile;

	@CrossOrigin("*")
	@RequestMapping(value = "/dashboard_report_instagram",
		    method = RequestMethod.POST,
		    consumes = MediaType.APPLICATION_JSON_VALUE,
		    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ReportInstagramDashboardDTO> doAction(@Valid @RequestBody Profile profile, Long dateFrom_, Long dateUntil_) throws Exception, OptimisticLockException {
		
		MaintenanceReportGenerateRemote reportGenerateRemote = (MaintenanceReportGenerateRemote)
				RecoverMaintenance.recoverMaintenance("ReportGenerate");

		setParameters(new HashMap<String, Object>());

		profile.setReportState(reportGenerateRemote.getEntity(profile.getNetworkId(), Profile.INSTAGRAM).getState());

		ReportInstagramDashboardDTO reportInstagramDashboard;
		
		if(profile.getReportState().equals(Profile.REPORT_FINALIZED) || profile.getReportState().equals(Profile.REPORT_REFRESH_TOKEN)) {
//			reportInstagramDashboard = UtilReports.mountReportInstagramDashboard(profile, dateFrom_, dateUntil_);
			reportInstagramDashboard = null;
		} else {
			reportInstagramDashboard = new ReportInstagramDashboardDTO();
		}

		return new ResponseEntity<>(reportInstagramDashboard, HttpStatus.OK);
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub
		
	}

}
