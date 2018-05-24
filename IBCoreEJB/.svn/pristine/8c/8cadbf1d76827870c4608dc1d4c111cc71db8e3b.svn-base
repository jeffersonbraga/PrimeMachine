package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.Sample;

@Stateless
public class SampleDAOImpl extends AbstractDAOImpl<Sample> implements SampleDAO{

	@Override
	public Sample getByName(String name, Long idMonitoring) {
		
		sql = "SELECT s FROM Sample s WHERE s.monitoring.idMonitoring = :idMonitoring and s.name = :name";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		query.setParameter("name", name);
		
		try {
			return (Sample) query.getSingleResult();	
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Sample> listByMonitoring(Long idMonitoring) {
		
		sql = "SELECT s FROM Sample s WHERE s.monitoring.idMonitoring = :idMonitoring";
		
		query = em.createQuery(sql);
		query.setParameter("idMonitoring", idMonitoring);
		
		return query.getResultList();
	} 
}
