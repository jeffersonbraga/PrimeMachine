package br.com.opsocial.server.utils.twitter;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;

public class TwitterAuthentication {

	private Profile profile;
	
	public TwitterAuthentication(Profile profile) {
		this.profile = profile;
	}
	
	public Profile deactivateProfile() throws Exception {
		
		MaintenanceProfileRemote remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");

		profile.setActive('F');
		
		return remote.save(profile);
	}
}
