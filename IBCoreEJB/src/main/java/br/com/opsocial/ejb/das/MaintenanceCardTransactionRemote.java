package br.com.opsocial.ejb.das;

import java.util.List;

import javax.ejb.Remote;

import br.com.opsocial.ejb.das.generic.AbstractDAS;
import br.com.opsocial.ejb.entity.cardtransaction.CardTransaction;

@Remote
public interface MaintenanceCardTransactionRemote extends AbstractDAS<CardTransaction> {
	
	public List<CardTransaction> getByAccount(Long idAccount, String num_cartao, String mes_cartao, String ano_cartao);
}
