package br.com.opsocial.server.actions.feeds;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceStreamRemote;
import br.com.opsocial.ejb.das.MaintenanceTabRemote;
import br.com.opsocial.ejb.das.MaintenanceUserRemote;
import br.com.opsocial.ejb.entity.application.Profile;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.feed.Stream;
import br.com.opsocial.ejb.entity.feed.Tab;
import br.com.opsocial.server.security.SecurityUtils;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class AddStreamByProfiles {

	MaintenanceStreamRemote streamRemote;
	MaintenanceTabRemote tabRemote;
	
	@CrossOrigin
	@RequestMapping(value = "/add_streams_by_profile",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Stream>> doAction(@Valid @RequestBody List<Profile> profiles, HttpSession session, HttpServletRequest request) throws Exception {

		streamRemote = (MaintenanceStreamRemote) RecoverMaintenance.recoverMaintenance("Stream");
		tabRemote = (MaintenanceTabRemote) RecoverMaintenance.recoverMaintenance("Tab");
		
		User user = SecurityUtils.getCurrentUser();
		
		ArrayList<Stream> streams = new ArrayList<Stream>();
		Long order = null;
		
		for(Profile profile: profiles){
			Tab tab = this.getInitialTab(user.getIdUser());
			Stream stream = new Stream();
			
			stream.setProfile(profile);
			
			if(order == null){
				order = this.getLastOrder(tab.getIdTab());
			}
			order++;
			
			stream.setStreamOrder(order);
			stream.setTab(tab);
			
			streams.add(streamRemote.save(stream));
		}
				
		return new ResponseEntity<>(streams, HttpStatus.OK);
	}
	
	private Tab getInitialTab(Long idUser) {
		Tab tab = tabRemote.getInitialTab(idUser);
		
		return tab;
	}

	private Long getLastOrder(Long idTab){
		
		Long order = streamRemote.getMaxOrderByTab(idTab);
		
		if(order != null){
			return order;
		}else{
			return 0L;
		}
	}
}
