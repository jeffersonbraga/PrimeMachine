package br.com.opsocial.server.actions.monitorings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.entity.comments.CommentData;
import org.jinstagram.entity.comments.MediaCommentsFeed;
import org.jinstagram.entity.media.MediaInfoFeed;
import org.jinstagram.exceptions.InstagramException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.client.entity.application.MonitoringDTO;
import br.com.opsocial.client.entity.instagram.InstagramCommentDTO;
import br.com.opsocial.client.entity.instagram.InstagramPostMonitoringDTO;
import br.com.opsocial.client.entity.primitive.Boolean;
import br.com.opsocial.client.entity.primitive.Long;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.UtilFunctions;
import br.com.opsocial.server.utils.networksintegrations.InstagramIntegration;
import br.com.opsocial.ejb.das.MaintenanceInstagramPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.instagram.InstagramPost;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;

@RestController
@RequestMapping("woopsocial")
public class MonitoringInstagramAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/monitoring_instagram",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception {
		
		MonitoringDTO monitoring = (MonitoringDTO) getParameters().get("monitoring");
		Long mostRecent = (Long) getParameters().get("mostRecent");
		Long elder = (Long) getParameters().get("elder");
		
		MaintenanceInstagramPostMonitoringRemote remote = (MaintenanceInstagramPostMonitoringRemote) 
				RecoverMaintenance.recoverMaintenance("InstagramPostMonitoring");
		
		MaintenanceMonitoringPostTagRemote monitoringPostTagRemote = (MaintenanceMonitoringPostTagRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringPostTag");
		
		List<InstagramPostMonitoring> instagramPostMonitorings = new ArrayList<InstagramPostMonitoring>();
		
		// Carregar tags ------------------------------------

		MaintenanceMonitoringTagRemote monitoringTagRemote = (MaintenanceMonitoringTagRemote) RecoverMaintenance.recoverMaintenance("MonitoringTag");
		
		monitoring.setMonitoringTags(MountDTO.mountMonitoringTag(
				monitoringTagRemote.listByMonitoring(monitoring.getIdMonitoring(), monitoring.getAccount().getIdAccount())));
		
		//------------------------------------------------------
		
		if(mostRecent != null) {

			if(mostRecent.getValue() != null) {
				instagramPostMonitorings = remote.listMostRecents(mostRecent.getValue(), monitoring.getIdMonitoring());
				getParameters().put("requestType", new Character('N'));
			} else {
				instagramPostMonitorings = remote.listMostRecents(monitoring.getIdMonitoring(), 30);	
			}
			
		} else if(elder != null) {
			instagramPostMonitorings = remote.listElder(elder.getValue(), monitoring.getIdMonitoring(), 30);
			getParameters().put("requestType", new Character('O'));
			
		} else {
			instagramPostMonitorings = remote.listMostRecents(monitoring.getIdMonitoring(), 30);			
		}
		
		List<InstagramPostMonitoringDTO> posts = new ArrayList<InstagramPostMonitoringDTO>();
	
		for(InstagramPostMonitoring instagramPostMonitoring : instagramPostMonitorings) {
			
			InstagramPostMonitoringDTO instagramPostMonitoringDTO = MountDTO.mountInstagramPostMonitoring(instagramPostMonitoring);
			
			instagramPostMonitoringDTO.getInstagramPostDTO().setPictureCaption(
					UtilFunctions.getFomattedStringWithWBR(instagramPostMonitoringDTO.getInstagramPostDTO().getPictureCaption(), 32));
			
			instagramPostMonitoringDTO.getInstagramPostDTO().setComments(recoverComments(instagramPostMonitoringDTO.getInstagramPostDTO().getIdMedia()));
			
			instagramPostMonitoringDTO.setMonitoringPostTags(
					MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(instagramPostMonitoring.getInstagramPost().getIdMedia().toString(), 
						monitoring.getIdMonitoring(), instagramPostMonitoring.getTerm(), Profile.INSTAGRAM)));
			
			posts.add(instagramPostMonitoringDTO);
		}

		Date now = new Date();
		
		Date createTime = new Date(monitoring.getCreatedAt() * 1000);
		createTime.setMinutes(createTime.getMinutes() + 5);
		
		if(now.after(createTime) && posts.isEmpty() && (mostRecent == null || mostRecent.getValue() == null)) {
			getParameters().put("no_results", new Boolean(true));
		} else {
			getParameters().put("posts", posts);
		}
		getParameters().put("monitoring", monitoring);
	}
	
	private List<InstagramCommentDTO> recoverComments(long idMedia) throws InstagramException {
		
		try {
			Instagram instagram = new Instagram(InstagramIntegration.CLIENT_ID);

			MediaCommentsFeed mediaCommentsFeed = instagram.getMediaComments(String.valueOf(idMedia));

			List<InstagramCommentDTO> comments = new ArrayList<InstagramCommentDTO>();
			
			if(mediaCommentsFeed.getCommentDataList() != null) {
				for(CommentData commentData : mediaCommentsFeed.getCommentDataList()) {
						
					InstagramCommentDTO comment = new InstagramCommentDTO();
					
					comment.setComment(commentData.getText());
					comment.setUserName(commentData.getCommentFrom().getUsername());
					comment.setProfilePicture(commentData.getCommentFrom().getProfilePicture());
					comment.setExternalId(java.lang.Long.valueOf(commentData.getId()));
				
					comments.add(comment);
				}
			}
			
			return comments;
			
		} catch (Exception e) {
			e.printStackTrace();
			
			return null;
		}
	}
	
	private InstagramPost updateInstagramData(InstagramPost post) {
		
		Instagram instagram = new Instagram(InstagramIntegration.CLIENT_ID);
		
		try {
			MediaInfoFeed feed = instagram.getMediaInfo(String.valueOf(post.getIdMedia()));
			
			if(feed.getData() != null) {
				post.setCountLikes(feed.getData().getLikes().getCount());
			}
		} catch (InstagramException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return post;
	}
}
