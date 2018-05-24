package br.com.opsocial.ejb.entity.agendar;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="agendar", schema="opsocial")
public class Agendar extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "agendar_seq")
	private Integer cd_agendamento;

	@Column
	private Integer cd_pessoa;

	@Column
	private Integer cd_formaatendimento;

	@Column
	private Integer cd_urgencia;

	@Column
	private Integer cd_especialidade;

	@Column
	private String ds_identificacao;

	@Column
	private String ds_medicorequerente;

	@Column
	private String ds_requerimento;

	@Column
	private Date dt_agendamento;

	@Column
	private Integer st_agendamento;

	public Integer getCd_agendamento() {
		return cd_agendamento;
	}

	public Integer getCd_pessoa() {
		return cd_pessoa;
	}

	public Integer getCd_formaatendimento() {
		return cd_formaatendimento;
	}

	public Integer getCd_urgencia() {
		return cd_urgencia;
	}

	public Integer getCd_especialidade() {
		return cd_especialidade;
	}

	public String getDs_identificacao() {
		return ds_identificacao;
	}

	public String getDs_medicorequerente() {
		return ds_medicorequerente;
	}

	public String getDs_requerimento() {
		return ds_requerimento;
	}

	public Date getDt_agendamento() {
		return dt_agendamento;
	}

	public Integer getSt_agendamento() {
		return st_agendamento;
	}

	public void setCd_agendamento(Integer cd_agendamento) {
		this.cd_agendamento = cd_agendamento;
	}

	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}

	public void setCd_formaatendimento(Integer cd_formaatendimento) {
		this.cd_formaatendimento = cd_formaatendimento;
	}

	public void setCd_urgencia(Integer cd_urgencia) {
		this.cd_urgencia = cd_urgencia;
	}

	public void setCd_especialidade(Integer cd_especialidade) {
		this.cd_especialidade = cd_especialidade;
	}

	public void setDs_identificacao(String ds_identificacao) {
		this.ds_identificacao = ds_identificacao;
	}

	public void setDs_medicorequerente(String ds_medicorequerente) {
		this.ds_medicorequerente = ds_medicorequerente;
	}

	public void setDs_requerimento(String ds_requerimento) {
		this.ds_requerimento = ds_requerimento;
	}

	public void setDt_agendamento(Date dt_agendamento) {
		this.dt_agendamento = dt_agendamento;
	}

	public void setSt_agendamento(Integer st_agendamento) {
		this.st_agendamento = st_agendamento;
	}
}