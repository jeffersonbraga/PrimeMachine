package br.com.primum.modelo.ocorrencia;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.contexto.ContextoAplicacao;


/**
 */
@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(catalog = "gestor", name = "ocorrencia")
public class Ocorrencia implements Serializable {

	/**
	 * 
	 */
	@Transient	
	private static final long serialVersionUID = ContextoAplicacao.SERIAL_PADRAO;// 1L;//-5360363345633325133L;

	@Id
	@Column(name = "cd_ocorrencia", nullable = false)
	private Integer cd_ocorrencia;

	@Column(name = "cd_atividade", nullable = false)
	private Integer cd_atividade;

	@Column(name = "cd_usuario", nullable = false)
	private Integer cd_usuario;

	////@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name = "dt_ocorrencia", nullable = false)
	//@Temporal(value = TemporalType.DATE)
	private Calendar dt_ocorrencia;
	
	@Column(name = "ds_ocorrencia", nullable = false, columnDefinition = "TEXT")
	private String ds_ocorrencia;
	
	@Column(name = "st_atividade", nullable = false)
	private Integer st_atividade;

	public Integer getCd_ocorrencia() {
		return cd_ocorrencia;
	}

	public void setCd_ocorrencia(Integer cd_ocorrencia) {
		this.cd_ocorrencia = cd_ocorrencia;
	}

	public Integer getCd_atividade() {
		return cd_atividade;
	}

	public void setCd_atividade(Integer cd_atividade) {
		this.cd_atividade = cd_atividade;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Calendar getDt_ocorrencia() {
		return dt_ocorrencia;
	}

	public void setDt_ocorrencia(Calendar dt_ocorrencia) {
		this.dt_ocorrencia = dt_ocorrencia;
	}

	public String getDs_ocorrencia() {
		return ds_ocorrencia;
	}

	public void setDs_ocorrencia(String ds_ocorrencia) {
		this.ds_ocorrencia = ds_ocorrencia;
	}

	public Integer getSt_atividade() {
		return st_atividade;
	}

	public void setSt_atividade(Integer st_atividade) {
		this.st_atividade = st_atividade;
	}
}