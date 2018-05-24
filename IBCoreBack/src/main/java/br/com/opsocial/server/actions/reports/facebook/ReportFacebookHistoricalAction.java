package 	br.com.opsocial.server.actions.reports.facebook;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.opsocial.client.entity.report.facebook.ReportFacebookHistoricalDTO;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.InternalAnalytic;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.reports.UtilReports;

@RestController
@RequestMapping("woopsocial")
public class ReportFacebookHistoricalAction {

	private Profile profile;

	@CrossOrigin
	@RequestMapping(value = "/report_facebook_historical",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> doAction(Long idProfile) {
		
		MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		
		ReportFacebookHistoricalDTO reportFacebookHistorical;
		
		try {
			
			profile = remote.getById(idProfile);
			Character reportState = UtilReports.getReportState(profile);
			
			if(reportState.equals(Profile.REPORT_FINALIZED) || reportState.equals(Profile.REPORT_REFRESH_TOKEN)) {
				reportFacebookHistorical = UtilReports.mountReportFacebookHistoricalDTO(profile, 10);
			} else {
				reportFacebookHistorical = new ReportFacebookHistoricalDTO();
			}
			
			UtilFunctions.addInternalAnalytic(InternalAnalytic.REPORTS_MODULE, InternalAnalytic.GEN_FACEBOOK_HISTORICAL_WEB, profile.getAccount().getIdAccount());
			
			JsonObject response = new JsonObject();
			response.add("reportFacebookHistorical", new Gson().toJsonTree(reportFacebookHistorical));
			response.addProperty("reportState", reportState);
			
			return new ResponseEntity<>(response.toString(), HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
