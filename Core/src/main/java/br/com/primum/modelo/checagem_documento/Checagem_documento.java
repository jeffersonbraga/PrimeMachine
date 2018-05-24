package br.com.primum.modelo.checagem_documento;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="checagem_documento")
public class Checagem_documento {

	@Id
	private Integer cd_checagem_documento;

	@Column
	private Integer cd_usuario;

	@Column
	private Integer cd_documento;

	@Column
	private Integer tp_checagem_documento;

	@Column
	private Integer st_checagem_documento;

	public Integer getCd_checagem_documento() {
		return cd_checagem_documento;
	}

	public void setCd_checagem_documento(Integer cd_checagem_documento) {
		this.cd_checagem_documento = cd_checagem_documento;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getCd_documento() {
		return cd_documento;
	}

	public void setCd_documento(Integer cd_documento) {
		this.cd_documento = cd_documento;
	}

	public Integer getTp_checagem_documento() {
		return tp_checagem_documento;
	}

	public void setTp_checagem_documento(Integer tp_checagem_documento) {
		this.tp_checagem_documento = tp_checagem_documento;
	}

	public Integer getSt_checagem_documento() {
		return st_checagem_documento;
	}

	public void setSt_checagem_documento(Integer st_checagem_documento) {
		this.st_checagem_documento = st_checagem_documento;
	}
}