package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.report.ReportControl;

@Stateless  
public class ReportControlDAOImpl extends AbstractDAOImpl<ReportControl> implements ReportControlDAO {

	@Override
	public ReportControl getEntityByNetwork(String networkId,
			Character networkType, Long date) {

		sql = "SELECT rc FROM ReportControl rc WHERE rc.networkId = :networkId " +
				"and rc.networkType = :networkType " +
				"and rc.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("networkId", networkId);
		query.setParameter("networkType", networkType);
		query.setParameter("date", date);

		try {
			return (ReportControl) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ReportControl getEntityByNetwork(String networkId,
			Character networkType, String type, Long date) {

		sql = "SELECT rc FROM ReportControl rc WHERE rc.networkId = :networkId " +
				"and rc.networkType = :networkType " +
				"and rc.type = :type " +
				"and rc.date = :date";	
		
		query = em.createQuery(sql);
		query.setParameter("networkId", networkId);
		query.setParameter("networkType", networkType);
		query.setParameter("type", type);
		query.setParameter("date", date);

		try {
			return (ReportControl) query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public boolean hasEntity(String networkId, Character networkType, String type, Long date) {
		
		sql = "SELECT rc FROM ReportControl rc WHERE rc.networkId = :networkId " +
				"and rc.networkType = :networkType " +
				"and rc.type = :type " +
				"and rc.date = :date";
		
		query = em.createQuery(sql);
		query.setParameter("networkId", networkId);
		query.setParameter("networkType", networkType);
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
	public boolean hasReportTwitterForProfile(String networkId, Character networkType) {
		
		sql = "SELECT COUNT(rc) FROM ReportControl rc WHERE rc.networkId = :networkId " +
				"AND rc.networkType = :networkType";
		
		query = em.createQuery(sql);
		query.setParameter("networkId", networkId);
		query.setParameter("networkType", networkType);
		
		try {
			return ((Long) query.getSingleResult()) > 0L ? true : false;
		} catch (NoResultException e) {
			return false;
		}
	}

	@Override
	public Long getDate(String networkId, Character networkType, String type) {
		
		sql = "SELECT rc.date FROM ReportControl rc WHERE rc.networkId = :networkId " +
				"AND rc.networkType = :networkType " +
				"AND rc.type = :type";
		
		query = em.createQuery(sql);
		query.setParameter("networkId", networkId);
		query.setParameter("networkType", networkType);
		query.setParameter("type", type);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public ReportControl getEntity(String networkId, Character networkType, String type) {

		sql = "SELECT rc FROM ReportControl rc WHERE rc.networkId = :networkId " +
				"AND rc.networkType = :networkType " +
				"AND rc.type = :type";
		
		query = em.createQuery(sql);
		query.setParameter("networkId", networkId);
		query.setParameter("networkType", networkType);
		query.setParameter("type", type);
		
		try {
			return (ReportControl) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	@Override
	public Long getMaxDate(String networkId, Character networkType) {
		
		String where = "x.networkId = '"+networkId+"' AND x.networkType = '"+networkType+"'";
		
		return getMax("date", where);
	}

	@Override
	public Long getMinDate(String networkId, Character networkType) {
		
		String where = "x.networkId = '"+networkId+"' AND x.networkType = '"+networkType+"'";
		
		return getMin("date", where);
	}
	
	@Override
	public Long getMaxDate(String networkId, Character networkType, String type) {
		
		String where = "x.networkId = '" + networkId + "' AND x.networkType = '" + networkType + "' AND x.type = '" + type + "'";
		
		return getMax("date", where);
	}

	@Override
	public Long getMinDate(String networkId, Character networkType, String type) {
		
		String where = "x.networkId = '"+networkId+"' AND x.networkType = '"+networkType+"' AND x.type = '" + type + "'";
		
		return getMin("date", where);
	}

	@Override
	public List<Long> getDates(String networkId, Character networkType, String type) {
		
		sql = "SELECT rc.date FROM ReportControl rc WHERE rc.networkId = :networkId " + 
				"AND rc.networkType = :networkType " +
				"AND rc.type = :type " +
				"ORDER BY rc.date ASC";


		query = em.createQuery(sql);
		query.setParameter("networkId", networkId);
		query.setParameter("networkType", networkType);
		query.setParameter("type", type);


		try {

			return query.getResultList();

		} catch (NoResultException e) {
 
			return new ArrayList<Long>();
		}
				 
	}

	@Override
	public Long getEntityCount(String networkId, Character networkType) {
		
		sql = "SELECT COUNT(rc) FROM ReportControl rc WHERE rc.networkId = :networkId " +
				"AND rc.networkType = :networkType " +
				"GROUP BY rc.networkId";
		
		query = em.createQuery(sql);
		query.setParameter("networkId", networkId);
		query.setParameter("networkType", networkType);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

	@Override
	public Long getFirstDate(String networkId, Character networkType) {
		
		sql = "SELECT rc.date FROM ReportControl rc WHERE rc.networkId = :networkId " +
				"AND rc.networkType = :networkType " +
				"ORDER BY rc.date ASC";
		
		query = em.createQuery(sql);
		query.setParameter("networkId", networkId);
		query.setParameter("networkType", networkType);
		query.setFirstResult(0);
		query.setMaxResults(1);
		
		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}

}
