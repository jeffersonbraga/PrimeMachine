package br.com.primum.modelo.usuario_grupo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="usuario_grupo")
public class Usuario_grupo {

	@Id
	@Column
	private Integer cd_usuario_grupo;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	private Integer cd_grupo_usuario;

	public Integer getCd_usuario_grupo() {
		return cd_usuario_grupo;
	}

	public void setCd_usuario_grupo(Integer cd_usuario_grupo) {
		this.cd_usuario_grupo = cd_usuario_grupo;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getCd_grupo_usuario() {
		return cd_grupo_usuario;
	}

	public void setCd_grupo_usuario(Integer cd_grupo_usuario) {
		this.cd_grupo_usuario = cd_grupo_usuario;
	}
}