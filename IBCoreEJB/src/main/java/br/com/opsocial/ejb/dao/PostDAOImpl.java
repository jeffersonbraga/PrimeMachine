package br.com.opsocial.ejb.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.ejb.entity.sets.PostAttachment;

@Stateless
public class PostDAOImpl extends AbstractDAOImpl<Post> implements PostDAO {
	
	@Override
	public List<Post> listSchedulings(ArrayList<Integer> idProfiles, Long idAccount, Character status, Integer offset, Integer limit, String order) {

		sql = "SELECT p FROM Post p";
		
		sql += " WHERE (";
		boolean first = true;
		for(Integer idProfile : idProfiles) {
			if(first) {
				sql += "p.profile.idProfile = "+idProfile;
				first = false;
			} else {
				sql += " or p.profile.idProfile = "+idProfile;
			}
		}
		sql += ")";

		if(!status.equals('A')) {

			if(status.equals('O')) {

				sql += " AND p.status = :status ";
				sql += " AND p.posted = '" + Post.NOT_POSTED + "'";	
			} else {

				sql += " AND p.posted = :status ";	
			}
		}

		sql += " AND p.profile.account.idAccount = :idAccount "
				+ " AND p.scheduleDate is not null ";

		sql += " ORDER BY CAST(p.scheduleDate as TIMESTAMP) " + order;

		query = em.createQuery(sql);
		if(!status.equals('A')) {
			query.setParameter("status", status);
		}
		query.setParameter("idAccount", idAccount);
		query.setFirstResult(offset);
		query.setMaxResults(limit);
		
		try {
			return query.getResultList();
		} catch (Exception e) {
			return new ArrayList<Post>();
		}
	}

	@Override
	public Post getPostReloadFeature(User loggedUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Post> getByStartDate(Long startDate) {

		sql = "Select s From Post s Where (s.startDate >= :startDate and s.startDate <= :endDate) and s.status = 'O'";
		query = em.createQuery(sql);
		query.setParameter("startDate", startDate - 3600000L);
		query.setParameter("endDate", startDate);

		return query.getResultList(); 
	}

	@Override
	public void deleteAttachment(PostAttachment postAtt) {

		sql = "Delete From PostAttachment s Where (s.idPostAttachment = :idPostAttachment)";
		query = em.createQuery(sql);
		query.setParameter("idPostAttachment", postAtt.getIdPostAttachment());
		query.executeUpdate(); 
	}
}