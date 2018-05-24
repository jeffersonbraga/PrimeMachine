package br.com.primum.modelo.plano_acao_formulario;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ChaveEstrangeira;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.plano_acao.Plano_acao;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="plano_acao_formulario")
public class Plano_acao_formulario implements Serializable {

	/**
	 * 
	 */
	@Transient
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-7020075430338090621L;

	@Id
	@Column
	private Integer cd_plano_acao_formulario;

	@Column
	@ChaveEstrangeira(nomeObjetoOrigem="cd_resposta_exec_formulario")
	private Integer cd_resposta_exec_formulario;

	@Column
	@ChaveEstrangeira(nomeObjetoOrigem="cd_plano_acao")
	private Integer cd_plano_acao;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_criacao;

	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.plano_acao.Plano_acao", campoBuscar="cd_plano_acao", campoComparar="cd_plano_acao", persistir=false)
	private Plano_acao plano_acao;

	public Integer getCd_plano_acao_formulario() {
		return cd_plano_acao_formulario;
	}

	public void setCd_plano_acao_formulario(Integer cd_plano_acao_formulario) {
		this.cd_plano_acao_formulario = cd_plano_acao_formulario;
	}

	public Integer getCd_resposta_exec_formulario() {
		return cd_resposta_exec_formulario;
	}

	public void setCd_resposta_exec_formulario(Integer cd_resposta_exec_formulario) {
		this.cd_resposta_exec_formulario = cd_resposta_exec_formulario;
	}

	public Integer getCd_plano_acao() {
		return cd_plano_acao;
	}

	public void setCd_plano_acao(Integer cd_plano_acao) {
		this.cd_plano_acao = cd_plano_acao;
	}

	public Date getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(Date dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public Plano_acao getPlano_acao() {
		return plano_acao;
	}

	public void setPlano_acao(Plano_acao plano_acao) {
		this.plano_acao = plano_acao;
	}
	
}