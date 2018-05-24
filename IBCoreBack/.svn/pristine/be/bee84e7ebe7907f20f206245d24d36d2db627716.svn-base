package br.com.opsocial.server.actions.monitorings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/*import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;*/

import br.com.opsocial.client.entity.application.MonitoringDTO;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.client.entity.blogs.BlogPostMonitoringDTO;
import br.com.opsocial.client.entity.primitive.Boolean;
import br.com.opsocial.client.entity.primitive.Long;
import br.com.opsocial.client.utils.Icons;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.client.entity.mount.MountDTO;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.ejb.das.MaintenanceBlogPostMonitoringRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringPostTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringTagRemote;
import br.com.opsocial.ejb.entity.blogs.BlogPostMonitoring;

@RestController
@RequestMapping("woopsocial")
public class MonitoringBlogAction extends ServerAction {
	
	@Override
	@RequestMapping(value = "/monitoring_blog",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception {
		
		MonitoringDTO monitoring = (MonitoringDTO) getParameters().get("monitoring");
		Long mostRecent = (Long) getParameters().get("mostRecent");
		Long elder = (Long) getParameters().get("elder");
		
		MaintenanceBlogPostMonitoringRemote remote = (MaintenanceBlogPostMonitoringRemote) 
				RecoverMaintenance.recoverMaintenance("BlogPostMonitoring");
		
		MaintenanceMonitoringPostTagRemote monitoringPostTagRemote = (MaintenanceMonitoringPostTagRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringPostTag");
		
		List<BlogPostMonitoring> blogPostMonitorings = new ArrayList<BlogPostMonitoring>();
		
		// Carregar tags ------------------------------------

		MaintenanceMonitoringTagRemote monitoringTagRemote = (MaintenanceMonitoringTagRemote) RecoverMaintenance.recoverMaintenance("MonitoringTag");
		
		monitoring.setMonitoringTags(MountDTO.mountMonitoringTag(
				monitoringTagRemote.listByMonitoring(monitoring.getIdMonitoring(), monitoring.getAccount().getIdAccount())));
		
		//------------------------------------------------------
		
		if(mostRecent != null) {

			if(mostRecent.getValue() != null) {
				blogPostMonitorings = remote.listMostRecents(mostRecent.getValue(), monitoring.getIdMonitoring());
				getParameters().put("requestType", new Character('N'));
			} else {
				blogPostMonitorings = remote.listMostRecents(monitoring.getIdMonitoring(), 10);	
			}
			
		} else if(elder != null) {
			blogPostMonitorings = remote.listElder(elder.getValue(), monitoring.getIdMonitoring(), 10);
			getParameters().put("requestType", new Character('O'));
			
		} else {
			blogPostMonitorings = remote.listMostRecents(monitoring.getIdMonitoring(), 10);			
		}
		
		List<BlogPostMonitoringDTO> posts = new ArrayList<BlogPostMonitoringDTO>();
	
		for(BlogPostMonitoring blogPostMonitoring : blogPostMonitorings) {
			
			BlogPostMonitoringDTO blogPostMonitoringDTO = MountDTO.mountBlogPostMonitoring(blogPostMonitoring);
			blogPostMonitoringDTO.setMonitoringPostTags(MountDTO.mountMonitoringPostTag(monitoringPostTagRemote.listByMonitoringPost(blogPostMonitoring.getBlogPost().getIdBlogPost().toString(), 
					monitoring.getIdMonitoring(), blogPostMonitoring.getTerm(), Profile.BLOGS)));
			
			blogPostMonitoringDTO.getBlogPostDTO().setPublisherPicture(Icons.NEWS_AVATAR);	
			
			posts.add(blogPostMonitoringDTO);
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
}
