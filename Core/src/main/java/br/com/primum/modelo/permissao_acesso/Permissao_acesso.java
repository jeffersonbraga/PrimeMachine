package br.com.primum.modelo.permissao_acesso;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.programa.Programa;

@Entity
@org.hibernate.annotations.Entity(mutable=true)
@Table(name="permissao_acesso")
public class Permissao_acesso {

	@Id
	@Column
	private Integer cd_permissao_acesso;

	@Column
	private Integer cd_programa;

	@Column
	private Integer cd_usuario;

	@Column
	private Integer tp_salvar;

	@Column
	private Integer tp_excluir;

	@Column
	private Integer tp_visualizar;

	@Column
	private Integer tp_especial;

	@Column
	private Integer tp_editar;
	
	@Column
	private Integer cd_unidade;
	
	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.programa.Programa", campoBuscar="cd_programa", campoComparar="cd_programa", persistir=false)
	private Programa programa;

	@Transient
	private Integer cd_modulo;
	
	@Transient
	private String nm_usuario;
	
	
	
	public String getNm_usuario() {
		return nm_usuario;
	}

	public void setNm_usuario(String nm_usuario) {
		this.nm_usuario = nm_usuario;
	}

	public Integer getCd_modulo() {
		return cd_modulo;
	}

	public void setCd_modulo(Integer cd_modulo) {
		this.cd_modulo = cd_modulo;
	}
	
	public Integer getCd_permissao_acesso() {
		return cd_permissao_acesso;
	}

	public void setCd_permissao_acesso(Integer cd_permissao_acesso) {
		this.cd_permissao_acesso = cd_permissao_acesso;
	}

	public Integer getCd_programa() {
		return cd_programa;
	}

	public void setCd_programa(Integer cd_programa) {
		this.cd_programa = cd_programa;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getTp_salvar() {
		return tp_salvar;
	}

	public void setTp_salvar(Integer tp_salvar) {
		this.tp_salvar = tp_salvar;
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

	public Integer getTp_especial() {
		return tp_especial;
	}

	public void setTp_especial(Integer tp_especial) {
		this.tp_especial = tp_especial;
	}

	public Integer getTp_editar() {
		return tp_editar;
	}

	public void setTp_editar(Integer tp_editar) {
		this.tp_editar = tp_editar;
	}

	public Programa getPrograma() {
		return programa;
	}

	public void setPrograma(Programa programa) {
		this.programa = programa;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}	
}