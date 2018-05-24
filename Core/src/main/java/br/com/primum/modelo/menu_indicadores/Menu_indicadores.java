package br.com.primum.modelo.menu_indicadores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="menu_indicadores")
public class Menu_indicadores{

	@Id
	@Column
	private Integer cd_menu_indicadores;

	@Column
	private String ds_menu_indicadores;

	@Column
	private Integer cd_modulo_indicadores;

	@Column
	private Integer cd_menu_origem;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.menu_indicadores.Menu_indicadores", persistir=false, campoBuscar="cd_menu_origem")
	private List<Menu_indicadores> listaSubMenus;
	

	public Integer getCd_menu_indicadores(){
		return cd_menu_indicadores;
	}


	public void setCd_menu_indicadores(Integer cd_menu_indicadores){
		this.cd_menu_indicadores = cd_menu_indicadores;
	}


	public String getDs_menu_indicadores(){
		return ds_menu_indicadores;
	}


	public void setDs_menu_indicadores(String ds_menu_indicadores){
		this.ds_menu_indicadores = ds_menu_indicadores;
	}


	public Integer getCd_modulo_indicadores(){
		return cd_modulo_indicadores;
	}


	public void setCd_modulo_indicadores(Integer cd_modulo_indicadores){
		this.cd_modulo_indicadores = cd_modulo_indicadores;
	}


	public Integer getCd_menu_origem() {
		return cd_menu_origem;
	}


	public void setCd_menu_origem(Integer cd_menu_origem) {
		this.cd_menu_origem = cd_menu_origem;
	}


	public List<Menu_indicadores> getListaSubMenus() {
		return listaSubMenus;
	}


	public void setListaSubMenus(List<Menu_indicadores> listaSubMenus) {
		this.listaSubMenus = listaSubMenus;
	}
	
	

}