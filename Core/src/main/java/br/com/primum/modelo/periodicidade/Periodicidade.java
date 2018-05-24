package br.com.primum.modelo.periodicidade;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.escala_tempo.Escala_tempo;
import br.com.primum.modelo.ocorrencia_periodicidade.Ocorrencia_periodicidade;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="periodicidade")
public class Periodicidade {

	@Id
	private Integer cd_periodicidade;
	
	@Column
	private String nm_periodicidade;
	
	@Column
	private Integer cd_escala_tempo;
	
	@Column
	private Integer vl_tempo_periodicidade;

	@Column
	private Integer tp_periodicidade;

	@Column
	private Integer tp_ciclo_tempo;

	@Column
	private Integer nr_repeticao;

	
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.escala_tempo.Escala_tempo", campoBuscar="cd_escala_tempo", campoComparar="cd_escala_tempo", notificador=false, persistir=false)
	@Transient
	private Escala_tempo escala_tempo;
	
	@ObjetoBusca(caminhoClasseJava="br.com.primum.modelo.ocorrencia_periodicidade.Ocorrencia_periodicidade", campoBuscar="cd_periodicidade", campoComparar="cd_periodicidade", notificador=false, persistir=true)
	@Transient
	private Ocorrencia_periodicidade ocorrencia_periodicidade;
	
	public Integer getCd_periodicidade() {
		return cd_periodicidade;
	}

	public void setCd_periodicidade(Integer cd_periodicidade) {
		this.cd_periodicidade = cd_periodicidade;
	}

	public String getNm_periodicidade() {
		return nm_periodicidade;
	}

	public void setNm_periodicidade(String nm_periodicidade) {
		this.nm_periodicidade = nm_periodicidade;
	}

	public Integer getCd_escala_tempo() {
		return cd_escala_tempo;
	}

	public void setCd_escala_tempo(Integer cd_escala_tempo) {
		this.cd_escala_tempo = cd_escala_tempo;
	}

	public Integer getVl_tempo_periodicidade() {
		return vl_tempo_periodicidade;
	}

	public void setVl_tempo_periodicidade(Integer vl_tempo_periodicidade) {
		this.vl_tempo_periodicidade = vl_tempo_periodicidade;
	}

	public Escala_tempo getEscala_tempo() {
		return escala_tempo;
	}

	public void setEscala_tempo(Escala_tempo escala_tempo) {
		this.escala_tempo = escala_tempo;
	}

	public Integer getTp_periodicidade() {
		return tp_periodicidade;
	}

	public void setTp_periodicidade(Integer tp_periodicidade) {
		this.tp_periodicidade = tp_periodicidade;
	}

	public Ocorrencia_periodicidade getOcorrencia_periodicidade() {
		return ocorrencia_periodicidade;
	}

	public void setOcorrencia_periodicidade(
			Ocorrencia_periodicidade ocorrencia_periodicidade) {
		this.ocorrencia_periodicidade = ocorrencia_periodicidade;
	}

	public Integer getTp_ciclo_tempo() {
		return tp_ciclo_tempo;
	}

	public void setTp_ciclo_tempo(Integer tp_ciclo_tempo) {
		this.tp_ciclo_tempo = tp_ciclo_tempo;
	}

	public Integer getNr_repeticao() {
		return nr_repeticao;
	}

	public void setNr_repeticao(Integer nr_repeticao) {
		this.nr_repeticao = nr_repeticao;
	}
}
