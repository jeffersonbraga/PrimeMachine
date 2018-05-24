package br.com.opsocial.ejb.dao;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.idclass.MonitoringUserId;
import br.com.opsocial.ejb.entity.monitoring.MonitoringUser;

@Stateless
public class MonitoringUserDAOImpl extends AbstractDAOImpl<MonitoringUser> implements MonitoringUserDAO {

	@Override
	public MonitoringUser save(MonitoringUser monitoringUser) throws Exception {
		
		try {
			
			MonitoringUserId monitoringUserId = new MonitoringUserId();
			monitoringUserId.setIdUser(monitoringUser.getIdUser());
			monitoringUserId.setNetworkType(monitoringUser.getNetworkType());
			
			MonitoringUser objTmp = getById(monitoringUserId);
			
			if(objTmp != null) {
				
				monitoringUser.setVersion(objTmp.getVersion());
			
				em.merge(monitoringUser);
				
				this.flush();

			} else {
				
				em.persist(monitoringUser);
				
				this.flush();
			}
			
		} catch (Exception e) {
			throw new Exception(e);
		}
		
		return monitoringUser;
	}
	
}
