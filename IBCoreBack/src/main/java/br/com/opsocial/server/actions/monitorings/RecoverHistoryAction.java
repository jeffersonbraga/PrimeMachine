package br.com.opsocial.server.actions.monitorings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.client.entity.primitive.PersistentList;
import br.com.opsocial.client.entity.primitive.StringList;
import br.com.opsocial.client.entity.primitive.StringUtil;
////import br.com.opsocial.client.view.components.Checkbox;
////import br.com.opsocial.client.view.streaming.monitorings.HistoricalMonitoringPostsWindow;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceFacePostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceGooglePlusPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceYoutubePostMonitoringRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.blogs.BlogPostMonitoring;
import br.com.opsocial.ejb.entity.facebook.FacePostMonitoring;
import br.com.opsocial.ejb.entity.google.GooglePlusPostMonitoring;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;
import br.com.opsocial.ejb.entity.news.NewsPostMonitoring;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPostMonitoring;
import br.com.opsocial.ejb.entity.twitter.TwitterPostMonitoring;
import br.com.opsocial.ejb.entity.youtube.YoutubePostMonitoring;

@RestController
@RequestMapping("woopsocial")
public class RecoverHistoryAction extends ServerAction {
	
	private static Long count;
	
	@Override
	@RequestMapping(value = "/recover_history",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		Long idMonitoring = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("idmonitoring")).getValue();
		Long startDate = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("startDate")).getValue();
		Long endDate = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("endDate")).getValue();
		StringUtil qualification = (StringUtil) getParameters().get("qualification");
		StringUtil network = (StringUtil) getParameters().get("network");
		List<Long> monitoringTags = (List<Long>) getParameters().get("monitoringTags");
		PersistentList<StringUtil> monitoringTerms = (PersistentList<StringUtil>) getParameters().get("monitoringTerms");
		StringList monitoringWords = (StringList) getParameters().get("monitoringWords");
		Integer offset =  ((br.com.opsocial.client.entity.primitive.Integer) getParameters().get("offset")).getValue();
		Integer limit =  ((br.com.opsocial.client.entity.primitive.Integer) getParameters().get("limit")).getValue();
		Boolean isFirstTimeByTab = ((br.com.opsocial.client.entity.primitive.Boolean) getParameters().get("isFirstTimeByTab")).getValue();
		
		List<String> listMonitoringTerms = new ArrayList<String>();
		
		if(monitoringTerms != null) {
			if(!network.getValue().contains("INSTAGRAM")) {
				for (StringUtil term : monitoringTerms.getPersistences()) {
					listMonitoringTerms.add(term.getValue()); 
				}
			} else {
				for (StringUtil term : monitoringTerms.getPersistences()) {
					
					// Validacao feita pois posts mais antigos (do INSTAGRAM) foram salvos todos SEM '#' (hashtags), mesmo se houvesse, 
					// fazendo com que a verificacao posterior sempre resultasse em falso (canContinue).
					String termString = term.getValue();
					
					if(termString.startsWith("#")) {
						
						listMonitoringTerms.add(termString);
						termString = termString.substring(1);
						listMonitoringTerms.add(termString);
					} else {
						listMonitoringTerms.add(termString);
					}
				}
			}
		} else {
			listMonitoringTerms = null;
		}
		
//		MaintenanceFacePostMonitoringRemote faceRemote = (MaintenanceFacePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("FacePostMonitoring");
		MaintenanceTwitterPostMonitoringRemote twitterRemote = (MaintenanceTwitterPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");
		MaintenanceGooglePlusPostMonitoringRemote googleRemote = (MaintenanceGooglePlusPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("GooglePlusPostMonitoring");
		MaintenanceYoutubePostMonitoringRemote youtubeRemote = (MaintenanceYoutubePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("YoutubePostMonitoring");
		MaintenanceInstagramPostMonitoringRemote instagramRemote = (MaintenanceInstagramPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("InstagramPostMonitoring");
		MaintenanceReclameAquiPostMonitoringRemote reclameAquiRemote = (MaintenanceReclameAquiPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("ReclameAquiPostMonitoring");
		MaintenanceNewsPostMonitoringRemote newsRemote = (MaintenanceNewsPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("NewsPostMonitoring");
		MaintenanceBlogPostMonitoringRemote blogRemote = (MaintenanceBlogPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("BlogPostMonitoring");
		
		MaintenanceMonitoringPostTagRemote monitoringPostTagRemote = (MaintenanceMonitoringPostTagRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringPostTag");
		
		setParameters(new HashMap<String, Object>());

		List<PostMonitoringDTO> postTwitterMonitoringDTOs = new ArrayList<PostMonitoringDTO>();
		List<PostMonitoringDTO> postGooglePlusMonitoringDTOs = new ArrayList<PostMonitoringDTO>();
		List<PostMonitoringDTO> postYoutubeMonitoringDTOs = new ArrayList<PostMonitoringDTO>();
		List<PostMonitoringDTO> postInstagramMonitoringDTOs = new ArrayList<PostMonitoringDTO>();
		List<PostMonitoringDTO> postReclameAquiMonitoringDTOs = new ArrayList<PostMonitoringDTO>();
		List<PostMonitoringDTO> postNewsMonitoringDTOs = new ArrayList<PostMonitoringDTO>();
		List<PostMonitoringDTO> postBlogsMonitoringDTOs = new ArrayList<PostMonitoringDTO>();
		
		if(isFirstTimeByTab) {
			count = 0L;
			
			if(network.getValue().contains("TWITTER")) {
				
				count += twitterRemote.getByIntervalCount(idMonitoring, startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, 
						monitoringTags != null ? monitoringTags : null, 
						listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null);
			} else if(network.getValue().contains("GOOGLE")) {

				count += googleRemote.getByIntervalCount(idMonitoring, startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, 
						monitoringTags != null ? monitoringTags : null, 
						listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null);
			} else if(network.getValue().contains("YOUTUBE")) {

				count += youtubeRemote.getByIntervalCount(idMonitoring, startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, 
						monitoringTags != null ? monitoringTags : null, 
						listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null);
			} else if(network.getValue().contains("INSTAGRAM")) {

				count += instagramRemote.getByIntervalCount(idMonitoring, startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, 
						monitoringTags != null ? monitoringTags : null, 
						listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null);
			} else if(network.getValue().contains("RECLAME AQUI")) {

				count += reclameAquiRemote.getByIntervalCount(idMonitoring, startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, 
						monitoringTags != null ? monitoringTags : null, 
						listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null);
			} else if(network.getValue().contains("NEWS")) {

				count += newsRemote.getByIntervalCount(idMonitoring, startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, 
						monitoringTags != null ? monitoringTags : null, 
						listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null);
			} else if(network.getValue().contains("BLOGS")) {

				count += blogRemote.getByIntervalCount(idMonitoring, startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, 
						monitoringTags != null ? monitoringTags : null, 
						listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null);
			} else {
				count = 0L;
			}
		}
		
		getParameters().put("counts", new br.com.opsocial.client.entity.primitive.Long(count));
		
		if(network.getValue().contains("TWITTER")) {
			
			// Twitter
			
			List<TwitterPostMonitoring> twitterPosts = twitterRemote.getByInterval(idMonitoring, 
					startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, monitoringTags != null ? monitoringTags : null, listMonitoringTerms != null ? listMonitoringTerms : null, 
					monitoringWords != null ? monitoringWords.getValue() : null, offset, limit);
			
			for(TwitterPostMonitoring twitterPostMonitoring : twitterPosts) {
				
				PostMonitoringDTO postMonitoringDTO = MountDTO.mountTwitterPostMonitoring(twitterPostMonitoring);
				postMonitoringDTO.setNetwork(Profile.TWITTER);
				postMonitoringDTO.setCreatedTime(twitterPostMonitoring.getTwitterPost().getCreatedAt());
				postMonitoringDTO.setMonitoringPostTags(
						MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(twitterPostMonitoring.getTwitterPost().getStatusId().toString(), 
							idMonitoring, twitterPostMonitoring.getTerm(), Profile.TWITTER)));
				
				postTwitterMonitoringDTOs.add(postMonitoringDTO);
			}

			getParameters().put("page", new br.com.opsocial.client.entity.primitive.Integer(offset + 1));
			getParameters().put("twitterPosts", postTwitterMonitoringDTOs);
		}
		
		if(network.getValue().contains("GOOGLE")) {
		
			// Google+
			
			List<GooglePlusPostMonitoring> googlePosts = (List<GooglePlusPostMonitoring>) googleRemote.getByInterval(idMonitoring, 
					startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, monitoringTags != null ? monitoringTags : null, 
							listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null, offset, limit);
		
			for(GooglePlusPostMonitoring googlePlusPostMonitoring : googlePosts) {
				
				PostMonitoringDTO postMonitoringDTO = MountDTO.mountGooglePlusPostMonitoring(googlePlusPostMonitoring);
				postMonitoringDTO.setNetwork(Profile.GOOGLE);
				postMonitoringDTO.setCreatedTime(googlePlusPostMonitoring.getGooglePlusPost().getPublished());
				postMonitoringDTO.setMonitoringPostTags(
						MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(googlePlusPostMonitoring.getGooglePlusPost().getActivityId(), 
							idMonitoring, googlePlusPostMonitoring.getTerm(), Profile.GOOGLE)));
				
				postGooglePlusMonitoringDTOs.add(postMonitoringDTO);
			}

			getParameters().put("page", new br.com.opsocial.client.entity.primitive.Integer(offset + 1));
			getParameters().put("googlePlusPosts", postGooglePlusMonitoringDTOs);
		}
		
		if(network.getValue().contains("YOUTUBE")) {
		
			// Youtube
			
			List<YoutubePostMonitoring> youtubePosts = youtubeRemote.getByInterval(idMonitoring, 
					startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, monitoringTags != null ? monitoringTags : null,
							listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null, offset, limit);
		
			for(YoutubePostMonitoring youtubePostMonitoring : youtubePosts) {
				
				PostMonitoringDTO postMonitoringDTO = MountDTO.mountYoutubePostMonitoring(youtubePostMonitoring);
				postMonitoringDTO.setNetwork(Profile.YOUTUBE);
				postMonitoringDTO.setCreatedTime(youtubePostMonitoring.getYoutubePost().getPublishedAt());
				postMonitoringDTO.setMonitoringPostTags(
						MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(youtubePostMonitoring.getYoutubePost().getVideoId(), 
								idMonitoring, youtubePostMonitoring.getTerm(), Profile.YOUTUBE)));
				
				postYoutubeMonitoringDTOs.add(postMonitoringDTO);
			}

			getParameters().put("page", new br.com.opsocial.client.entity.primitive.Integer(offset + 1));
			getParameters().put("youtubePosts", postYoutubeMonitoringDTOs);
		}
		
		if(network.getValue().contains("INSTAGRAM")) {
			
			// Instagram
			
			List<InstagramPostMonitoring> instagramPosts = instagramRemote.getByInterval(idMonitoring, 
					startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, monitoringTags != null ? monitoringTags : null, 
							listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null, offset, limit);
		
			for(InstagramPostMonitoring instagramPostMonitoring : instagramPosts) {
				
				PostMonitoringDTO postMonitoringDTO = MountDTO.mountInstagramPostMonitoring(instagramPostMonitoring);
				postMonitoringDTO.setNetwork(Profile.INSTAGRAM);
				postMonitoringDTO.setCreatedTime(new Long(instagramPostMonitoring.getInstagramPost().getCreateTime()));
				postMonitoringDTO.setMonitoringPostTags(
						MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(instagramPostMonitoring.getInstagramPost().getIdMedia().toString(), 
								idMonitoring, instagramPostMonitoring.getTerm(), Profile.INSTAGRAM)));

				getParameters().put("page", new br.com.opsocial.client.entity.primitive.Integer(offset + 1));
				postInstagramMonitoringDTOs.add(postMonitoringDTO);
			}

			getParameters().put("page", new br.com.opsocial.client.entity.primitive.Integer(offset + 1));
			getParameters().put("instagramPosts", postInstagramMonitoringDTOs);
		}
		
		if(network.getValue().contains("RECLAME AQUI")) {
			
			// ReclameAqui
			
			List<ReclameAquiPostMonitoring> reclameAquiPosts = reclameAquiRemote.getByInterval(idMonitoring, 
					startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, monitoringTags != null ? monitoringTags : null, 
							listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null, offset, limit);
		
			for(ReclameAquiPostMonitoring reclameAquiPostMonitoring : reclameAquiPosts) {
				
				PostMonitoringDTO postMonitoringDTO = MountDTO.mountReclameAquiPostMonitoring(reclameAquiPostMonitoring);
				postMonitoringDTO.setNetwork(Profile.RECLAMEAQUI);
				postMonitoringDTO.setCreatedTime(new Long(reclameAquiPostMonitoring.getReclameAquiPost().getCreatedTime()));
				postMonitoringDTO.setMonitoringPostTags(
						MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(reclameAquiPostMonitoring.getReclameAquiPost().getCacheId(), 
								idMonitoring, reclameAquiPostMonitoring.getTerm(), Profile.RECLAMEAQUI)));
				
				postReclameAquiMonitoringDTOs.add(postMonitoringDTO);
			}

			getParameters().put("page", new br.com.opsocial.client.entity.primitive.Integer(offset + 1));
			getParameters().put("reclameAquiPosts", postReclameAquiMonitoringDTOs);
		}
		
		if(network.getValue().contains("NEWS")) {
			
			// News
			
			List<NewsPostMonitoring> newsPosts = newsRemote.getByInterval(idMonitoring, 
					startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, monitoringTags != null ? monitoringTags : null, 
							listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null, offset, limit);
		
			for(NewsPostMonitoring newsPostMonitoring : newsPosts) {
				
				PostMonitoringDTO postMonitoringDTO = MountDTO.mountNewsPostMonitoring(newsPostMonitoring);
				postMonitoringDTO.setNetwork(Profile.NEWS);
				postMonitoringDTO.setCreatedTime(new Long(newsPostMonitoring.getNewsPost().getCreatedTime()));
				postMonitoringDTO.setMonitoringPostTags(MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(newsPostMonitoring.getNewsPost().getIdNewsPost().toString(), 
						idMonitoring, newsPostMonitoring.getTerm(), Profile.NEWS)));
				
				postNewsMonitoringDTOs.add(postMonitoringDTO);
			}

			getParameters().put("page", new br.com.opsocial.client.entity.primitive.Integer(offset + 1));
			getParameters().put("newsPosts", postNewsMonitoringDTOs);
		}

		if(network.getValue().contains("BLOGS")) {
			
			// Blogs
			
			List<BlogPostMonitoring> blogPosts = blogRemote.getByInterval(idMonitoring, 
					startDate / 1000, endDate / 1000, qualification != null ? qualification.getValue() : null, monitoringTags != null ? monitoringTags : null, 
							listMonitoringTerms != null ? listMonitoringTerms : null, monitoringWords != null ? monitoringWords.getValue() : null, offset, limit);
		
			for(BlogPostMonitoring blogPostMonitoring : blogPosts) {
				
				PostMonitoringDTO postMonitoringDTO = MountDTO.mountBlogPostMonitoring(blogPostMonitoring);
				postMonitoringDTO.setNetwork(Profile.BLOGS);
				postMonitoringDTO.setCreatedTime(new Long(blogPostMonitoring.getBlogPost().getCreatedTime()));
				postMonitoringDTO.setMonitoringPostTags(MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(blogPostMonitoring.getBlogPost().getIdBlogPost().toString(), 
						idMonitoring, blogPostMonitoring.getTerm(), Profile.BLOGS)));
				
				postBlogsMonitoringDTOs.add(postMonitoringDTO);
			}

			getParameters().put("page", new br.com.opsocial.client.entity.primitive.Integer(offset + 1));
			getParameters().put("blogsPosts", postBlogsMonitoringDTOs);
		}
	}
}
