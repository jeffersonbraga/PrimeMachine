package br.com.opsocial.ejb.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReport;

@Local
public interface MonitoringReportDAO extends AbstractDAO<MonitoringReport> {
	
	public boolean hasEntity(Character networkType, Long date, String periodType, String period, Long idMonitoring);
	
	public MonitoringReport getEntity(Character networkType, Long date, String periodType, String period, Long idMonitoring);
	
	public boolean hasEntity(Character networkType, Long date, Character garbage, String periodType, Long idMonitoring);
	
	public MonitoringReport getEntity(Character networkType, Long date, Character garbage, String periodType, Long idMonitoring);
	
	public Map<Character, Long> getPostsPerDaysNetworks(Long idMonitoring, Long dateFrom, Long dateUntil);
	
	public Map<Character, Long> getFacebookPostsInteractions(Long idMonitoring, Long dateFrom, Long dateUntil);

	public Map<Integer, Map<Character, Long>> getPostsPerHoursNetworks(Long idMonitoring, Long dateFrom, Long dateUntil);
	
	public Map<Character, Long> getPostsPerHoursNetworks(Long idMonitoring, Long dateFrom, Long dateUntil, Integer hourFrom, Integer hourUntil);

	public Map<Integer, Map<Character, Long>> getPostsPerWeekDaysNetworks(Long idMonitoring, Long dateFrom, Long dateUntil); 
	
	public Long getPostsTotal(Long idMonitoring, Long dateFrom, Long dateUntil);
	
	public Long getGarbagePostsTotal(Long idMonitoring, Long dateFrom, Long dateUntil);
	
	public Long getReportInitialDate(Long idMonitoring);
	
	public List<Long> getDatesGeneratedReports(Long idMonitoring);
	
	public Long getPostsTotalByTerm(Long idMonitoring, String term, Long dateFrom, Long dateUntil);
	
	// New Reports
	public Map<Date, Long> getPostsPerDay(Long idMonitoring, Long dateFrom, Long dateUntil);
	
	public Map<Date, Map<Character, Long>> getPostsPerDayNetworks(Long idMonitoring, Long dateFrom, Long dateUntil);

	public Map<Character, Long> getSentimentAnalysisForSamples(Long idMonitoring, HashMap<Character, String> mappedPosts);
	
	public Map<Character, Long> getSentimentAnalysisForSamples(Long idMonitoring, String term, HashMap<Character, String> mappedPosts);
	
	public Long getPostsTotalByTermForSamples(Long idMonitoring, String term, HashMap<Character, String> mappedPosts);
	
}
