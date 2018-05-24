package br.com.primum.modelo.campo_agrupador;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="campo_agrupador")
public class Campo_agrupador {
	@Id
	@Column
	private Integer cd_campo_agrupador;

	@Column
	private Integer cd_campo_indicador;
	
	@Column
	private Integer cd_campo_formulario;
	
	@Column
	private Integer cd_campo_formulario_pai;

	@Column
	private Integer cd_formulario;

	@Column
	private String nm_campo_formulario;

	

	public Integer getCd_campo_agrupador() {
		return cd_campo_agrupador;
	}

	public void setCd_campo_agrupador(Integer cd_campo_agrupador) {
		this.cd_campo_agrupador = cd_campo_agrupador;
	}

	public Integer getCd_campo_indicador() {
		return cd_campo_indicador;
	}

	public void setCd_campo_indicador(Integer cd_campo_indicador) {
		this.cd_campo_indicador = cd_campo_indicador;
	}

	public Integer getCd_campo_formulario() {
		return cd_campo_formulario;
	}

	public void setCd_campo_formulario(Integer cd_campo_formulario) {
		this.cd_campo_formulario = cd_campo_formulario;
	}

	public Integer getCd_formulario() {
		return cd_formulario;
	}

	public void setCd_formulario(Integer cd_formulario) {
		this.cd_formulario = cd_formulario;
	}

	public Integer getCd_campo_formulario_pai() {
		return cd_campo_formulario_pai;
	}

	public void setCd_campo_formulario_pai(Integer cd_campo_formulario_pai) {
		this.cd_campo_formulario_pai = cd_campo_formulario_pai;
	}

	public String getNm_campo_formulario() {
		return nm_campo_formulario;
	}

	public void setNm_campo_formulario(String nm_campo_formulario) {
		this.nm_campo_formulario = nm_campo_formulario;
	}
	
}
