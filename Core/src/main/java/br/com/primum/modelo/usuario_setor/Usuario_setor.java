package br.com.primum.modelo.usuario_setor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="usuario_setor")
public class Usuario_setor {

	@Id
	@Column
	private Integer cd_usuario_setor;

	@Column
	private Integer cd_usuario;

	@Column
	private Integer cd_setor;

	@Column
	private Integer tp_responsavel;

	public Integer getCd_usuario_setor() {
		return cd_usuario_setor;
	}

	public void setCd_usuario_setor(Integer cd_usuario_setor) {
		this.cd_usuario_setor = cd_usuario_setor;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getCd_setor() {
		return cd_setor;
	}

	public void setCd_setor(Integer cd_setor) {
		this.cd_setor = cd_setor;
	}

	public Integer getTp_responsavel() {
		return tp_responsavel;
	}

	public void setTp_responsavel(Integer tp_responsavel) {
		this.tp_responsavel = tp_responsavel;
	}
}