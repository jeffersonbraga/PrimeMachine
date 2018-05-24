package br.com.opsocial.ejb.entity.cronograma;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.opsocial.ejb.entity.generic.Persistent;

@Entity
@Table(name="cronograma", schema="opsocial")
public class Cronograma extends Persistent {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "cronograma_seq")
    private Integer cd_cronograma;

	@Column
	private Integer cd_agendamento;

	@Column
	private Integer cd_procedimento;

	@Column
	private Integer cd_etapa_procedimento;

	@Column
	private String ds_etapa;

	@Column
	private Date dt_cronograma;

	@Column
	private String st_cronograma;

	public Integer getCd_cronograma() {
		return cd_cronograma;
	}

	public Integer getCd_agendamento() {
		return cd_agendamento;
	}

	public Integer getCd_procedimento() {
		return cd_procedimento;
	}

	public Integer getCd_etapa_procedimento() {
		return cd_etapa_procedimento;
	}

	public String getDs_etapa() {
		return ds_etapa;
	}

	public Date getDt_cronograma() {
		return dt_cronograma;
	}

	public String getSt_cronograma() {
		return st_cronograma;
	}

	public void setCd_cronograma(Integer cd_cronograma) {
		this.cd_cronograma = cd_cronograma;
	}

	public void setCd_agendamento(Integer cd_agendamento) {
		this.cd_agendamento = cd_agendamento;
	}

	public void setCd_procedimento(Integer cd_procedimento) {
		this.cd_procedimento = cd_procedimento;
	}

	public void setCd_etapa_procedimento(Integer cd_etapa_procedimento) {
		this.cd_etapa_procedimento = cd_etapa_procedimento;
	}

	public void setDs_etapa(String ds_etapa) {
		this.ds_etapa = ds_etapa;
	}

	public void setDt_cronograma(Date dt_cronograma) {
		this.dt_cronograma = dt_cronograma;
	}

	public void setSt_cronograma(String st_cronograma) {
		this.st_cronograma = st_cronograma;
	}
}