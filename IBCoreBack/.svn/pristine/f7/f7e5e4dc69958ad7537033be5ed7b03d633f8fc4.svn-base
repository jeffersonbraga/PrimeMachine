package br.com.opsocial.server.actions.reports.facebook;

import java.util.HashMap;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.application.TableOrder;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookPostToPostDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.GenericDateInterval;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.reports.UtilReports;
import br.com.opsocial.server.utils.reports.pdf.ReportFacebookPostToPostPDF;
import br.com.opsocial.ejb.das.MaintenanceReportLogoRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.ReportLogoId;
import br.com.opsocial.ejb.entity.report.ReportLogo;

@RestController
@RequestMapping("woopsocial")
public class ReportFacebookPostToPostPDFAction extends ServerAction {

	@Override
	@RequestMapping(value = "/report_facebook_post_to_post_pdf",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		Profile profile = (Profile) getParameters().get("profile");
		Long dateFrom = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("dateFrom")).getValue();
		Long dateUntil = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("dateUntil")).getValue();
		TableOrder postsMostEngagedOrder = ((TableOrder) getParameters().get("postsMostEngagedOrder"));
		
		setParameters(new HashMap<String, Object>());
		
		MaintenanceReportLogoRemote reportLogoRemote = (MaintenanceReportLogoRemote) RecoverMaintenance.recoverMaintenance("ReportLogo");
		
		ReportLogoId reportLogoId = new ReportLogoId();
		reportLogoId.setObjectId(profile.getIdProfile());
		reportLogoId.setType(profile.getType());
		
		ReportLogo reportLogo = reportLogoRemote.getById(reportLogoId);
		
		if(reportLogo != null) {
			profile.setReportLogo(MountDTO.mountReportLogo(reportLogo));
		}
		
		ReportFacebookPostToPostDTO reportFacebookPostToPost = UtilReports.mountReportFacebookPostToPost(profile, new GenericDateInterval(dateFrom, dateUntil));
//		reportFacebookPostToPost.setPostsMostEngagedOrder(postsMostEngagedOrder);
		
		ReportFacebookPostToPostPDF reportFacebookPostToPostPDF = new ReportFacebookPostToPostPDF(reportFacebookPostToPost, profile);
		reportFacebookPostToPostPDF.createPDF();
		
		getParameters().put("pdfToDownload", new StringUtil(reportFacebookPostToPostPDF.getFileName()));	
	}

}
