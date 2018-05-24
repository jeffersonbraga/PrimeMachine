package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.TwitterStatusReportDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.report.TwitterStatusReport;

@Stateless
public class MaintenanceTwitterStatusReportBean extends AbstractDASImpl<TwitterStatusReport> implements MaintenanceTwitterStatusReportRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	TwitterStatusReportDAO dao; 
	
	@Override
	public AbstractDAO<TwitterStatusReport> getDAO() {
		return dao;
	}

	@Override
	public TwitterStatusReport getEntityByProperty(String idProfile,
			String idStatus, String property) throws IllegalArgumentException {	
		return dao.getEntityByProperty(idProfile, idStatus, property);
	}

	@Override
	public Long getEntityValueByProperty(String idProfile, String idStatus, String property) throws IllegalArgumentException {
		return dao.getEntityValueByProperty(idProfile, idStatus, property);
	}

	@Override
	public void setDao(AbstractDAO<TwitterStatusReport> dao) {
		this.dao = (TwitterStatusReportDAO) dao;
		
	}

	@Override
	public AbstractDAO<TwitterStatusReport> getDao() {
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
