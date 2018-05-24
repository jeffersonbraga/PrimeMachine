package br.com.primum.modelo.pais;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="pais")
public class Pais{

	@Id
	@Column
	private Integer cd_pais;

	@Column
	private String nm_pais;

	@Column
	private String ds_sigla;


	public Integer getCd_pais(){
		return cd_pais;
	}


	public void setCd_pais(Integer cd_pais){
		this.cd_pais = cd_pais;
	}


	public String getNm_pais(){
		return nm_pais;
	}


	public void setNm_pais(String nm_pais){
		this.nm_pais = nm_pais;
	}


	public String getDs_sigla(){
		return ds_sigla;
	}


	public void setDs_sigla(String ds_sigla){
		this.ds_sigla = ds_sigla;
	}

}