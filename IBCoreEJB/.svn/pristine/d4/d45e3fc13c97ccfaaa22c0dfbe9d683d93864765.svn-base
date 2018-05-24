package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;
import javax.persistence.OptimisticLockException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.idclass.MonitoringSearchId;
import br.com.opsocial.ejb.entity.monitoring.MonitoringSearch;

@Stateless
public class MonitoringSearchDAOImpl extends AbstractDAOImpl<MonitoringSearch> implements MonitoringSearchDAO {

	@Override
	public MonitoringSearch save(MonitoringSearch monitoringSearch) throws Exception {
		
		try {
			
			MonitoringSearchId monitoringSearchId = new MonitoringSearchId();
			monitoringSearchId.setMonitoring(monitoringSearch.getMonitoring().getIdMonitoring());
			monitoringSearchId.setTerm(monitoringSearch.getTerm());
			monitoringSearchId.setNetwork(monitoringSearch.getNetwork());
			
			MonitoringSearch objTmp = getById(monitoringSearchId);
			
			if(objTmp != null) {
				
				try {
					validateVersion(objTmp, monitoringSearch);
				} catch (IllegalStateException ex) {
					throw new OptimisticLockException();
				}
			
				em.merge(monitoringSearch);
				
				this.flush();

			} else {
				
				em.persist(monitoringSearch);
				
				this.flush();
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return monitoringSearch;
	}
	
}
