package br.com.opsocial.server.actions.reports.instagram;

import java.util.HashMap;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceReportLogoRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.ReportLogoId;
import br.com.opsocial.ejb.entity.report.ReportLogo;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.client.entity.primitive.PersistentMap;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.client.entity.report.instagram.ReportInstagramOverviewDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.GenericDateInterval;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.reports.UtilReports;
import br.com.opsocial.server.utils.reports.pdf.ReportInstagramOverviewPDF;

@RestController
@RequestMapping("woopsocial")
public class ReportInstagramOverviewPDFAction extends ServerAction {

	@Override
	@RequestMapping(value = "/report_instagram_overview_pdf",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		HashMap<String, String> chartsImages = ((PersistentMap<String, String>) getParameters().get("chartsImages")).getValue();
		Profile profile = (Profile) getParameters().get("profile");
		Long dateFrom = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("dateFrom")).getValue();
		Long dateUntil = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("dateUntil")).getValue();
		
		setParameters(new HashMap<String, Object>());
		
		MaintenanceReportLogoRemote reportLogoRemote = (MaintenanceReportLogoRemote) RecoverMaintenance.recoverMaintenance("ReportLogo");
		
		ReportLogoId reportLogoId = new ReportLogoId();
		reportLogoId.setObjectId(profile.getIdProfile());
		reportLogoId.setType(profile.getType());
		
		ReportLogo reportLogo = reportLogoRemote.getById(reportLogoId);
		
		if(reportLogo != null) {
			profile.setReportLogo(MountDTO.mountReportLogo(reportLogo));
		}
		
		ReportInstagramOverviewDTO reportInstagramOverview = UtilReports.mountReportInstagramOverviewDTO(profile, new GenericDateInterval(dateFrom, dateUntil));

		ReportInstagramOverviewPDF reportInstagramOverviewPDF = new ReportInstagramOverviewPDF(reportInstagramOverview, profile, chartsImages);
		reportInstagramOverviewPDF.createPDF();
		
		getParameters().put("pdfToDownload", new StringUtil(reportInstagramOverviewPDF.getFileName()));
	}

}
