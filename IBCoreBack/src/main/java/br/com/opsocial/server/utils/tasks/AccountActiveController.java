package br.com.opsocial.server.utils.tasks;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.tasks.MonitoringController.TaskMonitoring;
import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceSetRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.sets.Sets;

public class AccountActiveController {
	
	public AccountActiveController(ServletContext context) {
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				try {
					
					MaintenanceAccountRemote remote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
					
					List<Account> accounts = remote.listInactiveAccounts(new Date());
					
					for(Account account : accounts) {
						account.setActive('F');
						remote.save(account);
						
						MaintenanceSetRemote setRemote = (MaintenanceSetRemote) RecoverMaintenance.recoverMaintenance("Set");
						
						List<Sets> sets = setRemote.listByAccount(account.getIdAccount());
						
						for(Sets set : sets) {
							set.setActive('F');
							setRemote.save(set);
						}
						
						MaintenanceMonitoringRemote monitoringRemote = (MaintenanceMonitoringRemote) RecoverMaintenance.recoverMaintenance("Monitoring");
						
						MonitoringController controller = (MonitoringController) context.getAttribute("monitoring_controller");
						
						List<Monitoring> monitorings = monitoringRemote.getMonitoringsByAccount(account.getIdAccount());
						
						try {
							for(Monitoring monitoring : monitorings) {
								
								TaskMonitoring taskToCancel = controller.getMappedTasks().get(monitoring.getIdMonitoring());
								
								controller.getMappedTasks().remove(monitoring.getIdMonitoring());
								controller.getAlreadyMonitoring().remove(monitoring.getIdMonitoring());
								taskToCancel.cancel();
							}	
						} catch (Exception e) {
							e.printStackTrace();
						}
					} 
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 60000L * 60 * 24);
	}
}
