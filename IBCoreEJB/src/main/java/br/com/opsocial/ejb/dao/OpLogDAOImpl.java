package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.OpLog;
import br.com.opsocial.ejb.entity.application.User;

@Stateless
public class OpLogDAOImpl extends AbstractDAOImpl<OpLog> implements OpLogDAO{

	@Override
	public List<OpLog> getLogByUserEntityAndOperation(Long idUser, String entity, String operation) {
		sql = "SELECT l FROM OpLog l WHERE l.idUser = :idUser "
				+ " AND l.entity = :entity"
				+ " AND l.operation = :operation ORDER BY l.date";
		
		query = em.createQuery(sql);
		query.setParameter("idUser", idUser);
		query.setParameter("entity", entity);
		query.setParameter("operation", operation);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<OpLog>();
		}
	}
	
	@Override
	public User getLogByEntityByInsert(Long entityKey, String entity) {
		sql = "SELECT l FROM OpLog l WHERE "
				+ " l.entity = :entity"
				+ " AND l.entityKey = :entityKey "
				+ " AND l.operation = 'insert'";
		
		query = em.createQuery(sql);
		query.setParameter("entityKey", entityKey);
		query.setParameter("entity", entity);
		
		try {
			OpLog opLog = (OpLog) query.getSingleResult();
			
			if (opLog != null) {				
				sql = "SELECT u FROM User u WHERE "
						+ " u.idUser = :idUser";
				
				Query queryUser = em.createQuery(sql);
				queryUser.setParameter("idUser", opLog.getIdtUser());
				
				return (User) queryUser.getSingleResult();
			} else {
				return null;
			}
		} catch (Exception e) {
			return null;
		}
	}
}
