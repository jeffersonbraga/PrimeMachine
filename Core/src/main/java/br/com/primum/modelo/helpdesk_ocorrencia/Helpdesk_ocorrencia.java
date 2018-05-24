package br.com.primum.modelo.helpdesk_ocorrencia;

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
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.acao_corretiva_helpdesk.Acao_corretiva_helpdesk;
import br.com.primum.modelo.diagrama_helpdesk.Diagrama_helpdesk;
import br.com.primum.modelo.evidencia_helpdesk.Evidencia_helpdesk;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="helpdesk_ocorrencia")
public class Helpdesk_ocorrencia {

	@Id
	@Column
	private Integer cd_helpdesk_ocorrencia;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_criacao;
	
	@Column
	private Integer cd_classificacao_helpdesk;
	
	@Column
	private Integer cd_natureza_ocorrencia;
	
	@Column
	private Integer st_helpdesk_ocorrencia;
	
	@Column
	private String ds_ocorrencia;
	
	@Column
	private Integer cd_unidade;
	
	@Column
	private Integer cd_responsavel_ocorrencia;
	
	@Column
	private Integer cd_unidade_registro;
	
	@Column
	private Integer cd_responsavel_registro;
	
	
	@Column
	private String ds_resp_tecnica;
	
	@Column
	private String ds_resp_usuario;
	
	@Column
	private String ds_observacoes;
	
	@Column
	private Integer tp_parecer_reclamante;
	
	@Column
	private Integer cd_resp_resolucao;
	
	@Column
	private Integer cd_resp_salvar;

	@Column
	private Integer cd_resp_analise;
	
	@Column
	private Integer tp_notificados;
	
	@Column
	private String ds_historico;
	
	@Column
	private Integer cd_setor;
	
	
	@Transient
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.diagrama_helpdesk.Diagrama_helpdesk", campoBuscar="cd_helpdesk_ocorrencia", campoComparar="cd_helpdesk_ocorrencia", persistir=true,notificador=false)
	private Diagrama_helpdesk diagrama_helpdesk;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.acao_corretiva_helpdesk.Acao_corretiva_helpdesk", campoBuscar="cd_helpdesk_ocorrencia", persistir=true)
	private List<Acao_corretiva_helpdesk> listaAcoesImediatas;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.evidencia_helpdesk.Evidencia_helpdesk", campoBuscar="cd_helpdesk_ocorrencia", persistir=true)
	private List<Evidencia_helpdesk> listaEvidencias;
	
//	@Transient
//	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.categoria_ocorrencia.Categoria_ocorrencia", campoBuscar="cd_helpdesk_ocorrencia", persistir=true)
//	private List<Categoria_ocorrencia> listaCategoria_ocorrencia;

	@Column
	private Integer cd_helpdesk_origem;
	
	@Column
	private String ds_titulo;

	
//	@Transient
//	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.configuracao_helpdesk.Configuracao_helpdesk", campoBuscar="cd_classificacao_helpdesk", campoComparar="cd_classificacao_helpdesk", persistir=false,notificador=false)
//	private Configuracao_helpdesk configuracao_helpdesk;
	
	
	public Integer getCd_helpdesk_ocorrencia() {
		return cd_helpdesk_ocorrencia;
	}

	public Diagrama_helpdesk getDiagrama_helpdesk() {
		return diagrama_helpdesk;
	}

	public void setDiagrama_helpdesk(Diagrama_helpdesk diagrama_helpdesk) {
		this.diagrama_helpdesk = diagrama_helpdesk;
	}

	public void setCd_helpdesk_ocorrencia(Integer cd_helpdesk_ocorrencia) {
		this.cd_helpdesk_ocorrencia = cd_helpdesk_ocorrencia;
	}

	public Integer getSt_helpdesk_ocorrencia() {
		return st_helpdesk_ocorrencia;
	}

	public void setSt_helpdesk_ocorrencia(Integer st_helpdesk_ocorrencia) {
		this.st_helpdesk_ocorrencia = st_helpdesk_ocorrencia;
	}

	public Date getDt_criacao() {
		return dt_criacao;
	}

	public void setDt_criacao(Date dt_criacao) {
		this.dt_criacao = dt_criacao;
	}

	public Integer getCd_classificacao_helpdesk() {
		return cd_classificacao_helpdesk;
	}

	public void setCd_classificacao_helpdesk(Integer cd_classificacao_helpdesk) {
		this.cd_classificacao_helpdesk = cd_classificacao_helpdesk;
	}

	public Integer getCd_natureza_ocorrencia() {
		return cd_natureza_ocorrencia;
	}

	public void setCd_natureza_ocorrencia(Integer cd_natureza_ocorrencia) {
		this.cd_natureza_ocorrencia = cd_natureza_ocorrencia;
	}

	public String getDs_ocorrencia() {
		return ds_ocorrencia;
	}

	public void setDs_ocorrencia(String ds_ocorrencia) {
		this.ds_ocorrencia = ds_ocorrencia;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}

	public Integer getCd_responsavel_ocorrencia() {
		return cd_responsavel_ocorrencia;
	}

	public void setCd_responsavel_ocorrencia(Integer cd_responsavel_ocorrencia) {
		this.cd_responsavel_ocorrencia = cd_responsavel_ocorrencia;
	}

	public Integer getCd_unidade_registro() {
		return cd_unidade_registro;
	}

	public void setCd_unidade_registro(Integer cd_unidade_registro) {
		this.cd_unidade_registro = cd_unidade_registro;
	}

	public Integer getCd_responsavel_registro() {
		return cd_responsavel_registro;
	}

	public void setCd_responsavel_registro(Integer cd_responsavel_registro) {
		this.cd_responsavel_registro = cd_responsavel_registro;
	}

	public Integer getCd_setor() {
		return cd_setor;
	}

	public void setCd_setor(Integer cd_setor) {
		this.cd_setor = cd_setor;
	}

	public List<Acao_corretiva_helpdesk> getListaAcoesImediatas() {
		return listaAcoesImediatas;
	}

	public void setListaAcoesImediatas(
			List<Acao_corretiva_helpdesk> listaAcoesImediatas) {
		this.listaAcoesImediatas = listaAcoesImediatas;
	}

//	public List<Categoria_ocorrencia> getListaCategoria_ocorrencia() {
//		return listaCategoria_ocorrencia;
//	}
//
//	public void setListaCategoria_ocorrencia(
//			List<Categoria_ocorrencia> listaCategoria_ocorrencia) {
//		this.listaCategoria_ocorrencia = listaCategoria_ocorrencia;
//	}

	public String getDs_resp_tecnica() {
		return ds_resp_tecnica;
	}

	public void setDs_resp_tecnica(String ds_resp_tecnica) {
		this.ds_resp_tecnica = ds_resp_tecnica;
	}

	public String getDs_resp_usuario() {
		return ds_resp_usuario;
	}

	public void setDs_resp_usuario(String ds_resp_usuario) {
		this.ds_resp_usuario = ds_resp_usuario;
	}

	public String getDs_observacoes() {
		return ds_observacoes;
	}

	public void setDs_observacoes(String ds_observacoes) {
		this.ds_observacoes = ds_observacoes;
	}

	public Integer getTp_parecer_reclamante() {
		return tp_parecer_reclamante;
	}

	public void setTp_parecer_reclamante(Integer tp_parecer_reclamante) {
		this.tp_parecer_reclamante = tp_parecer_reclamante;
	}

//	public Configuracao_helpdesk getConfiguracao_helpdesk() {
//		return configuracao_helpdesk;
//	}
//
//	public void setConfiguracao_helpdesk(Configuracao_helpdesk configuracao_helpdesk) {
//		this.configuracao_helpdesk = configuracao_helpdesk;
//	}
	
	

	public Integer getCd_resp_resolucao() {
		return cd_resp_resolucao;
	}

	public Integer getCd_resp_analise() {
		return cd_resp_analise;
	}

	public void setCd_resp_analise(Integer cd_resp_analise) {
		this.cd_resp_analise = cd_resp_analise;
	}

	public void setCd_resp_resolucao(Integer cd_resp_resolucao) {
		this.cd_resp_resolucao = cd_resp_resolucao;
	}

	public Integer getCd_resp_salvar() {
		return cd_resp_salvar;
	}

	public void setCd_resp_salvar(Integer cd_resp_salvar) {
		this.cd_resp_salvar = cd_resp_salvar;
	}

	public Integer getCd_helpdesk_origem() {
		return cd_helpdesk_origem;
	}

	public void setCd_helpdesk_origem(Integer cd_helpdesk_origem) {
		this.cd_helpdesk_origem = cd_helpdesk_origem;
	}

	public Integer getTp_notificados() {
		return tp_notificados;
	}

	public void setTp_notificados(Integer tp_notificados) {
		this.tp_notificados = tp_notificados;
	}

	public String getDs_historico() {
		return ds_historico;
	}

	public void setDs_historico(String ds_historico) {
		this.ds_historico = ds_historico;
	}

	public List<Evidencia_helpdesk> getListaEvidencias() {
		return listaEvidencias;
	}

	public void setListaEvidencias(List<Evidencia_helpdesk> listaEvidencias) {
		this.listaEvidencias = listaEvidencias;
	}

	public String getDs_titulo() {
		return ds_titulo;
	}

	public void setDs_titulo(String ds_titulo) {
		this.ds_titulo = ds_titulo;
	}

}
