package br.com.opsocial.server.utils.facebook;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;

public class FacebookAuthentication {

	private Profile profile;
	private MaintenanceProfileRemote remote;
	
	public FacebookAuthentication(Profile profile) {
		this.profile = profile;
	}
	
	public boolean verifyAccessTokenValidity() {

		Calendar dateNow = new GregorianCalendar();
		dateNow.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		
		if(profile.getTokenDate() != null && profile.getTokenExpiresIn() != null) {
			if((dateNow.getTimeInMillis() / 1000L) - profile.getTokenDate() >= profile.getTokenExpiresIn()) {
				return false;
			} else {
				return true;
			}
		} else {
			return true;
		}
	}
	
	public Profile deactivateProfile() throws Exception {
		
		remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");

		profile.setActive('F');
		profile.setTurn('F');
		
		return remote.save(profile);
	}
	
	public Profile deactivateProfile(Profile profile) throws Exception {
		
		remote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		
		profile.setActive('F');
		profile.setTurn('F');
		
		return remote.save(profile);
	}
	
}
