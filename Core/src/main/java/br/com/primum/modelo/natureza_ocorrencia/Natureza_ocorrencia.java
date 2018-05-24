package br.com.primum.modelo.natureza_ocorrencia;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="natureza_ocorrencia")
public class Natureza_ocorrencia {
	
	@Id
	@Column
	private Integer cd_natureza_ocorrencia;

	@Column
	private String ds_natureza_ocorrencia;

	@Column
	private Integer cd_classificacao_helpdesk;

	public Integer getCd_natureza_ocorrencia() {
		return cd_natureza_ocorrencia;
	}

	public void setCd_natureza_ocorrencia(Integer cd_natureza_ocorrencia) {
		this.cd_natureza_ocorrencia = cd_natureza_ocorrencia;
	}

	public String getDs_natureza_ocorrencia() {
		return ds_natureza_ocorrencia;
	}

	public void setDs_natureza_ocorrencia(String ds_natureza_ocorrencia) {
		this.ds_natureza_ocorrencia = ds_natureza_ocorrencia;
	}

	public Integer getCd_classificacao_helpdesk() {
		return cd_classificacao_helpdesk;
	}

	public void setCd_classificacao_helpdesk(Integer cd_classificacao_helpdesk) {
		this.cd_classificacao_helpdesk = cd_classificacao_helpdesk;
	}
	
}
