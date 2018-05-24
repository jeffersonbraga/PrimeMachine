package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.instagram.InstagramPostComment;
import br.com.opsocial.ejb.entity.instagram.InstagramReport;
import br.com.opsocial.ejb.entity.instagram.InstagramSimpleDataDTO;

@Remote
public interface MaintenanceInstagramPostCommentRemote extends AbstractDAS<InstagramPostComment> {

	public Map<Integer, Long> getCommentsPerMonthYear(String idInstagramUser, Integer year);
	List<InstagramReport> getCommentsPerDay(String idInstagramUser, Long dateFrom, Long dateUntil);
	public Long getCountOfComments(String idInstagramUser, Long dateFrom, Long dateUntil);
	public Object[] getCommentsOrigin(String idInstagramUser);
	public Object[] getCommentsOrigin(String idInstagramUser, Long dateFrom, Long dateUntil);
	
	public Map<Integer, Long> getCommentsPerHour(String idInstagramUser, Long dateFrom, Long dateUntil);
	public Map<Integer, Long> getCommentsPerHour(String idInstagramUser);
	
	public Object[] getTimeWithMoreComments(String idInstagramUser, Long dateFrom, Long dateUntil);
	public Object[] getTimeWithMoreComments(String idInstagramUser);
	
	public Object[] getTimeWithLessComments(String idInstagramUser, Long dateFrom, Long dateUntil);
	public Object[] getTimeWithLessComments(String idInstagramUser);
	public List<InstagramSimpleDataDTO> getCommentsPerMonthYearSimpleData(String valueOf, int i);
	public List<InstagramSimpleDataDTO> getCommentsPerHourSimpleData(String valueOf, Long dateFrom, Long dateUntil);	
}
