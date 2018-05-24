package br.com.primum.modelo.formulario_indicador;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.campo_indicador.Campo_indicador;
import br.com.primum.modelo.formulario.Formulario;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="formulario_indicador")
public class Formulario_indicador{

	@Id
	@Column
	private Integer cd_formulario_indicador;

	@Column
	private Integer cd_indicador_customizado;

	@Column
	private Integer cd_formulario;

	@Transient
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.formulario.Formulario", campoBuscar="cd_formulario", campoComparar="cd_formulario",persistir=false, notificador=false)
	private Formulario formulario;
	
	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.campo_indicador.Campo_indicador", campoBuscar="cd_formulario_indicador", persistir=true)
	private List<Campo_indicador> listaCampos_indicador;
	
	public Integer getCd_formulario_indicador(){
		return cd_formulario_indicador;
	}


	public void setCd_formulario_indicador(Integer cd_formulario_indicador){
		this.cd_formulario_indicador = cd_formulario_indicador;
	}


	public Integer getCd_indicador_customizado(){
		return cd_indicador_customizado;
	}


	public void setCd_indicador_customizado(Integer cd_indicador_customizado){
		this.cd_indicador_customizado = cd_indicador_customizado;
	}


	public Integer getCd_formulario(){
		return cd_formulario;
	}


	public void setCd_formulario(Integer cd_formulario){
		this.cd_formulario = cd_formulario;
	}


	public Formulario getFormulario() {
		return formulario;
	}


	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}


	public List<Campo_indicador> getListaCampos_indicador() {
		return listaCampos_indicador;
	}


	public void setListaCampos_indicador(List<Campo_indicador> listaCampos_indicador) {
		this.listaCampos_indicador = listaCampos_indicador;
	}

}