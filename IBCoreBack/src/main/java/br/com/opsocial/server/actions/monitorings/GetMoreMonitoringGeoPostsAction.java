package br.com.opsocial.server.actions.monitorings;

import java.util.HashMap;

import javax.persistence.OptimisticLockException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.opsocial.ejb.das.MaintenanceMonitoringPostLocationRemote;

import br.com.opsocial.client.entity.monitoring.MonitoringReportDTO;
import br.com.opsocial.client.entity.primitive.StringUtil;
import br.com.opsocial.server.services.ServerAction;
import br.com.opsocial.server.utils.RecoverMaintenance;
import br.com.opsocial.server.utils.monitorings.UtilMonitorings;

@RestController
@RequestMapping("woopsocial")
public class GetMoreMonitoringGeoPostsAction extends ServerAction {

	@Override
	@RequestMapping(value = "/get_more_monitoring_geoposts",
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_VALUE)
	public void doAction() throws Exception, OptimisticLockException {
		
		Long idMonitoring = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("idMonitoring")).getValue();
		Integer offset = ((br.com.opsocial.client.entity.primitive.Integer) getParameters().get("offset")).getValue();
		Long dateFrom = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("dateFrom")).getValue();
		Long dateUntil = ((br.com.opsocial.client.entity.primitive.Long) getParameters().get("dateUntil")).getValue();
		
		setParameters(new HashMap<String, Object>());
		
		MaintenanceMonitoringPostLocationRemote monitoringPostLocationRemote = (MaintenanceMonitoringPostLocationRemote)
				RecoverMaintenance.recoverMaintenance("MonitoringPostLocation");
		
		String monitoringGeoPostsJSON = UtilMonitorings.getMonitoringGeoPostsJSON(
				monitoringPostLocationRemote.getGetPosts(idMonitoring, dateFrom, dateUntil, offset, MonitoringReportDTO.GEO_POSTS_LIMIT));
		
		getParameters().put("monitoringGeoPostsJSON", new StringUtil(monitoringGeoPostsJSON));
	}

}
