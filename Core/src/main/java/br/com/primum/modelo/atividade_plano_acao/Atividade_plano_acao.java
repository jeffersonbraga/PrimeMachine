package br.com.primum.modelo.atividade_plano_acao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name = "atividade_plano_acao")
public class Atividade_plano_acao implements Serializable {

	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//8704568763762205573L;

	@Id
	private Integer cd_atividade_plano_acao;

	@Column
	private Integer cd_plano_acao;

	@Column
	private Integer cd_atividades;

	@Column
	private String ds_observacao;

	@Column
	private Integer cd_responsavel_execucao;

	@Column
	private Integer st_atividade_plano_acao;

	@Column
	private String ds_caminho_evidencia;


	public Integer getCd_atividade_plano_acao() {
		return cd_atividade_plano_acao;
	}

	public void setCd_atividade_plano_acao(Integer cd_atividade_plano_acao) {
		this.cd_atividade_plano_acao = cd_atividade_plano_acao;
	}

	public Integer getCd_plano_acao() {
		return cd_plano_acao;
	}

	public void setCd_plano_acao(Integer cd_plano_acao) {
		this.cd_plano_acao = cd_plano_acao;
	}

	public Integer getCd_atividades() {
		return cd_atividades;
	}

	public void setCd_atividades(Integer cd_atividades) {
		this.cd_atividades = cd_atividades;
	}

	public String getDs_observacao() {
		return ds_observacao;
	}

	public void setDs_observacao(String ds_observacao) {
		this.ds_observacao = ds_observacao;
	}

	public Integer getSt_atividade_plano_acao() {
		return st_atividade_plano_acao;
	}

	public void setSt_atividade_plano_acao(Integer st_atividade_plano_acao) {
		this.st_atividade_plano_acao = st_atividade_plano_acao;
	}

	public Integer getCd_responsavel_execucao() {
		return cd_responsavel_execucao;
	}

	public void setCd_responsavel_execucao(Integer cd_responsavel_execucao) {
		this.cd_responsavel_execucao = cd_responsavel_execucao;
	}

	public String getDs_caminho_evidencia() {
		return ds_caminho_evidencia;
	}

	public void setDs_caminho_evidencia(String ds_caminho_evidencia) {
		this.ds_caminho_evidencia = ds_caminho_evidencia;
	}
}