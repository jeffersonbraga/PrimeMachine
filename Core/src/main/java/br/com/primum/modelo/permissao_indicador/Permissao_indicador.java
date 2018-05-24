package br.com.primum.modelo.permissao_indicador;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="permissao_indicador")
public class Permissao_indicador{

	@Id
	@Column
	private Integer cd_permissao_indicador;

	@Column
	private Integer cd_indicador_customizado;

	@Column
	private Integer cd_usuario;


	public Integer getCd_permissao_indicador(){
		return cd_permissao_indicador;
	}


	public void setCd_permissao_indicador(Integer cd_permissao_indicador){
		this.cd_permissao_indicador = cd_permissao_indicador;
	}


	public Integer getCd_indicador_customizado(){
		return cd_indicador_customizado;
	}


	public void setCd_indicador_customizado(Integer cd_indicador_customizado){
		this.cd_indicador_customizado = cd_indicador_customizado;
	}


	public Integer getCd_usuario(){
		return cd_usuario;
	}


	public void setCd_usuario(Integer cd_usuario){
		this.cd_usuario = cd_usuario;
	}

}