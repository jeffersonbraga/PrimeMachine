package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.MonitoringReportControl;

@Stateless
public class MonitoringReportControlDAOImpl extends AbstractDAOImpl<MonitoringReportControl> implements MonitoringReportControlDAO {

	@Override
	public boolean hasEntity(Long idMonitoring, String type, Long date) {
		
		sql = "SELECT mrc FROM MonitoringReportControl mrc WHERE mrc.monitoring.idMonitoring = :idMonitoring " +
				"and mrc.type = :type " +
				"and mrc.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("type", type);
		query.setParameter("date", date);
		
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
		
	}

	@Override
	public MonitoringReportControl getEntity(Long idMonitoring, String type, Long date) {
		
		sql = "SELECT mrc FROM MonitoringReportControl mrc WHERE mrc.monitoring.idMonitoring = :idMonitoring " +
				"and mrc.type = :type " +
				"and mrc.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("type", type);
		query.setParameter("date", date);
		
		try {
			return (MonitoringReportControl) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Long> getDates(Long idMonitoring, String type) {
		
		sql = "SELECT mrc.date FROM MonitoringReportControl mrc WHERE mrc.monitoring.idMonitoring = :idMonitoring " +
				"AND mrc.type = :type " +
				"ORDER BY mrc.date ASC";

		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("type", type);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<Long>();
		}
	}

}
