package br.com.primum.modelo.revisao_programa_qualidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;
import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.doc_revisao_formulario.Doc_revisao_formulario;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="revisao_programa_qualidade")
public class Revisao_programa_qualidade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//7229593858862785983L;

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_revisao_programa_qualidade;

	@Column
	private Integer cd_programa_qualidade;

	@Column
	private Integer cd_fiscal_responsavel;

	@Column
	private String nm_revisao_programa_qualidade;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_revisao_programa_qualidade;

	@Column
	private Integer st_revisao_programa_qualidade;

	@Column
	private String ds_revisao_programa_qualidade;
	
	@Transient
	@ListaBusca(persistir=true, caminhoClasseJava="br.com.primum.modelo.doc_revisao_formulario.Doc_revisao_formulario", campoBuscar="cd_revisao_programa_qualidade")
	private List<Doc_revisao_formulario> listaDocumentos;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_parecer_programa_qualidade;
	
	@Column
	private Integer tp_parecer_programa_qualidade;
	
	@Column
	private String ds_observacao;
	
	@Column
	private Integer cd_revisao_origem;
	
	@Column
	private Integer cd_unidade;

	@Column
	private Integer cd_usuario_elaborador;

	@Transient
	public static Integer PLANEJAMENTO 		= 0;

	@Transient
	public static Integer AGUARDA_APROVACAO = 1;

	@Transient
	public static Integer REVISAO_APROVADA 	= 2;

	@Transient
	public static Integer REVISAO_REPROVADA = 3;

	@Transient
	public static Integer REVISAO_ARQUIVADA = 4;

//	@Column
//	private Integer st_nova_revisao;
//	
//	
//	public Integer getSt_nova_revisao() {
//		return st_nova_revisao;
//	}
//
//	public void setSt_nova_revisao(Integer st_nova_revisao) {
//		this.st_nova_revisao = st_nova_revisao;
//	}

	public Integer getCd_revisao_origem() {
		return cd_revisao_origem;
	}

	public void setCd_revisao_origem(Integer cd_revisao_origem) {
		this.cd_revisao_origem = cd_revisao_origem;
	}

	public Date getDt_parecer_programa_qualidade() {
		return dt_parecer_programa_qualidade;
	}

	public void setDt_parecer_programa_qualidade(Date dt_parecer_programa_qualidade) {
		this.dt_parecer_programa_qualidade = dt_parecer_programa_qualidade;
	}

	public Integer getTp_parecer_programa_qualidade() {
		return tp_parecer_programa_qualidade;
	}

	public void setTp_parecer_programa_qualidade(
			Integer tp_parecer_programa_qualidade) {
		this.tp_parecer_programa_qualidade = tp_parecer_programa_qualidade;
	}

	public String getDs_observacao() {
		return ds_observacao;
	}

	public void setDs_observacao(String ds_observacao) {
		this.ds_observacao = ds_observacao;
	}

	public List<Doc_revisao_formulario> getListaDocumentos() {
		return listaDocumentos;
	}

	public void setListaDocumentos(
			List<Doc_revisao_formulario> listaDocumentos) {
		this.listaDocumentos = listaDocumentos;
	}

	public Integer getCd_revisao_programa_qualidade() {
		return cd_revisao_programa_qualidade;
	}

	public void setCd_revisao_programa_qualidade(
			Integer cd_revisao_programa_qualidade) {
		this.cd_revisao_programa_qualidade = cd_revisao_programa_qualidade;
	}

	public Integer getCd_programa_qualidade() {
		return cd_programa_qualidade;
	}

	public void setCd_programa_qualidade(Integer cd_programa_qualidade) {
		this.cd_programa_qualidade = cd_programa_qualidade;
	}

	public String getNm_revisao_programa_qualidade() {
		return nm_revisao_programa_qualidade;
	}

	public void setNm_revisao_programa_qualidade(
			String nm_revisao_programa_qualidade) {
		this.nm_revisao_programa_qualidade = nm_revisao_programa_qualidade;
	}

	public Date getDt_revisao_programa_qualidade() {
		return dt_revisao_programa_qualidade;
	}

	public void setDt_revisao_programa_qualidade(Date dt_revisao_programa_qualidade) {
		this.dt_revisao_programa_qualidade = dt_revisao_programa_qualidade;
	}

	public Integer getSt_revisao_programa_qualidade() {
		return st_revisao_programa_qualidade;
	}

	public void setSt_revisao_programa_qualidade(
			Integer st_revisao_programa_qualidade) {
		this.st_revisao_programa_qualidade = st_revisao_programa_qualidade;
	}

	public String getDs_revisao_programa_qualidade() {
		return ds_revisao_programa_qualidade;
	}

	public void setDs_revisao_programa_qualidade(
			String ds_revisao_programa_qualidade) {
		this.ds_revisao_programa_qualidade = ds_revisao_programa_qualidade;
	}

	public Integer getCd_fiscal_responsavel() {
		return cd_fiscal_responsavel;
	}

	public void setCd_fiscal_responsavel(Integer cd_fiscal_responsavel) {
		this.cd_fiscal_responsavel = cd_fiscal_responsavel;
	}

	public Integer getCd_usuario_elaborador() {
		return cd_usuario_elaborador;
	}

	public void setCd_usuario_elaborador(Integer cd_usuario_elaborador) {
		this.cd_usuario_elaborador = cd_usuario_elaborador;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}
	
	
}