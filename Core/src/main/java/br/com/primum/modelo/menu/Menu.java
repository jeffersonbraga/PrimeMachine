package br.com.primum.modelo.menu;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="menu")
public class Menu {
	
	@Id
	//@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer cd_menu;
	
	@Column
	private Integer cd_menu_item;

	@Column
	private Integer cd_programa;

	@Column
	private Integer cd_modulo;

	@Column
	private String ds_menu;
	
	@Column
	private Integer nr_ordem;
	
	@Column
	private Integer st_visivel;
	
	@Column
	private Integer vl_valor_abrir;

	public Integer getCd_menu() {
		return cd_menu;
	}

	public void setCd_menu(Integer cd_menu) {
		this.cd_menu = cd_menu;
	}

	public Integer getCd_menu_item() {
		return cd_menu_item;
	}

	public void setCd_menu_item(Integer cd_menu_item) {
		this.cd_menu_item = cd_menu_item;
	}

	public Integer getCd_programa() {
		return cd_programa;
	}

	public void setCd_programa(Integer cd_programa) {
		this.cd_programa = cd_programa;
	}

	public String getDs_menu() {
		return ds_menu;
	}

	public void setDs_menu(String ds_menu) {
		this.ds_menu = ds_menu;
	}

	public Integer getNr_ordem() {
		return nr_ordem;
	}

	public void setNr_ordem(Integer nr_ordem) {
		this.nr_ordem = nr_ordem;
	}

	public Integer getSt_visivel() {
		return st_visivel;
	}

	public void setSt_visivel(Integer st_visivel) {
		this.st_visivel = st_visivel;
	}

	public Integer getCd_modulo() {
		return cd_modulo;
	}

	public void setCd_modulo(Integer cd_modulo) {
		this.cd_modulo = cd_modulo;
	}

	public Integer getVl_valor_abrir() {
		return vl_valor_abrir;
	}

	public void setVl_valor_abrir(Integer vl_valor_abrir) {
		this.vl_valor_abrir = vl_valor_abrir;
	}
}
