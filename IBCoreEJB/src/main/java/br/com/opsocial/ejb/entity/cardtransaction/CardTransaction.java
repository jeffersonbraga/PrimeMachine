package br.com.opsocial.ejb.entity.cardtransaction;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.application.Account;
import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="cardtransaction", schema="opsocial")
public class CardTransaction extends Persistent {

	@Id
	@Column(name = "idtransaction")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "card_idtransaction_seq")
	private Long idtransaction;

	@Column
	private String numpedido;

	@Column
	private String statuspagamento;

	@Column
	private String mensagemtransacao;

	@Column
	private String operadoramensagem;

	@Column
	private String metodo;

	@Column
	private String id_transacao;

	@Column
	private String valor;

	@Column
	private String id_librepag;

	@Column
	private String autorizacao_id;

	@Column
	private String id_assinatura;

	@Column
	private String token;

	@Column
	private String last4;

	@Column
	private String mes;

	@Column
	private String ano;

	@Column(name="profile_id")
	private String profileIpag;

	@Column(name="date_transaction")
	private Date date_transaction;

	@Column(name="date_start")
	private String date_start; 

	@JoinColumn(name = "idaccount", referencedColumnName = "idaccount")
	private Account account;

	@Column(name="dateevent")
	private Date date_event;

	public Long getIdtransaction() {
		return idtransaction;
	}

	public String getNumpedido() {
		return numpedido;
	}

	public String getStatuspagamento() {
		return statuspagamento;
	}

	public String getMensagemtransacao() {
		return mensagemtransacao;
	}

	public String getOperadoramensagem() {
		return operadoramensagem;
	}

	public String getMetodo() {
		return metodo;
	}

	public Account getAccount() {
		return account;
	}

	public void setIdtransaction(Long idtransaction) {
		this.idtransaction = idtransaction;
	}

	public void setNumpedido(String numpedido) {
		this.numpedido = numpedido;
	}

	public void setStatuspagamento(String statuspagamento) {
		this.statuspagamento = statuspagamento;
	}

	public void setMensagemtransacao(String mensagemtransacao) {
		this.mensagemtransacao = mensagemtransacao;
	}

	public void setOperadoramensagem(String operadoramensagem) {
		this.operadoramensagem = operadoramensagem;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getId_transacao() {
		return id_transacao;
	}

	public String getValor() {
		return valor;
	}

	public String getId_librepag() {
		return id_librepag;
	}

	public String getAutorizacao_id() {
		return autorizacao_id;
	}

	public void setId_transacao(String id_transacao) {
		this.id_transacao = id_transacao;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public void setId_librepag(String id_librepag) {
		this.id_librepag = id_librepag;
	}

	public void setAutorizacao_id(String autorizacao_id) {
		this.autorizacao_id = autorizacao_id;
	}

	public String getId_assinatura() {
		return id_assinatura;
	}

	public void setId_assinatura(String id_assinatura) {
		this.id_assinatura = id_assinatura;
	}
	
	@Override
	public Long getId() {		
		return this.idtransaction;
	}

	@Override
	public void setId(Long id) {
		this.idtransaction = id;		
	}

	public String getToken() {
		return token;
	}

	public String getLast4() {
		return last4;
	}

	public String getMes() {
		return mes;
	}

	public String getAno() {
		return ano;
	}

	public String getProfileIpag() {
		return profileIpag;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setLast4(String last4) {
		this.last4 = last4;
	}

	public void setMes(String mes) {
		this.mes = mes;
	}

	public void setAno(String ano) {
		this.ano = ano;
	}

	public void setProfileIpag(String profileIpag) {
		this.profileIpag = profileIpag;
	}

	public Date getDate_transaction() {
		return date_transaction;
	}

	public String getDate_start() {
		return date_start;
	}

	public void setDate_transaction(Date date_transaction) {
		this.date_transaction = date_transaction;
	}

	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}

	public Date getDate_event() {
		return date_event;
	}

	public void setDate_event(Date date_event) {
		this.date_event = date_event;
	}
}