package br.com.opsocial.server.actions.monitorings;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.blogs.BlogPostMonitoringDTO;
import br.com.opsocial.client.entity.facebook.FacePostMonitoringDTO;
import br.com.opsocial.client.entity.googleplus.GooglePlusPostMonitoringDTO;
import br.com.opsocial.client.entity.instagram.InstagramPostMonitoringDTO;
import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.client.entity.monitoring.TwitterPostMonitoringDTO;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.client.entity.news.NewsPostMonitoringDTO;
import br.com.opsocial.client.entity.reclameaqui.ReclameAquiPostMonitoringDTO;
import br.com.opsocial.client.entity.youtube.YoutubePostMonitoringDTO;
import br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceExceptionRemote;
import br.com.opsocial.ejb.das.MaintenanceFacePostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceGooglePlusPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceYoutubePostMonitoringRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.idclass.BlogPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.GooglePlusPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.InstagramPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.NewsPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.ReclameAquiPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.TwitterPostMonitoringId;
import br.com.opsocial.ejb.entity.application.idclass.YoutubePostMonitoringId;
import br.com.opsocial.ejb.entity.blogs.BlogPostMonitoring;
import br.com.opsocial.ejb.entity.facebook.FacePostMonitoring;
import br.com.opsocial.ejb.entity.google.GooglePlusPostMonitoring;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReport;
import br.com.opsocial.ejb.entity.news.NewsPostMonitoring;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPostMonitoring;
import br.com.opsocial.ejb.entity.twitter.TwitterPostMonitoring;
import br.com.opsocial.ejb.entity.youtube.YoutubePostMonitoring;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;

@RestController
@RequestMapping("woopsocial")
public class DeletePostAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/delete_post",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		MaintenanceMonitoringPostTagRemote monitoringPostTagRemote = (MaintenanceMonitoringPostTagRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringPostTag");
	
		PostMonitoringDTO post = (PostMonitoringDTO) getParameters().get("post");
		
		if(post instanceof FacePostMonitoringDTO) {
			
			MaintenanceFacePostMonitoringRemote facePostMonitoringRemote = (MaintenanceFacePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("FacePostMonitoring");
			
			FacePostMonitoring facePostMonitoring = facePostMonitoringRemote.getByComposedId(post.getMonitoring().getIdMonitoring(), 
					((FacePostMonitoringDTO) post).getFacebookPostDTO().getPostId(), post.getTerm());
			
			facePostMonitoringRemote.delete(facePostMonitoring);
			
			UtilFunctions.updateMonitoringReports(post, MonitoringReport.NOT_GARBAGE);
			
		} else if(post instanceof TwitterPostMonitoringDTO) {
			
			MaintenanceTwitterPostMonitoringRemote twitterPostMonitoringRemote = (MaintenanceTwitterPostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");
			
			TwitterPostMonitoringDTO twitterPostMonitoringDTO = (TwitterPostMonitoringDTO) post;
			
			TwitterPostMonitoringId twitterPostMonitoringId = new TwitterPostMonitoringId();
			twitterPostMonitoringId.setTwitterPost(twitterPostMonitoringDTO.getTwitterPostDTO().getStatusId());
			twitterPostMonitoringId.setMonitoring(twitterPostMonitoringDTO.getMonitoring().getIdMonitoring());
			twitterPostMonitoringId.setTerm(twitterPostMonitoringDTO.getTerm());
			
			TwitterPostMonitoring twitterPostMonitoring = twitterPostMonitoringRemote.getById(twitterPostMonitoringId);
			
			twitterPostMonitoringRemote.delete(twitterPostMonitoring);
			
			twitterPostMonitoringDTO = MountDTO.mountTwitterPostMonitoring(twitterPostMonitoring);
			twitterPostMonitoringDTO.setMonitoringPostTags(
					MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(twitterPostMonitoring.getTwitterPost().getStatusId().toString(), 
							twitterPostMonitoring.getMonitoring().getIdMonitoring(), twitterPostMonitoring.getTerm(), Profile.TWITTER)));
			
			UtilFunctions.updateMonitoringReports(twitterPostMonitoringDTO, MonitoringReport.NOT_GARBAGE);
			
		} else if(post instanceof GooglePlusPostMonitoringDTO) {
			
			MaintenanceGooglePlusPostMonitoringRemote googlePlusMonitoringRemote = (MaintenanceGooglePlusPostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("GooglePlusPostMonitoring");
			
			GooglePlusPostMonitoringDTO googlePlusPostMonitoringDTO = (GooglePlusPostMonitoringDTO) post;
			
			GooglePlusPostMonitoringId googlePlusPostMonitoringId = new GooglePlusPostMonitoringId();
			googlePlusPostMonitoringId.setGooglePlusPost(googlePlusPostMonitoringDTO.getGooglePlusPostDTO().getIdActivity());
			googlePlusPostMonitoringId.setMonitoring(googlePlusPostMonitoringDTO.getMonitoringDTO().getIdMonitoring());
			googlePlusPostMonitoringId.setTerm(googlePlusPostMonitoringDTO.getTerm());
			
			GooglePlusPostMonitoring googlePlusPostMonitoring = googlePlusMonitoringRemote.getById(googlePlusPostMonitoringId); 
			googlePlusPostMonitoring.setGarbage('T');
			googlePlusMonitoringRemote.merge(googlePlusPostMonitoring);
			
			googlePlusPostMonitoringDTO = MountDTO.mountGooglePlusPostMonitoring(googlePlusPostMonitoring); 
			googlePlusPostMonitoringDTO.setMonitoringPostTags(
					MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(googlePlusPostMonitoring.getGooglePlusPost().getActivityId(), 
						googlePlusPostMonitoring.getMonitoring().getIdMonitoring(), googlePlusPostMonitoring.getTerm(), Profile.GOOGLE)));
			
			UtilFunctions.updateMonitoringReports(googlePlusPostMonitoringDTO, MonitoringReport.NOT_GARBAGE);
			
		} else if(post instanceof YoutubePostMonitoringDTO) {
			
			MaintenanceYoutubePostMonitoringRemote youtubePostMonitoringRemote = (MaintenanceYoutubePostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("YoutubePostMonitoring");
			
			YoutubePostMonitoringDTO youtubePostMonitoringDTO = (YoutubePostMonitoringDTO) post;
			
			YoutubePostMonitoringId youtubePostMonitoringId = new YoutubePostMonitoringId();
			youtubePostMonitoringId.setYoutubePost(youtubePostMonitoringDTO.getYoutubePostDTO().getResourceId().getVideoId());
			youtubePostMonitoringId.setMonitoring(youtubePostMonitoringDTO.getMonitoring().getIdMonitoring());
			youtubePostMonitoringId.setTerm(youtubePostMonitoringDTO.getTerm());
			
			YoutubePostMonitoring youtubePostMonitoring = youtubePostMonitoringRemote.getById(youtubePostMonitoringId);
			youtubePostMonitoring.setGarbage('T');
			youtubePostMonitoringRemote.merge(youtubePostMonitoring);
			
			youtubePostMonitoringDTO = MountDTO.mountYoutubePostMonitoring(youtubePostMonitoring);
			youtubePostMonitoringDTO.setMonitoringPostTags(
					MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(youtubePostMonitoring.getYoutubePost().getVideoId(), 
						youtubePostMonitoring.getMonitoring().getIdMonitoring(), youtubePostMonitoring.getTerm(), Profile.YOUTUBE)));
			
			UtilFunctions.updateMonitoringReports(youtubePostMonitoringDTO, MonitoringReport.NOT_GARBAGE);
			
		} else if(post instanceof InstagramPostMonitoringDTO) {
			
			InstagramPostMonitoringDTO instagramPostMonitoringDTO = (InstagramPostMonitoringDTO) post;
			
			try {
				
				MaintenanceInstagramPostMonitoringRemote instagramPostMonitoringRemote = (MaintenanceInstagramPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("InstagramPostMonitoring");
				
				InstagramPostMonitoringId instagramPostMonitoringId = new InstagramPostMonitoringId();
				instagramPostMonitoringId.setInstagramPost(instagramPostMonitoringDTO.getInstagramPostDTO().getIdMedia());
				instagramPostMonitoringId.setMonitoring(instagramPostMonitoringDTO.getMonitoring().getIdMonitoring());
				instagramPostMonitoringId.setTerm(instagramPostMonitoringDTO.getTerm());
				
				InstagramPostMonitoring instagramPostMonitoring = instagramPostMonitoringRemote.getById(instagramPostMonitoringId);
				instagramPostMonitoring.setGarbage('T');
				instagramPostMonitoringRemote.merge(instagramPostMonitoring);
				
				instagramPostMonitoringDTO = MountDTO.mountInstagramPostMonitoring(instagramPostMonitoring);
				instagramPostMonitoringDTO.setMonitoringPostTags(
						MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(instagramPostMonitoring.getInstagramPost().getIdMedia().toString(), 
							instagramPostMonitoring.getMonitoring().getIdMonitoring(), instagramPostMonitoring.getTerm(), Profile.INSTAGRAM)));
				
				UtilFunctions.updateMonitoringReports(instagramPostMonitoringDTO, MonitoringReport.NOT_GARBAGE);
				
			} catch (Exception e) {
				
				StringWriter printStackTrace = new StringWriter();
				
				e.printStackTrace(new PrintWriter(printStackTrace));
				//----- System.out.println("Monitoring..: " + instagramPostMonitoringDTO.getMonitoring().getIdMonitoring());
				//----- System.out.println("Post ID..: " + instagramPostMonitoringDTO.getInstagramPostDTO().getIdMedia());
				//----- System.out.println("Post Term..: " + instagramPostMonitoringDTO.getTerm());
				
				MaintenanceExceptionRemote exceptionRemote = (MaintenanceExceptionRemote)
						RecoverMaintenance.recoverMaintenance("Exception");
				
				br.com.opsocial.ejb.entity.application.Exception exception = new br.com.opsocial.ejb.entity.application.Exception();
				exception.setObjectId(instagramPostMonitoringDTO.getTerm());
				exception.setDate(new Date());
				exception.setException(printStackTrace.toString());
				exception.setCode(br.com.opsocial.ejb.entity.application.Exception.INSTA_DELETE_MONITORING_POSTS_ERROR);
				exception.setMessage("Monitoring..: " + instagramPostMonitoringDTO.getMonitoring().getIdMonitoring() +
						"Post ID..: " + instagramPostMonitoringDTO.getInstagramPostDTO().getIdMedia() + 
						"Post Term..: " + instagramPostMonitoringDTO.getTerm());
				
				try {
					
					exceptionRemote.save(exception);
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		} else if(post instanceof ReclameAquiPostMonitoringDTO) {
			
			MaintenanceReclameAquiPostMonitoringRemote reclameAquiPostMonitoringRemote = (MaintenanceReclameAquiPostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("ReclameAquiPostMonitoring");
			
			ReclameAquiPostMonitoringDTO reclameAquiPostMonitoringDTO = (ReclameAquiPostMonitoringDTO) post;
			
			ReclameAquiPostMonitoringId reclameAquiPostMonitoringId = new ReclameAquiPostMonitoringId();
			reclameAquiPostMonitoringId.setReclameAquiPost(reclameAquiPostMonitoringDTO.getReclameAquiPostDTO().getCacheId());
			reclameAquiPostMonitoringId.setMonitoring(reclameAquiPostMonitoringDTO.getMonitoring().getIdMonitoring());
			reclameAquiPostMonitoringId.setTerm(reclameAquiPostMonitoringDTO.getTerm());
			
			ReclameAquiPostMonitoring reclameAquiPostMonitoring = reclameAquiPostMonitoringRemote.getById(reclameAquiPostMonitoringId);
			reclameAquiPostMonitoring.setGarbage('T');
			reclameAquiPostMonitoringRemote.merge(reclameAquiPostMonitoring);
			
			reclameAquiPostMonitoringDTO = MountDTO.mountReclameAquiPostMonitoring(reclameAquiPostMonitoring);
			reclameAquiPostMonitoringDTO.setMonitoringPostTags(
					MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(reclameAquiPostMonitoring.getReclameAquiPost().getCacheId(), 
						reclameAquiPostMonitoring.getMonitoring().getIdMonitoring(), reclameAquiPostMonitoring.getTerm(), Profile.RECLAMEAQUI)));
			
			UtilFunctions.updateMonitoringReports(reclameAquiPostMonitoringDTO, MonitoringReport.NOT_GARBAGE);
			
		} else if(post instanceof NewsPostMonitoringDTO) {
			
			MaintenanceNewsPostMonitoringRemote newsPostMonitoringRemote = (MaintenanceNewsPostMonitoringRemote) 
					RecoverMaintenance.recoverMaintenance("NewsPostMonitoring");
			
			NewsPostMonitoringDTO newsPostMonitoringDTO = (NewsPostMonitoringDTO) post;
			
			NewsPostMonitoringId newsPostMonitoringId = new NewsPostMonitoringId();
			newsPostMonitoringId.setNewsPost(newsPostMonitoringDTO.getNewsPostDTO().getIdNewsPost());
			newsPostMonitoringId.setMonitoring(newsPostMonitoringDTO.getMonitoring().getIdMonitoring());
			newsPostMonitoringId.setTerm(newsPostMonitoringDTO.getTerm());
			
			NewsPostMonitoring newsPostMonitoring = newsPostMonitoringRemote.getById(newsPostMonitoringId);
			newsPostMonitoring.setGarbage('T');
			newsPostMonitoringRemote.merge(newsPostMonitoring);
			
			newsPostMonitoringDTO = MountDTO.mountNewsPostMonitoring(newsPostMonitoring);
			newsPostMonitoringDTO.setMonitoringPostTags(MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(newsPostMonitoring.getNewsPost().getIdNewsPost().toString(), 
					newsPostMonitoring.getMonitoring().getIdMonitoring(), newsPostMonitoring.getTerm(), Profile.NEWS)));
			
			UtilFunctions.updateMonitoringReports(newsPostMonitoringDTO, MonitoringReport.NOT_GARBAGE);
			
		} else if(post instanceof BlogPostMonitoringDTO) {
			
			MaintenanceBlogPostMonitoringRemote blogPostMonitoringRemote = (MaintenanceBlogPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("BlogPostMonitoring");
			
			BlogPostMonitoringDTO blogPostMonitoringDTO = (BlogPostMonitoringDTO) post;
			
			BlogPostMonitoringId blogPostMonitoringId = new BlogPostMonitoringId();
			blogPostMonitoringId.setBlogPost(blogPostMonitoringDTO.getBlogPostDTO().getIdBlogPost());
			blogPostMonitoringId.setMonitoring(blogPostMonitoringDTO.getMonitoringDTO().getIdMonitoring());
			blogPostMonitoringId.setTerm(blogPostMonitoringDTO.getTerm());
			
			BlogPostMonitoring blogPostMonitoring = blogPostMonitoringRemote.getById(blogPostMonitoringId);
			blogPostMonitoring.setGarbage('T');
			blogPostMonitoringRemote.merge(blogPostMonitoring);
			
			blogPostMonitoringDTO = MountDTO.mountBlogPostMonitoring(blogPostMonitoring);
			blogPostMonitoringDTO.setMonitoringPostTags(MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(blogPostMonitoring.getBlogPost().getIdBlogPost().toString(), 
					blogPostMonitoring.getMonitoring().getIdMonitoring(), blogPostMonitoring.getTerm(), Profile.BLOGS)));
			
			UtilFunctions.updateMonitoringReports(blogPostMonitoringDTO, MonitoringReport.NOT_GARBAGE);
		}
	}
	
}
