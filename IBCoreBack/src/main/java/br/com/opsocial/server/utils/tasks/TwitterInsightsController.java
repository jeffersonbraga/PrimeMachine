package br.com.opsocial.server.utils.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.reports.ReportTwitter;
import br.com.opsocial.server.validation.ReportsValidation;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;

public class TwitterInsightsController {

	private Timer timer;

	public TwitterInsightsController() {
		
		timer = new Timer();
		
		Calendar taskDate = Calendar.getInstance();
		taskDate.set(Calendar.DAY_OF_MONTH, taskDate.get(Calendar.DAY_OF_MONTH) + 1);
		taskDate.set(Calendar.HOUR_OF_DAY, 0);
		taskDate.set(Calendar.MINUTE, 0);
		taskDate.set(Calendar.SECOND, 0);
		
		Date taskTime = taskDate.getTime();
		
		timer.schedule(new Task(), taskTime, 86400 * 1000L);
	}

	class Task extends TimerTask {
		
		public void run() {
			
			Calendar reportDate = Calendar.getInstance();
			reportDate.add(Calendar.DATE, -1);

			final Long reportDateStamp = UtilFunctions.getMidnightDateNoDaylightTime(reportDate.getTime());

			MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
			
			List<Profile> profiles = profileRemote.listActivesByType(Profile.TWITTER);
			HashSet<String> profilesMap = new HashSet<String>();
			
			for (final Profile profile : profiles) {
				
				if(!profilesMap.contains(profile.getNetworkId()) && new ReportsValidation().canGenerateReport(profile)) {
					
					new Thread(new ReportTwitter(profile, reportDateStamp)).start();
					
					profilesMap.add(profile.getNetworkId());
				}
				
				
			}			
		}
	}
	
	

}
