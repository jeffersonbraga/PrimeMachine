package br.com.opsocial.server.actions.reports.instagram;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.client.entity.report.instagram.ReportInstagramHistoricalDTO;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.reports.UtilReports;
import br.com.opsocial.server.utils.reports.xls.ReportInstagramHistoricalXLS;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.InternalAnalytic;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;

@RestController
@RequestMapping("woopsocial")
public class ReportInstagramHistoricalXLSAction {

	private Profile profile;	
	
	@RequestMapping(value = "/report_instagram_historical_xls",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doAction(HttpSession session, HttpServletRequest request, Long idProfile) throws Exception, OptimisticLockException {
		
		User user = SecurityUtils.getCurrentUser();
		
		List<Object> l = new ArrayList<Object>();
		
		MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		
		profile = remote.getById(idProfile);
		
		ReportInstagramHistoricalDTO reportInstagramHistorical = UtilReports.mountReportInstagramHistoricalDTO(profile);

		ReportInstagramHistoricalXLS reportInstagramHistoricalXLS = new ReportInstagramHistoricalXLS(reportInstagramHistorical, profile);
		reportInstagramHistoricalXLS.createXLS();
		
		UtilFunctions.addInternalAnalytic(InternalAnalytic.REPORTS_MODULE, InternalAnalytic.GEN_INSTAGRAM_HISTORICAL_XLS, profile.getAccount().getIdAccount());

		l.add(new StringUtil(reportInstagramHistoricalXLS.getFileName()));
		l.add(new StringUtil(reportInstagramHistoricalXLS.getFormattedFileName()));
		l.add(new br.com.opsocial.client.entity.primitive.Long(profile.getAccount().getIdAccount()));
		
		String message = OpSocialBackApplication.messageByLocaleService.getMessage("report.notification.xlscreation", new Object[]{user.getName(), profile.getScreenName()});
		UtilFunctions.addNotification(message, user.getAccount().getIdAccount(), Notification.COLOR_SUCCESS, "");
		
		return new ResponseEntity<>(l, HttpStatus.OK);
	}

}
