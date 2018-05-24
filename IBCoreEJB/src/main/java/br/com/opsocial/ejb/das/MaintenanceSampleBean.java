package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.SampleDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.monitoring.Sample;

@Stateless
public class MaintenanceSampleBean extends AbstractDASImpl<Sample> implements MaintenanceSampleRemote {
	
private static final long serialVersionUID = 1L;
	
	@EJB
	SampleDAO dao; 

	@Override
	public AbstractDAO<Sample> getDAO() { 
		return dao;
	} 

	@Override
	public void setDao(AbstractDAO<Sample> dao) {
		this.dao = (SampleDAO) dao;	
	}

	@Override
	public AbstractDAO<Sample> getDao() {
		return dao;
	}

	@Override
	public Sample getByName(String name, Long idMonitoring) {
		return dao.getByName(name, idMonitoring);
	}

	@Override
	public List<Sample> listByMonitoring(Long idMonitoring) {
		return dao.listByMonitoring(idMonitoring);
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
