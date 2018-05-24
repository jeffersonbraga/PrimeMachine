package br.com.opsocial.server.utils.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.entity.application.Profile;

public class ProfileGroupDeleteController {

	private Timer timer;

	public ProfileGroupDeleteController() {

		timer = new Timer();
		
		Calendar taskDate = Calendar.getInstance();
//		taskDate.set(Calendar.DAY_OF_MONTH, taskDate.get(Calendar.DAY_OF_MONTH) + 1);
		taskDate.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
		taskDate.set(Calendar.AM_PM, Calendar.AM);
		taskDate.set(Calendar.HOUR_OF_DAY, 6);
		taskDate.set(Calendar.MINUTE, 0);
		taskDate.set(Calendar.SECOND, 0);
		
		Date taskTime = taskDate.getTime();
		
		timer.schedule(new Task(), taskTime, 86400 * 1000L);
	}
	
	class Task extends TimerTask {

		public void run() {

			try {
				
				Calendar currentDate = Calendar.getInstance();
				currentDate.add(Calendar.DATE, -1);
				
				Long beforeInsertionDate = UtilFunctions.getMidnightDateNoDaylightTime(currentDate.getTime());
				
				MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
				
				List<Profile> profiles = profileRemote.listGroupsWithNoMonitoring(beforeInsertionDate);
				for (Profile profile : profiles) {
					profileRemote.delete(profile);
				}
	    		
			} catch (Exception e) {
    			e.printStackTrace();
    		}
		}
	}
	
}
