package br.com.opsocial.server.utils.monitorings;

import java.util.Date;
import java.util.List;

import br.com.opsocial.client.entity.monitoring.MonitoringReportDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceFacePostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceGooglePlusPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringGroupReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringInfluentialUserRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLocationRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLocationReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringQualificationReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportControlRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTermReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringUserReportRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceYoutubePostMonitoringRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringGroupReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLocationReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringQualificationReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReportControl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTagReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTermReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringUser;
import br.com.opsocial.ejb.entity.monitoring.MonitoringUserReport;

public class MonitoringReportGenerate {
	
	private Monitoring monitoring;
	private Long generateDay;
	
	private MaintenanceMonitoringReportRemote monitoringReportRemote;
	private MaintenanceMonitoringTermReportRemote monitoringTermReportRemote;
	private MaintenanceMonitoringQualificationReportRemote monitoringQualificationReportRemote;
	private MaintenanceMonitoringTagReportRemote monitoringTagReportRemote;
	private MaintenanceMonitoringTagRemote monitoringTagRemote;
	private MaintenanceMonitoringUserReportRemote monitoringUserReportRemote;
	private MaintenanceMonitoringLocationReportRemote monitoringLocationReportRemote;
	private MaintenanceMonitoringGroupReportRemote monitoringGroupReportRemote;
	private MaintenanceMonitoringReportControlRemote monitoringReportControlRemote;
	
	private MaintenanceFacePostMonitoringRemote facePostMonitoringRemote;
	private MaintenanceTwitterPostMonitoringRemote twitterPostMonitoringRemote;
	private MaintenanceGooglePlusPostMonitoringRemote googlePlusPostMonitoringRemote;
	private MaintenanceYoutubePostMonitoringRemote youtubePostMonitoringRemote;
	private MaintenanceInstagramPostMonitoringRemote instagramPostMonitoringRemote;
	private MaintenanceReclameAquiPostMonitoringRemote reclameAquiPostMonitoringRemote;
	private MaintenanceNewsPostMonitoringRemote newsPostMonitoringRemote;
	private MaintenanceBlogPostMonitoringRemote blogPostMonitoringRemote;
	
	private MaintenanceMonitoringInfluentialUserRemote monitoringInfluentialUserRemote;
	private MaintenanceMonitoringLocationRemote monitoringLocationRemote;
	
	private MaintenanceProfileRemote profileRemote;
	
	public MonitoringReportGenerate(Monitoring monitoring, Long generateDay) {
		
		this.monitoring = monitoring;
		this.generateDay = generateDay;
		
		monitoringReportRemote = (MaintenanceMonitoringReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringReport");
		monitoringTermReportRemote = (MaintenanceMonitoringTermReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringTermReport");
		monitoringQualificationReportRemote = (MaintenanceMonitoringQualificationReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringQualificationReport");
		monitoringTagReportRemote = (MaintenanceMonitoringTagReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringTagReport");
		monitoringTagRemote = (MaintenanceMonitoringTagRemote) RecoverMaintenance.recoverMaintenance("MonitoringTag");
		monitoringUserReportRemote = (MaintenanceMonitoringUserReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringUserReport");
		monitoringLocationReportRemote = (MaintenanceMonitoringLocationReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringLocationReport");
		monitoringGroupReportRemote = (MaintenanceMonitoringGroupReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringGroupReport");
		monitoringReportControlRemote= (MaintenanceMonitoringReportControlRemote) RecoverMaintenance.recoverMaintenance("MonitoringReportControl");
		
		facePostMonitoringRemote = (MaintenanceFacePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("FacePostMonitoring");
		twitterPostMonitoringRemote = (MaintenanceTwitterPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");
		googlePlusPostMonitoringRemote = (MaintenanceGooglePlusPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("GooglePlusPostMonitoring");
		youtubePostMonitoringRemote = (MaintenanceYoutubePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("YoutubePostMonitoring");
		instagramPostMonitoringRemote = (MaintenanceInstagramPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("InstagramPostMonitoring");
		reclameAquiPostMonitoringRemote = (MaintenanceReclameAquiPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("ReclameAquiPostMonitoring");
		newsPostMonitoringRemote = (MaintenanceNewsPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("NewsPostMonitoring");
		blogPostMonitoringRemote = (MaintenanceBlogPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("BlogPostMonitoring");
		
		monitoringInfluentialUserRemote = (MaintenanceMonitoringInfluentialUserRemote) RecoverMaintenance.recoverMaintenance("MonitoringInfluentialUser");
		monitoringLocationRemote = (MaintenanceMonitoringLocationRemote) RecoverMaintenance.recoverMaintenance("MonitoringLocation");
		
		profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
	}
	
	public void generateReport() {
		
		saveCountOfPostsPerDay();
		saveCountOfGarbagePostsPerDay();
		saveCountOfPostsPerDayHour();
	}
	
	public void generateTermReport() {
		
		try {
			
			if(!monitoringReportControlRemote.hasEntity(monitoring.getIdMonitoring(), MonitoringReportControl.TERM, generateDay)) {
				
				MonitoringReportControl monitoringReportControl = new MonitoringReportControl();
				monitoringReportControl.setMonitoring(monitoring);
				monitoringReportControl.setType(MonitoringReportControl.TERM);
				monitoringReportControl.setDate(generateDay);
				monitoringReportControl.setStartDate(new Date().getTime() / 1000L);
				
				try {
					
					monitoringReportControl =  monitoringReportControlRemote.save(monitoringReportControl);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				savePostsTermPerDay();
				
				monitoringReportControl = monitoringReportControlRemote.getById(monitoringReportControl.getIdMonitoringReportControl());

				monitoringReportControl.setFinishDate(new Date().getTime() / 1000L);
				monitoringReportControlRemote.save(monitoringReportControl);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generateTagReport() {
		
		try {
			
			if(!monitoringReportControlRemote.hasEntity(monitoring.getIdMonitoring(), MonitoringReportControl.TAG, generateDay)) {
				
				MonitoringReportControl monitoringReportControl = new MonitoringReportControl();
				monitoringReportControl.setMonitoring(monitoring);
				monitoringReportControl.setType(MonitoringReportControl.TAG);
				monitoringReportControl.setDate(generateDay);
				monitoringReportControl.setStartDate(new Date().getTime() / 1000L);
				
				try {
					
					monitoringReportControl = monitoringReportControlRemote.save(monitoringReportControl);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				saveTagPostsPerDay();
				
				monitoringReportControl = monitoringReportControlRemote.getById(monitoringReportControl.getIdMonitoringReportControl());

				monitoringReportControl.setFinishDate(new Date().getTime() / 1000L);
				monitoringReportControlRemote.save(monitoringReportControl);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void generateQualificationReport() {
		
		try {
			
			if(!monitoringReportControlRemote.hasEntity(monitoring.getIdMonitoring(), MonitoringReportControl.QUALIFICATION, generateDay)) {
				
				MonitoringReportControl monitoringReportControl = new MonitoringReportControl();
				monitoringReportControl.setMonitoring(monitoring);
				monitoringReportControl.setType(MonitoringReportControl.QUALIFICATION);
				monitoringReportControl.setDate(generateDay);
				monitoringReportControl.setStartDate(new Date().getTime() / 1000L);
				
				try {
					
					monitoringReportControl = monitoringReportControlRemote.save(monitoringReportControl);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				saveQualificationPostsPerDay();
				
				monitoringReportControl = monitoringReportControlRemote.getById(monitoringReportControl.getIdMonitoringReportControl());

				monitoringReportControl.setFinishDate(new Date().getTime() / 1000L);
				monitoringReportControlRemote.save(monitoringReportControl);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void generateLocationReport() {
		
		try {
			
			if(!monitoringReportControlRemote.hasEntity(monitoring.getIdMonitoring(), MonitoringReportControl.LOCATION, generateDay)) {
			
				MonitoringReportControl monitoringReportControl = new MonitoringReportControl();
				monitoringReportControl.setMonitoring(monitoring);
				monitoringReportControl.setType(MonitoringReportControl.LOCATION);
				monitoringReportControl.setDate(generateDay);
				monitoringReportControl.setStartDate(new Date().getTime() / 1000L);
				
				try {
					
					monitoringReportControl = monitoringReportControlRemote.save(monitoringReportControl);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				saveLocationsReports();
				
				monitoringReportControl = monitoringReportControlRemote.getById(monitoringReportControl.getIdMonitoringReportControl());

				monitoringReportControl.setFinishDate(new Date().getTime() / 1000L);
				monitoringReportControlRemote.save(monitoringReportControl);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void generateUserReport() {
		
		try {
			
			if(!monitoringReportControlRemote.hasEntity(monitoring.getIdMonitoring(), MonitoringReportControl.USERS, generateDay)) {
			
				MonitoringReportControl monitoringReportControl = new MonitoringReportControl();
				monitoringReportControl.setMonitoring(monitoring);
				monitoringReportControl.setType(MonitoringReportControl.USERS);
				monitoringReportControl.setDate(generateDay);
				monitoringReportControl.setStartDate(new Date().getTime() / 1000L);
				
				try {
					
					monitoringReportControl = monitoringReportControlRemote.save(monitoringReportControl);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				saveUsersReports();
				
				monitoringReportControl = monitoringReportControlRemote.getById(monitoringReportControl.getIdMonitoringReportControl());

				monitoringReportControl.setFinishDate(new Date().getTime() / 1000L);
				monitoringReportControlRemote.save(monitoringReportControl);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void saveLocationsReports() {
		
		List<Object[]> locations = monitoringLocationRemote.getPostsCitiesCountByDay(monitoring.getIdMonitoring(), generateDay);
		
		for (int i = 0; i < locations.size(); i++) {
			
			try {
				
				MonitoringLocationReport monitoringLocationReport = new MonitoringLocationReport();
				monitoringLocationReport.setMonitoring(monitoring);
				monitoringLocationReport.setLocation((String) locations.get(i)[1]);
				monitoringLocationReport.setValue((Long) locations.get(i)[2]);
				monitoringLocationReport.setDate(generateDay);
				
				monitoringLocationReportRemote.save(monitoringLocationReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
	}
	
	private void saveUsersReports() {
		
		MonitoringUserReport monitoringUsersTotal = new MonitoringUserReport();
		monitoringUsersTotal.setMonitoring(monitoring);
		monitoringUsersTotal.setProperty(MonitoringUserReport.USERS_COUNT);
		monitoringUsersTotal.setValue(monitoringInfluentialUserRemote.getPostsUsersPerDayCount(monitoring.getIdMonitoring(), generateDay));
		monitoringUsersTotal.setDate(generateDay);
		
		try {
			
			monitoringUserReportRemote.save(monitoringUsersTotal);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringUserReport monitoringUsersFemaleTotal = new MonitoringUserReport();
		monitoringUsersFemaleTotal.setMonitoring(monitoring);
		monitoringUsersFemaleTotal.setProperty(MonitoringUserReport.USERS_FEMALE_COUNT);
		monitoringUsersFemaleTotal.setValue(monitoringInfluentialUserRemote.getPostsUsersPerGenderCount(
				monitoring.getId(), generateDay, MonitoringUser.FEMALE));
		monitoringUsersFemaleTotal.setDate(generateDay);
		
		try {
			
			monitoringUserReportRemote.save(monitoringUsersFemaleTotal);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringUserReport monitoringUsersMaleTotal = new MonitoringUserReport();
		monitoringUsersMaleTotal.setMonitoring(monitoring);
		monitoringUsersMaleTotal.setProperty(MonitoringUserReport.USERS_MALE_COUNT);
		monitoringUsersMaleTotal.setValue(monitoringInfluentialUserRemote.getPostsUsersPerGenderCount(
				monitoring.getId(), generateDay, MonitoringUser.MALE));
		monitoringUsersMaleTotal.setDate(generateDay);
		
		try {
			
			monitoringUserReportRemote.save(monitoringUsersMaleTotal);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void saveTagPostsPerDay() {
		
		List<Object[]> tagsPostsFacebook = facePostMonitoringRemote.getCountTagsPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for (int i = 0; i < tagsPostsFacebook.size(); i++) {
			
			try {
			
				Long idTag = (Long) tagsPostsFacebook.get(i)[0];

				MonitoringTagReport monitoringTagReport = new MonitoringTagReport();
				monitoringTagReport.setMonitoring(monitoring);
				monitoringTagReport.setMonitoringTag(monitoringTagRemote.getById(idTag));
				monitoringTagReport.setNetworkType(Profile.FACEBOOK);
				monitoringTagReport.setDate(generateDay);
				monitoringTagReport.setValue((Long) tagsPostsFacebook.get(i)[1]);
			
				monitoringTagReportRemote.save(monitoringTagReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> tagsPostsTwitter = twitterPostMonitoringRemote.getCountTagsPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for (int i = 0; i < tagsPostsTwitter.size(); i++) {
			
			try {
			
				Long idTag = (Long) tagsPostsTwitter.get(i)[0];

				MonitoringTagReport monitoringTagReport = new MonitoringTagReport();
				monitoringTagReport.setMonitoring(monitoring);
				monitoringTagReport.setMonitoringTag(monitoringTagRemote.getById(idTag));
				monitoringTagReport.setNetworkType(Profile.TWITTER);
				monitoringTagReport.setDate(generateDay);
				monitoringTagReport.setValue((Long) tagsPostsTwitter.get(i)[1]);
			
				monitoringTagReportRemote.save(monitoringTagReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> tagsPostsGooglePlus = googlePlusPostMonitoringRemote.getCountTagsPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for (int i = 0; i < tagsPostsGooglePlus.size(); i++) {
			
			try {
			
				Long idTag = (Long) tagsPostsGooglePlus.get(i)[0];

				MonitoringTagReport monitoringTagReport = new MonitoringTagReport();
				monitoringTagReport.setMonitoring(monitoring);
				monitoringTagReport.setMonitoringTag(monitoringTagRemote.getById(idTag));
				monitoringTagReport.setNetworkType(Profile.GOOGLE);
				monitoringTagReport.setDate(generateDay);
				monitoringTagReport.setValue((Long) tagsPostsGooglePlus.get(i)[1]);
			
				monitoringTagReportRemote.save(monitoringTagReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> tagsPostsYoutube = youtubePostMonitoringRemote.getCountTagsPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for (int i = 0; i < tagsPostsYoutube.size(); i++) {
			
			try {
			
				Long idTag = (Long) tagsPostsYoutube.get(i)[0];

				MonitoringTagReport monitoringTagReport = new MonitoringTagReport();
				monitoringTagReport.setMonitoring(monitoring);
				monitoringTagReport.setMonitoringTag(monitoringTagRemote.getById(idTag));
				monitoringTagReport.setNetworkType(Profile.YOUTUBE);
				monitoringTagReport.setDate(generateDay);
				monitoringTagReport.setValue((Long) tagsPostsYoutube.get(i)[1]);
			
				monitoringTagReportRemote.save(monitoringTagReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> tagsPostsInstagram = instagramPostMonitoringRemote.getCountTagsPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for (int i = 0; i < tagsPostsInstagram.size(); i++) {
			
			try {
			
				Long idTag = (Long) tagsPostsInstagram.get(i)[0];

				MonitoringTagReport monitoringTagReport = new MonitoringTagReport();
				monitoringTagReport.setMonitoring(monitoring);
				monitoringTagReport.setMonitoringTag(monitoringTagRemote.getById(idTag));
				monitoringTagReport.setNetworkType(Profile.INSTAGRAM);
				monitoringTagReport.setDate(generateDay);
				monitoringTagReport.setValue((Long) tagsPostsInstagram.get(i)[1]);
			
				monitoringTagReportRemote.save(monitoringTagReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> tagsPostsReclameAqui = reclameAquiPostMonitoringRemote.getCountTagsPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for (int i = 0; i < tagsPostsReclameAqui.size(); i++) {
			
			try {
			
				Long idTag = (Long) tagsPostsReclameAqui.get(i)[0];

				MonitoringTagReport monitoringTagReport = new MonitoringTagReport();
				monitoringTagReport.setMonitoring(monitoring);
				monitoringTagReport.setMonitoringTag(monitoringTagRemote.getById(idTag));
				monitoringTagReport.setNetworkType(Profile.RECLAMEAQUI);
				monitoringTagReport.setDate(generateDay);
				monitoringTagReport.setValue((Long) tagsPostsReclameAqui.get(i)[1]);
			
				monitoringTagReportRemote.save(monitoringTagReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> tagsPostsNews = newsPostMonitoringRemote.getCountTagsPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for (int i = 0; i < tagsPostsNews.size(); i++) {
			
			try {
			
				Long idTag = (Long) tagsPostsNews.get(i)[0];

				MonitoringTagReport monitoringTagReport = new MonitoringTagReport();
				monitoringTagReport.setMonitoring(monitoring);
				monitoringTagReport.setMonitoringTag(monitoringTagRemote.getById(idTag));
				monitoringTagReport.setNetworkType(Profile.NEWS);
				monitoringTagReport.setDate(generateDay);
				monitoringTagReport.setValue((Long) tagsPostsNews.get(i)[1]);
			
				monitoringTagReportRemote.save(monitoringTagReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> tagsPostsBlogs = blogPostMonitoringRemote.getCountTagsPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for (int i = 0; i < tagsPostsBlogs.size(); i++) {
			
			try {
			
				Long idTag = (Long) tagsPostsBlogs.get(i)[0];

				MonitoringTagReport monitoringTagReport = new MonitoringTagReport();
				monitoringTagReport.setMonitoring(monitoring);
				monitoringTagReport.setMonitoringTag(monitoringTagRemote.getById(idTag));
				monitoringTagReport.setNetworkType(Profile.BLOGS);
				monitoringTagReport.setDate(generateDay);
				monitoringTagReport.setValue((Long) tagsPostsBlogs.get(i)[1]);
			
				monitoringTagReportRemote.save(monitoringTagReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void saveQualificationPostsPerDay() {
		
		List<Object[]> qualificationPostsFacebook = facePostMonitoringRemote.getCountQualificPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < qualificationPostsFacebook.size(); i++) {
			
			try {
			
				MonitoringQualificationReport monitoringQualificationReport = new MonitoringQualificationReport();
				monitoringQualificationReport.setMonitoring(monitoring);
				monitoringQualificationReport.setNetworkType(Profile.FACEBOOK);
				monitoringQualificationReport.setDate(generateDay);
				monitoringQualificationReport.setQualification(((String) qualificationPostsFacebook.get(i)[0]).charAt(0));
				monitoringQualificationReport.setTerm((String) qualificationPostsFacebook.get(i)[1]);
				monitoringQualificationReport.setValue((Long) qualificationPostsFacebook.get(i)[2]);

				monitoringQualificationReportRemote.save(monitoringQualificationReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> qualificationPostsTwitter = twitterPostMonitoringRemote.getCountQualificPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < qualificationPostsTwitter.size(); i++) {
			
			try {

				MonitoringQualificationReport monitoringQualificationReport = new MonitoringQualificationReport();
				monitoringQualificationReport.setMonitoring(monitoring);
				monitoringQualificationReport.setNetworkType(Profile.TWITTER);
				monitoringQualificationReport.setDate(generateDay);
				monitoringQualificationReport.setQualification(((String) qualificationPostsTwitter.get(i)[0]).charAt(0));
				monitoringQualificationReport.setTerm((String) qualificationPostsTwitter.get(i)[1]);
				monitoringQualificationReport.setValue((Long) qualificationPostsTwitter.get(i)[2]);

				monitoringQualificationReportRemote.save(monitoringQualificationReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> qualificationPostsGooglePlus = googlePlusPostMonitoringRemote.getCountQualificPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < qualificationPostsGooglePlus.size(); i++) {
			
			try {

				MonitoringQualificationReport monitoringQualificationReport = new MonitoringQualificationReport();
				monitoringQualificationReport.setMonitoring(monitoring);
				monitoringQualificationReport.setNetworkType(Profile.GOOGLE);
				monitoringQualificationReport.setDate(generateDay);
				monitoringQualificationReport.setQualification(((String) qualificationPostsGooglePlus.get(i)[0]).charAt(0));
				monitoringQualificationReport.setTerm((String) qualificationPostsGooglePlus.get(i)[1]);
				monitoringQualificationReport.setValue((Long) qualificationPostsGooglePlus.get(i)[2]);

				monitoringQualificationReportRemote.save(monitoringQualificationReport);

			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> qualificationPostsYoutube = youtubePostMonitoringRemote.getCountQualificPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < qualificationPostsYoutube.size(); i++) {
			
			try {
			
				MonitoringQualificationReport monitoringQualificationReport = new MonitoringQualificationReport();
				monitoringQualificationReport.setMonitoring(monitoring);
				monitoringQualificationReport.setNetworkType(Profile.YOUTUBE);
				monitoringQualificationReport.setDate(generateDay);
				monitoringQualificationReport.setQualification(((String) qualificationPostsYoutube.get(i)[0]).charAt(0));
				monitoringQualificationReport.setTerm((String) qualificationPostsYoutube.get(i)[1]);
				monitoringQualificationReport.setValue((Long) qualificationPostsYoutube.get(i)[2]);
				
				monitoringQualificationReportRemote.save(monitoringQualificationReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> qualificationPostsInstagram = instagramPostMonitoringRemote.getCountQualificPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < qualificationPostsInstagram.size(); i++) {
			
			try {
			
				MonitoringQualificationReport monitoringQualificationReport = new MonitoringQualificationReport();
				monitoringQualificationReport.setMonitoring(monitoring);
				monitoringQualificationReport.setNetworkType(Profile.INSTAGRAM);
				monitoringQualificationReport.setDate(generateDay);
				monitoringQualificationReport.setQualification(((String) qualificationPostsInstagram.get(i)[0]).charAt(0));
				monitoringQualificationReport.setTerm((String) qualificationPostsInstagram.get(i)[1]);
				monitoringQualificationReport.setValue((Long) qualificationPostsInstagram.get(i)[2]);

				monitoringQualificationReportRemote.save(monitoringQualificationReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> qualificationPostsReclameAqui = reclameAquiPostMonitoringRemote.getCountQualificPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < qualificationPostsReclameAqui.size(); i++) {
			
			try {
			
				MonitoringQualificationReport monitoringQualificationReport = new MonitoringQualificationReport();
				monitoringQualificationReport.setMonitoring(monitoring);
				monitoringQualificationReport.setNetworkType(Profile.RECLAMEAQUI);
				monitoringQualificationReport.setDate(generateDay);
				monitoringQualificationReport.setQualification(((String) qualificationPostsReclameAqui.get(i)[0]).charAt(0));
				monitoringQualificationReport.setTerm((String) qualificationPostsReclameAqui.get(i)[1]);
				monitoringQualificationReport.setValue((Long) qualificationPostsReclameAqui.get(i)[2]);

				monitoringQualificationReportRemote.save(monitoringQualificationReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> qualificationPostsNews = newsPostMonitoringRemote.getCountQualificPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < qualificationPostsNews.size(); i++) {
			
			try {
			
				MonitoringQualificationReport monitoringQualificationReport = new MonitoringQualificationReport();
				monitoringQualificationReport.setMonitoring(monitoring);
				monitoringQualificationReport.setNetworkType(Profile.NEWS);
				monitoringQualificationReport.setDate(generateDay);
				monitoringQualificationReport.setQualification(((String) qualificationPostsNews.get(i)[0]).charAt(0));
				monitoringQualificationReport.setTerm((String) qualificationPostsNews.get(i)[1]);
				monitoringQualificationReport.setValue((Long) qualificationPostsNews.get(i)[2]);

				monitoringQualificationReportRemote.save(monitoringQualificationReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> qualificationPostsBlogs = blogPostMonitoringRemote.getCountQualificPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < qualificationPostsBlogs.size(); i++) {
			
			try {
			
				MonitoringQualificationReport monitoringQualificationReport = new MonitoringQualificationReport();
				monitoringQualificationReport.setMonitoring(monitoring);
				monitoringQualificationReport.setNetworkType(Profile.BLOGS);
				monitoringQualificationReport.setDate(generateDay);
				monitoringQualificationReport.setQualification(((String) qualificationPostsBlogs.get(i)[0]).charAt(0));
				monitoringQualificationReport.setTerm((String) qualificationPostsBlogs.get(i)[1]);
				monitoringQualificationReport.setValue((Long) qualificationPostsBlogs.get(i)[2]);

				monitoringQualificationReportRemote.save(monitoringQualificationReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void saveCountOfGarbagePostsPerDay() {
		
		MonitoringReport monitoringReportFacebook = new MonitoringReport();
		monitoringReportFacebook.setMonitoring(monitoring);
		monitoringReportFacebook.setNetworkType(Profile.FACEBOOK);
		monitoringReportFacebook.setDate(generateDay);
		monitoringReportFacebook.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportFacebook.setValue(facePostMonitoringRemote.getCountOfGarbagePostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportFacebook.setGarbage('T');
		
		try {
		
			monitoringReportRemote.save(monitoringReportFacebook);
				
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportGooglePlus = new MonitoringReport();
		monitoringReportGooglePlus.setMonitoring(monitoring);
		monitoringReportGooglePlus.setNetworkType(Profile.GOOGLE);
		monitoringReportGooglePlus.setDate(generateDay);
		monitoringReportGooglePlus.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportGooglePlus.setValue(googlePlusPostMonitoringRemote.getCountOfGarbagePostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportGooglePlus.setGarbage('T');
		
		try {
			
			monitoringReportRemote.save(monitoringReportGooglePlus);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportYoutube = new MonitoringReport();
		monitoringReportYoutube.setMonitoring(monitoring);
		monitoringReportYoutube.setNetworkType(Profile.YOUTUBE);
		monitoringReportYoutube.setDate(generateDay);
		monitoringReportYoutube.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportYoutube.setValue(youtubePostMonitoringRemote.getCountOfGarbagePostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportYoutube.setGarbage('T');
		
		try {
			
			monitoringReportRemote.save(monitoringReportYoutube);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportInstagram = new MonitoringReport();
		monitoringReportInstagram.setMonitoring(monitoring);
		monitoringReportInstagram.setNetworkType(Profile.INSTAGRAM);
		monitoringReportInstagram.setDate(generateDay);
		monitoringReportInstagram.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportInstagram.setValue(instagramPostMonitoringRemote.getCountOfGarbagePostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportInstagram.setGarbage('T');
		
		try {
			
			monitoringReportRemote.save(monitoringReportInstagram);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportNews = new MonitoringReport();
		monitoringReportNews.setMonitoring(monitoring);
		monitoringReportNews.setNetworkType(Profile.NEWS);
		monitoringReportNews.setDate(generateDay);
		monitoringReportNews.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportNews.setValue(newsPostMonitoringRemote.getCountOfGarbagePostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportNews.setGarbage('T');
		
		try {
			
			monitoringReportRemote.save(monitoringReportNews);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportBlogs = new MonitoringReport();
		monitoringReportBlogs.setMonitoring(monitoring);
		monitoringReportBlogs.setNetworkType(Profile.BLOGS);
		monitoringReportBlogs.setDate(generateDay);
		monitoringReportBlogs.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportBlogs.setValue(blogPostMonitoringRemote.getCountOfGarbagePostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportBlogs.setGarbage('T');
		
		try {
			
			monitoringReportRemote.save(monitoringReportBlogs);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void saveCountOfPostsPerDayHour() {
		
		List<Object[]> postsPerDayHourFacebook = facePostMonitoringRemote.getCountOfPostsPerHourDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsPerDayHourFacebook.size(); i++) {
			
			MonitoringReport monitoringReport = new MonitoringReport();
			monitoringReport.setMonitoring(monitoring);
			monitoringReport.setNetworkType(Profile.FACEBOOK);
			monitoringReport.setDate(generateDay);
			monitoringReport.setPeriodType(MonitoringReportDTO.HOUR);
			monitoringReport.setPeriod(((Integer) postsPerDayHourFacebook.get(i)[0]).toString());
			monitoringReport.setValue((Long) postsPerDayHourFacebook.get(i)[1]);
			
			try {
				
				monitoringReportRemote.save(monitoringReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> postsPerDayHourTwitter = twitterPostMonitoringRemote.getCountOfPostsPerHourDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsPerDayHourTwitter.size(); i++) {
			
			MonitoringReport monitoringReport = new MonitoringReport();
			monitoringReport.setMonitoring(monitoring);
			monitoringReport.setNetworkType(Profile.TWITTER);
			monitoringReport.setDate(generateDay);
			monitoringReport.setPeriodType(MonitoringReportDTO.HOUR);
			monitoringReport.setPeriod(((Integer) postsPerDayHourTwitter.get(i)[0]).toString());
			monitoringReport.setValue((Long) postsPerDayHourTwitter.get(i)[1]);
			
			try {
				
				monitoringReportRemote.save(monitoringReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> postsPerDayHourGooglePlus = googlePlusPostMonitoringRemote.getCountOfPostsPerHourDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsPerDayHourGooglePlus.size(); i++) {
			
			MonitoringReport monitoringReport = new MonitoringReport();
			monitoringReport.setMonitoring(monitoring);
			monitoringReport.setNetworkType(Profile.GOOGLE);
			monitoringReport.setDate(generateDay);
			monitoringReport.setPeriodType(MonitoringReportDTO.HOUR);
			monitoringReport.setPeriod(((Integer) postsPerDayHourGooglePlus.get(i)[0]).toString());
			monitoringReport.setValue((Long) postsPerDayHourGooglePlus.get(i)[1]);
			
			try {
				
				monitoringReportRemote.save(monitoringReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> postsPerDayHourYoutube = youtubePostMonitoringRemote.getCountOfPostsPerHourDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsPerDayHourYoutube.size(); i++) {
			
			MonitoringReport monitoringReport = new MonitoringReport();
			monitoringReport.setMonitoring(monitoring);
			monitoringReport.setNetworkType(Profile.YOUTUBE);
			monitoringReport.setDate(generateDay);
			monitoringReport.setPeriodType(MonitoringReportDTO.HOUR);
			monitoringReport.setPeriod(((Integer) postsPerDayHourYoutube.get(i)[0]).toString());
			monitoringReport.setValue((Long) postsPerDayHourYoutube.get(i)[1]);
			
			try {
				
				monitoringReportRemote.save(monitoringReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		List<Object[]> postsPerDayHourInstagram = instagramPostMonitoringRemote.getCountOfPostsPerHourDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsPerDayHourInstagram.size(); i++) {
			
			MonitoringReport monitoringReport = new MonitoringReport();
			monitoringReport.setMonitoring(monitoring);
			monitoringReport.setNetworkType(Profile.INSTAGRAM);
			monitoringReport.setDate(generateDay);
			monitoringReport.setPeriodType(MonitoringReportDTO.HOUR);
			monitoringReport.setPeriod(((Integer) postsPerDayHourInstagram.get(i)[0]).toString());
			monitoringReport.setValue((Long) postsPerDayHourInstagram.get(i)[1]);
			
			try {
				
				monitoringReportRemote.save(monitoringReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	private void savePostsTermPerDay() {
		
		List<Object[]> postsTermFacebook = facePostMonitoringRemote.getCountOfTermPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsTermFacebook.size(); i++) {
			
			try {
				
				MonitoringTermReport monitoringTermReport = new MonitoringTermReport();
				monitoringTermReport.setMonitoring(monitoring);
				monitoringTermReport.setTerm((String) postsTermFacebook.get(i)[0]);
				monitoringTermReport.setNetworkType(Profile.FACEBOOK);
				monitoringTermReport.setDate(generateDay);
				monitoringTermReport.setValue((Long) postsTermFacebook.get(i)[1]);
				
				monitoringTermReportRemote.save(monitoringTermReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}	
		
		List<Object[]> postsTermTwitter = twitterPostMonitoringRemote.getCountOfTermPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsTermTwitter.size(); i++) {
			
			try {
				
				MonitoringTermReport monitoringTermReport = new MonitoringTermReport();
				monitoringTermReport.setMonitoring(monitoring);
				monitoringTermReport.setTerm((String) postsTermTwitter.get(i)[0]);
				monitoringTermReport.setNetworkType(Profile.TWITTER);
				monitoringTermReport.setDate(generateDay);
				monitoringTermReport.setValue((Long) postsTermTwitter.get(i)[1]);
				
				monitoringTermReportRemote.save(monitoringTermReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		List<Object[]> postsTermGooglePlus = googlePlusPostMonitoringRemote.getCountOfTermPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsTermGooglePlus.size(); i++) {
			
			try {
				
				MonitoringTermReport monitoringTermReport = new MonitoringTermReport();
				monitoringTermReport.setMonitoring(monitoring);
				monitoringTermReport.setTerm((String) postsTermGooglePlus.get(i)[0]);
				monitoringTermReport.setNetworkType(Profile.GOOGLE);
				monitoringTermReport.setDate(generateDay);
				monitoringTermReport.setValue((Long) postsTermGooglePlus.get(i)[1]);
				
				monitoringTermReportRemote.save(monitoringTermReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		List<Object[]> postsTermYoutube = youtubePostMonitoringRemote.getCountOfTermPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsTermYoutube.size(); i++) {
			
			try {
				
				MonitoringTermReport monitoringTermReport = new MonitoringTermReport();
				monitoringTermReport.setMonitoring(monitoring);
				monitoringTermReport.setTerm((String) postsTermYoutube.get(i)[0]);
				monitoringTermReport.setNetworkType(Profile.YOUTUBE);
				monitoringTermReport.setDate(generateDay);
				monitoringTermReport.setValue((Long) postsTermYoutube.get(i)[1]);
				
				monitoringTermReportRemote.save(monitoringTermReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		List<Object[]> postsTermInstagram = instagramPostMonitoringRemote.getCountOfTermPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsTermInstagram.size(); i++) {
			
			try {
				
				MonitoringTermReport monitoringTermReport = new MonitoringTermReport();
				monitoringTermReport.setMonitoring(monitoring);
				monitoringTermReport.setTerm((String) postsTermInstagram.get(i)[0]);
				monitoringTermReport.setNetworkType(Profile.INSTAGRAM);
				monitoringTermReport.setDate(generateDay);
				monitoringTermReport.setValue((Long) postsTermInstagram.get(i)[1]);
				
				monitoringTermReportRemote.save(monitoringTermReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		List<Object[]> postsTermReclameAqui = reclameAquiPostMonitoringRemote.getCountOfTermPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsTermReclameAqui.size(); i++) {
			
			try {
				
				MonitoringTermReport monitoringTermReport = new MonitoringTermReport();
				monitoringTermReport.setMonitoring(monitoring);
				monitoringTermReport.setTerm((String) postsTermReclameAqui.get(i)[0]);
				monitoringTermReport.setNetworkType(Profile.RECLAMEAQUI);
				monitoringTermReport.setDate(generateDay);
				monitoringTermReport.setValue((Long) postsTermReclameAqui.get(i)[1]);
				
				monitoringTermReportRemote.save(monitoringTermReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		List<Object[]> postsTermNews = newsPostMonitoringRemote.getCountOfTermPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsTermNews.size(); i++) {
			
			try {
				
				MonitoringTermReport monitoringTermReport = new MonitoringTermReport();
				monitoringTermReport.setMonitoring(monitoring);
				monitoringTermReport.setTerm((String) postsTermNews.get(i)[0]);
				monitoringTermReport.setNetworkType(Profile.NEWS);
				monitoringTermReport.setDate(generateDay);
				monitoringTermReport.setValue((Long) postsTermNews.get(i)[1]);
				
				monitoringTermReportRemote.save(monitoringTermReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
		List<Object[]> postsTermBlogs = blogPostMonitoringRemote.getCountOfTermPostsPerDay(monitoring.getIdMonitoring(), generateDay);
		for(int i = 0; i < postsTermBlogs.size(); i++) {
			
			try {
				
				MonitoringTermReport monitoringTermReport = new MonitoringTermReport();
				monitoringTermReport.setMonitoring(monitoring);
				monitoringTermReport.setTerm((String) postsTermBlogs.get(i)[0]);
				monitoringTermReport.setNetworkType(Profile.BLOGS);
				monitoringTermReport.setDate(generateDay);
				monitoringTermReport.setValue((Long) postsTermBlogs.get(i)[1]);
				
				monitoringTermReportRemote.save(monitoringTermReport);
				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
		
	}
	
	private void saveCountOfPostsPerDay() {
		
		MonitoringReport monitoringReportFacebook = new MonitoringReport();
		monitoringReportFacebook.setMonitoring(monitoring);
		monitoringReportFacebook.setNetworkType(Profile.FACEBOOK);
		monitoringReportFacebook.setDate(generateDay);
		monitoringReportFacebook.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportFacebook.setValue(facePostMonitoringRemote.getCountOfPostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportFacebook.setGarbage('F');
		
		try {
		
			monitoringReportRemote.save(monitoringReportFacebook);
				
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportTwitter = new MonitoringReport();
		monitoringReportTwitter.setMonitoring(monitoring);
		monitoringReportTwitter.setNetworkType(Profile.TWITTER);
		monitoringReportTwitter.setDate(generateDay);
		monitoringReportTwitter.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportTwitter.setValue(twitterPostMonitoringRemote.getCountOfPostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportTwitter.setGarbage('F');
		
		try {
			
			monitoringReportRemote.save(monitoringReportTwitter);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportGooglePlus = new MonitoringReport();
		monitoringReportGooglePlus.setMonitoring(monitoring);
		monitoringReportGooglePlus.setNetworkType(Profile.GOOGLE);
		monitoringReportGooglePlus.setDate(generateDay);
		monitoringReportGooglePlus.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportGooglePlus.setValue(googlePlusPostMonitoringRemote.getCountOfPostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportGooglePlus.setGarbage('F');
		
		try {
			
			monitoringReportRemote.save(monitoringReportGooglePlus);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportYoutube = new MonitoringReport();
		monitoringReportYoutube.setMonitoring(monitoring);
		monitoringReportYoutube.setNetworkType(Profile.YOUTUBE);
		monitoringReportYoutube.setDate(generateDay);
		monitoringReportYoutube.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportYoutube.setValue(youtubePostMonitoringRemote.getCountOfPostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportYoutube.setGarbage('F');
		
		try {
			
			monitoringReportRemote.save(monitoringReportYoutube);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportInstagram = new MonitoringReport();
		monitoringReportInstagram.setMonitoring(monitoring);
		monitoringReportInstagram.setNetworkType(Profile.INSTAGRAM);
		monitoringReportInstagram.setDate(generateDay);
		monitoringReportInstagram.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportInstagram.setValue(instagramPostMonitoringRemote.getCountOfPostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportInstagram.setGarbage('F');
		
		try {
			
			monitoringReportRemote.save(monitoringReportInstagram);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportReclameAqui = new MonitoringReport();
		monitoringReportReclameAqui.setMonitoring(monitoring);
		monitoringReportReclameAqui.setNetworkType(Profile.RECLAMEAQUI);
		monitoringReportReclameAqui.setDate(generateDay);
		monitoringReportReclameAqui.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportReclameAqui.setValue(reclameAquiPostMonitoringRemote.getCountOfPostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportReclameAqui.setGarbage('F');
		
		try {
			
			monitoringReportRemote.save(monitoringReportReclameAqui);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportNews = new MonitoringReport();
		monitoringReportNews.setMonitoring(monitoring);
		monitoringReportNews.setNetworkType(Profile.NEWS);
		monitoringReportNews.setDate(generateDay);
		monitoringReportNews.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportNews.setValue(newsPostMonitoringRemote.getCountOfPostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportNews.setGarbage('F');
		
		try {
			
			monitoringReportRemote.save(monitoringReportNews);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		MonitoringReport monitoringReportBlogs = new MonitoringReport();
		monitoringReportBlogs.setMonitoring(monitoring);
		monitoringReportBlogs.setNetworkType(Profile.BLOGS);
		monitoringReportBlogs.setDate(generateDay);
		monitoringReportBlogs.setPeriodType(MonitoringReportDTO.DAY);
		monitoringReportBlogs.setValue(blogPostMonitoringRemote.getCountOfPostsPerDay(monitoring.getIdMonitoring(), generateDay));
		monitoringReportBlogs.setGarbage('F');
		
		try {
			
			monitoringReportRemote.save(monitoringReportBlogs);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}