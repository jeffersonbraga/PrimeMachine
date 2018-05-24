package br.com.primum.modelo.documento;

import java.util.Date;
import java.util.List;

import javassist.bytecode.ByteArray;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.checagem_documento.Checagem_documento;
import br.com.primum.modelo.config_impressao.Config_impressao;
import br.com.primum.modelo.notificacao.Notificacao;
import br.com.primum.modelo.permissao_acesso_documento.Permissao_acesso_documento;
import br.com.primum.modelo.setor.Setor;



@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="documento")
public class Documento {

	@Id
	@Column
	private Integer cd_documento;

	@Column
	private Integer cd_setor;

	@Column
	private String nm_documento;

	@Column
	private String ds_documento;

	@Column
	private Integer cd_sessaodocumento;

	@Column
	private Integer st_documento;

	@Column
	private Integer tp_disponibilidade;

	@Transient
	private ByteArray url_documento;

	/******/
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_criacao;

	@Column
	private Integer cd_responsavel_criar;

	@Column
	private String ds_copia_controlada;

	@Column
	private String ds_copia_nao_controlada;
	
	@Column
	private String ds_cc_impressao;
	
	@Column
	private String ds_cnc_impressao;
	
	@Column
	private String ds_cc_visualizar;
	
	@Column
	private String ds_cnc_visualizar;
	
	@Column
	private Integer tp_documento;

	@Column
	private Integer tp_processo;

	@Column
	private Integer nr_sequencial;

	@Column
	private Integer nr_revisao;

	@Column
	private String ds_historico_revisao;

	@Column
	private Integer cd_resp_qualidade;

	@Column
	private Integer cd_resp_setor;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_aprovacao_qualidade;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_aprovacao_setor;

	@Column
	private Integer tp_aprovado;

	@Column
	private String ds_parecer;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.permissao_acesso_documento.Permissao_acesso_documento", campoBuscar="cd_documento", persistir=true)
	private List<Permissao_acesso_documento> listaPermissoes;

	@Transient
	@ObjetoBusca(notificador=true, caminhoClasseJava="br.com.primum.modelo.notificacao.Notificacao", campoBuscar="cd_codigo_parametro", campoComparar="cd_documento", persistir=true)
	private Notificacao notificacao;

	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.setor.Setor", campoBuscar="cd_setor", campoComparar="cd_setor", persistir=false)
	private Setor setor;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.checagem_documento.Checagem_documento", campoBuscar="cd_documento", persistir=true)
	private List<Checagem_documento> listaChecagem_documento;

	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.config_impressao.Config_impressao", campoBuscar="cd_documento", campoComparar="cd_documento", persistir=true)
	private Config_impressao config_impressao;

	public Notificacao getNotificacao() {
		return notificacao;
	}

	public void setNotificacao(Notificacao notificacao) {
		this.notificacao = notificacao;
	}

	public String getDs_parecer() {
		return ds_parecer;
	}

	public void setDs_parecer(String ds_parecer) {
		this.ds_parecer = ds_parecer;
	}

	public List<Permissao_acesso_documento> getListaPermissoes() {
		return listaPermissoes;
	}

	public void setListaPermissoes(List<Permissao_acesso_documento> listaPermissoes) {
		this.listaPermissoes = listaPermissoes;
	}

	public Integer getTp_aprovado() {
		return tp_aprovado;
	}

	public void setTp_aprovado(Integer tp_aprovado) {
		this.tp_aprovado = tp_aprovado;
	}

	public Integer getCd_documento() {
		return cd_documento;
	}

	public void setCd_documento(Integer cd_documento) {
		this.cd_documento = cd_documento;
	}

	public String getNm_documento() {
		return nm_documento;
	}

	public void setNm_documento(String nm_documento) {
		this.nm_documento = nm_documento;
	}

	public String getDs_documento() {
		return ds_documento;
	}

	public void setDs_documento(String ds_documento) {
		this.ds_documento = ds_documento;
	}

	public Integer getCd_sessaodocumento() {
		return cd_sessaodocumento;
	}

	public void setCd_sessaodocumento(Integer cd_sessaodocumento) {
		this.cd_sessaodocumento = cd_sessaodocumento;
	}

	public Integer getSt_documento() {
		return st_documento;
	}

	public void setSt_documento(Integer st_documento) {
		this.st_documento = st_documento;
	}

	public Integer getNr_sequencial() {
		return nr_sequencial;
	}

	public void setNr_sequencial(Integer nr_sequencial) {
		this.nr_sequencial = nr_sequencial;
	}

	public String getDs_cc_impressao() {
		return ds_cc_impressao;
	}

	public void setDs_cc_impressao(String ds_cc_impressao) {
		this.ds_cc_impressao = ds_cc_impressao;
	}

	public String getDs_cnc_impressao() {
		return ds_cnc_impressao;
	}

	public void setDs_cnc_impressao(String ds_cnc_impressao) {
		this.ds_cnc_impressao = ds_cnc_impressao;
	}

	public String getDs_cc_visualizar() {
		return ds_cc_visualizar;
	}

	public void setDs_cc_visualizar(String ds_cc_visualizar) {
		this.ds_cc_visualizar = ds_cc_visualizar;
	}

	public String getDs_cnc_visualizar() {
		return ds_cnc_visualizar;
	}

	public void setDs_cnc_visualizar(String ds_cnc_visualizar) {
		this.ds_cnc_visualizar = ds_cnc_visualizar;
	}

	public Integer getTp_disponibilidade() {
		return tp_disponibilidade;
	}

	public void setTp_disponibilidade(Integer tp_disponibilidade) {
		this.tp_disponibilidade = tp_disponibilidade;
	}

	public Date getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(Date dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public Integer getCd_responsavel_criar() {
		return cd_responsavel_criar;
	}

	public void setCd_responsavel_criar(Integer cd_responsavel_criar) {
		this.cd_responsavel_criar = cd_responsavel_criar;
	}

	public String getDs_copia_controlada() {
		return ds_copia_controlada;
	}

	public void setDs_copia_controlada(String ds_copia_controlada) {
		this.ds_copia_controlada = ds_copia_controlada;
	}

	public String getDs_copia_nao_controlada() {
		return ds_copia_nao_controlada;
	}

	public void setDs_copia_nao_controlada(String ds_copia_nao_controlada) {
		this.ds_copia_nao_controlada = ds_copia_nao_controlada;
	}

	public Integer getTp_documento() {
		return tp_documento;
	}

	public void setTp_documento(Integer tp_documento) {
		this.tp_documento = tp_documento;
	}

	public Integer getTp_processo() {
		return tp_processo;
	}

	public void setTp_processo(Integer tp_processo) {
		this.tp_processo = tp_processo;
	}


	public Integer getNr_revisao() {
		return nr_revisao;
	}

	public void setNr_revisao(Integer nr_revisao) {
		this.nr_revisao = nr_revisao;
	}

	public String getDs_historico_revisao() {
		return ds_historico_revisao;
	}

	public void setDs_historico_revisao(String ds_historico_revisao) {
		this.ds_historico_revisao = ds_historico_revisao;
	}

	public Integer getCd_resp_qualidade() {
		return cd_resp_qualidade;
	}

	public void setCd_resp_qualidade(Integer cd_resp_qualidade) {
		this.cd_resp_qualidade = cd_resp_qualidade;
	}

	public Integer getCd_resp_setor() {
		return cd_resp_setor;
	}

	public void setCd_resp_setor(Integer cd_resp_setor) {
		this.cd_resp_setor = cd_resp_setor;
	}

	public Date getDt_aprovacao_qualidade() {
		return dt_aprovacao_qualidade;
	}

	public void setDt_aprovacao_qualidade(Date dt_aprovacao_qualidade) {
		this.dt_aprovacao_qualidade = dt_aprovacao_qualidade;
	}

	public Date getDt_aprovacao_setor() {
		return dt_aprovacao_setor;
	}

	public void setDt_aprovacao_setor(Date dt_aprovacao_setor) {
		this.dt_aprovacao_setor = dt_aprovacao_setor;
	}

	public Integer getCd_setor() {
		return cd_setor;
	}

	public void setCd_setor(Integer cd_setor) {
		this.cd_setor = cd_setor;
	}

	public Setor getSetor() {
		return setor;
	}

	public void setSetor(Setor setor) {
		this.setor = setor;
	}

	public List<Checagem_documento> getListaChecagem_documento() {
		return listaChecagem_documento;
	}

	public void setListaChecagem_documento(
			List<Checagem_documento> listaChecagem_documento) {
		this.listaChecagem_documento = listaChecagem_documento;
	}

	public Config_impressao getConfig_impressao() {
		return config_impressao;
	}

	public void setConfig_impressao(Config_impressao config_impressao) {
		this.config_impressao = config_impressao;
	}

	public ByteArray getUrl_documento() {
		return url_documento;
	}

	public void setUrl_documento(ByteArray url_documento) {
		this.url_documento = url_documento;
	}
}