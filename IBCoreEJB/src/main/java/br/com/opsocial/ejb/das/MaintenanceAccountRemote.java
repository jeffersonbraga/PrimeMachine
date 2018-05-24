package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.application.Account;	

@Remote
public interface MaintenanceAccountRemote extends AbstractDAS<Account> {

	public List<Account> listInactiveAccounts(Date now);

	public List<Account> listInactiveTestAccounts(Date now);
	
	public List<Account> listActiveAccounts(Date now);
	
	public List<Account> listByCadastreDate(Date init, Date end);
	
	public List<Account> listByPlanType(Byte planType);
}
