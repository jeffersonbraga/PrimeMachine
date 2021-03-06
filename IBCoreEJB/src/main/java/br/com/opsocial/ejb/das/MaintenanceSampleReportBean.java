package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.SampleReportDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.SampleReport;

@Stateless
public class MaintenanceSampleReportBean extends AbstractDASImpl<SampleReport> implements MaintenanceSampleReportRemote {
	
	private static final long serialVersionUID = 1L;

	@EJB
	SampleReportDAO dao; 

	@Override
	public AbstractDAO<SampleReport> getDAO() { 
		return dao;
	} 

	@Override
	public void setDao(AbstractDAO<SampleReport> dao) {
		this.dao = (SampleReportDAO) dao;	
	}

	@Override
	public AbstractDAO<SampleReport> getDao() {
		return dao;
	}

	@Override
	public List<Object[]> getPostsPerHoursNetworks(Long idSample) {
		return dao.getPostsPerHoursNetworks(idSample);
	}

	@Override
	public List<Object[]> getPostsPerWeekNetworks(Long idSample) {
		return dao.getPostsPerWeekNetworks(idSample);
	}

	@Override
	public Object[] getTimeWithMorePosts(Long idSample) {
		return dao.getTimeWithMorePosts(idSample);
	}

	@Override
	public Object[] getWeekDayWithMorePosts(Long idSample) {
		return dao.getWeekDayWithMorePosts(idSample);
	}

	@Override
	public List<Object[]> getPostsByDate(Long idSample) {
		return dao.getPostsByDate(idSample);
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
