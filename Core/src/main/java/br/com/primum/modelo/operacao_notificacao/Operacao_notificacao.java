package br.com.primum.modelo.operacao_notificacao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="operacao_notificacao")
public class Operacao_notificacao {

	
	@Id
	@Column
	private Integer cd_operacao_notificacao;
	
	@Column
	private String ds_operacao_notificacao;

	public Integer getCd_operacao_notificacao() {
		return cd_operacao_notificacao;
	}

	public void setCd_operacao_notificacao(Integer cd_operacao_notificacao) {
		this.cd_operacao_notificacao = cd_operacao_notificacao;
	}

	public String getDs_operacao_notificacao() {
		return ds_operacao_notificacao;
	}

	public void setDs_operacao_notificacao(String ds_operacao_notificacao) {
		this.ds_operacao_notificacao = ds_operacao_notificacao;
	}
	
	
}
