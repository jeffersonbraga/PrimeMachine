package br.com.opsocial.server.actions.reports.facebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookCityDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceFaceReportLocationRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.ReportFacebookLocation;

@RestController
@RequestMapping("woopsocial")
public class UpdateCitiesStatsAction extends ServerAction {

	@Override
	@RequestMapping(value = "/update_cities_stats",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		Profile profile = (Profile) getParameters().get("profile");
		
		Integer offset = ((Integer) getParameters().get("offset"));
		Integer limit = ((Integer) getParameters().get("limit"));
		
		setParameters(new HashMap<String, Object>());
		
		MaintenanceFaceReportLocationRemote faceReportLocationRemote = (MaintenanceFaceReportLocationRemote) 
				RecoverMaintenance.recoverMaintenance("FaceReportLocation");
		
		List<ReportFacebookLocation> citiesMoreFans = faceReportLocationRemote.getReportFacebookCityFan(profile.getNetworkId(), offset, limit);
		List<ReportFacebookCityDTO> citiesMoreFansDTO = new ArrayList<ReportFacebookCityDTO>();
		
		for(ReportFacebookLocation reportFacebookCity : citiesMoreFans) {
			citiesMoreFansDTO.add(MountDTO.mountReportFacebookCity(reportFacebookCity));
		}
		
		List<ReportFacebookLocation> citiesMoreReached = faceReportLocationRemote.getReportFacebookCityImp(profile.getNetworkId(), offset, limit);
		List<ReportFacebookCityDTO> citiesMoreReachedDTO = new ArrayList<ReportFacebookCityDTO>();
		
		for(ReportFacebookLocation reportFacebookCity : citiesMoreReached) {
			citiesMoreReachedDTO.add(MountDTO.mountReportFacebookCity(reportFacebookCity));
		}
		
		getParameters().put("citiesMoreFans", citiesMoreFansDTO);
		getParameters().put("citiesMoreReached", citiesMoreReachedDTO);
	}
	
}
