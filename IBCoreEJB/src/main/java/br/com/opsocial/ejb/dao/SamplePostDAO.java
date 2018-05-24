package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.SamplePost;

@Local
public interface SamplePostDAO extends AbstractDAO<SamplePost>{
	
	public List<SamplePost> listBySample(Long idSample, Integer offset);

	public List<Object[]> getPostsByNetwork(Long idSample);
	
	public Object[] getMostVolume(Long idSample);
	
	public List<Object[]> listPostsGroupByNetworks(Long idSample);

}
