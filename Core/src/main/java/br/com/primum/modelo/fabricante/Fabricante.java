package br.com.primum.modelo.fabricante;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="fabricante")
public class Fabricante {
	
	@Id
	@Column
	private Integer cd_fabricante;
	
	@Column
	private String nm_fabricante;
	
	@Column
	private String ds_fabricante;
	
	
	public Integer getCd_fabricante() {
		return cd_fabricante;
	}
	public void setCd_fabricante(Integer cd_fabricante) {
		this.cd_fabricante = cd_fabricante;
	}
	public String getNm_fabricante() {
		return nm_fabricante;
	}
	public void setNm_fabricante(String nm_fabricante) {
		this.nm_fabricante = nm_fabricante;
	}
	public String getDs_fabricante() {
		return ds_fabricante;
	}
	public void setDs_fabricante(String ds_fabricante) {
		this.ds_fabricante = ds_fabricante;
	}
}
