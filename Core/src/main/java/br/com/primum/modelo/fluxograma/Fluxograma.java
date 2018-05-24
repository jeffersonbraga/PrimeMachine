package br.com.primum.modelo.fluxograma;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.processo_fluxo.Processo_fluxo;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="fluxograma")
public class Fluxograma {

	@Id
	@Column
	private Integer cd_fluxograma; 
	
	@Column
	private String nm_fluxograma; 
	
	@Column
	private String ds_fluxograma; 
	
	@Column
	private Integer cd_usuario;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.processo_fluxo.Processo_fluxo", campoBuscar="cd_fluxograma", persistir=true)
	private List<Processo_fluxo> listaProcessos;

	
	/*******/
	
	
	public Integer getCd_fluxograma() {
		return cd_fluxograma;
	}

	public void setCd_fluxograma(Integer cd_fluxograma) {
		this.cd_fluxograma = cd_fluxograma;
	}

	public String getNm_fluxograma() {
		return nm_fluxograma;
	}

	public void setNm_fluxograma(String nm_fluxograma) {
		this.nm_fluxograma = nm_fluxograma;
	}

	public String getDs_fluxograma() {
		return ds_fluxograma;
	}

	public void setDs_fluxograma(String ds_fluxograma) {
		this.ds_fluxograma = ds_fluxograma;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public List<Processo_fluxo> getListaProcessos() {
		return listaProcessos;
	}

	public void setListaProcessos(List<Processo_fluxo> listaProcessos) {
		this.listaProcessos = listaProcessos;
	} 
}
