package br.com.opsocial.ejb.das;

import java.util.Map;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.MonitoringQualificationReport;

@Remote
public interface MaintenanceMonitoringQualificationReportRemote extends AbstractDAS<MonitoringQualificationReport> {

	public boolean hasEntity(Long idMonitoring, String term, Character qualification, Character networkType, Long date);
	public MonitoringQualificationReport getEntity(Long idMonitoring, String term, Character qualification, Character networkType, Long date);
	
	public Map<Character, Long> getSentimentAnalysis(Long idMonitoring, Long dateFrom, Long dateUntil);
	public Map<Character, Long> getSentimentAnalysis(Long idMonitoring, String term, Long dateFrom, Long dateUntil);

	public Map<Character, Long> getSentimentAnalysisByNetwork(Long idMonitoring, Long dateFrom, Long dateUntil, String qualifications);
}
