package br.com.opsocial.server.actions.reports.linkedin;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.OpSocialBackApplication;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.client.entity.report.linkedin.ReportLinkedinOverviewDTO;
import br.com.opsocial.ejb.entity.application.InternalAnalytic;
import br.com.opsocial.ejb.entity.application.Notification;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.reports.xls.ReportLinkedinOverviewXLS;

@RestController
@RequestMapping("woopsocial")
public class ReportLinkedinOverviewXLSAction {

	private User user; 
	private Profile profile;
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/report_linkedin_overview_xls",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Object>> doAction(@Valid @RequestBody ReportLinkedinOverviewDTO reportLinkedinOverviewDTO, 
			HttpSession session, HttpServletRequest request) throws Exception, OptimisticLockException {
		
		user = SecurityUtils.getCurrentUser();
		
		this.profile = reportLinkedinOverviewDTO.getProfile();
		
		List<Object> l = new ArrayList<Object>();
		
		ReportLinkedinOverviewXLS reportLinkedinOverviewXLS = new ReportLinkedinOverviewXLS(reportLinkedinOverviewDTO);
		reportLinkedinOverviewXLS.createXLS();
		
		UtilFunctions.addInternalAnalytic(InternalAnalytic.REPORTS_MODULE, InternalAnalytic.GEN_LINKEDIN_OVERVIEW_XLS, profile.getAccount().getIdAccount());

		l.add(new StringUtil(reportLinkedinOverviewXLS.getFileName()));
		l.add(new StringUtil(reportLinkedinOverviewXLS.getFormattedFileName()));
		l.add(new br.com.opsocial.client.entity.primitive.Long(profile.getAccount().getIdAccount()));
		
		String message = OpSocialBackApplication.messageByLocaleService.getMessage("report.notification.xlscreation", new Object[]{user.getName(), profile.getScreenName()});
		UtilFunctions.addNotification(message, user.getAccount().getIdAccount(), Notification.COLOR_SUCCESS, "");
		
		return new ResponseEntity<>(l, HttpStatus.OK);
	}
}
