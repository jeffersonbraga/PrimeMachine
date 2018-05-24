package br.com.primum.modelo.tipo_turno;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="tipo_turno")
public class Tipo_turno{

	@Id
	@Column
	private Integer cd_tipo_turno;

	@Column
	private String ds_tipo_turno;

	@Column
	private Integer st_tipo_turno;


	public Integer getCd_tipo_turno(){
		return cd_tipo_turno;
	}


	public void setCd_tipo_turno(Integer cd_tipo_turno){
		this.cd_tipo_turno = cd_tipo_turno;
	}


	public String getDs_tipo_turno(){
		return ds_tipo_turno;
	}


	public void setDs_tipo_turno(String ds_tipo_turno){
		this.ds_tipo_turno = ds_tipo_turno;
	}


	public Integer getSt_tipo_turno() {
		return st_tipo_turno;
	}


	public void setSt_tipo_turno(Integer st_tipo_turno) {
		this.st_tipo_turno = st_tipo_turno;
	}

}