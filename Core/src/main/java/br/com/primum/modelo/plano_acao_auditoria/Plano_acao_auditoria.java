package br.com.primum.modelo.plano_acao_auditoria;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.plano_acao.Plano_acao;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="plano_acao_auditoria")
public class Plano_acao_auditoria {
	
	@Id
	@Column
	private Integer cd_plano_acao_auditoria;
	
	@Column
	private Integer cd_plano_acao;
	
	@Column
	private Integer cd_resposta_exec_auditoria;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_criacao;

	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.plano_acao.Plano_acao",campoBuscar="cd_plano_acao", campoComparar="cd_plano_acao")
	@Transient
	private Plano_acao plano_acao;
	
	
	
	public Plano_acao getPlano_acao() {
		return plano_acao;
	}

	public void setPlano_acao(Plano_acao plano_acao) {
		this.plano_acao = plano_acao;
	}

	public Integer getCd_plano_acao_auditoria() {
		return cd_plano_acao_auditoria;
	}

	public void setCd_plano_acao_auditoria(Integer cd_plano_acao_auditoria) {
		this.cd_plano_acao_auditoria = cd_plano_acao_auditoria;
	}

	public Integer getCd_plano_acao() {
		return cd_plano_acao;
	}

	public void setCd_plano_acao(Integer cd_plano_acao) {
		this.cd_plano_acao = cd_plano_acao;
	}

	
	public Integer getCd_resposta_exec_auditoria() {
		return cd_resposta_exec_auditoria;
	}

	public void setCd_resposta_exec_auditoria(Integer cd_resposta_exec_auditoria) {
		this.cd_resposta_exec_auditoria = cd_resposta_exec_auditoria;
	}

	public Date getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(Date dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

}
