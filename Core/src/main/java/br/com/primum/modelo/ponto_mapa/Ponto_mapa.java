package br.com.primum.modelo.ponto_mapa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.primum.contexto.ContextoAplicacao;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="ponto_mapa")
public class Ponto_mapa implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//6295442548820185155L;


	@Id
	@Column
	private Integer cd_ponto_mapa;

	
	@Column
	private String nm_ponto_mapa;
	
	@Column
	private Integer cd_mapa_formulario;

	@Column
	private Integer vl_x;

	@Column
	private Integer vl_y;

	@Column
	private Integer cd_campo_formulario;


	public Integer getCd_ponto_mapa(){
		return cd_ponto_mapa;
	}


	public void setCd_ponto_mapa(Integer cd_ponto_mapa){
		this.cd_ponto_mapa = cd_ponto_mapa;
	}

	
	public String getNm_ponto_mapa() {
		return nm_ponto_mapa;
	}


	public void setNm_ponto_mapa(String nm_ponto_mapa) {
		this.nm_ponto_mapa = nm_ponto_mapa;
	}


	public Integer getCd_mapa_formulario(){
		return cd_mapa_formulario;
	}


	public void setCd_mapa_formulario(Integer cd_mapa_formulario){
		this.cd_mapa_formulario = cd_mapa_formulario;
	}


	public Integer getVl_x(){
		return vl_x;
	}


	public void setVl_x(Integer vl_x){
		this.vl_x = vl_x;
	}


	public Integer getVl_y(){
		return vl_y;
	}


	public void setVl_y(Integer vl_y){
		this.vl_y = vl_y;
	}


	public Integer getCd_campo_formulario(){
		return cd_campo_formulario;
	}


	public void setCd_campo_formulario(Integer cd_campo_formulario){
		this.cd_campo_formulario = cd_campo_formulario;
	}

}