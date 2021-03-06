package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.AccountDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.Account;

@Stateless
public class MaintenanceAccountBean extends AbstractDASImpl<Account> implements MaintenanceAccountRemote {

	private static final long serialVersionUID = 1L;		

	@EJB
	AccountDAO dao;  

	@Override
	public AbstractDAO<Account> getDAO() {
		return dao;
	}
	
	@Override
	public void setDao(AbstractDAO<Account> dao) {
		this.dao = (AccountDAO) dao;
	}

	@Override
	public AbstractDAO<Account> getDao() {
		return dao;
	}

	@Override
	public List<Account> listInactiveAccounts(Date now) {
		return dao.listInactiveAccounts(now);
	}
	
	@Override
	public List<Account> listByCadastreDate(Date init, Date end) {
		return dao.listByCadastreDate(init, end);
	}

	@Override
	public List<Account> listActiveAccounts(Date now) {
		return dao.listActiveAccounts(now);
	}

	@Override
	public List<Account> listInactiveTestAccounts(Date now) {
		return dao.listInactiveTestAccounts(now);
	}
	
	protected Long idUserSession;

	@Override
	public void setIdUserSession(Long idUserSession) {
		this.idUserSession = idUserSession;
	}
	
	@Override
	public Long getIdUserSession(){
		return this.idUserSession;
	}

	@Override
	public List<Account> listByPlanType(Byte planType) {
		return dao.listByPlanType(planType);
	}
}
