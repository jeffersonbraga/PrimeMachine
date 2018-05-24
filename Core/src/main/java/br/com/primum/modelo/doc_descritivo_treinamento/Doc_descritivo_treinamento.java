package br.com.primum.modelo.doc_descritivo_treinamento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="doc_descritivo_treinamento")
public class Doc_descritivo_treinamento {

	@Id
	@Column
	private Integer cd_doc_descritivo_treinamento;

	@Column
	private Integer cd_descritivo_treinamento;

	@Column
	private String ds_documento;

	@Column
	private String ds_local_armazenamento;


	public Integer getCd_doc_descritivo_treinamento() {
		return cd_doc_descritivo_treinamento;
	}

	public void setCd_doc_descritivo_treinamento(
			Integer cd_doc_descritivo_treinamento) {
		this.cd_doc_descritivo_treinamento = cd_doc_descritivo_treinamento;
	}

	public Integer getCd_descritivo_treinamento() {
		return cd_descritivo_treinamento;
	}

	public void setCd_descritivo_treinamento(Integer cd_descritivo_treinamento) {
		this.cd_descritivo_treinamento = cd_descritivo_treinamento;
	}

	public String getDs_documento() {
		return ds_documento;
	}

	public void setDs_documento(String ds_documento) {
		this.ds_documento = ds_documento;
	}

	public String getDs_local_armazenamento() {
		return ds_local_armazenamento;
	}

	public void setDs_local_armazenamento(String ds_local_armazenamento) {
		this.ds_local_armazenamento = ds_local_armazenamento;
	}
}