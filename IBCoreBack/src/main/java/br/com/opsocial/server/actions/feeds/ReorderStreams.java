package br.com.opsocial.server.actions.feeds;

import java.util.ArrayList;
import java.util.List;

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
import br.com.opsocial.ejb.entity.feed.Stream;
import br.com.opsocial.server.utils.RecoverMaintenance;

@RestController
@RequestMapping("woopsocial")
public class ReorderStreams {

	MaintenanceStreamRemote streamRemote;
	MaintenanceTabRemote tabRemote;
	
	@CrossOrigin
	@RequestMapping(value = "/reorder_streams",
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Stream>> doAction(@Valid @RequestBody List<Stream> streams) throws Exception {

		streamRemote = (MaintenanceStreamRemote) RecoverMaintenance.recoverMaintenance("Stream");
		
		Long order = 0L;
		ArrayList<Stream> streamsList = new ArrayList<Stream>();
		
		for(Stream stream: streams){
			Stream streamDb = streamRemote.getById(stream.getIdStream());
			
			order++;
			
			streamDb.setStreamOrder(order);
			
			streamsList.add(streamRemote.save(streamDb));
		}
				
		return new ResponseEntity<>(streamsList, HttpStatus.OK);
	}
}
