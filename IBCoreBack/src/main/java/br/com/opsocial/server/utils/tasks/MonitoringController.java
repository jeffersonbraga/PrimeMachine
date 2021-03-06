package br.com.opsocial.server.utils.tasks;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.monitorings.MonitoringGooglePlus;
import br.com.opsocial.server.utils.monitorings.MonitoringInstagram;
import br.com.opsocial.server.utils.monitorings.MonitoringTwitter;
import br.com.opsocial.server.utils.monitorings.MonitoringYoutube;
import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringSearchRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.MonitoringSearchId;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringSearch;

public class MonitoringController {

	private Timer timer;
	private HashSet<Long> alreadyMonitoring;
	private HashMap<Long,TaskMonitoring> mappedTasks;
	
	public final static Integer LIMIT_PER_CALL = 20;
	public final static Long SEARCH_CYCLE_TIME = 900L;
	public final static Integer SEARCH_CYCLE_THRESHOLD = 4;
	
	public MonitoringController() {
		
		timer = new Timer();
		timer.schedule(new Task(), 0, 60L * 1000L);
		alreadyMonitoring = new HashSet<Long>();
		
		mappedTasks = new HashMap<Long,TaskMonitoring>();
	}
	
	class Task extends TimerTask {
		
		int delay = 0;
        
    	public void run() {
    		
    		try {

    			MaintenanceAccountRemote remote = (MaintenanceAccountRemote) RecoverMaintenance.recoverMaintenance("Account");

	    		List<Account> accounts = remote.getAllActive();
	    		
	    		for(Account account : accounts) {
	    				    			
    				MaintenanceMonitoringRemote monitoringRemote = (MaintenanceMonitoringRemote) RecoverMaintenance.recoverMaintenance("Monitoring");

		    		List<Monitoring> monitorings = monitoringRemote.getMonitoringsByAccount(account.getIdAccount());
		    		
		    		for(Monitoring monitoring : monitorings) {
		    			
		    			if(!alreadyMonitoring.contains(monitoring.getIdMonitoring()) && monitoring.getState().equals(Monitoring.RUNNING)) {
		    				
		    				startMonitoring(monitoring, delay);
		    				
		    				delay += 2000;
		    			}
		    		}
				}		    		
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
    	}
	}
	
	public void startMonitoring(Monitoring monitoring, Integer delay) {
		
		alreadyMonitoring.add(monitoring.getIdMonitoring());
		Timer timer = new Timer();
		
		TaskMonitoring taskMonitoring = new TaskMonitoring(monitoring);
		timer.schedule(taskMonitoring, delay, SEARCH_CYCLE_TIME * 1000L);
		
		mappedTasks.put(monitoring.getIdMonitoring(), taskMonitoring);
	}
	
	public class TaskMonitoring extends TimerTask {
		
		private Monitoring monitoring;
		
		public TaskMonitoring(Monitoring monitoring) {
			this.monitoring = monitoring;
		}
        
    	public void run() {
    		
    		//----- System.out.println("Monitoramento: " + monitoring.getIdMonitoring());
    		
    		try {
    			
    			MaintenanceMonitoringRemote monitoringRemote = (MaintenanceMonitoringRemote) RecoverMaintenance.recoverMaintenance("Monitoring");
    			
    			monitoring = monitoringRemote.getById(monitoring.getIdMonitoring());
    			
    			String[] terms = monitoring.getTerms().split(";");
    			
    			for(String term : terms) {

    				//Twitter monitoring term.
    				if(isToSearch(term, Profile.TWITTER)) {
    					new MonitoringTwitter(monitoring).getCurrentPosts(term);
    				}
    				
    				//Google Plus monitoring term.
    				if(isToSearch(term, Profile.GOOGLE)) {
    					new MonitoringGooglePlus(monitoring).getCurrentPosts(term);
    				}
    				
    				// Youtube monitoring term
    				if(isToSearch(term, Profile.YOUTUBE)) {
    					new MonitoringYoutube(monitoring).getCurrentPosts(term);
    				}
    	
    				// Instagram monitoring term
    				if(isToSearch(term, Profile.INSTAGRAM)) {
    					new MonitoringInstagram(monitoring).saveNewsFeeds(term);
    				}
    					
    			}
    		} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
    		
    		//----- System.out.println("Fim Monitoramento: " + monitoring.getIdMonitoring());
    	}

		public Monitoring getMonitoring() {
			return monitoring;
		}

		public void setMonitoring(Monitoring monitoring) {
			this.monitoring = monitoring;
		}
		
		/**
		 * 
		 * Método responsável por verificar a relevância da pesquisa do termo e 
		 * decidir se a pesquisa deve ser feita ou não.
		 * 
		 * @param term
		 * @param network
		 * @return
		 */
		private Boolean isToSearch(String term, Character network) {
			
			Boolean isToSearch = true;
			
			MaintenanceMonitoringSearchRemote monitoringSearchRemote = 
					(MaintenanceMonitoringSearchRemote) RecoverMaintenance.recoverMaintenance("MonitoringSearch");
			
			MonitoringSearchId monitoringSearchId = new MonitoringSearchId();
			monitoringSearchId.setMonitoring(monitoring.getIdMonitoring());
			monitoringSearchId.setTerm(term);
			monitoringSearchId.setNetwork(network);
			
			MonitoringSearch monitoringSearch = monitoringSearchRemote.getById(monitoringSearchId);
			
			if(monitoringSearch == null) {
				
				monitoringSearch = new MonitoringSearch();
				monitoringSearch.setMonitoring(monitoring);
				monitoringSearch.setTerm(term);
				monitoringSearch.setNetwork(network);
				monitoringSearch.setExpectedPosts(LIMIT_PER_CALL);
				monitoringSearch.setReturnedPosts(-1);
				monitoringSearch.setCycles(0L);
				monitoringSearch.setSearches(0L);
				monitoringSearch.setSearchesWithResults(0L);
				monitoringSearch.setLastDateSearched(new Date().getTime() / 1000L);
				monitoringSearch.setLastDateSearchedWithResults(new Date().getTime() / 1000L);
				
				try {

					monitoringSearchRemote.save(monitoringSearch);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			} else {
				
				if(monitoringSearch.getReturnedPosts() < 1 && monitoringSearch.getCycles() > 1L) {
					
					Long nowDate = new Date().getTime() / 1000L;
					
					if((nowDate - monitoringSearch.getLastDateSearched()) < (SEARCH_CYCLE_TIME * SEARCH_CYCLE_THRESHOLD)) {
						isToSearch = false;
					}
					
				}
				
			}
			
			return isToSearch;
		}
	}

	public HashMap<Long, TaskMonitoring> getMappedTasks() {
		return mappedTasks;
	}

	public void setMappedTasks(HashMap<Long, TaskMonitoring> mappedTasks) {
		this.mappedTasks = mappedTasks;
	}

	public HashSet<Long> getAlreadyMonitoring() {
		return alreadyMonitoring;
	}

	public void setAlreadyMonitoring(HashSet<Long> alreadyMonitoring) {
		this.alreadyMonitoring = alreadyMonitoring;
	}
}
