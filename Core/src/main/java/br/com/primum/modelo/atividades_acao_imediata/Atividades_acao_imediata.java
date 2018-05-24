package br.com.primum.modelo.atividades_acao_imediata;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="atividades_acao_imediata")
public class Atividades_acao_imediata {

	@Id
	@Column
	private Integer cd_atividades_acao_imediata;

	@Column
	private Integer cd_acao_corretiva_helpdesk;
	
	@Column
	private Integer cd_atividades;
	
	@Column
	private String ds_atividades;
	
	@Column
	private Integer cd_responsavel;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_prazo_previsto;
	
	@Column
	private Integer st_status;

	public Integer getCd_atividades_acao_imediata() {
		return cd_atividades_acao_imediata;
	}

	public void setCd_atividades_acao_imediata(Integer cd_atividades_acao_imediata) {
		this.cd_atividades_acao_imediata = cd_atividades_acao_imediata;
	}

	

	public Integer getCd_acao_corretiva_helpdesk() {
		return cd_acao_corretiva_helpdesk;
	}

	public void setCd_acao_corretiva_helpdesk(Integer cd_acao_corretiva_helpdesk) {
		this.cd_acao_corretiva_helpdesk = cd_acao_corretiva_helpdesk;
	}

	public Integer getCd_atividades() {
		return cd_atividades;
	}

	public void setCd_atividades(Integer cd_atividades) {
		this.cd_atividades = cd_atividades;
	}

	public String getDs_atividades() {
		return ds_atividades;
	}

	public void setDs_atividades(String ds_atividades) {
		this.ds_atividades = ds_atividades;
	}

	public Integer getCd_responsavel() {
		return cd_responsavel;
	}

	public void setCd_responsavel(Integer cd_responsavel) {
		this.cd_responsavel = cd_responsavel;
	}

	public Date getDt_prazo_previsto() {
		return dt_prazo_previsto;
	}

	public void setDt_prazo_previsto(Date dt_prazo_previsto) {
		this.dt_prazo_previsto = dt_prazo_previsto;
	}

	public Integer getSt_status() {
		return st_status;
	}

	public void setSt_status(Integer st_status) {
		this.st_status = st_status;
	}

}
