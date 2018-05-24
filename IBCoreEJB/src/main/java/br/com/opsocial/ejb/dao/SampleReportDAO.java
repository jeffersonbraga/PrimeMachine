package br.com.opsocial.ejb.dao;

import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.monitoring.SampleReport;

@Local
public interface SampleReportDAO extends AbstractDAO<SampleReport> {

	public List<Object[]> getPostsPerHoursNetworks(Long idSample);

	public List<Object[]> getPostsPerWeekNetworks(Long idSample);
	
	public List<Object[]> getPostsByDate(Long idSample);
	
	public Object[] getTimeWithMorePosts(Long idSample);
	
	public Object[] getWeekDayWithMorePosts(Long idSample);
}
