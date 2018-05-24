package br.com.primum.modelo.registro_treinamento;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import br.com.primum.interfaces.ListaBusca;
import br.com.primum.interfaces.ObjetoBusca;
import br.com.primum.modelo.descritivo_treinamento.Descritivo_treinamento;
import br.com.primum.modelo.participantes_treinamento.Participantes_treinamento;

@Entity @org.hibernate.annotations.Entity(mutable=true)
@Table(name="registro_treinamento")
public class Registro_treinamento {
	
	@Id
	@Column
	private Integer cd_registro_treinamento;

	@Column
	private Integer cd_descritivo_treinamento;
	
	@Column
	private Integer cd_pessoa;
	
	//@Column
	//private Float nr_custo_previsto;
	
	@Column
	private java.math.BigDecimal nr_custo_previsto;

	@Column
	private Integer tp_origem_instrutor;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_inicio;
	
	@Column
	//@Temporal(value = TemporalType.DATE)
	private Date dt_termino;
	
	@Column
	private Integer hr_inicio;
	
	@Column
	private Integer hr_termino;
	
	@Column
	private String ds_observacao_instrutor;
	
	@Column
	private String ds_avaliacao_instrutor;
	
	@Column
	private Integer cd_unidade;
	
	@ObjetoBusca(notificador=false, persistir=false, caminhoClasseJava="br.com.primum.modelo.descritivo_treinamento.Descritivo_treinamento", campoBuscar="cd_descritivo_treinamento", campoComparar="cd_descritivo_treinamento")
	@Transient
	private Descritivo_treinamento descritivo_treinamento;
	
	@ListaBusca(caminhoClasseJava="br.com.primum.modelo.participante_treinamento.Participante_treinamento", persistir=true, campoBuscar="cd_registro_treinamento")
	@Transient
	private List<Participantes_treinamento> listaParticipantes;

	public Integer getCd_registro_treinamento() {
		return cd_registro_treinamento;
	}


	public void setCd_registro_treinamento(Integer cd_registro_treinamento) {
		this.cd_registro_treinamento = cd_registro_treinamento;
	}


	public Integer getCd_descritivo_treinamento() {
		return cd_descritivo_treinamento;
	}


	public void setCd_descritivo_treinamento(Integer cd_descritivo_treinamento) {
		this.cd_descritivo_treinamento = cd_descritivo_treinamento;
	}


	public Integer getCd_pessoa() {
		return cd_pessoa;
	}


	public void setCd_pessoa(Integer cd_pessoa) {
		this.cd_pessoa = cd_pessoa;
	}


	public java.math.BigDecimal getNr_custo_previsto() {
		return nr_custo_previsto;
	}


	public void setNr_custo_previsto(java.math.BigDecimal nr_custo_previsto) {
		this.nr_custo_previsto = nr_custo_previsto;
	}


	public Integer getTp_origem_instrutor() {
		return tp_origem_instrutor;
	}


	public void setTp_origem_instrutor(Integer tp_origem_instrutor) {
		this.tp_origem_instrutor = tp_origem_instrutor;
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


	public Integer getHr_inicio() {
		return hr_inicio;
	}


	public void setHr_inicio(Integer hr_inicio) {
		this.hr_inicio = hr_inicio;
	}


	public Integer getHr_termino() {
		return hr_termino;
	}


	public void setHr_termino(Integer hr_termino) {
		this.hr_termino = hr_termino;
	}


	public String getDs_observacao_instrutor() {
		return ds_observacao_instrutor;
	}


	public void setDs_observacao_instrutor(String ds_observacao_instrutor) {
		this.ds_observacao_instrutor = ds_observacao_instrutor;
	}


	public String getDs_avaliacao_instrutor() {
		return ds_avaliacao_instrutor;
	}


	public void setDs_avaliacao_instrutor(String ds_avaliacao_instrutor) {
		this.ds_avaliacao_instrutor = ds_avaliacao_instrutor;
	}


	public List<Participantes_treinamento> getListaParticipantes() {
		return listaParticipantes;
	}


	public void setListaParticipantes(
			List<Participantes_treinamento> listaParticipantes) {
		this.listaParticipantes = listaParticipantes;
	}
	
	public Descritivo_treinamento getDescritivo_treinamento() {
		return descritivo_treinamento;
	}

	public void setDescritivo_treinamento(Descritivo_treinamento descritivo_treinamento) {
		this.descritivo_treinamento = descritivo_treinamento;
	}


	public Integer getCd_unidade() {
		return cd_unidade;
	}


	public void setCd_unidade(Integer cd_unidade) {
		this.cd_unidade = cd_unidade;
	}
}
