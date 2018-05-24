package br.com.primum.modelo.escalonamento_formulario;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.escalonamento_usuario.Escalonamento_usuario;
import br.com.primum.modelo.formulario.Formulario;

@SuppressWarnings("serial")
@XmlRootElement(name = "Escalonamento_formulario")
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="escalonamento_formulario")
public class Escalonamento_formulario implements Serializable {

	@Id
	@Column
	private Integer cd_escalonamento_formulario;

	@Column
	private Integer cd_formulario;

	@Column
	private Integer cd_sub_formulario;

	@Column
	private Integer cd_usuario;
	
	@Column
	private Integer cd_unidade;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date hr_execucao;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio_vigencia;

	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_termino_vigencia;

	@Transient
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.escalonamento_usuario.Escalonamento_usuario", persistir=true, campoBuscar="cd_escalonamento_formulario")
	private List<Escalonamento_usuario> listaEscalonamento_usuario;

	@Transient
	@ObjetoBusca(notificador=false, caminhoClasseJava="br.com.primum.modelo.formulario.Formulario", campoBuscar="cd_formulario", campoComparar="cd_formulario", persistir=false)
	private Formulario formulario;

	public Integer getCd_escalonamento_formulario() {
		return cd_escalonamento_formulario;
	}

	public void setCd_escalonamento_formulario(Integer cd_escalonamento_formulario) {
		this.cd_escalonamento_formulario = cd_escalonamento_formulario;
	}

	public Integer getCd_formulario() {
		return cd_formulario;
	}

	public void setCd_formulario(Integer cd_formulario) {
		this.cd_formulario = cd_formulario;
	}

	public Integer getCd_unidade() {
		return cd_unidade;
	}

	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}

	public Integer getCd_sub_formulario() {
		return cd_sub_formulario;
	}

	public void setCd_sub_formulario(Integer cd_sub_formulario) {
		this.cd_sub_formulario = cd_sub_formulario;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Date getHr_execucao() {
		return hr_execucao;
	}

	public void setHr_execucao(Date hr_execucao) {
		this.hr_execucao = hr_execucao;
	}

	public Date getDt_inicio_vigencia() {
		return dt_inicio_vigencia;
	}

	public void setDt_inicio_vigencia(Date dt_inicio_vigencia) {
		this.dt_inicio_vigencia = dt_inicio_vigencia;
	}

	public Date getDt_termino_vigencia() {
		return dt_termino_vigencia;
	}

	public void setDt_termino_vigencia(Date dt_termino_vigencia) {
		this.dt_termino_vigencia = dt_termino_vigencia;
	}

	public List<Escalonamento_usuario> getListaEscalonamento_usuario() {
		return listaEscalonamento_usuario;
	}

	public void setListaEscalonamento_usuario(
			List<Escalonamento_usuario> listaEscalonamento_usuario) {
		this.listaEscalonamento_usuario = listaEscalonamento_usuario;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}
}