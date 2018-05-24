package br.com.primum.modelo.faixa_class_auditoria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="faixa_class_auditoria")
public class Faixa_class_auditoria {

	@Id
	@Column
	private Integer cd_faixa_class_auditoria;

	@Column
	private Integer cd_classificacao_auditoria;

	@Column
	private String ds_faixa_class_auditoria;

	@Column
	private Float vl_minimo;
	
	@Column
	private Float vl_maximo;

	public Integer getCd_faixa_class_auditoria() {
		return cd_faixa_class_auditoria;
	}

	public void setCd_faixa_class_auditoria(Integer cd_faixa_class_auditoria) {
		this.cd_faixa_class_auditoria = cd_faixa_class_auditoria;
	}

	public Integer getCd_classificacao_auditoria() {
		return cd_classificacao_auditoria;
	}

	public void setCd_classificacao_auditoria(Integer cd_classificacao_auditoria) {
		this.cd_classificacao_auditoria = cd_classificacao_auditoria;
	}

	public String getDs_faixa_class_auditoria() {
		return ds_faixa_class_auditoria;
	}

	public void setDs_faixa_class_auditoria(String ds_faixa_class_auditoria) {
		this.ds_faixa_class_auditoria = ds_faixa_class_auditoria;
	}

	public Float getVl_minimo() {
		return vl_minimo;
	}

	public void setVl_minimo(Float vl_minimo) {
		this.vl_minimo = vl_minimo;
	}

	public Float getVl_maximo() {
		return vl_maximo;
	}

	public void setVl_maximo(Float vl_maximo) {
		this.vl_maximo = vl_maximo;
	}

	
}