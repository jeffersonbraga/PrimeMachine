package br.com.opsocial.server.utils.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import br.com.opsocial.server.utils.reports.TwitterFollowersTask;

public class TwitterFollowersController {

	private Timer timer;
	
	public TwitterFollowersController() {
		
		timer = new Timer();
		
		Calendar taskDate = Calendar.getInstance();
		taskDate.set(Calendar.DAY_OF_MONTH, taskDate.get(Calendar.DAY_OF_MONTH) + 1);
		taskDate.set(Calendar.HOUR_OF_DAY, 1);
		taskDate.set(Calendar.MINUTE, 0);
		taskDate.set(Calendar.SECOND, 0);
		
		Date taskTime = taskDate.getTime();
		
		timer.schedule(new TwitterFollowersTask(), taskTime, 86400 * 1000L);
	}
}
