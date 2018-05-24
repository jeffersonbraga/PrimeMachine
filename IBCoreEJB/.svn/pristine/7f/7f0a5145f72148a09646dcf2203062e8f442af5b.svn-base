package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.monitoring.SampleReport;

@Remote
public interface MaintenanceSampleReportRemote extends AbstractDAS<SampleReport> {
	
	public List<Object[]> getPostsPerHoursNetworks(Long idSample);
	
	public List<Object[]> getPostsPerWeekNetworks(Long idSample);
	
	public List<Object[]> getPostsByDate(Long idSample);
	
	public Object[] getTimeWithMorePosts(Long idSample);
	
	public Object[] getWeekDayWithMorePosts(Long idSample);
}
