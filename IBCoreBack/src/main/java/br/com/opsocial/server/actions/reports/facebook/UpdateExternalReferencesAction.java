package br.com.opsocial.server.actions.reports.facebook;

import java.util.HashMap;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceFaceReportLocationRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class UpdateExternalReferencesAction extends ServerAction {

	@Override
	@RequestMapping(value = "/update_external_references",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		Profile profile = (Profile) getParameters().get("profile");
		
		Integer offset = ((Integer)getParameters().get("offset"));
//		Integer limit = ((br.com.opsocial.client.entity.primitive.Integer)getParameters().get("limit")).getValue();
		
		Long dateFrom = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("dateFrom")).getValue();
		Long dateUntil = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("dateUntil")).getValue();
		
		setParameters(new HashMap<String, Object>());
		
		MaintenanceFaceReportLocationRemote faceReportLocationRemote = (MaintenanceFaceReportLocationRemote) 
				RecoverMaintenance.recoverMaintenance("FaceReportLocation");
		
		List<ReportFacebookLocation> externalReferences = faceReportLocationRemote.getExternalReferences(profile.getNetworkId(), dateFrom, dateUntil, offset, null);
		
		getParameters().put("externalReferences", externalReferences);
	}

}
