package br.com.primum.modelo.campo_indicador;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.modelo.campo_agrupador.Campo_agrupador;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="campo_indicador")
public class Campo_indicador{

	@Id
	@Column
	private Integer cd_campo_indicador;

	@Column
	private Integer cd_campo_formulario;

	@Column
	private Integer cd_formulario;
	
	@Column
	private Integer cd_formulario_indicador;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.campo_agrupador.Campo_agrupador", campoBuscar="cd_campo_indicador", persistir=true)
	private List<Campo_agrupador> listaCamposAgrupados;

	
	public List<Campo_agrupador> getListaCamposAgrupados() {
		return listaCamposAgrupados;
	}


	public void setListaCamposAgrupados(List<Campo_agrupador> listaCamposAgrupados) {
		this.listaCamposAgrupados = listaCamposAgrupados;
	}


	public Integer getCd_campo_indicador(){
		return cd_campo_indicador;
	}


	public void setCd_campo_indicador(Integer cd_campo_indicador){
		this.cd_campo_indicador = cd_campo_indicador;
	}


	public Integer getCd_campo_formulario(){
		return cd_campo_formulario;
	}


	public void setCd_campo_formulario(Integer cd_campo_formulario){
		this.cd_campo_formulario = cd_campo_formulario;
	}


	public Integer getCd_formulario(){
		return cd_formulario;
	}


	public void setCd_formulario(Integer cd_formulario){
		this.cd_formulario = cd_formulario;
	}


	public Integer getCd_formulario_indicador() {
		return cd_formulario_indicador;
	}


	public void setCd_formulario_indicador(Integer cd_formulario_indicador) {
		this.cd_formulario_indicador = cd_formulario_indicador;
	}

}