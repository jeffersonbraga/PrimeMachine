package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ReportGenerateDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.report.ReportGenerate;

@Stateless
public class MaintenanceReportGenerateBean extends AbstractDASImpl<ReportGenerate> implements MaintenanceReportGenerateRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	ReportGenerateDAO dao;
	
	@Override
	public AbstractDAO<ReportGenerate> getDAO() {
		return dao;
	}

	@Override
	public boolean hasEntity(String networkId, Character networkType) {
		return dao.hasEntity(networkId, networkType);
	}

	@Override
	public List<ReportGenerate> getEntities(Character state) {
		return dao.getEntities(state);
	}

	@Override
	public ReportGenerate getEntity(String networkId, Character networkType) {
		return dao.getEntity(networkId, networkType);
	}

	@Override
	public void setDao(AbstractDAO<ReportGenerate> dao) {
		this.dao = (ReportGenerateDAO) dao;
		
	}

	@Override
	public AbstractDAO<ReportGenerate> getDao() {
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
