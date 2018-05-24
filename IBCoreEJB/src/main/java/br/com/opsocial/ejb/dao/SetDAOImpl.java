package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.sets.Sets;

@Stateless
public class SetDAOImpl extends AbstractDAOImpl<Sets> implements SetDAO {
	
	@Override
	public List<Sets> listByAccount(Long idAccount) {

		preexecute();

		sql = "Select s From Sets s where s.isSchedule = 'F' and s.profile.account.idAccount = :idAccount order by s.idSet ";
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		
		return query.getResultList();
	}

	@Override
	public List<Sets> getByStartDate(Long startDate) {
		
		preexecute();
		
		sql = "Select s From Sets s Where (s.startDate >= :startDate and s.startDate <= :endDate) and s.state = 'on' and s.status = 'O' and s.profile.active = 'T'";
		query = em.createQuery(sql);
		query.setParameter("startDate", startDate - 3600L);
		query.setParameter("endDate", startDate);
		
		return query.getResultList(); 
	}

	@Override
	public List<Sets> getByEndDate(Long endDate) {
		
		preexecute();

		sql = "Select s From Sets s Where s.endDate = :endDate and s.state = 'on'";
		query = em.createQuery(sql);
		query.setParameter("endDate", endDate);
		
		return query.getResultList();
	}

	@Override
	public List<Sets> getByProfile(Long idProfile, Long idAccount) {
		
		preexecute();

		sql = "Select s From Sets s Where s.profile.idProfile = :idProfile and s.profile.account.idAccount = :idAccount";
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("idAccount", idAccount);
		
		return query.getResultList();
		
	}

	@Override
	public List<Sets> listSchedulings(Long idAccount) {
		preexecute();

		sql = "SELECT s FROM Sets s WHERE s.isSchedule = 'T' AND s.state = 'on' AND s.status = 'O' AND s.profile.account.idAccount = :idAccount ORDER BY s.startDate";
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		
		return query.getResultList();
	}

	@Override
	public Long getAmount(Long idAccount) {
		
		sql = "select count(s) from Sets s where s.isSchedule = 'F' and s.profile.account.idAccount = :idAccount";
		
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);

		return (Long) query.getSingleResult();
	}

	@Override
	public List<Sets> listActives() {
		preexecute();
		
		sql = "Select s From Sets s Where s.status='S' and s.state='on'";
		query = em.createQuery(sql);
		
		return query.getResultList();
	}

	@Override
	public List<Sets> listSchedulings(Long idProfile, Long idAccount, Character status, Integer offset, Integer limit) {

		sql = "SELECT s FROM Sets s JOIN s.posts p WHERE s.profile.idProfile = :idProfile AND " +
				"s.profile.account.idAccount = :idAccount AND " +
				"s.isSchedule = 'T' ";
		
		if(!status.equals('A')) {
			sql += "AND p.posted = :status ";
		}
				
		sql += "ORDER BY s.startDate";
		
		query = em.createQuery(sql);
		query.setParameter("idProfile", idProfile);
		query.setParameter("idAccount", idAccount);
		if(!status.equals('A')) {
			query.setParameter("status", status);
		}
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Sets>();
		}
	}
}
