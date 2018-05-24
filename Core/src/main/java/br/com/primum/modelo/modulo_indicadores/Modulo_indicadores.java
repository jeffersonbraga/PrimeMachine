package br.com.primum.modelo.modulo_indicadores;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.menu_indicadores.Menu_indicadores;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="modulo_indicadores")
public class Modulo_indicadores{

	@Id
	@Column
	private Integer cd_modulo_indicadores;

	@Column
	private String ds_modulo_indicadores;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.menu_indicadores.Menu_indicadores", campoBuscar="cd_modulo_indicadores", persistir=false)
	private List<Menu_indicadores> listaMenus;
	
	public Integer getCd_modulo_indicadores(){
		return cd_modulo_indicadores;
	}


	public void setCd_modulo_indicadores(Integer cd_modulo_indicadores){
		this.cd_modulo_indicadores = cd_modulo_indicadores;
	}


	public String getDs_modulo_indicadores(){
		return ds_modulo_indicadores;
	}


	public void setDs_modulo_indicadores(String ds_modulo_indicadores){
		this.ds_modulo_indicadores = ds_modulo_indicadores;
	}


	public List<Menu_indicadores> getListaMenus() {
		return listaMenus;
	}


	public void setListaMenus(List<Menu_indicadores> listaMenus) {
		this.listaMenus = listaMenus;
	}

}