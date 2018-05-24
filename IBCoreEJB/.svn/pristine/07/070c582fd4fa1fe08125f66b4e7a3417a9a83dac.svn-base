package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.SamplePost;

@Remote
public interface MaintenanceSamplePostRemote extends AbstractDAS<SamplePost> {
	
	public List<SamplePost> listBySample(Long idSample, Integer offset);
	
	public List<Object[]> getPostsByNetwork(Long idSample);
	
	public Object[] getMostVolume(Long idSample);
	
	public List<Object[]> listPostsGroupByNetworks(Long idSample);
}
