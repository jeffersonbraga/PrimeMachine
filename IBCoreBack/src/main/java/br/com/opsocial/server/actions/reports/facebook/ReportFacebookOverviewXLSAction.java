package br.com.opsocial.server.actions.reports.facebook;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookOverviewDTO;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.InternalAnalytic;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.GenericDateInterval;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.reports.UtilReports;
import br.com.opsocial.server.utils.reports.xls.ReportFacebookOverviewXLS;

@RestController
@RequestMapping("woopsocial")
public class ReportFacebookOverviewXLSAction {
 
	private Profile profile;
	
	private GenericDateInterval genericDateInterval;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/report_facebook_overview_xls",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doAction(HttpSession session, HttpServletRequest request, Long idProfile, Long dateFrom, Long dateUntil) throws Exception, OptimisticLockException {
		
		User user = SecurityUtils.getCurrentUser();
		
		List<Object> l = new ArrayList<Object>();
		
		MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		
		profile = remote.getById(idProfile);
		
		Calendar fromDate = Calendar.getInstance();
		fromDate.setTimeInMillis(dateFrom);
		
		Calendar untilDate = Calendar.getInstance();
		untilDate.setTimeInMillis(dateUntil);
		
		genericDateInterval = new GenericDateInterval(UtilFunctions.getDateForFacebookReport(fromDate), UtilFunctions.getDateForFacebookReport(untilDate));
		
		ReportFacebookOverviewDTO reportFacebookOverview = UtilReports.mountReportFacebookOverviewDTO(profile, genericDateInterval, 10);

		ReportFacebookOverviewXLS reportFacebookOverviewXLS = new ReportFacebookOverviewXLS(reportFacebookOverview, profile);
		reportFacebookOverviewXLS.createXLS();
		
		UtilFunctions.addInternalAnalytic(InternalAnalytic.REPORTS_MODULE, InternalAnalytic.GEN_FACEBOOK_OVERVIEW_XLS, profile.getAccount().getIdAccount());

		l.add(new StringUtil(reportFacebookOverviewXLS.getFileName()));
		l.add(new StringUtil(reportFacebookOverviewXLS.getFormattedFileName()));
		l.add(new br.com.opsocial.client.entity.primitive.Long(profile.getAccount().getIdAccount()));
		
		String message = OpSocialBackApplication.messageByLocaleService.getMessage("report.notification.xlscreation", new Object[]{user.getName(), profile.getScreenName()});
		UtilFunctions.addNotification(message, user.getAccount().getIdAccount(), Notification.COLOR_SUCCESS, "");
		
		return new ResponseEntity<>(l, HttpStatus.OK);
	}
	
}
