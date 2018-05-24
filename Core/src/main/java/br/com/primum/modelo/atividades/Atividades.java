package br.com.primum.modelo.atividades;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="atividades")
public class Atividades {
	
	@Id
	@Column
	private Integer cd_atividades;
	
	@Column
	private String nm_atividades;
	
	@Column
	private String ds_atividades;
	
	
	
	public Integer getCd_atividades() {
		return cd_atividades;
	}
	public void setCd_atividades(Integer cd_atividades) {
		this.cd_atividades = cd_atividades;
	}
	public String getNm_atividades() {
		return nm_atividades;
	}
	public void setNm_atividades(String nm_atividades) {
		this.nm_atividades = nm_atividades;
	}
	public String getDs_atividades() {
		return ds_atividades;
	}
	public void setDs_atividades(String ds_atividades) {
		this.ds_atividades = ds_atividades;
	}
}
