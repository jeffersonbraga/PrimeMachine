package br.com.opsocial.server.utils.tasks;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.networksintegrations.GoogleIntegration;

import com.google.api.services.customsearch.model.Result;

import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringSearchRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.MonitoringSearchId;
import br.com.opsocial.ejb.entity.application.idclass.ReclameAquiPostMonitoringId;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringSearch;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPost;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPostMonitoring;

public class ReclameAquiPostController {
	
	public final static Integer LIMIT_PER_CALL = 10;
	public final static Long SEARCH_CYCLE_TIME = 86400L;
	public final static Integer SEARCH_CYCLE_THRESHOLD = 2;
	
	public ReclameAquiPostController() {
		
		Calendar taskDate = Calendar.getInstance(); 
		taskDate.set(Calendar.DAY_OF_MONTH, taskDate.get(Calendar.DAY_OF_MONTH) + 1);
		taskDate.set(Calendar.HOUR_OF_DAY, 2);
		taskDate.set(Calendar.MINUTE, 0);
		taskDate.set(Calendar.SECOND, 0);
		
		Date taskTime = taskDate.getTime();
		
		Timer timer = new Timer();
		
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
									savePosts(monitoring);		
								}
							}, delay);
							
							delay += 2000L;
						}
					}
				} 
			}
		}, taskTime, 86400L * 1000L);
	}
	
	public static void savePosts(Monitoring monitoring) {
		
		MaintenanceReclameAquiPostRemote reclameAquiPostRemote = (MaintenanceReclameAquiPostRemote) RecoverMaintenance.recoverMaintenance("ReclameAquiPost");
		MaintenanceReclameAquiPostMonitoringRemote reclameAquiPostMonitoringRemote = (MaintenanceReclameAquiPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("ReclameAquiPostMonitoring");
		
		MaintenanceMonitoringSearchRemote monitoringSearchRemote = (MaintenanceMonitoringSearchRemote) RecoverMaintenance.recoverMaintenance("MonitoringSearch");
		
		String[] terms = monitoring.getTerms().split(";");
		
		for(String term : terms) {
			
			try {
				
				if(isToSearch(term, monitoring)) {
					
					MonitoringSearch monitoringSearch = getMonitoringSearch(monitoring.getIdMonitoring(), term);
					
					List<Result> results = new GoogleIntegration().customSearch(term);
					
					Long searchedDate = new Date().getTime() / 1000L;
					Integer newPostsCount = 0;
					
					for(Result result : results) {

						if(validateIgnore(result,monitoring)) {

							Long retrievedDate = new Date().getTime() / 1000L;

							ReclameAquiPost reclameAquiPost = reclameAquiPostRemote.getById(result.getCacheId());

							if(reclameAquiPost == null) {

								reclameAquiPost = new ReclameAquiPost();

								reclameAquiPost.setCacheId(result.getCacheId());
								reclameAquiPost.setDisplayLink(result.getDisplayLink() != null ? result.getDisplayLink() : null);
								reclameAquiPost.setImageLink(result.getImage() != null ? result.getImage().getThumbnailLink() : null);
								reclameAquiPost.setImageHeight(result.getImage() != null ? result.getImage().getThumbnailHeight() : null);
								reclameAquiPost.setImageWidth(result.getImage() != null ? result.getImage().getThumbnailWidth() : null);
								reclameAquiPost.setLink(result.getLink() != null ? result.getLink() : null);
								reclameAquiPost.setSnippet(result.getSnippet() != null ? result.getSnippet() : null);
								reclameAquiPost.setTitle(result.getTitle() != null ? result.getTitle() : null);

								Date createdTime = new Date();

								try {
									if(reclameAquiPost.getSnippet().substring(0,15).contains("minutos")) {	
										Integer minutesAgo = Integer.valueOf(reclameAquiPost.getSnippet().substring(0, reclameAquiPost.getSnippet().indexOf(" ")));
										createdTime.setMinutes(createdTime.getMinutes() - minutesAgo);

									} else if(reclameAquiPost.getSnippet().substring(0,15).contains("minuto")) {	
										createdTime.setMinutes(createdTime.getMinutes() - 1);

									} else if(reclameAquiPost.getSnippet().substring(0,15).contains("horas")) {	
										Integer hoursAgo = Integer.valueOf(reclameAquiPost.getSnippet().substring(0, reclameAquiPost.getSnippet().indexOf(" ")));
										createdTime.setHours(createdTime.getHours() - hoursAgo);

									} else if(reclameAquiPost.getSnippet().substring(0,15).contains("hora")) {	
										createdTime.setHours(createdTime.getHours() - 1);

									} else if(reclameAquiPost.getSnippet().substring(0,15).contains("dias")) {	
										Integer daysAgo = Integer.valueOf(reclameAquiPost.getSnippet().substring(0, reclameAquiPost.getSnippet().indexOf(" ")));
										createdTime.setDate(createdTime.getDate() - daysAgo);

									} else if(reclameAquiPost.getSnippet().substring(0,15).contains("dia")) {
										createdTime.setDate(createdTime.getDate() - 1);
									} else {
										createdTime.setDate(Integer.valueOf(reclameAquiPost.getSnippet().substring(0, reclameAquiPost.getSnippet().indexOf(" "))));

										String month = reclameAquiPost.getSnippet().substring(reclameAquiPost.getSnippet().indexOf(" ")+1,reclameAquiPost.getSnippet().indexOf("...")-6);

										if(month.equals("jan.")) {
											createdTime.setMonth(0);
										} else if(month.equals("fev.")) {
											createdTime.setMonth(1);
										} else if(month.equals("mar.")) {
											createdTime.setMonth(2);
										} else if(month.equals("abr.")) {
											createdTime.setMonth(3);
										} else if(month.equals("mai.")) {
											createdTime.setMonth(4);
										} else if(month.equals("jun.")) {
											createdTime.setMonth(5);
										} else if(month.equals("jul.")) {
											createdTime.setMonth(6);
										} else if(month.equals("ago.")) {
											createdTime.setMonth(7);
										} else if(month.equals("Sets.")) {
											createdTime.setMonth(8);
										} else if(month.equals("out.")) {
											createdTime.setMonth(9);
										} else if(month.equals("nov.")) {
											createdTime.setMonth(10);
										} else if(month.equals("dez.")) {
											createdTime.setMonth(11);
										}

										Integer year = Integer.valueOf("1" + reclameAquiPost.getSnippet().substring(reclameAquiPost.getSnippet().indexOf("...")-3,reclameAquiPost.getSnippet().indexOf("...")-1));
										createdTime.setYear(year);
									}

									reclameAquiPost.setCreatedTime(createdTime.getTime() / 1000);

								} catch (Exception e) {
									reclameAquiPost.setCreatedTime(new Date().getTime());
									e.printStackTrace();
								}

								reclameAquiPostRemote.save(reclameAquiPost);
							}

							if(reclameAquiPostMonitoringRemote.getByComposedId(monitoring.getIdMonitoring(), reclameAquiPost.getCacheId().toString()) == null) { 

								ReclameAquiPostMonitoringId reclameAquiPostMonitoringId = new ReclameAquiPostMonitoringId();
								reclameAquiPostMonitoringId.setReclameAquiPost(reclameAquiPost.getCacheId());
								reclameAquiPostMonitoringId.setMonitoring(monitoring.getIdMonitoring());
								reclameAquiPostMonitoringId.setTerm(term);

								if(reclameAquiPostMonitoringRemote.getById(reclameAquiPostMonitoringId) == null) { 
									
									newPostsCount++;
									
									ReclameAquiPostMonitoring reclameAquiPostMonitoring = new ReclameAquiPostMonitoring();
									reclameAquiPostMonitoring.setReclameAquiPost(reclameAquiPost);
									reclameAquiPostMonitoring.setMonitoring(monitoring);
									reclameAquiPostMonitoring.setTerm(term);
									reclameAquiPostMonitoring.setGarbage('F');
									reclameAquiPostMonitoring.setRetrievedDate(retrievedDate);

									executeAutomaticNeuterQualification(result, reclameAquiPostMonitoring, monitoring);
									executeAutomaticNegativeQualification(result, reclameAquiPostMonitoring, monitoring);
									executeAutomaticPositiveQualification(result, reclameAquiPostMonitoring, monitoring);

									reclameAquiPostMonitoringRemote.save(reclameAquiPostMonitoring);
								}
							}
						}
					}
					
					if(newPostsCount > 0) {
						monitoringSearch.setLastDateSearchedWithResults(searchedDate);
						monitoringSearch.setSearchesWithResults(monitoringSearch.getSearchesWithResults() + 1L);
					}
					
					Long cycles = monitoringSearch.getCycles(); 
					
					if((monitoringSearch.getReturnedPosts() == 0 && monitoringSearch.getReturnedPosts().equals(newPostsCount)) 
							|| monitoringSearch.getReturnedPosts() > 0 && newPostsCount > 0) {
						cycles++;
					} else {
						cycles = 1L;
					}
					
					monitoringSearch.setCycles(cycles);
					monitoringSearch.setLastDateSearched(searchedDate);
					monitoringSearch.setExpectedPosts(LIMIT_PER_CALL);
					monitoringSearch.setSearches(monitoringSearch.getSearches() + 1L);
					monitoringSearch.setReturnedPosts(newPostsCount);
					
					monitoringSearchRemote.save(monitoringSearch);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * 
	 * Método responsável por verificar a relevância da pesquisa do termo e 
	 * decidir se a pesquisa deve ser feita ou não.
	 * 
	 * @param term
	 * @param monitoring
	 * @return
	 */
	private static Boolean isToSearch(String term, Monitoring monitoring) {
		
		Boolean isToSearch = true;
		
		MaintenanceMonitoringSearchRemote monitoringSearchRemote = 
				(MaintenanceMonitoringSearchRemote) RecoverMaintenance.recoverMaintenance("MonitoringSearch");
		
		MonitoringSearchId monitoringSearchId = new MonitoringSearchId();
		monitoringSearchId.setMonitoring(monitoring.getIdMonitoring());
		monitoringSearchId.setTerm(term);
		monitoringSearchId.setNetwork(Profile.RECLAMEAQUI);
		
		MonitoringSearch monitoringSearch = monitoringSearchRemote.getById(monitoringSearchId);
		
		if(monitoringSearch == null) {
			
			monitoringSearch = new MonitoringSearch();
			monitoringSearch.setMonitoring(monitoring);
			monitoringSearch.setTerm(term);
			monitoringSearch.setNetwork(Profile.RECLAMEAQUI);
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
				
				// Se o intervalo de tempo for menor do que o máximo definido para que uma busca 
				// volte a fazer chamadas(SEARCH_CYCLE_TIME * SEARCH_CYCLE_THRESHOLD), as chamadas serão canceladas até que 
				// o tempo máximo limite sem chamadas seja ultrapassado.
				if((nowDate - monitoringSearch.getLastDateSearched()) < (SEARCH_CYCLE_TIME * SEARCH_CYCLE_THRESHOLD)) {
					isToSearch = false;
				}
				
			}
			
		}
		
		return isToSearch;
	}
	
	private static MonitoringSearch getMonitoringSearch(Long idMonitoring, String term) {
	
		MaintenanceMonitoringSearchRemote monitoringSearchRemote = 
				(MaintenanceMonitoringSearchRemote) RecoverMaintenance.recoverMaintenance("MonitoringSearch");
		
		MonitoringSearchId monitoringSearchId = new MonitoringSearchId();
		monitoringSearchId.setMonitoring(idMonitoring);
		monitoringSearchId.setTerm(term);
		monitoringSearchId.setNetwork(Profile.RECLAMEAQUI);
		
		return monitoringSearchRemote.getById(monitoringSearchId);
	}
	
	private static boolean validateIgnore(Result result, Monitoring monitoring) {

		if(monitoring.getIgnoreTerms() == null) {
			return true;
		} else {
			String[] ignoreTerms = monitoring.getIgnoreTerms().split(";");
			
			if(result.getTitle() != null) {
				for(String ignoreTerm : ignoreTerms) {
					if(removeLetterSpecialCharacter(result.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(ignoreTerm).toLowerCase())) {
						return false;
					}
				}
			}
			
			if(result.getSnippet() != null){
				for(String ignoreTerm : ignoreTerms) {
					if(removeLetterSpecialCharacter(result.getSnippet()).toLowerCase()
						.contains(removeLetterSpecialCharacter(ignoreTerm).toLowerCase())) {
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	public static String removeLetterSpecialCharacter(String value) {
		
		value = value.replaceAll("[ÂÀÁÄÃ]", "A");
		value = value.replaceAll("[âãàáä]", "a");
		value = value.replaceAll("[ÊÈÉË]", "E");
		value = value.replaceAll("[êèéë]", "e");
		value = value.replaceAll("ÎÍÌÏ", "I");
		value = value.replaceAll("îíìï", "i");
		value = value.replaceAll("[ÔÕÒÓÖ]", "O");
		value = value.replaceAll("[ôõòóö]", "o");
		value = value.replaceAll("[ÛÙÚÜ]", "U");
		value = value.replaceAll("[ûúùü]", "u");
		value = value.replaceAll("Ç", "C");
		value = value.replaceAll("ç", "c");
		
		return value;
	}
	
	private static ReclameAquiPostMonitoring executeAutomaticPositiveQualification(Result result, ReclameAquiPostMonitoring reclameAquiPostMonitoring, Monitoring monitoring) {
		
		if(monitoring.getAutoPositiveTerms() != null) {
			
			String[] terms = monitoring.getAutoPositiveTerms().split(";");
			
			if(result.getTitle() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(result.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						reclameAquiPostMonitoring.setQualification(PostMonitoringDTO.POSITIVE);
						return reclameAquiPostMonitoring;
					}
				}
			}
			
			if(result.getSnippet() != null){
				for(String ignoreTerm : terms) {
					if(removeLetterSpecialCharacter(result.getSnippet()).toLowerCase()
						.contains(removeLetterSpecialCharacter(ignoreTerm).toLowerCase())) {
						reclameAquiPostMonitoring.setQualification(PostMonitoringDTO.POSITIVE);
						return reclameAquiPostMonitoring;
					}
				}
			}
		}
		
		return reclameAquiPostMonitoring;
	}
	
	private static ReclameAquiPostMonitoring executeAutomaticNegativeQualification(Result result, ReclameAquiPostMonitoring reclameAquiPostMonitoring, Monitoring monitoring) {
		
		if(monitoring.getAutoNegativeTerms() != null) {
			
			String[] terms = monitoring.getAutoNegativeTerms().split(";");
			
			if(result.getTitle() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(result.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						reclameAquiPostMonitoring.setQualification(PostMonitoringDTO.NEGATIVE);
						return reclameAquiPostMonitoring;
					}
				}
			} 
			
			if(result.getSnippet() != null){
				for(String ignoreTerm : terms) {
					if(removeLetterSpecialCharacter(result.getSnippet()).toLowerCase()
						.contains(removeLetterSpecialCharacter(ignoreTerm).toLowerCase())) {
						reclameAquiPostMonitoring.setQualification(PostMonitoringDTO.NEGATIVE);
						return reclameAquiPostMonitoring;
					}
				}
			}
		}
		
		return reclameAquiPostMonitoring;
	}

	private static ReclameAquiPostMonitoring executeAutomaticNeuterQualification(Result result, ReclameAquiPostMonitoring reclameAquiPostMonitoring, Monitoring monitoring) {
	
		if(monitoring.getAutoNeuterTerms() != null) {
			
			String[] terms = monitoring.getAutoNeuterTerms().split(";");
			
			if(result.getTitle() != null) {
				for(String term : terms) {
					if(removeLetterSpecialCharacter(result.getTitle()).toLowerCase()
						.contains(removeLetterSpecialCharacter(term).toLowerCase())) {
						reclameAquiPostMonitoring.setQualification(PostMonitoringDTO.NEUTER);
						return reclameAquiPostMonitoring;
					}
				}
			}
			
			if(result.getSnippet() != null){
				for(String ignoreTerm : terms) {
					if(removeLetterSpecialCharacter(result.getSnippet()).toLowerCase()
						.contains(removeLetterSpecialCharacter(ignoreTerm).toLowerCase())) {
						reclameAquiPostMonitoring.setQualification(PostMonitoringDTO.NEUTER);
						return reclameAquiPostMonitoring;
					}
				}
			}
		}
		
		return reclameAquiPostMonitoring;
	}
}
