package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringLogReport;

@Stateless
public class MonitoringLogReportDAOImpl extends AbstractDAOImpl<MonitoringLogReport> implements MonitoringLogReportDAO {

	@Override
	public List<MonitoringLogReport> getMonitoringLogReports(Long idMonitoring, String reportType, Character updateApplied) {
		
		sql = "SELECT mlr FROM MonitoringLogReport mlr WHERE mlr.monitoring.idMonitoring = :idMonitoring AND " +
				"mlr.reportType = :reportType AND mlr.updateApplied = :updateApplied";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("reportType", reportType);
		query.setParameter("updateApplied", updateApplied);
		
		try {
			return (List<MonitoringLogReport>) query.getResultList();
		} catch (Exception e) {
			return new ArrayList<MonitoringLogReport>();
		}
	}

	@Override
	public boolean hasMonitoringLogReports(Long idMonitoring, Character updateApplied) {
		
		Long count;
		
		sql = "SELECT COUNT(mlr) FROM MonitoringLogReport mlr WHERE mlr.monitoring.idMonitoring = :idMonitoring AND " +
				"mlr.updateApplied = :updateApplied";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("updateApplied", updateApplied);
		
		count = (Long) query.getSingleResult(); 
		if(count > 0) {
			return true;
		} else {
			return false;
		}
	}

}
