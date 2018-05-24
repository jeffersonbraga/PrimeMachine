package br.com.opsocial.ejb.das;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.PostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.User;
import br.com.opsocial.ejb.entity.sets.Post;
import br.com.opsocial.ejb.entity.sets.PostAttachment;

@Stateless
public class MaintenancePostBean extends AbstractDASImpl<Post> implements MaintenancePostRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	PostDAO dao;
	
	@Override
	public void setDao(AbstractDAO<Post> dao) {
		this.dao = (PostDAO) dao;
	}

	@Override
	public AbstractDAO<Post> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO getDAO() {
		return dao;
	}

	public List<Post> listSchedulings(ArrayList<Integer> idProfile, Long idAccount, Character status, Integer offset, Integer limit, String order) {
		return dao.listSchedulings(idProfile, idAccount, status, offset, limit, order);
	}
    
    @Override
    public void setIdUserSession(Long idUserSession){
    	this.idUserSession = idUserSession;
    }
	
    @Override
	public Long getIdUserSession(){
		return this.idUserSession;
	}

	@Override
	public Post getPostReloadFeature(User loggedUser) {
		
		return dao.getPostReloadFeature(loggedUser);
	}

	@Override
	public List<Post> getByStartDate(Long time) {
		
		return dao.getByStartDate(time);
	}

	@Override
	public void deleteAttachment(PostAttachment postAtt) {
		
		dao.deleteAttachment(postAtt);
	}
}