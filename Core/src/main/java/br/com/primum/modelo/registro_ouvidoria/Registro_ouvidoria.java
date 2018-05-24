package br.com.primum.modelo.registro_ouvidoria;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="registro_ouvidoria")
public class Registro_ouvidoria {

	@Id
	@Column
	private Integer cd_registro_ouvidoria;

	public Integer getCd_registro_ouvidoria() {
		return cd_registro_ouvidoria;
	}

	public void setCd_registro_ouvidoria(Integer cd_registro_ouvidoria) {
		this.cd_registro_ouvidoria = cd_registro_ouvidoria;
	}
}
