 package br.com.primum.modelo.escalonamento_usuario;

import java.io.Serializable;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.primum.interfaces.ChaveEstrangeira;
import br.com.primum.modelo.escalonamento_formulario.Escalonamento_formulario;
import br.com.primum.modelo.execucao_formulario.Execucao_formulario;

@SuppressWarnings("serial")
@XmlRootElement(name = "Escalonamento_usuario")
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="escalonamento_usuario")
public class Escalonamento_usuario implements Serializable {

	@Id
	@Column
	private Integer cd_escalonamento_usuario;

	@ChaveEstrangeira(nomeObjetoOrigem="cd_escalonamento_formulario")
	@Column
	private Integer cd_escalonamento_formulario;

	@Column
	private Integer cd_usuario;

	@Column
	//@Temporal(value = TemporalType.TIMESTAMP)
	private Date hr_execucao;
	
	@Column
	private String ds_dias_repeticao;

	@Column
	//@Temporal(value = TemporalType.TIMESTAMP)
	private Date dt_termino_vigencia;

	//@Column
	//private Integer nr_proximo_dia;

	@Transient
	private Integer st_escalonamento_usuario;
	
	@Transient
	private Escalonamento_formulario escalonamento_formulario;
	
	@Transient
	private Execucao_formulario execucao_formulario;
	
	@Transient
	private String hr_execucao_efetiva;
	
	@Transient
	private String hr_termino_efetivo;

	
//	
//	public Integer getNr_proximo_dia() {
//		return nr_proximo_dia;
//	}
//
//	public void setNr_proximo_dia(Integer nr_proximo_dia) {
//		this.nr_proximo_dia = nr_proximo_dia;
//	}

	public String getHr_termino_efetivo() {
		return hr_termino_efetivo;
	}

	public void setHr_termino_efetivo(String hr_termino_efetivo) {
		this.hr_termino_efetivo = hr_termino_efetivo;
	}

	public String getHr_execucao_efetiva() {
		return hr_execucao_efetiva;
	}

	public void setHr_execucao_efetiva(String hr_execucao_efetiva) {
		this.hr_execucao_efetiva = hr_execucao_efetiva;
	}

	public Integer getCd_escalonamento_usuario() {
		return cd_escalonamento_usuario;
	}

	public void setCd_escalonamento_usuario(Integer cd_escalonamento_usuario) {
		this.cd_escalonamento_usuario = cd_escalonamento_usuario;
	}

	public Integer getCd_escalonamento_formulario() {
		return cd_escalonamento_formulario;
	}

	public void setCd_escalonamento_formulario(Integer cd_escalonamento_formulario) {
		this.cd_escalonamento_formulario = cd_escalonamento_formulario;
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

	public String getDs_dias_repeticao() {
		return ds_dias_repeticao;
	}

	public void setDs_dias_repeticao(String ds_dias_repeticao) {
		this.ds_dias_repeticao = ds_dias_repeticao;
	}

	public Escalonamento_formulario getEscalonamento_formulario() {
		return escalonamento_formulario;
	}

	public void setEscalonamento_formulario(
			Escalonamento_formulario escalonamento_formulario) {
		this.escalonamento_formulario = escalonamento_formulario;
	}

	public Integer getSt_escalonamento_usuario() {
		return st_escalonamento_usuario;
	}

	public void setSt_escalonamento_usuario(Integer st_escalonamento_usuario) {
		this.st_escalonamento_usuario = st_escalonamento_usuario;
	}

	public Execucao_formulario getExecucao_formulario() {
		return execucao_formulario;
	}

	public void setExecucao_formulario(Execucao_formulario execucao_formulario) {
		this.execucao_formulario = execucao_formulario;
	}

	public Date getDt_termino_vigencia() {
		return dt_termino_vigencia;
	}

	public void setDt_termino_vigencia(Date dt_termino_vigencia) {
		this.dt_termino_vigencia = dt_termino_vigencia;
	}
	

    @Override
    public String toString() {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(this.getHr_execucao());
        String horario = (cal.get(cal.HOUR_OF_DAY) + ":" + cal.get(cal.MINUTE));




        return this.escalonamento_formulario.getFormulario().getNm_formulario() + horario;
    }
}