package br.com.opsocial.ejb.dao;

import java.util.Map;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.MonitoringQualificationReport;

@Local
public interface MonitoringQualificationReportDAO extends AbstractDAO<MonitoringQualificationReport> {

	public boolean hasEntity(Long idMonitoring, String term, Character qualification, Character networkType, Long date);
	public MonitoringQualificationReport getEntity(Long idMonitoring, String term, Character qualification, Character networkType, Long date);
	
	public Map<Character, Long> getSentimentAnalysis(Long idMonitoring, Long dateFrom, Long dateUntil);
	public Map<Character, Long> getSentimentAnalysis(Long idMonitoring, String term, Long dateFrom, Long dateUntil);
	
	public Map<Character, Long> getSentimentAnalysisByNetwork(Long idMonitoring, Long dateFrom, Long dateUntil, String qualifications);
	
}
