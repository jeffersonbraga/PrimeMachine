package br.com.opsocial.ejb.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import br.com.opsocial.ejb.dao.generic.AbstractDAO;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.cardtransaction.CardTransaction;	

@Local
public interface CardTransactionDAO extends AbstractDAO<CardTransaction	> {

	public List<CardTransaction> getByAccount(Long idAccount, String num_cartao, String mes_cartao, String ano_cartao);
}
