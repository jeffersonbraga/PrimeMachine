package br.com.opsocial.server.actions.monitorings;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.client.entity.monitoring.MonitoringInfluentialUserDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringReportDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringTagDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringTagReportDTO;
import br.com.opsocial.client.entity.monitoring.MonitoringTermReportDTO;
import br.com.opsocial.client.entity.monitoring.SampleDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.Calc;
import br.com.opsocial.server.utils.GenericDateInterval;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.monitorings.UtilMonitorings;
import br.com.opsocial.ejb.das.MaintenanceMonitoringInfluentialUserRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringReportRemote;
import br.com.opsocial.ejb.das.MaintenanceSamplePostRemote;
import br.com.opsocial.ejb.das.MaintenanceSampleReportRemote;

@RestController
@RequestMapping("woopsocial")
public class GenerateSampleReportAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/generate_sample_report",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		getParameters().put("monitoringReport", mountMonitoringReport((SampleDTO) getParameters().get("sample")));		
	}
	
	public static MonitoringReportDTO mountMonitoringReport(SampleDTO sample) {
		

		MaintenanceSamplePostRemote samplePostRemote = (MaintenanceSamplePostRemote) RecoverMaintenance.recoverMaintenance("SamplePost");
		MaintenanceSampleReportRemote sampleReportRemote = (MaintenanceSampleReportRemote) RecoverMaintenance.recoverMaintenance("SampleReport");
		
		MonitoringReportDTO monitoringReportDTO = new MonitoringReportDTO();
		
		monitoringReportDTO.setDateFrom(sample.getStartDate().getTime() / 1000);
		monitoringReportDTO.setDateUntil(sample.getEndDate().getTime() / 1000);
		
		monitoringReportDTO.setPostsPerDay(new HashMap<Date,Long>());
		
		monitoringReportDTO.setPostsPerDaysNetworks(new HashMap<Character, Long>());
		
		List<Object[]> postsByNetwork = samplePostRemote.getPostsByNetwork(sample.getIdSample());
		
		for(Object[] obj : postsByNetwork) {
			monitoringReportDTO.getPostsPerDaysNetworks().put(obj[0].toString().toCharArray()[0], Long.valueOf(obj[1].toString()));
		}

		monitoringReportDTO.setPostsPerHoursNetworks(new HashMap<Integer,Map<Character,Long>>());
		
		List<Object[]> postsPerHoursNetworks = sampleReportRemote.getPostsPerHoursNetworks(sample.getIdSample());
		
		for(Object[] obj : postsPerHoursNetworks) {
			
			if(monitoringReportDTO.getPostsPerHoursNetworks().get(obj[0]) == null) {
				monitoringReportDTO.getPostsPerHoursNetworks().put(Integer.valueOf(obj[0].toString()), new HashMap<Character,Long>());
			}
			
			monitoringReportDTO.getPostsPerHoursNetworks().get(obj[0]).put(obj[1].toString().charAt(0), Long.valueOf(obj[2].toString()));
			
		}
		
		monitoringReportDTO.setPostsPerWeekDaysNetworks(new HashMap<Integer,Map<Character,Long>>());
		
		List<Object[]> postsPerWeekNetworks = sampleReportRemote.getPostsPerWeekNetworks(sample.getIdSample());
		
		for(Object[] obj : postsPerWeekNetworks) {
			
			if(monitoringReportDTO.getPostsPerWeekDaysNetworks().get(obj[0]) == null) {
				monitoringReportDTO.getPostsPerWeekDaysNetworks().put(Integer.valueOf(obj[0].toString()), new HashMap<Character,Long>());
			}
			
			monitoringReportDTO.getPostsPerWeekDaysNetworks().get(obj[0]).put(obj[1].toString().charAt(0), Long.valueOf(obj[2].toString()));
			
		}
		
//		List<Object[]> postsByDate = sampleReportRemote.getPostsByDate(sample.getIdSample());
//		
//		for(Object[] obj : postsByDate) {
//			monitoringReportDTO.getPostsPerDay().put(new Date(Long.valueOf(obj.toString())), Long.valueOf(obj[1].toString()));
//		}
		
		Object[] mostVolumeResult = samplePostRemote.getMostVolume(sample.getIdSample());
		
		monitoringReportDTO.setHighestPostsVolumeNetwork(mostVolumeResult[0].toString().toCharArray()[0]);
		monitoringReportDTO.setHighestPostsVolumePercentage(Integer.valueOf(String.valueOf(Long.valueOf(mostVolumeResult[1].toString())  * 100 / sample.getSample())));
		
		monitoringReportDTO.setTimeWithMorePosts(Integer.valueOf(sampleReportRemote.getTimeWithMorePosts(sample.getIdSample())[0].toString()));
		monitoringReportDTO.setTimeWithMorePostsPercentage(Integer.valueOf(sampleReportRemote.getTimeWithMorePosts(sample.getIdSample())[1].toString()) * 100 / sample.getSample());
		
		monitoringReportDTO.setWeekDayWithMorePosts(Integer.valueOf(sampleReportRemote.getWeekDayWithMorePosts(sample.getIdSample())[0].toString()));
		monitoringReportDTO.setWeekDayWithMorePostsPercentage(Integer.valueOf(sampleReportRemote.getWeekDayWithMorePosts(sample.getIdSample())[1].toString()) * 100 / sample.getSample());
		
		monitoringReportDTO.setPostsTotal(Long.valueOf(sample.getSample()));
		monitoringReportDTO.setDailyAverage(Long.valueOf(sample.getSample() / 7));
		
		monitoringReportDTO.setGarbagePostsTotal(0L);
		monitoringReportDTO.setGarbagePostsTotalPercentage(0);
		
		//Sentimental -----------------------------------------------------------------------------
		
		List<Object[]> objects = samplePostRemote.listPostsGroupByNetworks(sample.getIdSample());
		HashMap<Character, String> mappedPosts = new HashMap<Character,String>();
		
		for(Object[] obj : objects) {
			
			Character network = obj[0].toString().charAt(0); 
			
			if(mappedPosts.get(network) == null) {
				mappedPosts.put(network, "(");
			}
			
			if(network.equals(Profile.TWITTER) || network.equals(Profile.INSTAGRAM) || network.equals(Profile.NEWS) || network.equals(Profile.BLOGS)) {
				mappedPosts.put(obj[0].toString().charAt(0), mappedPosts.get(obj[0].toString().charAt(0)) + obj[1].toString() + ",");	
			} else {
				mappedPosts.put(obj[0].toString().charAt(0), mappedPosts.get(obj[0].toString().charAt(0)) + "'" + obj[1].toString() + "',");
			}
		}
		
		for(Character network : mappedPosts.keySet()) {
			if(mappedPosts.get(network).equals("(")) {
				mappedPosts.put(network, null);
			} else {
				mappedPosts.put(network, mappedPosts.get(network).substring(0,mappedPosts.get(network).lastIndexOf(",")) + ")");
			}
		}
		
		MaintenanceMonitoringReportRemote monitoringReportRemote = (MaintenanceMonitoringReportRemote) RecoverMaintenance.recoverMaintenance("MonitoringReport");
		
		monitoringReportDTO.setSentimentAnalysis(monitoringReportRemote.getSentimentAnalysisForSamples(sample.getMonitoringDTO().getIdMonitoring(), mappedPosts));
		Long qualifiedPostsTotal = 0L;
		Long positivePosts = 0L;
		for(Map.Entry<Character, Long> sentimentAnalysis : monitoringReportDTO.getSentimentAnalysis().entrySet()) {
			qualifiedPostsTotal = qualifiedPostsTotal + sentimentAnalysis.getValue();
			if(!sentimentAnalysis.getKey().equals('N')) {
				positivePosts = positivePosts + sentimentAnalysis.getValue();
			}
		}
		monitoringReportDTO.setQualifiedPostsTotal(qualifiedPostsTotal);
		monitoringReportDTO.setApprovalRatePerc(Calc.calcPercentageNormal(positivePosts.doubleValue(), qualifiedPostsTotal.doubleValue(), 0).intValue());
		
		List<MonitoringTermReportDTO> monitoringTermReports = new ArrayList<MonitoringTermReportDTO>();
		String[] tags = sample.getMonitoringDTO().getTerms().split(";");
		
		for(String tag : tags) {
			
			MonitoringTermReportDTO monitoringTermReport = new MonitoringTermReportDTO();
			monitoringTermReport.setTerm(tag);
			monitoringTermReport.setSentimentAnalysis(monitoringReportRemote.getSentimentAnalysisForSamples(sample.getMonitoringDTO().getIdMonitoring(), tag, mappedPosts));
			monitoringTermReport.setTotalPosts(monitoringReportRemote.getPostsTotalByTermForSamples(sample.getMonitoringDTO().getIdMonitoring(), tag, mappedPosts));
			
			Long qualifiedPostsByTerm = 0L;
			for(Map.Entry<Character, Long> sentimentAnalysis : monitoringTermReport.getSentimentAnalysis().entrySet()) {
				qualifiedPostsByTerm = qualifiedPostsByTerm + sentimentAnalysis.getValue();
			}
			
			Map<Character, Integer> sentimentAnalysisPerc = new HashMap<Character, Integer>();
			for(Map.Entry<Character, Long> sentimentAnalysis : monitoringTermReport.getSentimentAnalysis().entrySet()) {
				sentimentAnalysisPerc.put(sentimentAnalysis.getKey(), Calc.calcPercentageNormal(sentimentAnalysis.getValue().doubleValue(), qualifiedPostsByTerm.doubleValue(), 0).intValue());
			}
			
			monitoringTermReport.setSentimentAnalysisPerc(sentimentAnalysisPerc);
			
			monitoringTermReports.add(monitoringTermReport);
		}
		
		monitoringReportDTO.setMonitoringTermReports(monitoringTermReports);
		
		//----------------------------------------------------------
		
		//Ingluentials -----------------------------------------------------------------------------
		
		MaintenanceMonitoringInfluentialUserRemote monitoringInfluentialUserRemote = (MaintenanceMonitoringInfluentialUserRemote) 
				RecoverMaintenance.recoverMaintenance("MonitoringInfluentialUser");

		Map<String, Long> mostInfluentialUsers = monitoringInfluentialUserRemote.getMostInfluentialFans(sample.getMonitoringDTO().getIdMonitoring(), Profile.FACEBOOK, 
				sample.getStartDate().getTime() / 1000, sample.getEndDate().getTime() / 1000, 0, 10);
		
		List<MonitoringInfluentialUserDTO> monitoringInfluentialUsers = new ArrayList<MonitoringInfluentialUserDTO>();
		for(Map.Entry<String, Long> monitoringInfluentialUser : mostInfluentialUsers.entrySet()) {
			monitoringInfluentialUsers.add(MountDTO.mountMonitoringInfluentialUser(monitoringInfluentialUserRemote.getMostInfluentialFan(monitoringInfluentialUser.getKey(), 
					sample.getMonitoringDTO().getIdMonitoring(), Profile.FACEBOOK, sample.getStartDate().getTime() / 1000, sample.getEndDate().getTime() / 1000)));
		}
		
		monitoringReportDTO.setMonitoringInfluentialUsers(monitoringInfluentialUsers);
		
		//----------------------------------------------------------
		
		//Tags -----------------------------------------------------------------------------
		
		MaintenanceMonitoringPostTagRemote monitoringPostTagRemote = (MaintenanceMonitoringPostTagRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringPostTag");
		
		//TO DO 
		//List<MonitoringTagDTO> monitoringTags = sample.getMonitoringDTO().getMonitoringTags();
		List<MonitoringTagDTO> monitoringTags = new ArrayList<MonitoringTagDTO>();
		List<MonitoringTagReportDTO> monitoringTagReports = new ArrayList<MonitoringTagReportDTO>();
		for(MonitoringTagDTO monitoringTag : monitoringTags) {
			
			MonitoringTagReportDTO monitoringTagReport = new MonitoringTagReportDTO();
			monitoringTagReport.setMonitoringTag(monitoringTag);
			monitoringTagReport.setTaggedPostsPerNetwork(
					monitoringPostTagRemote.getTaggedPostsPerNetworkForSamples(sample.getMonitoringDTO().getIdMonitoring(), monitoringTag.getIdTag(), mappedPosts));
			
			Long taggedPosts = 0L;
			for(Map.Entry<Character, Long> taggerPostsPerNetwork : monitoringTagReport.getTaggedPostsPerNetwork().entrySet()) {
				taggedPosts = taggedPosts + taggerPostsPerNetwork.getValue();
			}
			
			monitoringTagReport.setTotalTaggedPosts(taggedPosts);
			
			monitoringTagReports.add(monitoringTagReport);
		}
		
		monitoringReportDTO.setMonitoringTagReports(monitoringTagReports);
		
		//Nuvem de tags ------------------------------------------------------------
		
		//----- System.out.println("Início nuvem: " + new java.util.Date());
        
		GenericDateInterval genericDateInterval = new GenericDateInterval(UtilFunctions.getMidnightDateNoDaylightTime(sample.getStartDate()), 
        											  UtilFunctions.getMidnightDateNoDaylightTime(sample.getEndDate()));
        
        monitoringReportDTO.setCloud(UtilMonitorings.mountCloud(sample.getMonitoringDTO(), genericDateInterval));
        
        return monitoringReportDTO;
	}
}
