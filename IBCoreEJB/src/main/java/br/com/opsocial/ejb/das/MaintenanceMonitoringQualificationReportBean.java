package br.com.opsocial.ejb.das;

import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.MonitoringQualificationReportDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringQualificationReport;

@Stateless
public class MaintenanceMonitoringQualificationReportBean extends AbstractDASImpl<MonitoringQualificationReport> implements MaintenanceMonitoringQualificationReportRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	MonitoringQualificationReportDAO dao;
	
	@Override
	public void setDao(AbstractDAO<MonitoringQualificationReport> dao) {
		this.dao = (MonitoringQualificationReportDAO) dao;
	}

	@Override
	public AbstractDAO<MonitoringQualificationReport> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<MonitoringQualificationReport> getDAO() {
		return dao;
	}

	@Override
	public Map<Character, Long> getSentimentAnalysis(Long idMonitoring, Long dateFrom, Long dateUntil) {
		return dao.getSentimentAnalysis(idMonitoring, dateFrom, dateUntil);
	}

	@Override
	public Map<Character, Long> getSentimentAnalysis(Long idMonitoring, String term, Long dateFrom, Long dateUntil) {
		return dao.getSentimentAnalysis(idMonitoring, term, dateFrom, dateUntil);
	}

	@Override
	public boolean hasEntity(Long idMonitoring, String term, Character qualification, Character networkType, Long date) {
		return dao.hasEntity(idMonitoring, term, qualification, networkType, date);
	}

	@Override
	public MonitoringQualificationReport getEntity(Long idMonitoring, String term, Character qualification, Character networkType,
			Long date) {
		return dao.getEntity(idMonitoring, term, qualification, networkType, date);
	}

	@Override
	public Map<Character, Long> getSentimentAnalysisByNetwork(
			Long idMonitoring, Long dateFrom, Long dateUntil,
			String qualifications) {
		return dao.getSentimentAnalysisByNetwork(idMonitoring, dateFrom, dateUntil, qualifications);
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
