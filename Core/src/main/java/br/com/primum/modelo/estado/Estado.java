package br.com.primum.modelo.estado;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="estado")
public class Estado {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_estado;
	
	@Column
	private String ds_estado;
	
	@Column
	private String sg_estado;

	public Integer getCd_estado() {
		return cd_estado;
	}

	public void setCd_estado(Integer cd_estado) {
		this.cd_estado = cd_estado;
	}

	public String getDs_estado() {
		return ds_estado;
	}

	public void setDs_estado(String ds_estado) {
		this.ds_estado = ds_estado;
	}

	public String getSg_estado() {
		return sg_estado;
	}

	public void setSg_estado(String sg_estado) {
		this.sg_estado = sg_estado;
	}
}
