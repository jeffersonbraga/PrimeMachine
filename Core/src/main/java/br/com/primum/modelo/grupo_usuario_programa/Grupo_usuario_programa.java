package br.com.primum.modelo.grupo_usuario_programa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.programa.Programa;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="grupo_usuario_programa")
public class Grupo_usuario_programa {

	@Id
	@Column
	private Integer cd_grupo_usuario_programa; 
	
	@Column
	private Integer cd_grupo_usuario; 
	
	@Column
	private Integer cd_programa; 
	
	@Column
	private Integer tp_salvar; 
	
	@Column
	private Integer tp_editar;
	
	@Column
	private Integer tp_excluir;
	
	@Column
	private Integer tp_visualizar;

	@Transient
	private Integer cd_modulo;
	
	@Transient
	private String nm_programa;
	
	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.programa.Programa", campoBuscar="cd_programa", campoComparar="cd_programa", persistir=false)
	private Programa programa;
	
	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public Integer getCd_grupo_usuario() {
		return cd_grupo_usuario;
	}

	public void setCd_grupo_usuario(Integer cd_grupo_usuario) {
		this.cd_grupo_usuario = cd_grupo_usuario;
	}

	public String getNm_programa() {
		return nm_programa;
	}

	public void setNm_programa(String nm_programa) {
		this.nm_programa = nm_programa;
	}

	public Integer getCd_modulo() {
		return cd_modulo;
	}

	public void setCd_modulo(Integer cd_modulo) {
		this.cd_modulo = cd_modulo;
	}
	public Integer getCd_grupo_usuario_programa() {
		return cd_grupo_usuario_programa;
	}

	public void setCd_grupo_usuario_programa(Integer cd_grupo_usuario_programa) {
		this.cd_grupo_usuario_programa = cd_grupo_usuario_programa;
	}

	public Integer getCd_programa() {
		return cd_programa;
	}

	public void setCd_programa(Integer cd_programa) {
		this.cd_programa = cd_programa;
	}

	public Integer getTp_salvar() {
		return tp_salvar;
	}

	public void setTp_salvar(Integer tp_salvar) {
		this.tp_salvar = tp_salvar;
	}

	public Integer getTp_editar() {
		return tp_editar;
	}

	public void setTp_editar(Integer tp_editar) {
		this.tp_editar = tp_editar;
	}

	public Integer getTp_excluir() {
		return tp_excluir;
	}

	public void setTp_excluir(Integer tp_excluir) {
		this.tp_excluir = tp_excluir;
	}

	public Integer getTp_visualizar() {
		return tp_visualizar;
	}

	public void setTp_visualizar(Integer tp_visualizar) {
		this.tp_visualizar = tp_visualizar;
	}
}
