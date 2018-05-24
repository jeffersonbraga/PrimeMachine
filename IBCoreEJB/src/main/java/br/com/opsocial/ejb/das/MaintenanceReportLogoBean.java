package br.com.opsocial.ejb.das;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ReportLogoDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.report.ReportLogo;

@Stateless
public class MaintenanceReportLogoBean extends AbstractDASImpl<ReportLogo> implements MaintenanceReportLogoRemote {
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	ReportLogoDAO dao;

	@Override
	public void setDao(AbstractDAO<ReportLogo> dao) {
		this.dao = (ReportLogoDAO) dao;
	}

	@Override
	public AbstractDAO<ReportLogo> getDao() {
		return dao;
	}

	@Override
	public AbstractDAO<ReportLogo> getDAO() {
		return dao;
	}
	
	@Override
	public ReportLogo save(ReportLogo object) throws IllegalArgumentException, Exception {
		return dao.save(object);
	}
	
	@Override
	public void delete(ReportLogo reportLogo) throws IllegalArgumentException, IllegalStateException, Exception {
		super.delete(reportLogo);
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
