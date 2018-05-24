package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.report.TwitterStatusReport;

@Stateless
public class TwitterStatusReportDAOImpl extends AbstractDAOImpl<TwitterStatusReport> implements TwitterStatusReportDAO {

	@Override
	public TwitterStatusReport getEntityByProperty(String where) {
		
		try {
			sql = "SELECT tsr FROM TwitterStatusReport tsr WHERE " + where.trim();
			
			return (TwitterStatusReport) em.createQuery(sql).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Long getEntityValueByProperty(String idProfile, String idStatus, String property) {
		
		sql = "SELECT tsr.value FROM TwitterStatusReport tsr WHERE tsr.idProfile = :idProfile " +
				"AND tsr.idStatus = :idStatus AND tsr.property = :property";
		
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("idStatus", idStatus);
		query.setParameter("property", property);

		try {
			return (Long) query.getSingleResult();
		} catch (NoResultException e) {
			return 0L;
		}
	}
	
	@Override
	public TwitterStatusReport getEntityByProperty(String idProfile,
			String idStatus, String property) throws IllegalArgumentException {
		
		String where = "tsr.idProfile = '"+idProfile+"' AND tsr.idStatus = '"+idStatus+"' AND tsr.property = '"+property+"'";
		
		try {
			sql = "SELECT tsr FROM TwitterStatusReport tsr WHERE " + where.trim();
			
			return (TwitterStatusReport) em.createQuery(sql).getSingleResult();
		} catch (NoResultException e) {
			return null;
		} catch (Exception e) {
			return null;
		}
	}

}
