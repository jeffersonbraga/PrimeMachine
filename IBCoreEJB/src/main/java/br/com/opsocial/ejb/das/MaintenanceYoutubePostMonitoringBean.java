package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.YoutubePostMonitoringDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.youtube.YoutubePostMonitoring;

@Stateless
public class MaintenanceYoutubePostMonitoringBean extends AbstractDASImpl<YoutubePostMonitoring> implements MaintenanceYoutubePostMonitoringRemote {

	private static final long serialVersionUID = 1L;
	@EJB
	YoutubePostMonitoringDAO dao;
	
	@Override
	public YoutubePostMonitoringDAO getDAO() {
		return dao;
	}

	@Override
	public Long getCountOfPosts(Long idMonitoring, Long dateFrom, Long dateUntil) {
		return dao.getCountOfPosts(idMonitoring, dateFrom, dateUntil);
	}

	@Override
	public void setDao(AbstractDAO<YoutubePostMonitoring> dao) {
		this.dao = (YoutubePostMonitoringDAO) dao;
	}

	@Override
	public AbstractDAO<YoutubePostMonitoring> getDao() {
		return dao;
	}

	@Override
	public List<YoutubePostMonitoring> listMostRecents(Long idMonitoring,
			Integer maxResults) {
		return dao.listMostRecents(idMonitoring, maxResults);
	}

	@Override
	public List<YoutubePostMonitoring> listMostRecents(Long publishedAt,
			Long idMonitoring) {
		return dao.listMostRecents(publishedAt, idMonitoring);
	}

	@Override
	public List<YoutubePostMonitoring> listElder(Long publishedAt,
			Long idMonitoring, Integer maxResults) {
		return dao.listElder(publishedAt, idMonitoring, maxResults);
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
	public List<YoutubePostMonitoring> getByInterval(Long idMonitoring, Long startDate, Long endDate, 
			String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer offset, Integer limit) {
		return dao.getByInterval(idMonitoring, startDate, endDate, qualification, monitoringTags, monitoringTerms, monitoringWords, offset, limit);
	}

	@Override
	public Long getByIntervalCount(Long idMonitoring, Long startDate,
			Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords) {
		return dao.getByIntervalCount(idMonitoring, startDate, endDate, qualification, monitoringTags, monitoringTerms, monitoringWords);
	}

	@Override
	public List<YoutubePostMonitoring> listPostsByActiveAccount(Date now,
			Long from, Long until) {
		return dao.listPostsByActiveAccount(now, from, until);
	}

	@Override
	public List<YoutubePostMonitoring> listByMonitoring(Long idMonitoring) {
		return dao.listByMonitoring(idMonitoring);
	}

	@Override
	public List<YoutubePostMonitoring> listByTerm(String term, Long idMonitoring) {
		return dao.listByTerm(term, idMonitoring);
	}

	@Override
	public YoutubePostMonitoring getByComposedId(Long idMonitoring,
			String videoId, String term) {
		return dao.getByComposedId(idMonitoring, videoId, term);
	}

	@Override
	public Long getCountOfGarbagePostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountOfGarbagePostsPerDay(idMonitoring, date);
	}

	@Override
	public YoutubePostMonitoring getByComposedId(Long idMonitoring,
			String videoId) {
		return dao.getByComposedId(idMonitoring, videoId);
	}

	@Override
	public List<YoutubePostMonitoring> listSample(Long idMonitoring,
			Long startDate, Long endDate, String qualification,
			List<Long> monitoringTags, Integer sample, List<String> notIn) {
		return dao.listSample(idMonitoring, startDate, endDate, qualification, monitoringTags, sample, notIn);
	}

	@Override
	public List<YoutubePostMonitoring> listSamePosts(Long idMonitoring,
			String description) {
		return dao.listSamePosts(idMonitoring, description);
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
