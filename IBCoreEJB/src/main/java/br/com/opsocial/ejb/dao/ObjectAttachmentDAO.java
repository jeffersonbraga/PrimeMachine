package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.google.ObjectAttachment;

@Local
public interface ObjectAttachmentDAO extends AbstractDAO<ObjectAttachment> {

	public List<ObjectAttachment> listByPost(String activityId);
	
}
