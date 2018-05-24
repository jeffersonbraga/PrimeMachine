package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.AnniversarieDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.Anniversarie;

@Stateless
public class MaintenanceAnniversarieBean extends AbstractDASImpl<Anniversarie> implements MaintenanceAnniversarieRemote {

	private static final long serialVersionUID = 1L;

	@EJB
	AnniversarieDAO dao;  

	@Override
	public AbstractDAO<Anniversarie> getDAO() {
		return dao;
	}
	
	@Override
	public void setDao(AbstractDAO<Anniversarie> dao) {
		this.dao = (AnniversarieDAO) dao;
	}

	@Override
	public AbstractDAO<Anniversarie> getDao() {
		return dao;
	}

	@Override
	public List<Anniversarie> listByDate(Date initDate, Date endDate) {
		return dao.listByDate(initDate, endDate);
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
