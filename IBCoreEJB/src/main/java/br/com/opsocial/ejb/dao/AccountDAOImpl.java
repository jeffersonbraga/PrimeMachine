package br.com.opsocial.ejb.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.application.Profile;

@Stateless
public class AccountDAOImpl extends AbstractDAOImpl<Account> implements AccountDAO {

	@Override
	public List<Account> listInactiveAccounts(Date now) {
		
		sql = "Select a From Account a where " +
				"((a.testExpire < :now and a.planType = 0)" +
				" or " +
				"(a.planExpire < :now and a.planType <> 0))";
		
		query = em.createQuery(sql);
		query.setParameter("now", now);
		
		return query.getResultList();
	}
	
	@Override
	public List<Account> listInactiveTestAccounts(Date now) {
		
		sql = "Select a From Account a where a.testExpire < :now and a.planType = 0";
		
		query = em.createQuery(sql);
		query.setParameter("now", now);
		
		return query.getResultList();
	}
	
	@Override
	public List<Account> listActiveAccounts(Date now) {
		
		sql = "Select a From Account a where " +
				"((a.testExpire > :now and a.planType = 0)" +
				" or " +
				"(a.planExpire > :now and a.planType <> 0))";
			
		query = em.createQuery(sql);
		query.setParameter("now", now);
		
		return query.getResultList();
	}
	
	@Override
	public List<Account> listByCadastreDate(Date init, Date end) {
		
		sql = "select a from Account a where a.cadastreDate between :init and :end";
		
		query = em.createQuery(sql);
		query.setParameter("init", init);
		query.setParameter("end", end);
		
		return query.getResultList(); 
	}

	@Override
	public List<Account> listByPlanType(Byte planType) {
		
		sql = "SELECT a FROM Account a WHERE a.active = :active AND a.planType = :planType";
		
		query = em.createQuery(sql);
		query.setParameter("active", 'T');
		query.setParameter("planType", planType);
		
		return query.getResultList(); 
	}
}
