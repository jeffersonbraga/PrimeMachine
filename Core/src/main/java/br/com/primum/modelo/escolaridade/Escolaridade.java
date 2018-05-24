package br.com.primum.modelo.escolaridade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="escolaridade")
public class Escolaridade {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_escolaridade;
	
	@Column
	private String ds_escolaridade;
	
	public Integer getCd_escolaridade() {
		return cd_escolaridade;
	}
	public void setCd_escolaridade(Integer cd_escolaridade) {
		this.cd_escolaridade = cd_escolaridade;
	}
	public String getDs_escolaridade() {
		return ds_escolaridade;
	}
	public void setDs_escolaridade(String ds_escolaridade) {
		this.ds_escolaridade = ds_escolaridade;
	}
}
