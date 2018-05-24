package br.com.primum.modelo.doc_revisao_formulario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.primum.contexto.ContextoAplicacao;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="doc_revisao_formulario")
public class Doc_revisao_formulario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-3667659692722846073L;

	@Id
	@Column
	private Integer cd_doc_revisao_formulario;

	@Column
	private Integer cd_revisao_programa_qualidade;

	@Column
	private String nm_doc_revisao_formulario;

	@Column
	private String ds_local_armazenamento;

	@Column
	private Boolean vl_aprovada;

	
	public Boolean getVl_aprovada() {
		return vl_aprovada;
	}

	public void setVl_aprovada(Boolean vl_aprovada) {
		this.vl_aprovada = vl_aprovada;
	}

	public Integer getCd_doc_revisao_formulario() {
		return cd_doc_revisao_formulario;
	}

	public void setCd_doc_revisao_formulario(Integer cd_doc_revisao_formulario) {
		this.cd_doc_revisao_formulario = cd_doc_revisao_formulario;
	}

	public String getNm_doc_revisao_formulario() {
		return nm_doc_revisao_formulario;
	}

	public void setNm_doc_revisao_formulario(String nm_doc_revisao_formulario) {
		this.nm_doc_revisao_formulario = nm_doc_revisao_formulario;
	}

	public Integer getCd_revisao_programa_qualidade() {
		return cd_revisao_programa_qualidade;
	}

	public void setCd_revisao_programa_qualidade(
			Integer cd_revisao_programa_qualidade) {
		this.cd_revisao_programa_qualidade = cd_revisao_programa_qualidade;
	}


	public String getDs_local_armazenamento() {
		return ds_local_armazenamento;
	}

	public void setDs_local_armazenamento(String ds_local_armazenamento) {
		this.ds_local_armazenamento = ds_local_armazenamento;
	}
	
	
}
