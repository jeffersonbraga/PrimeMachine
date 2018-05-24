package br.com.primum.modelo.atividade_acao_pl_hp;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="atividade_acao_pl_hp")
public class Atividade_acao_pl_hp {

	@Id
	@Column
	private Integer cd_atividade_acao_pl_hp;
	
	@Column
	private Integer cd_acao_plano_acao_hp;
	
	@Column
	private Integer cd_atividades;
	
	@Column
	private String ds_atividades;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_prazo_previsto;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_realizado;
	
	@Column
	private Integer cd_usuario_responsavel;
	
	@Column
	private Integer st_atividade_acao_pl_hp;
	
	
	

	public Integer getCd_atividade_acao_pl_hp() {
		return cd_atividade_acao_pl_hp;
	}

	public void setCd_atividade_acao_pl_hp(Integer cd_atividade_acao_pl_hp) {
		this.cd_atividade_acao_pl_hp = cd_atividade_acao_pl_hp;
	}

	public Integer getCd_acao_plano_acao_hp() {
		return cd_acao_plano_acao_hp;
	}

	public void setCd_acao_plano_acao_hp(Integer cd_acao_plano_acao_hp) {
		this.cd_acao_plano_acao_hp = cd_acao_plano_acao_hp;
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

	public Date getDt_inicio() {
		return dt_inicio;
	}

	public void setDt_inicio(Date dt_inicio) {
		this.dt_inicio = dt_inicio;
	}

	public Date getDt_prazo_previsto() {
		return dt_prazo_previsto;
	}

	public void setDt_prazo_previsto(Date dt_prazo_previsto) {
		this.dt_prazo_previsto = dt_prazo_previsto;
	}

	public Date getDt_realizado() {
		return dt_realizado;
	}

	public void setDt_realizado(Date dt_realizado) {
		this.dt_realizado = dt_realizado;
	}

	public Integer getCd_usuario_responsavel() {
		return cd_usuario_responsavel;
	}

	public void setCd_usuario_responsavel(Integer cd_usuario_responsavel) {
		this.cd_usuario_responsavel = cd_usuario_responsavel;
	}

	public Integer getSt_atividade_acao_pl_hp() {
		return st_atividade_acao_pl_hp;
	}

	public void setSt_atividade_acao_pl_hp(Integer st_atividade_acao_pl_hp) {
		this.st_atividade_acao_pl_hp = st_atividade_acao_pl_hp;
	}

}
