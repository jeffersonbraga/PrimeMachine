package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.InstagramReportDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.instagram.InstagramCommentCompareDTO;
import br.com.opsocial.ejb.entity.instagram.InstagramFollowerTrend;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;

@Stateless
public class MaintenanceInstagramReportBean extends AbstractDASImpl<InstagramReport> implements MaintenanceInstagramReportRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	InstagramReportDAO dao;
	
	@Override
	public void setDao(AbstractDAO<InstagramReport> dao) {
		this.dao = (InstagramReportDAO) dao;
	}

	@Override
	public AbstractDAO<InstagramReport> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<InstagramReport> getDAO() {
		return dao;
	}

	@Override
	public InstagramReport getEntity(String idInstagramUser, String property, Long date) {
		return dao.getEntity(idInstagramUser, property, date);
	}
	
	@Override
	public InstagramReport getEntity(String idInstagramUser, String property, String label) {
		return dao.getEntity(idInstagramUser, property, label);
	}

	@Override
	public InstagramReport getEntityBeforeDate(String idInstagramUser, String property, Long date) {
		return dao.getEntityBeforeDate(idInstagramUser, property, date);
	}

	@Override
	public List<InstagramFollowerTrend> getInstagramFollowersTrends(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getInstagramFollowersTrends(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public InstagramFollowerTrend getInstagramFollowersTrend(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getInstagramFollowersTrend(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramReport> getAudiencesFollowersTrend(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getAudiencesFollowersTrend(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramReport> getAudiencesDemograph(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getAudiencesDemograph(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramReport> getAudiencesTopCountry(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getAudiencesTopCountry(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramReport> getAudiencesTopCity(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getAudiencesTopCity(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramReport> getAudiencesOnlineFollowersByHour(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getAudiencesOnlineFollowersByHour(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramReport> getAudiencesOnlineFollowersByDay(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getAudiencesOnlineFollowersByDay(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramReport> getAudiencesPostsInteraction(String idInstagramUser, Long dateFrom, Long dateUntil) {
		return dao.getAudiencesPostsInteraction(idInstagramUser, dateFrom, dateUntil);
	}

	@Override
	public List<InstagramCommentCompareDTO> getCommentsPerDay(String networkId, Long dateFromVerified, Long dateUntil) {
		return dao.getCommentsPerDay(networkId, dateFromVerified, dateUntil);
	}

	@Override
	public List<InstagramReport> getLikesPerDayFollowers(String networkId, Long dateFromVerified, Long dateUntil) {
		return dao.getLikesPerDayFollowers(networkId, dateFromVerified, dateUntil);
	}

	@Override
	public List<InstagramReport> getLikesPerDayNoFollowers(String networkId, Long dateFromVerified, Long dateUntil) {
		return dao.getLikesPerDayNoFollowers(networkId, dateFromVerified, dateUntil);
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

	@Override
	public InstagramSimpleDataDTO getAudiencesAveragePublicAge(String networkId, Long dateFromVerified,
			Long dateUntil) {
		return dao.getAudiencesAveragePublicAge(networkId, dateFromVerified, dateUntil);
	}

	@Override
	public List<InstagramSimpleDataDTO> getAudiencesAveragePublicGender(String networkId, Long dateFromVerified,
			Long dateUntil) {
		return dao.getAudiencesAveragePublicGender(networkId, dateFromVerified, dateUntil);
	}

	@Override
	public Long getPropertySum(String idInstagramUser, String property, Long dateFrom, Long dateUntil) {
		return dao.getPropertySum(idInstagramUser, property, dateFrom, dateUntil); 
	}
}