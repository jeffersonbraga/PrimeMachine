package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.ReportControlDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.report.ReportControl;

@Stateless
public class MaintenanceReportControlBean extends AbstractDASImpl<ReportControl> implements MaintenanceReportControlRemote {

	private static final long serialVersionUID = 1L;
	
	@EJB
	ReportControlDAO dao;  
	
	@Override
	public AbstractDAO<ReportControl> getDAO() {
		return dao;
	}

	@Override
	public ReportControl getEntityByNetwork(String networkId,
			Character networkType, Long date) {
		return dao.getEntityByNetwork(networkId, networkType, date);
	}

	@Override
	public ReportControl getEntityByNetwork(String networkId,
			Character networkType, String type, Long date) {
		return dao.getEntityByNetwork(networkId, networkType, type, date);
	}

	@Override
	public Long getMaxDate(String networkId, Character networkType) {
		return dao.getMaxDate(networkId, networkType);
	}

	@Override
	public Long getMinDate(String networkId, Character networkType) {
		return dao.getMinDate(networkId, networkType);
	}
	
	@Override
	public Long getMaxDate(String networkId, Character networkType, String type) {
		return dao.getMaxDate(networkId, networkType, type);
	}

	@Override
	public Long getMinDate(String networkId, Character networkType, String type) {
		return dao.getMinDate(networkId, networkType, type);
	}

	@Override
	public boolean hasEntity(String networkId, Character networkType,String type, Long date) {
		return dao.hasEntity(networkId, networkType, type, date);
	}
	
	@Override
	public boolean hasReportTwitterForProfile(String networkId, Character networkType) {
		return dao.hasReportTwitterForProfile(networkId, networkType);
	}

	@Override
	public Long getDate(String networkId, Character networkType, String type) {
		return dao.getDate(networkId, networkType, type);
	}
	
	@Override
	public List<Long> getDates(String networkId, Character networkType,String type) {
		return dao.getDates(networkId, networkType, type);
	}

	@Override
	public ReportControl getEntity(String networkId, Character networkType, String type) {
		return dao.getEntity(networkId, networkType, type);
	}

	@Override
	public void setDao(AbstractDAO<ReportControl> dao) {
		this.dao = (ReportControlDAO) dao;
	}

	@Override
	public AbstractDAO<ReportControl> getDao() {
		return dao;
	}

	@Override
	public Long getEntityCount(String networkId, Character networkType) {
		return dao.getEntityCount(networkId, networkType);
	}

	@Override
	public Long getFirstDate(String networkId, Character networkType) {
		return dao.getFirstDate(networkId, networkType);
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