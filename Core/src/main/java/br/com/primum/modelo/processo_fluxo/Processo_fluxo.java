package br.com.primum.modelo.processo_fluxo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="processo_fluxo")
public class Processo_fluxo {

	@Id
	@Column
	private Integer cd_processo_fluxo;

	@Column
	private String ds_processo_fluxo;

	@Column
	private Integer cd_formulario;

	@Column
	private Integer cd_fluxograma;

	@Column
	private Integer vl_posicao_x;

	@Column
	private Integer vl_posicao_y;

	@Column
	private Integer tp_obrigatorio;

	@Column
	private Integer cd_processo_precedente;
	
	
	/****************/
	
	
	
	public Integer getCd_processo_fluxo() {
		return cd_processo_fluxo;
	}

	public void setCd_processo_fluxo(Integer cd_processo_fluxo) {
		this.cd_processo_fluxo = cd_processo_fluxo;
	}

	public String getDs_processo_fluxo() {
		return ds_processo_fluxo;
	}

	public void setDs_processo_fluxo(String ds_processo_fluxo) {
		this.ds_processo_fluxo = ds_processo_fluxo;
	}

	public Integer getCd_formulario() {
		return cd_formulario;
	}

	public void setCd_formulario(Integer cd_formulario) {
		this.cd_formulario = cd_formulario;
	}

	public Integer getCd_fluxograma() {
		return cd_fluxograma;
	}

	public void setCd_fluxograma(Integer cd_fluxograma) {
		this.cd_fluxograma = cd_fluxograma;
	}

	public Integer getVl_posicao_x() {
		return vl_posicao_x;
	}

	public void setVl_posicao_x(Integer vl_posicao_x) {
		this.vl_posicao_x = vl_posicao_x;
	}

	public Integer getVl_posicao_y() {
		return vl_posicao_y;
	}

	public void setVl_posicao_y(Integer vl_posicao_y) {
		this.vl_posicao_y = vl_posicao_y;
	}

	public Integer getCd_processo_precedente() {
		return cd_processo_precedente;
	}

	public void setCd_processo_precedente(Integer cd_processo_precedente) {
		this.cd_processo_precedente = cd_processo_precedente;
	}

	public Integer getTp_obrigatorio() {
		return tp_obrigatorio;
	}

	public void setTp_obrigatorio(Integer tp_obrigatorio) {
		this.tp_obrigatorio = tp_obrigatorio;
	}
	
	
}
