package br.com.primum.modelo.grupousuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="grupousuario")
public class GrupoUsuario {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_grupousuario;
	
	@Column
	private String ds_grupousuario;
	
	@Column
	private Integer nr_nivelacesso;

	public Integer getCd_grupousuario() {
		return cd_grupousuario;
	}

	public void setCd_grupousuario(Integer cd_grupousuario) {
		this.cd_grupousuario = cd_grupousuario;
	}

	public String getDs_grupousuario() {
		return ds_grupousuario;
	}

	public void setDs_grupousuario(String ds_grupousuario) {
		this.ds_grupousuario = ds_grupousuario;
	}

	public Integer getNr_nivelacesso() {
		return nr_nivelacesso;
	}

	public void setNr_nivelacesso(Integer nr_nivelacesso) {
		this.nr_nivelacesso = nr_nivelacesso;
	}
}
