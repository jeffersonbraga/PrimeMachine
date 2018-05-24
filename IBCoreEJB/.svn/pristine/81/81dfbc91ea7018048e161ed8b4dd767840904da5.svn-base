package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.feed.Tab;

@Stateless
public class TabDAOImpl extends AbstractDAOImpl<Tab> implements TabDAO {

	@Override
	public List<Tab> getTabsByUser(Long idUser){
		sql = "SELECT t FROM Tab t"
				+ " WHERE t.user.idUser = :idUser"
				+ " ORDER BY t.idTab ASC";
		
		query = em.createQuery(sql);
		query.setParameter("idUser", idUser);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Tab>();
		}
	}

	@Override
	public Tab getInitialTab(Long idUser) {
		sql = "SELECT t FROM Tab t"
				+ " WHERE t.user.idUser = :idUser"
				+ " ORDER BY t.idTab asc ";
		
		query = em.createQuery(sql);
		query.setParameter("idUser", idUser);
		query.setMaxResults(1);
		
		try {
			return (Tab) query.getSingleResult();
		} catch (Exception e) {
			return new Tab();
		}
	}
}
