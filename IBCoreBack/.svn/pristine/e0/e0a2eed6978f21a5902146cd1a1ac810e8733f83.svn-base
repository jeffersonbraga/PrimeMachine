package br.com.opsocial.server.actions.monitorings;

import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.application.MonitoringDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.client.entity.blogs.BlogPostMonitoringDTO;
import br.com.opsocial.client.entity.facebook.FacePostMonitoringDTO;
import br.com.opsocial.client.entity.googleplus.GooglePlusPostMonitoringDTO;
import br.com.opsocial.client.entity.instagram.InstagramPostMonitoringDTO;
import br.com.opsocial.client.entity.monitoring.TwitterPostMonitoringDTO;
import br.com.opsocial.client.entity.news.NewsPostMonitoringDTO;
import br.com.opsocial.client.entity.primitive.Boolean;
import br.com.opsocial.client.entity.primitive.PersistentList;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.client.entity.reclameaqui.ReclameAquiPostMonitoringDTO;
import br.com.opsocial.client.entity.report.ReportLogoDTO;
import br.com.opsocial.client.entity.youtube.YoutubePostMonitoringDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.monitorings.MonitoringBlogsNewsPosts;
import br.com.opsocial.server.utils.tasks.MonitoringController;
import br.com.opsocial.server.utils.tasks.MonitoringController.TaskMonitoring;
import br.com.opsocial.server.utils.tasks.ReclameAquiPostController;
import br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceFacePostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceGooglePlusPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceReportLogoRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceYoutubePostMonitoringRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.ReportLogoId;
import br.com.opsocial.ejb.entity.blogs.BlogPostMonitoring;
import br.com.opsocial.ejb.entity.facebook.FacePostMonitoring;
import br.com.opsocial.ejb.entity.google.GooglePlusPostMonitoring;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReport;
import br.com.opsocial.ejb.entity.news.NewsPostMonitoring;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPostMonitoring;
import br.com.opsocial.ejb.entity.report.ReportLogo;
import br.com.opsocial.ejb.entity.twitter.TwitterPostMonitoring;
import br.com.opsocial.ejb.entity.youtube.YoutubePostMonitoring;

@RestController
@RequestMapping("woopsocial")
public class InsertMonitoringAction extends ServerAction {

	private MonitoringDTO monitoringDTO;

	@Override
	@RequestMapping(value = "/insert_monitoring",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception {

		String name = ((StringUtil) getParameters().get("name")).getValue();
		java.lang.Character type = ((Character) getParameters().get("type"));
		Boolean isEdition = (Boolean) getParameters().get("isEdition");

		final MaintenanceMonitoringRemote remote = (MaintenanceMonitoringRemote) RecoverMaintenance.recoverMaintenance("Monitoring");
		final MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");

		String monitoringTermsToCheck = "";
		
		if(getParameters().get("monitoring") != null) {
			monitoringDTO = (MonitoringDTO) getParameters().get("monitoring");
			monitoringTermsToCheck = remote.getById(monitoringDTO.getIdMonitoring()).getTerms();
		} else {

			String terms = ((StringUtil) getParameters().get("terms")).getValue();
			String ignoreTerms = ((StringUtil) getParameters().get("ignoreTerms")).getValue();
			String autoPositiveTerms = ((StringUtil) getParameters().get("autoPositiveTerms")).getValue();
			String autoNeuterTerms = ((StringUtil) getParameters().get("autoNeuterTerms")).getValue();
			String autoNegativeTerms = ((StringUtil) getParameters().get("autoNegativeTerms")).getValue();
			java.lang.Boolean applyQualificationsToSamePosts = ((Boolean) getParameters().get("applyQualificationsToSamePosts")).getValue();
			java.lang.Boolean applyTagsToSamePosts = ((Boolean) getParameters().get("applyTagsToSamePosts")).getValue();

			monitoringDTO = new MonitoringDTO();
			monitoringDTO.setName(name);

			monitoringDTO.setTerms(terms.replaceAll("\"", ""));
			monitoringDTO.setTerms(terms.replaceAll(",", ";"));
			monitoringDTO.setIgnoreTerms(ignoreTerms != null ? ignoreTerms.replaceAll("\"", "") : null);
			monitoringDTO.setIgnoreTerms(ignoreTerms != null ? ignoreTerms.replaceAll(",", ";") : null);
			monitoringDTO.setAutoPositiveTerms(autoPositiveTerms != null ? autoPositiveTerms.replaceAll("\"", "") : null);
			monitoringDTO.setAutoPositiveTerms(autoPositiveTerms != null ? autoPositiveTerms.replaceAll(",", ";") : null);
			monitoringDTO.setAutoNeuterTerms(autoNeuterTerms != null ? autoNeuterTerms.replaceAll("\"", "") : null);
			monitoringDTO.setAutoNeuterTerms(autoNeuterTerms != null ? autoNeuterTerms.replaceAll(",", ";") : null);
			monitoringDTO.setAutoNegativeTerms(autoNegativeTerms != null ? autoNegativeTerms.replaceAll("\"", "") : null);			
			monitoringDTO.setAutoNegativeTerms(autoNegativeTerms != null ? autoNegativeTerms.replaceAll(",", ";") : null);
			monitoringDTO.setCreatedAt(Calendar.getInstance().getTimeInMillis() / 1000L);
			monitoringDTO.setLanguage("pt_BR");
			monitoringDTO.setAccount(getUser().getAccount());
			monitoringDTO.setType(type);
			monitoringDTO.setApplyQualificationToSamePosts(applyQualificationsToSamePosts);
			monitoringDTO.setApplyTagsToSamePosts(applyTagsToSamePosts);

			// Setar ip do usuário nos monitoramentos para recuperação das notícias e blogs do google ***********
			monitoringDTO.setUserIp(getRequest().getRemoteAddr());		
		}

		CheckMonitoringTerms checkMonitoringTerms = new CheckMonitoringTerms(monitoringDTO, 
					monitoringTermsToCheck);

		if(remote.hasMonitoring(monitoringDTO.getAccount().getIdAccount(), 
				monitoringDTO.getName(), monitoringDTO.getType()) && !isEdition.getValue()) {
			getParameters().put("alreadyExists", new Boolean(true));
		} else {

			final Monitoring monitoring = remote.save(MountDTO.mountMonitoring(monitoringDTO));

			MonitoringController controller = (MonitoringController) getSession().getServletContext().getAttribute("monitoring_controller");

			if(isEdition.getValue()) {

				try {
					
					TaskMonitoring taskToCancel = controller.getMappedTasks().get(monitoringDTO.getIdMonitoring());
					
					controller.getMappedTasks().remove(monitoringDTO.getIdMonitoring());
					controller.getAlreadyMonitoring().remove(monitoringDTO.getIdMonitoring());
					taskToCancel.cancel();
					
				} catch (Exception e) {
					e.printStackTrace();
				}

				checkMonitoringTerms.start();
			}

			controller.startMonitoring(monitoring, 0);

			new Thread() { // Processo paralelo

				public void run() {
					ReclameAquiPostController.savePosts(monitoring);
					new MonitoringBlogsNewsPosts(monitoring).savePostsWebHoseAPI();
				}
			}.start();

			MaintenanceReportLogoRemote reportLogoRemote = (MaintenanceReportLogoRemote) 
					RecoverMaintenance.recoverMaintenance("ReportLogo");

			if(getParameters().get("reportLogo") != null) {

				ReportLogo reportLogo = MountDTO.mountReportLogo((ReportLogoDTO) getParameters().get("reportLogo"));
				reportLogo.setObjectId(monitoring.getIdMonitoring());
				reportLogo.setType(ReportLogo.MONITORING);

				reportLogo = reportLogoRemote.save(reportLogo);

				MonitoringDTO monitoringDTO = MountDTO.mountMonitoring(monitoring);
				monitoringDTO.setReportLogo(MountDTO.mountReportLogo(reportLogo));

			} else if(monitoringDTO.getReportLogo() != null) {

				ReportLogo reportLogo = MountDTO.mountReportLogo(monitoringDTO.getReportLogo());
				reportLogo.setObjectId(monitoring.getIdMonitoring());
				reportLogo.setType(ReportLogo.MONITORING);

				reportLogoRemote.save(reportLogo);

				ReportLogoId reportLogoId = new ReportLogoId();
				reportLogoId.setObjectId(reportLogo.getObjectId());
				reportLogoId.setType(reportLogo.getType());

				reportLogo = reportLogoRemote.getById(reportLogoId);

				MonitoringDTO monitoringDTO = MountDTO.mountMonitoring(monitoring);
				monitoringDTO.setReportLogo(MountDTO.mountReportLogo(reportLogo));
			}

			if(getParameters().containsKey("groups")) {

				List<Profile> groups = ((PersistentList<Profile>) getParameters().get("groups")).getPersistences();

				for(Profile group : groups) {
					group.setIdMonitoringForGroups(monitoring.getIdMonitoring());
					profileRemote.save(MountDTO.mountProfile(group));
				}
			}

			getParameters().put("monitoring", monitoringDTO);
			getParameters().put("alreadyExists", new Boolean(false));
		}
	}

	/**
	 * 
	 * Verifica se o algum termo foi excluído da lista de termos de um monitoramento e toma as devidas medidas 
	 * (exclui posts recuperados por aquele termo, atualiza dados de relatório relacionados ao termo, etc.)
	 * 
	 * @author rafael
	 *
	 */
	class CheckMonitoringTerms extends Thread {

		MonitoringDTO monitoringDTO;
		String monitoringTermsToCheck;

		public CheckMonitoringTerms(MonitoringDTO monitoringDTO, String monitoringTermsToCheck) {
			this.monitoringDTO = monitoringDTO;
			this.monitoringTermsToCheck = monitoringTermsToCheck;
		}

		@Override
		public void run() {

			HashSet<String> viewTerms = new HashSet<String>(); 

			String[] arrayViewTerms = monitoringDTO.getTerms().split(";");

			for(String term : arrayViewTerms) {
				viewTerms.add(term.trim());
			}

			for(String baseTerm : monitoringTermsToCheck.split(";")) {
				if(!viewTerms.contains(baseTerm.trim())) {
					deletePosts(monitoringDTO.getIdMonitoring(), baseTerm);		
				}
			}

		}

		private void deletePosts(Long idMonitoring, String term) {

			MaintenanceMonitoringPostTagRemote monitoringPostTagRemote = (MaintenanceMonitoringPostTagRemote)
					RecoverMaintenance.recoverMaintenance("MonitoringPostTag");

			// Exclusão de postsMonitorings

			MaintenanceFacePostMonitoringRemote facePostMonitoringRemote = (MaintenanceFacePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("FacePostMonitoring");

			List<FacePostMonitoring> facePostMonitorings = facePostMonitoringRemote.listByTerm(term, idMonitoring);

			for(FacePostMonitoring facePostMonitoring : facePostMonitorings) {

				try {

					facePostMonitoringRemote.delete(facePostMonitoring, false);

					FacePostMonitoringDTO facePostMonitoringDTO = MountDTO.mountFacePostMonitoring(facePostMonitoring);
					facePostMonitoringDTO.setMonitoringPostTags(
							MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(facePostMonitoring.getFacebookPost().getPostId(), 
									monitoringDTO.getIdMonitoring(), facePostMonitoring.getTerm(), Profile.FACEBOOK)));

					UtilFunctions.updateMonitoringReports(facePostMonitoringDTO, facePostMonitoringDTO.getGarbage());

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}	

			}

			MaintenanceTwitterPostMonitoringRemote twitterPostMonitoringRemote = (MaintenanceTwitterPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");

			List<TwitterPostMonitoring> twitterPostMonitorings = twitterPostMonitoringRemote.listByTerm(term, idMonitoring);

			for(TwitterPostMonitoring twitterPostMonitoring : twitterPostMonitorings) {

				try {

					twitterPostMonitoringRemote.delete(twitterPostMonitoring, false);

					TwitterPostMonitoringDTO twitterPostMonitoringDTO = MountDTO.mountTwitterPostMonitoring(twitterPostMonitoring); 
					twitterPostMonitoringDTO.setMonitoringPostTags(
							MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(twitterPostMonitoring.getTwitterPost().getStatusId().toString(), 
									idMonitoring, twitterPostMonitoring.getTerm(), Profile.TWITTER)));

					UtilFunctions.updateMonitoringReports(twitterPostMonitoringDTO, MonitoringReport.NOT_GARBAGE);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			MaintenanceGooglePlusPostMonitoringRemote googlePlusPostMonitoringRemote = (MaintenanceGooglePlusPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("GooglePlusPostMonitoring");

			List<GooglePlusPostMonitoring> googlePlusPostMonitorings = googlePlusPostMonitoringRemote.listByTerm(term, idMonitoring);

			for(GooglePlusPostMonitoring googlePlusPostMonitoring : googlePlusPostMonitorings) {

				try {

					googlePlusPostMonitoringRemote.delete(googlePlusPostMonitoring, false);

					GooglePlusPostMonitoringDTO googlePlusPostMonitoringDTO = MountDTO.mountGooglePlusPostMonitoring(googlePlusPostMonitoring); 
					googlePlusPostMonitoringDTO.setMonitoringPostTags(
							MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(googlePlusPostMonitoring.getGooglePlusPost().getActivityId(), 
									idMonitoring, googlePlusPostMonitoring.getTerm(), Profile.GOOGLE)));

					UtilFunctions.updateMonitoringReports(googlePlusPostMonitoringDTO, googlePlusPostMonitoringDTO.getGarbage());

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}

			MaintenanceYoutubePostMonitoringRemote youtubePostMonitoringRemote = (MaintenanceYoutubePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("YoutubePostMonitoring");

			List<YoutubePostMonitoring> youtubePostMonitorings = youtubePostMonitoringRemote.listByTerm(term, idMonitoring);

			for(YoutubePostMonitoring youtubePostMonitoring : youtubePostMonitorings) {

				try {

					youtubePostMonitoringRemote.delete(youtubePostMonitoring, false);

					YoutubePostMonitoringDTO youtubePostMonitoringDTO = MountDTO.mountYoutubePostMonitoring(youtubePostMonitoring); 
					youtubePostMonitoringDTO.setMonitoringPostTags(
							MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(youtubePostMonitoring.getYoutubePost().getVideoId(), 
									idMonitoring, youtubePostMonitoring.getTerm(), Profile.YOUTUBE)));

					UtilFunctions.updateMonitoringReports(youtubePostMonitoringDTO, youtubePostMonitoringDTO.getGarbage());

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			MaintenanceInstagramPostMonitoringRemote instagramPostMonitoringRemote = (MaintenanceInstagramPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("InstagramPostMonitoring");

			List<InstagramPostMonitoring> instagramPostMonitorings = instagramPostMonitoringRemote.listByTerm(term, idMonitoring);

			for(InstagramPostMonitoring instagramPostMonitoring : instagramPostMonitorings) {

				try {

					instagramPostMonitoringRemote.delete(instagramPostMonitoring, false);

					InstagramPostMonitoringDTO instagramPostMonitoringDTO = MountDTO.mountInstagramPostMonitoring(instagramPostMonitoring);
					instagramPostMonitoringDTO.setMonitoringPostTags(
							MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(instagramPostMonitoring.getInstagramPost().getIdMedia().toString(), 
									idMonitoring, instagramPostMonitoring.getTerm(), Profile.INSTAGRAM)));

					UtilFunctions.updateMonitoringReports(instagramPostMonitoringDTO, instagramPostMonitoringDTO.getGarbage());

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			MaintenanceReclameAquiPostMonitoringRemote reclameAquiPostMonitoringRemote = (MaintenanceReclameAquiPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("ReclameAquiPostMonitoring");

			List<ReclameAquiPostMonitoring> reclameAquiPostMonitorings = reclameAquiPostMonitoringRemote.listByTerm(term, idMonitoring);

			for(ReclameAquiPostMonitoring reclameAquiPostMonitoring : reclameAquiPostMonitorings) {

				try {

					reclameAquiPostMonitoringRemote.delete(reclameAquiPostMonitoring, false);

					ReclameAquiPostMonitoringDTO reclameAquiPostMonitoringDTO = MountDTO.mountReclameAquiPostMonitoring(reclameAquiPostMonitoring); 
					reclameAquiPostMonitoringDTO.setMonitoringPostTags(
							MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(reclameAquiPostMonitoring.getReclameAquiPost().getCacheId(), 
									monitoringDTO.getIdMonitoring(), reclameAquiPostMonitoring.getTerm(), Profile.RECLAMEAQUI)));

					UtilFunctions.updateMonitoringReports(reclameAquiPostMonitoringDTO, MonitoringReport.NOT_GARBAGE);

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			MaintenanceNewsPostMonitoringRemote newsPostMonitoringRemote = (MaintenanceNewsPostMonitoringRemote)
					RecoverMaintenance.recoverMaintenance("NewsPostMonitoring");

			List<NewsPostMonitoring> newsPostMonitorings = newsPostMonitoringRemote.listByTerm(term, idMonitoring);

			for(NewsPostMonitoring newsPostMonitoring : newsPostMonitorings) {

				try {

					newsPostMonitoringRemote.delete(newsPostMonitoring, false);

					NewsPostMonitoringDTO newsPostMonitoringDTO = MountDTO.mountNewsPostMonitoring(newsPostMonitoring);
					newsPostMonitoringDTO.setMonitoringPostTags(MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(newsPostMonitoring.getNewsPost().getIdNewsPost().toString(), 
							idMonitoring, newsPostMonitoring.getTerm(), Profile.NEWS)));

					UtilFunctions.updateMonitoringReports(newsPostMonitoringDTO, newsPostMonitoringDTO.getGarbage());

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			MaintenanceBlogPostMonitoringRemote blogPostMonitoringRemote = (MaintenanceBlogPostMonitoringRemote)
					RecoverMaintenance.recoverMaintenance("BlogPostMonitoring");

			List<BlogPostMonitoring> blogPostMonitorings = blogPostMonitoringRemote.listByTerm(term, idMonitoring);

			for(BlogPostMonitoring blogPostMonitoring : blogPostMonitorings) {

				try {

					blogPostMonitoringRemote.delete(blogPostMonitoring, false);

					BlogPostMonitoringDTO blogPostMonitoringDTO = MountDTO.mountBlogPostMonitoring(blogPostMonitoring);
					blogPostMonitoringDTO.setMonitoringPostTags(MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(blogPostMonitoring.getBlogPost().getIdBlogPost().toString(), 
							idMonitoring, blogPostMonitoring.getTerm(), Profile.BLOGS)));

					UtilFunctions.updateMonitoringReports(blogPostMonitoringDTO, blogPostMonitoringDTO.getGarbage());

				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

	}
}

