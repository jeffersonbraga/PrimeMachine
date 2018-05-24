package br.com.primum.modelo.permissao_menu_indicadores;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="permissao_menu_indicadores")
public class Permissao_menu_indicadores {
	
	@Id
	@Column
	private Integer cd_permissao_menu_indicadores;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	private Integer cd_unidade;
	
	@Column
	private Integer cd_menu_indicadores;
	
	@Column
	private Integer cd_modulo_indicadores;
	
	@Column
	private Integer tp_visualizar;
	
	
	public Integer getCd_modulo_indicadores() {
		return cd_modulo_indicadores;
	}

	public void setCd_modulo_indicadores(Integer cd_modulo_indicadores) {
		this.cd_modulo_indicadores = cd_modulo_indicadores;
	}

	public Integer getTp_visualizar() {
		return tp_visualizar;
	}

	public void setTp_visualizar(Integer tp_visualizar) {
		this.tp_visualizar = tp_visualizar;
	}

	public Integer getCd_permissao_menu_indicadores() {
		return cd_permissao_menu_indicadores;
	}

	public void setCd_permissao_menu_indicadores(
			Integer cd_permissao_menu_indicadores) {
		this.cd_permissao_menu_indicadores = cd_permissao_menu_indicadores;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}

	public Integer getCd_menu_indicadores() {
		return cd_menu_indicadores;
	}

	public void setCd_menu_indicadores(Integer cd_menu_indicadores) {
		this.cd_menu_indicadores = cd_menu_indicadores;
	}
	
	
}
