package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ObjectAttachmentDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.google.ObjectAttachment;

@Stateless
public class MaintenanceObjectAttachmentBean extends AbstractDASImpl<ObjectAttachment> implements MaintenanceObjectAttachmentRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	ObjectAttachmentDAO dao;

	@Override
	public ObjectAttachmentDAO getDAO() {
		return dao;
	}

	@Override
	public void setDao(AbstractDAO<ObjectAttachment> dao) {
		this.dao = (ObjectAttachmentDAO) dao;
	}

	@Override
	public AbstractDAO<ObjectAttachment> getDao() {
		return dao;
	}

	@Override
	public List<ObjectAttachment> listByPost(String activityId) {
		return dao.listByPost(activityId);
	}

	@Override
	public void setIdUserSession(Long idUserSession) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getIdUserSession() {
		// TODO Auto-generated method stub
		return null;
	}

}
