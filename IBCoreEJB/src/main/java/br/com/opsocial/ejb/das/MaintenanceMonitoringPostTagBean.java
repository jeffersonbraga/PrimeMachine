package br.com.opsocial.ejb.das;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringPostTagDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringPostTag;

@Stateless
public class MaintenanceMonitoringPostTagBean extends AbstractDASImpl<MonitoringPostTag> implements MaintenanceMonitoringPostTagRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	MonitoringPostTagDAO dao;

	@Override
	public void setDao(AbstractDAO<MonitoringPostTag> dao) {
		this.dao = (MonitoringPostTagDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringPostTag> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<MonitoringPostTag> getDAO() {
		return dao;
	}

	@Override
	public List<MonitoringPostTag> listByMonitoringPost(String postId,
			Long idMonitoring, String term, Character network) {
		return dao.listByMonitoringPost(postId, idMonitoring, term, network);
	}

	@Override
	public boolean hasMonitoringTag(Long idTag, String postId, Long idMonitoring, String term, Character network) {
		return dao.hasMonitoringTag(idTag, postId, idMonitoring, term, network);
	}
	
	@Override
	public boolean thereIsMonitoringTag(String postId, Long idMonitoring, String term, Character network, List<Long> monitoringTags) {
		return dao.thereIsMonitoringTag(postId, idMonitoring, term, network, monitoringTags);
	}

	@Override
	public Map<Character, Long> getTaggedPostsPerNetworkForSamples(
			Long idMonitoring, Long idTag,
			HashMap<Character, String> mappedPosts) {
		return dao.getTaggedPostsPerNetworkForSamples(idMonitoring, idTag, mappedPosts);
	}

	@Override
	public void setIdUserSession(Long idUserSession) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getIdUserSession() {
		// TODO Auto-generated method stub
		return null;
	}

}
