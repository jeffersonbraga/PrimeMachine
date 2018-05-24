package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.Timezone;

@Stateless
public class TimezoneDAOImpl extends AbstractDAOImpl<Timezone> implements TimezoneDAO {

	@Override
	public List<Timezone> listTimezones() {
		sql = "SELECT t FROM Timezone t";
		
		query = em.createQuery(sql);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Timezone>();
		}
	}
	
}