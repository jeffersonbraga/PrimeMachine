package br.com.opsocial.server.actions.profiles;

import javax.persistence.OptimisticLockException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceReportLogoRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.ReportLogoId;
import br.com.opsocial.ejb.entity.report.ReportLogo;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class DeleteProfileAction extends ServerAction {
	
	@CrossOrigin
	@RequestMapping(value = "/delete_profile",
    method = RequestMethod.DELETE,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> doActionNew(Long idProfile) {

		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		
		Profile profile = profileRemote.getById(idProfile);
		
		try {
			
			// Importante!!! Verifica se perfil tem filhos, e anexa informação de perfil pai no campo profileInformation.
			if(profile.getType().equals(Profile.FACEBOOK) || profile.getType().equals(Profile.LINKEDIN)) {
				
				profile.setChildrenProfiles(profileRemote.getChildrenByProfile(profile.getIdProfile()));
				
				if(!profile.getChildrenProfiles().isEmpty()) {
					
					String profileInformation = profile.getNetworkId() + "♪" + profile.getScreenName();
					
					for(Profile childDTO : profile.getChildrenProfiles()) {
						
						Profile child = profileRemote.getById(childDTO.getIdProfile());
						child.setParentProfile(null);
						child.setProfileInformation(profileInformation);
						profileRemote.save(child);
					}
				}
			}
			
			profileRemote.delete(profile);
			
			MaintenanceReportLogoRemote reportLogoRemote = (MaintenanceReportLogoRemote) 
					RecoverMaintenance.recoverMaintenance("ReportLogo");
			
			ReportLogoId reportLogoId = new ReportLogoId();
			reportLogoId.setObjectId(profile.getIdProfile());
			reportLogoId.setType(profile.getType());
			
			ReportLogo reportLogo = reportLogoRemote.getById(reportLogoId);
			
			if(reportLogo != null) {
				reportLogoRemote.delete(reportLogo);
			}
			
			return new ResponseEntity<Object>(HttpStatus.OK);
	 
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Object>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void doAction() throws Exception, OptimisticLockException {
		// TODO Auto-generated method stub
		
	}
}
