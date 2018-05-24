package br.com.primum.modelo.sessaodocumento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="sessaodocumento")
public class SessaoDocumento {

	@Id
	@Column
	private Integer cd_sessaodocumento;
	
	@Column
	private String nm_sessaodocumento;
	
	@Column
	private String ds_sessaodocumento;
	
	@Column
	private Integer cd_departamento;
	
	@Column
	private Integer st_sessaodocumento;
	
	@Column
	private Integer cd_unidade;

	public Integer getCd_sessaodocumento() {
		return cd_sessaodocumento;
	}

	public void setCd_sessaodocumento(Integer cd_sessaodocumento) {
		this.cd_sessaodocumento = cd_sessaodocumento;
	}

	public String getNm_sessaodocumento() {
		return nm_sessaodocumento;
	}

	public void setNm_sessaodocumento(String nm_sessaodocumento) {
		this.nm_sessaodocumento = nm_sessaodocumento;
	}

	public String getDs_sessaodocumento() {
		return ds_sessaodocumento;
	}

	public void setDs_sessaodocumento(String ds_sessaodocumento) {
		this.ds_sessaodocumento = ds_sessaodocumento;
	}

	public Integer getCd_departamento() {
		return cd_departamento;
	}

	public void setCd_departamento(Integer cd_departamento) {
		this.cd_departamento = cd_departamento;
	}

	public Integer getSt_sessaodocumento() {
		return st_sessaodocumento;
	}

	public void setSt_sessaodocumento(Integer st_sessaodocumento) {
		this.st_sessaodocumento = st_sessaodocumento;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}
}
