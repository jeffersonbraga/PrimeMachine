package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.YoutubePostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.youtube.YoutubePost;

@Stateless
public class MaintenanceYoutubePostBean extends AbstractDASImpl<YoutubePost> implements MaintenanceYoutubePostRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	YoutubePostDAO dao;
	
	@Override
	public AbstractDAO getDAO() {
		return dao;
	}
	
	@Override
	public YoutubePost save(YoutubePost object) throws IllegalArgumentException, Exception {
		return dao.save(object);
	}

	@Override
	public void setDao(AbstractDAO<YoutubePost> dao) {
		this.dao = (YoutubePostDAO) dao;
	}

	@Override
	public AbstractDAO<YoutubePost> getDao() {
		return dao;
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
