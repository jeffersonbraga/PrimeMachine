package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.das.MaintenanceAccountRemote;
import br.com.opsocial.ejb.das.MaintenanceColorTagRemote;
import br.com.opsocial.ejb.das.MaintenanceMonitoringRemote;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.monitoring.ColorTag;
import br.com.opsocial.ejb.entity.monitoring.Monitoring;
import br.com.opsocial.ejb.entity.monitoring.MonitoringTag;

@Stateless
public class MonitoringTagDAOImpl extends AbstractDAOImpl<MonitoringTag> implements MonitoringTagDAO {

	@Override
	public List<MonitoringTag> listByMonitoring(Long idMonitoring, Long idAccount) {
		
		sql = "SELECT mt.idtag, mt.idmonitoring, " + 
					 "mt.idaccount, ct.idcolortag, mt.name, mt.version " +
					 "FROM opsocial.monitoringstags mt " +
				"INNER JOIN opsocial.colortags ct ON " +
				"ct.idColorTag = mt.idColorTag " +
				"WHERE (mt.idMonitoring IS NULL OR mt.idMonitoring = " + idMonitoring + ") AND " + 
				"mt.idAccount = " + idAccount + " " + 
				"ORDER BY mt.idtag ASC";
		
		query = em.createNativeQuery(sql);
		
		List<Object[]> result = query.getResultList();
		List<MonitoringTag> monitoringTags = new ArrayList<MonitoringTag>();
		
		for(Object[] object : result) {
			
			MonitoringTag monitoringTag = new MonitoringTag();
			monitoringTag.setIdTag((Long) object[0]);
			
			if(object[1] != null) {
				
				try {
					
					MaintenanceMonitoringRemote monitoringRemote = (MaintenanceMonitoringRemote) 
							new InitialContext().lookup("global/OpSocialBack/MaintenanceMonitoringBean!br.com.opsocial.ejb.das.MaintenanceMonitoringRemote");
					
					Monitoring monitoring = monitoringRemote.getById(((Integer) object[1]).longValue());
					monitoringTag.setMonitoring(monitoring);
					
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
			
			try {
				
				MaintenanceAccountRemote accountRemote = (MaintenanceAccountRemote) 
						new InitialContext().lookup("global/OpSocialBack/MaintenanceAccountBean!br.com.opsocial.ejb.das.MaintenanceAccountRemote");
				
				Account account = accountRemote.getById((Long) object[2]);
				monitoringTag.setAccount(account);
				
			} catch (NamingException e) {
				e.printStackTrace();
			}
			
			try {
				
				MaintenanceColorTagRemote colorTagRemote = (MaintenanceColorTagRemote) 
						new InitialContext().lookup("global/OpSocialBack/MaintenanceColorTagBean!br.com.opsocial.ejb.das.MaintenanceColorTagRemote");
				
				ColorTag colorTag = colorTagRemote.getById((Long) object[3]);
				monitoringTag.setColorTag(colorTag);
				
			} catch (NamingException e) {
				e.printStackTrace();
			}
			
			monitoringTag.setName((String) object[4]);
			monitoringTag.setVersion((Long) object[5]);
			
			monitoringTags.add(monitoringTag);
		}

		return monitoringTags;
	}

	@Override
	public List<MonitoringTag> listGlobalTags(Long idAccount) {
		
		sql = "SELECT mt FROM MonitoringTag mt WHERE mt.account.idAccount = :idAccount " +
				"AND mt.monitoring IS NULL ORDER BY mt.name ASC";
		
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		
		return query.getResultList();
	}

	@Override
	public List<MonitoringTag> listForMonitoring(Long idMonitoring, Long idAccount) {
		
		sql = "SELECT mt FROM MonitoringTag mt WHERE mt.account.idAccount = :idAccount " +
				"AND mt.monitoring.idMonitoring = :idMonitoring ORDER BY mt.name ASC";
		
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		query.setParameter("idMonitoring", idMonitoring);
		
		return query.getResultList();
	}
	
}
