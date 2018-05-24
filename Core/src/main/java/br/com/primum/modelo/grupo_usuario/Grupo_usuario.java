package br.com.primum.modelo.grupo_usuario;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.grupo_usuario_programa.Grupo_usuario_programa;
import br.com.primum.modelo.usuario_grupo.Usuario_grupo;
import br.com.primum.modelo.usuario_grupo_usuario.Usuario_grupo_usuario;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="grupo_usuario")
public class Grupo_usuario {

	@Id
	@Column
	private Integer cd_grupo_usuario;

	@Column
	private String nm_grupo_usuario;
	
	@Column
	private String ds_grupo_usuario;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.grupo_usuario_programa.Grupo_usuario_programa", campoBuscar="cd_grupo_usuario", persistir=true)
	private List<Grupo_usuario_programa> listaPermissoes;

	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.usuario_grupo_usuario.Usuario_grupo_usuario", campoBuscar="cd_grupo_usuario", persistir=true)
	private List<Usuario_grupo_usuario> listaUsuarios;
	
	public Integer getCd_grupo_usuario() {
		return cd_grupo_usuario;
	}

	public void setCd_grupo_usuario(Integer cd_grupo_usuario) {
		this.cd_grupo_usuario = cd_grupo_usuario;
	}

	public String getNm_grupo_usuario() {
		return nm_grupo_usuario;
	}

	public void setNm_grupo_usuario(String nm_grupo_usuario) {
		this.nm_grupo_usuario = nm_grupo_usuario;
	}
	
	public String getDs_grupo_usuario() {
		return ds_grupo_usuario;
	}

	public void setDs_grupo_usuario(String ds_grupo_usuario) {
		this.ds_grupo_usuario = ds_grupo_usuario;
	}

	public List<Grupo_usuario_programa> getListaPermissoes() {
		return listaPermissoes;
	}

	public void setListaPermissoes(List<Grupo_usuario_programa> listaPermissoes) {
		this.listaPermissoes = listaPermissoes;
	}

	public List<Usuario_grupo_usuario> getListaUsuarios() {
		return listaUsuarios;
	}

	public void setListaUsuarios(List<Usuario_grupo_usuario> listaUsuarios) {
		this.listaUsuarios = listaUsuarios;
	}

}