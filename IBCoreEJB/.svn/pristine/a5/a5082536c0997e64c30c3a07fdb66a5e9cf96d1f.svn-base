package br.com.opsocial.ejb.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityTransaction;

import br.com.opsocial.ejb.dao.generic.AbstractDAOImpl;
import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.cardtransaction.CardTransaction;

@Stateless
public class CardTransactionDAOImpl extends AbstractDAOImpl<CardTransaction> implements CardTransactionDAO {


	@Override
	public List<CardTransaction> getByAccount(Long idAccount, String num_cartao, String mes_cartao, String ano_cartao) {

		sql = "SELECT a FROM CardTransaction a WHERE a.account.idAccount = :account and a.last4 like CONCAT('%', :num_cartao) and a.mes = :mes_cartao and a.ano = :ano_cartao and a.date_event = CURRENT_DATE";

		query = em.createQuery(sql);
		query.setParameter("account", idAccount);
		query.setParameter("num_cartao", num_cartao);
		query.setParameter("mes_cartao", mes_cartao);
		query.setParameter("ano_cartao", ano_cartao);

		return query.getResultList(); 
	}
}
