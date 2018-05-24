package br.com.primum.modelo.campospesquisa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.campo.Campo;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="campospesquisa")
public class CamposPesquisa {

	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_campospesquisa;
	
	@Column
	private Integer cd_programa;
	
	@Column
	private Integer cd_campo;
	
	@Column
	private Integer st_listapesquisa;
	
	@Column
	private Integer st_gridpesquisa;
	
	@Column
	private Integer st_filtro_pesquisa;

	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.campo.Campo", campoBuscar="cd_campo", campoComparar="cd_campo")
	@Transient
	private Campo campo;

	public Integer getCd_campospesquisa() {
		return cd_campospesquisa;
	}

	public void setCd_campospesquisa(Integer cd_campospesquisa) {
		this.cd_campospesquisa = cd_campospesquisa;
	}

	public Integer getCd_programa() {
		return cd_programa;
	}

	public void setCd_programa(Integer cd_programa) {
		this.cd_programa = cd_programa;
	}

	public Integer getCd_campo() {
		return cd_campo;
	}

	public void setCd_campo(Integer cd_campo) {
		this.cd_campo = cd_campo;
	}

	public Integer getSt_listapesquisa() {
		return st_listapesquisa;
	}

	public void setSt_listapesquisa(Integer st_listapesquisa) {
		this.st_listapesquisa = st_listapesquisa;
	}

	public Integer getSt_gridpesquisa() {
		return st_gridpesquisa;
	}

	public void setSt_gridpesquisa(Integer st_gridpesquisa) {
		this.st_gridpesquisa = st_gridpesquisa;
	}

	public Campo getCampo() {
		return campo;
	}

	public void setCampo(Campo campo) {
		this.campo = campo;
	}

	public Integer getSt_filtro_pesquisa() {
		return st_filtro_pesquisa;
	}

	public void setSt_filtro_pesquisa(Integer st_filtro_pesquisa) {
		this.st_filtro_pesquisa = st_filtro_pesquisa;
	}
}
