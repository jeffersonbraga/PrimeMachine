package br.com.primum.modelo.filial;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.primum.interfaces.ChavePrimaria;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="filial")
public class Filial {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	@ChavePrimaria
	private Integer cd_filial;
	@Column
	private Integer cd_empresa;
	@Column
	private String ds_filial;
	@Column
	private String ds_nomefantasia;
	@Column
	private String ds_cnpj;
	
	public Integer getCd_filial() {
		return cd_filial;
	}
	public void setCd_filial(Integer cd_filial) {
		this.cd_filial = cd_filial;
	}
	public Integer getCd_empresa() {
		return cd_empresa;
	}
	public void setCd_empresa(Integer cd_empresa) {
		this.cd_empresa = cd_empresa;
	}
	public String getDs_filial() {
		return ds_filial;
	}
	public void setDs_filial(String ds_filial) {
		this.ds_filial = ds_filial;
	}
	public String getDs_nomefantasia() {
		return ds_nomefantasia;
	}
	public void setDs_nomefantasia(String ds_nomefantasia) {
		this.ds_nomefantasia = ds_nomefantasia;
	}
	public String getDs_cnpj() {
		return ds_cnpj;
	}
	public void setDs_cnpj(String ds_cnpj) {
		this.ds_cnpj = ds_cnpj;
	}
}
