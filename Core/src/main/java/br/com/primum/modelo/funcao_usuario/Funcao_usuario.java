package br.com.primum.modelo.funcao_usuario;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="funcao_usuario")
public class Funcao_usuario {

	@Id
	@Column
	private Integer cd_funcao_usuario;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	private Integer cd_profissao;

	public Integer getCd_funcao_usuario() {
		return cd_funcao_usuario;
	}

	public void setCd_funcao_usuario(Integer cd_funcao_usuario) {
		this.cd_funcao_usuario = cd_funcao_usuario;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getCd_profissao() {
		return cd_profissao;
	}

	public void setCd_profissao(Integer cd_profissao) {
		this.cd_profissao = cd_profissao;
	}
}
