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
import br.com.opsocial.client.entity.monitoring.SampleDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceFacePostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceGooglePlusPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringQualificationReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagReportRemote;
import br.com.opsocial.ejb.das.MaintenanceNewsPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceReclameAquiPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceYoutubePostMonitoringRemote;

@RestController
@RequestMapping("woopsocial")
public class RecoverSampleAction extends ServerAction {
	
	private MaintenanceFacePostMonitoringRemote facePostMonitoringRemote;
	private MaintenanceTwitterPostMonitoringRemote twitterPostMonitoringRemote;
	private MaintenanceGooglePlusPostMonitoringRemote googlePlusPostMonitoringRemote;
	private MaintenanceYoutubePostMonitoringRemote youtubePostMonitoringRemote;
	private MaintenanceInstagramPostMonitoringRemote instagramPostMonitoringRemote;
	private MaintenanceReclameAquiPostMonitoringRemote reclameAquiPostMonitoringRemote;
	private MaintenanceNewsPostMonitoringRemote newsPostMonitoringRemote;
	private MaintenanceBlogPostMonitoringRemote blogPostMonitoringRemote;
	
	@Override
	@RequestMapping(value = "/recover_sample",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {	

		SampleDTO sampleDTO = (SampleDTO) getParameters().get("sample");
		
		List<Long> monitoringTags = new ArrayList<Long>();
		
		if(!sampleDTO.getTags().isEmpty()) {
			for(String idTag : sampleDTO.getTags().split(";")) {
				monitoringTags.add(Long.valueOf(idTag));	
			}
		}
		
		facePostMonitoringRemote = (MaintenanceFacePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("FacePostMonitoring");
		twitterPostMonitoringRemote = (MaintenanceTwitterPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("TwitterPostMonitoring");
		googlePlusPostMonitoringRemote = (MaintenanceGooglePlusPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("GooglePlusPostMonitoring");
		youtubePostMonitoringRemote = (MaintenanceYoutubePostMonitoringRemote) RecoverMaintenance.recoverMaintenance("YoutubePostMonitoring");
		instagramPostMonitoringRemote = (MaintenanceInstagramPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("InstagramPostMonitoring");
		reclameAquiPostMonitoringRemote = (MaintenanceReclameAquiPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("ReclameAquiPostMonitoring");
		newsPostMonitoringRemote = (MaintenanceNewsPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("NewsPostMonitoring");
		blogPostMonitoringRemote = (MaintenanceBlogPostMonitoringRemote) RecoverMaintenance.recoverMaintenance("BlogPostMonitoring");
		
		MaintenanceMonitoringReportRemote monitoringReportRemote = (MaintenanceMonitoringReportRemote) 
				RecoverMaintenance.recoverMaintenance("MonitoringReport");
		
		Long startDate = null;
		
		if(sampleDTO.getStartDate() != null) {
			startDate = UtilFunctions.getMidnightDateNoDaylightTime(sampleDTO.getStartDate());
		} else {
			startDate = UtilFunctions.getMidnightDateNoDaylightTime(new Date(sampleDTO.getMonitoringDTO().getCreatedAt() * 1000));
		}
		
		Long endDate = null;
		
		if(sampleDTO.getEndDate() != null) {
			endDate = UtilFunctions.getMidnightDateNoDaylightTime(sampleDTO.getEndDate());;
		} else {
			endDate = UtilFunctions.getMidnightDateNoDaylightTime(new Date());
		}
		
		if(sampleDTO.getQualifications().isEmpty() && sampleDTO.getTags().isEmpty()) {
						
			Long count = 0L;
			
			sampleDTO.setPostsByNetwork(monitoringReportRemote.getPostsPerDaysNetworks(
				sampleDTO.getMonitoringDTO().getIdMonitoring(), 
				startDate, 
				endDate));
			
			for(Character network : sampleDTO.getPostsByNetwork().keySet()) {
				
				if(sampleDTO.getNetworks().contains(String.valueOf(network.toString()))) {
					count += sampleDTO.getPostsByNetwork().get(network);		
				}
			}
			
			sampleDTO.setCount(sampleDTO.getCount() + count);
			
		}  else if(!sampleDTO.getQualifications().isEmpty()) {
			
			MaintenanceMonitoringQualificationReportRemote monitoringQualificationReportRemote = 
					(MaintenanceMonitoringQualificationReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringQualificationReport");
			
			Long count = 0L;
			
			sampleDTO.setPostsByNetwork(monitoringQualificationReportRemote.getSentimentAnalysisByNetwork(
					sampleDTO.getMonitoringDTO().getIdMonitoring(), 
					startDate, 
					endDate,
					sampleDTO.getQualifications().replace(";",",")));
			
			for(Character network : sampleDTO.getPostsByNetwork().keySet()) {
				if(sampleDTO.getNetworks().contains(String.valueOf(network.toString()))) {
					count += sampleDTO.getPostsByNetwork().get(network);
				}
			}
			
			sampleDTO.setCount(count);
			
		} else if(!sampleDTO.getTags().isEmpty()) {
			
			MaintenanceMonitoringTagReportRemote monitoringTagReportRemote = 
					(MaintenanceMonitoringTagReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringTagReport");
			
			Long count = 0L;
			
			sampleDTO.setPostsByNetwork(monitoringTagReportRemote.getTaggedPostsPerNetwork(
				sampleDTO.getMonitoringDTO().getIdMonitoring(),
				startDate, 
				endDate,
				sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime()
			));
			
			for(Character network : sampleDTO.getPostsByNetwork().keySet()) {
				if(sampleDTO.getNetworks().contains(String.valueOf(network.toString()))) {
					count += sampleDTO.getPostsByNetwork().get(network);
				}
			}
			
			sampleDTO.setCount(count);
			
		} else {

			populateFacebookCount(sampleDTO, monitoringTags);
			populateTwitterCount(sampleDTO, monitoringTags);
			populateGoogleCount(sampleDTO, monitoringTags);
			populateYoutubeCount(sampleDTO, monitoringTags);
			populateInstagramCount(sampleDTO, monitoringTags);
			populateReclameAquiCount(sampleDTO, monitoringTags);
			populateNewsCount(sampleDTO, monitoringTags);
			populateBlogsCount(sampleDTO, monitoringTags);
		}
		
		Integer n = null;
		
		if(sampleDTO.getError() != null) {
		
			Float Z = null;
			
			if(sampleDTO.getTrustLevel().equals(0.90F)) {
				Z = 1.645F;
			} else if(sampleDTO.getTrustLevel().equals(0.93F)) {
				Z = 1.8F;
			} else if(sampleDTO.getTrustLevel().equals(0.95F)) {
				Z = 1.96F;
			} else if(sampleDTO.getTrustLevel().equals(0.97F)) {
				Z = 2.1F;
			} else if(sampleDTO.getTrustLevel().equals(0.99F)) {
				Z = 2.575F;
			}
			
			Float p = 0.5F;
			Long N = sampleDTO.getCount();
			Float e = sampleDTO.getError();
			
			n = new Float(N*Z*Z*p*(1-p) / (Z*Z*p*(1-p) + e*e*(N-1))).intValue();
			
		} else {
			n=sampleDTO.getCount().intValue();
		}
	
		getParameters().put("population", new br.com.opsocial.client.entity.primitive.Long(sampleDTO.getCount()));
		getParameters().put("sample", new br.com.opsocial.client.entity.primitive.Integer(n));
		getParameters().put("sampleDTO", sampleDTO);
		
	}
	
	private void populateFacebookCount(SampleDTO sampleDTO, List<Long> monitoringTags) {
	
		if(sampleDTO.getNetworks().contains(String.valueOf(Profile.FACEBOOK))) { 
			
			Long count = facePostMonitoringRemote.getByIntervalCount(
					sampleDTO.getMonitoringDTO().getIdMonitoring(), 
					sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt(),
					sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime(),	
					sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
					monitoringTags != null ? monitoringTags : null);
			
			sampleDTO.setPostsByNetwork(new HashMap<Character,Long>());
			sampleDTO.getPostsByNetwork().put(Profile.FACEBOOK, count);
			
			sampleDTO.setCount(sampleDTO.getCount() + count);
			
		}
	}
	
	private void populateTwitterCount(SampleDTO sampleDTO, List<Long> monitoringTags) {
	
		if(sampleDTO.getNetworks().contains(String.valueOf(Profile.TWITTER))) {
			
			Long count = twitterPostMonitoringRemote.getByIntervalCount(
				sampleDTO.getMonitoringDTO().getIdMonitoring(), 
				sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() : sampleDTO.getMonitoringDTO().getCreatedAt(),
				sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() : new Date().getTime(),	
				sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
				monitoringTags != null ? monitoringTags : null, 
				null, //Terms
				null); //limit
			
			sampleDTO.setPostsByNetwork(new HashMap<Character,Long>());
			sampleDTO.getPostsByNetwork().put(Profile.TWITTER, count);

			sampleDTO.setCount(sampleDTO.getCount() + count);
		}
	}
	
	private void populateGoogleCount(SampleDTO sampleDTO, List<Long> monitoringTags) {

		if(sampleDTO.getNetworks().contains(String.valueOf(Profile.GOOGLE))) {
			
			Long count = googlePlusPostMonitoringRemote.getByIntervalCount(
				sampleDTO.getMonitoringDTO().getIdMonitoring(), 
				sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() : sampleDTO.getMonitoringDTO().getCreatedAt(),
				sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() : new Date().getTime(),	
				sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
				monitoringTags != null ? monitoringTags : null,
				null,
				null);
	
			sampleDTO.setPostsByNetwork(new HashMap<Character,Long>());
			sampleDTO.getPostsByNetwork().put(Profile.GOOGLE, count);
			
			sampleDTO.setCount(sampleDTO.getCount() + count);
		}
	}
	
	private void populateYoutubeCount(SampleDTO sampleDTO, List<Long> monitoringTags) {
	
		if(sampleDTO.getNetworks().contains(String.valueOf(Profile.YOUTUBE))) {
	
			Long count = youtubePostMonitoringRemote.getByIntervalCount(
				sampleDTO.getMonitoringDTO().getIdMonitoring(), 
				sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() : sampleDTO.getMonitoringDTO().getCreatedAt(),
				sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() : new Date().getTime(),	
				sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
				monitoringTags != null ? monitoringTags : null,
				null,
				null);
	
			sampleDTO.setPostsByNetwork(new HashMap<Character,Long>());
			sampleDTO.getPostsByNetwork().put(Profile.YOUTUBE, count);
			
			sampleDTO.setCount(sampleDTO.getCount() + count);
		}
	}
	
	private void populateInstagramCount(SampleDTO sampleDTO, List<Long> monitoringTags) {
	
		if(sampleDTO.getNetworks().contains(String.valueOf(Profile.INSTAGRAM))) {
			
			Long count = instagramPostMonitoringRemote.getByIntervalCount(
				sampleDTO.getMonitoringDTO().getIdMonitoring(), 
				sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() : sampleDTO.getMonitoringDTO().getCreatedAt(),
				sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() : new Date().getTime(),	
				sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
				monitoringTags != null ? monitoringTags : null,
				null,
				null);
	
			sampleDTO.setPostsByNetwork(new HashMap<Character,Long>());
			sampleDTO.getPostsByNetwork().put(Profile.INSTAGRAM, count);

			sampleDTO.setCount(sampleDTO.getCount() + count);
		}
	}
	
	private void populateReclameAquiCount(SampleDTO sampleDTO, List<Long> monitoringTags) {
	
		if(sampleDTO.getNetworks().contains(String.valueOf(Profile.RECLAMEAQUI))) {
			
			Long count = reclameAquiPostMonitoringRemote.getByIntervalCount(
				sampleDTO.getMonitoringDTO().getIdMonitoring(), 
				sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt() / 1000,
				sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime() / 1000,	
				sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
				monitoringTags != null ? monitoringTags : null,
				null,
				null);
	
			sampleDTO.setPostsByNetwork(new HashMap<Character,Long>());
			sampleDTO.getPostsByNetwork().put(Profile.RECLAMEAQUI, count);
			
			sampleDTO.setCount(sampleDTO.getCount() + count);
		}
	}
	
	private void populateNewsCount(SampleDTO sampleDTO, List<Long> monitoringTags) {
	
		if(sampleDTO.getNetworks().contains(String.valueOf(Profile.NEWS))) {
			
			Long count = newsPostMonitoringRemote.getByIntervalCount(
				sampleDTO.getMonitoringDTO().getIdMonitoring(), 
				sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt() / 1000,
				sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime() / 1000,	
				sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
				monitoringTags != null ? monitoringTags : null,
				null,
				null);
	
			sampleDTO.setPostsByNetwork(new HashMap<Character,Long>());
			sampleDTO.getPostsByNetwork().put(Profile.NEWS, count);
			
			sampleDTO.setCount(sampleDTO.getCount() + count);
		}
	}
	

	private void populateBlogsCount(SampleDTO sampleDTO, List<Long> monitoringTags) {
		
		if(sampleDTO.getNetworks().contains(String.valueOf(Profile.BLOGS))) {
			
			Long count = blogPostMonitoringRemote.getByIntervalCount(
				sampleDTO.getMonitoringDTO().getIdMonitoring(), 
				sampleDTO.getStartDate() != null ? sampleDTO.getStartDate().getTime() / 1000 : sampleDTO.getMonitoringDTO().getCreatedAt() / 1000,
				sampleDTO.getEndDate() != null ? sampleDTO.getEndDate().getTime() / 1000 : new Date().getTime() / 1000,	
				sampleDTO.getQualifications() != null && !sampleDTO.getQualifications().isEmpty() ? sampleDTO.getQualifications() : null, 
				monitoringTags != null ? monitoringTags : null,
				null,
				null);
	
			sampleDTO.setPostsByNetwork(new HashMap<Character,Long>());
			sampleDTO.getPostsByNetwork().put(Profile.BLOGS, count);
			
			sampleDTO.setCount(sampleDTO.getCount() + count);
		}
	}
}
