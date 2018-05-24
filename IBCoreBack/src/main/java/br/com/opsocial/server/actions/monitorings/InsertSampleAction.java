package br.com.opsocial.server.actions.monitorings;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.client.entity.monitoring.PostMonitoringDTO;
import br.com.opsocial.client.entity.monitoring.SampleDTO;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceFacePostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceGooglePlusPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceSamplePostRemote;
import br.com.opsocial.ejb.das.MaintenanceSampleRemote;
import br.com.opsocial.ejb.das.MaintenanceSampleReportRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceYoutubePostMonitoringRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.blogs.BlogPostMonitoring;
import br.com.opsocial.ejb.entity.facebook.FacePostMonitoring;
import br.com.opsocial.ejb.entity.google.GooglePlusPostMonitoring;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;
import br.com.opsocial.ejb.entity.monitoring.Sample;
import br.com.opsocial.ejb.entity.monitoring.SamplePost;
import br.com.opsocial.ejb.entity.monitoring.SampleReport;
import br.com.opsocial.ejb.entity.news.NewsPostMonitoring;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPostMonitoring;
import br.com.opsocial.ejb.entity.twitter.TwitterPostMonitoring;
import br.com.opsocial.ejb.entity.youtube.YoutubePostMonitoring;

@RestController
@RequestMapping("woopsocial")
public class InsertSampleAction extends ServerAction {
	
	private MaintenanceFacePostMonitoringRemote facePostMonitoringRemote;
	private MaintenanceTwitterPostMonitoringRemote twitterPostMonitoringRemote;
	private MaintenanceGooglePlusPostMonitoringRemote googlePlusPostMonitoringRemote;
	private MaintenanceYoutubePostMonitoringRemote youtubePostMonitoringRemote;
	private MaintenanceInstagramPostMonitoringRemote instagramPostMonitoringRemote;
	private MaintenanceReclameAquiPostMonitoringRemote reclameAquiPostMonitoringRemote;
	private MaintenanceNewsPostMonitoringRemote newsPostMonitoringRemote;
	private MaintenanceBlogPostMonitoringRemote blogPostMonitoringRemote;
	
	private Sample sampleBase;
	
	@Override
	@RequestMapping(value = "/insert_sample",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
	
		SampleDTO sampleDTO = (SampleDTO) getParameters().get("sample");
		sampleDTO.setCreateDate(new Date());
		
		if(sampleDTO.getStartDate() == null) {
			sampleDTO.setStartDate(new Date(sampleDTO.getMonitoringDTO().getCreatedAt()*1000));
		}
		
		if(sampleDTO.getEndDate() == null) {
			sampleDTO.setEndDate(new Date());
		}
		
		setParameters(new HashMap<String,Object>());
		
		MaintenanceSampleRemote remote = (MaintenanceSampleRemote) RecoverMaintenance.recoverMaintenance("Sample");
		
		if(remote.getByName(sampleDTO.getName(), sampleDTO.getMonitoringDTO().getIdMonitoring()) == null) {
			sampleBase = remote.save(MountDTO.mountSample(sampleDTO));
			
			List<Long> monitoringTags = new ArrayList<Long>();
			
			if(!sampleDTO.getTags().isEmpty()) {
				for(String idTag : sampleDTO.getTags().split(";")) {
					monitoringTags.add(Long.valueOf(idTag));	
				}
			}

			List<PostMonitoringDTO> posts = new ArrayList<PostMonitoringDTO>();
			List<PostMonitoringDTO> facebookPosts = new ArrayList<PostMonitoringDTO>();
			List<PostMonitoringDTO> twitterPosts = new ArrayList<PostMonitoringDTO>();
			List<PostMonitoringDTO> googlePosts = new ArrayList<PostMonitoringDTO>();
			List<PostMonitoringDTO> youtubePosts = new ArrayList<PostMonitoringDTO>();
			List<PostMonitoringDTO> instagramPosts = new ArrayList<PostMonitoringDTO>();
			List<PostMonitoringDTO> reclameAquiPosts = new ArrayList<PostMonitoringDTO>();
			List<PostMonitoringDTO> newsPosts = new ArrayList<PostMonitoringDTO>();
			List<PostMonitoringDTO> blogsPosts = new ArrayList<PostMonitoringDTO>();
			
			//FACEBOOK
			
			if(sampleDTO.getNetworks().contains(String.valueOf(Profile.FACEBOOK)) && 
					sampleDTO.getPostsByNetwork().get(Profile.FACEBOOK) > 0) {
				
				Double networkPercent = sampleDTO.getPostsByNetwork().get(Profile.FACEBOOK).doubleValue() / sampleDTO.getCount().doubleValue();
				
				facePostMonitoringRemote = (MaintenanceFacePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("FacePostMonitoring");
				
				List<String> notIn = new ArrayList<String>();
				
				if(!facebookPosts.isEmpty()) {
					for(PostMonitoringDTO post : facebookPosts) {
						notIn.add(post.getPostId());
					}
				}
				
				List<FacePostMonitoring> facePosts = facePostMonitoringRemote.listSample(
					sampleDTO.getMonitoringDTO().getIdMonitoring(), 
					sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt(),
					sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime(),	
					sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
					monitoringTags != null ? monitoringTags : null, Double.valueOf(sampleDTO.getSample() * networkPercent).intValue(), notIn);
				
				for(FacePostMonitoring facePostMonitoring : facePosts) {
					facebookPosts.add(MountDTO.mountFacePostMonitoring(facePostMonitoring));
					posts.add(MountDTO.mountFacePostMonitoring(facePostMonitoring));
				}
			}	
			
			//TWITTER
			
			if(sampleDTO.getNetworks().contains(String.valueOf(Profile.TWITTER)) && sampleDTO.getPostsByNetwork().get(Profile.TWITTER) > 0) {
				
				Double networkPercent = sampleDTO.getPostsByNetwork().get(Profile.TWITTER).doubleValue() / sampleDTO.getCount().doubleValue();
				
				twitterPostMonitoringRemote = (MaintenanceTwitterPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");
				
				List<String> notIn = new ArrayList<String>();
				
				if(!twitterPosts.isEmpty()) {
					for(PostMonitoringDTO post : twitterPosts) {
						notIn.add(post.getPostId());
					}
				}
				
				List<TwitterPostMonitoring> twitterPostMonitorings = twitterPostMonitoringRemote.listSample(
					sampleDTO.getMonitoringDTO().getIdMonitoring(), 
					sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt(),
					sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime(),	
					sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
					monitoringTags != null ? monitoringTags : null, Double.valueOf(sampleDTO.getSample() * networkPercent).intValue(), notIn);
				
				for(TwitterPostMonitoring twitterPostMonitoring : twitterPostMonitorings) {
					twitterPosts.add(MountDTO.mountTwitterPostMonitoring(twitterPostMonitoring));
					posts.add(MountDTO.mountTwitterPostMonitoring(twitterPostMonitoring));
				}
			}	
			
			//GOOGLE+
			
			if(sampleDTO.getNetworks().contains(String.valueOf(Profile.GOOGLE)) && sampleDTO.getPostsByNetwork().get(Profile.GOOGLE) > 0) {
				
				Double networkPercent = sampleDTO.getPostsByNetwork().get(Profile.GOOGLE).doubleValue() / sampleDTO.getCount().doubleValue();
				
				googlePlusPostMonitoringRemote = (MaintenanceGooglePlusPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("GooglePlusPostMonitoring");
				
				List<String> notIn = new ArrayList<String>();
				
				if(!googlePosts.isEmpty()) {
					for(PostMonitoringDTO post : googlePosts) {
						notIn.add(post.getPostId());
					}
				}
				
				List<GooglePlusPostMonitoring> googlePlusPostMonitorings = googlePlusPostMonitoringRemote.listSample(
					sampleDTO.getMonitoringDTO().getIdMonitoring(), 
					sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt(),
					sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime(),	
					sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
					monitoringTags != null ? monitoringTags : null, Double.valueOf(sampleDTO.getSample() * networkPercent).intValue(), notIn);
				
				for(GooglePlusPostMonitoring googlePlusPostMonitoring : googlePlusPostMonitorings) {
					googlePosts.add(MountDTO.mountGooglePlusPostMonitoring(googlePlusPostMonitoring));
					posts.add(MountDTO.mountGooglePlusPostMonitoring(googlePlusPostMonitoring));
				}
			}	
						
			//YOUTUBE
			
			if(sampleDTO.getNetworks().contains(String.valueOf(Profile.YOUTUBE)) && sampleDTO.getPostsByNetwork().get(Profile.YOUTUBE) > 0) {
				
				Double networkPercent = sampleDTO.getPostsByNetwork().get(Profile.YOUTUBE).doubleValue() / sampleDTO.getCount().doubleValue();
				
				youtubePostMonitoringRemote = (MaintenanceYoutubePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("YoutubePostMonitoring");
				
				List<String> notIn = new ArrayList<String>();
				
				if(!youtubePosts.isEmpty()) {
					for(PostMonitoringDTO post : youtubePosts) {
						notIn.add(post.getPostId());
					}
				}
				
				List<YoutubePostMonitoring> youtubePostMonitorings = youtubePostMonitoringRemote.listSample(
					sampleDTO.getMonitoringDTO().getIdMonitoring(), 
					sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt(),
					sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime(),	
					sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
					monitoringTags != null ? monitoringTags : null, Double.valueOf(sampleDTO.getSample() * networkPercent).intValue(), notIn);
				
				for(YoutubePostMonitoring youtubePostMonitoring : youtubePostMonitorings) {
					youtubePosts.add(MountDTO.mountYoutubePostMonitoring(youtubePostMonitoring));
					posts.add(MountDTO.mountYoutubePostMonitoring(youtubePostMonitoring));
				}
			}	
							
			//INSTAGRAM
			
			if(sampleDTO.getNetworks().contains(String.valueOf(Profile.INSTAGRAM)) && sampleDTO.getPostsByNetwork().get(Profile.INSTAGRAM) > 0) {
				
				Double networkPercent = sampleDTO.getPostsByNetwork().get(Profile.INSTAGRAM).doubleValue() / sampleDTO.getCount().doubleValue();
				
				instagramPostMonitoringRemote = (MaintenanceInstagramPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("InstagramPostMonitoring");
				
				List<String> notIn = new ArrayList<String>();
				
				if(!instagramPosts.isEmpty()) {
					for(PostMonitoringDTO post : instagramPosts) {
						notIn.add(post.getPostId());
					}
				}
				
				List<InstagramPostMonitoring> instagramPostMonitorings = instagramPostMonitoringRemote.listSample(
					sampleDTO.getMonitoringDTO().getIdMonitoring(), 
					sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt(),
					sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime(),	
					sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
					monitoringTags != null ? monitoringTags : null, Double.valueOf(sampleDTO.getSample() * networkPercent).intValue(), notIn);
				
				for(InstagramPostMonitoring instagramPostMonitoring : instagramPostMonitorings) {
					instagramPosts.add(MountDTO.mountInstagramPostMonitoring(instagramPostMonitoring));
					posts.add(MountDTO.mountInstagramPostMonitoring(instagramPostMonitoring));
				}
			}	
							
			//RECLAMEAQUI
			
			if(sampleDTO.getNetworks().contains(String.valueOf(Profile.RECLAMEAQUI)) && sampleDTO.getPostsByNetwork().get(Profile.RECLAMEAQUI) > 0) {
				
				Double networkPercent = sampleDTO.getPostsByNetwork().get(Profile.RECLAMEAQUI).doubleValue() / sampleDTO.getCount().doubleValue();
				
				reclameAquiPostMonitoringRemote = (MaintenanceReclameAquiPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("ReclameAquiPostMonitoring");
				
				List<String> notIn = new ArrayList<String>();
				
				if(!reclameAquiPosts.isEmpty()) {
					for(PostMonitoringDTO post : reclameAquiPosts) {
						notIn.add(post.getPostId());
					}
				}
				
				List<ReclameAquiPostMonitoring> reclameAquiPostMonitorings = reclameAquiPostMonitoringRemote.listSample(
					sampleDTO.getMonitoringDTO().getIdMonitoring(), 
					sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt(),
					sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime(),	
					sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
					monitoringTags != null ? monitoringTags : null, Double.valueOf(sampleDTO.getSample() * networkPercent).intValue(), notIn);
				
				for(ReclameAquiPostMonitoring reclameAquiPostMonitoring : reclameAquiPostMonitorings) {
					reclameAquiPosts.add(MountDTO.mountReclameAquiPostMonitoring(reclameAquiPostMonitoring));
					posts.add(MountDTO.mountReclameAquiPostMonitoring(reclameAquiPostMonitoring));
				}
			}	
							
			//NEWS
			
			if(sampleDTO.getNetworks().contains(String.valueOf(Profile.NEWS)) && sampleDTO.getPostsByNetwork().get(Profile.NEWS) > 0) {
				
				Double networkPercent = sampleDTO.getPostsByNetwork().get(Profile.NEWS).doubleValue() / sampleDTO.getCount().doubleValue();
				
				newsPostMonitoringRemote = (MaintenanceNewsPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("NewsPostMonitoring");
				
				List<String> notIn = new ArrayList<String>();
				
				if(!newsPosts.isEmpty()) {
					for(PostMonitoringDTO post : newsPosts) {
						notIn.add(post.getPostId());
					}
				}
				
				List<NewsPostMonitoring> newsPostMonitorings = newsPostMonitoringRemote.listSample(
					sampleDTO.getMonitoringDTO().getIdMonitoring(), 
					sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt(),
					sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime(),	
					sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
					monitoringTags != null ? monitoringTags : null, Double.valueOf(sampleDTO.getSample() * networkPercent).intValue(), notIn);
				
				for(NewsPostMonitoring newsPostMonitoring : newsPostMonitorings) {
					newsPosts.add(MountDTO.mountNewsPostMonitoring(newsPostMonitoring));
					posts.add(MountDTO.mountNewsPostMonitoring(newsPostMonitoring));
				}
			}	
										
			//BLOGS
			
			if(sampleDTO.getNetworks().contains(String.valueOf(Profile.BLOGS)) && sampleDTO.getPostsByNetwork().get(Profile.BLOGS) > 0) {
				
				Double networkPercent = sampleDTO.getPostsByNetwork().get(Profile.BLOGS).doubleValue() / sampleDTO.getCount().doubleValue();
				
				blogPostMonitoringRemote = (MaintenanceBlogPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("BlogPostMonitoring");
				
				List<String> notIn = new ArrayList<String>();
				
				if(!blogsPosts.isEmpty()) {
					for(PostMonitoringDTO post : blogsPosts) {
						notIn.add(post.getPostId());
					}
				}
				
				List<BlogPostMonitoring> blogPostMonitorings = blogPostMonitoringRemote.listSample(
					sampleDTO.getMonitoringDTO().getIdMonitoring(), 
					sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt(),
					sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime(),	
					sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
					monitoringTags != null ? monitoringTags : null, Double.valueOf(sampleDTO.getSample() * networkPercent).intValue(), notIn);
				
				for(BlogPostMonitoring blogPostMonitoring : blogPostMonitorings) {
					blogsPosts.add(MountDTO.mountBlogPostMonitoring(blogPostMonitoring));
					posts.add(MountDTO.mountBlogPostMonitoring(blogPostMonitoring));
				}
			}	
										
			MaintenanceSamplePostRemote samplePostRemote = (MaintenanceSamplePostRemote) RecoverMaintenance.recoverMaintenance("SamplePost");

			HashMap<Character, HashMap<Integer, Integer>> mappedPostsByNetworkAndWeek = new HashMap<Character, HashMap<Integer, Integer>>();
			HashMap<Character, HashMap<Integer, Integer>> mappedPostsByNetworkAndHour = new HashMap<Character, HashMap<Integer, Integer>>();
			HashMap<Long, Long> mappedPostsByDate = new HashMap<Long, Long>();
			
			for(PostMonitoringDTO post : posts) {
				
				Date date = new Date(post.getCreatedTime()*1000);

				if(mappedPostsByNetworkAndWeek.get(post.getNetwork()) == null) {
					mappedPostsByNetworkAndWeek.put(post.getNetwork(), new HashMap<Integer, Integer>());
				}
				
				if(mappedPostsByNetworkAndWeek.get(post.getNetwork()).get(date.getDay()) == null) {
					mappedPostsByNetworkAndWeek.get(post.getNetwork()).put(date.getDay(), 0);
				}
				
				if(mappedPostsByNetworkAndHour.get(post.getNetwork()) == null) {
					mappedPostsByNetworkAndHour.put(post.getNetwork(), new HashMap<Integer, Integer>());
				}
				
				if(mappedPostsByNetworkAndHour.get(post.getNetwork()).get(date.getHours()) == null) {
					mappedPostsByNetworkAndHour.get(post.getNetwork()).put(date.getHours(), 0);
				}
				
				mappedPostsByNetworkAndWeek.get(post.getNetwork()).put(date.getDay(),mappedPostsByNetworkAndWeek.get(post.getNetwork()).get(date.getDay()) + 1);
				mappedPostsByNetworkAndHour.get(post.getNetwork()).put(date.getHours(),mappedPostsByNetworkAndHour.get(post.getNetwork()).get(date.getHours()) + 1);

				date.setHours(0);
				date.setMinutes(0);
				date.setSeconds(0);
				
				if(mappedPostsByDate.get(date.getTime()) == null) {
					mappedPostsByDate.put(date.getTime(), 1L);
				} else {
					mappedPostsByDate.put(date.getTime(), mappedPostsByDate.get(date.getTime()) + 1L);
				}
				
				SamplePost samplePost = new SamplePost();
				samplePost.setNetwork(post.getNetwork());
				samplePost.setPostId(post.getPostId());
				samplePost.setSample(sampleBase);

				samplePostRemote.save(samplePost);
			}
			
			insertEmpties(mappedPostsByNetworkAndWeek, mappedPostsByNetworkAndHour);
			
			//REPORTS
			
			MaintenanceSampleReportRemote sampleReportRemote = (MaintenanceSampleReportRemote) RecoverMaintenance.recoverMaintenance("SampleReport");
			
//			for(Long date: mappedPostsByDate.keySet()) {
//				
//				SampleReport postsByDay = new SampleReport();
//				postsByDay.setSample(sampleBase);
//				postsByDay.setProperty(SampleReport.POSTS_BY_DATE);
//				postsByDay.setKey(date);
//				postsByDay.setValue(mappedPostsByDate.get(date));
//				
//				sampleReportRemote.save(postsByDay);
//			}
			
			for(Character network : mappedPostsByNetworkAndWeek.keySet()) {
				
				for(Integer dayOfWeek : mappedPostsByNetworkAndWeek.get(network).keySet()) {
					
					SampleReport postsByDay = new SampleReport();
					postsByDay.setSample(sampleBase);
					postsByDay.setProperty(SampleReport.POSTS_BY_DAY);
					postsByDay.setKey(dayOfWeek);
					postsByDay.setValue(Long.valueOf(mappedPostsByNetworkAndWeek.get(network).get(dayOfWeek)));
					postsByDay.setNetwork(network);
					
					sampleReportRemote.save(postsByDay);
				}
			}
			
			for(Character network : mappedPostsByNetworkAndHour.keySet()) {
				
				for(Integer hour : mappedPostsByNetworkAndHour.get(network).keySet()) {
					
					SampleReport postsByDay = new SampleReport();
					postsByDay.setSample(sampleBase);
					postsByDay.setProperty(SampleReport.POSTS_BY_HOUR);
					postsByDay.setKey(hour);
					postsByDay.setValue(Long.valueOf(mappedPostsByNetworkAndHour.get(network).get(hour)));
					postsByDay.setNetwork(network);
					
					sampleReportRemote.save(postsByDay);
				}
			}
			
		} else {
			getParameters().put("message", new StringUtil("Já existe uma amostragem com esse nome"));
		}
	}
	
	private void insertEmpties(HashMap<Character, HashMap<Integer, Integer>> mappedPostsByNetworkAndWeek, HashMap<Character, HashMap<Integer, Integer>> mappedPostsByNetworkAndHour) {
		
		if(mappedPostsByNetworkAndWeek.get(Profile.FACEBOOK) == null) {
			
			mappedPostsByNetworkAndWeek.put(Profile.FACEBOOK, new HashMap<Integer,Integer>());
			
			for(int i=0;i<7;i++) {
				mappedPostsByNetworkAndWeek.get(Profile.FACEBOOK).put(i, 0);
			}
		} else {
			for(int i=0;i<7;i++) {
				if(mappedPostsByNetworkAndWeek.get(Profile.FACEBOOK).get(i) == null) {
					mappedPostsByNetworkAndWeek.get(Profile.FACEBOOK).put(i, 0);	
				}
			}
		}
		
		if(mappedPostsByNetworkAndHour.get(Profile.FACEBOOK) == null) {
			
			mappedPostsByNetworkAndHour.put(Profile.FACEBOOK, new HashMap<Integer,Integer>());
			
			for(int i=0;i<24;i++) {
				mappedPostsByNetworkAndHour.get(Profile.FACEBOOK).put(i, 0);
			}
		} else {
			for(int i=0;i<24;i++) {
				if(mappedPostsByNetworkAndHour.get(Profile.FACEBOOK).get(i) == null) {
					mappedPostsByNetworkAndHour.get(Profile.FACEBOOK).put(i, 0);	
				}
			}
		}
		


		if(mappedPostsByNetworkAndWeek.get(Profile.TWITTER) == null) {
			
			mappedPostsByNetworkAndWeek.put(Profile.TWITTER, new HashMap<Integer,Integer>());
			
			for(int i=0;i<7;i++) {
				mappedPostsByNetworkAndWeek.get(Profile.TWITTER).put(i, 0);
			}
		} else {
			for(int i=0;i<7;i++) {
				if(mappedPostsByNetworkAndWeek.get(Profile.TWITTER).get(i) == null) {
					mappedPostsByNetworkAndWeek.get(Profile.TWITTER).put(i, 0);	
				}
			}
		}
		
		if(mappedPostsByNetworkAndHour.get(Profile.TWITTER) == null) {
			
			mappedPostsByNetworkAndHour.put(Profile.TWITTER, new HashMap<Integer,Integer>());
			
			for(int i=0;i<24;i++) {
				mappedPostsByNetworkAndHour.get(Profile.TWITTER).put(i, 0);
			}
		} else {
			for(int i=0;i<24;i++) {
				if(mappedPostsByNetworkAndHour.get(Profile.TWITTER).get(i) == null) {
					mappedPostsByNetworkAndHour.get(Profile.TWITTER).put(i, 0);	
				}
			}
		}
		


		if(mappedPostsByNetworkAndWeek.get(Profile.GOOGLE) == null) {
			
			mappedPostsByNetworkAndWeek.put(Profile.GOOGLE, new HashMap<Integer,Integer>());
			
			for(int i=0;i<7;i++) {
				mappedPostsByNetworkAndWeek.get(Profile.GOOGLE).put(i, 0);
			}
		} else {
			for(int i=0;i<7;i++) {
				if(mappedPostsByNetworkAndWeek.get(Profile.GOOGLE).get(i) == null) {
					mappedPostsByNetworkAndWeek.get(Profile.GOOGLE).put(i, 0);	
				}
			}
		}
		
		if(mappedPostsByNetworkAndHour.get(Profile.GOOGLE) == null) {
			
			mappedPostsByNetworkAndHour.put(Profile.GOOGLE, new HashMap<Integer,Integer>());
			
			for(int i=0;i<24;i++) {
				mappedPostsByNetworkAndHour.get(Profile.GOOGLE).put(i, 0);
			}
		} else {
			for(int i=0;i<24;i++) {
				if(mappedPostsByNetworkAndHour.get(Profile.GOOGLE).get(i) == null) {
					mappedPostsByNetworkAndHour.get(Profile.GOOGLE).put(i, 0);	
				}
			}
		}
		


		if(mappedPostsByNetworkAndWeek.get(Profile.YOUTUBE) == null) {
			
			mappedPostsByNetworkAndWeek.put(Profile.YOUTUBE, new HashMap<Integer,Integer>());
			
			for(int i=0;i<7;i++) {
				mappedPostsByNetworkAndWeek.get(Profile.YOUTUBE).put(i, 0);
			}
		} else {
			for(int i=0;i<7;i++) {
				if(mappedPostsByNetworkAndWeek.get(Profile.YOUTUBE).get(i) == null) {
					mappedPostsByNetworkAndWeek.get(Profile.YOUTUBE).put(i, 0);	
				}
			}
		}
		
		if(mappedPostsByNetworkAndHour.get(Profile.YOUTUBE) == null) {

			mappedPostsByNetworkAndHour.put(Profile.YOUTUBE, new HashMap<Integer,Integer>());
			
			for(int i=0;i<24;i++) {
				mappedPostsByNetworkAndHour.get(Profile.YOUTUBE).put(i, 0);
			}
		} else {
			for(int i=0;i<24;i++) {
				if(mappedPostsByNetworkAndHour.get(Profile.YOUTUBE).get(i) == null) {
					mappedPostsByNetworkAndHour.get(Profile.YOUTUBE).put(i, 0);	
				}
			}
		}
		


		if(mappedPostsByNetworkAndWeek.get(Profile.INSTAGRAM) == null) {

			mappedPostsByNetworkAndWeek.put(Profile.INSTAGRAM, new HashMap<Integer,Integer>());
			
			for(int i=0;i<7;i++) {
				mappedPostsByNetworkAndWeek.get(Profile.INSTAGRAM).put(i, 0);
			}
		} else {
			for(int i=0;i<7;i++) {
				if(mappedPostsByNetworkAndWeek.get(Profile.INSTAGRAM).get(i) == null) {
					mappedPostsByNetworkAndWeek.get(Profile.INSTAGRAM).put(i, 0);	
				}
			}
		}
		
		if(mappedPostsByNetworkAndHour.get(Profile.INSTAGRAM) == null) {

			mappedPostsByNetworkAndHour.put(Profile.INSTAGRAM, new HashMap<Integer,Integer>());
			
			for(int i=0;i<24;i++) {
				mappedPostsByNetworkAndHour.get(Profile.INSTAGRAM).put(i, 0);
			}
		} else {
			for(int i=0;i<24;i++) {
				if(mappedPostsByNetworkAndHour.get(Profile.INSTAGRAM).get(i) == null) {
					mappedPostsByNetworkAndHour.get(Profile.INSTAGRAM).put(i, 0);	
				}
			}
		}
		


		if(mappedPostsByNetworkAndWeek.get(Profile.RECLAMEAQUI) == null) {

			mappedPostsByNetworkAndWeek.put(Profile.RECLAMEAQUI, new HashMap<Integer,Integer>());
			
			for(int i=0;i<7;i++) {
				mappedPostsByNetworkAndWeek.get(Profile.RECLAMEAQUI).put(i, 0);
			}
		} else {
			for(int i=0;i<7;i++) {
				if(mappedPostsByNetworkAndWeek.get(Profile.RECLAMEAQUI).get(i) == null) {
					mappedPostsByNetworkAndWeek.get(Profile.RECLAMEAQUI).put(i, 0);	
				}
			}
		}
		
		if(mappedPostsByNetworkAndHour.get(Profile.RECLAMEAQUI) == null) {

			mappedPostsByNetworkAndHour.put(Profile.RECLAMEAQUI, new HashMap<Integer,Integer>());
			
			for(int i=0;i<24;i++) {
				mappedPostsByNetworkAndHour.get(Profile.RECLAMEAQUI).put(i, 0);
			}
		} else {
			for(int i=0;i<24;i++) {
				if(mappedPostsByNetworkAndHour.get(Profile.RECLAMEAQUI).get(i) == null) {
					mappedPostsByNetworkAndHour.get(Profile.RECLAMEAQUI).put(i, 0);	
				}
			}
		}
		


		if(mappedPostsByNetworkAndWeek.get(Profile.NEWS) == null) {

			mappedPostsByNetworkAndWeek.put(Profile.NEWS, new HashMap<Integer,Integer>());
			
			for(int i=0;i<7;i++) {
				mappedPostsByNetworkAndWeek.get(Profile.NEWS).put(i, 0);
			}
		} else {
			for(int i=0;i<7;i++) {
				if(mappedPostsByNetworkAndWeek.get(Profile.NEWS).get(i) == null) {
					mappedPostsByNetworkAndWeek.get(Profile.NEWS).put(i, 0);	
				}
			}
		}
		
		if(mappedPostsByNetworkAndHour.get(Profile.NEWS) == null) {

			mappedPostsByNetworkAndHour.put(Profile.NEWS, new HashMap<Integer,Integer>());
			
			for(int i=0;i<24;i++) {
				mappedPostsByNetworkAndHour.get(Profile.NEWS).put(i, 0);
			}
		} else {
			for(int i=0;i<24;i++) {
				if(mappedPostsByNetworkAndHour.get(Profile.NEWS).get(i) == null) {
					mappedPostsByNetworkAndHour.get(Profile.NEWS).put(i, 0);	
				}
			}
		}
		


		if(mappedPostsByNetworkAndWeek.get(Profile.BLOGS) == null) {

			mappedPostsByNetworkAndWeek.put(Profile.BLOGS, new HashMap<Integer,Integer>());
			
			for(int i=0;i<7;i++) {
				mappedPostsByNetworkAndWeek.get(Profile.BLOGS).put(i, 0);
			}
		} else {
			for(int i=0;i<7;i++) {
				if(mappedPostsByNetworkAndWeek.get(Profile.BLOGS).get(i) == null) {
					mappedPostsByNetworkAndWeek.get(Profile.BLOGS).put(i, 0);	
				}
			}
		}
		
		if(mappedPostsByNetworkAndHour.get(Profile.BLOGS) == null) {

			mappedPostsByNetworkAndHour.put(Profile.BLOGS, new HashMap<Integer,Integer>());
			
			for(int i=0;i<24;i++) {
				mappedPostsByNetworkAndHour.get(Profile.BLOGS).put(i, 0);
			}
		} else {
			for(int i=0;i<24;i++) {
				if(mappedPostsByNetworkAndHour.get(Profile.BLOGS).get(i) == null) {
					mappedPostsByNetworkAndHour.get(Profile.BLOGS).put(i, 0);	
				}
			}
		}		
	}
}
