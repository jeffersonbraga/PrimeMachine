package br.com.primum.modelo.empresa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import br.com.primum.interfaces.ChavePrimaria;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="empresa")
public class Empresa {
	
	@Id
	private Integer cd_empresa;
	
	@Column
	private String nm_empresa;
	
	@Column
	private String ds_razao_social;
	
	@Column
	private String st_empresa;

	public Integer getCd_empresa() {
		return cd_empresa;
	}

	public void setCd_empresa(Integer cd_empresa) {
		this.cd_empresa = cd_empresa;
	}

	public String getNm_empresa() {
		return nm_empresa;
	}

	public void setNm_empresa(String nm_empresa) {
		this.nm_empresa = nm_empresa;
	}

	public String getDs_razao_social() {
		return ds_razao_social;
	}

	public void setDs_razao_social(String ds_razao_social) {
		this.ds_razao_social = ds_razao_social;
	}

	public String getSt_empresa() {
		return st_empresa;
	}

	public void setSt_empresa(String st_empresa) {
		this.st_empresa = st_empresa;
	}
}