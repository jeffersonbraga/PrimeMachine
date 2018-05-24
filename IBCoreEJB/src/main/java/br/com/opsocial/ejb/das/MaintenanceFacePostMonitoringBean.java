package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.FacePostMonitoringDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.facebook.FacePostMonitoring;

@Stateless
public class MaintenanceFacePostMonitoringBean extends AbstractDASImpl<FacePostMonitoring> implements MaintenanceFacePostMonitoringRemote {
 
	private static final long serialVersionUID = 1L;

	@EJB
	FacePostMonitoringDAO dao;

	@Override
	public FacePostMonitoringDAO getDAO() {
		return dao;
	}
	
	@Override
	public void setDao(AbstractDAO<FacePostMonitoring> dao) {
		this.dao = (FacePostMonitoringDAO) dao;
	}

	@Override
	public AbstractDAO<FacePostMonitoring> getDao() {
		return dao;
	}

	@Override
	public List<FacePostMonitoring> listMostRecents(Long idMonitoring, Integer maxResults) {
		return dao.listMostRecents(idMonitoring, maxResults);
	}

	@Override
	public List<FacePostMonitoring> listMostRecents(Long createdTime, Long idMonitoring) {
		return dao.listMostRecents(createdTime, idMonitoring);
	}

	@Override
	public List<FacePostMonitoring> listElder(Long createdTime, Long idMonitoring, Integer maxResults) {
		return dao.listElder(createdTime, idMonitoring, maxResults);
	}

	@Override
	public Long getCountOfPosts(Long idMonitoring, Long dateFrom, Long dateUntil) {
		return dao.getCountOfPosts(idMonitoring, dateFrom, dateUntil);
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
	public Long getCountOfGarbagePostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountOfGarbagePostsPerDay(idMonitoring, date);
	}

	@Override
	public List<FacePostMonitoring> getByInterval(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags, List<String> monitoringTerms, List<String> monitoringWords, Integer limit) {
		return dao.getByInterval(idMonitoring, startDate, endDate, qualification, monitoringTags, monitoringTerms, monitoringWords, limit);
	}

	@Override
	public Long getByIntervalCount(Long idMonitoring, Long startDate, Long endDate, String qualification, List<Long> monitoringTags) {
		return dao.getByIntervalCount(idMonitoring, startDate, endDate, qualification, monitoringTags);
	}

	@Override
	public List<FacePostMonitoring> listPostsByActiveAccount(Date now, Long from, Long until) {
		return dao.listPostsByActiveAccount(now, from, until);
	}

	@Override
	public List<FacePostMonitoring> listByMonitoring(Long idMonitoring) {
		return dao.listByMonitoring(idMonitoring);
	}

	@Override
	public List<FacePostMonitoring> listByTerm(String term, Long idMonitoring) {
		return dao.listByTerm(term, idMonitoring);
	}

	@Override
	public FacePostMonitoring getByComposedId(Long idMonitoring, String postId, String term) {
		return dao.getByComposedId(idMonitoring, postId, term);
	}

	@Override
	public Map<String, Long> getCountOfPostsPerUser(Long idMonitoring, Long date) {
		return dao.getCountOfPostsPerUser(idMonitoring, date);
	}

	@Override
	public FacePostMonitoring getByComposedId(Long idMonitoring, String postId) {
		return dao.getByComposedId(idMonitoring, postId);
	}

	@Override
	public List<FacePostMonitoring> listSample(Long idMonitoring,
			Long startDate, Long endDate, String qualification,
			List<Long> monitoringTags, Integer sample, List<String> notIn) {
		return dao.listSample(idMonitoring, startDate, endDate, qualification, monitoringTags, sample, notIn);
	}

	@Override
	public List<FacePostMonitoring> listByObjectId(Long idMonitoring,
			String objectId) {
		return dao.listByObjectId(idMonitoring, objectId);
	}	

	@Override
	public List<String[]> getWordsFromPostsContent(Long idMonitoring,
			Long startDate, Long endDate, Character qualification) {
		return dao.getWordsFromPostsContent(idMonitoring, startDate, endDate, qualification);
	}

	@Override
	public List<Object[]> getCountTagsPostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountTagsPostsPerDay(idMonitoring, date);
	}

	@Override
	public List<Object[]> getCountGroupsPostsPerDay(Long idMonitoring, Long date) {
		return dao.getCountGroupsPostsPerDay(idMonitoring, date);
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
