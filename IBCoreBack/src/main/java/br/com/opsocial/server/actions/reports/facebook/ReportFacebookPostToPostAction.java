package br.com.opsocial.server.actions.reports.facebook;

import java.util.Calendar;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import br.com.opsocial.client.entity.report.facebook.ReportFacebookPostToPostDTO;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.InternalAnalytic;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.server.utils.GenericDateInterval;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.reports.UtilReports;

@RestController
@RequestMapping("woopsocial")
public class ReportFacebookPostToPostAction {

	private Profile profile;
	private GenericDateInterval genericDateInterval;
	
	@CrossOrigin
	@RequestMapping(value = "/report_facebook_post_to_post",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> doActionNew(Long idProfile, Long dateFrom, Long dateUntil) {
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		
		ReportFacebookPostToPostDTO reportFacebookPostToPost;
		
		try {
			
			profile = profileRemote.getById(idProfile);
			Character reportState = UtilReports.getReportState(profile);
			
			Calendar fromDate = Calendar.getInstance();
			fromDate.setTimeInMillis(dateFrom);
			
			Calendar untilDate = Calendar.getInstance();
			untilDate.setTimeInMillis(dateUntil);
			
			genericDateInterval = new GenericDateInterval(UtilFunctions.getDateForFacebookReport(fromDate), UtilFunctions.getDateForFacebookReport(untilDate));
			
			if(reportState.equals(Profile.REPORT_FINALIZED) || reportState.equals(Profile.REPORT_REFRESH_TOKEN)) {
				reportFacebookPostToPost = UtilReports.mountReportFacebookPostToPost(profile, genericDateInterval);
			} else {
				reportFacebookPostToPost = new ReportFacebookPostToPostDTO();
			}
			
			UtilFunctions.addInternalAnalytic(InternalAnalytic.REPORTS_MODULE, InternalAnalytic.GEN_FACEBOOK_POST_TO_POST_WEB, profile.getAccount().getIdAccount());
			
			JsonObject response = new JsonObject();
			response.add("reportFacebookPostToPost", new Gson().toJsonTree(reportFacebookPostToPost));
			response.addProperty("reportState", reportState);
			
			return new ResponseEntity<>(response.toString(), HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
