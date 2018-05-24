package br.com.opsocial.server.actions.reports.twitter;

import java.util.Calendar;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.client.entity.report.twitter.TwitterReportStatusDTO;
import br.com.opsocial.ejb.das.MaintenanceProfileRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterStatusRemote;
import br.com.opsocial.ejb.das.MaintenanceTwitterStatusReportRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.server.utils.GenericDateInterval;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.reports.ReportTwitter;

@RestController
@RequestMapping("woopsocial")
public class UpdateTwitterStatusAction {

	@CrossOrigin
	@RequestMapping(value = "/report_twitter_statuses",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> doActionNew(Long idProfile, Long dateFrom, Long dateUntil, Integer offset) throws Exception, OptimisticLockException {
		
		MaintenanceProfileRemote profileRemote = (MaintenanceProfileRemote) RecoverMaintenance.recoverMaintenance("Profile");
		
		List<TwitterReportStatusDTO> twitterReportStatus;
		
		ResponseEntity<Object> responseEntity;
		
		try {
			
			Profile profile = profileRemote.getById(idProfile);
			
			Calendar fromDate = Calendar.getInstance();
			fromDate.setTimeInMillis(dateFrom);
			
			Calendar untilDate = Calendar.getInstance();
			untilDate.setTimeInMillis(dateUntil);
			
			GenericDateInterval genericDateInterval = new GenericDateInterval(UtilFunctions.getDateForTwitterReport(fromDate), UtilFunctions.getDateForTwitterReport(untilDate));
			
			MaintenanceTwitterStatusRemote twitterStatusRemote = (MaintenanceTwitterStatusRemote)
					RecoverMaintenance.recoverMaintenance("TwitterStatus");
			
			MaintenanceTwitterStatusReportRemote twitterStatusReportRemote = (MaintenanceTwitterStatusReportRemote)
					RecoverMaintenance.recoverMaintenance("TwitterStatusReport");
			
			twitterReportStatus = MountDTO.mountTwitterReportStatus(
					twitterStatusRemote.getTwitterStatus(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400, offset, 50), 
						profile);
			
			for(TwitterReportStatusDTO twitterReportStatusDTO : twitterReportStatus) {
				
				twitterReportStatusDTO.setRepliesCount(twitterStatusReportRemote.getEntityValueByProperty(
						twitterReportStatusDTO.getIdProfile(), twitterReportStatusDTO.getIdStatus(), "repliesOfMeCountAll"));
				
				twitterReportStatusDTO.setRetweetsCount(twitterStatusReportRemote.getEntityValueByProperty(
						twitterReportStatusDTO.getIdProfile(), twitterReportStatusDTO.getIdStatus(), "retweetsOfMeCountAll"));
				
				twitterReportStatusDTO.setFavoriteCount(twitterStatusReportRemote.getEntityValueByProperty(
						twitterReportStatusDTO.getIdProfile(), twitterReportStatusDTO.getIdStatus(), ReportTwitter.FAVORITE_COUNT_ALL));
			}
			
			responseEntity = new ResponseEntity<Object>(twitterReportStatus, HttpStatus.OK);
					
		} catch (Exception e) {
			e.printStackTrace();
			responseEntity = new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return responseEntity;
		
	}

}
