package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;

@Stateless
public class MonitoringDAOImpl extends AbstractDAOImpl<Monitoring> implements MonitoringDAO {

	@Override
	public Boolean hasMonitoring(Long idAccount, String name, Character type) {
		
		sql = "SELECT m FROM Monitoring m WHERE m.account.idAccount = :idAccount AND UPPER(m.name) = :name AND m.type = :type AND m.state <> :state";
		
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		query.setParameter("name", name.toUpperCase());
		query.setParameter("type", type);
		query.setParameter("state", Monitoring.DELETED);
		
		try {
			query.getSingleResult();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<Monitoring> getMonitoringsByAccount(Long idAccount) {

		sql = "SELECT m FROM Monitoring m WHERE m.account.idAccount = :idAccount " +
				"AND m.state <> :state " +
				"ORDER BY m.createdAt DESC";
		
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		query.setParameter("state", Monitoring.DELETED);
		
		return query.getResultList();
	}
}
