package br.com.primum.modelo.notificacao_grupo_usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="notificacao_grupo_usuario")
public class Notificacao_grupo_usuario {

	@Id
	@Column
	private Integer cd_notificacao_grupo_usuario;

	@Column
	private Integer cd_notificacao;

	@Column
	private Integer cd_grupo_usuario;

	@Column
	private Integer st_notificar;
	

	public Integer getSt_notificar() {
		return st_notificar;
	}

	public void setSt_notificar(Integer st_notificar) {
		this.st_notificar = st_notificar;
	}
	
	public Integer getCd_notificacao_grupo_usuario() {
		return cd_notificacao_grupo_usuario;
	}

	public void setCd_notificacao_grupo_usuario(Integer cd_notificacao_grupo_usuario) {
		this.cd_notificacao_grupo_usuario = cd_notificacao_grupo_usuario;
	}

	public Integer getCd_notificacao() {
		return cd_notificacao;
	}

	public void setCd_notificacao(Integer cd_notificacao) {
		this.cd_notificacao = cd_notificacao;
	}

	public Integer getCd_grupo_usuario() {
		return cd_grupo_usuario;
	}

	public void setCd_grupo_usuario(Integer cd_grupo_usuario) {
		this.cd_grupo_usuario = cd_grupo_usuario;
	}
}