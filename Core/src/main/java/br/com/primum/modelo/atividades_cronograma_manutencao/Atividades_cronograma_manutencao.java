package br.com.primum.modelo.atividades_cronograma_manutencao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="atividades_cronograma_manutencao")
public class Atividades_cronograma_manutencao {
	
	@Id
	@Column
	private Integer cd_atividades_cronograma_manutencao;
	
	@Column
	private Integer cd_cronograma_manutencao;
	
	@Column
	private Integer cd_atividades;
	
	@Column
	private Integer nr_ordem_executar;
	
	@Column
	private Integer nr_quantidade;
	
	@Column
	private Integer nr_tempo_estimado;
	
	@Column
	private java.math.BigDecimal vl_custo_previsto;

	@Transient
	private Boolean marcada;
	
	

	public Boolean getMarcada() {
		return marcada;
	}

	public void setMarcada(Boolean marcada) {
		this.marcada = marcada;
	}

	public Integer getCd_atividades_cronograma_manutencao() {
		return cd_atividades_cronograma_manutencao;
	}

	public void setCd_atividades_cronograma_manutencao(
			Integer cd_atividades_cronograma_manutencao) {
		this.cd_atividades_cronograma_manutencao = cd_atividades_cronograma_manutencao;
	}

	public Integer getCd_cronograma_manutencao() {
		return cd_cronograma_manutencao;
	}

	public void setCd_cronograma_manutencao(Integer cd_cronograma_manutencao) {
		this.cd_cronograma_manutencao = cd_cronograma_manutencao;
	}

	public Integer getCd_atividades() {
		return cd_atividades;
	}

	public void setCd_atividades(Integer cd_atividades) {
		this.cd_atividades = cd_atividades;
	}

	public Integer getNr_ordem_executar() {
		return nr_ordem_executar;
	}

	public void setNr_ordem_executar(Integer nr_ordem_executar) {
		this.nr_ordem_executar = nr_ordem_executar;
	}

	public Integer getNr_quantidade() {
		return nr_quantidade;
	}

	public void setNr_quantidade(Integer nr_quantidade) {
		this.nr_quantidade = nr_quantidade;
	}

	public Integer getNr_tempo_estimado() {
		return nr_tempo_estimado;
	}

	public void setNr_tempo_estimado(Integer nr_tempo_estimado) {
		this.nr_tempo_estimado = nr_tempo_estimado;
	}

	public java.math.BigDecimal getVl_custo_previsto() {
		return vl_custo_previsto;
	}

	public void setVl_custo_previsto(java.math.BigDecimal vl_custo_previsto) {
		this.vl_custo_previsto = vl_custo_previsto;
	}

}
