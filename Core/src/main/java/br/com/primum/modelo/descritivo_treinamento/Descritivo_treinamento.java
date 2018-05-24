package br.com.primum.modelo.descritivo_treinamento;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.doc_descritivo_treinamento.Doc_descritivo_treinamento;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="descritivo_treinamento")
public class Descritivo_treinamento {

	@Id
	@Column
	private Integer cd_descritivo_treinamento;

	@Column
	private String nm_descritivo_treinamento;

	@Column
	private String ds_objetivo;

	@Column
	private String ds_interessados;

	@Column
	private String ds_quando;

	@Column
	private String ds_como;

	@Column
	private String ds_conteudo;

	@Column
	private Integer nr_carga_horaria;

	@Column
	private String ds_metodologia_eficacia;

	@Column
	private String ds_parecer_avaliacao;

	@Transient
	private String nm_documento;
	
	@Transient
	private byte[] url_documento;
	
	
	@Transient
	@ListaBusca(persistir=true, caminhoClasseJava="br.com.primum.modelo.doc_descritivo_treinamento.Doc_descritivo_treinamento", campoBuscar="cd_descritivo_treinamento")
	private List<Doc_descritivo_treinamento> listaDocumentosDescritivo;


	public List<Doc_descritivo_treinamento> getListaDocumentosDescritivo() {
		return listaDocumentosDescritivo;
	}

	public void setListaDocumentosDescritivo(
			List<Doc_descritivo_treinamento> listaDocumentosDescritivo) {
		this.listaDocumentosDescritivo = listaDocumentosDescritivo;
	}


	public String getNm_documento() {
		return nm_documento;
	}


	public void setNm_documento(String nm_documento) {
		this.nm_documento = nm_documento;
	}

	
	public Integer getCd_descritivo_treinamento() {
		return cd_descritivo_treinamento;
	}

	public void setCd_descritivo_treinamento(Integer cd_descritivo_treinamento) {
		this.cd_descritivo_treinamento = cd_descritivo_treinamento;
	}


	public String getNm_descritivo_treinamento() {
		return nm_descritivo_treinamento;
	}


	public void setNm_descritivo_treinamento(String nm_descritivo_treinamento) {
		this.nm_descritivo_treinamento = nm_descritivo_treinamento;
	}


	public String getDs_objetivo() {
		return ds_objetivo;
	}


	public void setDs_objetivo(String ds_objetivo) {
		this.ds_objetivo = ds_objetivo;
	}


	public String getDs_interessados() {
		return ds_interessados;
	}


	public void setDs_interessados(String ds_interessados) {
		this.ds_interessados = ds_interessados;
	}


	public String getDs_quando() {
		return ds_quando;
	}


	public void setDs_quando(String ds_quando) {
		this.ds_quando = ds_quando;
	}


	public String getDs_como() {
		return ds_como;
	}


	public void setDs_como(String ds_como) {
		this.ds_como = ds_como;
	}


	public String getDs_conteudo() {
		return ds_conteudo;
	}


	public void setDs_conteudo(String ds_conteudo) {
		this.ds_conteudo = ds_conteudo;
	}


	public Integer getNr_carga_horaria() {
		return nr_carga_horaria;
	}


	public void setNr_carga_horaria(Integer nr_carga_horaria) {
		this.nr_carga_horaria = nr_carga_horaria;
	}


	public String getDs_metodologia_eficacia() {
		return ds_metodologia_eficacia;
	}


	public void setDs_metodologia_eficacia(String ds_metodologia_eficacia) {
		this.ds_metodologia_eficacia = ds_metodologia_eficacia;
	}


	public String getDs_parecer_avaliacao() {
		return ds_parecer_avaliacao;
	}


	public void setDs_parecer_avaliacao(String ds_parecer_avaliacao) {
		this.ds_parecer_avaliacao = ds_parecer_avaliacao;
	}


	public byte[] getUrl_documento() {
		return url_documento;
	}


	public void setUrl_documento(byte[] url_documento) {
		this.url_documento = url_documento;
	}

}