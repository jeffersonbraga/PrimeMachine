package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.instagram.InstagramPostLike;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;

@Remote 
public interface MaintenanceInstagramPostLikeRemote extends AbstractDAS<InstagramPostLike> {

	public Map<Integer, Long> getLikesPerMonthYear(String idInstagramUser, Integer year);
	public List<InstagramReport> getLikesPerDay(String idInstagramUser, Long dateFrom, Long dateUntil);
	public Long getCountOfLikes(String idInstagramUser, Long dateFrom, Long dateUntil);
	public Object[] getLikesOrigin(String idInstagramUser);
	public Object[] getLikesOrigin(String idInstagramUser, Long dateFrom, Long dateUntil);
	
	public Map<Integer, Long> getLikesPerHour(String idInstagramUser);
	public Map<Integer, Long> getLikesPerHour(String idInstagramUser, Long dateFrom, Long dateUntil);
	
	public Object[] getTimeWithMoreLikes(String idInstagramUser, Long dateFrom, Long dateUntil);
	public Object[] getTimeWithMoreLikes(String idInstagramUser);
	
	public Object[] getTimeWithLessLikes(String idInstagramUser, Long dateFrom, Long dateUntil);
	public Object[] getTimeWithLessLikes(String idInstagramUser);
	public List<InstagramSimpleDataDTO> getLikesPerMonthYearSimpleData(String valueOf, int i);
	public List<InstagramSimpleDataDTO> getLikesPerHourSimpleData(String valueOf, Long dateFrom, Long dateUntil);
}
