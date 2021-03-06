package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.SamplePostDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.SamplePost;

@Stateless
public class MaintenanceSamplePostBean extends AbstractDASImpl<SamplePost> implements MaintenanceSamplePostRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	SamplePostDAO dao; 

	@Override
	public AbstractDAO<SamplePost> getDAO() { 
		return dao;
	} 

	@Override
	public void setDao(AbstractDAO<SamplePost> dao) {
		this.dao = (SamplePostDAO) dao;	
	}

	@Override
	public AbstractDAO<SamplePost> getDao() {
		return dao;
	}

	@Override
	public List<SamplePost> listBySample(Long idSample, Integer offset) {
		return dao.listBySample(idSample, offset);
	}

	@Override
	public List<Object[]> getPostsByNetwork(Long idSample) {
		return dao.getPostsByNetwork(idSample);
	}

	@Override
	public Object[] getMostVolume(Long idSample) {
		return dao.getMostVolume(idSample);
	}

	@Override
	public List<Object[]> listPostsGroupByNetworks(Long idSample) {
		return dao.listPostsGroupByNetworks(idSample);
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
