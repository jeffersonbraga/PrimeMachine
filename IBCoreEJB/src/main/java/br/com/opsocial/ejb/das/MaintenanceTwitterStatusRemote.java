package br.com.opsocial.ejb.das;

import java.util.List;
import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.report.TwitterStatus;

@Remote
public interface MaintenanceTwitterStatusRemote extends AbstractDAS<TwitterStatus> {

	public List<TwitterStatus> getTwitterStatusByProfile(String idProfile, Integer pagingSince, Integer limit);

	public Map<Integer, Long> getTweetsPerHour(String idProfile, Long dateFrom, Long dateUntil);
	public Map<Integer, Long> getTweetsPerWeekDay(String idProfile, Long dateFrom, Long dateUntil);
	
	public Object[] getTimeWithMoreTweets(String idProfile, Long dateFrom, Long dateUntil);
	public Object[] getWeekDayWithMoreTweets(String idProfile, Long dateFrom, Long dateUntil);
	
	public List<TwitterStatus> getTwitterStatus(String idProfile, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	public List<TwitterStatus> getTwitterStatusMostEngaged(String idProfile, Long dateFrom, Long dateUntil, Integer offset, Integer limit);
	
	public long[] getTwitterStatusIds(String idProfile, Integer offset, Integer limit);
}
