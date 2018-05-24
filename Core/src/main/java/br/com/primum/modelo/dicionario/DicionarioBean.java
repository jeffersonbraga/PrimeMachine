package br.com.primum.modelo.dicionario;

import java.util.List;

public class DicionarioBean {
	
	private Integer cd_programa;
	
	private String 	ds_programa;
	
	private String 	ds_caminhoprograma;
	
	private Integer cd_tabela;
	
	private Integer cd_modulo;
	
	private String 	ds_modulo;
	
	private Integer cd_menu;
	
	private Integer cd_menu_item;
	
	private String 	ds_menu;

	private Integer tp_programa;

	private Integer st_visivel;

	private Integer nr_ordem;

	private Integer vl_valor_abrir;
	
	private String ds_ajuda_programa;

	private List<DicionarioBean> children;

	
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

	public Integer getCd_tabela() {
		return cd_tabela;
	}

	public void setCd_tabela(Integer cd_tabela) {
		this.cd_tabela = cd_tabela;
	}

	public Integer getCd_modulo() {
		return cd_modulo;
	}

	public void setCd_modulo(Integer cd_modulo) {
		this.cd_modulo = cd_modulo;
	}

	public String getDs_modulo() {
		return ds_modulo;
	}

	public void setDs_modulo(String ds_modulo) {
		this.ds_modulo = ds_modulo;
	}

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

	public String getDs_menu() {
		return ds_menu;
	}

	public void setDs_menu(String ds_menu) {
		this.ds_menu = ds_menu;
	}

	public Integer getTp_programa() {
		return tp_programa;
	}

	public void setTp_programa(Integer tp_programa) {
		this.tp_programa = tp_programa;
	}

	public Integer getSt_visivel() {
		return st_visivel;
	}

	public void setSt_visivel(Integer st_visivel) {
		this.st_visivel = st_visivel;
	}

	public Integer getNr_ordem() {
		return nr_ordem;
	}

	public void setNr_ordem(Integer nr_ordem) {
		this.nr_ordem = nr_ordem;
	}

	public Integer getVl_valor_abrir() {
		return vl_valor_abrir;
	}

	public void setVl_valor_abrir(Integer vl_valor_abrir) {
		this.vl_valor_abrir = vl_valor_abrir;
	}

	public List<DicionarioBean> getChildren() {
		return children;
	}

	public void setChildren(List<DicionarioBean> children) {
		this.children = children;
	}
}