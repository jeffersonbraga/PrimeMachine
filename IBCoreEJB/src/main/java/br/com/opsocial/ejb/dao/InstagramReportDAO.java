package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.instagram.InstagramCommentCompareDTO;
import br.com.opsocial.ejb.entity.instagram.InstagramFollowerTrend;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;

@Local
public interface InstagramReportDAO extends AbstractDAO<InstagramReport> {

	public InstagramReport getEntity(String idInstagramUser, String property, Long date);
	public InstagramReport getEntity(String idInstagramUser, String property, String label);
	
	public Long getPropertySum(String idInstagramUser, String property, Long dateFrom, Long dateUntil);
	
	public InstagramReport getEntityBeforeDate(String idInstagramUser, String property, Long date);

	public List<InstagramFollowerTrend> getInstagramFollowersTrends(String idInstagramUser, Long dateFrom, Long dateUntil);
	public InstagramFollowerTrend getInstagramFollowersTrend(String idInstagramUser, Long dateFrom, Long dateUntil);

	public List<InstagramReport> getAudiencesFollowersTrend(String networkId, Long dateFromVerified, Long dateUntil);
	public List<InstagramReport> getAudiencesDemograph(String networkId, Long dateFromVerified, Long dateUntil);
	public List<InstagramReport> getAudiencesTopCountry(String networkId, Long dateFromVerified, Long dateUntil);
	public List<InstagramReport> getAudiencesTopCity(String networkId, Long dateFromVerified, Long dateUntil);
	public List<InstagramReport> getAudiencesOnlineFollowersByHour(String networkId, Long dateFromVerified, Long dateUntil);
	public List<InstagramReport> getAudiencesOnlineFollowersByDay(String networkId, Long dateFromVerified, Long dateUntil);
	public List<InstagramReport> getAudiencesPostsInteraction(String networkId, Long dateFromVerified, Long dateUntil);	

	public List<InstagramReport> getLikesPerDayFollowers(String networkId, Long dateFromVerified, Long dateUntil);
	public List<InstagramReport> getLikesPerDayNoFollowers(String networkId, Long dateFromVerified, Long dateUntil);	

	public List<InstagramCommentCompareDTO> getCommentsPerDay(String networkId, Long dateFromVerified, Long dateUntil);
	
	public InstagramSimpleDataDTO getAudiencesAveragePublicAge(String networkId, Long dateFromVerified, Long dateUntil);
	public List<InstagramSimpleDataDTO> getAudiencesAveragePublicGender(String networkId, Long dateFromVerified, Long dateUntil);	
}
