package br.com.opsocial.server.utils.reports;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.TimerTask;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.validation.ReportsValidation;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterFollowerControlRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterFollowerRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.TwitterFollowerControl;

public class TwitterFollowersTask extends TimerTask {

	private List<Profile> profiles;
	private MaintenanceProfileRemote profileRemote;
	private Long reportDateStamp;
	
	private MaintenanceTwitterFollowerControlRemote twitterFollowerControlRemote;
	private MaintenanceTwitterFollowerRemote twitterFollowerRemote;
	
	public TwitterFollowersTask() {
		
		Calendar reportDate = Calendar.getInstance();
		reportDate.set(Calendar.HOUR_OF_DAY, 0);
		reportDate.set(Calendar.MINUTE, 0);
		reportDate.set(Calendar.SECOND, 0);

		reportDateStamp = reportDate.getTimeInMillis() / 1000L;
		
		twitterFollowerControlRemote = (MaintenanceTwitterFollowerControlRemote) RecoverMaintenance.recoverMaintenance("TwitterFollowerControl");
		twitterFollowerRemote = (MaintenanceTwitterFollowerRemote) RecoverMaintenance.recoverMaintenance("TwitterFollower");
		profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
	}
	
	@Override
	public void run() {
		
		profiles = profileRemote.listActivesByType(Profile.TWITTER);
		
		HashSet<String> profilesAllMap = new HashSet<String>();
		
		for(Profile profile : profiles) {
			
			if(!profilesAllMap.contains(profile.getNetworkId())) {
				
				if(updateProfileFollowers(profile) && new ReportsValidation().canGenerateReport(profile)) {
					new Thread(new ReportTwitterFollowers(profile)).start();
				}
				
			}
			
			profilesAllMap.add(profile.getNetworkId());
		}
	}
	
	private boolean updateProfileFollowers(Profile profile) {
		
		TwitterFollowerControl twitterFollowerControl = twitterFollowerControlRemote.getById(Long.valueOf(profile.getNetworkId()));
		
		if(twitterFollowerControl == null) {
			
			twitterFollowerControl = new TwitterFollowerControl();
			twitterFollowerControl.setIdProfile(Long.valueOf(profile.getNetworkId()));
			twitterFollowerControl.setDate(reportDateStamp);
			twitterFollowerControl.setNextCursor(-1L);
			twitterFollowerControl.setState(ReportTwitterFollowers.GENERATING);
			
			try {
				
				twitterFollowerControlRemote.save(twitterFollowerControl);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return true;
		} else {
			
			if(twitterFollowerControl.getState().equals(ReportTwitterFollowers.GENERATING)) {
				return false;
			} else {
				
				twitterFollowerControl.setDate(reportDateStamp);
				twitterFollowerControl.setState(ReportTwitterFollowers.GENERATING);
				
				try {
					
					twitterFollowerControlRemote.save(twitterFollowerControl);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				// Rotina que desativa temporariamente os seguidores, permitindo assim descobrir quais perfis deixaram de seguir o perfil corrente.
				twitterFollowerRemote.deactivateFollowersTemporarily(Long.valueOf(profile.getNetworkId()));
				
				return true;
			}	
		}
	}

}
