package br.com.primum.modelo.plano_acao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.acao_corretiva.Acao_corretiva;
import br.com.primum.modelo.acao_corretiva_pa.Acao_corretiva_pa;
import br.com.primum.modelo.acao_preventiva.Acao_preventiva;
import br.com.primum.modelo.acao_preventiva_pa.Acao_preventiva_pa;
import br.com.primum.modelo.atividade_plano_acao.Atividade_plano_acao;
import br.com.primum.modelo.plano_acao_nc.Plano_acao_nc;
import br.com.primum.modelo.setor.Setor;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="plano_acao")
public class Plano_acao implements Serializable {

	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-8684551501912259704L;

	@Id
	private Integer cd_plano_acao;

	@Column
	private String ds_protocolo;

	@Column
	private Integer vl_prioridade;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_criacao;

	@Column
	private String ds_anomalia;

	@Column
	private String ds_causa_provavel;

	@Column
	private String ds_destino_produto;

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
	private String vl_verificacao;

	@Column
	private Integer tp_parecer_tecnico;

	@Column
	private Integer cd_setor_ocorrencia;

//	@Column
//	private Integer cd_acao_corretiva;
//
//	@Column
//	private Integer cd_acao_preventiva;

	@Column
	private Integer cd_setor_responsavel;

	@Column
	private Integer cd_usuario_execucao;

	@Column
	private Integer cd_usuario_validacao;
	
	@Column
	private Integer cd_usuario_abrir;
	
	@Column
	private Integer st_plano_acao;
	
	@Column
	private Integer cd_unidade;
	
	@Column
	private String ds_caminho_evidencia;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_repeticao;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.atividade_plano_acao.Atividade_plano_acao", persistir=true, campoBuscar="cd_plano_acao")
	private List<Atividade_plano_acao> listaAtividade_plano_acao;
	
	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.setor.Setor",persistir=false, campoBuscar="cd_setor", campoComparar="cd_setor_ocorrencia")
	private Setor setor_ocorrencia;
	
	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.setor.Setor",persistir=false, campoBuscar="cd_setor", campoComparar="cd_setor_responsavel")
	private Setor setor_responsavel;

//	@Transient
//	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.plano_acao_nc.Plano_acao_nc",persistir=false, campoBuscar="cd_plano_acao_executar", campoComparar="cd_plano_acao")
//	private Plano_acao_nc plano_acao_nc_executar;

	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.plano_acao_nc.Plano_acao_nc",persistir=true, campoBuscar="cd_plano_acao_origem", campoComparar="cd_plano_acao")
	private Plano_acao_nc plano_acao_nc_origem;
	
	
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.acao_corretiva_pa.Acao_corretiva_pa", persistir=true, campoBuscar="cd_plano_acao")
	private List<Acao_corretiva_pa> listaAcoesCorretivaProcesso;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.acao_preventiva_pa.Acao_preventiva_pa", persistir=true, campoBuscar="cd_plano_acao")
	private List<Acao_preventiva_pa> listaAcoesCorretivaProduto;
	

//	public Plano_acao_nc getPlano_acao_nc_executar() {
//		return plano_acao_nc_executar;
//	}
//
//	public void setPlano_acao_nc_executar(Plano_acao_nc plano_acao_nc_executar) {
//		this.plano_acao_nc_executar = plano_acao_nc_executar;
//	}

	public Plano_acao_nc getPlano_acao_nc_origem() {
		return plano_acao_nc_origem;
	}

	public void setPlano_acao_nc_origem(Plano_acao_nc plano_acao_nc_origem) {
		this.plano_acao_nc_origem = plano_acao_nc_origem;
	}

	public Setor getSetor_responsavel() {
		return setor_responsavel;
	}

	public void setSetor_responsavel(Setor setor_responsavel) {
		this.setor_responsavel = setor_responsavel;
	}

	public Integer getCd_plano_acao() {
		return cd_plano_acao;
	}

	public void setCd_plano_acao(Integer cd_plano_acao) {
		this.cd_plano_acao = cd_plano_acao;
	}

	public String getDs_protocolo() {
		return ds_protocolo;
	}

	public void setDs_protocolo(String ds_protocolo) {
		this.ds_protocolo = ds_protocolo;
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

	public String getDs_anomalia() {
		return ds_anomalia;
	}

	public void setDs_anomalia(String ds_anomalia) {
		this.ds_anomalia = ds_anomalia;
	}

	public String getDs_causa_provavel() {
		return ds_causa_provavel;
	}

	public void setDs_causa_provavel(String ds_causa_provavel) {
		this.ds_causa_provavel = ds_causa_provavel;
	}

	public String getDs_destino_produto() {
		return ds_destino_produto;
	}

	public void setDs_destino_produto(String ds_destino_produto) {
		this.ds_destino_produto = ds_destino_produto;
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

	public String getVl_verificacao() {
		return vl_verificacao;
	}

	public void setVl_verificacao(String vl_verificacao) {
		this.vl_verificacao = vl_verificacao;
	}

	public Integer getTp_parecer_tecnico() {
		return tp_parecer_tecnico;
	}

	public void setTp_parecer_tecnico(Integer tp_parecer_tecnico) {
		this.tp_parecer_tecnico = tp_parecer_tecnico;
	}

	public Integer getCd_setor_ocorrencia() {
		return cd_setor_ocorrencia;
	}

	public void setCd_setor_ocorrencia(Integer cd_setor_ocorrencia) {
		this.cd_setor_ocorrencia = cd_setor_ocorrencia;
	}

	
	public Integer getCd_setor_responsavel() {
		return cd_setor_responsavel;
	}

	public void setCd_setor_responsavel(Integer cd_setor_responsavel) {
		this.cd_setor_responsavel = cd_setor_responsavel;
	}

	public Integer getCd_usuario_execucao() {
		return cd_usuario_execucao;
	}

	public void setCd_usuario_execucao(Integer cd_usuario_execucao) {
		this.cd_usuario_execucao = cd_usuario_execucao;
	}

	public Integer getCd_usuario_validacao() {
		return cd_usuario_validacao;
	}

	public void setCd_usuario_validacao(Integer cd_usuario_validacao) {
		this.cd_usuario_validacao = cd_usuario_validacao;
	}

	public Integer getCd_usuario_abrir() {
		return cd_usuario_abrir;
	}

	public void setCd_usuario_abrir(Integer cd_usuario_abrir) {
		this.cd_usuario_abrir = cd_usuario_abrir;
	}

	public Integer getSt_plano_acao() {
		return st_plano_acao;
	}

	public void setSt_plano_acao(Integer st_plano_acao) {
		this.st_plano_acao = st_plano_acao;
	}

	public List<Atividade_plano_acao> getListaAtividade_plano_acao() {
		return listaAtividade_plano_acao;
	}

	public void setListaAtividade_plano_acao(
			List<Atividade_plano_acao> listaAtividade_plano_acao) {
		this.listaAtividade_plano_acao = listaAtividade_plano_acao;
	}

	public Setor getSetor_ocorrencia() {
		return setor_ocorrencia;
	}

	public void setSetor_ocorrencia(Setor setor_ocorrencia) {
		this.setor_ocorrencia = setor_ocorrencia;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}

	public String getDs_caminho_evidencia() {
		return ds_caminho_evidencia;
	}

	public void setDs_caminho_evidencia(String ds_caminho_evidencia) {
		this.ds_caminho_evidencia = ds_caminho_evidencia;
	}

	public Date getDt_repeticao() {
		return dt_repeticao;
	}

	public void setDt_repeticao(Date dt_repeticao) {
		this.dt_repeticao = dt_repeticao;
	}

	public List<Acao_corretiva_pa> getListaAcoesCorretivaProcesso() {
		return listaAcoesCorretivaProcesso;
	}

	public void setListaAcoesCorretivaProcesso(
			List<Acao_corretiva_pa> listaAcoesCorretivaProcesso) {
		this.listaAcoesCorretivaProcesso = listaAcoesCorretivaProcesso;
	}

	public List<Acao_preventiva_pa> getListaAcoesCorretivaProduto() {
		return listaAcoesCorretivaProduto;
	}

	public void setListaAcoesCorretivaProduto(
			List<Acao_preventiva_pa> listaAcoesCorretivaProduto) {
		this.listaAcoesCorretivaProduto = listaAcoesCorretivaProduto;
	}

}