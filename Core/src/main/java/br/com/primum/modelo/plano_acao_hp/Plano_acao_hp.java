package br.com.primum.modelo.plano_acao_hp;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.acao_plano_acao_hp.Acao_plano_acao_hp;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="plano_acao_hp")
public class Plano_acao_hp {

	@Id
	@Column
	private Integer cd_plano_acao_hp;

	@Column
	private Integer cd_causa_efeito;
	
	@Column
	private String ds_protocolo;
	
	@Column
	private String ds_observacoes;
	
	@Column
	private Integer vl_prioridade;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_criacao;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_prazo_previsto;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_realizado;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_verificacao;
	
	@Column
	private Integer tp_parecer_tecnico;
	
	@Column
	private Integer cd_usuario_responsavel;
	
	@Column
	private Integer cd_usuario_validacao;
	
	@Column
	private Integer st_plano_acao;
	
	@Column
	private Integer cd_usuario_abrir;

	@Column
	private String ds_caminho_evidencia;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.acao_plano_acao_hp.Acao_plano_acao_hp", campoBuscar="cd_plano_acao_hp", persistir=true)
	private List<Acao_plano_acao_hp> listaAcoes;
	
	@Transient
	private Integer cd_helpdesk_ocorrencia;
	
	
	public Integer getCd_helpdesk_ocorrencia() {
		return cd_helpdesk_ocorrencia;
	}

	public void setCd_helpdesk_ocorrencia(Integer cd_helpdesk_ocorrencia) {
		this.cd_helpdesk_ocorrencia = cd_helpdesk_ocorrencia;
	}

	public Integer getCd_plano_acao_hp() {
		return cd_plano_acao_hp;
	}

	public void setCd_plano_acao_hp(Integer cd_plano_acao_hp) {
		this.cd_plano_acao_hp = cd_plano_acao_hp;
	}

	public Integer getCd_causa_efeito() {
		return cd_causa_efeito;
	}

	public void setCd_causa_efeito(Integer cd_causa_efeito) {
		this.cd_causa_efeito = cd_causa_efeito;
	}

	public String getDs_protocolo() {
		return ds_protocolo;
	}

	public void setDs_protocolo(String ds_protocolo) {
		this.ds_protocolo = ds_protocolo;
	}

	public String getDs_observacoes() {
		return ds_observacoes;
	}

	public void setDs_observacoes(String ds_observacoes) {
		this.ds_observacoes = ds_observacoes;
	}

	public Integer getVl_prioridade() {
		return vl_prioridade;
	}

	public void setVl_prioridade(Integer vl_prioridade) {
		this.vl_prioridade = vl_prioridade;
	}

	public Date getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(Date dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public Date getDt_prazo_previsto() {
		return dt_prazo_previsto;
	}

	public void setDt_prazo_previsto(Date dt_prazo_previsto) {
		this.dt_prazo_previsto = dt_prazo_previsto;
	}

	public Date getDt_realizado() {
		return dt_realizado;
	}

	public void setDt_realizado(Date dt_realizado) {
		this.dt_realizado = dt_realizado;
	}

	public Date getDt_verificacao() {
		return dt_verificacao;
	}

	public void setDt_verificacao(Date dt_verificacao) {
		this.dt_verificacao = dt_verificacao;
	}

	public Integer getTp_parecer_tecnico() {
		return tp_parecer_tecnico;
	}

	public void setTp_parecer_tecnico(Integer tp_parecer_tecnico) {
		this.tp_parecer_tecnico = tp_parecer_tecnico;
	}

	public Integer getCd_usuario_responsavel() {
		return cd_usuario_responsavel;
	}

	public void setCd_usuario_responsavel(Integer cd_usuario_responsavel) {
		this.cd_usuario_responsavel = cd_usuario_responsavel;
	}

	public Integer getCd_usuario_validacao() {
		return cd_usuario_validacao;
	}

	public void setCd_usuario_validacao(Integer cd_usuario_validacao) {
		this.cd_usuario_validacao = cd_usuario_validacao;
	}

	public Integer getSt_plano_acao() {
		return st_plano_acao;
	}

	public void setSt_plano_acao(Integer st_plano_acao) {
		this.st_plano_acao = st_plano_acao;
	}

	public Integer getCd_usuario_abrir() {
		return cd_usuario_abrir;
	}

	public void setCd_usuario_abrir(Integer cd_usuario_abrir) {
		this.cd_usuario_abrir = cd_usuario_abrir;
	}

	public String getDs_caminho_evidencia() {
		return ds_caminho_evidencia;
	}

	public void setDs_caminho_evidencia(String ds_caminho_evidencia) {
		this.ds_caminho_evidencia = ds_caminho_evidencia;
	}

	public List<Acao_plano_acao_hp> getListaAcoes() {
		return listaAcoes;
	}

	public void setListaAcoes(List<Acao_plano_acao_hp> listaAcoes) {
		this.listaAcoes = listaAcoes;
	}

}
