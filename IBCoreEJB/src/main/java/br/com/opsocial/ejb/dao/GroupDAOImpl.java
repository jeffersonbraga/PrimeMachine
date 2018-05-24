package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.groups.Groups;

@Stateless
public class GroupDAOImpl extends AbstractDAOImpl<Groups> implements GroupDAO {

	@Override
	public List<Groups> listGroupsByAccount(Long idAccount, Character groupType) {

		sql = "SELECT g FROM Groups g WHERE g.account.idAccount = :idAccount AND g.groupType = :groupType ORDER BY g.name";
		
		query = em.createQuery(sql);
		query.setParameter("idAccount", idAccount);
		query.setParameter("groupType", groupType);
		
		return query.getResultList();
	}
}
