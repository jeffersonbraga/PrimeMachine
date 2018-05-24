package br.com.primum.modelo.notificacao_usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.notificacao_automatica.Notificacao_automatica;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="notificacao_usuario")
public class Notificacao_usuario {

	@Id
	@Column
	private Integer cd_notificacao_usuario;

	@Column
	private Integer cd_notificacao;

	@Column
	private Integer cd_notificacao_automatica;

	@Column
	private Integer cd_usuario;
	
	@Column
	private Integer st_notificar;
	
	@Column
	private Integer st_visualizado;
	
	@Column
	private Integer cd_codigo_parametro;
	
	

	public Integer getCd_codigo_parametro() {
		return cd_codigo_parametro;
	}

	public void setCd_codigo_parametro(Integer cd_codigo_parametro) {
		this.cd_codigo_parametro = cd_codigo_parametro;
	}

	public Integer getCd_notificacao_automatica() {
		return cd_notificacao_automatica;
	}

	public void setCd_notificacao_automatica(Integer cd_notificacao_automatica) {
		this.cd_notificacao_automatica = cd_notificacao_automatica;
	}

	public Integer getSt_visualizado() {
		return st_visualizado;
	}

	public void setSt_visualizado(Integer st_visualizado) {
		this.st_visualizado = st_visualizado;
	}

	public Integer getSt_notificar() {
		return st_notificar;
	}

	public void setSt_notificar(Integer st_notificar) {
		this.st_notificar = st_notificar;
	}

	public Integer getCd_notificacao_usuario() {
		return cd_notificacao_usuario;
	}

	public void setCd_notificacao_usuario(Integer cd_notificacao_usuario) {
		this.cd_notificacao_usuario = cd_notificacao_usuario;
	}

	public Integer getCd_notificacao() {
		return cd_notificacao;
	}

	public void setCd_notificacao(Integer cd_notificacao) {
		this.cd_notificacao = cd_notificacao;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

}