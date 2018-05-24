package br.com.opsocial.server.utils.tasks;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.mailbox.TaskUpdateProfileConversations;
import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.entity.application.Account;

public class MailboxController {
	
	private MaintenanceAccountRemote accountRemote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");
	
	public MailboxController() {
	
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				
				List<Account> accounts = accountRemote.getAllActive();
				
				Date sevenDaysAgo = new Date();
				sevenDaysAgo.setDate(sevenDaysAgo.getDate() - 7);
				
				for(Account account : accounts) {
					
					if(account.getUsesSac()) {
						if(account.getSacLastUse().getTime() > sevenDaysAgo.getTime()) {
							new Thread(new TaskUpdateProfileConversations(account)).start();
						} else {
							
							account.setUsesSac(false);
							
							try {
								accountRemote.save(account);
							} catch (IllegalArgumentException e) {
								e.printStackTrace();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			
		}, 60000L, 600000L); // Verifica novas mensagens a cada 10 minutos, para todas as contas ativas do OpSocial.
	}
	
}
