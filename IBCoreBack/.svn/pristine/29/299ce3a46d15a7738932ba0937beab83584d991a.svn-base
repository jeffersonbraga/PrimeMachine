package br.com.opsocial.server.utils.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.monitorings.MonitoringBlogsNewsPosts;
import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

public class NewsBlogsPostsController {

	public NewsBlogsPostsController() {
		
		Timer timer = new Timer();
		
		Calendar taskDate = Calendar.getInstance();
		taskDate.set(Calendar.HOUR_OF_DAY, 23);
		taskDate.set(Calendar.MINUTE, 0);
		taskDate.set(Calendar.SECOND, 0);
		
		Date taskTime = taskDate.getTime();
		
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
					
				MaintenanceAccountRemote remote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
				MaintenanceMonitoringRemote monitoringRemote = (MaintenanceMonitoringRemote) RecoverMaintenance.recoverMaintenance("Monitoring");
				
				List<Account> accounts = remote.listActiveAccounts(new Date());
				
				Long delay = 0L;
				
				for(Account account : accounts) {
					
					List<Monitoring> monitorings = monitoringRemote.getMonitoringsByAccount(account.getIdAccount());
					
					for(final Monitoring monitoring : monitorings) {
						
						if(monitoring.getState().equals(Monitoring.RUNNING)) {
				
							new Timer().schedule(new TimerTask() {
								
								@Override
								public void run() {
									new MonitoringBlogsNewsPosts(monitoring).savePostsWebHoseAPI();		
								}
							}, delay);
							
							delay += 2000L;
						}
					}
				} 
			}
		}, taskTime, 86400L * 1000L);
	}
}
