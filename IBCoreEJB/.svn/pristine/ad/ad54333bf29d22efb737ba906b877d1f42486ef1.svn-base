package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ReclameAquiPostMonitoringDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.reclameaqui.ReclameAquiPostMonitoring;

@Stateless
public class MaintenanceReclameAquiPostMonitoringBean extends AbstractDASImpl<ReclameAquiPostMonitoring> implements MaintenanceReclameAquiPostMonitoringRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	ReclameAquiPostMonitoringDAO dao;  

	@Override
	public AbstractDAO<ReclameAquiPostMonitoring> getDAO() {
		return dao;
	}

	@Override
	public void setDao(AbstractDAO<ReclameAquiPostMonitoring> dao) {
		this.dao = (ReclameAquiPostMonitoringDAO) dao;
	}

	@Override
	public AbstractDAO<ReclameAquiPostMonitoring> getDao() {
		return dao;
	}

	@Override
	public List<ReclameAquiPostMonitoring> listMostRecents(Long idMonitoring,
			Integer maxResults) {
		return dao.listMostRecents(idMonitoring, maxResults);
	}

	@Override
	public List<ReclameAquiPostMonitoring> listMostRecents(Long createdTime,
			Long idMonitoring) {
		return dao.listMostRecents(createdTime, idMonitoring);
	}

	@Override
	public List<ReclameAquiPostMonitoring> listElder(Long createdTime,
			Long idMonitoring, Integer maxResults) {
		return dao.listElder(createdTime, idMonitoring, maxResults);
	}

	@Override
	public List<ReclameAquiPostMonitoring> getByInterval(Long idMonitoring,
			Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit) {
		return dao.getByInterval(idMonitoring, startDate, endDate, qualification, monitoringTags, monitoringTerms, monitoringWords, offset, limit);
	}

	@Override
	public Long getByIntervalCount(Long idMonitoring, Long startDate,
			Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords) {
		return dao.getByIntervalCount(idMonitoring, startDate, endDate, qualification, monitoringTags, monitoringTerms, monitoringWords);
	}

	@Override
	public ReclameAquiPostMonitoring getByComposedId(Long idMonitoring,
			String cacheId, String term) {
		return dao.getByComposedId(idMonitoring, cacheId, term);
	}

	@Override
	public List<ReclameAquiPostMonitoring> listByTerm(String term,
			Long idMonitoring) {
		return dao.listByTerm(term, idMonitoring);
	}

	@Override
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountOfTermPostsPerDay(idMonitoring, date);
	}
	
	@Override
	public Long getCountOfPostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountOfPostsPerDay(idMonitoring, date);
	}

	@Override
	public List<Object[]> getCountOfPostsPerHourDay(Long idMonitoring, Long date) {
		return dao.getCountOfPostsPerHourDay(idMonitoring, date);
	}
	
	@Override
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountQualificPostsPerDay(idMonitoring, date);
	}
	
	@Override
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountTagsPostsPerDay(idMonitoring, date);
	}

	@Override
	public ReclameAquiPostMonitoring getByComposedId(Long idMonitoring,
			String cacheId) {
		return dao.getByComposedId(idMonitoring, cacheId);
	}

	@Override
	public List<ReclameAquiPostMonitoring> listSample(Long idMonitoring,
			Long startDate, Long endDate, String qualification,
			List<Long> monitoringTags, Integer sample, List<String> notIn) {
		return dao.listSample(idMonitoring, startDate, endDate, qualification, monitoringTags, sample, notIn);
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
