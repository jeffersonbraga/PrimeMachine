package br.com.opsocial.server.actions.monitorings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.client.entity.blogs.BlogPostMonitoringDTO;
import br.com.opsocial.client.entity.facebook.FacePostMonitoringDTO;
import br.com.opsocial.client.entity.googleplus.GooglePlusPostMonitoringDTO;
import br.com.opsocial.client.entity.instagram.InstagramPostMonitoringDTO;
import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.client.entity.monitoring.TwitterPostMonitoringDTO;
import br.com.opsocial.client.entity.news.NewsPostMonitoringDTO;
import br.com.opsocial.client.entity.reclameaqui.ReclameAquiPostMonitoringDTO;
import br.com.opsocial.client.entity.youtube.YoutubePostMonitoringDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.monitorings.json.MonitoringQualificationReportIdJSON;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceGooglePlusPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringInfluentialUserRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringLogReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringQualificationReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportControlRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceYoutubePostMonitoringRemote;
import br.com.opsocial.ejb.entity.blogs.BlogPostMonitoring;
import br.com.opsocial.ejb.entity.google.GooglePlusPostMonitoring;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringInfluentialUser;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLogReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringQualificationReport;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReportControl;
import br.com.opsocial.ejb.entity.news.NewsPostMonitoring;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPostMonitoring;
import br.com.opsocial.ejb.entity.twitter.TwitterPostMonitoring;
import br.com.opsocial.ejb.entity.youtube.YoutubePostMonitoring;

@RestController
@RequestMapping("woopsocial")
public class ChangeQualificationAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/change_qualification",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
	
		java.lang.Character type = ((Character) getParameters().get("network"));
		
		java.lang.Character oldQualification;
		 
		if(type.equals(Profile.TWITTER)) {
			
			MaintenanceTwitterPostMonitoringRemote twitterPostMonitoringRemote = (MaintenanceTwitterPostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");
			
			TwitterPostMonitoringDTO twitterPostMonitoringDTO = (TwitterPostMonitoringDTO) getParameters().get("post");
			
			TwitterPostMonitoring twitterPostMonitoring = twitterPostMonitoringRemote.getByComposedId(twitterPostMonitoringDTO.getMonitoring().getIdMonitoring(), 
					twitterPostMonitoringDTO.getTwitterPostDTO().getStatusId(), twitterPostMonitoringDTO.getTerm());

			oldQualification = twitterPostMonitoring.getQualification();

			twitterPostMonitoring.setQualification(twitterPostMonitoringDTO.getQualification());

			twitterPostMonitoringRemote.merge(twitterPostMonitoring);

			updateQualificationPostsUsers(twitterPostMonitoringDTO, twitterPostMonitoring.getTwitterPost().getUserId().toString(), oldQualification);
			updateMonitoringQualificationReport(twitterPostMonitoringDTO, oldQualification);

			if(twitterPostMonitoringDTO.getMonitoring().getApplyQualificationToSamePosts()) {

				List<TwitterPostMonitoring> samePosts = twitterPostMonitoringRemote.listByRetweedStatus(twitterPostMonitoring.getMonitoring().getIdMonitoring(), 
						twitterPostMonitoring.getTwitterPost().getStatusId(), twitterPostMonitoring.getTwitterPost().getRetweetedStatus());
				
				List<TwitterPostMonitoringDTO> samePostsDtos = new ArrayList<TwitterPostMonitoringDTO>();
				
				for(TwitterPostMonitoring postMonitoring : samePosts) {
					
					java.lang.Character sameOldQualification = postMonitoring.getQualification();
					
					postMonitoring.setQualification(twitterPostMonitoring.getQualification());
					postMonitoring = twitterPostMonitoringRemote.merge(postMonitoring);
					
					TwitterPostMonitoringDTO sameTwitterPostMonitoring = MountDTO.mountTwitterPostMonitoring(postMonitoring);
					
					updateQualificationPostsUsers(sameTwitterPostMonitoring, postMonitoring.getTwitterPost().getUserId().toString(), sameOldQualification);
					updateMonitoringQualificationReport(sameTwitterPostMonitoring, sameOldQualification);

					samePostsDtos.add(sameTwitterPostMonitoring);
				}

				getParameters().put("samePosts", samePostsDtos);
			}
		} else if(type.equals(Profile.GOOGLE)) {

			MaintenanceGooglePlusPostMonitoringRemote remote = (MaintenanceGooglePlusPostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("GooglePlusPostMonitoring");
			
			GooglePlusPostMonitoringDTO googlePlusPostMonitoringDTO = (GooglePlusPostMonitoringDTO) getParameters().get("post");
			
			GooglePlusPostMonitoring googlePlusPostMonitoring = remote.getByComposedId(googlePlusPostMonitoringDTO.getMonitoringDTO().getIdMonitoring(),
					googlePlusPostMonitoringDTO.getGooglePlusPostDTO().getIdActivity(), googlePlusPostMonitoringDTO.getTerm());
			
			oldQualification = googlePlusPostMonitoring.getQualification();
			
			googlePlusPostMonitoring.setQualification(googlePlusPostMonitoringDTO.getQualification());
			
			remote.merge(googlePlusPostMonitoring);
			
			updateMonitoringQualificationReport(googlePlusPostMonitoringDTO, oldQualification);
			
			if(googlePlusPostMonitoringDTO.getMonitoringDTO().getApplyQualificationToSamePosts()) {
			
				List<GooglePlusPostMonitoring> samePosts = remote.listSamePosts(googlePlusPostMonitoring.getMonitoring().getIdMonitoring(), 
						googlePlusPostMonitoring.getGooglePlusPost().getActivityId(), 
							googlePlusPostMonitoring.getGooglePlusPost().getObjectId());
				
				List<GooglePlusPostMonitoringDTO> samePostsDtos = new ArrayList<GooglePlusPostMonitoringDTO>();
				
				for(GooglePlusPostMonitoring postMonitoring : samePosts) {
					
					try {
						
						java.lang.Character sameOldQualification = postMonitoring.getQualification();
						
						postMonitoring.setQualification(googlePlusPostMonitoring.getQualification());
						postMonitoring = remote.merge(postMonitoring);
						
						GooglePlusPostMonitoringDTO sameGooglePlusPostMonitoring = MountDTO.mountGooglePlusPostMonitoring(postMonitoring); 
						
						updateMonitoringQualificationReport(sameGooglePlusPostMonitoring, sameOldQualification);
						
						samePostsDtos.add(sameGooglePlusPostMonitoring);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				getParameters().put("samePosts", samePostsDtos);
			}
		
		} else if(type.equals(Profile.YOUTUBE)) {
			
			MaintenanceYoutubePostMonitoringRemote remote = (MaintenanceYoutubePostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("YoutubePostMonitoring");
			
			YoutubePostMonitoringDTO youtubePostMonitoringDTO = (YoutubePostMonitoringDTO) getParameters().get("post");
			
			YoutubePostMonitoring youtubePostMonitoring = remote.getByComposedId(youtubePostMonitoringDTO.getMonitoring().getIdMonitoring(), 
					youtubePostMonitoringDTO.getYoutubePostDTO().getResourceId().getVideoId(), youtubePostMonitoringDTO.getTerm());
			
			oldQualification = youtubePostMonitoring.getQualification();
			
			youtubePostMonitoring.setQualification(youtubePostMonitoringDTO.getQualification());
			
			remote.merge(youtubePostMonitoring);
			
			updateMonitoringQualificationReport(youtubePostMonitoringDTO, oldQualification);
			
			if(youtubePostMonitoringDTO.getMonitoring().getApplyQualificationToSamePosts()) {
			
				List<YoutubePostMonitoring> samePosts = remote.listSamePosts(
						youtubePostMonitoring.getMonitoring().getIdMonitoring(), youtubePostMonitoring.getYoutubePost().getDescription());
				
				List<YoutubePostMonitoringDTO> samePostsDtos = new ArrayList<YoutubePostMonitoringDTO>();
				
				for(YoutubePostMonitoring postMonitoring : samePosts) {
					
					java.lang.Character sameOldQualification = postMonitoring.getQualification();
					
					postMonitoring.setQualification(youtubePostMonitoring.getQualification());
					postMonitoring = remote.merge(postMonitoring);
					
					YoutubePostMonitoringDTO sameYoutubePostMonitoring = MountDTO.mountYoutubePostMonitoring(postMonitoring); 
					
					updateMonitoringQualificationReport(sameYoutubePostMonitoring, sameOldQualification);
					
					samePostsDtos.add(sameYoutubePostMonitoring);
				}
				
				getParameters().put("samePosts", samePostsDtos);
			}
			
		} else if(type.equals(Profile.INSTAGRAM)) {
			
			MaintenanceInstagramPostMonitoringRemote remote = (MaintenanceInstagramPostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("InstagramPostMonitoring");
			
			InstagramPostMonitoringDTO instagramPostMonitoringDTO = (InstagramPostMonitoringDTO) getParameters().get("post");
			
			InstagramPostMonitoring instagramPostMonitoring = remote.getByComposedId(instagramPostMonitoringDTO.getMonitoring().getIdMonitoring(),
					instagramPostMonitoringDTO.getInstagramPostDTO().getIdMedia(), instagramPostMonitoringDTO.getTerm());
			
			oldQualification = instagramPostMonitoring.getQualification();
			
			instagramPostMonitoring.setQualification(instagramPostMonitoringDTO.getQualification());
			
			remote.merge(instagramPostMonitoring);
			
			updateMonitoringQualificationReport(instagramPostMonitoringDTO, oldQualification);
			
		} else if(type.equals(Profile.RECLAMEAQUI)) {
			
			MaintenanceReclameAquiPostMonitoringRemote remote = (MaintenanceReclameAquiPostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("ReclameAquiPostMonitoring");
			
			ReclameAquiPostMonitoringDTO reclameAquiPostMonitoringDTO = (ReclameAquiPostMonitoringDTO) getParameters().get("post");
			
			ReclameAquiPostMonitoring reclameAquiPostMonitoring = remote.getByComposedId(reclameAquiPostMonitoringDTO.getMonitoring().getIdMonitoring(),
					reclameAquiPostMonitoringDTO.getReclameAquiPostDTO().getCacheId(), reclameAquiPostMonitoringDTO.getTerm());
			
			oldQualification = reclameAquiPostMonitoring.getQualification();
			
			reclameAquiPostMonitoring.setQualification(reclameAquiPostMonitoringDTO.getQualification());
			
			remote.merge(reclameAquiPostMonitoring);
			
			updateMonitoringQualificationReport(reclameAquiPostMonitoringDTO, oldQualification);
			
		} else if(type.equals(Profile.NEWS)) {
			
			MaintenanceNewsPostMonitoringRemote remote = (MaintenanceNewsPostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("NewsPostMonitoring");
			
			NewsPostMonitoringDTO newsPostMonitoringDTO = (NewsPostMonitoringDTO) getParameters().get("post");
			
			NewsPostMonitoring newsPostMonitoring = remote.getByComposedId(newsPostMonitoringDTO.getMonitoring().getIdMonitoring(),
					newsPostMonitoringDTO.getNewsPostDTO().getIdNewsPost(), newsPostMonitoringDTO.getTerm());
			
			oldQualification = newsPostMonitoring.getQualification();
			
			newsPostMonitoring.setQualification(newsPostMonitoringDTO.getQualification());
			
			remote.merge(newsPostMonitoring);
			
			updateMonitoringQualificationReport(newsPostMonitoringDTO, oldQualification);
			
		} else if(type.equals(Profile.BLOGS)) {
			
			MaintenanceBlogPostMonitoringRemote remote = (MaintenanceBlogPostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("BlogPostMonitoring");
			
			BlogPostMonitoringDTO blogPostMonitoringDTO = (BlogPostMonitoringDTO) getParameters().get("post");
			
			BlogPostMonitoring blogPostMonitoring = remote.getByComposedId(blogPostMonitoringDTO.getMonitoringDTO().getIdMonitoring(),
					blogPostMonitoringDTO.getBlogPostDTO().getIdBlogPost(), blogPostMonitoringDTO.getTerm());
			
			oldQualification = blogPostMonitoring.getQualification();
			
			blogPostMonitoring.setQualification(blogPostMonitoringDTO.getQualification());
			
			remote.merge(blogPostMonitoring);
			
			updateMonitoringQualificationReport(blogPostMonitoringDTO, oldQualification);
		}
	}
	
	public void updateMonitoringQualificationReport(PostMonitoringDTO postMonitoring, java.lang.Character oldQualification) {
		
		Gson gson = new GsonBuilder().serializeNulls().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
		
		MaintenanceMonitoringRemote monitoringRemote = (MaintenanceMonitoringRemote)
				RecoverMaintenance.recoverMaintenance("Monitoring");
		
		MaintenanceMonitoringReportControlRemote monitoringReportControlRemote = 
				(MaintenanceMonitoringReportControlRemote) RecoverMaintenance.recoverMaintenance("MonitoringReportControl");
		
		MaintenanceMonitoringQualificationReportRemote monitoringQualificationReportRemote = 
				(MaintenanceMonitoringQualificationReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringQualificationReport");
		
		MaintenanceMonitoringLogReportRemote monitoringLogReportRemote = 
				(MaintenanceMonitoringLogReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringLogReport");
		
		Long generateDay = UtilFunctions.getMidnightDateNoDaylightTime(new Date(postMonitoring.getRetrievedDate() * 1000L));
		
		try {
			
			if(monitoringReportControlRemote.hasEntity(postMonitoring.getMonitoring().getIdMonitoring(), 
					MonitoringReportControl.QUALIFICATION, generateDay)) {
				
				// Aplica se uma nova qualificação foi aplicada ao Post.
				if(postMonitoring.getQualification() != null) {
					
					if(monitoringQualificationReportRemote.hasEntity(postMonitoring.getMonitoring().getIdMonitoring(), 
							postMonitoring.getTerm(), postMonitoring.getQualification(), postMonitoring.getNetwork(), generateDay)) {
						
						try {
						
							MonitoringQualificationReport monitoringQualificationReport = monitoringQualificationReportRemote.getEntity(postMonitoring.getMonitoring().getIdMonitoring(), 
									postMonitoring.getTerm(), postMonitoring.getQualification(), postMonitoring.getNetwork(), generateDay);
						
							MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
							monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
							monitoringLogReport.setReportType(MonitoringLogReport.MON_QUALIFICATION_REPORT);
							monitoringLogReport.setReportId(monitoringQualificationReport.getIdMonitoringQualificationReport().toString());
							monitoringLogReport.setValueToApply(1);
							monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
							monitoringLogReport.setLogDate(new Date().getTime() / 1000L);

							monitoringLogReportRemote.save(monitoringLogReport);
							
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					} else {
						
						try {
							
							MonitoringQualificationReportIdJSON monitoringQualificationReportIdJSON = new MonitoringQualificationReportIdJSON();
							monitoringQualificationReportIdJSON.setIdMonitoring(postMonitoring.getMonitoring().getIdMonitoring());
							monitoringQualificationReportIdJSON.setTerm(postMonitoring.getTerm());
							monitoringQualificationReportIdJSON.setQualification(postMonitoring.getQualification());
							monitoringQualificationReportIdJSON.setNetworkType(postMonitoring.getNetwork());
							monitoringQualificationReportIdJSON.setDate(generateDay);
							
							MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
							monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
							monitoringLogReport.setReportType(MonitoringLogReport.MON_QUALIFICATION_REPORT_NEW);
							monitoringLogReport.setReportId(gson.toJson(monitoringQualificationReportIdJSON));
							monitoringLogReport.setValueToApply(1);
							monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
							monitoringLogReport.setLogDate(new Date().getTime() / 1000L);

							monitoringLogReportRemote.save(monitoringLogReport);
							
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					}
				}
				
				// Aplica se o post contava com uma qualificação e agora não conta mais.
				if(oldQualification != null) {
					
					if(monitoringQualificationReportRemote.hasEntity(postMonitoring.getMonitoring().getIdMonitoring(), 
							postMonitoring.getTerm(), oldQualification, postMonitoring.getNetwork(), generateDay)) {
						
						try {

							MonitoringQualificationReport monitoringQualificationReport = monitoringQualificationReportRemote.getEntity(postMonitoring.getMonitoring().getIdMonitoring(), 
									postMonitoring.getTerm(), oldQualification, postMonitoring.getNetwork(), generateDay);

							MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
							monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
							monitoringLogReport.setReportType(MonitoringLogReport.MON_QUALIFICATION_REPORT);
							monitoringLogReport.setReportId(monitoringQualificationReport.getIdMonitoringQualificationReport().toString());
							monitoringLogReport.setValueToApply(-1);
							monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
							monitoringLogReport.setLogDate(new Date().getTime() / 1000L);

							monitoringLogReportRemote.save(monitoringLogReport);

						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						
						try {
							
							MonitoringQualificationReportIdJSON monitoringQualificationReportIdJSON = new MonitoringQualificationReportIdJSON();
							monitoringQualificationReportIdJSON.setIdMonitoring(postMonitoring.getMonitoring().getIdMonitoring());
							monitoringQualificationReportIdJSON.setTerm(postMonitoring.getTerm());
							monitoringQualificationReportIdJSON.setQualification(oldQualification);
							monitoringQualificationReportIdJSON.setNetworkType(postMonitoring.getNetwork());
							monitoringQualificationReportIdJSON.setDate(generateDay);
							
							MonitoringLogReport monitoringLogReport = new MonitoringLogReport();
							monitoringLogReport.setMonitoring(monitoringRemote.getById(postMonitoring.getMonitoring().getIdMonitoring()));
							monitoringLogReport.setReportType(MonitoringLogReport.MON_QUALIFICATION_REPORT_NEW);
							monitoringLogReport.setReportId(gson.toJson(monitoringQualificationReportIdJSON));
							monitoringLogReport.setValueToApply(-1);
							monitoringLogReport.setUpdateApplied(MonitoringLogReport.UPDATE_NOT_APPLIED);
							monitoringLogReport.setLogDate(new Date().getTime() / 1000L);

							monitoringLogReportRemote.save(monitoringLogReport);
							
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void updateQualificationPostsUsers(PostMonitoringDTO postMonitoring, String idUser, java.lang.Character oldQualification) {
		
		MaintenanceMonitoringInfluentialUserRemote monitoringInfluentialUserRemote = 
				(MaintenanceMonitoringInfluentialUserRemote) RecoverMaintenance.recoverMaintenance("MonitoringInfluentialUser");
		
		Long generateDay = UtilFunctions.getMidnightDateNoDaylightTime(new Date(postMonitoring.getRetrievedDate() * 1000L));
		
		MonitoringInfluentialUser monitoringInfluentialUser;
		
		try {
			
			if(monitoringInfluentialUserRemote.hasEntity(idUser, postMonitoring.getMonitoring().getIdMonitoring(), postMonitoring.getNetwork(), generateDay)) {
				
				monitoringInfluentialUser = monitoringInfluentialUserRemote.getEntity(idUser, postMonitoring.getMonitoring().getIdMonitoring(), postMonitoring.getNetwork(), generateDay);
				
				if(postMonitoring.getQualification() != null) {
					if(postMonitoring.getQualification().equals(FacePostMonitoringDTO.POSITIVE)) {
						monitoringInfluentialUser.setPositivePostsCount(monitoringInfluentialUser.getPositivePostsCount() + 1L);
					} else if(postMonitoring.getQualification().equals(FacePostMonitoringDTO.NEGATIVE)) {
						monitoringInfluentialUser.setNegativePostsCount(monitoringInfluentialUser.getNegativePostsCount() + 1L);
					}
				}
				
				if(oldQualification != null) {
					if(oldQualification.equals(FacePostMonitoringDTO.POSITIVE)) {
						monitoringInfluentialUser.setPositivePostsCount(monitoringInfluentialUser.getPositivePostsCount() - 1L);
					} else if(oldQualification.equals(FacePostMonitoringDTO.NEGATIVE)) {
						monitoringInfluentialUser.setNegativePostsCount(monitoringInfluentialUser.getNegativePostsCount() - 1L);
					}
				}
				
				try {
					
					monitoringInfluentialUserRemote.save(monitoringInfluentialUser);
					
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
