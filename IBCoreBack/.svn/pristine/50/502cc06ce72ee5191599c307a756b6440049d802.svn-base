package br.com.opsocial.server.actions.reports.facebook;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.client.entity.report.facebook.FaceReportPostDTO;
import br.com.opsocial.client.entity.report.facebook.ReportFacebookPostToPostDTO;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.GenericDateInterval;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.reports.UtilReports;
import br.com.opsocial.ejb.das.MaintenanceFaceReportPostRemote;
import br.com.opsocial.ejb.das.MaintenanceFacebookPostInsightRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.report.FaceReportPost;

@RestController
@RequestMapping("woopsocial")
public class UpdatePostToPostMostEngagedAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/update_post_to_post_most_engaged",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {

		Profile profile = (Profile) getParameters().get("profile");

		Integer offset = ((Integer) getParameters().get("offset"));
		
		Long dateFrom = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("dateFrom")).getValue();
		Long dateUntil = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("dateUntil")).getValue();
		
		GenericDateInterval genericDateInterval = new GenericDateInterval(dateFrom, dateUntil);
		
		setParameters(new HashMap<String, Object>());

		MaintenanceFaceReportPostRemote faceReportPostRemote = (MaintenanceFaceReportPostRemote) 
				RecoverMaintenance.recoverMaintenance("FaceReportPost");

		MaintenanceFacebookPostInsightRemote facebookPostInsightRemote = (MaintenanceFacebookPostInsightRemote)			
				RecoverMaintenance.recoverMaintenance("FacebookPostInsight");

		List<FaceReportPostDTO> postsMostEngagedDTO = new ArrayList<FaceReportPostDTO>();
		List<FaceReportPost> postsMostEngaged = faceReportPostRemote.getPostsMostEngaged(profile.getNetworkId(), genericDateInterval.getDateFrom(), genericDateInterval.getDateUntil() + 86400, offset, ReportFacebookPostToPostDTO.POSTS_PER_CALL);
		
		for(FaceReportPost faceReportPost : postsMostEngaged) {

			FaceReportPostDTO faceReportPostDTO = MountDTO.mountFaceReportPost(faceReportPost); 
//			faceReportPostDTO.setPostInsights(UtilReports.checkPostInsights(facebookPostInsightRemote.getPostInsights(faceReportPostDTO.getPostId())));
//			faceReportPostDTO.setTotalReach(faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_IMPRESSIONS_PAID_UNIQUE)
//					+ faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_IMPRESSIONS_ORGANIC_UNIQUE)
//					+ faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_IMPRESSIONS_VIRAL_UNIQUE));
//			faceReportPostDTO.setTotalInteractions(faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_COMMENT)
//				+ faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_SHARE)
//				+ faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_CONSUMPTIONS)
//				+ faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_ANGER_TOTAL)
//				+ faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_HAHA_TOTAL)
//				+ faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_LOVE_TOTAL)
//				+ faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_SORRY_TOTAL)
//				+ faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_WOW_TOTAL));
//
//			if(faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_LIKE_TOTAL) > 0) {
//				faceReportPostDTO.setTotalInteractions(faceReportPostDTO.getTotalInteractions() + 
//						faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_REACTIONS_LIKE_TOTAL));
//			} else {
//				faceReportPostDTO.setTotalInteractions(faceReportPostDTO.getTotalInteractions() + 
//						faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_LIKE));
//				faceReportPostDTO.getPostInsights().put(FaceReportPostDTO.POST_REACTIONS_LIKE_TOTAL, 
//						faceReportPostDTO.getPostInsights().get(FaceReportPostDTO.POST_STORIES_BY_ACTION_TYPE_LIKE));
//			}

			postsMostEngagedDTO.add(faceReportPostDTO);
		}

		getParameters().put("postsMostEngaged", postsMostEngagedDTO);
		getParameters().put("offset", offset + ReportFacebookPostToPostDTO.POSTS_PER_CALL);
	}

}
