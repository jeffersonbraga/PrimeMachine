package br.com.primum.modelo.plano_acao_ocorrencia;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="plano_acao_ocorrencia")
public class Plano_acao_ocorrencia {

	@Id
	@Column
	private Integer cd_plano_acao_ocorrencia;
	
	@Column
	private Integer cd_resolucao_ocorrencia;
	
	@Column
	private Integer cd_usuario;
	
	@Column
	private Integer cd_acao_corretiva;
	
	@Column
	private Integer st_plano_acao_ocorrencia;
	
	@Column
	private String ds_plano_acao_ocorrencia;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_termino;


	public Integer getCd_plano_acao_ocorrencia() {
		return cd_plano_acao_ocorrencia;
	}

	public void setCd_plano_acao_ocorrencia(Integer cd_plano_acao_ocorrencia) {
		this.cd_plano_acao_ocorrencia = cd_plano_acao_ocorrencia;
	}

	public Integer getCd_resolucao_ocorrencia() {
		return cd_resolucao_ocorrencia;
	}

	public void setCd_resolucao_ocorrencia(Integer cd_resolucao_ocorrencia) {
		this.cd_resolucao_ocorrencia = cd_resolucao_ocorrencia;
	}

	public Integer getCd_usuario() {
		return cd_usuario;
	}

	public void setCd_usuario(Integer cd_usuario) {
		this.cd_usuario = cd_usuario;
	}

	public Integer getCd_acao_corretiva() {
		return cd_acao_corretiva;
	}

	public void setCd_acao_corretiva(Integer cd_acao_corretiva) {
		this.cd_acao_corretiva = cd_acao_corretiva;
	}

	public Integer getSt_plano_acao_ocorrencia() {
		return st_plano_acao_ocorrencia;
	}

	public void setSt_plano_acao_ocorrencia(Integer st_plano_acao_ocorrencia) {
		this.st_plano_acao_ocorrencia = st_plano_acao_ocorrencia;
	}

	public String getDs_plano_acao_ocorrencia() {
		return ds_plano_acao_ocorrencia;
	}

	public void setDs_plano_acao_ocorrencia(String ds_plano_acao_ocorrencia) {
		this.ds_plano_acao_ocorrencia = ds_plano_acao_ocorrencia;
	}

	public Date getDt_inicio() {
		return dt_inicio;
	}

	public void setDt_inicio(Date dt_inicio) {
		this.dt_inicio = dt_inicio;
	}

	public Date getDt_termino() {
		return dt_termino;
	}

	public void setDt_termino(Date dt_termino) {
		this.dt_termino = dt_termino;
	}

}
