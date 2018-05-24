package br.com.primum.modelo.insumo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="insumo")
public class Insumo {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_insumo;
	
	@Column
	private Integer cd_grade;
	
	@Column
	private String ds_insumo;
	
	@Column
	private Integer cd_unidade;

	public Integer getCd_insumo() {
		return cd_insumo;
	}

	public void setCd_insumo(Integer cd_insumo) {
		this.cd_insumo = cd_insumo;
	}

	public Integer getCd_grade() {
		return cd_grade;
	}

	public void setCd_grade(Integer cd_grade) {
		this.cd_grade = cd_grade;
	}

	public String getDs_insumo() {
		return ds_insumo;
	}

	public void setDs_insumo(String ds_insumo) {
		this.ds_insumo = ds_insumo;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}
}
