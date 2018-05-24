package br.com.primum.modelo.municipio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="municipio")
public class Municipio {

	@Id
	@Column
	private Integer cd_municipio;
	
	@Column
	private Integer cd_estado;
	
	@Column
	private String nm_municipio;
	
	@Column
	private String ds_sigla;

	public Integer getCd_municipio() {
		return cd_municipio;
	}

	public void setCd_municipio(Integer cd_municipio) {
		this.cd_municipio = cd_municipio;
	}

	public Integer getCd_estado() {
		return cd_estado;
	}

	public void setCd_estado(Integer cd_estado) {
		this.cd_estado = cd_estado;
	}

	public String getDs_municipio() {
		return nm_municipio;
	}

	public void setDs_municipio(String ds_municipio) {
		this.nm_municipio = ds_municipio;
	}

	public String getDs_sigla() {
		return ds_sigla;
	}

	public void setDs_sigla(String ds_sigla) {
		this.ds_sigla = ds_sigla;
	}
}
