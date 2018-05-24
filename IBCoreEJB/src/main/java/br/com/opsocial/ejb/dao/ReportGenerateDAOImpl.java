package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.report.ReportGenerate;

@Stateless  
public class ReportGenerateDAOImpl extends AbstractDAOImpl<ReportGenerate> implements ReportGenerateDAO {

	@Override
	public boolean hasEntity(String networkId, Character networkType) {
		
		sql = "SELECT rg FROM ReportGenerate rg WHERE rg.networkId = :networkId " +
				"and rg.networkType = :networkType";
		
		query = em.createQuery(sql);
		query.setParameter("networkId", networkId);
		query.setParameter("networkType", networkType);

		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}
	}
	
	@Override
	public ReportGenerate getEntity(String networkId, Character networkType) {
		
		sql = "SELECT rg FROM ReportGenerate rg WHERE rg.networkId = :networkId " +
				"and rg.networkType = :networkType";
		
		query = em.createQuery(sql);
		query.setParameter("networkId", networkId);
		query.setParameter("networkType", networkType);

		try {
			return (ReportGenerate) query.getSingleResult();
		} catch (NoResultException e) {
			return new ReportGenerate();
		}
	}

	@Override
	public List<ReportGenerate> getEntities(Character state) {
		
		sql = "SELECT rg FROM ReportGenerate rg WHERE rg.state = :state";
		
		query = em.createQuery(sql);
		query.setParameter("state", state);

		try {
			return query.getResultList();
		} catch (NoResultException e) {
			return new ArrayList<ReportGenerate>();
		}
	}

}
