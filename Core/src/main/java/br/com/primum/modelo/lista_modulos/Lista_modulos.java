package br.com.primum.modelo.lista_modulos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="lista_modulos")
public class Lista_modulos {
	
	@Id
	@Column
	private Integer cd_lista_modulos; 
	
	@Column
	private Integer cd_modulo;

	public Integer getCd_lista_modulos() {
		return cd_lista_modulos;
	}

	public void setCd_lista_modulos(Integer cd_lista_modulos) {
		this.cd_lista_modulos = cd_lista_modulos;
	}

	public Integer getCd_modulo() {
		return cd_modulo;
	}

	public void setCd_modulo(Integer cd_modulo) {
		this.cd_modulo = cd_modulo;
	}

}
