package br.com.opsocial.server.utils.reports;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.naming.NamingException;

import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.facebook.FacebookAPI;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportControlRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportRemote;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceReportControlRemote;
import br.com.opsocial.ejb.das.MaintenanceReportGenerateRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.report.ReportGenerate;

public abstract class ReportGenerateVerification {

	public ReportGenerateVerification() {

	}

	public static boolean isReportActive(ReportGenerate reportGenerate) throws Exception {

		if(new FacebookAPI().recoverValueByProperty(reportGenerate.getNetworkId(), reportGenerate.getTokenSecret(), FacebookAPI.INSIGHT_PAGE_FANS) > Profile.FANS_FACEBOOK_TO_REPORT) {
			return true;
		} else {
			return false;
		}
	}

	public static void removeRefreshTokenState(List<Profile> profiles) {

		try {

			MaintenanceReportGenerateRemote reportGenerateRemote = (MaintenanceReportGenerateRemote) RecoverMaintenance.recoverMaintenance("ReportGenerate");

			if(!profiles.isEmpty()) {
				
				ReportGenerateThread reportGenerateThread = new ReportGenerateThread();

				for(Profile profile : profiles) {

					try {
						
						if(reportGenerateRemote.hasEntity(profile.getNetworkId(), profile.getType())) {

							ReportGenerate reportGenerate = reportGenerateRemote.getEntity(profile.getNetworkId(), profile.getType());

							if(profile.getType().equals(Profile.FACEBOOK_PAGE)) {
								
								reportGenerate.setTokenSecret(profile.getToken());
								
								if(ReportGenerateVerification.isReportActive(reportGenerate) && !reportGenerate.getState().equals(ReportFacebook.GENERATING)) {

									reportGenerate.setState(ReportFacebook.FINALIZED);
									reportGenerate = reportGenerateRemote.save(reportGenerate);
									
								} else {

									reportGenerate.setState(ReportFacebook.UNAVAILABLE);
									reportGenerateRemote.save(reportGenerate);
								}
								
							} else if(profile.getType().equals(Profile.INSTAGRAM)) {
								
								reportGenerate.setTokenSecret(profile.getToken());
								
								if(!reportGenerate.getState().equals(ReportFacebook.GENERATING)) {
									
									reportGenerate.setState(ReportFacebook.FINALIZED);
									reportGenerate = reportGenerateRemote.save(reportGenerate);
								}
							}						
							
						} else {

							ReportGenerate reportGenerate = new ReportGenerate();
							reportGenerate.setNetworkId(profile.getNetworkId());
							reportGenerate.setNetworkType(profile.getType());
							reportGenerate.setTokenSecret(profile.getToken());
							reportGenerate.setState(ReportFacebook.UNAVAILABLE);

							// Verifica se a página pode resgatar seus insights.
							if(ReportGenerateVerification.isReportActive(reportGenerate)) {

								reportGenerate.setState(ReportFacebook.GENERATING);
								reportGenerate = reportGenerateRemote.save(reportGenerate);

								reportGenerateThread.addReportGenerateRunnable(new ReportGenerateRunnable(profile, ReportGenerateRunnable.REPORT_DAYS));
							} else {

								reportGenerate = reportGenerateRemote.save(reportGenerate);
							}
						}
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				reportGenerateThread.start();
			}

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static List<Long> getListDaysForInstagramReport(String networkId, Character networkType, String type) {

		ZonedDateTime dateUntil;
		List<Long> datesToSearch;
		List<Long> datesSearched;

		datesToSearch = new ArrayList<Long>();
		
		LocalDateTime untilDate = LocalDateTime.now(ZoneId.of("UTC"));
		dateUntil = UtilFunctions.getDateForInstagramReport(untilDate.minusDays(2));
		
		MaintenanceReportControlRemote remoteReportControl = (MaintenanceReportControlRemote) 
				RecoverMaintenance.recoverMaintenance("ReportControl");

		datesSearched = remoteReportControl.getDates(networkId, networkType, type);

		// TODO: finish this routine.
		if(!datesSearched.isEmpty()) {
			
			ZonedDateTime dateSince = UtilFunctions.getDateForInstagramReport(LocalDateTime.ofEpochSecond(datesSearched.get(0), 0, ZoneOffset.UTC)); 
			
			List<ZonedDateTime> reportDates = Stream.iterate(dateSince, date -> date.plusDays(1))
				    .limit(ChronoUnit.DAYS.between(dateSince, dateUntil) + 1L)
				    .collect(Collectors.toList());
			
			for(ZonedDateTime currentDay : reportDates) {
				
				Long currentDayInSeconds = currentDay.toEpochSecond();
				
				if(!datesSearched.contains(currentDayInSeconds)) {
					datesToSearch.add(currentDayInSeconds);
				}
			}
		}
		
		return datesToSearch;
	}

	public static List<Long> getListDaysForFacebookReport(String networkId, Character networkType, String type) {

		Long dateUntil;
		List<Long> datesToSearch;
		List<Long> datesSearched;

		datesToSearch = new ArrayList<Long>();

		Calendar untilDate = new GregorianCalendar();
		untilDate.set(Calendar.DAY_OF_MONTH, untilDate.get(Calendar.DAY_OF_MONTH) - 3);

		dateUntil = UtilFunctions.getDateForFacebookReport(untilDate);

		MaintenanceReportControlRemote remoteReportControl = (MaintenanceReportControlRemote) 
				RecoverMaintenance.recoverMaintenance("ReportControl");

		datesSearched = remoteReportControl.getDates(networkId, networkType, type);

		if(!datesSearched.isEmpty()) {

			for(Long day = datesSearched.get(0); day <= dateUntil; day += 86400) {
				if(!datesSearched.contains(day)) {
					datesToSearch.add(day);
				}
			}

		}

		return datesToSearch;
	}
	
	public static List<Long> getListDaysForMonitoringReport(Monitoring monitoring, String type, Long dateUntil) {
	
		Long dateFrom;
		List<Long> datesToSearch;
		List<Long> datesSearched;

		datesToSearch = new ArrayList<Long>();

		try {
			
			dateFrom = UtilFunctions.getMidnightDateNoDaylightTime(new Date(monitoring.getCreatedAt() * 1000L));

			MaintenanceMonitoringReportControlRemote monitoringReportControlRemote =
					(MaintenanceMonitoringReportControlRemote) RecoverMaintenance.recoverMaintenance("MonitoringReportControl");

			datesSearched = monitoringReportControlRemote.getDates(monitoring.getIdMonitoring(), type);

			for(Long day = dateFrom; day <= dateUntil; day += 86400) {
				if(!datesSearched.contains(day)) {
					datesToSearch.add(day);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datesToSearch;
	}

	public static List<Long> getListDaysForMonitoringReport(Monitoring monitoring, Long dateUntil) {

		Long dateFrom;
		List<Long> datesToSearch;
		List<Long> datesSearched;

		datesToSearch = new ArrayList<Long>();

		try {
			
			dateFrom = UtilFunctions.getMidnightDateNoDaylightTime(new Date(monitoring.getCreatedAt() * 1000L));

			MaintenanceMonitoringReportRemote remoteMonitoringReportRemote = (MaintenanceMonitoringReportRemote)
					RecoverMaintenance.recoverMaintenance("MonitoringReport");

			datesSearched = remoteMonitoringReportRemote.getDatesGeneratedReports(monitoring.getIdMonitoring());

			for(Long day = dateFrom; day <= dateUntil; day += 86400) {
				if(!datesSearched.contains(day)) {
					datesToSearch.add(day);
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return datesToSearch;
	}

	public static void setRefreshTokenState(Profile profile, Boolean isChangeAdmin) {

		try {

			MaintenanceReportGenerateRemote reportGenerateRemote = (MaintenanceReportGenerateRemote) RecoverMaintenance.recoverMaintenance("ReportGenerate");

			MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");

			if(!isChangeAdmin) {
				
				List<Profile> pagesProfile;

				if(profile.getType().equals(Profile.FACEBOOK_PAGE) || profile.getType().equals(Profile.INSTAGRAM)) {
					pagesProfile = new ArrayList<Profile>();
					if(profile.getParentProfile() == null) {
						pagesProfile = profileRemote.getEntitiesByProfileInformation(profile.getProfileInformation().split("♪")[0], 
								profile.getAccount().getIdAccount());
					} else {
						pagesProfile = profileRemote.getChildrenByProfile(profile.getParentProfile().getIdProfile());
					}
				} else {
					pagesProfile = profileRemote.getChildrenByProfile(profile.getIdProfile());
				}

				for(Profile page : pagesProfile) {

					ReportGenerate reportGenerate = reportGenerateRemote.getEntity(page.getNetworkId(), page.getType());

					reportGenerate.setState(ReportFacebook.REFRESH_TOKEN);
					reportGenerateRemote.save(reportGenerate);
				}
				
			} else {
				
				ReportGenerate reportGenerate = reportGenerateRemote.getEntity(profile.getNetworkId(), profile.getType());

				reportGenerate.setState(ReportFacebook.REFRESH_TOKEN);
				reportGenerateRemote.save(reportGenerate);
			}

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}

}
