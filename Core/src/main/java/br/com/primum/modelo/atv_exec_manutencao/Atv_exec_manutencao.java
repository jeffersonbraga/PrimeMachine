package br.com.primum.modelo.atv_exec_manutencao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="atv_exec_manutencao")
public class Atv_exec_manutencao {

	@Id
	@Column
	private Integer cd_atv_exec_manutencao;

	@Column
	private Integer cd_executar_manutencao;

	@Column
	private Integer cd_atividades;

	@Column
	private Integer nr_quantidade;
	
	@Column
	private Integer nr_tempo_horas;
	
	@Column
	private String ds_atividade_execucao;

	public Integer getCd_atv_exec_manutencao() {
		return cd_atv_exec_manutencao;
	}

	public void setCd_atv_exec_manutencao(Integer cd_atv_exec_manutencao) {
		this.cd_atv_exec_manutencao = cd_atv_exec_manutencao;
	}

	public Integer getCd_executar_manutencao() {
		return cd_executar_manutencao;
	}

	public void setCd_executar_manutencao(Integer cd_executar_manutencao) {
		this.cd_executar_manutencao = cd_executar_manutencao;
	}

	public Integer getCd_atividades() {
		return cd_atividades;
	}

	public void setCd_atividades(Integer cd_atividades) {
		this.cd_atividades = cd_atividades;
	}

	public Integer getNr_quantidade() {
		return nr_quantidade;
	}

	public void setNr_quantidade(Integer nr_quantidade) {
		this.nr_quantidade = nr_quantidade;
	}

	public Integer getNr_tempo_horas() {
		return nr_tempo_horas;
	}

	public void setNr_tempo_horas(Integer nr_tempo_horas) {
		this.nr_tempo_horas = nr_tempo_horas;
	}

	public String getDs_atividade_execucao() {
		return ds_atividade_execucao;
	}

	public void setDs_atividade_execucao(String ds_atividade_execucao) {
		this.ds_atividade_execucao = ds_atividade_execucao;
	}

}
