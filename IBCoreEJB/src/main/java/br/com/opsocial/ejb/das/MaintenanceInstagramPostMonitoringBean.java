package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.InstagramPostMonitoringDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramPostMonitoring;

@Stateless
public class MaintenanceInstagramPostMonitoringBean extends AbstractDASImpl<InstagramPostMonitoring> implements MaintenanceInstagramPostMonitoringRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	InstagramPostMonitoringDAO dao;  

	@Override
	public AbstractDAO<InstagramPostMonitoring> getDAO() {
		return dao;
	}
	
	@Override
	public void setDao(AbstractDAO<InstagramPostMonitoring> dao) {
		this.dao = (InstagramPostMonitoringDAO) dao;
	}

	@Override
	public AbstractDAO<InstagramPostMonitoring> getDao() {
		return dao;
	}

	@Override
	public List<InstagramPostMonitoring> listMostRecents(Long idMonitoring,
			Integer maxResults) {
		return dao.listMostRecents(idMonitoring, maxResults);
	}

	@Override
	public List<InstagramPostMonitoring> listMostRecents(Long createTime,
			Long idMonitoring) {
		return dao.listMostRecents(createTime, idMonitoring);
	}

	@Override
	public List<InstagramPostMonitoring> listElder(Long createTime,
			Long idMonitoring, Integer maxResults) {
		return dao.listElder(createTime, idMonitoring, maxResults);
	}

	@Override
	public List<InstagramPostMonitoring> getByInterval(Long idMonitoring, Long startDate, Long endDate, 
			String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit) {
		return dao.getByInterval(idMonitoring, startDate, endDate, qualification, monitoringTags, monitoringTerms, monitoringWords, offset, limit);
	}

	@Override
	public Long getByIntervalCount(Long idMonitoring, Long startDate,
			Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords) {
		return dao.getByIntervalCount(idMonitoring, startDate, endDate, qualification, monitoringTags, monitoringTerms, monitoringWords);
	}

	@Override
	public List<InstagramPostMonitoring> listPostsByActiveAccount(Date now,
			Long from, Long until) {
		return dao.listPostsByActiveAccount(now, from, until);
	}

	@Override
	public List<InstagramPostMonitoring> listByMonitoring(Long idMonitoring) {
		return dao.listByMonitoring(idMonitoring);
	}

	@Override
	public List<InstagramPostMonitoring> listByTerm(String term,
			Long idMonitoring) {
		return dao.listByTerm(term, idMonitoring);
	}

	@Override
	public InstagramPostMonitoring getByComposedId(Long idMonitoring,
			Long idMedia, String term) {
		return dao.getByComposedId(idMonitoring, idMedia, term);
	}
	
	@Override
	public List<Object[]> getCountOfTermPostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountOfTermPostsPerDay(idMonitoring, date);
	}

	@Override
	public Long getCountOfGarbagePostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountOfGarbagePostsPerDay(idMonitoring, date);
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
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountTagsPostsPerDay(idMonitoring, date);
	}

	@Override
	public InstagramPostMonitoring getByComposedId(Long idMonitoring,
			Long idMedia) {
		return dao.getByComposedId(idMonitoring, idMedia);
	}
	
	@Override
	public List<Object[]> getCountQualificPostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountQualificPostsPerDay(idMonitoring, date);
	}
	
	@Override
	public List<InstagramPostMonitoring> listSample(Long idMonitoring,
			Long startDate, Long endDate, String qualification,
			List<Long> monitoringTags, Integer sample, List<String> notIn) {
		return dao.listSample(idMonitoring, startDate, endDate, qualification, monitoringTags, sample, notIn);
	}

	@Override
	public List<InstagramPostMonitoring> getInstagramPostsByUser(Long idMonitoring, Long userId, Long dateFrom, Long dateUntil) {
		return dao.getInstagramPostsByUser(idMonitoring, userId, dateFrom, dateUntil);
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
