package br.com.primum.modelo.programa;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.campospesquisa.CamposPesquisa;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="programa")
public class Programa {

	@Id
	private Integer cd_programa;

	@Column
	private String ds_programa;

	@Column
	private String ds_caminhoprograma;

	@Column
	private Integer cd_tabela;
	
	@Column
	private Integer tp_programa;
	
	@Column
	private String ds_ajuda_programa;

	@Column
	private Integer cd_campo_filtro;
	
	@Transient
	private List<?> listaValoresFiltro;
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.campospesquisa.CamposPesquisa", campoBuscar="cd_programa", persistir=true)
	@Transient
	private List<CamposPesquisa> listaCamposPesquisa;
	
	@Transient
	private List<Object> listaDados;

	@Transient
	private Integer cd_modulo;
	
	
	
	public Integer getCd_modulo() {
		return cd_modulo;
	}

	public void setCd_modulo(Integer cd_modulo) {
		this.cd_modulo = cd_modulo;
	}

	public String getDs_ajuda_programa() {
		return ds_ajuda_programa;
	}

	public void setDs_ajuda_programa(String ds_ajuda_programa) {
		this.ds_ajuda_programa = ds_ajuda_programa;
	}

	public Integer getCd_programa() {
		return cd_programa;
	}

	public void setCd_programa(Integer cd_programa) {
		this.cd_programa = cd_programa;
	}

	public String getDs_programa() {
		return ds_programa;
	}

	public void setDs_programa(String ds_programa) {
		this.ds_programa = ds_programa;
	}

	public String getDs_caminhoprograma() {
		return ds_caminhoprograma;
	}

	public void setDs_caminhoprograma(String ds_caminhoprograma) {
		this.ds_caminhoprograma = ds_caminhoprograma;
	}

	public Integer getCd_campo_filtro() {
		return cd_campo_filtro;
	}

	public void setCd_campo_filtro(Integer cd_campo_filtro) {
		this.cd_campo_filtro = cd_campo_filtro;
	}

	public Integer getCd_tabela() {
		return cd_tabela;
	}

	public void setCd_tabela(Integer cd_tabela) {
		this.cd_tabela = cd_tabela;
	}

	public Integer getTp_programa() {
		return tp_programa;
	}

	public void setTp_programa(Integer tp_programa) {
		this.tp_programa = tp_programa;
	}

	public List<CamposPesquisa> getListaCamposPesquisa() {
		return listaCamposPesquisa;
	}

	public void setListaCamposPesquisa(List<CamposPesquisa> listaCamposPesquisa) {
		this.listaCamposPesquisa = listaCamposPesquisa;
	}

	public List<Object> getListaDados() {
		return listaDados;
	}

	public void setListaDados(List<Object> listaDados) {
		this.listaDados = listaDados;
	}

	public List<?> getListaValoresFiltro() {
		return listaValoresFiltro;
	}

	public void setListaValoresFiltro(List<?> listaValoresFiltro) {
		this.listaValoresFiltro = listaValoresFiltro;
	}
	
}