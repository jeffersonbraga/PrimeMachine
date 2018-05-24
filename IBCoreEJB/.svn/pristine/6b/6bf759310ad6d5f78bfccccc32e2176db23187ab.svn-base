package br.com.opsocial.ejb.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.Anniversarie;

@Stateless
public class AnniversarieDAOImpl extends AbstractDAOImpl<Anniversarie> implements AnniversarieDAO {

	@Override
	public List<Anniversarie> listByDate(Date initDate, Date endDate) {

		sql = "Select a From Anniversarie a Where a.date between :initDate and :endDate";
		
		query = em.createQuery(sql);
		query.setParameter("initDate", initDate);
		query.setParameter("endDate", endDate);
		
		return query.getResultList();
	}
}
