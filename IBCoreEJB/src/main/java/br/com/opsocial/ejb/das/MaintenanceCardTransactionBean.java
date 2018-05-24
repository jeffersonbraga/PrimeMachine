package br.com.opsocial.ejb.das;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.opsocial.ejb.dao.AccountDAO;
import br.com.opsocial.ejb.dao.CardTransactionDAO;
import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.das.generic.AbstractDASImpl;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.cardtransaction.CardTransaction;

@Stateless
public class MaintenanceCardTransactionBean extends AbstractDASImpl<CardTransaction> implements MaintenanceCardTransactionRemote {

	private static final long serialVersionUID = 1L;		

	@EJB
	CardTransactionDAO dao;

	@Override
	public void setDao(AbstractDAO<CardTransaction> dao) {
		this.dao = (CardTransactionDAO) dao;
	}

	@Override
	public AbstractDAO<CardTransaction> getDao() {
		// TODO Auto-generated method stub
		return this.dao;
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

	@Override
	public AbstractDAO getDAO() {
		// TODO Auto-generated method stub
		return dao;
	}

	@Override
	public List<CardTransaction> getByAccount(Long idAccount, String num_cartao, String mes_cartao, String ano_cartao) {
		// TODO Auto-generated method stub
		return dao.getByAccount(idAccount, num_cartao, mes_cartao, ano_cartao);
	}
}
