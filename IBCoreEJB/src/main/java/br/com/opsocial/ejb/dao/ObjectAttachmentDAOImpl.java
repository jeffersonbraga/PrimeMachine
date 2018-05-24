package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.google.ObjectAttachment;

@Stateless
public class ObjectAttachmentDAOImpl extends AbstractDAOImpl<ObjectAttachment> implements ObjectAttachmentDAO {

	@Override
	public List<ObjectAttachment> listByPost(String activityId) {

		sql = "Select o From ObjectAttachment o where o.googlePlusPost.activityId = :activityId";
		
		query = em.createQuery(sql);
		query.setParameter("activityId", activityId);

		return query.getResultList();
	}
}
