package br.com.primum.modelo.tipo_campo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="tipo_campo")
public class Tipo_campo {

	@Id
	@Column
	private Integer cd_tipo_campo;
	
	@Column
	private String ds_tipo_campo;

	public Integer getCd_tipo_campo() {
		return cd_tipo_campo;
	}

	public void setCd_tipo_campo(Integer cd_tipo_campo) {
		this.cd_tipo_campo = cd_tipo_campo;
	}

	public String getDs_tipo_campo() {
		return ds_tipo_campo;
	}

	public void setDs_tipo_campo(String ds_tipo_campo) {
		this.ds_tipo_campo = ds_tipo_campo;
	}
}
