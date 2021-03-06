package br.com.opsocial.server.actions.reports;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.OptimisticLockException;
import javax.servlet.http.HttpSession;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceReportLogoRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.application.idclass.ReportLogoId;
import br.com.opsocial.ejb.entity.report.ReportLogo;


@RestController
@RequestMapping("woopsocial")
public class ShowReportsAction extends ServerAction {

	@RequestMapping(value = "/show_reports",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction(HttpSession session) {
		
		User user = SecurityUtils.getCurrentUser();
		
		MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		MaintenanceReportLogoRemote reportLogoRemote = (MaintenanceReportLogoRemote) RecoverMaintenance.recoverMaintenance("ReportLogo");
		
		List<Profile> facebookProfiles = remote.getEntityByNetworkType(Profile.FACEBOOK_PAGE, user.getAccount().getIdAccount());
		List<Profile> facebookProfilesDTO = new ArrayList<Profile>();
		
		for(Profile profile : facebookProfiles) {
			
			Profile profileDTO = profile;
			
			ReportLogoId reportLogoId = new ReportLogoId();
			reportLogoId.setObjectId(profile.getIdProfile());
			reportLogoId.setType(profile.getType());
			
			ReportLogo reportLogo = reportLogoRemote.getById(reportLogoId);
			
			if(reportLogo != null) {
				profileDTO.setReportLogo(MountDTO.mountReportLogo(reportLogo));
			}
			
			facebookProfilesDTO.add(profileDTO);
		}
		
		List<Profile> twitterProfiles = remote.getEntityByNetworkType(Profile.TWITTER, user.getAccount().getIdAccount());
		List<Profile> twitterProfilesDTO = new ArrayList<Profile>();
		
		for(Profile profile : twitterProfiles) {
			
			Profile profileDTO = profile;
			
			ReportLogoId reportLogoId = new ReportLogoId();
			reportLogoId.setObjectId(profile.getIdProfile());
			reportLogoId.setType(profile.getType());
			
			ReportLogo reportLogo = reportLogoRemote.getById(reportLogoId);
			
			if(reportLogo != null) {
				profileDTO.setReportLogo(MountDTO.mountReportLogo(reportLogo));
			}
			
			twitterProfilesDTO.add(profileDTO);
		}
		
		List<Profile> instagramProfiles = remote.getEntityByNetworkType(Profile.INSTAGRAM, user.getAccount().getIdAccount());
		List<Profile> instagramProfilesDTO = new ArrayList<Profile>();
		
		for(Profile profile : instagramProfiles) {
			
			Profile profileDTO = profile;
			
			ReportLogoId reportLogoId = new ReportLogoId();
			reportLogoId.setObjectId(profile.getIdProfile());
			reportLogoId.setType(profile.getType());

			ReportLogo reportLogo = reportLogoRemote.getById(reportLogoId);

			if(reportLogo != null) {
				profileDTO.setReportLogo(MountDTO.mountReportLogo(reportLogo));
			}

			instagramProfilesDTO.add(profileDTO);
		}

		getParameters().put("facebookProfiles", facebookProfilesDTO);
		getParameters().put("twitterProfiles", twitterProfilesDTO);
		getParameters().put("instagramProfiles", instagramProfilesDTO);

		//----- System.out.println("{pesquisaGrid:ShowReportsAction}");
		//return new ResponseEntity<>("{pesquisaGrid:ShowReportsAction}", HttpStatus.OK);
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {}
}